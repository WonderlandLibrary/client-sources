/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.lib;

import mpp.venusfr.scripts.interpreter.Globals;
import mpp.venusfr.scripts.interpreter.Lua;
import mpp.venusfr.scripts.interpreter.LuaBoolean;
import mpp.venusfr.scripts.interpreter.LuaClosure;
import mpp.venusfr.scripts.interpreter.LuaError;
import mpp.venusfr.scripts.interpreter.LuaFunction;
import mpp.venusfr.scripts.interpreter.LuaNil;
import mpp.venusfr.scripts.interpreter.LuaNumber;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaThread;
import mpp.venusfr.scripts.interpreter.LuaUserdata;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Print;
import mpp.venusfr.scripts.interpreter.Prototype;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.lib.LibFunction;
import mpp.venusfr.scripts.interpreter.lib.TwoArgFunction;
import mpp.venusfr.scripts.interpreter.lib.VarArgFunction;
import mpp.venusfr.scripts.interpreter.lib.ZeroArgFunction;

public class DebugLib
extends TwoArgFunction {
    public static boolean CALLS;
    public static boolean TRACE;
    static final LuaString LUA;
    private static final LuaString QMARK;
    private static final LuaString CALL;
    private static final LuaString LINE;
    private static final LuaString COUNT;
    private static final LuaString RETURN;
    static final LuaString FUNC;
    static final LuaString ISTAILCALL;
    static final LuaString ISVARARG;
    static final LuaString NUPS;
    static final LuaString NPARAMS;
    static final LuaString NAME;
    static final LuaString NAMEWHAT;
    static final LuaString WHAT;
    static final LuaString SOURCE;
    static final LuaString SHORT_SRC;
    static final LuaString LINEDEFINED;
    static final LuaString LASTLINEDEFINED;
    static final LuaString CURRENTLINE;
    static final LuaString ACTIVELINES;
    Globals globals;

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        this.globals = luaValue2.checkglobals();
        this.globals.debuglib = this;
        LuaTable luaTable = new LuaTable();
        luaTable.set("debug", (LuaValue)new debug());
        luaTable.set("gethook", (LuaValue)new gethook(this));
        luaTable.set("getinfo", (LuaValue)new getinfo(this));
        luaTable.set("getlocal", (LuaValue)new getlocal(this));
        luaTable.set("getmetatable", (LuaValue)new getmetatable());
        luaTable.set("getregistry", (LuaValue)new getregistry(this));
        luaTable.set("getupvalue", (LuaValue)new getupvalue());
        luaTable.set("getuservalue", (LuaValue)new getuservalue());
        luaTable.set("sethook", (LuaValue)new sethook(this));
        luaTable.set("setlocal", (LuaValue)new setlocal(this));
        luaTable.set("setmetatable", (LuaValue)new setmetatable());
        luaTable.set("setupvalue", (LuaValue)new setupvalue());
        luaTable.set("setuservalue", (LuaValue)new setuservalue());
        luaTable.set("traceback", (LuaValue)new traceback(this));
        luaTable.set("upvalueid", (LuaValue)new upvalueid());
        luaTable.set("upvaluejoin", (LuaValue)new upvaluejoin());
        luaValue2.set("debug", (LuaValue)luaTable);
        if (!luaValue2.get("package").isnil()) {
            luaValue2.get("package").get("loaded").set("debug", (LuaValue)luaTable);
        }
        return luaTable;
    }

    public void onCall(LuaFunction luaFunction) {
        LuaThread.State state = this.globals.running.state;
        if (state.inhook) {
            return;
        }
        this.callstack().onCall(luaFunction);
        if (state.hookcall) {
            this.callHook(state, CALL, NIL);
        }
    }

    public void onCall(LuaClosure luaClosure, Varargs varargs, LuaValue[] luaValueArray) {
        LuaThread.State state = this.globals.running.state;
        if (state.inhook) {
            return;
        }
        this.callstack().onCall(luaClosure, varargs, luaValueArray);
        if (state.hookcall) {
            this.callHook(state, CALL, NIL);
        }
    }

    public void onInstruction(int n, Varargs varargs, int n2) {
        int n3;
        LuaThread.State state = this.globals.running.state;
        if (state.inhook) {
            return;
        }
        this.callstack().onInstruction(n, varargs, n2);
        if (state.hookfunc == null) {
            return;
        }
        if (state.hookcount > 0 && ++state.bytecodes % state.hookcount == 0) {
            this.callHook(state, COUNT, NIL);
        }
        if (state.hookline && (n3 = this.callstack().currentline()) != state.lastline) {
            state.lastline = n3;
            this.callHook(state, LINE, LuaValue.valueOf(n3));
        }
    }

    public void onReturn() {
        LuaThread.State state = this.globals.running.state;
        if (state.inhook) {
            return;
        }
        this.callstack().onReturn();
        if (state.hookrtrn) {
            this.callHook(state, RETURN, NIL);
        }
    }

    public String traceback(int n) {
        return this.callstack().traceback(n);
    }

    public CallFrame getCallFrame(int n) {
        return this.callstack().getCallFrame(n);
    }

    void callHook(LuaThread.State state, LuaValue luaValue, LuaValue luaValue2) {
        if (state.inhook || state.hookfunc == null) {
            return;
        }
        state.inhook = true;
        try {
            state.hookfunc.call(luaValue, luaValue2);
        } catch (LuaError luaError) {
            throw luaError;
        } catch (RuntimeException runtimeException) {
            throw new LuaError(runtimeException);
        } finally {
            state.inhook = false;
        }
    }

    CallStack callstack() {
        return this.callstack(this.globals.running);
    }

    CallStack callstack(LuaThread luaThread) {
        if (luaThread.callstack == null) {
            luaThread.callstack = new CallStack();
        }
        return (CallStack)luaThread.callstack;
    }

    static LuaString findupvalue(LuaClosure luaClosure, int n) {
        if (luaClosure.upValues != null && n > 0 && n <= luaClosure.upValues.length) {
            if (luaClosure.p.upvalues != null && n <= luaClosure.p.upvalues.length) {
                return luaClosure.p.upvalues[n - 1].name;
            }
            return LuaString.valueOf("." + n);
        }
        return null;
    }

    static void lua_assert(boolean bl) {
        if (!bl) {
            throw new RuntimeException("lua_assert failed");
        }
    }

    static NameWhat getfuncname(CallFrame callFrame) {
        LuaString luaString;
        if (!callFrame.f.isclosure()) {
            return new NameWhat(callFrame.f.classnamestub(), "Java");
        }
        Prototype prototype = callFrame.f.checkclosure().p;
        int n = callFrame.pc;
        int n2 = prototype.code[n];
        switch (Lua.GET_OPCODE(n2)) {
            case 29: 
            case 30: {
                return DebugLib.getobjname(prototype, n, Lua.GETARG_A(n2));
            }
            case 34: {
                return new NameWhat("(for iterator)", "(for iterator");
            }
            case 6: 
            case 7: 
            case 12: {
                luaString = LuaValue.INDEX;
                break;
            }
            case 8: 
            case 10: {
                luaString = LuaValue.NEWINDEX;
                break;
            }
            case 24: {
                luaString = LuaValue.EQ;
                break;
            }
            case 13: {
                luaString = LuaValue.ADD;
                break;
            }
            case 14: {
                luaString = LuaValue.SUB;
                break;
            }
            case 15: {
                luaString = LuaValue.MUL;
                break;
            }
            case 16: {
                luaString = LuaValue.DIV;
                break;
            }
            case 17: {
                luaString = LuaValue.MOD;
                break;
            }
            case 18: {
                luaString = LuaValue.POW;
                break;
            }
            case 19: {
                luaString = LuaValue.UNM;
                break;
            }
            case 21: {
                luaString = LuaValue.LEN;
                break;
            }
            case 25: {
                luaString = LuaValue.LT;
                break;
            }
            case 26: {
                luaString = LuaValue.LE;
                break;
            }
            case 22: {
                luaString = LuaValue.CONCAT;
                break;
            }
            default: {
                return null;
            }
        }
        return new NameWhat(luaString.tojstring(), "metamethod");
    }

    public static NameWhat getobjname(Prototype prototype, int n, int n2) {
        int n3 = n;
        LuaString luaString = prototype.getlocalname(n2 + 1, n3);
        if (luaString != null) {
            return new NameWhat(luaString.tojstring(), "local");
        }
        n3 = DebugLib.findsetreg(prototype, n, n2);
        if (n3 != -1) {
            int n4 = prototype.code[n3];
            switch (Lua.GET_OPCODE(n4)) {
                case 0: {
                    int n5 = Lua.GETARG_A(n4);
                    int n6 = Lua.GETARG_B(n4);
                    if (n6 >= n5) break;
                    return DebugLib.getobjname(prototype, n3, n6);
                }
                case 6: 
                case 7: {
                    int n7 = Lua.GETARG_C(n4);
                    int n8 = Lua.GETARG_B(n4);
                    LuaString luaString2 = Lua.GET_OPCODE(n4) == 7 ? prototype.getlocalname(n8 + 1, n3) : (n8 < prototype.upvalues.length ? prototype.upvalues[n8].name : QMARK);
                    String string = DebugLib.kname(prototype, n3, n7);
                    return new NameWhat(string, luaString2 != null && luaString2.eq_b(ENV) ? "global" : "field");
                }
                case 5: {
                    int n9 = Lua.GETARG_B(n4);
                    luaString = n9 < prototype.upvalues.length ? prototype.upvalues[n9].name : QMARK;
                    return luaString == null ? null : new NameWhat(luaString.tojstring(), "upvalue");
                }
                case 1: 
                case 2: {
                    int n10;
                    int n11 = n10 = Lua.GET_OPCODE(n4) == 1 ? Lua.GETARG_Bx(n4) : Lua.GETARG_Ax(prototype.code[n3 + 1]);
                    if (!prototype.k[n10].isstring()) break;
                    luaString = prototype.k[n10].strvalue();
                    return new NameWhat(luaString.tojstring(), "constant");
                }
                case 12: {
                    int n12 = Lua.GETARG_C(n4);
                    String string = DebugLib.kname(prototype, n3, n12);
                    return new NameWhat(string, "method");
                }
            }
        }
        return null;
    }

    static String kname(Prototype prototype, int n, int n2) {
        if (Lua.ISK(n2)) {
            LuaValue luaValue = prototype.k[Lua.INDEXK(n2)];
            if (luaValue.isstring()) {
                return luaValue.tojstring();
            }
        } else {
            NameWhat nameWhat = DebugLib.getobjname(prototype, n, n2);
            if (nameWhat != null && "constant".equals(nameWhat.namewhat)) {
                return nameWhat.name;
            }
        }
        return "?";
    }

    static int findsetreg(Prototype prototype, int n, int n2) {
        int n3 = -1;
        block8: for (int i = 0; i < n; ++i) {
            int n4 = prototype.code[i];
            int n5 = Lua.GET_OPCODE(n4);
            int n6 = Lua.GETARG_A(n4);
            switch (n5) {
                case 4: {
                    int n7 = Lua.GETARG_B(n4);
                    if (n6 > n2 || n2 > n6 + n7) continue block8;
                    n3 = i;
                    continue block8;
                }
                case 34: {
                    if (n2 < n6 + 2) continue block8;
                    n3 = i;
                    continue block8;
                }
                case 29: 
                case 30: {
                    if (n2 < n6) continue block8;
                    n3 = i;
                    continue block8;
                }
                case 23: {
                    int n7 = Lua.GETARG_sBx(n4);
                    int n8 = i + 1 + n7;
                    if (i >= n8 || n8 > n) continue block8;
                    i += n7;
                    continue block8;
                }
                case 27: {
                    if (n2 != n6) continue block8;
                    n3 = i;
                    continue block8;
                }
                case 36: {
                    if ((n4 >> 14 & 0x1FF) != 0) continue block8;
                    ++i;
                    continue block8;
                }
                default: {
                    if (!Lua.testAMode(n5) || n2 != n6) continue block8;
                    n3 = i;
                }
            }
        }
        return n3;
    }

    static {
        try {
            CALLS = null != System.getProperty("CALLS");
        } catch (Exception exception) {
            // empty catch block
        }
        try {
            TRACE = null != System.getProperty("TRACE");
        } catch (Exception exception) {
            // empty catch block
        }
        LUA = DebugLib.valueOf("Lua");
        QMARK = DebugLib.valueOf("?");
        CALL = DebugLib.valueOf("call");
        LINE = DebugLib.valueOf("line");
        COUNT = DebugLib.valueOf("count");
        RETURN = DebugLib.valueOf("return");
        FUNC = DebugLib.valueOf("func");
        ISTAILCALL = DebugLib.valueOf("istailcall");
        ISVARARG = DebugLib.valueOf("isvararg");
        NUPS = DebugLib.valueOf("nups");
        NPARAMS = DebugLib.valueOf("nparams");
        NAME = DebugLib.valueOf("name");
        NAMEWHAT = DebugLib.valueOf("namewhat");
        WHAT = DebugLib.valueOf("what");
        SOURCE = DebugLib.valueOf("source");
        SHORT_SRC = DebugLib.valueOf("short_src");
        LINEDEFINED = DebugLib.valueOf("linedefined");
        LASTLINEDEFINED = DebugLib.valueOf("lastlinedefined");
        CURRENTLINE = DebugLib.valueOf("currentline");
        ACTIVELINES = DebugLib.valueOf("activelines");
    }

    static final class debug
    extends ZeroArgFunction {
        debug() {
        }

        @Override
        public LuaValue call() {
            return NONE;
        }
    }

    final class gethook
    extends VarArgFunction {
        final DebugLib this$0;

        gethook(DebugLib debugLib) {
            this.this$0 = debugLib;
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaThread luaThread = varargs.narg() > 0 ? varargs.checkthread(1) : this.this$0.globals.running;
            LuaThread.State state = luaThread.state;
            return gethook.varargsOf(state.hookfunc != null ? state.hookfunc : NIL, gethook.valueOf((state.hookcall ? "c" : "") + (state.hookline ? "l" : "") + (state.hookrtrn ? "r" : "")), gethook.valueOf(state.hookcount));
        }
    }

    final class getinfo
    extends VarArgFunction {
        final DebugLib this$0;

        getinfo(DebugLib debugLib) {
            this.this$0 = debugLib;
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            CallFrame callFrame;
            int n = 1;
            LuaThread luaThread = varargs.isthread(n) ? varargs.checkthread(n++) : this.this$0.globals.running;
            LuaValue luaValue = varargs.arg(n++);
            String string = varargs.optjstring(n++, "flnStu");
            CallStack callStack = this.this$0.callstack(luaThread);
            if (luaValue.isnumber()) {
                callFrame = callStack.getCallFrame(luaValue.toint());
                if (callFrame == null) {
                    return NONE;
                }
                luaValue = callFrame.f;
            } else if (luaValue.isfunction()) {
                callFrame = callStack.findCallFrame(luaValue);
            } else {
                return getinfo.argerror(n - 2, "function or level");
            }
            DebugInfo debugInfo = callStack.auxgetinfo(string, (LuaFunction)luaValue, callFrame);
            LuaTable luaTable = new LuaTable();
            if (string.indexOf(83) >= 0) {
                luaTable.set(WHAT, (LuaValue)LUA);
                luaTable.set(SOURCE, (LuaValue)getinfo.valueOf(debugInfo.source));
                luaTable.set(SHORT_SRC, (LuaValue)getinfo.valueOf(debugInfo.short_src));
                luaTable.set(LINEDEFINED, (LuaValue)getinfo.valueOf(debugInfo.linedefined));
                luaTable.set(LASTLINEDEFINED, (LuaValue)getinfo.valueOf(debugInfo.lastlinedefined));
            }
            if (string.indexOf(108) >= 0) {
                luaTable.set(CURRENTLINE, (LuaValue)getinfo.valueOf(debugInfo.currentline));
            }
            if (string.indexOf(117) >= 0) {
                luaTable.set(NUPS, (LuaValue)getinfo.valueOf(debugInfo.nups));
                luaTable.set(NPARAMS, (LuaValue)getinfo.valueOf(debugInfo.nparams));
                luaTable.set(ISVARARG, (LuaValue)(debugInfo.isvararg ? ONE : ZERO));
            }
            if (string.indexOf(110) >= 0) {
                luaTable.set(NAME, (LuaValue)LuaValue.valueOf(debugInfo.name != null ? debugInfo.name : "?"));
                luaTable.set(NAMEWHAT, (LuaValue)LuaValue.valueOf(debugInfo.namewhat));
            }
            if (string.indexOf(116) >= 0) {
                luaTable.set(ISTAILCALL, (LuaValue)ZERO);
            }
            if (string.indexOf(76) >= 0) {
                CallFrame callFrame2;
                LuaTable luaTable2 = new LuaTable();
                luaTable.set(ACTIVELINES, (LuaValue)luaTable2);
                int n2 = 1;
                while ((callFrame2 = callStack.getCallFrame(n2)) != null) {
                    if (callFrame2.f == luaValue) {
                        luaTable2.insert(-1, getinfo.valueOf(callFrame2.currentline()));
                    }
                    ++n2;
                }
            }
            if (string.indexOf(102) >= 0 && luaValue != null) {
                luaTable.set(FUNC, luaValue);
            }
            return luaTable;
        }
    }

    final class getlocal
    extends VarArgFunction {
        final DebugLib this$0;

        getlocal(DebugLib debugLib) {
            this.this$0 = debugLib;
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            int n = 1;
            LuaThread luaThread = varargs.isthread(n) ? varargs.checkthread(n++) : this.this$0.globals.running;
            int n2 = varargs.checkint(n++);
            int n3 = varargs.checkint(n++);
            CallFrame callFrame = this.this$0.callstack(luaThread).getCallFrame(n2);
            return callFrame != null ? callFrame.getLocal(n3) : NONE;
        }
    }

    static final class getmetatable
    extends LibFunction {
        getmetatable() {
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            LuaValue luaValue2 = luaValue.getmetatable();
            return luaValue2 != null ? luaValue2 : NIL;
        }
    }

    final class getregistry
    extends ZeroArgFunction {
        final DebugLib this$0;

        getregistry(DebugLib debugLib) {
            this.this$0 = debugLib;
        }

        @Override
        public LuaValue call() {
            return this.this$0.globals;
        }
    }

    static final class getupvalue
    extends VarArgFunction {
        getupvalue() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaClosure luaClosure;
            LuaString luaString;
            LuaFunction luaFunction = varargs.checkfunction(1);
            int n = varargs.checkint(2);
            if (luaFunction instanceof LuaClosure && (luaString = DebugLib.findupvalue(luaClosure = (LuaClosure)luaFunction, n)) != null) {
                return getupvalue.varargsOf(luaString, (Varargs)luaClosure.upValues[n - 1].getValue());
            }
            return NIL;
        }
    }

    static final class getuservalue
    extends LibFunction {
        getuservalue() {
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            return luaValue.isuserdata() ? luaValue : NIL;
        }
    }

    final class sethook
    extends VarArgFunction {
        final DebugLib this$0;

        sethook(DebugLib debugLib) {
            this.this$0 = debugLib;
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            int n = 1;
            LuaThread luaThread = varargs.isthread(n) ? varargs.checkthread(n++) : this.this$0.globals.running;
            LuaFunction luaFunction = varargs.optfunction(n++, null);
            String string = varargs.optjstring(n++, "");
            int n2 = varargs.optint(n++, 0);
            boolean bl = false;
            boolean bl2 = false;
            boolean bl3 = false;
            block5: for (int i = 0; i < string.length(); ++i) {
                switch (string.charAt(i)) {
                    case 'c': {
                        bl = true;
                        continue block5;
                    }
                    case 'l': {
                        bl2 = true;
                        continue block5;
                    }
                    case 'r': {
                        bl3 = true;
                    }
                }
            }
            LuaThread.State state = luaThread.state;
            state.hookfunc = luaFunction;
            state.hookcall = bl;
            state.hookline = bl2;
            state.hookcount = n2;
            state.hookrtrn = bl3;
            return NONE;
        }
    }

    final class setlocal
    extends VarArgFunction {
        final DebugLib this$0;

        setlocal(DebugLib debugLib) {
            this.this$0 = debugLib;
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            int n = 1;
            LuaThread luaThread = varargs.isthread(n) ? varargs.checkthread(n++) : this.this$0.globals.running;
            int n2 = varargs.checkint(n++);
            int n3 = varargs.checkint(n++);
            LuaValue luaValue = varargs.arg(n++);
            CallFrame callFrame = this.this$0.callstack(luaThread).getCallFrame(n2);
            return callFrame != null ? callFrame.setLocal(n3, luaValue) : NONE;
        }
    }

    static final class setmetatable
    extends TwoArgFunction {
        setmetatable() {
        }

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            LuaTable luaTable = luaValue2.opttable(null);
            switch (luaValue.type()) {
                case 0: {
                    LuaNil.s_metatable = luaTable;
                    break;
                }
                case 3: {
                    LuaNumber.s_metatable = luaTable;
                    break;
                }
                case 1: {
                    LuaBoolean.s_metatable = luaTable;
                    break;
                }
                case 4: {
                    LuaString.s_metatable = luaTable;
                    break;
                }
                case 6: {
                    LuaFunction.s_metatable = luaTable;
                    break;
                }
                case 8: {
                    LuaThread.s_metatable = luaTable;
                    break;
                }
                default: {
                    luaValue.setmetatable(luaTable);
                }
            }
            return luaValue;
        }
    }

    static final class setupvalue
    extends VarArgFunction {
        setupvalue() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaClosure luaClosure;
            LuaString luaString;
            LuaFunction luaFunction = varargs.checkfunction(1);
            int n = varargs.checkint(2);
            LuaValue luaValue = varargs.arg(3);
            if (luaFunction instanceof LuaClosure && (luaString = DebugLib.findupvalue(luaClosure = (LuaClosure)luaFunction, n)) != null) {
                luaClosure.upValues[n - 1].setValue(luaValue);
                return luaString;
            }
            return NIL;
        }
    }

    static final class setuservalue
    extends VarArgFunction {
        setuservalue() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            Object object = varargs.checkuserdata(1);
            LuaValue luaValue = varargs.checkvalue(2);
            LuaUserdata luaUserdata = (LuaUserdata)varargs.arg1();
            luaUserdata.m_instance = luaValue.checkuserdata();
            luaUserdata.m_metatable = luaValue.getmetatable();
            return NONE;
        }
    }

    final class traceback
    extends VarArgFunction {
        final DebugLib this$0;

        traceback(DebugLib debugLib) {
            this.this$0 = debugLib;
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            int n = 1;
            LuaThread luaThread = varargs.isthread(n) ? varargs.checkthread(n++) : this.this$0.globals.running;
            String string = varargs.optjstring(n++, null);
            int n2 = varargs.optint(n++, 1);
            String string2 = this.this$0.callstack(luaThread).traceback(n2);
            return traceback.valueOf((String)(string != null ? string + "\n" + string2 : string2));
        }
    }

    static final class upvalueid
    extends VarArgFunction {
        upvalueid() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaFunction luaFunction = varargs.checkfunction(1);
            int n = varargs.checkint(2);
            if (luaFunction instanceof LuaClosure) {
                LuaClosure luaClosure = (LuaClosure)luaFunction;
                if (luaClosure.upValues != null && n > 0 && n <= luaClosure.upValues.length) {
                    return upvalueid.valueOf(luaClosure.upValues[n - 1].hashCode());
                }
            }
            return NIL;
        }
    }

    static final class upvaluejoin
    extends VarArgFunction {
        upvaluejoin() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaClosure luaClosure = varargs.checkclosure(1);
            int n = varargs.checkint(2);
            LuaClosure luaClosure2 = varargs.checkclosure(3);
            int n2 = varargs.checkint(4);
            if (n < 1 || n > luaClosure.upValues.length) {
                this.argerror("index out of range");
            }
            if (n2 < 1 || n2 > luaClosure2.upValues.length) {
                this.argerror("index out of range");
            }
            luaClosure.upValues[n - 1] = luaClosure2.upValues[n2 - 1];
            return NONE;
        }
    }

    public static class CallStack {
        static final CallFrame[] EMPTY = new CallFrame[0];
        CallFrame[] frame = EMPTY;
        int calls = 0;

        CallStack() {
        }

        synchronized int currentline() {
            return this.calls > 0 ? this.frame[this.calls - 1].currentline() : -1;
        }

        private synchronized CallFrame pushcall() {
            if (this.calls >= this.frame.length) {
                int n;
                int n2 = Math.max(4, this.frame.length * 3 / 2);
                CallFrame[] callFrameArray = new CallFrame[n2];
                System.arraycopy(this.frame, 0, callFrameArray, 0, this.frame.length);
                for (n = this.frame.length; n < n2; ++n) {
                    callFrameArray[n] = new CallFrame();
                }
                this.frame = callFrameArray;
                for (n = 1; n < n2; ++n) {
                    callFrameArray[n].previous = callFrameArray[n - 1];
                }
            }
            return this.frame[this.calls++];
        }

        final synchronized void onCall(LuaFunction luaFunction) {
            this.pushcall().set(luaFunction);
        }

        final synchronized void onCall(LuaClosure luaClosure, Varargs varargs, LuaValue[] luaValueArray) {
            this.pushcall().set(luaClosure, varargs, luaValueArray);
        }

        final synchronized void onReturn() {
            if (this.calls > 0) {
                this.frame[--this.calls].reset();
            }
        }

        final synchronized void onInstruction(int n, Varargs varargs, int n2) {
            if (this.calls > 0) {
                this.frame[this.calls - 1].instr(n, varargs, n2);
            }
        }

        synchronized String traceback(int n) {
            CallFrame callFrame;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("stack traceback:");
            while ((callFrame = this.getCallFrame(n++)) != null) {
                stringBuffer.append("\n\t");
                stringBuffer.append(callFrame.shortsource());
                stringBuffer.append(':');
                if (callFrame.currentline() > 0) {
                    stringBuffer.append(callFrame.currentline() + ":");
                }
                stringBuffer.append(" in ");
                DebugInfo debugInfo = this.auxgetinfo("n", callFrame.f, callFrame);
                if (callFrame.linedefined() == 0) {
                    stringBuffer.append("main chunk");
                    continue;
                }
                if (debugInfo.name != null) {
                    stringBuffer.append("function '");
                    stringBuffer.append(debugInfo.name);
                    stringBuffer.append('\'');
                    continue;
                }
                stringBuffer.append("function <");
                stringBuffer.append(callFrame.shortsource());
                stringBuffer.append(':');
                stringBuffer.append(callFrame.linedefined());
                stringBuffer.append('>');
            }
            stringBuffer.append("\n\t[Java]: in ?");
            return stringBuffer.toString();
        }

        synchronized CallFrame getCallFrame(int n) {
            if (n < 1 || n > this.calls) {
                return null;
            }
            return this.frame[this.calls - n];
        }

        synchronized CallFrame findCallFrame(LuaValue luaValue) {
            for (int i = 1; i <= this.calls; ++i) {
                if (this.frame[this.calls - i].f != luaValue) continue;
                return this.frame[i];
            }
            return null;
        }

        synchronized DebugInfo auxgetinfo(String string, LuaFunction luaFunction, CallFrame callFrame) {
            DebugInfo debugInfo = new DebugInfo();
            int n = string.length();
            block8: for (int i = 0; i < n; ++i) {
                switch (string.charAt(i)) {
                    case 'S': {
                        debugInfo.funcinfo(luaFunction);
                        continue block8;
                    }
                    case 'l': {
                        debugInfo.currentline = callFrame != null && callFrame.f.isclosure() ? callFrame.currentline() : -1;
                        continue block8;
                    }
                    case 'u': {
                        Object object;
                        if (luaFunction != null && luaFunction.isclosure()) {
                            object = luaFunction.checkclosure().p;
                            debugInfo.nups = (short)((Prototype)object).upvalues.length;
                            debugInfo.nparams = (short)((Prototype)object).numparams;
                            debugInfo.isvararg = ((Prototype)object).is_vararg != 0;
                            continue block8;
                        }
                        debugInfo.nups = 0;
                        debugInfo.isvararg = true;
                        debugInfo.nparams = 0;
                        continue block8;
                    }
                    case 't': {
                        debugInfo.istailcall = false;
                        continue block8;
                    }
                    case 'n': {
                        Object object;
                        if (callFrame != null && callFrame.previous != null && callFrame.previous.f.isclosure() && (object = DebugLib.getfuncname(callFrame.previous)) != null) {
                            debugInfo.name = ((NameWhat)object).name;
                            debugInfo.namewhat = ((NameWhat)object).namewhat;
                        }
                        if (debugInfo.namewhat != null) continue block8;
                        debugInfo.namewhat = "";
                        debugInfo.name = null;
                        continue block8;
                    }
                    case 'L': 
                    case 'f': {
                        continue block8;
                    }
                }
            }
            return debugInfo;
        }
    }

    public static class CallFrame {
        LuaFunction f;
        int pc;
        int top;
        Varargs v;
        LuaValue[] stack;
        CallFrame previous;

        void set(LuaClosure luaClosure, Varargs varargs, LuaValue[] luaValueArray) {
            this.f = luaClosure;
            this.v = varargs;
            this.stack = luaValueArray;
        }

        public String shortsource() {
            return this.f.isclosure() ? this.f.checkclosure().p.shortsource() : "[Java]";
        }

        void set(LuaFunction luaFunction) {
            this.f = luaFunction;
        }

        void reset() {
            this.f = null;
            this.v = null;
            this.stack = null;
        }

        void instr(int n, Varargs varargs, int n2) {
            this.pc = n;
            this.v = varargs;
            this.top = n2;
            if (TRACE) {
                Print.printState(this.f.checkclosure(), n, this.stack, n2, varargs);
            }
        }

        Varargs getLocal(int n) {
            LuaString luaString = this.getlocalname(n);
            if (n >= 1 && n <= this.stack.length && this.stack[n - 1] != null) {
                return LuaValue.varargsOf(luaString == null ? LuaValue.NIL : luaString, (Varargs)this.stack[n - 1]);
            }
            return LuaValue.NIL;
        }

        Varargs setLocal(int n, LuaValue luaValue) {
            LuaString luaString = this.getlocalname(n);
            if (n >= 1 && n <= this.stack.length && this.stack[n - 1] != null) {
                this.stack[n - 1] = luaValue;
                return luaString == null ? LuaValue.NIL : luaString;
            }
            return LuaValue.NIL;
        }

        public int currentline() {
            if (!this.f.isclosure()) {
                return 1;
            }
            int[] nArray = this.f.checkclosure().p.lineinfo;
            return nArray == null || this.pc < 0 || this.pc >= nArray.length ? -1 : nArray[this.pc];
        }

        String sourceline() {
            if (!this.f.isclosure()) {
                return this.f.tojstring();
            }
            return this.f.checkclosure().p.shortsource() + ":" + this.currentline();
        }

        int linedefined() {
            return this.f.isclosure() ? this.f.checkclosure().p.linedefined : -1;
        }

        LuaString getlocalname(int n) {
            if (!this.f.isclosure()) {
                return null;
            }
            return this.f.checkclosure().p.getlocalname(n, this.pc);
        }
    }

    static class NameWhat {
        final String name;
        final String namewhat;

        NameWhat(String string, String string2) {
            this.name = string;
            this.namewhat = string2;
        }
    }

    static class DebugInfo {
        String name;
        String namewhat;
        String what;
        String source;
        int currentline;
        int linedefined;
        int lastlinedefined;
        short nups;
        short nparams;
        boolean isvararg;
        boolean istailcall;
        String short_src;
        CallFrame cf;

        DebugInfo() {
        }

        public void funcinfo(LuaFunction luaFunction) {
            if (luaFunction.isclosure()) {
                Prototype prototype = luaFunction.checkclosure().p;
                this.source = prototype.source != null ? prototype.source.tojstring() : "=?";
                this.linedefined = prototype.linedefined;
                this.lastlinedefined = prototype.lastlinedefined;
                this.what = this.linedefined == 0 ? "main" : "Lua";
                this.short_src = prototype.shortsource();
            } else {
                this.source = "=[Java]";
                this.linedefined = -1;
                this.lastlinedefined = -1;
                this.what = "Java";
                this.short_src = luaFunction.name();
            }
        }
    }
}

