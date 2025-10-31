package ma.emsi.elouafi.tp1elouafi.llm;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.json.*;

import java.io.StringReader;

@ApplicationScoped
public class JsonUtil {

    private String roleSysteme;

    @Inject
    private LlmClientPourGemini client;

    public void setRoleSysteme(String roleSysteme) {
        this.roleSysteme = roleSysteme;
    }

    public LlmInteraction envoyerRequete(String question) throws RequeteException {
        try {
            String requeteJson = buildBody(roleSysteme, question);

            Response resp = client.envoyerRequete(requeteJson);
            int status = resp.getStatus();
            String reponseJson = resp.readEntity(String.class);
            resp.close();

            if (status != 200) {
                throw new RequeteException("HTTP " + status + " : " + reponseJson);
            }

            String reponseExtraite = extractText(reponseJson);
            return new LlmInteraction(requeteJson, reponseJson, reponseExtraite);

        } catch (Exception e) {
            throw new RequeteException("Appel Gemini échoué : " + e.getMessage(), e);
        }
    }

    private static String buildBody(String role, String question) {
        String r = (role == null) ? "" : role;
        String q = (question == null) ? "" : question;
        r = r.replace("\"", "\\\"");
        q = q.replace("\"", "\\\"");
        // Format attendu par l’API Gemini
        return "{ \"contents\": [ { \"parts\": [ " +
                "{ \"text\": \"" + r + "\" }, " +
                "{ \"text\": \"" + q + "\" } ] } ] }";
    }

    private static String extractText(String json) {
        try (JsonReader reader = Json.createReader(new StringReader(json))) {
            JsonObject root = reader.readObject();
            JsonArray candidates = root.getJsonArray("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                JsonObject content = candidates.getJsonObject(0).getJsonObject("content");
                if (content != null) {
                    JsonArray parts = content.getJsonArray("parts");
                    if (parts != null && !parts.isEmpty()) {
                        return parts.getJsonObject(0).getString("text", json);
                    }
                }
            }
        } catch (Exception ignore) { }
        return json; // fallback si structure inattendue
    }
}
