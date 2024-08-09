/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import mpp.venusfr.scripts.interpreter.Globals;
import mpp.venusfr.scripts.interpreter.LuaError;
import mpp.venusfr.scripts.interpreter.LuaFunction;
import mpp.venusfr.scripts.interpreter.LuaThread;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Prototype;
import mpp.venusfr.scripts.interpreter.UpValue;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.lib.DebugLib;

public class LuaClosure
extends LuaFunction {
    private static final UpValue[] NOUPVALUES = new UpValue[0];
    public final Prototype p;
    public UpValue[] upValues;
    final Globals globals;

    public LuaClosure(Prototype prototype, LuaValue luaValue) {
        this.p = prototype;
        this.initupvalue1(luaValue);
        this.globals = luaValue instanceof Globals ? (Globals)luaValue : null;
    }

    @Override
    public void initupvalue1(LuaValue luaValue) {
        if (this.p.upvalues == null || this.p.upvalues.length == 0) {
            this.upValues = NOUPVALUES;
        } else {
            this.upValues = new UpValue[this.p.upvalues.length];
            this.upValues[0] = new UpValue(new LuaValue[]{luaValue}, 0);
        }
    }

    @Override
    public boolean isclosure() {
        return false;
    }

    @Override
    public LuaClosure optclosure(LuaClosure luaClosure) {
        return this;
    }

    @Override
    public LuaClosure checkclosure() {
        return this;
    }

    @Override
    public String tojstring() {
        return "function: " + this.p.toString();
    }

    private LuaValue[] getNewStack() {
        int n = this.p.maxstacksize;
        LuaValue[] luaValueArray = new LuaValue[n];
        System.arraycopy(NILS, 0, luaValueArray, 0, n);
        return luaValueArray;
    }

    @Override
    public final LuaValue call() {
        LuaValue[] luaValueArray = this.getNewStack();
        return this.execute(luaValueArray, NONE).arg1();
    }

    @Override
    public final LuaValue call(LuaValue luaValue) {
        LuaValue[] luaValueArray = this.getNewStack();
        switch (this.p.numparams) {
            default: {
                luaValueArray[0] = luaValue;
                return this.execute(luaValueArray, NONE).arg1();
            }
            case 0: 
        }
        return this.execute(luaValueArray, luaValue).arg1();
    }

    @Override
    public final LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        LuaValue[] luaValueArray = this.getNewStack();
        switch (this.p.numparams) {
            default: {
                luaValueArray[0] = luaValue;
                luaValueArray[1] = luaValue2;
                return this.execute(luaValueArray, NONE).arg1();
            }
            case 1: {
                luaValueArray[0] = luaValue;
                return this.execute(luaValueArray, luaValue2).arg1();
            }
            case 0: 
        }
        return this.execute(luaValueArray, this.p.is_vararg != 0 ? LuaClosure.varargsOf(luaValue, (Varargs)luaValue2) : NONE).arg1();
    }

    @Override
    public final LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
        LuaValue[] luaValueArray = this.getNewStack();
        switch (this.p.numparams) {
            default: {
                luaValueArray[0] = luaValue;
                luaValueArray[1] = luaValue2;
                luaValueArray[2] = luaValue3;
                return this.execute(luaValueArray, NONE).arg1();
            }
            case 2: {
                luaValueArray[0] = luaValue;
                luaValueArray[1] = luaValue2;
                return this.execute(luaValueArray, luaValue3).arg1();
            }
            case 1: {
                luaValueArray[0] = luaValue;
                return this.execute(luaValueArray, this.p.is_vararg != 0 ? LuaClosure.varargsOf(luaValue2, (Varargs)luaValue3) : NONE).arg1();
            }
            case 0: 
        }
        return this.execute(luaValueArray, this.p.is_vararg != 0 ? LuaClosure.varargsOf(luaValue, luaValue2, luaValue3) : NONE).arg1();
    }

    @Override
    public final Varargs invoke(Varargs varargs) {
        return this.onInvoke(varargs).eval();
    }

    @Override
    public final Varargs onInvoke(Varargs varargs) {
        LuaValue[] luaValueArray = this.getNewStack();
        for (int i = 0; i < this.p.numparams; ++i) {
            luaValueArray[i] = varargs.arg(i + 1);
        }
        return this.execute(luaValueArray, this.p.is_vararg != 0 ? varargs.subargs(this.p.numparams + 1) : NONE);
    }

    /*
     * Exception decompiling
     */
    protected Varargs execute(LuaValue[] var1_1, Varargs var2_2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [44[CASE]], but top level block is 10[TRYBLOCK]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:538)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         *     at async.DecompilerRunnable.cfrDecompilation(DecompilerRunnable.java:348)
         *     at async.DecompilerRunnable.call(DecompilerRunnable.java:309)
         *     at async.DecompilerRunnable.call(DecompilerRunnable.java:31)
         *     at java.util.concurrent.FutureTask.run(FutureTask.java:266)
         *     at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
         *     at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
         *     at java.lang.Thread.run(Thread.java:750)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    String errorHook(String string, int n) {
        if (this.globals == null) {
            return string;
        }
        LuaThread luaThread = this.globals.running;
        if (luaThread.errorfunc == null) {
            return this.globals.debuglib != null ? string + "\n" + this.globals.debuglib.traceback(n) : string;
        }
        LuaValue luaValue = luaThread.errorfunc;
        luaThread.errorfunc = null;
        try {
            String string2 = luaValue.call(LuaValue.valueOf(string)).tojstring();
            return string2;
        } catch (Throwable throwable) {
            String string3 = "error in error handling";
            return string3;
        } finally {
            luaThread.errorfunc = luaValue;
        }
    }

    private void processErrorHooks(LuaError luaError, Prototype prototype, int n) {
        String string = "?";
        int n2 = -1;
        DebugLib.CallFrame callFrame = null;
        if (this.globals != null && this.globals.debuglib != null && (callFrame = this.globals.debuglib.getCallFrame(luaError.level)) != null) {
            String string2 = callFrame.shortsource();
            string = string2 != null ? string2 : "?";
            n2 = callFrame.currentline();
        }
        if (callFrame == null) {
            string = prototype.source != null ? prototype.source.tojstring() : "?";
            n2 = prototype.lineinfo != null && n >= 0 && n < prototype.lineinfo.length ? prototype.lineinfo[n] : -1;
        }
        luaError.fileline = string + ":" + n2;
        System.out.println(n2);
        luaError.line = n2;
        luaError.traceback = this.errorHook(luaError.getMessage(), luaError.level);
    }

    private UpValue findupval(LuaValue[] luaValueArray, short s, UpValue[] upValueArray) {
        int n;
        int n2 = upValueArray.length;
        for (n = 0; n < n2; ++n) {
            if (upValueArray[n] == null || upValueArray[n].index != s) continue;
            return upValueArray[n];
        }
        for (n = 0; n < n2; ++n) {
            if (upValueArray[n] != null) continue;
            upValueArray[n] = new UpValue(luaValueArray, s);
            return upValueArray[n];
        }
        LuaClosure.error("No space for upvalue");
        return null;
    }

    protected LuaValue getUpvalue(int n) {
        return this.upValues[n].getValue();
    }

    protected void setUpvalue(int n, LuaValue luaValue) {
        this.upValues[n].setValue(luaValue);
    }

    @Override
    public String name() {
        return "<" + this.p.shortsource() + ":" + this.p.linedefined + ">";
    }
}

