package ez.h.ui.clickgui.element;

import ez.h.ui.clickgui.options.*;
import ez.h.ui.clickgui.*;
import ez.h.features.visual.*;
import java.awt.*;
import ez.h.utils.*;
import ez.h.ui.fonts.*;

public class ElementMode extends Element
{
    ElementButton button;
    public OptionMode option;
    public float lerpedWidth;
    Panel panel;
    
    public ElementMode(final Panel panel, final OptionMode optionMode, final ElementButton button) {
        super(panel);
        this.panel = panel;
        this.button = button;
        this.option = optionMode;
        this.height = 30.0f;
        this.displayOption = optionMode;
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        final float a = rk.a(this.y * 2.0f, 0.0f, (float)bib.z().e);
        Color color;
        if (ClickGUI.easing.enabled) {
            color = Utils.getGradientOffset(ClickGUI.clickGUIColor.getColor(), ClickGUI.easingColor.getColor(), a / bib.z().e, 61 + 213 - 193 + 174);
        }
        else {
            color = ClickGUI.clickGUIColor.getColor();
        }
        this.lerpedWidth = MathUtils.lerp(this.lerpedWidth, 63.0f, 0.1f);
        RenderUtils.drawRect(this.x + 2.0f, this.y, this.x + this.width - 2.0f, this.y + this.height, new Color(0xC8 ^ 0xC2, 0x91 ^ 0x9B, 0x94 ^ 0x9E, 94 + 113 - 102 + 95).getRGB());
        CFontManager.manropesmall.drawCenteredStringNoShadow(this.option.getName(), this.x + this.width / 2.0f, this.y + 3.0f, RenderUtils.injectAlpha(-1, 198 + 39 - 65 + 28).getRGB());
        RenderUtils.drawBlurredShadow(this.x + 20.0f - 1.0f, this.y + this.height / 2.0f - 1.0f, this.lerpedWidth + 2.0f, 3.0f, 6, color);
        RenderUtils.drawRoundedRect(this.x + 20.0f, this.y + this.height / 2.0f, this.lerpedWidth, 1.0, 1.0, color.getRGB());
        CFontManager.manropesmall.drawCenteredStringNoShadow(this.option.getMode(), this.x + this.width / 2.0f, this.y + this.height / 2.0f + 1.5f, RenderUtils.injectAlpha(-1, 82 + 128 - 67 + 7).getRGB());
        super.render(n, n2, n3);
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (!this.isHover((float)n, (float)n2, this.x, this.y, this.width, this.height)) {
            return;
        }
        int n4 = this.option.getIndex();
        if (n3 == 1) {
            if (n4 == 0) {
                n4 = this.option.getValues().length;
            }
            if (n4 >= 1) {
                --n4;
            }
            this.option.setIndex(n4);
            this.option.setMode(this.option.getValues()[n4]);
        }
        else if (n3 == 0) {
            if (++n4 >= this.option.getValues().length) {
                n4 = 0;
            }
            this.option.setIndex(n4);
            this.option.setMode(this.option.getValues()[n4]);
        }
        super.mouseClicked(n, n2, n3);
    }
}
