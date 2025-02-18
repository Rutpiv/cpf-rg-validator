package br.edu.fesa.validator.controller;

import br.edu.fesa.validator.model.DocumentResultModel;
import br.edu.fesa.validator.service.DocumentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DocumentController {

  @Autowired private DocumentService documentService;

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("modo", "single");
    return "index";
  }

  @PostMapping("/validate")
  public String validateSingle(
      @RequestParam String documento, RedirectAttributes redirectAttributes) {
    DocumentResultModel resultado = documentService.validateDocument(documento);
    redirectAttributes.addFlashAttribute("resultado", resultado);
    redirectAttributes.addFlashAttribute("modo", "single");
    return "redirect:/?modo=single";
  }

  @PostMapping("/validate/batch")
  public String validateBatch(
      @RequestParam String documentos, RedirectAttributes redirectAttributes) {
    List<DocumentResultModel> resultados = documentService.validateDocuments(documentos);
    redirectAttributes.addFlashAttribute("resultados", resultados);
    redirectAttributes.addFlashAttribute("modo", "batch");
    return "redirect:/?modo=batch";
  }
}
