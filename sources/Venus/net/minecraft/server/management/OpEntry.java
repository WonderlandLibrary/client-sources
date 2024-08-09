/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.server.management.UserListEntry;

public class OpEntry
extends UserListEntry<GameProfile> {
    private final int permissionLevel;
    private final boolean bypassesPlayerLimit;

    public OpEntry(GameProfile gameProfile, int n, boolean bl) {
        super(gameProfile);
        this.permissionLevel = n;
        this.bypassesPlayerLimit = bl;
    }

    public OpEntry(JsonObject jsonObject) {
        super(OpEntry.constructProfile(jsonObject));
        this.permissionLevel = jsonObject.has("level") ? jsonObject.get("level").getAsInt() : 0;
        this.bypassesPlayerLimit = jsonObject.has("bypassesPlayerLimit") && jsonObject.get("bypassesPlayerLimit").getAsBoolean();
    }

    public int getPermissionLevel() {
        return this.permissionLevel;
    }

    public boolean bypassesPlayerLimit() {
        return this.bypassesPlayerLimit;
    }

    @Override
    protected void onSerialization(JsonObject jsonObject) {
        if (this.getValue() != null) {
            jsonObject.addProperty("uuid", ((GameProfile)this.getValue()).getId() == null ? "" : ((GameProfile)this.getValue()).getId().toString());
            jsonObject.addProperty("name", ((GameProfile)this.getValue()).getName());
            jsonObject.addProperty("level", this.permissionLevel);
            jsonObject.addProperty("bypassesPlayerLimit", this.bypassesPlayerLimit);
        }
    }

    private static GameProfile constructProfile(JsonObject jsonObject) {
        if (jsonObject.has("uuid") && jsonObject.has("name")) {
            UUID uUID;
            String string = jsonObject.get("uuid").getAsString();
            try {
                uUID = UUID.fromString(string);
            } catch (Throwable throwable) {
                return null;
            }
            return new GameProfile(uUID, jsonObject.get("name").getAsString());
        }
        return null;
    }
}

