package ez.h.ui.clickgui.element;

import ez.h.ui.clickgui.options.*;
import ez.h.ui.clickgui.*;
import ez.h.features.visual.*;
import java.awt.*;
import ez.h.ui.fonts.*;
import java.text.*;
import ez.h.utils.*;

public class ElementSlider extends Element
{
    public float clamp;
    ElementButton button;
    public OptionSlider option;
    public boolean isDragging;
    Panel panel;
    
    public ElementSlider(final Panel panel, final OptionSlider optionSlider, final ElementButton button) {
        super(panel);
        this.panel = panel;
        this.button = button;
        this.option = optionSlider;
        this.height = 18.0f;
        this.clamp = optionSlider.getNum() / optionSlider.max;
        this.displayOption = optionSlider;
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (this.isHover((float)n, (float)n2, this.x + 2.0f, this.y, this.width, this.height) && n3 == 0) {
            this.isDragging = true;
        }
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void mouseRealesed(final int n, final int n2, final int n3) {
        this.isDragging = false;
        super.mouseRealesed(n, n2, n3);
    }
    
    @Override
    public void render(int n, final int n2, final float n3) {
        n -= (int)this.x;
        final float a = rk.a(this.y * 2.0f, 0.0f, (float)bib.z().e);
        Color color;
        if (ClickGUI.easing.enabled) {
            color = Utils.getGradientOffset(ClickGUI.clickGUIColor.getColor(), ClickGUI.easingColor.getColor(), a / bib.z().e, 55 + 29 + 154 + 17);
        }
        else {
            color = ClickGUI.clickGUIColor.getColor();
        }
        RenderUtils.drawRect(this.x + 2.0f, this.y, this.x + this.width - 2.0f, this.y + this.height, new Color(0x12 ^ 0x18, 0x19 ^ 0x13, 0x4A ^ 0x40, 32 + 21 + 70 + 77).getRGB());
        CFontManager.manropesmall.drawStringWithShadow(this.option.getName(), this.x + 5.0f, this.y, new Color(44 + 119 - 0 + 92, 64 + 113 - 88 + 166, 162 + 125 - 275 + 243, 14 + 43 + 30 + 63).getRGB());
        final String string = ((this.option.slidertype == OptionSlider.SliderType.MS || this.option.slidertype == OptionSlider.SliderType.NULLINT || this.option.getNum() % 1.0f == 0.0f) ? new DecimalFormat("##") : new DecimalFormat("##.##")).format(this.option.getNum()) + ((this.option.slidertype == OptionSlider.SliderType.NULL || this.option.slidertype == OptionSlider.SliderType.NULLINT) ? "" : this.option.slidertype);
        CFontManager.manropesmall.drawCenteredString(string, this.x + this.width - CFontManager.manropesmall.getStringWidth(string) / 2.0f - 4.0f, this.y, new Color(183 + 170 - 188 + 90, 30 + 103 - 69 + 191, 79 + 138 - 4 + 42, 112 + 56 - 51 + 33).getRGB());
        if (this.isDragging) {
            this.clamp = rk.a(n / this.width, 0.0f, 1.0f);
            final double n4 = this.option.min + this.clamp * (this.option.max - this.option.min);
            this.option.setNum((this.option.slidertype == OptionSlider.SliderType.MS) ? ((float)(int)n4) : ((float)n4));
        }
        this.clamp = rk.a(MathUtils.lerp(this.clamp, (this.option.getNum() - this.option.min) / (this.option.max - this.option.min), 0.1f), 0.0f, 1.0f);
        RenderUtils.drawRoundedRect(this.x + 5.0f, this.y + this.height / 2.0f + 3.0f, this.width - 10.0f, 2.0, 2.0, Integer.MIN_VALUE);
        RenderUtils.injectAlpha(color.getRGB(), 0).getRGB();
        color.getRGB();
        if (this.x + this.clamp * this.width - 4.0f > this.x + 4.0f) {
            RenderUtils.drawBlurredShadow(this.x + 4.0f - 1.0f, this.y + this.height / 2.0f + 3.0f - 1.0f, this.clamp * this.width - 8.0f + 2.0f, 4.0f, 6, color.brighter());
            RenderUtils.drawRoundedRect(this.x + 5.0f, this.y + this.height / 2.0f + 3.0f, this.clamp * this.width - 10.0f, 2.0, this.clamp * 2.0f, color.getRGB());
        }
        super.render(n, n2, n3);
    }
}
