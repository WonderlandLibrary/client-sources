package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.Iterator;

public class BlockBed extends BlockDirectional
{
    public static final PropertyEnum Õ;
    public static final PropertyBool à¢;
    private static final String ¥à = "CL_00000198";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("part", HorizonCode_Horizon_È.class);
        à¢ = PropertyBool.HorizonCode_Horizon_È("occupied");
    }
    
    public BlockBed() {
        super(Material.£á);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockBed.Õ, HorizonCode_Horizon_È.Â).HorizonCode_Horizon_È(BlockBed.à¢, false));
        this.È();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, BlockPos pos, IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.ŠÄ) {
            return true;
        }
        if (state.HorizonCode_Horizon_È(BlockBed.Õ) != HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            pos = pos.HorizonCode_Horizon_È((EnumFacing)state.HorizonCode_Horizon_È(BlockBed.ŠÂµà));
            state = worldIn.Â(pos);
            if (state.Ý() != this) {
                return true;
            }
        }
        if (!worldIn.£à.Âµá€() || worldIn.Ý(pos) == BiomeGenBase.Ï­Ðƒà) {
            worldIn.Ø(pos);
            final BlockPos var9 = pos.HorizonCode_Horizon_È(((EnumFacing)state.HorizonCode_Horizon_È(BlockBed.ŠÂµà)).Âµá€());
            if (worldIn.Â(var9).Ý() == this) {
                worldIn.Ø(var9);
            }
            worldIn.HorizonCode_Horizon_È(null, pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + 0.5, pos.Ý() + 0.5, 5.0f, true, true);
            return true;
        }
        if (state.HorizonCode_Horizon_È(BlockBed.à¢)) {
            final EntityPlayer var10 = this.áˆºÑ¢Õ(worldIn, pos);
            if (var10 != null) {
                playerIn.Â(new ChatComponentTranslation("tile.bed.occupied", new Object[0]));
                return true;
            }
            state = state.HorizonCode_Horizon_È(BlockBed.à¢, false);
            worldIn.HorizonCode_Horizon_È(pos, state, 4);
        }
        final EntityPlayer.Â var11 = playerIn.HorizonCode_Horizon_È(pos);
        if (var11 == EntityPlayer.Â.HorizonCode_Horizon_È) {
            state = state.HorizonCode_Horizon_È(BlockBed.à¢, true);
            worldIn.HorizonCode_Horizon_È(pos, state, 4);
            return true;
        }
        if (var11 == EntityPlayer.Â.Ý) {
            playerIn.Â(new ChatComponentTranslation("tile.bed.noSleep", new Object[0]));
        }
        else if (var11 == EntityPlayer.Â.Ó) {
            playerIn.Â(new ChatComponentTranslation("tile.bed.notSafe", new Object[0]));
        }
        return true;
    }
    
    private EntityPlayer áˆºÑ¢Õ(final World worldIn, final BlockPos p_176470_2_) {
        for (final EntityPlayer var4 : worldIn.Ó) {
            if (var4.Ï­Ó() && var4.ÇªáˆºÕ.equals(p_176470_2_)) {
                return var4;
            }
        }
        return null;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        this.È();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        final EnumFacing var5 = (EnumFacing)state.HorizonCode_Horizon_È(BlockBed.ŠÂµà);
        if (state.HorizonCode_Horizon_È(BlockBed.Õ) == HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            if (worldIn.Â(pos.HorizonCode_Horizon_È(var5.Âµá€())).Ý() != this) {
                worldIn.Ø(pos);
            }
        }
        else if (worldIn.Â(pos.HorizonCode_Horizon_È(var5)).Ý() != this) {
            worldIn.Ø(pos);
            if (!worldIn.ŠÄ) {
                this.HorizonCode_Horizon_È(worldIn, pos, state, 0);
            }
        }
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return (state.HorizonCode_Horizon_È(BlockBed.Õ) == HorizonCode_Horizon_È.HorizonCode_Horizon_È) ? null : Items.áŒŠÕ;
    }
    
    private void È() {
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.5625f, 1.0f);
    }
    
    public static BlockPos Â(final World worldIn, final BlockPos p_176468_1_, int p_176468_2_) {
        final EnumFacing var3 = (EnumFacing)worldIn.Â(p_176468_1_).HorizonCode_Horizon_È(BlockBed.ŠÂµà);
        final int var4 = p_176468_1_.HorizonCode_Horizon_È();
        final int var5 = p_176468_1_.Â();
        final int var6 = p_176468_1_.Ý();
        for (int var7 = 0; var7 <= 1; ++var7) {
            final int var8 = var4 - var3.Ø() * var7 - 1;
            final int var9 = var6 - var3.áˆºÑ¢Õ() * var7 - 1;
            final int var10 = var8 + 2;
            final int var11 = var9 + 2;
            for (int var12 = var8; var12 <= var10; ++var12) {
                for (int var13 = var9; var13 <= var11; ++var13) {
                    final BlockPos var14 = new BlockPos(var12, var5, var13);
                    if (áŒŠÆ(worldIn, var14)) {
                        if (p_176468_2_ <= 0) {
                            return var14;
                        }
                        --p_176468_2_;
                    }
                }
            }
        }
        return null;
    }
    
    protected static boolean áŒŠÆ(final World worldIn, final BlockPos p_176469_1_) {
        return World.HorizonCode_Horizon_È(worldIn, p_176469_1_.Âµá€()) && !worldIn.Â(p_176469_1_).Ý().Ó().Â() && !worldIn.Â(p_176469_1_.Ø­áŒŠá()).Ý().Ó().Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        if (state.HorizonCode_Horizon_È(BlockBed.Õ) == HorizonCode_Horizon_È.Â) {
            super.HorizonCode_Horizon_È(worldIn, pos, state, chance, 0);
        }
    }
    
    @Override
    public int ˆá() {
        return 1;
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Items.áŒŠÕ;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn) {
        if (playerIn.áˆºáˆºáŠ.Ø­áŒŠá && state.HorizonCode_Horizon_È(BlockBed.Õ) == HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            final BlockPos var5 = pos.HorizonCode_Horizon_È(((EnumFacing)state.HorizonCode_Horizon_È(BlockBed.ŠÂµà)).Âµá€());
            if (worldIn.Â(var5).Ý() == this) {
                worldIn.Ø(var5);
            }
        }
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        final EnumFacing var2 = EnumFacing.Â(meta);
        return ((meta & 0x8) > 0) ? this.¥à().HorizonCode_Horizon_È(BlockBed.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È).HorizonCode_Horizon_È(BlockBed.ŠÂµà, var2).HorizonCode_Horizon_È(BlockBed.à¢, (meta & 0x4) > 0) : this.¥à().HorizonCode_Horizon_È(BlockBed.Õ, HorizonCode_Horizon_È.Â).HorizonCode_Horizon_È(BlockBed.ŠÂµà, var2);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        if (state.HorizonCode_Horizon_È(BlockBed.Õ) == HorizonCode_Horizon_È.Â) {
            final IBlockState var4 = worldIn.Â(pos.HorizonCode_Horizon_È((EnumFacing)state.HorizonCode_Horizon_È(BlockBed.ŠÂµà)));
            if (var4.Ý() == this) {
                state = state.HorizonCode_Horizon_È(BlockBed.à¢, var4.HorizonCode_Horizon_È(BlockBed.à¢));
            }
        }
        return state;
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((EnumFacing)state.HorizonCode_Horizon_È(BlockBed.ŠÂµà)).Ý();
        if (state.HorizonCode_Horizon_È(BlockBed.Õ) == HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            var3 |= 0x8;
            if (state.HorizonCode_Horizon_È(BlockBed.à¢)) {
                var3 |= 0x4;
            }
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockBed.ŠÂµà, BlockBed.Õ, BlockBed.à¢ });
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("HEAD", 0, "HEAD", 0, "head"), 
        Â("FOOT", 1, "FOOT", 1, "foot");
        
        private final String Ý;
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002134";
        
        static {
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            Ø­áŒŠá = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45735_1_, final int p_i45735_2_, final String p_i45735_3_) {
            this.Ý = p_i45735_3_;
        }
        
        @Override
        public String toString() {
            return this.Ý;
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.Ý;
        }
    }
}
