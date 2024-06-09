package me.protocol_client.module.modUtils;

import java.util.ArrayList;

public class Waypoint {
	public String name;
	public int x;
	public int y;
	public int z;
	public int r;
	public int g;
	public int b;
	public static ArrayList<Waypoint> point = new ArrayList<Waypoint>();
	public Waypoint(String name, int x, int y, int z, int r, int g, int b){
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	public static void addWaypoint(String name, int x, int y, int z, int r, int g, int b){
		point.add(new Waypoint(name, x, y, z, r, g, b));
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getZ(){
		return z;
	}
	public int getR(){
		return r;
	}
	public int getG(){
		return g;
	}
	public int getB(){
		return b;
	}
	public static ArrayList<Waypoint> getWaypoints(){
		return point;
	}
	public static Waypoint getWaypointbyName(String name){
		for(Waypoint point : getWaypoints()){
			if(point.getName() == name){
				return point;
			}
		}
		return null;
	}
}
