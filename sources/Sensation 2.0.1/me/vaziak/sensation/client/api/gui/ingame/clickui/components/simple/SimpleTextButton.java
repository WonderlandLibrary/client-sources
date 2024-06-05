package me.vaziak.sensation.client.api.gui.ingame.clickui.components.simple;

import net.minecraft.util.ResourceLocation;

import java.awt.*;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.gui.ingame.clickui.Interface;
import me.vaziak.sensation.client.api.gui.ingame.clickui.components.base.BaseButton;
import me.vaziak.sensation.utils.anthony.Draw;
import me.vaziak.sensation.utils.anthony.basicfont.FontRenderer;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;

/**
 * @author antja03
 */
public class SimpleTextButton extends BaseButton {

    private Color buttonColor;
    private Color buttonColorHovered;
    private Color textColor;
    private String text;
    private FontRenderer fontRenderer;

    public SimpleTextButton(Interface theInterface, Color buttonColor, Color buttonColorHovered, String text, int fontSize, Color textColor, double positionX, double positionY, double maxWidth, double maxHeight, Action action) {
        super(theInterface, positionX, positionY, maxWidth, maxHeight, action);
        this.buttonColor = buttonColor;
        this.buttonColorHovered = buttonColorHovered;
        this.text = text;
        this.textColor = textColor;
        this.fontRenderer = new FontRenderer(Fonts.fontFromTTF(new ResourceLocation("client/verdana.ttf"), fontSize, Font.PLAIN), true, true);
    }

    public void drawComponent(double x, double y) {
        this.positionX = x - theInterface.getPositionX();
        this.positionY = y - theInterface.getPositionY();

        int color = isMouseOver() ?
                Sensation.instance.userInterface.theInterface.getColor(buttonColorHovered.getRed(), buttonColorHovered.getGreen(), buttonColorHovered.getBlue(), buttonColorHovered.getAlpha())
                : Sensation.instance.userInterface.theInterface.getColor(buttonColor.getRed(), buttonColor.getGreen(), buttonColor.getBlue(), buttonColor.getAlpha());

        Draw.drawRectangle(x, y, x + maxWidth, y + maxHeight, color);
        fontRenderer.drawCenteredString(text, x + maxWidth / 2, y + (maxHeight / 2) - (fontRenderer.getStringHeight(text) / 2),
                Sensation.instance.userInterface.theInterface.getColor(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), textColor.getAlpha()));
    }

}
