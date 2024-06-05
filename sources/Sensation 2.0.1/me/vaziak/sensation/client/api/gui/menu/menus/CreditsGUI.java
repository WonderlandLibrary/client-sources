package me.vaziak.sensation.client.api.gui.menu.menus;

import java.awt.Color;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.gui.ingame.clickui.Screen;
import me.vaziak.sensation.client.api.gui.menu.components.IconButton;
import me.vaziak.sensation.client.api.gui.menu.menus.account.DirectLoginGui;
import me.vaziak.sensation.client.impl.visual.Interface;
import me.vaziak.sensation.utils.anthony.Draw;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;
import me.vaziak.sensation.utils.client.MusicPlayer;
import me.vaziak.sensation.utils.math.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class CreditsGUI extends GuiScreen implements GuiYesNoCallback {
    private GuiScreen parentScreen;
    
    public CreditsGUI(GuiScreen parent) {
    	this.parentScreen = parent;
    }

    public void initGui() {
        super.initGui();
 
    } 
    protected void actionPerformed(GuiButton button) throws IOException { 
    }
   /* public void drawScreen(int mouseX, int mouseY, float partialTicks) {

    	MusicPlayer mp = new MusicPlayer();
       	mp.started = true;
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.enableAlpha();
        Gui.drawRect(0,0,width,height,new Color(0,0,0, 150).getRGB());
        drawGradientRect(0, 0, width, height, new Color(0,0,0).getRGB(), new Color(0,0,0, 120).getRGB());
        drawGradientRect(0, 0, width, height, new Color(0,0,0, 0).getRGB(), new Color(0,0,0, 120).getRGB());
        GlStateManager.popMatrix();

        GlStateManager.color(1f, 1f, 1f, 1f);
        Draw.drawImg(new ResourceLocation("client/Background1.jpg"),1, 1, width, height);
        int logoPositionY = height / 2 - 130;
        *//*Credits section*//*
        Fonts.bf28.drawStringWithShadow("Credits: ", 2, 10, new Color(255, 64, 37).getRGB());
        Fonts.bf20.drawStringWithShadow("AntiVirus fly:", 2, 30, new Color(255, 64, 37).getRGB());
        Fonts.bf20.drawStringWithShadow("WinterwarePvP", 2, 40, new Color(255, 64, 37).getRGB());


        Fonts.bf20.drawStringWithShadow("ClickGUI/Buttons:", 120, 30, new Color(255, 64, 37).getRGB());
        Fonts.bf20.drawStringWithShadow("Anthony Jullian aka AnthonyJ", 120, 40, new Color(255, 64, 37).getRGB());
        
        Draw.drawBorderedRectangle(2, 70, 350, 110, 2, new Color (0,0,0).getRGB(), new Color (255,0,0).getRGB(), true);
        Fonts.bf20.drawStringWithShadow("Some people have attempted to dox me and come after sensation, ", 5, 80, new Color(255, 64, 37).getRGB());
        Fonts.bf20.drawStringWithShadow("So I decided to accurately dox them... ", 5, 90, new Color(255, 64, 37).getRGB());
     
        Draw.drawBorderedRectangle(2, 100, 263, 459, 2, new Color (0,0,0).getRGB(), new Color (255,0,0).getRGB(), true);
        Fonts.bf20.drawStringWithShadow("Final Exception (Morait): ", 6, 110, new Color(255, 64, 37).getRGB());
        Fonts.bf20.drawStringWithShadow("Age: 13, YOB: 2006", 10, 120, new Color(255, 64, 37).getRGB()); 
        Fonts.bf20.drawStringWithShadow("Aliases: Vlad Vlucci, Final", 10, 130, new Color(255, 64, 37).getRGB());
        Fonts.bf20.drawStringWithShadow("Living Location(s): Bucharest Romania", 10, 140, new Color(255, 64, 37).getRGB());
        Fonts.bf20.drawStringWithShadow("  Parent(s): ", 10, 150, new Color(255, 64, 37).getRGB());
        Fonts.bf20.drawStringWithShadow("  	Mothers Name: Anda Vilcu Morait", 15, 160, new Color(255, 64, 37).getRGB());
        Fonts.bf20.drawStringWithShadow("  Mothers Date of Birth: September 6, 1973", 15, 170, new Color(255, 64, 37).getRGB());
        Fonts.bf20.drawStringWithShadow(" 	 Home Town: Bucharest Romania", 15, 180, new Color(255, 64, 37).getRGB());	
        Fonts.bf20.drawStringWithShadow(" 	 Educated At: ASE Bucuresti - FABBV", 15, 190, new Color(255, 64, 37).getRGB());
        

        GlStateManager.color(1f, 1f, 1f, 1f);
        Draw.drawImg(new ResourceLocation("client/gui/logo/final incorperated.jpg"),5, 200, 128, 128);
        Fonts.bf20.drawStringWithShadow("Thicc boiii", 5, 210, new Color(255, 64, 37).getRGB());
        Fonts.bf20.drawStringWithShadow("build sandcastle", 5, 220, new Color(255, 64, 37).getRGB());
        GlStateManager.color(1f, 1f, 1f, 1f);
        Draw.drawImg(new ResourceLocation("client/gui/logo/SPOTLIGHTUHMOONLIGHTUH.jpeg"),132, 200, 128, 128);
        Fonts.bf20.drawStringWithShadow("Say hi to Jonathan son", 132, 290, new Color(255, 64, 37).getRGB());
        GlStateManager.color(1f, 1f, 1f, 1f);
        Draw.drawImg(new ResourceLocation("client/gui/logo/final.jpg"),5, 328, 128, 128);
        Draw.drawImg(new ResourceLocation("client/gui/logo/she hot tho.png"),132, 328, 128, 128);
        Fonts.bf20.drawStringWithShadow("Stop looking at me vaziak", 132, 428, new Color(255, 64, 37).getRGB()); 
        super.drawScreen(mouseX, mouseY, partialTicks);
    }*/

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            Minecraft.getMinecraft().displayGuiScreen(parentScreen);
        }
        super.keyTyped(typedChar, keyCode);
    }
}
