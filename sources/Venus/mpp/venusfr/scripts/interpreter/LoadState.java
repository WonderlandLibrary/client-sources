/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import mpp.venusfr.scripts.interpreter.Globals;
import mpp.venusfr.scripts.interpreter.LocVars;
import mpp.venusfr.scripts.interpreter.LuaError;
import mpp.venusfr.scripts.interpreter.LuaInteger;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Prototype;
import mpp.venusfr.scripts.interpreter.Upvaldesc;

public class LoadState {
    public static final Globals.Undumper instance = new GlobalsUndumper();
    public static final int NUMBER_FORMAT_FLOATS_OR_DOUBLES = 0;
    public static final int NUMBER_FORMAT_INTS_ONLY = 1;
    public static final int NUMBER_FORMAT_NUM_PATCH_INT32 = 4;
    public static final int LUA_TINT = -2;
    public static final int LUA_TNONE = -1;
    public static final int LUA_TNIL = 0;
    public static final int LUA_TBOOLEAN = 1;
    public static final int LUA_TLIGHTUSERDATA = 2;
    public static final int LUA_TNUMBER = 3;
    public static final int LUA_TSTRING = 4;
    public static final int LUA_TTABLE = 5;
    public static final int LUA_TFUNCTION = 6;
    public static final int LUA_TUSERDATA = 7;
    public static final int LUA_TTHREAD = 8;
    public static final int LUA_TVALUE = 9;
    public static String encoding = null;
    public static final byte[] LUA_SIGNATURE = new byte[]{27, 76, 117, 97};
    public static final byte[] LUAC_TAIL = new byte[]{25, -109, 13, 10, 26, 10};
    public static final String SOURCE_BINARY_STRING = "binary string";
    public static final int LUAC_VERSION = 82;
    public static final int LUAC_FORMAT = 0;
    public static final int LUAC_HEADERSIZE = 12;
    private int luacVersion;
    private int luacFormat;
    private boolean luacLittleEndian;
    private int luacSizeofInt;
    private int luacSizeofSizeT;
    private int luacSizeofInstruction;
    private int luacSizeofLuaNumber;
    private int luacNumberFormat;
    public final DataInputStream is;
    String name;
    private static final LuaValue[] NOVALUES = new LuaValue[0];
    private static final Prototype[] NOPROTOS = new Prototype[0];
    private static final LocVars[] NOLOCVARS = new LocVars[0];
    private static final Upvaldesc[] NOUPVALDESCS = new Upvaldesc[0];
    private static final int[] NOINTS = new int[0];
    private byte[] buf = new byte[512];

    public static void install(Globals globals) {
        globals.undumper = instance;
    }

    int loadInt() throws IOException {
        this.is.readFully(this.buf, 0, 4);
        return this.luacLittleEndian ? this.buf[3] << 24 | (0xFF & this.buf[2]) << 16 | (0xFF & this.buf[1]) << 8 | 0xFF & this.buf[0] : this.buf[0] << 24 | (0xFF & this.buf[1]) << 16 | (0xFF & this.buf[2]) << 8 | 0xFF & this.buf[3];
    }

    int[] loadIntArray() throws IOException {
        int n = this.loadInt();
        if (n == 0) {
            return NOINTS;
        }
        int n2 = n << 2;
        if (this.buf.length < n2) {
            this.buf = new byte[n2];
        }
        this.is.readFully(this.buf, 0, n2);
        int[] nArray = new int[n];
        int n3 = 0;
        int n4 = 0;
        while (n3 < n) {
            nArray[n3] = this.luacLittleEndian ? this.buf[n4 + 3] << 24 | (0xFF & this.buf[n4 + 2]) << 16 | (0xFF & this.buf[n4 + 1]) << 8 | 0xFF & this.buf[n4] : this.buf[n4] << 24 | (0xFF & this.buf[n4 + 1]) << 16 | (0xFF & this.buf[n4 + 2]) << 8 | 0xFF & this.buf[n4 + 3];
            ++n3;
            n4 += 4;
        }
        return nArray;
    }

    long loadInt64() throws IOException {
        int n;
        int n2;
        if (this.luacLittleEndian) {
            n2 = this.loadInt();
            n = this.loadInt();
        } else {
            n = this.loadInt();
            n2 = this.loadInt();
        }
        return (long)n << 32 | (long)n2 & 0xFFFFFFFFL;
    }

    LuaString loadString() throws IOException {
        int n;
        int n2 = n = this.luacSizeofSizeT == 8 ? (int)this.loadInt64() : this.loadInt();
        if (n == 0) {
            return null;
        }
        byte[] byArray = new byte[n];
        this.is.readFully(byArray, 0, n);
        return LuaString.valueUsing(byArray, 0, byArray.length - 1);
    }

    public static LuaValue longBitsToLuaNumber(long l) {
        int n;
        long l2;
        long l3;
        if ((l & Long.MAX_VALUE) == 0L) {
            return LuaValue.ZERO;
        }
        int n2 = (int)(l >> 52 & 0x7FFL) - 1023;
        if (n2 >= 0 && n2 < 31 && ((l3 = l & 0xFFFFFFFFFFFFFL) & (l2 = (1L << (n = 52 - n2)) - 1L)) == 0L) {
            int n3 = (int)(l3 >> n) | 1 << n2;
            return LuaInteger.valueOf(l >> 63 != 0L ? -n3 : n3);
        }
        return LuaValue.valueOf(Double.longBitsToDouble(l));
    }

    LuaValue loadNumber() throws IOException {
        if (this.luacNumberFormat == 1) {
            return LuaInteger.valueOf(this.loadInt());
        }
        return LoadState.longBitsToLuaNumber(this.loadInt64());
    }

    void loadConstants(Prototype prototype) throws IOException {
        int n = this.loadInt();
        LuaValue[] luaValueArray = n > 0 ? new LuaValue[n] : NOVALUES;
        block7: for (int i = 0; i < n; ++i) {
            switch (this.is.readByte()) {
                case 0: {
                    luaValueArray[i] = LuaValue.NIL;
                    continue block7;
                }
                case 1: {
                    luaValueArray[i] = 0 != this.is.readUnsignedByte() ? LuaValue.TRUE : LuaValue.FALSE;
                    continue block7;
                }
                case -2: {
                    luaValueArray[i] = LuaInteger.valueOf(this.loadInt());
                    continue block7;
                }
                case 3: {
                    luaValueArray[i] = this.loadNumber();
                    continue block7;
                }
                case 4: {
                    luaValueArray[i] = this.loadString();
                    continue block7;
                }
                default: {
                    throw new IllegalStateException("bad constant");
                }
            }
        }
        prototype.k = luaValueArray;
        n = this.loadInt();
        Prototype[] prototypeArray = n > 0 ? new Prototype[n] : NOPROTOS;
        for (int i = 0; i < n; ++i) {
            prototypeArray[i] = this.loadFunction(prototype.source);
        }
        prototype.p = prototypeArray;
    }

    void loadUpvalues(Prototype prototype) throws IOException {
        int n = this.loadInt();
        prototype.upvalues = n > 0 ? new Upvaldesc[n] : NOUPVALDESCS;
        for (int i = 0; i < n; ++i) {
            boolean bl = this.is.readByte() != 0;
            int n2 = this.is.readByte() & 0xFF;
            prototype.upvalues[i] = new Upvaldesc(null, bl, n2);
        }
    }

    void loadDebug(Prototype prototype) throws IOException {
        int n;
        prototype.source = this.loadString();
        prototype.lineinfo = this.loadIntArray();
        int n2 = this.loadInt();
        prototype.locvars = n2 > 0 ? new LocVars[n2] : NOLOCVARS;
        for (n = 0; n < n2; ++n) {
            LuaString luaString = this.loadString();
            int n3 = this.loadInt();
            int n4 = this.loadInt();
            prototype.locvars[n] = new LocVars(luaString, n3, n4);
        }
        n2 = this.loadInt();
        for (n = 0; n < n2; ++n) {
            prototype.upvalues[n].name = this.loadString();
        }
    }

    public Prototype loadFunction(LuaString luaString) throws IOException {
        Prototype prototype = new Prototype();
        prototype.linedefined = this.loadInt();
        prototype.lastlinedefined = this.loadInt();
        prototype.numparams = this.is.readUnsignedByte();
        prototype.is_vararg = this.is.readUnsignedByte();
        prototype.maxstacksize = this.is.readUnsignedByte();
        prototype.code = this.loadIntArray();
        this.loadConstants(prototype);
        this.loadUpvalues(prototype);
        this.loadDebug(prototype);
        return prototype;
    }

    public void loadHeader() throws IOException {
        this.luacVersion = this.is.readByte();
        this.luacFormat = this.is.readByte();
        this.luacLittleEndian = 0 != this.is.readByte();
        this.luacSizeofInt = this.is.readByte();
        this.luacSizeofSizeT = this.is.readByte();
        this.luacSizeofInstruction = this.is.readByte();
        this.luacSizeofLuaNumber = this.is.readByte();
        this.luacNumberFormat = this.is.readByte();
        for (int i = 0; i < LUAC_TAIL.length; ++i) {
            if (this.is.readByte() == LUAC_TAIL[i]) continue;
            throw new LuaError("Unexpeted byte in luac tail of header, index=" + i);
        }
    }

    public static Prototype undump(InputStream inputStream, String string) throws IOException {
        if (inputStream.read() != LUA_SIGNATURE[0] || inputStream.read() != LUA_SIGNATURE[1] || inputStream.read() != LUA_SIGNATURE[2] || inputStream.read() != LUA_SIGNATURE[3]) {
            return null;
        }
        String string2 = LoadState.getSourceName(string);
        LoadState loadState = new LoadState(inputStream, string2);
        loadState.loadHeader();
        switch (loadState.luacNumberFormat) {
            case 0: 
            case 1: 
            case 4: {
                break;
            }
            default: {
                throw new LuaError("unsupported int size");
            }
        }
        return loadState.loadFunction(LuaString.valueOf(string2));
    }

    public static String getSourceName(String string) {
        String string2 = string;
        if (string.startsWith("@") || string.startsWith("=")) {
            string2 = string.substring(1);
        } else if (string.startsWith("\u001b")) {
            string2 = SOURCE_BINARY_STRING;
        }
        return string2;
    }

    private LoadState(InputStream inputStream, String string) {
        this.name = string;
        this.is = new DataInputStream(inputStream);
    }

    private static final class GlobalsUndumper
    implements Globals.Undumper {
        private GlobalsUndumper() {
        }

        @Override
        public Prototype undump(InputStream inputStream, String string) throws IOException {
            return LoadState.undump(inputStream, string);
        }
    }
}

