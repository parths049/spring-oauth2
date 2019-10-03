package com.poc.model;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> implements Serializable {

private static final long serialVersionUID = 1L;

  public abstract ID getId();

  @JsonIgnore
  @Column(name = "created_datetime")
  protected Date createdDatetime;

  @JsonIgnore
  @Column(name = "modified_datetime")
  protected Date modifiedDatetime;

  @PrePersist
  public void prePersist() {
    this.createdDatetime = new Date();
    this.modifiedDatetime = this.createdDatetime;
  }

  @Override
  public String toString() {
    return getClass() + " [getId()=" + getId() + "]";
  }

  @PreUpdate
  public void preUpdate() {
    this.modifiedDatetime = new Date();
  }

  protected Date getCreateDate() {
    return this.createdDatetime;
  }

  protected Date getModifyDate() {
    return this.modifiedDatetime;
  }

  protected void setCreatedDatetime(Date createdDatetime) {
	this.createdDatetime = createdDatetime;
  }
  
  public Date getCreatedDatetime() {
	return createdDatetime;
  }
}
