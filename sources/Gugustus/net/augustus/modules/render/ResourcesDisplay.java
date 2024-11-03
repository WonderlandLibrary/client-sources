package net.augustus.modules.render;

import java.awt.Color;

import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.DoubleValue;

public class ResourcesDisplay extends Module{
	
   public DoubleValue x = new DoubleValue(332678634, "X", this, 0, 0, 2000, 0);
   public DoubleValue y = new DoubleValue(2390654, "Y", this, 0, 0, 2000, 0);
	
	public ResourcesDisplay() {
		super("ResourcesDisplay", Color.green, Categorys.RENDER);
	}

}
