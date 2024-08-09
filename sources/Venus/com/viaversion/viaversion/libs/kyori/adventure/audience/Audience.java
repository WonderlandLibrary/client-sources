/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 */
package com.viaversion.viaversion.libs.kyori.adventure.audience;

import com.viaversion.viaversion.libs.kyori.adventure.audience.Audiences;
import com.viaversion.viaversion.libs.kyori.adventure.audience.EmptyAudience;
import com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience;
import com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudienceOverrideNotRequired;
import com.viaversion.viaversion.libs.kyori.adventure.audience.MessageType;
import com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar;
import com.viaversion.viaversion.libs.kyori.adventure.chat.SignedMessage;
import com.viaversion.viaversion.libs.kyori.adventure.identity.Identified;
import com.viaversion.viaversion.libs.kyori.adventure.identity.Identity;
import com.viaversion.viaversion.libs.kyori.adventure.inventory.Book;
import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointered;
import com.viaversion.viaversion.libs.kyori.adventure.sound.Sound;
import com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.title.Title;
import com.viaversion.viaversion.libs.kyori.adventure.title.TitlePart;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public interface Audience
extends Pointered {
    @NotNull
    public static Audience empty() {
        return EmptyAudience.INSTANCE;
    }

    @NotNull
    public static Audience audience(@NotNull @NotNull Audience @NotNull ... audienceArray) {
        int n = audienceArray.length;
        if (n == 0) {
            return Audience.empty();
        }
        if (n == 1) {
            return audienceArray[0];
        }
        return Audience.audience(Arrays.asList(audienceArray));
    }

    @NotNull
    public static ForwardingAudience audience(@NotNull Iterable<? extends Audience> iterable) {
        return () -> Audience.lambda$audience$0(iterable);
    }

    @NotNull
    public static Collector<? super Audience, ?, ForwardingAudience> toAudience() {
        return Audiences.COLLECTOR;
    }

    @NotNull
    default public Audience filterAudience(@NotNull Predicate<? super Audience> predicate) {
        return predicate.test(this) ? this : Audience.empty();
    }

    default public void forEachAudience(@NotNull Consumer<? super Audience> consumer) {
        consumer.accept(this);
    }

    @ForwardingAudienceOverrideNotRequired
    default public void sendMessage(@NotNull ComponentLike componentLike) {
        this.sendMessage(componentLike.asComponent());
    }

    default public void sendMessage(@NotNull Component component) {
        this.sendMessage(component, MessageType.SYSTEM);
    }

    @Deprecated
    @ForwardingAudienceOverrideNotRequired
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    default public void sendMessage(@NotNull ComponentLike componentLike, @NotNull MessageType messageType) {
        this.sendMessage(componentLike.asComponent(), messageType);
    }

    @Deprecated
    @ForwardingAudienceOverrideNotRequired
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    default public void sendMessage(@NotNull Component component, @NotNull MessageType messageType) {
        this.sendMessage(Identity.nil(), component, messageType);
    }

    @Deprecated
    @ForwardingAudienceOverrideNotRequired
    default public void sendMessage(@NotNull Identified identified, @NotNull ComponentLike componentLike) {
        this.sendMessage(identified, componentLike.asComponent());
    }

    @Deprecated
    @ForwardingAudienceOverrideNotRequired
    default public void sendMessage(@NotNull Identity identity, @NotNull ComponentLike componentLike) {
        this.sendMessage(identity, componentLike.asComponent());
    }

    @Deprecated
    @ForwardingAudienceOverrideNotRequired
    default public void sendMessage(@NotNull Identified identified, @NotNull Component component) {
        this.sendMessage(identified, component, MessageType.CHAT);
    }

    @Deprecated
    @ForwardingAudienceOverrideNotRequired
    default public void sendMessage(@NotNull Identity identity, @NotNull Component component) {
        this.sendMessage(identity, component, MessageType.CHAT);
    }

    @Deprecated
    @ForwardingAudienceOverrideNotRequired
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    default public void sendMessage(@NotNull Identified identified, @NotNull ComponentLike componentLike, @NotNull MessageType messageType) {
        this.sendMessage(identified, componentLike.asComponent(), messageType);
    }

    @Deprecated
    @ForwardingAudienceOverrideNotRequired
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    default public void sendMessage(@NotNull Identity identity, @NotNull ComponentLike componentLike, @NotNull MessageType messageType) {
        this.sendMessage(identity, componentLike.asComponent(), messageType);
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    default public void sendMessage(@NotNull Identified identified, @NotNull Component component, @NotNull MessageType messageType) {
        this.sendMessage(identified.identity(), component, messageType);
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    default public void sendMessage(@NotNull Identity identity, @NotNull Component component, @NotNull MessageType messageType) {
    }

    default public void sendMessage(@NotNull Component component,  @NotNull ChatType.Bound bound) {
        this.sendMessage(component, MessageType.CHAT);
    }

    @ForwardingAudienceOverrideNotRequired
    default public void sendMessage(@NotNull ComponentLike componentLike,  @NotNull ChatType.Bound bound) {
        this.sendMessage(componentLike.asComponent(), bound);
    }

    default public void sendMessage(@NotNull SignedMessage signedMessage,  @NotNull ChatType.Bound bound) {
        Component component;
        Component component2 = component = signedMessage.unsignedContent() != null ? signedMessage.unsignedContent() : Component.text(signedMessage.message());
        if (signedMessage.isSystem()) {
            this.sendMessage(component);
        } else {
            this.sendMessage(signedMessage.identity(), component, MessageType.CHAT);
        }
    }

    @ForwardingAudienceOverrideNotRequired
    default public void deleteMessage(@NotNull SignedMessage signedMessage) {
        if (signedMessage.canDelete()) {
            this.deleteMessage(Objects.requireNonNull(signedMessage.signature()));
        }
    }

    default public void deleteMessage(@NotNull SignedMessage.Signature signature) {
    }

    @ForwardingAudienceOverrideNotRequired
    default public void sendActionBar(@NotNull ComponentLike componentLike) {
        this.sendActionBar(componentLike.asComponent());
    }

    default public void sendActionBar(@NotNull Component component) {
    }

    @ForwardingAudienceOverrideNotRequired
    default public void sendPlayerListHeader(@NotNull ComponentLike componentLike) {
        this.sendPlayerListHeader(componentLike.asComponent());
    }

    default public void sendPlayerListHeader(@NotNull Component component) {
        this.sendPlayerListHeaderAndFooter(component, Component.empty());
    }

    @ForwardingAudienceOverrideNotRequired
    default public void sendPlayerListFooter(@NotNull ComponentLike componentLike) {
        this.sendPlayerListFooter(componentLike.asComponent());
    }

    default public void sendPlayerListFooter(@NotNull Component component) {
        this.sendPlayerListHeaderAndFooter(Component.empty(), component);
    }

    @ForwardingAudienceOverrideNotRequired
    default public void sendPlayerListHeaderAndFooter(@NotNull ComponentLike componentLike, @NotNull ComponentLike componentLike2) {
        this.sendPlayerListHeaderAndFooter(componentLike.asComponent(), componentLike2.asComponent());
    }

    default public void sendPlayerListHeaderAndFooter(@NotNull Component component, @NotNull Component component2) {
    }

    @ForwardingAudienceOverrideNotRequired
    default public void showTitle(@NotNull Title title) {
        Title.Times times = title.times();
        if (times != null) {
            this.sendTitlePart(TitlePart.TIMES, times);
        }
        this.sendTitlePart(TitlePart.SUBTITLE, title.subtitle());
        this.sendTitlePart(TitlePart.TITLE, title.title());
    }

    default public <T> void sendTitlePart(@NotNull TitlePart<T> titlePart, @NotNull T t) {
    }

    default public void clearTitle() {
    }

    default public void resetTitle() {
    }

    default public void showBossBar(@NotNull BossBar bossBar) {
    }

    default public void hideBossBar(@NotNull BossBar bossBar) {
    }

    default public void playSound(@NotNull Sound sound) {
    }

    default public void playSound(@NotNull Sound sound, double d, double d2, double d3) {
    }

    default public void playSound(@NotNull Sound sound, @NotNull Sound.Emitter emitter) {
    }

    @ForwardingAudienceOverrideNotRequired
    default public void stopSound(@NotNull Sound sound) {
        this.stopSound(Objects.requireNonNull(sound, "sound").asStop());
    }

    default public void stopSound(@NotNull SoundStop soundStop) {
    }

    @ForwardingAudienceOverrideNotRequired
    default public void openBook(@NotNull Book.Builder builder) {
        this.openBook(builder.build());
    }

    default public void openBook(@NotNull Book book) {
    }

    private static Iterable lambda$audience$0(Iterable iterable) {
        return iterable;
    }
}

