package net.augustus.utils.HackerDetector;

import net.minecraft.entity.player.EntityPlayer;

public class Check {
	
	public boolean failed = false;
	public String name;
	public boolean enabled;
	
	public Check(String name, boolean enabled) {
		this.name = name;
		this.enabled = enabled;
	}
	
	public boolean runCheck(EntityPlayer player) {
		System.out.println(name + " not " + name + "'ing. Did you not put @Override?");
		return false;
	}
	
	public boolean failed() {
		return this.failed;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled; 
	}

	public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}	
	
	
	
}
