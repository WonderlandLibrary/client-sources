/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.move;

import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.set.Mode;
import drunkclient.beta.UTILS.world.PacketUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Phase
extends Module {
    public Mode<Enum> modes = new Mode("Modes", "Modes", (Enum[])Modes.values(), (Enum)Modes.BlocksMC);

    public Phase() {
        super("Phase", new String[0], Type.MOVE, "Allows you to walks through blocks");
        this.addValues(this.modes);
    }

    @Override
    public void onEnable() {
        if (this.modes.getModeAsString().equalsIgnoreCase("BlocksMC")) {
            Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 4.0, Minecraft.thePlayer.posZ);
            this.setEnabled(false);
        }
        if (this.modes.getModeAsString().equalsIgnoreCase("AAC4")) {
            PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 1.0E-8, Minecraft.thePlayer.posZ, false));
        }
        super.onEnable();
    }

    static enum Modes {
        BlocksMC,
        AAC4;

    }
}

