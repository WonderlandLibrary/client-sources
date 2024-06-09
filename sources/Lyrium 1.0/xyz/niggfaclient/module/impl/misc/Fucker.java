// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.misc;

import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import java.util.Objects;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.network.Packet;
import xyz.niggfaclient.utils.network.PacketUtil;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.EnumFacing;
import xyz.niggfaclient.module.ModuleManager;
import xyz.niggfaclient.module.impl.movement.Flight;
import xyz.niggfaclient.utils.player.BlockUtils;
import net.minecraft.init.Blocks;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.Listener;
import net.minecraft.util.Vec3;
import net.minecraft.util.BlockPos;
import xyz.niggfaclient.utils.other.TimerUtil;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Fucker", description = "When you are near from a bed/cake you automatically destroy them", cat = Category.MISC)
public class Fucker extends Module
{
    private final DoubleProperty range;
    private final Property<Boolean> rotations;
    private final Property<Boolean> swing;
    private final Property<Boolean> cake;
    private final Property<Boolean> bed;
    public TimerUtil timer;
    public BlockPos blockPos;
    private Vec3 respawnPoint;
    private float currentDamage;
    @EventLink
    private final Listener<MotionEvent> motionEventListener;
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    
    public Fucker() {
        this.range = new DoubleProperty("Range", 4.0, 1.0, 7.0, 0.5);
        this.rotations = new Property<Boolean>("Rotations", false);
        this.swing = new Property<Boolean>("Swing", false);
        this.cake = new Property<Boolean>("Cake", false);
        this.bed = new Property<Boolean>("Bed", false);
        this.timer = new TimerUtil();
        this.blockPos = null;
        ArrayList<Integer> pos;
        BlockPos pos2;
        BlockPos currentPos;
        float[] rotations;
        ArrayList<Integer> pos3;
        float[] rotations2;
        this.motionEventListener = (e -> {
            if (e.isPre()) {
                if (!this.mc.gameSettings.keyBindAttack.isKeyDown()) {
                    pos = this.getBlock(Blocks.cake, this.range.getValue().intValue());
                    if (this.cake.getValue() && this.respawnPoint != null && pos != null) {
                        pos2 = new BlockPos(this.mc.thePlayer.posX + pos.get(0), this.mc.thePlayer.posY + pos.get(1), this.mc.thePlayer.posZ + pos.get(2));
                        currentPos = pos2.add(pos.get(0), pos.get(1), pos.get(2));
                        if (this.rotations.getValue()) {
                            rotations = BlockUtils.getRotations(this.blockPos.getX(), this.blockPos.getY(), this.blockPos.getZ());
                            e.setYaw(rotations[0]);
                            e.setPitch(rotations[1]);
                        }
                        if (!ModuleManager.getModule(Flight.class).isEnabled()) {
                            this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getHeldItem(), pos2, EnumFacing.UP, new Vec3(currentPos.getX(), currentPos.getY(), currentPos.getZ()));
                        }
                        if (this.swing.getValue()) {
                            this.mc.thePlayer.swingItem();
                        }
                        else {
                            PacketUtil.sendPacket(new C0APacketAnimation());
                        }
                    }
                    if (this.blockPos == null || (this.mc.theWorld.getBlockState(this.blockPos).getBlock() != Block.getBlockById(26) && this.mc.thePlayer != null)) {
                        pos3 = this.getBlock(Blocks.bed, this.range.getValue().intValue());
                        if (pos3 != null) {
                            this.blockPos = new BlockPos(this.mc.thePlayer.posX + Objects.requireNonNull(pos3).get(0), this.mc.thePlayer.posY + Objects.requireNonNull(pos3).get(1), this.mc.thePlayer.posZ + Objects.requireNonNull(pos3).get(2));
                        }
                    }
                    if (this.bed.getValue() && this.respawnPoint != null && this.blockPos != null) {
                        if (this.rotations.getValue()) {
                            rotations2 = BlockUtils.getRotations(this.blockPos.getX(), this.blockPos.getY(), this.blockPos.getZ());
                            e.setYaw(rotations2[0]);
                            e.setPitch(rotations2[1]);
                        }
                        if (this.currentDamage == 0.0f) {
                            this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.blockPos, EnumFacing.UP));
                            if (this.mc.theWorld.getBlockState(this.blockPos).getBlock().getPlayerRelativeBlockHardness(this.mc.thePlayer) >= 1.0f) {
                                if (this.swing.getValue()) {
                                    this.mc.thePlayer.swingItem();
                                }
                                else {
                                    PacketUtil.sendPacket(new C0APacketAnimation());
                                }
                                this.mc.playerController.onPlayerDestroyBlock(this.blockPos, EnumFacing.DOWN);
                                this.currentDamage = 0.0f;
                                return;
                            }
                        }
                        if (this.swing.getValue()) {
                            this.mc.thePlayer.swingItem();
                        }
                        else {
                            PacketUtil.sendPacket(new C0APacketAnimation());
                        }
                        this.currentDamage += this.mc.theWorld.getBlockState(this.blockPos).getBlock().getPlayerRelativeBlockHardness(this.mc.thePlayer);
                        this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.blockPos, (int)(this.currentDamage * 10.0f) - 1);
                        if (this.currentDamage >= 1.0f) {
                            PacketUtil.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.blockPos, EnumFacing.UP));
                            this.mc.playerController.onPlayerDestroyBlock(this.blockPos, EnumFacing.DOWN);
                            this.currentDamage = 0.0f;
                        }
                    }
                    if (this.rotations.getValue()) {
                        this.mc.thePlayer.renderYawOffset = e.getYaw();
                        this.mc.thePlayer.rotationYawHead = e.getYaw();
                        this.mc.thePlayer.rotationPitchHead = e.getPitch();
                    }
                }
            }
            return;
        });
        S08PacketPlayerPosLook s08;
        this.packetEventListener = (e -> {
            if (e.getState() == PacketEvent.State.RECEIVE && !this.mc.isSingleplayer() && e.getPacket() instanceof S08PacketPlayerPosLook) {
                s08 = (S08PacketPlayerPosLook)e.getPacket();
                if (this.mc.thePlayer.getDistance(s08.getX(), s08.getY(), s08.getZ()) > 40.0) {
                    this.respawnPoint = new Vec3(s08.getX(), s08.getY(), s08.getZ());
                }
            }
        });
    }
    
    public ArrayList<Integer> getBlock(final Block b, final int r) {
        final ArrayList<Integer> pos = new ArrayList<Integer>();
        for (int x = -r; x < r; ++x) {
            for (int y = r; y > -r; --y) {
                for (int z = -r; z < r; ++z) {
                    if (this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer.posX + x, this.mc.thePlayer.posY + y, this.mc.thePlayer.posZ + z)).getBlock() == b) {
                        pos.add(x);
                        pos.add(y);
                        pos.add(z);
                        return pos;
                    }
                }
            }
        }
        return null;
    }
}
