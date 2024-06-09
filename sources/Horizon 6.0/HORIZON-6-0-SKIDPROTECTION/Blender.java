package HORIZON-6-0-SKIDPROTECTION;

public class Blender
{
    public static final int HorizonCode_Horizon_È = 0;
    public static final int Â = 1;
    public static final int Ý = 2;
    public static final int Ø­áŒŠá = 3;
    public static final int Âµá€ = 4;
    public static final int Ó = 5;
    public static final int à = 6;
    public static final int Ø = 7;
    public static final int áŒŠÆ = 1;
    
    public static int HorizonCode_Horizon_È(String str) {
        if (str == null) {
            return 1;
        }
        str = str.toLowerCase().trim();
        if (str.equals("alpha")) {
            return 0;
        }
        if (str.equals("add")) {
            return 1;
        }
        if (str.equals("subtract")) {
            return 2;
        }
        if (str.equals("multiply")) {
            return 3;
        }
        if (str.equals("dodge")) {
            return 4;
        }
        if (str.equals("burn")) {
            return 5;
        }
        if (str.equals("screen")) {
            return 6;
        }
        if (str.equals("replace")) {
            return 7;
        }
        Config.Â("Unknown blend: " + str);
        return 1;
    }
    
    public static void HorizonCode_Horizon_È(final int blend, final float brightness) {
        switch (blend) {
            case 0: {
                GlStateManager.Ý();
                GlStateManager.á();
                GlStateManager.Â(770, 771);
                GlStateManager.Ý(1.0f, 1.0f, 1.0f, brightness);
                break;
            }
            case 1: {
                GlStateManager.Ý();
                GlStateManager.á();
                GlStateManager.Â(770, 1);
                GlStateManager.Ý(1.0f, 1.0f, 1.0f, brightness);
                break;
            }
            case 2: {
                GlStateManager.Ý();
                GlStateManager.á();
                GlStateManager.Â(775, 0);
                GlStateManager.Ý(brightness, brightness, brightness, 1.0f);
                break;
            }
            case 3: {
                GlStateManager.Ý();
                GlStateManager.á();
                GlStateManager.Â(774, 771);
                GlStateManager.Ý(brightness, brightness, brightness, brightness);
                break;
            }
            case 4: {
                GlStateManager.Ý();
                GlStateManager.á();
                GlStateManager.Â(1, 1);
                GlStateManager.Ý(brightness, brightness, brightness, 1.0f);
                break;
            }
            case 5: {
                GlStateManager.Ý();
                GlStateManager.á();
                GlStateManager.Â(0, 769);
                GlStateManager.Ý(brightness, brightness, brightness, 1.0f);
                break;
            }
            case 6: {
                GlStateManager.Ý();
                GlStateManager.á();
                GlStateManager.Â(1, 769);
                GlStateManager.Ý(brightness, brightness, brightness, 1.0f);
                break;
            }
            case 7: {
                GlStateManager.Ø­áŒŠá();
                GlStateManager.ÂµÈ();
                GlStateManager.Ý(1.0f, 1.0f, 1.0f, brightness);
                break;
            }
        }
        GlStateManager.µÕ();
    }
    
    public static void HorizonCode_Horizon_È(final float rainBrightness) {
        GlStateManager.Ý();
        GlStateManager.á();
        GlStateManager.Â(770, 1);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, rainBrightness);
    }
}
