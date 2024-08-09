/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step.xbl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;
import net.raphimc.mcauth.MinecraftAuth;
import net.raphimc.mcauth.step.AbstractStep;
import net.raphimc.mcauth.step.xbl.XblResponseHandler;
import net.raphimc.mcauth.step.xbl.session.StepFullXblSession;
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
public class StepXblXstsToken
extends AbstractStep<StepFullXblSession.FullXblSession, XblXsts<?>> {
    public static final String XBL_XSTS_URL = "https://xsts.auth.xboxlive.com/xsts/authorize";
    private final String relyingParty;

    public StepXblXstsToken(AbstractStep<?, StepFullXblSession.FullXblSession> abstractStep, String string) {
        super(abstractStep);
        this.relyingParty = string;
    }

    @Override
    public XblXstsToken applyStep(HttpClient httpClient, StepFullXblSession.FullXblSession fullXblSession) throws Exception {
        MinecraftAuth.LOGGER.info("Requesting XSTS Token...");
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("SandboxId", "RETAIL");
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(new JsonPrimitive(fullXblSession.prevResult().token()));
        jsonObject2.add("UserTokens", jsonArray);
        if (fullXblSession.prevResult2() != null) {
            jsonObject2.addProperty("TitleToken", fullXblSession.prevResult2().token());
            jsonObject2.addProperty("DeviceToken", fullXblSession.prevResult2().prevResult().prevResult2().token());
        }
        jsonObject.add("Properties", jsonObject2);
        jsonObject.addProperty("RelyingParty", this.relyingParty);
        jsonObject.addProperty("TokenType", "JWT");
        HttpPost httpPost = new HttpPost(XBL_XSTS_URL);
        httpPost.setEntity(new StringEntity(jsonObject.toString(), ContentType.APPLICATION_JSON));
        httpPost.addHeader("x-xbl-contract-version", "1");
        if (fullXblSession.prevResult2() != null) {
            httpPost.addHeader(CryptUtil.getSignatureHeader(httpPost, fullXblSession.prevResult2().prevResult().prevResult2().privateKey()));
        }
        String string = httpClient.execute((HttpUriRequest)httpPost, new XblResponseHandler());
        JsonObject jsonObject3 = JsonParser.parseString(string).getAsJsonObject();
        XblXstsToken xblXstsToken = new XblXstsToken(Instant.parse(jsonObject3.get("NotAfter").getAsString()).toEpochMilli(), jsonObject3.get("Token").getAsString(), jsonObject3.getAsJsonObject("DisplayClaims").getAsJsonArray("xui").get(0).getAsJsonObject().get("uhs").getAsString(), fullXblSession);
        MinecraftAuth.LOGGER.info("Got XSTS Token, expires: " + Instant.ofEpochMilli(XblXstsToken.access$000(xblXstsToken)).atZone(ZoneId.systemDefault()));
        return xblXstsToken;
    }

    @Override
    public XblXsts<?> refresh(HttpClient httpClient, XblXsts<?> xblXsts) throws Exception {
        if (xblXsts == null || xblXsts.isExpired()) {
            return super.refresh(httpClient, xblXsts);
        }
        return xblXsts;
    }

    @Override
    public XblXstsToken fromJson(JsonObject jsonObject) throws Exception {
        StepFullXblSession.FullXblSession fullXblSession = this.prevStep != null ? (StepFullXblSession.FullXblSession)this.prevStep.fromJson(jsonObject.getAsJsonObject("prev")) : null;
        return new XblXstsToken(jsonObject.get("expireTimeMs").getAsLong(), jsonObject.get("token").getAsString(), jsonObject.get("userHash").getAsString(), fullXblSession);
    }

    @Override
    public AbstractStep.StepResult fromJson(JsonObject jsonObject) throws Exception {
        return this.fromJson(jsonObject);
    }

    @Override
    public AbstractStep.StepResult refresh(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.refresh(httpClient, (XblXsts)stepResult);
    }

    @Override
    public AbstractStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, (StepFullXblSession.FullXblSession)stepResult);
    }

    public static interface XblXsts<P extends AbstractStep.StepResult<?>>
    extends AbstractStep.StepResult<P> {
        public long expireTimeMs();

        public String token();

        public String userHash();

        default public StepInitialXblSession.InitialXblSession initialXblSession() {
            return this.fullXblSession().prevResult().prevResult();
        }

        public StepFullXblSession.FullXblSession fullXblSession();

        @Override
        public JsonObject toJson();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class XblXstsToken
    implements AbstractStep.StepResult<StepFullXblSession.FullXblSession>,
    XblXsts<StepFullXblSession.FullXblSession> {
        private final long expireTimeMs;
        private final String token;
        private final String userHash;
        private final StepFullXblSession.FullXblSession prevResult;

        public XblXstsToken(long l, String string, String string2, StepFullXblSession.FullXblSession fullXblSession) {
            this.expireTimeMs = l;
            this.token = string;
            this.userHash = string2;
            this.prevResult = fullXblSession;
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

        @Override
        public StepFullXblSession.FullXblSession fullXblSession() {
            return this.prevResult;
        }

        @Override
        public long expireTimeMs() {
            return this.expireTimeMs;
        }

        @Override
        public String token() {
            return this.token;
        }

        @Override
        public String userHash() {
            return this.userHash;
        }

        @Override
        public StepFullXblSession.FullXblSession prevResult() {
            return this.prevResult;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (object == null || object.getClass() != this.getClass()) {
                return true;
            }
            XblXstsToken xblXstsToken = (XblXstsToken)object;
            return this.expireTimeMs == xblXstsToken.expireTimeMs && Objects.equals(this.token, xblXstsToken.token) && Objects.equals(this.userHash, xblXstsToken.userHash) && Objects.equals(this.prevResult, xblXstsToken.prevResult);
        }

        public int hashCode() {
            return Objects.hash(this.expireTimeMs, this.token, this.userHash, this.prevResult);
        }

        public String toString() {
            return "XblXstsToken[expireTimeMs=" + this.expireTimeMs + ", token=" + this.token + ", userHash=" + this.userHash + ", prevResult=" + this.prevResult + ']';
        }

        @Override
        public AbstractStep.StepResult prevResult() {
            return this.prevResult();
        }

        static long access$000(XblXstsToken xblXstsToken) {
            return xblXstsToken.expireTimeMs;
        }
    }
}

