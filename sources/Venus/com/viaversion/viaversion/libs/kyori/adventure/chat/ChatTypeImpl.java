/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.chat;

import com.viaversion.viaversion.libs.kyori.adventure.chat.ChatType;
import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class ChatTypeImpl
implements ChatType {
    private final Key key;

    ChatTypeImpl(@NotNull Key key) {
        this.key = key;
    }

    @Override
    @NotNull
    public Key key() {
        return this.key;
    }

    public String toString() {
        return Internals.toString(this);
    }

    static final class BoundImpl
    implements ChatType.Bound {
        private final ChatType chatType;
        private final Component name;
        @Nullable
        private final Component target;

        BoundImpl(ChatType chatType, Component component, @Nullable Component component2) {
            this.chatType = chatType;
            this.name = component;
            this.target = component2;
        }

        @Override
        @NotNull
        public ChatType type() {
            return this.chatType;
        }

        @Override
        @NotNull
        public Component name() {
            return this.name;
        }

        @Override
        @Nullable
        public Component target() {
            return this.target;
        }

        public String toString() {
            return Internals.toString(this);
        }
    }
}

