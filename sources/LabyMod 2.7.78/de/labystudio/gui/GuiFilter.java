package de.labystudio.gui;

import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import de.labystudio.utils.FilterLoader;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiFilter extends GuiScreen
{
    private DrawUtils draw;
    private GuiTextField inputField;
    private GuiTextField input;
    private GuiButton addButton;
    private GuiButton toggle;
    private GuiButton help;
    private boolean allowScroll;
    private String text;
    private ArrayList<GuiButton> buttons = new ArrayList();
    int z = 0;
    int y = 0;
    int lines = 8;
    int scroll = 0;

    public GuiFilter(String text)
    {
        this.text = text;
    }

    public void addSymbol(String symbol)
    {
        GuiButton guibutton = new GuiButton(-1, this.width - 4 - 20 - this.z, 4 + this.y, 20, 20, symbol);
        guibutton.run = "true";
        this.buttonList.add(guibutton);
        this.z += 24;

        if (this.z % (24 * this.lines) == 0)
        {
            this.z = 0;
            this.y += 24;
        }
    }

    public void initFilters()
    {
        this.buttons.clear();

        for (String s : FilterLoader.filters)
        {
            GuiButton guibutton = new GuiButton(-3, 0, 0, 20, 20, Color.cl("c") + "X");
            guibutton.run = s;
            this.buttonList.add(guibutton);
            this.buttons.add(guibutton);
        }
    }

    public void drawFilters()
    {
        int i = 25 + this.scroll;
        int j = 0;

        for (String s : FilterLoader.filters)
        {
            if (j < this.buttons.size())
            {
                GuiButton guibutton = (GuiButton)this.buttons.get(j);
                guibutton.visible = 6 + i > 15 && 6 + i < 160;

                if (guibutton.visible)
                {
                    guibutton.xPosition = this.width - 190;
                    guibutton.yPosition = 6 + i;
                    DrawUtils drawutils = this.draw;
                    DrawUtils.drawRect(this.width - 170, 6 + i, this.width - 7, 6 + i + 20, Integer.MIN_VALUE);
                    this.draw.drawString(s.replace("%b%", " | " + Color.cl("c")).replace("%k%", Color.cl("6") + "").replace("%s%", Color.cl("b") + " (Sound) "), (double)(this.width - 167), (double)(12 + i));
                }

                this.allowScroll = 6 + i > 160;
                i += 22;
                ++j;
            }
        }

        if (FilterLoader.filters.size() < 5)
        {
            this.scroll = 0;
        }
    }

    public void add()
    {
        if (this.addButton.enabled)
        {
            FilterLoader.filters.add(this.input.getText());
            this.input.setText("");
            this.initFilters();
            FilterLoader.saveFilters();
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        this.input.mouseClicked(mouseX, mouseY, mouseButton);
        this.inputField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    public void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);

        if (this.inputField.isFocused())
        {
            this.text = this.inputField.getText();

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
                        this.inputField.textboxKeyTyped(typedChar, keyCode);
                    }
                }
            }
            else
            {
                String s = this.inputField.getText().trim();

                if (s.length() > 0)
                {
                    this.sendChatMessage(s);
                }

                this.mc.displayGuiScreen((GuiScreen)null);
            }
        }

        if (this.input.isFocused())
        {
            if (keyCode == 1)
            {
                this.mc.displayGuiScreen((GuiScreen)null);
            }
            else if (keyCode == 28)
            {
                this.add();
            }
            else
            {
                this.input.textboxKeyTyped(typedChar, keyCode);
            }
        }
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();

        if (i != 0)
        {
            if (i > 1)
            {
                i = 1;
            }

            if (i < -1)
            {
                i = -1;
            }

            if (i > 0)
            {
                if (this.scroll < 0)
                {
                    this.scroll += 22;
                }
            }
            else if (this.allowScroll)
            {
                this.scroll -= 22;
            }
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.buttonList.clear();
        this.addButton = new GuiButton(1, this.width - 192, 4, 30, 20, Color.cl("c") + "Add");
        this.buttonList.add(this.addButton);
        this.buttonList.add(new GuiButton(0, this.width - 48, 4, 45, 20, Color.cl("c") + "Close"));
        this.help = new GuiButton(-1, this.width - 215, 4, 20, 20, Color.cl("b") + "?");
        this.buttonList.add(this.help);
        String s = "\u2716";

        if (FilterLoader.enabled)
        {
            s = "\u2714";
        }

        s = Color.booleanToColor(Boolean.valueOf(FilterLoader.enabled)) + s;
        this.toggle = new GuiButton(2, this.width - 215, 27, 20, 20, s);
        this.buttonList.add(this.toggle);
        this.initFilters();
        Keyboard.enableRepeatEvents(true);
        this.inputField = new GuiTextField(0, this.fontRendererObj, 4, this.height - 12, this.width - 4, 12);
        this.inputField.setMaxStringLength(500);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.text);
        this.input = new GuiTextField(0, this.fontRendererObj, this.width - 160, 6, 110, 17);
        this.input.setMaxStringLength(500);
        this.input.setFocused(false);
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

            case 1:
                this.add();
                break;

            case 2:
                FilterLoader.enabled = !FilterLoader.enabled;
                this.initGui();
        }

        if (button.id == -5)
        {
            this.inputField.textboxKeyTyped("&".charAt(0), 0);
            this.inputField.textboxKeyTyped(button.displayString.replace(Color.c + "", "").substring(0, 1).charAt(0), 0);
        }

        if (button.run.equals("true"))
        {
            this.inputField.textboxKeyTyped(button.displayString.charAt(0), 0);
        }

        if (button.id == -3)
        {
            FilterLoader.filters.remove(button.run);
            this.buttons.remove(button);
            this.initGui();
            FilterLoader.saveFilters();
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.draw = LabyMod.getInstance().draw;
        drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        drawRect(this.width - 193, 27, this.width - 3, 169, Integer.MIN_VALUE);
        drawRect(this.width - 193, 3, this.width - 3, 25, Integer.MIN_VALUE);
        this.addButton.enabled = !this.input.getText().replace(" ", "").isEmpty() && !this.contains(this.input.getText());
        this.inputField.drawTextBox();
        this.input.drawTextBox();
        this.drawFilters();
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (this.help.isMouseOver())
        {
            int i = 0;
            this.draw.drawRightString("If a word of this list is written", (double)mouseX, (double)mouseY);
            i = i + 10;
            this.draw.drawRightString("in the chat, it will automatically", (double)mouseX, (double)(mouseY + i));
            i = i + 10;
            this.draw.drawRightString("be displayed in an extra chat to ", (double)mouseX, (double)(mouseY + i));
            i = i + 10;
            this.draw.drawRightString("the right of the normal chat.", (double)mouseX, (double)(mouseY + i));
            i = i + 10;
            this.draw.drawRightString("If you add " + Color.cl("b") + "%k%" + Color.cl("f") + " in front of a specific word,", (double)mouseX, (double)(mouseY + i), 0.5D);
            i = i + 5;
            this.draw.drawRightString("you want to filter out of the chat " + Color.cl("c") + "(e.g. %k%Test)" + Color.cl("f") + ",", (double)mouseX, (double)(mouseY + i), 0.5D);
            i = i + 5;
            this.draw.drawRightString("the message will be highlighted and WON\'T be shown on the extra Chat.", (double)mouseX, (double)(mouseY + i), 0.5D);
            i = i + 10;
            this.draw.drawRightString("To avoid specific words from a message,", (double)mouseX, (double)(mouseY + i), 0.5D);
            i = i + 5;
            this.draw.drawRightString("you can create a blacklist with " + Color.cl("b") + "%b%" + Color.cl("f") + ". " + Color.cl("c") + "(e.g. Test%b%Hello)" + Color.cl("f") + "", (double)mouseX, (double)(mouseY + i), 0.5D);
            i = i + 5;
            this.draw.drawRightString("Now, the word \'Test\' would be filtered, but", (double)mouseX, (double)(mouseY + i), 0.5D);
            i = i + 5;
            this.draw.drawRightString("if the same message contains \'Hello\' it would be ignored.", (double)mouseX, (double)(mouseY + i), 0.5D);
            i = i + 5;
            this.draw.drawRightString("This you can do also with more Words", (double)mouseX, (double)(mouseY + i), 0.5D);
            i = i + 5;
            this.draw.drawRightString("" + Color.cl("c") + "(e.g. Test%b%Hello%b%How are you%b%Minecraft)" + Color.cl("f") + "", (double)mouseX, (double)(mouseY + i), 0.5D);
            i = i + 10;
            this.draw.drawRightString("To get a Sound alert on specific Messages,", (double)mouseX, (double)(mouseY + i), 0.5D);
            i = i + 5;
            this.draw.drawRightString("just add " + Color.cl("b") + "%s%" + Color.cl("f") + " at the end of the word " + Color.cl("c") + "(e.g. Test%s%)", (double)mouseX, (double)(mouseY + i), 0.5D);
            i = i + 5;
            this.draw.drawRightString("Now you would hear a sound, when \'Test\' is written in the Chat.", (double)mouseX, (double)(mouseY + i), 0.5D);
            i = i + 10;
            this.draw.drawRightString("The whole thing with " + Color.cl("b") + "%k%" + Color.cl("f") + ", " + Color.cl("b") + "%b%" + Color.cl("f") + " and " + Color.cl("b") + "%s%" + Color.cl("f") + " can also be combined:", (double)mouseX, (double)(mouseY + i), 0.5D);
            i = i + 5;
            this.draw.drawRightString("" + Color.cl("c") + "%k%Test%b%Hello%s%", (double)mouseX, (double)(mouseY + i), 0.5D);
            i = i + 5;
            this.draw.drawRightString("->Now, the message would be", (double)mouseX, (double)(mouseY + i), 0.5D);
            i = i + 5;
            this.draw.drawRightString("highlighted and you can hear a sound alert,", (double)mouseX, (double)(mouseY + i), 0.5D);
            i = i + 5;
            this.draw.drawRightString("if it isn\'t containing the word \'Hello\'", (double)mouseX, (double)(mouseY + i), 0.5D);
            i = i + 5;
        }

        if (this.toggle.isMouseOver())
        {
            if (FilterLoader.enabled)
            {
                this.draw.drawRightString(Color.cl("a") + "Filter enabled", (double)mouseX, (double)mouseY);
            }
            else
            {
                this.draw.drawRightString(Color.cl("c") + "Filter disabled", (double)mouseX, (double)mouseY);
            }
        }
    }

    private boolean contains(String s)
    {
        for (String s1 : FilterLoader.filters)
        {
            if (s1.equalsIgnoreCase(s1))
            {
                return true;
            }
        }

        return false;
    }
}
