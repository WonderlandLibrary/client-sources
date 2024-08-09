/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.management;

import com.google.gson.JsonObject;
import java.io.File;
import java.net.SocketAddress;
import net.minecraft.server.management.IPBanEntry;
import net.minecraft.server.management.UserList;
import net.minecraft.server.management.UserListEntry;

public class IPBanList
extends UserList<String, IPBanEntry> {
    public IPBanList(File file) {
        super(file);
    }

    @Override
    protected UserListEntry<String> createEntry(JsonObject jsonObject) {
        return new IPBanEntry(jsonObject);
    }

    public boolean isBanned(SocketAddress socketAddress) {
        String string = this.addressToString(socketAddress);
        return this.hasEntry(string);
    }

    public boolean isBanned(String string) {
        return this.hasEntry(string);
    }

    public IPBanEntry getBanEntry(SocketAddress socketAddress) {
        String string = this.addressToString(socketAddress);
        return (IPBanEntry)this.getEntry(string);
    }

    private String addressToString(SocketAddress socketAddress) {
        String string = socketAddress.toString();
        if (string.contains("/")) {
            string = string.substring(string.indexOf(47) + 1);
        }
        if (string.contains(":")) {
            string = string.substring(0, string.indexOf(58));
        }
        return string;
    }
}

