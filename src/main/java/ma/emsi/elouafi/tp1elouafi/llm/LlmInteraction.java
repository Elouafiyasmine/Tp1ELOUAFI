package ma.emsi.elouafi.tp1elouafi.llm;

import java.io.Serializable;

/**
 * Représente une interaction complète avec le LLM :
 * - le JSON de la requête envoyée
 * - le JSON de la réponse reçue
 * - la réponse texte extraite pour affichage
 */
public class LlmInteraction implements Serializable {

    private final String questionJson;   // JSON envoyé au LLM
    private final String reponseJson;    // JSON reçu du LLM
    private final String reponseExtraite; // Réponse texte affichable

    /**
     * Constructeur principal.
     * @param questionJson JSON de la requête envoyée
     * @param reponseJson JSON de la réponse reçue
     * @param reponseExtraite Réponse texte extraite du JSON
     */
    public LlmInteraction(String questionJson, String reponseJson, String reponseExtraite) {
        this.questionJson = questionJson;
        this.reponseJson = reponseJson;
        this.reponseExtraite = reponseExtraite;
    }

    /** JSON complet envoyé au LLM */
    public String questionJson() {
        return questionJson;
    }

    /** JSON complet reçu du LLM */
    public String reponseJson() {
        return reponseJson;
    }

    /** Réponse texte extraite du JSON */
    public String reponseExtraite() {
        return reponseExtraite;
    }
}
