package net.augustus.modules.render;

import java.awt.Color;

import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.ColorSetting;

public class OldArmor extends Module{

	public BooleanValue rainbow;
	public ColorSetting color;
	public BooleanValue damageColor;
	public ColorSetting hitColor;
	
	
	public OldArmor() {
		super("CustomArmor", Color.cyan, Categorys.RENDER);
		rainbow = new BooleanValue(0, "Rainbow", this, false);
		color = new ColorSetting(2, "Color", this, new Color(255, 255, 255, 255));
		damageColor = new BooleanValue(3, "DamageColor", this, false);
		hitColor = new ColorSetting(4, "Color", this, new Color(255, 0, 0, 255));
	}

}
