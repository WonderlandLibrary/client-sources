package win.sightclient.utils;

public class Vec2 {

	private double x, y;
	
	public Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public Vec2 offset(double x, double y) {
		return new Vec2(this.x + x, this.y + y);
	}
	
	public Vec2 offset(Vec2 vec) {
		return new Vec2(this.x + vec.x, this.y + vec.y);
	}
	
	public Vec2 opposite() {
		return new Vec2(-this.x, -this.y);
	}
} 
