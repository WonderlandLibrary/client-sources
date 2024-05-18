package de.labystudio.gui;

import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import de.labystudio.utils.Scrollbar;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

public class GuiStopWatch extends GuiMenuScreen
{
    DrawUtils draw;
    Scrollbar scrollbar;
    int selectedRound = 0;
    public static int timer = 0;
    public static long start = 0L;
    public static boolean running = false;
    public static boolean repeat = false;
    public static ArrayList<GuiStopWatch.Round> rounds = new ArrayList();
    GuiButton startStop;
    GuiButton reset;
    GuiButton round;

    public GuiStopWatch()
    {
        super((GuiScreen)null);
        this.childScreen = this;
        this.draw = LabyMod.getInstance().draw;
        this.id = "Stopwatch";
        this.scrollbar = new Scrollbar(10);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        super.initGui();
        this.buttonList.add(this.startStop = new GuiButton(0, this.width / 2 - 50 - 30, 125, 103, 20, ""));
        this.buttonList.add(this.reset = new GuiButton(1, this.width / 2 - 50 + 75, 125, 50, 20, Color.cl("4") + "Reset"));
        this.buttonList.add(this.round = new GuiButton(2, this.width - 60, 125, 50, 20, Color.cl("b") + "Round"));
        this.refreshButtons();
        this.scrollbar.setPosition(this.width - 11, 150, this.width - 5, this.height - 30);
    }

    public void refreshButtons()
    {
        if (running)
        {
            this.startStop.displayString = Color.cl("c") + "Stop";
            this.round.enabled = true;
        }
        else
        {
            this.startStop.displayString = Color.cl("a") + "Start";
            this.round.enabled = true;
        }

        if (getTimer() == 0)
        {
            this.reset.enabled = false;
        }
        else
        {
            this.reset.enabled = true;
        }

        this.scrollbar.update(rounds.size());
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        this.scrollbar.mouseAction(mouseX, mouseY, false);
        int i = 0;
        int j = rounds.size() * 10 + this.scrollbar.getScrollY();

        for (GuiStopWatch.Round guistopwatch$round : rounds)
        {
            if (j + 145 - i > 130 && mouseX > 15 && mouseX < this.width - 20 && mouseY > j + 145 - i - 3 && mouseY < j + 145 - i + 9)
            {
                this.selectedRound = guistopwatch$round.getMs();
                break;
            }

            i += 10;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public static void handleKeys(int key)
    {
        if (ConfigManager.settings.stopWatch)
        {
            if (key == -1)
            {
                repeat = false;
            }
            else
            {
                if (LabyMod.getInstance().mc.currentScreen == null && !repeat)
                {
                    if (Keyboard.isKeyDown(29) && Keyboard.isKeyDown(208))
                    {
                        repeat = true;

                        if (running)
                        {
                            timer = getTimer();
                            running = false;
                        }
                        else
                        {
                            start = System.currentTimeMillis();
                            running = true;
                        }

                        return;
                    }

                    if (Keyboard.isKeyDown(29) && Keyboard.isKeyDown(205))
                    {
                        int i = 0;

                        if (rounds.size() != 0)
                        {
                            i = ((GuiStopWatch.Round)rounds.get(rounds.size() - 1)).getMs();
                        }

                        if (getTimer() != i && getTimer() != 0)
                        {
                            repeat = true;
                            int j = getTimer();
                            rounds.add(new GuiStopWatch.Round(j, parseTime(j)));
                        }

                        return;
                    }
                }
            }
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    public void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPermformed(button);

        if (button.id == 0)
        {
            if (running)
            {
                timer = getTimer();
                running = false;
            }
            else
            {
                start = System.currentTimeMillis();
                running = true;
            }
        }

        if (button.id == 1)
        {
            running = false;
            timer = 0;
            rounds.clear();
        }

        if (button.id == 2)
        {
            int i = getTimer();
            rounds.add(new GuiStopWatch.Round(i, parseTime(i)));
            this.scrollbar.setScrollY(0);
            this.selectedRound = i;
        }

        this.refreshButtons();
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

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException
    {
        this.scrollbar.mouseInput();
        super.handleMouseInput();
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == 0)
        {
            this.mc.displayGuiScreen(this.childScreen);
        }

        super.keyTyped(typedChar, keyCode);
    }

    public static int getTimer()
    {
        if (!running)
        {
            start = System.currentTimeMillis();
        }

        return (int)((long)timer + (System.currentTimeMillis() / 100L - start / 100L));
    }

    public static String parseTime(int time)
    {
        String s = time / 600 % 60 + "";
        String s1 = time / 10 % 60 + "";
        String s2 = time % 10 + "";
        String s3 = time / 600 / 60 % 24 + "";
        String s4 = time / 600 / 60 / 24 + "";

        if (s.length() == 1)
        {
            s = "0" + s;
        }

        if (s1.length() == 1)
        {
            s1 = "0" + s1;
        }

        if (!s3.equals("0"))
        {
            s3 = s3 + "h ";
        }
        else
        {
            s3 = "";
        }

        if (!s4.equals("0"))
        {
            s4 = s4 + "d ";
        }
        else
        {
            s4 = "";
        }

        return s4 + s3 + s + ":" + s1 + "." + s2;
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        if (LabyMod.getInstance().isInGame())
        {
            GlStateManager.enableBlend();
            this.draw.drawTransparentBackground(0, 52, this.width, 120);
            this.draw.drawTransparentBackground(10, 150, this.width - 10, this.height - 30);
            GlStateManager.disableBlend();
        }
        else
        {
            this.drawDefaultBackground();
            this.draw.drawChatBackground(0, 52, this.width, 120);
            this.draw.drawChatBackground(10, 150, this.width - 10, this.height - 30);
        }

        String s = parseTime(getTimer());
        this.draw.drawCenteredStringWithoutShadow(s, (double)(this.width / 2), 65.0D, 5.0D);
        int i = 0;
        int j = 1;
        int k = rounds.size() * 10 + this.scrollbar.getScrollY();

        for (GuiStopWatch.Round guistopwatch$round : rounds)
        {
            if (k + 145 - i > 130)
            {
                if (this.selectedRound == guistopwatch$round.getMs())
                {
                    DrawUtils drawutils = this.draw;
                    DrawUtils.drawRect(10, k + 145 - i - 1, this.width - 10, k + 145 - i + 9, Integer.MIN_VALUE);
                    this.draw.drawString(Color.cl("a") + "" + j + ".  " + Color.cl("b") + guistopwatch$round.getParsed() + " " + Color.cl("7") + " (" + parseTime(getTimer() - guistopwatch$round.getMs()) + " ago)", 15.0D, (double)(k + 145 - i), 1.0D);
                }
                else
                {
                    this.draw.drawString(Color.cl("e") + "" + j + ".  " + Color.cl("f") + guistopwatch$round.getParsed(), 15.0D, (double)(k + 145 - i), 1.0D);
                }
            }

            ++j;
            i += 10;
        }

        int l = 0;

        if (rounds.size() != 0)
        {
            l = ((GuiStopWatch.Round)rounds.get(rounds.size() - 1)).getMs();
        }

        this.round.enabled = getTimer() != l && getTimer() != 0;
        this.draw.overlayBackground(0, 52);
        this.draw.overlayBackground(120, 150);
        this.draw.overlayBackground(this.height - 30, this.height);
        this.draw.overlayBackground(0, 150, 10, this.height - 30);
        this.draw.overlayBackground(this.width - 10, 150, this.width, this.height - 30);
        this.draw.drawRightString("Ctrl + Arrow Down = Start/Stop", (double)(this.width - 5), 5.0D, 0.5D);
        this.draw.drawRightString("Ctrl + Arrow Right = Round", (double)(this.width - 5), 10.0D, 0.5D);
        this.scrollbar.draw();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public static class Round
    {
        private int ms = 0;
        private String parsed = "";

        public Round(int ms, String parsed)
        {
            this.ms = ms;
            this.parsed = parsed;
        }

        public int getMs()
        {
            return this.ms;
        }

        public String getParsed()
        {
            return this.parsed;
        }
    }
}
