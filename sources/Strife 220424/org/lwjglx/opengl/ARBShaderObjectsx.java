package org.lwjglx.opengl;

public class ARBShaderObjectsx {
	
	public static void glShaderSourceARB(int shader, java.nio.ByteBuffer string) {
		byte[] b = new byte[string.remaining()];
		string.get(b);
		org.lwjgl.opengl.ARBShaderObjects.glShaderSourceARB(shader, new String(b));
	}

}
