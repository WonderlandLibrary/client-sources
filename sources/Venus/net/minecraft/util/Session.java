/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class Session {
    private final String username;
    private final String playerID;
    private final String token;
    private final Type sessionType;

    public Session(String string, String string2, String string3, String string4) {
        this.username = string;
        this.playerID = string2;
        this.token = string3;
        this.sessionType = Type.setSessionType(string4);
    }

    public String getSessionID() {
        return "token:" + this.token + ":" + this.playerID;
    }

    public String getPlayerID() {
        return this.playerID;
    }

    public String getUsername() {
        return this.username;
    }

    public String getToken() {
        return this.token;
    }

    public GameProfile getProfile() {
        try {
            UUID uUID = UUIDTypeAdapter.fromString(this.getPlayerID());
            return new GameProfile(uUID, this.getUsername());
        } catch (IllegalArgumentException illegalArgumentException) {
            return new GameProfile(null, this.getUsername());
        }
    }

    public static enum Type {
        LEGACY("legacy"),
        MOJANG("mojang");

        private static final Map<String, Type> SESSION_TYPES;
        private final String sessionType;

        private Type(String string2) {
            this.sessionType = string2;
        }

        @Nullable
        public static Type setSessionType(String string) {
            return SESSION_TYPES.get(string.toLowerCase(Locale.ROOT));
        }

        private static String lambda$static$0(Type type) {
            return type.sessionType;
        }

        static {
            SESSION_TYPES = Arrays.stream(Type.values()).collect(Collectors.toMap(Type::lambda$static$0, Function.identity()));
        }
    }
}

