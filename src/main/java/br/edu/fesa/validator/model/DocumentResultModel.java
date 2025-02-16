package br.edu.fesa.validator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResultModel {
    private String documento;
    private boolean validado;
    private boolean formatoValido;
    private boolean digitosRepetidos;
    private boolean digitosVerificadoresOK;
    private String mensagem;
}
