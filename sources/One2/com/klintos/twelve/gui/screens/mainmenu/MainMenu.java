// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.screens.mainmenu;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.lwjgl.opengl.GL11;

import com.klintos.twelve.Twelve;
import com.klintos.twelve.gui.components.ExpandButton;
import com.klintos.twelve.gui.screens.altmanager.GuiAltManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;

public class MainMenu extends GuiMainMenu
{
    private ParticleGenerator particles;
    
    public void initGui() {
        if (!Twelve.getInstance().ghost) {
            this.particles = new ParticleGenerator(25, this.width, this.height);
            this.viewportTexture = new DynamicTexture(256, 256);
            this.field_110351_G = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
            this.buttonList.add(new ExpandButton(0, this.width / 2 - 100, this.height / 2 - 11, 200, 20, "Multiplayer"));
            this.buttonList.add(new ExpandButton(1, this.width / 2 - 100, this.height / 2 + 11, 200, 20, "Alt Manager"));
            this.buttonList.add(new ExpandButton(2, this.width / 2 - 100, this.height / 2 + 33, 99, 20, "Singleplayer"));
            this.buttonList.add(new ExpandButton(3, this.width / 2 + 1, this.height / 2 + 33, 99, 20, "Realms"));
            this.buttonList.add(new ExpandButton(4, this.width / 2 - 100, this.height / 2 + 55, 65, 20, "Language"));
            this.buttonList.add(new ExpandButton(5, this.width / 2 - 32, this.height / 2 + 55, 64, 20, "Options"));
            this.buttonList.add(new ExpandButton(6, this.width / 2 + 35, this.height / 2 + 55, 65, 20, "Quit"));
        }
        else {
            this.viewportTexture = new DynamicTexture(256, 256);
            this.field_110351_G = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
            final Calendar var1 = Calendar.getInstance();
            var1.setTime(new Date());
            if (var1.get(2) + 1 == 11 && var1.get(5) == 9) {
                this.splashText = "Happy birthday, ez!";
            }
            else if (var1.get(2) + 1 == 6 && var1.get(5) == 1) {
                this.splashText = "Happy birthday, Notch!";
            }
            else if (var1.get(2) + 1 == 12 && var1.get(5) == 24) {
                this.splashText = "Merry X-mas!";
            }
            else if (var1.get(2) + 1 == 1 && var1.get(5) == 1) {
                this.splashText = "Happy new year!";
            }
            else if (var1.get(2) + 1 == 10 && var1.get(5) == 31) {
                this.splashText = "OOoooOOOoooo! Spooky!";
            }
            final boolean var2 = true;
            final int var3 = this.height / 4 + 48;
            if (this.mc.isDemo()) {
                this.addDemoButtons(var3, 24);
            }
            else {
                this.addSingleplayerMultiplayerButtons(var3, 24);
            }
            this.buttonList.add(new GuiButton(0, this.width / 2 - 100, var3 + 72 + 12, 98, 20, I18n.format("menu.options", new Object[0])));
            this.buttonList.add(new GuiButton(4, this.width / 2 + 2, var3 + 72 + 12, 98, 20, I18n.format("menu.quit", new Object[0])));
            this.buttonList.add(new GuiButtonLanguage(5, this.width / 2 - 124, var3 + 72 + 12));
            final Object var4 = this.field_104025_t;
            synchronized (this.field_104025_t) {
                this.field_92023_s = this.fontRendererObj.getStringWidth(this.field_92025_p);
                this.field_92024_r = this.fontRendererObj.getStringWidth(this.field_146972_A);
                final int var5 = Math.max(this.field_92023_s, this.field_92024_r);
                this.field_92022_t = (this.width - var5) / 2;
                this.field_92021_u = ((GuiButton)this.buttonList.get(0)).yPosition - 24;
                this.field_92020_v = this.field_92022_t + var5;
                this.field_92019_w = this.field_92021_u + 24;
            }
            // monitorexit(this.field_104025_t)
        }
    }
    
    public void actionPerformed(final GuiButton button) throws IOException {
        if (!Twelve.getInstance().ghost) {
            if (button.id == 0) {
                this.mc.displayGuiScreen((GuiScreen)new GuiMultiplayer((GuiScreen)this));
            }
            if (button.id == 1) {
                this.mc.displayGuiScreen((GuiScreen)new GuiAltManager());
            }
            if (button.id == 2) {
                this.mc.displayGuiScreen((GuiScreen)new GuiSelectWorld((GuiScreen)this));
            }
            if (button.id == 3) {
                this.switchToRealms();
            }
            if (button.id == 4) {
                this.mc.displayGuiScreen((GuiScreen)new GuiLanguage((GuiScreen)this, this.mc.gameSettings, this.mc.getLanguageManager()));
            }
            if (button.id == 5) {
                this.mc.displayGuiScreen((GuiScreen)new GuiOptions((GuiScreen)this, this.mc.gameSettings));
            }
            if (button.id == 6) {
                this.mc.shutdown();
            }
        }
        else {
            if (button.id == 0) {
                this.mc.displayGuiScreen((GuiScreen)new GuiOptions((GuiScreen)this, this.mc.gameSettings));
            }
            if (button.id == 5) {
                this.mc.displayGuiScreen((GuiScreen)new GuiLanguage((GuiScreen)this, this.mc.gameSettings, this.mc.getLanguageManager()));
            }
            if (button.id == 1) {
                this.mc.displayGuiScreen((GuiScreen)new GuiSelectWorld((GuiScreen)this));
            }
            if (button.id == 2) {
                this.mc.displayGuiScreen((GuiScreen)new GuiMultiplayer((GuiScreen)this));
            }
            if (button.id == 14 && this.field_175372_K.visible) {
                this.switchToRealms();
            }
            if (button.id == 4) {
                this.mc.shutdown();
            }
            if (button.id == 11) {
                this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
            }
            if (button.id == 12) {
                final ISaveFormat var2 = this.mc.getSaveLoader();
                final WorldInfo var3 = var2.getWorldInfo("Demo_World");
                if (var3 != null) {
                    final GuiYesNo var4 = GuiSelectWorld.func_152129_a((GuiYesNoCallback)this, var3.getWorldName(), 12);
                    this.mc.displayGuiScreen((GuiScreen)var4);
                }
            }
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (!Twelve.getInstance().ghost) {
            GlStateManager.disableAlpha();
            this.renderSkybox(mouseX, mouseY, partialTicks);
            GlStateManager.enableAlpha();
            this.drawGradientRect(0, 0, this.width, this.height, 0, -1610612736);
            this.drawGradientRect(0, 0, this.width, this.height, -2130750123, 16764108);
            this.mc.getTextureManager().bindTexture(MainMenu.minecraftTitleTextures);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(this.width / 2), (float)(this.height - 50), 0.0f);
            float var8 = 1.8f - MathHelper.abs(MathHelper.sin(Minecraft.getSystemTime() % 1000L / 1000.0f * 3.1415927f * 2.0f) * 0.1f);
            var8 = var8 * 100.0f / (this.fontRendererObj.getStringWidth(this.splashText) + 32);
            GL11.glScalef(var8 + 1.0f, var8 + 1.0f, var8 + 1.0f);
            if (Twelve.getInstance().outdated) {
                this.drawCenteredString(this.fontRendererObj, "§cClient outdated!", 0, 0, -1);
            }
            GL11.glPopMatrix();
            drawRect(this.width / 2 - 104, this.height / 2 - 79, this.width / 2 + 104, this.height / 2 + 79, 1073741824);
            final float scale = 7.0f;
            GL11.glScalef(scale, scale, scale);
            this.drawString(this.fontRendererObj, "ONE§c2", this.width / 2 / (int) scale - (int) 12.0f, this.height / 2 / (int) scale - (int) 10.3f, 16777215);
            GL11.glScalef(1.0f / scale, 1.0f / scale, 1.0f / scale);
            this.drawString(this.fontRendererObj, "Minecraft §c1.8", (int) 2.0f, (int)(this.height - 10), 16777215);
            this.drawString(this.fontRendererObj, "ONE2 §cb1", (int)(this.width - this.fontRendererObj.getStringWidth("ONE2 b1") - 2), (int)(this.height - 10), 16777215);
            this.drawString(this.fontRendererObj, "Welcome §c" + this.mc.session.username, (int)(this.width / 2 - this.fontRendererObj.getStringWidth("Welcome " + this.mc.session.username) / 2), (int)(this.height - 10), -1);
            this.particles.drawParticles();
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
