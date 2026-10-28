package app.dto;

import app.entities.Bulletin;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class BulletinDTO {

    private long id;

    private String title;

    private String picture;

    private String description;

    private int store_id;

    private LocalDate date;

    public BulletinDTO(Bulletin bulletin) {
        this.id = bulletin.getId();
        this.title = bulletin.getTitle();
        this.picture = bulletin.getPicture();
        this.description = bulletin.getDescription();
        this.store_id = bulletin.getStore_id();
        this.date = bulletin.getDate();
    }
}
