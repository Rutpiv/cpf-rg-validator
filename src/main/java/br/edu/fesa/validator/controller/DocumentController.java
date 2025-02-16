package br.edu.fesa.validator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DocumentController {

  @GetMapping("/")
  public String mostrarFormulario() {
    return "index";
  }
}
