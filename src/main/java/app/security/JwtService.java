
package app.security;

import app.entities.Employee;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.Instant;
import java.util.Date;

public class JwtService {

    private final Algorithm algorithm;

    public JwtService(String secret) {
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException(
                    "JWT secret must be at least 32 characters"
            );
        }

        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String generateToken(Employee employee) {
        Instant now = Instant.now();

        return JWT.create()
                .withIssuer("DeliHelper")
                .withSubject(String.valueOf(employee.getId()))
                .withClaim("username", employee.getUsername())
                .withClaim("store_id", employee.getStore_id())
                .withArrayClaim(
                        "roles",
                        employee.getRolesAsStrings().toArray(new String[0])
                )
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusSeconds(900)))
                .sign(algorithm);
    }

    public DecodedJWT verifyToken(String token) {
        return JWT.require(algorithm)
                .withIssuer("DeliHelper")
                .build()
                .verify(token);
    }
}
