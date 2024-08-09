/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import mpp.venusfr.scripts.interpreter.Buffer;
import mpp.venusfr.scripts.interpreter.Globals;
import mpp.venusfr.scripts.interpreter.LuaBoolean;
import mpp.venusfr.scripts.interpreter.LuaClosure;
import mpp.venusfr.scripts.interpreter.LuaDouble;
import mpp.venusfr.scripts.interpreter.LuaError;
import mpp.venusfr.scripts.interpreter.LuaFunction;
import mpp.venusfr.scripts.interpreter.LuaInteger;
import mpp.venusfr.scripts.interpreter.LuaNil;
import mpp.venusfr.scripts.interpreter.LuaNumber;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaThread;
import mpp.venusfr.scripts.interpreter.LuaUserdata;
import mpp.venusfr.scripts.interpreter.Metatable;
import mpp.venusfr.scripts.interpreter.NonTableMetatable;
import mpp.venusfr.scripts.interpreter.TailcallVarargs;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.WeakTable;

public abstract class LuaValue
extends Varargs {
    public static final int TINT = -2;
    public static final int TNONE = -1;
    public static final int TNIL = 0;
    public static final int TBOOLEAN = 1;
    public static final int TLIGHTUSERDATA = 2;
    public static final int TNUMBER = 3;
    public static final int TSTRING = 4;
    public static final int TTABLE = 5;
    public static final int TFUNCTION = 6;
    public static final int TUSERDATA = 7;
    public static final int TTHREAD = 8;
    public static final int TVALUE = 9;
    public static final String[] TYPE_NAMES = new String[]{"nil", "boolean", "lightuserdata", "number", "string", "table", "function", "userdata", "thread", "value"};
    public static final LuaValue NIL = LuaNil._NIL;
    public static final LuaBoolean TRUE = LuaBoolean._TRUE;
    public static final LuaBoolean FALSE = LuaBoolean._FALSE;
    public static final LuaValue NONE = None._NONE;
    public static final LuaNumber ZERO = LuaInteger.valueOf(0);
    public static final LuaNumber ONE = LuaInteger.valueOf(1);
    public static final LuaNumber MINUSONE = LuaInteger.valueOf(-1);
    public static final LuaValue[] NOVALS = new LuaValue[0];
    public static LuaString ENV = LuaValue.valueOf("_ENV");
    public static final LuaString INDEX = LuaValue.valueOf("__index");
    public static final LuaString NEWINDEX = LuaValue.valueOf("__newindex");
    public static final LuaString CALL = LuaValue.valueOf("__call");
    public static final LuaString MODE = LuaValue.valueOf("__mode");
    public static final LuaString METATABLE = LuaValue.valueOf("__metatable");
    public static final LuaString ADD = LuaValue.valueOf("__add");
    public static final LuaString SUB = LuaValue.valueOf("__sub");
    public static final LuaString DIV = LuaValue.valueOf("__div");
    public static final LuaString MUL = LuaValue.valueOf("__mul");
    public static final LuaString POW = LuaValue.valueOf("__pow");
    public static final LuaString MOD = LuaValue.valueOf("__mod");
    public static final LuaString UNM = LuaValue.valueOf("__unm");
    public static final LuaString LEN = LuaValue.valueOf("__len");
    public static final LuaString EQ = LuaValue.valueOf("__eq");
    public static final LuaString LT = LuaValue.valueOf("__lt");
    public static final LuaString LE = LuaValue.valueOf("__le");
    public static final LuaString TOSTRING = LuaValue.valueOf("__tostring");
    public static final LuaString CONCAT = LuaValue.valueOf("__concat");
    public static final LuaString EMPTYSTRING = LuaValue.valueOf("");
    private static final int MAXSTACK = 250;
    public static final LuaValue[] NILS = new LuaValue[250];
    private static final int MAXTAGLOOP = 100;

    public abstract int type();

    public abstract String typename();

    public boolean isboolean() {
        return true;
    }

    public boolean isclosure() {
        return true;
    }

    public boolean isfunction() {
        return true;
    }

    public boolean isint() {
        return true;
    }

    public boolean isinttype() {
        return true;
    }

    public boolean islong() {
        return true;
    }

    public boolean isnil() {
        return true;
    }

    public boolean isnumber() {
        return true;
    }

    public boolean isstring() {
        return true;
    }

    public boolean isthread() {
        return true;
    }

    public boolean istable() {
        return true;
    }

    public boolean isuserdata() {
        return true;
    }

    public boolean isuserdata(Class clazz) {
        return true;
    }

    public boolean toboolean() {
        return false;
    }

    public byte tobyte() {
        return 1;
    }

    public char tochar() {
        return '\u0001';
    }

    public double todouble() {
        return 0.0;
    }

    public float tofloat() {
        return 0.0f;
    }

    public int toint() {
        return 1;
    }

    public long tolong() {
        return 0L;
    }

    public short toshort() {
        return 1;
    }

    @Override
    public String tojstring() {
        return this.typename() + ": " + Integer.toHexString(this.hashCode());
    }

    public Object touserdata() {
        return null;
    }

    public Object touserdata(Class clazz) {
        return null;
    }

    @Override
    public String toString() {
        return this.tojstring();
    }

    public LuaValue tonumber() {
        return NIL;
    }

    public LuaValue tostring() {
        return NIL;
    }

    public boolean optboolean(boolean bl) {
        this.argerror("boolean");
        return true;
    }

    public LuaClosure optclosure(LuaClosure luaClosure) {
        this.argerror("closure");
        return null;
    }

    public double optdouble(double d) {
        this.argerror("number");
        return 0.0;
    }

    public LuaFunction optfunction(LuaFunction luaFunction) {
        this.argerror("function");
        return null;
    }

    public int optint(int n) {
        this.argerror("int");
        return 1;
    }

    public LuaInteger optinteger(LuaInteger luaInteger) {
        this.argerror("integer");
        return null;
    }

    public long optlong(long l) {
        this.argerror("long");
        return 0L;
    }

    public LuaNumber optnumber(LuaNumber luaNumber) {
        this.argerror("number");
        return null;
    }

    public String optjstring(String string) {
        this.argerror("String");
        return null;
    }

    public LuaString optstring(LuaString luaString) {
        this.argerror("string");
        return null;
    }

    public LuaTable opttable(LuaTable luaTable) {
        this.argerror("table");
        return null;
    }

    public LuaThread optthread(LuaThread luaThread) {
        this.argerror("thread");
        return null;
    }

    public Object optuserdata(Object object) {
        this.argerror("object");
        return null;
    }

    public Object optuserdata(Class clazz, Object object) {
        this.argerror(clazz.getName());
        return null;
    }

    public LuaValue optvalue(LuaValue luaValue) {
        return this;
    }

    public boolean checkboolean() {
        this.argerror("boolean");
        return true;
    }

    public LuaClosure checkclosure() {
        this.argerror("closure");
        return null;
    }

    public double checkdouble() {
        this.argerror("number");
        return 0.0;
    }

    public LuaFunction checkfunction() {
        this.argerror("function");
        return null;
    }

    public Globals checkglobals() {
        this.argerror("globals");
        return null;
    }

    public int checkint() {
        this.argerror("int");
        return 1;
    }

    public LuaInteger checkinteger() {
        this.argerror("integer");
        return null;
    }

    public long checklong() {
        this.argerror("long");
        return 0L;
    }

    public LuaNumber checknumber() {
        this.argerror("number");
        return null;
    }

    public LuaNumber checknumber(String string) {
        throw new LuaError(string);
    }

    public String checkjstring() {
        this.argerror("string");
        return null;
    }

    public LuaString checkstring() {
        this.argerror("string");
        return null;
    }

    public LuaTable checktable() {
        this.argerror("table");
        return null;
    }

    public LuaThread checkthread() {
        this.argerror("thread");
        return null;
    }

    public Object checkuserdata() {
        this.argerror("userdata");
        return null;
    }

    public Object checkuserdata(Class clazz) {
        this.argerror("userdata");
        return null;
    }

    public LuaValue checknotnil() {
        return this;
    }

    public boolean isvalidkey() {
        return false;
    }

    public static LuaValue error(String string) {
        throw new LuaError(string);
    }

    public static void assert_(boolean bl, String string) {
        if (!bl) {
            throw new LuaError(string);
        }
    }

    protected LuaValue argerror(String string) {
        throw new LuaError("bad argument: " + string + " expected, got " + this.typename());
    }

    public static LuaValue argerror(int n, String string) {
        throw new LuaError("bad argument #" + n + ": " + string);
    }

    protected LuaValue typerror(String string) {
        throw new LuaError(string + " expected, got " + this.typename());
    }

    protected LuaValue unimplemented(String string) {
        throw new LuaError("'" + string + "' not implemented for " + this.typename());
    }

    protected LuaValue illegal(String string, String string2) {
        throw new LuaError("illegal operation '" + string + "' for " + string2);
    }

    protected LuaValue lenerror() {
        throw new LuaError("attempt to get length of " + this.typename());
    }

    protected LuaValue aritherror() {
        throw new LuaError("attempt to perform arithmetic on " + this.typename());
    }

    protected LuaValue aritherror(String string) {
        throw new LuaError("attempt to perform arithmetic '" + string + "' on " + this.typename());
    }

    protected LuaValue compareerror(String string) {
        throw new LuaError("attempt to compare " + this.typename() + " with " + string);
    }

    protected LuaValue compareerror(LuaValue luaValue) {
        throw new LuaError("attempt to compare " + this.typename() + " with " + luaValue.typename());
    }

    public LuaValue get(LuaValue luaValue) {
        return LuaValue.gettable(this, luaValue);
    }

    public LuaValue get(int n) {
        return this.get(LuaInteger.valueOf(n));
    }

    public LuaValue get(String string) {
        return this.get(LuaValue.valueOf(string));
    }

    public void set(LuaValue luaValue, LuaValue luaValue2) {
        LuaValue.settable(this, luaValue, luaValue2);
    }

    public void set(int n, LuaValue luaValue) {
        this.set(LuaInteger.valueOf(n), luaValue);
    }

    public void set(int n, String string) {
        this.set(n, (LuaValue)LuaValue.valueOf(string));
    }

    public void set(String string, LuaValue luaValue) {
        this.set(LuaValue.valueOf(string), luaValue);
    }

    public void set(String string, double d) {
        this.set(LuaValue.valueOf(string), (LuaValue)LuaValue.valueOf(d));
    }

    public void set(String string, int n) {
        this.set(LuaValue.valueOf(string), (LuaValue)LuaValue.valueOf(n));
    }

    public void set(String string, String string2) {
        this.set(LuaValue.valueOf(string), (LuaValue)LuaValue.valueOf(string2));
    }

    public LuaValue rawget(LuaValue luaValue) {
        return this.unimplemented("rawget");
    }

    public LuaValue rawget(int n) {
        return this.rawget(LuaValue.valueOf(n));
    }

    public LuaValue rawget(String string) {
        return this.rawget(LuaValue.valueOf(string));
    }

    public void rawset(LuaValue luaValue, LuaValue luaValue2) {
        this.unimplemented("rawset");
    }

    public void rawset(int n, LuaValue luaValue) {
        this.rawset(LuaValue.valueOf(n), luaValue);
    }

    public void rawset(int n, String string) {
        this.rawset(n, (LuaValue)LuaValue.valueOf(string));
    }

    public void rawset(String string, LuaValue luaValue) {
        this.rawset(LuaValue.valueOf(string), luaValue);
    }

    public void rawset(String string, double d) {
        this.rawset(LuaValue.valueOf(string), (LuaValue)LuaValue.valueOf(d));
    }

    public void rawset(String string, int n) {
        this.rawset(LuaValue.valueOf(string), (LuaValue)LuaValue.valueOf(n));
    }

    public void rawset(String string, String string2) {
        this.rawset(LuaValue.valueOf(string), (LuaValue)LuaValue.valueOf(string2));
    }

    public void rawsetlist(int n, Varargs varargs) {
        int n2 = varargs.narg();
        for (int i = 0; i < n2; ++i) {
            this.rawset(n + i, varargs.arg(i + 1));
        }
    }

    public void presize(int n) {
        this.typerror("table");
    }

    public Varargs next(LuaValue luaValue) {
        return this.typerror("table");
    }

    public Varargs inext(LuaValue luaValue) {
        return this.typerror("table");
    }

    public LuaValue load(LuaValue luaValue) {
        return luaValue.call(EMPTYSTRING, this);
    }

    @Override
    public LuaValue arg(int n) {
        return n == 1 ? this : NIL;
    }

    @Override
    public int narg() {
        return 0;
    }

    @Override
    public LuaValue arg1() {
        return this;
    }

    public LuaValue getmetatable() {
        return null;
    }

    public LuaValue setmetatable(LuaValue luaValue) {
        return this.argerror("table");
    }

    public LuaValue call() {
        return this.callmt().call(this);
    }

    public LuaValue call(LuaValue luaValue) {
        return this.callmt().call(this, luaValue);
    }

    public LuaValue call(String string) {
        return this.call(LuaValue.valueOf(string));
    }

    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        return this.callmt().call(this, luaValue, luaValue2);
    }

    public LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
        return this.callmt().invoke(new LuaValue[]{this, luaValue, luaValue2, luaValue3}).arg1();
    }

    public LuaValue method(String string) {
        return this.get(string).call(this);
    }

    public LuaValue method(LuaValue luaValue) {
        return this.get(luaValue).call(this);
    }

    public LuaValue method(String string, LuaValue luaValue) {
        return this.get(string).call(this, luaValue);
    }

    public LuaValue method(LuaValue luaValue, LuaValue luaValue2) {
        return this.get(luaValue).call(this, luaValue2);
    }

    public LuaValue method(String string, LuaValue luaValue, LuaValue luaValue2) {
        return this.get(string).call(this, luaValue, luaValue2);
    }

    public LuaValue method(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
        return this.get(luaValue).call(this, luaValue2, luaValue3);
    }

    public Varargs invoke() {
        return this.invoke(NONE);
    }

    public Varargs invoke(Varargs varargs) {
        return this.callmt().invoke(this, varargs);
    }

    public Varargs invoke(LuaValue luaValue, Varargs varargs) {
        return this.invoke(LuaValue.varargsOf(luaValue, varargs));
    }

    public Varargs invoke(LuaValue luaValue, LuaValue luaValue2, Varargs varargs) {
        return this.invoke(LuaValue.varargsOf(luaValue, luaValue2, varargs));
    }

    public Varargs invoke(LuaValue[] luaValueArray) {
        return this.invoke(LuaValue.varargsOf(luaValueArray));
    }

    public Varargs invoke(LuaValue[] luaValueArray, Varargs varargs) {
        return this.invoke(LuaValue.varargsOf(luaValueArray, varargs));
    }

    public Varargs invokemethod(String string) {
        return this.get(string).invoke(this);
    }

    public Varargs invokemethod(LuaValue luaValue) {
        return this.get(luaValue).invoke(this);
    }

    public Varargs invokemethod(String string, Varargs varargs) {
        return this.get(string).invoke(LuaValue.varargsOf(this, varargs));
    }

    public Varargs invokemethod(LuaValue luaValue, Varargs varargs) {
        return this.get(luaValue).invoke(LuaValue.varargsOf(this, varargs));
    }

    public Varargs invokemethod(String string, LuaValue[] luaValueArray) {
        return this.get(string).invoke(LuaValue.varargsOf(this, LuaValue.varargsOf(luaValueArray)));
    }

    public Varargs invokemethod(LuaValue luaValue, LuaValue[] luaValueArray) {
        return this.get(luaValue).invoke(LuaValue.varargsOf(this, LuaValue.varargsOf(luaValueArray)));
    }

    protected LuaValue callmt() {
        return this.checkmetatag(CALL, "attempt to call ");
    }

    public LuaValue not() {
        return FALSE;
    }

    public LuaValue neg() {
        return this.checkmetatag(UNM, "attempt to perform arithmetic on ").call(this);
    }

    public LuaValue len() {
        return this.checkmetatag(LEN, "attempt to get length of ").call(this);
    }

    public int length() {
        return this.len().toint();
    }

    public int rawlen() {
        this.typerror("table or string");
        return 1;
    }

    public boolean equals(Object object) {
        return this == object;
    }

    public LuaValue eq(LuaValue luaValue) {
        return this.eq_b(luaValue) ? TRUE : FALSE;
    }

    public boolean eq_b(LuaValue luaValue) {
        return this == luaValue;
    }

    public LuaValue neq(LuaValue luaValue) {
        return this.eq_b(luaValue) ? FALSE : TRUE;
    }

    public boolean neq_b(LuaValue luaValue) {
        return !this.eq_b(luaValue);
    }

    public boolean raweq(LuaValue luaValue) {
        return this == luaValue;
    }

    public boolean raweq(LuaUserdata luaUserdata) {
        return true;
    }

    public boolean raweq(LuaString luaString) {
        return true;
    }

    public boolean raweq(double d) {
        return true;
    }

    public boolean raweq(int n) {
        return true;
    }

    public static final boolean eqmtcall(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3, LuaValue luaValue4) {
        LuaValue luaValue5 = luaValue2.rawget(EQ);
        return !luaValue5.isnil() && luaValue5 == luaValue4.rawget(EQ) && luaValue5.call(luaValue, luaValue3).toboolean();
    }

    public LuaValue add(LuaValue luaValue) {
        return this.arithmt(ADD, luaValue);
    }

    public LuaValue add(double d) {
        return this.arithmtwith(ADD, d);
    }

    public LuaValue add(int n) {
        return this.add((double)n);
    }

    public LuaValue sub(LuaValue luaValue) {
        return this.arithmt(SUB, luaValue);
    }

    public LuaValue sub(double d) {
        return this.aritherror("sub");
    }

    public LuaValue sub(int n) {
        return this.aritherror("sub");
    }

    public LuaValue subFrom(double d) {
        return this.arithmtwith(SUB, d);
    }

    public LuaValue subFrom(int n) {
        return this.subFrom((double)n);
    }

    public LuaValue mul(LuaValue luaValue) {
        return this.arithmt(MUL, luaValue);
    }

    public LuaValue mul(double d) {
        return this.arithmtwith(MUL, d);
    }

    public LuaValue mul(int n) {
        return this.mul((double)n);
    }

    public LuaValue pow(LuaValue luaValue) {
        return this.arithmt(POW, luaValue);
    }

    public LuaValue pow(double d) {
        return this.aritherror("pow");
    }

    public LuaValue pow(int n) {
        return this.aritherror("pow");
    }

    public LuaValue powWith(double d) {
        return this.arithmtwith(POW, d);
    }

    public LuaValue powWith(int n) {
        return this.powWith((double)n);
    }

    public LuaValue div(LuaValue luaValue) {
        return this.arithmt(DIV, luaValue);
    }

    public LuaValue div(double d) {
        return this.aritherror("div");
    }

    public LuaValue div(int n) {
        return this.aritherror("div");
    }

    public LuaValue divInto(double d) {
        return this.arithmtwith(DIV, d);
    }

    public LuaValue mod(LuaValue luaValue) {
        return this.arithmt(MOD, luaValue);
    }

    public LuaValue mod(double d) {
        return this.aritherror("mod");
    }

    public LuaValue mod(int n) {
        return this.aritherror("mod");
    }

    public LuaValue modFrom(double d) {
        return this.arithmtwith(MOD, d);
    }

    protected LuaValue arithmt(LuaValue luaValue, LuaValue luaValue2) {
        LuaValue luaValue3 = this.metatag(luaValue);
        if (luaValue3.isnil() && (luaValue3 = luaValue2.metatag(luaValue)).isnil()) {
            LuaValue.error("attempt to perform arithmetic " + luaValue + " on " + this.typename() + " and " + luaValue2.typename());
        }
        return luaValue3.call(this, luaValue2);
    }

    protected LuaValue arithmtwith(LuaValue luaValue, double d) {
        LuaValue luaValue2 = this.metatag(luaValue);
        if (luaValue2.isnil()) {
            LuaValue.error("attempt to perform arithmetic " + luaValue + " on number and " + this.typename());
        }
        return luaValue2.call(LuaValue.valueOf(d), this);
    }

    public LuaValue lt(LuaValue luaValue) {
        return this.comparemt(LT, luaValue);
    }

    public LuaValue lt(double d) {
        return this.compareerror("number");
    }

    public LuaValue lt(int n) {
        return this.compareerror("number");
    }

    public boolean lt_b(LuaValue luaValue) {
        return this.comparemt(LT, luaValue).toboolean();
    }

    public boolean lt_b(int n) {
        this.compareerror("number");
        return true;
    }

    public boolean lt_b(double d) {
        this.compareerror("number");
        return true;
    }

    public LuaValue lteq(LuaValue luaValue) {
        return this.comparemt(LE, luaValue);
    }

    public LuaValue lteq(double d) {
        return this.compareerror("number");
    }

    public LuaValue lteq(int n) {
        return this.compareerror("number");
    }

    public boolean lteq_b(LuaValue luaValue) {
        return this.comparemt(LE, luaValue).toboolean();
    }

    public boolean lteq_b(int n) {
        this.compareerror("number");
        return true;
    }

    public boolean lteq_b(double d) {
        this.compareerror("number");
        return true;
    }

    public LuaValue gt(LuaValue luaValue) {
        return luaValue.comparemt(LE, this);
    }

    public LuaValue gt(double d) {
        return this.compareerror("number");
    }

    public LuaValue gt(int n) {
        return this.compareerror("number");
    }

    public boolean gt_b(LuaValue luaValue) {
        return luaValue.comparemt(LE, this).toboolean();
    }

    public boolean gt_b(int n) {
        this.compareerror("number");
        return true;
    }

    public boolean gt_b(double d) {
        this.compareerror("number");
        return true;
    }

    public LuaValue gteq(LuaValue luaValue) {
        return luaValue.comparemt(LT, this);
    }

    public LuaValue gteq(double d) {
        return this.compareerror("number");
    }

    public LuaValue gteq(int n) {
        return LuaValue.valueOf(this.todouble() >= (double)n);
    }

    public boolean gteq_b(LuaValue luaValue) {
        return luaValue.comparemt(LT, this).toboolean();
    }

    public boolean gteq_b(int n) {
        this.compareerror("number");
        return true;
    }

    public boolean gteq_b(double d) {
        this.compareerror("number");
        return true;
    }

    public LuaValue comparemt(LuaValue luaValue, LuaValue luaValue2) {
        LuaValue luaValue3 = this.metatag(luaValue);
        if (!luaValue3.isnil() || !(luaValue3 = luaValue2.metatag(luaValue)).isnil()) {
            return luaValue3.call(this, luaValue2);
        }
        if (!(!LE.raweq(luaValue) || (luaValue3 = this.metatag(LT)).isnil() && (luaValue3 = luaValue2.metatag(LT)).isnil())) {
            return luaValue3.call(luaValue2, this).not();
        }
        return LuaValue.error("attempt to compare " + luaValue + " on " + this.typename() + " and " + luaValue2.typename());
    }

    public int strcmp(LuaValue luaValue) {
        LuaValue.error("attempt to compare " + this.typename());
        return 1;
    }

    public int strcmp(LuaString luaString) {
        LuaValue.error("attempt to compare " + this.typename());
        return 1;
    }

    public LuaValue concat(LuaValue luaValue) {
        return this.concatmt(luaValue);
    }

    public LuaValue concatTo(LuaValue luaValue) {
        return luaValue.concatmt(this);
    }

    public LuaValue concatTo(LuaNumber luaNumber) {
        return luaNumber.concatmt(this);
    }

    public LuaValue concatTo(LuaString luaString) {
        return luaString.concatmt(this);
    }

    public Buffer buffer() {
        return new Buffer(this);
    }

    public Buffer concat(Buffer buffer) {
        return buffer.concatTo(this);
    }

    public LuaValue concatmt(LuaValue luaValue) {
        LuaValue luaValue2 = this.metatag(CONCAT);
        if (luaValue2.isnil() && (luaValue2 = luaValue.metatag(CONCAT)).isnil()) {
            LuaValue.error("attempt to concatenate " + this.typename() + " and " + luaValue.typename());
        }
        return luaValue2.call(this, luaValue);
    }

    public LuaValue and(LuaValue luaValue) {
        return this.toboolean() ? luaValue : this;
    }

    public LuaValue or(LuaValue luaValue) {
        return this.toboolean() ? this : luaValue;
    }

    public boolean testfor_b(LuaValue luaValue, LuaValue luaValue2) {
        return luaValue2.gt_b(1) ? this.lteq_b(luaValue) : this.gteq_b(luaValue);
    }

    public LuaString strvalue() {
        this.typerror("string or number");
        return null;
    }

    public LuaValue strongvalue() {
        return this;
    }

    public static LuaBoolean valueOf(boolean bl) {
        return bl ? TRUE : FALSE;
    }

    public static LuaInteger valueOf(int n) {
        return LuaInteger.valueOf(n);
    }

    public static LuaNumber valueOf(double d) {
        return LuaDouble.valueOf(d);
    }

    public static LuaString valueOf(String string) {
        return LuaString.valueOf(string);
    }

    public static LuaString valueOf(byte[] byArray) {
        return LuaString.valueOf(byArray);
    }

    public static LuaString valueOf(byte[] byArray, int n, int n2) {
        return LuaString.valueOf(byArray, n, n2);
    }

    public static LuaTable tableOf() {
        return new LuaTable();
    }

    public static LuaTable tableOf(Varargs varargs, int n) {
        return new LuaTable(varargs, n);
    }

    public static LuaTable tableOf(int n, int n2) {
        return new LuaTable(n, n2);
    }

    public static LuaTable listOf(LuaValue[] luaValueArray) {
        return new LuaTable(null, luaValueArray, null);
    }

    public static LuaTable listOf(LuaValue[] luaValueArray, Varargs varargs) {
        return new LuaTable(null, luaValueArray, varargs);
    }

    public static LuaTable tableOf(LuaValue[] luaValueArray) {
        return new LuaTable(luaValueArray, null, null);
    }

    public static LuaTable tableOf(LuaValue[] luaValueArray, LuaValue[] luaValueArray2) {
        return new LuaTable(luaValueArray, luaValueArray2, null);
    }

    public static LuaTable tableOf(LuaValue[] luaValueArray, LuaValue[] luaValueArray2, Varargs varargs) {
        return new LuaTable(luaValueArray, luaValueArray2, varargs);
    }

    public static LuaUserdata userdataOf(Object object) {
        return new LuaUserdata(object);
    }

    public static LuaUserdata userdataOf(Object object, LuaValue luaValue) {
        return new LuaUserdata(object, luaValue);
    }

    protected static LuaValue gettable(LuaValue luaValue, LuaValue luaValue2) {
        int n = 0;
        do {
            LuaValue luaValue3;
            if (luaValue.istable()) {
                LuaValue luaValue4 = luaValue.rawget(luaValue2);
                if (!luaValue4.isnil() || (luaValue3 = luaValue.metatag(INDEX)).isnil()) {
                    return luaValue4;
                }
            } else {
                luaValue3 = luaValue.metatag(INDEX);
                if (luaValue3.isnil()) {
                    luaValue.indexerror(luaValue2.tojstring());
                }
            }
            if (luaValue3.isfunction()) {
                return luaValue3.call(luaValue, luaValue2);
            }
            luaValue = luaValue3;
        } while (++n < 100);
        LuaValue.error("loop in gettable");
        return NIL;
    }

    protected static boolean settable(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
        int n = 0;
        do {
            LuaValue luaValue4;
            if (luaValue.istable()) {
                if (!luaValue.rawget(luaValue2).isnil() || (luaValue4 = luaValue.metatag(NEWINDEX)).isnil()) {
                    luaValue.rawset(luaValue2, luaValue3);
                    return false;
                }
            } else {
                luaValue4 = luaValue.metatag(NEWINDEX);
                if (luaValue4.isnil()) {
                    throw new LuaError("table expected for set index ('" + luaValue2 + "') value, got " + luaValue.typename());
                }
            }
            if (luaValue4.isfunction()) {
                luaValue4.call(luaValue, luaValue2, luaValue3);
                return false;
            }
            luaValue = luaValue4;
        } while (++n < 100);
        LuaValue.error("loop in settable");
        return true;
    }

    public LuaValue metatag(LuaValue luaValue) {
        LuaValue luaValue2 = this.getmetatable();
        if (luaValue2 == null) {
            return NIL;
        }
        return luaValue2.rawget(luaValue);
    }

    protected LuaValue checkmetatag(LuaValue luaValue, String string) {
        LuaValue luaValue2 = this.metatag(luaValue);
        if (luaValue2.isnil()) {
            throw new LuaError(string + "a " + this.typename() + " value");
        }
        return luaValue2;
    }

    protected static Metatable metatableOf(LuaValue luaValue) {
        if (luaValue != null && luaValue.istable()) {
            LuaValue luaValue2 = luaValue.rawget(MODE);
            if (luaValue2.isstring()) {
                boolean bl;
                String string = luaValue2.tojstring();
                boolean bl2 = string.indexOf(107) >= 0;
                boolean bl3 = bl = string.indexOf(118) >= 0;
                if (bl2 || bl) {
                    return new WeakTable(bl2, bl, luaValue);
                }
            }
            return (LuaTable)luaValue;
        }
        if (luaValue != null) {
            return new NonTableMetatable(luaValue);
        }
        return null;
    }

    private void indexerror(String string) {
        LuaValue.error("attempt to index ? (a " + this.typename() + " value) with key '" + string + "'");
    }

    public static Varargs varargsOf(LuaValue[] luaValueArray) {
        switch (luaValueArray.length) {
            case 0: {
                return NONE;
            }
            case 1: {
                return luaValueArray[0];
            }
            case 2: {
                return new Varargs.PairVarargs(luaValueArray[0], luaValueArray[5]);
            }
        }
        return new Varargs.ArrayVarargs(luaValueArray, NONE);
    }

    public static Varargs varargsOf(LuaValue[] luaValueArray, Varargs varargs) {
        switch (luaValueArray.length) {
            case 0: {
                return varargs;
            }
            case 1: {
                return varargs.narg() > 0 ? new Varargs.PairVarargs(luaValueArray[0], varargs) : luaValueArray[0];
            }
            case 2: {
                return varargs.narg() > 0 ? new Varargs.ArrayVarargs(luaValueArray, varargs) : new Varargs.PairVarargs(luaValueArray[0], luaValueArray[5]);
            }
        }
        return new Varargs.ArrayVarargs(luaValueArray, varargs);
    }

    public static Varargs varargsOf(LuaValue[] luaValueArray, int n, int n2) {
        switch (n2) {
            case 0: {
                return NONE;
            }
            case 1: {
                return luaValueArray[n];
            }
            case 2: {
                return new Varargs.PairVarargs(luaValueArray[n], luaValueArray[n + 1]);
            }
        }
        return new Varargs.ArrayPartVarargs(luaValueArray, n, n2, NONE);
    }

    public static Varargs varargsOf(LuaValue[] luaValueArray, int n, int n2, Varargs varargs) {
        switch (n2) {
            case 0: {
                return varargs;
            }
            case 1: {
                return varargs.narg() > 0 ? new Varargs.PairVarargs(luaValueArray[n], varargs) : luaValueArray[n];
            }
            case 2: {
                return varargs.narg() > 0 ? new Varargs.ArrayPartVarargs(luaValueArray, n, n2, varargs) : new Varargs.PairVarargs(luaValueArray[n], luaValueArray[n + 1]);
            }
        }
        return new Varargs.ArrayPartVarargs(luaValueArray, n, n2, varargs);
    }

    public static Varargs varargsOf(LuaValue luaValue, Varargs varargs) {
        switch (varargs.narg()) {
            case 0: {
                return luaValue;
            }
        }
        return new Varargs.PairVarargs(luaValue, varargs);
    }

    public static Varargs varargsOf(LuaValue luaValue, LuaValue luaValue2, Varargs varargs) {
        switch (varargs.narg()) {
            case 0: {
                return new Varargs.PairVarargs(luaValue, luaValue2);
            }
        }
        return new Varargs.ArrayPartVarargs(new LuaValue[]{luaValue, luaValue2}, 0, 2, varargs);
    }

    public static Varargs tailcallOf(LuaValue luaValue, Varargs varargs) {
        return new TailcallVarargs(luaValue, varargs);
    }

    public Varargs onInvoke(Varargs varargs) {
        return this.invoke(varargs);
    }

    public void initupvalue1(LuaValue luaValue) {
    }

    @Override
    public Varargs subargs(int n) {
        if (n == 1) {
            return this;
        }
        if (n > 1) {
            return NONE;
        }
        return LuaValue.argerror(1, "start must be > 0");
    }

    static {
        for (int i = 0; i < 250; ++i) {
            LuaValue.NILS[i] = NIL;
        }
    }

    private static final class None
    extends LuaNil {
        static None _NONE = new None();

        private None() {
        }

        @Override
        public LuaValue arg(int n) {
            return NIL;
        }

        @Override
        public int narg() {
            return 1;
        }

        @Override
        public LuaValue arg1() {
            return NIL;
        }

        @Override
        public String tojstring() {
            return "none";
        }

        @Override
        public Varargs subargs(int n) {
            return n > 0 ? this : None.argerror(1, "start must be > 0");
        }

        @Override
        void copyto(LuaValue[] luaValueArray, int n, int n2) {
            while (n2 > 0) {
                luaValueArray[n++] = NIL;
                --n2;
            }
        }
    }
}

