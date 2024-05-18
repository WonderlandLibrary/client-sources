package Squad.info;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import Squad.Modules.Other.GuiHUD;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

public class ModuleInfo {
	
	public static String Title;
	public static String Message;
	public static int fading = 0;
	public static boolean buildUp = false;
	public static boolean onEnable = false;
	public static boolean onDisable = false;
	public static boolean onModuleInfo = true;
	
	public static void pushMessage(String title, String message)
	{
		ModuleInfo.Title = title;
		ModuleInfo.Message = message;
		ModuleInfo.buildUp = true;

	}
	
	public static void render()
	{
		if(ModuleInfo.Title != null && ModuleInfo.Message != null)
		{
			//Gui.drawRect(left, top, right, bottom, color);
			

			drawRectum(fading > 105 ? GuiScreen.width - 105 : GuiScreen.width - fading, GuiScreen.height - 140, GuiScreen.width, GuiScreen.height - 100, Color.BLACK.getRGB());
			drawRectum(fading > 105 ? GuiScreen.width - 105 : GuiScreen.width - fading, GuiScreen.height - 140, 
					fading > 105 ? GuiScreen.width - 105 + 2 : GuiScreen.width - fading + 2, GuiScreen.height - 100, 0xffffffff);
			GuiHUD.f2u24.drawString(ModuleInfo.Title, 
					fading > 105 ? GuiScreen.width - 105 + 10 : GuiScreen.width - fading + 10,GuiScreen.height - 135,  -274787843);
			GuiHUD.f2u24.drawString(ModuleInfo.Message, 
					fading > 105  ? GuiScreen.width - 105 + 10 : GuiScreen.width - fading + 10,GuiScreen.height - 120,  Color.WHITE.getRGB());

		}
	}
	  public static void drawRectum(float g, float h, float i, float j, int col1)
	   {
	     float f = (col1 >> 24 & 0xFF) / 255.0F;
	     float f1 = (col1 >> 16 & 0xFF) / 255.0F;
	     float f2 = (col1 >> 8 & 0xFF) / 255.0F;
	     float f3 = (col1 & 0xFF) / 255.0F;
	     
	     GL11.glEnable(3042);
	     GL11.glDisable(3553);
	     GL11.glBlendFunc(770, 771);
	     GL11.glEnable(2848);
	     
	     GL11.glPushMatrix();
	     GL11.glColor4f(f1, f2, f3, f);
	     GL11.glBegin(7);
	     GL11.glVertex2d(i, h);
	     GL11.glVertex2d(g, h);
	     GL11.glVertex2d(g, j);
	     GL11.glVertex2d(i, j);
	     GL11.glEnd();
	     GL11.glPopMatrix();
	     
	     GL11.glEnable(3553);
	     GL11.glDisable(3042);
	     GL11.glDisable(2848);
	   }

}