/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.movement;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.BoundingBoxEvent;
import me.thekirkayt.event.events.PacketSendEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.LiquidUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

@Module.Mod
public class Jesus
extends Module {
    public static boolean shouldOffsetPacket;

    @EventTarget
    private void onUpdate(UpdateEvent event) {
        event.getState();
        if (event.getState() == Event.State.PRE) {
            if (LiquidUtils.isInLiquid() && ClientUtils.mc().thePlayer.isInsideOfMaterial(Material.air) && !ClientUtils.mc().thePlayer.isSneaking()) {
                ClientUtils.mc().thePlayer.motionY = 0.085;
            }
            if (!LiquidUtils.isOnLiquid() || LiquidUtils.isInLiquid() || !this.shouldSetBoundingBox()) {
                shouldOffsetPacket = false;
            }
        }
    }

    @EventTarget
    private void onBoundingBox(BoundingBoxEvent event) {
        if (!LiquidUtils.isInLiquid() && event.getBlock() instanceof BlockLiquid && ClientUtils.world().getBlockState(event.getBlockPos()).getBlock() instanceof BlockLiquid && (Integer)ClientUtils.world().getBlockState(event.getBlockPos()).getValue(BlockLiquid.LEVEL) == 0 && this.shouldSetBoundingBox() && (double)(event.getBlockPos().getY() + 1) <= ClientUtils.mc().thePlayer.boundingBox.minY) {
            event.setBoundingBox(new AxisAlignedBB(event.getBlockPos().getX(), event.getBlockPos().getY(), event.getBlockPos().getZ(), event.getBlockPos().getX() + 1, event.getBlockPos().getY() + 1, event.getBlockPos().getZ() + 1));
        }
    }

    @EventTarget
    private void onPacketSend(PacketSendEvent event) {
        event.getState();
        if (event.getState() == Event.State.PRE && event.getPacket() instanceof C03PacketPlayer && LiquidUtils.isOnLiquid()) {
            C03PacketPlayer packet = (C03PacketPlayer)event.getPacket();
            shouldOffsetPacket = !shouldOffsetPacket;
            boolean bl = shouldOffsetPacket;
            if (shouldOffsetPacket) {
                packet.y -= 1.0E-6;
            }
        }
    }

    private boolean shouldSetBoundingBox() {
        return !ClientUtils.mc().thePlayer.isSneaking() && ClientUtils.mc().thePlayer.fallDistance < 4.0f;
    }

    public void onDisable() {
        shouldOffsetPacket = false;
        super.disable();
    }
}

