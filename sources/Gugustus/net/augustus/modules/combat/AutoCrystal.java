package net.augustus.modules.combat;

import java.awt.Color;

import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;

public class AutoCrystal extends Module{

	public BooleanValue AutoPlace;
	
	public BooleanValue AutoBreak;
	
	public AutoCrystal() {
		super("AutoCrystal", Color.red, Categorys.COMBAT);
	}

}
