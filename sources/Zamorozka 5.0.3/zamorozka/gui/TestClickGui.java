package zamorozka.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Comparator;
import java.util.Locale;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.HUD.ArreyList;
import zamorozka.ui.Colors;
import zamorozka.ui.RenderingUtils;

public class TestClickGui extends GuiScreen{
	
	  public void initGui()
	  {
	    Keyboard.enableRepeatEvents(true);
	    this.buttonList.clear();
	    final int[] yDist = {1};
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Font[] allFonts = ge.getAllFonts();
	    for (Font font : allFonts) {
		    
					buttonList.add(new GuiButton(font.hashCode(), 1, yDist[0], font.getName()));
					yDist[0] += 20;
					int dw = Mouse.getEventDWheel();
				    if(dw != 0) {
				        if (dw > 0) {
				            dw = -2;
				        } else {
				            dw = 1;
				        }
				        float amountScrolled = (float) (dw *0.2);
				        yDist[0] -= amountScrolled;
			}
	    }
	  }
	  protected void actionPerformed(GuiButton guibutton)
	  {
		  try {
			    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			    Font[] allFonts = ge.getAllFonts();
			    for (Font font : allFonts) {
			    	
			    if (guibutton.id == font.hashCode())
			    {
			    	
			}
			    }
		  }catch (Exception e) {}
	  }
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		 float[] shadow = new float[]{0.125F, 0.125F, 0.125F};
			try {
		        int[] counter3 = {1};
				final FontRenderer fontRenderer = mc.fontRendererObj;
			        final int[] yDist = {1};
			        if (ModuleManager.getModule(ArreyList.class).getState()){
			    		String mode = Zamorozka.instance.settingsManager.getSettingByName("Array Mode").getValString();
   			        ModuleManager.getModules().stream().filter(Module :: getState).sorted(Comparator.comparingInt(module -> -Zamorozka.theClient.FONT_MANAGER.chat.getStringWidth(module.getDisplayName()))).forEach(module -> {
   			        	if(module.getDisplayName() != "ClickGUI" && module.getDisplayName() != "SaveModule"){
   			                int color = -1;
   			                if(Zamorozka.settingsManager.getSettingByName("ArrayBackground").getValBoolean() && ModuleManager.getModule(ArreyList.class).getState()) {
   			        		RenderingUtils.drawRect(width -4- Zamorozka.theClient.FONT_MANAGER.chat.getStringWidth(module.getDisplayName()), yDist[0], width+ Zamorozka.theClient.FONT_MANAGER.chat.getStringWidth(module.getDisplayName()), yDist[0]+8.9, Colors.getColor(20, 255));
   			                }
   			                if(Zamorozka.settingsManager.getSettingByName("ArrayRect").getValBoolean() && ModuleManager.getModule(ArreyList.class).getState()) {
   			        		RenderingUtils.drawRect(width -1.5, yDist[0]-1, width+ Zamorozka.theClient.FONT_MANAGER.chat.getStringWidth(module.getDisplayName()), yDist[0]+10, color);
   			                }
   			                char mno= module.getDisplayName().charAt(0);
   			        		String modn = module.getDisplayName().substring(1);
   			        		Zamorozka.theClient.FONT_MANAGER.chat.drawStringWithShadow(String.valueOf(mno)+modn, width -3.7- Zamorozka.theClient.FONT_MANAGER.chat.getStringWidth(module.getDisplayName()), yDist[0]+1, color);
   			        		
   			        		yDist[0] += fontRenderer.FONT_HEIGHT;
   			        
   			        	
   			        	}
   			        });	
			        }
				
			} catch (Exception ex) {}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

}
