package pw.latematt.xiv.ui.clickgui.theme.themes;

import org.lwjgl.input.Mouse;
import pw.latematt.xiv.ui.clickgui.GuiClick;
import pw.latematt.xiv.ui.clickgui.element.Element;
import pw.latematt.xiv.ui.clickgui.element.elements.ModButton;
import pw.latematt.xiv.ui.clickgui.element.elements.ValueSlider;
import pw.latematt.xiv.ui.clickgui.panel.Panel;
import pw.latematt.xiv.ui.clickgui.theme.ClickTheme;
import pw.latematt.xiv.utils.NahrFont;
import pw.latematt.xiv.utils.RenderUtils;

import java.util.Objects;

/**
 * @author Rederpz
 */
public class DebugTheme extends ClickTheme {
    protected NahrFont font;

    protected GuiClick gui;

    public DebugTheme(GuiClick gui) {
        super("Debug", 96, 13, gui, true);
        this.gui = gui;
    }

    @Override
    public void renderPanel(Panel panel) {
        if (Objects.isNull(font)) {
            font = new NahrFont("Comic Sans MS", 18);
        }

        panel.setOpenHeight(15);
        panel.setButtonOffset(1.5F);
        panel.setWidth(100);

        RenderUtils.drawBorderedRect(panel.getX(), panel.getY() + 2, panel.getX() + panel.getWidth(), panel.getY() + (panel.isOpen() ? panel.getHeight() : panel.getOpenHeight()), 0xFF000000, 0x66000000);
        font.drawString(panel.getName(), panel.getX() + 3, panel.getY(), NahrFont.FontType.NORMAL, 0xFFFFFFFF);

        if (panel.isOpen()) {
            RenderUtils.drawRect(panel.getX() + 1, panel.getY() + panel.getOpenHeight(), panel.getX() + panel.getWidth() - 1, panel.getY() + panel.getOpenHeight() + 0.5F, 0xFF000000);
        }

        RenderUtils.drawBorderedGradientRect(panel.getX() + panel.getWidth() - panel.getOpenHeight() + 2.5F, panel.getY() + 3.5F, panel.getX() + panel.getWidth() - 1.5F, panel.getY() + panel.getOpenHeight() - 1.5F, 0xFF000000, 0xFF212121, 0xFF212121);
        font.drawString(panel.isOpen() ? "^" : "v", panel.getX() + panel.getWidth() - (panel.isOpen() ? 9.75F : 9.50F), panel.getY() + (panel.isOpen() ? 1 : -2), NahrFont.FontType.NORMAL, 0xFFFFFFFF);
    }

    @Override
    public void renderButton(String name, boolean enabled, float x, float y, float width, float height, boolean overElement, Element element) {
        if (Objects.isNull(font)) {
            font = new NahrFont("Comic Sans MS", 18);
        }

        element.setWidth(this.getElementWidth());
        element.setHeight(this.getElementHeight());

        RenderUtils.drawBorderedGradientRect(x, y, x + 96, y + getElementHeight(), 0xFF000000, enabled ? 0xFF5AACEB : 0xFF232323, enabled ? 0xFF1466A5 : 0xFF212121);

        font.drawString(name, x + 2, y - 3, NahrFont.FontType.NORMAL, 0xFFFFFFFF);
        if (element instanceof ModButton) {
            ModButton butt = (ModButton) element;

            if (butt.elements.size() > 0) {
                font.drawString(butt.open ? "-" : "+", x + element.getWidth() - 8, y - 3, NahrFont.FontType.NORMAL, 0xFFFFFFFF);

                if (butt.open) {
                    float elementHeight = element.getHeight();

                    for (Element elem : butt.elements) {
                        elementHeight += elem.getHeight();
                    }

                    float elemY = y + 1;
                    for (Element elem : butt.elements) {
                        elem.setX(x);
                        if (elem instanceof ValueSlider) {
                            elem.setY(elemY += elem.getHeight());
                            elemY += 1;
                        } else {
                            elem.setY(elemY += elem.getHeight());
                        }

                        int var141 = RenderUtils.newScaledResolution().getScaledWidth();
                        int var151 = RenderUtils.newScaledResolution().getScaledHeight();
                        final int var161 = Mouse.getX() * var141 / this.mc.displayWidth;
                        final int var171 = var151 - Mouse.getY() * var151 / this.mc.displayHeight - 1;

                        elem.drawElement(var161, var171);
                    }

                    element.setHeight(elementHeight + 1);
                }
            }
        }
    }

    @Override
    public void renderSlider(String name, float value, float x, float y, float width, float height, float sliderX, boolean overElement, Element element) {
        if (Objects.isNull(font)) {
            font = new NahrFont("Comic Sans MS", 18);
        }

        element.setWidth(96);
        element.setHeight(this.getElementHeight());

        RenderUtils.drawBorderedRect(x, y + 1, x + element.getWidth(), y + height, 0x801E1E1E, 0xFF212121);
        RenderUtils.drawGradientRect(x, y + 1, x + sliderX, y + height, 0xFF5AACEB, 0xFF1466A5);

        font.drawString(name, x + 2, y - 1, NahrFont.FontType.SHADOW_THIN, 0xFFFFF0F0);
        font.drawString(value + "", x + element.getWidth() - font.getStringWidth(value + "") - 2, y - 1, NahrFont.FontType.SHADOW_THIN, 0xFFFFF0F0);
    }
}
