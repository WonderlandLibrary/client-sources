package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockDirt extends Block
{
    public static final PropertyEnum VARIANT;
    public static final PropertyBool SNOWY;
    
    protected BlockDirt() {
        super(Material.ground);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockDirt.VARIANT, DirtType.DIRT).withProperty(BlockDirt.SNOWY, false));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        if (state.getValue(BlockDirt.VARIANT) == DirtType.PODZOL) {
            final Block var4 = worldIn.getBlockState(pos.offsetUp()).getBlock();
            state = state.withProperty(BlockDirt.SNOWY, var4 == Blocks.snow || var4 == Blocks.snow_layer);
        }
        return state;
    }
    
    @Override
    public void getSubBlocks(final Item itemIn, final CreativeTabs tab, final List list) {
        list.add(new ItemStack(this, 1, DirtType.DIRT.getMetadata()));
        list.add(new ItemStack(this, 1, DirtType.COARSE_DIRT.getMetadata()));
        list.add(new ItemStack(this, 1, DirtType.PODZOL.getMetadata()));
    }
    
    @Override
    public int getDamageValue(final World worldIn, final BlockPos pos) {
        final IBlockState var3 = worldIn.getBlockState(pos);
        return (var3.getBlock() != this) ? 0 : ((DirtType)var3.getValue(BlockDirt.VARIANT)).getMetadata();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int meta) {
        return this.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.byMetadata(meta));
    }
    
    @Override
    public int getMetaFromState(final IBlockState state) {
        return ((DirtType)state.getValue(BlockDirt.VARIANT)).getMetadata();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockDirt.VARIANT, BlockDirt.SNOWY });
    }
    
    @Override
    public int damageDropped(final IBlockState state) {
        DirtType var2 = (DirtType)state.getValue(BlockDirt.VARIANT);
        if (var2 == DirtType.PODZOL) {
            var2 = DirtType.DIRT;
        }
        return var2.getMetadata();
    }
    
    static {
        VARIANT = PropertyEnum.create("variant", DirtType.class);
        SNOWY = PropertyBool.create("snowy");
    }
    
    public enum DirtType implements IStringSerializable
    {
        DIRT("DIRT", 0, 0, "dirt", "default"), 
        COARSE_DIRT("COARSE_DIRT", 1, 1, "coarse_dirt", "coarse"), 
        PODZOL("PODZOL", 2, 2, "podzol");
        
        private static final DirtType[] METADATA_LOOKUP;
        private static final DirtType[] $VALUES;
        private final int metadata;
        private final String name;
        private final String unlocalizedName;
        
        private DirtType(final String p_i45727_1_, final int p_i45727_2_, final int metadata, final String name) {
            this(p_i45727_1_, p_i45727_2_, metadata, name, name);
        }
        
        private DirtType(final String p_i45728_1_, final int p_i45728_2_, final int metadata, final String name, final String unlocalizedName) {
            this.metadata = metadata;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
        
        public static DirtType byMetadata(int metadata) {
            if (metadata < 0 || metadata >= DirtType.METADATA_LOOKUP.length) {
                metadata = 0;
            }
            return DirtType.METADATA_LOOKUP[metadata];
        }
        
        public int getMetadata() {
            return this.metadata;
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        static {
            METADATA_LOOKUP = new DirtType[values().length];
            $VALUES = new DirtType[] { DirtType.DIRT, DirtType.COARSE_DIRT, DirtType.PODZOL };
            for (final DirtType var4 : values()) {
                DirtType.METADATA_LOOKUP[var4.getMetadata()] = var4;
            }
        }
    }
}
