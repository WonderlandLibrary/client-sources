/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;
import net.minecraft.server.management.ProfileBanEntry;
import net.minecraft.server.management.UserList;
import net.minecraft.server.management.UserListEntry;

public class BanList
extends UserList<GameProfile, ProfileBanEntry> {
    public BanList(File file) {
        super(file);
    }

    @Override
    protected UserListEntry<GameProfile> createEntry(JsonObject jsonObject) {
        return new ProfileBanEntry(jsonObject);
    }

    public boolean isBanned(GameProfile gameProfile) {
        return this.hasEntry(gameProfile);
    }

    @Override
    public String[] getKeys() {
        String[] stringArray = new String[this.getEntries().size()];
        int n = 0;
        for (UserListEntry userListEntry : this.getEntries()) {
            stringArray[n++] = ((GameProfile)userListEntry.getValue()).getName();
        }
        return stringArray;
    }

    @Override
    protected String getObjectKey(GameProfile gameProfile) {
        return gameProfile.getId().toString();
    }

    @Override
    protected String getObjectKey(Object object) {
        return this.getObjectKey((GameProfile)object);
    }
}

