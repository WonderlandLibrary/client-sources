package xyz.mathax.mathaxclient.systems.enemies;

import com.mojang.util.UUIDTypeAdapter;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import xyz.mathax.mathaxclient.utils.network.Http;
import xyz.mathax.mathaxclient.utils.render.PlayerHeadTexture;
import xyz.mathax.mathaxclient.utils.render.PlayerHeadUtils;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.Objects;
import java.util.UUID;

public class Enemy implements ISerializable<Enemy>, Comparable<Enemy> {
    public String name;

    public @Nullable UUID uuid;

    private @Nullable PlayerHeadTexture headTexture;

    public Enemy(String name, @Nullable UUID uuid) {
        this.name = name;
        this.uuid = uuid;
        this.headTexture = PlayerHeadUtils.fetchHead(uuid);
    }

    public Enemy(PlayerEntity player) {
        this(player.getEntityName(), player.getUuid());
    }

    public Enemy(String name) {
        this(name, null);
    }

    public String getName() {
        return name;
    }

    public PlayerHeadTexture getHead() {
        return headTexture != null ? headTexture : PlayerHeadUtils.STEVE_HEAD;
    }

    public void updateInfo() {
        String response = Http.get("https://api.mojang.com/users/profiles/minecraft/" + name).sendString();
        if (response == null) {
            return;
        }

        JSONObject json = new JSONObject(response);
        if (json.has("name")) {
            name = json.getString("name");
        }

        if (json.has("id")) {
            uuid = UUIDTypeAdapter.fromString(json.getString("id"));
            headTexture = PlayerHeadUtils.fetchHead(uuid);
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);

        if (uuid != null) {
            json.put("uuid", UUIDTypeAdapter.fromUUID(uuid));
        }

        return json;
    }

    @Override
    public Enemy fromJson(JSONObject json) {
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Enemy enemy = (Enemy) object;
        return Objects.equals(name, enemy.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(@NotNull Enemy enemy) {
        return name.compareTo(enemy.name);
    }
}