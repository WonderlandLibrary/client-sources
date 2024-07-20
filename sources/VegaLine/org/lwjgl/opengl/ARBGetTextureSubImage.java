/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.opengl.GL45;

public final class ARBGetTextureSubImage {
    private ARBGetTextureSubImage() {
    }

    public static void glGetTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type2, ByteBuffer pixels) {
        GL45.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type2, pixels);
    }

    public static void glGetTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type2, DoubleBuffer pixels) {
        GL45.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type2, pixels);
    }

    public static void glGetTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type2, FloatBuffer pixels) {
        GL45.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type2, pixels);
    }

    public static void glGetTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type2, IntBuffer pixels) {
        GL45.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type2, pixels);
    }

    public static void glGetTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type2, ShortBuffer pixels) {
        GL45.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type2, pixels);
    }

    public static void glGetTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type2, int pixels_bufSize, long pixels_buffer_offset) {
        GL45.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type2, pixels_bufSize, pixels_buffer_offset);
    }

    public static void glGetCompressedTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, ByteBuffer pixels) {
        GL45.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels);
    }

    public static void glGetCompressedTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, DoubleBuffer pixels) {
        GL45.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels);
    }

    public static void glGetCompressedTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, FloatBuffer pixels) {
        GL45.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels);
    }

    public static void glGetCompressedTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, IntBuffer pixels) {
        GL45.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels);
    }

    public static void glGetCompressedTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, ShortBuffer pixels) {
        GL45.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels);
    }

    public static void glGetCompressedTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int pixels_bufSize, long pixels_buffer_offset) {
        GL45.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels_bufSize, pixels_buffer_offset);
    }
}

