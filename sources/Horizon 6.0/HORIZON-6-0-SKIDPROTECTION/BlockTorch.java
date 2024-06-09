package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.Iterator;
import com.google.common.base.Predicate;

public class BlockTorch extends Block
{
    public static final PropertyDirection Õ;
    private static final String à¢ = "CL_00000325";
    
    static {
        Õ = PropertyDirection.HorizonCode_Horizon_È("facing", (Predicate)new Predicate() {
            private static final String HorizonCode_Horizon_È = "CL_00002054";
            
            public boolean HorizonCode_Horizon_È(final EnumFacing p_176601_1_) {
                return p_176601_1_ != EnumFacing.HorizonCode_Horizon_È;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((EnumFacing)p_apply_1_);
            }
        });
    }
    
    protected BlockTorch() {
        super(Material.µà);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockTorch.Õ, EnumFacing.Â));
        this.HorizonCode_Horizon_È(true);
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
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
    
    private boolean áŒŠÆ(final World worldIn, final BlockPos p_176594_2_) {
        if (World.HorizonCode_Horizon_È(worldIn, p_176594_2_)) {
            return true;
        }
        final Block var3 = worldIn.Â(p_176594_2_).Ý();
        return var3 instanceof BlockFence || var3 == Blocks.Ï­Ðƒà || var3 == Blocks.Ï­Ó || var3 == Blocks.ÐƒáŒŠÂµÐƒÕ;
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        for (final EnumFacing var4 : BlockTorch.Õ.Â()) {
            if (this.Â(worldIn, pos, var4)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean Â(final World worldIn, final BlockPos p_176595_2_, final EnumFacing p_176595_3_) {
        final BlockPos var4 = p_176595_2_.HorizonCode_Horizon_È(p_176595_3_.Âµá€());
        final boolean var5 = p_176595_3_.á().Ø­áŒŠá();
        return (var5 && worldIn.Ø­áŒŠá(var4, true)) || (p_176595_3_.equals(EnumFacing.Â) && this.áŒŠÆ(worldIn, var4));
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        if (this.Â(worldIn, pos, facing)) {
            return this.¥à().HorizonCode_Horizon_È(BlockTorch.Õ, facing);
        }
        for (final EnumFacing var10 : EnumFacing.Ý.HorizonCode_Horizon_È) {
            if (worldIn.Ø­áŒŠá(pos.HorizonCode_Horizon_È(var10.Âµá€()), true)) {
                return this.¥à().HorizonCode_Horizon_È(BlockTorch.Õ, var10);
            }
        }
        return this.¥à();
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.Ó(worldIn, pos, state);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        this.Âµá€(worldIn, pos, state);
    }
    
    protected boolean Âµá€(final World worldIn, final BlockPos p_176592_2_, final IBlockState p_176592_3_) {
        if (!this.Ó(worldIn, p_176592_2_, p_176592_3_)) {
            return true;
        }
        final EnumFacing var4 = (EnumFacing)p_176592_3_.HorizonCode_Horizon_È(BlockTorch.Õ);
        final EnumFacing.HorizonCode_Horizon_È var5 = var4.á();
        final EnumFacing var6 = var4.Âµá€();
        boolean var7 = false;
        if (var5.Ø­áŒŠá() && !worldIn.Ø­áŒŠá(p_176592_2_.HorizonCode_Horizon_È(var6), true)) {
            var7 = true;
        }
        else if (var5.Ý() && !this.áŒŠÆ(worldIn, p_176592_2_.HorizonCode_Horizon_È(var6))) {
            var7 = true;
        }
        if (var7) {
            this.HorizonCode_Horizon_È(worldIn, p_176592_2_, p_176592_3_, 0);
            worldIn.Ø(p_176592_2_);
            return true;
        }
        return false;
    }
    
    protected boolean Ó(final World worldIn, final BlockPos p_176593_2_, final IBlockState p_176593_3_) {
        if (p_176593_3_.Ý() == this && this.Â(worldIn, p_176593_2_, (EnumFacing)p_176593_3_.HorizonCode_Horizon_È(BlockTorch.Õ))) {
            return true;
        }
        if (worldIn.Â(p_176593_2_).Ý() == this) {
            this.HorizonCode_Horizon_È(worldIn, p_176593_2_, p_176593_3_, 0);
            worldIn.Ø(p_176593_2_);
        }
        return false;
    }
    
    @Override
    public MovingObjectPosition HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final Vec3 start, final Vec3 end) {
        final EnumFacing var5 = (EnumFacing)worldIn.Â(pos).HorizonCode_Horizon_È(BlockTorch.Õ);
        float var6 = 0.15f;
        if (var5 == EnumFacing.Ó) {
            this.HorizonCode_Horizon_È(0.0f, 0.2f, 0.5f - var6, var6 * 2.0f, 0.8f, 0.5f + var6);
        }
        else if (var5 == EnumFacing.Âµá€) {
            this.HorizonCode_Horizon_È(1.0f - var6 * 2.0f, 0.2f, 0.5f - var6, 1.0f, 0.8f, 0.5f + var6);
        }
        else if (var5 == EnumFacing.Ø­áŒŠá) {
            this.HorizonCode_Horizon_È(0.5f - var6, 0.2f, 0.0f, 0.5f + var6, 0.8f, var6 * 2.0f);
        }
        else if (var5 == EnumFacing.Ý) {
            this.HorizonCode_Horizon_È(0.5f - var6, 0.2f, 1.0f - var6 * 2.0f, 0.5f + var6, 0.8f, 1.0f);
        }
        else {
            var6 = 0.1f;
            this.HorizonCode_Horizon_È(0.5f - var6, 0.0f, 0.5f - var6, 0.5f + var6, 0.6f, 0.5f + var6);
        }
        return super.HorizonCode_Horizon_È(worldIn, pos, start, end);
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        final EnumFacing var5 = (EnumFacing)state.HorizonCode_Horizon_È(BlockTorch.Õ);
        final double var6 = pos.HorizonCode_Horizon_È() + 0.5;
        final double var7 = pos.Â() + 0.7;
        final double var8 = pos.Ý() + 0.5;
        final double var9 = 0.22;
        final double var10 = 0.27;
        if (var5.á().Ø­áŒŠá()) {
            final EnumFacing var11 = var5.Âµá€();
            worldIn.HorizonCode_Horizon_È(EnumParticleTypes.á, var6 + var10 * var11.Ø(), var7 + var9, var8 + var10 * var11.áˆºÑ¢Õ(), 0.0, 0.0, 0.0, new int[0]);
            worldIn.HorizonCode_Horizon_È(EnumParticleTypes.Ñ¢á, var6 + var10 * var11.Ø(), var7 + var9, var8 + var10 * var11.áˆºÑ¢Õ(), 0.0, 0.0, 0.0, new int[0]);
        }
        else {
            worldIn.HorizonCode_Horizon_È(EnumParticleTypes.á, var6, var7, var8, 0.0, 0.0, 0.0, new int[0]);
            worldIn.HorizonCode_Horizon_È(EnumParticleTypes.Ñ¢á, var6, var7, var8, 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        IBlockState var2 = this.¥à();
        switch (meta) {
            case 1: {
                var2 = var2.HorizonCode_Horizon_È(BlockTorch.Õ, EnumFacing.Ó);
                break;
            }
            case 2: {
                var2 = var2.HorizonCode_Horizon_È(BlockTorch.Õ, EnumFacing.Âµá€);
                break;
            }
            case 3: {
                var2 = var2.HorizonCode_Horizon_È(BlockTorch.Õ, EnumFacing.Ø­áŒŠá);
                break;
            }
            case 4: {
                var2 = var2.HorizonCode_Horizon_È(BlockTorch.Õ, EnumFacing.Ý);
                break;
            }
            default: {
                var2 = var2.HorizonCode_Horizon_È(BlockTorch.Õ, EnumFacing.Â);
                break;
            }
        }
        return var2;
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = 0;
        switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[((EnumFacing)state.HorizonCode_Horizon_È(BlockTorch.Õ)).ordinal()]) {
            case 1: {
                var3 = (var2 | 0x1);
                break;
            }
            case 2: {
                var3 = (var2 | 0x2);
                break;
            }
            case 3: {
                var3 = (var2 | 0x3);
                break;
            }
            case 4: {
                var3 = (var2 | 0x4);
                break;
            }
            default: {
                var3 = (var2 | 0x5);
                break;
            }
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockTorch.Õ });
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002053";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                BlockTorch.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockTorch.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockTorch.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockTorch.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                BlockTorch.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                BlockTorch.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Â.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
    }
}
