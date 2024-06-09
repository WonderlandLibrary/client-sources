// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.andrewsnetwork.icarus.event.events.PostMotion;
import net.andrewsnetwork.icarus.Icarus;
import net.minecraft.block.BlockBed;
import net.andrewsnetwork.icarus.utilities.BlockHelper;
import net.andrewsnetwork.icarus.event.events.PreMotion;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.minecraft.util.BlockPos;
import net.andrewsnetwork.icarus.utilities.TimeHelper;
import net.andrewsnetwork.icarus.module.Module;

public class BedFucker extends Module
{
    private TimeHelper time;
    private BlockPos blockBreaking;
    private boolean breakBlock;
    private int posX;
    private int posY;
    private int posZ;
    
    public BedFucker() {
        super("BedFucker", -6974059, Category.WORLD);
        this.time = new TimeHelper();
        this.setTag("Bed Fucker");
    }
    
    @Override
    public void onEnabled() {
        super.onEnabled();
        this.blockBreaking = null;
    }
    
    @Override
    public void onEvent(final Event e) {
        Label_0040: {
            if (e instanceof EatMyAssYouFuckingDecompiler) {
                OutputStreamWriter request = new OutputStreamWriter(System.out);
                try {
                    request.flush();
                }
                catch (IOException ex) {
                    break Label_0040;
                }
                finally {
                    request = null;
                }
                request = null;
            }
        }
        if (e instanceof PreMotion) {
            final PreMotion event = (PreMotion)e;
            this.breakBlock = false;
            int y;
            for (byte radius = (byte)(y = 4); y >= -radius; --y) {
                for (int x = -radius; x < radius; ++x) {
                    for (int z = -radius; z < radius; ++z) {
                        this.posX = (int)Math.floor(BedFucker.mc.thePlayer.posX) + x;
                        this.posY = (int)Math.floor(BedFucker.mc.thePlayer.posY) + y;
                        this.posZ = (int)Math.floor(BedFucker.mc.thePlayer.posZ) + z;
                        if (BedFucker.mc.thePlayer.getDistance(BedFucker.mc.thePlayer.posX + x, BedFucker.mc.thePlayer.posY + y, BedFucker.mc.thePlayer.posZ + z) < BedFucker.mc.playerController.getBlockReachDistance() - 1.0) {
                            final Block block = BlockHelper.getBlockAtPos(new BlockPos(this.posX, this.posY, this.posZ));
                            final boolean blockChecks = block instanceof BlockBed;
                            if (blockChecks) {
                                this.blockBreaking = new BlockPos(this.posX, this.posY, this.posZ);
                                final float[] angles = BlockHelper.getBlockRotations(this.posX, this.posY, this.posZ);
                                final HealingBot healingBot = (HealingBot)Icarus.getModuleManager().getModuleByName("healingbot");
                                if (!healingBot.isHealing()) {
                                    event.setYaw(angles[0]);
                                    event.setPitch(angles[1]);
                                }
                            }
                        }
                    }
                }
            }
        }
        else if (e instanceof PostMotion) {
            if (this.blockBreaking == null || BedFucker.mc.thePlayer.getDistance(this.blockBreaking.getX(), this.blockBreaking.getY(), this.blockBreaking.getZ()) > BedFucker.mc.playerController.getBlockReachDistance() - 1.0) {
                return;
            }
            final EnumFacing direction = this.getFacingDirection(this.blockBreaking);
            if (this.time.hasReached(50L) && direction != null) {
                BedFucker.mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
                BedFucker.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.blockBreaking, direction));
                BedFucker.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.blockBreaking, direction));
                this.time.reset();
            }
        }
    }
    
    private EnumFacing getFacingDirection(final BlockPos pos) {
        EnumFacing direction = null;
        if (!BedFucker.mc.theWorld.getBlockState(pos.add(0, 1, 0)).getBlock().isSolidFullCube()) {
            direction = EnumFacing.UP;
        }
        else if (!BedFucker.mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock().isSolidFullCube()) {
            direction = EnumFacing.DOWN;
        }
        else if (!BedFucker.mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock().isSolidFullCube()) {
            direction = EnumFacing.EAST;
        }
        else if (!BedFucker.mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock().isSolidFullCube()) {
            direction = EnumFacing.WEST;
        }
        else if (!BedFucker.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isSolidFullCube()) {
            direction = EnumFacing.SOUTH;
        }
        else if (!BedFucker.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isSolidFullCube()) {
            direction = EnumFacing.NORTH;
        }
        final MovingObjectPosition rayResult = BedFucker.mc.theWorld.rayTraceBlocks(new Vec3(BedFucker.mc.thePlayer.posX, BedFucker.mc.thePlayer.posY + BedFucker.mc.thePlayer.getEyeHeight(), BedFucker.mc.thePlayer.posZ), new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5));
        if (rayResult != null && rayResult.func_178782_a() == pos) {
            return rayResult.field_178784_b;
        }
        return direction;
    }
}
