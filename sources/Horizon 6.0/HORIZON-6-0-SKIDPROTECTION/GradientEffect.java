package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.awt.Paint;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class GradientEffect implements ConfigurableEffect
{
    private Color HorizonCode_Horizon_È;
    private Color Â;
    private int Ý;
    private float Ø­áŒŠá;
    private boolean Âµá€;
    
    public GradientEffect() {
        this.HorizonCode_Horizon_È = Color.cyan;
        this.Â = Color.blue;
        this.Ý = 0;
        this.Ø­áŒŠá = 1.0f;
    }
    
    public GradientEffect(final Color topColor, final Color bottomColor, final float scale) {
        this.HorizonCode_Horizon_È = Color.cyan;
        this.Â = Color.blue;
        this.Ý = 0;
        this.Ø­áŒŠá = 1.0f;
        this.HorizonCode_Horizon_È = topColor;
        this.Â = bottomColor;
        this.Ø­áŒŠá = scale;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final BufferedImage image, final Graphics2D g, final UnicodeFont unicodeFont, final Glyph glyph) {
        final int ascent = unicodeFont.Å();
        final float height = ascent * this.Ø­áŒŠá;
        final float top = -glyph.à() + unicodeFont.£à() + this.Ý + ascent / 2 - height / 2.0f;
        g.setPaint(new GradientPaint(0.0f, top, this.HorizonCode_Horizon_È, 0.0f, top + height, this.Â, this.Âµá€));
        g.fill(glyph.Âµá€());
    }
    
    public Color HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void HorizonCode_Horizon_È(final Color topColor) {
        this.HorizonCode_Horizon_È = topColor;
    }
    
    public Color Ý() {
        return this.Â;
    }
    
    public void Â(final Color bottomColor) {
        this.Â = bottomColor;
    }
    
    public int Ø­áŒŠá() {
        return this.Ý;
    }
    
    public void HorizonCode_Horizon_È(final int offset) {
        this.Ý = offset;
    }
    
    public float Âµá€() {
        return this.Ø­áŒŠá;
    }
    
    public void HorizonCode_Horizon_È(final float scale) {
        this.Ø­áŒŠá = scale;
    }
    
    public boolean Ó() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final boolean cyclic) {
        this.Âµá€ = cyclic;
    }
    
    @Override
    public String toString() {
        return "Gradient";
    }
    
    @Override
    public List Â() {
        final List values = new ArrayList();
        values.add(EffectUtil.HorizonCode_Horizon_È("Top color", this.HorizonCode_Horizon_È));
        values.add(EffectUtil.HorizonCode_Horizon_È("Bottom color", this.Â));
        values.add(EffectUtil.HorizonCode_Horizon_È("Offset", this.Ý, "This setting allows you to move the gradient up or down. The gradient is normally centered on the glyph."));
        values.add(EffectUtil.HorizonCode_Horizon_È("Scale", this.Ø­áŒŠá, 0.0f, 1.0f, "This setting allows you to change the height of the gradient by apercentage. The gradient is normally the height of most glyphs in the font."));
        values.add(EffectUtil.HorizonCode_Horizon_È("Cyclic", this.Âµá€, "If this setting is checked, the gradient will repeat."));
        return values;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final List values) {
        for (final HorizonCode_Horizon_È value : values) {
            if (value.HorizonCode_Horizon_È().equals("Top color")) {
                this.HorizonCode_Horizon_È = (Color)value.Ý();
            }
            else if (value.HorizonCode_Horizon_È().equals("Bottom color")) {
                this.Â = (Color)value.Ý();
            }
            else if (value.HorizonCode_Horizon_È().equals("Offset")) {
                this.Ý = (int)value.Ý();
            }
            else if (value.HorizonCode_Horizon_È().equals("Scale")) {
                this.Ø­áŒŠá = (float)value.Ý();
            }
            else {
                if (!value.HorizonCode_Horizon_È().equals("Cyclic")) {
                    continue;
                }
                this.Âµá€ = (boolean)value.Ý();
            }
        }
    }
}
