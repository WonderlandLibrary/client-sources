package net.minecraft.client.gui;

import de.labystudio.gui.GuiCapeSettings;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import java.io.IOException;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EnumPlayerModelParts;

public class GuiCustomizeSkin extends GuiScreen
{
    boolean advanced = false;

    /** The parent GUI for this GUI */
    GuiButton parentScreen2;
    GuiButton optionsBackground;
    GuiButton statIcons;
    GuiButton icons;
    GuiButton zLevel;

    /** The parent GUI for this GUI */
    private final GuiScreen parentScreen;

    /** The title of the GUI. */
    private String title;
    private static final String __OBFID = "CL_00001932";

    public void LabyMod()
    {
        this.buttonList.clear();

        if (this.advanced)
        {
            int i = 0;
            EnumPlayerModelParts[] aenumplayermodelparts = EnumPlayerModelParts.values();
            int j = aenumplayermodelparts.length;
            EnumPlayerModelParts enumplayermodelparts = aenumplayermodelparts[6];
            this.buttonList.add(new GuiCustomizeSkin.ButtonPart(enumplayermodelparts.getPartId(), this.width / 2 - 40 + i % 2 * 160, this.height / 6 - 24 + 25, 80, 20, enumplayermodelparts, (Object)null));
            enumplayermodelparts = aenumplayermodelparts[1];
            this.buttonList.add(new GuiCustomizeSkin.ButtonPart(enumplayermodelparts.getPartId(), this.width / 2 - 40 + i % 2 * 160, this.height / 6 + 24 + 20, 80, 20, enumplayermodelparts, (Object)null));
            enumplayermodelparts = aenumplayermodelparts[3];
            this.buttonList.add(new GuiCustomizeSkin.ButtonPart(enumplayermodelparts.getPartId(), this.width / 2 - 40 + i % 2 * 160 + 90, this.height / 6 + 24 + 0, 100, 20, enumplayermodelparts, (Object)null));
            enumplayermodelparts = aenumplayermodelparts[2];
            this.buttonList.add(new GuiCustomizeSkin.ButtonPart(enumplayermodelparts.getPartId(), this.width / 2 - 40 + i % 2 * 160 - 110, this.height / 6 + 24 + 0, 100, 20, enumplayermodelparts, (Object)null));
            enumplayermodelparts = aenumplayermodelparts[4];
            this.buttonList.add(new GuiCustomizeSkin.ButtonPart(enumplayermodelparts.getPartId(), this.width / 2 - 55 + i % 2 * 160 - 120, this.height / 6 + 24 + 50, 130, 20, enumplayermodelparts, (Object)null));
            enumplayermodelparts = aenumplayermodelparts[5];
            this.buttonList.add(new GuiCustomizeSkin.ButtonPart(enumplayermodelparts.getPartId(), this.width / 2 - 35 + i % 2 * 160 + 80, this.height / 6 + 24 + 50, 130, 20, enumplayermodelparts, (Object)null));
            enumplayermodelparts = aenumplayermodelparts[0];
            this.buttonList.add(new GuiCustomizeSkin.ButtonPart(enumplayermodelparts.getPartId(), this.width / 2 - 40 + i % 2 * 160, this.height / 6 + 24 + 80, 80, 20, enumplayermodelparts, (Object)null));

            for (int k = 0; k <= this.buttonList.size() - 1; ++k)
            {
                GuiButton guibutton = (GuiButton)this.buttonList.get(k);
                guibutton.displayString = guibutton.displayString.replace("ON", Color.c + "aON").replace("OFF", Color.c + "cOFF").replace("An", Color.c + "aAn").replace("Aus", Color.c + "cAus");
            }
        }
        else
        {
            this.buttonList.add(this.parentScreen2 = new GuiButton(1, this.width / 2 + 40, this.height / 2 - 58, 80, 20, ""));
            this.buttonList.add(this.optionsBackground = new GuiButton(2, this.width / 2 + 40, this.height / 2 - 33, 80, 20, ""));
            this.buttonList.add(this.statIcons = new GuiButton(3, this.width / 2 + 40, this.height / 2 - 8, 80, 20, ""));
        }

        this.buttonList.add(this.icons = new GuiButton(40, this.width / 2 + 40, this.height / 2 + 17, 80, 20, ""));
        this.buttonList.add(this.zLevel = new GuiButton(10, 2, 2, 80, 20, ""));
        this.refreshButton();
    }

    public void refreshButton()
    {
        if (this.parentScreen != null)
        {
            this.parentScreen2.displayString = this.getStatus("Hat", EnumPlayerModelParts.HAT);
            this.optionsBackground.displayString = this.getStatus("Jacket", EnumPlayerModelParts.JACKET);
            this.statIcons.displayString = this.getStatus("Pants", EnumPlayerModelParts.RIGHT_PANTS_LEG);
        }

        if (this.icons != null)
        {
            if (!this.advanced)
            {
                this.icons.displayString = "Advanced..";
            }
            else
            {
                this.icons.displayString = "Simple..";
                this.icons.yPosition = this.height / 2 + 55;
            }
        }

        if (this.zLevel != null)
        {
            this.zLevel.displayString = "Cape Settings";

            if (this.advanced)
            {
                this.zLevel.yPosition = this.height / 2 + 55;
                this.zLevel.xPosition = this.width / 2 - 120;
            }
            else
            {
                this.zLevel.yPosition = this.height / 2 + 43;
                this.zLevel.xPosition = this.width / 2 + 40;
            }
        }
    }

    public String getStatus(String p_getStatus_1_, EnumPlayerModelParts p_getStatus_2_)
    {
        String s;

        if (this.mc.gameSettings.getModelParts().contains(p_getStatus_2_))
        {
            s = Color.cl("a") + "ON";
        }
        else
        {
            s = Color.cl("C") + "OFF";
        }

        return p_getStatus_1_ + ": " + s + Color.cl("f") + "";
    }

    public GuiCustomizeSkin(GuiScreen parentScreenIn)
    {
        this.parentScreen = parentScreenIn;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        int i = 0;
        this.title = I18n.format("options.skinCustomisation.title", new Object[0]);
        this.LabyMod();

        if (i % 2 == 1)
        {
            ++i;
        }

        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 2 + 90, I18n.format("gui.done", new Object[0])));
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (!this.advanced)
            {
                if (button.id == 1)
                {
                    this.mc.gameSettings.switchModelPartEnabled(EnumPlayerModelParts.HAT);
                    this.refreshButton();
                }
                else if (button.id == 2)
                {
                    this.mc.gameSettings.switchModelPartEnabled(EnumPlayerModelParts.JACKET);
                    this.mc.gameSettings.switchModelPartEnabled(EnumPlayerModelParts.RIGHT_SLEEVE);
                    this.mc.gameSettings.switchModelPartEnabled(EnumPlayerModelParts.LEFT_SLEEVE);
                    this.refreshButton();
                }
                else if (button.id == 3)
                {
                    this.mc.gameSettings.switchModelPartEnabled(EnumPlayerModelParts.LEFT_PANTS_LEG);
                    this.mc.gameSettings.switchModelPartEnabled(EnumPlayerModelParts.RIGHT_PANTS_LEG);
                    this.refreshButton();
                }
            }

            if (button.id == 40)
            {
                this.advanced = !this.advanced;
                this.initGui();
            }

            if (button.id == 10)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiCapeSettings(this));
            }
            else if (button.id == 200)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if (button instanceof GuiCustomizeSkin.ButtonPart)
            {
                EnumPlayerModelParts enumplayermodelparts = ((GuiCustomizeSkin.ButtonPart)button).playerModelParts;
                this.mc.gameSettings.switchModelPartEnabled(enumplayermodelparts);
                button.displayString = this.func_175358_a(enumplayermodelparts);
                button.displayString = button.displayString.replace("ON", Color.c + "aON").replace("OFF", Color.c + "cOFF").replace("An", Color.c + "aAn").replace("Aus", Color.c + "cAus");
                this.mc.gameSettings.saveOptions();
            }
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 16777215);

        if (this.mc.thePlayer != null)
        {
            if (this.advanced)
            {
                DrawUtils drawutils2 = LabyMod.getInstance().draw;
                DrawUtils.drawEntityOnScreen(40, this.height - 10, 30, 40.0F - (float)mouseX, (float)(this.height - 10 - 120 + 75 - 50) - (float)mouseY, 0, this.mc.thePlayer);
            }
            else
            {
                DrawUtils drawutils = LabyMod.getInstance().draw;
                DrawUtils.drawEntityOnScreen(this.width / 2 - 20, this.height / 2 + 60, 30, (float)(this.width / 2 - 20 - mouseX), (float)(this.height / 2 - 40 - mouseY), 0, this.mc.thePlayer);
            }

            if (this.mc.isSingleplayer())
            {
                LabyMod.getInstance().draw.drawString(Color.cl("c") + "Preview is not live!", 3.0D, (double)(this.height - 10));
            }
        }
        else if (!this.advanced)
        {
            DrawUtils drawutils1 = LabyMod.getInstance().draw;
            DrawUtils.drawRect(this.width / 2 - 110, this.height / 2 - 50, this.width / 2 + 30, this.height / 2 + 45, 1129010000);
            int i = this.width / 2 - 40;
            int j = this.height / 2;
            LabyMod.getInstance().draw.drawCenteredString("Preview not available.", i, j - 15);
            LabyMod.getInstance().draw.drawCenteredString(Color.cl("c") + "You are not ingame!", this.width / 2 - 40, this.height / 2 - 5);
        }

        if (!LabyMod.getInstance().isInGame())
        {
            this.zLevel.enabled = false;
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == 1)
        {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }

    private String func_175358_a(EnumPlayerModelParts playerModelParts)
    {
        String s;

        if (this.mc.gameSettings.getModelParts().contains(playerModelParts))
        {
            s = I18n.format("options.on", new Object[0]);
        }
        else
        {
            s = I18n.format("options.off", new Object[0]);
        }

        return playerModelParts.func_179326_d().getFormattedText() + ": " + s;
    }

    class ButtonPart extends GuiButton
    {
        private final EnumPlayerModelParts playerModelParts;
        private static final String __OBFID = "CL_00001930";

        private ButtonPart(int p_i45514_2_, int p_i45514_3_, int p_i45514_4_, int p_i45514_5_, int p_i45514_6_, EnumPlayerModelParts playerModelParts)
        {
            super(p_i45514_2_, p_i45514_3_, p_i45514_4_, p_i45514_5_, p_i45514_6_, GuiCustomizeSkin.this.func_175358_a(playerModelParts));
            this.playerModelParts = playerModelParts;
        }

        ButtonPart(int p_i3_2_, int p_i3_3_, int p_i3_4_, int p_i3_5_, int p_i3_6_, EnumPlayerModelParts p_i3_7_, Object p_i3_8_)
        {
            this(p_i3_2_, p_i3_3_, p_i3_4_, p_i3_5_, p_i3_6_, p_i3_7_);
        }
    }
}
