package com.haulmont.testtask.prescription;

import javax.persistence.*;
import java.sql.Date;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
@Entity
@Table(name = "prescriptions")
public class Prescription {
  
//  public enum Priority {
//    UNSOLET,
//    CITO,
//    STATIM
//  }
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String description;
  private Long doctorId;
  private Long patientId;
  private Date creationDate;
  private Integer validityLength;
//  private Priority priority;
  private String priority;
  
  public Prescription(){}
  public Prescription(Long doctorId, Long patientId, String description,
                      Date creationDate, Integer validityLength, String/*Priority*/ priority) {
    this.doctorId = doctorId;
    this.patientId = patientId;
    this.description = description;
    this.creationDate = creationDate;
    this.validityLength = validityLength;
    this.priority = priority;
  }
  
  public Long getId() {
    return id;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public Long getDoctorId() {
    return doctorId;
  }
  
  public void setDoctorId(Long doctorId) {
    this.doctorId = doctorId;
  }
  
  public Long getPatientId() {
    return patientId;
  }
  
  public void setPatientId(Long patientId) {
    this.patientId = patientId;
  }
  
  public Date getCreationDate() {
    return creationDate;
  }
  
  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }
  
  public Integer getValidityLength() {
    return validityLength;
  }
  
  public void setValidityLength(Integer validityLength) {
    this.validityLength = validityLength;
  }
  
  public String/*Priority*/ getPriority() {
    return priority;
  }
  
  public void setPriority(String/*Priority*/ priority) {
    this.priority = priority;
  }
  
  @Override
  public String toString(){
    return "Prescription {" +
      "id: " + id +
      ", doctorId: " + doctorId +
      ", patientId: " + patientId +
      ", description: " + description +
      ", creationDate: " + creationDate +
      ", validityLength: " + validityLength +
      ", priority: " + priority + "}";
  }
}