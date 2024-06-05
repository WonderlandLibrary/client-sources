package zamorozka.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.Session;
import zamorozka.main.Zamorozka;

public class GuiSpammer extends GuiScreen{
	
	
	private GuiScreen parentScreen;
	  private GuiTextField usernameTextField;
	  
	  public GuiSpammer(GuiScreen guiscreen)
	  {
	    this.parentScreen = guiscreen;
	  }
	  
	  public void updateScreen()
	  {
	    this.usernameTextField.updateCursorCounter();
	  }
	  
	  public void onGuiClosed()
	  {
	    Keyboard.enableRepeatEvents(false);
	  }
	  
	  protected void actionPerformed(GuiButton guibutton)
	  {
	    if (!guibutton.enabled) {
	      return;
	    }
	    if (guibutton.id == 1) {
	      mc.displayGuiScreen(this.parentScreen);
	    }
	    if (guibutton.id == 0) {
	    	for(int i=0; i< 10; i++){
	    		mc.player.connection.sendPacket(new CPacketChatMessage(usernameTextField.getText()));
	    	}
	    	mc.displayGuiScreen(this.parentScreen);
	    }
	    if (guibutton.id == 7) {
	    	 mc.displayGuiScreen(new GuiName(parentScreen));
		    }
	    if (guibutton.id == 6) {
	    	 mc.displayGuiScreen(new GuiPremN(parentScreen));
		    }

	  }
	  
	  protected void keyTyped(char c, int i)
	  {
	    this.usernameTextField.textboxKeyTyped(c, i);
	    if ((c == '\t') && 
	      (this.usernameTextField.isFocused())) {
	      this.usernameTextField.setFocused(false);
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
	  }
	  
	  public void initGui()
	  {
	    Keyboard.enableRepeatEvents(true);
	    this.buttonList.clear();
	    this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "Done"));
	    this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, "Cancel"));
	    this.usernameTextField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 116, 200, 20);
	  }
	  public void drawScreen(int i, int j, float f)
	  {
	    drawDefaultBackground();
	    drawString(this.fontRendererObj, "Message:", this.width / 2 - 100, 104, 10526880);
	    drawString(this.fontRendererObj, "",this.width / 2, this.height / 4 - 40 + 20, 16777215);
	    this.usernameTextField.drawTextBox();
	    super.drawScreen(i, j, f);
	  }

}
