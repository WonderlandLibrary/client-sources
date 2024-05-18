package de.labystudio.utils;

import de.labystudio.labymod.LabyMod;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class WebPanel extends GuiScreen
{
    private GuiScreen multiplayerScreen;
    private String pin;
    private GuiTextField field;

    public static void open(String message, GuiScreen multiplayerScreen)
    {
        message = Color.removeColor(message);
        System.out.println(message);

        if (message.contains("Created PIN for ") || message.contains("You need LabyMod to register"))
        {
            if (message.contains(":"))
            {
                Minecraft.getMinecraft().displayGuiScreen(new WebPanel(message.split("\n")[1], multiplayerScreen));
            }
        }
    }

    public WebPanel(String pin, GuiScreen multiplayerScreen)
    {
        this.pin = pin;
        this.multiplayerScreen = multiplayerScreen;
        LabyMod.getInstance().openWebpage("http://www.labymod.net/key/?id=" + LabyMod.getInstance().getPlayerUUID().toString() + "&pin=" + this.pin, false);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(5, this.width / 2 - 120, this.height / 2 + 10, 170, 20, "Okay"));
        this.buttonList.add(new GuiButton(6, this.width / 2 + 55, this.height / 2 + 10, 75, 20, "Not working?"));
        this.field = new GuiTextField(0, this.mc.fontRendererObj, this.width / 2 - 100, this.height / 2 + 35, 200, 20);
        this.field.setVisible(false);
        this.field.setMaxStringLength(640);
        this.field.setText("http://www.labymod.net/key/?id=" + LabyMod.getInstance().getPlayerUUID().toString() + "&pin=" + this.pin);
        super.initGui();
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 5)
        {
            Minecraft.getMinecraft().displayGuiScreen(this.multiplayerScreen);
        }

        if (button.id == 6)
        {
            this.field.setVisible(true);
            this.field.setFocused(true);
            this.field.setCursorPositionZero();
            this.field.setSelectionPos(this.field.getMaxStringLength() - 1);
            button.enabled = false;
        }

        super.actionPerformed(button);
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        this.field.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        this.field.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawBackground(0);
        LabyMod.getInstance().draw.drawCenteredString(Color.cl("a") + "A new tab containing a form to register", this.width / 2, this.height / 2 - 20);
        LabyMod.getInstance().draw.drawCenteredString(Color.cl("a") + "your account has been opened in your browser", this.width / 2, this.height / 2 - 10);
        this.field.drawTextBox();

        if (this.field.getVisible())
        {
            LabyMod.getInstance().draw.drawCenteredString(Color.cl("c") + "Open this link in your Browser", this.width / 2, this.height / 2 + 60);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
