package com.graduationproject.controller;


import com.google.common.hash.Hashing;
import com.graduationproject.model.*;
import com.graduationproject.responses.*;
import com.graduationproject.service.*;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.NonUniqueResultException;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private SecurityTokenService securityTokenService;

    private static final String SELF_HISTORY_ID = "0395e1e0-c60b-4564-8dea-e92fb83bb9ea";


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

    @RequestMapping(value = "/updateMedicineQuantity", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity updateMedicineQuantity(@RequestBody Medicine medicine) {
        try {
            medicineService.updateMedicine(medicine);
            return  ResponseEntity.ok().build();
        } catch (NullPointerException | PropertyValueException | NonUniqueResultException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**-----------------------------------------------**/

    @RequestMapping(value = "/verifyPatient", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity verifyPatient(@RequestBody Patient patient) {
        try {
            if (patient.getUsername() != null && patient.getEmail() != null) {
                List<ErrorResponse> errorResponses = new ArrayList<>();
                errorResponses.add(new ErrorResponse(patientService.verifyPatient(patient.getUsername(), patient.getEmail())));
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
                String token = retrievedPatient.getId() + "|" + UUID.randomUUID() + "|ELIXIR";
                securityTokenService.updateSecurityToken(new SecurityToken(retrievedPatient.getUsername(), token, new Date()));
                return ResponseEntity.ok().body(new PatientResponse(1, 0, patients, token));
            }
            return ResponseEntity.ok().body(new PatientResponse(0, 1, null, null));
        } catch (NullPointerException | PropertyValueException | NonUniqueResultException e) {
            System.out.println("error login : " + e);
            return ResponseEntity.badRequest().body(new PatientResponse(0, 1, null, null));
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
                String token = patient.getId() + "|" + UUID.randomUUID() + "|ELIXIR";
                securityTokenService.addToken(new SecurityToken(patient.getUsername(), token, new Date()));
                return ResponseEntity.ok().body(new PatientResponse(1, 0, patients, token));
            }
            return ResponseEntity.badRequest().body(new PatientResponse(0, 1, null, null));
        } catch (NullPointerException | PropertyValueException e) {
            System.out.println("error register : " + e);
            return ResponseEntity.badRequest().body(new PatientResponse(0, 1, null, null));
        }
    }

    @RequestMapping(value = "/setPIN", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<PatientResponse> setPIN(@RequestBody Patient patient) {
        try {
            if (patientService.setPatientPIN(patient.getUsername(), patient.getPin()))
                return ResponseEntity.ok().body(new PatientResponse(1, 0, null, null));
            else
                return ResponseEntity.badRequest().body(new PatientResponse(0, 1, null, null));
        } catch (NullPointerException | PropertyValueException | NonUniqueResultException e) {
            return ResponseEntity.badRequest().body(new PatientResponse(0, 1, null, null));
        }

    }

    @RequestMapping(value = "/accessPatient", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity accessPatient(@RequestBody Patient patient) {
        System.out.println("In API controller with patient username " + patient.getUsername() + " and pin " + patient.getPin());
        try {
            Patient retrievedPatient = patientService.accessPatient(patient.getUsername(), patient.getPin());

            if (retrievedPatient != null) {
                List<Patient> patients = new ArrayList<>();
                retrievedPatient.setPassword(null);
                patients.add(retrievedPatient);
                System.out.println("retrieved patient from access patient with PIN " + retrievedPatient.getUsername() + " - " + retrievedPatient.getPin());
                return ResponseEntity.ok().body(new PatientResponse(1, 0, patients, null));
            }
            return ResponseEntity.ok().body(new PatientResponse(0, 1, null, null));
        } catch (NullPointerException | PropertyValueException | NonUniqueResultException e) {
            System.out.println("error login : " + e);
            return ResponseEntity.badRequest().body(new PatientResponse(0, 1, null, null));
        }
    }

    /**-----------------------------------------------**/

    @RequestMapping(value = "/sync", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity sync(@RequestBody String jsonParser) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JsonNode root = objectMapper.readTree(jsonParser);

            JsonNode patientJson = root.path("patient");
            if (patientJson.isMissingNode()) {
                return ResponseEntity.badRequest().body(new PrescriptionResponse(null, 0 , 0));
            } else {
                if (patientJson.get("id").isNull())
                    return ResponseEntity.badRequest().body(new PrescriptionResponse(null, 0 , 0));
                Patient patient = patientService.getPatient(patientJson.get("id").asText());
                if (patient == null)
                    return ResponseEntity.badRequest().body(new PrescriptionResponse(null, 0 , 0));

                int success_prescription, success_sync_offline = 1;

                long lastUpdated;
                if (root.has("lastUpdated"))
                    lastUpdated = (root.get("lastUpdated").isLong()) ? root.get("lastUpdated").asLong() : 0;
                else
                    lastUpdated = 0;

                List<PrescriptionResultResponse> prescriptionResultResponses = new ArrayList<>();
                List<Prescription> prescriptions = prescriptionService.getAllPrescriptions(patient.getId(), lastUpdated);
                if (prescriptions.size() == 0) {
                    success_prescription = 1;
                } else {
                    List<RegularOrder> regular_orders;
                    for (Prescription prescription: prescriptions) {
                        regular_orders = regularOrderService.getPrescriptionRegularOrders(prescription.getId());
                        prescriptionResultResponses.add(new PrescriptionResultResponse(prescription, cartMedicineService.getAllCartMedicines(prescription.getId()), regular_orders));
                    }
                    success_prescription = 1;
                }

                JsonNode prescriptionsJsonArray = root.path("prescriptions");
                if (!prescriptionsJsonArray.isMissingNode() && !prescriptionsJsonArray.isNull() && prescriptionsJsonArray.isArray() && prescriptionsJsonArray.size() > 0) {
                    ResponseEntity<SuccessResponse> responseEntity;
                    for (JsonNode prescriptionJson :
                            prescriptionsJsonArray) {

                        responseEntity = setPrescription(prescriptionJson.toString(), false);
                        success_sync_offline = (success_sync_offline == 0)?
                                success_sync_offline : success_sync_offline & responseEntity.getBody().getSuccess();
                    }
                }

                return ResponseEntity.ok().body(new PrescriptionResponse(prescriptionResultResponses, success_sync_offline, success_prescription));
            }
        } catch (NullPointerException | PropertyValueException | NonUniqueResultException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new PrescriptionResponse(null, 0 , 0));
        }
    }

    @RequestMapping(value = "/setPrescription", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity setPrescription(@RequestBody String jsonParser) {
        return setPrescription(jsonParser, true);
    }
    private  @ResponseBody ResponseEntity setPrescription(@RequestBody String jsonParser, boolean checkAvailability) {

        System.out.println("i am in web service in set prescription"); //TODO: delete print
        System.out.println("the parser json: " + jsonParser);//TODO: delete print
        int success = 1;
        HttpStatus httpStatus;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {

            String prescriptionDetails = "";
            String outOfStockMedicines = "";

            JsonNode root = objectMapper.readTree(jsonParser);

            JsonNode prescriptionJson = root.path("prescription");
            if (prescriptionJson.isMissingNode()) {
                success = 0;
                httpStatus = HttpStatus.BAD_REQUEST;
            } else {
                Prescription prescription = objectMapper.convertValue(prescriptionJson, Prescription.class);

                JsonNode cartMedicinesJsonArray = root.path("cartMedicines");
                if (cartMedicinesJsonArray.isArray() && cartMedicinesJsonArray.size() > 0) {
                    List<CartMedicine> cartMedicines = new ArrayList<>();
                    double prescription_price = 0;
                    int i = 0;
                    if (checkAvailability) {
                        CartMedicine cartMedicine;
                        for (JsonNode cartMedicineJson : cartMedicinesJsonArray) {
                            cartMedicine = objectMapper.convertValue(cartMedicineJson, CartMedicine.class);
                            cartMedicine.setPrescription_id(prescription.getId());
                            Medicine medicine = medicineService.getMedicine(cartMedicine.getMedicine_id());

                            prescription_price += (medicine.getPrice()) * cartMedicine.getQuantity();
                            cartMedicines.add(cartMedicine);

                            prescriptionDetails += medicine.getName() + "," + cartMedicine.getQuantity();
                            if (cartMedicinesJsonArray.size() != (++i))
                                prescriptionDetails += "&";

                            if (cartMedicine.getQuantity() > medicine.getQuantity())
                                outOfStockMedicines += medicine.getName() + "," + cartMedicine.getQuantity() + "&";
                        }
                    } else {
                        CartMedicine cartMedicine;
                        for (JsonNode cartMedicineJson : cartMedicinesJsonArray) {
                            cartMedicine = objectMapper.convertValue(cartMedicineJson, CartMedicine.class);
                            cartMedicine.setPrescription_id(prescription.getId());
                            Medicine medicine = medicineService.getMedicine(cartMedicine.getMedicine_id());
                            prescription_price += (medicine.getPrice()) * cartMedicine.getQuantity();
                            cartMedicines.add(cartMedicine);
                        }
                    }

                    prescription.setPrice(prescription_price);
                    httpStatus = HttpStatus.OK;

                    try {
                        JsonNode historyJson = root.path("history");
                        if (!historyJson.isMissingNode()) {
                            History history = objectMapper.convertValue(historyJson, History.class);
                            historyService.addHistory(history);
                            success = 1;
                        } else
                            prescription.setHistory_id(SELF_HISTORY_ID);

                        prescriptionService.addPrescription(prescription);
                        cartMedicineService.addCartMedicines(cartMedicines);

                        RegularOrder regularOrder = new RegularOrder();
                        if (root.has("fire_time1")) {
                            if (root.get("fire_time1").isLong()) {
                                regularOrder.setFire_time(root.get("fire_time1").asLong());
                                regularOrder.setPrescription_id(prescription.getId());
                                regularOrder.setPatient_id(prescription.getPatient_id());
                                regularOrderService.addRegularOrder(regularOrder);
                                success = 1;
                                httpStatus = HttpStatus.OK;
                            } else
                                success = 0;
                        }
                        if (root.has("fire_time2")) {
                            if (root.get("fire_time2").isLong()) {
                                regularOrder.setFire_time(root.get("fire_time2").asLong());
                                regularOrder.setPrescription_id(prescription.getId());
                                regularOrder.setPatient_id(prescription.getPatient_id());
                                regularOrderService.addRegularOrder(regularOrder);
                                success = (success == 0)? success : 1;
                                httpStatus = HttpStatus.OK;
                            } else
                                success = 0;
                        }

                    } catch (NullPointerException | PropertyValueException  e) {
                        return ResponseEntity.badRequest().body(new SuccessResponse(0, null, null));
                    }
                } else {
                    success = 0;
                    httpStatus = HttpStatus.BAD_REQUEST;
                }
            }

            if (outOfStockMedicines.length() > 0)
                outOfStockMedicines = outOfStockMedicines.substring(0, outOfStockMedicines.length() - 1);

            return ResponseEntity.status(httpStatus).body(new SuccessResponse(success, prescriptionDetails, outOfStockMedicines));

        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new SuccessResponse(0, null, null));
        }
    }

    @RequestMapping(value = "/getPatientDetails", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity getPatientDetails(@RequestBody Patient patient) {
        try {
            List<Prescription> prescriptions = prescriptionService.getAllPrescriptions(patient.getId(), 0);
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
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(prescription.getPrescription_date()),
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

    @RequestMapping(value = "/getPrescriptionDetails", method = RequestMethod.POST)
    public @ResponseBody String getPrescriptionDetails(@RequestBody String jsonParser) {

        System.out.println("Iam in web service get prescription details");//TODO: delete pring
        System.out.println("the parser json: " + jsonParser);//TODO: delete print

        String prescriptionDetails = "";
        int i = 0;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            JsonNode root = objectMapper.readTree(jsonParser);
            if (!root.isNull()) {

                JsonNode cartMedicinesJsonArray = root.path("cartMedicines");
                if (cartMedicinesJsonArray.isArray() && cartMedicinesJsonArray.size() > 0) {
                    CartMedicine cartMedicine;
                    for (JsonNode cartMedicineJson : cartMedicinesJsonArray) {
                        cartMedicine = objectMapper.convertValue(cartMedicineJson, CartMedicine.class);

                        prescriptionDetails += medicineService.getMedicine(cartMedicine.getMedicine_id()).getName() + "#" + cartMedicine.getQuantity();
                        if (cartMedicinesJsonArray.size() != (++i))
                            prescriptionDetails += "|";
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return prescriptionDetails;
    }

    @RequestMapping(value = "finalizeOrder", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity finalizeOrder(@RequestBody String jsonParser) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JsonNode root = objectMapper.readTree(jsonParser);

            JsonNode medicinesOrdered = root.path("medicines");
            if (!medicinesOrdered.isMissingNode() && !medicinesOrdered.isNull() && medicinesOrdered.isArray() && medicinesOrdered.size() > 0) {
                for (JsonNode medicineOrder :
                        medicinesOrdered) {
                    if (medicineService.updateMedicineQuantity(medicineOrder.get("id").asText(), medicineOrder.get("quantity").asInt()) != 1)
                        return ResponseEntity.badRequest().build();
                }
                return ResponseEntity.ok().body("Done");
            }
            return ResponseEntity.badRequest().build();
        } catch (NullPointerException | PropertyValueException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
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