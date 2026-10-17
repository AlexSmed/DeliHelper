package app.dto;

import jakarta.persistence.Column;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDTO {

    private int id;

    private String username;

    private String  password;

    private int store_id;

    private boolean is_admin;
}
