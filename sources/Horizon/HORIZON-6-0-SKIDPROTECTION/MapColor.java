package HORIZON-6-0-SKIDPROTECTION;

public class MapColor
{
    public static final MapColor[] HorizonCode_Horizon_È;
    public static final MapColor Â;
    public static final MapColor Ý;
    public static final MapColor Ø­áŒŠá;
    public static final MapColor Âµá€;
    public static final MapColor Ó;
    public static final MapColor à;
    public static final MapColor Ø;
    public static final MapColor áŒŠÆ;
    public static final MapColor áˆºÑ¢Õ;
    public static final MapColor ÂµÈ;
    public static final MapColor á;
    public static final MapColor ˆÏ­;
    public static final MapColor £á;
    public static final MapColor Å;
    public static final MapColor £à;
    public static final MapColor µà;
    public static final MapColor ˆà;
    public static final MapColor ¥Æ;
    public static final MapColor Ø­à;
    public static final MapColor µÕ;
    public static final MapColor Æ;
    public static final MapColor Šáƒ;
    public static final MapColor Ï­Ðƒà;
    public static final MapColor áŒŠà;
    public static final MapColor ŠÄ;
    public static final MapColor Ñ¢á;
    public static final MapColor ŒÏ;
    public static final MapColor Çªà¢;
    public static final MapColor Ê;
    public static final MapColor ÇŽÉ;
    public static final MapColor ˆá;
    public static final MapColor ÇŽÕ;
    public static final MapColor É;
    public static final MapColor áƒ;
    public static final MapColor á€;
    public static final MapColor Õ;
    public final int à¢;
    public final int ŠÂµà;
    private static final String ¥à = "CL_00000544";
    
    static {
        HorizonCode_Horizon_È = new MapColor[64];
        Â = new MapColor(0, 0);
        Ý = new MapColor(1, 8368696);
        Ø­áŒŠá = new MapColor(2, 16247203);
        Âµá€ = new MapColor(3, 10987431);
        Ó = new MapColor(4, 16711680);
        à = new MapColor(5, 10526975);
        Ø = new MapColor(6, 10987431);
        áŒŠÆ = new MapColor(7, 31744);
        áˆºÑ¢Õ = new MapColor(8, 16777215);
        ÂµÈ = new MapColor(9, 10791096);
        á = new MapColor(10, 12020271);
        ˆÏ­ = new MapColor(11, 7368816);
        £á = new MapColor(12, 4210943);
        Å = new MapColor(13, 6837042);
        £à = new MapColor(14, 16776437);
        µà = new MapColor(15, 14188339);
        ˆà = new MapColor(16, 11685080);
        ¥Æ = new MapColor(17, 6724056);
        Ø­à = new MapColor(18, 15066419);
        µÕ = new MapColor(19, 8375321);
        Æ = new MapColor(20, 15892389);
        Šáƒ = new MapColor(21, 5000268);
        Ï­Ðƒà = new MapColor(22, 10066329);
        áŒŠà = new MapColor(23, 5013401);
        ŠÄ = new MapColor(24, 8339378);
        Ñ¢á = new MapColor(25, 3361970);
        ŒÏ = new MapColor(26, 6704179);
        Çªà¢ = new MapColor(27, 6717235);
        Ê = new MapColor(28, 10040115);
        ÇŽÉ = new MapColor(29, 1644825);
        ˆá = new MapColor(30, 16445005);
        ÇŽÕ = new MapColor(31, 6085589);
        É = new MapColor(32, 4882687);
        áƒ = new MapColor(33, 55610);
        á€ = new MapColor(34, 1381407);
        Õ = new MapColor(35, 7340544);
    }
    
    private MapColor(final int p_i2117_1_, final int p_i2117_2_) {
        if (p_i2117_1_ >= 0 && p_i2117_1_ <= 63) {
            this.ŠÂµà = p_i2117_1_;
            this.à¢ = p_i2117_2_;
            MapColor.HorizonCode_Horizon_È[p_i2117_1_] = this;
            return;
        }
        throw new IndexOutOfBoundsException("Map colour ID must be between 0 and 63 (inclusive)");
    }
    
    public int HorizonCode_Horizon_È(final int p_151643_1_) {
        short var2 = 220;
        if (p_151643_1_ == 3) {
            var2 = 135;
        }
        if (p_151643_1_ == 2) {
            var2 = 255;
        }
        if (p_151643_1_ == 1) {
            var2 = 220;
        }
        if (p_151643_1_ == 0) {
            var2 = 180;
        }
        final int var3 = (this.à¢ >> 16 & 0xFF) * var2 / 255;
        final int var4 = (this.à¢ >> 8 & 0xFF) * var2 / 255;
        final int var5 = (this.à¢ & 0xFF) * var2 / 255;
        return 0xFF000000 | var3 << 16 | var4 << 8 | var5;
    }
}
