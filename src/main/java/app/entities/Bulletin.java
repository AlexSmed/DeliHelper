package app.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Bulletin {

    @Id
    @GeneratedValue
    @Column (name = "id")
    private long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "picture", length = 300)
    private String picture;

    @Column(name = "description", length = 10000)
    private String description;

    @Column(name = "store_id")
    private int store_id;

    @Column(name = "Date")
    private LocalDate date;

    public Bulletin(){

    }

}

