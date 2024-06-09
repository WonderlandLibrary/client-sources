package lunadevs.luna.module.render;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;

public class Nameprotect extends Module{

	public static boolean active;
	
	public static String newname = "§4xX§2Franklin§4Xx §r";
	
	public Nameprotect() {
		super("NameProtect", 0, Category.RENDER, false);
	}

@Override
public void onDisable() {
	active=false;
	super.onDisable();
}
	
	 @Override
	public void onEnable() {
		active=true;
		super.onEnable();
	}
	 
	@Override
	public String getValue() {
		return null;
	}

}
