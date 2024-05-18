package net.minecraft.server.management;

import com.mojang.authlib.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;

public class UserListBans extends UserList<GameProfile, UserListBansEntry>
{
    public boolean isBanned(final GameProfile gameProfile) {
        return ((UserList<GameProfile, V>)this).hasEntry(gameProfile);
    }
    
    public UserListBans(final File file) {
        super(file);
    }
    
    @Override
    public String[] getKeys() {
        final String[] array = new String[((UserList<K, UserListBansEntry>)this).getValues().size()];
        int length = "".length();
        final Iterator<UserListBansEntry> iterator = ((UserList<K, UserListBansEntry>)this).getValues().values().iterator();
        "".length();
        if (-1 == 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            array[length++] = iterator.next().getValue().getName();
        }
        return array;
    }
    
    public GameProfile isUsernameBanned(final String s) {
        final Iterator<UserListBansEntry> iterator = ((UserList<K, UserListBansEntry>)this).getValues().values().iterator();
        "".length();
        if (2 < 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final UserListBansEntry userListBansEntry = iterator.next();
            if (s.equalsIgnoreCase(userListBansEntry.getValue().getName())) {
                return userListBansEntry.getValue();
            }
        }
        return null;
    }
    
    @Override
    protected UserListEntry<GameProfile> createEntry(final JsonObject jsonObject) {
        return new UserListBansEntry(jsonObject);
    }
    
    @Override
    protected String getObjectKey(final GameProfile gameProfile) {
        return gameProfile.getId().toString();
    }
    
    @Override
    protected String getObjectKey(final Object o) {
        return this.getObjectKey((GameProfile)o);
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
