package net.minecraft.client.gui;

import de.labystudio.downloader.ModInfoDownloader;
import de.labystudio.downloader.UserCapesDownloader;
import de.labystudio.downloader.UserCosmeticsDownloader;
import de.labystudio.gui.GuiCosmetics;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import de.labystudio.utils.SkinChanger;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EnumPlayerModelParts;

public class GuiCustomizeSkin extends GuiScreen
{
    public static long REFRESH_COOLDOWN = 0L;
    boolean advanced = false;

    /** The parent GUI for this GUI */
    GuiButton parentScreen;
    GuiButton optionsBackground;
    GuiButton statIcons;
    GuiButton icons;
    GuiButton zLevel;

    /** The title of the GUI. */
    GuiButton title;
    GuiButton NEWLINE_SPLITTER;

    /** The button that was just pressed. */
    GuiButton selectedButton;
    private GuiButton buttonRefresh;
    boolean steveModel = true;
    String output = "";
    private float previewMouseClickX = 0.0F;
    private float previewMouseClickY = 0.0F;
    private float previewMouseDragX = 0.0F;
    private float previewMouseDragY = 0.0F;

    /** The parent GUI for this GUI */
    private final GuiScreen parentScreen1;

    /** The title of the GUI. */
    private String title1;
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
            this.buttonList.add(this.parentScreen = new GuiButton(1, this.width / 2 + 40, this.height / 2 - 58, 80, 20, ""));
            this.buttonList.add(this.optionsBackground = new GuiButton(2, this.width / 2 + 40, this.height / 2 - 33, 80, 20, ""));
            this.buttonList.add(this.statIcons = new GuiButton(3, this.width / 2 + 40, this.height / 2 - 8, 80, 20, ""));
        }

        this.buttonList.add(this.icons = new GuiButton(40, this.width / 2 + 40, this.height / 2 + 17, 80, 20, ""));
        this.buttonList.add(this.zLevel = new GuiButton(10, 2, 2, 95, 20, ""));
        this.buttonList.add(this.title = new GuiButton(11, 2, 2, 95, 20, ""));
        this.buttonList.add(this.NEWLINE_SPLITTER = new GuiButton(12, this.width / 2 + 40, this.height / 2 + 42, 80, 20, ""));
        this.buttonList.add(this.selectedButton = new GuiButton(13, this.width / 2 + 125, this.height / 2 + 42, 35, 20, ""));
        this.buttonList.add(this.buttonRefresh = new GuiButton(5, 5, 3, 60, 20, REFRESH_COOLDOWN < System.currentTimeMillis() ? "Refresh" : "Cooldown.."));
        this.refreshButton();
    }

    public void refreshButton()
    {
        if (this.parentScreen != null)
        {
            this.parentScreen.displayString = this.getStatus("Hat", EnumPlayerModelParts.HAT);
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
                this.icons.xPosition = this.width / 2 + 51;
            }
        }

        if (this.zLevel != null)
        {
            this.zLevel.displayString = "Cape Settings";

            if (this.advanced)
            {
                this.zLevel.yPosition = this.height / 2 + 55;
                this.zLevel.xPosition = this.width / 2 - 141;
            }
            else
            {
                this.zLevel.yPosition = this.height / 2 + 75;
                this.zLevel.xPosition = this.width / 2 + 5;
            }
        }

        if (this.title != null)
        {
            this.title.displayString = "Cosmetics";

            if (this.advanced)
            {
                this.title.yPosition = this.height / 2 + 55;
                this.title.xPosition = this.width / 2 - 45;
            }
            else
            {
                this.title.yPosition = this.height / 2 + 75;
                this.title.xPosition = this.width / 2 - 100;
            }
        }

        if (this.NEWLINE_SPLITTER != null)
        {
            this.NEWLINE_SPLITTER.displayString = "Change skin";
            this.NEWLINE_SPLITTER.visible = !this.advanced;
        }

        if (this.selectedButton != null)
        {
            this.selectedButton.displayString = this.steveModel ? "Steve" : "Alex";
            this.selectedButton.visible = !this.advanced;
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
        this.parentScreen1 = parentScreenIn;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        int i = 0;
        this.title1 = I18n.format("options.skinCustomisation.title", new Object[0]);
        this.LabyMod();

        if (i % 2 == 1)
        {
            ++i;
        }

        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 2 + 97, I18n.format("gui.done", new Object[0])));
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

            if (button.id == 5)
            {
                LabyMod.getInstance().getCapeManager().refresh();
                LabyMod.getInstance().getCosmeticManager().getOfflineCosmetics().clear();
                new ModInfoDownloader();
                new UserCosmeticsDownloader();
                new UserCapesDownloader();
                REFRESH_COOLDOWN = System.currentTimeMillis() + 10000L;
                button.enabled = false;
                button.displayString = "Done!";
            }

            if (button.id == 40)
            {
                this.advanced = !this.advanced;
                this.initGui();
            }

            if (button.id == 10)
            {
                LabyMod.getInstance().openWebpage("https://www.labymod.net/#login", true);
            }

            if (button.id == 11)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiCosmetics(this));
            }

            if (button.id == 12)
            {
                this.NEWLINE_SPLITTER.enabled = false;
                (new SkinChanger(Minecraft.getMinecraft().getSession(), this.steveModel, new SkinChanger.MessageCallBack()
                {
                    public void ok(String p_ok_1_)
                    {
                        GuiCustomizeSkin.this.NEWLINE_SPLITTER.enabled = true;

                        if (p_ok_1_.isEmpty())
                        {
                            GuiCustomizeSkin.this.output = Color.cl("a") + "Your skin has been changed, reconnect in order to see it!";
                        }
                        else if (p_ok_1_.contains("Current IP not secured"))
                        {
                            GuiCustomizeSkin.this.output = Color.cl("c") + "Your IP has been changed since your last login into the Minecraft website!";
                        }
                        else if (p_ok_1_.contains("The request requires user authentication"))
                        {
                            GuiCustomizeSkin.this.output = Color.cl("c") + "Invalid session id. (Try restarting your game)";
                        }
                        else
                        {
                            GuiCustomizeSkin.this.output = p_ok_1_;
                        }
                    }
                })).start();
            }

            if (button.id == 13)
            {
                this.steveModel = !this.steveModel;
                this.refreshButton();
            }
            else if (button.id == 200)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen1);
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
        this.drawCenteredString(this.fontRendererObj, this.title1, this.width / 2, 20, 16777215);

        if (this.mc.thePlayer != null)
        {
            if (this.advanced)
            {
                DrawUtils drawutils = LabyMod.getInstance().draw;
                DrawUtils.drawEntityOnScreen(40, this.height - 10, 30, 40.0F - (float)mouseX, (float)(this.height - 10 - 120 + 75 - 50) - (float)mouseY, 0, 0, this.mc.thePlayer);
            }
            else
            {
                boolean flag = this.previewMouseDragX == 0.0F && this.previewMouseClickX == 0.0F && this.previewMouseDragY == 0.0F && this.previewMouseClickY == 0.0F;
                float f = flag ? (float)(this.width / 2 - 20 - mouseX) : 0.0F;
                float f1 = flag ? (float)(this.height / 2 - 40 - mouseY) : 0.0F;
                DrawUtils drawutils2 = LabyMod.getInstance().draw;
                DrawUtils.drawEntityOnScreen(this.width / 2 - 20, this.height / 2 + 60 - (this.previewMouseDragY == 0.0F ? 0 : 60), 30, f, f1, (int)(-this.previewMouseDragX), (int)(-this.previewMouseDragY), this.mc.thePlayer);
            }

            if (this.mc.isSingleplayer())
            {
                LabyMod.getInstance().draw.drawString(Color.cl("c") + "Live preview only in multiplayer!", 3.0D, (double)(this.height - 10));
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
            this.title.enabled = false;
        }

        LabyMod.getInstance().draw.drawCenteredString(Color.cl("c") + this.output, this.width / 2, 5);
        boolean flag1 = this.buttonRefresh.enabled;
        this.buttonRefresh.enabled = REFRESH_COOLDOWN < System.currentTimeMillis();

        if (!flag1 && this.buttonRefresh.enabled)
        {
            this.buttonRefresh.displayString = "Refresh";
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if (mouseX > this.width / 2 - 100 && mouseX < this.width / 2 + 30 && mouseY > this.height / 2 - 100 && mouseY < this.height / 2 + 70)
        {
            this.previewMouseClickX = this.previewMouseDragX + (float)mouseX;
            this.previewMouseClickY = this.previewMouseDragY + (float)mouseY;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Called when a mouse button is pressed and the mouse is moved around. Parameters are : mouseX, mouseY,
     * lastButtonClicked & timeSinceMouseClick.
     */
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        if (this.previewMouseClickX != 0.0F)
        {
            this.previewMouseDragX = this.previewMouseClickX - (float)mouseX;
        }

        if (this.previewMouseClickY != 0.0F)
        {
            this.previewMouseDragY = this.previewMouseClickY - (float)mouseY;
        }

        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    /**
     * Called when a mouse button is released.  Args : mouseX, mouseY, releaseButton
     */
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        this.previewMouseClickX = 0.0F;
        this.previewMouseClickY = 0.0F;
        this.previewMouseDragX %= 360.0F;
        this.previewMouseDragY %= 360.0F;
        super.mouseReleased(mouseX, mouseY, state);
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
            this.mc.displayGuiScreen(this.parentScreen1);
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

        ButtonPart(int p_i4_2_, int p_i4_3_, int p_i4_4_, int p_i4_5_, int p_i4_6_, EnumPlayerModelParts p_i4_7_, Object p_i4_8_)
        {
            this(p_i4_2_, p_i4_3_, p_i4_4_, p_i4_5_, p_i4_6_, p_i4_7_);
        }
    }
}
