package de.labystudio.gui;

import de.labystudio.labymod.ConfigManager;
import de.labystudio.modapi.ModManager;
import de.labystudio.utils.Color;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiCompModSettings extends GuiScreen
{
    GuiScreen lastScreen;
    GuiButton cancel;

    public GuiCompModSettings(GuiScreen lastScreen)
    {
        this.lastScreen = lastScreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.cancel = new GuiButton(0, this.width / 2 - 100, this.height / 6 + 168, "Done");
        this.buttonList.add(this.cancel);
        int i = 1;
        boolean flag = true;
        int j = 0;

        for (String s : ModManager.getSettings().keySet())
        {
            int k = -100;
            int l = 98;

            if (!flag)
            {
                k = 2;
            }
            else if (ModManager.getSettings().keySet().size() == i)
            {
                l = 200;
            }

            if (i <= 12)
            {
                GuiButton guibutton = new GuiButton(i, this.width / 2 + k, 70 + j, l, 20, s);
                guibutton.enabled = ConfigManager.settings.api;
                this.buttonList.add(guibutton);

                if (!flag)
                {
                    j += 22;
                }

                flag = !flag;
                ++i;
            }
        }

        super.initGui();
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == 1)
        {
            this.mc.displayGuiScreen(this.lastScreen);
        }
        else
        {
            super.keyTyped(typedChar, keyCode);
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        int i = 1;

        for (GuiScreen guiscreen : ModManager.getSettings().values())
        {
            if (button.id == i)
            {
                ModManager.updateLastScreen(this);
                this.mc.displayGuiScreen(guiscreen);
                break;
            }

            ++i;
        }

        if (button.id == 0)
        {
            this.mc.displayGuiScreen(this.lastScreen);
        }
        else
        {
            super.actionPerformed(button);
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        String s = "s";

        if (ModManager.getSettings().size() == 1)
        {
            s = "";
        }

        this.drawCenteredString(this.fontRendererObj, ConfigManager.settings.api ? "Mod Settings (" + ModManager.getSettings().size() + " Mod" + s + ")" : Color.cl("c") + "LabyMod API is disabled! (LabyMod Settings -> Extras -> LabyMod API)", this.width / 2, 15, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
