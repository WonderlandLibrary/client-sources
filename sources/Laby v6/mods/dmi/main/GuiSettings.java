package mods.dmi.main;

import de.labystudio.labymod.LabyMod;
import de.labystudio.modapi.ModAPI;
import de.labystudio.utils.Color;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiSettings extends GuiScreen
{
    GuiButton LOGGER;
    GuiButton optionsBackground;
    SliderDMI slider;

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 6 + 150, "Done"));
        this.buttonList.add(this.LOGGER = new GuiButton(1, this.width / 2 - 100, this.height / 6 + 30, ""));
        this.buttonList.add(this.optionsBackground = new GuiButton(2, this.width / 2 - 100, this.height / 6 + 70, ""));
        this.buttonList.add(this.slider = new SliderDMI(3, this.width / 2 - 100, this.height / 6 + 110, 197));
        this.refresh();
    }

    public void refresh()
    {
        String s = Color.cl("a") + "Enabled";

        if (!Settings.settings.enabled)
        {
            s = Color.cl("c") + "Disabled";
        }

        this.LOGGER.displayString = s;
        s = "Hearts";

        if (!Settings.settings.DMILayout)
        {
            s = "Number";
        }

        this.optionsBackground.displayString = s;
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            Settings.save();
            this.mc.displayGuiScreen(ModAPI.getLastScreen());
        }

        if (button.id == 1)
        {
            Settings.settings.enabled = !Settings.settings.enabled;
            this.refresh();
        }

        if (button.id == 2)
        {
            Settings.settings.DMILayout = !Settings.settings.DMILayout;
            this.refresh();
        }

        super.actionPerformed(button);
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == 1)
        {
            Settings.save();
            this.mc.displayGuiScreen(ModAPI.getLastScreen());
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        double d0 = (double)(this.width / 2 - 100);
        int i = this.height / 6;
        LabyMod.getInstance().draw.drawString("Damage Indicator:", d0, (double)(i + 20));
        d0 = (double)(this.width / 2 - 100);
        i = this.height / 6;
        LabyMod.getInstance().draw.drawString("Layout:", d0, (double)(i + 60));
        d0 = (double)(this.width / 2 - 100);
        i = this.height / 6;
        LabyMod.getInstance().draw.drawString("Scale:", d0, (double)(i + 100));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
