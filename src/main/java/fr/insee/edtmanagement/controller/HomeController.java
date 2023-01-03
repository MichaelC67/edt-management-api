package fr.insee.edtmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	 @GetMapping(value = "/")
	   public String redirect() {
	      return "redirect:/swagger-ui.html";
	   }
}
