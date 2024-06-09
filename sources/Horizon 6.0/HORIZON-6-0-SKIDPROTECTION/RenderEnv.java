package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.BitSet;

public class RenderEnv
{
    private IBlockAccess HorizonCode_Horizon_È;
    private IBlockState Â;
    private BlockPos Ý;
    private GameSettings Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private int à;
    private float[] Ø;
    private BitSet áŒŠÆ;
    private BlockModelRenderer.HorizonCode_Horizon_È áˆºÑ¢Õ;
    private BlockPosM ÂµÈ;
    private boolean[] á;
    private static ThreadLocal ˆÏ­;
    
    static {
        RenderEnv.ˆÏ­ = new ThreadLocal();
    }
    
    private RenderEnv(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos) {
        this.Âµá€ = -1;
        this.Ó = -1;
        this.à = -1;
        this.Ø = new float[EnumFacing.à.length * 2];
        this.áŒŠÆ = new BitSet(3);
        this.áˆºÑ¢Õ = new BlockModelRenderer.HorizonCode_Horizon_È();
        this.ÂµÈ = null;
        this.á = null;
        this.HorizonCode_Horizon_È = blockAccess;
        this.Â = blockState;
        this.Ý = blockPos;
        this.Ø­áŒŠá = Config.ÇªØ­();
    }
    
    public static RenderEnv HorizonCode_Horizon_È(final IBlockAccess blockAccessIn, final IBlockState blockStateIn, final BlockPos blockPosIn) {
        RenderEnv re = RenderEnv.ˆÏ­.get();
        if (re == null) {
            re = new RenderEnv(blockAccessIn, blockStateIn, blockPosIn);
            RenderEnv.ˆÏ­.set(re);
            return re;
        }
        re.Â(blockAccessIn, blockStateIn, blockPosIn);
        return re;
    }
    
    private void Â(final IBlockAccess blockAccessIn, final IBlockState blockStateIn, final BlockPos blockPosIn) {
        this.HorizonCode_Horizon_È = blockAccessIn;
        this.Â = blockStateIn;
        this.Ý = blockPosIn;
        this.Âµá€ = -1;
        this.Ó = -1;
        this.à = -1;
        this.áŒŠÆ.clear();
    }
    
    public int HorizonCode_Horizon_È() {
        if (this.Âµá€ < 0) {
            this.Âµá€ = Block.HorizonCode_Horizon_È(this.Â.Ý());
        }
        return this.Âµá€;
    }
    
    public int Â() {
        if (this.Ó < 0) {
            this.Ó = this.Â.Ý().Ý(this.Â);
        }
        return this.Ó;
    }
    
    public float[] Ý() {
        return this.Ø;
    }
    
    public BitSet Ø­áŒŠá() {
        return this.áŒŠÆ;
    }
    
    public BlockModelRenderer.HorizonCode_Horizon_È Âµá€() {
        return this.áˆºÑ¢Õ;
    }
    
    public boolean HorizonCode_Horizon_È(final List listQuads) {
        if (this.à < 0 && listQuads.size() > 0) {
            if (listQuads.get(0) instanceof BreakingFour) {
                this.à = 1;
            }
            else {
                this.à = 0;
            }
        }
        return this.à == 1;
    }
    
    public boolean HorizonCode_Horizon_È(final BakedQuad quad) {
        if (this.à < 0) {
            if (quad instanceof BreakingFour) {
                this.à = 1;
            }
            else {
                this.à = 0;
            }
        }
        return this.à == 1;
    }
    
    public boolean Ó() {
        return this.à == 1;
    }
    
    public IBlockState à() {
        return this.Â;
    }
    
    public BlockPosM Ø() {
        if (this.ÂµÈ == null) {
            this.ÂµÈ = new BlockPosM(0, 0, 0);
        }
        return this.ÂµÈ;
    }
    
    public boolean[] áŒŠÆ() {
        if (this.á == null) {
            this.á = new boolean[4];
        }
        return this.á;
    }
}
