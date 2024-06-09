/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Combat;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import us.amerikan.events.EventPreMotion;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;
import us.amerikan.utils.TimeHelper;

public class BedFucker
extends Module {
    private TimeHelper bedfuckerTime = new TimeHelper();

    public BedFucker() {
        super("BedFucker", "BedFucker", 0, Category.COMBAT);
    }

    @EventTarget
    public void onPre(EventPreMotion event) {
        for (int xOffset = -5; xOffset < 6; ++xOffset) {
            for (int zOffset = -5; zOffset < 6; ++zOffset) {
                for (int yOffset = 6; yOffset > -6; --yOffset) {
                    double x2 = Minecraft.thePlayer.posX + (double)xOffset;
                    double y2 = Minecraft.thePlayer.posY + (double)yOffset;
                    double z2 = Minecraft.thePlayer.posZ + (double)zOffset;
                    BlockPos pos = new BlockPos(x2, y2, z2);
                    int id2 = Block.getIdFromBlock(BedFucker.mc.theWorld.getBlockState(pos).getBlock());
                    if (id2 != 26 || !this.bedfuckerTime.isDelayComplete(700L)) continue;
                    Minecraft.thePlayer.setSprinting(false);
                    event.yaw = BedFucker.getRotations(new BlockPos(x2, y2, z2))[0];
                    event.pitch = BedFucker.getRotations(new BlockPos(x2, y2, z2))[1];
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 1.0, Minecraft.thePlayer.posZ, Minecraft.thePlayer.onGround));
                    this.destroyBlock(new BlockPos(x2, y2, z2));
                    event.yaw = BedFucker.getRotations(new BlockPos(x2, y2, z2))[0];
                    event.pitch = BedFucker.getRotations(new BlockPos(x2, y2, z2))[1];
                    Minecraft.thePlayer.setSprinting(false);
                    this.bedfuckerTime.setLastMS();
                    Minecraft.thePlayer.setSprinting(false);
                }
            }
        }
    }

    private void destroyBlock(BlockPos pos) {
        Minecraft.thePlayer.swingItem();
        Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
        Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP));
    }

    private static float[] getRotations(BlockPos pos) {
        if (pos == null) {
            return null;
        }
        double diffX = (double)pos.getX() - Minecraft.thePlayer.posX;
        double diffZ = (double)pos.getZ() - Minecraft.thePlayer.posZ;
        double diffY = (float)pos.getY() - Minecraft.thePlayer.getEyeHeight();
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793) / (float)dist;
        float[] arrf = new float[2];
        arrf[0] = Minecraft.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.thePlayer.rotationYaw);
        arrf[1] = Minecraft.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.thePlayer.rotationPitch);
        return arrf;
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
    }
}

