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
public class IridiumTheme extends ClickTheme {
    protected NahrFont font;

    protected GuiClick gui;

    public IridiumTheme(GuiClick gui) {
        super("Iridium", 104, 17, gui, false);
        this.gui = gui;
    }

    @Override
    public void renderPanel(Panel panel) {
        if (Objects.isNull(font)) {
            this.font = new NahrFont(new Font("Arial", Font.BOLD, 20), 20);
        }

        panel.setOpenHeight(17);
        panel.setButtonOffset(1.5F);
        panel.setWidth(105);

        RenderUtils.drawBorderedRect(panel.getX(), panel.getY() + 2, panel.getX() + panel.getWidth(), panel.getY() + (panel.isOpen() ? panel.getHeight() : panel.getOpenHeight()), 0.5F, 0xFF676767, 0xDD343434);
        font.drawString(panel.getName(), panel.getX() + 2, panel.getY() + 1.5F, NahrFont.FontType.SHADOW_THIN, 0xFFFFFFFF);

        if (panel.isOpen()) {
            RenderUtils.drawRect(panel.getX() + 2, panel.getY() + panel.getOpenHeight() - 1, panel.getX() + panel.getWidth() - 2, panel.getY() + panel.getOpenHeight() - 0.5F, 0xFF676767);
        }

        RenderUtils.drawBorderedRect(panel.getX() + panel.getWidth() - panel.getOpenHeight() + 3.5F, panel.getY() + 4.0F, panel.getX() + panel.getWidth() - 2.0F, panel.getY() + panel.getOpenHeight() - 2.0F, 0.5F, 0xFF676767, panel.isOpen() ? 0xBB323232 : 0xBB121212);
    }

    @Override
    public void renderButton(String name, boolean enabled, float x, float y, float width, float height, boolean overElement, Element element) {
        if (Objects.isNull(font)) {
            this.font = new NahrFont(new Font("Arial", Font.BOLD, 20), 20);
        }

        element.setWidth(this.getElementWidth());
        element.setHeight(this.getElementHeight());

        RenderUtils.drawBorderedRect(x, y, x + width - 3, y + height, 0.25F, enabled ? 0xFF368CF7 : 0xFF676767, enabled ? 0xDD145495 : 0xDD343434);

        font.drawString(name, x + (width - font.getStringWidth(name)) / 2, y, NahrFont.FontType.SHADOW_THIN, 0xFFFFFFFF);
    }

    @Override
    public void renderSlider(String name, float value, float x, float y, float width, float height, float sliderX, boolean overElement, Element element) {
        if (Objects.isNull(font)) {
            this.font = new NahrFont(new Font("Arial", Font.BOLD, 20), 20);
        }

        element.setWidth(101);
        element.setHeight(this.getElementHeight());

        RenderUtils.drawBorderedRect(x, y + 1, x + element.getWidth(), y + height, 0x801E1E1E, 0xFF212121);
        RenderUtils.drawBorderedRect(x, y + 1, x + sliderX, y + height, 0x801E1E1E, 0xFF5AACEB);

        font.drawString(name, x + 2, y - 1, NahrFont.FontType.SHADOW_THIN, 0xFFFFF0F0);
        font.drawString(value + "", x + element.getWidth() - font.getStringWidth(value + "") - 2, y - 1, NahrFont.FontType.SHADOW_THIN, 0xFFFFF0F0);
    }
}
