package gq.vapu.czfclient.Util;

import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

import java.util.Objects;

public class BlockUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private EntityOtherPlayerMP player;

    public static String getBlockName(Block block) {
        if (block == Blocks.air) {
            return null;
        } else {
            Item item = Item.getItemFromBlock(block);
            ItemStack itemStack = item != null ? new ItemStack(Item.getByNameOrId(block.getUnlocalizedName()), 1, 0) : null;
            String name = itemStack == null ? block.getLocalizedName() : item.getItemStackDisplayName(itemStack);
            return name.length() > 5 && name.startsWith("tile.") ? block.getUnlocalizedName() : name;
        }
    }

    public static boolean isOnLiquid() {
        Minecraft.getMinecraft();
        AxisAlignedBB par1AxisAlignedBB = mc.thePlayer.boundingBox.offset(0.0D, -0.01D, 0.0D).contract(0.001D, 0.001D, 0.001D);
        int var4 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
        int var6 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
        int var8 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        int var9 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);

        for (int var11 = var4; var11 < var5; ++var11) {
            for (int var12 = var6; var12 < var7; ++var12) {
                for (int var13 = var8; var13 < var9; ++var13) {
                    BlockPos pos = new BlockPos(var11, var12, var13);
                    Minecraft.getMinecraft();
                    Block var14 = mc.theWorld.getBlockState(pos).getBlock();
                    if (!(var14 instanceof BlockAir) && !(var14 instanceof BlockLiquid)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static Block getBlock(double x, double y, double z) {
        Minecraft.getMinecraft();
        return mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static Block getBlock(BlockPos block) {
        Minecraft.getMinecraft();
        return mc.theWorld.getBlockState(block).getBlock();
    }

    public static Block getBlock(Entity entity, double offsetY) {
        if (entity == null) {
            return null;
        } else {
            int y = (int) entity.getEntityBoundingBox().offset(0.0D, offsetY, 0.0D).minY;

            for (int x = MathHelper.floor_double(entity.getEntityBoundingBox().minX); x < MathHelper.floor_double(entity.getEntityBoundingBox().maxX) + 1; ++x) {
                int z = MathHelper.floor_double(entity.getEntityBoundingBox().minZ);
                if (z < MathHelper.floor_double(entity.getEntityBoundingBox().maxZ) + 1) {
                    return mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                }
            }

            return null;
        }
    }

    public static boolean isInLiquid() {
        Minecraft.getMinecraft();
        AxisAlignedBB par1AxisAlignedBB = mc.thePlayer.boundingBox.contract(0.001D, 0.001D, 0.001D);
        int var4 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
        int var6 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
        int var8 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        int var9 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);

        for (int var11 = var4; var11 < var5; ++var11) {
            for (int var12 = var6; var12 < var7; ++var12) {
                for (int var13 = var8; var13 < var9; ++var13) {
                    BlockPos pos = new BlockPos(var11, var12, var13);
                    Minecraft.getMinecraft();
                    Block var14 = mc.theWorld.getBlockState(pos).getBlock();
                    if (var14 instanceof BlockLiquid) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean CanStep() {
        Minecraft.getMinecraft();
        AxisAlignedBB par1AxisAlignedBB = mc.thePlayer.boundingBox.contract(0.0D, 0.001D, 0.0D);
        int var6 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);

        for (int y = var6; y < var7; ++y) {
            BlockPos pos = new BlockPos(mc.thePlayer.posX, y, mc.thePlayer.posZ);
            Minecraft.getMinecraft();
            Block var14 = mc.theWorld.getBlockState(pos).getBlock();
            if (var14.isFullBlock()) {
                return true;
            }
        }

        return false;
    }

    public static boolean isOnLadder() {
        boolean onLadder = false;
        int y = (int) mc.thePlayer.getEntityBoundingBox().offset(0.0D, 1.0D, 0.0D).minY;

        for (int x = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z) {
                Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (Objects.nonNull(block) && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLadder) && !(block instanceof BlockVine)) {
                        return false;
                    }

                    onLadder = true;
                }
            }
        }

        return onLadder || mc.thePlayer.isOnLadder();
    }

    public static boolean isInsideBlock() {
        Minecraft.getMinecraft();
        int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX);

        while (true) {
            Minecraft.getMinecraft();
            if (x >= MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1) {
                return false;
            }

            Minecraft.getMinecraft();
            int y = MathHelper.floor_double(mc.thePlayer.boundingBox.minY);

            while (true) {
                Minecraft.getMinecraft();
                if (y >= MathHelper.floor_double(mc.thePlayer.boundingBox.maxY) + 1) {
                    ++x;
                    break;
                }

                Minecraft.getMinecraft();
                int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ);

                while (true) {
                    Minecraft.getMinecraft();
                    if (z >= MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + 1) {
                        ++y;
                        break;
                    }

                    Minecraft.getMinecraft();
                    Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != null && !(block instanceof BlockAir)) {
                        Minecraft.getMinecraft();
                        WorldClient var10001 = mc.theWorld;
                        BlockPos var10002 = new BlockPos(x, y, z);
                        Minecraft.getMinecraft();
                        AxisAlignedBB boundingBox;
                        if ((boundingBox = block.getCollisionBoundingBox(var10001, var10002, mc.theWorld.getBlockState(new BlockPos(x, y, z)))) != null) {
                            Minecraft.getMinecraft();
                            if (mc.thePlayer.boundingBox.intersectsWith(boundingBox)) {
                                return true;
                            }
                        }
                    }

                    ++z;
                }
            }
        }
    }

    public static float[] getBlockRotations(double x, double y, double z) {
        double var4 = x - mc.thePlayer.posX + 0.5D;
        double var6 = z - mc.thePlayer.posZ + 0.5D;
        double var8 = y - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight() - 1.0D);
        double var14 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);
        float var12 = (float) (Math.atan2(var6, var4) * 180.0D / 3.141592653589793D) - 90.0F;
        return new float[]{var12, (float) (-Math.atan2(var8, var14) * 180.0D / 3.141592653589793D)};
    }

    public static float[] getFacingRotations(BlockPos pos, EnumFacing facing) {
        EntityXPOrb temp = new EntityXPOrb(mc.theWorld);
        temp.posX = (double) pos.getX() + 0.5D;
        temp.posY = (double) pos.getY() + 0.5D;
        temp.posZ = (double) pos.getZ() + 0.5D;
        temp.posX += (double) facing.getDirectionVec().getX() * 0.25D;
        temp.posZ += (double) facing.getDirectionVec().getZ() * 0.25D;
        temp.posY += 0.5D;
        return getRotationsNeeded(temp);
    }

    private static float[] getRotationsNeeded(Entity entity) {
        double posX = entity.posX - mc.thePlayer.posX;
        double posY = entity.posY - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        double posZ = entity.posZ - mc.thePlayer.posZ;
        double var14 = MathHelper.sqrt_double(posX * posX + posZ * posZ);
        float yaw = (float) (Math.atan2(posZ, posX) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) (-(Math.atan2(posY, var14) * 180.0D / 3.141592653589793D));
        return new float[]{yaw, pitch};
    }

    public static void updateTool(BlockPos pos) {
        Block block = mc.theWorld.getBlockState(pos).getBlock();
        float strength = 1.0F;
        int bestItemIndex = -1;
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
            if (itemStack == null) {
                continue;
            }
            if ((itemStack.getStrVsBlock(block) > strength)) {
                strength = itemStack.getStrVsBlock(block);
                bestItemIndex = i;
            }
        }
        if (bestItemIndex != -1) {
            mc.thePlayer.inventory.currentItem = bestItemIndex;
        }
    }
}
