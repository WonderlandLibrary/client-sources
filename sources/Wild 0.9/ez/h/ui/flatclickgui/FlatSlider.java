package ez.h.ui.flatclickgui;

import ez.h.ui.clickgui.options.*;
import java.text.*;
import java.awt.*;
import ez.h.utils.*;
import ez.h.ui.clickgui.element.*;
import ez.h.ui.fonts.*;

public class FlatSlider extends FlatElement
{
    public boolean isDragging;
    public float clamp;
    public OptionSlider option;
    
    @Override
    public void render(int n, final int n2, final float n3) {
        final CFontRenderer manropesmall = CFontManager.manropesmall;
        n -= (int)this.x;
        this.height = 14.0f;
        new StringBuilder().append(((this.option.slidertype == OptionSlider.SliderType.MS || this.option.slidertype == OptionSlider.SliderType.NULLINT || this.option.getNum() % 1.0f == 0.0f) ? new DecimalFormat("##") : new DecimalFormat("##.##")).format(this.option.getNum())).append((this.option.slidertype == OptionSlider.SliderType.NULL || this.option.slidertype == OptionSlider.SliderType.NULLINT) ? "" : this.option.slidertype).toString();
        if (this.isDragging) {
            this.clamp = rk.a((n - (0x40 ^ 0x72)) / (this.width / 2.0f), 0.0f, 1.0f);
            final double n4 = this.option.min + this.clamp * (this.option.max - this.option.min);
            this.option.setNum((this.option.slidertype == OptionSlider.SliderType.MS) ? ((float)(int)n4) : ((float)n4));
        }
        this.clamp = rk.a(MathUtils.lerp(this.clamp, (this.option.getNum() - this.option.min) / (this.option.max - this.option.min), 0.1f), 0.0f, 1.0f);
        manropesmall.drawString(this.option.getName(), this.x + 6.0f, this.y + 2.0f, new Color(165 + 248 - 226 + 68, 114 + 43 + 3 + 95, 189 + 2 + 11 + 53, 12 + 108 - 106 + 136).getRGB());
        final float n5 = (float)manropesmall.getStringWidth(this.option.getNum() + "");
        RenderUtils.drawRectWH(this.x + manropesmall.getStringWidth(this.option.getName() + "") + 20.0f, this.y + 8.0f, 75.0f, 1.0f, new Color(-1).darker().darker().getRGB());
        ElementColor.drawCircle(this.x + manropesmall.getStringWidth(this.option.getName() + "") + 20.0f + this.clamp * 75.0f, this.y + 8.5, 2.5, new Color(4386017 + 12650730 - 15765977 + 14519550).getRGB());
        CFontManager.manropesmall.drawString(this.option.getNum() + "", this.x + this.width - manropesmall.getStringWidth(this.option.getNum() + "") - 4.0f, this.y + 2.0f, new Color(188 + 120 - 217 + 164, 76 + 16 - 81 + 244, 93 + 238 - 318 + 242, 19 + 80 - 4 + 55).getRGB());
        super.render(n, n2, n3);
    }
    
    @Override
    public void mouseRealesed(final int n, final int n2, final int n3) {
        this.isDragging = false;
        super.mouseRealesed(n, n2, n3);
    }
    
    public FlatSlider(final OptionSlider optionSlider) {
        this.option = optionSlider;
        this.height = 14.0f;
        this.clamp = optionSlider.getNum() / optionSlider.max;
        this.displayOption = optionSlider;
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (FlatElement.isHover((float)n, (float)n2, this.x + CFontManager.manropesmall.getStringWidth(this.option.getName() + "") + 20.0f, this.y + 2.0f, 75.0f, this.height) && n3 == 0) {
            this.isDragging = true;
        }
        super.mouseClicked(n, n2, n3);
    }
}
