package net.minecraft.client.gui;

import java.io.IOException;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Session;

import java.util.Random;

import org.apache.logging.log4j.core.config.plugins.ResolverUtil.Test;

import Snipeware.Client;
import Snipeware.gui.alt.gui.GuiAltManager;
import Snipeware.gui.menu.ClientMainMenu;
import Snipeware.module.impl.movement.Flight;
import Snipeware.util.other.PlayerUtil;

public class GuiDisconnected extends GuiScreen
{
	
	

    private String reason;
    private IChatComponent message;
    private List<String> multilineMessage;
    private final GuiScreen parentScreen;
    private int field_175353_i;

   

    public GuiDisconnected(GuiScreen screen, String reasonLocalizationKey, IChatComponent chatComp)
    {
        this.parentScreen = screen;
        this.reason = I18n.format(reasonLocalizationKey, new Object[0]);
        this.message = chatComp;
   
    }

   


    	public static String generateRandomPassword(int len) {
    		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
              +"lmnopqrstuvwxyz!@#$%&";
    		Random rnd = new Random();
    		StringBuilder sb = new StringBuilder(len);
    		for (int i = 0; i < len; i++)
    			sb.append(chars.charAt(rnd.nextInt(chars.length())));
    		return sb.toString();
    	}
    
    
    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.buttonList.clear();
        this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), this.width - 50);
        this.field_175353_i = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT, I18n.format("Reconnect", new Object[0])));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 21, I18n.format("Random Username", new Object[0])));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 42, I18n.format("AltManager", new Object[0])));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 63, I18n.format("gui.toMenu", new Object[0])));
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        if (button.id == 1)
        {
        	mc.displayGuiScreen(new GuiConnecting(new GuiMultiplayer(new GuiMainMenu()), mc, mc.lastServerData));
        }
        if (button.id == 2)
        {
                 Minecraft.getMinecraft().session = new Session(generateRandomPassword(8), "", "", "mojang");
                 mc.displayGuiScreen(new GuiConnecting(new GuiMultiplayer(new ClientMainMenu()), mc, mc.lastServerData));   
        }
        if (button.id == 3)
        {
              
                 mc.displayGuiScreen(new GuiAltManager());   
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */	
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
    //	  if(Client.getInstance().getModuleManager().getModule("Skyblock").isEnabled()) {
	//		  mc.displayGuiScreen(new GuiConnecting(new GuiMultiplayer(new GuiMainMenu()), mc, mc.lastServerData));
	//		}
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.reason, this.width / 2, this.height / 2 - this.field_175353_i / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
        int i = this.height / 2 - this.field_175353_i / 2;

        if (this.multilineMessage != null)
        {
            for (String s : this.multilineMessage)
            {
                this.drawCenteredString(this.fontRendererObj, s, this.width / 2, i, 16777215);
                i += this.fontRendererObj.FONT_HEIGHT;
            }
        }
        GuiConnecting.isconnected = false;

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
}
