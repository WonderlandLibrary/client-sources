package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import com.google.common.base.Predicate;

public class BlockNewLog extends BlockLog
{
    public static final PropertyEnum à¢;
    private static final String ¥à = "CL_00000277";
    
    static {
        à¢ = PropertyEnum.HorizonCode_Horizon_È("variant", BlockPlanks.HorizonCode_Horizon_È.class, (Predicate)new Predicate() {
            private static final String HorizonCode_Horizon_È = "CL_00002089";
            
            public boolean HorizonCode_Horizon_È(final BlockPlanks.HorizonCode_Horizon_È p_180194_1_) {
                return p_180194_1_.Â() >= 4;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((BlockPlanks.HorizonCode_Horizon_È)p_apply_1_);
            }
        });
    }
    
    public BlockNewLog() {
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockNewLog.à¢, BlockPlanks.HorizonCode_Horizon_È.Âµá€).HorizonCode_Horizon_È(BlockNewLog.Õ, BlockLog.HorizonCode_Horizon_È.Â));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        list.add(new ItemStack(itemIn, 1, BlockPlanks.HorizonCode_Horizon_È.Âµá€.Â() - 4));
        list.add(new ItemStack(itemIn, 1, BlockPlanks.HorizonCode_Horizon_È.Ó.Â() - 4));
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        IBlockState var2 = this.¥à().HorizonCode_Horizon_È(BlockNewLog.à¢, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È((meta & 0x3) + 4));
        switch (meta & 0xC) {
            case 0: {
                var2 = var2.HorizonCode_Horizon_È(BlockNewLog.Õ, BlockLog.HorizonCode_Horizon_È.Â);
                break;
            }
            case 4: {
                var2 = var2.HorizonCode_Horizon_È(BlockNewLog.Õ, BlockLog.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
                break;
            }
            case 8: {
                var2 = var2.HorizonCode_Horizon_È(BlockNewLog.Õ, BlockLog.HorizonCode_Horizon_È.Ý);
                break;
            }
            default: {
                var2 = var2.HorizonCode_Horizon_È(BlockNewLog.Õ, BlockLog.HorizonCode_Horizon_È.Ø­áŒŠá);
                break;
            }
        }
        return var2;
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((BlockPlanks.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockNewLog.à¢)).Â() - 4;
        switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[((BlockLog.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockNewLog.Õ)).ordinal()]) {
            case 1: {
                var3 |= 0x4;
                break;
            }
            case 2: {
                var3 |= 0x8;
                break;
            }
            case 3: {
                var3 |= 0xC;
                break;
            }
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockNewLog.à¢, BlockNewLog.Õ });
    }
    
    @Override
    protected ItemStack Ó(final IBlockState state) {
        return new ItemStack(Item_1028566121.HorizonCode_Horizon_È(this), 1, ((BlockPlanks.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockNewLog.à¢)).Â() - 4);
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((BlockPlanks.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockNewLog.à¢)).Â() - 4;
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002088";
        
        static {
            HorizonCode_Horizon_È = new int[BlockLog.HorizonCode_Horizon_È.values().length];
            try {
                BlockNewLog.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockLog.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockNewLog.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockLog.HorizonCode_Horizon_È.Ý.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockNewLog.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockLog.HorizonCode_Horizon_È.Ø­áŒŠá.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
        }
    }
}
