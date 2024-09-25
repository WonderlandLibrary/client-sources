/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.spongepowered.api.command.CommandSource
 *  org.spongepowered.api.text.serializer.TextSerializers
 *  org.spongepowered.api.util.Identifiable
 */
package us.myles.ViaVersion.sponge.commands;

import java.util.UUID;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.util.Identifiable;
import us.myles.ViaVersion.api.command.ViaCommandSender;
import us.myles.viaversion.libs.bungeecordchat.api.chat.TextComponent;
import us.myles.viaversion.libs.bungeecordchat.chat.ComponentSerializer;

public class SpongeCommandSender
implements ViaCommandSender {
    private final CommandSource source;

    public SpongeCommandSender(CommandSource source) {
        this.source = source;
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.source.hasPermission(permission);
    }

    @Override
    public void sendMessage(String msg) {
        this.source.sendMessage(TextSerializers.JSON.deserialize(ComponentSerializer.toString(TextComponent.fromLegacyText(msg))));
    }

    @Override
    public UUID getUUID() {
        if (this.source instanceof Identifiable) {
            return ((Identifiable)this.source).getUniqueId();
        }
        return UUID.fromString(this.getName());
    }

    @Override
    public String getName() {
        return this.source.getName();
    }
}

