package HORIZON-6-0-SKIDPROTECTION;

public class Material
{
    public static final Material HorizonCode_Horizon_È;
    public static final Material Â;
    public static final Material Ý;
    public static final Material Ø­áŒŠá;
    public static final Material Âµá€;
    public static final Material Ó;
    public static final Material à;
    public static final Material Ø;
    public static final Material áŒŠÆ;
    public static final Material áˆºÑ¢Õ;
    public static final Material ÂµÈ;
    public static final Material á;
    public static final Material ˆÏ­;
    public static final Material £á;
    public static final Material Å;
    public static final Material £à;
    public static final Material µà;
    public static final Material ˆà;
    public static final Material ¥Æ;
    public static final Material Ø­à;
    public static final Material µÕ;
    public static final Material Æ;
    public static final Material Šáƒ;
    public static final Material Ï­Ðƒà;
    public static final Material áŒŠà;
    public static final Material ŠÄ;
    public static final Material Ñ¢á;
    public static final Material ŒÏ;
    public static final Material Çªà¢;
    public static final Material Ê;
    public static final Material ÇŽÉ;
    public static final Material ˆá;
    public static final Material ÇŽÕ;
    public static final Material É;
    public static final Material áƒ;
    private boolean á€;
    private boolean Õ;
    private boolean à¢;
    private final MapColor ŠÂµà;
    private boolean ¥à;
    private int Âµà;
    private boolean Ç;
    private static final String È = "CL_00000542";
    
    static {
        HorizonCode_Horizon_È = new MaterialTransparent(MapColor.Â);
        Â = new Material(MapColor.Ý);
        Ý = new Material(MapColor.á);
        Ø­áŒŠá = new Material(MapColor.Å).Ó();
        Âµá€ = new Material(MapColor.ˆÏ­).Âµá€();
        Ó = new Material(MapColor.Ø).Âµá€();
        à = new Material(MapColor.Ø).Âµá€().£á();
        Ø = new MaterialLiquid(MapColor.£á).ˆÏ­();
        áŒŠÆ = new MaterialLiquid(MapColor.Ó).ˆÏ­();
        áˆºÑ¢Õ = new Material(MapColor.áŒŠÆ).Ó().µà().ˆÏ­();
        ÂµÈ = new MaterialLogic(MapColor.áŒŠÆ).ˆÏ­();
        á = new MaterialLogic(MapColor.áŒŠÆ).Ó().ˆÏ­().Ø();
        ˆÏ­ = new Material(MapColor.Âµá€);
        £á = new Material(MapColor.Âµá€).Ó();
        Å = new MaterialTransparent(MapColor.Â).ˆÏ­();
        £à = new Material(MapColor.Ø­áŒŠá);
        µà = new MaterialLogic(MapColor.Â).ˆÏ­();
        ˆà = new MaterialLogic(MapColor.Âµá€).Ó();
        ¥Æ = new Material(MapColor.Â).µà().Å();
        Ø­à = new Material(MapColor.Â).Å();
        µÕ = new Material(MapColor.Ó).Ó().µà();
        Æ = new Material(MapColor.áŒŠÆ).ˆÏ­();
        Šáƒ = new Material(MapColor.à).µà().Å();
        Ï­Ðƒà = new Material(MapColor.à).Å();
        áŒŠà = new MaterialLogic(MapColor.áˆºÑ¢Õ).Ø().µà().Âµá€().ˆÏ­();
        ŠÄ = new Material(MapColor.áˆºÑ¢Õ).Âµá€();
        Ñ¢á = new Material(MapColor.áŒŠÆ).µà().ˆÏ­();
        ŒÏ = new Material(MapColor.ÂµÈ);
        Çªà¢ = new Material(MapColor.áŒŠÆ).ˆÏ­();
        Ê = new Material(MapColor.áŒŠÆ).ˆÏ­();
        ÇŽÉ = new MaterialPortal(MapColor.Â).£á();
        ˆá = new Material(MapColor.Â).ˆÏ­();
        ÇŽÕ = new Material() {
            private static final String á€ = "CL_00000543";
            
            @Override
            public boolean Ø­áŒŠá() {
                return false;
            }
        }.Âµá€().ˆÏ­();
        É = new Material(MapColor.ˆÏ­).£á();
        áƒ = new Material(MapColor.Â).Âµá€().£á();
    }
    
    public Material(final MapColor p_i2116_1_) {
        this.¥à = true;
        this.ŠÂµà = p_i2116_1_;
    }
    
    public boolean HorizonCode_Horizon_È() {
        return false;
    }
    
    public boolean Â() {
        return true;
    }
    
    public boolean Ý() {
        return true;
    }
    
    public boolean Ø­áŒŠá() {
        return true;
    }
    
    private Material µà() {
        this.à¢ = true;
        return this;
    }
    
    protected Material Âµá€() {
        this.¥à = false;
        return this;
    }
    
    protected Material Ó() {
        this.á€ = true;
        return this;
    }
    
    public boolean à() {
        return this.á€;
    }
    
    public Material Ø() {
        this.Õ = true;
        return this;
    }
    
    public boolean áŒŠÆ() {
        return this.Õ;
    }
    
    public boolean áˆºÑ¢Õ() {
        return !this.à¢ && this.Ø­áŒŠá();
    }
    
    public boolean ÂµÈ() {
        return this.¥à;
    }
    
    public int á() {
        return this.Âµà;
    }
    
    protected Material ˆÏ­() {
        this.Âµà = 1;
        return this;
    }
    
    protected Material £á() {
        this.Âµà = 2;
        return this;
    }
    
    protected Material Å() {
        this.Ç = true;
        return this;
    }
    
    public MapColor £à() {
        return this.ŠÂµà;
    }
}
