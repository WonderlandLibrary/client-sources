/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.bypass;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.UpdateEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Timer;

@Module.Mod
public class FlyHypixel
extends Module {
    @EventTarget
    private void onUpdate(UpdateEvent event) {
        Minecraft.thePlayer.motionY = 0.0;
        Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 1.0E-9, Minecraft.thePlayer.posZ);
        if (Minecraft.thePlayer.ticksExisted % 3 == 0 && Minecraft.theWorld.getBlockState(new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 0.2, Minecraft.thePlayer.posZ)).getBlock() instanceof BlockAir) {
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 1.0E-9, Minecraft.thePlayer.posZ, Minecraft.thePlayer.rotationYaw, Minecraft.thePlayer.rotationPitch, true));
            Timer.timerSpeed = 1.0f;
        }
    }
}

