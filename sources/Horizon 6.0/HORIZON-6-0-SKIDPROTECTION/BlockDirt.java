package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class BlockDirt extends Block
{
    public static final PropertyEnum Õ;
    public static final PropertyBool à¢;
    private static final String ŠÂµà = "CL_00000228";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("variant", HorizonCode_Horizon_È.class);
        à¢ = PropertyBool.HorizonCode_Horizon_È("snowy");
    }
    
    protected BlockDirt() {
        super(Material.Ý);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockDirt.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È).HorizonCode_Horizon_È(BlockDirt.à¢, false));
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        if (state.HorizonCode_Horizon_È(BlockDirt.Õ) == HorizonCode_Horizon_È.Ý) {
            final Block var4 = worldIn.Â(pos.Ø­áŒŠá()).Ý();
            state = state.HorizonCode_Horizon_È(BlockDirt.à¢, var4 == Blocks.ˆà¢ || var4 == Blocks.áŒŠá€);
        }
        return state;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        list.add(new ItemStack(this, 1, HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()));
        list.add(new ItemStack(this, 1, HorizonCode_Horizon_È.Â.Â()));
        list.add(new ItemStack(this, 1, HorizonCode_Horizon_È.Ý.Â()));
    }
    
    @Override
    public int Ó(final World worldIn, final BlockPos pos) {
        final IBlockState var3 = worldIn.Â(pos);
        return (var3.Ý() != this) ? 0 : ((HorizonCode_Horizon_È)var3.HorizonCode_Horizon_È(BlockDirt.Õ)).Â();
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockDirt.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta));
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockDirt.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockDirt.Õ, BlockDirt.à¢ });
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        HorizonCode_Horizon_È var2 = (HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockDirt.Õ);
        if (var2 == HorizonCode_Horizon_È.Ý) {
            var2 = HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        }
        return var2.Â();
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("DIRT", 0, "DIRT", 0, 0, "dirt", "default"), 
        Â("COARSE_DIRT", 1, "COARSE_DIRT", 1, 1, "coarse_dirt", "coarse"), 
        Ý("PODZOL", 2, "PODZOL", 2, 2, "podzol");
        
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private final int Âµá€;
        private final String Ó;
        private final String à;
        private static final HorizonCode_Horizon_È[] Ø;
        private static final String áŒŠÆ = "CL_00002125";
        
        static {
            áˆºÑ¢Õ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            Ø­áŒŠá = new HorizonCode_Horizon_È[values().length];
            Ø = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.Ø­áŒŠá[var4.Â()] = var4;
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45727_1_, final int p_i45727_2_, final int metadata, final String name) {
            this(s, n, p_i45727_1_, p_i45727_2_, metadata, name, name);
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45728_1_, final int p_i45728_2_, final int metadata, final String name, final String unlocalizedName) {
            this.Âµá€ = metadata;
            this.Ó = name;
            this.à = unlocalizedName;
        }
        
        public int Â() {
            return this.Âµá€;
        }
        
        public String Ý() {
            return this.à;
        }
        
        @Override
        public String toString() {
            return this.Ó;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(int metadata) {
            if (metadata < 0 || metadata >= HorizonCode_Horizon_È.Ø­áŒŠá.length) {
                metadata = 0;
            }
            return HorizonCode_Horizon_È.Ø­áŒŠá[metadata];
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.Ó;
        }
    }
}
