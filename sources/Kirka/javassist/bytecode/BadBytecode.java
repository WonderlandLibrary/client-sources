/*
 * Decompiled with CFR 0.143.
 */
package javassist.bytecode;

import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;

public class BadBytecode
extends Exception {
    public BadBytecode(int opcode) {
        super("bytecode " + opcode);
    }

    public BadBytecode(String msg) {
        super(msg);
    }

    public BadBytecode(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BadBytecode(MethodInfo minfo, Throwable cause) {
        super(minfo.toString() + " in " + minfo.getConstPool().getClassName() + ": " + cause.getMessage(), cause);
    }
}

