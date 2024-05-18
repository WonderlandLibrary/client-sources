package de.labystudio.gui;

import de.labystudio.capes.CapeCallback;
import de.labystudio.capes.CapeMover;
import de.labystudio.capes.CapeUploader;
import de.labystudio.capes.EnumCapePriority;
import de.labystudio.cosmetic.EnumCosmetic;
import de.labystudio.downloader.ModInfoDownloader;
import de.labystudio.downloader.UserCapesDownloader;
import de.labystudio.gui.extras.ModGuiTextField;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.labymod.Source;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EnumPlayerModelParts;
import org.lwjgl.input.Keyboard;

public class GuiCapeSettings extends GuiScreen
{
    private GuiScreen lastScreen;
    String error = "";
    private static long cdPriority = 0L;
    private static long cdRefresh = 0L;
    boolean hasCape = false;
    boolean moveCape = false;
    boolean accept = false;
    EnumCapePriority capeType;
    GuiButton LOGGER;
    GuiButton optionsBackground;
    GuiButton statIcons;
    GuiButton icons;
    ModGuiTextField zLevel;
    GuiButton PROTOCOLS;
    GuiButton NEWLINE_SPLITTER;

    /** The button that was just pressed. */
    GuiButton selectedButton;
    GuiButton eventButton;

    /** Reference to the Minecraft object. */
    GuiButton mc;
    Minecraft mc2 = Minecraft.getMinecraft();

    public GuiCapeSettings(GuiScreen lastScreen)
    {
        Keyboard.enableRepeatEvents(true);
        this.lastScreen = lastScreen;
        this.checkCape();
    }

    public void checkCape()
    {
        this.hasCape = Minecraft.getMinecraft() != null && Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer instanceof AbstractClientPlayer && Minecraft.getMinecraft().thePlayer.capeType != null;

        if (this.hasCape)
        {
            this.capeType = Minecraft.getMinecraft().thePlayer.capeType;
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.accept = false;
        this.moveCape = false;
        this.buttonList.clear();
        this.buttonList.add(this.LOGGER = new GuiButton(1, this.width / 2 - 110, this.height / 2 - 28 - 20, 80, 20, ""));
        this.buttonList.add(this.optionsBackground = new GuiButton(2, this.width / 2 - 110, this.height / 2 + 11 - 20, 80, 20, ""));
        this.buttonList.add(this.statIcons = new GuiButton(3, this.width / 2 - 110, this.height / 2 + 35 - 20, 80, 20, ""));
        this.buttonList.add(this.icons = new GuiButton(4, this.width / 2 - 110, this.height / 2 + 59 - 20, 80, 20, ""));
        this.buttonList.add(this.selectedButton = new GuiButton(7, 2, this.height - 22, 60, 20, ""));
        this.zLevel = new ModGuiTextField(0, this.mc2.fontRendererObj, this.width / 2 - 100, this.height / 4 - 30, 200, 20);
        this.zLevel.setBlacklistWord(" ");
        this.buttonList.add(this.PROTOCOLS = new GuiButton(5, this.width / 2 - 100, this.height / 4 - 5, 200, 20, ""));
        this.buttonList.add(this.NEWLINE_SPLITTER = new GuiButton(6, 2, 2, 40, 20, ""));
        this.buttonList.add(this.mc = new GuiButton(9, this.width / 2 - 110, this.height / 2 + 82 - 20, 80, 20, ""));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 2 + 90, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(this.eventButton = new GuiButton(8, this.width - 20 - 2, 2, 20, 20, "C"));
        this.eventButton.visible = LabyMod.getInstance().getCosmeticManager().hasCosmetic(new EnumCosmetic[] {EnumCosmetic.WINGS, EnumCosmetic.BLAZE, EnumCosmetic.HAT});
        this.refreshButtons();
    }

    public void refreshButtons()
    {
        if (ConfigManager.settings.capePriority.equals("of"))
        {
            this.LOGGER.displayString = "OptiFine";
        }
        else if (ConfigManager.settings.capePriority.equals("original"))
        {
            this.LOGGER.displayString = "Original";
        }
        else
        {
            this.LOGGER.displayString = "LabyMod";
        }

        this.optionsBackground.displayString = this.getStatus("Cape", EnumPlayerModelParts.CAPE);
        this.icons.displayString = "Move";

        if (this.accept)
        {
            this.PROTOCOLS.displayString = Color.cl("c") + Color.cl("l") + "Press again to accept";
        }
        else
        {
            this.PROTOCOLS.displayString = "Move";
        }

        this.NEWLINE_SPLITTER.displayString = Color.cl("6") + "Donate";
        this.selectedButton.displayString = "Refresh";

        if (LabyMod.getInstance().getCosmeticManager().colorPicker)
        {
            this.eventButton.displayString = Color.cl("a") + "C";
        }
        else
        {
            this.eventButton.displayString = Color.cl("c") + "C";
        }

        if (ConfigManager.settings.capes.booleanValue())
        {
            this.mc.displayString = "Capes: " + Color.cl("a") + "ON";
        }
        else
        {
            this.mc.displayString = "Capes: " + Color.cl("c") + "OFF";
        }
    }

    public String getStatus(String name, EnumPlayerModelParts part)
    {
        String s;

        if (this.mc2.gameSettings.getModelParts().contains(part))
        {
            s = Color.cl("a") + "SHOWN";
        }
        else
        {
            s = Color.cl("C") + "HIDDEN";
        }

        if (!this.hasCape)
        {
            s = "NO CAPE";
        }

        return s + Color.cl("f") + "";
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (button.id == 1)
            {
                if (ConfigManager.settings.capePriority.equals("of"))
                {
                    ConfigManager.settings.capePriority = "labymod";
                }
                else if (ConfigManager.settings.capePriority.equals("labymod"))
                {
                    ConfigManager.settings.capePriority = "original";
                }
                else
                {
                    ConfigManager.settings.capePriority = "of";
                }

                cdPriority = System.currentTimeMillis();
                LabyMod.getInstance().getCapeManager().refresh();
                this.refreshButtons();
                ConfigManager.save();
            }
            else if (button.id == 2)
            {
                this.mc2.gameSettings.switchModelPartEnabled(EnumPlayerModelParts.CAPE);
                this.refreshButtons();
            }
            else if (button.id == 3)
            {
                (new CapeUploader(new CapeCallback()
                {
                    public void failed(String error)
                    {
                        GuiCapeSettings.this.error = error;
                    }
                    public void done()
                    {
                        GuiCapeSettings.this.error = Color.cl("a") + "Cape uploaded!";
                    }
                })).start();
            }
            else if (button.id == 4)
            {
                this.moveCape = true;
                LabyMod.getInstance().getCosmeticManager().colorPicker = false;
                this.refreshButtons();
            }
            else if (button.id == 5)
            {
                if (!this.accept && this.zLevel.getText().length() != 0)
                {
                    this.accept = true;
                    this.refreshButtons();
                }
                else
                {
                    button.enabled = false;
                    (new CapeMover(this.zLevel.getText(), new CapeCallback()
                    {
                        public void done()
                        {
                            LabyMod.getInstance().getCapeManager().refresh();
                            GuiCapeSettings.this.checkCape();
                            GuiCapeSettings.this.initGui();
                        }
                        public void failed(String error)
                        {
                            GuiCapeSettings.this.initGui();
                            GuiCapeSettings.this.error = error;
                        }
                    })).start();
                }
            }
            else if (button.id == 6)
            {
                LabyMod.getInstance().openWebpage(Source.url_donate);
            }
            else if (button.id == 200)
            {
                this.mc2.gameSettings.saveOptions();
                this.mc2.displayGuiScreen(this.lastScreen);
            }
            else if (button.id == 7)
            {
                cdRefresh = System.currentTimeMillis();
                LabyMod.getInstance().getCapeManager().refresh();
                LabyMod.getInstance().getCosmeticManager().getCosmetics().clear();
                new ModInfoDownloader();
                new UserCapesDownloader();
                this.refreshButtons();
            }
            else if (button.id == 8)
            {
                this.moveCape = false;
                LabyMod.getInstance().getCosmeticManager().colorPicker = !LabyMod.getInstance().getCosmeticManager().colorPicker;
                this.refreshButtons();
            }
            else if (button.id == 9)
            {
                ConfigManager.settings.capes = Boolean.valueOf(!ConfigManager.settings.capes.booleanValue());
                this.refreshButtons();
            }
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == 1)
        {
            this.mc2.gameSettings.saveOptions();
            this.mc2.displayGuiScreen(this.lastScreen);
        }

        if (this.moveCape && this.zLevel.textboxKeyTyped(typedChar, keyCode) && this.zLevel.getText().length() > 16)
        {
            this.zLevel.setText(this.zLevel.getText().substring(0, 16));
        }

        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if (this.moveCape)
        {
            this.zLevel.mouseClicked(mouseX, mouseY, mouseButton);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        if (!LabyMod.getInstance().isInGame())
        {
            this.mc2.displayGuiScreen(this.lastScreen);
        }
        else
        {
            this.drawDefaultBackground();
            this.drawCenteredString(this.fontRendererObj, "Cape Settings", this.width / 2, 20, 16777215);

            if (!this.error.isEmpty())
            {
                int i = LabyMod.getInstance().draw.getStringWidth(this.error) / 2 + 20;
                LabyMod.getInstance();
                LabyMod.drawRect(this.width / 2 - i, 2, this.width / 2 + i, 16, Integer.MIN_VALUE);

                if (this.error.contains("Cape uploaded"))
                {
                    LabyMod.getInstance().draw.drawCenteredString(Color.cl("a") + this.error, this.width / 2, 5);
                }
                else
                {
                    LabyMod.getInstance().draw.drawCenteredString(Color.cl("4") + "Error: " + Color.cl("c") + this.error, this.width / 2, 5);
                }
            }

            boolean flag3 = this.LOGGER.enabled;
            this.LOGGER.enabled = cdPriority + 2000L < System.currentTimeMillis();

            if (this.LOGGER.enabled != flag3 && this.LOGGER.enabled)
            {
                this.refreshButtons();
            }

            if (!this.LOGGER.enabled)
            {
                this.LOGGER.displayString = "Switch.. " + this.getLoading();
            }

            boolean flag = this.selectedButton.enabled;
            this.selectedButton.enabled = cdRefresh + 10000L < System.currentTimeMillis();

            if (this.selectedButton.enabled != flag && this.selectedButton.enabled)
            {
                this.refreshButtons();
            }

            if (!this.selectedButton.enabled)
            {
                this.selectedButton.displayString = "Refresh.. " + this.getLoading();
            }

            this.statIcons.enabled = !CapeUploader.openUpload && !CapeUploader.upload;

            if (CapeUploader.upload)
            {
                this.statIcons.displayString = "Upload.. " + this.getLoading();
            }
            else
            {
                this.statIcons.displayString = "Upload..";
            }

            if (CapeMover.moving)
            {
                this.PROTOCOLS.displayString = "Moving.. " + this.getLoading();
            }

            if (this.mc2.thePlayer == null)
            {
                DrawUtils drawutils1 = LabyMod.getInstance().draw;
                DrawUtils.drawRect(this.width / 2 - 20, this.height / 2 - 30, this.width / 2 + 100, this.height / 2 + 78, 1129010000);
                int i1 = this.width / 2 + 40;
                int j1 = this.height / 2;
                LabyMod.getInstance().draw.drawCenteredString("Preview not available.", i1, j1 + 17);
                LabyMod.getInstance().draw.drawCenteredString(Color.cl("c") + "You are not ingame!", this.width / 2 + 40, this.height / 2 + 27);
            }

            this.icons.enabled = !this.moveCape && !CapeMover.moving;
            this.PROTOCOLS.enabled = !this.zLevel.getText().isEmpty() && !CapeMover.moving;
            this.PROTOCOLS.visible = this.moveCape;
            boolean flag1 = this.hasCape;
            this.checkCape();

            if (flag1 != this.hasCape)
            {
                this.optionsBackground.displayString = this.getStatus("Cape", EnumPlayerModelParts.CAPE);
            }

            if (!this.hasCape)
            {
                this.optionsBackground.enabled = false;
                this.statIcons.enabled = false;
                this.icons.enabled = false;
                this.moveCape = false;
            }
            else if (this.capeType != null && this.capeType != EnumCapePriority.LABYMOD)
            {
                this.statIcons.enabled = false;
                this.icons.enabled = false;
                this.moveCape = false;
            }
            else
            {
                this.statIcons.enabled = true;
                this.icons.enabled = true;
            }

            this.NEWLINE_SPLITTER.visible = !this.hasCape;
            boolean flag2 = this.zLevel.getText().isEmpty() && !this.zLevel.isFocused();

            if (this.moveCape)
            {
                this.zLevel.drawTextBox();

                if (flag2)
                {
                    LabyMod.getInstance().draw.drawString(Color.cl("7") + "Enter the name of the new owner..", (double)(this.zLevel.xPosition + 5), (double)(this.zLevel.yPosition + 6));
                }
                else
                {
                    for (int j = 0; j <= 2; ++j)
                    {
                        LabyMod.getInstance();
                        LabyMod.drawRect(this.width / 2 - 120, this.height / 4 + 20, this.width / 2 + 120, this.height / 2 + 85, Integer.MIN_VALUE);
                    }

                    int k = 25;
                    LabyMod.getInstance().draw.drawCenteredString(Color.cl("4") + "Caution!", this.width / 2, this.height / 4 + k);
                    k = k + 10;
                    LabyMod.getInstance().draw.drawCenteredString(Color.cl("c") + "Yow won\'t be the owner of your cape", this.width / 2, this.height / 4 + k);
                    k = k + 10;
                    LabyMod.getInstance().draw.drawCenteredString(Color.cl("c") + "after moving it to another account.", this.width / 2, this.height / 4 + k);
                    k = k + 20;

                    if (this.zLevel.getText().isEmpty())
                    {
                        double d0 = (double)(this.width / 2 - 110);
                        LabyMod.getInstance().draw.drawString("-> The new owner of the cape will", d0, (double)(this.height / 4 + k));
                        k = k + 10;
                        d0 = (double)(this.width / 2 - 95);
                        LabyMod.getInstance().draw.drawString("then be changed", d0, (double)(this.height / 4 + k));
                        k = k + 20;
                        d0 = (double)(this.width / 2 - 110);
                        LabyMod.getInstance().draw.drawString("-> That cannot be undone unless ", d0, (double)(this.height / 4 + k));
                        k = k + 10;
                        d0 = (double)(this.width / 2 - 95);
                        LabyMod.getInstance().draw.drawString("the new owner moves their", d0, (double)(this.height / 4 + k));
                        k = k + 10;
                        d0 = (double)(this.width / 2 - 95);
                        LabyMod.getInstance().draw.drawString("cape back to your account.", d0, (double)(this.height / 4 + k));
                        k = k + 10;
                    }
                    else
                    {
                        double d1 = (double)(this.width / 2 - 110);
                        LabyMod.getInstance().draw.drawString("-> The new owner of the cape will", d1, (double)(this.height / 4 + k));
                        k = k + 10;
                        LabyMod.getInstance().draw.drawString("then be " + Color.cl("e") + this.zLevel.getText(), (double)(this.width / 2 - 95), (double)(this.height / 4 + k));
                        k = k + 20;
                        d1 = (double)(this.width / 2 - 110);
                        LabyMod.getInstance().draw.drawString("-> That cannot be undone unless ", d1, (double)(this.height / 4 + k));
                        k = k + 10;
                        LabyMod.getInstance().draw.drawString("" + Color.cl("e") + this.zLevel.getText() + Color.cl("f") + " moves their", (double)(this.width / 2 - 95), (double)(this.height / 4 + k));
                        k = k + 10;
                        d1 = (double)(this.width / 2 - 95);
                        LabyMod.getInstance().draw.drawString("cape back to your account.", d1, (double)(this.height / 4 + k));
                        k = k + 10;
                    }
                }
            }

            this.LOGGER.visible = flag2;
            this.optionsBackground.visible = flag2;
            this.statIcons.visible = flag2;
            this.icons.visible = flag2;

            if (flag2)
            {
                double d2 = (double)(this.width / 2 - 110);
                LabyMod.getInstance().draw.drawString("Priority:", d2, (double)(this.height / 2 - 40 - 20));
                d2 = (double)(this.width / 2 - 110);
                LabyMod.getInstance().draw.drawString("My cape:", d2, (double)(this.height / 2 + 0 - 20));

                if (this.mc2.thePlayer != null)
                {
                    DrawUtils drawutils = LabyMod.getInstance().draw;
                    DrawUtils.drawEntityOnScreen(this.width / 2 + 40, this.height / 2 + 80, 30, (float)(this.width / 2 + 35 - mouseX) * -1.0F, (float)(this.height / 2 - 20 - mouseY), 180, this.mc2.thePlayer);
                }
            }

            if (LabyMod.getInstance().getCosmeticManager().colorPicker)
            {
                if (this.height < 280)
                {
                    LabyMod.getInstance().draw.drawCenteredString(Color.cl("c") + "Your screen scale is to small to display the color picker", this.width / 2, 40);
                }
                else
                {
                    int l = 40;
                    drawRect(this.width / 2 - 100, l, this.width / 2 + 100, l + 10, Integer.MIN_VALUE);
                    float f = (float)LabyMod.getInstance().getCosmeticManager().colorR / 255.0F * 200.0F;
                    drawRect((int)f + this.width / 2 - 101, l - 3, (int)f + 2 + this.width / 2 - 99, l + 10 + 3, Integer.MAX_VALUE);
                    drawRect((int)f + this.width / 2 - 100, l - 2, (int)f + 2 + this.width / 2 - 100, l + 10 + 2, Color.toRGB(LabyMod.getInstance().getCosmeticManager().colorR, 0, 0, 200));
                    LabyMod.getInstance().draw.drawCenteredString(Color.cl("c") + LabyMod.getInstance().getCosmeticManager().colorR + "", this.width / 2, l + 1);
                    l = 60;
                    drawRect(this.width / 2 - 100, l, this.width / 2 + 100, l + 10, Integer.MIN_VALUE);
                    f = (float)LabyMod.getInstance().getCosmeticManager().colorG / 255.0F * 200.0F;
                    drawRect((int)f + this.width / 2 - 101, l - 3, (int)f + 2 + this.width / 2 - 99, l + 10 + 3, Integer.MAX_VALUE);
                    drawRect((int)f + this.width / 2 - 100, l - 2, (int)f + 2 + this.width / 2 - 100, l + 10 + 2, Color.toRGB(0, LabyMod.getInstance().getCosmeticManager().colorG, 0, 200));
                    LabyMod.getInstance().draw.drawCenteredString(Color.cl("2") + LabyMod.getInstance().getCosmeticManager().colorG + "", this.width / 2, l + 1);
                    l = 80;
                    drawRect(this.width / 2 - 100, l, this.width / 2 + 100, l + 10, Integer.MIN_VALUE);
                    f = (float)LabyMod.getInstance().getCosmeticManager().colorB / 255.0F * 200.0F;
                    drawRect((int)f + this.width / 2 - 101, l - 3, (int)f + 2 + this.width / 2 - 99, l + 10 + 3, Integer.MAX_VALUE);
                    drawRect((int)f + this.width / 2 - 100, l - 2, (int)f + 2 + this.width / 2 - 100, l + 10 + 2, Color.toRGB(0, 0, LabyMod.getInstance().getCosmeticManager().colorB, 200));
                    LabyMod.getInstance().draw.drawCenteredString(Color.cl("9") + LabyMod.getInstance().getCosmeticManager().colorB + "", this.width / 2, l + 1);
                }
            }

            super.drawScreen(mouseX, mouseY, partialTicks);
            LabyMod.getInstance().overlay(mouseX, mouseY);
        }
    }

    /**
     * Called when a mouse button is pressed and the mouse is moved around. Parameters are : mouseX, mouseY,
     * lastButtonClicked & timeSinceMouseClick.
     */
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        int i = 40;

        if (mouseX > this.width / 2 - 100 && mouseX < this.width / 2 + 100 && mouseY > i && mouseY < i + 10)
        {
            LabyMod.getInstance().getCosmeticManager().colorR = (int)((double)(mouseX - (this.width / 2 - 100)) * 1.285D);
        }

        i = 60;

        if (mouseX > this.width / 2 - 100 && mouseX < this.width / 2 + 100 && mouseY > i && mouseY < i + 10)
        {
            LabyMod.getInstance().getCosmeticManager().colorG = (int)((double)(mouseX - (this.width / 2 - 100)) * 1.285D);
        }

        i = 80;

        if (mouseX > this.width / 2 - 100 && mouseX < this.width / 2 + 100 && mouseY > i && mouseY < i + 10)
        {
            LabyMod.getInstance().getCosmeticManager().colorB = (int)((double)(mouseX - (this.width / 2 - 100)) * 1.285D);
        }

        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    private String getLoading()
    {
        String s = "";
        int i = (int)(System.currentTimeMillis() / 100L % 3L);

        switch (i)
        {
            case 0:
                s = "\\";
                break;

            case 1:
                s = "-";
                break;

            case 2:
                s = "/";
                break;

            case 3:
                s = "|";
        }

        return s;
    }
}
