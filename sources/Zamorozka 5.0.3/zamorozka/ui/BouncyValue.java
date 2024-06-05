package zamorozka.ui;

public class BouncyValue {

	private float last;
	private float current;
	private float springLength;
	private final float bounceAmount;
	private final float bounceStrength;

	public BouncyValue(final float bounceAmount, final float bounceStrength) {
		this.last = 0.0f;
		this.current = 0.0f;
		this.springLength = 0.0f;
		this.bounceAmount = bounceAmount;
		this.bounceStrength = bounceStrength;
	}

	public void subtract(final float f) {
		this.last -= f;
		this.current -= f;
	}

	public void subtract(final double d) {
		this.subtract((float) d);
	}

	public void reset() {
		this.last = 0.0f;
		this.current = 0.0f;
		this.springLength = 0.0f;
	}

	public void update() {
		this.last = this.current;
		this.springLength *= this.bounceAmount;
		this.springLength -= this.current * this.bounceStrength;
		this.current += this.springLength;
	}

	public float get(final float f) {
		return this.last + (this.current - this.last) * f;
	}

}
