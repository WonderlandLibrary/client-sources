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
import net.raphimc.mcauth.step.xbl.StepXblTitleToken;
import net.raphimc.mcauth.step.xbl.StepXblUserToken;
import net.raphimc.mcauth.step.xbl.StepXblXstsToken;
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
public class StepXblSisuAuthentication
extends AbstractStep<StepInitialXblSession.InitialXblSession, StepXblXstsToken.XblXsts<?>> {
    public static final String XBL_SISU_URL = "https://sisu.xboxlive.com/authorize";
    private final String relyingParty;

    public StepXblSisuAuthentication(AbstractStep<?, StepInitialXblSession.InitialXblSession> abstractStep, String string) {
        super(abstractStep);
        this.relyingParty = string;
    }

    @Override
    public XblSisuTokens applyStep(HttpClient httpClient, StepInitialXblSession.InitialXblSession initialXblSession) throws Exception {
        MinecraftAuth.LOGGER.info("Authenticating with Xbox Live using SISU...");
        if (initialXblSession.prevResult2() == null) {
            throw new IllegalStateException("A XBL Device Token is needed for SISU authentication");
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("AccessToken", "t=" + initialXblSession.prevResult().access_token());
        jsonObject.addProperty("DeviceToken", initialXblSession.prevResult2().token());
        jsonObject.addProperty("AppId", initialXblSession.prevResult().prevResult().clientId());
        jsonObject.add("ProofKey", CryptUtil.getProofKey(initialXblSession.prevResult2().publicKey()));
        jsonObject.addProperty("SiteName", "user.auth.xboxlive.com");
        jsonObject.addProperty("RelyingParty", this.relyingParty);
        jsonObject.addProperty("Sandbox", "RETAIL");
        jsonObject.addProperty("UseModernGamertag", true);
        HttpPost httpPost = new HttpPost(XBL_SISU_URL);
        httpPost.setEntity(new StringEntity(jsonObject.toString(), ContentType.APPLICATION_JSON));
        httpPost.addHeader(CryptUtil.getSignatureHeader(httpPost, initialXblSession.prevResult2().privateKey()));
        String string = httpClient.execute((HttpUriRequest)httpPost, new XblResponseHandler());
        JsonObject jsonObject2 = JsonParser.parseString(string).getAsJsonObject();
        XblSisuTokens xblSisuTokens = new XblSisuTokens(new XblSisuTokens.SisuTitleToken(Instant.parse(jsonObject2.getAsJsonObject("TitleToken").get("NotAfter").getAsString()).toEpochMilli(), jsonObject2.getAsJsonObject("TitleToken").get("Token").getAsString(), jsonObject2.getAsJsonObject("TitleToken").getAsJsonObject("DisplayClaims").getAsJsonObject("xti").get("tid").getAsString()), new XblSisuTokens.SisuUserToken(Instant.parse(jsonObject2.getAsJsonObject("UserToken").get("NotAfter").getAsString()).toEpochMilli(), jsonObject2.getAsJsonObject("UserToken").get("Token").getAsString(), jsonObject2.getAsJsonObject("UserToken").getAsJsonObject("DisplayClaims").getAsJsonArray("xui").get(0).getAsJsonObject().get("uhs").getAsString()), new XblSisuTokens.SisuXstsToken(Instant.parse(jsonObject2.getAsJsonObject("AuthorizationToken").get("NotAfter").getAsString()).toEpochMilli(), jsonObject2.getAsJsonObject("AuthorizationToken").get("Token").getAsString(), jsonObject2.getAsJsonObject("AuthorizationToken").getAsJsonObject("DisplayClaims").getAsJsonArray("xui").get(0).getAsJsonObject().get("uhs").getAsString()), initialXblSession);
        MinecraftAuth.LOGGER.info("Got XBL Title+User+XSTS Token, expires: " + Instant.ofEpochMilli(xblSisuTokens.expireTimeMs()).atZone(ZoneId.systemDefault()));
        return xblSisuTokens;
    }

    @Override
    public StepXblXstsToken.XblXsts<?> refresh(HttpClient httpClient, StepXblXstsToken.XblXsts<?> xblXsts) throws Exception {
        if (xblXsts == null || xblXsts.isExpired()) {
            return super.refresh(httpClient, xblXsts);
        }
        return xblXsts;
    }

    @Override
    public XblSisuTokens fromJson(JsonObject jsonObject) throws Exception {
        StepInitialXblSession.InitialXblSession initialXblSession = this.prevStep != null ? (StepInitialXblSession.InitialXblSession)this.prevStep.fromJson(jsonObject.getAsJsonObject("prev")) : null;
        return new XblSisuTokens(XblSisuTokens.SisuTitleToken.fromJson(jsonObject.getAsJsonObject("titleToken")), XblSisuTokens.SisuUserToken.fromJson(jsonObject.getAsJsonObject("userToken")), XblSisuTokens.SisuXstsToken.fromJson(jsonObject.getAsJsonObject("xstsToken")), initialXblSession);
    }

    @Override
    public AbstractStep.StepResult fromJson(JsonObject jsonObject) throws Exception {
        return this.fromJson(jsonObject);
    }

    @Override
    public AbstractStep.StepResult refresh(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.refresh(httpClient, (StepXblXstsToken.XblXsts)stepResult);
    }

    @Override
    public AbstractStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, (StepInitialXblSession.InitialXblSession)stepResult);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class XblSisuTokens
    implements AbstractStep.StepResult<StepInitialXblSession.InitialXblSession>,
    StepXblXstsToken.XblXsts<StepInitialXblSession.InitialXblSession> {
        private final SisuTitleToken titleToken;
        private final SisuUserToken userToken;
        private final SisuXstsToken xstsToken;
        private final StepInitialXblSession.InitialXblSession prevResult;

        public XblSisuTokens(SisuTitleToken sisuTitleToken, SisuUserToken sisuUserToken, SisuXstsToken sisuXstsToken, StepInitialXblSession.InitialXblSession initialXblSession) {
            this.titleToken = sisuTitleToken;
            this.userToken = sisuUserToken;
            this.xstsToken = sisuXstsToken;
            this.prevResult = initialXblSession;
        }

        @Override
        public long expireTimeMs() {
            return Math.min(Math.min(SisuXstsToken.access$000(this.xstsToken), SisuTitleToken.access$100(this.titleToken)), SisuUserToken.access$200(this.userToken));
        }

        @Override
        public String token() {
            return SisuXstsToken.access$300(this.xstsToken);
        }

        @Override
        public String userHash() {
            return SisuXstsToken.access$400(this.xstsToken);
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("titleToken", this.titleToken.toJson());
            jsonObject.add("userToken", this.userToken.toJson());
            jsonObject.add("xstsToken", this.xstsToken.toJson());
            if (this.prevResult != null) {
                jsonObject.add("prev", this.prevResult.toJson());
            }
            return jsonObject;
        }

        @Override
        public boolean isExpired() {
            return this.expireTimeMs() <= System.currentTimeMillis();
        }

        @Override
        public StepFullXblSession.FullXblSession fullXblSession() {
            StepXblUserToken.XblUserToken xblUserToken = new StepXblUserToken.XblUserToken(SisuUserToken.access$200(this.userToken), SisuUserToken.access$500(this.userToken), SisuUserToken.access$600(this.userToken), this.prevResult);
            StepXblTitleToken.XblTitleToken xblTitleToken = new StepXblTitleToken.XblTitleToken(SisuTitleToken.access$100(this.titleToken), SisuTitleToken.access$700(this.titleToken), SisuTitleToken.access$800(this.titleToken), this.prevResult);
            return new StepFullXblSession.FullXblSession(xblUserToken, xblTitleToken);
        }

        public SisuTitleToken titleToken() {
            return this.titleToken;
        }

        public SisuUserToken userToken() {
            return this.userToken;
        }

        public SisuXstsToken xstsToken() {
            return this.xstsToken;
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
            XblSisuTokens xblSisuTokens = (XblSisuTokens)object;
            return Objects.equals(this.titleToken, xblSisuTokens.titleToken) && Objects.equals(this.userToken, xblSisuTokens.userToken) && Objects.equals(this.xstsToken, xblSisuTokens.xstsToken) && Objects.equals(this.prevResult, xblSisuTokens.prevResult);
        }

        public int hashCode() {
            return Objects.hash(this.titleToken, this.userToken, this.xstsToken, this.prevResult);
        }

        public String toString() {
            return "XblSisuTokens[titleToken=" + this.titleToken + ", userToken=" + this.userToken + ", xstsToken=" + this.xstsToken + ", prevResult=" + this.prevResult + ']';
        }

        @Override
        public AbstractStep.StepResult prevResult() {
            return this.prevResult();
        }

        public static final class SisuXstsToken {
            private final long expireTimeMs;
            private final String token;
            private final String userHash;

            public SisuXstsToken(long l, String string, String string2) {
                this.expireTimeMs = l;
                this.token = string;
                this.userHash = string2;
            }

            public static SisuXstsToken fromJson(JsonObject jsonObject) {
                return new SisuXstsToken(jsonObject.get("expireTimeMs").getAsLong(), jsonObject.get("token").getAsString(), jsonObject.get("userHash").getAsString());
            }

            public JsonObject toJson() {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("expireTimeMs", this.expireTimeMs);
                jsonObject.addProperty("token", this.token);
                jsonObject.addProperty("userHash", this.userHash);
                return jsonObject;
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

            public boolean equals(Object object) {
                if (object == this) {
                    return false;
                }
                if (object == null || object.getClass() != this.getClass()) {
                    return true;
                }
                SisuXstsToken sisuXstsToken = (SisuXstsToken)object;
                return this.expireTimeMs == sisuXstsToken.expireTimeMs && Objects.equals(this.token, sisuXstsToken.token) && Objects.equals(this.userHash, sisuXstsToken.userHash);
            }

            public int hashCode() {
                return Objects.hash(this.expireTimeMs, this.token, this.userHash);
            }

            public String toString() {
                return "SisuXstsToken[expireTimeMs=" + this.expireTimeMs + ", token=" + this.token + ", userHash=" + this.userHash + ']';
            }

            static long access$000(SisuXstsToken sisuXstsToken) {
                return sisuXstsToken.expireTimeMs;
            }

            static String access$300(SisuXstsToken sisuXstsToken) {
                return sisuXstsToken.token;
            }

            static String access$400(SisuXstsToken sisuXstsToken) {
                return sisuXstsToken.userHash;
            }
        }

        public static final class SisuUserToken {
            private final long expireTimeMs;
            private final String token;
            private final String userHash;

            public SisuUserToken(long l, String string, String string2) {
                this.expireTimeMs = l;
                this.token = string;
                this.userHash = string2;
            }

            public static SisuUserToken fromJson(JsonObject jsonObject) {
                return new SisuUserToken(jsonObject.get("expireTimeMs").getAsLong(), jsonObject.get("token").getAsString(), jsonObject.get("userHash").getAsString());
            }

            public JsonObject toJson() {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("expireTimeMs", this.expireTimeMs);
                jsonObject.addProperty("token", this.token);
                jsonObject.addProperty("userHash", this.userHash);
                return jsonObject;
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

            public boolean equals(Object object) {
                if (object == this) {
                    return false;
                }
                if (object == null || object.getClass() != this.getClass()) {
                    return true;
                }
                SisuUserToken sisuUserToken = (SisuUserToken)object;
                return this.expireTimeMs == sisuUserToken.expireTimeMs && Objects.equals(this.token, sisuUserToken.token) && Objects.equals(this.userHash, sisuUserToken.userHash);
            }

            public int hashCode() {
                return Objects.hash(this.expireTimeMs, this.token, this.userHash);
            }

            public String toString() {
                return "SisuUserToken[expireTimeMs=" + this.expireTimeMs + ", token=" + this.token + ", userHash=" + this.userHash + ']';
            }

            static long access$200(SisuUserToken sisuUserToken) {
                return sisuUserToken.expireTimeMs;
            }

            static String access$500(SisuUserToken sisuUserToken) {
                return sisuUserToken.token;
            }

            static String access$600(SisuUserToken sisuUserToken) {
                return sisuUserToken.userHash;
            }
        }

        public static final class SisuTitleToken {
            private final long expireTimeMs;
            private final String token;
            private final String titleId;

            public SisuTitleToken(long l, String string, String string2) {
                this.expireTimeMs = l;
                this.token = string;
                this.titleId = string2;
            }

            public static SisuTitleToken fromJson(JsonObject jsonObject) {
                return new SisuTitleToken(jsonObject.get("expireTimeMs").getAsLong(), jsonObject.get("token").getAsString(), jsonObject.get("titleId").getAsString());
            }

            public JsonObject toJson() {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("expireTimeMs", this.expireTimeMs);
                jsonObject.addProperty("token", this.token);
                jsonObject.addProperty("titleId", this.titleId);
                return jsonObject;
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

            public boolean equals(Object object) {
                if (object == this) {
                    return false;
                }
                if (object == null || object.getClass() != this.getClass()) {
                    return true;
                }
                SisuTitleToken sisuTitleToken = (SisuTitleToken)object;
                return this.expireTimeMs == sisuTitleToken.expireTimeMs && Objects.equals(this.token, sisuTitleToken.token) && Objects.equals(this.titleId, sisuTitleToken.titleId);
            }

            public int hashCode() {
                return Objects.hash(this.expireTimeMs, this.token, this.titleId);
            }

            public String toString() {
                return "SisuTitleToken[expireTimeMs=" + this.expireTimeMs + ", token=" + this.token + ", titleId=" + this.titleId + ']';
            }

            static long access$100(SisuTitleToken sisuTitleToken) {
                return sisuTitleToken.expireTimeMs;
            }

            static String access$700(SisuTitleToken sisuTitleToken) {
                return sisuTitleToken.token;
            }

            static String access$800(SisuTitleToken sisuTitleToken) {
                return sisuTitleToken.titleId;
            }
        }
    }
}

