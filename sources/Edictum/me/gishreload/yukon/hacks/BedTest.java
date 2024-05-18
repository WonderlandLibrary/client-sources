package me.gishreload.yukon.hacks;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.module.Module;

public class BedTest extends Module {

	public BedTest() {
		super("ExploitBed", 0, Category.OTHER);
		// TODO Auto-generated constructor stub
	}

		public void onEnable() {
				//mc.thePlayer.isDead = true;
				mc.thePlayer.ignoreItemEntityData();
				
			}
		public void onDisable() {
			mc.thePlayer.isDead = false;
	
		}
}
