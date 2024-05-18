package de.labystudio.gui.chat;

import de.labystudio.gui.GuiAutoText;
import de.labystudio.gui.GuiFilter;
import de.labystudio.gui.GuiNameHistory;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Allowed;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import de.labystudio.utils.NameHistory;
import de.labystudio.utils.NameMCUtil;
import de.labystudio.utils.UUIDFetcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.IChatComponent;
import org.lwjgl.input.Mouse;

public class GuiChatHoverNameHistory extends GuiChat
{
    public static String[] chatIcons;
    private static Random random = new Random();

    public GuiChatHoverNameHistory(String defaultText)
    {
        super(defaultText);
    }

    public GuiChatHoverNameHistory()
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        super.initGui();
        chatIcons = Allowed.chat() ? new String[] {"\u03c0"}: new String[0];
        this.initButtons();
    }

    public void drawButtons(int mouseX, int mouseY, float partialTicks)
    {
        int i = 0;

        for (String s : chatIcons)
        {
            drawRect(this.width - 2 - 13 - i * 14, this.height - 14, this.width - 2 - i * 14, this.height - 2, Integer.MIN_VALUE);
            boolean flag = mouseX > this.width - 2 - 13 - i * 14 && mouseX < this.width - 2 - i * 14 && mouseY > this.height - 14 && mouseY < this.height - 2;
            this.drawCenteredString(this.mc.fontRendererObj, s, this.width - 8 - i * 14, this.height - 12, flag ? -100000 : -1);
            ++i;
        }
    }

    public void onButtonClick(int mouseX, int mouseY, int mouseButton)
    {
        for (int i = 0; i < chatIcons.length; ++i)
        {
            boolean flag = mouseX > this.width - 2 - 13 - i * 14 && mouseX < this.width - 2 - i * 14 && mouseY > this.height - 14 && mouseY < this.height - 2;

            if (flag)
            {
                switch (i)
                {
                    case 0:
                        this.mc.displayGuiScreen((GuiScreen)(this.mc.currentScreen instanceof GuiChatSymbols ? new GuiChatHoverNameHistory(this.inputField.getText()) : new GuiChatSymbols(this.inputField.getText())));
                }
            }
        }
    }

    public void initButtons()
    {
        int i = 0;

        if (ConfigManager.settings.chatFilter)
        {
            GuiButton guibutton;
            this.buttonList.add(guibutton = new GuiButton(2, this.width - 48 - i, 4, 45, 20, Color.cl("a") + "Filter"));
            guibutton.enabled = Allowed.chat();
            i += 47;
        }

        if (ConfigManager.settings.autoText)
        {
            GuiButton guibutton1;
            this.buttonList.add(guibutton1 = new GuiButton(3, this.width - 54 - i, 4, 50, 20, Color.cl("a") + "AutoText"));
            guibutton1.enabled = Allowed.chat();
            i += 47;
        }

        if (ConfigManager.settings.nameHistory)
        {
            this.buttonList.add(new GuiButton(4, this.width - 76 - i, 4, 67, 20, Color.cl("a") + "NameHistory"));
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if (button.id == 2)
        {
            this.mc.displayGuiScreen(new GuiFilter(this.inputField.getText()));
        }

        if (button.id == 3)
        {
            this.mc.displayGuiScreen(new GuiAutoText(this.inputField.getText()));
        }

        if (button.id == 4)
        {
            this.mc.displayGuiScreen(new GuiNameHistory(this.inputField.getText()));
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.onButtonClick(mouseX, mouseY, mouseButton);

        if (mouseButton == 1)
        {
            IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());

            if (ichatcomponent != null && ichatcomponent.getChatStyle() != null && ichatcomponent.getChatStyle().getChatClickEvent() != null)
            {
                String s = ichatcomponent.getChatStyle().getChatClickEvent().getValue();

                if (s != null && s.startsWith("/msg "))
                {
                    String s1 = s.replace("/msg ", "").replace(" ", "");

                    if (!NameMCUtil.isInCache(s1))
                    {
                        NameMCUtil.getNameHistory(s1);
                    }
                }
            }
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawButtons(mouseX, mouseY, partialTicks);
        IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());

        try
        {
            if (ichatcomponent != null && ichatcomponent.getChatStyle() != null && ichatcomponent.getChatStyle().getChatClickEvent() != null)
            {
                String s = ichatcomponent.getChatStyle().getChatClickEvent().getValue();

                if (s != null && s.startsWith("/msg "))
                {
                    String s1 = s.replace("/msg ", "").replace(" ", "");

                    if (NameMCUtil.isInCache(s1))
                    {
                        NameHistory namehistory = NameMCUtil.getNameHistory(s1);
                        ArrayList<String> arraylist = new ArrayList();
                        boolean flag = true;

                        for (UUIDFetcher uuidfetcher : namehistory.getChanges())
                        {
                            if (uuidfetcher.changedToAt != 0L)
                            {
                                long i = System.currentTimeMillis() - uuidfetcher.changedToAt;
                                long j = i / 1000L;
                                long k = j / 60L;
                                long l = k / 60L;
                                long i1 = l / 24L;
                                long j1 = i1 / 31L;
                                long k1 = j1 / 12L;
                                String s2 = null;

                                if (j1 >= 12L)
                                {
                                    s2 = k1 + " year" + (k1 == 1L ? "" : "s");
                                }
                                else if (i1 >= 31L)
                                {
                                    s2 = j1 + " month" + (j1 == 1L ? "" : "s");
                                }
                                else if (l >= 24L)
                                {
                                    s2 = i1 + " day" + (i1 == 1L ? "" : "s");
                                }
                                else if (k >= 60L)
                                {
                                    s2 = l + " hour" + (l == 1L ? "" : "s");
                                }
                                else if (j >= 60L)
                                {
                                    s2 = k + " min" + (k == 1L ? "" : "s");
                                }
                                else
                                {
                                    s2 = j + "sec" + (j == 1L ? "" : "s");
                                }

                                if (i < 0L)
                                {
                                    s2 = "In the future!";
                                }

                                String s3 = "7";

                                if (flag)
                                {
                                    s3 = "6";
                                }

                                flag = false;
                                arraylist.add(Color.cl(s3) + uuidfetcher.name + Color.cl("8") + " - " + Color.cl("8") + s2);
                            }
                            else
                            {
                                arraylist.add(Color.cl("a") + uuidfetcher.name);
                            }
                        }

                        this.drawHoveringText(arraylist, mouseX, mouseY);
                        GlStateManager.disableLighting();
                    }
                    else
                    {
                        ArrayList<String> arraylist1 = new ArrayList();
                        arraylist1.add(Color.cl("a") + "Rightclick this name to view all name changes!");
                        this.drawHoveringText(arraylist1, mouseX, mouseY);
                        GlStateManager.disableLighting();
                    }
                }
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        DrawUtils drawutils = LabyMod.getInstance().draw;
        DrawUtils.updateMouse(mouseX, mouseY);
    }
}
