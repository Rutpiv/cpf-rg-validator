package br.edu.fesa.validator.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CheckDigitValidatorTest {

  private final CheckDigitValidator validator = new CheckDigitValidator();

  // ====================== Testes para CPF ======================

  // Caso 1: CPF com pontuação completa (ex.: 811.835.170-09) – Deve ser válido
  @Test
  void validarDigitosCPF_ComPontuacaoCompleta_DeveSerValido() {
    assertNull(validator.validarDigitosCPF("811.835.170-09"));
  }

  // Caso 2: CPF com pontuação incompleta (ex.: 811.835170-09) – Deve ser válido
  @Test
  void validarDigitosCPF_ComPontuacaoIncompleta_DeveSerValido() {
    assertNull(validator.validarDigitosCPF("811.835170-09"));
  }

  // Caso 3: CPF sem pontuação (ex.: 81183517009) – Deve ser válido
  @Test
  void validarDigitosCPF_SemPontuacao_DeveSerValido() {
    assertNull(validator.validarDigitosCPF("81183517009"));
  }

  // Caso 4: CPF com todos os números iguais (ex.: 111.111.111-11) – Deve ser inválido
  @Test
  void validarDigitosCPF_DigitosRepetidos_DeveSerInvalido() {
    assertEquals(
      "Dígitos verificadores do CPF inválidos",
      validator.validarDigitosCPF("111.111.111-11")
    );
  }

  // Caso 5: CPF com dígitos verificadores inválidos (ex.: 811.835.170-00) - Deve ser inválido
  @Test
  void validarDigitosCPF_DigitosInvalidos_DeveSerInvalido() {
    assertEquals(
      "Dígitos verificadores do CPF inválidos",
      validator.validarDigitosCPF("811.835.170-00")
    );
  }

  // ====================== Testes para RG ======================

  // Caso 1: RG com pontuação completa (ex.: 34.998.152-8) – Deve ser válido
  @Test
  void validarDigitosRG_ComPontuacaoCompleta_DeveSerValido() {
    assertNull(validator.validarDigitosRG("34.998.152-8"));
  }

  // Caso 2: RG com pontuação incompleta (ex.: 34.998152-8) – Deve ser válido
  @Test
  void validarDigitosRG_ComPontuacaoIncompleta_DeveSerValido() {
    assertNull(validator.validarDigitosRG("34.998152-8"));
  }

  // Caso 3: RG sem pontuação (ex.: 349981528) – Deve ser válido
  @Test
  void validarDigitosRG_SemPontuacao_DeveSerValido() {
    assertNull(validator.validarDigitosRG("349981528"));
  }

  // Caso 4: RG com dígito verificador 'X' maiúsculo (ex.: 33.331.427-X) - Deve ser válido
  @Test
  void validarDigitosRG_ComDigitoX_DeveSerValido() {
    assertNull(validator.validarDigitosRG("33.331.427-X"));
  }

  // Caso 5: RG com dígito verificador 'x' minúsculo (ex.: 33.331.427-X) - Deve ser válido
  @Test
  void validarDigitosRG_ComDigitoXMaiusculo_DeveSerValido() {
    assertNull(validator.validarDigitosRG("33.331.427-x"));
  }

  // Caso 6: RG com todos os números iguais (ex.: 11.111.111-1) – Deve ser inválido
  @Test
  void validarDigitosRG_DigitosRepetidos_DeveSerInvalido() {
    assertEquals(
      "Dígito verificador do RG inválido",
      validator.validarDigitosRG("11.111.111-1")
    );
  }

  // Caso 7: RG com dígitos verificadores inválidos (ex.: 33.998.152-9) - Deve ser inválido
  @Test
  void validarDigitosRG_DigitoInvalido_DeveSerInvalido() {
    assertEquals(
      "Dígito verificador do RG inválido",
      validator.validarDigitosRG("34.998.152-9")
    );
  }
}
