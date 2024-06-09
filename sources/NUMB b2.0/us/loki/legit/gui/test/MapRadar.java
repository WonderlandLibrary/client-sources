package us.loki.legit.gui.test;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;

public class MapRadar {
	
	private int posX;
	private int posY;
	private Minecraft mc;
	
	public void radarRotate(){
		GL11.glPushMatrix();
		GL11.glTranslatef(posX, posY, 0);
    	GL11.glRotatef(-mc.thePlayer.rotationYaw, 0, 0, 1);
    	
	}
	public void drawRadar(){
		
	}
}
