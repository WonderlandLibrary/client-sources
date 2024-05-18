package de.labystudio.gui;

import de.labystudio.gui.extras.ModGuiTextField;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import de.labystudio.utils.NameHistory;
import de.labystudio.utils.NameMCUtil;
import de.labystudio.utils.Scrollbar;
import de.labystudio.utils.UUIDFetcher;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class GuiNameHistory extends GuiScreen
{
    private DrawUtils draw;
    private ModGuiTextField search;
    private GuiTextField chatField;
    private GuiButton searchButton;
    private Scrollbar scroll;
    private NameHistory history;
    private String text;

    public GuiNameHistory(String text)
    {
        this.text = text;
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        this.search.mouseClicked(mouseX, mouseY, mouseButton);
        this.chatField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    public void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);

        if (this.chatField.isFocused())
        {
            this.text = this.chatField.getText();

            if (keyCode == 1)
            {
                this.mc.displayGuiScreen((GuiScreen)null);
            }
            else if (keyCode != 28 && keyCode != 156)
            {
                if (keyCode != 200 && keyCode != 208)
                {
                    if (keyCode == 201)
                    {
                        this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
                    }
                    else if (keyCode == 209)
                    {
                        this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
                    }
                    else
                    {
                        this.chatField.textboxKeyTyped(typedChar, keyCode);
                    }
                }
            }
            else
            {
                String s = this.chatField.getText().trim();

                if (s.length() > 0)
                {
                    this.sendChatMessage(s);
                }

                this.mc.displayGuiScreen((GuiScreen)null);
            }
        }

        if (this.search.isFocused())
        {
            if (keyCode == 28)
            {
                this.history = NameMCUtil.getNameHistory(this.search.getText());
            }

            this.search.textboxKeyTyped(typedChar, keyCode);
        }
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.chatField = new GuiTextField(0, this.fontRendererObj, 4, this.height - 12, this.width - 4, 12);
        this.chatField.setMaxStringLength(500);
        this.chatField.setEnableBackgroundDrawing(false);
        this.chatField.setFocused(true);
        this.chatField.setText(this.text);
        this.chatField.setCursorPositionEnd();
        this.search = new ModGuiTextField(0, this.fontRendererObj, this.width - 190, 5, 140, 18);
        this.search.setMaxStringLength(100);
        this.search.setFocused(false);
        this.searchButton = new GuiButton(24, this.width - 46, 4, 42, 20, "Search");
        this.buttonList.add(this.searchButton);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 0:
                this.mc.displayGuiScreen(new GuiChat(this.text));
                break;

            case 24:
                this.history = NameMCUtil.getNameHistory(this.search.getText());
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.draw = LabyMod.getInstance().draw;
        drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        drawRect(this.width - 193, 3, this.width - 3, 25, Integer.MIN_VALUE);

        if (this.history != null && this.history.getChanges().length > 1)
        {
            drawRect(this.width - 193, 27, this.width - 3, 35 + this.history.getChanges().length * 10, Integer.MIN_VALUE);
        }

        this.chatField.drawTextBox();
        this.search.drawTextBox();
        this.searchButton.enabled = !this.search.getText().isEmpty() && this.search.getText().length() > 2 && this.search.getText().length() <= 16;
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (this.history != null && this.history.getChanges().length != 0)
        {
            if (this.history.getChanges().length == 1)
            {
                this.draw.drawString(Color.cl("c") + "No name changes found", (double)(this.width - 190), 28.0D);
            }
            else
            {
                boolean flag = true;
                int i = 30;

                for (UUIDFetcher uuidfetcher : this.history.getChanges())
                {
                    if (uuidfetcher.changedToAt != 0L)
                    {
                        long j = System.currentTimeMillis() - uuidfetcher.changedToAt;
                        long k = j / 1000L;
                        long l = k / 60L;
                        long i1 = l / 60L;
                        long j1 = i1 / 24L;
                        long k1 = j1 / 31L;
                        long l1 = k1 / 12L;
                        String s = null;

                        if (k1 >= 12L)
                        {
                            s = l1 + " year" + (l1 == 1L ? "" : "s");
                        }
                        else if (j1 >= 31L)
                        {
                            s = k1 + " month" + (k1 == 1L ? "" : "s");
                        }
                        else if (i1 >= 24L)
                        {
                            s = j1 + " day" + (j1 == 1L ? "" : "s");
                        }
                        else if (l >= 60L)
                        {
                            s = i1 + " hour" + (i1 == 1L ? "" : "s");
                        }
                        else if (k >= 60L)
                        {
                            s = l + " min" + (l == 1L ? "" : "s");
                        }
                        else
                        {
                            s = k + "sec" + (k == 1L ? "" : "s");
                        }

                        s = s + " ago";

                        if (j < 0L)
                        {
                            s = "In the future!";
                        }

                        if (flag)
                        {
                            this.draw.drawString(Color.cl("a") + Color.cl("l") + "x " + Color.cl("7") + uuidfetcher.name + Color.cl("8") + " - " + Color.cl("e") + s, (double)(this.width - 190), (double)i);
                            flag = false;
                        }
                        else
                        {
                            this.draw.drawString(Color.cl("c") + Color.cl("l") + "x " + Color.cl("7") + uuidfetcher.name + Color.cl("8") + " - " + Color.cl("e") + s, (double)(this.width - 190), (double)i);
                        }
                    }
                    else
                    {
                        this.draw.drawString(Color.cl("4") + Color.cl("l") + "x " + Color.cl("7") + uuidfetcher.name, (double)(this.width - 190), (double)i);
                    }

                    i += 10;
                }
            }
        }
        else
        {
            this.draw.drawString(Color.cl("4") + "Not found", (double)(this.width - 190), 28.0D);
        }
    }
}
