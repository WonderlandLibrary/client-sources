package me.vaziak.sensation.client.api.gui.menu.menus;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import me.vaziak.sensation.client.api.gui.menu.components.TextButton;
import net.minecraft.client.gui.*;
import net.minecraft.util.EnumChatFormatting;
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
import me.vaziak.sensation.utils.client.ClientUtil;
import me.vaziak.sensation.utils.client.MusicPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class MainMenuGui extends GuiScreen implements GuiYesNoCallback {
    private static final Logger logger = LogManager.getLogger();

    private final ResourceLocation logoPath = new ResourceLocation("client/gui/logo/128x128.png");
    private int lastButtonHeight;
    private int topButtonHeight;
    private MusicPlayer mp = new MusicPlayer();
    private int currentBackground = 1;

	private int initiates;
    public MainMenuGui() {
    }

    public void initGui() {
    	initiates++;
    	initiates = 20;
        super.initGui();
       /* if (initiates < 3) {
	            try { 
	            	if (!mp.started){
	            		mp.setStream(new URL("https://files.catbox.moe/2e5exz.mp3").openStream()); 
	            		mp.start();
	            		mp.setVolume(65);
	            		mp.started = true;
	            	}
	        } catch (IOException e) {
	        	 mp.stop();
	            e.printStackTrace();
	        }
        }*/
       this.buttonList.add(
                new IconButton(1, width / 2 - 110, height / 2 + 20, 40, 40,
                        new ResourceLocation("client/gui/icon/person/single/1_white_64.png"), 16));
        this.buttonList.add(
                new IconButton(2, width / 2 - 55, height / 2 + 20, 40, 40,
                        new ResourceLocation("client/gui/icon/person/multiple/1_white_64.png"), 16));


        //        Draw.drawImg(new ResourceLocation("client/gui/logo/64x64.png"), this.width / 2 - 32, logoPositionY - 64, 64, 64);
/*
        this.buttonList.add(
                new IconButton(3, width / 2 - 20, height / 2 + 80, 40, 40,
                        new ResourceLocation("client/gui/icon/info/info64_white.png"), 16));
*/

//        this.buttonList.add(new TextButton(124, width / 2, width / 2 - 32, height / 2 + 40, 40, "Change Background"));

        this.buttonList.add(
                new IconButton(999, width / 2, topButtonHeight = height / 2 + 20, 40, 40,
                        new ResourceLocation("client/gui/icon/account/1_white_64.png"), 16));


        this.buttonList.add(
                new IconButton(0, width / 2 + 55, height / 2 + 20, 40, 40,
                        new ResourceLocation("client/gui/icon/cog/1_white_64.png"), 16));

        this.buttonList.add(new IconButton(666, width / 2 - 32, (topButtonHeight - 30) - 64, 64, 64, new ResourceLocation("client/gui/logo/64x64.png"), 64));

        //this.buttonList.add(new IconButton(420, width / 2 - 22, height / 2 + 45, 40, 40, new ResourceLocation("client/gui/icon/cb/cb16x.png"), 16)); Will fix in a later version.

        this.mc.func_181537_a(false);
    }

    public void updateScreen() {
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 420) {
            currentBackground++;

            if (currentBackground > 2) {
                currentBackground = 1;
            }
        }
        if (button.id == 999) {

            if (initiates < 3) {
            	mp.stop(); 
            }
            this.mc.displayGuiScreen(new DirectLoginGui(this));
        }

        if (button.id == 666) {
            ClientUtil.launchWebsite("https://azuma.club");
        }

        if (button.id == 0) {

            if (initiates < 3) {
            	mp.stop(); 
            }
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings)); 
        }

        if (button.id == 1) {

            if (initiates < 3) {
            	mp.stop(); 
            }
            this.mc.displayGuiScreen(new GuiSelectWorld(this)); 
        }

        if (button.id == 2) {

            if (initiates < 3) {
            	mp.stop(); 
            }
            this.mc.displayGuiScreen(new GuiMultiplayer(this)); 
        }
        
        if (button.id == 3) {

            if (initiates < 3) {
            	mp.stop(); 
            }
            this.mc.displayGuiScreen(new CreditsGUI(this)); 
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.enableAlpha();
        /*Gui.drawRect(0,0,width,height,new Color(0,0,0, 150).getRGB());
        drawGradientRect(0, 0, width, height, new Color(0,0,0).getRGB(), new Color(0,0,0, 120).getRGB());
        drawGradientRect(0, 0, width, height, new Color(0,0,0, 0).getRGB(), new Color(0,0,0, 120).getRGB());
*/
        GlStateManager.color(1f, 1f, 1f, 1f);
        Draw.drawImg(new ResourceLocation("client/background" + currentBackground + ".jpg"),0, 0, width, height);
        GlStateManager.popMatrix();
 
        int logoPositionY = topButtonHeight - 30;
        //Fonts.bf28.drawCenteredStringWithShadow("Spooksation", this.width / 2, logoPositionY, new Color(255, 64, 37).getRGB());
        //Draw.drawImg(new ResourceLocation("client/gui/logo/64x64.png"), this.width / 2 - 32, logoPositionY - 64, 64, 64);

        ScaledResolution scaledResolution = new ScaledResolution(mc);

        Fonts.arial.drawStringWithShadow("S" + EnumChatFormatting.WHITE + "ensation", 2, scaledResolution.getScaledHeight() - Fonts.arial.getStringHeight("Sensation") - 2, new Color(255, 71, 71).getRGB());
        Fonts.arial.drawStringWithShadow("Developed by Vaziak & Criiptic", scaledResolution.getScaledWidth() - Fonts.arial.getStringWidth("Developed by Vaziak & Criiptic") - 2, scaledResolution.getScaledHeight() - Fonts.arial.getStringHeight("Developed by Vaziak & Criiptic") - 2, -1);
        Fonts.arial.drawStringWithShadow("Welcome back, §c" + Sensation.instance.username, 2, 2, -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE)
            return;

        if (keyCode == Keyboard.KEY_RETURN) {
            currentBackground++;

            if (currentBackground > 2)
                currentBackground = 1;
        }
        if (Sensation.instance.cheatManager != null) {
            if (!Sensation.instance.cheatManager.getCheatRegistry().isEmpty()) {
                Interface cheatInterface = (Interface) Sensation.instance.cheatManager.getCheatRegistry().get("Interface");
                if (keyCode == cheatInterface.getBind())
                    mc.displayGuiScreen(new Screen(this));
            }
        }


        super.keyTyped(typedChar, keyCode);
    }
}
