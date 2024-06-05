/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.misc;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPacketReceive;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.UTILS.world.PacketUtil;
import drunkclient.beta.UTILS.world.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;

public class AutoRegister
extends Module {
    private boolean A;
    private boolean A2;
    private Timer y = new Timer();
    private String password = "Niggerslikes321";

    public AutoRegister() {
        super("AutoRegister", new String[0], Type.MISC, "Automatically Register/login on server");
    }

    @EventHandler
    public void ReceivePacket(EventPacketReceive e) {
        if (!(e.getPacket() instanceof S02PacketChat)) return;
        S02PacketChat packet = (S02PacketChat)e.getPacket();
        String receivedMessage = packet.getChatComponent().getUnformattedText();
        if (receivedMessage.equals("/register <password> <password>")) {
            this.y.reset();
            this.A = true;
        }
        if (receivedMessage.equals("Wrong password.")) {
            // empty if block
        }
        if (!receivedMessage.equals("/login <password>")) return;
        this.y.reset();
        this.A2 = true;
    }

    @EventHandler
    public void onUpdate(EventPreUpdate e) {
        if (Minecraft.thePlayer == null) return;
        if (AutoRegister.mc.theWorld == null) return;
        if (!this.A2 || !this.y.hasElapsed(250L, false)) {
            if (!this.A) return;
            if (!this.y.hasElapsed(3100L, false)) return;
        }
        if (this.A) {
            PacketUtil.sendPacketNoEvent(new C01PacketChatMessage("/register " + this.password + " " + this.password));
            this.A = false;
        }
        if (!this.A2) return;
        PacketUtil.sendPacketNoEvent(new C01PacketChatMessage("/login " + this.password));
        this.A2 = false;
    }
}

