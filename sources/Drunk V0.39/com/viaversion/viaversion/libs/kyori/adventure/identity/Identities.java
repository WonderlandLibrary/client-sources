/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.kyori.adventure.identity;

import com.viaversion.viaversion.libs.kyori.adventure.identity.Identity;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

final class Identities {
    static final Identity NIL = new Identity(){
        private final UUID uuid = new UUID(0L, 0L);

        @Override
        @NotNull
        public UUID uuid() {
            return this.uuid;
        }

        public String toString() {
            return "Identity.nil()";
        }

        public boolean equals(Object that) {
            if (this != that) return false;
            return true;
        }

        public int hashCode() {
            return 0;
        }
    };

    private Identities() {
    }
}

