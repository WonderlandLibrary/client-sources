/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.move;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPacketSend;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.set.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall
extends Module {
    private boolean needSpoof = false;
    private boolean packetModify = false;
    private int packet1Count = 0;
    Mode<Enum> modes = new Mode("Modes", "Mode", (Enum[])Modes.values(), (Enum)Modes.Vanilla);

    public NoFall() {
        super("NoFall", new String[0], Type.MOVE, "No fall damage");
        this.addValues(this.modes);
    }

    @EventHandler
    public void onUpdate(EventPreUpdate e) {
        this.setSuffix(this.modes.getModeAsString());
        switch (this.modes.getModeAsString()) {
            case "Verus": {
                if ((double)Minecraft.thePlayer.fallDistance - Minecraft.thePlayer.motionY > 3.0) {
                    Minecraft.thePlayer.motionY = 0.0;
                    Minecraft.thePlayer.fallDistance = 0.0f;
                    Minecraft.thePlayer.motionX *= 0.6;
                    Minecraft.thePlayer.motionZ *= 0.6;
                    this.needSpoof = true;
                }
                if (Minecraft.thePlayer.fallDistance / 3.0f > (float)this.packet1Count) {
                    this.packet1Count = (int)(Minecraft.thePlayer.fallDistance / 3.0f);
                    this.packetModify = true;
                }
                if (!Minecraft.thePlayer.onGround) return;
                this.packet1Count = 0;
                return;
            }
        }
    }

    @EventHandler
    public void onPacketSend(EventPacketSend e) {
        if (!(e.getPacket() instanceof C03PacketPlayer)) return;
        switch (this.modes.getModeAsString()) {
            case "Verus": {
                if (!this.needSpoof) return;
                ((C03PacketPlayer)e.getPacket()).onGround = true;
                this.needSpoof = false;
                return;
            }
        }
    }

    @Override
    public void onEnable() {
        this.needSpoof = false;
        this.packetModify = false;
        this.packet1Count = 0;
    }

    static enum Modes {
        Verus,
        Vanilla;

    }
}

