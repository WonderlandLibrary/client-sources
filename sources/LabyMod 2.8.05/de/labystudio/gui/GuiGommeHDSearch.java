package de.labystudio.gui;

import de.labystudio.gommehd.GommeHDSign;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class GuiGommeHDSearch extends GuiScreen
{
    private DrawUtils draw;
    private GuiTextField field_146302_g;
    private GuiTextField field_146302_g2;
    private GuiTextField field_146302_g3;
    GuiButton gommeSeachAllowedButton;
    GuiButton gommeSoundButton;
    GuiButton gommeNightMode;
    GuiButton buttonClear;

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.draw = LabyMod.getInstance().draw;
        this.buttonList.clear();
        boolean flag = true;
        this.buttonClear = new GuiButton(0, this.width / 2 + 81, this.height / 2 - 53, 20, 20, Color.c + "4" + "X");
        this.buttonList.add(this.buttonClear);
        this.field_146302_g = new GuiTextField(2, this.draw.fontRenderer, this.width / 2 - 99, this.height / 2 - 53, 178, 20);
        this.field_146302_g.setFocused(true);
        this.field_146302_g.setText(GommeHDSign.search);
        this.field_146302_g.setMaxStringLength(400);
        this.buttonClear.enabled = this.field_146302_g.getText().length() > 0 && this.field_146302_g.getText().split(":").length > 0;
        this.field_146302_g2 = new GuiTextField(2, this.draw.fontRenderer, this.width / 2 + 10, this.height / 2 - 12, 90, 20);
        this.field_146302_g2.setText(GommeHDSign.partySize + "");
        this.field_146302_g2.setMaxStringLength(2);
        this.field_146302_g3 = new GuiTextField(8, this.draw.fontRenderer, this.width / 2 + 10, this.height / 2 + 29, 90, 20);
        this.field_146302_g3.setText(GommeHDSign.blacklist + "");
        this.field_146302_g3.setMaxStringLength(400);
        this.gommeSeachAllowedButton = new GuiButton(1, this.width / 2 - 100, this.height / 2 - 12, 90, 20, this.getSymbol(GommeHDSign.allowed));
        this.buttonList.add(this.gommeSeachAllowedButton);
        this.gommeSoundButton = new GuiButton(3, this.width / 2 - 100, this.height / 2 + 27, 90, 20, this.getSymbol(GommeHDSign.sound));
        this.buttonList.add(this.gommeSoundButton);
        this.gommeNightMode = new GuiButton(4, this.width / 2 - 100, this.height / 2 + 65, 90, 20, this.getSymbol(GommeHDSign.nightMode));
        this.buttonList.add(this.gommeNightMode);
        GuiButton guibutton = new GuiButton(-1, this.width - 53, this.height - 23, 50, 20, "Search");
        guibutton.enabled = false;
        this.buttonList.add(guibutton);
    }

    public String getSymbol(boolean b)
    {
        return b ? Color.c + "a\u2714 Enabled" : Color.c + "4\u2716 Disabled";
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 0:
                this.field_146302_g.setText("");
                this.buttonClear.enabled = false;
                break;

            case 1:
                GommeHDSign.allowed = !GommeHDSign.allowed;

                if (!GommeHDSign.allowed)
                {
                    GommeHDSign.autoJoin = false;
                    GommeHDSign.sound = false;
                }

                button.displayString = this.getSymbol(GommeHDSign.allowed);
                button.displayString = this.getSymbol(GommeHDSign.allowed);
                this.gommeSoundButton.displayString = this.getSymbol(GommeHDSign.sound);
                break;

            case 2:
                button.displayString = this.getSymbol(GommeHDSign.autoJoin);
                break;

            case 3:
                GommeHDSign.sound = !GommeHDSign.sound;
                button.displayString = this.getSymbol(GommeHDSign.sound);
                break;

            case 4:
                GommeHDSign.nightMode = !GommeHDSign.nightMode;
                button.displayString = this.getSymbol(GommeHDSign.nightMode);
        }

        this.save();
    }

    public void back()
    {
        this.save();

        if (LabyMod.getInstance().isInGame())
        {
            this.mc.displayGuiScreen(new GuiIngameMenu());
        }
        else
        {
            this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
        }
    }

    public void save()
    {
        GommeHDSign.search = this.field_146302_g.getText();
        GommeHDSign.blacklist = this.field_146302_g3.getText();

        if (!this.field_146302_g2.getText().isEmpty())
        {
            if (isNumeric(this.field_146302_g2.getText()))
            {
                GommeHDSign.partySize = Integer.parseInt(this.field_146302_g2.getText());
            }
            else
            {
                this.field_146302_g2.setText("0");
            }
        }
        else
        {
            GommeHDSign.partySize = 0;
        }
    }

    public static boolean isNumeric(String str)
    {
        for (char c0 : str.toCharArray())
        {
            if (!Character.isDigit(c0))
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        if (this.field_146302_g.textboxKeyTyped(typedChar, keyCode))
        {
            this.buttonClear.enabled = this.field_146302_g.getText().length() > 0 && this.field_146302_g.getText().split(":").length > 0;
            this.save();
        }

        if (this.field_146302_g3.textboxKeyTyped(typedChar, keyCode))
        {
            this.save();
        }

        if (!isNumeric(typedChar + "") && keyCode != 14)
        {
            if (keyCode == 1)
            {
                this.mc.displayGuiScreen((GuiScreen)null);
            }
        }
        else if (this.field_146302_g2.textboxKeyTyped(typedChar, keyCode))
        {
            this.save();
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.field_146302_g.mouseClicked(mouseX, mouseY, mouseButton);
        this.field_146302_g2.mouseClicked(mouseX, mouseY, mouseButton);
        this.field_146302_g3.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.gommeSoundButton.enabled = GommeHDSign.allowed;
        this.draw.drawCenteredString("" + Color.c + "fGommeHD Map Search", this.width / 2, this.height / 2 - 70);
        this.draw.drawString("" + Color.c + "fColored Signs:", (double)(this.width / 2 - 99), (double)(this.height / 2 - 24));
        this.draw.drawString("" + Color.c + "fParty size:", (double)(this.width / 2 + 10), (double)(this.height / 2 - 24));
        this.draw.drawString("" + Color.c + "fBlacklist:", (double)(this.width / 2 + 10), (double)(this.height / 2 + 17));
        this.draw.drawString("" + Color.c + "fSounds:", (double)(this.width / 2 - 99), (double)(this.height / 2 + 17));
        this.draw.drawString("" + Color.c + "fNightmode:", (double)(this.width / 2 - 99), (double)(this.height / 2 + 55));
        this.draw.drawBox(this.width / 2 + 80, this.height / 2 - 54, this.width / 2 - 99 + 201, this.height / 2 - 53 + 21);
        this.field_146302_g.drawTextBox();
        this.field_146302_g2.drawTextBox();
        this.field_146302_g3.drawTextBox();

        if (!GommeHDSign.search.isEmpty() && GommeHDSign.search.toLowerCase().contains(GommeHDSign.blacklist.toLowerCase()))
        {
            if (GommeHDSign.blacklist.length() < 13)
            {
                this.draw.drawString(Color.c + "c" + GommeHDSign.blacklist, (double)(this.width / 2 + 14), (double)(this.height / 2 + 35));
            }

            if (GommeHDSign.search.length() < 22)
            {
                this.draw.drawString(GommeHDSign.search.replace(GommeHDSign.blacklist, Color.c + "c" + GommeHDSign.blacklist + Color.c + "f"), (double)(this.width / 2 - 95), (double)(this.height / 2 - 47));
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
