package de.labystudio.gui;

import de.labystudio.gui.extras.GuiCustomButton;
import de.labystudio.gui.extras.ModGuiTextField;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.AutoTextLoader;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiAutoText extends GuiScreen
{
    private DrawUtils draw;
    private ModGuiTextField commandInput;
    private GuiTextField chatField;
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

    public GuiAutoText(String text)
    {
        this.text = text;
    }

    public void addSymbol(String symbol)
    {
        GuiCustomButton guicustombutton = new GuiCustomButton(-1, this.width - 4 - 20 - this.z, 4 + this.y, 20, 20, symbol);
        guicustombutton.run = "true";
        this.buttonList.add(guicustombutton);
        this.z += 24;

        if (this.z % (24 * this.lines) == 0)
        {
            this.z = 0;
            this.y += 24;
        }
    }

    public void initAutoText()
    {
        this.buttons.clear();
        AutoTextLoader.listening = false;
        AutoTextLoader.key = -1;
        AutoTextLoader.alt = false;
        AutoTextLoader.shift = false;
        AutoTextLoader.ctrl = false;
        Object object = null;

        for (String s : AutoTextLoader.autoText.keySet())
        {
            GuiCustomButton guicustombutton = new GuiCustomButton(-3, 0, 0, 20, 20, Color.cl("c") + "X");
            guicustombutton.run = s;
            this.buttonList.add(guicustombutton);
            this.buttons.add(guicustombutton);
        }
    }

    public void drawAutoText()
    {
        int i = 25 + this.scroll;
        int j = 0;

        for (String s : AutoTextLoader.autoText.keySet())
        {
            if (j < this.buttons.size())
            {
                GuiCustomButton guicustombutton = (GuiCustomButton)this.buttons.get(j);
                guicustombutton.visible = 6 + i > 15 && 6 + i < 160;

                if (guicustombutton.visible)
                {
                    guicustombutton.xPosition = this.width - 190;
                    guicustombutton.yPosition = 6 + i;
                    DrawUtils drawutils = this.draw;
                    DrawUtils.drawRect(this.width - 170, 6 + i, this.width - 7, 6 + i + 20, Integer.MIN_VALUE);
                    String s1 = "";

                    if (AutoTextLoader.isAlt(s))
                    {
                        s1 = s1 + "Alt+";
                    }

                    if (AutoTextLoader.isShift(s))
                    {
                        s1 = s1 + "Shift+";
                    }

                    if (AutoTextLoader.isCtrl(s))
                    {
                        s1 = s1 + "Ctrl+";
                    }

                    this.draw.drawString(Color.cl("7") + "[" + Color.cl("c") + s1 + Keyboard.getKeyName(AutoTextLoader.getNormalKey(s)) + Color.cl("7") + "] " + Color.cl("e") + (String)AutoTextLoader.autoText.get(s), (double)(this.width - 167), (double)(12 + i));
                }

                this.allowScroll = 6 + i > 160;
                i += 22;
                ++j;
            }
        }

        if (AutoTextLoader.autoText.size() < 5)
        {
            this.scroll = 0;
        }
    }

    public void add()
    {
        if (this.addButton.enabled)
        {
            String s = "";

            if (AutoTextLoader.alt)
            {
                s = s + "#ALT";
            }

            if (AutoTextLoader.ctrl)
            {
                s = s + "#CTRL";
            }

            if (AutoTextLoader.shift)
            {
                s = s + "#SHIFT";
            }

            String s1;

            for (s1 = s + AutoTextLoader.key; AutoTextLoader.autoText.containsKey(s1); s1 = s1 + ";")
            {
                ;
            }

            AutoTextLoader.autoText.put(s1, this.commandInput.getText());
            this.initAutoText();
            AutoTextLoader.save();
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        this.commandInput.mouseClicked(mouseX, mouseY, mouseButton);
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

        if (this.commandInput.isFocused() && !AutoTextLoader.listening)
        {
            this.commandInput.textboxKeyTyped(typedChar, keyCode);
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
        this.addButton = new GuiButton(1, this.width - 192, 4, 30, 20, "");
        this.buttonList.add(this.addButton);
        this.buttonList.add(new GuiButton(0, this.width - 48, 4, 45, 20, Color.cl("c") + "Close"));
        this.help = new GuiButton(-1, this.width - 215, 4, 20, 20, Color.cl("b") + "?");
        this.buttonList.add(this.help);
        String s = "\u2716";

        if (AutoTextLoader.enabled)
        {
            s = "\u2714";
        }

        s = Color.booleanToColor(Boolean.valueOf(AutoTextLoader.enabled)) + s;
        this.toggle = new GuiButton(2, this.width - 215, 27, 20, 20, s);
        this.buttonList.add(this.toggle);
        this.initAutoText();
        Keyboard.enableRepeatEvents(true);
        this.chatField = new GuiTextField(0, this.fontRendererObj, 4, this.height - 12, this.width - 4, 12);
        this.chatField.setMaxStringLength(500);
        this.chatField.setEnableBackgroundDrawing(false);
        this.chatField.setFocused(true);
        this.chatField.setText(this.text);
        this.chatField.setCursorPositionEnd();
        this.commandInput = new ModGuiTextField(0, this.fontRendererObj, this.width - 160, 6, 110, 17);
        this.commandInput.setMaxStringLength(100);
        this.commandInput.setFocused(false);
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
                if (!this.commandInput.getText().replace(" ", "").isEmpty())
                {
                    AutoTextLoader.key = -1;
                    AutoTextLoader.listening = true;
                    AutoTextLoader.alt = false;
                    AutoTextLoader.shift = false;
                    AutoTextLoader.ctrl = false;
                }

                break;

            case 2:
                AutoTextLoader.enabled = !AutoTextLoader.enabled;
                this.initGui();
        }

        if (button.id == -5)
        {
            this.chatField.textboxKeyTyped("&".charAt(0), 0);
            this.chatField.textboxKeyTyped(button.displayString.replace(Color.c + "", "").substring(0, 1).charAt(0), 0);
        }

        if (button instanceof GuiCustomButton)
        {
            if (((GuiCustomButton)button).run.equals("true"))
            {
                this.chatField.textboxKeyTyped(button.displayString.charAt(0), 0);
            }

            if (button.id == -3)
            {
                AutoTextLoader.autoText.remove(((GuiCustomButton)button).run);
                this.buttons.remove(button);
                this.initGui();
                AutoTextLoader.save();
            }
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
        this.addButton.enabled = !this.commandInput.getText().replace(" ", "").isEmpty();
        this.chatField.drawTextBox();
        this.commandInput.drawTextBox();
        this.drawAutoText();

        if (AutoTextLoader.listening)
        {
            this.addButton.displayString = "> <";

            if (AutoTextLoader.key != -1)
            {
                if (!this.contains(AutoTextLoader.key + ""))
                {
                    this.add();
                }

                AutoTextLoader.key = -1;
                AutoTextLoader.listening = false;
                AutoTextLoader.alt = false;
                AutoTextLoader.shift = false;
                AutoTextLoader.ctrl = false;
                this.commandInput.setText("");
            }
        }
        else
        {
            this.addButton.displayString = Color.cl("a") + "Add";
        }

        super.drawScreen(mouseX, mouseY, partialTicks);

        if (this.help.isMouseOver())
        {
            this.draw.drawRightString("First write a sentence then enter your hotkey", (double)mouseX, (double)mouseY);
            this.draw.drawRightString("and by pressing the hotkey during gameplay", (double)mouseX, (double)(mouseY + 10));
            this.draw.drawRightString("your sentence will be written in the chat!", (double)mouseX, (double)(mouseY + 20));
        }

        if (this.toggle.isMouseOver())
        {
            if (AutoTextLoader.enabled)
            {
                this.draw.drawRightString(Color.cl("a") + "AutoText enabled", (double)mouseX, (double)mouseY);
            }
            else
            {
                this.draw.drawRightString(Color.cl("c") + "AutoText disabled", (double)mouseX, (double)mouseY);
            }
        }
    }

    private boolean contains(String i)
    {
        for (String s : AutoTextLoader.autoText.keySet())
        {
            if (s.equals(i))
            {
                return true;
            }
        }

        return false;
    }
}
