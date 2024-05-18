// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import net.minecraft.block.BlockAir;
import net.minecraft.util.MathHelper;
import net.minecraft.network.play.client.C03PacketPlayer;
import com.klintos.twelve.mod.events.EventPacketSend;
import net.minecraft.block.Block;
import com.klintos.twelve.utils.PlayerUtils;
import com.klintos.twelve.mod.events.EventPreUpdate;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.BlockLiquid;
import com.klintos.twelve.mod.events.EventSetBoundingBox;
import com.klintos.twelve.utils.TimerUtil;

public class Jesus extends Mod
{
    private TimerUtil timer;
    
    public Jesus() {
        super("Jesus", 36, ModCategory.WORLD);
        this.timer = new TimerUtil();
    }
    
    @EventTarget
    public void onSetBoundingBox(final EventSetBoundingBox event) {
        if (event.getBlock() instanceof BlockLiquid) {
            final boolean check = !Jesus.mc.thePlayer.isSneaking() && isOnLiquid() && !this.isInLiquid() && Jesus.mc.thePlayer.fallDistance < 3.0f;
            final AxisAlignedBB aabb = new AxisAlignedBB((double)event.getX(), (double)event.getY(), (double)event.getZ(), (double)(event.getX() + 1), (double)(event.getY() + 1), (double)(event.getZ() + 1));
            event.setAABB(check ? aabb : null);
        }
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        final Block block = PlayerUtils.getBlock((int)Jesus.mc.thePlayer.posX, (int)(Jesus.mc.thePlayer.getBoundingBox().maxY + 0.1), (int)Jesus.mc.thePlayer.posZ);
        if ((this.isInLiquid() || Jesus.mc.thePlayer.isInWater()) && !Jesus.mc.thePlayer.isSneaking() && !(block instanceof BlockLiquid)) {
            Jesus.mc.thePlayer.motionY = 0.085;
        }
    }
    
    @EventTarget
    public void onPacketSend(final EventPacketSend event) {
        if (event.getPacket() instanceof C03PacketPlayer) {
            final C03PacketPlayer player = (C03PacketPlayer)event.getPacket();
            if (isOnLiquid() && this.timer.delay(180.0)) {
                final C03PacketPlayer c03PacketPlayer = player;
                c03PacketPlayer.y -= 1.0E-7;
            }
        }
    }
    
    public static boolean isOnLiquid() {
        boolean onLiquid = false;
        final int y = (int)Jesus.mc.thePlayer.getBoundingBox().offset(0.0, -0.1, 0.0).minY;
        for (int x = MathHelper.floor_double(Jesus.mc.thePlayer.getBoundingBox().minX); x < MathHelper.floor_double(Jesus.mc.thePlayer.getBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(Jesus.mc.thePlayer.getBoundingBox().minZ); z < MathHelper.floor_double(Jesus.mc.thePlayer.getBoundingBox().maxZ) + 1; ++z) {
                final Block block = PlayerUtils.getBlock(x, y, z);
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }
    
    private boolean isInLiquid() {
        boolean inLiquid = false;
        final int y = (int)Jesus.mc.thePlayer.getBoundingBox().minY;
        for (int x = MathHelper.floor_double(Jesus.mc.thePlayer.getBoundingBox().minX); x < MathHelper.floor_double(Jesus.mc.thePlayer.getBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(Jesus.mc.thePlayer.getBoundingBox().minZ); z < MathHelper.floor_double(Jesus.mc.thePlayer.getBoundingBox().maxZ) + 1; ++z) {
                final Block block = PlayerUtils.getBlock(x, y, z);
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    inLiquid = true;
                }
            }
        }
        return inLiquid;
    }
    
    private boolean isStandingStill() {
        return Math.abs(Jesus.mc.thePlayer.motionX) <= 0.01 && Math.abs(Jesus.mc.thePlayer.motionZ) <= 0.01;
    }
}
