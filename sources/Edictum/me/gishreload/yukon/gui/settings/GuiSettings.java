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

import me.gishreload.yukon.gui.DarkButtons;

public class GuiSettings extends GuiScreen
{
    private final GuiScreen parentScreen;
    private EntityLiving par3EntityPlayer;
    private EntityPlayerMP thePlayer;

    public GuiSettings(GuiScreen par1GuiScreen)
    {
        this.parentScreen = par1GuiScreen;
    }


    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
    	this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, "Flight"));
    	this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 100 + 12, "KillAura"));
    	this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 80 + 12, "Color"));
    	this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 60 + 12, "Scaffold"));
    }
    
    protected void actionPerformed(GuiButton par1GuiButton)
    {
    	if (par1GuiButton.id == 0)
        {
            this.mc.displayGuiScreen(new GuiSettings(this.parentScreen));
        }
    	
    	if (par1GuiButton.id == 1)
        {
            this.mc.displayGuiScreen(new FlightSetting(this.parentScreen));
        }
    	
    	if (par1GuiButton.id == 2)
        {
            this.mc.displayGuiScreen(new KillAuraSetting(this.parentScreen));
        }
    	
    	if (par1GuiButton.id == 3)
        {
            this.mc.displayGuiScreen(new ColorSetting(this.parentScreen));
        }
    	if (par1GuiButton.id == 4)
        {
            this.mc.displayGuiScreen(new ScaffoldSetting(this.parentScreen));
        }

    }
}
    
    