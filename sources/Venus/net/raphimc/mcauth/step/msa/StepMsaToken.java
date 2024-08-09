/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step.msa;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Objects;
import net.raphimc.mcauth.MinecraftAuth;
import net.raphimc.mcauth.step.AbstractStep;
import net.raphimc.mcauth.step.msa.MsaCodeStep;
import net.raphimc.mcauth.step.msa.MsaResponseHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StepMsaToken
extends AbstractStep<MsaCodeStep.MsaCode, MsaToken> {
    public static final String TOKEN_URL = "https://login.live.com/oauth20_token.srf";

    public StepMsaToken(AbstractStep<?, MsaCodeStep.MsaCode> abstractStep) {
        super(abstractStep);
    }

    @Override
    public MsaToken applyStep(HttpClient httpClient, MsaCodeStep.MsaCode msaCode) throws Exception {
        return this.apply(httpClient, msaCode.code(), msaCode.redirectUri() != null ? "authorization_code" : "refresh_token", msaCode);
    }

    @Override
    public MsaToken refresh(HttpClient httpClient, MsaToken msaToken) throws Exception {
        if (msaToken == null) {
            return super.refresh(httpClient, null);
        }
        if (msaToken.isExpired()) {
            return this.apply(httpClient, msaToken.refresh_token(), "refresh_token", msaToken.prevResult());
        }
        return msaToken;
    }

    @Override
    public MsaToken fromJson(JsonObject jsonObject) throws Exception {
        MsaCodeStep.MsaCode msaCode = this.prevStep != null ? (MsaCodeStep.MsaCode)this.prevStep.fromJson(jsonObject.getAsJsonObject("prev")) : null;
        return new MsaToken(jsonObject.get("user_id").getAsString(), jsonObject.get("expireTimeMs").getAsLong(), jsonObject.get("access_token").getAsString(), jsonObject.get("refresh_token").getAsString(), msaCode);
    }

    private MsaToken apply(HttpClient httpClient, String string, String string2, MsaCodeStep.MsaCode msaCode) throws Exception {
        MinecraftAuth.LOGGER.info("Getting MSA Token...");
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("client_id", msaCode.clientId()));
        arrayList.add(new BasicNameValuePair("scope", msaCode.scope()));
        arrayList.add(new BasicNameValuePair("grant_type", string2));
        if (string2.equals("refresh_token")) {
            arrayList.add(new BasicNameValuePair("refresh_token", string));
        } else {
            arrayList.add(new BasicNameValuePair("code", string));
            arrayList.add(new BasicNameValuePair("redirect_uri", msaCode.redirectUri()));
        }
        if (msaCode.clientSecret() != null) {
            arrayList.add(new BasicNameValuePair("client_secret", msaCode.clientSecret()));
        }
        HttpPost httpPost = new HttpPost(TOKEN_URL);
        httpPost.setEntity(new UrlEncodedFormEntity(arrayList, StandardCharsets.UTF_8));
        String string3 = httpClient.execute((HttpUriRequest)httpPost, new MsaResponseHandler());
        JsonObject jsonObject = JsonParser.parseString(string3).getAsJsonObject();
        MsaToken msaToken = new MsaToken(jsonObject.get("user_id").getAsString(), System.currentTimeMillis() + jsonObject.get("expires_in").getAsLong() * 1000L, jsonObject.get("access_token").getAsString(), jsonObject.get("refresh_token").getAsString(), msaCode);
        MinecraftAuth.LOGGER.info("Got MSA Token, expires: " + Instant.ofEpochMilli(MsaToken.access$000(msaToken)).atZone(ZoneId.systemDefault()));
        return msaToken;
    }

    @Override
    public AbstractStep.StepResult fromJson(JsonObject jsonObject) throws Exception {
        return this.fromJson(jsonObject);
    }

    @Override
    public AbstractStep.StepResult refresh(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.refresh(httpClient, (MsaToken)stepResult);
    }

    @Override
    public AbstractStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, (MsaCodeStep.MsaCode)stepResult);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class MsaToken
    implements AbstractStep.StepResult<MsaCodeStep.MsaCode> {
        private final String user_id;
        private final long expireTimeMs;
        private final String access_token;
        private final String refresh_token;
        private final MsaCodeStep.MsaCode prevResult;

        public MsaToken(String string, long l, String string2, String string3, MsaCodeStep.MsaCode msaCode) {
            this.user_id = string;
            this.expireTimeMs = l;
            this.access_token = string2;
            this.refresh_token = string3;
            this.prevResult = msaCode;
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("user_id", this.user_id);
            jsonObject.addProperty("expireTimeMs", this.expireTimeMs);
            jsonObject.addProperty("access_token", this.access_token);
            jsonObject.addProperty("refresh_token", this.refresh_token);
            if (this.prevResult != null) {
                jsonObject.add("prev", this.prevResult.toJson());
            }
            return jsonObject;
        }

        @Override
        public boolean isExpired() {
            return this.expireTimeMs <= System.currentTimeMillis();
        }

        public boolean isTitleClientId() {
            return !this.prevResult.clientId().matches("\\p{XDigit}{8}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}+");
        }

        public String user_id() {
            return this.user_id;
        }

        public long expireTimeMs() {
            return this.expireTimeMs;
        }

        public String access_token() {
            return this.access_token;
        }

        public String refresh_token() {
            return this.refresh_token;
        }

        @Override
        public MsaCodeStep.MsaCode prevResult() {
            return this.prevResult;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (object == null || object.getClass() != this.getClass()) {
                return true;
            }
            MsaToken msaToken = (MsaToken)object;
            return Objects.equals(this.user_id, msaToken.user_id) && this.expireTimeMs == msaToken.expireTimeMs && Objects.equals(this.access_token, msaToken.access_token) && Objects.equals(this.refresh_token, msaToken.refresh_token) && Objects.equals(this.prevResult, msaToken.prevResult);
        }

        public int hashCode() {
            return Objects.hash(this.user_id, this.expireTimeMs, this.access_token, this.refresh_token, this.prevResult);
        }

        public String toString() {
            return "MsaToken[user_id=" + this.user_id + ", expireTimeMs=" + this.expireTimeMs + ", access_token=" + this.access_token + ", refresh_token=" + this.refresh_token + ", prevResult=" + this.prevResult + ']';
        }

        @Override
        public AbstractStep.StepResult prevResult() {
            return this.prevResult();
        }

        static long access$000(MsaToken msaToken) {
            return msaToken.expireTimeMs;
        }
    }
}

