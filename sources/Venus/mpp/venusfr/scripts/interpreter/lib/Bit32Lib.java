/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.lib;

import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.lib.TwoArgFunction;
import mpp.venusfr.scripts.interpreter.lib.VarArgFunction;

public class Bit32Lib
extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        LuaTable luaTable = new LuaTable();
        this.bind(luaTable, Bit32LibV.class, new String[]{"band", "bnot", "bor", "btest", "bxor", "extract", "replace"});
        this.bind(luaTable, Bit32Lib2.class, new String[]{"arshift", "lrotate", "lshift", "rrotate", "rshift"});
        luaValue2.set("bit32", (LuaValue)luaTable);
        if (!luaValue2.get("package").isnil()) {
            luaValue2.get("package").get("loaded").set("bit32", (LuaValue)luaTable);
        }
        return luaTable;
    }

    static LuaValue arshift(int n, int n2) {
        if (n2 >= 0) {
            return Bit32Lib.bitsToValue(n >> n2);
        }
        return Bit32Lib.bitsToValue(n << -n2);
    }

    static LuaValue rshift(int n, int n2) {
        if (n2 >= 32 || n2 <= -32) {
            return ZERO;
        }
        if (n2 >= 0) {
            return Bit32Lib.bitsToValue(n >>> n2);
        }
        return Bit32Lib.bitsToValue(n << -n2);
    }

    static LuaValue lshift(int n, int n2) {
        if (n2 >= 32 || n2 <= -32) {
            return ZERO;
        }
        if (n2 >= 0) {
            return Bit32Lib.bitsToValue(n << n2);
        }
        return Bit32Lib.bitsToValue(n >>> -n2);
    }

    static Varargs band(Varargs varargs) {
        int n = -1;
        for (int i = 1; i <= varargs.narg(); ++i) {
            n &= varargs.checkint(i);
        }
        return Bit32Lib.bitsToValue(n);
    }

    static Varargs bnot(Varargs varargs) {
        return Bit32Lib.bitsToValue(~varargs.checkint(1));
    }

    static Varargs bor(Varargs varargs) {
        int n = 0;
        for (int i = 1; i <= varargs.narg(); ++i) {
            n |= varargs.checkint(i);
        }
        return Bit32Lib.bitsToValue(n);
    }

    static Varargs btest(Varargs varargs) {
        int n = -1;
        for (int i = 1; i <= varargs.narg(); ++i) {
            n &= varargs.checkint(i);
        }
        return Bit32Lib.valueOf(n != 0);
    }

    static Varargs bxor(Varargs varargs) {
        int n = 0;
        for (int i = 1; i <= varargs.narg(); ++i) {
            n ^= varargs.checkint(i);
        }
        return Bit32Lib.bitsToValue(n);
    }

    static LuaValue lrotate(int n, int n2) {
        if (n2 < 0) {
            return Bit32Lib.rrotate(n, -n2);
        }
        return Bit32Lib.bitsToValue(n << (n2 &= 0x1F) | n >>> 32 - n2);
    }

    static LuaValue rrotate(int n, int n2) {
        if (n2 < 0) {
            return Bit32Lib.lrotate(n, -n2);
        }
        return Bit32Lib.bitsToValue(n >>> (n2 &= 0x1F) | n << 32 - n2);
    }

    static LuaValue extract(int n, int n2, int n3) {
        if (n2 < 0) {
            Bit32Lib.argerror(2, "field cannot be negative");
        }
        if (n3 < 0) {
            Bit32Lib.argerror(3, "width must be postive");
        }
        if (n2 + n3 > 32) {
            Bit32Lib.error("trying to access non-existent bits");
        }
        return Bit32Lib.bitsToValue(n >>> n2 & -1 >>> 32 - n3);
    }

    static LuaValue replace(int n, int n2, int n3, int n4) {
        if (n3 < 0) {
            Bit32Lib.argerror(3, "field cannot be negative");
        }
        if (n4 < 0) {
            Bit32Lib.argerror(4, "width must be postive");
        }
        if (n3 + n4 > 32) {
            Bit32Lib.error("trying to access non-existent bits");
        }
        int n5 = -1 >>> 32 - n4 << n3;
        n = n & ~n5 | n2 << n3 & n5;
        return Bit32Lib.bitsToValue(n);
    }

    private static LuaValue bitsToValue(int n) {
        return n < 0 ? Bit32Lib.valueOf((long)n & 0xFFFFFFFFL) : Bit32Lib.valueOf(n);
    }

    static final class Bit32LibV
    extends VarArgFunction {
        Bit32LibV() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            switch (this.opcode) {
                case 0: {
                    return Bit32Lib.band(varargs);
                }
                case 1: {
                    return Bit32Lib.bnot(varargs);
                }
                case 2: {
                    return Bit32Lib.bor(varargs);
                }
                case 3: {
                    return Bit32Lib.btest(varargs);
                }
                case 4: {
                    return Bit32Lib.bxor(varargs);
                }
                case 5: {
                    return Bit32Lib.extract(varargs.checkint(1), varargs.checkint(2), varargs.optint(3, 1));
                }
                case 6: {
                    return Bit32Lib.replace(varargs.checkint(1), varargs.checkint(2), varargs.checkint(3), varargs.optint(4, 1));
                }
            }
            return NIL;
        }
    }

    static final class Bit32Lib2
    extends TwoArgFunction {
        Bit32Lib2() {
        }

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            switch (this.opcode) {
                case 0: {
                    return Bit32Lib.arshift(luaValue.checkint(), luaValue2.checkint());
                }
                case 1: {
                    return Bit32Lib.lrotate(luaValue.checkint(), luaValue2.checkint());
                }
                case 2: {
                    return Bit32Lib.lshift(luaValue.checkint(), luaValue2.checkint());
                }
                case 3: {
                    return Bit32Lib.rrotate(luaValue.checkint(), luaValue2.checkint());
                }
                case 4: {
                    return Bit32Lib.rshift(luaValue.checkint(), luaValue2.checkint());
                }
            }
            return NIL;
        }
    }
}

