package net.minecraft.server.management;

import com.mojang.authlib.*;
import java.util.*;
import java.io.*;
import com.google.gson.*;

public class UserListWhitelist extends UserList<GameProfile, UserListWhitelistEntry>
{
    public GameProfile func_152706_a(final String s) {
        final Iterator<UserListWhitelistEntry> iterator = ((UserList<K, UserListWhitelistEntry>)this).getValues().values().iterator();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            final UserListWhitelistEntry userListWhitelistEntry = iterator.next();
            if (s.equalsIgnoreCase(userListWhitelistEntry.getValue().getName())) {
                return userListWhitelistEntry.getValue();
            }
        }
        return null;
    }
    
    public UserListWhitelist(final File file) {
        super(file);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String[] getKeys() {
        final String[] array = new String[((UserList<K, UserListWhitelistEntry>)this).getValues().size()];
        int length = "".length();
        final Iterator<UserListWhitelistEntry> iterator = ((UserList<K, UserListWhitelistEntry>)this).getValues().values().iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            array[length++] = iterator.next().getValue().getName();
        }
        return array;
    }
    
    @Override
    protected String getObjectKey(final GameProfile gameProfile) {
        return gameProfile.getId().toString();
    }
    
    @Override
    protected String getObjectKey(final Object o) {
        return this.getObjectKey((GameProfile)o);
    }
    
    @Override
    protected UserListEntry<GameProfile> createEntry(final JsonObject jsonObject) {
        return new UserListWhitelistEntry(jsonObject);
    }
}
