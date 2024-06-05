package zamorozka.gui;

import java.awt.Color;
import java.io.IOException;
import java.net.Proxy;

import org.lwjgl.input.Keyboard;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import zamorozka.main.Zamorozka;

public class Guiprefix extends GuiScreen{
	
	private GuiScreen parentScreen;
	private GuiTextField usernameTextField;
	private GuiTextField passwordTextField;
	  public Guiprefix(GuiScreen guiscreen)
	  {
	    this.parentScreen = guiscreen;
	  }
	  public void updateScreen()
	  {
	    this.usernameTextField.updateCursorCounter();
	    this.passwordTextField.updateCursorCounter();
	  }
	  public void onGuiClosed()
	  {
	    Keyboard.enableRepeatEvents(false);
	  }
	  

	  public void initGui()
	  {
	    Keyboard.enableRepeatEvents(true);
	    this.buttonList.clear();
		buttonList.add(new GuiButton(4, this.width /2 - 150 / 2, 150+10*4, "Login"));
		buttonList.add(new GuiButton(5, this.width /2 - 150 / 2, 150+20*4, "Back"));
	    this.usernameTextField = new GuiTextField(2, this.fontRendererObj, this.width/2 - 150 / 2, 100, 200, 20);
	    this.passwordTextField = new GuiTextField(2, this.fontRendererObj, this.width/2 - 150 / 2, 132, 200, 20);
	  }
	  protected void keyTyped(char c, int i)
	  {
	    this.usernameTextField.textboxKeyTyped(c, i);
	    this.passwordTextField.textboxKeyTyped(c, i);
	    if ((c == '\t') && 
	      (this.usernameTextField.isFocused() && this.passwordTextField.isFocused())) {
	      this.usernameTextField.setFocused(false);
	      this.passwordTextField.setFocused(false);
	    }
	    if (c == '\r') {
	      actionPerformed((GuiButton)this.buttonList.get(0));
	    }
	  }
	  
	  protected void mouseClicked(int i, int j, int k)
			    throws IOException
			  {
			    super.mouseClicked(i, j, k);
			    this.usernameTextField.mouseClicked(i, j, k);
			    this.passwordTextField.mouseClicked(i, j, k);
			  }
	  
	 protected void actionPerformed(GuiButton guibutton)
	  {
		if(guibutton.id == 4){
			if(usernameTextField.getText() != null && !usernameTextField.getText().isEmpty() && passwordTextField.getText() != null && !passwordTextField.getText().isEmpty()){
				if(!usernameTextField.getText().contains(" ") && !passwordTextField.getText().contains(" ")){
					
					try{
							try{

							}catch(Exception e){
								
							}
					}catch(Exception e){
						System.out.print("Erroe alt");
					}
				}else{
					System.out.print("Error alt");
				}
			}else{
				System.out.print("No Alt");
			}
		}
		if(guibutton.id == 5){
			mc.displayGuiScreen(null);
		}
		this.mc.displayGuiScreen(this.parentScreen);
	}
	@Override

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		
		Zamorozka.theClient.FONT_MANAGER.mainMenu.drawString("PREFIX HACK CLIENT", this.width/2 - 150 / 2, 50, 0xFFFAFA);
		drawString(mc.fontRendererObj, "Nickname:", this.width/2 - 150 / 2, 90, Color.white.getRGB());
		drawString(mc.fontRendererObj, "Password:", this.width/2 - 150 / 2, 122, Color.white.getRGB());
		this.usernameTextField.drawTextBox();
		this.passwordTextField.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}


	

}
