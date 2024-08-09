/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import mpp.venusfr.scripts.interpreter.LoadState;
import mpp.venusfr.scripts.interpreter.LocVars;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Prototype;

public class DumpState {
    public static boolean ALLOW_INTEGER_CASTING = false;
    public static final int NUMBER_FORMAT_FLOATS_OR_DOUBLES = 0;
    public static final int NUMBER_FORMAT_INTS_ONLY = 1;
    public static final int NUMBER_FORMAT_NUM_PATCH_INT32 = 4;
    public static final int NUMBER_FORMAT_DEFAULT = 0;
    private boolean IS_LITTLE_ENDIAN = true;
    private int NUMBER_FORMAT = 0;
    private int SIZEOF_LUA_NUMBER = 8;
    private static final int SIZEOF_INT = 4;
    private static final int SIZEOF_SIZET = 4;
    private static final int SIZEOF_INSTRUCTION = 4;
    DataOutputStream writer;
    boolean strip;
    int status;

    public DumpState(OutputStream outputStream, boolean bl) {
        this.writer = new DataOutputStream(outputStream);
        this.strip = bl;
        this.status = 0;
    }

    void dumpBlock(byte[] byArray, int n) throws IOException {
        this.writer.write(byArray, 0, n);
    }

    void dumpChar(int n) throws IOException {
        this.writer.write(n);
    }

    void dumpInt(int n) throws IOException {
        if (this.IS_LITTLE_ENDIAN) {
            this.writer.writeByte(n & 0xFF);
            this.writer.writeByte(n >> 8 & 0xFF);
            this.writer.writeByte(n >> 16 & 0xFF);
            this.writer.writeByte(n >> 24 & 0xFF);
        } else {
            this.writer.writeInt(n);
        }
    }

    void dumpString(LuaString luaString) throws IOException {
        int n = luaString.len().toint();
        this.dumpInt(n + 1);
        luaString.write(this.writer, 0, n);
        this.writer.write(0);
    }

    void dumpDouble(double d) throws IOException {
        long l = Double.doubleToLongBits(d);
        if (this.IS_LITTLE_ENDIAN) {
            this.dumpInt((int)l);
            this.dumpInt((int)(l >> 32));
        } else {
            this.writer.writeLong(l);
        }
    }

    void dumpCode(Prototype prototype) throws IOException {
        int[] nArray = prototype.code;
        int n = nArray.length;
        this.dumpInt(n);
        for (int i = 0; i < n; ++i) {
            this.dumpInt(nArray[i]);
        }
    }

    void dumpConstants(Prototype prototype) throws IOException {
        int n;
        LuaValue[] luaValueArray = prototype.k;
        int n2 = luaValueArray.length;
        this.dumpInt(n2);
        block11: for (n = 0; n < n2; ++n) {
            LuaValue luaValue = luaValueArray[n];
            switch (luaValue.type()) {
                case 0: {
                    this.writer.write(0);
                    continue block11;
                }
                case 1: {
                    this.writer.write(1);
                    this.dumpChar(luaValue.toboolean() ? 1 : 0);
                    continue block11;
                }
                case 3: {
                    switch (this.NUMBER_FORMAT) {
                        case 0: {
                            this.writer.write(3);
                            this.dumpDouble(luaValue.todouble());
                            continue block11;
                        }
                        case 1: {
                            if (!ALLOW_INTEGER_CASTING && !luaValue.isint()) {
                                throw new IllegalArgumentException("not an integer: " + luaValue);
                            }
                            this.writer.write(3);
                            this.dumpInt(luaValue.toint());
                            continue block11;
                        }
                        case 4: {
                            if (luaValue.isint()) {
                                this.writer.write(-2);
                                this.dumpInt(luaValue.toint());
                                continue block11;
                            }
                            this.writer.write(3);
                            this.dumpDouble(luaValue.todouble());
                            continue block11;
                        }
                    }
                    throw new IllegalArgumentException("number format not supported: " + this.NUMBER_FORMAT);
                }
                case 4: {
                    this.writer.write(4);
                    this.dumpString((LuaString)luaValue);
                    continue block11;
                }
                default: {
                    throw new IllegalArgumentException("bad type for " + luaValue);
                }
            }
        }
        n2 = prototype.p.length;
        this.dumpInt(n2);
        for (n = 0; n < n2; ++n) {
            this.dumpFunction(prototype.p[n]);
        }
    }

    void dumpUpvalues(Prototype prototype) throws IOException {
        int n = prototype.upvalues.length;
        this.dumpInt(n);
        for (int i = 0; i < n; ++i) {
            this.writer.writeByte(prototype.upvalues[i].instack ? 1 : 0);
            this.writer.writeByte(prototype.upvalues[i].idx);
        }
    }

    void dumpDebug(Prototype prototype) throws IOException {
        int n;
        if (this.strip) {
            this.dumpInt(0);
        } else {
            this.dumpString(prototype.source);
        }
        int n2 = this.strip ? 0 : prototype.lineinfo.length;
        this.dumpInt(n2);
        for (n = 0; n < n2; ++n) {
            this.dumpInt(prototype.lineinfo[n]);
        }
        n2 = this.strip ? 0 : prototype.locvars.length;
        this.dumpInt(n2);
        for (n = 0; n < n2; ++n) {
            LocVars locVars = prototype.locvars[n];
            this.dumpString(locVars.varname);
            this.dumpInt(locVars.startpc);
            this.dumpInt(locVars.endpc);
        }
        n2 = this.strip ? 0 : prototype.upvalues.length;
        this.dumpInt(n2);
        for (n = 0; n < n2; ++n) {
            this.dumpString(prototype.upvalues[n].name);
        }
    }

    void dumpFunction(Prototype prototype) throws IOException {
        this.dumpInt(prototype.linedefined);
        this.dumpInt(prototype.lastlinedefined);
        this.dumpChar(prototype.numparams);
        this.dumpChar(prototype.is_vararg);
        this.dumpChar(prototype.maxstacksize);
        this.dumpCode(prototype);
        this.dumpConstants(prototype);
        this.dumpUpvalues(prototype);
        this.dumpDebug(prototype);
    }

    void dumpHeader() throws IOException {
        this.writer.write(LoadState.LUA_SIGNATURE);
        this.writer.write(82);
        this.writer.write(0);
        this.writer.write(this.IS_LITTLE_ENDIAN ? 1 : 0);
        this.writer.write(4);
        this.writer.write(4);
        this.writer.write(4);
        this.writer.write(this.SIZEOF_LUA_NUMBER);
        this.writer.write(this.NUMBER_FORMAT);
        this.writer.write(LoadState.LUAC_TAIL);
    }

    public static int dump(Prototype prototype, OutputStream outputStream, boolean bl) throws IOException {
        DumpState dumpState = new DumpState(outputStream, bl);
        dumpState.dumpHeader();
        dumpState.dumpFunction(prototype);
        return dumpState.status;
    }

    public static int dump(Prototype prototype, OutputStream outputStream, boolean bl, int n, boolean bl2) throws IOException {
        switch (n) {
            case 0: 
            case 1: 
            case 4: {
                break;
            }
            default: {
                throw new IllegalArgumentException("number format not supported: " + n);
            }
        }
        DumpState dumpState = new DumpState(outputStream, bl);
        dumpState.IS_LITTLE_ENDIAN = bl2;
        dumpState.NUMBER_FORMAT = n;
        dumpState.SIZEOF_LUA_NUMBER = n == 1 ? 4 : 8;
        dumpState.dumpHeader();
        dumpState.dumpFunction(prototype);
        return dumpState.status;
    }
}

