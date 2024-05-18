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

public class ScaffoldSetting extends GuiScreen
{
    private final GuiScreen parentScreen;
    private EntityLiving par3EntityPlayer;
    private EntityPlayerMP thePlayer;

    public ScaffoldSetting(GuiScreen par1GuiScreen)
    {
        this.parentScreen = par1GuiScreen;
    }
    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
    	this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 41 + 12, "safe"));
    }
    
    protected void actionPerformed(GuiButton par1GuiButton)
    {	
    	if (par1GuiButton.id == 1)
        {		
    		Meanings.SafeWalk2 = !Meanings.SafeWalk2;
        }
    	
    } 
/**
 * Draws the screen and all the components in it.
 */
    public void drawScreen(int par1, int par2, float par3)
    {
    	this.drawDefaultBackground();
        	mc.fontRendererObj.drawString("\u00a7aSafe = "+Meanings.SafeWalk2, 74, 4, 0xFF0000);
    	super.drawScreen(par1, par2, par3);
    }
}
    
    