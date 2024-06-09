/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.modules;

import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import pw.vertexcode.nemphis.events.UpdateEvent;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.nemphis.utils.EntityHelper;
import pw.vertexcode.util.event.EventListener;
import pw.vertexcode.util.management.TimeHelper;
import pw.vertexcode.util.module.ModuleInformation;
import pw.vertexcode.util.module.types.ToggleableModule;

@ModuleInformation(category=Category.MOVEMENT, color=-1618884, name="ScaffoldWalk")
public class ScaffoldWalk
extends ToggleableModule {
    private BlockData blockData = null;
    private TimeHelper time = new TimeHelper();
    private static Packet packet;
    private List<Block> blacklist = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava);

    @EventListener
    public void onUpdate(UpdateEvent e) {
        BlockPos blockBelow;
        ScaffoldWalk.mc.thePlayer.setSprinting(false);
        if (!ScaffoldWalk.mc.thePlayer.onGround) {
            return;
        }
        if (Minecraft.getMinecraft().gameSettings.keyBindJump.getIsKeyPressed() && ScaffoldWalk.mc.thePlayer.onGround && packet instanceof C03PacketPlayer.C05PacketPlayerLook) {
            packet = new C03PacketPlayer.C05PacketPlayerLook(-90.0f, -90.0f, true);
        }
        if (Minecraft.getMinecraft().thePlayer.getHeldItem() != null && !Minecraft.getMinecraft().thePlayer.isSneaking()) {
            Minecraft.getMinecraft().thePlayer.getHeldItem().getItem() instanceof ItemBlock;
        }
        this.blockData = null;
        if (Minecraft.getMinecraft().thePlayer.getHeldItem() != null && !Minecraft.getMinecraft().thePlayer.isSneaking() && Minecraft.getMinecraft().thePlayer.getHeldItem().getItem() instanceof ItemBlock && Minecraft.getMinecraft().theWorld.getBlockState(blockBelow = new BlockPos(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY - 1.0, Minecraft.getMinecraft().thePlayer.posZ)).getBlock() == Blocks.air) {
            this.blockData = this.getBlockData(blockBelow);
            if (this.blockData != null) {
                this.getFacingRotations(this.blockData.position.getX() - 1, this.blockData.position.getY() - 1, this.blockData.position.getZ() - 1, this.blockData.face);
            }
        }
        if (this.blockData == null) {
            return;
        }
        Minecraft.getMinecraft().rightClickDelayTimer = 3;
        if (Minecraft.getMinecraft().playerController.func_178890_a(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().theWorld, Minecraft.getMinecraft().thePlayer.getHeldItem(), this.blockData.position, this.blockData.face, new Vec3(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ()))) {
            Minecraft.getMinecraft().thePlayer.swingItem();
        }
    }

    public BlockData getBlockData(BlockPos pos) {
        if (!this.blacklist.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, -1, 0)).getBlock())) {
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.blacklist.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock())) {
            return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.blacklist.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(1, 0, 0)).getBlock())) {
            return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.blacklist.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, 0, -1)).getBlock())) {
            return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!this.blacklist.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, 0, 1)).getBlock())) {
            return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
        }
        return null;
    }

    public float[] getBlockRotations(int x, int y, int z) {
        EntitySnowball temp = new EntitySnowball(ScaffoldWalk.mc.theWorld);
        temp.posX = (double)x + 0.5;
        temp.posY = (double)y + 0.5;
        temp.posZ = (double)z + 0.5;
        return EntityHelper.getAngles(temp);
    }

    public float[] getFacingRotations(int x, int y, int z, EnumFacing facing) {
        EntitySnowball temp = new EntitySnowball(ScaffoldWalk.mc.theWorld);
        temp.posX = (double)x + 0.5;
        temp.posY = (double)y + 0.5;
        temp.posZ = (double)z + 0.5;
        EntitySnowball entity = temp;
        entity.posX += (double)facing.getDirectionVec().getX() * 0.25;
        EntitySnowball entity2 = temp;
        entity2.posY += (double)facing.getDirectionVec().getY() * 0.25;
        EntitySnowball entity3 = temp;
        entity3.posZ += (double)facing.getDirectionVec().getZ() * 0.25;
        return EntityHelper.getAngles(temp);
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

