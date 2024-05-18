package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.Iterator;
import com.google.common.base.Predicate;

public class BlockStem extends BlockBush implements IGrowable
{
    public static final PropertyInteger Õ;
    public static final PropertyDirection à¢;
    private final Block ŠÂµà;
    private static final String ¥à = "CL_00000316";
    
    static {
        Õ = PropertyInteger.HorizonCode_Horizon_È("age", 0, 7);
        à¢ = PropertyDirection.HorizonCode_Horizon_È("facing", (Predicate)new Predicate() {
            private static final String HorizonCode_Horizon_È = "CL_00002059";
            
            public boolean HorizonCode_Horizon_È(final EnumFacing p_177218_1_) {
                return p_177218_1_ != EnumFacing.HorizonCode_Horizon_È;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((EnumFacing)p_apply_1_);
            }
        });
    }
    
    protected BlockStem(final Block p_i45430_1_) {
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockStem.Õ, 0).HorizonCode_Horizon_È(BlockStem.à¢, EnumFacing.Â));
        this.ŠÂµà = p_i45430_1_;
        this.HorizonCode_Horizon_È(true);
        final float var2 = 0.125f;
        this.HorizonCode_Horizon_È(0.5f - var2, 0.0f, 0.5f - var2, 0.5f + var2, 0.25f, 0.5f + var2);
        this.HorizonCode_Horizon_È((CreativeTabs)null);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        state = state.HorizonCode_Horizon_È(BlockStem.à¢, EnumFacing.Â);
        for (final EnumFacing var5 : EnumFacing.Ý.HorizonCode_Horizon_È) {
            if (worldIn.Â(pos.HorizonCode_Horizon_È(var5)).Ý() == this.ŠÂµà) {
                state = state.HorizonCode_Horizon_È(BlockStem.à¢, var5);
                break;
            }
        }
        return state;
    }
    
    @Override
    protected boolean Ý(final Block ground) {
        return ground == Blocks.£Â;
    }
    
    @Override
    public void Â(final World worldIn, BlockPos pos, IBlockState state, final Random rand) {
        super.Â(worldIn, pos, state, rand);
        if (worldIn.ˆÏ­(pos.Ø­áŒŠá()) >= 9) {
            final float var5 = BlockCrops.HorizonCode_Horizon_È(this, worldIn, pos);
            if (rand.nextInt((int)(25.0f / var5) + 1) == 0) {
                final int var6 = (int)state.HorizonCode_Horizon_È(BlockStem.Õ);
                if (var6 < 7) {
                    state = state.HorizonCode_Horizon_È(BlockStem.Õ, var6 + 1);
                    worldIn.HorizonCode_Horizon_È(pos, state, 2);
                }
                else {
                    for (final EnumFacing var8 : EnumFacing.Ý.HorizonCode_Horizon_È) {
                        if (worldIn.Â(pos.HorizonCode_Horizon_È(var8)).Ý() == this.ŠÂµà) {
                            return;
                        }
                    }
                    pos = pos.HorizonCode_Horizon_È(EnumFacing.Ý.HorizonCode_Horizon_È.HorizonCode_Horizon_È(rand));
                    final Block var9 = worldIn.Â(pos.Âµá€()).Ý();
                    if (worldIn.Â(pos).Ý().É == Material.HorizonCode_Horizon_È && (var9 == Blocks.£Â || var9 == Blocks.Âµá€ || var9 == Blocks.Ø­áŒŠá)) {
                        worldIn.Â(pos, this.ŠÂµà.¥à());
                    }
                }
            }
        }
    }
    
    public void à(final World worldIn, final BlockPos p_176482_2_, final IBlockState p_176482_3_) {
        final int var4 = (int)p_176482_3_.HorizonCode_Horizon_È(BlockStem.Õ) + MathHelper.HorizonCode_Horizon_È(worldIn.Å, 2, 5);
        worldIn.HorizonCode_Horizon_È(p_176482_2_, p_176482_3_.HorizonCode_Horizon_È(BlockStem.Õ, Math.min(7, var4)), 2);
    }
    
    @Override
    public int Âµá€(final IBlockState state) {
        if (state.Ý() != this) {
            return super.Âµá€(state);
        }
        final int var2 = (int)state.HorizonCode_Horizon_È(BlockStem.Õ);
        final int var3 = var2 * 32;
        final int var4 = 255 - var2 * 8;
        final int var5 = var2 * 4;
        return var3 << 16 | var4 << 8 | var5;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
        return this.Âµá€(worldIn.Â(pos));
    }
    
    @Override
    public void ŠÄ() {
        final float var1 = 0.125f;
        this.HorizonCode_Horizon_È(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, 0.25f, 0.5f + var1);
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        this.Ê = ((int)access.Â(pos).HorizonCode_Horizon_È(BlockStem.Õ) * 2 + 2) / 16.0f;
        final float var3 = 0.125f;
        this.HorizonCode_Horizon_È(0.5f - var3, 0.0f, 0.5f - var3, 0.5f + var3, (float)this.Ê, 0.5f + var3);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        super.HorizonCode_Horizon_È(worldIn, pos, state, chance, fortune);
        if (!worldIn.ŠÄ) {
            final Item_1028566121 var6 = this.È();
            if (var6 != null) {
                final int var7 = (int)state.HorizonCode_Horizon_È(BlockStem.Õ);
                for (int var8 = 0; var8 < 3; ++var8) {
                    if (worldIn.Å.nextInt(15) <= var7) {
                        Block.HorizonCode_Horizon_È(worldIn, pos, new ItemStack(var6));
                    }
                }
            }
        }
    }
    
    protected Item_1028566121 È() {
        return (this.ŠÂµà == Blocks.Ø­Æ) ? Items.£áƒ : ((this.ŠÂµà == Blocks.ˆÅ) ? Items.Ï­áˆºÓ : null);
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return null;
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        final Item_1028566121 var3 = this.È();
        return (var3 != null) ? var3 : null;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176473_2_, final IBlockState p_176473_3_, final boolean p_176473_4_) {
        return (int)p_176473_3_.HorizonCode_Horizon_È(BlockStem.Õ) != 7;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180670_2_, final BlockPos p_180670_3_, final IBlockState p_180670_4_) {
        return true;
    }
    
    @Override
    public void Â(final World worldIn, final Random p_176474_2_, final BlockPos p_176474_3_, final IBlockState p_176474_4_) {
        this.à(worldIn, p_176474_3_, p_176474_4_);
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockStem.Õ, meta);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return (int)state.HorizonCode_Horizon_È(BlockStem.Õ);
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockStem.Õ, BlockStem.à¢ });
    }
}
