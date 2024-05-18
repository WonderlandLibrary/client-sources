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
public class PringlesTheme extends ClickTheme {
    protected NahrFont font;

    protected GuiClick gui;

    public PringlesTheme(GuiClick gui) {
        super("Pringles", 96, 15, gui, false);
        this.gui = gui;
    }

    @Override
    public void renderPanel(Panel panel) {
        if (Objects.isNull(font)) {
            font = new NahrFont("Tahoma", 18);
        }

        panel.setOpenHeight(17);
        panel.setButtonOffset(1.5F);
        panel.setWidth(100);

        RenderUtils.drawBorderedGradientRect(panel.getX(), panel.getY() + 2, panel.getX() + panel.getWidth(), panel.getY() + panel.getOpenHeight(), 0xFF000000, 0xDD082D6C, 0xDD0A0673);
        font.drawString(panel.getName().toUpperCase(), panel.getX() + 3, panel.getY() + 1.5F, NahrFont.FontType.NORMAL, 0xFFFFFFFF);

        if (panel.isOpen()) {
            RenderUtils.drawBorderedRect(panel.getX(), panel.getY() + panel.getOpenHeight(), panel.getX() + panel.getWidth(), panel.getY() + panel.getHeight(), 0xFF000000, 0xBB000000);
        }

        RenderUtils.drawBorderedRect(panel.getX() + panel.getWidth() - panel.getOpenHeight() + 3.5F, panel.getY() + 4.0F, panel.getX() + panel.getWidth() - 2.0F, panel.getY() + panel.getOpenHeight() - 2.0F, 0xFF000000, panel.isOpen() ? 0x00000000 : 0x22FFFFFF);
    }

    @Override
    public void renderButton(String name, boolean enabled, float x, float y, float width, float height, boolean overElement, Element element) {
        if (Objects.isNull(font)) {
            font = new NahrFont("Tahoma", 18);
        }

        element.setWidth(this.getElementWidth());
        element.setHeight(this.getElementHeight());

        RenderUtils.drawBorderedRect(x, y, x + 96, y + height, 0xFF000000, enabled ? 0x330066FF : 0x00000000);

        font.drawString(name, x + 2, y, NahrFont.FontType.SHADOW_THIN, enabled ? 0xFF4279B2 : 0xFFFFFFFF);
    }

    @Override
    public void renderSlider(String name, float value, float x, float y, float width, float height, float sliderX, boolean overElement, Element element) {
        if (Objects.isNull(font)) {
            font = new NahrFont("Tahoma", 18);
        }

        element.setWidth(96);
        element.setHeight(this.getElementHeight());

        //RenderUtils.drawBorderedRect(x, y + 1, x + element.getWidth(), y + height, 0x801E1E1E, 0xFF212121);
        RenderUtils.drawRect(x, y + 1, x + sliderX, y + height, 0x330066FF);

        font.drawString(name, x + 2, y - 1, NahrFont.FontType.SHADOW_THIN, 0xFFFFFFFF);
        font.drawString(value + "", x + element.getWidth() - font.getStringWidth(value + "") - 2, y - 1, NahrFont.FontType.SHADOW_THIN, 0xFFFFFFFF);
    }
}
