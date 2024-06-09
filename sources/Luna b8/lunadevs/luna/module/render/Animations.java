package lunadevs.luna.module.render;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;

public class Animations extends Module{

	public static boolean active;
	
	public Animations() {
		super("Animations", 0, Category.RENDER, false);
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
