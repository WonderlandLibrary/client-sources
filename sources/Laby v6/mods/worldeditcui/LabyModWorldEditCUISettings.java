package mods.worldeditcui;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class LabyModWorldEditCUISettings extends GuiScreen
{
    private LabyModWorldEditCUI controller;

    public LabyModWorldEditCUISettings(LabyModWorldEditCUI controller)
    {
        this.controller = controller;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.buttonList.add(new GuiButton(1, 2, 2, 60, 20, "WorldEditCUI: " + this.booleanToString(this.controller.visible)));
        this.buttonList.add(new GuiButton(2, 2, 24, 60, 20, "CLEAR SELECTION"));
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if (button.id == 1)
        {
            this.controller.visible = !this.controller.visible;
            button.displayString = "WorldEditCUI: " + this.booleanToString(this.controller.visible);
        }

        if (button.id == 2 && this.mc.thePlayer != null)
        {
            this.mc.thePlayer.sendChatMessage("//sel");
        }
    }

    private String booleanToString(boolean bool)
    {
        return bool ? "\u00a7a\u00a7lON" : "\u00a7c\u00a7lOFF";
    }
}
