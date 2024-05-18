package de.labystudio.gui.chat;

import de.labystudio.utils.Color;
import de.labystudio.utils.Scrollbar;
import java.io.IOException;
import java.util.HashMap;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class GuiChatSymbols extends GuiChatHoverNameHistory
{
    private static String[] symbols = createSymbols();
    private Scrollbar scrollbar = new Scrollbar(15);
    private String[] colorCodes = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "k", "m", "n", "l", "o", "r"};
    private HashMap<String, Long> pressedAnimation = new HashMap();

    public GuiChatSymbols(String defaultText)
    {
        super(defaultText);
    }

    public static String[] getSymbols()
    {
        return symbols;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        super.initGui();
        this.scrollbar.setPosition(this.width - 6, this.height - 145, this.width - 5, this.height - 20);
        this.scrollbar.update(34);
        this.scrollbar.setSpeed(10);
        this.scrollbar.setEntryHeight(10);
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        this.scrollbar.mouseInput();
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.scrollbar.calc();
        drawRect(this.width - 100, this.height - 150, this.width - 2, this.height - 16, Integer.MIN_VALUE);
        drawRect(this.width - 6, this.height - 145, this.width - 5, this.height - 20, Integer.MIN_VALUE);
        drawRect(this.width - 7, this.scrollbar.top, this.width - 4, (int)((double)this.scrollbar.top + this.scrollbar.barLength), Integer.MAX_VALUE);
        int i = 0;
        int j = 0;

        for (String s : symbols)
        {
            if (j * 10 + this.scrollbar.getScrollY() > -5 && j * 10 + this.scrollbar.getScrollY() < 125)
            {
                boolean flag = mouseX > this.width - 93 + i * 10 - 5 && mouseX < this.width - 93 + i * 10 + 6 && mouseY > this.height - 147 + j * 10 + this.scrollbar.getScrollY() - 5 && mouseY < this.height - 147 + j * 10 + this.scrollbar.getScrollY() + 6;
                this.drawCenteredString(this.mc.fontRendererObj, s, this.width - 93 + i * 10, this.height - 147 + j * 10 + this.scrollbar.getScrollY(), flag ? -24000 : -1);
            }

            ++i;

            if (i > 8)
            {
                i = 0;
                ++j;
            }
        }

        i = 0;
        j = 0;

        for (String s2 : this.colorCodes)
        {
            Long olong = (Long)this.pressedAnimation.get(s2);
            int k = (int)(olong == null ? 0L : olong.longValue() - System.currentTimeMillis());
            boolean flag1 = mouseX > this.width - 111 - j * 11 && mouseX < this.width - 111 + 10 - j * 11 && mouseY > this.height - 150 + i * 11 && mouseY < this.height - 150 + 10 + i * 11;
            drawRect(this.width - 111 - j * 11, this.height - 150 + i * 11, this.width - 111 + 10 - j * 11, this.height - 150 + 10 + i * 11, !flag1 && k == 0 ? Integer.MIN_VALUE : Color.toRGB(132, 132, 132, k == 0 ? 130 : k / 4));
            this.drawCenteredString(this.mc.fontRendererObj, Color.cl(s2) + s2, this.width - 111 - j * 11 + 5, this.height - 150 + i * 11 + 1, Integer.MAX_VALUE);

            if (k < 0)
            {
                this.pressedAnimation.remove(s2);
            }

            ++i;

            if (i > 11 || s2.equals("9") || s2.equals("f"))
            {
                i = 0;
                ++j;
            }
        }

        boolean flag2 = false;

        for (String s3 : this.colorCodes)
        {
            if (this.inputField.getText().contains("&" + s3))
            {
                flag2 = true;
                break;
            }
        }

        if (flag2)
        {
            drawRect(2, this.height - 16 - 12, this.width - 101, this.height - 16, Integer.MIN_VALUE);
            String s1;

            for (s1 = this.inputField.getText().replace("&", Color.c); s1.contains("  "); s1 = s1.replace("  ", " "))
            {
                ;
            }

            this.drawString(this.mc.fontRendererObj, Color.cl("r") + s1, 4, this.height - 16 - 10, Integer.MAX_VALUE);
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.scrollbar.mouseAction(mouseX, mouseY, false);
        int i = 0;
        int j = 0;

        for (String s : symbols)
        {
            if (j * 10 + this.scrollbar.getScrollY() > -5 && j * 10 + this.scrollbar.getScrollY() < 125)
            {
                boolean flag = mouseX > this.width - 93 + i * 10 - 5 && mouseX < this.width - 93 + i * 10 + 6 && mouseY > this.height - 147 + j * 10 + this.scrollbar.getScrollY() - 5 && mouseY < this.height - 147 + j * 10 + this.scrollbar.getScrollY() + 6;

                if (flag)
                {
                    this.inputField.textboxKeyTyped(s.charAt(0), 0);
                    this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 2.0F));
                    break;
                }
            }

            ++i;

            if (i > 8)
            {
                i = 0;
                ++j;
            }
        }

        i = 0;
        j = 0;

        for (String s1 : this.colorCodes)
        {
            if (mouseX > this.width - 111 - j * 11 && mouseX < this.width - 111 + 10 - j * 11 && mouseY > this.height - 150 + i * 11 && mouseY < this.height - 150 + 10 + i * 11)
            {
                this.inputField.textboxKeyTyped("&".charAt(0), 0);
                this.inputField.textboxKeyTyped(s1.charAt(0), 0);
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 2.0F));
            }

            ++i;

            if (i > 11 || s1.equals("9") || s1.equals("f"))
            {
                i = 0;
                ++j;
            }
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
        String s = this.inputField.getText();
        int i = this.inputField.getCursorPosition();

        if (i > 1 && s.length() > 0)
        {
            String s1 = String.valueOf(typedChar);
            boolean flag = false;

            for (String s2 : this.colorCodes)
            {
                if (s2.equals(s1))
                {
                    flag = true;
                    break;
                }
            }

            if (i > 1 && flag && String.valueOf(s.charAt(i - 2)).equals("&"))
            {
                this.pressedAnimation.put(s1, Long.valueOf(System.currentTimeMillis() + 1000L));
            }
        }
    }

    /**
     * Called when a mouse button is pressed and the mouse is moved around. Parameters are : mouseX, mouseY,
     * lastButtonClicked & timeSinceMouseClick.
     */
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        this.scrollbar.mouseAction(mouseX, mouseY, true);
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    public static String[] createSymbols()
    {
        String s = "\u2764\u2765\u2714\u2716\u2717\u2718\u2742\u22c6\u2722\u2723\u2724\u2725\u2726\u2729\u272a\u272b\u272c\u272d\u272e\u272f\u2730\u2605\u2731\u2732\u2733\u2734\u2735\u2736\u2737\u2738\u2739\u273a\u273b\u273c\u2744\u2745\u2746\u2747\u2748\u2749\u274a\u274b\u2606\u2721\u273d\u273e\u273f\u2740\u2741\u2743\u270c\u267c\u267d\u2702\u2704\u2708\u27a1\u2b05\u2b06\u2b07\u279f\u27a2\u27a3\u27a4\u27a5\u27a6\u27a7\u27a8\u279a\u2798\u2799\u279b\u279c\u279d\u279e\u27b8\u27b2\u27b3\u27b4\u27b5\u27b6\u27b7\u27b9\u27ba\u27bb\u27bc\u27bd\u24c2\u2b1b\u2b1c\u2588\u259b\u2580\u259c\u2586\u2584\u258c\u2615\u2139\u2122\u2691\u2690\u2603\u26a0\u2694\u2696\u2692\u2699\u269c\u2680\u2681\u2682\u2683\u2684\u2685\u268a\u268b\u268c\u268d\u268e\u268f\u2630\u2631\u2632\u2633\u2634\u2635\u2636\u2637\u2686\u2687\u2688\u2689\u267f\u2669\u266a\u266b\u266c\u266d\u266e\u266f\u2660\u2661\u2662\u2663\u2664\u2665\u2666\u2667\u2654\u2655\u2656\u2657\u2658\u2659\u265a\u265b\u265c\u265d\u265e\u265f\u26aa\u26ab\u262f\u262e\u2623\u260f\u2780\u2781\u2782\u2783\u2784\u2785\u2786\u2787\u2788\u2789\u278a\u278b\u278c\u278d\u278e\u278f\u2790\u2791\u2792\u2793\u24d0\u24d1\u24d2\u24d3\u24d4\u24d5\u24d6\u24d7\u24d8\u24d9\u24da\u24db\u24dc\u24dd\u24de\u24df\u24e0\u24e1\u24e2\u24e3\u24e4\u24e5\u24e6\u24e7\u24e8\u24e9\uc6c3\uc720\u264b\u2622\u2620\u2611\u25b2\u231a\u00bf\u2763\u2642\u2640\u263f\u24b6\u270d\u2709\u2624\u2612\u25bc\u2318\u231b\u00a1\u10e6\u30c4\u263c\u2601\u2652\u270e\u00a9\u00ae\u03a3\u262d\u271e\u2103\u2109\u03df\u2602\u00a2\u00a3\u221e\u00bd\u262a\u263a\u263b\u2639\u2307\u269b\u2328\u2706\u260e\u2325\u21e7\u21a9\u2190\u2192\u2191\u2193\u27ab\u261c\u261e\u261d\u261f\u267a\u2332\u26a2\u26a3\u2751\u2752\u25c8\u25d0\u25d1\u00ab\u00bb\u2039\u203a\u2013\u2014\u2044\u00b6\u203d\u2042\u203b\u00b1\u00d7\u2248\u00f7\u2260\u03c0\u2020\u2021\u00a5\u20ac\u2030\u2026\u00b7\u2022\u25cf";
        String[] astring = new String[s.length()];

        for (int i = 0; i < s.length(); ++i)
        {
            astring[i] = String.valueOf(s.charAt(i));
        }

        return astring;
    }
}
