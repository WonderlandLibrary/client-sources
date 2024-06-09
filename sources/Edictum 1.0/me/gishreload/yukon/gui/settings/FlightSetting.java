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

public class FlightSetting extends GuiScreen
{
    private final GuiScreen parentScreen;
    private EntityLiving par3EntityPlayer;
    private EntityPlayerMP thePlayer;

    public FlightSetting(GuiScreen par1GuiScreen)
    {
        this.parentScreen = par1GuiScreen;
    }
    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
    	this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 41 + 12, "+(speed)"));
    	this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 60 + 12, "-(speed)"));
    	this.buttonList.add(new GuiButton(9, this.width / 2 - 100, this.height / 4 + 0 + 12, "+ (damage)"));
    	this.buttonList.add(new GuiButton(8, this.width / 2 - 100, this.height / 4 + -20 + 12, "- (damage)"));
    	this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 20 + 12, "ncp damage"));
    }
    
    protected void actionPerformed(GuiButton par1GuiButton)
    {
    	if (par1GuiButton.id == 0)
        {
            this.mc.displayGuiScreen(new FlightSetting(this.parentScreen));
        }
    	
    	if (par1GuiButton.id == 1)
        {		
    		mc.flightspeed += 0.1F;
        }
    	
    	if (par1GuiButton.id == 2)
        {		
    		mc.flightspeed -= 0.1F;
        }
    	
    	if (par1GuiButton.id == 9)
        {		
    		mc.glidedamage += 0.1D;
        }
    	
    	if (par1GuiButton.id == 8)
        {		
    		mc.glidedamage -= 0.1D;
        }
    	
    	if (par1GuiButton.id == 3)
        {		
    		Meanings.glidedamage = !Meanings.glidedamage;
        }
    	
    } 
/**
 * Draws the screen and all the components in it.
 */
    public void drawScreen(int par1, int par2, float par3)
    {
    	this.drawDefaultBackground();
    	mc.fontRendererObj.drawString("\u00a7aFlight speed = "+mc.flightspeed, 74, 4, 0xFF0000);
    	mc.fontRendererObj.drawString("\u00a7adamage = "+mc.glidedamage, 74, 14, 0xFF0000);
    	if(Meanings.glidedamage){
    	mc.fontRendererObj.drawString("\u00a7aNCP damage = true", 74, 24, 0xFF0000);
    	}
    	if(!Meanings.glidedamage){
    	mc.fontRendererObj.drawString("\u00a7aNCP damage = false", 74, 24, 0xFF0000);
    	}
    	super.drawScreen(par1, par2, par3);
    }
}
    
    