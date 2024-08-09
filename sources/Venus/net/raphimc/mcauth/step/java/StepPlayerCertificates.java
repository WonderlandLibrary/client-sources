/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step.java;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import net.raphimc.mcauth.MinecraftAuth;
import net.raphimc.mcauth.step.AbstractStep;
import net.raphimc.mcauth.step.java.StepMCToken;
import net.raphimc.mcauth.util.CryptUtil;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StepPlayerCertificates
extends AbstractStep<StepMCToken.MCToken, PlayerCertificates> {
    public static final String PLAYER_CERTIFICATES_URL = "https://api.minecraftservices.com/player/certificates";

    public StepPlayerCertificates(AbstractStep<?, StepMCToken.MCToken> abstractStep) {
        super(abstractStep);
    }

    @Override
    public PlayerCertificates applyStep(HttpClient httpClient, StepMCToken.MCToken mCToken) throws Exception {
        MinecraftAuth.LOGGER.info("Getting player certificates...");
        HttpPost httpPost = new HttpPost(PLAYER_CERTIFICATES_URL);
        httpPost.setEntity(new StringEntity("", ContentType.APPLICATION_JSON));
        httpPost.addHeader("Authorization", "Bearer " + mCToken.access_token());
        String string = httpClient.execute((HttpUriRequest)httpPost, new BasicResponseHandler());
        JsonObject jsonObject = JsonParser.parseString(string).getAsJsonObject();
        JsonObject jsonObject2 = jsonObject.getAsJsonObject("keyPair");
        PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getMimeDecoder().decode(jsonObject2.get("privateKey").getAsString().replace("-----BEGIN RSA PRIVATE KEY-----", "").replace("-----END RSA PRIVATE KEY-----", "")));
        RSAPrivateKey rSAPrivateKey = (RSAPrivateKey)CryptUtil.RSA_KEYFACTORY.generatePrivate(pKCS8EncodedKeySpec);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getMimeDecoder().decode(jsonObject2.get("publicKey").getAsString().replace("-----BEGIN RSA PUBLIC KEY-----", "").replace("-----END RSA PUBLIC KEY-----", "")));
        RSAPublicKey rSAPublicKey = (RSAPublicKey)CryptUtil.RSA_KEYFACTORY.generatePublic(x509EncodedKeySpec);
        PlayerCertificates playerCertificates = new PlayerCertificates(Instant.parse(jsonObject.get("expiresAt").getAsString()).toEpochMilli(), rSAPublicKey, rSAPrivateKey, Base64.getMimeDecoder().decode(jsonObject.get("publicKeySignatureV2").getAsString()), jsonObject.has("publicKeySignature") ? Base64.getMimeDecoder().decode(jsonObject.get("publicKeySignature").getAsString()) : new byte[]{}, mCToken);
        MinecraftAuth.LOGGER.info("Got player certificates, expires: " + Instant.ofEpochMilli(PlayerCertificates.access$000(playerCertificates)).atZone(ZoneId.systemDefault()));
        return playerCertificates;
    }

    @Override
    public PlayerCertificates refresh(HttpClient httpClient, PlayerCertificates playerCertificates) throws Exception {
        if (playerCertificates == null || playerCertificates.isExpired()) {
            return super.refresh(httpClient, playerCertificates);
        }
        return playerCertificates;
    }

    @Override
    public PlayerCertificates fromJson(JsonObject jsonObject) throws Exception {
        StepMCToken.MCToken mCToken = this.prevStep != null ? (StepMCToken.MCToken)this.prevStep.fromJson(jsonObject.getAsJsonObject("prev")) : null;
        return new PlayerCertificates(jsonObject.get("expireTimeMs").getAsLong(), (RSAPublicKey)CryptUtil.RSA_KEYFACTORY.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(jsonObject.get("publicKey").getAsString()))), (RSAPrivateKey)CryptUtil.RSA_KEYFACTORY.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(jsonObject.get("privateKey").getAsString()))), Base64.getDecoder().decode(jsonObject.get("publicKeySignature").getAsString()), Base64.getDecoder().decode(jsonObject.get("legacyPublicKeySignature").getAsString()), mCToken);
    }

    @Override
    public AbstractStep.StepResult fromJson(JsonObject jsonObject) throws Exception {
        return this.fromJson(jsonObject);
    }

    @Override
    public AbstractStep.StepResult refresh(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.refresh(httpClient, (PlayerCertificates)stepResult);
    }

    @Override
    public AbstractStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, (StepMCToken.MCToken)stepResult);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class PlayerCertificates
    implements AbstractStep.StepResult<StepMCToken.MCToken> {
        private final long expireTimeMs;
        private final RSAPublicKey publicKey;
        private final RSAPrivateKey privateKey;
        private final byte[] publicKeySignature;
        private final byte[] legacyPublicKeySignature;
        private final StepMCToken.MCToken prevResult;

        public PlayerCertificates(long l, RSAPublicKey rSAPublicKey, RSAPrivateKey rSAPrivateKey, byte[] byArray, byte[] byArray2, StepMCToken.MCToken mCToken) {
            this.expireTimeMs = l;
            this.publicKey = rSAPublicKey;
            this.privateKey = rSAPrivateKey;
            this.publicKeySignature = byArray;
            this.legacyPublicKeySignature = byArray2;
            this.prevResult = mCToken;
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("expireTimeMs", this.expireTimeMs);
            jsonObject.addProperty("publicKey", Base64.getEncoder().encodeToString(this.publicKey.getEncoded()));
            jsonObject.addProperty("privateKey", Base64.getEncoder().encodeToString(this.privateKey.getEncoded()));
            jsonObject.addProperty("publicKeySignature", Base64.getEncoder().encodeToString(this.publicKeySignature));
            jsonObject.addProperty("legacyPublicKeySignature", Base64.getEncoder().encodeToString(this.legacyPublicKeySignature));
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

        public RSAPublicKey publicKey() {
            return this.publicKey;
        }

        public RSAPrivateKey privateKey() {
            return this.privateKey;
        }

        public byte[] publicKeySignature() {
            return this.publicKeySignature;
        }

        public byte[] legacyPublicKeySignature() {
            return this.legacyPublicKeySignature;
        }

        @Override
        public StepMCToken.MCToken prevResult() {
            return this.prevResult;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            PlayerCertificates playerCertificates = (PlayerCertificates)object;
            return this.expireTimeMs == playerCertificates.expireTimeMs && Objects.equals(this.publicKey, playerCertificates.publicKey) && Objects.equals(this.privateKey, playerCertificates.privateKey) && Arrays.equals(this.publicKeySignature, playerCertificates.publicKeySignature) && Arrays.equals(this.legacyPublicKeySignature, playerCertificates.legacyPublicKeySignature) && Objects.equals(this.prevResult, playerCertificates.prevResult);
        }

        public int hashCode() {
            int n = Objects.hash(this.expireTimeMs, this.publicKey, this.privateKey, this.prevResult);
            n = 31 * n + Arrays.hashCode(this.publicKeySignature);
            n = 31 * n + Arrays.hashCode(this.legacyPublicKeySignature);
            return n;
        }

        public String toString() {
            return "PlayerCertificates{expireTimeMs=" + this.expireTimeMs + ", publicKey=" + this.publicKey + ", privateKey=" + this.privateKey + ", publicKeySignature=" + Arrays.toString(this.publicKeySignature) + ", legacyPublicKeySignature=" + Arrays.toString(this.legacyPublicKeySignature) + ", prevResult=" + this.prevResult + '}';
        }

        @Override
        public AbstractStep.StepResult prevResult() {
            return this.prevResult();
        }

        static long access$000(PlayerCertificates playerCertificates) {
            return playerCertificates.expireTimeMs;
        }
    }
}

