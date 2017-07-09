package com.rajcorporation.tender.security.auth.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


import com.fasterxml.jackson.annotation.JsonIgnore;


@MappedSuperclass
public class DatedModel implements Serializable {

    @Column( name = "created_at" )
    private Timestamp createdAt;

    @Column( name = "updated_at" )
    private Timestamp updatedAt;

    public DatedModel() {
    }

    @JsonIgnore
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt( Timestamp createdAt ) {
        this.createdAt = createdAt;
    }

    @JsonIgnore
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt( Timestamp updatedAt ) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    @PreUpdate
    public void preSave() {
        preSaveImpl();
    }

    protected void preSaveImpl() {
        Timestamp now = Timestamp.from(Instant.now());
        if ( getCreatedAt() == null ) {
            setCreatedAt( now );
        }
        setUpdatedAt( now );
    }

}
