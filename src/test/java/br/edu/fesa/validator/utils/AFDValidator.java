package br.edu.fesa.validator.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AFDValidatorTest {

  private final AFDValidator validator = new AFDValidator();

  // ====================== Teste vazio ======================

  // String vazia – Deve ser inválido
  @Test
  void validarDocumento_StringVazia_DeveSerInvalido() {
    assertEquals(
        "Formato inválido: documento incompleto ou formato incorreto",
        validator.validarDocumento(""));
  }

  // ====================== Testes para CPF ======================

  // Caso 1: CPF com pontuação completa (ex.: 811.835.170-09) – Deve ser válido
  @Test
  void validarCPF_FormatoCompleto_DeveSerValido() {
    assertEquals("CPF válido", validator.validarDocumento("811.835.170-09"));
  }

  // Caso 2: CPF com pontuação incompleta (ex.: 811.835170-09) – Deve ser válido
  @Test
  void validarCPF_FormatoIncompleto_DeveSerValido() {
    assertEquals("CPF válido", validator.validarDocumento("811.835170-09"));
  }

  // Caso 3: CPF sem pontuação (ex.: 81183517009) – Deve ser válido
  @Test
  void validarCPF_SemPontuacao_DeveSerValido() {
    assertEquals("CPF válido", validator.validarDocumento("81183517009"));
  }

  // Caso 4: CPF com todos os números iguais (ex.: 111.111.111-11) – Deve ser inválido
  @Test
  void validarCPF_DigitosRepetidosFormatoCorreto_DeveSerValido() {
    assertEquals("CPF válido", validator.validarDocumento("111.111.111-11"));
  }

  // Caso 5: CPF com caracteres errados (ex.: 123a456b789-0c) – Deve ser inválido
  @Test
  void validarCPF_CaracteresInvalidos_DeveSerInvalido() {
    assertEquals(
        "Formato inválido: caractere 'a' não permitido",
        validator.validarDocumento("123a456b789-0c"));
  }

  // Caso 6: CPF com poucos caracteres (ex.: 1234567890) – Deve ser inválido
  @Test
  void validarCPF_TamanhoIncorreto_DeveSerInvalido() {
    assertEquals(
        "Formato inválido: documento incompleto ou formato incorreto",
        validator.validarDocumento("1234567890"));
  }

  // ====================== Testes para RG ======================

  // Caso 1: RG com pontuação completa (ex.: 34.998.152-8) – Deve ser válido
  @Test
  void validarRG_FormatoCompleto_DeveSerValido() {
    assertEquals("RG válido", validator.validarDocumento("34.998.152-8"));
  }

  // Caso 2: RG com pontuação incompleta (ex.: 34.998152-8) – Deve ser válido
  @Test
  void validarRG_FormatoIncompleto_DeveSerValido() {
    assertEquals("RG válido", validator.validarDocumento("34.998152-8"));
  }

  // Caso 3: RG sem pontuação (ex.: 349981528) – Deve ser válido
  @Test
  void validarRG_SemPontuacao_DeveSerValido() {
    assertEquals("RG válido", validator.validarDocumento("349981528"));
  }

  // Caso 4: RG com todos os números iguais (ex.: 11.111.111-1) – Deve ser inválido
  @Test
  void validarRG_DigitosRepetidosFormatoCorreto_DeveSerValido() {
    assertEquals("RG válido", validator.validarDocumento("11.111.111-1"));
  }

  // Caso 5: RG com caracteres errados (ex.: 12a34-56b7) – Deve ser inválido
  @Test
  void validarRG_CaracteresInvalidos_DeveSerInvalido() {
    assertEquals(
        "Formato inválido: caractere 'a' não permitido", validator.validarDocumento("12a34-56b7"));
  }

  // Caso 6: RG com poucos caracteres (ex.: 12345678) – Deve ser inválido
  @Test
  void validarRG_TamanhoIncorreto_DeveSerInvalido() {
    assertEquals(
        "Formato inválido: documento incompleto ou formato incorreto",
        validator.validarDocumento("12345678"));
  }
}
