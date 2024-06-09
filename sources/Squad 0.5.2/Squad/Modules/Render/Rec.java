package Squad.Modules.Render;

import java.awt.Color;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventRender2D;
import Squad.Utils.FontUtils;
import Squad.base.Module;

public class Rec extends Module{

	public Rec() {
		super("Kamara", 0, 0, Category.Render);
		// TODO Auto-generated constructor stub
	} public static FontUtils fu1 = new FontUtils("Russian", 0, 45);
	  public static FontUtils fu2 = new FontUtils("Russian", 0, 42);
	  public static FontUtils fu3 = new FontUtils("Russian", 0, 47);
	  public static FontUtils f2u24 = new FontUtils("Thruster Regular", 0, 19);
	  public static FontUtils f2u25 = new FontUtils("Thruster Regular", 0, 47);


@EventTarget
public void onTick(EventRender2D ev){
	fu3.drawStringWithShadow("Rec...", 5.0F, 330.0F, Color.RED.getRGB());	
}
}