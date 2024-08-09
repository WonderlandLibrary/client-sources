package ez.h.ui.clickgui.options;

import ez.h.ui.clickgui.element.*;
import java.awt.*;
import ez.h.utils.*;
import ez.h.features.*;

public class OptionColor extends Option
{
    public boolean needAlpha;
    public ElementColor elementColor;
    public int alpha;
    Color color;
    
    public Color getColor() {
        if (this.color == null) {
            return new Color(193 + 103 - 230 + 189, 184 + 173 - 299 + 197, 56 + 112 - 9 + 96, this.alpha);
        }
        return RenderUtils.injectAlpha(this.color.getRGB(), this.alpha);
    }
    
    public void setColor(final Color color) {
        this.color = RenderUtils.injectAlpha(color.getRGB(), this.alpha);
    }
    
    public OptionColor(final Feature feature, final String s, final Color color) {
        super(feature, s, color);
        this.alpha = 83 + 84 + 5 + 83;
        this.color = color;
        this.alpha = color.getAlpha();
    }
    
    public OptionColor(final Feature feature, final String s, final Color color, final boolean needAlpha) {
        super(feature, s, color);
        this.alpha = 164 + 144 - 188 + 135;
        this.color = color;
        this.alpha = color.getAlpha();
        this.needAlpha = needAlpha;
    }
}
