package me.gishreload.yukon.gui.settings;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import me.gishreload.yukon.Meanings;
import me.gishreload.yukon.gui.DarkButtons;
import me.gishreload.yukon.hacks.Flight;

public class ColorSetting extends GuiScreen
{
    private final GuiScreen parentScreen;
    private EntityLiving par3EntityPlayer;
    private EntityPlayerMP thePlayer;

    public ColorSetting(GuiScreen par1GuiScreen)
    {
        this.parentScreen = par1GuiScreen;
    }
    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
    	this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 0 + 12, "Gold"));
    	this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 20 + 12, "Green"));
    	this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 40 + 12, "Violet"));
    	this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 60 + 12, "Red"));
    	this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 80 + 12, "Default"));
    }
    
    protected void actionPerformed(GuiButton par1GuiButton)
    {
    	if (par1GuiButton.id == 0)
        {
            this.mc.displayGuiScreen(new ColorSetting(this.parentScreen));
        }
    	
    	if (par1GuiButton.id == 1)
        {		
    		Meanings.Violet = true;
    		Meanings.Red = false;
    		Meanings.Default = false;
    		Meanings.Green = false;
    		Meanings.Gold = false;
        }
    	
    	if (par1GuiButton.id == 2)
        {		
    		Meanings.Red = true;
    		Meanings.Violet = false;
    		Meanings.Default = false;
    		Meanings.Green = false;
    		Meanings.Gold = false;
        }
    	
    	if (par1GuiButton.id == 3)
        {		
    		Meanings.Red = false;
    		Meanings.Violet = false;
    		Meanings.Default = true;
    		Meanings.Green = false;
    		Meanings.Gold = false;
        }
    	
    	if (par1GuiButton.id == 4)
        {		
    		Meanings.Red = false;
    		Meanings.Violet = false;
    		Meanings.Default = false;
    		Meanings.Green = true;
    		Meanings.Gold = false;
        }
    	
    	if (par1GuiButton.id == 5)
        {		
    		Meanings.Red = false;
    		Meanings.Violet = false;
    		Meanings.Default = false;
    		Meanings.Green = false;
    		Meanings.Gold = true;
        }
    } 
/**
 * Draws the screen and all the components in it.
 */
    public void drawScreen(int par1, int par2, float par3)
    {
    	this.drawDefaultBackground();
    	
    	if(!Meanings.Red){
    		mc.fontRendererObj.drawString("\u00a7f Red", 74, 4, 0xFF0000);
    	}else{
    		mc.fontRendererObj.drawString("\u00a74 Red", 74, 4, 0xFF0000);
    	}
    	
    	if(!Meanings.Violet){
    		mc.fontRendererObj.drawString("\u00a7f Violet", 74, 14, 0xFF0000);
    	}else{
    		mc.fontRendererObj.drawString("\u00a75 Violet", 74, 14, 0xFF0000);
    	}
    	
    	if(!Meanings.Default){
    		mc.fontRendererObj.drawString("\u00a7f Default", 74, 24, 0xFF0000);
    	}else{
    		mc.fontRendererObj.drawString("\u00a77 Default", 74, 24, 0xFF0000);
    	}
    	
    	if(!Meanings.Green){
    		mc.fontRendererObj.drawString("\u00a7f Green", 74, 34, 0xFF0000);
    	}else{
    		mc.fontRendererObj.drawString("\u00a72 Green", 74, 34, 0xFF0000);
    	}
    	
    	if(!Meanings.Gold){
    		mc.fontRendererObj.drawString("\u00a7f Gold", 74, 44, 0xFF0000);
    	}else{
    		mc.fontRendererObj.drawString("\u00a76 Gold", 74, 44, 0xFF0000);
    	}

    	super.drawScreen(par1, par2, par3);
    }
}
    
    