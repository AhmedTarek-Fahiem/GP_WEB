package com.graduationproject.controller;


import com.google.gson.Gson;
import com.graduationproject.model.*;
import com.graduationproject.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
//@SessionAttributes("roles")
public class ProjectWebController {


    private final String API_CONTROLLER_LINK = "http://localhost:8080/api/";


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
            System.out.println("login method anonymous user");
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("user", new User());
            return modelAndView;
        } else {
            System.out.println("login method logged-in user");
            return patientAccess();
        }
    }

    @RequestMapping(value = "/access", method = RequestMethod.GET)
    public ModelAndView patientAccess() {
        System.out.println("patientAccess method with get");
        ModelAndView modelAndView = new ModelAndView("access");
        modelAndView.addObject("patient", new Patient());
        return modelAndView;
    }

    @RequestMapping(value = "/access", method = RequestMethod.POST)
    public ModelAndView accessPatientProfile(@Valid@ModelAttribute Patient patient) {
        System.out.println("accessPatientProfile method with post");
        System.out.println("accessPatientProfile method with post - patient username : " + patient.getUsername());
        System.out.println("accessPatientProfile method with post - patient PIN : " + patient.getPIN());

        RestTemplate operations = new RestTemplate();
        ResponseEntity<String> responseAccessEntity = operations.postForEntity(API_CONTROLLER_LINK + "accessPatient", patient, String.class);
        System.out.println("response Access Entity = " + responseAccessEntity);
        if (responseAccessEntity.getStatusCode().equals(HttpStatus.OK)) {
            Gson gson = new Gson();
            PatientResponse patientResponse = gson.fromJson(responseAccessEntity.getBody(), PatientResponse.class);
            if (patientResponse.getSuccess() == 1) {
                patient = patientResponse.getPatients().get(0);
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> responseDetailsEntity = restTemplate.postForEntity(API_CONTROLLER_LINK + "getPatientDetails", patient, String.class);
                System.out.println("response Details Entity = " + responseDetailsEntity);
                if (responseDetailsEntity.getStatusCode().equals(HttpStatus.OK)){
                    PatientDetailsResponse patientDetailsResponse = gson.fromJson(responseDetailsEntity.getBody(), PatientDetailsResponse.class);
                    if (patientDetailsResponse.getSuccess() == 1)
                        return new ModelAndView("patient_profile").addObject("patient", patient).addObject("patient_details", patientDetailsResponse.getPatientDetails());
                    return new ModelAndView("patient_profile").addObject("patient", patient);
                }
                return new ModelAndView("patient_profile").addObject("patient", patientResponse.getPatients().get(0)).addObject("fetch_details_error", "Error in fetching the patient details");
            }
        }
        return new ModelAndView("access").addObject("error", "Wrong Username or PIN.");
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
}
