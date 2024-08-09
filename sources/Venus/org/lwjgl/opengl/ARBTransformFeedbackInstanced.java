/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL42C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBTransformFeedbackInstanced {
    protected ARBTransformFeedbackInstanced() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glDrawTransformFeedbackInstanced, gLCapabilities.glDrawTransformFeedbackStreamInstanced);
    }

    public static void glDrawTransformFeedbackInstanced(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLsizei") int n3) {
        GL42C.glDrawTransformFeedbackInstanced(n, n2, n3);
    }

    public static void glDrawTransformFeedbackStreamInstanced(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLsizei") int n4) {
        GL42C.glDrawTransformFeedbackStreamInstanced(n, n2, n3, n4);
    }

    static {
        GL.initialize();
    }
}

