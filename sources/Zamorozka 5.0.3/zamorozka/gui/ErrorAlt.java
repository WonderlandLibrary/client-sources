package zamorozka.gui;

import java.awt.Color;
import java.io.IOException;
import java.net.Proxy;

import org.lwjgl.input.Keyboard;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import zamorozka.main.Zamorozka;

public class ErrorAlt extends GuiScreen{
	
	private GuiScreen parentScreen;
	private GuiTextField usernameTextField;
	private GuiTextField passwordTextField;
	  public ErrorAlt(GuiScreen guiscreen)
	  {
	    this.parentScreen = guiscreen;
	  }
	  public void onGuiClosed()
	  {
	    Keyboard.enableRepeatEvents(false);
	  }

	@Override

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawGradientRect(0, 0, width, height, 0xFFFF6147, 0xFFE61717);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}


	

}
