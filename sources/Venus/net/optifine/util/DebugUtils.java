/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.nio.FloatBuffer;
import net.minecraft.util.math.vector.Matrix4f;
import net.optifine.util.StrUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class DebugUtils {
    private static FloatBuffer floatBuffer16 = BufferUtils.createFloatBuffer(16);
    private static float[] floatArray16 = new float[16];

    public static String getGlModelView() {
        floatBuffer16.clear();
        GL11.glGetFloatv(2982, floatBuffer16);
        floatBuffer16.get(floatArray16);
        float[] fArray = DebugUtils.transposeMat4(floatArray16);
        return DebugUtils.getMatrix4(fArray);
    }

    public static String getGlProjection() {
        floatBuffer16.clear();
        GL11.glGetFloatv(2983, floatBuffer16);
        floatBuffer16.get(floatArray16);
        float[] fArray = DebugUtils.transposeMat4(floatArray16);
        return DebugUtils.getMatrix4(fArray);
    }

    private static float[] transposeMat4(float[] fArray) {
        float[] fArray2 = new float[16];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                fArray2[i * 4 + j] = fArray[j * 4 + i];
            }
        }
        return fArray2;
    }

    public static String getMatrix4(Matrix4f matrix4f) {
        matrix4f.write(floatArray16);
        return DebugUtils.getMatrix4(floatArray16);
    }

    private static String getMatrix4(float[] fArray) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < fArray.length; ++i) {
            String string = String.format("%.2f", Float.valueOf(fArray[i]));
            if (i > 0) {
                if (i % 4 == 0) {
                    stringBuffer.append("\n");
                } else {
                    stringBuffer.append(", ");
                }
            }
            string = StrUtils.fillLeft(string, 5, ' ');
            stringBuffer.append(string);
        }
        return stringBuffer.toString();
    }
}

