
package app.security;

import app.dao.EmployeeDAO;
import app.entities.Employee;
import io.javalin.http.Context;
import java.util.Map;

public class AuthController {

    private final EmployeeDAO employeeDAO;
    private final JwtService jwtService;

    public AuthController(
            EmployeeDAO employeeDAO,
            JwtService jwtService) {
        this.employeeDAO = employeeDAO;
        this.jwtService = jwtService;
    }

    public void login(Context ctx) {
        LoginRequest request;

        try {
            request = ctx.bodyAsClass(LoginRequest.class);
        } catch (Exception e) {
            ctx.status(400).json(
                    Map.of("error", "Invalid login request")
            );
            return;
        }

        if (request.username() == null ||
                request.password() == null) {
            ctx.status(400).json(
                    Map.of("error", "Username and password are required")
            );
            return;
        }

        Employee employee =
                employeeDAO.findByUsername(request.username());

        if (employee == null ||
                !employee.verifyPassword(request.password())) {
            ctx.status(401).json(
                    Map.of("error", "Invalid username or password")
            );
            return;
        }

        String token = jwtService.generateToken(employee);

        ctx.json(Map.of(
                "token", token,
                "tokenType", "Bearer",
                "expiresIn", 900
        ));
    }

    public record LoginRequest(
            String username,
            String password
    ) {}
}
