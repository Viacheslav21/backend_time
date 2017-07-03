package ua.backend.task.DB.entity;

import javax.persistence.*;

/**
 * Created by Slavik on 30.06.17.
 */
@Entity
@Table(name = "contacts")
public class ContactEntity {

    public ContactEntity() {}

    public ContactEntity(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String name;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}