package net.minecraft.server.management;

import java.io.*;
import com.google.gson.*;
import java.net.*;

public class BanList extends UserList
{
    public BanList(final File bansFile) {
        super(bansFile);
    }
    
    @Override
    protected UserListEntry createEntry(final JsonObject entryData) {
        return new IPBanEntry(entryData);
    }
    
    public boolean isBanned(final SocketAddress address) {
        final String var2 = this.addressToString(address);
        return this.hasEntry(var2);
    }
    
    public IPBanEntry getBanEntry(final SocketAddress address) {
        final String var2 = this.addressToString(address);
        return (IPBanEntry)this.getEntry(var2);
    }
    
    private String addressToString(final SocketAddress address) {
        String var2 = address.toString();
        if (var2.contains("/")) {
            var2 = var2.substring(var2.indexOf(47) + 1);
        }
        if (var2.contains(":")) {
            var2 = var2.substring(0, var2.indexOf(58));
        }
        return var2;
    }
}
