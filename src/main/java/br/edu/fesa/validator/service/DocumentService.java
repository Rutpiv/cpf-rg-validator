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

@Service
public class DocumentService {

  private final AFDValidator afdValidator;
  private final CheckDigitValidator checkDigitValidator;

  @Autowired
  public DocumentService(AFDValidator afdValidator, CheckDigitValidator checkDigitValidator) {
    this.afdValidator = afdValidator;
    this.checkDigitValidator = checkDigitValidator;
  }

  public DocumentResultModel validateDocument(String documento) {
    DocumentResultModel result = new DocumentResultModel();
    result.setDocumento(documento);

    // Validação 1: Dígitos repetidos
    boolean digitosRepetidos = validarDigitosRepetidos(documento);
    result.setDigitosRepetidos(digitosRepetidos);

    // Validação 2: Formato do documento
    String formatoValido = afdValidator.validarDocumento(documento);
    boolean formatoOK = formatoValido.startsWith("CPF") || formatoValido.startsWith("RG");
    result.setFormatoValido(formatoOK);

    // Validação 3: Dígitos verificadores (se formato válido)
    boolean digitosVerificadoresOK = false;
    if (formatoOK) {
      try {
        String erro =
            formatoValido.startsWith("CPF")
                ? checkDigitValidator.validarDigitosCPF(documento)
                : checkDigitValidator.validarDigitosRG(documento);

        digitosVerificadoresOK = (erro == null);
      } catch (Exception e) {
        digitosVerificadoresOK = false;
      }
    }
    result.setDigitosVerificadoresOK(digitosVerificadoresOK);

    // Resultado final
    result.setValidado(!digitosRepetidos && formatoOK && digitosVerificadoresOK);
    result.setMensagem(gerarMensagem(result));

    return result;
  }

  public List<DocumentResultModel> validateDocuments(String documentos) {
    return Arrays.stream(documentos.split("\n"))
        .filter(line -> !line.isBlank())
        .map(String::trim)
        .map(this::validateDocument)
        .collect(Collectors.toList());
  }

  private boolean validarDigitosRepetidos(String documento) {
    String numeros = documento.replaceAll("[^0-9]", "");
    return numeros.matches("^(\\d)\\1+$");
  }

  private String gerarMensagem(DocumentResultModel result) {
    StringBuilder mensagem = new StringBuilder();

    if (result.isValidado()) {
      return "Documento válido";
    }

    mensagem.append("Documento inválido: ");
    List<String> erros = new ArrayList<>();

    if (result.isDigitosRepetidos()) {
      erros.add("dígitos repetidos");
    }
    if (!result.isFormatoValido()) {
      erros.add("formato inválido");
    }
    if (result.isFormatoValido() && !result.isDigitosVerificadoresOK()) {
      erros.add("dígitos verificadores incorretos");
    }

    return mensagem.append(String.join(" + ", erros)).toString();
  }
}
