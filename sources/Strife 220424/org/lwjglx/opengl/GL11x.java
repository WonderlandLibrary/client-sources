package org.lwjglx.opengl;

import org.lwjgl.opengl.GL11;

public class GL11x {
	
	public static void glColorPointer(int size, boolean unsigned, int stride, java.nio.ByteBuffer pointer) {
		if (unsigned) {
			org.lwjgl.opengl.GL11.glColorPointer(size, GL11.GL_UNSIGNED_BYTE, stride, pointer);
		}
		else {
			org.lwjgl.opengl.GL11.glColorPointer(size, GL11.GL_BYTE, stride, pointer);
		}
	}
	
	public static void glColorPointer(int size, int stride, java.nio.FloatBuffer pointer) {
		org.lwjgl.opengl.GL11.glColorPointer(size, GL11.GL_FLOAT, stride, pointer);
	}
	
	public static void glGetFloat(int pname, java.nio.FloatBuffer params) {
		org.lwjgl.opengl.GL11.glGetFloatv(pname, params);
	}
	
	public static void glLoadMatrix(java.nio.FloatBuffer m) {
		org.lwjgl.opengl.GL11.glLoadMatrixf(m);
	}
	
	public static void glTexCoordPointer(int size, int stride, java.nio.FloatBuffer pointer) {
		org.lwjgl.opengl.GL11.glTexCoordPointer(size, GL11.GL_FLOAT, stride, pointer);
	}
	
	public static void glVertexPointer(int size, int stride, java.nio.FloatBuffer pointer) {
		org.lwjgl.opengl.GL11.glVertexPointer(size, GL11.GL_FLOAT, stride, pointer);
	}

}
