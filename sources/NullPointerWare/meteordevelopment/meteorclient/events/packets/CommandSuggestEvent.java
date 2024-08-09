/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.events.packets;

import net.minecraft.network.packet.s2c.play.CommandSuggestionsS2CPacket;

public class CommandSuggestEvent {
    private static final CommandSuggestEvent INSTANCE = new CommandSuggestEvent();

    public CommandSuggestionsS2CPacket packet;

    public static CommandSuggestEvent get(CommandSuggestionsS2CPacket packet) {
        INSTANCE.packet = packet;
        return INSTANCE;
    }

    public CommandSuggestionsS2CPacket getPacket() {
        return packet;
    }
}
