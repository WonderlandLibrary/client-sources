package org.lwjglx.opengl;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;

public class GL15x {
	
	public static ByteBuffer glGetBufferPointer(int target, int pname) {
		int size = GL15.glGetBufferParameteri(target, GL15.GL_BUFFER_SIZE);
		long res = GL15.glGetBufferPointer(target, pname);
		return MemoryUtil.memByteBuffer(res, size);
	}

}
