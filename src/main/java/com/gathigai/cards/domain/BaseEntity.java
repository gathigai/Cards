package com.gathigai.cards.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    public UUID publicId;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Basic
    @Column(name = "create_date", updatable = false, columnDefinition = "DATE")
    private Date createDate;


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "YYYY-MM-DD HH:MM")
    @UpdateTimestamp
    @Basic
    @Column(name = "update_date", updatable = false)
    private Date updateDate;

    @JsonIgnore
    @Basic
    @Column(name = "deleted", columnDefinition = "boolean default false")
    private boolean deleted;

    @JsonIgnore
    @Basic
    @Column(name = "created_by")
    private UUID createdBy;

    @JsonIgnore
    @Basic
    @Column(name = "updated_by")
    private UUID updatedBy;

    @JsonIgnore
    @Basic
    @Column(name = "deleted_by")
    private UUID deletedBy;

    @Version
    @Basic
    @Column(name = "version")
    private Integer version;


    @PrePersist
    public void prePersist() {
        this.deleted = false;
        this.publicId = UUID.randomUUID();
    }

    @PreUpdate
    public void preUpdate() {

    }
}
