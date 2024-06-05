/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.javassist.bytecode.analysis;

import com.viaversion.viaversion.libs.javassist.bytecode.CodeIterator;
import com.viaversion.viaversion.libs.javassist.bytecode.Opcode;

public class Util
implements Opcode {
    public static int getJumpTarget(int pos, CodeIterator iter) {
        int n;
        int opcode = iter.byteAt(pos);
        if (opcode != 201 && opcode != 200) {
            n = iter.s16bitAt(pos + 1);
            return pos += n;
        }
        n = iter.s32bitAt(pos + 1);
        return pos += n;
    }

    public static boolean isJumpInstruction(int opcode) {
        if (opcode >= 153) {
            if (opcode <= 168) return true;
        }
        if (opcode == 198) return true;
        if (opcode == 199) return true;
        if (opcode == 201) return true;
        if (opcode == 200) return true;
        return false;
    }

    public static boolean isGoto(int opcode) {
        if (opcode == 167) return true;
        if (opcode == 200) return true;
        return false;
    }

    public static boolean isJsr(int opcode) {
        if (opcode == 168) return true;
        if (opcode == 201) return true;
        return false;
    }

    public static boolean isReturn(int opcode) {
        if (opcode < 172) return false;
        if (opcode > 177) return false;
        return true;
    }
}

