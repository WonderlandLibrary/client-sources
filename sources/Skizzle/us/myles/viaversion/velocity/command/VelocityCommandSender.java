/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.velocitypowered.api.command.CommandSource
 *  com.velocitypowered.api.proxy.Player
 *  net.kyori.text.serializer.gson.GsonComponentSerializer
 */
package us.myles.ViaVersion.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import java.util.UUID;
import net.kyori.text.serializer.gson.GsonComponentSerializer;
import us.myles.ViaVersion.api.command.ViaCommandSender;
import us.myles.viaversion.libs.bungeecordchat.api.chat.TextComponent;
import us.myles.viaversion.libs.bungeecordchat.chat.ComponentSerializer;

public class VelocityCommandSender
implements ViaCommandSender {
    private final CommandSource source;

    public VelocityCommandSender(CommandSource source) {
        this.source = source;
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.source.hasPermission(permission);
    }

    @Override
    public void sendMessage(String msg) {
        this.source.sendMessage(GsonComponentSerializer.INSTANCE.deserialize(ComponentSerializer.toString(TextComponent.fromLegacyText(msg))));
    }

    @Override
    public UUID getUUID() {
        if (this.source instanceof Player) {
            return ((Player)this.source).getUniqueId();
        }
        return UUID.fromString(this.getName());
    }

    @Override
    public String getName() {
        if (this.source instanceof Player) {
            return ((Player)this.source).getUsername();
        }
        return "?";
    }
}

