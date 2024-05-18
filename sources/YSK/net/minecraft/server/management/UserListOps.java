package net.minecraft.server.management;

import com.mojang.authlib.*;
import java.util.*;
import java.io.*;
import com.google.gson.*;

public class UserListOps extends UserList<GameProfile, UserListOpsEntry>
{
    @Override
    public String[] getKeys() {
        final String[] array = new String[((UserList<K, UserListOpsEntry>)this).getValues().size()];
        int length = "".length();
        final Iterator<UserListOpsEntry> iterator = ((UserList<K, UserListOpsEntry>)this).getValues().values().iterator();
        "".length();
        if (0 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            array[length++] = iterator.next().getValue().getName();
        }
        return array;
    }
    
    @Override
    protected String getObjectKey(final Object o) {
        return this.getObjectKey((GameProfile)o);
    }
    
    @Override
    protected String getObjectKey(final GameProfile gameProfile) {
        return gameProfile.getId().toString();
    }
    
    public boolean func_183026_b(final GameProfile gameProfile) {
        final UserListOpsEntry userListOpsEntry = this.getEntry(gameProfile);
        int n;
        if (userListOpsEntry != null) {
            n = (userListOpsEntry.func_183024_b() ? 1 : 0);
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
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
            if (4 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GameProfile getGameProfileFromName(final String s) {
        final Iterator<UserListOpsEntry> iterator = ((UserList<K, UserListOpsEntry>)this).getValues().values().iterator();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final UserListOpsEntry userListOpsEntry = iterator.next();
            if (s.equalsIgnoreCase(userListOpsEntry.getValue().getName())) {
                return userListOpsEntry.getValue();
            }
        }
        return null;
    }
    
    public UserListOps(final File file) {
        super(file);
    }
    
    @Override
    protected UserListEntry<GameProfile> createEntry(final JsonObject jsonObject) {
        return new UserListOpsEntry(jsonObject);
    }
}
