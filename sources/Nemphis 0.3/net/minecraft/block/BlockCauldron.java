/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCauldron
extends Block {
    public static final PropertyInteger field_176591_a = PropertyInteger.create("level", 0, 3);
    private static final String __OBFID = "CL_00000213";

    public BlockCauldron() {
        super(Material.iron);
        this.setDefaultState(this.blockState.getBaseState().withProperty(field_176591_a, Integer.valueOf(0)));
    }

    @Override
    public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.3125f, 1.0f);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        float var7 = 0.125f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, var7, 1.0f, 1.0f);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var7);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        this.setBlockBounds(1.0f - var7, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        this.setBlockBounds(0.0f, 0.0f, 1.0f - var7, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        this.setBlockBoundsForItemRender();
    }

    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        int var5 = (Integer)state.getValue(field_176591_a);
        float var6 = (float)pos.getY() + (6.0f + (float)(3 * var5)) / 16.0f;
        if (!worldIn.isRemote && entityIn.isBurning() && var5 > 0 && entityIn.getEntityBoundingBox().minY <= (double)var6) {
            entityIn.extinguish();
            this.func_176590_a(worldIn, pos, state, var5 - 1);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemArmor var12;
        if (worldIn.isRemote) {
            return true;
        }
        ItemStack var9 = playerIn.inventory.getCurrentItem();
        if (var9 == null) {
            return true;
        }
        int var10 = (Integer)state.getValue(field_176591_a);
        Item var11 = var9.getItem();
        if (var11 == Items.water_bucket) {
            if (var10 < 3) {
                if (!playerIn.capabilities.isCreativeMode) {
                    playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(Items.bucket));
                }
                this.func_176590_a(worldIn, pos, state, 3);
            }
            return true;
        }
        if (var11 == Items.glass_bottle) {
            if (var10 > 0) {
                if (!playerIn.capabilities.isCreativeMode) {
                    ItemStack var13 = new ItemStack(Items.potionitem, 1, 0);
                    if (!playerIn.inventory.addItemStackToInventory(var13)) {
                        worldIn.spawnEntityInWorld(new EntityItem(worldIn, (double)pos.getX() + 0.5, (double)pos.getY() + 1.5, (double)pos.getZ() + 0.5, var13));
                    } else if (playerIn instanceof EntityPlayerMP) {
                        ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                    }
                    --var9.stackSize;
                    if (var9.stackSize <= 0) {
                        playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, null);
                    }
                }
                this.func_176590_a(worldIn, pos, state, var10 - 1);
            }
            return true;
        }
        if (var10 > 0 && var11 instanceof ItemArmor && (var12 = (ItemArmor)var11).getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER && var12.hasColor(var9)) {
            var12.removeColor(var9);
            this.func_176590_a(worldIn, pos, state, var10 - 1);
            return true;
        }
        if (var10 > 0 && var11 instanceof ItemBanner && TileEntityBanner.func_175113_c(var9) > 0) {
            ItemStack var13 = var9.copy();
            var13.stackSize = 1;
            TileEntityBanner.func_175117_e(var13);
            if (var9.stackSize <= 1 && !playerIn.capabilities.isCreativeMode) {
                playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, var13);
            } else {
                if (!playerIn.inventory.addItemStackToInventory(var13)) {
                    worldIn.spawnEntityInWorld(new EntityItem(worldIn, (double)pos.getX() + 0.5, (double)pos.getY() + 1.5, (double)pos.getZ() + 0.5, var13));
                } else if (playerIn instanceof EntityPlayerMP) {
                    ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                }
                if (!playerIn.capabilities.isCreativeMode) {
                    --var9.stackSize;
                }
            }
            if (!playerIn.capabilities.isCreativeMode) {
                this.func_176590_a(worldIn, pos, state, var10 - 1);
            }
            return true;
        }
        return false;
    }

    public void func_176590_a(World worldIn, BlockPos p_176590_2_, IBlockState p_176590_3_, int p_176590_4_) {
        worldIn.setBlockState(p_176590_2_, p_176590_3_.withProperty(field_176591_a, Integer.valueOf(MathHelper.clamp_int(p_176590_4_, 0, 3))), 2);
        worldIn.updateComparatorOutputLevel(p_176590_2_, this);
    }

    @Override
    public void fillWithRain(World worldIn, BlockPos pos) {
        IBlockState var3;
        if (worldIn.rand.nextInt(20) == 1 && (Integer)(var3 = worldIn.getBlockState(pos)).getValue(field_176591_a) < 3) {
            worldIn.setBlockState(pos, var3.cycleProperty(field_176591_a), 2);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.cauldron;
    }

    @Override
    public Item getItem(World worldIn, BlockPos pos) {
        return Items.cauldron;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World worldIn, BlockPos pos) {
        return (Integer)worldIn.getBlockState(pos).getValue(field_176591_a);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(field_176591_a, Integer.valueOf(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (Integer)state.getValue(field_176591_a);
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, field_176591_a);
    }
}

