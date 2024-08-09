/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.minecraft;

import com.mojang.authlib.GameProfile;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class InsecureTextureException
extends RuntimeException {
    public InsecureTextureException(String string) {
        super(string);
    }

    public static class MissingTextureException
    extends InsecureTextureException {
        public MissingTextureException() {
            super("No texture information found");
        }
    }

    public static class WrongTextureOwnerException
    extends InsecureTextureException {
        private final GameProfile expected;
        private final UUID resultId;
        private final String resultName;

        public WrongTextureOwnerException(GameProfile gameProfile, UUID uUID, String string) {
            super("Decrypted textures payload was for another user (expected " + gameProfile.getId() + "/" + gameProfile.getName() + " but was for " + uUID + "/" + string + ")");
            this.expected = gameProfile;
            this.resultId = uUID;
            this.resultName = string;
        }
    }

    public static class OutdatedTextureException
    extends InsecureTextureException {
        private final Date validFrom;
        private final Calendar limit;

        public OutdatedTextureException(Date date, Calendar calendar) {
            super("Decrypted textures payload is too old (" + date + ", but we need it to be at least " + calendar + ")");
            this.validFrom = date;
            this.limit = calendar;
        }
    }
}

