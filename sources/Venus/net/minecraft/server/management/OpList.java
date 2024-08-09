/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;
import net.minecraft.server.management.OpEntry;
import net.minecraft.server.management.UserList;
import net.minecraft.server.management.UserListEntry;

public class OpList
extends UserList<GameProfile, OpEntry> {
    public OpList(File file) {
        super(file);
    }

    @Override
    protected UserListEntry<GameProfile> createEntry(JsonObject jsonObject) {
        return new OpEntry(jsonObject);
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

    public boolean bypassesPlayerLimit(GameProfile gameProfile) {
        OpEntry opEntry = (OpEntry)this.getEntry(gameProfile);
        return opEntry != null ? opEntry.bypassesPlayerLimit() : false;
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

