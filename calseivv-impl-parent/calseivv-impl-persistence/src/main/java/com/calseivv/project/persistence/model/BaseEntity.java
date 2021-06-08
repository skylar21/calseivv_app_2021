package com.calseivv.project.persistence.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class BaseEntity<K> implements Serializable {

    private static final long serialVersionUID = 9000507224489891053L;

    public abstract K getId();

    public abstract void setId(K id);

    private LocalDateTime createdAt = LocalDateTime.now();

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
