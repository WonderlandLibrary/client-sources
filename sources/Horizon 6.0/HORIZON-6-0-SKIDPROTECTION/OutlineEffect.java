package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Stroke;
import java.awt.Color;

public class OutlineEffect implements ConfigurableEffect
{
    private float HorizonCode_Horizon_È;
    private Color Â;
    private int Ý;
    private Stroke Ø­áŒŠá;
    
    public OutlineEffect() {
        this.HorizonCode_Horizon_È = 2.0f;
        this.Â = Color.black;
        this.Ý = 2;
    }
    
    public OutlineEffect(final int width, final Color color) {
        this.HorizonCode_Horizon_È = 2.0f;
        this.Â = Color.black;
        this.Ý = 2;
        this.HorizonCode_Horizon_È = width;
        this.Â = color;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final BufferedImage image, Graphics2D g, final UnicodeFont unicodeFont, final Glyph glyph) {
        g = (Graphics2D)g.create();
        if (this.Ø­áŒŠá != null) {
            g.setStroke(this.Ø­áŒŠá);
        }
        else {
            g.setStroke(this.Âµá€());
        }
        g.setColor(this.Â);
        g.draw(glyph.Âµá€());
        g.dispose();
    }
    
    public float HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void HorizonCode_Horizon_È(final int width) {
        this.HorizonCode_Horizon_È = width;
    }
    
    public Color Ý() {
        return this.Â;
    }
    
    public void HorizonCode_Horizon_È(final Color color) {
        this.Â = color;
    }
    
    public int Ø­áŒŠá() {
        return this.Ý;
    }
    
    public Stroke Âµá€() {
        if (this.Ø­áŒŠá == null) {
            return new BasicStroke(this.HorizonCode_Horizon_È, 2, this.Ý);
        }
        return this.Ø­áŒŠá;
    }
    
    public void HorizonCode_Horizon_È(final Stroke stroke) {
        this.Ø­áŒŠá = stroke;
    }
    
    public void Â(final int join) {
        this.Ý = join;
    }
    
    @Override
    public String toString() {
        return "Outline";
    }
    
    @Override
    public List Â() {
        final List values = new ArrayList();
        values.add(EffectUtil.HorizonCode_Horizon_È("Color", this.Â));
        values.add(EffectUtil.HorizonCode_Horizon_È("Width", this.HorizonCode_Horizon_È, 0.1f, 999.0f, "This setting controls the width of the outline. The glyphs will need padding so the outline doesn't get clipped."));
        values.add(EffectUtil.HorizonCode_Horizon_È("Join", String.valueOf(this.Ý), new String[][] { { "Bevel", "2" }, { "Miter", "0" }, { "Round", "1" } }, "This setting defines how the corners of the outline are drawn. This is usually only noticeable at large outline widths."));
        return values;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final List values) {
        for (final HorizonCode_Horizon_È value : values) {
            if (value.HorizonCode_Horizon_È().equals("Color")) {
                this.Â = (Color)value.Ý();
            }
            else if (value.HorizonCode_Horizon_È().equals("Width")) {
                this.HorizonCode_Horizon_È = (float)value.Ý();
            }
            else {
                if (!value.HorizonCode_Horizon_È().equals("Join")) {
                    continue;
                }
                this.Ý = Integer.parseInt((String)value.Ý());
            }
        }
    }
}
