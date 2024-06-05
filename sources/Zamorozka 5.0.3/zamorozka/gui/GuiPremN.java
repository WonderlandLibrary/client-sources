package zamorozka.gui;

import java.awt.Color;
import java.io.IOException;
import java.net.Proxy;

import javax.swing.JOptionPane;

import org.lwjgl.input.Keyboard;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import zamorozka.main.Zamorozka;

public class GuiPremN extends GuiScreen{
	
	private GuiScreen parentScreen;
	private GuiTextField usernameTextField;
	private GuiTextField passwordTextField;
	  public GuiPremN(GuiScreen guiscreen)
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
							final YggdrasilUserAuthentication aut = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
							aut.setUsername(usernameTextField.getText());
							aut.setPassword(passwordTextField.getText());
							try{
								aut.logIn();
								
								this.mc.session = new Session(aut.getSelectedProfile().getName(), aut.getSelectedProfile().getId().toString(), aut.getAuthenticatedToken(), "mojang");
		
								
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
	     ScaledResolution s1 = new ScaledResolution(this.mc);
	     this.mc.getTextureManager().bindTexture(new ResourceLocation("altfon1.jpg"));
	     Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, s1.getScaledWidth(), s1.getScaledHeight(), s1.getScaledWidth(), s1.getScaledHeight());
	     drawGradientRect(0, 0, width, height, 0x02044A0, 0x570044A0);
	     drawGradientRect(0, 0, width, height, 0x02044A0, 0x570044A0);
	     drawGradientRect(0, 0, width, height, 0x02044A0, 0x570044A0);
	     drawGradientRect(0, 0, width, height, 0x02044A0, 0x570044A0);


		drawString(mc.fontRendererObj, "Email:", this.width/2 - 150 / 2, 90, Color.white.getRGB());
		drawString(mc.fontRendererObj, "Password:", this.width/2 - 150 / 2, 122, Color.white.getRGB());
		this.usernameTextField.drawTextBox();
		this.passwordTextField.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}


	

}
