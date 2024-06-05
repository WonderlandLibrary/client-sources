/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.player;

import cc.diablo.event.impl.PacketEvent;
import cc.diablo.helpers.PacketHelper;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import com.google.common.eventbus.Subscribe;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotate
extends Module {
    public NoRotate() {
        super("NoRotate", "Doesn't let servers force you to rotate", 0, Category.Player);
    }

    @Subscribe
    public void onPacket(PacketEvent e) {
        if (e.isIncoming() && e.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)e.getPacket();
            packet.yaw = NoRotate.mc.thePlayer.rotationYaw;
            packet.pitch = NoRotate.mc.thePlayer.rotationPitch;
            PacketHelper.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(NoRotate.mc.thePlayer.posX, NoRotate.mc.thePlayer.posY, NoRotate.mc.thePlayer.posZ, packet.getYaw(), packet.getPitch(), NoRotate.mc.thePlayer.onGround));
        }
    }
}

