// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.utilities;

import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.client.Minecraft;

public final class BlockHelper
{
    private static Minecraft mc;
    
    static {
        BlockHelper.mc = Minecraft.getMinecraft();
    }
    
    public static void bestTool(final int x, final int y, final int z) {
        final int blockId = Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock());
        int bestSlot = 0;
        float f = -1.0f;
        for (int i1 = 36; i1 < 45; ++i1) {
            try {
                final ItemStack curSlot = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i1).getStack();
                if ((curSlot.getItem() instanceof ItemTool || curSlot.getItem() instanceof ItemSword || curSlot.getItem() instanceof ItemShears) && curSlot.getStrVsBlock(Block.getBlockById(blockId)) > f) {
                    bestSlot = i1 - 36;
                    f = curSlot.getStrVsBlock(Block.getBlockById(blockId));
                }
            }
            catch (Exception ex) {}
        }
        if (f != -1.0f) {
            Minecraft.getMinecraft().thePlayer.inventory.currentItem = bestSlot;
            BlockHelper.mc.playerController.updateController();
        }
    }
    
    public static Block getBlockAtPos(final BlockPos inBlockPos) {
        final IBlockState s = BlockHelper.mc.theWorld.getBlockState(inBlockPos);
        return s.getBlock();
    }
    
    public static float[] getBlockRotations(final double x, final double y, final double z) {
        final double var4 = x - BlockHelper.mc.thePlayer.posX + 0.5;
        final double var5 = z - BlockHelper.mc.thePlayer.posZ + 0.5;
        final double var6 = y - (BlockHelper.mc.thePlayer.posY + BlockHelper.mc.thePlayer.getEyeHeight() - 1.0);
        final double var7 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
        final float var8 = (float)(Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
        return new float[] { var8, (float)(-(Math.atan2(var6, var7) * 180.0 / 3.141592653589793)) };
    }
    
    public static Block getBlock(final Entity entity, final double offset) {
        if (entity == null) {
            return null;
        }
        final int y = (int)entity.getEntityBoundingBox().offset(0.0, offset, 0.0).minY;
        for (int x = MathHelper.floor_double(entity.getEntityBoundingBox().minX); x < MathHelper.floor_double(entity.getEntityBoundingBox().maxX) + 1; ++x) {
            final int z = MathHelper.floor_double(entity.getEntityBoundingBox().minZ);
            if (z < MathHelper.floor_double(entity.getEntityBoundingBox().maxZ) + 1) {
                return BlockHelper.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
            }
        }
        return null;
    }
    
    public static boolean isInsideBlock(final EntityPlayer player) {
        for (int x = MathHelper.floor_double(player.boundingBox.minX); x < MathHelper.floor_double(player.boundingBox.maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(player.boundingBox.minY); y < MathHelper.floor_double(player.boundingBox.maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(player.boundingBox.minZ); z < MathHelper.floor_double(player.boundingBox.maxZ) + 1; ++z) {
                    final Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != null) {
                        if (!(block instanceof BlockAir)) {
                            final AxisAlignedBB boundingBox = block.getCollisionBoundingBox(BlockHelper.mc.theWorld, new BlockPos(x, y, z), BlockHelper.mc.theWorld.getBlockState(new BlockPos(x, y, z)));
                            if (boundingBox != null && player.boundingBox.intersectsWith(boundingBox)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean isInLiquid() {
        boolean inLiquid = false;
        if (BlockHelper.mc.thePlayer == null || BlockHelper.mc.thePlayer.boundingBox == null) {
            return false;
        }
        final int y = (int)BlockHelper.mc.thePlayer.boundingBox.minY;
        for (int x = MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
                final Block block = BlockHelper.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
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
    
    public static boolean isInLiquidNew() {
        boolean inLiquid = false;
        final int y = (int)BlockHelper.mc.thePlayer.boundingBox.minY;
        for (int x = MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
                final Block block = BlockHelper.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
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
    
    public static boolean isOnIce() {
        boolean onIce = false;
        final int y = (int)(BlockHelper.mc.thePlayer.boundingBox.minY - 1.0);
        for (int x = MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
                final Block block = BlockHelper.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null && !(block instanceof BlockAir) && (block instanceof BlockPackedIce || block instanceof BlockIce)) {
                    onIce = true;
                }
            }
        }
        return onIce;
    }
    
    public static boolean isOnLadder() {
        boolean onLadder = false;
        final int y = (int)(BlockHelper.mc.thePlayer.boundingBox.minY - 1.0);
        for (int x = MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
                final Block block = BlockHelper.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLadder) && !(block instanceof BlockLadder)) {
                        return false;
                    }
                    onLadder = true;
                }
            }
        }
        return onLadder || BlockHelper.mc.thePlayer.isOnLadder();
    }
    
    public static boolean isOnLiquid() {
        boolean onLiquid = false;
        final int y = (int)(BlockHelper.mc.thePlayer.boundingBox.minY - 0.01);
        for (int x = MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
                final Block block = BlockHelper.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
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
    
    public static EnumFacing getFacing(final BlockPos pos) {
        final EnumFacing[] orderedValues = { EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.DOWN };
        EnumFacing[] array;
        for (int length = (array = orderedValues).length, i = 0; i < length; ++i) {
            final EnumFacing facing = array[i];
            final Entity temp = new EntitySnowball(BlockHelper.mc.theWorld);
            temp.posX = pos.getX() + 0.5;
            temp.posY = pos.getY() + 0.5;
            temp.posZ = pos.getZ() + 0.5;
            final Entity entity = temp;
            entity.posX += facing.getDirectionVec().getX() * 0.5;
            final Entity entity2 = temp;
            entity2.posY += facing.getDirectionVec().getY() * 0.5;
            final Entity entity3 = temp;
            entity3.posZ += facing.getDirectionVec().getZ() * 0.5;
            if (BlockHelper.mc.thePlayer.canEntityBeSeen(temp)) {
                return facing;
            }
        }
        return null;
    }
}
