package br.edu.fesa.validator.utils;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AFDValidator {

  public enum Estado {
    Q0,
    Q1,
    Q2,
    Q3,
    Q4,
    Q5,
    Q6,
    Q7,
    Q8,
    Q9,
    Q10,
    Q11,
    Q12,
    Q13,
    Q14,
    Q15,
    Q16,
    Q17,
    Q18,
    Q19,
    Q20,
    Q21,
    Q22,
    Q23,
    Q24,
    Q25,
    Q26
  }

  private final Map<Estado, Map<Character, Estado>> transicoes = new HashMap<>();

  public AFDValidator() {
    configurarTransicoes();
  }

  private void configurarTransicoes() {
    // Transições principais para CPF e RG
    transicoes.put(Estado.Q0, criarTransicao(Estado.Q1, null, null, null));

    transicoes.put(Estado.Q1, criarTransicao(Estado.Q2, null, null, null));

    transicoes.put(Estado.Q2, criarTransicao(Estado.Q3, null, Estado.Q12, null));

    transicoes.put(Estado.Q3, criarTransicao(Estado.Q4, null, Estado.Q20, null));

    transicoes.put(Estado.Q4, criarTransicao(Estado.Q5, null, null, null));
    transicoes.put(Estado.Q5, criarTransicao(Estado.Q6, null, Estado.Q17, null));

    transicoes.put(Estado.Q6, criarTransicao(Estado.Q7, null, Estado.Q25, null));

    transicoes.put(Estado.Q7, criarTransicao(Estado.Q8, null, null, null));

    transicoes.put(Estado.Q8, criarTransicao(Estado.Q9, Estado.Q19, null, Estado.Q18));

    // Transições para pontuações
    transicoes.put(Estado.Q12, criarTransicao(Estado.Q13, null, null, null));
    transicoes.put(Estado.Q13, criarTransicao(Estado.Q14, null, null, null));
    transicoes.put(Estado.Q14, criarTransicao(Estado.Q15, null, null, null));
    transicoes.put(Estado.Q15, criarTransicao(Estado.Q16, null, Estado.Q17, null));
    transicoes.put(Estado.Q16, criarTransicao(Estado.Q7, null, null, null));
    transicoes.put(Estado.Q17, criarTransicao(Estado.Q16, null, null, null));

    // Transições para RG
    transicoes.put(Estado.Q18, criarTransicao(Estado.Q19, Estado.Q19, null, null));

    // Transições para CPF
    transicoes.put(Estado.Q20, criarTransicao(Estado.Q21, null, null, null));
    transicoes.put(Estado.Q21, criarTransicao(Estado.Q22, null, null, null));
    transicoes.put(Estado.Q22, criarTransicao(Estado.Q23, null, null, null));
    transicoes.put(Estado.Q23, criarTransicao(Estado.Q24, null, Estado.Q25, null));
    transicoes.put(Estado.Q24, criarTransicao(Estado.Q8, null, null, null));
    transicoes.put(Estado.Q25, criarTransicao(Estado.Q24, null, null, null));

    // Estado final CPF
    transicoes.put(Estado.Q9, criarTransicao(Estado.Q10, null, null, Estado.Q26));
    transicoes.put(Estado.Q26, criarTransicao(Estado.Q10, null, null, null));
    transicoes.put(Estado.Q10, criarTransicao(Estado.Q11, null, null, null));
  }

  private Map<Character, Estado> criarTransicao(
      Estado digito, Estado x, Estado ponto, Estado hifen) {
    Map<Character, Estado> mapa = new HashMap<>();

    // Dígitos 0-9
    if (digito != null) {
      for (char c = '0'; c <= '9'; c++) {
        mapa.put(c, digito);
      }
    }

    // Letra X (maiúscula/minúscula)
    if (x != null) {
      mapa.put('x', x);
      mapa.put('X', x);
    }

    // Pontuação
    if (ponto != null) {
      mapa.put('.', ponto);
    }

    // Hífen
    if (hifen != null) {
      mapa.put('-', hifen);
    }

    return mapa;
  }

  public String validarDocumento(String documento) {
    Estado estadoAtual = Estado.Q0;

    for (char c : documento.toCharArray()) {
      c = Character.toLowerCase(c);
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
