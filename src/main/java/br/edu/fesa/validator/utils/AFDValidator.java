package br.edu.fesa.validator.utils;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * AFDValidator is responsible for validating the format of documents (CPF and RG) using a
 * deterministic finite automaton.
 */
@Component
public class AFDValidator {

  /** Represents the states of the automaton. */
  public enum State {
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
    Q26,
    Q27,
    Q28,
    Q29,
    Q30
  }

  // Maps each state to its transitions based on the input character
  private final Map<State, Map<Character, State>> transitions = new HashMap<>();

  public AFDValidator() {
    configureTransitions();
  }

  /** Configures the state transitions for the automaton. */
  private void configureTransitions() {
    // Main transitions for numbers (0-9)
    transitions.put(State.Q0, createTransition(State.Q1, null, null, null));
    transitions.put(State.Q1, createTransition(State.Q2, null, null, null));
    transitions.put(State.Q2, createTransition(State.Q3, null, State.Q12, null));
    transitions.put(State.Q3, createTransition(State.Q4, null, State.Q22, null));
    transitions.put(State.Q4, createTransition(State.Q5, null, null, null));
    transitions.put(State.Q5, createTransition(State.Q6, null, State.Q20, null));
    transitions.put(State.Q6, createTransition(State.Q7, null, State.Q29, null));
    transitions.put(State.Q7, createTransition(State.Q8, null, null, null));
    transitions.put(State.Q8, createTransition(State.Q9, State.Q19, null, State.Q21));
    transitions.put(State.Q9, createTransition(State.Q10, null, null, State.Q30)); // Final state
    transitions.put(State.Q10, createTransition(State.Q11, null, null, null));
    transitions.put(State.Q11, createTransition(null, null, null, null)); // Final state

    // Punctuation handling (RG path)
    transitions.put(State.Q12, createTransition(State.Q13, null, null, null));
    transitions.put(State.Q13, createTransition(State.Q14, null, null, null));
    transitions.put(State.Q14, createTransition(State.Q15, null, null, null));
    transitions.put(State.Q15, createTransition(State.Q16, null, State.Q20, null));
    transitions.put(State.Q16, createTransition(State.Q17, null, null, null));
    transitions.put(State.Q17, createTransition(State.Q18, null, null, null));
    transitions.put(State.Q18, createTransition(State.Q19, State.Q19, null, State.Q21));
    transitions.put(State.Q19, createTransition(null, null, null, null)); // Final state
    transitions.put(State.Q20, createTransition(State.Q16, null, null, null));
    transitions.put(State.Q21, createTransition(State.Q19, State.Q19, null, null));

    // Punctuation handling (CPF path)
    transitions.put(State.Q22, createTransition(State.Q23, null, null, null));
    transitions.put(State.Q23, createTransition(State.Q24, null, null, null));
    transitions.put(State.Q24, createTransition(State.Q25, null, null, null));
    transitions.put(State.Q25, createTransition(State.Q26, null, State.Q29, null));
    transitions.put(State.Q26, createTransition(State.Q27, null, null, null));
    transitions.put(State.Q27, createTransition(State.Q28, null, null, null));
    transitions.put(State.Q28, createTransition(State.Q10, null, null, State.Q30));
    transitions.put(State.Q29, createTransition(State.Q26, null, null, null));
    transitions.put(State.Q30, createTransition(State.Q10, null, null, null));
  }

  /**
   * Creates a transition mapping for a given set of input characters.
   *
   * @param digit the state to transition to when a digit (0-9) is encountered
   * @param x the state to transition to when 'x' or 'X' is encountered
   * @param dot the state to transition to when a dot is encountered
   * @param hyphen the state to transition to when a hyphen is encountered
   * @return a map representing the transitions from a given state
   */
  private Map<Character, State> createTransition(State digit, State x, State dot, State hyphen) {
    Map<Character, State> map = new HashMap<>();

    // Digits 0-9
    if (digit != null) {
      for (char c = '0'; c <= '9'; c++) {
        map.put(c, digit);
      }
    }

    // Letter 'X' (both lowercase and uppercase)
    if (x != null) {
      map.put('x', x);
      map.put('X', x);
    }

    // Dot punctuation
    if (dot != null) {
      map.put('.', dot);
    }

    // Hyphen punctuation
    if (hyphen != null) {
      map.put('-', hyphen);
    }

    return map;
  }

  /**
   * Validates the format of a document using the deterministic finite automaton.
   *
   * @param document the document string to validate
   * @return a message in Portuguese indicating the result of the validation
   */
  public String validateDocument(String document) {
    State currentState = State.Q0;

    for (char c : document.toCharArray()) {
      c = Character.toLowerCase(c);
      Map<Character, State> stateTransitions = transitions.get(currentState);

      if (stateTransitions == null || !stateTransitions.containsKey(c)) {
        return "Formato inválido: caractere '" + c + "' não permitido";
      }

      currentState = stateTransitions.get(c);
    }

    // Check for final states
    if (currentState == State.Q11) return "CPF válido";
    if (currentState == State.Q9 || currentState == State.Q19) return "RG válido";

    return "Formato inválido: documento incompleto ou formato incorreto";
  }
}
