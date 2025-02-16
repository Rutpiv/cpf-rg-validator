package br.edu.fesa.validator.service;

import br.edu.fesa.validator.utils.AFDValidator;
import br.edu.fesa.validator.utils.CheckDigitValidator;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

  private final AFDValidator formatValidator;
  private final CheckDigitValidator digitValidator;

  public DocumentService() {
    this.formatValidator = new AFDValidator();
    this.digitValidator = new CheckDigitValidator();
  }

  public String validarDocumento(String documento) {
    // Validação 1: Dígitos repetidos
    String erroRepetidos = validarDigitosRepetidos(documento);
    if (erroRepetidos != null) {
      return erroRepetidos;
    }

    // Validação 2: Formato do documento
    String resultadoFormato = formatValidator.validarDocumento(documento);
    if (!resultadoFormato.startsWith("CPF") && !resultadoFormato.startsWith("RG")) {
      return resultadoFormato;
    }

    // Validação 3: Dígitos verificadores
    try {
      String erroDigitos =
          resultadoFormato.startsWith("CPF")
              ? digitValidator.validarDigitosCPF(documento)
              : digitValidator.validarDigitosRG(documento);

      return erroDigitos == null
          ? "Documento válido"
          : resultadoFormato + ", mas " + erroDigitos.toLowerCase();

    } catch (Exception e) {
      return "Erro na validação: " + e.getMessage();
    }
  }

  private String validarDigitosRepetidos(String documento) {
    String numeros = documento.replaceAll("[^0-9]", "");

    if (numeros.length() < 11) return null; // Não aplica para documentos curtos

    if (numeros.matches("^(\\d)\\1+$")) {
      return "Documento inválido: todos os dígitos são iguais";
    }
    return null;
  }
}
