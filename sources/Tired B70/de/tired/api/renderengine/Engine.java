package de.tired.api.renderengine;

import de.tired.api.extension.Extension;
import de.tired.interfaces.FHook;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

/**
 * Rendering engine coded by Felix1337.. dont steal code thx
 */

public class Engine {

	private ElementType elementType;

	//i didnt use primitive because a int cant be null.
	private Integer x, y, width, height;

	private double radius;

	private Color color;

	private boolean rectBehindTitle;

	private final ArrayList<String> strings = new ArrayList<>();

	private String text;

	public void drawElement(ElementType elementType, String... title) {

		this.elementType = elementType;

		/**
		 * Setting up default values, if not defined.
		 */

		if (x == null) {
			x = 20; //default x
		}
		if (y == null) {
			y = 30; //default y
		}

		if (width == null) {
			width = 120; //default width
		}

		if (height == null) {
			height = 40; //default height
		}

		switch (elementType) {
			case NORMAL_RECT:
				Gui.drawRect(x, y, width, height, color.getRGB());
				break;
			case ROUNDED_RECT:
				Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRoundedRect(x, y, width, height, (int) radius, color.getRGB());
		}

		if (title != null) {
			renderTitle(title[0]);
		}

		int yAdd = 0;
		if (text != null) {
			for (String textor : strings) {
				drawEngineString(textor, x + 5, y + 25 + yAdd, -1);
				yAdd += 15;
			}

		} else {
			System.out.println("Text must be defined before using");
		}
	}

	public void renderTitle(String title, boolean... middle) {

		if (title.isEmpty()) return;
		if (rectBehindTitle) {
			switch (elementType) {
				case NORMAL_RECT:
					Gui.drawRect(x, y, width, y + 20, color.darker().getRGB());
					break;
				case ROUNDED_RECT:
					Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRoundedRect(x, y, width, y + 20, (int) radius, color.darker().getRGB());
			}
		}
		FHook.fontRenderer.drawStringWithShadow(title, x + 3, y + 4, -1);
	}

	public void rectBehindTitle(boolean rect) {
		this.rectBehindTitle = rect;
	}

	public Engine setRadius(double radius) {
		this.radius = radius;
		return this;
	}

	public Engine setHeight(double height) {
		this.height = (int) height;
		return this;
	}

	public Engine setWidth(double width) {
		this.width = (int) width;
		return this;
	}
	public Engine colorEngine(Color color) {
		color(color);
		return this;
	}

	public void drawEngineString(String text, int x, int y, int color) {
		this.text = text;
		FHook.fontRenderer.drawStringWithShadow(text, x, y, color);
	}

	public Engine setupString(String text) {
		this.text = text;
		strings.add(text);
		return this;
	}

	public Engine setY(int y) {
		setupY(y);
		return this;
	}

	private void setupY(int y) {
		this.y = y;
	}

	public Engine setX(int x) {
		setupX(x);
		return this;
	}

	private void color(Color color) {
		this.color = color;
	}

	private void setupX(int x) {
		this.x = x;
	}

	public Engine scale(int scaleFactor) {
		GL11.glPushMatrix();
		GL11.glScaled(scaleFactor, scaleFactor, scaleFactor);
		drawElement(elementType);
		GL11.glPopMatrix();
		return this;
	}

}
