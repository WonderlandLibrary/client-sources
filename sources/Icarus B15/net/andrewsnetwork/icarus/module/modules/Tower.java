// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.util.EnumFacing;
import net.andrewsnetwork.icarus.event.events.Walking;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.andrewsnetwork.icarus.event.events.SentPacket;
import net.minecraft.util.Vec3;
import net.andrewsnetwork.icarus.event.events.PostMotion;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.utilities.EntityHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.item.ItemBlock;
import net.andrewsnetwork.icarus.event.events.PreMotion;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.utilities.TimeHelper;
import net.andrewsnetwork.icarus.utilities.BlockData;
import net.andrewsnetwork.icarus.module.Module;

public class Tower extends Module
{
    private BlockData blockData;
    private TimeHelper time;
    
    public Tower() {
        super("Tower", -4521985, 21, Category.WORLD);
        this.blockData = null;
        this.time = new TimeHelper();
    }
    
    @Override
    public void onEnabled() {
        super.onEnabled();
        this.blockData = null;
    }
    
    @Override
    public void onEvent(final Event event) {
        Label_0040: {
            if (event instanceof EatMyAssYouFuckingDecompiler) {
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
        if (event instanceof PreMotion) {
            final PreMotion pre = (PreMotion)event;
            this.blockData = null;
            if (Tower.mc.thePlayer.getHeldItem() != null && !Tower.mc.thePlayer.isSneaking() && Tower.mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
                final BlockPos blockBelow = new BlockPos(Tower.mc.thePlayer.posX, Tower.mc.thePlayer.posY - 1.0, Tower.mc.thePlayer.posZ);
                if (Tower.mc.theWorld.getBlockState(blockBelow).getBlock() == Blocks.air) {
                    this.blockData = this.getBlockData(blockBelow);
                    if (this.blockData != null) {
                        final float[] values = EntityHelper.getFacingRotations(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face);
                        final HealingBot healingBot = (HealingBot)Icarus.getModuleManager().getModuleByName("healingbot");
                        if (!healingBot.isHealing()) {
                            pre.setYaw(values[0]);
                            pre.setPitch(values[1]);
                        }
                    }
                }
            }
        }
        else if (event instanceof PostMotion) {
            if (this.blockData == null) {
                return;
            }
            if (this.time.hasReached(125L)) {
                if (Tower.mc.playerController.func_178890_a(Tower.mc.thePlayer, Tower.mc.theWorld, Tower.mc.thePlayer.getHeldItem(), this.blockData.position, this.blockData.face, new Vec3(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ()))) {
                    Tower.mc.thePlayer.swingItem();
                }
                Tower.mc.thePlayer.jump();
                this.time.reset();
            }
        }
        else if (event instanceof SentPacket) {
            final SentPacket sent = (SentPacket)event;
            if (sent.getPacket() instanceof C03PacketPlayer) {
                final C03PacketPlayer player = (C03PacketPlayer)sent.getPacket();
                if (this.blockData == null) {
                    return;
                }
                final float[] values = EntityHelper.getFacingRotations(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face);
                player.yaw = values[0];
                player.pitch = values[1];
            }
        }
        else if (event instanceof Walking) {
            ((Walking)event).setCancelled(true);
        }
    }
    
    public BlockData getBlockData(final BlockPos pos) {
        if (Tower.mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
        }
        if (Tower.mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (Tower.mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (Tower.mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (Tower.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
        }
        return null;
    }
}
