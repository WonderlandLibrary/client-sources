package HORIZON-6-0-SKIDPROTECTION;

import java.awt.Color;
import java.util.Random;

public class FADE
{
    public static float HorizonCode_Horizon_È;
    public static float Â;
    public static float Ý;
    public static int Ø­áŒŠá;
    public boolean Âµá€;
    public boolean Ó;
    public boolean à;
    public boolean Ø;
    public boolean áŒŠÆ;
    public boolean áˆºÑ¢Õ;
    private TimeHelper ÂµÈ;
    
    static {
        FADE.HorizonCode_Horizon_È = 0.0f;
        FADE.Â = 0.0f;
        FADE.Ý = 0.0f;
        FADE.Ø­áŒŠá = 0;
    }
    
    public FADE() {
        this.Âµá€ = true;
        this.Ó = false;
        this.à = false;
        this.Ø = false;
        this.áŒŠÆ = false;
        this.áˆºÑ¢Õ = false;
        this.ÂµÈ = new TimeHelper();
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventTick e) {
        if (!this.ÂµÈ.Â(5L)) {
            return;
        }
        this.ÂµÈ.Ø­áŒŠá();
        if (this.Âµá€) {
            FADE.HorizonCode_Horizon_È += 0.03;
            if (FADE.HorizonCode_Horizon_È >= 1.0f) {
                this.Âµá€ = false;
                this.Ó = true;
            }
        }
        if (this.Ø) {
            FADE.HorizonCode_Horizon_È -= 0.03;
            if (FADE.HorizonCode_Horizon_È <= 0.2f) {
                this.Ø = false;
                this.áŒŠÆ = true;
            }
        }
        if (this.Ó) {
            FADE.Â += 0.03;
            if (FADE.Â >= 1.0f) {
                this.Ó = false;
                this.à = true;
            }
        }
        if (this.áŒŠÆ) {
            FADE.Â -= 0.03;
            if (FADE.Â <= 0.2f) {
                this.áŒŠÆ = false;
                this.áˆºÑ¢Õ = true;
            }
        }
        if (this.à) {
            FADE.Ý += 0.07;
            if (FADE.Ý >= 1.0f) {
                this.à = false;
                this.Ø = true;
            }
        }
        if (this.áˆºÑ¢Õ) {
            FADE.Ý -= 0.07;
            if (FADE.Ý <= 0.2f) {
                this.Âµá€ = true;
                this.áˆºÑ¢Õ = false;
            }
        }
        final int var7 = new Random().nextInt(254);
        int var8 = (int)(var7 * 255.0f / 20.0f);
        if (var8 > 255) {
            var8 = 255;
        }
        final int var9 = Color.HSBtoRGB(var7 / 50.0f, 0.7f, 0.6f) & 0xFFFFFF;
        FADE.Ø­áŒŠá = var9 + (var8 << 24 & 0xFF000000);
    }
}
