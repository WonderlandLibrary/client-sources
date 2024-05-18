package de.labystudio.gui;

import de.labystudio.chat.LabyModPlayer;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.language.L;
import de.labystudio.utils.Color;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public class GuiMenuScreen extends GuiScreen
{
    private Gui parentScreen;
    public GuiScreen childScreen;
    public String id = "";
    int chat = 0;
    int next = 2;

    public GuiMenuScreen(GuiScreen parent)
    {
        this.parentScreen = parent;
    }

    public Gui getParent()
    {
        return this.parentScreen;
    }

    protected void actionPermformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if (button.id == 100)
        {
            LabyMod.getInstance();
            LabyMod.getInstance().openMenu = null;
            LabyMod.getInstance();
            LabyMod.getInstance().back();
        }

        if (button.id == 102)
        {
            GuiScreen guiscreen = new GuiOnlineChat();
            LabyMod.getInstance();
            LabyMod.getInstance().openMenu = guiscreen;
            this.mc.displayGuiScreen(guiscreen);
        }

        if (button.id == 103)
        {
            GuiScreen guiscreen1 = new GuiTags();
            LabyMod.getInstance();
            LabyMod.getInstance().openMenu = guiscreen1;
            this.mc.displayGuiScreen(guiscreen1);
        }

        if (button.id == 104)
        {
            GuiScreen guiscreen2 = new GuiTeamSpeak();
            LabyMod.getInstance();
            LabyMod.getInstance().openMenu = guiscreen2;
            this.mc.displayGuiScreen(guiscreen2);
        }

        if (button.id == 105)
        {
            GuiScreen guiscreen3 = new GuiGames();
            LabyMod.getInstance();
            LabyMod.getInstance().openMenu = guiscreen3;
            this.mc.displayGuiScreen(guiscreen3);
        }

        if (button.id == 106)
        {
            GuiScreen guiscreen4 = new GuiStopWatch();
            LabyMod.getInstance();
            LabyMod.getInstance().openMenu = guiscreen4;
            this.mc.displayGuiScreen(guiscreen4);
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        LabyMod.getInstance();
        LabyMod.getInstance().chatChange = false;
        LabyMod.getInstance();

        if (LabyMod.getInstance().openMenu != null)
        {
            if (this.childScreen == null)
            {
                System.out.println("Can\'t find childScreen");
                Minecraft minecraft1 = this.mc;
                LabyMod.getInstance();
                minecraft1.displayGuiScreen(LabyMod.getInstance().openMenu);
            }
            else
            {
                String s = this.childScreen.getClass().getSimpleName();
                LabyMod.getInstance();

                if (!s.equalsIgnoreCase(LabyMod.getInstance().openMenu.getClass().getSimpleName()))
                {
                    Minecraft minecraft = this.mc;
                    LabyMod.getInstance();
                    minecraft.displayGuiScreen(LabyMod.getInstance().openMenu);
                    return;
                }
            }
        }

        super.initGui();
        this.next = 2;
        LabyMod.getInstance();
        String s1 = LabyMod.getInstance().isInGame() ? L._("tab_menu", new Object[0]) : L._("tab_multiplayer", new Object[0]);
        LabyMod.getInstance();
        this.addButton(100, s1, LabyMod.getInstance().isInGame() ? "Menu" : "Multiplayer");
        this.addButton(102, L._("tab_chat", new Object[0]), "Chat");

        if (ConfigManager.settings.tags.booleanValue())
        {
            this.addButton(103, L._("tab_tags", new Object[0]), "Tags");
        }

        if (ConfigManager.settings.teamSpeak.booleanValue())
        {
            this.addButton(104, L._("tab_teamspeak", new Object[0]), "TeamSpeak");
        }

        if (ConfigManager.settings.miniGames)
        {
            this.addButton(105, L._("tab_games", new Object[0]), "Games");
        }

        if (ConfigManager.settings.stopWatch)
        {
            this.addButton(106, L._("tab_stopwatch", new Object[0]), "Stopwatch");
        }
    }

    public void addButton(int id, String title, String uid)
    {
        int j = this.next;
        LabyMod.getInstance();
        GuiButton guibutton = new GuiButton(id, j, 4, LabyMod.getInstance().draw.getStringWidth(title) + 10, 20, title);

        if (this.id.toLowerCase().contains(uid.toLowerCase()))
        {
            guibutton.enabled = false;
        }

        this.buttonList.add(guibutton);
        int i = this.next;
        LabyMod.getInstance();
        this.next = i + LabyMod.getInstance().draw.getStringWidth(title) + 11;

        if (title.toLowerCase().contains(L._("tab_chat", new Object[0])))
        {
            this.chat = this.next;
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        LabyMod.getInstance();
        LabyMod.getInstance().overlay(mouseX, mouseY);

        if (this.chat != 0)
        {
            try
            {
                int i = 0;

                for (LabyModPlayer labymodplayer : LabyMod.getInstance().getClient().getFriends())
                {
                    i += labymodplayer.messages;
                }

                if (i != 0)
                {
                    GL11.glPushMatrix();
                    GL11.glScaled(0.5D, 0.5D, 0.5D);
                    LabyMod.getInstance().draw.drawRightString(Color.cl("c") + i, (double)(this.chat - 5) / 0.5D, 38.0D);
                    GL11.glPopMatrix();
                }
            }
            catch (Exception var7)
            {
                ;
            }
        }
    }
}
