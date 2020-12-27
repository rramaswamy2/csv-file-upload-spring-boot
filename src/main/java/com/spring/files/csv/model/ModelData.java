package com.spring.files.csv.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "model_table")
public class ModelData {

  @Id
  //@GeneratedValue
  @Column(name = "id")
  @NotNull(message = "id is primary key and should not be blank")
  private long id;
  
  @Column(name = "name")
  @NotEmpty(message = "Please provide a name")
  private String name;

  @Column(name = "description")
  @NotEmpty(message = "Please provide a description")
  private String description;

  @Column(name = "updated_timestamp")
  private String updated_timestamp;

  public ModelData() {

  }

  public ModelData(long id, String name, String description, String timestamp) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.updated_timestamp = timestamp;
  }

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

public String getUpdated_timestamp() {
	return updated_timestamp;
}

public void setUpdated_timestamp(String updated_timestamp) {
	this.updated_timestamp = updated_timestamp;
}

@Override
public String toString() {
	return "ModelData [id=" + id + ", name=" + name + ", description=" + description + ", updated_timestamp="
			+ updated_timestamp + "]";
}

@Override
public int hashCode() {
	return Objects.hash(description, id, name, updated_timestamp);
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	ModelData other = (ModelData) obj;
	return Objects.equals(description, other.description) && id == other.id && Objects.equals(name, other.name)
			&& Objects.equals(updated_timestamp, other.updated_timestamp);
}
  
  

 

}
