package br.edu.fesa.validator.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 081220023 &
 */
public class AFDValidator {

  public enum Estado {
    Q0,
    q1,
    q2,
    q3,
    q4,
    q5,
    q6,
    q7,
    q8,
    Q9,
    q10,
    Q11,
    q12,
    q13,
    q14,
    q15,
    q16,
    q17,
    q18,
    Q19
  }

  private final Map<Estado, Map<Character, Estado>> transicoes = new HashMap<>();

  public AFDValidator() {
    configurarTransicoes();
  }

  private void configurarTransicoes() {
    // Transições principais
    transicoes.put(Estado.Q0, criarTransicaoNumeros(Estado.q1));

    transicoes.put(Estado.q1, criarTransicaoNumeros(Estado.q2));

    transicoes.put(Estado.q2, criarTransicaoComPontuacao(Estado.q3, Estado.q15));

    transicoes.put(Estado.q3, criarTransicaoComPontuacao(Estado.q4, Estado.q12));

    transicoes.put(Estado.q4, criarTransicaoNumeros(Estado.q5));
    transicoes.put(Estado.q5, criarTransicaoNumeros(Estado.q6));

    transicoes.put(Estado.q6, criarTransicaoComPontuacao(Estado.q7, Estado.q13));

    transicoes.put(Estado.q7, criarTransicaoNumeros(Estado.q8));

    transicoes.put(Estado.q8, criarTransicaoFinalRG(Estado.Q9, Estado.Q19, Estado.q18));

    // Transições para pontuações
    transicoes.put(Estado.q12, criarTransicaoNumeros(Estado.q4));
    transicoes.put(Estado.q13, criarTransicaoNumeros(Estado.q7));
    transicoes.put(Estado.q15, criarTransicaoNumeros(Estado.q16));
    transicoes.put(Estado.q16, criarTransicaoNumeros(Estado.q17));
    transicoes.put(Estado.q17, criarTransicaoNumeros(Estado.q6));

    // Transição para hífen
    transicoes.put(Estado.q18, criarTransicaoFinalRG(Estado.Q9, Estado.Q19, null));

    // Estado final Q9 (RG)
    transicoes.put(Estado.Q9, criarTransicaoFinalCPF(Estado.q10, Estado.q14));

    transicoes.put(Estado.q10, criarTransicaoNumeros(Estado.Q11));
    transicoes.put(Estado.q14, criarTransicaoNumeros(Estado.q10));
  }

  private Map<Character, Estado> criarTransicaoNumeros(Estado proximo) {
    Map<Character, Estado> mapa = new HashMap<>();
    for (char c = '0'; c <= '9'; c++) {
      mapa.put(c, proximo);
    }
    return mapa;
  }

  private Map<Character, Estado> criarTransicaoComPontuacao(
      Estado proxNumero, Estado proxPontuacao) {
    Map<Character, Estado> mapa = criarTransicaoNumeros(proxNumero);
    mapa.put('.', proxPontuacao);
    return mapa;
  }

  private Map<Character, Estado> criarTransicaoFinalRG(
      Estado proxNumero, Estado proxX, Estado proxHifen) {
    Map<Character, Estado> mapa = criarTransicaoNumeros(proxNumero);
    mapa.put('x', proxX);
    mapa.put('X', proxX); // Aceita maiúsculo e minúsculo
    if (proxHifen != null) mapa.put('-', proxHifen);
    return mapa;
  }

  private Map<Character, Estado> criarTransicaoFinalCPF(Estado proxNumero, Estado proxHifen) {
    Map<Character, Estado> mapa = criarTransicaoNumeros(proxNumero);
    mapa.put('-', proxHifen);
    return mapa;
  }

  public String validarDocumento(String documento) {
    Estado estadoAtual = Estado.Q0;

    for (char c : documento.toCharArray()) {
      c = Character.toLowerCase(c); // Normaliza caracteres
      Map<Character, Estado> transicoesEstado = transicoes.get(estadoAtual);

      if (transicoesEstado == null || !transicoesEstado.containsKey(c)) {
        return "Formato inválido: caractere '" + c + "' não permitido";
      }

      estadoAtual = transicoesEstado.get(c);
    }

    // Verifica estados finais
    if (estadoAtual == Estado.Q11) return "CPF válido";
    if (estadoAtual == Estado.Q9 || estadoAtual == Estado.Q19) return "RG válido";

    return "Formato inválido: documento incompleto ou formato incorreto";
  }
}
