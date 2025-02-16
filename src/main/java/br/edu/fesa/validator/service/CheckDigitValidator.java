package br.edu.fesa.validator.service;


public class CheckDigitValidator {

    public String validarDigitosCPF(String cpf) {
        // Implementação da validação dos dígitos do CPF
        int[] numbers = cpf.replaceAll("[^0-9]", "").chars().map(Character::getNumericValue).toArray();
        
        // Cálculo do primeiro dígito
        int sum = 0;
        for(int i = 0; i < 9; i++) {
            sum += numbers[i] * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if(firstDigit >= 10) firstDigit = 0;

        // Cálculo do segundo dígito
        sum = 0;
        for(int i = 0; i < 10; i++) {
            sum += numbers[i] * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if(secondDigit >= 10) secondDigit = 0;

        return (numbers[9] == firstDigit && numbers[10] == secondDigit) ? null : "Dígitos verificadores do CPF inválidos";
    }

    public String validarDigitosRG(String rg) {
        // Implementação da validação do dígito do RG (exemplo para SP)
        String cleanRg = rg.replaceAll("[^0-9Xx]", "");
        char digito = Character.toUpperCase(cleanRg.charAt(cleanRg.length() - 1));
        
        int[] numbers = cleanRg.substring(0, cleanRg.length() - 1).chars()
                          .map(Character::getNumericValue)
                          .toArray();
        
        int sum = 0;
        for(int i = 0; i < numbers.length; i++) {
            sum += numbers[i] * (2 + i);
        }
        
        int calculated = 11 - (sum % 11);
        char expected = calculated == 10 ? 'X' : (char) (calculated + '0');
        
        return (digito == expected) ? null : "Dígito verificador do RG inválido";
    }
}
