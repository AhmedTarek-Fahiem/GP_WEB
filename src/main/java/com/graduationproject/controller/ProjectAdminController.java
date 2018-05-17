package com.graduationproject.controller;


import com.graduationproject.model.*;
import com.graduationproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping(value = "/admin")
public class ProjectAdminController {


    @Autowired
    private MedicineService medicineService;


    /**-----------------------------------------------**/

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ModelAndView admin() {
        return new ModelAndView("medicines_list").addObject("medicines_list", medicineService.getAllMedicines());
    }

    @RequestMapping(value = "/newMedicine", method = RequestMethod.GET)
    public ModelAndView newMedicine(ModelAndView model) {
        Medicine medicine = new Medicine();
        model.addObject("medicine", medicine);
        model.setViewName("medicine_form");
        return model;
    }

    @RequestMapping(value = "/editMedicine/{id}", method = RequestMethod.GET)
    public ModelAndView editMedicine(@PathVariable(value = "id") UUID medicine_id) {
        Medicine medicine = medicineService.getMedicine(medicine_id.toString());
        ModelAndView model = new ModelAndView("medicine_form");
        model.addObject("medicine", medicine);
        return model;
    }

    @RequestMapping(value = "/saveMedicine", method = RequestMethod.POST)
    public ModelAndView saveMedicine(@ModelAttribute Medicine medicine) {
        medicineService.addMedicine(medicine);
        return new ModelAndView("redirect:/admin");
    }

    @RequestMapping(value = "/deleteMedicine/{id}", method = RequestMethod.GET)
    public ModelAndView deleteMedicine(@PathVariable(value = "id") UUID medicine_id) {
        if (medicineService.deleteMedicine(medicine_id.toString()))
            return new ModelAndView("redirect:/admin");
        else
            return new ModelAndView("redirect:/logout");
    }

}