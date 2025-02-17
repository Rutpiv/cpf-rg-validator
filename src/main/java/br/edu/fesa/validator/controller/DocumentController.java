package br.edu.fesa.validator.controller;

import br.edu.fesa.validator.model.DocumentResultModel;
import br.edu.fesa.validator.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("modo", "single");
        return "index";
    }

    @PostMapping("/validate")
    public String validateSingle(
        @RequestParam String documento, 
        Model model
    ) {
        DocumentResultModel resultado = documentService.validateDocument(documento);
        model.addAttribute("resultado", resultado);
        model.addAttribute("modo", "single");
        return "index";
    }

    @PostMapping("/validate/batch")
    public String validateBatch(
        @RequestParam String documentos, 
        Model model
    ) {
        List<DocumentResultModel> resultados = documentService.validateDocuments(documentos);
        model.addAttribute("resultados", resultados);
        model.addAttribute("modo", "batch");
        return "index";
    }
}
