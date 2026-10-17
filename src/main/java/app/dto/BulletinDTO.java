package app.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BulletinDTO {

    private long id;

    private String title;

    private String picture;

    private String description;

    private int store_id;

    private LocalDate date;
}
