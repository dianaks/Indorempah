package com.blibli.future.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Nita on 02/09/2016.
 */
@Controller
public class MainController {

    @RequestMapping("/")
    public String greeting(Model model) {
        String nama = "Budi";
        model.addAttribute("name", nama);
        return "landing";
    }

}