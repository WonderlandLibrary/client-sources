package ez.h.ui.flatclickgui;

import ez.h.ui.clickgui.options.*;
import ez.h.ui.fonts.*;
import java.awt.*;
import ez.h.utils.*;
import org.lwjgl.input.*;

public class FlatMode extends FlatElement
{
    int textAlpha;
    OptionMode option;
    boolean isOpen;
    float animHeight;
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.height = 14.0f;
        this.textAlpha = (int)MathUtils.lerp((float)this.textAlpha, this.isOpen ? 255.0f : 0.0f, this.isOpen ? 0.05f : 0.1f);
        final float n4 = (float)(this.option.getValues().length * (CFontManager.manropesmall.FONT_HEIGHT * 0.9) + 12.0);
        this.animHeight = MathUtils.lerp(this.animHeight, this.isOpen ? n4 : 10.0f, this.isOpen ? (7.0f / bib.af()) : (10.0f / bib.af()));
        this.height = this.animHeight + 5.0f;
        RenderUtils.drawRoundedRect(this.x + this.width - 50.0f - 2.0f, this.y + 2.0f, 48.0, this.animHeight, 6.0, new Color(0x71 ^ 0x54, 0x42 ^ 0x67, 0xB1 ^ 0x94, 244 + 89 - 152 + 74).getRGB());
        RenderUtils.drawRoundedRect(this.x + this.width - 50.0f - 2.0f, this.y + 2.0f, 48.0, 10.0, 6.0, new Color(0x9E ^ 0xA8, 0x5F ^ 0x69, 0x3F ^ 0x9, 101 + 23 + 123 + 8).getRGB());
        CFontManager.manropesmall.drawScaledString(this.option.getMode() + "", this.x + this.width - 27.0f - 0.85f * (CFontManager.manropesmall.getStringWidth(this.option.getMode()) / 2.0f), this.y + 2.0f, -1, 0.85f, false);
        if (this.isOpen) {
            float n5 = CFontManager.manropesmall.FONT_HEIGHT * 0.8f - 1.0f;
            for (final String mode : this.option.getValues()) {
                final boolean hover = FlatElement.isHover((float)n, (float)n2, this.x + this.width - 26.0f - 0.9f * (CFontManager.manropesmall.getStringWidth(mode) / 2.0f), this.y + 4.5f + n5, 50.0f, 9.0f);
                if (hover && Mouse.isButtonDown(0)) {
                    this.option.setMode(mode);
                    this.isOpen = false;
                }
                CFontManager.manropesmall.drawScaledString(mode + "", this.x + this.width - 26.0f - 0.9f * (CFontManager.manropesmall.getStringWidth(mode) / 2.0f), this.y + 4.5f + n5, hover ? new Color(39 + 198 - 90 + 108, 215 + 75 - 166 + 131, 150 + 44 - 19 + 80, this.textAlpha).darker().getRGB() : new Color(245 + 40 - 197 + 167, 206 + 122 - 257 + 184, 13 + 17 + 93 + 132, this.textAlpha).getRGB(), 0.9f, false);
                n5 += CFontManager.manropesmall.FONT_HEIGHT * 0.9f;
            }
        }
        CFontManager.manropesmall.drawString(this.displayOption.getName(), this.x + 6.0f, this.y + 2.0f, new Color(55 + 126 - 48 + 122, 153 + 209 - 225 + 118, 138 + 154 - 142 + 105, 44 + 132 - 86 + 60).getRGB());
        super.render(n, n2, n3);
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (FlatElement.isHover((float)n, (float)n2, this.x + this.width - 50.0f - 2.0f, this.y + 2.0f, 48.0f, 10.0f) && n3 == 0) {
            this.isOpen = !this.isOpen;
        }
        super.mouseClicked(n, n2, n3);
    }
    
    public FlatMode(final OptionMode optionMode) {
        this.animHeight = 10.0f;
        this.displayOption = optionMode;
        this.option = optionMode;
    }
}
