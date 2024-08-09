/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL43C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLDebugMessageCallbackI;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class KHRDebug {
    public static final int GL_DEBUG_OUTPUT = 37600;
    public static final int GL_DEBUG_OUTPUT_SYNCHRONOUS = 33346;
    public static final int GL_CONTEXT_FLAG_DEBUG_BIT = 2;
    public static final int GL_MAX_DEBUG_MESSAGE_LENGTH = 37187;
    public static final int GL_MAX_DEBUG_LOGGED_MESSAGES = 37188;
    public static final int GL_DEBUG_LOGGED_MESSAGES = 37189;
    public static final int GL_DEBUG_NEXT_LOGGED_MESSAGE_LENGTH = 33347;
    public static final int GL_MAX_DEBUG_GROUP_STACK_DEPTH = 33388;
    public static final int GL_DEBUG_GROUP_STACK_DEPTH = 33389;
    public static final int GL_MAX_LABEL_LENGTH = 33512;
    public static final int GL_DEBUG_CALLBACK_FUNCTION = 33348;
    public static final int GL_DEBUG_CALLBACK_USER_PARAM = 33349;
    public static final int GL_DEBUG_SOURCE_API = 33350;
    public static final int GL_DEBUG_SOURCE_WINDOW_SYSTEM = 33351;
    public static final int GL_DEBUG_SOURCE_SHADER_COMPILER = 33352;
    public static final int GL_DEBUG_SOURCE_THIRD_PARTY = 33353;
    public static final int GL_DEBUG_SOURCE_APPLICATION = 33354;
    public static final int GL_DEBUG_SOURCE_OTHER = 33355;
    public static final int GL_DEBUG_TYPE_ERROR = 33356;
    public static final int GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR = 33357;
    public static final int GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR = 33358;
    public static final int GL_DEBUG_TYPE_PORTABILITY = 33359;
    public static final int GL_DEBUG_TYPE_PERFORMANCE = 33360;
    public static final int GL_DEBUG_TYPE_OTHER = 33361;
    public static final int GL_DEBUG_TYPE_MARKER = 33384;
    public static final int GL_DEBUG_TYPE_PUSH_GROUP = 33385;
    public static final int GL_DEBUG_TYPE_POP_GROUP = 33386;
    public static final int GL_DEBUG_SEVERITY_HIGH = 37190;
    public static final int GL_DEBUG_SEVERITY_MEDIUM = 37191;
    public static final int GL_DEBUG_SEVERITY_LOW = 37192;
    public static final int GL_DEBUG_SEVERITY_NOTIFICATION = 33387;
    public static final int GL_BUFFER = 33504;
    public static final int GL_SHADER = 33505;
    public static final int GL_PROGRAM = 33506;
    public static final int GL_QUERY = 33507;
    public static final int GL_PROGRAM_PIPELINE = 33508;
    public static final int GL_SAMPLER = 33510;
    public static final int GL_DISPLAY_LIST = 33511;

    protected KHRDebug() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glDebugMessageControl, gLCapabilities.glDebugMessageInsert, gLCapabilities.glDebugMessageCallback, gLCapabilities.glGetDebugMessageLog, gLCapabilities.glPushDebugGroup, gLCapabilities.glPopDebugGroup, gLCapabilities.glObjectLabel, gLCapabilities.glGetObjectLabel, gLCapabilities.glObjectPtrLabel, gLCapabilities.glGetObjectPtrLabel);
    }

    public static void nglDebugMessageControl(int n, int n2, int n3, int n4, long l, boolean bl) {
        GL43C.nglDebugMessageControl(n, n2, n3, n4, l, bl);
    }

    public static void glDebugMessageControl(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @Nullable @NativeType(value="GLuint const *") IntBuffer intBuffer, @NativeType(value="GLboolean") boolean bl) {
        GL43C.glDebugMessageControl(n, n2, n3, intBuffer, bl);
    }

    public static void glDebugMessageControl(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint const *") int n4, @NativeType(value="GLboolean") boolean bl) {
        GL43C.glDebugMessageControl(n, n2, n3, n4, bl);
    }

    public static void nglDebugMessageInsert(int n, int n2, int n3, int n4, int n5, long l) {
        GL43C.nglDebugMessageInsert(n, n2, n3, n4, n5, l);
    }

    public static void glDebugMessageInsert(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        GL43C.glDebugMessageInsert(n, n2, n3, n4, byteBuffer);
    }

    public static void glDebugMessageInsert(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLchar const *") CharSequence charSequence) {
        GL43C.glDebugMessageInsert(n, n2, n3, n4, charSequence);
    }

    public static void nglDebugMessageCallback(long l, long l2) {
        GL43C.nglDebugMessageCallback(l, l2);
    }

    public static void glDebugMessageCallback(@Nullable @NativeType(value="GLDEBUGPROC") GLDebugMessageCallbackI gLDebugMessageCallbackI, @NativeType(value="void const *") long l) {
        GL43C.glDebugMessageCallback(gLDebugMessageCallbackI, l);
    }

    public static int nglGetDebugMessageLog(int n, int n2, long l, long l2, long l3, long l4, long l5, long l6) {
        return GL43C.nglGetDebugMessageLog(n, n2, l, l2, l3, l4, l5, l6);
    }

    @NativeType(value="GLuint")
    public static int glGetDebugMessageLog(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLenum *") IntBuffer intBuffer, @Nullable @NativeType(value="GLenum *") IntBuffer intBuffer2, @Nullable @NativeType(value="GLuint *") IntBuffer intBuffer3, @Nullable @NativeType(value="GLenum *") IntBuffer intBuffer4, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer5, @Nullable @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        return GL43C.glGetDebugMessageLog(n, intBuffer, intBuffer2, intBuffer3, intBuffer4, intBuffer5, byteBuffer);
    }

    public static void nglPushDebugGroup(int n, int n2, int n3, long l) {
        GL43C.nglPushDebugGroup(n, n2, n3, l);
    }

    public static void glPushDebugGroup(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        GL43C.glPushDebugGroup(n, n2, byteBuffer);
    }

    public static void glPushDebugGroup(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLchar const *") CharSequence charSequence) {
        GL43C.glPushDebugGroup(n, n2, charSequence);
    }

    public static void glPopDebugGroup() {
        GL43C.glPopDebugGroup();
    }

    public static void nglObjectLabel(int n, int n2, int n3, long l) {
        GL43C.nglObjectLabel(n, n2, n3, l);
    }

    public static void glObjectLabel(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        GL43C.glObjectLabel(n, n2, byteBuffer);
    }

    public static void glObjectLabel(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLchar const *") CharSequence charSequence) {
        GL43C.glObjectLabel(n, n2, charSequence);
    }

    public static void nglGetObjectLabel(int n, int n2, int n3, long l, long l2) {
        GL43C.nglGetObjectLabel(n, n2, n3, l, l2);
    }

    public static void glGetObjectLabel(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        GL43C.glGetObjectLabel(n, n2, intBuffer, byteBuffer);
    }

    @NativeType(value="void")
    public static String glGetObjectLabel(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLsizei") int n3) {
        return GL43C.glGetObjectLabel(n, n2, n3);
    }

    @NativeType(value="void")
    public static String glGetObjectLabel(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        return KHRDebug.glGetObjectLabel(n, n2, GL11.glGetInteger(33512));
    }

    public static void nglObjectPtrLabel(long l, int n, long l2) {
        GL43C.nglObjectPtrLabel(l, n, l2);
    }

    public static void glObjectPtrLabel(@NativeType(value="void *") long l, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        GL43C.glObjectPtrLabel(l, byteBuffer);
    }

    public static void glObjectPtrLabel(@NativeType(value="void *") long l, @NativeType(value="GLchar const *") CharSequence charSequence) {
        GL43C.glObjectPtrLabel(l, charSequence);
    }

    public static void nglGetObjectPtrLabel(long l, int n, long l2, long l3) {
        GL43C.nglGetObjectPtrLabel(l, n, l2, l3);
    }

    public static void glGetObjectPtrLabel(@NativeType(value="void *") long l, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        GL43C.glGetObjectPtrLabel(l, intBuffer, byteBuffer);
    }

    @NativeType(value="void")
    public static String glGetObjectPtrLabel(@NativeType(value="void *") long l, @NativeType(value="GLsizei") int n) {
        return GL43C.glGetObjectPtrLabel(l, n);
    }

    @NativeType(value="void")
    public static String glGetObjectPtrLabel(@NativeType(value="void *") long l) {
        return KHRDebug.glGetObjectPtrLabel(l, GL11.glGetInteger(33512));
    }

    public static void glDebugMessageControl(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @Nullable @NativeType(value="GLuint const *") int[] nArray, @NativeType(value="GLboolean") boolean bl) {
        GL43C.glDebugMessageControl(n, n2, n3, nArray, bl);
    }

    @NativeType(value="GLuint")
    public static int glGetDebugMessageLog(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLenum *") int[] nArray, @Nullable @NativeType(value="GLenum *") int[] nArray2, @Nullable @NativeType(value="GLuint *") int[] nArray3, @Nullable @NativeType(value="GLenum *") int[] nArray4, @Nullable @NativeType(value="GLsizei *") int[] nArray5, @Nullable @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        return GL43C.glGetDebugMessageLog(n, nArray, nArray2, nArray3, nArray4, nArray5, byteBuffer);
    }

    public static void glGetObjectLabel(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        GL43C.glGetObjectLabel(n, n2, nArray, byteBuffer);
    }

    public static void glGetObjectPtrLabel(@NativeType(value="void *") long l, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        GL43C.glGetObjectPtrLabel(l, nArray, byteBuffer);
    }

    static {
        GL.initialize();
    }
}

