package net.minecraft.server.management;

import com.mojang.authlib.*;
import com.google.gson.*;
import java.util.*;

public class UserListWhitelistEntry extends UserListEntry<GameProfile>
{
    private static final String[] I;
    
    private static GameProfile gameProfileFromJsonObject(final JsonObject jsonObject) {
        if (jsonObject.has(UserListWhitelistEntry.I["   ".length()]) && jsonObject.has(UserListWhitelistEntry.I[0xC7 ^ 0xC3])) {
            final String asString = jsonObject.get(UserListWhitelistEntry.I[0x75 ^ 0x70]).getAsString();
            UUID fromString;
            try {
                fromString = UUID.fromString(asString);
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
            catch (Throwable t) {
                return null;
            }
            return new GameProfile(fromString, jsonObject.get(UserListWhitelistEntry.I[0x9D ^ 0x9B]).getAsString());
        }
        return null;
    }
    
    public UserListWhitelistEntry(final GameProfile gameProfile) {
        super(gameProfile);
    }
    
    private static void I() {
        (I = new String[0x71 ^ 0x76])["".length()] = I("\u001f\r\n\u000b", "jxcol");
        UserListWhitelistEntry.I[" ".length()] = I("", "IOpDk");
        UserListWhitelistEntry.I["  ".length()] = I("\u001b-\u0003\r", "uLnhZ");
        UserListWhitelistEntry.I["   ".length()] = I("/\u001c&#", "ZiOGx");
        UserListWhitelistEntry.I[0xF ^ 0xB] = I(" \u0003\u0014\u0007", "Nbybe");
        UserListWhitelistEntry.I[0xC2 ^ 0xC7] = I(" &\b,", "USaHl");
        UserListWhitelistEntry.I[0x1C ^ 0x1A] = I("\u00171><", "yPSYa");
    }
    
    @Override
    protected void onSerialization(final JsonObject jsonObject) {
        if (this.getValue() != null) {
            final String s = UserListWhitelistEntry.I["".length()];
            String string;
            if (this.getValue().getId() == null) {
                string = UserListWhitelistEntry.I[" ".length()];
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            else {
                string = this.getValue().getId().toString();
            }
            jsonObject.addProperty(s, string);
            jsonObject.addProperty(UserListWhitelistEntry.I["  ".length()], this.getValue().getName());
            super.onSerialization(jsonObject);
        }
    }
    
    public UserListWhitelistEntry(final JsonObject jsonObject) {
        super(gameProfileFromJsonObject(jsonObject), jsonObject);
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
}
