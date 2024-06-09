package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBanner extends BlockContainer
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);
    protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);

    protected BlockBanner()
    {
        super(Material.WOOD);
    }

    /**
     * Gets the localized name of this block. Used for the statistics page.
     */
    public String getLocalizedName()
    {
        return I18n.translateToLocal("item.banner.white.name");
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return true;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    /**
     * Return true if an entity can be spawned inside the block (used to get the player's bed spawn location)
     */
    public boolean canSpawnInBlock()
    {
        return true;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityBanner();
    }

    @Nullable

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.BANNER;
    }

    @Nullable
    private ItemStack getTileDataItemStack(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof TileEntityBanner)
        {
            ItemStack itemstack = new ItemStack(Items.BANNER, 1, ((TileEntityBanner)tileentity).getBaseColor());
            NBTTagCompound nbttagcompound = tileentity.func_189515_b(new NBTTagCompound());
            nbttagcompound.removeTag("x");
            nbttagcompound.removeTag("y");
            nbttagcompound.removeTag("z");
            nbttagcompound.removeTag("id");
            itemstack.setTagInfo("BlockEntityTag", nbttagcompound);
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        ItemStack itemstack = this.getTileDataItemStack(worldIn, pos, state);
        return itemstack != null ? itemstack : new ItemStack(Items.BANNER);
    }

    /**
     * Spawns this Block's drops into the World as EntityItems.
     */
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        ItemStack itemstack = this.getTileDataItemStack(worldIn, pos, state);

        if (itemstack != null)
        {
            spawnAsEntity(worldIn, pos, itemstack);
        }
        else
        {
            super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
        }
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return !this.hasInvalidNeighbor(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos);
    }

    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack)
    {
        if (te instanceof TileEntityBanner)
        {
            TileEntityBanner tileentitybanner = (TileEntityBanner)te;
            ItemStack itemstack = new ItemStack(Items.BANNER, 1, ((TileEntityBanner)te).getBaseColor());
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            TileEntityBanner.setBaseColorAndPatterns(nbttagcompound, tileentitybanner.getBaseColor(), tileentitybanner.getPatterns());
            itemstack.setTagInfo("BlockEntityTag", nbttagcompound);
            spawnAsEntity(worldIn, pos, itemstack);
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, (TileEntity)null, stack);
        }
    }

    public static class BlockBannerHanging extends BlockBanner
    {
        protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 0.78125D, 1.0D);
        protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.78125D, 0.125D);
        protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 0.78125D, 1.0D);
        protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 0.78125D, 1.0D);

        public BlockBannerHanging()
        {
            this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        }

        public IBlockState withRotation(IBlockState state, Rotation rot)
        {
            return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
        }

        public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
        {
            return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
        }

        public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
        {
            switch ((EnumFacing)state.getValue(FACING))
            {
                case NORTH:
                default:
                    return NORTH_AABB;

                case SOUTH:
                    return SOUTH_AABB;

                case WEST:
                    return WEST_AABB;

                case EAST:
                    return EAST_AABB;
            }
        }

        public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_)
        {
            EnumFacing enumfacing = (EnumFacing)p_189540_1_.getValue(FACING);

            if (!p_189540_2_.getBlockState(p_189540_3_.offset(enumfacing.getOpposite())).getMaterial().isSolid())
            {
                this.dropBlockAsItem(p_189540_2_, p_189540_3_, p_189540_1_, 0);
                p_189540_2_.setBlockToAir(p_189540_3_);
            }

            super.func_189540_a(p_189540_1_, p_189540_2_, p_189540_3_, p_189540_4_);
        }

        public IBlockState getStateFromMeta(int meta)
        {
            EnumFacing enumfacing = EnumFacing.getFront(meta);

            if (enumfacing.getAxis() == EnumFacing.Axis.Y)
            {
                enumfacing = EnumFacing.NORTH;
            }

            return this.getDefaultState().withProperty(FACING, enumfacing);
        }

        public int getMetaFromState(IBlockState state)
        {
            return ((EnumFacing)state.getValue(FACING)).getIndex();
        }

        protected BlockStateContainer createBlockState()
        {
            return new BlockStateContainer(this, new IProperty[] {FACING});
        }
    }

    public static class BlockBannerStanding extends BlockBanner
    {
        public BlockBannerStanding()
        {
            this.setDefaultState(this.blockState.getBaseState().withProperty(ROTATION, Integer.valueOf(0)));
        }

        public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
        {
            return STANDING_AABB;
        }

        public IBlockState withRotation(IBlockState state, Rotation rot)
        {
            return state.withProperty(ROTATION, Integer.valueOf(rot.rotate(((Integer)state.getValue(ROTATION)).intValue(), 16)));
        }

        public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
        {
            return state.withProperty(ROTATION, Integer.valueOf(mirrorIn.mirrorRotation(((Integer)state.getValue(ROTATION)).intValue(), 16)));
        }

        public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_)
        {
            if (!p_189540_2_.getBlockState(p_189540_3_.down()).getMaterial().isSolid())
            {
                this.dropBlockAsItem(p_189540_2_, p_189540_3_, p_189540_1_, 0);
                p_189540_2_.setBlockToAir(p_189540_3_);
            }

            super.func_189540_a(p_189540_1_, p_189540_2_, p_189540_3_, p_189540_4_);
        }

        public IBlockState getStateFromMeta(int meta)
        {
            return this.getDefaultState().withProperty(ROTATION, Integer.valueOf(meta));
        }

        public int getMetaFromState(IBlockState state)
        {
            return ((Integer)state.getValue(ROTATION)).intValue();
        }

        protected BlockStateContainer createBlockState()
        {
            return new BlockStateContainer(this, new IProperty[] {ROTATION});
        }
    }
}
