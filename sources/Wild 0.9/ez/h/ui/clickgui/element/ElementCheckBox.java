package ez.h.ui.clickgui.element;

import ez.h.ui.clickgui.*;
import ez.h.ui.clickgui.options.*;
import ez.h.features.visual.*;
import java.awt.*;
import ez.h.ui.fonts.*;
import ez.h.utils.*;

public class ElementCheckBox extends Element
{
    float checkBoxOffset;
    Panel panel;
    public OptionBoolean option;
    public float gradientFactor;
    ElementButton button;
    
    public ElementCheckBox(final Panel panel, final OptionBoolean optionBoolean, final ElementButton button) {
        super(panel);
        this.checkBoxOffset = 0.0f;
        this.panel = panel;
        this.button = button;
        this.option = optionBoolean;
        this.height = 18.0f;
        this.displayOption = optionBoolean;
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (!this.isHover((float)n, (float)n2, this.x, this.y, this.width, this.height)) {
            return;
        }
        this.option.enabled = !this.option.enabled;
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        if (this.option.enabled) {
            this.gradientFactor = AnimUtils.lerp(this.gradientFactor, 150.0f, 0.15f);
        }
        else {
            this.gradientFactor = AnimUtils.lerp(this.gradientFactor, 0.0f, 0.15f);
        }
        final float a = rk.a(this.y * 2.0f, 0.0f, (float)bib.z().e);
        Color color;
        if (ClickGUI.easing.enabled) {
            color = Utils.getGradientOffset(ClickGUI.clickGUIColor.getColor(), ClickGUI.easingColor.getColor(), a / bib.z().e, 68 + 121 + 16 + 50);
        }
        else {
            color = ClickGUI.clickGUIColor.getColor();
        }
        RenderUtils.injectAlpha(color.getRGB(), (int)this.gradientFactor).getRGB();
        RenderUtils.drawRect(this.x + 2.0f, this.y, this.x + this.width - 2.0f, this.y + this.height, new Color(0x18 ^ 0x12, 0x96 ^ 0x9C, 0x29 ^ 0x23, 16 + 106 - 117 + 195).getRGB());
        CFontManager.manropesmall.drawStringWithShadow(this.option.getName(), this.x + 6.0f, this.y + 3.0f, new Color(245 + 163 - 347 + 194, 64 + 154 - 78 + 115, 97 + 1 + 68 + 89, (int)rk.a(this.gradientFactor + 90.0f, 0.0f, 200.0f)).getRGB());
        this.checkBoxOffset = MathUtils.lerp(this.checkBoxOffset, this.option.enabled ? 11.0f : 19.0f, 0.25f);
        RenderUtils.drawBlurredShadow(this.x + this.width - 20.0f - 1.0f, this.y + 7.0f - 1.0f, 17.0f, 7.0f, 6, this.option.enabled ? RenderUtils.injectAlpha(color.getRGB(), (int)((19.0f - this.checkBoxOffset) / 8.0f * 255.0f)) : new Color(0, 0, 0, 0x7E ^ 0x1A));
        RenderUtils.drawRectWH(this.x + this.width - 20.0f, this.y + 7.0f, 15.0f, 5.0f, color.darker().getRGB());
        RenderUtils.drawBlurredShadow(this.x + this.width - this.checkBoxOffset - 1.0f, this.y + 5.0f - 1.0f, 7.0f, 11.0f, 6, this.option.enabled ? RenderUtils.injectAlpha(color.getRGB(), (int)((19.0f - this.checkBoxOffset) / 8.0f * 255.0f)) : new Color(0, 0, 0, 0x62 ^ 0x6));
        RenderUtils.drawRectWH(this.x + this.width - this.checkBoxOffset, this.y + 5.0f, 5.0f, 9.0f, color.getRGB());
        super.render(n, n2, n3);
    }
}
