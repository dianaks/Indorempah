package com.blibli.future.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Nita on 15/10/2016.
 */



@Entity
public class Category {

    @Id
    private long id;
    private String name;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
