package none.event.events;

import none.event.Event;

public class EventPreMotionUpdate extends Event {
    private boolean isPre;
    
    private float yaw, pitch;
    public double x, y, z;
    private boolean sneaking, sprinting, onGround;
    private boolean rotate = false;

    public static float YAW, PITCH, PREVYAW, PREVPITCH;
    
    public void fire(float yaw, float pitch, double x, double y, double z, boolean sneaking, boolean sprinting, boolean onGround) {
        this.isPre = true;
        this.yaw = yaw;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z;
        this.sneaking = sneaking;
        this.sprinting = sprinting;
        this.onGround = onGround;
        this.rotate = true;
        super.fire();
    }
    
    public void fire() {
    	PREVYAW = YAW;
    	PREVPITCH = PITCH;
    	YAW = this.yaw;
    	PITCH = this.pitch;
    	this.rotate = false;
        this.isPre = false;
        super.fire();
    }
    
    public boolean isPre() {
        return isPre;
    }

    public boolean isPost() {
        return !isPre;
    }

    public float getYaw() {
        return yaw;
    }
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }
    public void setPitch(float pitch) {
    	if (pitch >= 90) {
    		this.pitch = 90;
    	}else if (pitch <= -90) {
    		this.pitch = -90;
    	}else {
    		this.pitch = pitch;
    	}
    }

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	public boolean isSneaking() {
		return sneaking;
	}
	
	public void setSneaking(boolean sneaking) {
		this.sneaking = sneaking;
	}

	public boolean isSprinting() {
		return sprinting;
	}

	public void setSprinting(boolean sprinting) {
		this.sprinting = sprinting;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public boolean isRotate() {
		return rotate;
	}

	public void setRotate(boolean rotate) {
		this.rotate = rotate;
	}
}
