/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step.bedrock;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;
import net.raphimc.mcauth.MinecraftAuth;
import net.raphimc.mcauth.step.AbstractStep;
import net.raphimc.mcauth.step.bedrock.PlayFabResponseHandler;
import net.raphimc.mcauth.step.xbl.StepXblXstsToken;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StepPlayFabToken
extends AbstractStep<StepXblXstsToken.XblXsts<?>, PlayFabToken> {
    public static final String PLAY_FAB_URL = "https://" + "20CA2".toLowerCase() + ".playfabapi.com/Client/LoginWithXbox";

    public StepPlayFabToken(AbstractStep<?, StepXblXstsToken.XblXsts<?>> abstractStep) {
        super(abstractStep);
    }

    @Override
    public PlayFabToken applyStep(HttpClient httpClient, StepXblXstsToken.XblXsts<?> xblXsts) throws Exception {
        MinecraftAuth.LOGGER.info("Authenticating with PlayFab...");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("CreateAccount", true);
        jsonObject.add("EncryptedRequest", null);
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("GetCharacterInventories", false);
        jsonObject2.addProperty("GetCharacterList", false);
        jsonObject2.addProperty("GetPlayerProfile", true);
        jsonObject2.addProperty("GetPlayerStatistics", false);
        jsonObject2.addProperty("GetTitleData", false);
        jsonObject2.addProperty("GetUserAccountInfo", true);
        jsonObject2.addProperty("GetUserData", false);
        jsonObject2.addProperty("GetUserInventory", false);
        jsonObject2.addProperty("GetUserReadOnlyData", false);
        jsonObject2.addProperty("GetUserVirtualCurrency", false);
        jsonObject2.add("PlayerStatisticNames", null);
        jsonObject2.add("ProfileConstraints", null);
        jsonObject2.add("TitleDataKeys", null);
        jsonObject2.add("UserDataKeys", null);
        jsonObject2.add("UserReadOnlyDataKeys", null);
        jsonObject.add("InfoRequestParameters", jsonObject2);
        jsonObject.add("PlayerSecret", null);
        jsonObject.addProperty("TitleId", "20CA2");
        jsonObject.addProperty("XboxToken", "XBL3.0 x=" + xblXsts.userHash() + ";" + xblXsts.token());
        HttpPost httpPost = new HttpPost(PLAY_FAB_URL);
        httpPost.setEntity(new StringEntity(jsonObject.toString(), ContentType.APPLICATION_JSON));
        String string = httpClient.execute((HttpUriRequest)httpPost, new PlayFabResponseHandler());
        JsonObject jsonObject3 = JsonParser.parseString(string).getAsJsonObject();
        JsonObject jsonObject4 = jsonObject3.getAsJsonObject("data");
        JsonObject jsonObject5 = jsonObject4.getAsJsonObject("EntityToken");
        PlayFabToken playFabToken = new PlayFabToken(Instant.parse(jsonObject5.get("TokenExpiration").getAsString()).toEpochMilli(), jsonObject5.get("EntityToken").getAsString(), jsonObject5.get("Entity").getAsJsonObject().get("Id").getAsString(), jsonObject4.get("SessionTicket").getAsString(), jsonObject4.get("PlayFabId").getAsString(), xblXsts);
        MinecraftAuth.LOGGER.info("Got PlayFab Token, expires: " + Instant.ofEpochMilli(PlayFabToken.access$000(playFabToken)).atZone(ZoneId.systemDefault()));
        return playFabToken;
    }

    @Override
    public PlayFabToken refresh(HttpClient httpClient, PlayFabToken playFabToken) throws Exception {
        if (playFabToken == null || playFabToken.isExpired()) {
            return super.refresh(httpClient, playFabToken);
        }
        return playFabToken;
    }

    @Override
    public PlayFabToken fromJson(JsonObject jsonObject) throws Exception {
        StepXblXstsToken.XblXsts xblXsts = this.prevStep != null ? (StepXblXstsToken.XblXsts)this.prevStep.fromJson(jsonObject.getAsJsonObject("prev")) : null;
        return new PlayFabToken(jsonObject.get("expireTimeMs").getAsLong(), jsonObject.get("entityToken").getAsString(), jsonObject.get("entityId").getAsString(), jsonObject.get("sessionTicket").getAsString(), jsonObject.get("playFabId").getAsString(), xblXsts);
    }

    @Override
    public AbstractStep.StepResult fromJson(JsonObject jsonObject) throws Exception {
        return this.fromJson(jsonObject);
    }

    @Override
    public AbstractStep.StepResult refresh(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.refresh(httpClient, (PlayFabToken)stepResult);
    }

    @Override
    public AbstractStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, (StepXblXstsToken.XblXsts)stepResult);
    }

    public static final class PlayFabToken
    implements AbstractStep.StepResult<StepXblXstsToken.XblXsts<?>> {
        private final long expireTimeMs;
        private final String entityToken;
        private final String entityId;
        private final String sessionTicket;
        private final String playFabId;
        private final StepXblXstsToken.XblXsts<?> prevResult;

        public PlayFabToken(long l, String string, String string2, String string3, String string4, StepXblXstsToken.XblXsts<?> xblXsts) {
            this.expireTimeMs = l;
            this.entityToken = string;
            this.entityId = string2;
            this.sessionTicket = string3;
            this.playFabId = string4;
            this.prevResult = xblXsts;
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("expireTimeMs", this.expireTimeMs);
            jsonObject.addProperty("entityToken", this.entityToken);
            jsonObject.addProperty("entityId", this.entityId);
            jsonObject.addProperty("sessionTicket", this.sessionTicket);
            jsonObject.addProperty("playFabId", this.playFabId);
            if (this.prevResult != null) {
                jsonObject.add("prev", this.prevResult.toJson());
            }
            return jsonObject;
        }

        @Override
        public boolean isExpired() {
            return this.expireTimeMs <= System.currentTimeMillis();
        }

        public long expireTimeMs() {
            return this.expireTimeMs;
        }

        public String entityToken() {
            return this.entityToken;
        }

        public String entityId() {
            return this.entityId;
        }

        public String sessionTicket() {
            return this.sessionTicket;
        }

        public String playFabId() {
            return this.playFabId;
        }

        @Override
        public StepXblXstsToken.XblXsts<?> prevResult() {
            return this.prevResult;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            PlayFabToken playFabToken = (PlayFabToken)object;
            return this.expireTimeMs == playFabToken.expireTimeMs && Objects.equals(this.entityToken, playFabToken.entityToken) && Objects.equals(this.entityId, playFabToken.entityId) && Objects.equals(this.sessionTicket, playFabToken.sessionTicket) && Objects.equals(this.playFabId, playFabToken.playFabId) && Objects.equals(this.prevResult, playFabToken.prevResult);
        }

        public int hashCode() {
            return Objects.hash(this.expireTimeMs, this.entityToken, this.entityId, this.sessionTicket, this.playFabId, this.prevResult);
        }

        public String toString() {
            return "PlayFabToken{expireTimeMs=" + this.expireTimeMs + ", entityToken='" + this.entityToken + '\'' + ", entityId='" + this.entityId + '\'' + ", sessionTicket='" + this.sessionTicket + '\'' + ", playFabId='" + this.playFabId + '\'' + ", prevResult=" + this.prevResult + '}';
        }

        @Override
        public AbstractStep.StepResult prevResult() {
            return this.prevResult();
        }

        static long access$000(PlayFabToken playFabToken) {
            return playFabToken.expireTimeMs;
        }
    }
}

