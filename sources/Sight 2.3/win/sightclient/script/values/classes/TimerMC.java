package win.sightclient.script.values.classes;

import net.minecraft.client.Minecraft;
import win.sightclient.script.values.ClassMC;

public class TimerMC extends ClassMC {

	public float timerSpeed;
	
	@Override
	public void preRun() {
		timerSpeed = Minecraft.getMinecraft().timer.timerSpeed;
	}
	
	@Override
	public void postRun() {
		Minecraft.getMinecraft().timer.timerSpeed = timerSpeed;
	}
}
