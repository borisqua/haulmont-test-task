package com.haulmont.testtask.jpa.prescription.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@SuppressWarnings({"unused"})
@Controller
@RequestMapping(method = GET, path = "/prescriptions/view")
public class PrescriptionHumanizedController {
  
  private final PrescriptionHumanizedRepository prescriptionRepository;
  private static final Logger LOGGER = LoggerFactory.getLogger(PrescriptionHumanizedController.class.getName());
  
  @Autowired
  public PrescriptionHumanizedController(PrescriptionHumanizedRepository prescriptionRepository) {
    this.prescriptionRepository = prescriptionRepository;
  }
  
  @GetMapping("/all")
  public @ResponseBody
  Iterable<PrescriptionHumanized> showAllPrescriptions() {
    try {
      return prescriptionRepository.findAll();
    } catch (Exception ignored) {
      return null;
    }
  }
  
  @RequestMapping(method = GET, path = "/get")
  public @ResponseBody
  Optional<PrescriptionHumanized> getPrescription(@RequestParam(name = "id") Long id) {
    try {
      return prescriptionRepository.findById(id);
    } catch (Exception ignored) {
      return Optional.empty();
    }
  }
  
  @RequestMapping(method = GET, path = "/filter")
  public @ResponseBody
  Iterable<PrescriptionHumanized> filterPrescription(
    @RequestParam(name = "patient", required = false) String patient,
    @RequestParam(name = "priority", required = false) String priority,
    @RequestParam(name = "pattern", required = false) String pattern
  ) {
    
    Map<String, String> criteria = new HashMap<>();
    criteria.put("patient", patient);
    criteria.put("priority", priority);
    criteria.put("pattern", pattern);
    
    LOGGER.info("HaulmontLOG4J2:  filterPrescription -> {}", patient);
    
    return prescriptionRepository.findByCustomCriteria(PrescriptionHumanized.class, criteria);
  }
  
}
