package net.minecraft.client.gui;

import net.minecraft.client.settings.GameSettings;

public class GuiOptionButton extends GuiButton
{
    private final GameSettings.Options enumOptions;

    public GuiOptionButton(int buttonID, int posX, int posY, String buttonText)
    {
        this(buttonID, posX, posY, null, buttonText);
    }

    public GuiOptionButton(int buttonID, int posX, int posY, int width, int height, String buttonText)
    {
        super(buttonID, posX, posY, width, height, buttonText);
        this.enumOptions = null;
    }

    public GuiOptionButton(int buttonID, int posX, int posY, GameSettings.Options enumOptions, String buttonText)
    {
        super(buttonID, posX, posY, 150, 20, buttonText);
        this.enumOptions = enumOptions;
    }

    public GameSettings.Options returnEnumOptions()
    {
        return this.enumOptions;
    }
}
