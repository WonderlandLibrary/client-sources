/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.move;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.set.Mode;
import drunkclient.beta.UTILS.world.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public class HighJump
extends Module {
    public Mode<Enum> mode = new Mode("Mode", "Mode", (Enum[])Modes.values(), (Enum)Modes.Verus);
    public Mode<Enum> verusMode = new Mode("Verus Mode", "Verus Mode", (Enum[])VerusMode.values(), (Enum)VerusMode.Packet);
    public boolean damagedPacket = false;
    public boolean canJump = false;
    public Timer packettimer = new Timer();
    public Timer packettimer2 = new Timer();

    public HighJump() {
        super("HighJump", new String[0], Type.MOVE, "No");
        this.addValues(this.mode, this.verusMode);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.damagedPacket = false;
        this.canJump = false;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.packettimer.reset();
        this.packettimer2.reset();
    }

    @EventHandler
    public void onEvent(EventPreUpdate e) {
        switch (this.mode.getModeAsString()) {
            case "Verus": {
                if (!this.verusMode.getModeAsString().equalsIgnoreCase("Packet")) return;
                if (!this.damagedPacket && this.packettimer.hasElapsed(1000L, false)) {
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 3.0001, Minecraft.thePlayer.posZ, false));
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, true));
                }
                if (Minecraft.thePlayer.hurtTime <= 0) return;
                Minecraft.thePlayer.motionY += (double)0.16f;
                this.damagedPacket = true;
                return;
            }
        }
    }

    static enum Modes {
        Verus;

    }

    static enum VerusMode {
        Bow,
        Packet;

    }
}

