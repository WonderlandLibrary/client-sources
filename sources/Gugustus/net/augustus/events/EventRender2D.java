package net.augustus.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// @AllArgsConstructor
public class EventRender2D extends Event{
    private int scaledX;
    private int scaledY;
	public EventRender2D(int scaledX, int scaledY) {
		super();
		this.scaledX = scaledX;
		this.scaledY = scaledY;
	}
	public int getScaledX() {
		return scaledX;
	}
	public void setScaledX(int scaledX) {
		this.scaledX = scaledX;
	}
	public int getScaledY() {
		return scaledY;
	}
	public void setScaledY(int scaledY) {
		this.scaledY = scaledY;
	}
    
}
