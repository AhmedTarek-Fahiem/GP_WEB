package com.graduationproject.controller;


import com.google.common.hash.Hashing;
import com.graduationproject.model.*;
import com.graduationproject.responses.*;
import com.graduationproject.service.*;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.NonUniqueResultException;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/api")
public class ProjectAPIController {


    @Autowired
    private MedicineService medicineService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private CartMedicineService cartMedicineService;

    @Autowired
    private RegularOrderService regularOrderService;

    @Autowired
    private VersionService versionService;


    /**-----------------------------------------------**/

    @RequestMapping(value = "/getMedicinesList", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity listMedicine() {
        List<Medicine> medicineList = medicineService.getAllMedicines();
        if (medicineList.size() > 0)
            return ResponseEntity.ok(new MedicineResponse(1, medicineList));
        else
            return ResponseEntity.badRequest().body(new MedicineResponse(0, null));
    }

    @RequestMapping(value = "/getMedicine/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity getMedicine(@PathVariable(value = "id") UUID medicine_id) {
        Medicine medicine = medicineService.getMedicine(medicine_id.toString());
        if (medicine != null) {
            List<Medicine> medicineList = new ArrayList<>();
            medicineList.add(medicine);
            return ResponseEntity.ok().body(new MedicineResponse(1, medicineList));
        }
        else
            return ResponseEntity.badRequest().body(new MedicineResponse(0, null));
    }

    @RequestMapping(value = "/newMedicine", method = RequestMethod.GET)
    public ModelAndView newMedicine(ModelAndView model) {
        Medicine medicine = new Medicine();
        model.addObject("medicine", medicine);
        model.setViewName("MedicineForm");
        return model;
    }

    @RequestMapping(value = "/editMedicine/{id}", method = RequestMethod.GET)
    public ModelAndView editMedicine(@PathVariable(value = "id") UUID medicine_id) {
        Medicine medicine = medicineService.getMedicine(medicine_id.toString());
        ModelAndView model = new ModelAndView("MedicineForm");
        model.addObject("medicine", medicine);
        return model;
    }

    @RequestMapping(value = "/saveMedicine", method = RequestMethod.POST)
    public ModelAndView saveMedicine(@ModelAttribute Medicine medicine) {
        if (medicine.getId() == null) {
            medicineService.addMedicine(medicine);
        } else {
            medicineService.updateMedicine(medicine);
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/deleteMedicine/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity deleteMedicine(@PathVariable(value = "id") UUID medicine_id) {
        if (medicineService.deleteMedicine(medicine_id.toString()))
            return ResponseEntity.ok().body(new MedicineResponse(1, null));
        else
            return ResponseEntity.badRequest().body(new MedicineResponse(0, null));
    }

    /**-----------------------------------------------**/

    @RequestMapping(value = "/checkPatient", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity checkPatient(@RequestBody Patient patient) {
        try {
            if (patient.getUsername() != null && patient.getEmail() != null) {
                List<ErrorResponse> errorResponses = new ArrayList<>();
                errorResponses.add(new ErrorResponse(patientService.checkPatient(patient.getUsername(), patient.getEmail())));
                return ResponseEntity.ok().body(new ResultErrorResponse(errorResponses));
            }
            return ResponseEntity.badRequest().body(new ResultErrorResponse(null));
        } catch (NullPointerException | PropertyValueException | NonUniqueResultException e) {
            return ResponseEntity.badRequest().body(new ResultErrorResponse(null));
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity login(@RequestBody Patient patient, HttpServletRequest request) {
        try {
            Patient retrievedPatient = patientService.getPatient(patient.getUsername(), Hashing.sha256().hashString(patient.getPassword(), StandardCharsets.UTF_8).toString());

            if (retrievedPatient != null) {
                List<Patient> patients = new ArrayList<>();
                retrievedPatient.setPassword(null);
                patients.add(retrievedPatient);
                System.out.println(retrievedPatient.getUsername() + " - " + retrievedPatient.getEmail());
//                CsrfToken csrfToken = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
//                String csrfTokenParameterName = csrfToken.getParameterName();
//                String csrfTokenToken = csrfToken.getToken();
//                System.out.println("Token parameter name : " + csrfTokenParameterName + "  -  Token token : " + csrfTokenToken);
                return ResponseEntity.ok().body(new PatientResponse(1, 0, patients));
            }
            return ResponseEntity.ok().body(new PatientResponse(0, 1, null));
        } catch (NullPointerException | PropertyValueException | NonUniqueResultException e) {
            System.out.println("error login : " + e);
            return ResponseEntity.badRequest().body(new PatientResponse(0, 1, null));
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity register(@RequestBody Patient patient) {
        try {
            String password = patient.getPassword();
            patient.setPassword(Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString());
            if (patientService.addPatient(patient)) {
                patient.setPassword(null);
                List<Patient> patients = new ArrayList<>();
                patients.add(patient);
                return ResponseEntity.ok().body(new PatientResponse(1, 0, patients));
            }
            return ResponseEntity.badRequest().body(new PatientResponse(0, 1, null));
        } catch (NullPointerException | PropertyValueException e) {
            System.out.println("error register : " + e);
            return ResponseEntity.badRequest().body(new PatientResponse(0, 1, null));
        }
    }

    @RequestMapping(value = "/setPIN", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<PatientResponse> setPIN(@RequestBody Patient patient) {
        try {
            if (patientService.setPatientPIN(patient.getUsername(), patient.getPIN()))
                return ResponseEntity.ok().body(new PatientResponse(1, 0, null));
            else
                return ResponseEntity.badRequest().body(new PatientResponse(0, 1, null));
        } catch (NullPointerException | PropertyValueException | NonUniqueResultException e) {
            return ResponseEntity.badRequest().body(new PatientResponse(0, 1, null));
        }

    }

    @RequestMapping(value = "/accessPatient", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity accessPatient(@RequestBody Patient patient) {
        System.out.println("555555115555");
        try {
            Patient retrievedPatient = patientService.accessPatient(patient.getUsername(), patient.getPIN());

            if (retrievedPatient != null) {
                List<Patient> patients = new ArrayList<>();
                retrievedPatient.setPassword(null);
                patients.add(retrievedPatient);
                System.out.println("retrieved patient from access patient with PIN " + retrievedPatient.getUsername() + " - " + retrievedPatient.getEmail());
                return ResponseEntity.ok().body(new PatientResponse(1, 0, patients));
            }
            return ResponseEntity.ok().body(new PatientResponse(0, 1, null));
        } catch (NullPointerException | PropertyValueException | NonUniqueResultException e) {
            System.out.println("error login : " + e);
            return ResponseEntity.badRequest().body(new PatientResponse(0, 1, null));
        }
    }

    /**-----------------------------------------------**/

    @RequestMapping(value = "/getPrescriptions", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity getPrescriptions(@RequestBody Patient patient) {

        try {
            List<Prescription> prescriptions = prescriptionService.getAllPrescriptions(patient.getId());
            if (prescriptions.size() == 0) {
                return ResponseEntity.ok().body(new PrescriptionResponse(null, 1 , 1));
            } else {
                List<CartMedicine> cart_medicines = new ArrayList<>();
                for (Prescription prescription: prescriptions) {
//                    cartMedicineService.getAllCartMedicines(prescription.getId()).forEach(cart_medicines::add);
//                    cartMedicineService.getAllCartMedicines(prescription.getId()).forEach(cart_medicine -> cart_medicines.add(cart_medicine));
                    cart_medicines.addAll(cartMedicineService.getAllCartMedicines(prescription.getId()));
                }
                List<RegularOrder> regular_orders = regularOrderService.getAllRegularOrders(patient.getId());
                List<PrescriptionResultResponse> prescriptionResultResponses = new ArrayList<>();
                prescriptionResultResponses.add(new PrescriptionResultResponse(cart_medicines, prescriptions, regular_orders == null ? 0 : 1, regular_orders));
                return ResponseEntity.ok().body(new PrescriptionResponse(prescriptionResultResponses, 1, 1));
            }
        } catch (NullPointerException | PropertyValueException | NonUniqueResultException e) {
            return ResponseEntity.badRequest().body(new PrescriptionResponse(null, 0 , 0));
        }
    }

    @RequestMapping(value = "/setPrescription", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity setPrescription(@RequestBody String jsonParser){

        List<SuccessResponse> successResponses = new ArrayList<>();
        int success_prescription, success_cart, success_regular;
        HttpStatus httpStatus;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(jsonParser);

            JsonNode prescriptionJson = root.path("prescription");
            if (prescriptionJson.isMissingNode()) {
                success_prescription = success_cart = success_regular = 0;
                httpStatus = HttpStatus.BAD_REQUEST;
            } else {
                Prescription prescription = objectMapper.convertValue(prescriptionJson, Prescription.class);

                JsonNode cartMedicinesJsonArray = root.path("cartMedicines");
                if (cartMedicinesJsonArray.isArray() && cartMedicinesJsonArray.size() > 0) {
                    List<CartMedicine> cartMedicines = new ArrayList<>();
                    cartMedicinesJsonArray.forEach(cartMedicineJson -> cartMedicines.add(objectMapper.convertValue(cartMedicineJson, CartMedicine.class)));
                    success_prescription = success_cart = 1;
                    httpStatus = HttpStatus.OK;

                    try {
                        prescriptionService.addPrescription(prescription);
                        cartMedicineService.addCartMedicines(cartMedicines);

                        JsonNode regularOrdersJsonArray = root.path("regularOrders");
                        if (regularOrdersJsonArray.isArray() && !regularOrdersJsonArray.isNull()) {
                            List<RegularOrder> regularOrders = new ArrayList<>();
                            regularOrdersJsonArray.forEach(regularOrderJson -> regularOrders.add(objectMapper.convertValue(regularOrderJson, RegularOrder.class)));
                            success_regular = 1;
                            httpStatus = HttpStatus.OK;

                            regularOrderService.addRegularOrders(regularOrders);
                        } else {
                            success_regular = 0;
                        }

                    } catch (NullPointerException | PropertyValueException  e) {
                        successResponses.add(new SuccessResponse(0, 0, 0));
                        return ResponseEntity.badRequest().body(new ResultSuccessResponse(successResponses));
                    }
                } else {
                    success_prescription = success_cart = success_regular = 0;
                    httpStatus = HttpStatus.BAD_REQUEST;
                }
            }

            successResponses.add(new SuccessResponse(success_prescription, success_cart, success_regular));
            return ResponseEntity.status(httpStatus).body(new ResultSuccessResponse(successResponses));

        } catch (IOException e) {
            successResponses.add(new SuccessResponse(0, 0, 0));
            return ResponseEntity.badRequest().body(new ResultSuccessResponse(successResponses));
        }
    }

    @RequestMapping(value = "/getPatientDetails", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity getPatientDetails(@RequestBody Patient patient) {
        try {
            List<Prescription> prescriptions = prescriptionService.getAllPrescriptions(patient.getId());
            if (prescriptions.size() == 0) {
                return ResponseEntity.ok().body(new PatientDetailsResponse(1, null));
            } else {
                List<PatientDetails> patientDetailsList = new ArrayList<>();
                for (Prescription prescription: prescriptions) {
                    List<Medicine> medicineList = new ArrayList<>();
                    cartMedicineService.getAllCartMedicines(prescription.getId()).forEach(cartMedicine ->
                            medicineList.add(medicineService.getMedicine(cartMedicine.getMedicine_id()))
                    );
                    PatientDetails patientDetails = new PatientDetails(
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(prescription.getPrescription_date().getTime()),
                            medicineList,
                            historyService.getHistory(prescription.getHistory_id()).getDescription());

                    patientDetailsList.add(patientDetails);
                }
                return ResponseEntity.ok().body(new PatientDetailsResponse(1, patientDetailsList));
            }
        } catch (NullPointerException | PropertyValueException | NonUniqueResultException e) {
            return ResponseEntity.badRequest().body(new PatientDetailsResponse(0, null));
        }
    }

    /**-----------------------------------------------**/

    @RequestMapping(value = "/getVersion", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity getVersion() {
        Version version = versionService.getVersion();
        if (version != null)
            return ResponseEntity.ok().body(new VersionResponse(1, version.getVer().toString()));
        else
            return ResponseEntity.badRequest().body(new VersionResponse(0, null));
    }
}
