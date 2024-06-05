package net.minecraft.client.gui;

import me.satisfactory.base.utils.timer.*;
import me.mees.remix.font.*;
import org.lwjgl.opengl.*;
import me.satisfactory.base.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.input.*;
import net.minecraft.util.*;
import me.mees.remix.ui.gui.*;
import me.satisfactory.base.gui.*;
import java.awt.*;
import java.net.*;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback
{
    public static int anModule;
    public static int anAlt;
    public static int anSettings;
    public static int anPlayer;
    public static int anExit;
    public static int anMenu;
    public static int anSingle;
    public CFontRenderer clientFont;
    public CFontRenderer clientFont2;
    public static int anMulti;
    public static int anPlayerLoader;
    public static boolean playHovered;
    public static int bouncybounce;
    public static int clickeramount;
    public static int clicksec;
    public static boolean MenuOpen;
    public static int discordPlus;
    public static boolean isMees;
    boolean previousmouse;
    TimerUtil timer;
    
    public GuiMainMenu() {
        this.clientFont = FontLoaders.confortaa22;
        this.clientFont2 = FontLoaders.confortaa17;
        this.previousmouse = true;
        this.timer = new TimerUtil();
    }
    
    public static void doEffect() {
    }
    
    @Override
    public void initGui() {
        super.initGui();
    }
    
    public boolean isHovered(final float f, final int y, final float g, final int y2, final int mouseX, final int mouseY) {
        return mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GuiMainMenu.mc.getTextureManager().bindTexture(Base.INSTANCE.getMainMenu());
        this.drawTexturedModalRect(0, 0, 0, 0, GuiMainMenu.width, GuiMainMenu.height);
        Gui.drawScaledCustomSizeModalRect(0.0, 0.0, 0.0f, 0.0f, GuiMainMenu.width, GuiMainMenu.height, GuiMainMenu.width, GuiMainMenu.height, (float)GuiMainMenu.width, (float)GuiMainMenu.height);
        Gui.drawRect(0, GuiMainMenu.height - 25, GuiMainMenu.width, GuiMainMenu.height, new Color(0, 0, 0, 94).getRGB());
        Gui.drawRect(0, GuiMainMenu.height - 25, GuiMainMenu.width, GuiMainMenu.height - 25 + 1, Color.white.getRGB());
        RenderHelper.renderImage("remix/Remix logo 1.png", GuiMainMenu.width / 2 - 75 - GuiMainMenu.bouncybounce / 2, GuiMainMenu.height / 2 - 75 - GuiMainMenu.bouncybounce / 2, 150 + GuiMainMenu.bouncybounce, 150 + GuiMainMenu.bouncybounce);
        if (GuiMainMenu.clickeramount > 0) {
            this.clientFont.drawString(GuiMainMenu.clickeramount + "", (float)(GuiMainMenu.width - this.clientFont.getStringWidth(GuiMainMenu.clickeramount + "") - 5), (float)(this.clientFont.getStringHeight(GuiMainMenu.clickeramount + "") / 2), Color.white.getRGB());
        }
        if (!Mouse.isButtonDown(0)) {
            this.previousmouse = false;
        }
        if (this.isHovered((float)(GuiMainMenu.width / 2 - 75), GuiMainMenu.height / 2 - 75, (float)(GuiMainMenu.width / 2 - 75 + 150), GuiMainMenu.height / 2 - 75 + 150, mouseX, mouseY) && Mouse.isButtonDown(0)) {
            GuiMainMenu.bouncybounce = -10;
            if (!this.previousmouse) {
                ++GuiMainMenu.clickeramount;
                ++GuiMainMenu.clicksec;
                this.previousmouse = true;
            }
        }
        else {
            GuiMainMenu.bouncybounce = 0;
        }
        if (GuiMainMenu.playHovered) {
            Gui.drawRoundedRect(15.0f, (float)(GuiMainMenu.height - 25 - 15 - GuiMainMenu.anPlayerLoader), 150.0f, (float)(GuiMainMenu.height - 25 - 5), 4.0f, new Color(0, 0, 0, 150).getRGB());
            if (GuiMainMenu.anPlayerLoader == 60) {
                Gui.drawRect(0, 0, 0, 0, 0);
                RenderHelper.renderImage("remix/singleplayer.png", 30 - GuiMainMenu.anSingle / 2, GuiMainMenu.height - 25 - 5 - 35 - 25 - GuiMainMenu.anSingle, 35 + GuiMainMenu.anSingle, 35 + GuiMainMenu.anSingle);
                this.clientFont.drawString("Single", 32.0f, (float)(GuiMainMenu.height - 25 - 5 - 20), Color.white.getRGB());
            }
            if (this.isHovered(30.0f, GuiMainMenu.height - 25 - 5 - 35 - 25, 65.0f, GuiMainMenu.height - 25 - 5 - 35 - 25 + 35, mouseX, mouseY)) {
                GuiMainMenu.anSingle = Math.max(2, GuiMainMenu.anSingle++);
                if (Mouse.isButtonDown(0)) {
                    GuiMainMenu.mc.displayGuiScreen(new GuiSelectWorld(this));
                }
            }
            else {
                GuiMainMenu.anSingle = Math.min(0, GuiMainMenu.anSingle--);
            }
            if (GuiMainMenu.anPlayerLoader == 60) {
                RenderHelper.renderImage(new ResourceLocation("remix/multiplayer.png"), 85 - GuiMainMenu.anMulti / 2, GuiMainMenu.height - 25 - 5 - 35 - 25 - GuiMainMenu.anMulti, 52 + GuiMainMenu.anMulti, 35 + GuiMainMenu.anMulti);
                this.clientFont.drawString("Multi", 98.0f, (float)(GuiMainMenu.height - 25 - 5 - 20), Color.white.getRGB());
            }
            if (this.isHovered(85.0f, GuiMainMenu.height - 25 - 5 - 35 - 25, 150.0f, GuiMainMenu.height - 25 - 5 - 35 - 25 + GuiMainMenu.height - 25 - 5 - 35 - 25 + 35, mouseX, mouseY)) {
                GuiMainMenu.anMulti = Math.max(2, GuiMainMenu.anMulti++);
                if (Mouse.isButtonDown(0)) {
                    GuiMainMenu.mc.displayGuiScreen(new GuiMultiplayer(this));
                }
            }
            else {
                GuiMainMenu.anMulti = Math.min(0, GuiMainMenu.anMulti--);
            }
        }
        this.clientFont.drawString("By Aidan & Mees", (float)(GuiMainMenu.width - this.clientFont.getStringWidth("By Aidan & Mees") - 15), (float)((GuiMainMenu.height + (GuiMainMenu.height - 25)) / 2 - 2), Color.white.getRGB());
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderHelper.drawIcon((float)(108 - GuiMainMenu.anModule / 2), (float)((GuiMainMenu.height - 25 + (GuiMainMenu.height - 17)) / 2 - GuiMainMenu.anModule), 17 + GuiMainMenu.anModule, 17 + GuiMainMenu.anModule, Base.INSTANCE.getModuleImg());
        RenderHelper.drawIcon((float)(78 - GuiMainMenu.anAlt / 2), (float)((GuiMainMenu.height - 25 + (GuiMainMenu.height - 17)) / 2 - GuiMainMenu.anAlt), 13 + GuiMainMenu.anAlt, 17 + GuiMainMenu.anAlt, Base.INSTANCE.getAlt());
        RenderHelper.drawIcon((float)(45 - GuiMainMenu.anSettings / 2), (float)((GuiMainMenu.height - 25 + (GuiMainMenu.height - 15)) / 2 - GuiMainMenu.anSettings), 15 + GuiMainMenu.anSettings, 15 + GuiMainMenu.anSettings, Base.INSTANCE.getSettings());
        RenderHelper.drawIcon((float)(15 - GuiMainMenu.anPlayer / 2), (float)((GuiMainMenu.height - 25 + (GuiMainMenu.height - 15)) / 2 - GuiMainMenu.anPlayer), 13 + GuiMainMenu.anPlayer, 15 + GuiMainMenu.anPlayer, Base.INSTANCE.getPlayer());
        RenderHelper.drawIcon((float)(GuiMainMenu.width / 2 - 2 - GuiMainMenu.anExit / 2), (float)((GuiMainMenu.height - 25 + (GuiMainMenu.height - 17)) / 2 - GuiMainMenu.anExit), 16 + GuiMainMenu.anExit, 17 + GuiMainMenu.anExit, Base.INSTANCE.getExit());
        RenderHelper.drawIcon((float)(GuiMainMenu.width - 8), (float)((GuiMainMenu.height - 25 + (GuiMainMenu.height - 16)) / 2), 3, 16, Base.INSTANCE.getvMenu());
        if (this.isHovered(108.0f, GuiMainMenu.height - 25 + 4, 127.0f, GuiMainMenu.height - 25 + 4 + 19, mouseX, mouseY)) {
            GuiMainMenu.anModule = Math.max(2, GuiMainMenu.anModule++);
            if (Mouse.isButtonDown(0)) {
                GuiMainMenu.mc.displayGuiScreen(new CSGOGuiScreen());
            }
        }
        else {
            GuiMainMenu.anModule = Math.min(0, GuiMainMenu.anModule--);
        }
        if (this.isHovered(78.0f, GuiMainMenu.height - 25 + 4, 93.0f, GuiMainMenu.height - 25 + 4 + 19, mouseX, mouseY)) {
            GuiMainMenu.anAlt = Math.max(2, GuiMainMenu.anAlt++);
            if (Mouse.isButtonDown(0)) {
                GuiMainMenu.mc.displayGuiScreen(new GuiAltLogin(this));
            }
        }
        else {
            GuiMainMenu.anAlt = Math.min(0, GuiMainMenu.anAlt--);
        }
        if (this.isHovered(45.0f, GuiMainMenu.height - 25 + 6, 62.0f, GuiMainMenu.height - 25 + 6 + 17, mouseX, mouseY)) {
            GuiMainMenu.anSettings = Math.max(2, GuiMainMenu.anSettings++);
            if (Mouse.isButtonDown(0)) {
                GuiMainMenu.mc.displayGuiScreen(new GuiOptions(this, GuiMainMenu.mc.gameSettings));
            }
        }
        else {
            GuiMainMenu.anSettings = Math.min(0, GuiMainMenu.anSettings--);
        }
        if (this.isHovered(15.0f, GuiMainMenu.height - 25 + 6, 30.0f, GuiMainMenu.height - 25 + 6 + 17, mouseX, mouseY) || (GuiMainMenu.playHovered && this.isHovered(15.0f, GuiMainMenu.height - 25 - 85, 150.0f, GuiMainMenu.height - 25 + 5, mouseX, mouseY))) {
            if (this.isHovered(15.0f, GuiMainMenu.height - 25 + 6, 30.0f, GuiMainMenu.height - 25 + 6 + 17, mouseX, mouseY)) {
                GuiMainMenu.anPlayer = Math.max(2, GuiMainMenu.anPlayer++);
            }
            else {
                GuiMainMenu.anPlayer = 0;
            }
            if (GuiMainMenu.anPlayerLoader < 60) {
                GuiMainMenu.anPlayerLoader += 10;
            }
            GuiMainMenu.playHovered = true;
        }
        else {
            GuiMainMenu.playHovered = false;
            GuiMainMenu.anPlayer = Math.min(0, GuiMainMenu.anPlayer--);
            GuiMainMenu.anPlayerLoader = 0;
        }
        if (this.isHovered((float)(GuiMainMenu.width / 2 - 2), GuiMainMenu.height - 25 + 5, (float)(GuiMainMenu.width / 2 + 18), GuiMainMenu.height - 25 + 5 + 19, mouseX, mouseY)) {
            GuiMainMenu.anExit = Math.max(2, GuiMainMenu.anExit++);
            if (Mouse.isButtonDown(0)) {
                GuiMainMenu.mc.shutdown();
            }
        }
        else {
            GuiMainMenu.anExit = Math.min(0, GuiMainMenu.anExit--);
        }
        if (this.isHovered((float)(GuiMainMenu.width - 8), GuiMainMenu.height - 21, (float)(GuiMainMenu.width - 8 + 3), GuiMainMenu.height - 21 + 16, mouseX, mouseY) || (this.isHovered((float)(GuiMainMenu.width - 120), GuiMainMenu.height - 25 - 5 - 100, (float)GuiMainMenu.width, GuiMainMenu.height - 25 + 15, mouseX, mouseY) && GuiMainMenu.MenuOpen)) {
            GuiMainMenu.anMenu = Math.max(2, GuiMainMenu.anMenu++);
            if (GuiMainMenu.discordPlus < 110) {
                GuiMainMenu.discordPlus += 10;
            }
            GuiMainMenu.MenuOpen = true;
            Gui.drawRoundedRect((float)(GuiMainMenu.width - 120), (float)(GuiMainMenu.height - 25 - 5 - GuiMainMenu.discordPlus), (float)(GuiMainMenu.width - 8), (float)(GuiMainMenu.height - 25 - 5), 4.0f, new Color(0, 0, 0, 150).getRGB());
            if (this.isHovered((float)(GuiMainMenu.width - 25 - this.clientFont.getStringWidth(">") / 2), GuiMainMenu.height - 25 - 60 - 34, (float)(GuiMainMenu.width - 25 + this.clientFont.getStringWidth("<")), GuiMainMenu.height - 25 - 60 - 34 + this.clientFont.getStringHeight(">"), mouseX, mouseY) && Mouse.isButtonDown(0) && !this.previousmouse) {
                GuiMainMenu.isMees = !GuiMainMenu.isMees;
                this.previousmouse = true;
            }
            if (this.isHovered((float)(GuiMainMenu.width - 110 - this.clientFont.getStringWidth("<") / 2), GuiMainMenu.height - 25 - 60 - 34, (float)(GuiMainMenu.width - 110 + this.clientFont.getStringWidth("<")), GuiMainMenu.height - 25 - 60 - 34 + this.clientFont.getStringHeight("<"), mouseX, mouseY) && Mouse.isButtonDown(0) && !this.previousmouse) {
                GuiMainMenu.isMees = !GuiMainMenu.isMees;
                this.previousmouse = true;
            }
            if (this.isHovered((float)(GuiMainMenu.width - 64 - this.clientFont2.getStringWidth("Discord.gg/RemixClient") / 2), GuiMainMenu.height - 25 - 90 + 60 - this.clientFont.getStringHeight("Discord.gg/RemixClient") / 2, (float)(GuiMainMenu.width - 64 + this.clientFont.getStringWidth("Discord.gg/RemixClient") / 2), GuiMainMenu.height - 25 - 90 + 60 + this.clientFont.getStringHeight("Discord.gg/RemixClient"), mouseX, mouseY) && Mouse.isButtonDown(0) && !this.previousmouse) {
                try {
                    Desktop.getDesktop().browse(new URL("http://discord.gg/RemixClient").toURI());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                this.previousmouse = true;
            }
            if (GuiMainMenu.discordPlus == 110) {
                this.clientFont.drawString("<", (float)(GuiMainMenu.width - 110), (float)(GuiMainMenu.height - 25 - 60 - 34), Color.white.getRGB());
                this.clientFont.drawString(">", (float)(GuiMainMenu.width - 25), (float)(GuiMainMenu.height - 25 - 60 - 34), Color.white.getRGB());
                this.clientFont.drawCenteredString(GuiMainMenu.isMees ? "Mees#9579" : "Aidan#1337", (float)(GuiMainMenu.width - 67), (float)(GuiMainMenu.height - 25 - 90 + 40), Color.white.getRGB());
                this.clientFont2.drawCenteredString("Discord.gg/RemixClient", (float)(GuiMainMenu.width - 64), (float)(GuiMainMenu.height - 25 - 90 + 60), Color.white.getRGB());
                RenderHelper.renderImage(new ResourceLocation("remix/Discord.png"), GuiMainMenu.width - 90, GuiMainMenu.height - 25 - 60 - 34, 47, 34);
            }
        }
        else {
            GuiMainMenu.discordPlus = 0;
            GuiMainMenu.anMenu = Math.min(0, GuiMainMenu.anMenu--);
            GuiMainMenu.MenuOpen = false;
        }
        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    static {
        GuiMainMenu.anModule = 0;
        GuiMainMenu.anAlt = 0;
        GuiMainMenu.anSettings = 0;
        GuiMainMenu.anPlayer = 0;
        GuiMainMenu.anExit = 0;
        GuiMainMenu.anMenu = 0;
        GuiMainMenu.anSingle = 0;
        GuiMainMenu.anMulti = 0;
        GuiMainMenu.anPlayerLoader = 0;
        GuiMainMenu.bouncybounce = 0;
        GuiMainMenu.clickeramount = 0;
        GuiMainMenu.clicksec = 0;
        GuiMainMenu.discordPlus = 0;
    }
}
