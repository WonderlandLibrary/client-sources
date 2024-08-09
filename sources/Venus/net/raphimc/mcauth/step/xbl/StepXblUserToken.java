/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step.xbl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;
import net.raphimc.mcauth.MinecraftAuth;
import net.raphimc.mcauth.step.AbstractStep;
import net.raphimc.mcauth.step.xbl.XblResponseHandler;
import net.raphimc.mcauth.step.xbl.session.StepInitialXblSession;
import net.raphimc.mcauth.util.CryptUtil;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StepXblUserToken
extends AbstractStep<StepInitialXblSession.InitialXblSession, XblUserToken> {
    public static final String XBL_USER_URL = "https://user.auth.xboxlive.com/user/authenticate";

    public StepXblUserToken(AbstractStep<?, StepInitialXblSession.InitialXblSession> abstractStep) {
        super(abstractStep);
    }

    @Override
    public XblUserToken applyStep(HttpClient httpClient, StepInitialXblSession.InitialXblSession initialXblSession) throws Exception {
        MinecraftAuth.LOGGER.info("Authenticating user with Xbox Live...");
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("AuthMethod", "RPS");
        jsonObject2.addProperty("SiteName", "user.auth.xboxlive.com");
        jsonObject2.addProperty("RpsTicket", (initialXblSession.prevResult().isTitleClientId() ? "t=" : "d=") + initialXblSession.prevResult().access_token());
        if (initialXblSession.prevResult2() != null) {
            jsonObject2.add("ProofKey", CryptUtil.getProofKey(initialXblSession.prevResult2().publicKey()));
        }
        jsonObject.add("Properties", jsonObject2);
        jsonObject.addProperty("RelyingParty", "http://auth.xboxlive.com");
        jsonObject.addProperty("TokenType", "JWT");
        HttpPost httpPost = new HttpPost(XBL_USER_URL);
        httpPost.setEntity(new StringEntity(jsonObject.toString(), ContentType.APPLICATION_JSON));
        httpPost.addHeader("x-xbl-contract-version", "1");
        if (initialXblSession.prevResult2() != null) {
            httpPost.addHeader(CryptUtil.getSignatureHeader(httpPost, initialXblSession.prevResult2().privateKey()));
        }
        String string = httpClient.execute((HttpUriRequest)httpPost, new XblResponseHandler());
        JsonObject jsonObject3 = JsonParser.parseString(string).getAsJsonObject();
        XblUserToken xblUserToken = new XblUserToken(Instant.parse(jsonObject3.get("NotAfter").getAsString()).toEpochMilli(), jsonObject3.get("Token").getAsString(), jsonObject3.getAsJsonObject("DisplayClaims").getAsJsonArray("xui").get(0).getAsJsonObject().get("uhs").getAsString(), initialXblSession);
        MinecraftAuth.LOGGER.info("Got XBL User Token, expires: " + Instant.ofEpochMilli(XblUserToken.access$000(xblUserToken)).atZone(ZoneId.systemDefault()));
        return xblUserToken;
    }

    @Override
    public XblUserToken refresh(HttpClient httpClient, XblUserToken xblUserToken) throws Exception {
        if (xblUserToken == null || xblUserToken.isExpired()) {
            return super.refresh(httpClient, xblUserToken);
        }
        return xblUserToken;
    }

    @Override
    public XblUserToken fromJson(JsonObject jsonObject) throws Exception {
        StepInitialXblSession.InitialXblSession initialXblSession = this.prevStep != null ? (StepInitialXblSession.InitialXblSession)this.prevStep.fromJson(jsonObject.getAsJsonObject("prev")) : null;
        return new XblUserToken(jsonObject.get("expireTimeMs").getAsLong(), jsonObject.get("token").getAsString(), jsonObject.get("userHash").getAsString(), initialXblSession);
    }

    @Override
    public AbstractStep.StepResult fromJson(JsonObject jsonObject) throws Exception {
        return this.fromJson(jsonObject);
    }

    @Override
    public AbstractStep.StepResult refresh(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.refresh(httpClient, (XblUserToken)stepResult);
    }

    @Override
    public AbstractStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, (StepInitialXblSession.InitialXblSession)stepResult);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class XblUserToken
    implements AbstractStep.StepResult<StepInitialXblSession.InitialXblSession> {
        private final long expireTimeMs;
        private final String token;
        private final String userHash;
        private final StepInitialXblSession.InitialXblSession prevResult;

        public XblUserToken(long l, String string, String string2, StepInitialXblSession.InitialXblSession initialXblSession) {
            this.expireTimeMs = l;
            this.token = string;
            this.userHash = string2;
            this.prevResult = initialXblSession;
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("expireTimeMs", this.expireTimeMs);
            jsonObject.addProperty("token", this.token);
            jsonObject.addProperty("userHash", this.userHash);
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

        public String token() {
            return this.token;
        }

        public String userHash() {
            return this.userHash;
        }

        @Override
        public StepInitialXblSession.InitialXblSession prevResult() {
            return this.prevResult;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (object == null || object.getClass() != this.getClass()) {
                return true;
            }
            XblUserToken xblUserToken = (XblUserToken)object;
            return this.expireTimeMs == xblUserToken.expireTimeMs && Objects.equals(this.token, xblUserToken.token) && Objects.equals(this.userHash, xblUserToken.userHash) && Objects.equals(this.prevResult, xblUserToken.prevResult);
        }

        public int hashCode() {
            return Objects.hash(this.expireTimeMs, this.token, this.userHash, this.prevResult);
        }

        public String toString() {
            return "XblUserToken[expireTimeMs=" + this.expireTimeMs + ", token=" + this.token + ", userHash=" + this.userHash + ", prevResult=" + this.prevResult + ']';
        }

        @Override
        public AbstractStep.StepResult prevResult() {
            return this.prevResult();
        }

        static long access$000(XblUserToken xblUserToken) {
            return xblUserToken.expireTimeMs;
        }
    }
}

