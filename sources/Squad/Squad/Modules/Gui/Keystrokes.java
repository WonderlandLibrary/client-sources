package Squad.Modules.Gui;

import java.awt.Color;
import java.util.ArrayList;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventRender2D;
import Squad.Utils.FontUtils;
import Squad.base.Module;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Keystrokes extends Module {

	public Keystrokes() {
		super("KeyStrokes", 0, 0, Category.Gui);
		// TODO Auto-generated constructor stub
	
	
	}
	 public static FontUtils fu1 = new FontUtils("Russian", 0, 45);
	  public static FontUtils fu2 = new FontUtils("Russian", 0, 42);
	  public static FontUtils fu3 = new FontUtils("Russian", 0, 30);
	  public static FontUtils f2u24 = new FontUtils("Thruster Regular", 0, 19);
	  public static FontUtils f2u25 = new FontUtils("Thruster Regular", 0, 47);
		@EventTarget
		public void onTick(EventRender2D ev){
			Gui.drawRect(605.0F, 320F, 625.0F, 340.0F, 0x99000000);
			Gui.drawRect(587.0F, 340.0F, 643.0F, 360.0F, 0x99000000);
			//Gui.drawRect(0.0F, 360F, 685.0F, 365.0F, 0xff7cfc00);
			fu3.drawStringWithShadow("W", 607.0F, 320.0F, Color.WHITE.getRGB());
			fu3.drawStringWithShadow("A", 590.0F, 340.0F, Color.WHITE.getRGB());
			fu3.drawStringWithShadow("S", 610.0F, 340.0F, Color.WHITE.getRGB());
			fu3.drawStringWithShadow("D", 630.0F, 340.0F, Color.WHITE.getRGB());
			
		    if (Minecraft.getMinecraft().gameSettings.keyBindForward.pressed) {
				fu3.drawStringWithShadow("W", 607.0F, 320.0F, Color.CYAN.getRGB());
			}
		    if (Minecraft.getMinecraft().gameSettings.keyBindLeft.pressed) {
		    	fu3.drawStringWithShadow("A", 590.0F, 340.0F, Color.CYAN.getRGB());
		    }
		    if (Minecraft.getMinecraft().gameSettings.keyBindBack.pressed) {
				fu3.drawStringWithShadow("S", 610.0F, 340.0F, Color.CYAN.getRGB());
		    }
		    if (Minecraft.getMinecraft().gameSettings.keyBindRight.pressed) {
		    	fu3.drawStringWithShadow("D", 630.0F, 340.0F, Color.CYAN.getRGB());
		    	
		    }
		
			    }
		}

		



