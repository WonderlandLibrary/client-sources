package de.labystudio.gui;

import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class GuiSymbolSelector extends GuiScreen
{
    private DrawUtils draw;
    private GuiTextField inputField;
    private String text;
    int z = 0;
    int y = 0;
    int lines = 8;
    int page = 0;
    int per = 48;

    public GuiSymbolSelector(String text)
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

    public String getSymbols()
    {
        String s = "\u2764\u2765\u2714\u2716\u2717\u2718\u2742\u22c6\u2722\u2723\u2724\u2725\u2726\u2729\u272a\u272b\u272c\u272d\u272e\u272f\u2730\u2605\u2731\u2732\u2733\u2734\u2735\u2736\u2737\u2738\u2739\u273a\u273b\u273c\u2744\u2745\u2746\u2747\u2748\u2749\u274a\u274b\u2606\u2721\u2742\u273d\u273e\u273f\u2740\u2741\u2743\u274b\u270c\u267c\u267d\u2702\u2704\u2708\u27a1\u2b05\u2b06\u2b07\u279f\u27a1\u27a2\u27a3\u27a4\u27a5\u27a6\u27a7\u27a8\u279a\u2798\u2799\u279b\u279c\u279d\u279e\u27b8\u27b2\u27b3\u27b3\u27b4\u27b5\u27b6\u27b7\u27b8\u27b9\u27ba\u27bb\u27bc\u27bd\u24c2\u2b1b\u2b1c\u2588\u259b\u2580\u259c\u2586\u2584\u258c\u2615\u2139\u2122\u2691\u2690\u2603\u26a0\u2694\u2696\u2692\u2699\u269c\u2680\u2681\u2682\u2683\u2684\u2685\u268a\u268b\u268c\u268d\u268e\u268f\u2630\u2631\u2632\u2633\u2634\u2635\u2636\u2637\u2686\u2687\u2688\u2689\u267f\u2669\u266a\u266b\u266c\u266d\u266e\u266f\u2660\u2661\u2662\u2663\u2664\u2665\u2666\u2667\u2654\u2655\u2656\u2657\u2658\u2659\u265a\u265b\u265c\u265d\u265e\u265f\u26aa\u26ab\u262f\u262e\u2623\u260f\u2780\u2781\u2782\u2783\u2784\u2785\u2786\u2787\u2788\u2789\u278a\u278b\u278c\u278d\u278e\u278f\u2790\u2791\u2792\u2793\u24d0\u24d1\u24d2\u24d3\u24d4\u24d5\u24d6\u24d7\u24d8\u24d9\u24da\u24db\u24dc\u24dd\u24de\u24df\u24e0\u24e1\u24e2\u24e3\u24e4\u24e5\u24e6\u24e7\u24e8\u24e9\u2764\u2765\uc6c3\uc720\u264b\u262e\u270c\u260f\u2622\u2620\u2714\u2611\u265a\u25b2\u266a\u2708\u231a\u00bf\u2665\u2763\u2642\u2640\u263f\u24b6\u270d\u2709\u2623\u2624\u2718\u2612\u265b\u25bc\u266b\u2318\u231b\u00a1\u2661\u10e6\u30c4\u263c\u2601\u2745\u2652\u270e\u00a9\u00ae\u2122\u03a3\u272a\u272f\u262d\u27b3\u271e\u2103\u2109\u273f\u03df\u2603\u2602\u2704\u00a2\u00a3\u221e\u272b\u2605\u00bd\u262f\u2721\u262a\u273f\u263a\u263b\u2639\u263c\u2602\u2603\u2307\u269b\u2328\u2706\u260e\u2325\u21e7\u21a9\u271e\u2721\u262d\u2190\u2192\u2191\u2193\u27ab\u2b07\u2b06\u261c\u261e\u261d\u261f\u270d\u270e\u270c\u262e\u2714\u2605\u2606\u267a\u2691\u2690\u2709\u2704\u2332\u2708\u2666\u2663\u2660\u2665\u2764\u2661\u266a\u2669\u266b\u266c\u266f\u2640\u2642\u26a2\u26a3\u2751\u2752\u25c8\u25d0\u25d1\u2716\u221e\u00ab\u00bb\u2039\u203a\u2013\u2014\u2044\u00b6\u00a1\u00bf\u203d\u2042\u203b\u00b1\u00d7\u2248\u00f7\u2260\u03c0\u2020\u2021\u00a5\u20ac\u00a2\u00a3\u2122\u2030\u2026\u00b7\u2022\u25cf";
        String s1 = "";

        for (int i = 0; i < s.length(); ++i)
        {
            String s2 = s.charAt(i) + "";

            if (!s2.equals(" ") && !s1.contains(s2))
            {
                s1 = s1 + s2;
            }
        }

        return s1;
    }

    public void initSymbols()
    {
        String s = this.getSymbols();

        for (int i = 0; i <= this.buttonList.size() - 1; ++i)
        {
            GuiButton guibutton = (GuiButton)this.buttonList.get(i);

            if (guibutton.run.equals("true"))
            {
                guibutton.visible = false;
            }
        }

        GuiButton guibutton1 = new GuiButton(-1, this.width - 168, 4, 20, 20, Color.cl("a") + this.page);
        guibutton1.enabled = false;
        guibutton1.run = "false";
        this.buttonList.add(guibutton1);
        String s3 = s;

        try
        {
            try
            {
                s = s.substring(0 + this.page * this.per, this.per + this.page * this.per);
                ((GuiButton)this.buttonList.get(1)).enabled = true;
            }
            catch (Exception var7)
            {
                s = s.substring(0 + this.page * this.per, s3.length() - 1);
                ((GuiButton)this.buttonList.get(1)).enabled = false;
            }
        }
        catch (Exception var8)
        {
            ;
        }

        if (this.page == 0)
        {
            ((GuiButton)this.buttonList.get(0)).enabled = false;
        }
        else
        {
            ((GuiButton)this.buttonList.get(0)).enabled = true;
        }

        this.z = 0;
        this.y = 24;
        String s1 = "";

        for (int j = 0; j < s.length(); ++j)
        {
            String s2 = s.charAt(j) + "";

            if (!s2.equals(" "))
            {
                s1 = s1 + s2;
            }
        }

        for (int k = 0; k < s1.length(); ++k)
        {
            String s4 = s1.charAt(k) + "";

            if (!s4.equals(" "))
            {
                this.addSymbol(s4);
            }
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    public void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);

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

        this.text = this.inputField.getText();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.buttonList.add(new GuiButton(1, this.width - 192, 4, 20, 20, Color.cl("c") + "<"));
        this.buttonList.add(new GuiButton(2, this.width - 144, 4, 20, 20, Color.cl("c") + ">"));
        this.buttonList.add(new GuiButton(-1, this.width - 205, 148, 10, 20, ""));
        this.buttonList.add(new GuiButton(-1, this.width - 217, 45, 10, 20, ""));
        int i = 0;
        this.buttonList.add(new GuiButton(-5, this.width - 205, 4 + i, 10, 10, Color.cl("0") + "0"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 205, 4 + i, 10, 10, Color.cl("1") + "1"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 205, 4 + i, 10, 10, Color.cl("2") + "2"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 205, 4 + i, 10, 10, Color.cl("3") + "3"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 205, 4 + i, 10, 10, Color.cl("4") + "4"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 205, 4 + i, 10, 10, Color.cl("5") + "5"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 205, 4 + i, 10, 10, Color.cl("6") + "6"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 205, 4 + i, 10, 10, Color.cl("7") + "7"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 205, 4 + i, 10, 10, Color.cl("8") + "8"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 205, 4 + i, 10, 10, Color.cl("9") + "9"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 205, 4 + i, 10, 10, Color.cl("a") + "a"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 205, 4 + i, 10, 10, Color.cl("b") + "b"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 205, 4 + i, 10, 10, Color.cl("c") + "c"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 205, 4 + i, 10, 10, Color.cl("d") + "d"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 205, 4 + i, 10, 10, Color.cl("e") + "e"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 205, 4 + i, 10, 13, Color.cl("f") + "f"));
        i = i + 10;
        i = 0;
        this.buttonList.add(new GuiButton(-5, this.width - 217, 4 + i, 10, 10, Color.cl("l") + "l"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 217, 4 + i, 10, 10, Color.cl("o") + "o"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 217, 4 + i, 10, 10, Color.cl("n") + "n"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 217, 4 + i, 10, 10, Color.cl("m") + "m"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 217, 4 + i, 10, 10, Color.cl("k") + "k"));
        i = i + 10;
        this.buttonList.add(new GuiButton(-5, this.width - 217, 4 + i, 10, 10, Color.cl("r") + "r"));
        i = i + 10;
        this.buttonList.add(new GuiButton(0, this.width - 48, 4, 45, 20, Color.cl("c") + "Close"));
        this.initSymbols();
        Keyboard.enableRepeatEvents(true);
        this.inputField = new GuiTextField(0, this.fontRendererObj, 4, this.height - 12, this.width - 4, 12);
        this.inputField.setMaxStringLength(100);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.text);
        this.inputField.setCanLoseFocus(false);
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
                --this.page;
                this.initSymbols();
                break;

            case 2:
                ++this.page;
                this.initSymbols();
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

        this.text = this.inputField.getText();
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.draw = LabyMod.getInstance().draw;
        drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        drawRect(this.width - 193, 27, this.width - 3, 169, Integer.MIN_VALUE);
        drawRect(this.width - 193, 4, this.width - 3, 25, Integer.MIN_VALUE);
        drawRect(this.width - 218, 4, this.width - 194, 169, Integer.MIN_VALUE);
        this.inputField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
