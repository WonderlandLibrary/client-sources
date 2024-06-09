package HORIZON-6-0-SKIDPROTECTION;

import java.nio.FloatBuffer;
import java.io.Serializable;

public class Color implements Serializable
{
    private static final long Ø­à = 1393939L;
    protected transient SGL HorizonCode_Horizon_È;
    public static final Color Â;
    public static final Color Ý;
    public static final Color Ø­áŒŠá;
    public static final Color Âµá€;
    public static final Color Ó;
    public static final Color à;
    public static final Color Ø;
    public static final Color áŒŠÆ;
    public static final Color áˆºÑ¢Õ;
    public static final Color ÂµÈ;
    public static final Color á;
    public static final Color ˆÏ­;
    public static final Color £á;
    public static final Color Å;
    public float £à;
    public float µà;
    public float ˆà;
    public float ¥Æ;
    
    static {
        Â = new Color(0.0f, 0.0f, 0.0f, 0.0f);
        Ý = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        Ø­áŒŠá = new Color(1.0f, 1.0f, 0.0f, 1.0f);
        Âµá€ = new Color(1.0f, 0.0f, 0.0f, 1.0f);
        Ó = new Color(0.0f, 0.0f, 1.0f, 1.0f);
        à = new Color(0.0f, 1.0f, 0.0f, 1.0f);
        Ø = new Color(0.0f, 0.0f, 0.0f, 1.0f);
        áŒŠÆ = new Color(0.5f, 0.5f, 0.5f, 1.0f);
        áˆºÑ¢Õ = new Color(0.0f, 1.0f, 1.0f, 1.0f);
        ÂµÈ = new Color(0.3f, 0.3f, 0.3f, 1.0f);
        á = new Color(0.7f, 0.7f, 0.7f, 1.0f);
        ˆÏ­ = new Color(255, 175, 175, 255);
        £á = new Color(255, 200, 0, 255);
        Å = new Color(255, 0, 255, 255);
    }
    
    public Color(final Color color) {
        this.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
        this.¥Æ = 1.0f;
        this.£à = color.£à;
        this.µà = color.µà;
        this.ˆà = color.ˆà;
        this.¥Æ = color.¥Æ;
    }
    
    public Color(final FloatBuffer buffer) {
        this.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
        this.¥Æ = 1.0f;
        this.£à = buffer.get();
        this.µà = buffer.get();
        this.ˆà = buffer.get();
        this.¥Æ = buffer.get();
    }
    
    public Color(final float r, final float g, final float b) {
        this.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
        this.¥Æ = 1.0f;
        this.£à = r;
        this.µà = g;
        this.ˆà = b;
        this.¥Æ = 1.0f;
    }
    
    public Color(final float r, final float g, final float b, final float a) {
        this.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
        this.¥Æ = 1.0f;
        this.£à = Math.min(r, 1.0f);
        this.µà = Math.min(g, 1.0f);
        this.ˆà = Math.min(b, 1.0f);
        this.¥Æ = Math.min(a, 1.0f);
    }
    
    public Color(final int r, final int g, final int b) {
        this.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
        this.¥Æ = 1.0f;
        this.£à = r / 255.0f;
        this.µà = g / 255.0f;
        this.ˆà = b / 255.0f;
        this.¥Æ = 1.0f;
    }
    
    public Color(final int r, final int g, final int b, final int a) {
        this.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
        this.¥Æ = 1.0f;
        this.£à = r / 255.0f;
        this.µà = g / 255.0f;
        this.ˆà = b / 255.0f;
        this.¥Æ = a / 255.0f;
    }
    
    public Color(final int value) {
        this.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
        this.¥Æ = 1.0f;
        final int r = (value & 0xFF0000) >> 16;
        final int g = (value & 0xFF00) >> 8;
        final int b = value & 0xFF;
        int a = (value & 0xFF000000) >> 24;
        if (a < 0) {
            a += 256;
        }
        if (a == 0) {
            a = 255;
        }
        this.£à = r / 255.0f;
        this.µà = g / 255.0f;
        this.ˆà = b / 255.0f;
        this.¥Æ = a / 255.0f;
    }
    
    public static Color HorizonCode_Horizon_È(final String nm) {
        return new Color(Integer.decode(nm));
    }
    
    public void HorizonCode_Horizon_È() {
        this.HorizonCode_Horizon_È.Â(this.£à, this.µà, this.ˆà, this.¥Æ);
    }
    
    @Override
    public int hashCode() {
        return (int)(this.£à + this.µà + this.ˆà + this.¥Æ) * 255;
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other instanceof Color) {
            final Color o = (Color)other;
            return o.£à == this.£à && o.µà == this.µà && o.ˆà == this.ˆà && o.¥Æ == this.¥Æ;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "Color (" + this.£à + "," + this.µà + "," + this.ˆà + "," + this.¥Æ + ")";
    }
    
    public Color Â() {
        return this.HorizonCode_Horizon_È(0.5f);
    }
    
    public Color HorizonCode_Horizon_È(float scale) {
        scale = 1.0f - scale;
        final Color temp = new Color(this.£à * scale, this.µà * scale, this.ˆà * scale, this.¥Æ);
        return temp;
    }
    
    public Color Ý() {
        return this.Â(0.2f);
    }
    
    public int Ø­áŒŠá() {
        return (int)(this.£à * 255.0f);
    }
    
    public int Âµá€() {
        return (int)(this.µà * 255.0f);
    }
    
    public int Ó() {
        return (int)(this.ˆà * 255.0f);
    }
    
    public int à() {
        return (int)(this.¥Æ * 255.0f);
    }
    
    public int Ø() {
        return (int)(this.£à * 255.0f);
    }
    
    public int áŒŠÆ() {
        return (int)(this.µà * 255.0f);
    }
    
    public int áˆºÑ¢Õ() {
        return (int)(this.ˆà * 255.0f);
    }
    
    public int ÂµÈ() {
        return (int)(this.¥Æ * 255.0f);
    }
    
    public Color Â(float scale) {
        ++scale;
        final Color temp = new Color(this.£à * scale, this.µà * scale, this.ˆà * scale, this.¥Æ);
        return temp;
    }
    
    public Color HorizonCode_Horizon_È(final Color c) {
        return new Color(this.£à * c.£à, this.µà * c.µà, this.ˆà * c.ˆà, this.¥Æ * c.¥Æ);
    }
    
    public void Â(final Color c) {
        this.£à += c.£à;
        this.µà += c.µà;
        this.ˆà += c.ˆà;
        this.¥Æ += c.¥Æ;
    }
    
    public void Ý(final float value) {
        this.£à *= value;
        this.µà *= value;
        this.ˆà *= value;
        this.¥Æ *= value;
    }
    
    public Color Ý(final Color c) {
        final Color color;
        final Color copy = color = new Color(this.£à, this.µà, this.ˆà, this.¥Æ);
        color.£à += c.£à;
        final Color color2 = copy;
        color2.µà += c.µà;
        final Color color3 = copy;
        color3.ˆà += c.ˆà;
        final Color color4 = copy;
        color4.¥Æ += c.¥Æ;
        return copy;
    }
    
    public Color Ø­áŒŠá(final float value) {
        final Color color;
        final Color copy = color = new Color(this.£à, this.µà, this.ˆà, this.¥Æ);
        color.£à *= value;
        final Color color2 = copy;
        color2.µà *= value;
        final Color color3 = copy;
        color3.ˆà *= value;
        final Color color4 = copy;
        color4.¥Æ *= value;
        return copy;
    }
}
