package me.protocol_client.ui.click.Protocol.theme.themes;

import java.text.DecimalFormat;

import me.protocol_client.Wrapper;
import me.protocol_client.ui.click.Protocol.GuiClick;
import me.protocol_client.ui.click.Protocol.Elements.Element;
import me.protocol_client.ui.click.Protocol.Elements.moreElements.ModStuff;
import me.protocol_client.ui.click.Protocol.Elements.moreElements.ValueSlider;
import me.protocol_client.ui.click.Protocol.Panel.Panel;
import me.protocol_client.ui.click.Protocol.theme.ClickTheme;
import me.protocol_client.utils.RenderUtils;
import me.protocol_client.utils.RenderUtils2D;
import net.minecraft.client.gui.Gui;

public class ProtocolTheme extends ClickTheme {

	protected GuiClick	gui;

	public ProtocolTheme(GuiClick gui) {
		super("Protocol", 96, 13, gui, true);
		this.gui = gui;
	}

	@Override
	public void renderPanel(Panel panel) {

		panel.setOpenHeight(15);
		panel.setButtonOffset(0.7F);
		panel.setWidth(100);

		Wrapper.drawBorderRect(panel.getX(), panel.getY() + 2, panel.getX() + panel.getWidth(), panel.getY() + (panel.isOpen() ? panel.getHeight() : panel.getOpenHeight()), 0xFF000000, 0xff000000, 0.7f);
		Gui.drawString(Wrapper.fr(), panel.getName(), (int) (panel.getX() + 3), (int) (panel.getY() + 5F), 0xFFFFFFFF);

		if (panel.isOpen()) {
			Gui.drawRect(panel.getX() + 1, panel.getY() + panel.getOpenHeight(), panel.getX() + panel.getWidth() - 1, panel.getY() + panel.getOpenHeight() + 0.5F, 0xFF000000);
		}

		Gui.drawString(Wrapper.fr(), panel.isOpen() ? "<" : ">", (int) (panel.getX() + panel.getWidth() - (panel.isOpen() ? 8.5F : 9)), (int) (panel.getY() + (panel.isOpen() ? 5 : 5)), 0xFFFFFFFF);
	}

	@Override
	public void renderButton(String name, boolean enabled, float x, float y, float width, float height, boolean overElement, Element element) {
		element.setWidth(this.getElementWidth());
		element.setHeight(this.getElementHeight());
		int main = 0xffff4040;
		RenderUtils2D.drawRoundedRect(x, y, x + 96, y + getElementHeight(), enabled ? main : 0xff000000, enabled ? main : 0xFF232323);
		Gui.drawString(Wrapper.fr(), name, (int) (x + 2), (int) (y + 3), 0xFFFFFFFF);
		if (element instanceof ModStuff) {
			ModStuff butt = (ModStuff) element;

			if (butt.elements.size() > 0) {
				if (!butt.open) {
				}
				if (butt.open) {
					float elementHeight = element.getHeight();
					for (Element elem : butt.elements) {
						elementHeight += elem.getHeight() + 0.7;
					}

					float elemY = y + 2;
					for (Element elem : butt.elements) {
						elem.setX(x);
						if (elem instanceof ValueSlider) {
							elem.setY(elemY += elem.getHeight());
							elemY += 0.5f;
						} else {
							elem.setY(elemY += elem.getHeight());
							elemY += 0.7f;
						}

						elem.drawElement(RenderUtils2D.getMouseX(), RenderUtils2D.getMouseY());
					}
					element.setHeight(elementHeight + 2f);
				}
			}
		}
	}
	@Override
	public void renderSlider(String name, float value, float x, float y, float width, float height, float sliderX, boolean overElement, Element element) {

		element.setWidth(96);
		element.setHeight(this.getElementHeight());
		int main = 0xffff4040;
		RenderUtils2D.drawGradientRect(x, y + 1, x + element.getWidth(), y + height, 0xFF000000, 0xFF232323);
		RenderUtils2D.drawRoundedRect(x, y + 1, x + sliderX, y + height, main, main);
		DecimalFormat df = new DecimalFormat("#.##");
		String n = df.format(value);
		Gui.drawString(Wrapper.fr(), name, (int) (x + 2), (int) (y + 3), 0xFFFFF0F0);
		Gui.drawString(Wrapper.fr(), n, (int) (x + element.getWidth() - Wrapper.fr().getStringWidth(n) - 2), (int) (y + 3), 0xFFFFF0F0);
	}
	@Override
	public void drawTheShit(Element element, boolean overElement) {
		if (element instanceof ModStuff) {
			ModStuff butt = (ModStuff) element;
			if (overElement) {
				Gui.drawRect(RenderUtils2D.getMouseX() + 7 + Wrapper.fr().getStringWidth(butt.getMod().getName()), RenderUtils2D.getMouseY() + 18, RenderUtils2D.getMouseX() + 5, RenderUtils2D.getMouseY() + 9, 0x70000000);
				Gui.drawString(Wrapper.fr(), butt.getMod().getName(), RenderUtils2D.getMouseX() + 7, RenderUtils2D.getMouseY() + 10, -1);
			}
		}
	}
}
