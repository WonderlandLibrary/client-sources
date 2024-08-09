package dev.luvbeeq.discord.rpc.utils;

import com.sun.jna.Structure;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DiscordRichPresence extends Structure {
    public String largeImageKey;
    public String largeImageText;
    public String smallImageText;
    public String partyPrivacy;
    public long startTimestamp;
    public int instance;
    public String partyId;
    public int partySize;
    public long endTimestamp;
    public String details;
    public String joinSecret;
    public String spectateSecret;
    public String smallImageKey;
    public String matchSecret;
    public String state;
    public int partyMax;
    public String button_url_1;
    public String button_label_1;
    public String button_url_2;
    public String button_label_2;

    public DiscordRichPresence() {
        this.setStringEncoding("UTF-8");
    }

    protected List<String> getFieldOrder() {
        return Arrays.asList("state", "details", "startTimestamp", "endTimestamp", "largeImageKey", "largeImageText", "smallImageKey", "smallImageText", "partyId", "partySize", "partyMax", "partyPrivacy", "matchSecret", "joinSecret", "spectateSecret", "button_label_1", "button_url_1", "button_label_2", "button_url_2", "instance");
    }

    public static class Builder {
        private final DiscordRichPresence richPresence = new DiscordRichPresence();

        public Builder setSmallImage(String var1) {
            return this.setSmallImage(var1, "");
        }

        public Builder setDetails(String var1) {
            if (var1 != null && !var1.isEmpty()) {
                this.richPresence.details = var1.substring(0, Math.min(var1.length(), 128));
            }

            return this;
        }

        public Builder setLargeImage(String var1, String var2) {
            this.richPresence.largeImageKey = var1;
            this.richPresence.largeImageText = var2;
            return this;
        }

        public Builder setState(String var1) {
            if (var1 != null && !var1.isEmpty()) {
                this.richPresence.state = var1.substring(0, Math.min(var1.length(), 128));
            }

            return this;
        }

        public Builder setInstance(boolean var1) {
            if ((this.richPresence.button_label_1 == null || !this.richPresence.button_label_1.isEmpty()) && (this.richPresence.button_label_2 == null || !this.richPresence.button_label_2.isEmpty())) {
                this.richPresence.instance = var1 ? 1 : 0;
            }
            return this;
        }

        public Builder setButtons(RPCButton var1) {
            return this.setButtons(Collections.singletonList(var1));
        }

        public Builder setSmallImage(String var1, String var2) {
            this.richPresence.smallImageKey = var1;
            this.richPresence.smallImageText = var2;
            return this;
        }

        public Builder setParty(String var1, int var2, int var3) {
            if ((this.richPresence.button_label_1 == null || !this.richPresence.button_label_1.isEmpty()) && (this.richPresence.button_label_2 == null || !this.richPresence.button_label_2.isEmpty())) {
                this.richPresence.partyId = var1;
                this.richPresence.partySize = var2;
                this.richPresence.partyMax = var3;
            }
            return this;
        }

        public Builder setButtons(List<RPCButton> var1) {
            if (var1 != null && !var1.isEmpty()) {
                int var2 = Math.min(var1.size(), 2);
                this.richPresence.button_label_1 = var1.get(0).getLabel();
                this.richPresence.button_url_1 = var1.get(0).getUrl();
                if (var2 == 2) {
                    this.richPresence.button_label_2 = var1.get(1).getLabel();
                    this.richPresence.button_url_2 = var1.get(1).getUrl();
                }
            }

            return this;
        }

        public Builder setStartTimestamp(OffsetDateTime var1) {
            this.richPresence.startTimestamp = var1.toEpochSecond();
            return this;
        }

        public Builder setSecrets(String var1, String var2, String var3) {
            if ((this.richPresence.button_label_1 == null || !this.richPresence.button_label_1.isEmpty()) && (this.richPresence.button_label_2 == null || !this.richPresence.button_label_2.isEmpty())) {
                this.richPresence.matchSecret = var1;
                this.richPresence.joinSecret = var2;
                this.richPresence.spectateSecret = var3;
            }
            return this;
        }

        public void setButtons(RPCButton var1, RPCButton var2) {
            this.setButtons(Arrays.asList(var1, var2));
        }

        public void setStartTimestamp(long var1) {
            this.richPresence.startTimestamp = var1;
        }

        public Builder setSecrets(String var1, String var2) {
            if ((this.richPresence.button_label_1 == null || !this.richPresence.button_label_1.isEmpty()) && (this.richPresence.button_label_2 == null || !this.richPresence.button_label_2.isEmpty())) {
                this.richPresence.joinSecret = var1;
                this.richPresence.spectateSecret = var2;
            }
            return this;
        }

        public Builder setEndTimestamp(long var1) {
            this.richPresence.endTimestamp = var1;
            return this;
        }

        public Builder setEndTimestamp(OffsetDateTime var1) {
            this.richPresence.endTimestamp = var1.toEpochSecond();
            return this;
        }

        public Builder setLargeImage(String var1) {
            return this.setLargeImage(var1, "");
        }

        public DiscordRichPresence build() {
            return this.richPresence;
        }
    }
}