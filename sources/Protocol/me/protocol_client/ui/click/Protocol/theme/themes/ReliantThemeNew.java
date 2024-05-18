package me.protocol_client.ui.click.Protocol.theme.themes;

import java.text.DecimalFormat;

import me.protocol_client.Wrapper;
import me.protocol_client.ui.click.Protocol.GuiClick;
import me.protocol_client.ui.click.Protocol.Elements.Element;
import me.protocol_client.ui.click.Protocol.Elements.moreElements.ModStuff;
import me.protocol_client.ui.click.Protocol.Elements.moreElements.ValueSlider;
import me.protocol_client.ui.click.Protocol.Panel.Panel;
import me.protocol_client.ui.click.Protocol.theme.ClickTheme;
import me.protocol_client.utils.RenderUtils2D;
import net.minecraft.client.gui.Gui;

public class ReliantThemeNew extends ClickTheme {

	protected GuiClick	gui;

	public ReliantThemeNew(GuiClick gui) {
		super("Aristhena", 96, 13, gui, true);
		this.gui = gui;
	}

	@Override
	public void renderPanel(Panel panel) {

		panel.setOpenHeight(15);
		panel.setButtonOffset(2F);
		panel.setWidth(100);

		Wrapper.drawBorderRect(panel.getX(), panel.getY() + 2, panel.getX() + panel.getWidth(), panel.getY() + (panel.isOpen() ? panel.getHeight() + 2 : panel.getOpenHeight()), 0xFF000000, 0xff272727, 1);
		Gui.drawCenteredString(Wrapper.fr(), panel.getName(), (int) (panel.getX() + 50), (int) (panel.getY() + 5F), 0xffffffff);

		if (panel.isOpen()) {
		}

	}

	@Override
	public void renderButton(String name, boolean enabled, float x, float y, float width, float height, boolean overElement, Element element) {
		element.setWidth(this.getElementWidth());
		element.setHeight(this.getElementHeight() + 1);
		if (element instanceof ModStuff) {
			ModStuff butt = (ModStuff) element;
			if(butt.open && butt.elements.size() > 0){
			}
			if (!butt.open || butt.elements.size() == 0) {
				Wrapper.drawBorderRect(x + 4, y, x + 92, y + getElementHeight() + 2, 0xFF101010, enabled ? 0xff252525 : 0xFF393939, 0.7f);
			}
			Gui.drawCenteredString(Wrapper.fr(), name, (int) (x + 50), (int) y + 4, -1);
			if (butt.elements.size() > 0) {
				if (butt.open) {
					float elementHeight = element.getHeight();
					for (Element elem : butt.elements) {
						elementHeight += elem.getHeight() + 1;
					}
					Gui.drawRect(x + 2, y, x + 94, y + elementHeight + 6, 0xff303030);
					Gui.drawRect(x + 2, y, x + 94, y + 0.7f, 0xff101010);
					Gui.drawCenteredString(Wrapper.fr(), name, (int) (x + 50), (int) y + 4, -1);
					float elemY = y + 2;
					for (Element elem : butt.elements) {
						elem.setX(x);
						if (elem instanceof ValueSlider) {
							elem.setY(elemY += elem.getHeight());
							elemY += 0.5f;
						} else {
							elem.setY(elemY += elem.getHeight());
							elemY += 1.5f;
						}

						elem.drawElement(RenderUtils2D.getMouseX(), RenderUtils2D.getMouseY());
					}
					element.setHeight(elementHeight + 6);
					Gui.drawRect(x + 2, y, x + 2.7f, y + elementHeight + 6.7f, 0xff101010);
					Gui.drawRect(x + 94, y, x + 94.7f, y + elementHeight + 6.7f, 0xff101010);
					Gui.drawRect(x + 2, y + elementHeight + 6, x + 94, y + elementHeight + 6.7f, 0xff101010);
				}
			}
		} else {
			Wrapper.drawBorderRect(x + 4, y, x + 92, y + getElementHeight() + 2, 0xFF101010, enabled ? 0xff252525 : 0xFF393939, 0.7f);
			Gui.drawCenteredString(Wrapper.fr(), name, (int) (x + 50), (int) y + 4, -1);
		}
	}

	  public static double roundToIncrement(double val, double increment)
	  {
	    return val / increment * increment;
	  }
	
	@Override
	public void renderSlider(String name, float value, float x, float y, float width, float height, float sliderX, boolean overElement, Element element) {

		element.setWidth(92);
		element.setHeight(this.getElementHeight());
		x = x + 2;
		y = y + 10;
		height = 5;
		Gui.drawRect(x, y + 1, x + element.getWidth(), y + 3, 0xFF454545);
		Gui.drawRect(x, y + 1, x + sliderX, y + 3, 0xFF808080);
		DecimalFormat df = new DecimalFormat("#.##");
		String n = df.format(value);
		Gui.drawString(Wrapper.fr(), name, (int) (x + 2), (int) (y - 8), -1);
		Gui.drawString(Wrapper.fr(), n, (int) (x + element.getWidth() - Wrapper.fr().getStringWidth(n) - 2), (int) (y - 8), -1);
	}
	@Override
	public void drawTheShit(Element element, boolean overElement) {
	}
}
