/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.identity;

import com.viaversion.viaversion.libs.kyori.adventure.identity.Identity;
import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class IdentityImpl
implements Examinable,
Identity {
    private final UUID uuid;

    IdentityImpl(UUID uUID) {
        this.uuid = uUID;
    }

    @Override
    @NotNull
    public UUID uuid() {
        return this.uuid;
    }

    public String toString() {
        return Internals.toString(this);
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Identity)) {
            return true;
        }
        Identity identity = (Identity)object;
        return this.uuid.equals(identity.uuid());
    }

    public int hashCode() {
        return this.uuid.hashCode();
    }
}

