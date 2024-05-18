package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import java.util.*;

public class ItemMonsterPlacer extends Item
{
    private static final String[] I;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ItemMonsterPlacer() {
        this.setHasSubtypes(" ".length() != 0);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return " ".length() != 0;
        }
        if (!entityPlayer.canPlayerEdit(offset.offset(enumFacing), enumFacing, itemStack)) {
            return "".length() != 0;
        }
        final IBlockState blockState = world.getBlockState(offset);
        if (blockState.getBlock() == Blocks.mob_spawner) {
            final TileEntity tileEntity = world.getTileEntity(offset);
            if (tileEntity instanceof TileEntityMobSpawner) {
                ((TileEntityMobSpawner)tileEntity).getSpawnerBaseLogic().setEntityName(EntityList.getStringFromID(itemStack.getMetadata()));
                tileEntity.markDirty();
                world.markBlockForUpdate(offset);
                if (!entityPlayer.capabilities.isCreativeMode) {
                    itemStack.stackSize -= " ".length();
                }
                return " ".length() != 0;
            }
        }
        offset = offset.offset(enumFacing);
        double n4 = 0.0;
        if (enumFacing == EnumFacing.UP && blockState instanceof BlockFence) {
            n4 = 0.5;
        }
        final Entity spawnCreature = spawnCreature(world, itemStack.getMetadata(), offset.getX() + 0.5, offset.getY() + n4, offset.getZ() + 0.5);
        if (spawnCreature != null) {
            if (spawnCreature instanceof EntityLivingBase && itemStack.hasDisplayName()) {
                spawnCreature.setCustomNameTag(itemStack.getDisplayName());
            }
            if (!entityPlayer.capabilities.isCreativeMode) {
                itemStack.stackSize -= " ".length();
            }
        }
        return " ".length() != 0;
    }
    
    public static Entity spawnCreature(final World world, final int n, final double n2, final double n3, final double n4) {
        if (!EntityList.entityEggs.containsKey(n)) {
            return null;
        }
        Entity entityByID = null;
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < " ".length()) {
            entityByID = EntityList.createEntityByID(n, world);
            if (entityByID instanceof EntityLivingBase) {
                final EntityLiving entityLiving = (EntityLiving)entityByID;
                entityByID.setLocationAndAngles(n2, n3, n4, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0f), 0.0f);
                entityLiving.rotationYawHead = entityLiving.rotationYaw;
                entityLiving.renderYawOffset = entityLiving.rotationYaw;
                entityLiving.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entityLiving)), null);
                world.spawnEntityInWorld(entityByID);
                entityLiving.playLivingSound();
            }
            ++i;
        }
        return entityByID;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (world.isRemote) {
            return itemStack;
        }
        final MovingObjectPosition movingObjectPositionFromPlayer = this.getMovingObjectPositionFromPlayer(world, entityPlayer, " ".length() != 0);
        if (movingObjectPositionFromPlayer == null) {
            return itemStack;
        }
        if (movingObjectPositionFromPlayer.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final BlockPos blockPos = movingObjectPositionFromPlayer.getBlockPos();
            if (!world.isBlockModifiable(entityPlayer, blockPos)) {
                return itemStack;
            }
            if (!entityPlayer.canPlayerEdit(blockPos, movingObjectPositionFromPlayer.sideHit, itemStack)) {
                return itemStack;
            }
            if (world.getBlockState(blockPos).getBlock() instanceof BlockLiquid) {
                final Entity spawnCreature = spawnCreature(world, itemStack.getMetadata(), blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
                if (spawnCreature != null) {
                    if (spawnCreature instanceof EntityLivingBase && itemStack.hasDisplayName()) {
                        ((EntityLiving)spawnCreature).setCustomNameTag(itemStack.getDisplayName());
                    }
                    if (!entityPlayer.capabilities.isCreativeMode) {
                        itemStack.stackSize -= " ".length();
                    }
                    entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                }
            }
        }
        return itemStack;
    }
    
    private static void I() {
        (I = new String[0x88 ^ 0x8C])["".length()] = I("[*5\u001f\"", "uDTrG");
        ItemMonsterPlacer.I[" ".length()] = I("D", "dCWDL");
        ItemMonsterPlacer.I["  ".length()] = I("\u0014\u001f\u0003%\f\b_", "qqwLx");
        ItemMonsterPlacer.I["   ".length()] = I("i?\u00137\u0002", "GQrZg");
    }
    
    static {
        I();
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        final EntityList.EntityEggInfo entityEggInfo = EntityList.entityEggs.get(itemStack.getMetadata());
        int n2;
        if (entityEggInfo != null) {
            if (n == 0) {
                n2 = entityEggInfo.primaryColor;
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                n2 = entityEggInfo.secondaryColor;
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
        }
        else {
            n2 = 4264373 + 10311547 - 13170270 + 15371565;
        }
        return n2;
    }
    
    @Override
    public String getItemStackDisplayName(final ItemStack itemStack) {
        String s = new StringBuilder().append(StatCollector.translateToLocal(String.valueOf(this.getUnlocalizedName()) + ItemMonsterPlacer.I["".length()])).toString().trim();
        final String stringFromID = EntityList.getStringFromID(itemStack.getMetadata());
        if (stringFromID != null) {
            s = String.valueOf(s) + ItemMonsterPlacer.I[" ".length()] + StatCollector.translateToLocal(ItemMonsterPlacer.I["  ".length()] + stringFromID + ItemMonsterPlacer.I["   ".length()]);
        }
        return s;
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        final Iterator<EntityList.EntityEggInfo> iterator = EntityList.entityEggs.values().iterator();
        "".length();
        if (4 < 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            list.add(new ItemStack(item, " ".length(), iterator.next().spawnedID));
        }
    }
}
