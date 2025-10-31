package ma.emsi.elouafi.tp1elouafi.llm;

import jakarta.enterprise.context.Dependent;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;

@Dependent
public class LlmClientPourGemini implements Serializable {
    private final String key;
    private final Client clientRest;
    private final WebTarget target;

    public LlmClientPourGemini() {
        this.key = System.getenv("GEMINI_KEY"); // ⚠️ assure-toi que c'est bien ce nom
        if (this.key == null || this.key.isBlank()) {
            throw new IllegalStateException("La clé API Gemini (GEMINI_KEY) n'est pas définie.");
        }
        this.clientRest = ClientBuilder.newClient();
        this.target = clientRest
                .target("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent")
                .queryParam("key", this.key); // ✅ clé ajoutée dans l'URL
    }

    public Response envoyerRequete(Entity<?> requestEntity) {
        return target.request(MediaType.APPLICATION_JSON_TYPE).post(requestEntity);
    }

    // pratique: on prend directement une chaîne JSON
    public Response envoyerRequete(String bodyJson) {
        return envoyerRequete(Entity.json(bodyJson));
    }

    public void closeClient() {
        this.clientRest.close();
    }
}
