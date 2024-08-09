package ez.h.ui.flatclickgui;

import ez.h.ui.clickgui.options.*;
import ez.h.ui.fonts.*;
import java.awt.*;
import ez.h.features.visual.*;
import ez.h.utils.*;
import ez.h.ui.clickgui.element.*;

public class FlatCheckBox extends FlatElement
{
    float glowAnim;
    public OptionBoolean option;
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (!FlatElement.isHover((float)n, (float)n2, this.x, this.y, this.width, this.height)) {
            return;
        }
        this.option.enabled = !this.option.enabled;
        this.glowAnim = 40.0f;
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        CFontManager.manropesmall.drawString(this.option.getName(), this.x + 6.0f, this.y + 2.0f, new Color(160 + 240 - 354 + 209, 134 + 123 - 252 + 250, 237 + 0 - 72 + 90, 42 + 30 + 9 + 69).getRGB());
        this.glowAnim = MathUtils.lerp(this.glowAnim, this.option.enabled ? 10.0f : 40.0f, 0.1f);
        final int rgb = Utils.getGradientOffset(new Color(-1), ClickGUI.clickGUIColor.getColor(), 1.0f - (this.glowAnim - 10.0f) / 40.0f, 214 + 153 - 345 + 233).getRGB();
        RenderUtils.drawBlurredShadowCircle(this.x + this.width - 21.0f + 6.0f, this.y + 2.0f, 11.0f, 11.0f, (int)this.glowAnim, new Color(rgb));
        ElementColor.drawCircle(this.x + this.width - 15.0f + 6.0f, this.y + 8.0f, 3.0, new Color(rgb).darker().getRGB());
        super.render(n, n2, n3);
    }
    
    public FlatCheckBox(final OptionBoolean optionBoolean) {
        this.height = 14.0f;
        this.option = optionBoolean;
        this.displayOption = optionBoolean;
    }
}
