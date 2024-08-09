/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step.java;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;
import net.raphimc.mcauth.MinecraftAuth;
import net.raphimc.mcauth.step.AbstractStep;
import net.raphimc.mcauth.step.xbl.StepXblXstsToken;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StepMCToken
extends AbstractStep<StepXblXstsToken.XblXsts<?>, MCToken> {
    public static final String MINECRAFT_LOGIN_URL = "https://api.minecraftservices.com/authentication/login_with_xbox";

    public StepMCToken(AbstractStep<?, StepXblXstsToken.XblXsts<?>> abstractStep) {
        super(abstractStep);
    }

    @Override
    public MCToken applyStep(HttpClient httpClient, StepXblXstsToken.XblXsts<?> xblXsts) throws Exception {
        MinecraftAuth.LOGGER.info("Authenticating with Minecraft Services...");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("identityToken", "XBL3.0 x=" + xblXsts.userHash() + ";" + xblXsts.token());
        HttpPost httpPost = new HttpPost(MINECRAFT_LOGIN_URL);
        httpPost.setEntity(new StringEntity(jsonObject.toString(), ContentType.APPLICATION_JSON));
        String string = httpClient.execute((HttpUriRequest)httpPost, new BasicResponseHandler());
        JsonObject jsonObject2 = JsonParser.parseString(string).getAsJsonObject();
        MCToken mCToken = new MCToken(jsonObject2.get("access_token").getAsString(), jsonObject2.get("token_type").getAsString(), System.currentTimeMillis() + jsonObject2.get("expires_in").getAsLong() * 1000L, xblXsts);
        MinecraftAuth.LOGGER.info("Got MC Token, expires: " + Instant.ofEpochMilli(MCToken.access$000(mCToken)).atZone(ZoneId.systemDefault()));
        return mCToken;
    }

    @Override
    public MCToken refresh(HttpClient httpClient, MCToken mCToken) throws Exception {
        if (mCToken == null || mCToken.isExpired()) {
            return super.refresh(httpClient, mCToken);
        }
        return mCToken;
    }

    @Override
    public MCToken fromJson(JsonObject jsonObject) throws Exception {
        StepXblXstsToken.XblXsts xblXsts = this.prevStep != null ? (StepXblXstsToken.XblXsts)this.prevStep.fromJson(jsonObject.getAsJsonObject("prev")) : null;
        return new MCToken(jsonObject.get("access_token").getAsString(), jsonObject.get("token_type").getAsString(), jsonObject.get("expireTimeMs").getAsLong(), xblXsts);
    }

    @Override
    public AbstractStep.StepResult fromJson(JsonObject jsonObject) throws Exception {
        return this.fromJson(jsonObject);
    }

    @Override
    public AbstractStep.StepResult refresh(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.refresh(httpClient, (MCToken)stepResult);
    }

    @Override
    public AbstractStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, (StepXblXstsToken.XblXsts)stepResult);
    }

    public static final class MCToken
    implements AbstractStep.StepResult<StepXblXstsToken.XblXsts<?>> {
        private final String access_token;
        private final String token_type;
        private final long expireTimeMs;
        private final StepXblXstsToken.XblXsts<?> prevResult;

        public MCToken(String string, String string2, long l, StepXblXstsToken.XblXsts<?> xblXsts) {
            this.access_token = string;
            this.token_type = string2;
            this.expireTimeMs = l;
            this.prevResult = xblXsts;
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("access_token", this.access_token);
            jsonObject.addProperty("token_type", this.token_type);
            jsonObject.addProperty("expireTimeMs", this.expireTimeMs);
            if (this.prevResult != null) {
                jsonObject.add("prev", this.prevResult.toJson());
            }
            return jsonObject;
        }

        @Override
        public boolean isExpired() {
            return this.expireTimeMs <= System.currentTimeMillis();
        }

        public String access_token() {
            return this.access_token;
        }

        public String token_type() {
            return this.token_type;
        }

        public long expireTimeMs() {
            return this.expireTimeMs;
        }

        @Override
        public StepXblXstsToken.XblXsts<?> prevResult() {
            return this.prevResult;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (object == null || object.getClass() != this.getClass()) {
                return true;
            }
            MCToken mCToken = (MCToken)object;
            return Objects.equals(this.access_token, mCToken.access_token) && Objects.equals(this.token_type, mCToken.token_type) && this.expireTimeMs == mCToken.expireTimeMs && Objects.equals(this.prevResult, mCToken.prevResult);
        }

        public int hashCode() {
            return Objects.hash(this.access_token, this.token_type, this.expireTimeMs, this.prevResult);
        }

        public String toString() {
            return "MCToken[access_token=" + this.access_token + ", token_type=" + this.token_type + ", expireTimeMs=" + this.expireTimeMs + ", prevResult=" + this.prevResult + ']';
        }

        @Override
        public AbstractStep.StepResult prevResult() {
            return this.prevResult();
        }

        static long access$000(MCToken mCToken) {
            return mCToken.expireTimeMs;
        }
    }
}

