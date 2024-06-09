package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import com.google.common.base.Predicate;
import java.util.List;

public class EmptyChunk extends Chunk
{
    private static final String Ý = "CL_00000372";
    
    public EmptyChunk(final World worldIn, final int x, final int z) {
        super(worldIn, x, z);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int x, final int z) {
        return x == this.HorizonCode_Horizon_È && z == this.Â;
    }
    
    @Override
    public int Â(final int x, final int z) {
        return 0;
    }
    
    public void Ý() {
    }
    
    @Override
    public void Ø­áŒŠá() {
    }
    
    @Override
    public Block Ý(final BlockPos pos) {
        return Blocks.Â;
    }
    
    @Override
    public int Â(final BlockPos pos) {
        return 255;
    }
    
    @Override
    public int Âµá€(final BlockPos pos) {
        return 0;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final EnumSkyBlock p_177413_1_, final BlockPos p_177413_2_) {
        return p_177413_1_.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EnumSkyBlock p_177431_1_, final BlockPos p_177431_2_, final int p_177431_3_) {
    }
    
    @Override
    public int HorizonCode_Horizon_È(final BlockPos p_177443_1_, final int p_177443_2_) {
        return 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity entityIn) {
    }
    
    @Override
    public void Â(final Entity p_76622_1_) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76608_1_, final int p_76608_2_) {
    }
    
    @Override
    public boolean Ó(final BlockPos pos) {
        return false;
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final BlockPos p_177424_1_, final HorizonCode_Horizon_È p_177424_2_) {
        return null;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final TileEntity tileEntityIn) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final BlockPos pos, final TileEntity tileEntityIn) {
    }
    
    @Override
    public void à(final BlockPos pos) {
    }
    
    @Override
    public void Âµá€() {
    }
    
    @Override
    public void Ó() {
    }
    
    @Override
    public void à() {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_177414_1_, final AxisAlignedBB p_177414_2_, final List p_177414_3_, final Predicate p_177414_4_) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Class p_177430_1_, final AxisAlignedBB p_177430_2_, final List p_177430_3_, final Predicate p_177430_4_) {
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final boolean p_76601_1_) {
        return false;
    }
    
    @Override
    public Random HorizonCode_Horizon_È(final long seed) {
        return new Random(this.£à().Æ() + this.HorizonCode_Horizon_È * this.HorizonCode_Horizon_È * 4987142 + this.HorizonCode_Horizon_È * 5947611 + this.Â * this.Â * 4392871L + this.Â * 389711 ^ seed);
    }
    
    @Override
    public boolean Ø() {
        return true;
    }
    
    @Override
    public boolean Ý(final int p_76606_1_, final int p_76606_2_) {
        return true;
    }
}
