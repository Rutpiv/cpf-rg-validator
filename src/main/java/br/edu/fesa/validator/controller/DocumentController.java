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

/** Controller for handling document validation requests. */
@Controller
public class DocumentController {

  @Autowired private DocumentService documentService;

  /**
   * Displays the index page with the default mode set to "single".
   *
   * @param model the model to pass attributes to the view
   * @return the name of the index view
   */
  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("mode", "single");
    return "index";
  }

  /**
   * Validates a single document.
   *
   * @param document the document string to validate
   * @param redirectAttributes attributes for flash scope
   * @return redirect URL to the index page with "single" mode
   */
  @PostMapping("/validate")
  public String validateSingle(
      @RequestParam String document, RedirectAttributes redirectAttributes) {
    DocumentResultModel result = documentService.validateDocument(document);
    redirectAttributes.addFlashAttribute("result", result);
    redirectAttributes.addFlashAttribute("mode", "single");
    return "redirect:/?mode=single";
  }

  /**
   * Validates multiple documents in batch.
   *
   * @param documents a string containing multiple documents separated by newline
   * @param redirectAttributes attributes for flash scope
   * @return redirect URL to the index page with "batch" mode
   */
  @PostMapping("/validate/batch")
  public String validateBatch(
      @RequestParam String documents, RedirectAttributes redirectAttributes) {
    List<DocumentResultModel> results = documentService.validateDocuments(documents);
    redirectAttributes.addFlashAttribute("results", results);
    redirectAttributes.addFlashAttribute("mode", "batch");
    return "redirect:/?mode=batch";
  }
}
