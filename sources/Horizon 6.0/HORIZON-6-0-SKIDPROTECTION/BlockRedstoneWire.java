package HORIZON-6-0-SKIDPROTECTION;

import java.util.EnumSet;
import java.util.Random;
import java.util.Iterator;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Set;

public class BlockRedstoneWire extends Block
{
    public static final PropertyEnum Õ;
    public static final PropertyEnum à¢;
    public static final PropertyEnum ŠÂµà;
    public static final PropertyEnum ¥à;
    public static final PropertyInteger Âµà;
    private boolean Ç;
    private final Set È;
    private static final String áŠ = "CL_00000295";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("north", HorizonCode_Horizon_È.class);
        à¢ = PropertyEnum.HorizonCode_Horizon_È("east", HorizonCode_Horizon_È.class);
        ŠÂµà = PropertyEnum.HorizonCode_Horizon_È("south", HorizonCode_Horizon_È.class);
        ¥à = PropertyEnum.HorizonCode_Horizon_È("west", HorizonCode_Horizon_È.class);
        Âµà = PropertyInteger.HorizonCode_Horizon_È("power", 0, 15);
    }
    
    public BlockRedstoneWire() {
        super(Material.µà);
        this.Ç = true;
        this.È = Sets.newHashSet();
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockRedstoneWire.Õ, HorizonCode_Horizon_È.Ý).HorizonCode_Horizon_È(BlockRedstoneWire.à¢, HorizonCode_Horizon_È.Ý).HorizonCode_Horizon_È(BlockRedstoneWire.ŠÂµà, HorizonCode_Horizon_È.Ý).HorizonCode_Horizon_È(BlockRedstoneWire.¥à, HorizonCode_Horizon_È.Ý).HorizonCode_Horizon_È(BlockRedstoneWire.Âµà, 0));
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        state = state.HorizonCode_Horizon_È(BlockRedstoneWire.¥à, this.Ý(worldIn, pos, EnumFacing.Âµá€));
        state = state.HorizonCode_Horizon_È(BlockRedstoneWire.à¢, this.Ý(worldIn, pos, EnumFacing.Ó));
        state = state.HorizonCode_Horizon_È(BlockRedstoneWire.Õ, this.Ý(worldIn, pos, EnumFacing.Ý));
        state = state.HorizonCode_Horizon_È(BlockRedstoneWire.ŠÂµà, this.Ý(worldIn, pos, EnumFacing.Ø­áŒŠá));
        return state;
    }
    
    private HorizonCode_Horizon_È Ý(final IBlockAccess p_176341_1_, final BlockPos p_176341_2_, final EnumFacing p_176341_3_) {
        final BlockPos var4 = p_176341_2_.HorizonCode_Horizon_È(p_176341_3_);
        final Block var5 = p_176341_1_.Â(p_176341_2_.HorizonCode_Horizon_È(p_176341_3_)).Ý();
        if (!HorizonCode_Horizon_È(p_176341_1_.Â(var4), p_176341_3_) && (var5.à() || !áŒŠÆ(p_176341_1_.Â(var4.Âµá€())))) {
            final Block var6 = p_176341_1_.Â(p_176341_2_.Ø­áŒŠá()).Ý();
            return (!var6.à() && var5.à() && áŒŠÆ(p_176341_1_.Â(var4.Ø­áŒŠá()))) ? HorizonCode_Horizon_È.HorizonCode_Horizon_È : HorizonCode_Horizon_È.Ý;
        }
        return HorizonCode_Horizon_È.Â;
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        return null;
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
        final IBlockState var4 = worldIn.Â(pos);
        return (var4.Ý() != this) ? super.HorizonCode_Horizon_È(worldIn, pos, renderPass) : this.Âµá€((int)var4.HorizonCode_Horizon_È(BlockRedstoneWire.Âµà));
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return World.HorizonCode_Horizon_È(worldIn, pos.Âµá€()) || worldIn.Â(pos.Âµá€()).Ý() == Blocks.£Ø­à;
    }
    
    private IBlockState Âµá€(final World worldIn, final BlockPos p_176338_2_, IBlockState p_176338_3_) {
        p_176338_3_ = this.HorizonCode_Horizon_È(worldIn, p_176338_2_, p_176338_2_, p_176338_3_);
        final ArrayList var4 = Lists.newArrayList((Iterable)this.È);
        this.È.clear();
        for (final BlockPos var6 : var4) {
            worldIn.Â(var6, this);
        }
        return p_176338_3_;
    }
    
    private IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176345_2_, final BlockPos p_176345_3_, IBlockState p_176345_4_) {
        final IBlockState var5 = p_176345_4_;
        final int var6 = (int)p_176345_4_.HorizonCode_Horizon_È(BlockRedstoneWire.Âµà);
        final byte var7 = 0;
        int var8 = this.Â(worldIn, p_176345_3_, var7);
        this.Ç = false;
        final int var9 = worldIn.ŠÄ(p_176345_2_);
        this.Ç = true;
        if (var9 > 0 && var9 > var8 - 1) {
            var8 = var9;
        }
        int var10 = 0;
        for (final EnumFacing var12 : EnumFacing.Ý.HorizonCode_Horizon_È) {
            final BlockPos var13 = p_176345_2_.HorizonCode_Horizon_È(var12);
            final boolean var14 = var13.HorizonCode_Horizon_È() != p_176345_3_.HorizonCode_Horizon_È() || var13.Ý() != p_176345_3_.Ý();
            if (var14) {
                var10 = this.Â(worldIn, var13, var10);
            }
            if (worldIn.Â(var13).Ý().Ø() && !worldIn.Â(p_176345_2_.Ø­áŒŠá()).Ý().Ø()) {
                if (!var14 || p_176345_2_.Â() < p_176345_3_.Â()) {
                    continue;
                }
                var10 = this.Â(worldIn, var13.Ø­áŒŠá(), var10);
            }
            else {
                if (worldIn.Â(var13).Ý().Ø() || !var14 || p_176345_2_.Â() > p_176345_3_.Â()) {
                    continue;
                }
                var10 = this.Â(worldIn, var13.Âµá€(), var10);
            }
        }
        if (var10 > var8) {
            var8 = var10 - 1;
        }
        else if (var8 > 0) {
            --var8;
        }
        else {
            var8 = 0;
        }
        if (var9 > var8 - 1) {
            var8 = var9;
        }
        if (var6 != var8) {
            p_176345_4_ = p_176345_4_.HorizonCode_Horizon_È(BlockRedstoneWire.Âµà, var8);
            if (worldIn.Â(p_176345_2_) == var5) {
                worldIn.HorizonCode_Horizon_È(p_176345_2_, p_176345_4_, 2);
            }
            this.È.add(p_176345_2_);
            for (final EnumFacing var18 : EnumFacing.values()) {
                this.È.add(p_176345_2_.HorizonCode_Horizon_È(var18));
            }
        }
        return p_176345_4_;
    }
    
    private void áŒŠÆ(final World worldIn, final BlockPos p_176344_2_) {
        if (worldIn.Â(p_176344_2_).Ý() == this) {
            worldIn.Â(p_176344_2_, this);
            for (final EnumFacing var6 : EnumFacing.values()) {
                worldIn.Â(p_176344_2_.HorizonCode_Horizon_È(var6), this);
            }
        }
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (!worldIn.ŠÄ) {
            this.Âµá€(worldIn, pos, state);
            for (final EnumFacing var5 : EnumFacing.Ý.Â) {
                worldIn.Â(pos.HorizonCode_Horizon_È(var5), this);
            }
            for (final EnumFacing var5 : EnumFacing.Ý.HorizonCode_Horizon_È) {
                this.áŒŠÆ(worldIn, pos.HorizonCode_Horizon_È(var5));
            }
            for (final EnumFacing var5 : EnumFacing.Ý.HorizonCode_Horizon_È) {
                final BlockPos var6 = pos.HorizonCode_Horizon_È(var5);
                if (worldIn.Â(var6).Ý().Ø()) {
                    this.áŒŠÆ(worldIn, var6.Ø­áŒŠá());
                }
                else {
                    this.áŒŠÆ(worldIn, var6.Âµá€());
                }
            }
        }
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        super.Ø­áŒŠá(worldIn, pos, state);
        if (!worldIn.ŠÄ) {
            for (final EnumFacing var7 : EnumFacing.values()) {
                worldIn.Â(pos.HorizonCode_Horizon_È(var7), this);
            }
            this.Âµá€(worldIn, pos, state);
            for (final EnumFacing var9 : EnumFacing.Ý.HorizonCode_Horizon_È) {
                this.áŒŠÆ(worldIn, pos.HorizonCode_Horizon_È(var9));
            }
            for (final EnumFacing var9 : EnumFacing.Ý.HorizonCode_Horizon_È) {
                final BlockPos var10 = pos.HorizonCode_Horizon_È(var9);
                if (worldIn.Â(var10).Ý().Ø()) {
                    this.áŒŠÆ(worldIn, var10.Ø­áŒŠá());
                }
                else {
                    this.áŒŠÆ(worldIn, var10.Âµá€());
                }
            }
        }
    }
    
    private int Â(final World worldIn, final BlockPos p_176342_2_, final int p_176342_3_) {
        if (worldIn.Â(p_176342_2_).Ý() != this) {
            return p_176342_3_;
        }
        final int var4 = (int)worldIn.Â(p_176342_2_).HorizonCode_Horizon_È(BlockRedstoneWire.Âµà);
        return (var4 > p_176342_3_) ? var4 : p_176342_3_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!worldIn.ŠÄ) {
            if (this.Ø­áŒŠá(worldIn, pos)) {
                this.Âµá€(worldIn, pos, state);
            }
            else {
                this.HorizonCode_Horizon_È(worldIn, pos, state, 0);
                worldIn.Ø(pos);
            }
        }
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Items.ÇŽá;
    }
    
    @Override
    public int Â(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return this.Ç ? this.HorizonCode_Horizon_È(worldIn, pos, state, side) : 0;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        if (!this.Ç) {
            return 0;
        }
        final int var5 = (int)state.HorizonCode_Horizon_È(BlockRedstoneWire.Âµà);
        if (var5 == 0) {
            return 0;
        }
        if (side == EnumFacing.Â) {
            return var5;
        }
        final EnumSet var6 = EnumSet.noneOf(EnumFacing.class);
        for (final EnumFacing var8 : EnumFacing.Ý.HorizonCode_Horizon_È) {
            if (this.Ø­áŒŠá(worldIn, pos, var8)) {
                var6.add(var8);
            }
        }
        if (side.á().Ø­áŒŠá() && var6.isEmpty()) {
            return var5;
        }
        if (var6.contains(side) && !var6.contains(side.à()) && !var6.contains(side.Ó())) {
            return var5;
        }
        return 0;
    }
    
    private boolean Ø­áŒŠá(final IBlockAccess p_176339_1_, final BlockPos p_176339_2_, final EnumFacing p_176339_3_) {
        final BlockPos var4 = p_176339_2_.HorizonCode_Horizon_È(p_176339_3_);
        final IBlockState var5 = p_176339_1_.Â(var4);
        final Block var6 = var5.Ý();
        final boolean var7 = var6.Ø();
        final boolean var8 = p_176339_1_.Â(p_176339_2_.Ø­áŒŠá()).Ý().Ø();
        return (!var8 && var7 && Âµá€(p_176339_1_, var4.Ø­áŒŠá())) || HorizonCode_Horizon_È(var5, p_176339_3_) || (var6 == Blocks.ˆØ && var5.HorizonCode_Horizon_È(BlockRedstoneDiode.ŠÂµà) == p_176339_3_) || (!var7 && Âµá€(p_176339_1_, var4.Âµá€()));
    }
    
    protected static boolean Âµá€(final IBlockAccess p_176340_0_, final BlockPos p_176340_1_) {
        return áŒŠÆ(p_176340_0_.Â(p_176340_1_));
    }
    
    protected static boolean áŒŠÆ(final IBlockState p_176346_0_) {
        return HorizonCode_Horizon_È(p_176346_0_, null);
    }
    
    protected static boolean HorizonCode_Horizon_È(final IBlockState p_176343_0_, final EnumFacing p_176343_1_) {
        final Block var2 = p_176343_0_.Ý();
        if (var2 == Blocks.Œ) {
            return true;
        }
        if (Blocks.áŒŠá.Âµá€(var2)) {
            final EnumFacing var3 = (EnumFacing)p_176343_0_.HorizonCode_Horizon_È(BlockRedstoneRepeater.ŠÂµà);
            return var3 == p_176343_1_ || var3.Âµá€() == p_176343_1_;
        }
        return var2.áŒŠà() && p_176343_1_ != null;
    }
    
    @Override
    public boolean áŒŠà() {
        return this.Ç;
    }
    
    private int Âµá€(final int p_176337_1_) {
        final float var2 = p_176337_1_ / 15.0f;
        float var3 = var2 * 0.6f + 0.4f;
        if (p_176337_1_ == 0) {
            var3 = 0.3f;
        }
        float var4 = var2 * var2 * 0.7f - 0.5f;
        float var5 = var2 * var2 * 0.6f - 0.7f;
        if (var4 < 0.0f) {
            var4 = 0.0f;
        }
        if (var5 < 0.0f) {
            var5 = 0.0f;
        }
        final int var6 = MathHelper.HorizonCode_Horizon_È((int)(var3 * 255.0f), 0, 255);
        final int var7 = MathHelper.HorizonCode_Horizon_È((int)(var4 * 255.0f), 0, 255);
        final int var8 = MathHelper.HorizonCode_Horizon_È((int)(var5 * 255.0f), 0, 255);
        return 0xFF000000 | var6 << 16 | var7 << 8 | var8;
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        final int var5 = (int)state.HorizonCode_Horizon_È(BlockRedstoneWire.Âµà);
        if (var5 != 0) {
            final double var6 = pos.HorizonCode_Horizon_È() + 0.5 + (rand.nextFloat() - 0.5) * 0.2;
            final double var7 = pos.Â() + 0.0625f;
            final double var8 = pos.Ý() + 0.5 + (rand.nextFloat() - 0.5) * 0.2;
            final float var9 = var5 / 15.0f;
            final float var10 = var9 * 0.6f + 0.4f;
            final float var11 = Math.max(0.0f, var9 * var9 * 0.7f - 0.5f);
            final float var12 = Math.max(0.0f, var9 * var9 * 0.6f - 0.7f);
            worldIn.HorizonCode_Horizon_È(EnumParticleTypes.ÇŽÉ, var6, var7, var8, var10, var11, var12, new int[0]);
        }
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Items.ÇŽá;
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockRedstoneWire.Âµà, meta);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return (int)state.HorizonCode_Horizon_È(BlockRedstoneWire.Âµà);
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockRedstoneWire.Õ, BlockRedstoneWire.à¢, BlockRedstoneWire.ŠÂµà, BlockRedstoneWire.¥à, BlockRedstoneWire.Âµà });
    }
    
    enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("UP", 0, "UP", 0, "up"), 
        Â("SIDE", 1, "SIDE", 1, "side"), 
        Ý("NONE", 2, "NONE", 2, "none");
        
        private final String Ø­áŒŠá;
        private static final HorizonCode_Horizon_È[] Âµá€;
        private static final String Ó = "CL_00002070";
        
        static {
            à = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            Âµá€ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45689_1_, final int p_i45689_2_, final String p_i45689_3_) {
            this.Ø­áŒŠá = p_i45689_3_;
        }
        
        @Override
        public String toString() {
            return this.HorizonCode_Horizon_È();
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.Ø­áŒŠá;
        }
    }
}
