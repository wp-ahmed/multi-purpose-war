package site.easytobuild.multipurpose.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BusinessSelectionController {

    @GetMapping("/choose-your-business/")
    public String businessSelection() {
        return "business-selection";
    }
}
