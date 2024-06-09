package Squad.Events;

import com.darkmagician6.eventapi.events.Event;

public class EventMove implements Event {
	public double x;
	public double y;
	public double z;

	public EventMove(final double x, final double y, final double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public boolean isMoving() {
		return this.x != 0.0 && this.y != 0.0 && this.z != 0.0;
	}

	public double getX() {
		return this.x;
	}

	public void setX(final double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	public void setY(final double y) {
		this.y = y;
	}

	public double getZ() {
		return this.z;
	}

	public void setZ(final double z) {
		this.z = z;
	}
}
