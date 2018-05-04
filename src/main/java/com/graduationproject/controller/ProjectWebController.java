package com.graduationproject.controller;


import com.google.gson.Gson;
import com.graduationproject.model.*;
import com.graduationproject.responses.*;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import net.glxn.qrgen.image.ImageType;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import net.glxn.qrgen.QRCode;

@Controller
//@SessionAttributes("roles")
public class ProjectWebController {


    private final String API_CONTROLLER_LINK = "http://localhost/api/";


    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    @Autowired
    AuthenticationTrustResolver authenticationTrustResolver;


    /**-----------------------------------------------**/

    @RequestMapping(value = "/access_denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("logged_in_user", getPrincipal());
        return "access_denied";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        if (isCurrentAuthenticationAnonymous()) {
            System.out.println("login method anonymous user");//TODO: delete print code
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("user", new User());
            return modelAndView;
        } else {
            System.out.println("login method logged-in user");//TODO: delete print code
            return new ModelAndView("redirect:/" + "access_patient");
        }
    }

    @RequestMapping(value = "/access_patient", method = RequestMethod.GET)
    public ModelAndView patientAccess(HttpSession session) {
        System.out.println("patientAccess method with get");//TODO: delete print code
        session.invalidate();
        ModelAndView modelAndView = new ModelAndView("access");
        modelAndView.addObject("patient", new Patient());
        return modelAndView;
    }

    @RequestMapping(value = "/on_the_move_prescription", method = RequestMethod.GET)
    public ModelAndView onMovePrescription(HttpSession session){
        session.setAttribute("registered_patient", false);
        return new ModelAndView("new_prescription").addObject("medicines_list", getMedicinesList(session));
    }

    @RequestMapping(value = "/access_patient", method = RequestMethod.POST)
    public ModelAndView accessPatientProfile(@Valid@ModelAttribute Patient patient, HttpSession session) {
        System.out.println("accessPatientProfile method with post");//TODO: delete print code
        System.out.println("accessPatientProfile method with post - patient username : " + patient.getUsername());//TODO: delete print code
        System.out.println("accessPatientProfile method with post - patient PIN : " + patient.getPin());//TODO: delete print code

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(API_CONTROLLER_LINK + "accessPatient", patient, String.class);
        System.out.println("response Access Entity = " + responseEntity);//TODO: delete print code

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            Gson gson = new Gson();
            PatientResponse patientResponse = gson.fromJson(responseEntity.getBody(), PatientResponse.class);

            if (patientResponse.getSuccess() == 1) {
                patient = patientResponse.getPatients().get(0);

                session.setAttribute("registered_patient", true);
                session.setAttribute("patient", patient);

                return new ModelAndView("redirect:/" + "patient_profile/" + patient.getUsername());
            }
        }
        return new ModelAndView("access").addObject("error", "Wrong Username or PIN.");
    }

    @RequestMapping(value = "/patient_profile/{username}", method = RequestMethod.GET)
    public ModelAndView patientProfile(@PathVariable("username") String username, ModelMap modelMap, HttpSession session) {

        Patient patient = ((Patient)session.getAttribute("patient"));
        try {
            if ((session.getMaxInactiveInterval() > ((int)(session.getCreationTime() - session.getLastAccessedTime()))) && ((boolean)session.getAttribute("registered_patient")) && (patient.getUsername().equals(username)) ) {

                RestTemplate restTemplate = new RestTemplate();
                Gson gson = new Gson();

                ResponseEntity<String> responseEntity = restTemplate.postForEntity(API_CONTROLLER_LINK + "getPatientDetails", patient, String.class);
                System.out.println("response Details Entity = " + responseEntity);//TODO: delete print code

                String medicineList = getMedicinesList(session);

                if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
                    PatientDetailsResponse patientDetailsResponse = gson.fromJson(responseEntity.getBody(), PatientDetailsResponse.class);

                    if (patientDetailsResponse.getSuccess() == 1)
                        return new ModelAndView("patient_profile")
                                .addObject("medicines_list", medicineList)
                                .addObject("patient_details", patientDetailsResponse.getPatientDetails());

                    return new ModelAndView("patient_profile")
                            .addObject("medicines_list", medicineList);
                }
                return new ModelAndView("patient_profile")
                        .addObject("medicines_list", medicineList)
                        .addObject("fetch_details_error", "Error in fetching the patient details");
            } else
                return new ModelAndView("redirect:/" + "access_patient");
        } catch (NullPointerException e) {
            return new ModelAndView("redirect:/" + "access_patient");
        }
    }

    @RequestMapping(value = "/start_cart", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView startCart(@RequestBody String jsonParser, HttpSession session) {

        jsonParser = jsonParser.replace("jsonParser=", "");
        System.out.println("i'm in, it's dark here wow: " + jsonParser);//TODO: delete print code

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {

            JsonNode root = objectMapper.readTree(jsonParser);
            JsonNode cartMedicinesJsonArray = root.path("cart_medicines");
            JsonNode prescriptionMedicinesJsonArray = root.path("prescription_medicines");

            try {

                if (session.getAttribute("registered_patient") != null) {

                    if (cartMedicinesJsonArray.isArray() && (cartMedicinesJsonArray.size() > 0) && prescriptionMedicinesJsonArray.isArray() && (prescriptionMedicinesJsonArray.size() > 0)) {

                        List<CartMedicine> cartMedicines = new ArrayList<>();
                        List<Medicine> medicines = new ArrayList<>();
                        cartMedicinesJsonArray.forEach(cartMedicineJson -> cartMedicines.add(objectMapper.convertValue(cartMedicineJson, CartMedicine.class)));
                        prescriptionMedicinesJsonArray.forEach(prescriptionMedicine -> medicines.add(objectMapper.convertValue(prescriptionMedicine, Medicine.class)));

                        System.out.println("in start cart the cart medicines size before insert in session" + cartMedicines.size());//TODO: delete print
                        session.setAttribute("cart_medicines", cartMedicines);
                        session.setAttribute("prescription_medicines", medicines);

                        if ((boolean)session.getAttribute("registered_patient"))
                            return new ModelAndView("redirect:/" + "patient_cart/" + ((Patient) session.getAttribute("patient")).getUsername());
                        else
                            return new ModelAndView("redirect:/" + "patient_cart/anonymous");
                    } else {
                        //return error in parsing data or send empty cart
                        System.out.println("return error in parsing data or send empty cart ..");//TODO: delete print code
                        if ((boolean)session.getAttribute("registered_patient"))
                            return new ModelAndView("patient_profile").addObject("cart_error", "There is no medicines in cart! /n please add at least one medicine");
                        else
                            return new ModelAndView("new_prescription").addObject("cart_error", "There is no medicines in cart! /n please add at least one medicine");
                    }
                } else {
                    //you have no access to this level
                    return new ModelAndView("redirect:/" + "access_patient");
                }
            } catch (NullPointerException e) {
                //you have no access to this level
                return new ModelAndView("redirect:/" + "access_patient");
            }

        } catch (IOException e) {
            // return something went wrong when parsing the json
            System.out.println("return something went wrong when parsing the json");//TODO: delete print code
            System.out.println(e);//TODO: delete print code
            return new ModelAndView("patient_profile").addObject("cart_error", "Something wrong happened when parsing the data, Please Try again!");
        }
    }

    @RequestMapping(value = "/patient_cart/{username}", method = RequestMethod.GET)
    public ModelAndView patientCart(@PathVariable("username") String username, HttpSession session) {
        try {
            if (((List<CartMedicine>) session.getAttribute("cart_medicines")).size() > 0) {
                if ((boolean)session.getAttribute("registered_patient")) {
                    if (((Patient) session.getAttribute("patient")).getUsername().equals(username))
                        return new ModelAndView("patient_cart").addObject("history", new History());
                    else
                        return new ModelAndView("redirect:/" + "access_patient");
                } else
                    return new ModelAndView("patient_cart");
            } else
                return new ModelAndView("redirect:/" + "patient_profile/" + ((Patient) session.getAttribute("patient")).getUsername());
        } catch (NullPointerException e) {
            return new ModelAndView("redirect:/" + "access_patient");
        }
    }

    @RequestMapping(value = "/edit_medicine_quantity", method = RequestMethod.POST)
    public @ResponseBody String editMedicineQuantity(@RequestParam String medicine_id, @RequestParam int quantity, HttpSession session) {
        try {
            for (Medicine medicine : ((List<Medicine>) session.getAttribute("prescription_medicines"))) {
                if (medicine.getId().equals(medicine_id)) {
                    if (medicine.getQuantity() >= quantity) {
                        for (CartMedicine cartMedicine : ((List<CartMedicine>) session.getAttribute("cart_medicines"))) {
                            if (cartMedicine.getMedicine_id().equals(medicine_id)) {
                                cartMedicine.setQuantity(quantity > 0 ? quantity : 1);
                                return "Done";
                            }
                        }
                    } else
                        return "Out_of_Stock";
                }
            }
        } catch (NullPointerException e) {
        }
        return "Error";
    }
    @RequestMapping(value = "/delete_medicine", method = RequestMethod.POST)
    public @ResponseBody String deleteMedicine(@RequestParam String medicine_id, HttpSession session) {
        try {
            for (CartMedicine cartMedicine : (List<CartMedicine>) session.getAttribute("cart_medicines")) {
                if (cartMedicine.getMedicine_id().equals(medicine_id)) {
                    ((List<CartMedicine>) session.getAttribute("cart_medicines")).remove(cartMedicine);
                    for (Medicine medicine : ((List<Medicine>) session.getAttribute("prescription_medicines"))) {
                        if (medicine.getId().equals(medicine_id)) {
                            ((List<Medicine>) session.getAttribute("prescription_medicines")).remove(medicine);
                            return "Done_";
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("error in deleting");
        }
        return "Error_";
    }

    @RequestMapping(value = "patient_cart/cart_table", method = RequestMethod.GET)
    public ModelAndView cartTable(HttpSession session) {
        return new ModelAndView("cart_table");
    }

    @RequestMapping(value = "/commit_cart", method = RequestMethod.POST)
    public ModelAndView commitCart(@ModelAttribute History history, HttpSession session){

        System.out.println("i am in commit_cart ");//TODO: delete print

        Patient patient = (Patient) session.getAttribute("patient");
        try {

            List<CartMedicine> cartMedicines = (List<CartMedicine>) session.getAttribute("cart_medicines");
            System.out.println("in commit cart the cart medicines size: " + cartMedicines.size());//TODO: delete print

            if (cartMedicines.size() > 0) {
                if ((boolean)session.getAttribute("registered_patient")) {

                    RestTemplate restTemplate = new RestTemplate();
                    Gson gson = new Gson();
                    ResponseEntity<String> responseEntity = restTemplate.postForEntity(API_CONTROLLER_LINK + "accessPatient", patient, String.class);
                    if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                        if (gson.fromJson(responseEntity.getBody(), PatientResponse.class).getSuccess() == 1) {

                            Prescription prescription = new Prescription();
                            prescription.setPrescription_date(new Date().getTime());
                            prescription.setHistory_id(history.getId());
                            prescription.setPatient_id(patient.getId());
                            prescription.setPrice(0.0);

                            /** declaration of the json objects **/
                            JSONObject prescriptionJsonObject = new JSONObject(prescription);
                            JSONObject patientJsonObject = new JSONObject(patient);
                            JSONObject historyJsonObject = new JSONObject(history);
                            JSONArray cartMedicinesJsonObject = new JSONArray(cartMedicines);
                            /** put json objects into the main json which will send to the web service **/
                            JSONObject mainJsonObject = new JSONObject();
                            mainJsonObject.put("prescription", prescriptionJsonObject);
                            mainJsonObject.put("patient", patientJsonObject);
                            mainJsonObject.put("history", historyJsonObject);
                            mainJsonObject.put("cartMedicines", cartMedicinesJsonObject);


                            System.out.println("json that will send to web service: " + mainJsonObject.toString()); //TODO: delete print
                            responseEntity = restTemplate.postForEntity(API_CONTROLLER_LINK + "setPrescription", mainJsonObject.toString(), String.class);

                            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                                SuccessResponse successResponse = gson.fromJson(responseEntity.getBody(), ResultSuccessResponse.class).getSuccess().get(0);
                                if ((successResponse.getSuccess_prescription() == 1) & (successResponse.getSuccess_cart() == 1) && (successResponse.getSuccess_history() == 1) && (successResponse.getPrescription_details() != null)) {
                                    //return finally you have insert a new prescription to the patient
                                    System.out.println("prescription details: " + successResponse.getPrescription_details());//TODO: delete print
                                    System.out.println("return finally you have insert a new prescription to the patient");//TODO: delete print
                                    session.setAttribute("prescription_qr", Base64.encode(QRCode.from(successResponse.getPrescription_details()).to(ImageType.PNG).withSize(500, 500).stream().toByteArray()));
                                    return new ModelAndView("redirect:/" + "prescription_qr");
                                } else if (successResponse.getOutOfStockMedicines().length() > 0)
                                    return new ModelAndView("redirect:/" + "patient_profile/" + patient.getUsername()).addObject("cart_error", "Sorry This medicine/s is not currently in stock \n" + successResponse.getOutOfStockMedicines());
                            }
                            // return something went wrong when parsing the json
                            System.out.println("return something went wrong when parsing the json");//TODO: delete print
                            return new ModelAndView("redirect:/" + "patient_profile/" + patient.getUsername());
                        }
                        //return you are no longer have access for this patient
                        System.out.println("return you are no longer have access for this patient");//TODO: delete print
                        return new ModelAndView("redirect:/" + "access_patient");
                    }
                    //return you are no longer have access for this patient
                    System.out.println("return you are no longer have access for this patient");//TODO: delete print
                    return new ModelAndView("redirect:/" + "access_patient");
                }

                JSONArray cartMedicinesJsonArray = new JSONArray(cartMedicines);
                JSONObject mainJsonObject = new JSONObject();
                mainJsonObject.put("cartMedicines", cartMedicinesJsonArray);

                RestTemplate restTemplate = new RestTemplate();
                System.out.println("json that will send to eb service: " + mainJsonObject.toString()); //TODO: delete print
                String prescriptionDetails = restTemplate.postForObject(API_CONTROLLER_LINK + "getPrescriptionDetails", mainJsonObject.toString(), String.class);

                //return the string of QR
                System.out.println("prescription details: " + prescriptionDetails);//TODO: delete print
                System.out.println("return the string of QR");//TODO: delete print
                session.setAttribute("prescription_qr", Base64.encode(QRCode.from(prescriptionDetails).to(ImageType.PNG).withSize(500, 500).stream().toByteArray()));
                return new ModelAndView("redirect:/" + "prescription_qr");
            } else {
                System.out.println("return the cart medicines is empty");//TODO: delete print
                if ((boolean)session.getAttribute("registered_patient"))
                    return new ModelAndView("redirect:/" + "patient_profile/" + patient.getUsername());
                else
                    return new ModelAndView("redirect:/" + "on_the_move_prescription/");
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("return error");//TODO: delete print
            return new ModelAndView("redirect:/" + "patient_profile/" + patient.getUsername());
        }
    }

    @RequestMapping(value = "/prescription_qr", method = RequestMethod.GET)
    public ModelAndView prescriptionQR(HttpSession session) {
        try {
            if (session.getAttribute("prescription_qr") == null) {
                return new ModelAndView("redirect:/" + "access_patient");
            } else {
                ModelAndView modelAndView = new ModelAndView("prescription_qr");
                modelAndView.addObject("prescription_qr", session.getAttribute("prescription_qr"));
                session.invalidate();
                return modelAndView;
            }
        } catch (NullPointerException e) {
            return new ModelAndView("redirect:/" + "access_patient");
        }
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            //new SecurityContextLogoutHandler().logout(request, response, auth);
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login?logout";
    }

    private String getPrincipal(){
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails)
            username = ((UserDetails)principal).getUsername();
        else
            username = principal.toString();

        return username;
    }
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }
    private String getMedicinesList(HttpSession session) {

        RestTemplate restTemplate = new RestTemplate();
        Gson gson = new Gson();

        List<Medicine> medicines = new ArrayList<>();
        Enumeration attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            if (((String)attributeNames.nextElement()).equals("medicines_list")) {
                medicines = ((List<Medicine>) session.getAttribute("medicines_list"));
                break;
            }
        }
        if (medicines.isEmpty()) {
            try {
                medicines = gson.fromJson(restTemplate.getForEntity(API_CONTROLLER_LINK + "getMedicinesList", String.class).getBody(), MedicineResponse.class).getMedicines();
                session.setAttribute("medicines_list", medicines);
            } catch (NullPointerException e) {
            }
        }
        try {
            for (Medicine medicine_prescription : ((List<Medicine>)session.getAttribute("prescription_medicines"))) {
                for (Medicine medicine : medicines) {
                    if (medicine.getId().equals(medicine_prescription.getId())) {
                        medicines.remove(medicine);
                        break;
                    }
                }
            }
        } catch (NullPointerException e) {
        }
        return gson.toJson(medicines);
    }
}