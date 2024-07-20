/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Contract
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.kyori.adventure.chat;

import com.viaversion.viaversion.libs.kyori.adventure.chat.ChatTypeImpl;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.key.Keyed;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ChatType
extends Examinable,
Keyed {
    public static final ChatType CHAT = new ChatTypeImpl(Key.key("chat"));
    public static final ChatType SAY_COMMAND = new ChatTypeImpl(Key.key("say_command"));
    public static final ChatType MSG_COMMAND_INCOMING = new ChatTypeImpl(Key.key("msg_command_incoming"));
    public static final ChatType MSG_COMMAND_OUTGOING = new ChatTypeImpl(Key.key("msg_command_outgoing"));
    public static final ChatType TEAM_MSG_COMMAND_INCOMING = new ChatTypeImpl(Key.key("team_msg_command_incoming"));
    public static final ChatType TEAM_MSG_COMMAND_OUTGOING = new ChatTypeImpl(Key.key("team_msg_command_outgoing"));
    public static final ChatType EMOTE_COMMAND = new ChatTypeImpl(Key.key("emote_command"));

    @NotNull
    public static ChatType chatType(@NotNull Keyed key) {
        return key instanceof ChatType ? (ChatType)key : new ChatTypeImpl(Objects.requireNonNull(key, "key").key());
    }

    @Contract(value="_ -> new", pure=true)
    default public @NotNull Bound bind(@NotNull ComponentLike name) {
        return this.bind(name, null);
    }

    @Contract(value="_, _ -> new", pure=true)
    default public @NotNull Bound bind(@NotNull ComponentLike name, @Nullable ComponentLike target) {
        return new ChatTypeImpl.BoundImpl(this, Objects.requireNonNull(name.asComponent(), "name"), ComponentLike.unbox(target));
    }

    @Override
    @NotNull
    default public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("key", this.key()));
    }

    public static interface Bound
    extends Examinable {
        @Contract(pure=true)
        @NotNull
        public ChatType type();

        @Contract(pure=true)
        @NotNull
        public Component name();

        @Contract(pure=true)
        @Nullable
        public Component target();

        @Override
        @NotNull
        default public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(ExaminableProperty.of("type", this.type()), ExaminableProperty.of("name", this.name()), ExaminableProperty.of("target", this.target()));
        }
    }
}

