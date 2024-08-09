/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler.jse;

import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.compiler.jse.CoerceLuaToJava;
import mpp.venusfr.scripts.interpreter.lib.VarArgFunction;

abstract class JavaMember
extends VarArgFunction {
    static final int METHOD_MODIFIERS_VARARGS = 128;
    final CoerceLuaToJava.Coercion[] fixedargs;
    final CoerceLuaToJava.Coercion varargs;

    protected JavaMember(Class[] classArray, int n) {
        boolean bl = (n & 0x80) != 0;
        this.fixedargs = new CoerceLuaToJava.Coercion[bl ? classArray.length - 1 : classArray.length];
        for (int i = 0; i < this.fixedargs.length; ++i) {
            this.fixedargs[i] = CoerceLuaToJava.getCoercion(classArray[i]);
        }
        this.varargs = bl ? CoerceLuaToJava.getCoercion(classArray[classArray.length - 1]) : null;
    }

    int score(Varargs varargs) {
        int n;
        int n2 = varargs.narg();
        int n3 = n2 > this.fixedargs.length ? CoerceLuaToJava.SCORE_WRONG_TYPE * (n2 - this.fixedargs.length) : 0;
        for (n = 0; n < this.fixedargs.length; ++n) {
            n3 += this.fixedargs[n].score(varargs.arg(n + 1));
        }
        if (this.varargs != null) {
            for (n = this.fixedargs.length; n < n2; ++n) {
                n3 += this.varargs.score(varargs.arg(n + 1));
            }
        }
        return n3;
    }

    protected Object[] convertArgs(Varargs varargs) {
        Object[] objectArray;
        if (this.varargs == null) {
            objectArray = new Object[this.fixedargs.length];
            for (int i = 0; i < objectArray.length; ++i) {
                objectArray[i] = this.fixedargs[i].coerce(varargs.arg(i + 1));
            }
        } else {
            int n;
            int n2 = Math.max(this.fixedargs.length, varargs.narg());
            objectArray = new Object[n2];
            for (n = 0; n < this.fixedargs.length; ++n) {
                objectArray[n] = this.fixedargs[n].coerce(varargs.arg(n + 1));
            }
            for (n = this.fixedargs.length; n < n2; ++n) {
                objectArray[n] = this.varargs.coerce(varargs.arg(n + 1));
            }
        }
        return objectArray;
    }
}

