package me.valk.utils.render;

import org.lwjgl.opengl.Display;

import me.valk.utils.Wrapper;
import net.minecraft.client.gui.ScaledResolution;

public class ScreenUtil {

	public static int getWidth(){
		return Display.getWidth() / getScaleFactor();
	}
	
	public static int getHeight(){
		return Display.getHeight() / getScaleFactor();
	}
	
	public static int getScaleFactor(){
		ScaledResolution sr = new ScaledResolution(Wrapper.getMinecraft(), Wrapper.getMinecraft().displayWidth, Wrapper.getMinecraft().displayHeight);
		return sr.getScaleFactor();
	}
	
}
