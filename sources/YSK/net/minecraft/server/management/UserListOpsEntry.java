package net.minecraft.server.management;

import com.mojang.authlib.*;
import com.google.gson.*;
import java.util.*;

public class UserListOpsEntry extends UserListEntry<GameProfile>
{
    private final boolean field_183025_b;
    private final int field_152645_a;
    private static final String[] I;
    
    public boolean func_183024_b() {
        return this.field_183025_b;
    }
    
    @Override
    protected void onSerialization(final JsonObject jsonObject) {
        if (this.getValue() != null) {
            final String s = UserListOpsEntry.I[0x14 ^ 0x10];
            String string;
            if (this.getValue().getId() == null) {
                string = UserListOpsEntry.I[0xC6 ^ 0xC3];
                "".length();
                if (4 < 3) {
                    throw null;
                }
            }
            else {
                string = this.getValue().getId().toString();
            }
            jsonObject.addProperty(s, string);
            jsonObject.addProperty(UserListOpsEntry.I[0x85 ^ 0x83], this.getValue().getName());
            super.onSerialization(jsonObject);
            jsonObject.addProperty(UserListOpsEntry.I[0xAD ^ 0xAA], (Number)this.field_152645_a);
            jsonObject.addProperty(UserListOpsEntry.I[0x53 ^ 0x5B], this.field_183025_b);
        }
    }
    
    private static void I() {
        (I = new String[0x98 ^ 0x95])["".length()] = I("\u0006\u000b\u001e\u0002\b", "jnhgd");
        UserListOpsEntry.I[" ".length()] = I("?\n9\u0014\u0019", "SoOqu");
        UserListOpsEntry.I["  ".length()] = I("(<\u00112\"9 \u0012\u0003=+<\u0004!\u001d#(\b'", "JEaSQ");
        UserListOpsEntry.I["   ".length()] = I("\u000e*\"0\u0016\u001f6!\u0001\t\r*7#)\u0005>;%", "lSRQe");
        UserListOpsEntry.I[0x25 ^ 0x21] = I("\u0012\u001b\u0010!", "gnyEz");
        UserListOpsEntry.I[0x31 ^ 0x34] = I("", "OXztA");
        UserListOpsEntry.I[0x48 ^ 0x4E] = I("8\u00128\u0006", "VsUcN");
        UserListOpsEntry.I[0x56 ^ 0x51] = I("\u001a.8,\u000f", "vKNIc");
        UserListOpsEntry.I[0xBA ^ 0xB2] = I(".*);!?6*\n>-*<(\u001e%>0.", "LSYZR");
        UserListOpsEntry.I[0x14 ^ 0x1D] = I("\r<\"!", "xIKEr");
        UserListOpsEntry.I[0xAA ^ 0xA0] = I("8\u0012$\u000e", "VsIkJ");
        UserListOpsEntry.I[0x44 ^ 0x4F] = I("\u00120$\u0001", "gEMeX");
        UserListOpsEntry.I[0x8D ^ 0x81] = I(" \r#)", "NlNLQ");
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public UserListOpsEntry(final JsonObject jsonObject) {
        super(func_152643_b(jsonObject), jsonObject);
        int field_152645_a;
        if (jsonObject.has(UserListOpsEntry.I["".length()])) {
            field_152645_a = jsonObject.get(UserListOpsEntry.I[" ".length()]).getAsInt();
            "".length();
            if (3 == -1) {
                throw null;
            }
        }
        else {
            field_152645_a = "".length();
        }
        this.field_152645_a = field_152645_a;
        int field_183025_b;
        if (jsonObject.has(UserListOpsEntry.I["  ".length()]) && jsonObject.get(UserListOpsEntry.I["   ".length()]).getAsBoolean()) {
            field_183025_b = " ".length();
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else {
            field_183025_b = "".length();
        }
        this.field_183025_b = (field_183025_b != 0);
    }
    
    public UserListOpsEntry(final GameProfile gameProfile, final int field_152645_a, final boolean field_183025_b) {
        super(gameProfile);
        this.field_152645_a = field_152645_a;
        this.field_183025_b = field_183025_b;
    }
    
    public int getPermissionLevel() {
        return this.field_152645_a;
    }
    
    private static GameProfile func_152643_b(final JsonObject jsonObject) {
        if (jsonObject.has(UserListOpsEntry.I[0xAE ^ 0xA7]) && jsonObject.has(UserListOpsEntry.I[0x9D ^ 0x97])) {
            final String asString = jsonObject.get(UserListOpsEntry.I[0x83 ^ 0x88]).getAsString();
            UUID fromString;
            try {
                fromString = UUID.fromString(asString);
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            catch (Throwable t) {
                return null;
            }
            return new GameProfile(fromString, jsonObject.get(UserListOpsEntry.I[0x73 ^ 0x7F]).getAsString());
        }
        return null;
    }
    
    static {
        I();
    }
}
