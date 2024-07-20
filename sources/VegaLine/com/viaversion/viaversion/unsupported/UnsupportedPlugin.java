/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.unsupported;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.UnsupportedSoftware;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class UnsupportedPlugin
implements UnsupportedSoftware {
    private final String name;
    private final List<String> identifiers;
    private final String reason;

    public UnsupportedPlugin(String name, List<String> identifiers, String reason) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(reason);
        Preconditions.checkArgument(!identifiers.isEmpty());
        this.name = name;
        this.identifiers = Collections.unmodifiableList(identifiers);
        this.reason = reason;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public final @Nullable String match() {
        for (String identifier : this.identifiers) {
            if (!Via.getPlatform().hasPlugin(identifier)) continue;
            return identifier;
        }
        return null;
    }

    public static final class Reason {
        public static final String SECURE_CHAT_BYPASS = "Instead of doing the obvious (or nothing at all), these kinds of plugins completely break chat message handling, usually then also breaking other plugins.";
    }

    public static final class Builder {
        private final List<String> identifiers = new ArrayList<String>();
        private String name;
        private String reason;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder addPlugin(String identifier) {
            this.identifiers.add(identifier);
            return this;
        }

        public UnsupportedPlugin build() {
            return new UnsupportedPlugin(this.name, this.identifiers, this.reason);
        }
    }
}

