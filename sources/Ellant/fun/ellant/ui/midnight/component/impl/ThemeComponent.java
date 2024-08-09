package fun.ellant.ui.midnight.component.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.Ellant;
import fun.ellant.ui.midnight.component.ColorThemeWindow;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.font.Fonts;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import net.minecraft.util.math.vector.Vector4f;

import java.awt.*;

public class ThemeComponent extends Component {

    public Style style;
    public ColorThemeWindow[] colors = new ColorThemeWindow[2];
    public static ColorThemeWindow selected = null;

    public boolean opened;


    public ThemeComponent(Style style) {
        this.style = style;
        if (style.getStyleName().equalsIgnoreCase("Кастомный")) {
            colors[0] = new ColorThemeWindow(style.getFirstColor(), new Vector4f(0, 0, 0, 0));
            colors[1] = new ColorThemeWindow(style.getSecondColor(), new Vector4f(0, 0, 0, 0));
        }
    }

    @Override
    public void onConfigUpdate() {
        super.onConfigUpdate();
        for (ColorThemeWindow colorThemeWindow : colors) {
            if (colorThemeWindow != null)
                colorThemeWindow.onConfigUpdate();
        }

        if (style.getStyleName().equalsIgnoreCase("Кастомный")) {
            if (colors.length >= 2) {
                colors[0] = new ColorThemeWindow(style.getFirstColor(), new Vector4f(0, 0, 0, 0));
                colors[1] = new ColorThemeWindow(style.getSecondColor(), new Vector4f(0, 0, 0, 0));
            }
        }

    }

    @Override
    public void drawComponent(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (opened) {
            height += 12;
        }
        if (!(colors[0] == null && colors[1] == null)) {
            style.setFirstColor(new Color(colors[0].getColor()));
            style.setSecondColor(new Color(colors[1].getColor()));
        }


        DisplayUtils.drawShadow(x + 3, y, width - 6, height, 12, new Color(32, 36, 42).darker().darker().getRGB());
        DisplayUtils.drawRoundedRect(x + 3, y, width - 5, height, 4, Ellant.getInstance().getStyleManager().getCurrentStyle() == style ? new Color(32, 36, 42).brighter().getRGB() : new Color(32, 36, 42).getRGB());
        Fonts.gilroy[16].drawString(matrixStack, style.getStyleName(), x + 8, y + 8.5f, -1);

        int color1 = style.getStyleName().contains("Астольфо") ? ColorUtils.astolfo(10, 0, 0.7f, 1, 1) : style.getColor(0);
        int color2 = style.getStyleName().contains("Астольфо") ? ColorUtils.astolfo(10, 90, 0.7f, 1, 1) : style.getColor(90);
        int color3 = style.getStyleName().contains("Астольфо") ? ColorUtils.astolfo(10, 180, 0.7f, 1, 1) : style.getColor(180);
        int color4 = style.getStyleName().contains("Астольфо") ? ColorUtils.astolfo(10, 270, 0.7f, 1, 1) : style.getColor(270);

        DisplayUtils.drawGradientRound(x + width - 25, y + 6, 15, 9, 1, color1, color2, color3, color4);

        if (opened) {
            DisplayUtils.drawRectW(x + 10, y + 19, 8, 8, colors[0].getColor());
            DisplayUtils.drawRectW(x + 10 + 12, y + 19, 8, 8, colors[1].getColor());
        }

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY, 20)) {
            Ellant.getInstance().getStyleManager().setCurrentStyle(style);
        }
        if (isHovered(mouseX, mouseY, 20) && style.getStyleName().equalsIgnoreCase("Кастомный") && mouseButton == 1) {
            opened = !opened;
        }
        if (opened) {
            if (MathUtil.isInRegion(mouseX, mouseY, x + 10, y + 19, 8, 8)) {
                if (selected == colors[0]) {
                    selected = null;
                    return;
                }
                selected = colors[0];
                selected.pos = new Vector4f(mouseX, mouseY, 0, 0);
            }
            if (MathUtil.isInRegion(mouseX, mouseY, x + 10 + 12, y + 19, 8, 8)) {
                if (selected == colors[1]) {
                    selected = null;
                    return;
                }
                selected = colors[1];
                selected.pos = new Vector4f(mouseX, mouseY, 0, 0);
            }
        }
        if (selected != null)
            selected.click(mouseX, mouseY);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (selected != null)
            selected.unclick(mouseX, mouseY);
    }

    @Override
    public void keyTyped(int keyCode, int scanCode, int modifiers) {

    }

    @Override
    public void charTyped(char codePoint, int modifiers) {

    }
}
