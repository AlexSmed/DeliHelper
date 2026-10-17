package app.entities;

import jakarta.persistence.*;

@Entity
public class Routines {

    @Id
    @GeneratedValue
    @Column (name = "id")
    private long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "description", length = 10000)
    private String description;

    @Column(name = "store_id")
    private int store_id;

    public Routines(){

    }
}
