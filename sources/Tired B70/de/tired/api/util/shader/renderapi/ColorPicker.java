package de.tired.api.util.shader.renderapi;

import de.tired.api.extension.Extension;
import de.tired.api.util.math.TimerUtil;
import de.tired.api.util.font.FontManager;
import de.tired.api.guis.clickgui.setting.impl.ColorPickerSetting;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;

import java.awt.*;

import lombok.Getter;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

import static net.minecraft.client.gui.GuiScreen.drawRect;
import static org.lwjgl.input.Keyboard.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * @author kroko
 * @created on 20.02.2021 : 19:42
 */
@Getter
public class ColorPicker {

	final Type type;
	final ColorPickerSetting value;

	final TimerUtil timerUtil = new TimerUtil();

	int currentValue;

	int x, y, width, height, color = Color.red.getRGB();
	boolean typing;
	private double colorFieldDotX, lastColorFieldDotX, colorFieldDotY, lastColorFieldDotY;
	String hex;
	Color currentColor;

	public ColorPicker(Type type, ColorPickerSetting value) {
		this.type = type;
		this.value = value;
		currentValue = value.ColorPickerC.getRGB();
		hex = Integer.toHexString(currentValue).substring(2);
	}

	private Color getHoverColor() {
		final ByteBuffer rgb = BufferUtils.createByteBuffer(100);
		GL11.glReadPixels(Mouse.getX(), Mouse.getY(), 1, 1, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, rgb);
		return new Color(rgb.get(0) & 0xFF, rgb.get(1) & 0xFF, rgb.get(2) & 0xFF);
	}

	public void draw(int x, int y, int width, int height, int mouseX, int mouseY, Color currentColor) {
		draw(x, y, width, height, mouseX, mouseY, currentColor, true);
	}

	public void setColor(Color color) {
		glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
	}

	public void draw(int x, int y, int width, int height, int mouseX, int mouseY, Color currentColor, boolean isFront) {
		this.currentColor = currentColor;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		final float f = (float) (color >> 16 & 255) / 255.0F;
		final float f1 = (float) (color >> 8 & 255) / 255.0F;
		final float f2 = (float) (color & 255) / 255.0F;

		final double h = 1;
		for (int i = 0; i < height; i++) {
			Gui.drawRect(x + width + 10, y + (h * i), x + width + 20, y + (h * (i + 1)), Color.HSBtoRGB((float) i / height, 1, 1));

			if (isFront && Mouse.isButtonDown(0) && mouseX >= x + width + 10 && mouseX <= x + width + 20 && mouseY >= y + (h * i) && mouseY <= y + (h * (i + 1))) {
				color = Color.HSBtoRGB((float) i / height, 1, 1);

			}
		}

		for (int i = 0; i < height; i++) {
			if (color == Color.HSBtoRGB((float) i / height, 1, 1)) {

				Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(x + width + 15, y + (h * i) + 1, 3, -1);


			}
		}


		if (type == Type.QUAD) {
			glEnable(GL_BLEND);
			glShadeModel(GL_SMOOTH);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			glDisable(GL_TEXTURE_2D);
			glBegin(GL_POLYGON);
			setColor(new Color(f, f1, f2));
			glVertex2d(x + width, y);
			setColor(Color.white);
			glVertex2d(x, y);
			setColor(Color.black);
			glVertex2d(x, y + height);
			glVertex2d(x + width, y + height);
			glEnd();
			glEnable(GL_TEXTURE_2D);
		}



		if (isFront && Mouse.isButtonDown(0)) {
			if (isHover(mouseX, mouseY)) {
				final Color hoverColor = getHoverColor();
				if (value != null) {
					value.setColorPickerColor(hoverColor);
				}
				currentValue = hoverColor.getRGB();
				hex = Integer.toHexString(hoverColor.getRGB()).substring(2);
			}
		}

		drawRect(x + width + 25, y, x + width + 85, y + 25, currentColor.getRGB());
		drawRect(x + width + 25, (int) (y + 27 - FontManager.entypo.getStringHeight("#" + hex)), x + width + 85, y + 25, Integer.MIN_VALUE);
		FontManager.IBMPlexSans.drawString("#" + hex + (typing && !timerUtil.reachedTime(250) ? "_" : ""), x + width + 27, y + 21 - FontManager.IBMPlexSans.getStringHeight("#" + hex) / 2F, -1);
		if(timerUtil.reachedTime(500)) {
			timerUtil.doReset();
		}
		if (isFront && Mouse.isButtonDown(0)) {
			if (isHover(mouseX, mouseY)) {

				colorFieldDotX = MathHelper.clamp_double(mouseX, x, x + getWidth() - 5);
				colorFieldDotY = MathHelper.clamp_double(mouseY, y, y + getHeight() - 5);

				Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(colorFieldDotX, colorFieldDotY, 3, Color.BLACK.getRGB());
				Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(colorFieldDotX, colorFieldDotY, 2, Color.WHITE.getRGB());

			}
		}

	}

	public void handleClick(int mouseX, int mouseY, int mouseButton) {
		//fr.drawString("#" + Integer.toHexString(currentColor.getRGB()).substring(2), x + width + 27, y + 21 - fr.FONT_HEIGHT / 2, -1);
		if (mouseButton == 0) {
			final int xPos = x + width + 27 + FontManager.entypo.getStringWidth("#"), yPos = (int) (y + 21 - FontManager.entypo.getStringHeight("#") / 2);
			typing = mouseX >= xPos && mouseX <= xPos + FontManager.entypo.getStringWidth(hex) && mouseY >= yPos && mouseY <= yPos + FontManager.entypo.getStringHeight("#" + hex);
		}
	}

	public void handleInput(char typedChar, int keyCode) {
		try {
			if (typing) {
				int digit = Character.digit(typedChar, 16);

				switch (keyCode) {
					case KEY_BACK:
						if (hex.length() > 0) {
							hex = hex.substring(0, hex.length() - 1);
							if (hex.replace("-", "").length() > 0) {
								if (value != null)
									value.setColorPickerColor(new Color(Integer.parseInt(hex, 16)));
								currentValue = Integer.parseInt(hex, 16);
							}
						}
						break;
					case KEY_END:
					case KEY_RETURN:
						typing = false;
						if (hex.replace("-", "").length() > 0) {
							if (value != null) {
								value.setColorPickerColor(new Color(Integer.parseInt(hex, 16)));
							}
							currentValue = Integer.parseInt(hex, 16);
						}
						break;
					default:
						if ((typedChar == '-' || digit >= 0)) {
							hex = hex + typedChar;
							if (hex.replace("-", "").length() > 0) {
								if (value != null) {
									value.setColorPickerColor(new Color(Integer.parseInt(hex, 16)));
								}
								currentValue = Integer.parseInt(hex, 16);
							}
						}
						break;
				}
			}
		} catch (NumberFormatException e) {
			if (hex.length() > 0) {
				hex = hex.substring(0, hex.length() - 1);
			}
		}
	}

	public boolean isHover(int mouseX, int mouseY) {
		return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
	}

	public enum Type {
		QUAD, SHADER;
	}
}