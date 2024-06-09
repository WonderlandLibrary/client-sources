package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import com.google.common.base.Predicate;

public class BlockOldLeaf extends BlockLeaves
{
    public static final PropertyEnum È;
    private static final String áŠ = "CL_00000280";
    
    static {
        È = PropertyEnum.HorizonCode_Horizon_È("variant", BlockPlanks.HorizonCode_Horizon_È.class, (Predicate)new Predicate() {
            private static final String HorizonCode_Horizon_È = "CL_00002085";
            
            public boolean HorizonCode_Horizon_È(final BlockPlanks.HorizonCode_Horizon_È p_180202_1_) {
                return p_180202_1_.Â() < 4;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((BlockPlanks.HorizonCode_Horizon_È)p_apply_1_);
            }
        });
    }
    
    public BlockOldLeaf() {
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockOldLeaf.È, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È).HorizonCode_Horizon_È(BlockOldLeaf.à¢, true).HorizonCode_Horizon_È(BlockOldLeaf.Õ, true));
    }
    
    @Override
    public int Âµá€(final IBlockState state) {
        if (state.Ý() != this) {
            return super.Âµá€(state);
        }
        final BlockPlanks.HorizonCode_Horizon_È var2 = (BlockPlanks.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockOldLeaf.È);
        return (var2 == BlockPlanks.HorizonCode_Horizon_È.Â) ? ColorizerFoliage.HorizonCode_Horizon_È() : ((var2 == BlockPlanks.HorizonCode_Horizon_È.Ý) ? ColorizerFoliage.Â() : super.Âµá€(state));
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
        final IBlockState var4 = worldIn.Â(pos);
        if (var4.Ý() == this) {
            final BlockPlanks.HorizonCode_Horizon_È var5 = (BlockPlanks.HorizonCode_Horizon_È)var4.HorizonCode_Horizon_È(BlockOldLeaf.È);
            if (var5 == BlockPlanks.HorizonCode_Horizon_È.Â) {
                return ColorizerFoliage.HorizonCode_Horizon_È();
            }
            if (var5 == BlockPlanks.HorizonCode_Horizon_È.Ý) {
                return ColorizerFoliage.Â();
            }
        }
        return super.HorizonCode_Horizon_È(worldIn, pos, renderPass);
    }
    
    @Override
    protected void Â(final World worldIn, final BlockPos p_176234_2_, final IBlockState p_176234_3_, final int p_176234_4_) {
        if (p_176234_3_.HorizonCode_Horizon_È(BlockOldLeaf.È) == BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È && worldIn.Å.nextInt(p_176234_4_) == 0) {
            Block.HorizonCode_Horizon_È(worldIn, p_176234_2_, new ItemStack(Items.Âµá€, 1, 0));
        }
    }
    
    @Override
    protected int áŒŠÆ(final IBlockState p_176232_1_) {
        return (p_176232_1_.HorizonCode_Horizon_È(BlockOldLeaf.È) == BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá) ? 40 : super.áŒŠÆ(p_176232_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        list.add(new ItemStack(itemIn, 1, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()));
        list.add(new ItemStack(itemIn, 1, BlockPlanks.HorizonCode_Horizon_È.Â.Â()));
        list.add(new ItemStack(itemIn, 1, BlockPlanks.HorizonCode_Horizon_È.Ý.Â()));
        list.add(new ItemStack(itemIn, 1, BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â()));
    }
    
    @Override
    protected ItemStack Ó(final IBlockState state) {
        return new ItemStack(Item_1028566121.HorizonCode_Horizon_È(this), 1, ((BlockPlanks.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockOldLeaf.È)).Â());
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockOldLeaf.È, this.Âµá€(meta)).HorizonCode_Horizon_È(BlockOldLeaf.Õ, (meta & 0x4) == 0x0).HorizonCode_Horizon_È(BlockOldLeaf.à¢, (meta & 0x8) > 0);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((BlockPlanks.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockOldLeaf.È)).Â();
        if (!(boolean)state.HorizonCode_Horizon_È(BlockOldLeaf.Õ)) {
            var3 |= 0x4;
        }
        if (state.HorizonCode_Horizon_È(BlockOldLeaf.à¢)) {
            var3 |= 0x8;
        }
        return var3;
    }
    
    @Override
    public BlockPlanks.HorizonCode_Horizon_È Âµá€(final int p_176233_1_) {
        return BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È((p_176233_1_ & 0x3) % 4);
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockOldLeaf.È, BlockOldLeaf.à¢, BlockOldLeaf.Õ });
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((BlockPlanks.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockOldLeaf.È)).Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final EntityPlayer playerIn, final BlockPos pos, final IBlockState state, final TileEntity te) {
        if (!worldIn.ŠÄ && playerIn.áŒŠá() != null && playerIn.áŒŠá().HorizonCode_Horizon_È() == Items.áˆºà) {
            playerIn.HorizonCode_Horizon_È(StatList.É[Block.HorizonCode_Horizon_È(this)]);
            Block.HorizonCode_Horizon_È(worldIn, pos, new ItemStack(Item_1028566121.HorizonCode_Horizon_È(this), 1, ((BlockPlanks.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockOldLeaf.È)).Â()));
        }
        else {
            super.HorizonCode_Horizon_È(worldIn, playerIn, pos, state, te);
        }
    }
}
