package club.bluezenith.module.modules.fun;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;

@SuppressWarnings({"unused", "UnstableApiUsage"})
public class Derp extends Module {
	public Derp(){
		super("Derp", ModuleCategory.FUN);
	}
	private float yaw = 0;
	private float pitch = 0;
	private boolean lol = false;
	@Listener
	public void niggasex(UpdatePlayerEvent e){
		e.yaw = yaw;
		e.pitch = pitch;
		yaw += 50;
		yaw %= 360;
		yaw *= 1.05;
		/*if(pitch <= 90 && !lol){
			pitch += 10;
			if(pitch >= 90) lol = true;
		}else if(pitch >= 0 && lol){
			pitch -= 10;
			if(pitch <= 0) lol = false;
		}*/
		pitch = 90;
		//pitch = 0.00253523f;
	}
}
