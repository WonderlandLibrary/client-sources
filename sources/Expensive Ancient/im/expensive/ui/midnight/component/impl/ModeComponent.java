package im.expensive.ui.midnight.component.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.functions.settings.impl.ModeSetting;
import im.expensive.utils.font.Fonts;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.DisplayUtils;

import java.awt.*;
import java.util.HashMap;

public class ModeComponent extends Component {

    public ModeSetting option;

    public boolean opened;
    public HashMap<String, Float> animation = new HashMap<>();

    public ModeComponent(ModeSetting option) {
        this.option = option;
        for (String s : option.strings) {
            animation.put(s, 0f);
        }
        this.setting = option;
    }

    @Override
    public void drawComponent(MatrixStack matrixStack, int mouseX, int mouseY) {
        float off = 4;
        float offset = 17 - 8;
        for (String s : option.strings) {
            offset += 9;
        }
        if (!opened) offset = 0;
        Fonts.gilroy[14].drawString(matrixStack, option.getName(), x + 5, y + 3, -1);

        off += Fonts.gilroy[14].getFontHeight() / 2f + 2;
        height += offset + 7;
        DisplayUtils.drawShadow(x + 5, y + off, width - 10, 20 - 6, 10, new Color(26, 29, 33, 50).getRGB());
        DisplayUtils.drawRoundedRect(x + 5, y + off, width - 10, 20 - 6, 4, new Color(26, 29, 33).getRGB());
        DisplayUtils.drawShadow(x + 5, y + off + 17, width - 10, offset, 12, new Color(0, 0, 0, 100).getRGB());
        DisplayUtils.drawRoundedRect(x + 5, y + off + 17, width - 10, offset, 4, new Color(17, 18, 21).getRGB());
        Fonts.gilroy[14].drawString(matrixStack, option.get(), x + 10, y + 20 - 4, -1);
        if (opened) {
            int i = 1;
            for (String s : option.strings) {
                boolean hovered = MathUtil.isInRegion(mouseX, mouseY, x, y + off + 20 + i, width, 8);
                animation.put(s, MathUtil.lerp(animation.get(s), hovered ? 2 : 0, 10));
                Fonts.gilroy[14].drawString(matrixStack, s, x + 9 + animation.get(s), y + off + 23.5F + i, option.get().equals(s) ? new Color(74, 166, 218).getRGB() : new Color(163, 176, 188).getRGB());
                i += 9;
            }
            height += 3;
        }

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        float off = 3;
        off += Fonts.gilroy[14].getFontHeight() / 2f + 2;
        if (MathUtil.isInRegion(mouseX, mouseY, x + 5, y + off, width - 10, 20 - 5)) {
            opened = !opened;
        }


        if (!opened) return;
        int i = 1;
        for (String s : option.strings) {
            if (MathUtil.isInRegion(mouseX, mouseY, x, y + off + 20F + i, width, 8))
                option.set(s);
            i += 9;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void keyTyped(int keyCode, int scanCode, int modifiers) {

    }

    @Override
    public void charTyped(char codePoint, int modifiers) {

    }
}
