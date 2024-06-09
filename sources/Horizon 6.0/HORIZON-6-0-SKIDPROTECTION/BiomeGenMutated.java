package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import com.google.common.collect.Lists;

public class BiomeGenMutated extends BiomeGenBase
{
    protected BiomeGenBase ÇªØ­;
    private static final String Ñ¢à = "CL_00000178";
    
    public BiomeGenMutated(final int p_i45381_1_, final BiomeGenBase p_i45381_2_) {
        super(p_i45381_1_);
        this.ÇªØ­ = p_i45381_2_;
        this.HorizonCode_Horizon_È(p_i45381_2_.Ø­á, true);
        this.£Ï = String.valueOf(p_i45381_2_.£Ï) + " M";
        this.Ï­Ï­Ï = p_i45381_2_.Ï­Ï­Ï;
        this.£Â = p_i45381_2_.£Â;
        this.£Ó = p_i45381_2_.£Ó;
        this.ˆÐƒØ­à = p_i45381_2_.ˆÐƒØ­à;
        this.£Õ = p_i45381_2_.£Õ;
        this.Ï­Ô = p_i45381_2_.Ï­Ô;
        this.Œà = p_i45381_2_.Œà;
        this.Ðƒá = p_i45381_2_.Ðƒá;
        this.ˆÓ = p_i45381_2_.ˆÓ;
        this.¥Ä = p_i45381_2_.¥Ä;
        this.ÇªÂµÕ = Lists.newArrayList((Iterable)p_i45381_2_.ÇªÂµÕ);
        this.áˆºÇŽØ = Lists.newArrayList((Iterable)p_i45381_2_.áˆºÇŽØ);
        this.áŒŠáŠ = Lists.newArrayList((Iterable)p_i45381_2_.áŒŠáŠ);
        this.áŒŠÏ = Lists.newArrayList((Iterable)p_i45381_2_.áŒŠÏ);
        this.Ï­Ô = p_i45381_2_.Ï­Ô;
        this.Œà = p_i45381_2_.Œà;
        this.ˆÐƒØ­à = p_i45381_2_.ˆÐƒØ­à + 0.1f;
        this.£Õ = p_i45381_2_.£Õ + 0.2f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
        this.ÇªØ­.ˆÏ.HorizonCode_Horizon_È(worldIn, p_180624_2_, this, p_180624_3_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final Random p_180622_2_, final ChunkPrimer p_180622_3_, final int p_180622_4_, final int p_180622_5_, final double p_180622_6_) {
        this.ÇªØ­.HorizonCode_Horizon_È(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
    }
    
    @Override
    public float à() {
        return this.ÇªØ­.à();
    }
    
    @Override
    public WorldGenAbstractTree HorizonCode_Horizon_È(final Random p_150567_1_) {
        return this.ÇªØ­.HorizonCode_Horizon_È(p_150567_1_);
    }
    
    @Override
    public int Ý(final BlockPos p_180625_1_) {
        return this.ÇªØ­.Ý(p_180625_1_);
    }
    
    @Override
    public int Â(final BlockPos p_180627_1_) {
        return this.ÇªØ­.Â(p_180627_1_);
    }
    
    @Override
    public Class á() {
        return this.ÇªØ­.á();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final BiomeGenBase p_150569_1_) {
        return this.ÇªØ­.HorizonCode_Horizon_È(p_150569_1_);
    }
    
    @Override
    public Ø­áŒŠá ˆÏ­() {
        return this.ÇªØ­.ˆÏ­();
    }
}
