/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.lib;

import mpp.venusfr.scripts.interpreter.LuaError;
import mpp.venusfr.scripts.interpreter.LuaFunction;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.utils.client.IMinecraft;

public abstract class LibFunction
extends LuaFunction
implements IMinecraft {
    protected int opcode;
    protected String name;

    protected LibFunction() {
    }

    @Override
    public String tojstring() {
        return this.name != null ? "function: " + this.name : super.tojstring();
    }

    protected void bind(LuaValue luaValue, Class clazz, String[] stringArray) {
        this.bind(luaValue, clazz, stringArray, 0);
    }

    protected void bind(LuaValue luaValue, Class clazz, String[] stringArray, int n) {
        try {
            int n2 = stringArray.length;
            for (int i = 0; i < n2; ++i) {
                LibFunction libFunction = (LibFunction)clazz.newInstance();
                libFunction.opcode = n + i;
                libFunction.name = stringArray[i];
                luaValue.set(libFunction.name, (LuaValue)libFunction);
            }
        } catch (Exception exception) {
            throw new LuaError("bind failed: " + exception);
        }
    }

    protected static LuaValue[] newupe() {
        return new LuaValue[1];
    }

    protected static LuaValue[] newupn() {
        return new LuaValue[]{NIL};
    }

    protected static LuaValue[] newupl(LuaValue luaValue) {
        return new LuaValue[]{luaValue};
    }

    @Override
    public LuaValue call() {
        return LibFunction.argerror(1, "value expected");
    }

    @Override
    public LuaValue call(LuaValue luaValue) {
        return this.call();
    }

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        return this.call(luaValue);
    }

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
        return this.call(luaValue, luaValue2);
    }

    public LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3, LuaValue luaValue4) {
        return this.call(luaValue, luaValue2, luaValue3);
    }

    @Override
    public Varargs invoke(Varargs varargs) {
        switch (varargs.narg()) {
            case 0: {
                return this.call();
            }
            case 1: {
                return this.call(varargs.arg1());
            }
            case 2: {
                return this.call(varargs.arg1(), varargs.arg(2));
            }
            case 3: {
                return this.call(varargs.arg1(), varargs.arg(2), varargs.arg(3));
            }
        }
        return this.call(varargs.arg1(), varargs.arg(2), varargs.arg(3), varargs.arg(4));
    }
}

