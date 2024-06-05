package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public abstract class BlockSlab extends Block
{
    public static final PropertyEnum HALF_PROP;
    
    public BlockSlab(final Material p_i45714_1_) {
        super(p_i45714_1_);
        if (this.isDouble()) {
            this.fullBlock = true;
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
        this.setLightOpacity(255);
    }
    
    protected static boolean func_150003_a(final Block p_150003_0_) {
        return p_150003_0_ == Blocks.stone_slab || p_150003_0_ == Blocks.wooden_slab || p_150003_0_ == Blocks.stone_slab2;
    }
    
    @Override
    protected boolean canSilkHarvest() {
        return false;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
        if (this.isDouble()) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            final IBlockState var3 = access.getBlockState(pos);
            if (var3.getBlock() == this) {
                if (var3.getValue(BlockSlab.HALF_PROP) == EnumBlockHalf.TOP) {
                    this.setBlockBounds(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
                else {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
                }
            }
        }
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        if (this.isDouble()) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
    }
    
    @Override
    public void addCollisionBoxesToList(final World worldIn, final BlockPos pos, final IBlockState state, final AxisAlignedBB mask, final List list, final Entity collidingEntity) {
        this.setBlockBoundsBasedOnState(worldIn, pos);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return this.isDouble();
    }
    
    @Override
    public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        final IBlockState var9 = super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(BlockSlab.HALF_PROP, EnumBlockHalf.BOTTOM);
        return this.isDouble() ? var9 : ((facing != EnumFacing.DOWN && (facing == EnumFacing.UP || hitY <= 0.5)) ? var9 : var9.withProperty(BlockSlab.HALF_PROP, EnumBlockHalf.TOP));
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return this.isDouble() ? 2 : 1;
    }
    
    @Override
    public boolean isFullCube() {
        return this.isDouble();
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        if (this.isDouble()) {
            return super.shouldSideBeRendered(worldIn, pos, side);
        }
        if (side != EnumFacing.UP && side != EnumFacing.DOWN && !super.shouldSideBeRendered(worldIn, pos, side)) {
            return false;
        }
        final BlockPos var4 = pos.offset(side.getOpposite());
        final IBlockState var5 = worldIn.getBlockState(pos);
        final IBlockState var6 = worldIn.getBlockState(var4);
        final boolean var7 = func_150003_a(var5.getBlock()) && var5.getValue(BlockSlab.HALF_PROP) == EnumBlockHalf.TOP;
        final boolean var8 = func_150003_a(var6.getBlock()) && var6.getValue(BlockSlab.HALF_PROP) == EnumBlockHalf.TOP;
        return var8 ? (side == EnumFacing.DOWN || (side == EnumFacing.UP && super.shouldSideBeRendered(worldIn, pos, side)) || (!func_150003_a(var5.getBlock()) || !var7)) : (side == EnumFacing.UP || (side == EnumFacing.DOWN && super.shouldSideBeRendered(worldIn, pos, side)) || (!func_150003_a(var5.getBlock()) || var7));
    }
    
    public abstract String getFullSlabName(final int p0);
    
    @Override
    public int getDamageValue(final World worldIn, final BlockPos pos) {
        return super.getDamageValue(worldIn, pos) & 0x7;
    }
    
    public abstract boolean isDouble();
    
    public abstract IProperty func_176551_l();
    
    public abstract Object func_176553_a(final ItemStack p0);
    
    static {
        HALF_PROP = PropertyEnum.create("half", EnumBlockHalf.class);
    }
    
    public enum EnumBlockHalf implements IStringSerializable
    {
        TOP("TOP", 0, "top"), 
        BOTTOM("BOTTOM", 1, "bottom");
        
        private static final EnumBlockHalf[] $VALUES;
        private final String halfName;
        
        private EnumBlockHalf(final String p_i45713_1_, final int p_i45713_2_, final String p_i45713_3_) {
            this.halfName = p_i45713_3_;
        }
        
        @Override
        public String toString() {
            return this.halfName;
        }
        
        @Override
        public String getName() {
            return this.halfName;
        }
        
        static {
            $VALUES = new EnumBlockHalf[] { EnumBlockHalf.TOP, EnumBlockHalf.BOTTOM };
        }
    }
}
