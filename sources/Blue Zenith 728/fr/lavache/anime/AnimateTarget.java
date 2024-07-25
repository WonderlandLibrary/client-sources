package fr.lavache.anime;

import club.bluezenith.util.render.RenderUtil;

public class AnimateTarget extends Animate {
	private float value, min, max, speed, time, target;
	private boolean reversed;
	private Easing ease;

	public AnimateTarget() {
		this.ease = Easing.LINEAR;
		this.value = 0;
		this.min = 0;
		this.max = 1;
		this.speed = 50;
		this.reversed = false;
	}

	public void reset() { time = min; }

	public AnimateTarget update() {
		double sped = (speed * 0.01f * RenderUtil.delta);
		if (time < target) time += sped;
		if (time > target) time -= sped;
		this.value = clamp(getEase().ease(time, min, max, max), min, max);
		return this;
	}

	public AnimateTarget setTarget(float target){
		this.target = target;
		return this;
	}

	public AnimateTarget setValue(float value) {
		this.value = value;
		return this;
	}
	public AnimateTarget setMin(float min) {
		this.min = min;
		return this;
	}

	public AnimateTarget setMax(float max) {
		this.max = max;
		return this;
	}

	public AnimateTarget setSpeed(float speed) {
		this.speed = speed;
		return this;
	}

	public AnimateTarget setReversed(boolean reversed) {
		this.reversed = reversed;
		return this;
	}

	public AnimateTarget setEase(Easing ease) {
		this.ease = ease;
		return this;
	}

	public boolean hasReachedMax(){
		return getValue() >= getMax();
	}
	public boolean hasReachedMin(){
		return getValue() <= getMin();
	}

	public float getValue() { return value; }
	public float getMin() { return min; }
	public float getMax() { return max; }
	public float getSpeed() { return speed; }
	public boolean isReversed() { return reversed; }
	public Easing getEase() { return ease; }

	private float clamp(float num, float min, float max) { return num < min ? min : (num > max ? max : num); }
}
