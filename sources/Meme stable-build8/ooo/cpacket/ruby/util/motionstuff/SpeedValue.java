package ooo.cpacket.ruby.util.motionstuff;

import java.io.Serializable;

import javax.vecmath.Tuple2d;
import javax.vecmath.Tuple2f;
import javax.vecmath.Vector2d;

public class SpeedValue {
	private float baseSpeed;
	private float origin;
	private float slowdown;
	
	public SpeedValue(float baseSpeed, float slowdown) {
		this.baseSpeed = baseSpeed;
		this.origin = baseSpeed;
		this.slowdown = slowdown;
	}
	
	public float getSpeedWithSlowdown() {
		return baseSpeed -= slowdown;
	}
	
	public void reset() {
		this.baseSpeed = origin;
	}
	
	
}
