package br.edu.fesa.validator.controller;

import br.edu.fesa.validator.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DocumentController {

  @Autowired private DocumentService documentService;

  @GetMapping("/")
  public String mostrarFormulario() {
    return "index";
  }

  @PostMapping("/validar")
  public String validarDocumento(@RequestParam String documento, Model model) {
    String resultado = documentService.validarDocumento(documento);
    model.addAttribute("resultado", resultado);
    model.addAttribute("documento", documento); // Mantém o valor digitado no formulário
    return "index";
  }
}
