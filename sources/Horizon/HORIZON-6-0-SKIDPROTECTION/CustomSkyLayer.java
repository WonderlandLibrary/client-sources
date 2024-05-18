package HORIZON-6-0-SKIDPROTECTION;

import java.util.Properties;

public class CustomSkyLayer
{
    public String HorizonCode_Horizon_È;
    private int Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private int à;
    private int Ø;
    private boolean áŒŠÆ;
    private float áˆºÑ¢Õ;
    private float[] ÂµÈ;
    public int Â;
    public static final float[] Ý;
    
    static {
        Ý = new float[] { 1.0f, 0.0f, 0.0f };
    }
    
    public CustomSkyLayer(final Properties props, final String defSource) {
        this.HorizonCode_Horizon_È = null;
        this.Ø­áŒŠá = -1;
        this.Âµá€ = -1;
        this.Ó = -1;
        this.à = -1;
        this.Ø = 1;
        this.áŒŠÆ = false;
        this.áˆºÑ¢Õ = 1.0f;
        this.ÂµÈ = CustomSkyLayer.Ý;
        this.Â = -1;
        this.HorizonCode_Horizon_È = props.getProperty("source", defSource);
        this.Ø­áŒŠá = this.Â(props.getProperty("startFadeIn"));
        this.Âµá€ = this.Â(props.getProperty("endFadeIn"));
        this.Ó = this.Â(props.getProperty("startFadeOut"));
        this.à = this.Â(props.getProperty("endFadeOut"));
        this.Ø = Blender.HorizonCode_Horizon_È(props.getProperty("blend"));
        this.áŒŠÆ = this.HorizonCode_Horizon_È(props.getProperty("rotate"), true);
        this.áˆºÑ¢Õ = this.HorizonCode_Horizon_È(props.getProperty("speed"), 1.0f);
        this.ÂµÈ = this.HorizonCode_Horizon_È(props.getProperty("axis"), CustomSkyLayer.Ý);
    }
    
    private int Â(final String str) {
        if (str == null) {
            return -1;
        }
        final String[] strs = Config.HorizonCode_Horizon_È(str, ":");
        if (strs.length != 2) {
            Config.Â("Invalid time: " + str);
            return -1;
        }
        final String hourStr = strs[0];
        final String minStr = strs[1];
        int hour = Config.HorizonCode_Horizon_È(hourStr, -1);
        final int min = Config.HorizonCode_Horizon_È(minStr, -1);
        if (hour >= 0 && hour <= 23 && min >= 0 && min <= 59) {
            hour -= 6;
            if (hour < 0) {
                hour += 24;
            }
            final int time = hour * 1000 + (int)(min / 60.0 * 1000.0);
            return time;
        }
        Config.Â("Invalid time: " + str);
        return -1;
    }
    
    private boolean HorizonCode_Horizon_È(final String str, final boolean defVal) {
        if (str == null) {
            return defVal;
        }
        if (str.toLowerCase().equals("true")) {
            return true;
        }
        if (str.toLowerCase().equals("false")) {
            return false;
        }
        Config.Â("Unknown boolean: " + str);
        return defVal;
    }
    
    private float HorizonCode_Horizon_È(final String str, final float defVal) {
        if (str == null) {
            return defVal;
        }
        final float val = Config.HorizonCode_Horizon_È(str, Float.MIN_VALUE);
        if (val == Float.MIN_VALUE) {
            Config.Â("Invalid value: " + str);
            return defVal;
        }
        return val;
    }
    
    private float[] HorizonCode_Horizon_È(final String str, final float[] defVal) {
        if (str == null) {
            return defVal;
        }
        final String[] strs = Config.HorizonCode_Horizon_È(str, " ");
        if (strs.length != 3) {
            Config.Â("Invalid axis: " + str);
            return defVal;
        }
        final float[] fs = new float[3];
        for (int ax = 0; ax < strs.length; ++ax) {
            fs[ax] = Config.HorizonCode_Horizon_È(strs[ax], Float.MIN_VALUE);
            if (fs[ax] == Float.MIN_VALUE) {
                Config.Â("Invalid axis: " + str);
                return defVal;
            }
            if (fs[ax] < -1.0f || fs[ax] > 1.0f) {
                Config.Â("Invalid axis values: " + str);
                return defVal;
            }
        }
        final float var9 = fs[0];
        final float ay = fs[1];
        final float az = fs[2];
        if (var9 * var9 + ay * ay + az * az < 1.0E-5f) {
            Config.Â("Invalid axis values: " + str);
            return defVal;
        }
        final float[] as = { az, ay, -var9 };
        return as;
    }
    
    public boolean HorizonCode_Horizon_È(final String path) {
        if (this.HorizonCode_Horizon_È == null) {
            Config.Â("No source texture: " + path);
            return false;
        }
        this.HorizonCode_Horizon_È = TextureUtils.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, TextureUtils.Â(path));
        if (this.Ø­áŒŠá < 0 || this.Âµá€ < 0 || this.à < 0) {
            Config.Â("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
            return false;
        }
        final int timeFadeIn = this.Â(this.Âµá€ - this.Ø­áŒŠá);
        if (this.Ó < 0) {
            this.Ó = this.Â(this.à - timeFadeIn);
        }
        final int timeOn = this.Â(this.Ó - this.Âµá€);
        final int timeFadeOut = this.Â(this.à - this.Ó);
        final int timeOff = this.Â(this.Ø­áŒŠá - this.à);
        final int timeSum = timeFadeIn + timeOn + timeFadeOut + timeOff;
        if (timeSum != 24000) {
            Config.Â("Invalid fadeIn/fadeOut times, sum is more than 24h: " + timeSum);
            return false;
        }
        if (this.áˆºÑ¢Õ < 0.0f) {
            Config.Â("Invalid speed: " + this.áˆºÑ¢Õ);
            return false;
        }
        return true;
    }
    
    private int Â(int timeMc) {
        while (timeMc >= 24000) {
            timeMc -= 24000;
        }
        while (timeMc < 0) {
            timeMc += 24000;
        }
        return timeMc;
    }
    
    public void HorizonCode_Horizon_È(final int timeOfDay, final float celestialAngle, final float rainBrightness) {
        float brightness = rainBrightness * this.Ý(timeOfDay);
        brightness = Config.HorizonCode_Horizon_È(brightness, 0.0f, 1.0f);
        if (brightness >= 1.0E-4f) {
            GlStateManager.áŒŠÆ(this.Â);
            Blender.HorizonCode_Horizon_È(this.Ø, brightness);
            GlStateManager.Çªà¢();
            if (this.áŒŠÆ) {
                GlStateManager.Â(celestialAngle * 360.0f * this.áˆºÑ¢Õ, this.ÂµÈ[0], this.ÂµÈ[1], this.ÂµÈ[2]);
            }
            final Tessellator tess = Tessellator.HorizonCode_Horizon_È();
            GlStateManager.Â(90.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.Â(-90.0f, 0.0f, 0.0f, 1.0f);
            this.HorizonCode_Horizon_È(tess, 4);
            GlStateManager.Çªà¢();
            GlStateManager.Â(90.0f, 1.0f, 0.0f, 0.0f);
            this.HorizonCode_Horizon_È(tess, 1);
            GlStateManager.Ê();
            GlStateManager.Çªà¢();
            GlStateManager.Â(-90.0f, 1.0f, 0.0f, 0.0f);
            this.HorizonCode_Horizon_È(tess, 0);
            GlStateManager.Ê();
            GlStateManager.Â(90.0f, 0.0f, 0.0f, 1.0f);
            this.HorizonCode_Horizon_È(tess, 5);
            GlStateManager.Â(90.0f, 0.0f, 0.0f, 1.0f);
            this.HorizonCode_Horizon_È(tess, 2);
            GlStateManager.Â(90.0f, 0.0f, 0.0f, 1.0f);
            this.HorizonCode_Horizon_È(tess, 3);
            GlStateManager.Ê();
        }
    }
    
    private float Ý(final int timeOfDay) {
        if (this.HorizonCode_Horizon_È(timeOfDay, this.Ø­áŒŠá, this.Âµá€)) {
            final int timeFadeOut = this.Â(this.Âµá€ - this.Ø­áŒŠá);
            final int timeDiff = this.Â(timeOfDay - this.Ø­áŒŠá);
            return timeDiff / timeFadeOut;
        }
        if (this.HorizonCode_Horizon_È(timeOfDay, this.Âµá€, this.Ó)) {
            return 1.0f;
        }
        if (this.HorizonCode_Horizon_È(timeOfDay, this.Ó, this.à)) {
            final int timeFadeOut = this.Â(this.à - this.Ó);
            final int timeDiff = this.Â(timeOfDay - this.Ó);
            return 1.0f - timeDiff / timeFadeOut;
        }
        return 0.0f;
    }
    
    private void HorizonCode_Horizon_È(final Tessellator tess, final int side) {
        final WorldRenderer wr = tess.Ý();
        final double tx = side % 3 / 3.0;
        final double ty = side / 3 / 2.0;
        wr.Â();
        wr.HorizonCode_Horizon_È(-100.0, -100.0, -100.0, tx, ty);
        wr.HorizonCode_Horizon_È(-100.0, -100.0, 100.0, tx, ty + 0.5);
        wr.HorizonCode_Horizon_È(100.0, -100.0, 100.0, tx + 0.3333333333333333, ty + 0.5);
        wr.HorizonCode_Horizon_È(100.0, -100.0, -100.0, tx + 0.3333333333333333, ty);
        tess.Â();
    }
    
    public boolean HorizonCode_Horizon_È(final int timeOfDay) {
        return !this.HorizonCode_Horizon_È(timeOfDay, this.à, this.Ø­áŒŠá);
    }
    
    private boolean HorizonCode_Horizon_È(final int timeOfDay, final int timeStart, final int timeEnd) {
        return (timeStart <= timeEnd) ? (timeOfDay >= timeStart && timeOfDay <= timeEnd) : (timeOfDay >= timeStart || timeOfDay <= timeEnd);
    }
}
