/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step.java;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;
import net.raphimc.mcauth.MinecraftAuth;
import net.raphimc.mcauth.step.AbstractStep;
import net.raphimc.mcauth.step.java.StepGameOwnership;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StepMCProfile
extends AbstractStep<StepGameOwnership.GameOwnership, MCProfile> {
    public static final String MINECRAFT_PROFILE_URL = "https://api.minecraftservices.com/minecraft/profile";

    public StepMCProfile(AbstractStep<?, StepGameOwnership.GameOwnership> abstractStep) {
        super(abstractStep);
    }

    @Override
    public MCProfile applyStep(HttpClient httpClient, StepGameOwnership.GameOwnership gameOwnership) throws Exception {
        MinecraftAuth.LOGGER.info("Getting profile...");
        HttpGet httpGet = new HttpGet(MINECRAFT_PROFILE_URL);
        httpGet.addHeader("Authorization", gameOwnership.prevResult().token_type() + " " + gameOwnership.prevResult().access_token());
        String string = httpClient.execute((HttpUriRequest)httpGet, new BasicResponseHandler());
        JsonObject jsonObject = JsonParser.parseString(string).getAsJsonObject();
        if (jsonObject.has("error")) {
            throw new IOException("No valid minecraft profile found: " + jsonObject);
        }
        MCProfile mCProfile = new MCProfile(UUID.fromString(jsonObject.get("id").getAsString().replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5")), jsonObject.get("name").getAsString(), new URL(jsonObject.get("skins").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString()), gameOwnership);
        MinecraftAuth.LOGGER.info("Got MC Profile, name: " + MCProfile.access$000(mCProfile) + ", uuid: " + MCProfile.access$100(mCProfile));
        return mCProfile;
    }

    @Override
    public MCProfile fromJson(JsonObject jsonObject) throws Exception {
        StepGameOwnership.GameOwnership gameOwnership = this.prevStep != null ? (StepGameOwnership.GameOwnership)this.prevStep.fromJson(jsonObject.getAsJsonObject("prev")) : null;
        return new MCProfile(UUID.fromString(jsonObject.get("id").getAsString()), jsonObject.get("name").getAsString(), new URL(jsonObject.get("skin_url").getAsString()), gameOwnership);
    }

    @Override
    public AbstractStep.StepResult fromJson(JsonObject jsonObject) throws Exception {
        return this.fromJson(jsonObject);
    }

    @Override
    public AbstractStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, (StepGameOwnership.GameOwnership)stepResult);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class MCProfile
    implements AbstractStep.StepResult<StepGameOwnership.GameOwnership> {
        private final UUID id;
        private final String name;
        private final URL skin_url;
        private final StepGameOwnership.GameOwnership prevResult;

        public MCProfile(UUID uUID, String string, URL uRL, StepGameOwnership.GameOwnership gameOwnership) {
            this.id = uUID;
            this.name = string;
            this.skin_url = uRL;
            this.prevResult = gameOwnership;
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", this.id.toString());
            jsonObject.addProperty("name", this.name);
            jsonObject.addProperty("skin_url", this.skin_url.toString());
            if (this.prevResult != null) {
                jsonObject.add("prev", this.prevResult.toJson());
            }
            return jsonObject;
        }

        public UUID id() {
            return this.id;
        }

        public String name() {
            return this.name;
        }

        public URL skin_url() {
            return this.skin_url;
        }

        @Override
        public StepGameOwnership.GameOwnership prevResult() {
            return this.prevResult;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (object == null || object.getClass() != this.getClass()) {
                return true;
            }
            MCProfile mCProfile = (MCProfile)object;
            return Objects.equals(this.id, mCProfile.id) && Objects.equals(this.name, mCProfile.name) && Objects.equals(this.skin_url, mCProfile.skin_url) && Objects.equals(this.prevResult, mCProfile.prevResult);
        }

        public int hashCode() {
            return Objects.hash(this.id, this.name, this.skin_url, this.prevResult);
        }

        public String toString() {
            return "MCProfile[id=" + this.id + ", name=" + this.name + ", skin_url=" + this.skin_url + ", prevResult=" + this.prevResult + ']';
        }

        @Override
        public AbstractStep.StepResult prevResult() {
            return this.prevResult();
        }

        static String access$000(MCProfile mCProfile) {
            return mCProfile.name;
        }

        static UUID access$100(MCProfile mCProfile) {
            return mCProfile.id;
        }
    }
}

