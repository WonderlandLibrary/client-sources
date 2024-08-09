/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jagrosh.discordipc.entities;

import java.time.OffsetDateTime;
import org.json.JSONArray;
import org.json.JSONObject;

public class RichPresence {
    private final String state;
    private final String details;
    private final OffsetDateTime startTimestamp;
    private final OffsetDateTime endTimestamp;
    private final String largeImageKey;
    private final String largeImageText;
    private final String smallImageKey;
    private final String smallImageText;
    private final String partyId;
    private final int partySize;
    private final int partyMax;
    private final String matchSecret;
    private final String joinSecret;
    private final String spectateSecret;
    private final boolean instance;

    public RichPresence(String string, String string2, OffsetDateTime offsetDateTime, OffsetDateTime offsetDateTime2, String string3, String string4, String string5, String string6, String string7, int n, int n2, String string8, String string9, String string10, boolean bl) {
        this.state = string;
        this.details = string2;
        this.startTimestamp = offsetDateTime;
        this.endTimestamp = offsetDateTime2;
        this.largeImageKey = string3;
        this.largeImageText = string4;
        this.smallImageKey = string5;
        this.smallImageText = string6;
        this.partyId = string7;
        this.partySize = n;
        this.partyMax = n2;
        this.matchSecret = string8;
        this.joinSecret = string9;
        this.spectateSecret = string10;
        this.instance = bl;
    }

    public JSONObject toJson() {
        return new JSONObject().put("state", this.state).put("details", this.details).put("timestamps", new JSONObject().put("start", this.startTimestamp == null ? null : Long.valueOf(this.startTimestamp.toEpochSecond())).put("end", this.endTimestamp == null ? null : Long.valueOf(this.endTimestamp.toEpochSecond()))).put("assets", new JSONObject().put("large_image", this.largeImageKey).put("large_text", this.largeImageText).put("small_image", this.smallImageKey).put("small_text", this.smallImageText)).put("party", this.partyId == null ? null : new JSONObject().put("id", this.partyId).put("size", new JSONArray().put(this.partySize).put(this.partyMax))).put("secrets", new JSONObject().put("join", this.joinSecret).put("spectate", this.spectateSecret).put("match", this.matchSecret)).put("instance", this.instance);
    }

    public static class Builder {
        private String state;
        private String details;
        private OffsetDateTime startTimestamp;
        private OffsetDateTime endTimestamp;
        private String largeImageKey;
        private String largeImageText;
        private String smallImageKey;
        private String smallImageText;
        private String partyId;
        private int partySize;
        private int partyMax;
        private String matchSecret;
        private String joinSecret;
        private String spectateSecret;
        private boolean instance;

        public RichPresence build() {
            return new RichPresence(this.state, this.details, this.startTimestamp, this.endTimestamp, this.largeImageKey, this.largeImageText, this.smallImageKey, this.smallImageText, this.partyId, this.partySize, this.partyMax, this.matchSecret, this.joinSecret, this.spectateSecret, this.instance);
        }

        public Builder setState(String string) {
            this.state = string;
            return this;
        }

        public Builder setDetails(String string) {
            this.details = string;
            return this;
        }

        public Builder setStartTimestamp(OffsetDateTime offsetDateTime) {
            this.startTimestamp = offsetDateTime;
            return this;
        }

        public Builder setEndTimestamp(OffsetDateTime offsetDateTime) {
            this.endTimestamp = offsetDateTime;
            return this;
        }

        public Builder setLargeImage(String string, String string2) {
            this.largeImageKey = string;
            this.largeImageText = string2;
            return this;
        }

        public Builder setLargeImage(String string) {
            return this.setLargeImage(string, null);
        }

        public Builder setSmallImage(String string, String string2) {
            this.smallImageKey = string;
            this.smallImageText = string2;
            return this;
        }

        public Builder setSmallImage(String string) {
            return this.setSmallImage(string, null);
        }

        public Builder setParty(String string, int n, int n2) {
            this.partyId = string;
            this.partySize = n;
            this.partyMax = n2;
            return this;
        }

        public Builder setMatchSecret(String string) {
            this.matchSecret = string;
            return this;
        }

        public Builder setJoinSecret(String string) {
            this.joinSecret = string;
            return this;
        }

        public Builder setSpectateSecret(String string) {
            this.spectateSecret = string;
            return this;
        }

        public Builder setInstance(boolean bl) {
            this.instance = bl;
            return this;
        }
    }
}

