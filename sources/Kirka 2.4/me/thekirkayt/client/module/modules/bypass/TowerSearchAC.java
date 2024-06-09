/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.bypass;

import java.util.List;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.PacketSendEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.BlockData;
import me.thekirkayt.utils.EntityHelper;
import me.thekirkayt.utils.Timer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

@Module.Mod(displayName="TowerSearchAC")
public class TowerSearchAC
extends Module {
    public Minecraft mc = Minecraft.getMinecraft();
    private final Timer time = new Timer();
    private List<Block> invalid;
    private Timer timer;
    private BlockData blockData;
    boolean placing;
    boolean enabled;

    @Override
    public void enable() {
        super.enable();
        this.blockData = null;
        this.enabled = true;
    }

    public void diable() {
        super.disable();
        this.enabled = false;
    }

    @EventTarget
    private void onUpdate(UpdateEvent event) {
        if (event.getState().equals((Object)Event.State.PRE)) {
            BlockPos blockBelow;
            this.blockData = null;
            if (Minecraft.thePlayer.getHeldItem() != null && !Minecraft.thePlayer.isSneaking() && Minecraft.thePlayer.getHeldItem().getItem() instanceof ItemBlock && Minecraft.theWorld.getBlockState(blockBelow = new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 1.0, Minecraft.thePlayer.posZ)).getBlock() == Blocks.air) {
                this.blockData = this.getBlockData(blockBelow);
                if (this.blockData != null) {
                    float[] values = EntityHelper.getFacingRotations(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face);
                    if (this.enabled) {
                        event.setYaw(values[0]);
                        event.setPitch(values[1]);
                    }
                }
            }
        }
    }

    @EventTarget
    private void onUpdate2(UpdateEvent event) {
        if (event.getState().equals((Object)Event.State.POST)) {
            if (this.blockData == null) {
                return;
            }
            if (this.time.hasReached(125L) && this.enabled) {
                if (Minecraft.playerController.func_178890_a(Minecraft.thePlayer, Minecraft.theWorld, Minecraft.thePlayer.getHeldItem(), this.blockData.position, this.blockData.face, new Vec3(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ()))) {
                    Minecraft.thePlayer.swingItem();
                }
                Minecraft.thePlayer.jump();
                this.time.reset();
            }
        }
    }

    @EventTarget
    private void onPacketSend(PacketSendEvent event) {
        PacketSendEvent sent = event;
        if (sent.getPacket() instanceof C03PacketPlayer) {
            C03PacketPlayer player = (C03PacketPlayer)sent.getPacket();
            if (this.blockData == null) {
                return;
            }
            float[] values = EntityHelper.getFacingRotations(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face);
            player.yaw = values[0];
            player.pitch = values[1];
        }
    }

    @EventTarget
    private void onMove(MoveEvent event) {
        event.setCancelled(true);
    }

    public BlockData getBlockData(BlockPos pos) {
        if (Minecraft.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
        }
        if (Minecraft.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (Minecraft.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (Minecraft.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (Minecraft.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
        }
        return null;
    }
}

