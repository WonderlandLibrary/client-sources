/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.kyori.adventure.text.Component
 *  org.spongepowered.api.entity.living.player.server.ServerPlayer
 */
package com.viaversion.viaversion.sponge.commands;

import com.viaversion.viaversion.SpongePlugin;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

public class SpongePlayer
implements ViaCommandSender {
    private final ServerPlayer player;

    public SpongePlayer(ServerPlayer serverPlayer) {
        this.player = serverPlayer;
    }

    @Override
    public boolean hasPermission(String string) {
        return this.player.hasPermission(string);
    }

    @Override
    public void sendMessage(String string) {
        this.player.sendMessage((Component)SpongePlugin.LEGACY_SERIALIZER.deserialize(string));
    }

    @Override
    public UUID getUUID() {
        return this.player.uniqueId();
    }

    @Override
    public String getName() {
        return this.player.friendlyIdentifier().orElse(this.player.identifier());
    }
}

