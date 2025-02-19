package br.edu.fesa.validator.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/** Unit tests for the CheckDigitValidator class. */
@SpringBootTest
class CheckDigitValidatorTest {

  private final CheckDigitValidator validator = new CheckDigitValidator();

  // ====================== CPF Tests ======================

  // Case 1: CPF with full punctuation (e.g.: 811.835.170-09) – Should be valid
  @Test
  void validateCPFCheckDigits_FullPunctuation_ShouldBeValid() {
    assertNull(validator.validateCPFCheckDigits("811.835.170-09"));
  }

  // Case 2: CPF with incomplete punctuation (e.g.: 811.835170-09) – Should be valid
  @Test
  void validateCPFCheckDigits_IncompletePunctuation_ShouldBeValid() {
    assertNull(validator.validateCPFCheckDigits("811.835170-09"));
  }

  // Case 3: CPF without punctuation (e.g.: 81183517009) – Should be valid
  @Test
  void validateCPFCheckDigits_NoPunctuation_ShouldBeValid() {
    assertNull(validator.validateCPFCheckDigits("81183517009"));
  }

  // Case 4: CPF with all digits equal (e.g.: 111.111.111-11) – Should be invalid
  @Test
  void validateCPFCheckDigits_AllSameDigits_ShouldBeInvalid() {
    assertEquals(
        "Dígitos verificadores do CPF inválidos",
        validator.validateCPFCheckDigits("111.111.111-11"));
  }

  // Case 5: CPF with invalid check digits (e.g.: 811.835.170-00) – Should be invalid
  @Test
  void validateCPFCheckDigits_InvalidDigits_ShouldBeInvalid() {
    assertEquals(
        "Dígitos verificadores do CPF inválidos",
        validator.validateCPFCheckDigits("811.835.170-00"));
  }

  // ====================== RG Tests ======================

  // Case 1: RG with full punctuation (e.g.: 34.998.152-8) – Should be valid
  @Test
  void validateRGCheckDigit_FullPunctuation_ShouldBeValid() {
    assertNull(validator.validateRGCheckDigit("34.998.152-8"));
  }

  // Case 2: RG with incomplete punctuation (e.g.: 34.998152-8) – Should be valid
  @Test
  void validateRGCheckDigit_IncompletePunctuation_ShouldBeValid() {
    assertNull(validator.validateRGCheckDigit("34.998152-8"));
  }

  // Case 3: RG without punctuation (e.g.: 349981528) – Should be valid
  @Test
  void validateRGCheckDigit_NoPunctuation_ShouldBeValid() {
    assertNull(validator.validateRGCheckDigit("349981528"));
  }

  // Case 4: RG with uppercase 'X' as check digit (e.g.: 33.331.427-X) – Should be valid
  @Test
  void validateRGCheckDigit_UppercaseX_ShouldBeValid() {
    assertNull(validator.validateRGCheckDigit("33.331.427-X"));
  }

  // Case 5: RG with lowercase 'x' as check digit (e.g.: 33.331.427-x) – Should be valid
  @Test
  void validateRGCheckDigit_LowercaseX_ShouldBeValid() {
    assertNull(validator.validateRGCheckDigit("33.331.427-x"));
  }

  // Case 6: RG with all digits equal (e.g.: 11.111.111-1) – Should be invalid
  @Test
  void validateRGCheckDigit_AllSameDigits_ShouldBeInvalid() {
    assertEquals(
        "Dígito verificador do RG inválido", validator.validateRGCheckDigit("11.111.111-1"));
  }

  // Case 7: RG with invalid check digit (e.g.: 34.998.152-9) – Should be invalid
  @Test
  void validateRGCheckDigit_InvalidDigit_ShouldBeInvalid() {
    assertEquals(
        "Dígito verificador do RG inválido", validator.validateRGCheckDigit("34.998.152-9"));
  }
}
