package com.haulmont.testtask.jpa.doctor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
@Service
@RequestMapping(method = GET, path = "/doctors")
public class DoctorController {
  
  private final DoctorRepository doctorRepository;
  private static final Logger LOGGER = LoggerFactory.getLogger(DoctorController.class.getName());
  
  @Autowired
  public DoctorController(DoctorRepository doctorRepository) {
      this.doctorRepository = doctorRepository;
  }
  
  @GetMapping("/all")
  public @ResponseBody
  Iterable<Doctor> showAllDoctors() {
    try {
      return doctorRepository.findAll();
    } catch (Exception ignored){
      return null;
    }
  }
  
  @RequestMapping(method = GET, path = "/get")
  public @ResponseBody
  Optional<Doctor> getDoctor(@RequestParam(name = "id") Long id) {
    try{
      return doctorRepository.findById(id);
    } catch (Exception ignored){
      return Optional.empty();
    }
  }
  
  @RequestMapping(method = GET, path = "/add")
  public @ResponseBody
  Doctor addDoctor(@RequestParam(name = "name") String name,
                   @RequestParam(name = "surname") String surname,
                   @RequestParam(name = "patronymic", defaultValue = "") String patronymic,
                   @RequestParam(name = "specialization", defaultValue = "") String specialization
  ) {
    try{
      return doctorRepository.save(new Doctor(name, surname, patronymic, specialization));
    } catch (DataIntegrityViolationException dataIntegrityError) {
      LOGGER.debug("HaulmontLOG4J2: DATA INTEGRITY ERROR WHILE ADDING NEW ENTITY-> {}", dataIntegrityError);
      return  null;
    } catch (Exception unknown){
      LOGGER.debug("HaulmontLOG4J2:  UNKNOWN ERROR WHILE ADDING NEW ENTITY -> {}", unknown);
      return  null;
    }
  }
  
  @SuppressWarnings("Duplicates")
  @RequestMapping(method = GET, path = "/update")
  public @ResponseBody
  Doctor updateDoctor(@RequestParam(name = "id") Long id,
                      @RequestParam(name = "name", required = false) String name,
                      @RequestParam(name = "surname", required = false) String surname,
                      @RequestParam(name = "patronymic", required = false) String patronymic,
                      @RequestParam(name = "specialization", required = false) String specialization
  ) {
    try {
      Optional<Doctor> d = doctorRepository.findById(id);
      Doctor doctor;
      if (d.isPresent()) {
        doctor = d.get();
        if (name != null) {
          doctor.setName(name);
        }
        if (surname != null) {
          doctor.setSurname(surname);
        }
        if (patronymic != null) {
          doctor.setPatronymic(patronymic);
        }
        if (specialization != null) {
          doctor.setSpecialization(specialization);
        }
        return doctorRepository.save(doctor);
      } else {
        return null;
      }
    } catch (Exception e) {
      return null;
    }
  }
  
  @SuppressWarnings("Duplicates")
  @RequestMapping(method = GET, path = "/remove")
  public @ResponseBody
  Iterable<Doctor> removeDoctor(@RequestParam(name = "id") Long id) {
    try {
      Optional<Doctor> doctor = doctorRepository.findById(id);
      doctor.ifPresent(doctorRepository::delete);
      return doctorRepository.findAll();
    } catch (DataIntegrityViolationException dataIntegrityError) {
      LOGGER.debug("HaulmontLOG4J2: DATA INTEGRITY ERROR WHILE DELETING DOCTOR ENTITY -> {}", dataIntegrityError);
      return doctorRepository.findAll();
    } catch (Exception unknown) {
      LOGGER.debug("HaulmontLOG4J2:  UNKNOWN ERROR WHILE DELETING DOCTOR ENTITY -> {}", unknown);
      return doctorRepository.findAll();
    }
  }
  
  @GetMapping(path = "/names")
  public @ResponseBody
  Iterable<String> getAllDoctorsNames() {
    try{
      return doctorRepository.getAllDoctorsNames();
    } catch (Exception ignored){
      return null;
    }
  }
  
  @GetMapping(path = "/surnames")
  public @ResponseBody
  Iterable<String> getAllDoctorsSurnames() {
    try{
      return doctorRepository.getAllDoctorsSurnames();
    } catch (Exception ignored){
      return null;
    }
  }
  
  @RequestMapping(method = GET, path = "/fullnames")
  public @ResponseBody
  List<String> getAllFullNames(/*@RequestParam(name="pattern") String pattern*/){
    try{
      return doctorRepository.getAllDoctorsFullNames();
    } catch (Exception ignored){
      return null;
    }
  }
  
  @GetMapping(path = "/specializations")
  public @ResponseBody
  Iterable<String> getAllDoctorsSpecs() {
    try{
      return doctorRepository.getAllDoctorsSpecs();
    } catch (Exception ignored){
      return null;
    }
  }
  
}
