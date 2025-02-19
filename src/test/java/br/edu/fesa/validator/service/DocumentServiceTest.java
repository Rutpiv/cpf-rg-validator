package br.edu.fesa.validator.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import br.edu.fesa.validator.model.DocumentResultModel;
import br.edu.fesa.validator.utils.AFDValidator;
import br.edu.fesa.validator.utils.CheckDigitValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for the DocumentService class. */
@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {

  @Mock private AFDValidator afdValidator;

  @Mock private CheckDigitValidator checkDigitValidator;

  @InjectMocks private DocumentService documentService;

  // ====================== Empty String Test ======================

  // Empty string – Should be invalid
  @Test
  void validateDocument_EmptyString() {
    String document = "";
    when(afdValidator.validateDocument(document))
        .thenReturn("Formato inválido: documento incompleto ou formato incorreto");

    DocumentResultModel result = documentService.validateDocument(document);

    assertFalse(result.isValidated());
    assertFalse(result.isValidFormat());
    assertEquals("Documento inválido: formato inválido", result.getMessage());
  }

  // ====================== CPF Tests ======================

  // Case 1: CPF with full punctuation (e.g.: 811.835.170-09) – Should be valid
  @Test
  void validateCPF_ValidFullPunctuation() {
    String cpf = "811.835.170-09";
    when(afdValidator.validateDocument(cpf)).thenReturn("CPF válido");
    when(checkDigitValidator.validateCPFCheckDigits(cpf)).thenReturn(null);

    DocumentResultModel result = documentService.validateDocument(cpf);

    assertTrue(result.isValidated());
    assertEquals("Documento válido", result.getMessage());
  }

  // Case 2: CPF with incomplete punctuation (e.g.: 811.835170-09) – Should be valid
  @Test
  void validateCPF_ValidIncompletePunctuation() {
    String cpf = "811.835170-09";
    when(afdValidator.validateDocument(cpf)).thenReturn("CPF válido");
    when(checkDigitValidator.validateCPFCheckDigits(cpf)).thenReturn(null);

    DocumentResultModel result = documentService.validateDocument(cpf);

    assertTrue(result.isValidated());
    assertEquals("Documento válido", result.getMessage());
  }

  // Case 3: CPF without punctuation (e.g.: 81183517009) – Should be valid
  @Test
  void validateCPF_ValidNoPunctuation() {
    String cpf = "81183517009";
    when(afdValidator.validateDocument(cpf)).thenReturn("CPF válido");
    when(checkDigitValidator.validateCPFCheckDigits(cpf)).thenReturn(null);

    DocumentResultModel result = documentService.validateDocument(cpf);

    assertTrue(result.isValidated());
    assertEquals("Documento válido", result.getMessage());
  }

  // Case 4: CPF with all digits equal (e.g.: 111.111.111-11) – Should be invalid
  @Test
  void validateCPF_InvalidAllSameDigits() {
    String cpf = "111.111.111-11";
    when(afdValidator.validateDocument(cpf)).thenReturn("CPF válido");
    when(checkDigitValidator.validateCPFCheckDigits(cpf))
        .thenReturn("Dígitos verificadores do CPF inválidos");

    DocumentResultModel result = documentService.validateDocument(cpf);

    assertFalse(result.isValidated());
    assertTrue(result.isRepeatedDigits());
    assertEquals(
        "Documento inválido: dígitos repetidos + dígitos verificadores incorretos",
        result.getMessage());
  }

  // Case 5: CPF with wrong characters (e.g.: 123a456b789-0c) – Should be invalid
  @Test
  void validateCPF_InvalidWrongCharacters() {
    String cpf = "123a456b789-0c";
    when(afdValidator.validateDocument(cpf))
        .thenReturn("Formato inválido: caractere 'a' não permitido");

    DocumentResultModel result = documentService.validateDocument(cpf);

    assertFalse(result.isValidated());
    assertFalse(result.isValidFormat());
    assertEquals("Documento inválido: formato inválido", result.getMessage());
  }

  // Case 6: CPF with too few characters (e.g.: 1234567890) – Should be invalid
  @Test
  void validateCPF_InvalidTooShort() {
    String cpf = "1234567890";
    when(afdValidator.validateDocument(cpf))
        .thenReturn("Formato inválido: documento incompleto ou formato incorreto");

    DocumentResultModel result = documentService.validateDocument(cpf);

    assertFalse(result.isValidated());
    assertFalse(result.isValidFormat());
    assertEquals("Documento inválido: formato inválido", result.getMessage());
  }

  // ====================== RG Tests ======================

  // Case 1: RG with full punctuation (e.g.: 34.998.152-8) – Should be valid
  @Test
  void validateRG_ValidFullPunctuation() {
    String rg = "34.998.152-8";
    when(afdValidator.validateDocument(rg)).thenReturn("RG válido");
    when(checkDigitValidator.validateRGCheckDigit(rg)).thenReturn(null);

    DocumentResultModel result = documentService.validateDocument(rg);

    assertTrue(result.isValidated());
    assertEquals("Documento válido", result.getMessage());
  }

  // Case 2: RG with incomplete punctuation (e.g.: 34.998152-8) – Should be valid
  @Test
  void validateRG_ValidIncompletePunctuation() {
    String rg = "34.998152-8";
    when(afdValidator.validateDocument(rg)).thenReturn("RG válido");
    when(checkDigitValidator.validateRGCheckDigit(rg)).thenReturn(null);

    DocumentResultModel result = documentService.validateDocument(rg);

    assertTrue(result.isValidated());
    assertEquals("Documento válido", result.getMessage());
  }

  // Case 3: RG without punctuation (e.g.: 349981528) – Should be valid
  @Test
  void validateRG_ValidNoPunctuation() {
    String rg = "349981528";
    when(afdValidator.validateDocument(rg)).thenReturn("RG válido");
    when(checkDigitValidator.validateRGCheckDigit(rg)).thenReturn(null);

    DocumentResultModel result = documentService.validateDocument(rg);

    assertTrue(result.isValidated());
    assertEquals("Documento válido", result.getMessage());
  }

  // Case 4: RG with all digits equal (e.g.: 11.111.111-1) – Should be invalid
  @Test
  void validateRG_InvalidAllSameDigits() {
    String rg = "11.111.111-1";
    when(afdValidator.validateDocument(rg)).thenReturn("RG válido");
    when(checkDigitValidator.validateRGCheckDigit(rg))
        .thenReturn("Dígito verificador do RG inválido");

    DocumentResultModel result = documentService.validateDocument(rg);

    assertFalse(result.isValidated());
    assertTrue(result.isRepeatedDigits());
    assertEquals(
        "Documento inválido: dígitos repetidos + dígitos verificadores incorretos",
        result.getMessage());
  }

  // Case 5: RG with wrong characters (e.g.: 12a34-56b7) – Should be invalid
  @Test
  void validateRG_InvalidWrongCharacters() {
    String rg = "12a34-56b7";
    when(afdValidator.validateDocument(rg))
        .thenReturn("Formato inválido: caractere 'a' não permitido");

    DocumentResultModel result = documentService.validateDocument(rg);

    assertFalse(result.isValidated());
    assertFalse(result.isValidFormat());
    assertEquals("Documento inválido: formato inválido", result.getMessage());
  }

  // Case 6: RG with too few characters (e.g.: 12345678) – Should be invalid
  @Test
  void validateRG_InvalidTooShort() {
    String rg = "12345678";
    when(afdValidator.validateDocument(rg))
        .thenReturn("Formato inválido: documento incompleto ou formato incorreto");

    DocumentResultModel result = documentService.validateDocument(rg);

    assertFalse(result.isValidated());
    assertFalse(result.isValidFormat());
    assertEquals("Documento inválido: formato inválido", result.getMessage());
  }
}
