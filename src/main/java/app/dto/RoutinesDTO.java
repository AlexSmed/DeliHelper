package app.dto;

import jakarta.persistence.Column;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoutinesDTO {

    private long id;

    private String title;

    private String description;

    private int store_id;

}
