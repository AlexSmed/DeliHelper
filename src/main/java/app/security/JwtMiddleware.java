
package app.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;
import java.util.Map;

public class JwtMiddleware {

    public static void register(
            Javalin app,
            JwtService jwtService) {

        app.before(ctx -> {
            String path = ctx.path();

            // Public routes
            if (path.equals("/api/auth/login")
                    || path.equals("/api/auth/healthcheck")) {
                return;
            }

            // Only authenticate API routes.
            if (!path.startsWith("/api/")) {
                return;
            }

            String authorization =
                    ctx.header("Authorization");

            if (authorization == null
                    || !authorization.startsWith("Bearer ")) {
                reject(ctx, 401, "Authentication required");
                return;
            }

            String token = authorization.substring(7);

            try {
                DecodedJWT jwt =
                        jwtService.verifyToken(token);

                ctx.attribute(
                        "employeeId",
                        Integer.parseInt(jwt.getSubject())
                );

                ctx.attribute(
                        "storeId",
                        jwt.getClaim("store_id").asInt()
                );

                ctx.attribute(
                        "roles",
                        jwt.getClaim("roles")
                                .asList(String.class)
                );

            } catch (JWTVerificationException
                     | IllegalArgumentException e) {
                reject(ctx, 401, "Invalid or expired token");
            }
        });
    }

    private static void reject(
            Context ctx,
            int status,
            String message) {

        ctx.status(status).json(
                Map.of("error", message)
        );

        ctx.skipRemainingHandlers();
    }

    public static boolean isAdmin(Context ctx) {
        List<String> roles = ctx.attribute("roles");
        return roles != null && roles.contains("ADMIN");
    }
}
