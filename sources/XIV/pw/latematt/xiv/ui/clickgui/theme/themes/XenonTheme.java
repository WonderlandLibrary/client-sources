package pw.latematt.xiv.ui.clickgui.theme.themes;

import pw.latematt.xiv.ui.clickgui.GuiClick;
import pw.latematt.xiv.ui.clickgui.element.Element;
import pw.latematt.xiv.ui.clickgui.panel.Panel;
import pw.latematt.xiv.ui.clickgui.theme.ClickTheme;
import pw.latematt.xiv.utils.NahrFont;
import pw.latematt.xiv.utils.RenderUtils;

import java.util.Objects;

/**
 * @author Rederpz
 */
public class XenonTheme extends ClickTheme {
    protected NahrFont font;

    protected GuiClick gui;

    public XenonTheme(GuiClick gui) {
        super("Xenon", 96, 13, gui, false);
        this.gui = gui;
    }

    @Override
    public void renderPanel(Panel panel) {
        if (Objects.isNull(font)) {
            font = new NahrFont("Tahoma", 18);
        }

        panel.setOpenHeight(15);
        panel.setButtonOffset(1.5F);
        panel.setWidth(100);

        RenderUtils.drawBorderedRect(panel.getX(), panel.getY() + 2, panel.getX() + panel.getWidth(), panel.getY() + (panel.isOpen() ? panel.getHeight() : panel.getOpenHeight()), 0xFF000000, 0x66000000);
        font.drawString(panel.getName(), panel.getX() + 3, panel.getY() + 0.5F, NahrFont.FontType.NORMAL, 0xFFFFFFFF);

        if (panel.isOpen()) {
            RenderUtils.drawRect(panel.getX() + 1, panel.getY() + panel.getOpenHeight(), panel.getX() + panel.getWidth() - 1, panel.getY() + panel.getOpenHeight() + 0.5F, 0xFF000000);
        }

        RenderUtils.drawBorderedGradientRect(panel.getX() + panel.getWidth() - panel.getOpenHeight() + 2.5F, panel.getY() + 3.5F, panel.getX() + panel.getWidth() - 1.5F, panel.getY() + panel.getOpenHeight() - 1.5F, 0xFF000000, 0xFF212121, 0xFF212121);
        font.drawString(panel.isOpen() ? "-" : "+", panel.getX() + panel.getWidth() - (panel.isOpen() ? 8.5F : 10.25F), panel.getY() + (panel.isOpen() ? 1 : 0), NahrFont.FontType.NORMAL, 0xFFFFFFFF);
    }

    @Override
    public void renderButton(String name, boolean enabled, float x, float y, float width, float height, boolean overElement, Element element) {
        if (Objects.isNull(font)) {
            font = new NahrFont("Tahoma", 18);
        }

        element.setWidth(this.getElementWidth());
        element.setHeight(this.getElementHeight());

        RenderUtils.drawBorderedGradientRect(x, y, x + 96, y + height, 0xFF000000, enabled ? 0xFFA70400 : 0xFF232323, enabled ? 0xFF7E0001 : 0xFF212121);

        font.drawString(name, x + 2, y - 1, NahrFont.FontType.NORMAL, 0xFFFFFFFF);
    }

    @Override
    public void renderSlider(String name, float value, float x, float y, float width, float height, float sliderX, boolean overElement, Element element) {
        if (Objects.isNull(font)) {
            font = new NahrFont("Tahoma", 18);
        }

        element.setWidth(96);
        element.setHeight(this.getElementHeight());

        RenderUtils.drawBorderedRect(x, y + 1, x + element.getWidth(), y + height, 0x801E1E1E, 0xFF212121);
        RenderUtils.drawBorderedRect(x, y + 1, x + sliderX, y + height, 0x801E1E1E, 0xFF5AACEB);

        font.drawString(name, x + 2, y - 1, NahrFont.FontType.SHADOW_THIN, 0xFFFFF0F0);
        font.drawString(value + "", x + element.getWidth() - font.getStringWidth(value + "") - 2, y - 1, NahrFont.FontType.SHADOW_THIN, 0xFFFFF0F0);
    }
}
