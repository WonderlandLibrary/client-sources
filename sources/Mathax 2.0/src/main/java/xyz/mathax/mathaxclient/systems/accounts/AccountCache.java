package xyz.mathax.mathaxclient.systems.accounts;

import com.mojang.util.UUIDTypeAdapter;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import xyz.mathax.mathaxclient.utils.render.PlayerHeadTexture;
import xyz.mathax.mathaxclient.utils.render.PlayerHeadUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountCache implements ISerializable<AccountCache> {
    public String username = "";
    public String uuid = "";

    private PlayerHeadTexture headTexture;

    public PlayerHeadTexture getHeadTexture() {
        return headTexture != null ? headTexture : PlayerHeadUtils.STEVE_HEAD;
    }

    public void loadHead() {
        if (uuid == null || uuid.isBlank()) {
            return;
        }

        headTexture = PlayerHeadUtils.fetchHead(UUIDTypeAdapter.fromString(uuid));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("uuid", uuid);
        return json;
    }

    @Override
    public AccountCache fromJson(JSONObject json) {
        if (!json.has("username") || !json.has("uuid")) {
            throw new JSONException("Account json didn't contain username or uuid.");
        }

        username = json.getString("username");
        uuid = json.getString("uuid");
        loadHead();

        return this;
    }
}