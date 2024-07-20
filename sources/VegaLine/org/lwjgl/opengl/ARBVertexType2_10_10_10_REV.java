/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.opengl.GL33;

public final class ARBVertexType2_10_10_10_REV {
    public static final int GL_UNSIGNED_INT_2_10_10_10_REV = 33640;
    public static final int GL_INT_2_10_10_10_REV = 36255;

    private ARBVertexType2_10_10_10_REV() {
    }

    public static void glVertexP2ui(int type2, int value) {
        GL33.glVertexP2ui(type2, value);
    }

    public static void glVertexP3ui(int type2, int value) {
        GL33.glVertexP3ui(type2, value);
    }

    public static void glVertexP4ui(int type2, int value) {
        GL33.glVertexP4ui(type2, value);
    }

    public static void glVertexP2u(int type2, IntBuffer value) {
        GL33.glVertexP2u(type2, value);
    }

    public static void glVertexP3u(int type2, IntBuffer value) {
        GL33.glVertexP3u(type2, value);
    }

    public static void glVertexP4u(int type2, IntBuffer value) {
        GL33.glVertexP4u(type2, value);
    }

    public static void glTexCoordP1ui(int type2, int coords) {
        GL33.glTexCoordP1ui(type2, coords);
    }

    public static void glTexCoordP2ui(int type2, int coords) {
        GL33.glTexCoordP2ui(type2, coords);
    }

    public static void glTexCoordP3ui(int type2, int coords) {
        GL33.glTexCoordP3ui(type2, coords);
    }

    public static void glTexCoordP4ui(int type2, int coords) {
        GL33.glTexCoordP4ui(type2, coords);
    }

    public static void glTexCoordP1u(int type2, IntBuffer coords) {
        GL33.glTexCoordP1u(type2, coords);
    }

    public static void glTexCoordP2u(int type2, IntBuffer coords) {
        GL33.glTexCoordP2u(type2, coords);
    }

    public static void glTexCoordP3u(int type2, IntBuffer coords) {
        GL33.glTexCoordP3u(type2, coords);
    }

    public static void glTexCoordP4u(int type2, IntBuffer coords) {
        GL33.glTexCoordP4u(type2, coords);
    }

    public static void glMultiTexCoordP1ui(int texture, int type2, int coords) {
        GL33.glMultiTexCoordP1ui(texture, type2, coords);
    }

    public static void glMultiTexCoordP2ui(int texture, int type2, int coords) {
        GL33.glMultiTexCoordP2ui(texture, type2, coords);
    }

    public static void glMultiTexCoordP3ui(int texture, int type2, int coords) {
        GL33.glMultiTexCoordP3ui(texture, type2, coords);
    }

    public static void glMultiTexCoordP4ui(int texture, int type2, int coords) {
        GL33.glMultiTexCoordP4ui(texture, type2, coords);
    }

    public static void glMultiTexCoordP1u(int texture, int type2, IntBuffer coords) {
        GL33.glMultiTexCoordP1u(texture, type2, coords);
    }

    public static void glMultiTexCoordP2u(int texture, int type2, IntBuffer coords) {
        GL33.glMultiTexCoordP2u(texture, type2, coords);
    }

    public static void glMultiTexCoordP3u(int texture, int type2, IntBuffer coords) {
        GL33.glMultiTexCoordP3u(texture, type2, coords);
    }

    public static void glMultiTexCoordP4u(int texture, int type2, IntBuffer coords) {
        GL33.glMultiTexCoordP4u(texture, type2, coords);
    }

    public static void glNormalP3ui(int type2, int coords) {
        GL33.glNormalP3ui(type2, coords);
    }

    public static void glNormalP3u(int type2, IntBuffer coords) {
        GL33.glNormalP3u(type2, coords);
    }

    public static void glColorP3ui(int type2, int color) {
        GL33.glColorP3ui(type2, color);
    }

    public static void glColorP4ui(int type2, int color) {
        GL33.glColorP4ui(type2, color);
    }

    public static void glColorP3u(int type2, IntBuffer color) {
        GL33.glColorP3u(type2, color);
    }

    public static void glColorP4u(int type2, IntBuffer color) {
        GL33.glColorP4u(type2, color);
    }

    public static void glSecondaryColorP3ui(int type2, int color) {
        GL33.glSecondaryColorP3ui(type2, color);
    }

    public static void glSecondaryColorP3u(int type2, IntBuffer color) {
        GL33.glSecondaryColorP3u(type2, color);
    }

    public static void glVertexAttribP1ui(int index, int type2, boolean normalized, int value) {
        GL33.glVertexAttribP1ui(index, type2, normalized, value);
    }

    public static void glVertexAttribP2ui(int index, int type2, boolean normalized, int value) {
        GL33.glVertexAttribP2ui(index, type2, normalized, value);
    }

    public static void glVertexAttribP3ui(int index, int type2, boolean normalized, int value) {
        GL33.glVertexAttribP3ui(index, type2, normalized, value);
    }

    public static void glVertexAttribP4ui(int index, int type2, boolean normalized, int value) {
        GL33.glVertexAttribP4ui(index, type2, normalized, value);
    }

    public static void glVertexAttribP1u(int index, int type2, boolean normalized, IntBuffer value) {
        GL33.glVertexAttribP1u(index, type2, normalized, value);
    }

    public static void glVertexAttribP2u(int index, int type2, boolean normalized, IntBuffer value) {
        GL33.glVertexAttribP2u(index, type2, normalized, value);
    }

    public static void glVertexAttribP3u(int index, int type2, boolean normalized, IntBuffer value) {
        GL33.glVertexAttribP3u(index, type2, normalized, value);
    }

    public static void glVertexAttribP4u(int index, int type2, boolean normalized, IntBuffer value) {
        GL33.glVertexAttribP4u(index, type2, normalized, value);
    }
}

