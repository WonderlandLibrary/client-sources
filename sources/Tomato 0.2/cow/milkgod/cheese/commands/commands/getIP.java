/*
 * Decompiled with CFR 0_115.
 */
package cow.milkgod.cheese.commands.commands;

import com.darkmagician6.eventapi.EventTarget;
import cow.milkgod.cheese.commands.Command;
import cow.milkgod.cheese.events.EventTick;
import cow.milkgod.cheese.utils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;

public class getIP
extends Command {
    public getIP() {
        super("GetIP", null, new String[]{"gi"}, "none", "Copies the server's IP to clipboard.");
    }

    @EventTarget
    public void onChatSend(EventTick event) {
        double multiplier = 15.0;
        double mx2 = Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
        double mz2 = Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
        double x2 = 15.0 * mx2;
        double z2 = 15.0 * mz2;
        EntityPlayerSP thePlayer = this.mc.thePlayer;
        EntityPlayerSP thePlayer2 = this.mc.thePlayer;
        double n = 0.0;
        thePlayer2.motionZ = 0.0;
        thePlayer.motionX = 0.0;
        double[] nigger = new double[]{this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ};
        Wrapper.blinkToPos(nigger, Wrapper.getBlockPos(this.mc.thePlayer.posX + x2, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ + z2), 1.0);
        Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX + x2, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ + z2, false));
        this.setState(false);
    }
}

