package ru.drogunov.springproject.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Task {
    
    @Id
    private Long id;

    private String description;
    
    private Status status;
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
}
