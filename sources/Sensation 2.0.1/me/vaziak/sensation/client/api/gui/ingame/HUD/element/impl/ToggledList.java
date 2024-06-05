
package me.vaziak.sensation.client.api.gui.ingame.HUD.element.impl;

import java.awt.*;
import java.util.ArrayList;

import me.vaziak.sensation.client.api.event.EventSystem;
import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.gui.ingame.HUD.element.Element;
import me.vaziak.sensation.client.api.gui.ingame.HUD.element.Quadrant;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import me.vaziak.sensation.client.api.property.impl.ColorProperty;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.utils.anthony.ColorCreator;
import me.vaziak.sensation.utils.anthony.Draw;
import me.vaziak.sensation.utils.anthony.basicfont.FontRenderer;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

//import me.vaziak.sensation.utils.anthony.basicfont.Fonts.fontFromTTF;

/**
 * @author antja03
 */
public class ToggledList extends Element {

	protected Minecraft mc = Minecraft.getMinecraft();
	public BooleanProperty enabled = new BooleanProperty("Arraylist", "Show Arraylist", null, true);
	public StringsProperty mode = new StringsProperty("Mode", "What style of arraylist you would like", null, true, false, new String[] {"Background", "Borders", "Side Lines"});

	public StringsProperty modeFormatting = new StringsProperty("Mode Formatting", "How you want your mode to be formatted", null, false, true, new String[] {"None", "Default", "Dash", "Brackets", "Parentheses", "Double Arrow", "Single Arrow", "Line", "Directional Arrows", "Divide"});
	public StringsProperty textFormatting = new StringsProperty("Text Formatting", "How you want your text to be formatted", null, false, true, new String[] {"Default", "Uppercase", "Lowercase"} );
	public StringsProperty sideValue = new StringsProperty("Side", "What side do you want the arraylist to be set to", null, false, true, new String[] {"Right", "Left"} );
	public StringsProperty modeColor = new StringsProperty("Mode Color", "What color do you want the mode ot be", null, false, true, new String[] {"Gray", "White"});
	private StringsProperty colorMode = new StringsProperty("Color Mode", "How you want your arraylist colored", null, false, true, new String[] {"Static", "Rainbow", "Random"});

	public BooleanProperty keepSpaces = new BooleanProperty("Keep Spaces", "Keep the spaces in some modules such as No Fall. If disabled it will be NoFall",
			() -> enabled.getValue(), true);

	private DoubleProperty entryHeight = new DoubleProperty("Entry Height", "The height of each cheat name", null,
			10, 2, 12, 1, null);
	
	private DoubleProperty saturation = new DoubleProperty("Saturation", "The saturation of the arraylist", null, 10, 2, 10, 1, null);

	public ColorProperty backgroundColor = new ColorProperty("Background Color", "",
			() -> enabled.getValue() && mode.getValue().get("Background"), 1f, 0f, 1f, 255);

	private StringsProperty fonts = new StringsProperty("Font", "What font do you want to use", null, false, true, new String[] {"Verdana", "Arial", "Roboto", "Minecraft", "Open Sans", "Montserrat"});

	private DoubleProperty fontSize = new DoubleProperty("Font Size", "The size of each font", null,
			18, 10, 30, 1, null);

	public ColorProperty prop_color = new ColorProperty("Arraylist Color", "",
			() -> enabled.getValue() && colorMode.getValue().get("Static"), 1f, 0f, 1f, 255);

	private boolean decreasing = true;
	private float brightness = 1f;

	public ToggledList() {
		super("Module list", Quadrant.TOP_RIGHT, 2, 2);
		registerValue(enabled);
		registerValue(mode);
		registerValue(modeFormatting);
		registerValue(textFormatting);
		registerValue(backgroundColor);
		registerValue(modeColor);
		registerValue(colorMode);
		registerValue(keepSpaces);
		registerValue(prop_color);
		registerValue(saturation);
		registerValue(entryHeight);
		registerValue(fonts);
		registerValue(fontSize);
		registerValue(sideValue);
		EventSystem.hook(this);
	}

	private String modeFormatted;

	private FontRenderer font;

	void drawModule(Module cheat, ScaledResolution sr, String name, int y, double length) {
		if (cheat == null || cheat.color == null)
			return;
		if (!fonts.getValue().get("Minecraft")) {
			if (sideValue.getValue().get("Left")) {
				font.drawStringWithShadow(name,
						(float) (positionX),
						(float) (y + entryHeight.getValue() / 2 - font.getHeight() / 2),
						colorMode.getValue().get("Rainbow") ?
								ColorCreator.createRainbowFromOffset(5200, (int) (y * -15), saturation.getValue().floatValue() - 1)
								: (colorMode.getValue().get("Random") ? cheat.color.getRGB()
								: prop_color.getValue().getRGB()));
			} else {
				font.drawStringWithShadow(name,
						(float) (sr.getScaledWidth() - positionX - length),
						(float) (y + entryHeight.getValue() / 2 - font.getHeight() / 2),
						colorMode.getValue().get("Rainbow") ?
								ColorCreator.createRainbowFromOffset(5200, (int) (y * -15), saturation.getValue().floatValue() - 1)
								: (colorMode.getValue().get("Random") ? cheat.color.getRGB()
								: prop_color.getValue().getRGB())); //TODO: Add brightness thingy to color creator since vaziak wont give it to me
			}
		} else {
			if (sideValue.getValue().get("Left")) {
				mc.fontRendererObj.drawStringWithShadow(name,
						(float) (positionX),
						(float) (y + entryHeight.getValue() / 2 - mc.fontRendererObj.FONT_HEIGHT / 2),
						colorMode.getValue().get("Rainbow") ?
								ColorCreator.createRainbowFromOffset(5200, (int) (y * -15), saturation.getValue().floatValue() - 1)
								: (colorMode.getValue().get("Random") ? cheat.color.getRGB()
								: prop_color.getValue().getRGB()));
			} else {
				mc.fontRendererObj.drawStringWithShadow(name,
						(float) (sr.getScaledWidth() - positionX - mc.fontRendererObj.getStringWidth(name)),
						(float) (y + entryHeight.getValue() / 2 - mc.fontRendererObj.FONT_HEIGHT / 2),
						colorMode.getValue().get("Rainbow") ?
								ColorCreator.createRainbowFromOffset(5200, (int) (y * -15), saturation.getValue().floatValue() - 1)
								: (colorMode.getValue().get("Random") ? cheat.color.getRGB()
								: prop_color.getValue().getRGB()));
			}
		}
	}

	@Override
	public void drawElement(boolean editor) {
		this.editX = positionX + 1;
		this.editY = positionY;
		this.width = getLongestToggledLabelLength() + 3 + (mode.getValue().get("Side Lines") ? 5.5 : 0);
		this.height = getToggledCheats().size() * entryHeight.getValue();

		if (!enabled.getValue() || !Sensation.instance.cheatManager.isModuleEnabled("Overlay"))
			return;

		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		int y = (int) this.positionY - 2;
		for (Module m : getToggledModulesSortedLTS()) {
			if (m.color == null)
				return;

			String name = (keepSpaces.getValue() ? m.getId() : m.getId().replaceAll("\\s+", "")) + (m.getMode() != null ?  EnumChatFormatting.valueOf(modeColor.getSelectedStrings().get(0).toUpperCase()) + " " + String.format(modeFormatted, m.getMode()) : "");

			if (textFormatting.getValue().get("Uppercase")) {
				name = name.toUpperCase();
			} else if (textFormatting.getValue().get("Lowercase")) {
				name = name.toLowerCase();
			}

			if (!name.toLowerCase().contains("interface") && !name.toLowerCase().contains("overlay")) {
				double length = (!fonts.getValue().get("Minecraft") ? font.getStringWidth(name)
						: mc.fontRendererObj.getStringWidth(name));

				length /= 7;
				length *= (double) m.animation / 100;

				if (m.animation < 700) {
					m.animation += 25;
				}
				if (mode.getValue().get("Side Lines")) {
					if (sideValue.getValue().get("Left")) {
						Draw.drawRectangle(positionX - 3, y, positionX - 5.5, y + entryHeight.getValue(), colorMode.getValue().get("Rainbow") ?
								ColorCreator.createRainbowFromOffset(5200, (int) (y * -15), saturation.getValue().floatValue() - 1)
								: (colorMode.getValue().get("Random") ? m.color.getRGB()
								: prop_color.getValue().getRGB()));
					} else {
						Draw.drawRectangle(sr.getScaledWidth() - positionX + 3, y, sr.getScaledWidth() - positionX + 5.5, y + entryHeight.getValue(), colorMode.getValue().get("Rainbow") ?
								ColorCreator.createRainbowFromOffset(5200, (int) (y * -15), saturation.getValue().floatValue() - 1)
								: (colorMode.getValue().get("Random") ? m.color.getRGB()
								: prop_color.getValue().getRGB()));
					}
				}

				if (mode.getValue().get("Borders")) {
					if (sideValue.getValue().get("Left")) {
						Draw.drawBorderedRectangle((int) (positionX + 3 + length), y,
								(int) (positionX - 3), y + entryHeight.getValue() + 1, 1,
								new Color(30, 30, 30).getRGB(),
								colorMode.getValue().get("Rainbow") ?
										ColorCreator.createRainbowFromOffset(5200, (int) (y * -15), saturation.getValue().floatValue() - 1)
										: (colorMode.getValue().get("Random") ? m.color.getRGB()
										: prop_color.getValue().getRGB()),
								true);
					} else {

						Draw.drawBorderedRectangle((int) (sr.getScaledWidth() - positionX - 2 - length), y,
								(int) (sr.getScaledWidth() - positionX + 3), y + entryHeight.getValue() + 1, 1,
								new Color(30, 30, 30).getRGB(),
								colorMode.getValue().get("Rainbow") ?
										ColorCreator.createRainbowFromOffset(5200, (int) (y * -15), saturation.getValue().floatValue() - 1)
										: (colorMode.getValue().get("Random") ? m.color.getRGB()
										: prop_color.getValue().getRGB()),
								true);
					/*Gui.drawRect((int) (sr.getScaledWidth() - positionX - 2 - length), y,
							(int) (sr.getScaledWidth() - positionX + 3), (int) (y + entryHeight.getValue()),
							new Color(30, 30, 30).getRGB());*/
					}
				}

				if (mode.getValue().get("Background")) {
					if (sideValue.getValue().get("Left")) {
						Gui.drawRect((int) (positionX + 2 + length), y,
								(int) (positionX - 2), (int) (y + entryHeight.getValue()),
								backgroundColor.getValue().getRGB());
					} else {
						Gui.drawRect((int) (sr.getScaledWidth() - positionX - 2 - length), y,
								(int) (sr.getScaledWidth() - positionX + 3), (int) (y + entryHeight.getValue()),
								backgroundColor.getValue().getRGB());
					}
				}

				drawModule(m, sr, name, y, length);
				y += entryHeight.getValue();
			}
		}
	}

	private ArrayList<Module> getToggledCheats() {
		ArrayList<Module> toggledCheats = new ArrayList<>();
		for (Module cheat : Sensation.instance.cheatManager.getCheatRegistry().values()) {
			if (cheat.getState()) {
				toggledCheats.add(cheat);
			}
		}
		return toggledCheats;
	}

	public java.util.ArrayList<Module> getToggledModulesSortedLTS() {
		java.util.ArrayList<Module> toggledCheats = getToggledCheats();

		switch (modeFormatting.getSelectedStrings().get(0)) {
            case "None":
                modeFormatted = "";
                break;
			case "Default":
				modeFormatted = "%s";
				break;
			case "Dash":
				modeFormatted = "- %s";
				break;
			case "Brackets":
				modeFormatted = "[%s]";
				break;
			case "Parentheses":
				modeFormatted = "(%s)";
				break;
			case "Double Arrow":
				modeFormatted = "» %s";
				break;
			case "Single Arrow":
				modeFormatted = "> %s";
				break;
			case "Line":
				modeFormatted = "| %s";
				break;
			case "Directional Arrows":
				modeFormatted = "<> %s";
				break;
			case "Divide":
				modeFormatted = "/ %s";
				break;
		}

		String fontName = "";

		if (font != null) {
			fontName += font.getFont().getName().replaceAll(" Regular", "");
		}

		if (font == null || !fontName.equalsIgnoreCase(fonts.getSelectedStrings().get(0)) || font.getFont().getSize() != fontSize.getValue().intValue()) {
			if (!fonts.getValue().get("Minecraft"))
				font = new FontRenderer(Fonts.fontFromTTF(new ResourceLocation("client/" + fonts.getSelectedStrings().get(0).toLowerCase() + ".ttf"), fontSize.getValue().intValue(), Font.PLAIN), true, true);

		}


		toggledCheats.sort((cheat1,
				cheat2) -> (!fonts.getValue().get("Minecraft")
						? font.getStringWidth(
				(keepSpaces.getValue() ? cheat2.getId() : cheat2.getId().replaceAll(" ", "")) + (cheat2.getMode() != null ? " " + String.format(modeFormatted, cheat2.getMode()) : ""))
						: mc.fontRendererObj.getStringWidth(
				(keepSpaces.getValue() ? cheat2.getId() : cheat2.getId().replaceAll(" ", "")) + (cheat2.getMode() != null ? " " + String.format(modeFormatted, cheat2.getMode()) : "")))
						- (!fonts.getValue().get("Minecraft")
								? font.getStringWidth(
				(keepSpaces.getValue() ? cheat1.getId() : cheat1.getId().replaceAll(" ", "")) + (cheat1.getMode() != null ? " " + String.format(modeFormatted, cheat1.getMode()) : ""))
								: mc.fontRendererObj.getStringWidth(
				(keepSpaces.getValue() ? cheat1.getId() : cheat1.getId().replaceAll(" ", "")) + (cheat1.getMode() != null ? " " + String.format(modeFormatted, cheat1.getMode()) : ""))));

		return toggledCheats;
	}

	public double getLongestToggledLabelLength() {
		double longestLabelLength = 0;
		for (Module cheat : getToggledCheats()) {
			if (font == null)
				return 0;
			final double labelLength =
					(fonts.getValue().get("Minecraft") ?
							mc.fontRendererObj.getStringWidth(cheat.getId()) :
							font.getStringWidth(cheat.getId()));
			if (labelLength > longestLabelLength) {
				longestLabelLength = labelLength;
			}
		}
		return longestLabelLength;
	}

}