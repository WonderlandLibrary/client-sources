/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client;

import com.google.common.base.Charsets;
import java.nio.ByteBuffer;
import net.minecraft.util.text.TextProcessing;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.system.MemoryUtil;

public class ClipboardHelper {
    private final ByteBuffer buffer = BufferUtils.createByteBuffer(8192);

    public String getClipboardString(long l, GLFWErrorCallbackI gLFWErrorCallbackI) {
        GLFWErrorCallback gLFWErrorCallback = GLFW.glfwSetErrorCallback(gLFWErrorCallbackI);
        String string = GLFW.glfwGetClipboardString(l);
        string = string != null ? TextProcessing.func_238338_a_(string) : "";
        GLFWErrorCallback gLFWErrorCallback2 = GLFW.glfwSetErrorCallback(gLFWErrorCallback);
        if (gLFWErrorCallback2 != null) {
            gLFWErrorCallback2.free();
        }
        return string;
    }

    private static void copyToClipboard(long l, ByteBuffer byteBuffer, byte[] byArray) {
        byteBuffer.clear();
        byteBuffer.put(byArray);
        byteBuffer.put((byte)0);
        byteBuffer.flip();
        GLFW.glfwSetClipboardString(l, byteBuffer);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setClipboardString(long l, String string) {
        byte[] byArray = string.getBytes(Charsets.UTF_8);
        int n = byArray.length + 1;
        if (n < this.buffer.capacity()) {
            ClipboardHelper.copyToClipboard(l, this.buffer, byArray);
        } else {
            ByteBuffer byteBuffer = MemoryUtil.memAlloc(n);
            try {
                ClipboardHelper.copyToClipboard(l, byteBuffer, byArray);
            } finally {
                MemoryUtil.memFree(byteBuffer);
            }
        }
    }
}

