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
import java.util.function.Consumer;
import net.raphimc.mcauth.MinecraftAuth;
import net.raphimc.mcauth.step.AbstractStep;
import net.raphimc.mcauth.step.msa.MsaResponseHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StepMsaDeviceCode
extends AbstractStep<MsaDeviceCodeCallback, MsaDeviceCode> {
    public static final String CONNECT_URL = "https://login.live.com/oauth20_connect.srf";
    private final String clientId;
    private final String scope;

    public StepMsaDeviceCode(String string, String string2) {
        super(null);
        this.clientId = string;
        this.scope = string2;
    }

    @Override
    public MsaDeviceCode applyStep(HttpClient httpClient, MsaDeviceCodeCallback msaDeviceCodeCallback) throws Exception {
        MinecraftAuth.LOGGER.info("Getting device code for MSA login...");
        if (msaDeviceCodeCallback == null) {
            throw new IllegalStateException("Missing StepMsaDeviceCode.MsaDeviceCodeCallback input");
        }
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("client_id", this.clientId));
        arrayList.add(new BasicNameValuePair("response_type", "device_code"));
        arrayList.add(new BasicNameValuePair("scope", this.scope));
        HttpPost httpPost = new HttpPost(CONNECT_URL);
        httpPost.setEntity(new UrlEncodedFormEntity(arrayList, StandardCharsets.UTF_8));
        String string = httpClient.execute((HttpUriRequest)httpPost, new MsaResponseHandler());
        JsonObject jsonObject = JsonParser.parseString(string).getAsJsonObject();
        MsaDeviceCode msaDeviceCode = new MsaDeviceCode(System.currentTimeMillis() + jsonObject.get("expires_in").getAsLong() * 1000L, jsonObject.get("interval").getAsLong() * 1000L, jsonObject.get("device_code").getAsString(), jsonObject.get("user_code").getAsString(), jsonObject.get("verification_uri").getAsString());
        MinecraftAuth.LOGGER.info("Got MSA device code, expires: " + Instant.ofEpochMilli(MsaDeviceCode.access$000(msaDeviceCode)).atZone(ZoneId.systemDefault()));
        MsaDeviceCodeCallback.access$100(msaDeviceCodeCallback).accept(msaDeviceCode);
        return msaDeviceCode;
    }

    @Override
    public MsaDeviceCode fromJson(JsonObject jsonObject) throws Exception {
        return new MsaDeviceCode(jsonObject.get("expireTimeMs").getAsLong(), jsonObject.get("intervalMs").getAsLong(), jsonObject.get("deviceCode").getAsString(), jsonObject.get("userCode").getAsString(), jsonObject.get("verificationUrl").getAsString());
    }

    @Override
    public AbstractStep.StepResult fromJson(JsonObject jsonObject) throws Exception {
        return this.fromJson(jsonObject);
    }

    @Override
    public AbstractStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, (MsaDeviceCodeCallback)stepResult);
    }

    public static final class MsaDeviceCodeCallback
    implements AbstractStep.InitialInput {
        private final Consumer<MsaDeviceCode> callback;

        public MsaDeviceCodeCallback(Consumer<MsaDeviceCode> consumer) {
            this.callback = consumer;
        }

        public Consumer<MsaDeviceCode> callback() {
            return this.callback;
        }

        static Consumer access$100(MsaDeviceCodeCallback msaDeviceCodeCallback) {
            return msaDeviceCodeCallback.callback;
        }
    }

    public static final class MsaDeviceCode
    implements AbstractStep.StepResult<AbstractStep.StepResult<?>> {
        private final long expireTimeMs;
        private final long intervalMs;
        private final String deviceCode;
        private final String userCode;
        private final String verificationUri;

        public MsaDeviceCode(long l, long l2, String string, String string2, String string3) {
            this.expireTimeMs = l;
            this.intervalMs = l2;
            this.deviceCode = string;
            this.userCode = string2;
            this.verificationUri = string3;
        }

        @Override
        public AbstractStep.StepResult<?> prevResult() {
            return null;
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("expireTimeMs", this.expireTimeMs);
            jsonObject.addProperty("intervalMs", this.intervalMs);
            jsonObject.addProperty("deviceCode", this.deviceCode);
            jsonObject.addProperty("userCode", this.userCode);
            jsonObject.addProperty("verificationUri", this.verificationUri);
            return jsonObject;
        }

        @Override
        public boolean isExpired() throws Exception {
            return this.expireTimeMs <= System.currentTimeMillis();
        }

        public long expireTimeMs() {
            return this.expireTimeMs;
        }

        public long intervalMs() {
            return this.intervalMs;
        }

        public String deviceCode() {
            return this.deviceCode;
        }

        public String userCode() {
            return this.userCode;
        }

        public String verificationUri() {
            return this.verificationUri;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (object == null || object.getClass() != this.getClass()) {
                return true;
            }
            MsaDeviceCode msaDeviceCode = (MsaDeviceCode)object;
            return this.expireTimeMs == msaDeviceCode.expireTimeMs && this.intervalMs == msaDeviceCode.intervalMs && Objects.equals(this.deviceCode, msaDeviceCode.deviceCode) && Objects.equals(this.userCode, msaDeviceCode.userCode) && Objects.equals(this.verificationUri, msaDeviceCode.verificationUri);
        }

        public int hashCode() {
            return Objects.hash(this.expireTimeMs, this.intervalMs, this.deviceCode, this.userCode, this.verificationUri);
        }

        public String toString() {
            return "MsaDeviceCode[expireTimeMs=" + this.expireTimeMs + ", intervalMs=" + this.intervalMs + ", deviceCode=" + this.deviceCode + ", userCode=" + this.userCode + ", verificationUri=" + this.verificationUri + ']';
        }

        static long access$000(MsaDeviceCode msaDeviceCode) {
            return msaDeviceCode.expireTimeMs;
        }
    }
}

