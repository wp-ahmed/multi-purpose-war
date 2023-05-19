package site.easytobuild.multipurpose.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import site.easytobuild.multipurpose.entity.Business;
import site.easytobuild.multipurpose.jenkins.Jenkins;
import site.easytobuild.multipurpose.service.BusinessService;

import java.security.Principal;


@Controller
public class BusinessSelectionController {

    @Autowired
    private BusinessService businessService;

    @GetMapping("/choose-your-business/")
    public String businessSelection(Model model) {
        Business business = new Business();
        model.addAttribute("business",business);
        return "business-selection";
    }

    @PostMapping("/save-business")
    public RedirectView saveBusiness(@ModelAttribute("business") Business business, Principal principal, RedirectAttributes attributes) {
        businessService.save(business,principal);
        String name = business.getBusinessType();
        attributes.addAttribute("businessName", business.getBusinessName());
        return new RedirectView("/show-details/");
    }

    @GetMapping("/show-details/")
    public String showBusinessDetails(@RequestParam String businessName, Model model) {
        Business business = businessService.findByBusinessName(businessName);
        model.addAttribute("business",business);
        return "show-details";
    }

    @PostMapping("/build-your-business")
    public String buildYourSite(@ModelAttribute("business") Business business) {
        Jenkins jenkins = new Jenkins(business.getBusinessName(),business.getBusinessType());
        jenkins.createItem();
        jenkins.buildProject();
        return "redirect:success-build";
    }

    @GetMapping("/success-build")
    public String successBuilding(){
        return "success-build";
    }
}
