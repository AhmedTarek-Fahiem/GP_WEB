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
            if (patientService.setPatientPIN(patient.getUsername(), patient.getPin()))
                return ResponseEntity.ok().body(new PatientResponse(1, 0, null));
            else
                return ResponseEntity.badRequest().body(new PatientResponse(0, 1, null));
        } catch (NullPointerException | PropertyValueException | NonUniqueResultException e) {
            return ResponseEntity.badRequest().body(new PatientResponse(0, 1, null));
        }

    }

    @RequestMapping(value = "/accessPatient", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity accessPatient(@RequestBody Patient patient) {
        try {
            Patient retrievedPatient = patientService.accessPatient(patient.getUsername(), patient.getPin());

            if (retrievedPatient != null) {
                List<Patient> patients = new ArrayList<>();
                retrievedPatient.setPassword(null);
                patients.add(retrievedPatient);
                System.out.println("retrieved patient from access patient with PIN " + retrievedPatient.getUsername() + " - " + retrievedPatient.getPin());
                return ResponseEntity.ok().body(new PatientResponse(1, 0, patients));
            }
            return ResponseEntity.ok().body(new PatientResponse(0, 1, null));
        } catch (NullPointerException | PropertyValueException | NonUniqueResultException e) {
            System.out.println("error login : " + e);
            return ResponseEntity.badRequest().body(new PatientResponse(0, 1, null));
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

                long lastUpdate;
                if (root.has("lastUpdate"))
                    lastUpdate = (root.get("lastUpdate").isLong()) ? root.get("lastUpdate").asLong() : 0;
                else
                    lastUpdate = 0;

                List<PrescriptionResultResponse> prescriptionResultResponses = new ArrayList<>();
                List<Prescription> prescriptions = prescriptionService.getAllPrescriptions(patient.getId(), lastUpdate);
                if (prescriptions.size() == 0) {
                    success_prescription = 1;
                } else {
                    List<RegularOrder> regular_orders;
                    for (Prescription prescription: prescriptions) {
                        regular_orders = regularOrderService.getPrescriptionRegularOrders(prescription.getId());
                        prescriptionResultResponses.add(new PrescriptionResultResponse(prescription, cartMedicineService.getAllCartMedicines(prescription.getId()), regular_orders == null ? 0 : 1, regular_orders, historyService.getHistory(prescription.getHistory_id())));
                    }
                    success_prescription = 1;
                }

                JsonNode prescriptionsJsonArray = root.path("prescriptions");
                if (!prescriptionsJsonArray.isMissingNode() && !prescriptionsJsonArray.isNull() && prescriptionsJsonArray.isArray() && prescriptionsJsonArray.size() > 0) {
                    ResponseEntity<ResultSuccessResponse> responseEntity;
                    for (JsonNode prescriptionJson :
                            prescriptionsJsonArray) {

                        responseEntity = setPrescription(prescriptionJson.toString(), false);
                        success_sync_offline = (success_sync_offline == 0)?
                                success_sync_offline : success_sync_offline & responseEntity.getBody().getSuccess().get(0).getSuccess_prescription()
                                                                            & responseEntity.getBody().getSuccess().get(0).getSuccess_cart();
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
        List<SuccessResponse> successResponses = new ArrayList<>();
        int success_prescription, success_cart, success_regular, success_history;
        HttpStatus httpStatus;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {

            String prescriptionDetails = "";

            JsonNode root = objectMapper.readTree(jsonParser);

            JsonNode prescriptionJson = root.path("prescription");
            if (prescriptionJson.isMissingNode()) {
                success_prescription = success_cart = success_regular = success_history = 0;
                httpStatus = HttpStatus.BAD_REQUEST;
            } else {
                Prescription prescription = objectMapper.convertValue(prescriptionJson, Prescription.class);

                JsonNode cartMedicinesJsonArray = root.path("cartMedicines");
                if (cartMedicinesJsonArray.isArray() && cartMedicinesJsonArray.size() > 0) {
                    List<CartMedicine> cartMedicines = new ArrayList<>();
                    double prescription_price = 0;
                    int i = 0;
                    String outOfStockMedicines = "";
                    if (checkAvailability) {
                        CartMedicine cartMedicine;
                        for (JsonNode cartMedicineJson : cartMedicinesJsonArray) {
                            cartMedicine = objectMapper.convertValue(cartMedicineJson, CartMedicine.class);
                            cartMedicine.setPrescription_id(prescription.getId());
                            Medicine medicine = medicineService.getMedicine(cartMedicine.getMedicine_id());
                            if (cartMedicine.getQuantity() <= medicine.getQuantity()) {
                                prescription_price += (medicine.getPrice()) * cartMedicine.getQuantity();
                                cartMedicines.add(cartMedicine);

                                prescriptionDetails += medicine.getName() + "," + cartMedicine.getQuantity();
                                if (cartMedicinesJsonArray.size() != (++i))
                                    prescriptionDetails += "&";
                            } else
                                outOfStockMedicines += medicine.getName() + " , ";
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

                    if (outOfStockMedicines.length() == 0) {
                        prescription.setPrice(prescription_price);
                        success_prescription = success_cart = 1;
                        httpStatus = HttpStatus.OK;

                        try {
                            JsonNode historyJson = root.path("history");
                            if (!historyJson.isMissingNode()) {
                                History history = objectMapper.convertValue(historyJson, History.class);
                                success_history = 1;
                                historyService.addHistory(history);
                            } else {
                                prescription.setHistory_id(SELF_HISTORY_ID);
                                success_history = 0;
                            }

                            prescriptionService.addPrescription(prescription);
                            cartMedicineService.addCartMedicines(cartMedicines);

                            RegularOrder regularOrder = new RegularOrder();
                            success_regular = 0;
                            if (root.has("fire_time1")) {
                                if (root.get("fire_time1").isLong()) {
                                    regularOrder.setFire_time(root.get("fire_time1").asLong());
                                    regularOrder.setPrescription_id(prescription.getId());
                                    regularOrder.setPatient_id(prescription.getPatient_id());
                                    regularOrderService.addRegularOrder(regularOrder);
                                    success_regular = 1;
                                    httpStatus = HttpStatus.OK;
                                } else
                                    success_regular = 0;
                            }
                            if (root.has("fire_time2")) {
                                if (root.get("fire_time2").isLong()) {
                                    regularOrder.setFire_time(root.get("fire_time2").asLong());
                                    regularOrder.setPrescription_id(prescription.getId());
                                    regularOrder.setPatient_id(prescription.getPatient_id());
                                    regularOrderService.addRegularOrder(regularOrder);
                                    success_regular = 1;
                                    httpStatus = HttpStatus.OK;
                                } else
                                    success_regular = 0;
                            }

                        } catch (NullPointerException | PropertyValueException  e) {
                            successResponses.add(new SuccessResponse(0, 0, 0, 0, null, null));
                            return ResponseEntity.badRequest().body(new ResultSuccessResponse(successResponses));
                        }
                    } else {
                        successResponses.add(new SuccessResponse(0, 0, 0, 0, null, outOfStockMedicines));
                        return ResponseEntity.badRequest().body(new ResultSuccessResponse(successResponses));
                    }
                } else {
                    success_prescription = success_cart = success_regular = success_history = 0;
                    httpStatus = HttpStatus.BAD_REQUEST;
                }
            }

            successResponses.add(new SuccessResponse(success_prescription, success_cart, success_regular, success_history, prescriptionDetails, null));
            return ResponseEntity.status(httpStatus).body(new ResultSuccessResponse(successResponses));

        } catch (IOException e) {
            successResponses.add(new SuccessResponse(0, 0, 0, 0, null, null));
            return ResponseEntity.badRequest().body(new ResultSuccessResponse(successResponses));
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

                        prescriptionDetails += medicineService.getMedicine(cartMedicine.getMedicine_id()).getName() + "," + cartMedicine.getQuantity();
                        if (cartMedicinesJsonArray.size() != (++i))
                            prescriptionDetails += "&";
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return prescriptionDetails;
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