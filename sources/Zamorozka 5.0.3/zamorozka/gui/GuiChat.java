package zamorozka.gui;

import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import zamorozka.main.Zamorozka;

public class GuiChat extends GuiScreen{
	
	
	private GuiScreen parentScreen;
	  private GuiTextField usernameTextField;
	  
	  public GuiChat(GuiScreen guiscreen)
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

	  }
	  
	  protected void keyTyped(char c, int i)
	  {
	    if (c == '\r') {
	      actionPerformed((GuiButton)this.buttonList.get(0));
	    }
	  }
	  
	  protected void mouseClicked(int i, int j, int k)
	    throws IOException
	  {
	    super.mouseClicked(i, j, k);
	  }
	  
	  public void initGui()
	  {
	    Keyboard.enableRepeatEvents(true);
	    this.buttonList.clear();
	    this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "Done"));
	    this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, "Cancel"));
	    
	  }
	  public void drawScreen(int i, int j, float f)
	  {
	    drawDefaultBackground();

	    
	    
	    drawString(this.fontRendererObj, "",this.width / 2, this.height / 4 - 40 + 20, 16777215);
	    super.drawScreen(i, j, f);
	  }

}
