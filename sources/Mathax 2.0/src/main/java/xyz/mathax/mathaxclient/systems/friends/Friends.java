package xyz.mathax.mathaxclient.systems.friends;

import com.mojang.util.UUIDTypeAdapter;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.settings.ColorSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.settings.Settings;
import xyz.mathax.mathaxclient.systems.System;
import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.systems.enemies.Enemies;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class Friends extends System<Friends> implements Iterable<Friend> {
    private final List<Friend> friends = new ArrayList<>();

    public final Settings settings = new Settings();

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    public final Setting<SettingColor> colorSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Color")
            .description("The color used to show friends.")
            .defaultValue(new SettingColor(0, 255, 0))
            .build()
    );

    public Friends() {
        super("Friends", MatHax.VERSION_FOLDER);
    }

    public static Friends get() {
        return Systems.get(Friends.class);
    }

    public boolean add(Friend friend) {
        if (friend.name.isEmpty() || friend.name.contains(" ") || Enemies.get().contains(friend.name)) {
            return false;
        }

        if (!friends.contains(friend)) {
            friends.add(friend);
            save();

            return true;
        }

        return false;
    }

    public boolean remove(Friend friend) {
        if (friends.remove(friend)) {
            save();
            return true;
        }

        return false;
    }

    public Friend get(String name) {
        for (Friend friend : friends) {
            if (friend.name.equals(name)) {
                return friend;
            }
        }

        return null;
    }

    public Friend get(UUID uuid) {
        for (Friend friend : friends) {
            if (friend.uuid != null && friend.uuid.equals(uuid)) {
                return friend;
            }
        }

        return null;
    }

    public Friend get(PlayerEntity player) {
        return get(player.getEntityName());
    }

    public Friend get(PlayerListEntry playerListEntry) {
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
        return !contains(player);
    }

    public int count() {
        return friends.size();
    }

    public boolean isEmpty() {
        return friends.isEmpty();
    }

    @Override
    public @NotNull Iterator<Friend> iterator() {
        return friends.iterator();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("settings", settings.toJson());
        json.put("friends", new JSONArray());

        friends.forEach(friend -> json.append("friends", friend.toJson()));

        return json;
    }

    @Override
    public Friends fromJson(JSONObject json) {
        friends.clear();

        if (json.has("settings")) {
            settings.fromJson(json.getJSONObject("settings"));
        }

        if (json.has("friends") && JSONUtils.isValidJSONArray(json, "friends")) {
            for (Object object : json.getJSONArray("friends")) {
                if (object instanceof JSONObject friendJson) {
                    if (!friendJson.has("name")) {
                        continue;
                    }

                    String name = friendJson.getString("name");
                    if (get(name) != null) {
                        continue;
                    }

                    String uuid = null;
                    if (json.has("uuid")) {
                        uuid = friendJson.getString("id");
                    }

                    Friend friend = uuid != null ? new Friend(name, UUIDTypeAdapter.fromString(uuid)) : new Friend(name);

                    add(friend);
                }
            }
        }

        Collections.sort(friends);

        return this;
    }
}