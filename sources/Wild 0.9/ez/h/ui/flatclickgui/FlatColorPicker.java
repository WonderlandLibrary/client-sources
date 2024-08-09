package ez.h.ui.flatclickgui;

import ez.h.ui.clickgui.options.*;
import ez.h.ui.fonts.*;
import java.awt.*;
import ez.h.utils.*;

public class FlatColorPicker extends FlatElement
{
    OptionColor option;
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.height = 14.0f;
        CFontManager.manropesmall.drawString(this.displayOption.getName(), this.x + 6.0f, this.y + 2.0f, new Color(32 + 246 - 119 + 96, 185 + 133 - 196 + 133, 69 + 76 - 113 + 223, 134 + 48 - 41 + 9).getRGB());
        bus.I();
        RenderUtils.drawBlurredShadow(this.x + this.width - 24.0f, this.y + 3.5f, 22.0f, 11.0f, 0x24 ^ 0x2E, this.option.getColor());
        RenderUtils.drawRectWH(this.x + this.width - 22.0f, this.y + 5.5f, 18.0f, 7.0f, this.option.getColor().getRGB());
        super.render(n, n2, n3);
    }
    
    public FlatColorPicker(final OptionColor optionColor) {
        this.option = optionColor;
        this.displayOption = optionColor;
        this.height = 14.0f;
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (FlatElement.isHover((float)n, (float)n2, this.x + this.width - 22.0f, this.y + 5.5f, 18.0f, 7.0f) && n3 == 0) {
            ColorPicker.currentOption = this.option;
            ColorPicker.isOpen = true;
            ColorPicker.fadeCounter = bib.af() * 5;
        }
        super.mouseClicked(n, n2, n3);
    }
}
