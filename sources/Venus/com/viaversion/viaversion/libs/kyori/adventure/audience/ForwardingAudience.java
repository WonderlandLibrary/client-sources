/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$OverrideOnly
 *  org.jetbrains.annotations.UnknownNullability
 */
package com.viaversion.viaversion.libs.kyori.adventure.audience;

import com.viaversion.viaversion.libs.kyori.adventure.audience.Audience;
import com.viaversion.viaversion.libs.kyori.adventure.audience.MessageType;
import com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar;
import com.viaversion.viaversion.libs.kyori.adventure.chat.ChatType;
import com.viaversion.viaversion.libs.kyori.adventure.chat.SignedMessage;
import com.viaversion.viaversion.libs.kyori.adventure.identity.Identified;
import com.viaversion.viaversion.libs.kyori.adventure.identity.Identity;
import com.viaversion.viaversion.libs.kyori.adventure.inventory.Book;
import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointer;
import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointers;
import com.viaversion.viaversion.libs.kyori.adventure.sound.Sound;
import com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.title.TitlePart;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

@FunctionalInterface
public interface ForwardingAudience
extends Audience {
    @ApiStatus.OverrideOnly
    @NotNull
    public Iterable<? extends Audience> audiences();

    @Override
    @NotNull
    default public Pointers pointers() {
        return Pointers.empty();
    }

    @Override
    @NotNull
    default public Audience filterAudience(@NotNull Predicate<? super Audience> predicate) {
        @Nullable ArrayList<Audience> arrayList = null;
        for (Audience audience : this.audiences()) {
            Audience audience2;
            if (!predicate.test(audience) || (audience2 = audience.filterAudience(predicate)) == Audience.empty()) continue;
            if (arrayList == null) {
                arrayList = new ArrayList<Audience>();
            }
            arrayList.add(audience2);
        }
        return arrayList != null ? Audience.audience(arrayList) : Audience.empty();
    }

    @Override
    default public void forEachAudience(@NotNull Consumer<? super Audience> consumer) {
        for (Audience audience : this.audiences()) {
            audience.forEachAudience(consumer);
        }
    }

    @Override
    default public void sendMessage(@NotNull Component component) {
        for (Audience audience : this.audiences()) {
            audience.sendMessage(component);
        }
    }

    @Override
    default public void sendMessage(@NotNull Component component, @NotNull ChatType.Bound bound) {
        for (Audience audience : this.audiences()) {
            audience.sendMessage(component, bound);
        }
    }

    @Override
    default public void sendMessage(@NotNull SignedMessage signedMessage, @NotNull ChatType.Bound bound) {
        for (Audience audience : this.audiences()) {
            audience.sendMessage(signedMessage, bound);
        }
    }

    @Override
    default public void deleteMessage(@NotNull SignedMessage.Signature signature) {
        for (Audience audience : this.audiences()) {
            audience.deleteMessage(signature);
        }
    }

    @Override
    @Deprecated
    default public void sendMessage(@NotNull Identified identified, @NotNull Component component, @NotNull MessageType messageType) {
        for (Audience audience : this.audiences()) {
            audience.sendMessage(identified, component, messageType);
        }
    }

    @Override
    @Deprecated
    default public void sendMessage(@NotNull Identity identity, @NotNull Component component, @NotNull MessageType messageType) {
        for (Audience audience : this.audiences()) {
            audience.sendMessage(identity, component, messageType);
        }
    }

    @Override
    default public void sendActionBar(@NotNull Component component) {
        for (Audience audience : this.audiences()) {
            audience.sendActionBar(component);
        }
    }

    @Override
    default public void sendPlayerListHeader(@NotNull Component component) {
        for (Audience audience : this.audiences()) {
            audience.sendPlayerListHeader(component);
        }
    }

    @Override
    default public void sendPlayerListFooter(@NotNull Component component) {
        for (Audience audience : this.audiences()) {
            audience.sendPlayerListFooter(component);
        }
    }

    @Override
    default public void sendPlayerListHeaderAndFooter(@NotNull Component component, @NotNull Component component2) {
        for (Audience audience : this.audiences()) {
            audience.sendPlayerListHeaderAndFooter(component, component2);
        }
    }

    @Override
    default public <T> void sendTitlePart(@NotNull TitlePart<T> titlePart, @NotNull T t) {
        for (Audience audience : this.audiences()) {
            audience.sendTitlePart(titlePart, t);
        }
    }

    @Override
    default public void clearTitle() {
        for (Audience audience : this.audiences()) {
            audience.clearTitle();
        }
    }

    @Override
    default public void resetTitle() {
        for (Audience audience : this.audiences()) {
            audience.resetTitle();
        }
    }

    @Override
    default public void showBossBar(@NotNull BossBar bossBar) {
        for (Audience audience : this.audiences()) {
            audience.showBossBar(bossBar);
        }
    }

    @Override
    default public void hideBossBar(@NotNull BossBar bossBar) {
        for (Audience audience : this.audiences()) {
            audience.hideBossBar(bossBar);
        }
    }

    @Override
    default public void playSound(@NotNull Sound sound) {
        for (Audience audience : this.audiences()) {
            audience.playSound(sound);
        }
    }

    @Override
    default public void playSound(@NotNull Sound sound, double d, double d2, double d3) {
        for (Audience audience : this.audiences()) {
            audience.playSound(sound, d, d2, d3);
        }
    }

    @Override
    default public void playSound(@NotNull Sound sound, @NotNull Sound.Emitter emitter) {
        for (Audience audience : this.audiences()) {
            audience.playSound(sound, emitter);
        }
    }

    @Override
    default public void stopSound(@NotNull SoundStop soundStop) {
        for (Audience audience : this.audiences()) {
            audience.stopSound(soundStop);
        }
    }

    @Override
    default public void openBook(@NotNull Book book) {
        for (Audience audience : this.audiences()) {
            audience.openBook(book);
        }
    }

    public static interface Single
    extends ForwardingAudience {
        @ApiStatus.OverrideOnly
        @NotNull
        public Audience audience();

        @Override
        @Deprecated
        @NotNull
        default public Iterable<? extends Audience> audiences() {
            return Collections.singleton(this.audience());
        }

        @Override
        @NotNull
        default public <T> Optional<T> get(@NotNull Pointer<T> pointer) {
            return this.audience().get(pointer);
        }

        @Override
        @Contract(value="_, null -> null; _, !null -> !null")
        @Nullable
        default public <T> T getOrDefault(@NotNull Pointer<T> pointer, @Nullable T t) {
            return this.audience().getOrDefault(pointer, t);
        }

        @Override
        default public <T> @UnknownNullability T getOrDefaultFrom(@NotNull Pointer<T> pointer, @NotNull Supplier<? extends T> supplier) {
            return this.audience().getOrDefaultFrom(pointer, supplier);
        }

        @Override
        @NotNull
        default public Audience filterAudience(@NotNull Predicate<? super Audience> predicate) {
            Audience audience = this.audience();
            return predicate.test(audience) ? this : Audience.empty();
        }

        @Override
        default public void forEachAudience(@NotNull Consumer<? super Audience> consumer) {
            this.audience().forEachAudience(consumer);
        }

        @Override
        @NotNull
        default public Pointers pointers() {
            return this.audience().pointers();
        }

        @Override
        default public void sendMessage(@NotNull Component component) {
            this.audience().sendMessage(component);
        }

        @Override
        default public void sendMessage(@NotNull Component component, @NotNull ChatType.Bound bound) {
            this.audience().sendMessage(component, bound);
        }

        @Override
        default public void sendMessage(@NotNull SignedMessage signedMessage, @NotNull ChatType.Bound bound) {
            this.audience().sendMessage(signedMessage, bound);
        }

        @Override
        default public void deleteMessage(@NotNull SignedMessage.Signature signature) {
            this.audience().deleteMessage(signature);
        }

        @Override
        @Deprecated
        default public void sendMessage(@NotNull Identified identified, @NotNull Component component, @NotNull MessageType messageType) {
            this.audience().sendMessage(identified, component, messageType);
        }

        @Override
        @Deprecated
        default public void sendMessage(@NotNull Identity identity, @NotNull Component component, @NotNull MessageType messageType) {
            this.audience().sendMessage(identity, component, messageType);
        }

        @Override
        default public void sendActionBar(@NotNull Component component) {
            this.audience().sendActionBar(component);
        }

        @Override
        default public void sendPlayerListHeader(@NotNull Component component) {
            this.audience().sendPlayerListHeader(component);
        }

        @Override
        default public void sendPlayerListFooter(@NotNull Component component) {
            this.audience().sendPlayerListFooter(component);
        }

        @Override
        default public void sendPlayerListHeaderAndFooter(@NotNull Component component, @NotNull Component component2) {
            this.audience().sendPlayerListHeaderAndFooter(component, component2);
        }

        @Override
        default public <T> void sendTitlePart(@NotNull TitlePart<T> titlePart, @NotNull T t) {
            this.audience().sendTitlePart(titlePart, t);
        }

        @Override
        default public void clearTitle() {
            this.audience().clearTitle();
        }

        @Override
        default public void resetTitle() {
            this.audience().resetTitle();
        }

        @Override
        default public void showBossBar(@NotNull BossBar bossBar) {
            this.audience().showBossBar(bossBar);
        }

        @Override
        default public void hideBossBar(@NotNull BossBar bossBar) {
            this.audience().hideBossBar(bossBar);
        }

        @Override
        default public void playSound(@NotNull Sound sound) {
            this.audience().playSound(sound);
        }

        @Override
        default public void playSound(@NotNull Sound sound, double d, double d2, double d3) {
            this.audience().playSound(sound, d, d2, d3);
        }

        @Override
        default public void playSound(@NotNull Sound sound, @NotNull Sound.Emitter emitter) {
            this.audience().playSound(sound, emitter);
        }

        @Override
        default public void stopSound(@NotNull SoundStop soundStop) {
            this.audience().stopSound(soundStop);
        }

        @Override
        default public void openBook(@NotNull Book book) {
            this.audience().openBook(book);
        }
    }
}

