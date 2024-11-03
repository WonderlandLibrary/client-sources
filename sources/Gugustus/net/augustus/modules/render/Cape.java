package net.augustus.modules.render;

import java.awt.Color;

import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;

public class Cape extends Module{

   public StringValue cape = new StringValue(4, "Cape", this, "Mountain", new String[]{"Mountain", "Astelic", "Rainbow", "Purple", "Pickaxe", "Astelic Sky", "Buildings", "Totem", "Enchantment Table", "HardCore", "Wave", "Vape V4", "rgb", "Rainbow Virus"});
   public DoubleValue speed = new DoubleValue(696969, "AnimationSpeed", this, 1.0, 0.0, 100.0, 1);
	
	public Cape() {
		super("Cape", Color.gray, Categorys.RENDER);
	}

}
