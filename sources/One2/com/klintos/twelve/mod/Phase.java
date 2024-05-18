// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.util.BlockPos;
import net.minecraft.block.BlockAir;
import com.klintos.twelve.utils.PlayerUtils;
import net.minecraft.util.MathHelper;
import net.minecraft.util.AxisAlignedBB;
import com.klintos.twelve.mod.events.EventSetBoundingBox;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import com.klintos.twelve.mod.events.EventMove;

public class Phase extends Mod
{
    private int delay;
    
    public Phase() {
        super("Phase", 25, ModCategory.EXPLOITS);
    }
    
    @EventTarget
    public void onMove(final EventMove event) {
        if (Phase.mc.thePlayer.isCollidedHorizontally && !Phase.mc.thePlayer.isOnLadder() && Phase.mc.thePlayer.onGround) {
            ++this.delay;
            if (this.delay >= 2) {
                event.setCancelled(true);
                Phase.mc.thePlayer.isCollidedHorizontally = false;
                final double x = 0.1 * Math.cos(Math.toRadians(Phase.mc.thePlayer.rotationYaw + 90.0f));
                final double z = 0.1 * Math.sin(Math.toRadians(Phase.mc.thePlayer.rotationYaw + 90.0f));
                for (int i = 0; i < 10; ++i) {
                    Phase.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Phase.mc.thePlayer.posX, Phase.mc.thePlayer.posY - 0.01, Phase.mc.thePlayer.posZ, true));
                    Phase.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Phase.mc.thePlayer.posX + x * 1.1 * i, Phase.mc.thePlayer.posY, Phase.mc.thePlayer.posZ + z * 1.1 * i, true));
                }
                this.delay = 0;
            }
        }
    }
    
    @EventTarget
    public void onSetBoundingBox(final EventSetBoundingBox event) {
        if ((int)(Phase.mc.thePlayer.getBoundingBox().minY - 0.5) < event.getY() && this.isInsideBlock()) {
            event.setAABB(null);
        }
    }
    
    private boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(Phase.mc.thePlayer.getBoundingBox().minX); x < MathHelper.floor_double(Phase.mc.thePlayer.getBoundingBox().maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(Phase.mc.thePlayer.getBoundingBox().minY); y < MathHelper.floor_double(Phase.mc.thePlayer.getBoundingBox().maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(Phase.mc.thePlayer.getBoundingBox().minZ); z < MathHelper.floor_double(Phase.mc.thePlayer.getBoundingBox().maxZ) + 1; ++z) {
                    final Block block = PlayerUtils.getBlock(x, y, z);
                    if (block != null) {
                        if (!(block instanceof BlockAir)) {
                            final BlockPos blockPos = new BlockPos(x, y, z);
                            final AxisAlignedBB boundingBox = block.getCollisionBoundingBox((World)Phase.mc.theWorld, blockPos, Phase.mc.thePlayer.worldObj.getBlockState(blockPos));
                            if (boundingBox != null && Phase.mc.thePlayer.getBoundingBox().intersectsWith(boundingBox)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
