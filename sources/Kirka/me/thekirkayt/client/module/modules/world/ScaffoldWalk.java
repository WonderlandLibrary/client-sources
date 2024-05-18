/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.world;

import java.util.Arrays;
import java.util.List;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.timer.Timer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

@Module.Mod(displayName="ScaffoldWalk")
public class ScaffoldWalk
extends Module {
    private List<Block> invalid = Arrays.asList(Blocks.air, Blocks.water, Blocks.fire, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava);
    private Timer timer = new Timer();
    private BlockData blockData;
    boolean placing;

    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (event.getState() == Event.State.PRE) {
            this.blockData = null;
            if (!ClientUtils.player().isSneaking()) {
                BlockPos blockBelow1 = new BlockPos(ClientUtils.player().posX, ClientUtils.player().posY - 1.0, ClientUtils.player().posZ);
                if (ClientUtils.world().getBlockState(blockBelow1).getBlock() == Blocks.air) {
                    this.blockData = this.getBlockData(blockBelow1, this.invalid);
                    if (this.blockData != null) {
                        float yaw = this.aimAtLocation(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face)[0];
                        float pitch = this.aimAtLocation(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face)[1];
                        event.setYaw(yaw);
                        event.setPitch(pitch);
                    }
                }
            }
        }
        if (event.getState() == Event.State.POST && this.blockData != null && this.timer.delay(75.0f)) {
            ClientUtils.mc().rightClickDelayTimer = 0;
            int heldItem = ClientUtils.player().inventory.currentItem;
            for (int i = 0; i < 9; ++i) {
                if (ClientUtils.player().inventory.getStackInSlot(i) == null || !(ClientUtils.player().inventory.getStackInSlot(i).getItem() instanceof ItemBlock)) continue;
                ClientUtils.player().inventory.currentItem = i;
                break;
            }
            if (ClientUtils.playerController().func_178890_a(ClientUtils.player(), ClientUtils.world(), ClientUtils.player().getHeldItem(), this.blockData.position, this.blockData.face, new Vec3(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ()))) {
                ClientUtils.packet(new C0APacketAnimation());
            }
            ClientUtils.player().inventory.currentItem = heldItem;
        }
    }

    private float[] aimAtLocation(double x, double y, double z, EnumFacing facing) {
        EntitySnowball entitySnowball6;
        EntitySnowball entitySnowball4;
        EntitySnowball entitySnowball5;
        EntitySnowball temp = new EntitySnowball(ClientUtils.world());
        temp.posX = x + 0.5;
        temp.posY = y - 2.7035252353;
        temp.posZ = z + 0.5;
        EntitySnowball entitySnowball = entitySnowball4 = temp;
        entitySnowball4.posX += (double)facing.getDirectionVec().getX() * 0.25;
        EntitySnowball entitySnowball2 = entitySnowball5 = temp;
        entitySnowball5.posY += (double)facing.getDirectionVec().getY() * 0.25;
        EntitySnowball entitySnowball3 = entitySnowball6 = temp;
        entitySnowball6.posZ += (double)facing.getDirectionVec().getZ() * 0.25;
        return this.aimAtLocation(temp.posX, temp.posY, temp.posZ);
    }

    private float[] aimAtLocation(double positionX, double positionY, double positionZ) {
        double x = positionX - ClientUtils.player().posX;
        double y = positionY - ClientUtils.player().posY;
        double z = positionZ - ClientUtils.player().posZ;
        double distance = MathHelper.sqrt_double(x * x + z * z);
        return new float[]{(float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f, (float)(-(Math.atan2(y, distance) * 180.0 / 3.141592653589793))};
    }

    private BlockData getBlockData(BlockPos pos, List list) {
        return list.contains(ClientUtils.world().getBlockState(pos.add(0, -1, 0)).getBlock()) ? (list.contains(ClientUtils.world().getBlockState(pos.add(-1, 0, 0)).getBlock()) ? (list.contains(ClientUtils.world().getBlockState(pos.add(1, 0, 0)).getBlock()) ? (list.contains(ClientUtils.world().getBlockState(pos.add(0, 0, -1)).getBlock()) ? (list.contains(ClientUtils.world().getBlockState(pos.add(0, 0, 1)).getBlock()) ? null : new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH)) : new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH)) : new BlockData(pos.add(1, 0, 0), EnumFacing.WEST)) : new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST)) : new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
    }

    private class BlockData {
        public BlockPos position;
        public EnumFacing face;

        public BlockData(BlockPos position, EnumFacing face) {
            this.position = position;
            this.face = face;
        }
    }

}

