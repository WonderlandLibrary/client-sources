/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step.java;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.raphimc.mcauth.MinecraftAuth;
import net.raphimc.mcauth.step.AbstractStep;
import net.raphimc.mcauth.step.java.StepMCToken;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StepGameOwnership
extends AbstractStep<StepMCToken.MCToken, GameOwnership> {
    public static final String MINECRAFT_OWNERSHIP_URL = "https://api.minecraftservices.com/entitlements/mcstore";

    public StepGameOwnership(AbstractStep<?, StepMCToken.MCToken> abstractStep) {
        super(abstractStep);
    }

    @Override
    public GameOwnership applyStep(HttpClient httpClient, StepMCToken.MCToken mCToken) throws Exception {
        MinecraftAuth.LOGGER.info("Getting game ownership...");
        HttpGet httpGet = new HttpGet(MINECRAFT_OWNERSHIP_URL);
        httpGet.addHeader("Authorization", mCToken.token_type() + " " + mCToken.access_token());
        String string = httpClient.execute((HttpUriRequest)httpGet, new BasicResponseHandler());
        JsonObject jsonObject = JsonParser.parseString(string).getAsJsonObject();
        ArrayList<String> arrayList = new ArrayList<String>();
        JsonArray jsonArray = jsonObject.getAsJsonArray("items");
        for (JsonElement jsonElement : jsonArray) {
            arrayList.add(jsonElement.getAsJsonObject().get("name").getAsString());
        }
        GameOwnership gameOwnership = new GameOwnership(arrayList, mCToken);
        MinecraftAuth.LOGGER.info("Got GameOwnership, games: " + GameOwnership.access$000(gameOwnership));
        if (!GameOwnership.access$000(gameOwnership).contains("product_minecraft") || !GameOwnership.access$000(gameOwnership).contains("game_minecraft")) {
            MinecraftAuth.LOGGER.warn("Microsoft account does not own minecraft!");
        }
        return gameOwnership;
    }

    @Override
    public GameOwnership fromJson(JsonObject jsonObject) throws Exception {
        StepMCToken.MCToken mCToken = this.prevStep != null ? (StepMCToken.MCToken)this.prevStep.fromJson(jsonObject.getAsJsonObject("prev")) : null;
        return new GameOwnership((List)new Gson().fromJson(jsonObject.get("items"), (Type)((Object)List.class)), mCToken);
    }

    @Override
    public AbstractStep.StepResult fromJson(JsonObject jsonObject) throws Exception {
        return this.fromJson(jsonObject);
    }

    @Override
    public AbstractStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, (StepMCToken.MCToken)stepResult);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class GameOwnership
    implements AbstractStep.StepResult<StepMCToken.MCToken> {
        private final List<String> items;
        private final StepMCToken.MCToken prevResult;

        public GameOwnership(List<String> list, StepMCToken.MCToken mCToken) {
            this.items = list;
            this.prevResult = mCToken;
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("items", new Gson().toJsonTree(this.items));
            if (this.prevResult != null) {
                jsonObject.add("prev", this.prevResult.toJson());
            }
            return jsonObject;
        }

        public List<String> items() {
            return this.items;
        }

        @Override
        public StepMCToken.MCToken prevResult() {
            return this.prevResult;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (object == null || object.getClass() != this.getClass()) {
                return true;
            }
            GameOwnership gameOwnership = (GameOwnership)object;
            return Objects.equals(this.items, gameOwnership.items) && Objects.equals(this.prevResult, gameOwnership.prevResult);
        }

        public int hashCode() {
            return Objects.hash(this.items, this.prevResult);
        }

        public String toString() {
            return "GameOwnership[items=" + this.items + ", prevResult=" + this.prevResult + ']';
        }

        @Override
        public AbstractStep.StepResult prevResult() {
            return this.prevResult();
        }

        static List access$000(GameOwnership gameOwnership) {
            return gameOwnership.items;
        }
    }
}

