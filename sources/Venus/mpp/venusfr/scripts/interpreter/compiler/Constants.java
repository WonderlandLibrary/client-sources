/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler;

import mpp.venusfr.scripts.interpreter.LocVars;
import mpp.venusfr.scripts.interpreter.Lua;
import mpp.venusfr.scripts.interpreter.LuaError;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Prototype;
import mpp.venusfr.scripts.interpreter.Upvaldesc;
import mpp.venusfr.scripts.interpreter.compiler.InstructionPtr;
import mpp.venusfr.scripts.interpreter.compiler.LexState;

public class Constants
extends Lua {
    public static final int MAXSTACK = 250;
    static final int LUAI_MAXUPVAL = 255;
    static final int LUAI_MAXVARS = 200;
    static final int NO_REG = 255;
    static final int iABC = 0;
    static final int iABx = 1;
    static final int iAsBx = 2;
    static final int OpArgN = 0;
    static final int OpArgU = 1;
    static final int OpArgR = 2;
    static final int OpArgK = 3;

    protected static void _assert(boolean bl) {
        if (!bl) {
            throw new LuaError("compiler assert failed");
        }
    }

    static void SET_OPCODE(InstructionPtr instructionPtr, int n) {
        instructionPtr.set(instructionPtr.get() & 0xFFFFFFC0 | n << 0 & 0x3F);
    }

    static void SETARG_A(int[] nArray, int n, int n2) {
        nArray[n] = nArray[n] & 0xFFFFC03F | n2 << 6 & 0x3FC0;
    }

    static void SETARG_A(InstructionPtr instructionPtr, int n) {
        instructionPtr.set(instructionPtr.get() & 0xFFFFC03F | n << 6 & 0x3FC0);
    }

    static void SETARG_B(InstructionPtr instructionPtr, int n) {
        instructionPtr.set(instructionPtr.get() & 0x7FFFFF | n << 23 & 0xFF800000);
    }

    static void SETARG_C(InstructionPtr instructionPtr, int n) {
        instructionPtr.set(instructionPtr.get() & 0xFF803FFF | n << 14 & 0x7FC000);
    }

    static void SETARG_Bx(InstructionPtr instructionPtr, int n) {
        instructionPtr.set(instructionPtr.get() & 0x3FFF | n << 14 & 0xFFFFC000);
    }

    static void SETARG_sBx(InstructionPtr instructionPtr, int n) {
        Constants.SETARG_Bx(instructionPtr, n + 131071);
    }

    static int CREATE_ABC(int n, int n2, int n3, int n4) {
        return n << 0 & 0x3F | n2 << 6 & 0x3FC0 | n3 << 23 & 0xFF800000 | n4 << 14 & 0x7FC000;
    }

    static int CREATE_ABx(int n, int n2, int n3) {
        return n << 0 & 0x3F | n2 << 6 & 0x3FC0 | n3 << 14 & 0xFFFFC000;
    }

    static int CREATE_Ax(int n, int n2) {
        return n << 0 & 0x3F | n2 << 6 & 0xFFFFFFC0;
    }

    static LuaValue[] realloc(LuaValue[] luaValueArray, int n) {
        LuaValue[] luaValueArray2 = new LuaValue[n];
        if (luaValueArray != null) {
            System.arraycopy(luaValueArray, 0, luaValueArray2, 0, Math.min(luaValueArray.length, n));
        }
        return luaValueArray2;
    }

    static Prototype[] realloc(Prototype[] prototypeArray, int n) {
        Prototype[] prototypeArray2 = new Prototype[n];
        if (prototypeArray != null) {
            System.arraycopy(prototypeArray, 0, prototypeArray2, 0, Math.min(prototypeArray.length, n));
        }
        return prototypeArray2;
    }

    static LuaString[] realloc(LuaString[] luaStringArray, int n) {
        LuaString[] luaStringArray2 = new LuaString[n];
        if (luaStringArray != null) {
            System.arraycopy(luaStringArray, 0, luaStringArray2, 0, Math.min(luaStringArray.length, n));
        }
        return luaStringArray2;
    }

    static LocVars[] realloc(LocVars[] locVarsArray, int n) {
        LocVars[] locVarsArray2 = new LocVars[n];
        if (locVarsArray != null) {
            System.arraycopy(locVarsArray, 0, locVarsArray2, 0, Math.min(locVarsArray.length, n));
        }
        return locVarsArray2;
    }

    static Upvaldesc[] realloc(Upvaldesc[] upvaldescArray, int n) {
        Upvaldesc[] upvaldescArray2 = new Upvaldesc[n];
        if (upvaldescArray != null) {
            System.arraycopy(upvaldescArray, 0, upvaldescArray2, 0, Math.min(upvaldescArray.length, n));
        }
        return upvaldescArray2;
    }

    static LexState.Vardesc[] realloc(LexState.Vardesc[] vardescArray, int n) {
        LexState.Vardesc[] vardescArray2 = new LexState.Vardesc[n];
        if (vardescArray != null) {
            System.arraycopy(vardescArray, 0, vardescArray2, 0, Math.min(vardescArray.length, n));
        }
        return vardescArray2;
    }

    static LexState.Labeldesc[] grow(LexState.Labeldesc[] labeldescArray, int n) {
        return labeldescArray == null ? new LexState.Labeldesc[2] : (labeldescArray.length < n ? Constants.realloc(labeldescArray, labeldescArray.length * 2) : labeldescArray);
    }

    static LexState.Labeldesc[] realloc(LexState.Labeldesc[] labeldescArray, int n) {
        LexState.Labeldesc[] labeldescArray2 = new LexState.Labeldesc[n];
        if (labeldescArray != null) {
            System.arraycopy(labeldescArray, 0, labeldescArray2, 0, Math.min(labeldescArray.length, n));
        }
        return labeldescArray2;
    }

    static int[] realloc(int[] nArray, int n) {
        int[] nArray2 = new int[n];
        if (nArray != null) {
            System.arraycopy(nArray, 0, nArray2, 0, Math.min(nArray.length, n));
        }
        return nArray2;
    }

    static byte[] realloc(byte[] byArray, int n) {
        byte[] byArray2 = new byte[n];
        if (byArray != null) {
            System.arraycopy(byArray, 0, byArray2, 0, Math.min(byArray.length, n));
        }
        return byArray2;
    }

    static char[] realloc(char[] cArray, int n) {
        char[] cArray2 = new char[n];
        if (cArray != null) {
            System.arraycopy(cArray, 0, cArray2, 0, Math.min(cArray.length, n));
        }
        return cArray2;
    }

    protected Constants() {
    }
}

