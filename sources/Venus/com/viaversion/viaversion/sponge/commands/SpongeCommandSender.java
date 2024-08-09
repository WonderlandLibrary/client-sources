/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.kyori.adventure.identity.Identity
 *  net.kyori.adventure.text.Component
 *  org.spongepowered.api.command.CommandCause
 *  org.spongepowered.api.util.Identifiable
 */
package com.viaversion.viaversion.sponge.commands;

import com.viaversion.viaversion.SpongePlugin;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import java.util.UUID;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.util.Identifiable;

public class SpongeCommandSender
implements ViaCommandSender {
    private final CommandCause source;

    public SpongeCommandSender(CommandCause commandCause) {
        this.source = commandCause;
    }

    @Override
    public boolean hasPermission(String string) {
        return this.source.hasPermission(string);
    }

    @Override
    public void sendMessage(String string) {
        this.source.sendMessage(Identity.nil(), (Component)SpongePlugin.LEGACY_SERIALIZER.deserialize(string));
    }

    @Override
    public UUID getUUID() {
        if (this.source instanceof Identifiable) {
            return ((Identifiable)this.source).uniqueId();
        }
        return new UUID(0L, 0L);
    }

    @Override
    public String getName() {
        return this.source.friendlyIdentifier().orElse(this.source.identifier());
    }
}

