package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class ColorEffect implements ConfigurableEffect
{
    private Color HorizonCode_Horizon_È;
    
    public ColorEffect() {
        this.HorizonCode_Horizon_È = Color.white;
    }
    
    public ColorEffect(final Color color) {
        this.HorizonCode_Horizon_È = Color.white;
        this.HorizonCode_Horizon_È = color;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final BufferedImage image, final Graphics2D g, final UnicodeFont unicodeFont, final Glyph glyph) {
        g.setColor(this.HorizonCode_Horizon_È);
        g.fill(glyph.Âµá€());
    }
    
    public Color HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void HorizonCode_Horizon_È(final Color color) {
        if (color == null) {
            throw new IllegalArgumentException("color cannot be null.");
        }
        this.HorizonCode_Horizon_È = color;
    }
    
    @Override
    public String toString() {
        return "Color";
    }
    
    @Override
    public List Â() {
        final List values = new ArrayList();
        values.add(EffectUtil.HorizonCode_Horizon_È("Color", this.HorizonCode_Horizon_È));
        return values;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final List values) {
        for (final HorizonCode_Horizon_È value : values) {
            if (value.HorizonCode_Horizon_È().equals("Color")) {
                this.HorizonCode_Horizon_È((Color)value.Ý());
            }
        }
    }
}
