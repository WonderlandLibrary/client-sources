package net.minecraft.server.management;

import com.mojang.authlib.*;
import com.google.gson.*;
import java.util.*;

public class UserListBansEntry extends BanEntry<GameProfile>
{
    private static final String[] I;
    
    private static void I() {
        (I = new String[0xB4 ^ 0xB3])["".length()] = I("\r8\u0018\"", "xMqFG");
        UserListBansEntry.I[" ".length()] = I("", "ThcAA");
        UserListBansEntry.I["  ".length()] = I("\u001c\u0017?\u000e", "rvRkQ");
        UserListBansEntry.I["   ".length()] = I("\u00112\u000b\u000f", "dGbkc");
        UserListBansEntry.I[0x94 ^ 0x90] = I("\u0000\u0013\u001c\u0011", "nrqtq");
        UserListBansEntry.I[0x5E ^ 0x5B] = I("\u000f7\u0004\u0016", "zBmrP");
        UserListBansEntry.I[0xAF ^ 0xA9] = I("!2!\u0000", "OSLeB");
    }
    
    public UserListBansEntry(final GameProfile gameProfile, final Date date, final String s, final Date date2, final String s2) {
        super(gameProfile, date2, s, date2, s2);
    }
    
    private static GameProfile func_152648_b(final JsonObject jsonObject) {
        if (jsonObject.has(UserListBansEntry.I["   ".length()]) && jsonObject.has(UserListBansEntry.I[0x8A ^ 0x8E])) {
            final String asString = jsonObject.get(UserListBansEntry.I[0xAF ^ 0xAA]).getAsString();
            UUID fromString;
            try {
                fromString = UUID.fromString(asString);
                "".length();
                if (2 == 3) {
                    throw null;
                }
            }
            catch (Throwable t) {
                return null;
            }
            return new GameProfile(fromString, jsonObject.get(UserListBansEntry.I[0x20 ^ 0x26]).getAsString());
        }
        return null;
    }
    
    static {
        I();
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
            if (3 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public UserListBansEntry(final JsonObject jsonObject) {
        super(func_152648_b(jsonObject), jsonObject);
    }
    
    public UserListBansEntry(final GameProfile gameProfile) {
        this(gameProfile, null, null, null, null);
    }
    
    @Override
    protected void onSerialization(final JsonObject jsonObject) {
        if (this.getValue() != null) {
            final String s = UserListBansEntry.I["".length()];
            String string;
            if (this.getValue().getId() == null) {
                string = UserListBansEntry.I[" ".length()];
                "".length();
                if (4 == 0) {
                    throw null;
                }
            }
            else {
                string = this.getValue().getId().toString();
            }
            jsonObject.addProperty(s, string);
            jsonObject.addProperty(UserListBansEntry.I["  ".length()], this.getValue().getName());
            super.onSerialization(jsonObject);
        }
    }
}
