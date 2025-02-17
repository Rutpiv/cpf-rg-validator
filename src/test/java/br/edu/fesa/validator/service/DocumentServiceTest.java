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

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {

  @Mock private AFDValidator afdValidator;

  @Mock private CheckDigitValidator checkDigitValidator;

  @InjectMocks private DocumentService documentService;

  // ====================== Teste vazio ======================

  // String vazia – Deve ser inválido
  @Test
  void validateDocument_EmptyString() {
    String document = "";
    when(afdValidator.validarDocumento(document))
        .thenReturn("Formato inválido: documento incompleto ou formato incorreto");

    DocumentResultModel result = documentService.validateDocument(document);

    assertFalse(result.isValidado());
    assertFalse(result.isFormatoValido());
    assertEquals("Documento inválido: formato inválido", result.getMensagem());
  }

  // ====================== Testes para CPF ======================

  // Caso 1: CPF com pontuação completa (ex.: 123.456.789-09) – Deve ser válido
  @Test
  void validateCPF_ValidFullPunctuation() {
    String cpf = "811.835.170-09";
    when(afdValidator.validarDocumento(cpf)).thenReturn("CPF válido");
    when(checkDigitValidator.validarDigitosCPF(cpf)).thenReturn(null);

    DocumentResultModel result = documentService.validateDocument(cpf);

    assertTrue(result.isValidado());
    assertEquals("Documento válido", result.getMensagem());
  }

  // Caso 2: CPF com pontuação incompleta (ex.: 123.456789-09) – Deve ser válido
  @Test
  void validateCPF_ValidIncompletePunctuation() {
    String cpf = "811.835170-09";
    when(afdValidator.validarDocumento(cpf)).thenReturn("CPF válido");
    when(checkDigitValidator.validarDigitosCPF(cpf)).thenReturn(null);

    DocumentResultModel result = documentService.validateDocument(cpf);

    assertTrue(result.isValidado());
    assertEquals("Documento válido", result.getMensagem());
  }

  // Caso 3: CPF sem pontuação (ex.: 12345678909) – Deve ser válido
  @Test
  void validateCPF_ValidNoPunctuation() {
    String cpf = "81183517009";
    when(afdValidator.validarDocumento(cpf)).thenReturn("CPF válido");
    when(checkDigitValidator.validarDigitosCPF(cpf)).thenReturn(null);

    DocumentResultModel result = documentService.validateDocument(cpf);

    assertTrue(result.isValidado());
    assertEquals("Documento válido", result.getMensagem());
  }

  // Caso 4: CPF com todos os números iguais (ex.: 111.111.111-11) – Deve ser inválido
  @Test
  void validateCPF_InvalidAllSameDigits() {
    String cpf = "111.111.111-11";
    when(afdValidator.validarDocumento(cpf)).thenReturn("CPF válido");
    when(checkDigitValidator.validarDigitosCPF(cpf))
        .thenReturn("Dígitos verificadores do CPF inválidos");

    DocumentResultModel result = documentService.validateDocument(cpf);

    assertFalse(result.isValidado());
    assertTrue(result.isDigitosRepetidos());
    assertEquals(
        "Documento inválido: dígitos repetidos + dígitos verificadores incorretos",
        result.getMensagem());
  }

  // Caso 5: CPF com caracteres errados (ex.: 123a456b789-0c) – Deve ser inválido
  @Test
  void validateCPF_InvalidWrongCharacters() {
    String cpf = "123a456b789-0c";
    when(afdValidator.validarDocumento(cpf))
        .thenReturn("Formato inválido: caractere 'a' não permitido");

    DocumentResultModel result = documentService.validateDocument(cpf);

    assertFalse(result.isValidado());
    assertFalse(result.isFormatoValido());
    assertEquals("Documento inválido: formato inválido", result.getMensagem());
  }

  // Caso 6: CPF com poucos caracteres (ex.: 1234567890) – Deve ser inválido
  @Test
  void validateCPF_InvalidTooShort() {
    String cpf = "1234567890";
    when(afdValidator.validarDocumento(cpf))
        .thenReturn("Formato inválido: documento incompleto ou formato incorreto");

    DocumentResultModel result = documentService.validateDocument(cpf);

    assertFalse(result.isValidado());
    assertFalse(result.isFormatoValido());
    assertEquals("Documento inválido: formato inválido", result.getMensagem());
  }

  // ====================== Testes para RG ======================

  // Caso 1: RG com pontuação completa (ex.: 12.345.678-9) – Deve ser válido
  @Test
  void validateRG_ValidFullPunctuation() {
    String rg = "34.998.152-8";
    when(afdValidator.validarDocumento(rg)).thenReturn("RG válido");
    when(checkDigitValidator.validarDigitosRG(rg)).thenReturn(null);

    DocumentResultModel result = documentService.validateDocument(rg);

    assertTrue(result.isValidado());
    assertEquals("Documento válido", result.getMensagem());
  }

  // Caso 2: RG com pontuação incompleta (ex.: 12.345678-9) – Deve ser válido
  @Test
  void validateRG_ValidIncompletePunctuation() {
    String rg = "34.998152-8";
    when(afdValidator.validarDocumento(rg)).thenReturn("RG válido");
    when(checkDigitValidator.validarDigitosRG(rg)).thenReturn(null);

    DocumentResultModel result = documentService.validateDocument(rg);

    assertTrue(result.isValidado());
    assertEquals("Documento válido", result.getMensagem());
  }

  // Caso 3: RG sem pontuação (ex.: 123456789) – Deve ser válido
  @Test
  void validateRG_ValidNoPunctuation() {
    String rg = "349981528";
    when(afdValidator.validarDocumento(rg)).thenReturn("RG válido");
    when(checkDigitValidator.validarDigitosRG(rg)).thenReturn(null);

    DocumentResultModel result = documentService.validateDocument(rg);

    assertTrue(result.isValidado());
    assertEquals("Documento válido", result.getMensagem());
  }

  // Caso 4: RG com todos os números iguais (ex.: 111111111) – Deve ser inválido
  @Test
  void validateRG_InvalidAllSameDigits() {
    String rg = "111111111";
    when(afdValidator.validarDocumento(rg)).thenReturn("RG válido");
    when(checkDigitValidator.validarDigitosRG(rg)).thenReturn("Dígito verificador do RG inválido");

    DocumentResultModel result = documentService.validateDocument(rg);

    assertFalse(result.isValidado());
    assertTrue(result.isDigitosRepetidos());
    assertEquals(
        "Documento inválido: dígitos repetidos + dígitos verificadores incorretos",
        result.getMensagem());
  }

  // Caso 5: RG com caracteres errados (ex.: 12a34-56b7) – Deve ser inválido
  @Test
  void validateRG_InvalidWrongCharacters() {
    String rg = "12a34-56b7";
    when(afdValidator.validarDocumento(rg))
        .thenReturn("Formato inválido: caractere 'a' não permitido");

    DocumentResultModel result = documentService.validateDocument(rg);

    assertFalse(result.isValidado());
    assertFalse(result.isFormatoValido());
    assertEquals("Documento inválido: formato inválido", result.getMensagem());
  }

  // Caso 6: RG com poucos caracteres (ex.: 12345678) – Deve ser inválido
  @Test
  void validateRG_InvalidTooShort() {
    String rg = "12345678";
    when(afdValidator.validarDocumento(rg))
        .thenReturn("Formato inválido: documento incompleto ou formato incorreto");

    DocumentResultModel result = documentService.validateDocument(rg);

    assertFalse(result.isValidado());
    assertFalse(result.isFormatoValido());
    assertEquals("Documento inválido: formato inválido", result.getMensagem());
  }
}
