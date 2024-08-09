/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step.xbl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;
import net.raphimc.mcauth.MinecraftAuth;
import net.raphimc.mcauth.step.AbstractStep;
import net.raphimc.mcauth.step.xbl.XblResponseHandler;
import net.raphimc.mcauth.util.CryptUtil;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StepXblDeviceToken
extends AbstractStep<AbstractStep.StepResult<?>, XblDeviceToken> {
    public static final String XBL_DEVICE_URL = "https://device.auth.xboxlive.com/device/authenticate";
    private final String deviceType;

    public StepXblDeviceToken(String string) {
        super(null);
        this.deviceType = string;
    }

    @Override
    public XblDeviceToken applyStep(HttpClient httpClient, AbstractStep.StepResult<?> stepResult) throws Exception {
        MinecraftAuth.LOGGER.info("Authenticating device with Xbox Live...");
        UUID uUID = UUID.randomUUID();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"));
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        ECPublicKey eCPublicKey = (ECPublicKey)keyPair.getPublic();
        ECPrivateKey eCPrivateKey = (ECPrivateKey)keyPair.getPrivate();
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("AuthMethod", "ProofOfPossession");
        jsonObject2.addProperty("DeviceType", this.deviceType);
        jsonObject2.addProperty("Id", "{" + uUID + "}");
        jsonObject2.add("ProofKey", CryptUtil.getProofKey(eCPublicKey));
        jsonObject2.addProperty("Version", "0.0.0");
        jsonObject.add("Properties", jsonObject2);
        jsonObject.addProperty("RelyingParty", "http://auth.xboxlive.com");
        jsonObject.addProperty("TokenType", "JWT");
        HttpPost httpPost = new HttpPost(XBL_DEVICE_URL);
        httpPost.setEntity(new StringEntity(jsonObject.toString(), ContentType.APPLICATION_JSON));
        httpPost.addHeader("x-xbl-contract-version", "1");
        httpPost.addHeader(CryptUtil.getSignatureHeader(httpPost, eCPrivateKey));
        String string = httpClient.execute((HttpUriRequest)httpPost, new XblResponseHandler());
        JsonObject jsonObject3 = JsonParser.parseString(string).getAsJsonObject();
        XblDeviceToken xblDeviceToken = new XblDeviceToken(eCPublicKey, eCPrivateKey, uUID, Instant.parse(jsonObject3.get("NotAfter").getAsString()).toEpochMilli(), jsonObject3.get("Token").getAsString(), jsonObject3.getAsJsonObject("DisplayClaims").getAsJsonObject("xdi").get("did").getAsString());
        MinecraftAuth.LOGGER.info("Got XBL Device Token, expires: " + Instant.ofEpochMilli(XblDeviceToken.access$000(xblDeviceToken)).atZone(ZoneId.systemDefault()));
        return xblDeviceToken;
    }

    @Override
    public XblDeviceToken refresh(HttpClient httpClient, XblDeviceToken xblDeviceToken) throws Exception {
        if (xblDeviceToken == null || xblDeviceToken.isExpired()) {
            return this.applyStep(httpClient, (AbstractStep.StepResult)null);
        }
        return xblDeviceToken;
    }

    @Override
    public XblDeviceToken fromJson(JsonObject jsonObject) throws Exception {
        return new XblDeviceToken((ECPublicKey)CryptUtil.EC_KEYFACTORY.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(jsonObject.get("publicKey").getAsString()))), (ECPrivateKey)CryptUtil.EC_KEYFACTORY.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(jsonObject.get("privateKey").getAsString()))), UUID.fromString(jsonObject.get("id").getAsString()), jsonObject.get("expireTimeMs").getAsLong(), jsonObject.get("token").getAsString(), jsonObject.get("deviceId").getAsString());
    }

    @Override
    public AbstractStep.StepResult fromJson(JsonObject jsonObject) throws Exception {
        return this.fromJson(jsonObject);
    }

    @Override
    public AbstractStep.StepResult refresh(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.refresh(httpClient, (XblDeviceToken)stepResult);
    }

    @Override
    public AbstractStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, stepResult);
    }

    public static final class XblDeviceToken
    implements AbstractStep.StepResult<AbstractStep.StepResult<?>> {
        private final ECPublicKey publicKey;
        private final ECPrivateKey privateKey;
        private final UUID id;
        private final long expireTimeMs;
        private final String token;
        private final String deviceId;

        public XblDeviceToken(ECPublicKey eCPublicKey, ECPrivateKey eCPrivateKey, UUID uUID, long l, String string, String string2) {
            this.publicKey = eCPublicKey;
            this.privateKey = eCPrivateKey;
            this.id = uUID;
            this.expireTimeMs = l;
            this.token = string;
            this.deviceId = string2;
        }

        @Override
        public AbstractStep.StepResult<?> prevResult() {
            return null;
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("publicKey", Base64.getEncoder().encodeToString(this.publicKey.getEncoded()));
            jsonObject.addProperty("privateKey", Base64.getEncoder().encodeToString(this.privateKey.getEncoded()));
            jsonObject.addProperty("id", this.id.toString());
            jsonObject.addProperty("expireTimeMs", this.expireTimeMs);
            jsonObject.addProperty("token", this.token);
            jsonObject.addProperty("deviceId", this.deviceId);
            return jsonObject;
        }

        @Override
        public boolean isExpired() {
            return this.expireTimeMs <= System.currentTimeMillis();
        }

        public ECPublicKey publicKey() {
            return this.publicKey;
        }

        public ECPrivateKey privateKey() {
            return this.privateKey;
        }

        public UUID id() {
            return this.id;
        }

        public long expireTimeMs() {
            return this.expireTimeMs;
        }

        public String token() {
            return this.token;
        }

        public String deviceId() {
            return this.deviceId;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (object == null || object.getClass() != this.getClass()) {
                return true;
            }
            XblDeviceToken xblDeviceToken = (XblDeviceToken)object;
            return Objects.equals(this.publicKey, xblDeviceToken.publicKey) && Objects.equals(this.privateKey, xblDeviceToken.privateKey) && Objects.equals(this.id, xblDeviceToken.id) && this.expireTimeMs == xblDeviceToken.expireTimeMs && Objects.equals(this.token, xblDeviceToken.token) && Objects.equals(this.deviceId, xblDeviceToken.deviceId);
        }

        public int hashCode() {
            return Objects.hash(this.publicKey, this.privateKey, this.id, this.expireTimeMs, this.token, this.deviceId);
        }

        public String toString() {
            return "XblDeviceToken[publicKey=" + this.publicKey + ", privateKey=" + this.privateKey + ", id=" + this.id + ", expireTimeMs=" + this.expireTimeMs + ", token=" + this.token + ", deviceId=" + this.deviceId + ']';
        }

        static long access$000(XblDeviceToken xblDeviceToken) {
            return xblDeviceToken.expireTimeMs;
        }
    }
}

