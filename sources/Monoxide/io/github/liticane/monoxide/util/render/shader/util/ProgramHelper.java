package io.github.liticane.monoxide.util.render.shader.util;

import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

public class ProgramHelper {

    public static void uniform1f(final int programId, final String name, final float f) {
        GL20.glUniform1f(getLocation(programId, name), f);
    }

    public static void uniform2f(final int programId, final String name, final float f, final float g) {
        GL20.glUniform2f(getLocation(programId, name), f, g);
    }

    public static void uniform4f(final int programId, final String name, final float f, final float g, final float h, final float i) {
        GL20.glUniform4f(getLocation(programId, name), f, g, h, i);
    }

    public static void uniformFB(final int programId, final String name, final FloatBuffer floatBuffer) {
        GL20.glUniform1fv(getLocation(programId, name), floatBuffer);
    }

    public static void uniform1i(final int programId, final String name, final int i) {
        GL20.glUniform1i(getLocation(programId, name), i);
    }


    private static int getLocation(final int programId, final String name) {
        return GL20.glGetUniformLocation(programId, name);
    }

}
