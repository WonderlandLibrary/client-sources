package zamorozka.gui;

import java.io.IOException;


import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;



public class GuiAlt extends GuiScreen{
	
	  private GuiScreen parentScreen;
	  private float mousePosx;
	  private float mousePosY;
	 
	  
	  public GuiAlt(GuiScreen guiscreen)
	  {
	    this.parentScreen = guiscreen;
	  }
	  
	  public void onGuiClosed()
	  {
	    Keyboard.enableRepeatEvents(false);
	  }
	  @Override
	   public void updateScreen()
	   { 
		
	   }
	  protected void actionPerformed(GuiButton guibutton)
	  {
	    if (!guibutton.enabled) {
	      return;
	    }
	    if (guibutton.id == 0)
	    {
	      this.mc.displayGuiScreen(this.parentScreen);
	    }
	    else if (guibutton.id == 1)
	    {
	      this.mc.displayGuiScreen(new GuiOfflineName(this.parentScreen));
	    }
	    else if (guibutton.id == 2)
	    {
	      this.mc.displayGuiScreen(new GuiPremN(this.parentScreen));
	    }
	    else if (guibutton.id == 3)
	    {
	      this.mc.displayGuiScreen(new GuiName(this.parentScreen));
	    }

	    }
	  
	  
	  protected void mouseClicked(int i, int j, int k)
	    throws IOException
	  {
	    super.mouseClicked(i, j, k);
	  }
	  @Override
	  public void initGui()
	  {
	    Keyboard.enableRepeatEvents(true);
	  
	    this.buttonList.clear();
	    this.buttonList.add(new GuiButton(1, this.width / - 200 + 15, 10, 130, 20 * 1, "Crack name"));
	    this.buttonList.add(new GuiButton(2, this.width /  - 200 + 15, 67, 130, 20 * 1, "User/Pass"));
	    this.buttonList.add(new GuiButton(3, this.width / - 200 + 15, 35, 130, 20 * 1, "Email:Password"));
      this.buttonList.add(new GuiButton(0, this.width / - 200 + 15, 324, 130, 20 * 1, "Cancel"));
	  }

	  @Override
	  public void drawScreen(int i, int j, float f)
	  {
		  
		
	      drawDefaultBackground();
	  
	      drawRect(0, 1000, 150, 0, Integer.MIN_VALUE);
	      GL11.glPushMatrix();
	      GL11.glScalef(5.0F, 5.0F, 1.0F);
	      GL11.glPopMatrix();;
	    super.drawScreen(i, j, f);
	  }
	
	

}
