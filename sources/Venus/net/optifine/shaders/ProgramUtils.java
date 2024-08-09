/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import net.optifine.shaders.Program;

public class ProgramUtils {
    public static boolean hasActive(Program[] programArray) {
        for (int i = 0; i < programArray.length; ++i) {
            Program program = programArray[i];
            if (program.getId() != 0) {
                return false;
            }
            if (program.getComputePrograms().length <= 0) continue;
            return false;
        }
        return true;
    }
}

