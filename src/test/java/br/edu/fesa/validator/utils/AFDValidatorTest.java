package br.edu.fesa.validator.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Unit tests for the AFDValidator class. */
class AFDValidatorTest {

  private final AFDValidator validator = new AFDValidator();

  // ====================== Empty String Test ======================

  // Empty string – Should be invalid
  @Test
  void validateDocument_EmptyString_ShouldBeInvalid() {
    assertEquals(
        "Formato inválido: documento incompleto ou formato incorreto",
        validator.validateDocument(""));
  }

  // ====================== CPF Tests ======================

  // Case 1: CPF with full punctuation (e.g.: 811.835.170-09) – Should be valid
  @Test
  void validateCPF_FullPunctuation_ShouldBeValid() {
    assertEquals("CPF válido", validator.validateDocument("811.835.170-09"));
  }

  // Case 2: CPF with incomplete punctuation (e.g.: 811.835170-09) – Should be valid
  @Test
  void validateCPF_IncompletePunctuation_ShouldBeValid() {
    assertEquals("CPF válido", validator.validateDocument("811.835170-09"));
  }

  // Case 3: CPF without punctuation (e.g.: 81183517009) – Should be valid
  @Test
  void validateCPF_NoPunctuation_ShouldBeValid() {
    assertEquals("CPF válido", validator.validateDocument("81183517009"));
  }

  // Case 4: CPF with all digits equal (e.g.: 111.111.111-11) – Should be valid
  @Test
  void validateCPF_AllSameDigits_FormatCorrect_ShouldBeValid() {
    assertEquals("CPF válido", validator.validateDocument("111.111.111-11"));
  }

  // Case 5: CPF with wrong characters (e.g.: 123a456b789-0c) – Should be invalid
  @Test
  void validateCPF_InvalidCharacters_ShouldBeInvalid() {
    assertEquals(
        "Formato inválido: caractere 'a' não permitido",
        validator.validateDocument("123a456b789-0c"));
  }

  // Case 6: CPF with too few characters (e.g.: 1234567890) – Should be invalid
  @Test
  void validateCPF_IncorrectLength_ShouldBeInvalid() {
    assertEquals(
        "Formato inválido: documento incompleto ou formato incorreto",
        validator.validateDocument("1234567890"));
  }

  // ====================== RG Tests ======================

  // Case 1: RG with full punctuation (e.g.: 34.998.152-8) – Should be valid
  @Test
  void validateRG_FullPunctuation_ShouldBeValid() {
    assertEquals("RG válido", validator.validateDocument("34.998.152-8"));
  }

  // Case 2: RG with incomplete punctuation (e.g.: 34.998152-8) – Should be valid
  @Test
  void validateRG_IncompletePunctuation_ShouldBeValid() {
    assertEquals("RG válido", validator.validateDocument("34.998152-8"));
  }

  // Case 3: RG without punctuation (e.g.: 349981528) – Should be valid
  @Test
  void validateRG_NoPunctuation_ShouldBeValid() {
    assertEquals("RG válido", validator.validateDocument("349981528"));
  }

  // Case 4: RG with all digits equal (e.g.: 11.111.111-1) – Should be valid
  @Test
  void validateRG_AllSameDigits_FormatCorrect_ShouldBeValid() {
    assertEquals("RG válido", validator.validateDocument("11.111.111-1"));
  }

  // Case 5: RG with wrong characters (e.g.: 12a34-56b7) – Should be invalid
  @Test
  void validateRG_InvalidCharacters_ShouldBeInvalid() {
    assertEquals(
        "Formato inválido: caractere 'a' não permitido", validator.validateDocument("12a34-56b7"));
  }

  // Case 6: RG with too few characters (e.g.: 12345678) – Should be invalid
  @Test
  void validateRG_IncorrectLength_ShouldBeInvalid() {
    assertEquals(
        "Formato inválido: documento incompleto ou formato incorreto",
        validator.validateDocument("12345678"));
  }
}
