package com.boomer.client.module.modules.other;

import java.awt.Color;
import java.util.Random;

import com.boomer.client.event.bus.Handler;
import com.boomer.client.event.events.world.PacketEvent;
import com.boomer.client.module.Module;

import net.minecraft.network.play.client.C14PacketTabComplete;

/**
 * made by Xen for BoomerWare
 *
 * @since 6/11/2019
 **/
public class AntiTabComplete extends Module {

    public AntiTabComplete() {
        super("AntiTabComplete", Category.OTHER, new Color(0xA26287).getRGB());
        setDescription("Cancels the packet it sends the server when tabbing a friends name");
        setRenderlabel("Anti Tab Complete");
    }

    @Handler
    public void onPacket(PacketEvent event) {
        if(event.isSending()) {
            if (event.getPacket() instanceof C14PacketTabComplete) {
                C14PacketTabComplete packet = (C14PacketTabComplete) event.getPacket();

                if (packet.getMessage().startsWith(".")) {
                    String[] arguments = packet.getMessage().split(" ");
                    String[] messages = new String[]{"hey what's up ", "dude ", "hey ", "hi ", "man ", "yo ", "howdy ", "omg "};
                    Random random = new Random();

                    packet.setMessage(messages[random.nextInt(messages.length)] + arguments[arguments.length - 1]);
                }
            }
        }
    }
}
