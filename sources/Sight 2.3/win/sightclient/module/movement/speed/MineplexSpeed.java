package win.sightclient.module.movement.speed;

import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.event.events.player.EventMove;
import win.sightclient.module.Module;
import win.sightclient.module.ModuleMode;
import win.sightclient.module.combat.TargetStrafe;
import win.sightclient.utils.minecraft.MoveUtils;

public class MineplexSpeed extends ModuleMode{

	private double moveSpeed;
	private int delay2;
	
	public MineplexSpeed(Module parent) {
		super(parent);
	}

	private TargetStrafe targetStrafe;
	
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventMove) {
			EventMove em = (EventMove)e;
			if (this.targetStrafe == null) {
				this.targetStrafe = (TargetStrafe) Sight.instance.mm.getModuleByName("TargetStrafe");
			}
            double speed = 0;
            if (mc.thePlayer.isCollidedHorizontally) {
                this.moveSpeed = -2;
            }
            if (mc.thePlayer.onGround) {
                delay2 = 0;
                mc.thePlayer.motionY = 0.42;
                if (this.moveSpeed < 0)
                    this.moveSpeed++;
                if (mc.thePlayer.posY != (int) mc.thePlayer.posY) {
                    this.moveSpeed = -1;
                }
                mc.timer.timerSpeed = 1.001f;
            } else {
                if (mc.timer.timerSpeed == 2.001f)
                    mc.timer.timerSpeed = 1.05f;
                mc.thePlayer.motionY += 0.006;
                speed = (0.43095) - delay2 / 300 + this.moveSpeed / 5;
                delay2++;
            }
            if (this.targetStrafe.canStrafe()) {
            	this.targetStrafe.strafe(em, speed);
            } else {
                MoveUtils.setMotion(em, speed);
            }
		}
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		this.moveSpeed = -2;
	}
}
