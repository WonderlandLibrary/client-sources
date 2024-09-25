package eze.util;

import net.minecraft.client.*;
import net.minecraft.init.*;
import eze.events.*;
import eze.events.listeners.*;
import optifine.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.client.entity.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;

public class BlockUtils2
{
    private static ReflectorClass ForgeBlock;
    public static Minecraft mc;
    private static ReflectorMethod ForgeBlock_setLightOpacity;
    private static boolean directAccessValid;
    private List<Block> invalid;
    
    static {
        BlockUtils2.ForgeBlock = new ReflectorClass(Block.class);
        BlockUtils2.ForgeBlock_setLightOpacity = new ReflectorMethod(BlockUtils2.ForgeBlock, "setLightOpacity");
        BlockUtils2.directAccessValid = true;
    }
    
    public BlockUtils2() {
        this.invalid = Arrays.asList(Blocks.air, Blocks.water, Blocks.fire, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.chest, Blocks.anvil, Blocks.enchanting_table);
    }
    
    public void onEvent(final Event e) {
        if (e instanceof EventMotion) {
            final EventMotion eventMotion = (EventMotion)e;
        }
    }
    
    public static void setLightOpacity(final Block block, final int opacity) {
        if (BlockUtils2.directAccessValid) {
            try {
                block.setLightOpacity(opacity);
                return;
            }
            catch (IllegalAccessError var3) {
                BlockUtils2.directAccessValid = false;
                if (!BlockUtils2.ForgeBlock_setLightOpacity.exists()) {
                    throw var3;
                }
            }
        }
        Reflector.callVoid(block, BlockUtils2.ForgeBlock_setLightOpacity, opacity);
    }
    
    public static float[] faceTarget(final Entity target, final float p_70625_2_, final float p_70625_3_, final boolean miss) {
        final double var4 = target.posX - BlockUtils2.mc.thePlayer.posX;
        final double var5 = target.posZ - BlockUtils2.mc.thePlayer.posZ;
        double var7;
        if (target instanceof EntityLivingBase) {
            final EntityLivingBase var6 = (EntityLivingBase)target;
            var7 = var6.posY + var6.getEyeHeight() - (BlockUtils2.mc.thePlayer.posY + BlockUtils2.mc.thePlayer.getEyeHeight());
        }
        else {
            var7 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0 - (BlockUtils2.mc.thePlayer.posY + BlockUtils2.mc.thePlayer.getEyeHeight());
        }
        final Random rnd = new Random();
        final double var8 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
        final float var9 = (float)(Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
        final float var10 = (float)(-(Math.atan2(var7 - ((target instanceof EntityPlayer) ? 0.25 : 0.0), var8) * 180.0 / 3.141592653589793));
        final float pitch = changeRotation(BlockUtils2.mc.thePlayer.rotationPitch, var10, p_70625_3_);
        final float yaw = changeRotation(BlockUtils2.mc.thePlayer.rotationYaw, var9, p_70625_2_);
        return new float[] { yaw, pitch };
    }
    
    public static float changeRotation(final float p_70663_1_, final float p_70663_2_, final float p_70663_3_) {
        float var4 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
        if (var4 > p_70663_3_) {
            var4 = p_70663_3_;
        }
        if (var4 < -p_70663_3_) {
            var4 = -p_70663_3_;
        }
        return p_70663_1_ + var4;
    }
    
    public static float[] getFacingRotations(final int x, final int y, final int z, final EnumFacing facing) {
        final EntitySnowball temp = new EntitySnowball(BlockUtils2.mc.theWorld);
        temp.posX = x + 0.5;
        temp.posY = y + 0.5;
        temp.posZ = z + 0.5;
        final EntitySnowball entitySnowball = temp;
        entitySnowball.posX += facing.getDirectionVec().getX() * 0.25;
        final EntitySnowball entitySnowball2 = temp;
        entitySnowball2.posY += facing.getDirectionVec().getY() * 0.25;
        final EntitySnowball entitySnowball3 = temp;
        entitySnowball3.posZ += facing.getDirectionVec().getZ() * 0.25;
        return faceTarget(temp, 100.0f, 100.0f, false);
    }
    
    public static float[] getAngles(final EntityPlayerSP player, final BlockPos blockPos) {
        final double difX = blockPos.getX() + 0.5 - player.posX;
        final double difY = blockPos.getY() - player.posY + player.getEyeHeight();
        final double difZ = blockPos.getZ() + 0.5 - player.posZ;
        final double sqrt = Math.sqrt(difX * difX + difZ * difZ);
        final float yaw = (float)(Math.atan2(difZ, difX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(difY, sqrt) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }
    
    public static boolean isOnLiquid() {
        boolean onLiquid = false;
        if (getBlockAtPosC(BlockUtils2.mc.thePlayer, 0.30000001192092896, 0.10000000149011612, 0.30000001192092896).getMaterial().isLiquid() && getBlockAtPosC(BlockUtils2.mc.thePlayer, -0.30000001192092896, 0.10000000149011612, -0.30000001192092896).getMaterial().isLiquid()) {
            onLiquid = true;
        }
        return onLiquid;
    }
    
    public static boolean isOnLadder() {
        if (BlockUtils2.mc.thePlayer == null) {
            return false;
        }
        boolean onLadder = false;
        final int y = (int)BlockUtils2.mc.thePlayer.getEntityBoundingBox().offset(0.0, 1.0, 0.0).minY;
        for (int x = MathHelper.floor_double(BlockUtils2.mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(BlockUtils2.mc.thePlayer.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(BlockUtils2.mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(BlockUtils2.mc.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z) {
                final Block block = getBlock(x, y, z);
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLadder) && !(block instanceof BlockVine)) {
                        return false;
                    }
                    onLadder = true;
                }
            }
        }
        return onLadder || BlockUtils2.mc.thePlayer.isOnLadder();
    }
    
    public static boolean isOnIce() {
        if (BlockUtils2.mc.thePlayer == null) {
            return false;
        }
        boolean onIce = false;
        final int y = (int)BlockUtils2.mc.thePlayer.getEntityBoundingBox().offset(0.0, -0.01, 0.0).minY;
        for (int x = MathHelper.floor_double(BlockUtils2.mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(BlockUtils2.mc.thePlayer.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(BlockUtils2.mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(BlockUtils2.mc.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z) {
                final Block block = getBlock(x, y, z);
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockIce) && !(block instanceof BlockPackedIce)) {
                        return false;
                    }
                    onIce = true;
                }
            }
        }
        return onIce;
    }
    
    public boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(BlockUtils2.mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(BlockUtils2.mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(BlockUtils2.mc.thePlayer.boundingBox.minY); y < MathHelper.floor_double(BlockUtils2.mc.thePlayer.boundingBox.maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(BlockUtils2.mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(BlockUtils2.mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
                    final Block block = BlockUtils2.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    final AxisAlignedBB boundingBox;
                    if (block != null && !(block instanceof BlockAir) && (boundingBox = block.getCollisionBoundingBox(BlockUtils2.mc.theWorld, new BlockPos(x, y, z), BlockUtils2.mc.theWorld.getBlockState(new BlockPos(x, y, z)))) != null && BlockUtils2.mc.thePlayer.boundingBox.intersectsWith(boundingBox)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public Block getBlockByIDorName(final String message) {
        Block tBlock = null;
        try {
            tBlock = Block.getBlockById(Integer.parseInt(message));
        }
        catch (NumberFormatException e) {
            Block block = null;
            for (final Object object : Block.blockRegistry) {
                block = (Block)object;
                final String label = block.getLocalizedName().replace(" ", "");
                if (label.toLowerCase().startsWith(message)) {
                    break;
                }
                if (label.toLowerCase().contains(message)) {
                    break;
                }
            }
            if (block != null) {
                tBlock = block;
            }
        }
        return tBlock;
    }
    
    public static boolean isBlockUnderPlayer(final Material material, final float height) {
        return getBlockAtPosC(BlockUtils2.mc.thePlayer, 0.3100000023841858, height, 0.3100000023841858).getMaterial() == material && getBlockAtPosC(BlockUtils2.mc.thePlayer, -0.3100000023841858, height, -0.3100000023841858).getMaterial() == material && getBlockAtPosC(BlockUtils2.mc.thePlayer, -0.3100000023841858, height, 0.3100000023841858).getMaterial() == material && getBlockAtPosC(BlockUtils2.mc.thePlayer, 0.3100000023841858, height, -0.3100000023841858).getMaterial() == material;
    }
    
    public static boolean canSeeBlock(final float x, final float y, final float z) {
        return getFacing(new BlockPos(x, y, z)) != null;
    }
    
    public static EnumFacing getFacing(final BlockPos pos) {
        final EnumFacing[] var2;
        final EnumFacing[] orderedValues = var2 = new EnumFacing[] { EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.DOWN };
        for (int var3 = orderedValues.length, var4 = 0; var4 < var3; ++var4) {
            final EnumFacing facing = var2[var4];
            final EntitySnowball temp = new EntitySnowball(BlockUtils2.mc.theWorld);
            temp.posX = pos.getX() + 0.5;
            temp.posY = pos.getY() + 0.5;
            temp.posZ = pos.getZ() + 0.5;
            final EntitySnowball entitySnowball = temp;
            entitySnowball.posX += facing.getDirectionVec().getX() * 0.5;
            final EntitySnowball entitySnowball2 = temp;
            entitySnowball2.posY += facing.getDirectionVec().getY() * 0.5;
            final EntitySnowball entitySnowball3 = temp;
            entitySnowball3.posZ += facing.getDirectionVec().getZ() * 0.5;
            if (BlockUtils2.mc.thePlayer.canEntityBeSeen(temp)) {
                return facing;
            }
        }
        return null;
    }
    
    public BlockData getBlockData1(final BlockPos pos) {
        final List<Block> invalid = this.invalid;
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock())) {
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
        }
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock())) {
            return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
        }
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock())) {
            return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
        }
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock())) {
            return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock())) {
            return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
        }
        final BlockPos add = pos.add(-1, 0, 0);
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(add.add(-1, 0, 0)).getBlock())) {
            return new BlockData(add.add(-1, 0, 0), EnumFacing.EAST);
        }
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(add.add(1, 0, 0)).getBlock())) {
            return new BlockData(add.add(1, 0, 0), EnumFacing.WEST);
        }
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(add.add(0, 0, -1)).getBlock())) {
            return new BlockData(add.add(0, 0, -1), EnumFacing.SOUTH);
        }
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(add.add(0, 0, 1)).getBlock())) {
            return new BlockData(add.add(0, 0, 1), EnumFacing.NORTH);
        }
        final BlockPos add2 = pos.add(1, 0, 0);
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(add2.add(-1, 0, 0)).getBlock())) {
            return new BlockData(add2.add(-1, 0, 0), EnumFacing.EAST);
        }
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(add2.add(1, 0, 0)).getBlock())) {
            return new BlockData(add2.add(1, 0, 0), EnumFacing.WEST);
        }
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(add2.add(0, 0, -1)).getBlock())) {
            return new BlockData(add2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(add2.add(0, 0, 1)).getBlock())) {
            return new BlockData(add2.add(0, 0, 1), EnumFacing.NORTH);
        }
        final BlockPos add3 = pos.add(0, 0, -1);
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(add3.add(-1, 0, 0)).getBlock())) {
            return new BlockData(add3.add(-1, 0, 0), EnumFacing.EAST);
        }
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(add3.add(1, 0, 0)).getBlock())) {
            return new BlockData(add3.add(1, 0, 0), EnumFacing.WEST);
        }
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(add3.add(0, 0, -1)).getBlock())) {
            return new BlockData(add3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(add3.add(0, 0, 1)).getBlock())) {
            return new BlockData(add3.add(0, 0, 1), EnumFacing.NORTH);
        }
        final BlockPos add4 = pos.add(0, 0, 1);
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(add4.add(-1, 0, 0)).getBlock())) {
            return new BlockData(add4.add(-1, 0, 0), EnumFacing.EAST);
        }
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(add4.add(1, 0, 0)).getBlock())) {
            return new BlockData(add4.add(1, 0, 0), EnumFacing.WEST);
        }
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(add4.add(0, 0, -1)).getBlock())) {
            return new BlockData(add4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        Minecraft.getMinecraft();
        if (!invalid.contains(BlockUtils2.mc.theWorld.getBlockState(add4.add(0, 0, 1)).getBlock())) {
            return new BlockData(add4.add(0, 0, 1), EnumFacing.NORTH);
        }
        final BlockData blockData = null;
        return blockData;
    }
    
    public int getBlockSlot() {
        for (int i = 36; i < 45; ++i) {
            Minecraft.getMinecraft();
            final ItemStack itemStack = BlockUtils2.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock) {
                return i - 36;
            }
        }
        return -1;
    }
    
    public int getBestSlot() {
        Minecraft.getMinecraft();
        if (BlockUtils2.mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
            Minecraft.getMinecraft();
            return BlockUtils2.mc.thePlayer.inventory.currentItem;
        }
        for (int i = 0; i < 8; ++i) {
            Minecraft.getMinecraft();
            if (BlockUtils2.mc.thePlayer.inventory.getStackInSlot(i) != null) {
                Minecraft.getMinecraft();
                if (BlockUtils2.mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static BlockData getBlockData(final BlockPos pos, final List list) {
        return list.contains(BlockUtils2.mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock()) ? (list.contains(BlockUtils2.mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock()) ? (list.contains(BlockUtils2.mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock()) ? (list.contains(BlockUtils2.mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock()) ? (list.contains(BlockUtils2.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock()) ? null : new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH)) : new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH)) : new BlockData(pos.add(1, 0, 0), EnumFacing.WEST)) : new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST)) : new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
    }
    
    public static Block getBlockAtPosC(final EntityPlayer inPlayer, final double x, final double y, final double z) {
        return getBlock(new BlockPos(inPlayer.posX - x, inPlayer.posY - y, inPlayer.posZ - z));
    }
    
    public static Block getBlockUnderPlayer(final EntityPlayer inPlayer, final double height) {
        return getBlock(new BlockPos(inPlayer.posX, inPlayer.posY - height, inPlayer.posZ));
    }
    
    public static Block getBlockAbovePlayer(final EntityPlayer inPlayer, final double height) {
        return getBlock(new BlockPos(inPlayer.posX, inPlayer.posY + inPlayer.height + height, inPlayer.posZ));
    }
    
    public static Block getBlock(final int x, final int y, final int z) {
        return BlockUtils2.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }
    
    public static Block getBlock(final BlockPos pos) {
        return BlockUtils2.mc.theWorld.getBlockState(pos).getBlock();
    }
    
    public static class BlockData
    {
        public BlockPos position;
        public EnumFacing face;
        
        public BlockData(final BlockPos position, final EnumFacing face) {
            this.position = position;
            this.face = face;
        }
    }
}
