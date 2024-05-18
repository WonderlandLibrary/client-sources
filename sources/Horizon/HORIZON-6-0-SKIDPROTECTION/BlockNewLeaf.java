package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import com.google.common.base.Predicate;

public class BlockNewLeaf extends BlockLeaves
{
    public static final PropertyEnum È;
    private static final String áŠ = "CL_00000276";
    
    static {
        È = PropertyEnum.HorizonCode_Horizon_È("variant", BlockPlanks.HorizonCode_Horizon_È.class, (Predicate)new Predicate() {
            private static final String HorizonCode_Horizon_È = "CL_00002090";
            
            public boolean HorizonCode_Horizon_È(final BlockPlanks.HorizonCode_Horizon_È p_180195_1_) {
                return p_180195_1_.Â() >= 4;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((BlockPlanks.HorizonCode_Horizon_È)p_apply_1_);
            }
        });
    }
    
    public BlockNewLeaf() {
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockNewLeaf.È, BlockPlanks.HorizonCode_Horizon_È.Âµá€).HorizonCode_Horizon_È(BlockNewLeaf.à¢, true).HorizonCode_Horizon_È(BlockNewLeaf.Õ, true));
    }
    
    @Override
    protected void Â(final World worldIn, final BlockPos p_176234_2_, final IBlockState p_176234_3_, final int p_176234_4_) {
        if (p_176234_3_.HorizonCode_Horizon_È(BlockNewLeaf.È) == BlockPlanks.HorizonCode_Horizon_È.Ó && worldIn.Å.nextInt(p_176234_4_) == 0) {
            Block.HorizonCode_Horizon_È(worldIn, p_176234_2_, new ItemStack(Items.Âµá€, 1, 0));
        }
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((BlockPlanks.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockNewLeaf.È)).Â();
    }
    
    @Override
    public int Ó(final World worldIn, final BlockPos pos) {
        final IBlockState var3 = worldIn.Â(pos);
        return var3.Ý().Ý(var3) & 0x3;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        list.add(new ItemStack(itemIn, 1, 0));
        list.add(new ItemStack(itemIn, 1, 1));
    }
    
    @Override
    protected ItemStack Ó(final IBlockState state) {
        return new ItemStack(Item_1028566121.HorizonCode_Horizon_È(this), 1, ((BlockPlanks.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockNewLeaf.È)).Â() - 4);
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockNewLeaf.È, this.Âµá€(meta)).HorizonCode_Horizon_È(BlockNewLeaf.Õ, (meta & 0x4) == 0x0).HorizonCode_Horizon_È(BlockNewLeaf.à¢, (meta & 0x8) > 0);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((BlockPlanks.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockNewLeaf.È)).Â() - 4;
        if (!(boolean)state.HorizonCode_Horizon_È(BlockNewLeaf.Õ)) {
            var3 |= 0x4;
        }
        if (state.HorizonCode_Horizon_È(BlockNewLeaf.à¢)) {
            var3 |= 0x8;
        }
        return var3;
    }
    
    @Override
    public BlockPlanks.HorizonCode_Horizon_È Âµá€(final int p_176233_1_) {
        return BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È((p_176233_1_ & 0x3) + 4);
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockNewLeaf.È, BlockNewLeaf.à¢, BlockNewLeaf.Õ });
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final EntityPlayer playerIn, final BlockPos pos, final IBlockState state, final TileEntity te) {
        if (!worldIn.ŠÄ && playerIn.áŒŠá() != null && playerIn.áŒŠá().HorizonCode_Horizon_È() == Items.áˆºà) {
            playerIn.HorizonCode_Horizon_È(StatList.É[Block.HorizonCode_Horizon_È(this)]);
            Block.HorizonCode_Horizon_È(worldIn, pos, new ItemStack(Item_1028566121.HorizonCode_Horizon_È(this), 1, ((BlockPlanks.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockNewLeaf.È)).Â() - 4));
        }
        else {
            super.HorizonCode_Horizon_È(worldIn, playerIn, pos, state, te);
        }
    }
}
