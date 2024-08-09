/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import mpp.venusfr.scripts.interpreter.Buffer;
import mpp.venusfr.scripts.interpreter.LuaClosure;
import mpp.venusfr.scripts.interpreter.LuaFunction;
import mpp.venusfr.scripts.interpreter.LuaInteger;
import mpp.venusfr.scripts.interpreter.LuaNumber;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaThread;
import mpp.venusfr.scripts.interpreter.LuaValue;

public abstract class Varargs {
    public abstract LuaValue arg(int var1);

    public abstract int narg();

    public abstract LuaValue arg1();

    public Varargs eval() {
        return this;
    }

    public boolean isTailcall() {
        return true;
    }

    public int type(int n) {
        return this.arg(n).type();
    }

    public boolean isnil(int n) {
        return this.arg(n).isnil();
    }

    public boolean isfunction(int n) {
        return this.arg(n).isfunction();
    }

    public boolean isnumber(int n) {
        return this.arg(n).isnumber();
    }

    public boolean isstring(int n) {
        return this.arg(n).isstring();
    }

    public boolean istable(int n) {
        return this.arg(n).istable();
    }

    public boolean isthread(int n) {
        return this.arg(n).isthread();
    }

    public boolean isuserdata(int n) {
        return this.arg(n).isuserdata();
    }

    public boolean isvalue(int n) {
        return n > 0 && n <= this.narg();
    }

    public boolean optboolean(int n, boolean bl) {
        return this.arg(n).optboolean(bl);
    }

    public LuaClosure optclosure(int n, LuaClosure luaClosure) {
        return this.arg(n).optclosure(luaClosure);
    }

    public double optdouble(int n, double d) {
        return this.arg(n).optdouble(d);
    }

    public LuaFunction optfunction(int n, LuaFunction luaFunction) {
        return this.arg(n).optfunction(luaFunction);
    }

    public int optint(int n, int n2) {
        return this.arg(n).optint(n2);
    }

    public LuaInteger optinteger(int n, LuaInteger luaInteger) {
        return this.arg(n).optinteger(luaInteger);
    }

    public long optlong(int n, long l) {
        return this.arg(n).optlong(l);
    }

    public LuaNumber optnumber(int n, LuaNumber luaNumber) {
        return this.arg(n).optnumber(luaNumber);
    }

    public String optjstring(int n, String string) {
        return this.arg(n).optjstring(string);
    }

    public LuaString optstring(int n, LuaString luaString) {
        return this.arg(n).optstring(luaString);
    }

    public LuaTable opttable(int n, LuaTable luaTable) {
        return this.arg(n).opttable(luaTable);
    }

    public LuaThread optthread(int n, LuaThread luaThread) {
        return this.arg(n).optthread(luaThread);
    }

    public Object optuserdata(int n, Object object) {
        return this.arg(n).optuserdata(object);
    }

    public Object optuserdata(int n, Class clazz, Object object) {
        return this.arg(n).optuserdata(clazz, object);
    }

    public LuaValue optvalue(int n, LuaValue luaValue) {
        return n > 0 && n <= this.narg() ? this.arg(n) : luaValue;
    }

    public boolean checkboolean(int n) {
        return this.arg(n).checkboolean();
    }

    public LuaClosure checkclosure(int n) {
        return this.arg(n).checkclosure();
    }

    public double checkdouble(int n) {
        return this.arg(n).checkdouble();
    }

    public LuaFunction checkfunction(int n) {
        return this.arg(n).checkfunction();
    }

    public int checkint(int n) {
        return this.arg(n).checkint();
    }

    public LuaInteger checkinteger(int n) {
        return this.arg(n).checkinteger();
    }

    public long checklong(int n) {
        return this.arg(n).checklong();
    }

    public LuaNumber checknumber(int n) {
        return this.arg(n).checknumber();
    }

    public String checkjstring(int n) {
        return this.arg(n).checkjstring();
    }

    public LuaString checkstring(int n) {
        return this.arg(n).checkstring();
    }

    public LuaTable checktable(int n) {
        return this.arg(n).checktable();
    }

    public LuaThread checkthread(int n) {
        return this.arg(n).checkthread();
    }

    public Object checkuserdata(int n) {
        return this.arg(n).checkuserdata();
    }

    public Object checkuserdata(int n, Class clazz) {
        return this.arg(n).checkuserdata(clazz);
    }

    public LuaValue checkvalue(int n) {
        return n <= this.narg() ? this.arg(n) : LuaValue.argerror(n, "value expected");
    }

    public LuaValue checknotnil(int n) {
        return this.arg(n).checknotnil();
    }

    public void argcheck(boolean bl, int n, String string) {
        if (!bl) {
            LuaValue.argerror(n, string);
        }
    }

    public boolean isnoneornil(int n) {
        return n > this.narg() || this.arg(n).isnil();
    }

    public boolean toboolean(int n) {
        return this.arg(n).toboolean();
    }

    public byte tobyte(int n) {
        return this.arg(n).tobyte();
    }

    public char tochar(int n) {
        return this.arg(n).tochar();
    }

    public double todouble(int n) {
        return this.arg(n).todouble();
    }

    public float tofloat(int n) {
        return this.arg(n).tofloat();
    }

    public int toint(int n) {
        return this.arg(n).toint();
    }

    public long tolong(int n) {
        return this.arg(n).tolong();
    }

    public String tojstring(int n) {
        return this.arg(n).tojstring();
    }

    public short toshort(int n) {
        return this.arg(n).toshort();
    }

    public Object touserdata(int n) {
        return this.arg(n).touserdata();
    }

    public Object touserdata(int n, Class clazz) {
        return this.arg(n).touserdata(clazz);
    }

    public String tojstring() {
        Buffer buffer = new Buffer();
        buffer.append("(");
        int n = this.narg();
        for (int i = 1; i <= n; ++i) {
            if (i > 1) {
                buffer.append(",");
            }
            buffer.append(this.arg(i).tojstring());
        }
        buffer.append(")");
        return buffer.tojstring();
    }

    public String toString() {
        return this.tojstring();
    }

    public abstract Varargs subargs(int var1);

    void copyto(LuaValue[] luaValueArray, int n, int n2) {
        for (int i = 0; i < n2; ++i) {
            luaValueArray[n + i] = this.arg(i + 1);
        }
    }

    public Varargs dealias() {
        int n = this.narg();
        switch (n) {
            case 0: {
                return LuaValue.NONE;
            }
            case 1: {
                return this.arg1();
            }
            case 2: {
                return new PairVarargs(this.arg1(), this.arg(2));
            }
        }
        LuaValue[] luaValueArray = new LuaValue[n];
        this.copyto(luaValueArray, 0, n);
        return new ArrayVarargs(luaValueArray, LuaValue.NONE);
    }

    static final class PairVarargs
    extends Varargs {
        private final LuaValue v1;
        private final Varargs v2;

        PairVarargs(LuaValue luaValue, Varargs varargs) {
            this.v1 = luaValue;
            this.v2 = varargs;
        }

        @Override
        public LuaValue arg(int n) {
            return n == 1 ? this.v1 : this.v2.arg(n - 1);
        }

        @Override
        public int narg() {
            return 1 + this.v2.narg();
        }

        @Override
        public LuaValue arg1() {
            return this.v1;
        }

        @Override
        public Varargs subargs(int n) {
            if (n == 1) {
                return this;
            }
            if (n == 2) {
                return this.v2;
            }
            if (n > 2) {
                return this.v2.subargs(n - 1);
            }
            return LuaValue.argerror(1, "start must be > 0");
        }
    }

    static final class ArrayVarargs
    extends Varargs {
        private final LuaValue[] v;
        private final Varargs r;

        ArrayVarargs(LuaValue[] luaValueArray, Varargs varargs) {
            this.v = luaValueArray;
            this.r = varargs;
        }

        @Override
        public LuaValue arg(int n) {
            return n < 1 ? LuaValue.NIL : (n <= this.v.length ? this.v[n - 1] : this.r.arg(n - this.v.length));
        }

        @Override
        public int narg() {
            return this.v.length + this.r.narg();
        }

        @Override
        public LuaValue arg1() {
            return this.v.length > 0 ? this.v[0] : this.r.arg1();
        }

        @Override
        public Varargs subargs(int n) {
            if (n <= 0) {
                LuaValue.argerror(1, "start must be > 0");
            }
            if (n == 1) {
                return this;
            }
            if (n > this.v.length) {
                return this.r.subargs(n - this.v.length);
            }
            return LuaValue.varargsOf(this.v, n - 1, this.v.length - (n - 1), this.r);
        }

        @Override
        void copyto(LuaValue[] luaValueArray, int n, int n2) {
            int n3 = Math.min(this.v.length, n2);
            System.arraycopy(this.v, 0, luaValueArray, n, n3);
            this.r.copyto(luaValueArray, n + n3, n2 - n3);
        }
    }

    static final class ArrayPartVarargs
    extends Varargs {
        private final int offset;
        private final LuaValue[] v;
        private final int length;
        private final Varargs more;

        ArrayPartVarargs(LuaValue[] luaValueArray, int n, int n2) {
            this.v = luaValueArray;
            this.offset = n;
            this.length = n2;
            this.more = LuaValue.NONE;
        }

        public ArrayPartVarargs(LuaValue[] luaValueArray, int n, int n2, Varargs varargs) {
            this.v = luaValueArray;
            this.offset = n;
            this.length = n2;
            this.more = varargs;
        }

        @Override
        public LuaValue arg(int n) {
            return n < 1 ? LuaValue.NIL : (n <= this.length ? this.v[this.offset + n - 1] : this.more.arg(n - this.length));
        }

        @Override
        public int narg() {
            return this.length + this.more.narg();
        }

        @Override
        public LuaValue arg1() {
            return this.length > 0 ? this.v[this.offset] : this.more.arg1();
        }

        @Override
        public Varargs subargs(int n) {
            if (n <= 0) {
                LuaValue.argerror(1, "start must be > 0");
            }
            if (n == 1) {
                return this;
            }
            if (n > this.length) {
                return this.more.subargs(n - this.length);
            }
            return LuaValue.varargsOf(this.v, this.offset + n - 1, this.length - (n - 1), this.more);
        }

        @Override
        void copyto(LuaValue[] luaValueArray, int n, int n2) {
            int n3 = Math.min(this.length, n2);
            System.arraycopy(this.v, this.offset, luaValueArray, n, n3);
            this.more.copyto(luaValueArray, n + n3, n2 - n3);
        }
    }

    static class SubVarargs
    extends Varargs {
        private final Varargs v;
        private final int start;
        private final int end;

        public SubVarargs(Varargs varargs, int n, int n2) {
            this.v = varargs;
            this.start = n;
            this.end = n2;
        }

        @Override
        public LuaValue arg(int n) {
            return (n += this.start - 1) >= this.start && n <= this.end ? this.v.arg(n) : LuaValue.NIL;
        }

        @Override
        public LuaValue arg1() {
            return this.v.arg(this.start);
        }

        @Override
        public int narg() {
            return this.end + 1 - this.start;
        }

        @Override
        public Varargs subargs(int n) {
            if (n == 1) {
                return this;
            }
            int n2 = this.start + n - 1;
            if (n > 0) {
                if (n2 >= this.end) {
                    return LuaValue.NONE;
                }
                if (n2 == this.end) {
                    return this.v.arg(this.end);
                }
                if (n2 == this.end - 1) {
                    return new PairVarargs(this.v.arg(this.end - 1), this.v.arg(this.end));
                }
                return new SubVarargs(this.v, n2, this.end);
            }
            return new SubVarargs(this.v, n2, this.end);
        }
    }
}

