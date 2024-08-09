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
public class StepXblTitleToken
extends AbstractStep<StepInitialXblSession.InitialXblSession, XblTitleToken> {
    public static final String XBL_TITLE_URL = "https://title.auth.xboxlive.com/title/authenticate";

    public StepXblTitleToken(AbstractStep<?, StepInitialXblSession.InitialXblSession> abstractStep) {
        super(abstractStep);
    }

    @Override
    public XblTitleToken applyStep(HttpClient httpClient, StepInitialXblSession.InitialXblSession initialXblSession) throws Exception {
        MinecraftAuth.LOGGER.info("Authenticating title with Xbox Live...");
        if (initialXblSession.prevResult2() == null) {
            throw new IllegalStateException("A XBL Device Token is needed for Title authentication");
        }
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("AuthMethod", "RPS");
        jsonObject2.addProperty("SiteName", "user.auth.xboxlive.com");
        jsonObject2.addProperty("RpsTicket", "t=" + initialXblSession.prevResult().access_token());
        jsonObject2.addProperty("DeviceToken", initialXblSession.prevResult2().token());
        jsonObject2.add("ProofKey", CryptUtil.getProofKey(initialXblSession.prevResult2().publicKey()));
        jsonObject.add("Properties", jsonObject2);
        jsonObject.addProperty("RelyingParty", "http://auth.xboxlive.com");
        jsonObject.addProperty("TokenType", "JWT");
        HttpPost httpPost = new HttpPost(XBL_TITLE_URL);
        httpPost.setEntity(new StringEntity(jsonObject.toString(), ContentType.APPLICATION_JSON));
        httpPost.addHeader("x-xbl-contract-version", "1");
        httpPost.addHeader(CryptUtil.getSignatureHeader(httpPost, initialXblSession.prevResult2().privateKey()));
        String string = httpClient.execute((HttpUriRequest)httpPost, new XblResponseHandler());
        JsonObject jsonObject3 = JsonParser.parseString(string).getAsJsonObject();
        XblTitleToken xblTitleToken = new XblTitleToken(Instant.parse(jsonObject3.get("NotAfter").getAsString()).toEpochMilli(), jsonObject3.get("Token").getAsString(), jsonObject3.getAsJsonObject("DisplayClaims").getAsJsonObject("xti").get("tid").getAsString(), initialXblSession);
        MinecraftAuth.LOGGER.info("Got XBL Title Token, expires: " + Instant.ofEpochMilli(XblTitleToken.access$000(xblTitleToken)).atZone(ZoneId.systemDefault()));
        return xblTitleToken;
    }

    @Override
    public XblTitleToken refresh(HttpClient httpClient, XblTitleToken xblTitleToken) throws Exception {
        if (xblTitleToken == null || xblTitleToken.isExpired()) {
            return super.refresh(httpClient, xblTitleToken);
        }
        return xblTitleToken;
    }

    @Override
    public XblTitleToken fromJson(JsonObject jsonObject) throws Exception {
        StepInitialXblSession.InitialXblSession initialXblSession = this.prevStep != null ? (StepInitialXblSession.InitialXblSession)this.prevStep.fromJson(jsonObject.getAsJsonObject("prev")) : null;
        return new XblTitleToken(jsonObject.get("expireTimeMs").getAsLong(), jsonObject.get("token").getAsString(), jsonObject.get("userHash").getAsString(), initialXblSession);
    }

    @Override
    public AbstractStep.StepResult fromJson(JsonObject jsonObject) throws Exception {
        return this.fromJson(jsonObject);
    }

    @Override
    public AbstractStep.StepResult refresh(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.refresh(httpClient, (XblTitleToken)stepResult);
    }

    @Override
    public AbstractStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, (StepInitialXblSession.InitialXblSession)stepResult);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class XblTitleToken
    implements AbstractStep.StepResult<StepInitialXblSession.InitialXblSession> {
        private final long expireTimeMs;
        private final String token;
        private final String titleId;
        private final StepInitialXblSession.InitialXblSession prevResult;

        public XblTitleToken(long l, String string, String string2, StepInitialXblSession.InitialXblSession initialXblSession) {
            this.expireTimeMs = l;
            this.token = string;
            this.titleId = string2;
            this.prevResult = initialXblSession;
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("expireTimeMs", this.expireTimeMs);
            jsonObject.addProperty("token", this.token);
            jsonObject.addProperty("titleId", this.titleId);
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

        public String titleId() {
            return this.titleId;
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
            XblTitleToken xblTitleToken = (XblTitleToken)object;
            return this.expireTimeMs == xblTitleToken.expireTimeMs && Objects.equals(this.token, xblTitleToken.token) && Objects.equals(this.titleId, xblTitleToken.titleId) && Objects.equals(this.prevResult, xblTitleToken.prevResult);
        }

        public int hashCode() {
            return Objects.hash(this.expireTimeMs, this.token, this.titleId, this.prevResult);
        }

        public String toString() {
            return "XblTitleToken[expireTimeMs=" + this.expireTimeMs + ", token=" + this.token + ", titleId=" + this.titleId + ", prevResult=" + this.prevResult + ']';
        }

        @Override
        public AbstractStep.StepResult prevResult() {
            return this.prevResult();
        }

        static long access$000(XblTitleToken xblTitleToken) {
            return xblTitleToken.expireTimeMs;
        }
    }
}

