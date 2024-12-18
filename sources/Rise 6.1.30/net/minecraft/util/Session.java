package net.minecraft.util;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;

import java.util.Map;
import java.util.UUID;

public class Session {
    private final String username;
    private final String playerID;
    private final String token;
    private final Session.Type sessionType;

    public Session(final String usernameIn, final String playerIDIn, final String accessToken, final String sessionTypeIn) {
        this.username = usernameIn;
        this.playerID = playerIDIn;
        this.token = accessToken;
        this.sessionType = Session.Type.setSessionType(sessionTypeIn);
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
            final UUID uuid = UUIDTypeAdapter.fromString(this.getPlayerID());
            return new GameProfile(uuid, this.getUsername());
        } catch (final IllegalArgumentException var2) {
            return new GameProfile(null, this.getUsername());
        }
    }

    /**
     * Returns either 'legacy' or 'mojang' whether the account is migrated or not
     */
    public Session.Type getSessionType() {
        return this.sessionType;
    }

    public enum Type {
        LEGACY("legacy"),
        MOJANG("mojang");

        private static final Map<String, Session.Type> SESSION_TYPES = Maps.newHashMap();
        private final String sessionType;

        Type(final String sessionTypeIn) {
            this.sessionType = sessionTypeIn;
        }

        public static Session.Type setSessionType(final String sessionTypeIn) {
            return SESSION_TYPES.get(sessionTypeIn.toLowerCase());
        }

        static {
            for (final Session.Type session$type : values()) {
                SESSION_TYPES.put(session$type.sessionType, session$type);
            }
        }
    }
}
