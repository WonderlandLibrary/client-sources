package net.silentclient.client.gui.animation;

public final class SimpleAnimation {

    private float value;
    private long lastMS;

    public SimpleAnimation(float value){
    	this.value = value;
        this.lastMS = System.currentTimeMillis();
    }

    public void setAnimation(float value, double speed){
        long currentMS = System.currentTimeMillis();
        long delta = currentMS - this.lastMS;
        this.lastMS = currentMS;

        double deltaValue = 0D;

        if(speed > 28) {
        	speed = 28;
        }

        if (speed != 0D) {
        	deltaValue = Math.abs(value - this.value) * 0.35F / (10D / speed);
        }

        this.value = calculateCompensation(value, this.value, deltaValue, delta);
    }

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

    private float calculateCompensation(final float target, float current, final double speed, long delta) {
        float diff = current - target;

        double add =  (delta * (speed / 50));

        if (diff > speed){
        	if(current - add > target) {
                current -= add;
        	}else {
                current = target;
        	}
        }
        else if (diff < -speed) {
        	if(current + add < target) {
                current += add;
        	}else {
                current = target;
        	}
        }
        else{
            current = target;
        }

        return current;
    }
}
