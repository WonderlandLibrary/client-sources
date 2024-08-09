/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.UnknownNullability
 */
package com.viaversion.viaversion.libs.kyori.adventure.audience;

import com.viaversion.viaversion.libs.kyori.adventure.audience.Audience;
import com.viaversion.viaversion.libs.kyori.adventure.audience.MessageType;
import com.viaversion.viaversion.libs.kyori.adventure.chat.ChatType;
import com.viaversion.viaversion.libs.kyori.adventure.chat.SignedMessage;
import com.viaversion.viaversion.libs.kyori.adventure.identity.Identified;
import com.viaversion.viaversion.libs.kyori.adventure.identity.Identity;
import com.viaversion.viaversion.libs.kyori.adventure.inventory.Book;
import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointer;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

final class EmptyAudience
implements Audience {
    static final EmptyAudience INSTANCE = new EmptyAudience();

    EmptyAudience() {
    }

    @Override
    @NotNull
    public <T> Optional<T> get(@NotNull Pointer<T> pointer) {
        return Optional.empty();
    }

    @Override
    @Contract(value="_, null -> null; _, !null -> !null")
    @Nullable
    public <T> T getOrDefault(@NotNull Pointer<T> pointer, @Nullable T t) {
        return t;
    }

    @Override
    public <T> @UnknownNullability T getOrDefaultFrom(@NotNull Pointer<T> pointer, @NotNull Supplier<? extends T> supplier) {
        return supplier.get();
    }

    @Override
    @NotNull
    public Audience filterAudience(@NotNull Predicate<? super Audience> predicate) {
        return this;
    }

    @Override
    public void forEachAudience(@NotNull Consumer<? super Audience> consumer) {
    }

    @Override
    public void sendMessage(@NotNull ComponentLike componentLike) {
    }

    @Override
    public void sendMessage(@NotNull Component component) {
    }

    @Override
    @Deprecated
    public void sendMessage(@NotNull Identified identified, @NotNull Component component, @NotNull MessageType messageType) {
    }

    @Override
    @Deprecated
    public void sendMessage(@NotNull Identity identity, @NotNull Component component, @NotNull MessageType messageType) {
    }

    @Override
    public void sendMessage(@NotNull Component component, @NotNull ChatType.Bound bound) {
    }

    @Override
    public void sendMessage(@NotNull SignedMessage signedMessage, @NotNull ChatType.Bound bound) {
    }

    @Override
    public void deleteMessage(@NotNull SignedMessage.Signature signature) {
    }

    @Override
    public void sendActionBar(@NotNull ComponentLike componentLike) {
    }

    @Override
    public void sendPlayerListHeader(@NotNull ComponentLike componentLike) {
    }

    @Override
    public void sendPlayerListFooter(@NotNull ComponentLike componentLike) {
    }

    @Override
    public void sendPlayerListHeaderAndFooter(@NotNull ComponentLike componentLike, @NotNull ComponentLike componentLike2) {
    }

    @Override
    public void openBook(@NotNull Book.Builder builder) {
    }

    public boolean equals(Object object) {
        return this == object;
    }

    public int hashCode() {
        return 1;
    }

    public String toString() {
        return "EmptyAudience";
    }
}

