package me.protocol_client.ui.click.Protocol.theme.themes;

import java.text.DecimalFormat;

import org.lwjgl.opengl.GL11;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.ui.click.Protocol.GuiClick;
import me.protocol_client.ui.click.Protocol.Elements.Element;
import me.protocol_client.ui.click.Protocol.Elements.moreElements.ModStuff;
import me.protocol_client.ui.click.Protocol.Elements.moreElements.ValueSlider;
import me.protocol_client.ui.click.Protocol.Panel.Panel;
import me.protocol_client.ui.click.Protocol.theme.ClickTheme;
import me.protocol_client.utils.RenderUtils2D;
import net.minecraft.client.gui.Gui;

public class NodusTheme extends ClickTheme {

    protected GuiClick gui;

    public NodusTheme(GuiClick gui) {
        super("Nodus", 96, 13, gui, true);
        this.gui = gui;
    }

    @Override
    public void renderPanel(Panel panel) {
        panel.setOpenHeight(15);
        panel.setButtonOffset(1.5F);
        panel.setWidth(100);
        int linesColor = 553648127;
		int bodyColor = 0x80000000;
		String mc = panel.getName();
        Wrapper.drawBorderRect(panel.getX(), panel.getY() + 2, panel.getX() + panel.getWidth(), panel.getY() + 18, linesColor, 0xcc101010, 2);
        Gui.drawRect(panel.getX() + 2, panel.getY() + 18, panel.getX() + panel.getWidth() - 2, panel.getY() + (panel.isOpen() ? panel.getHeight() - 2 : 18), bodyColor);
        Gui.drawRect(panel.getX(), panel.getY() + 18, panel.getX() + 2, panel.getY() + (panel.isOpen() ? panel.getHeight() - 2 : 18), linesColor);
        Gui.drawRect(panel.getX() + panel.getWidth() - 2, panel.getY() + 18, panel.getX() + panel.getWidth(), panel.getY() + (panel.isOpen() ? panel.getHeight() - 2 : 18), linesColor);
        Gui.drawRect(panel.getX(), panel.getY() + (panel.isOpen() ? panel.getHeight() - 2 : 18), panel.getX() + panel.getWidth(), panel.getY() + (panel.isOpen() ? panel.getHeight() : 18), linesColor);
        
        
        Gui.drawString(Wrapper.fr(), Protocol.primColor + mc.toString().substring(0, 1) + mc.toString().substring(1, mc.toString().length()).toLowerCase() + Protocol.secColor + " (" + panel.getElements().size() + ")", (int)(panel.getX() + 3), (int)(panel.getY() + 6F), 0xFFFFFFFF);

          Wrapper.drawBorderRect(panel.getX() + panel.getWidth() - 13, panel.getY() + 5, panel.getX() + panel.getWidth() - 3, panel.getY() + panel.getOpenHeight() - 0, linesColor, panel.isOpen() ? 0xFF000000 : bodyColor, 1);

    }

    @Override
    public void renderButton(String name, boolean enabled, float x, float y, float width, float height, boolean overElement, Element element) {
        element.setWidth(this.getElementWidth());
        element.setHeight(this.getElementHeight());
        int linesColor = 553648127;
		int bodyColor = 0x65000000;
        //Wrapper.drawBorderRect(x, y, x + 96, y + getElementHeight(), 0xFF000000, enabled ? Protocol.getColor().getRGB() : 0xFF232323, 1);
        Gui.drawString(Wrapper.fr(), enabled ? Protocol.primColor + name : Protocol.secColor + name, (int)(x + 2), (int)(y + 3), 0xFFFFFFFF);
        if (element instanceof ModStuff) {
            ModStuff butt = (ModStuff) element;

            if (butt.elements.size() > 0) {
               Gui.drawString(Wrapper.fr(), butt.open ? "<<" : ">>", (int)(x + element.getWidth() - 10), (int)(y + 3), 0xFFFFFFFF);
              // Wrapper.drawBorderRect(butt.getX() + butt.getWidth() - 13, butt.getY() + 2, butt.getX() + butt.getWidth() - 3, butt.getY() + 12, linesColor, butt.open ? 0xFF000000 : bodyColor, 1);
                if (butt.open) {
                    float elementHeight = element.getHeight();
                    Gui.drawRect(x, y + 14, x + 96, y + 15, linesColor);
                    for (Element elem : butt.elements) {
                        elementHeight += elem.getHeight() + 1;
                    }

                    float elemY = y + 4;
                    for (Element elem : butt.elements) {
                        elem.setX(x);
                        if (elem instanceof ValueSlider) {
                            elem.setY(elemY += elem.getHeight());
                            elemY += 2;
                        } else {
                            elem.setY(elemY += elem.getHeight());
                            elemY += 1;
                        }

                        elem.drawElement(RenderUtils2D.getMouseX(), RenderUtils2D.getMouseY());
                    }
                    Gui.drawRect(x, y + 5 + elementHeight, x + 96, y + 6 + elementHeight, linesColor);
                    element.setHeight(elementHeight + 6);
                }
            }
        }
    }

    @Override
    public void renderSlider(String name, float value, float x, float y, float width, float height, float sliderX, boolean overElement, Element element) {

        element.setWidth(96);
        element.setHeight(this.getElementHeight());

        Gui.drawRect(x, y + 1, x + element.getWidth(), y + height, 0xff151515);
        Gui.drawRect(x, y + 1, x + sliderX, y + height, 0xff009000);
        DecimalFormat df = new DecimalFormat("#.##");
		String n = df.format(value);
        Gui.drawString(Wrapper.fr(), name, (int)(x + 2), (int)(y + 3), 0xFFFFF0F0);
        Gui.drawString(Wrapper.fr(), n, (int)(x + element.getWidth() - Wrapper.fr().getStringWidth(n) - 2), (int)(y + 3), 0xFFFFF0F0);
    }
	@Override
	public void drawTheShit(Element element, boolean overElement) {
	}
}
