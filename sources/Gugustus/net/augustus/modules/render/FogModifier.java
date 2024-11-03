package net.augustus.modules.render;

import java.awt.Color;

import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.DoubleValue;

public class FogModifier extends Module{

   public DoubleValue fogR;
   public DoubleValue fogG;
   public DoubleValue fogB;
	
	public FogModifier() {
		super("FogModifier", Color.blue, Categorys.RENDER);
		fogR = new DoubleValue(693636969, "Fog Red", this, 1.0, 0.0, 255.0, 1);
		fogG = new DoubleValue(69645769, "Fog Green", this, 1.0, 0.0, 255.0, 1);
		fogB = new DoubleValue(12367969, "Fog Blue", this, 1.0, 0.0, 255.0, 1);
	}
	
}
