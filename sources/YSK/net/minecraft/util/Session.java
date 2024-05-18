package net.minecraft.util;

import com.mojang.authlib.*;
import com.mojang.util.*;
import java.util.*;
import com.google.common.collect.*;

public class Session
{
    private final String playerID;
    private static final String[] I;
    private final Type sessionType;
    private final String token;
    private final String username;
    
    public String getPlayerID() {
        return this.playerID;
    }
    
    public Type getSessionType() {
        return this.sessionType;
    }
    
    public Session(final String username, final String playerID, final String token, final String sessionType) {
        this.username = username;
        this.playerID = playerID;
        this.token = token;
        this.sessionType = Type.setSessionType(sessionType);
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
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("0\u0002\u0013 \u0007~", "DmxEi");
        Session.I[" ".length()] = I("o", "UPXGW");
    }
    
    public String getSessionID() {
        return Session.I["".length()] + this.token + Session.I[" ".length()] + this.playerID;
    }
    
    static {
        I();
    }
    
    public GameProfile getProfile() {
        try {
            return new GameProfile(UUIDTypeAdapter.fromString(this.getPlayerID()), this.getUsername());
        }
        catch (IllegalArgumentException ex) {
            return new GameProfile((UUID)null, this.getUsername());
        }
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getToken() {
        return this.token;
    }
    
    public enum Type
    {
        private static final Type[] ENUM$VALUES;
        private static final Map<String, Type> SESSION_TYPES;
        
        LEGACY(Type.I["".length()], "".length(), Type.I[" ".length()]), 
        MOJANG(Type.I["  ".length()], " ".length(), Type.I["   ".length()]);
        
        private static final String[] I;
        private final String sessionType;
        
        private static void I() {
            (I = new String[0x67 ^ 0x63])["".length()] = I("\u00020\u0015\u0015\u0006\u0017", "NuRTE");
            Type.I[" ".length()] = I("<?6\u0005+)", "PZQdH");
            Type.I["  ".length()] = I("'+ \u0007\u001d-", "jdjFS");
            Type.I["   ".length()] = I(")8\u001f-\u0006#", "DWuLh");
        }
        
        public static Type setSessionType(final String s) {
            return Type.SESSION_TYPES.get(s.toLowerCase());
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
                if (3 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
            final Type[] enum$VALUES = new Type["  ".length()];
            enum$VALUES["".length()] = Type.LEGACY;
            enum$VALUES[" ".length()] = Type.MOJANG;
            ENUM$VALUES = enum$VALUES;
            SESSION_TYPES = Maps.newHashMap();
            final Type[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
            while (i < length) {
                final Type type = values[i];
                Type.SESSION_TYPES.put(type.sessionType, type);
                ++i;
            }
        }
        
        private Type(final String s, final int n, final String sessionType) {
            this.sessionType = sessionType;
        }
    }
}
