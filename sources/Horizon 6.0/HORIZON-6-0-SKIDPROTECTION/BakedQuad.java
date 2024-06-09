package HORIZON-6-0-SKIDPROTECTION;

public class BakedQuad
{
    protected final int[] HorizonCode_Horizon_È;
    protected final int Â;
    protected final EnumFacing Ý;
    private static final String Ø­áŒŠá = "CL_00002512";
    private TextureAtlasSprite Âµá€;
    
    public BakedQuad(final int[] p_i46232_1_, final int p_i46232_2_, final EnumFacing p_i46232_3_, final TextureAtlasSprite sprite) {
        this.Âµá€ = null;
        this.HorizonCode_Horizon_È = p_i46232_1_;
        this.Â = p_i46232_2_;
        this.Ý = p_i46232_3_;
        this.Âµá€ = sprite;
    }
    
    public TextureAtlasSprite HorizonCode_Horizon_È() {
        return this.Âµá€;
    }
    
    @Override
    public String toString() {
        return "vertex: " + this.HorizonCode_Horizon_È.length / 7 + ", tint: " + this.Â + ", facing: " + this.Ý + ", sprite: " + this.Âµá€;
    }
    
    public BakedQuad(final int[] p_i46232_1_, final int p_i46232_2_, final EnumFacing p_i46232_3_) {
        this.Âµá€ = null;
        this.HorizonCode_Horizon_È = p_i46232_1_;
        this.Â = p_i46232_2_;
        this.Ý = p_i46232_3_;
    }
    
    public int[] Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    public boolean Ý() {
        return this.Â != -1;
    }
    
    public int Ø­áŒŠá() {
        return this.Â;
    }
    
    public EnumFacing Âµá€() {
        return this.Ý;
    }
}
