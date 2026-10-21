package app;


import app.dao.EmployeeDAO;
import app.entities.Employee;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        EmployeeDAO employeeDAO = new EmployeeDAO();
        Scanner scanner = new Scanner(System.in);

        try {
            // Create an employee for demonstration
          /*  Employee employee = new Employee();
            employee.setUsername("john");
            employee.setPassword("Password123!");
            employee.setStore_id(8830);
            employee.setIs_admin(true);

            employeeDAO.save(employee);*/

            System.out.println("Employee created successfully!");

            // Login
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            Employee loggedInEmployee =
                    employeeDAO.findByUsername(username);

            if (loggedInEmployee == null
                    || !loggedInEmployee.verifyPassword(password)) {

                System.out.println("Invalid username or password.");
                return;
            }

            System.out.println("Login successful!");
            System.out.println(
                    "Roles: "
                            + loggedInEmployee.getRolesAsStrings()
            );

            // Check authorization
            if (loggedInEmployee.getRolesAsStrings()
                    .contains("ADMIN")) {

                System.out.println("Welcome, administrator!");
            } else {
                System.out.println("Welcome, employee!");
            }

        } finally {
            scanner.close();
            employeeDAO.close();
        }
    }
}
