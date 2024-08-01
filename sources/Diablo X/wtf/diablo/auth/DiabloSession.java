package wtf.diablo.auth;

import com.google.gson.JsonObject;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import wtf.diablo.client.util.Constants;

public final class DiabloSession {
    private final String token;

    //TODO: When adding user endpoint logic, make these final. no need for them not to be final
    private String username;
    private int uid;
    private DiabloRank rank;

    private String hwid;

    public DiabloSession(final String token) {
        this.token = token;
    }

    public void update() {
        final HttpResponse<String> response = Unirest.get(DiabloAPI.USER_ENDPOINT)
                .header("Authorization", "Bearer " + token)
                .asString();


        System.out.println(response.getBody());
        if (response.getStatus() == 200) {
            final JsonObject json = Constants.GSON.fromJson(response.getBody(), JsonObject.class);
            this.username = json.get("username").getAsString();
            this.uid = json.get("uid").getAsInt();
            this.rank = DiabloRank.valueOf(json.get("rank").getAsString());
            this.hwid = json.has("hwid") ? json.get("hwid").getAsString() : "";
        } else {
            throw new RuntimeException("Failed to update session");
        }
    }
    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public int getUid() {
        return uid;
    }

    public DiabloRank getRank() {
        return rank;
    }

    public String getHwid() {
        return hwid;
    }
}
