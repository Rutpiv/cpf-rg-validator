package br.edu.fesa.validator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Represents the result of a document validation process. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResultModel {
  /** The original document number */
  private String document;

  /** Overall validation status */
  private boolean validated;

  /** Indicates if document format is valid */
  private boolean validFormat;

  /** Indicates if document has repeated digits */
  private boolean repeatedDigits;

  /** Indicates if check digits are valid */
  private boolean checkDigitsValid;

  /** Validation message for user feedback */
  private String message;
}
