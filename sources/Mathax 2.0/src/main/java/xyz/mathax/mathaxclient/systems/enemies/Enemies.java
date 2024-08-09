package xyz.mathax.mathaxclient.systems.enemies;

import com.mojang.util.UUIDTypeAdapter;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.settings.ColorSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.settings.Settings;
import xyz.mathax.mathaxclient.systems.System;
import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.systems.friends.Friends;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class Enemies extends System<Enemies> implements Iterable<Enemy> {
    private final List<Enemy> enemies = new ArrayList<>();

    public final Settings settings = new Settings();

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    public final Setting<SettingColor> colorSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Color")
            .description("The color used to show enemies.")
            .defaultValue(new SettingColor(255, 0, 0))
            .build()
    );

    public Enemies() {
        super("Enemies", MatHax.VERSION_FOLDER);
    }

    public static Enemies get() {
        return Systems.get(Enemies.class);
    }

    public boolean add(Enemy enemy) {
        if (enemy.name.isEmpty() || enemy.name.contains(" ") || Friends.get().contains(enemy.name)) {
            return false;
        }

        if (!enemies.contains(enemy)) {
            enemies.add(enemy);
            save();

            return true;
        }

        return false;
    }

    public boolean remove(Enemy enemy) {
        if (enemies.remove(enemy)) {
            save();
            return true;
        }

        return false;
    }

    public Enemy get(String name) {
        for (Enemy enemy : enemies) {
            if (enemy.name.equals(name)) {
                return enemy;
            }
        }

        return null;
    }

    public Enemy get(UUID uuid) {
        for (Enemy enemy : enemies) {
            if (enemy.uuid != null && enemy.uuid.equals(uuid)) {
                return enemy;
            }
        }

        return null;
    }

    public Enemy get(PlayerEntity player) {
        return get(player.getEntityName());
    }

    public Enemy get(PlayerListEntry playerListEntry) {
        return get(playerListEntry.getProfile().getName());
    }

    public boolean contains(String name) {
        return get(name) != null;
    }

    public boolean contains(UUID uuid) {
        return get(uuid) != null;
    }

    public boolean contains(PlayerEntity player) {
        return get(player) != null;
    }

    public boolean contains(PlayerListEntry playerListEntry) {
        return get(playerListEntry) != null;
    }

    public boolean shouldAttack(PlayerEntity player) {
        return contains(player);
    }

    public int count() {
        return enemies.size();
    }

    public boolean isEmpty() {
        return enemies.isEmpty();
    }

    @Override
    public @NotNull Iterator<Enemy> iterator() {
        return enemies.iterator();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("settings", settings.toJson());
        json.put("enemies", new JSONArray());

        enemies.forEach(enemy -> json.append("enemies", enemy.toJson()));

        return json;
    }

    @Override
    public Enemies fromJson(JSONObject json) {
        enemies.clear();

        if (json.has("settings")) {
            settings.fromJson(json.getJSONObject("settings"));
        }

        if (json.has("enemies") && JSONUtils.isValidJSONArray(json, "enemies")) {
            for (Object object : json.getJSONArray("enemies")) {
                if (object instanceof JSONObject enemyJson) {
                    if (!enemyJson.has("name")) {
                        continue;
                    }

                    String name = enemyJson.getString("name");
                    if (get(name) != null) {
                        continue;
                    }

                    String uuid = null;
                    if (json.has("uuid")) {
                        uuid = enemyJson.getString("uuid");
                    }

                    Enemy enemy = uuid != null ? new Enemy(name, UUIDTypeAdapter.fromString(uuid)) : new Enemy(name);

                    add(enemy);
                }
            }
        }

        Collections.sort(enemies);

        return this;
    }
}