package com.alan.clients.util.account;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

@Getter
@Setter
public class Account {
    private AccountType type;
    private String name;
    private String uuid;
    private String accessToken;
    private long lastUsed;

    public Account(AccountType type, String name, String uuid, String accessToken) {
        this.type = type;
        this.name = name;
        this.uuid = uuid;
        this.accessToken = accessToken;
    }

    public boolean login() {
        Minecraft mc = Minecraft.getMinecraft();
        mc.setSession(new Session(name, uuid, accessToken, "mojang"));
        lastUsed = System.currentTimeMillis();
        return true;
    }

    public boolean isValid() {
        return name != null && uuid != null && accessToken != null &&    !name.isEmpty() && !uuid.isEmpty() && !accessToken.isEmpty();
    }

    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", type.getName());
        object.addProperty("name", name);
        object.addProperty("uuid", uuid);
        object.addProperty("accessToken", accessToken);
        object.addProperty("lastUsed", lastUsed);
        return object;
    }

    public void parseJson(JsonObject object) {
        if (object.has("type")) {
            type = AccountType.getByName(object.get("type").getAsString());
        } else {
            type = AccountType.CRACKED;
        }

        if (object.has("name")) {
            name = object.get("name").getAsString();
        }

        if (object.has("uuid")) {
            uuid = object.get("uuid").getAsString();
        }

        if (object.has("accessToken")) {
            accessToken = object.get("accessToken").getAsString();
        }

        if (object.has("lastUsed")) {
            lastUsed = object.get("lastUsed").getAsLong();
        }
    }
}
