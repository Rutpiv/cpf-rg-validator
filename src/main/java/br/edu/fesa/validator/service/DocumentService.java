package br.edu.fesa.validator.service;

import br.edu.fesa.validator.model.DocumentResultModel;
import br.edu.fesa.validator.utils.AFDValidator;
import br.edu.fesa.validator.utils.CheckDigitValidator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service class for validating documents. */
@Service
public class DocumentService {

  private final AFDValidator afdValidator;
  private final CheckDigitValidator checkDigitValidator;

  @Autowired
  public DocumentService(AFDValidator afdValidator, CheckDigitValidator checkDigitValidator) {
    this.afdValidator = afdValidator;
    this.checkDigitValidator = checkDigitValidator;
  }

  /**
   * Validates a single document.
   *
   * @param document the document to be validated
   * @return a DocumentResult containing validation details
   */
  public DocumentResultModel validateDocument(String document) {
    DocumentResultModel result = new DocumentResultModel();
    result.setDocument(document);

    // Validation 1: Check for repeated digits
    boolean repeatedDigits = checkRepeatedDigits(document);
    result.setRepeatedDigits(repeatedDigits);

    // Validation 2: Check the document format
    String formatResult = afdValidator.validateDocument(document);
    boolean formatOK = formatResult.startsWith("CPF") || formatResult.startsWith("RG");
    result.setValidFormat(formatOK);

    // Validation 3: Validate check digits (if format is valid)
    boolean checkDigitsValid = false;
    if (formatOK) {
      try {
        String error =
            formatResult.startsWith("CPF")
                ? checkDigitValidator.validateCPFCheckDigits(document)
                : checkDigitValidator.validateRGCheckDigit(document);
        checkDigitsValid = (error == null);
      } catch (Exception e) {
        checkDigitsValid = false;
      }
    }
    result.setCheckDigitsValid(checkDigitsValid);

    // Final validation: the document is valid if no repeated digits, format is correct, and check
    // digits are valid
    result.setValidated(!repeatedDigits && formatOK && checkDigitsValid);
    result.setMessage(generateMessage(result));

    return result;
  }

  /**
   * Validates multiple documents provided in a newline-separated string.
   *
   * @param documents a string containing multiple documents separated by newline
   * @return a list of DocumentResult objects for each document
   */
  public List<DocumentResultModel> validateDocuments(String documents) {
    return Arrays.stream(documents.split("\n"))
        .filter(line -> !line.isBlank())
        .map(String::trim)
        .map(this::validateDocument)
        .collect(Collectors.toList());
  }

  /**
   * Checks if the document consists of repeated digits.
   *
   * @param document the document to check
   * @return true if the document has all repeated digits, false otherwise
   */
  private boolean checkRepeatedDigits(String document) {
    String numbers = document.replaceAll("[^0-9]", "");
    return numbers.matches("^(\\d)\\1+$");
  }

  /**
   * Generates a validation message based on the validation result. The message remains in
   * Portuguese.
   *
   * @param result the DocumentResult containing validation details
   * @return a message describing the validation result
   */
  private String generateMessage(DocumentResultModel result) {
    StringBuilder message = new StringBuilder();

    if (result.isValidated()) {
      return "Documento válido";
    }

    message.append("Documento inválido: ");
    List<String> errors = new ArrayList<>();

    if (result.isRepeatedDigits()) {
      errors.add("dígitos repetidos");
    }
    if (!result.isValidFormat()) {
      errors.add("formato inválido");
    }
    if (result.isValidFormat() && !result.isCheckDigitsValid()) {
      errors.add("dígitos verificadores incorretos");
    }

    message.append(String.join(" + ", errors));
    return message.toString();
  }
}
