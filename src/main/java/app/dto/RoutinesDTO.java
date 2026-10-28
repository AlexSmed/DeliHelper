package app.dto;

import app.entities.Routines;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoutinesDTO {

    private long id;

    private String title;

    private String description;

    private int store_id;

    // Convert a Routines entity into a DTO
    public RoutinesDTO(Routines routine) {
        this.id = routine.getId();
        this.title = routine.getTitle();
        this.description = routine.getDescription();
        this.store_id = routine.getStore_id();
    }
}
