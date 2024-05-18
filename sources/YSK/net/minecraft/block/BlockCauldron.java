package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.stats.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;

public class BlockCauldron extends Block
{
    private static final String[] I;
    public static final PropertyInteger LEVEL;
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return " ".length() != 0;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.cauldron;
    }
    
    @Override
    public void fillWithRain(final World world, final BlockPos blockPos) {
        if (world.rand.nextInt(0x6 ^ 0x12) == " ".length()) {
            final IBlockState blockState = world.getBlockState(blockPos);
            if (blockState.getValue((IProperty<Integer>)BlockCauldron.LEVEL) < "   ".length()) {
                world.setBlockState(blockPos, blockState.cycleProperty((IProperty<Comparable>)BlockCauldron.LEVEL), "  ".length());
            }
        }
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    public BlockCauldron() {
        super(Material.iron, MapColor.stoneColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockCauldron.LEVEL, "".length()));
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos).getValue((IProperty<Integer>)BlockCauldron.LEVEL);
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockCauldron.LEVEL;
        return new BlockState(this, array);
    }
    
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setWaterLevel(final World world, final BlockPos blockPos, final IBlockState blockState, final int n) {
        world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockCauldron.LEVEL, MathHelper.clamp_int(n, "".length(), "   ".length())), "  ".length());
        world.updateComparatorOutputLevel(blockPos, this);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        final int intValue = blockState.getValue((IProperty<Integer>)BlockCauldron.LEVEL);
        final float n = blockPos.getY() + (6.0f + "   ".length() * intValue) / 16.0f;
        if (!world.isRemote && entity.isBurning() && intValue > 0 && entity.getEntityBoundingBox().minY <= n) {
            entity.extinguish();
            this.setWaterLevel(world, blockPos, blockState, intValue - " ".length());
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u000e\n>\u0014\t", "boHqe");
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<Integer>)BlockCauldron.LEVEL);
    }
    
    static {
        I();
        LEVEL = PropertyInteger.create(BlockCauldron.I["".length()], "".length(), "   ".length());
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List<AxisAlignedBB> list, final Entity entity) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.3125f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        final float n = 0.125f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, n, 1.0f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(1.0f - n, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(0.0f, 0.0f, 1.0f - n, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBoundsForItemRender();
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.cauldron;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockCauldron.LEVEL, n);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return " ".length() != 0;
        }
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem == null) {
            return " ".length() != 0;
        }
        final int intValue = blockState.getValue((IProperty<Integer>)BlockCauldron.LEVEL);
        final Item item = currentItem.getItem();
        if (item == Items.water_bucket) {
            if (intValue < "   ".length()) {
                if (!entityPlayer.capabilities.isCreativeMode) {
                    entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, new ItemStack(Items.bucket));
                }
                entityPlayer.triggerAchievement(StatList.field_181725_I);
                this.setWaterLevel(world, blockPos, blockState, "   ".length());
            }
            return " ".length() != 0;
        }
        if (item == Items.glass_bottle) {
            if (intValue > 0) {
                if (!entityPlayer.capabilities.isCreativeMode) {
                    final ItemStack itemStack = new ItemStack(Items.potionitem, " ".length(), "".length());
                    if (!entityPlayer.inventory.addItemStackToInventory(itemStack)) {
                        world.spawnEntityInWorld(new EntityItem(world, blockPos.getX() + 0.5, blockPos.getY() + 1.5, blockPos.getZ() + 0.5, itemStack));
                        "".length();
                        if (4 <= -1) {
                            throw null;
                        }
                    }
                    else if (entityPlayer instanceof EntityPlayerMP) {
                        ((EntityPlayerMP)entityPlayer).sendContainerToPlayer(entityPlayer.inventoryContainer);
                    }
                    entityPlayer.triggerAchievement(StatList.field_181726_J);
                    final ItemStack itemStack2 = currentItem;
                    itemStack2.stackSize -= " ".length();
                    if (currentItem.stackSize <= 0) {
                        entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
                    }
                }
                this.setWaterLevel(world, blockPos, blockState, intValue - " ".length());
            }
            return " ".length() != 0;
        }
        if (intValue > 0 && item instanceof ItemArmor) {
            final ItemArmor itemArmor = (ItemArmor)item;
            if (itemArmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER && itemArmor.hasColor(currentItem)) {
                itemArmor.removeColor(currentItem);
                this.setWaterLevel(world, blockPos, blockState, intValue - " ".length());
                entityPlayer.triggerAchievement(StatList.field_181727_K);
                return " ".length() != 0;
            }
        }
        if (intValue > 0 && item instanceof ItemBanner && TileEntityBanner.getPatterns(currentItem) > 0) {
            final ItemStack copy = currentItem.copy();
            copy.stackSize = " ".length();
            TileEntityBanner.removeBannerData(copy);
            if (currentItem.stackSize <= " ".length() && !entityPlayer.capabilities.isCreativeMode) {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, copy);
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                if (!entityPlayer.inventory.addItemStackToInventory(copy)) {
                    world.spawnEntityInWorld(new EntityItem(world, blockPos.getX() + 0.5, blockPos.getY() + 1.5, blockPos.getZ() + 0.5, copy));
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                }
                else if (entityPlayer instanceof EntityPlayerMP) {
                    ((EntityPlayerMP)entityPlayer).sendContainerToPlayer(entityPlayer.inventoryContainer);
                }
                entityPlayer.triggerAchievement(StatList.field_181728_L);
                if (!entityPlayer.capabilities.isCreativeMode) {
                    final ItemStack itemStack3 = currentItem;
                    itemStack3.stackSize -= " ".length();
                }
            }
            if (!entityPlayer.capabilities.isCreativeMode) {
                this.setWaterLevel(world, blockPos, blockState, intValue - " ".length());
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
}
