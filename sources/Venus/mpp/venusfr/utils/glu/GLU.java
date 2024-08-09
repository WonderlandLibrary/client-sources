/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.glu;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import mpp.venusfr.utils.glu.Project;

public class GLU {
    public static boolean gluProject(float f, float f2, float f3, FloatBuffer floatBuffer, FloatBuffer floatBuffer2, IntBuffer intBuffer, FloatBuffer floatBuffer3) {
        return Project.gluProject(f, f2, f3, floatBuffer, floatBuffer2, intBuffer, floatBuffer3);
    }
}

