package pw.latematt.xiv.ui.clickgui.theme.themes;

import pw.latematt.xiv.ui.clickgui.GuiClick;
import pw.latematt.xiv.ui.clickgui.element.Element;
import pw.latematt.xiv.ui.clickgui.panel.Panel;
import pw.latematt.xiv.ui.clickgui.theme.ClickTheme;
import pw.latematt.xiv.utils.NahrFont;
import pw.latematt.xiv.utils.RenderUtils;

import java.awt.*;
import java.util.Objects;

/**
 * @author Rederpz
 */
public class AvidTheme extends ClickTheme {
    protected NahrFont font;

    protected GuiClick gui;

    public AvidTheme(GuiClick gui) {
        super("Avid", 12, 11, gui, false);
        this.gui = gui;
    }

    @Override
    public void renderPanel(Panel panel) {
        if (Objects.isNull(font)) {
            this.font = new NahrFont(new Font("Verdana", Font.BOLD, 17), 17);
        }

        panel.setOpenHeight(17);
        panel.setButtonOffset(1.5F);
        panel.setWidth(115);

        RenderUtils.drawRect(panel.getX(), panel.getY() + 2, panel.getX() + panel.getWidth(), panel.getY() + panel.getOpenHeight() - 0.5F, 0xFF088FE5);
        font.drawString(panel.getName(), panel.getX() + 3, panel.getY() + 1, NahrFont.FontType.NORMAL, 0xFFFFFFFF);

        if (panel.isOpen()) {
            RenderUtils.drawRect(panel.getX(), panel.getY() + panel.getOpenHeight(), panel.getX() + panel.getWidth(), panel.getY() + panel.getHeight(), 0xBBBEBEBE);
        }

        RenderUtils.drawBorderedGradientRect(panel.getX() + panel.getWidth() - panel.getOpenHeight() + 4.5F, panel.getY() + 4.0F, panel.getX() + panel.getWidth() - 2.0F, panel.getY() + panel.getOpenHeight() - 2.5F, 0xFF000000, panel.isOpen() ? 0xFF6D6D6D : 0xFF08B8FF, panel.isOpen() ? 0xFF515151 : 0xFF08B8FF);
    }

    @Override
    public void renderButton(String name, boolean enabled, float x, float y, float width, float height, boolean overElement, Element element) {
        if (Objects.isNull(font)) {
            this.font = new NahrFont(new Font("Verdana", Font.BOLD, 17), 17);
        }

        element.setWidth(this.getElementWidth());
        element.setHeight(this.getElementHeight());

        RenderUtils.drawBorderedGradientRect(x, y, x + 11, y + height, 0xFF000000, !enabled ? 0xFF6D6D6D : 0xFF08B8FF, !enabled ? 0xFF515151 : 0xFF08B8FF);

        font.drawString(name, x + 13, y - 3, NahrFont.FontType.NORMAL, enabled ? 0xFF08B8FF : 0xFF515151);
    }

    @Override
    public void renderSlider(String name, float value, float x, float y, float width, float height, float sliderX, boolean overElement, Element element) {
        if (Objects.isNull(font)) {
            this.font = new NahrFont(new Font("Verdana", Font.BOLD, 17), 17);
        }

        element.setWidth(112);
        element.setHeight(this.getElementHeight());

        RenderUtils.drawBorderedRect(x, y + 1, x + element.getWidth(), y + height, 0x801E1E1E, 0xFF212121);
        RenderUtils.drawBorderedRect(x, y + 1, x + sliderX, y + height, 0x801E1E1E, 0xFF5AACEB);

        font.drawString(name, x + 2, y - 1, NahrFont.FontType.SHADOW_THIN, 0xFFFFF0F0);
        font.drawString(value + "", x + element.getWidth() - font.getStringWidth(value + "") - 2, y - 1, NahrFont.FontType.SHADOW_THIN, 0xFFFFF0F0);
    }
}
