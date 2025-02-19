package br.edu.fesa.validator.utils;

import org.springframework.stereotype.Component;

/** CheckDigitValidator is responsible for validating the check digits of CPF and RG documents. */
@Component
public class CheckDigitValidator {

  /**
   * Validates the check digits of a CPF.
   *
   * @param cpf the CPF string to validate
   * @return null if valid, or an error message in Portuguese if invalid
   */
  public String validateCPFCheckDigits(String cpf) {
    // Implementation of CPF check digits validation
    int[] numbers = cpf.replaceAll("[^0-9]", "").chars().map(Character::getNumericValue).toArray();

    // Check if all digits are the same
    boolean allSame = true;
    for (int i = 1; i < numbers.length; i++) {
      if (numbers[i] != numbers[0]) {
        allSame = false;
        break;
      }
    }
    if (allSame) {
      return "Dígitos verificadores do CPF inválidos";
    }

    // Calculation of the first check digit
    int sum = 0;
    for (int i = 0; i < 9; i++) {
      sum += numbers[i] * (10 - i);
    }
    int firstDigit = 11 - (sum % 11);
    if (firstDigit >= 10) firstDigit = 0;

    // Calculation of the second check digit
    sum = 0;
    for (int i = 0; i < 10; i++) {
      sum += numbers[i] * (11 - i);
    }
    int secondDigit = 11 - (sum % 11);
    if (secondDigit >= 10) secondDigit = 0;

    return (numbers[9] == firstDigit && numbers[10] == secondDigit)
        ? null
        : "Dígitos verificadores do CPF inválidos";
  }

  /**
   * Validates the check digit of an RG. This example is based on the RG validation for São Paulo.
   *
   * @param rg the RG string to validate
   * @return null if valid, or an error message in Portuguese if invalid
   */
  public String validateRGCheckDigit(String rg) {
    // Implementation of RG check digit validation (example for SP)
    String cleanRg = rg.replaceAll("[^0-9Xx]", "");
    char digit = Character.toUpperCase(cleanRg.charAt(cleanRg.length() - 1));

    int[] numbers =
        cleanRg
            .substring(0, cleanRg.length() - 1)
            .chars()
            .map(Character::getNumericValue)
            .toArray();

    int sum = 0;
    for (int i = 0; i < numbers.length; i++) {
      sum += numbers[i] * (2 + i);
    }

    int calculated = 11 - (sum % 11);
    char expected = calculated == 10 ? 'X' : (char) (calculated + '0');

    return (digit == expected) ? null : "Dígito verificador do RG inválido";
  }
}
