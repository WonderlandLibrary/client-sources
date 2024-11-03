package net.augustus.modules.render;

import java.awt.Color;

import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;

public class HurtCam extends Module{
	
	public BooleanValue remove;
	
	public HurtCam() {
		super("HurtCam", Color.cyan, Categorys.RENDER);
		remove = new BooleanValue(0, "Remove", this, false);
	}
	
}
