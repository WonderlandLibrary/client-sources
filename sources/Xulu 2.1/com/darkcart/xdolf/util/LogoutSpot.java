package com.darkcart.xdolf.util;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.client.renderer.entity.RenderManager;

public class LogoutSpot {
	public static ArrayList<LogoutSpot> logoutSpots = new ArrayList<LogoutSpot>();
	
	private String name;
	private double posX;
	private double posY;
	private double posZ;
	public double dX;
	public double dY;
	public double dZ;
	
	public float red, green, blue;
	
	public LogoutSpot(String name, double x, double y, double z, double renderX, double renderY, double renderZ)
	{
		this.name = name;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.red = (new Random().nextInt(255)) / 255F;
		this.green = (new Random().nextInt(255)) / 255F;
		this.blue = (new Random().nextInt(255)) / 255F;
		System.out.println(red + " " + green + " " + blue);
		update();
		logoutSpots.add(this);
	}
	
	public void update() 
	{
		dX = posX - RenderManager.renderPosX;
		dY = posY - RenderManager.renderPosY;
		dZ = posZ - RenderManager.renderPosZ;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public double getX() 
	{
		return posX;
	}
	
	public double getY() 
	{
		return posY;
	}
	
	public double getZ() 
	{
		return posZ;
	}
}
