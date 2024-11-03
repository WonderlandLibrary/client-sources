package net.augustus.latestCGUI;

import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.augustus.events.EventClickGui;
import net.augustus.font.testfontbase.FontUtil;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.BooleansSetting;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.Setting;
import net.augustus.settings.StringValue;
import net.augustus.utils.EventHandler;
import net.augustus.utils.interfaces.MM;
import net.augustus.utils.interfaces.SM;
import net.augustus.utils.skid.lorious.BlurUtil;
import net.augustus.utils.skid.lorious.RectUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

public class Clickgui extends GuiScreen {

	public int x, y;
	public int dragX, dragY;
	public int width, height;
	public boolean dragging;
	public Categorys currentCategory = Categorys.COMBAT;
	public Module currentModule = null;
	public double scrollBuffer = 0;
	public boolean draggingSlider = false;
	public boolean settingKey = false;

	public Clickgui() {
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (dragging) {
			x = dragX + mouseX;
			y = dragY + mouseY;
		}
		EventClickGui eventClickGui = new EventClickGui();
        EventHandler.call(eventClickGui);
		scrollBuffer += Mouse.getDWheel() / 100;
		BlurUtil.blur(x, y + 15, x + width, y + height);
		RectUtils.drawRect(x, y, x + width, y + 15, 0xff252525);
		RectUtils.drawRect(x, y + 15, x + width, y + height, 0x99404040);
		FontUtil.esp.drawStringWithShadow("CLICKGUI", x + 3, y + 3, -1);
		Gui.drawRect(x + 80, y + 15, x + 80 + 2, y + height, 0xff252525);
		Gui.drawRect(x + 80, y + 50, x + width, y + 50 + 2, 0xff252525);
		Color blue = new Color(41, 146, 222);
		int count = 0;
		for (Module m : MM.mm.getModules(currentCategory)) {
			FontUtil.arial.drawString(m.getName(), x + 3, y + 25 + (count * FontUtil.arial.getHeight() * 1.5),
					m.isToggled() ? blue.getRGB() : -1);
			count++;
		}
		count = 0;
		for (Categorys c : Categorys.values()) {
			FontUtil.arial.drawString(c.name(), x + 100 + (count * FontUtil.arial.getHeight() * 8), y + 25, -1);
			Gui.drawRect(x + 100 + (count * FontUtil.arial.getHeight() * 8),
					y + 25 + FontUtil.arial.getHeight(),
					x + 100 + (count * FontUtil.arial.getHeight() * 8)
							+ FontUtil.arial.getStringWidth(c.name()),
					y + 25 + FontUtil.arial.getHeight() + 1, currentCategory == c ? -1 : 0x00000000);
			count++;
		}
		if (currentModule != null) {
			FontUtil.esp.drawStringWithShadow(currentModule.getName() + ":", x + 100, y + 60, blue.getRGB());
			FontUtil.arial.drawString("Key: " + (settingKey ? "Listening..." : Keyboard.getKeyName(currentModule.getKey())), x + 100, y + 70, -1);
			count = 0;
			count += scrollBuffer;
			for (Setting s : SM.sm.getSettingsByMod(currentModule)) {
				double offset = y + 70 + 13 + (count * FontUtil.arial.getHeight() * 1.5);
				if (s.isVisible() && !((offset + FontUtil.arial.getHeight()) >= y + height)) {
					if ((offset + FontUtil.arial.getHeight() < y + 70 + 10)) {
						count++;
						continue;
					}
					FontUtil.arial.drawString(s.getName() + ":", x + 100, offset, -1);
					if (s instanceof BooleanValue) {
						FontUtil.arial.drawString(((BooleanValue) s).getBoolean() + "",
								x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 2, offset,
								((BooleanValue) s).getBoolean() ? 0xff00ff00 : 0xffff0000);
					}
					if (s instanceof DoubleValue) {
						RectUtils.drawRect(x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 3, offset,
								x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 120, offset + 1,
								0xff252525);
						RectUtils.drawRect(x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 3, offset,
								x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 3 - 1,
								offset + FontUtil.arial.getHeight(), 0xff252525);
						RectUtils.drawRect(x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 3,
								offset + FontUtil.arial.getHeight() - 1,
								x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 120,
								offset + FontUtil.arial.getHeight(), 0xff252525);
						RectUtils.drawRect(x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 120, offset,
								x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 120 + 1,
								offset + FontUtil.arial.getHeight(), 0xff252525);
						RectUtils.drawRect(x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 3,
								offset + 1,
								(int) (x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 3
										+ 117 * (((DoubleValue) s).getValue() - ((DoubleValue) s).getMinValue())
												/ (((DoubleValue) s).getMaxValue() - ((DoubleValue) s).getMinValue())),
								offset + FontUtil.arial.getHeight() - 1, blue.getRGB());
						FontUtil.arial.drawString(((DoubleValue) s).getValue() + "",
								x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 120 / 2 - 10, offset,
								-1);
						boolean hoveredSlider = mouseX > x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":")
								&& mouseY > offset + 1
								&& mouseX < x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 3 + 120
								&& mouseY < offset + FontUtil.arial.getHeight();
						if (hoveredSlider && draggingSlider) {
							double diff = Math.min(117, Math.max(0,
									mouseX - (x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 3)));
							if (mouseX < (x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 3)) {
								((DoubleValue) s).setValue(((DoubleValue) s).getMinValue());
							}
							if (diff <= 0) {
								((DoubleValue) s).setValue(((DoubleValue) s).getMinValue());
							} else {
								double newValue = roundToPlace(
										((diff / 117)
												* (((DoubleValue) s).getMaxValue() - ((DoubleValue) s).getMinValue())
												+ ((DoubleValue) s).getMinValue()),
										((DoubleValue) s).getDecimalPlaces());
								((DoubleValue) s).setValue(newValue);
							}
						}
					}
					if (s instanceof StringValue) {
						int strCount = 0;
						int xOffset = 0;
						for (String str : ((StringValue) s).getStringList()) {
							FontUtil.arial.drawString(str,
									x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 3 + xOffset,
									offset, ((StringValue) s).getSelected().equals(str) ? blue.getRGB() : -1);
							if (!(strCount == ((StringValue) s).getStringList().length - 1))
								FontUtil.arial.drawString(",",
										x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":" + str) + 3
												+ xOffset + 0.5,
										offset, -1);
							xOffset += FontUtil.arial.getStringWidth(str + ", ");
							strCount++;
						}
					}
					if (s instanceof BooleansSetting) {
						int strCount = 0;
						int xOffset = 0;
						for (Setting bv : ((BooleansSetting) s).getSettingList()) {
							FontUtil.arial.drawString(bv.getName(),
									x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 3 + xOffset,
									offset, ((BooleanValue) bv).getBoolean() ? blue.getRGB() : -1);
							if (!(strCount == ((BooleansSetting) s).getSettingList().length - 1))
								FontUtil.arial.drawString(",",
										x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":" + bv.getName())
												+ 3 + xOffset + 0.5,
										offset, -1);
							xOffset += FontUtil.arial.getStringWidth(bv.getName() + ", ");
							strCount++;
						}
					}
					count++;
				}
			}
		}
	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		boolean dragging = mouseX > x && mouseY > y && mouseX < x + width + 30 && mouseY < y + 15;
		if (dragging) {
			this.dragging = true;
			dragX = x - mouseX;
			dragY = y - mouseY;
		}
		int count = 0;
		if(currentModule != null) {
			boolean hoveredSetKey = mouseX > x + 100 && mouseY > y + 70 && mouseX < x + 100 + FontUtil.arial.getStringWidth(Keyboard.getKeyName(currentModule.getKey())) && mouseY < y + 70 + FontUtil.arial.getHeight();
			if(hoveredSetKey && mouseButton == 0)
				settingKey = !settingKey;
		}
		for (Module m : MM.mm.getModules(currentCategory)) {
			boolean hovered = mouseX > x + 3 && mouseY > y + 25 + (count * FontUtil.arial.getHeight() * 1.5)
					&& mouseX < x + 3 + FontUtil.arial.getStringWidth(m.getName()) && mouseY < y + 25
							+ (count * FontUtil.arial.getHeight() * 1.5) + FontUtil.arial.getHeight();
			if (hovered && mouseButton == 0) {
				m.toggle();
				currentModule = m;
			}
			if (hovered && mouseButton == 1) {
				currentModule = m;
			}
			count++;

		}
		count = 0;
		for (Categorys c : Categorys.values()) {
			boolean hovered = mouseX > x + 100 + (count * FontUtil.arial.getHeight() * 8) && mouseY > y + 25
					&& mouseX < x + 100 + (count * FontUtil.arial.getHeight() * 8)
							+ FontUtil.arial.getStringWidth(c.name())
					&& mouseY < y + 25 + FontUtil.arial.getHeight();
			if (hovered && mouseButton == 0) {
				currentCategory = c;
			}
			count++;
		}
		count = 0;
		count += scrollBuffer;
		for (Setting s : SM.sm.getSettingsByMod(currentModule)) {
			double offset = y + 70 + 13 + (count * FontUtil.arial.getHeight() * 1.5);
			if (s.isVisible() && !((offset + FontUtil.arial.getHeight()) >= y + height)) {
				if ((offset + FontUtil.arial.getHeight() < y + 70 + 10)) {
					count++;
					continue;
				}
				if (s instanceof BooleanValue) {
					boolean hoveredBooleanValue = mouseX > x + 100
							+ FontUtil.arial.getStringWidth(s.getName() + ":")
							&& mouseY > offset
							&& mouseX < x + 100
									+ FontUtil.arial
											.getStringWidth(s.getName() + ":" + ((BooleanValue) s).getBoolean())
							&& mouseY < offset + FontUtil.arial.getHeight();
					if (hoveredBooleanValue && mouseButton == 0)
						((BooleanValue) s).setBoolean(!((BooleanValue) s).getBoolean());
				}
				if (s instanceof DoubleValue) {
					boolean hoveredSlider = mouseX > x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 3
							&& mouseY > offset + 1
							&& mouseX < x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 3 + 120
							&& mouseY < offset + FontUtil.arial.getHeight();
					if (hoveredSlider && mouseButton == 0)
						draggingSlider = true;
				}
				if (s instanceof StringValue) {
					int strCount = 0;
					int xOffset = 0;
					for (String str : ((StringValue) s).getStringList()) {
						boolean hoveredOption = mouseX > x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":")
								+ 3 + xOffset
								&& mouseY > offset
								&& mouseX < x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 3 + xOffset
										+ FontUtil.arial.getStringWidth(s.getName() + ":" + str)
								&& mouseY < offset + FontUtil.arial.getHeight();
						if (hoveredOption && mouseButton == 0)
							((StringValue) s).setString(str);
						xOffset += FontUtil.arial.getStringWidth(str + ", ");
						strCount++;
					}
				}
				if (s instanceof BooleansSetting) {
					int strCount = 0;
					int xOffset = 0;
					for (Setting bv : ((BooleansSetting) s).getSettingList()) {
						boolean hoveredOption = mouseX > x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":")
								+ 3 + xOffset
								&& mouseY > offset
								&& mouseX < x + 100 + FontUtil.arial.getStringWidth(s.getName() + ":") + 3 + xOffset
										+ FontUtil.arial.getStringWidth(bv.getName())
								&& mouseY < offset + FontUtil.arial.getHeight();
						if (hoveredOption && mouseButton == 0)
							((BooleanValue) bv).setBoolean(!((BooleanValue) bv).getBoolean());
						xOffset += FontUtil.arial.getStringWidth(bv.getName() + ", ");
						strCount++;
					}
				}
				count++;
			}
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	protected void mouseReleased(int mouseX, int mouseY, int state) {
		dragging = false;
		draggingSlider = false;
		super.mouseReleased(mouseX, mouseY, state);
	}

	public void initGui() {
		x = 30;
		y = 30;
		width = 700;
		height = 400;
	}

	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(settingKey && currentModule != null) {
			currentModule.setKey(keyCode);
			settingKey = false;
		}
		super.keyTyped(typedChar, keyCode);
	}

	private double roundToPlace(double value, int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

}
