/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step.bedrock;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import net.raphimc.mcauth.MinecraftAuth;
import net.raphimc.mcauth.step.AbstractStep;
import net.raphimc.mcauth.step.xbl.StepXblXstsToken;
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
public class StepMCChain
extends AbstractStep<StepXblXstsToken.XblXsts<?>, MCChain> {
    public static final String MINECRAFT_LOGIN_URL = "https://multiplayer.minecraft.net/authentication";
    private static final String MOJANG_PUBLIC_KEY_BASE64 = "MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAECRXueJeTDqNRRgJi/vlRufByu/2G0i2Ebt6YMar5QX/R0DIIyrJMcUpruK4QveTfJSTp3Shlq4Gk34cD/4GUWwkv0DVuzeuB+tXija7HBxii03NHDbPAD0AKnLr2wdAp";
    private static final ECPublicKey MOJANG_PUBLIC_KEY = StepMCChain.publicKeyFromBase64("MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAECRXueJeTDqNRRgJi/vlRufByu/2G0i2Ebt6YMar5QX/R0DIIyrJMcUpruK4QveTfJSTp3Shlq4Gk34cD/4GUWwkv0DVuzeuB+tXija7HBxii03NHDbPAD0AKnLr2wdAp");
    private static final int CLOCK_SKEW = 60;

    public StepMCChain(AbstractStep<?, StepXblXstsToken.XblXsts<?>> abstractStep) {
        super(abstractStep);
    }

    @Override
    public MCChain applyStep(HttpClient httpClient, StepXblXstsToken.XblXsts<?> xblXsts) throws Exception {
        MinecraftAuth.LOGGER.info("Authenticating with Minecraft Services...");
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(new ECGenParameterSpec("secp384r1"));
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        ECPublicKey eCPublicKey = (ECPublicKey)keyPair.getPublic();
        ECPrivateKey eCPrivateKey = (ECPrivateKey)keyPair.getPrivate();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("identityPublicKey", Base64.getEncoder().encodeToString(eCPublicKey.getEncoded()));
        HttpPost httpPost = new HttpPost(MINECRAFT_LOGIN_URL);
        httpPost.setEntity(new StringEntity(jsonObject.toString(), ContentType.APPLICATION_JSON));
        httpPost.addHeader("Authorization", "XBL3.0 x=" + xblXsts.userHash() + ";" + xblXsts.token());
        String string = httpClient.execute((HttpUriRequest)httpPost, new BasicResponseHandler());
        JsonObject jsonObject2 = JsonParser.parseString(string).getAsJsonObject();
        JsonArray jsonArray = jsonObject2.get("chain").getAsJsonArray();
        if (jsonArray.size() != 2) {
            throw new IllegalStateException("Invalid chain size");
        }
        Jws<Claims> jws = Jwts.parser().clockSkewSeconds(60L).verifyWith(MOJANG_PUBLIC_KEY).build().parseSignedClaims(jsonArray.get(0).getAsString());
        ECPublicKey eCPublicKey2 = StepMCChain.publicKeyFromBase64(((Claims)jws.getPayload()).get("identityPublicKey", String.class));
        Jws<Claims> jws2 = Jwts.parser().clockSkewSeconds(60L).verifyWith(eCPublicKey2).build().parseSignedClaims(jsonArray.get(1).getAsString());
        Map map = ((Claims)jws2.getPayload()).get("extraData", Map.class);
        String string2 = (String)map.get("XUID");
        UUID uUID = UUID.fromString((String)map.get("identity"));
        String string3 = (String)map.get("displayName");
        if (!map.containsKey("titleId")) {
            MinecraftAuth.LOGGER.warn("Minecraft chain does not contain titleId! You might get kicked from some servers");
        }
        MCChain mCChain = new MCChain(eCPublicKey, eCPrivateKey, jsonArray.get(0).getAsString(), jsonArray.get(1).getAsString(), string2, uUID, string3, xblXsts);
        MinecraftAuth.LOGGER.info("Got MC Chain, name: " + MCChain.access$000(mCChain) + ", uuid: " + MCChain.access$100(mCChain) + ", xuid: " + MCChain.access$200(mCChain));
        return mCChain;
    }

    @Override
    public MCChain refresh(HttpClient httpClient, MCChain mCChain) throws Exception {
        if (mCChain == null || mCChain.isExpired()) {
            return super.refresh(httpClient, mCChain);
        }
        return mCChain;
    }

    @Override
    public MCChain fromJson(JsonObject jsonObject) throws Exception {
        StepXblXstsToken.XblXsts xblXsts = this.prevStep != null ? (StepXblXstsToken.XblXsts)this.prevStep.fromJson(jsonObject.getAsJsonObject("prev")) : null;
        return new MCChain(StepMCChain.publicKeyFromBase64(jsonObject.get("publicKey").getAsString()), (ECPrivateKey)CryptUtil.EC_KEYFACTORY.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(jsonObject.get("privateKey").getAsString()))), jsonObject.get("mojangJwt").getAsString(), jsonObject.get("identityJwt").getAsString(), jsonObject.get("xuid").getAsString(), UUID.fromString(jsonObject.get("id").getAsString()), jsonObject.get("displayName").getAsString(), xblXsts);
    }

    private static ECPublicKey publicKeyFromBase64(String string) {
        try {
            return (ECPublicKey)CryptUtil.EC_KEYFACTORY.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(string)));
        } catch (InvalidKeySpecException invalidKeySpecException) {
            throw new RuntimeException("Could not decode base64 public key", invalidKeySpecException);
        }
    }

    @Override
    public AbstractStep.StepResult fromJson(JsonObject jsonObject) throws Exception {
        return this.fromJson(jsonObject);
    }

    @Override
    public AbstractStep.StepResult refresh(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.refresh(httpClient, (MCChain)stepResult);
    }

    @Override
    public AbstractStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, (StepXblXstsToken.XblXsts)stepResult);
    }

    public static final class MCChain
    implements AbstractStep.StepResult<StepXblXstsToken.XblXsts<?>> {
        private final ECPublicKey publicKey;
        private final ECPrivateKey privateKey;
        private final String mojangJwt;
        private final String identityJwt;
        private final String xuid;
        private final UUID id;
        private final String displayName;
        private final StepXblXstsToken.XblXsts<?> prevResult;

        public MCChain(ECPublicKey eCPublicKey, ECPrivateKey eCPrivateKey, String string, String string2, String string3, UUID uUID, String string4, StepXblXstsToken.XblXsts<?> xblXsts) {
            this.publicKey = eCPublicKey;
            this.privateKey = eCPrivateKey;
            this.mojangJwt = string;
            this.identityJwt = string2;
            this.xuid = string3;
            this.id = uUID;
            this.displayName = string4;
            this.prevResult = xblXsts;
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("publicKey", Base64.getEncoder().encodeToString(this.publicKey.getEncoded()));
            jsonObject.addProperty("privateKey", Base64.getEncoder().encodeToString(this.privateKey.getEncoded()));
            jsonObject.addProperty("mojangJwt", this.mojangJwt);
            jsonObject.addProperty("identityJwt", this.identityJwt);
            jsonObject.addProperty("xuid", this.xuid);
            jsonObject.addProperty("id", this.id.toString());
            jsonObject.addProperty("displayName", this.displayName);
            if (this.prevResult != null) {
                jsonObject.add("prev", this.prevResult.toJson());
            }
            return jsonObject;
        }

        public ECPublicKey publicKey() {
            return this.publicKey;
        }

        public ECPrivateKey privateKey() {
            return this.privateKey;
        }

        public String mojangJwt() {
            return this.mojangJwt;
        }

        public String identityJwt() {
            return this.identityJwt;
        }

        public String xuid() {
            return this.xuid;
        }

        public UUID id() {
            return this.id;
        }

        public String displayName() {
            return this.displayName;
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
            MCChain mCChain = (MCChain)object;
            return Objects.equals(this.publicKey, mCChain.publicKey) && Objects.equals(this.privateKey, mCChain.privateKey) && Objects.equals(this.mojangJwt, mCChain.mojangJwt) && Objects.equals(this.identityJwt, mCChain.identityJwt) && Objects.equals(this.xuid, mCChain.xuid) && Objects.equals(this.id, mCChain.id) && Objects.equals(this.displayName, mCChain.displayName) && Objects.equals(this.prevResult, mCChain.prevResult);
        }

        public int hashCode() {
            return Objects.hash(this.publicKey, this.privateKey, this.mojangJwt, this.identityJwt, this.xuid, this.id, this.displayName, this.prevResult);
        }

        public String toString() {
            return "MCChain[publicKey=" + this.publicKey + ", privateKey=" + this.privateKey + ", mojangJwt=" + this.mojangJwt + ", identityJwt=" + this.identityJwt + ", xuid=" + this.xuid + ", id=" + this.id + ", displayName=" + this.displayName + ", prevResult=" + this.prevResult + ']';
        }

        @Override
        public AbstractStep.StepResult prevResult() {
            return this.prevResult();
        }

        static String access$000(MCChain mCChain) {
            return mCChain.displayName;
        }

        static UUID access$100(MCChain mCChain) {
            return mCChain.id;
        }

        static String access$200(MCChain mCChain) {
            return mCChain.xuid;
        }
    }
}

