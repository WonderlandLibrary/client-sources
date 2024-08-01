package wtf.diablo.client.gui.clickgui.dropdown.impl.component.setting.impl;

import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;
import wtf.diablo.client.font.FontHandler;
import wtf.diablo.client.font.TTFFontRenderer;
import wtf.diablo.client.gui.clickgui.dropdown.impl.component.ModulePanelComponent;
import wtf.diablo.client.gui.clickgui.dropdown.impl.component.setting.api.ISettingComponent;
import wtf.diablo.client.setting.impl.ColorSetting;
import wtf.diablo.client.util.render.ColorUtil;
import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;

public final class ColorSettingComponent implements ISettingComponent {
    private static final Color BACKGROUND_COLOR = new Color(19, 19, 19, 255);
    private final ModulePanelComponent modulePanelComponent;
    private final ColorSetting setting;
    private final int height;

    private boolean expanded;


    public ColorSettingComponent(final ColorSetting setting, final ModulePanelComponent modulePanelComponent) {
        this.setting = setting;
        this.modulePanelComponent = modulePanelComponent;
        this.height = 16;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        final int x = modulePanelComponent.getParent().getX();
        final int y = modulePanelComponent.getParent().getY() + modulePanelComponent.getParent().getPanelY();

        RenderUtil.drawRoundedRectangle(x, y, modulePanelComponent.getParent().getWidth(), expanded ? height + 80 : height, 0, 0, 0, 0, ColorUtil.SETTING_BACKGROUND_COLOR.getValue().darker().getRGB());
        RenderUtil.drawRoundedRectangle(x + 2, y, modulePanelComponent.getParent().getWidth() - 4, height + 2, 0, ColorUtil.SETTING_BACKGROUND_COLOR.getValue().getRGB());

        final int colorWidth = 12;
        final int colorHeight = 8;
        final int offsetX = 95;
        final int offsetY = 3;

        RenderUtil.drawBorderRect(x + offsetX - 1, y + offsetY, colorWidth, colorHeight, 1, -1, Color.BLACK.getRGB());
        RenderUtil.drawRect(x + offsetX, y + offsetY + 1, colorWidth - 2, colorHeight - 2, setting.getValue().getRGB());

        final TTFFontRenderer font = FontHandler.fetch("outfitregular 17");

        font.drawString(setting.getName(), x + colorWidth - 10, y + offsetY - 1, ColorUtil.SETTING_TEXT_COLOR.getValue().getRGB());

        if (expanded) {
            final int expandedWidth = 86;
            final int expandedHeight = 96;
            final int expandedX = x - expandedWidth + 92;
            final int expandedY = y;

           // RenderUtil.drawBorderRect(expandedX, expandedY, expandedWidth, expandedHeight, 1, ColorUtil.SETTING_BACKGROUND_COLOR.getValue().getRGB(), Color.BLACK.getRGB());

            final float[] hsb = Color.RGBtoHSB(setting.getValue().getRed(), setting.getValue().getGreen(), setting.getValue().getBlue(), null);

            final int hueWidth = 96;
            final int hueHeight = 8;

            drawHueRect(expandedX, y + expandedHeight - hueHeight - 4, hueWidth, hueHeight);
            //RenderUtil.drawBorderRect(expandedX + 4, y + expandedHeight - hueHeight - 4, hueWidth, hueHeight, 1, 0, Color.BLACK.getRGB());

            if (Mouse.isButtonDown(0) && RenderUtil.isHovered(mouseX, mouseY, expandedX, y + expandedHeight - hueHeight, expandedX + hueWidth, y + expandedHeight)) {
                final float hue = (mouseX - (expandedX + 6)) / (float) hueWidth;
                setting.setValue(new Color(Color.HSBtoRGB(hue, hsb[1], hsb[2])));
            }

            RenderUtil.drawRect(expandedX + (int) (hueWidth * hsb[0]), y + expandedHeight - hueHeight - 4, 2, hueHeight, Color.WHITE.getRGB());

            final int palletWidth = 96;
            final int palletHeight = 56;

            draw2DColorPalette(hsb[0], expandedX, y + expandedHeight - hueHeight - 4 - palletHeight - 4, palletWidth, palletHeight);
          //  RenderUtil.drawBorderRect(expandedX + 4, y + expandedHeight - hueHeight - 4 - palletHeight - 4, palletWidth, palletHeight, 1, 0, Color.BLACK.getRGB());

            if (Mouse.isButtonDown(0) && RenderUtil.isHovered(mouseX, mouseY, expandedX + 4, y + expandedHeight - hueHeight - 4 - palletHeight - 4, expandedX + 6 + palletWidth, y + expandedHeight - hueHeight - 6)) {
                final float saturation = (mouseX - (expandedX + 4)) / (float) palletWidth;
                final float brightness = 1.0F - ((mouseY - (y + expandedHeight - hueHeight - 4 - palletHeight - 4)) / (float) palletHeight);
                setting.setValue(new Color(Color.HSBtoRGB(hsb[0], saturation, brightness)));
            }

            RenderUtil.drawRect(expandedX + 3 + (int) (palletWidth * hsb[1]) - 1, y + expandedHeight - hueHeight - 8 - (int) (palletHeight * (hsb[2])) - 1, 2, 2, Color.WHITE.getRGB());
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        final int x = modulePanelComponent.getParent().getX();
        final int y = modulePanelComponent.getParent().getY() + modulePanelComponent.getPanelY();

        final int colorWidth = 12;
        final int colorHeight = 8;
        final int offsetX = 95;
        final int offsetY = 5;
        if (RenderUtil.isHovered(mouseX, mouseY, x + offsetX, y + offsetY, x + offsetX + colorWidth, y + offsetY + colorHeight)) {
            expanded = !expanded;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public int getHeight() {
        return expanded ? height + 80 : height;
    }

    public static void draw2DColorPalette(float hue, double left, double top, double width, double height) {
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                Gui.drawRect((int) (left + i), (int) (top + height - j), (int) (left + i + 1),
                        (int) (top + height - j + 1),
                        Color.HSBtoRGB(hue, (float) i / (float) width, (float) j / (float) height));
    }

    public static void drawHueRect(double left, double top, double width, double height) {
        for (int i = 0; i < width; i++)
            Gui.drawRect((int) left + i, (int) top, (int) left + i + 1, (int) (top + height),
                    Color.HSBtoRGB((float) ((360.f / width) * i / 360.f), 1, 1));
    }
}
