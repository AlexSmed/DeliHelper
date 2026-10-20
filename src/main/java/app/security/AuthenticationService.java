package app.security;

import app.dao.EmployeeDAO;
import app.entities.Employee;

public class AuthenticationService {

    private final EmployeeDAO employeeDAO;

    public AuthenticationService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public Employee login(String username, String password) {
        Employee employee = employeeDAO.findByUsername(username);

        if (employee == null || !employee.verifyPassword(password)) {
            return null;
        }

        return employee;
    }
}