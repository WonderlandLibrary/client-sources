package org.lwjglx.opengl;


public class GLContext {
	
	private static ContextCapabilities contextCapabilities = new ContextCapabilities();
	
	public static GLContext createFromCurrent() {
		return new GLContext();
	}
	
	public static ContextCapabilities getCapabilities() {
		return contextCapabilities;
	}
}
