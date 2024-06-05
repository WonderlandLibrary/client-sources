/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.misc;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPacketReceive;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;
import org.apache.commons.lang3.RandomUtils;

public class KillSluts
extends Module {
    private String[] Messages = new String[]{"drunkclient on top! [discord gg/drunkclient]", "why so ES?? [discord gg/drunkclient]", "co zawiodlo? [discord gg/drunkclient]", "drunkclient > all [discord gg/drunkclient]", "drunkclient to na prawde kacorhook ale pod nowa nazwa [discord gg/drunkclient]", "L, get drunk client get good [discord gg/drunkclient]", "get drunk client get good [discord gg/drunkclient]", "LMAO you are so bad [discord gg/drunkclient]", "you are trash, just trash [discord gg/drunkclient]", "jestes najgorszy, NAJgorszy [discord gg/drunkclient]", "chcesz byc lepszy? kup drunkclienta na discord gg/drunkclient! [discord gg/drunkclient]"};

    public KillSluts() {
        super("KillSluts", new String[0], Type.MISC, "Insults your opponents");
    }

    @EventHandler
    public void onPacketReceive(EventPacketReceive event) {
        if (!(event.getPacket() instanceof S02PacketChat)) return;
        S02PacketChat packet = (S02PacketChat)event.getPacket();
        String message = packet.getChatComponent().getUnformattedText();
        if (message.isEmpty()) return;
        Iterator iterator = KillSluts.mc.theWorld.loadedEntityList.iterator();
        while (iterator.hasNext()) {
            EntityPlayer p;
            Object entity = iterator.next();
            if (!(entity instanceof EntityPlayer) || !message.contains((p = (EntityPlayer)entity).getName())) continue;
            if (!message.contains(Minecraft.thePlayer.getName())) continue;
            if (!(message.contains("zostal zabity") || message.contains("zostal zabity przez") || message.contains("knocked") || message.contains("thrown"))) {
                if (!message.contains("foi morto por")) continue;
            }
            if (p.getName().equalsIgnoreCase(Minecraft.thePlayer.getName())) continue;
            EntityPlayer e = (EntityPlayer)entity;
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(e.getName() + ", " + this.randomPhrase() + " [" + RandomUtils.nextLong(4444L, 10000000L) + "]"));
        }
    }

    private String randomPhrase() {
        Random random = new Random();
        return this.Messages[random.nextInt(this.Messages.length)];
    }
}

