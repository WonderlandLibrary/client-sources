/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.lib;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import mpp.venusfr.scripts.interpreter.Globals;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.lib.TwoArgFunction;
import mpp.venusfr.scripts.interpreter.lib.VarArgFunction;

public abstract class IoLib
extends TwoArgFunction {
    protected static final int FTYPE_STDIN = 0;
    protected static final int FTYPE_STDOUT = 1;
    protected static final int FTYPE_STDERR = 2;
    protected static final int FTYPE_NAMED = 3;
    private File infile = null;
    private File outfile = null;
    private File errfile = null;
    private static final LuaValue STDIN = IoLib.valueOf("stdin");
    private static final LuaValue STDOUT = IoLib.valueOf("stdout");
    private static final LuaValue STDERR = IoLib.valueOf("stderr");
    private static final LuaValue FILE = IoLib.valueOf("file");
    private static final LuaValue CLOSED_FILE = IoLib.valueOf("closed file");
    private static final int IO_CLOSE = 0;
    private static final int IO_FLUSH = 1;
    private static final int IO_INPUT = 2;
    private static final int IO_LINES = 3;
    private static final int IO_OPEN = 4;
    private static final int IO_OUTPUT = 5;
    private static final int IO_POPEN = 6;
    private static final int IO_READ = 7;
    private static final int IO_TMPFILE = 8;
    private static final int IO_TYPE = 9;
    private static final int IO_WRITE = 10;
    private static final int FILE_CLOSE = 11;
    private static final int FILE_FLUSH = 12;
    private static final int FILE_LINES = 13;
    private static final int FILE_READ = 14;
    private static final int FILE_SEEK = 15;
    private static final int FILE_SETVBUF = 16;
    private static final int FILE_WRITE = 17;
    private static final int IO_INDEX = 18;
    private static final int LINES_ITER = 19;
    public static final String[] IO_NAMES = new String[]{"close", "flush", "input", "lines", "open", "output", "popen", "read", "tmpfile", "type", "write"};
    public static final String[] FILE_NAMES = new String[]{"close", "flush", "lines", "read", "seek", "setvbuf", "write"};
    LuaTable filemethods;
    protected Globals globals;

    protected abstract File wrapStdin() throws IOException;

    protected abstract File wrapStdout() throws IOException;

    protected abstract File wrapStderr() throws IOException;

    protected abstract File openFile(String var1, boolean var2, boolean var3, boolean var4, boolean var5) throws IOException;

    protected abstract File tmpFile() throws IOException;

    protected abstract File openProgram(String var1, String var2) throws IOException;

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        this.globals = luaValue2.checkglobals();
        LuaTable luaTable = new LuaTable();
        this.bind(luaTable, IoLibV.class, IO_NAMES);
        this.filemethods = new LuaTable();
        this.bind(this.filemethods, IoLibV.class, FILE_NAMES, 11);
        LuaTable luaTable2 = new LuaTable();
        this.bind(luaTable2, IoLibV.class, new String[]{"__index"}, 18);
        luaTable.setmetatable(luaTable2);
        this.setLibInstance(luaTable);
        this.setLibInstance(this.filemethods);
        this.setLibInstance(luaTable2);
        luaValue2.set("io", (LuaValue)luaTable);
        if (!luaValue2.get("package").isnil()) {
            luaValue2.get("package").get("loaded").set("io", (LuaValue)luaTable);
        }
        return luaTable;
    }

    private void setLibInstance(LuaTable luaTable) {
        LuaValue[] luaValueArray = luaTable.keys();
        int n = luaValueArray.length;
        for (int i = 0; i < n; ++i) {
            ((IoLibV)luaTable.get((LuaValue)luaValueArray[i])).iolib = this;
        }
    }

    private File input() {
        return this.infile != null ? this.infile : (this.infile = this.ioopenfile(0, "-", "r"));
    }

    public Varargs _io_flush() throws IOException {
        IoLib.checkopen(this.output());
        this.outfile.flush();
        return LuaValue.TRUE;
    }

    public Varargs _io_tmpfile() throws IOException {
        return this.tmpFile();
    }

    public Varargs _io_close(LuaValue luaValue) throws IOException {
        File file = luaValue.isnil() ? this.output() : IoLib.checkfile(luaValue);
        IoLib.checkopen(file);
        return IoLib.ioclose(file);
    }

    public Varargs _io_input(LuaValue luaValue) {
        this.infile = luaValue.isnil() ? this.input() : (luaValue.isstring() ? this.ioopenfile(3, luaValue.checkjstring(), "r") : IoLib.checkfile(luaValue));
        return this.infile;
    }

    public Varargs _io_output(LuaValue luaValue) {
        this.outfile = luaValue.isnil() ? this.output() : (luaValue.isstring() ? this.ioopenfile(3, luaValue.checkjstring(), "w") : IoLib.checkfile(luaValue));
        return this.outfile;
    }

    public Varargs _io_type(LuaValue luaValue) {
        File file = IoLib.optfile(luaValue);
        return file != null ? (file.isclosed() ? CLOSED_FILE : FILE) : NIL;
    }

    public Varargs _io_popen(String string, String string2) throws IOException {
        if (!"r".equals(string2) && !"w".equals(string2)) {
            IoLib.argerror(2, "invalid value: '" + string2 + "'; must be one of 'r' or 'w'");
        }
        return this.openProgram(string, string2);
    }

    public Varargs _io_open(String string, String string2) throws IOException {
        return this.rawopenfile(3, string, string2);
    }

    public Varargs _io_lines(Varargs varargs) {
        String string = varargs.optjstring(1, null);
        File file = string == null ? this.input() : this.ioopenfile(3, string, "r");
        IoLib.checkopen(file);
        return this.lines(file, string != null, varargs.subargs(2));
    }

    public Varargs _io_read(Varargs varargs) throws IOException {
        IoLib.checkopen(this.input());
        return this.ioread(this.infile, varargs);
    }

    public Varargs _io_write(Varargs varargs) throws IOException {
        IoLib.checkopen(this.output());
        return IoLib.iowrite(this.outfile, varargs);
    }

    public Varargs _file_close(LuaValue luaValue) throws IOException {
        return IoLib.ioclose(IoLib.checkfile(luaValue));
    }

    public Varargs _file_flush(LuaValue luaValue) throws IOException {
        IoLib.checkfile(luaValue).flush();
        return LuaValue.TRUE;
    }

    public Varargs _file_setvbuf(LuaValue luaValue, String string, int n) {
        if (!("no".equals(string) || "full".equals(string) || "line".equals(string))) {
            IoLib.argerror(1, "invalid value: '" + string + "'; must be one of 'no', 'full' or 'line'");
        }
        IoLib.checkfile(luaValue).setvbuf(string, n);
        return LuaValue.TRUE;
    }

    public Varargs _file_lines(Varargs varargs) {
        return this.lines(IoLib.checkfile(varargs.arg1()), false, varargs.subargs(2));
    }

    public Varargs _file_read(LuaValue luaValue, Varargs varargs) throws IOException {
        return this.ioread(IoLib.checkfile(luaValue), varargs);
    }

    public Varargs _file_seek(LuaValue luaValue, String string, int n) throws IOException {
        if (!("set".equals(string) || "end".equals(string) || "cur".equals(string))) {
            IoLib.argerror(1, "invalid value: '" + string + "'; must be one of 'set', 'cur' or 'end'");
        }
        return IoLib.valueOf(IoLib.checkfile(luaValue).seek(string, n));
    }

    public Varargs _file_write(LuaValue luaValue, Varargs varargs) throws IOException {
        return IoLib.iowrite(IoLib.checkfile(luaValue), varargs);
    }

    public Varargs _io_index(LuaValue luaValue) {
        return luaValue.equals(STDOUT) ? this.output() : (luaValue.equals(STDIN) ? this.input() : (luaValue.equals(STDERR) ? this.errput() : NIL));
    }

    public Varargs _lines_iter(LuaValue luaValue, boolean bl, Varargs varargs) throws IOException {
        File file = IoLib.optfile(luaValue);
        if (file == null) {
            IoLib.argerror(1, "not a file: " + luaValue);
        }
        if (file.isclosed()) {
            IoLib.error("file is already closed");
        }
        Varargs varargs2 = this.ioread(file, varargs);
        if (bl && varargs2.isnil(0) && file.eof()) {
            file.close();
        }
        return varargs2;
    }

    private File output() {
        return this.outfile != null ? this.outfile : (this.outfile = this.ioopenfile(1, "-", "w"));
    }

    private File errput() {
        return this.errfile != null ? this.errfile : (this.errfile = this.ioopenfile(2, "-", "w"));
    }

    private File ioopenfile(int n, String string, String string2) {
        try {
            return this.rawopenfile(n, string, string2);
        } catch (Exception exception) {
            IoLib.error("io error: " + exception.getMessage());
            return null;
        }
    }

    private static Varargs ioclose(File file) throws IOException {
        if (file.isstdfile()) {
            return IoLib.errorresult("cannot close standard file");
        }
        file.close();
        return IoLib.successresult();
    }

    private static Varargs successresult() {
        return LuaValue.TRUE;
    }

    static Varargs errorresult(Exception exception) {
        String string = exception.getMessage();
        return IoLib.errorresult("io error: " + (string != null ? string : exception.toString()));
    }

    private static Varargs errorresult(String string) {
        return IoLib.varargsOf(NIL, (Varargs)IoLib.valueOf(string));
    }

    private Varargs lines(File file, boolean bl, Varargs varargs) {
        try {
            return new IoLibV(file, "lnext", 19, this, bl, varargs);
        } catch (Exception exception) {
            return IoLib.error("lines: " + exception);
        }
    }

    private static Varargs iowrite(File file, Varargs varargs) throws IOException {
        int n = varargs.narg();
        for (int i = 1; i <= n; ++i) {
            file.write(varargs.checkstring(i));
        }
        return file;
    }

    private Varargs ioread(File file, Varargs varargs) throws IOException {
        int n = varargs.narg();
        if (n == 0) {
            return IoLib.freadline(file, false);
        }
        LuaValue[] luaValueArray = new LuaValue[n];
        int n2 = 0;
        while (n2 < n) {
            LuaValue luaValue;
            LuaValue luaValue2 = varargs.arg(n2 + 1);
            block0 : switch (luaValue2.type()) {
                case 3: {
                    luaValue = IoLib.freadbytes(file, luaValue2.toint());
                    break;
                }
                case 4: {
                    LuaString luaString = luaValue2.checkstring();
                    if (luaString.m_length >= 2 && luaString.m_bytes[luaString.m_offset] == 42) {
                        switch (luaString.m_bytes[luaString.m_offset + 1]) {
                            case 110: {
                                luaValue = IoLib.freadnumber(file);
                                break block0;
                            }
                            case 108: {
                                luaValue = IoLib.freadline(file, false);
                                break block0;
                            }
                            case 76: {
                                luaValue = IoLib.freadline(file, true);
                                break block0;
                            }
                            case 97: {
                                luaValue = IoLib.freadall(file);
                                break block0;
                            }
                        }
                    }
                }
                default: {
                    return IoLib.argerror(n2 + 1, "(invalid format)");
                }
            }
            if (!(luaValueArray[n2++] = luaValue).isnil()) continue;
        }
        return n2 == 0 ? NIL : IoLib.varargsOf(luaValueArray, 0, n2);
    }

    private static File checkfile(LuaValue luaValue) {
        File file = IoLib.optfile(luaValue);
        if (file == null) {
            IoLib.argerror(1, "file");
        }
        IoLib.checkopen(file);
        return file;
    }

    private static File optfile(LuaValue luaValue) {
        return luaValue instanceof File ? (File)luaValue : null;
    }

    private static File checkopen(File file) {
        if (file.isclosed()) {
            IoLib.error("attempt to use a closed file");
        }
        return file;
    }

    private File rawopenfile(int n, String string, String string2) throws IOException {
        int n2;
        int n3 = string2.length();
        for (n2 = 0; n2 < n3; n2 += 1) {
            char bl = string2.charAt(n2);
            if (!n2 && "rwa".indexOf(bl) >= 0 || n2 && bl == '+' || n2 >= 1 && bl == 'b') continue;
            n3 = -1;
            break;
        }
        if (n3 <= 0) {
            IoLib.argerror(2, "invalid mode: '" + string2 + "'");
        }
        switch (n) {
            case 0: {
                return this.wrapStdin();
            }
            case 1: {
                return this.wrapStdout();
            }
            case 2: {
                return this.wrapStderr();
            }
        }
        n2 = string2.startsWith("r") ? 1 : 0;
        boolean bl = string2.startsWith("a");
        boolean bl2 = string2.indexOf(43) > 0;
        boolean bl3 = string2.endsWith("b");
        return this.openFile(string, n2 != 0, bl, bl2, bl3);
    }

    public static LuaValue freadbytes(File file, int n) throws IOException {
        if (n == 0) {
            return file.eof() ? NIL : EMPTYSTRING;
        }
        byte[] byArray = new byte[n];
        int n2 = file.read(byArray, 0, byArray.length);
        if (n2 < 0) {
            return NIL;
        }
        return LuaString.valueUsing(byArray, 0, n2);
    }

    public static LuaValue freaduntil(File file, boolean bl, boolean bl2) throws IOException {
        int n;
        ByteArrayOutputStream byteArrayOutputStream;
        block10: {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                if (bl) {
                    block6: while ((n = file.read()) >= 0) {
                        switch (n) {
                            case 13: {
                                if (!bl2) continue block6;
                                byteArrayOutputStream.write(n);
                                break;
                            }
                            case 10: {
                                if (bl2) {
                                    byteArrayOutputStream.write(n);
                                }
                                break block10;
                            }
                            default: {
                                byteArrayOutputStream.write(n);
                                break;
                            }
                        }
                    }
                    break block10;
                }
                while ((n = file.read()) >= 0) {
                    byteArrayOutputStream.write(n);
                }
            } catch (EOFException eOFException) {
                n = -1;
            }
        }
        return n < 0 && byteArrayOutputStream.size() == 0 ? NIL : LuaString.valueUsing(byteArrayOutputStream.toByteArray());
    }

    public static LuaValue freadline(File file, boolean bl) throws IOException {
        return IoLib.freaduntil(file, true, bl);
    }

    public static LuaValue freadall(File file) throws IOException {
        int n = file.remaining();
        if (n >= 0) {
            return n == 0 ? EMPTYSTRING : IoLib.freadbytes(file, n);
        }
        return IoLib.freaduntil(file, false, false);
    }

    public static LuaValue freadnumber(File file) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IoLib.freadchars(file, " \t\r\n", null);
        IoLib.freadchars(file, "-+", byteArrayOutputStream);
        IoLib.freadchars(file, "0123456789", byteArrayOutputStream);
        IoLib.freadchars(file, ".", byteArrayOutputStream);
        IoLib.freadchars(file, "0123456789", byteArrayOutputStream);
        String string = byteArrayOutputStream.toString();
        return string.length() > 0 ? IoLib.valueOf(Double.parseDouble(string)) : NIL;
    }

    private static void freadchars(File file, String string, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        int n;
        while (string.indexOf(n = file.peek()) >= 0) {
            file.read();
            if (byteArrayOutputStream == null) continue;
            byteArrayOutputStream.write(n);
        }
        return;
    }

    protected abstract class File
    extends LuaValue {
        final IoLib this$0;

        protected File(IoLib ioLib) {
            this.this$0 = ioLib;
        }

        public abstract void write(LuaString var1) throws IOException;

        public abstract void flush() throws IOException;

        public abstract boolean isstdfile();

        public abstract void close() throws IOException;

        public abstract boolean isclosed();

        public abstract int seek(String var1, int var2) throws IOException;

        public abstract void setvbuf(String var1, int var2);

        public abstract int remaining() throws IOException;

        public abstract int peek() throws IOException;

        public abstract int read() throws IOException;

        public abstract int read(byte[] var1, int var2, int var3) throws IOException;

        public boolean eof() throws IOException {
            try {
                return this.peek() < 0;
            } catch (EOFException eOFException) {
                return false;
            }
        }

        @Override
        public LuaValue get(LuaValue luaValue) {
            return this.this$0.filemethods.get(luaValue);
        }

        @Override
        public int type() {
            return 0;
        }

        @Override
        public String typename() {
            return "userdata";
        }

        @Override
        public String tojstring() {
            return "file: " + Integer.toHexString(this.hashCode());
        }

        protected void finalize() {
            if (!this.isclosed()) {
                try {
                    this.close();
                } catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
    }

    static final class IoLibV
    extends VarArgFunction {
        private File f;
        public IoLib iolib;
        private boolean toclose;
        private Varargs args;

        public IoLibV() {
        }

        public IoLibV(File file, String string, int n, IoLib ioLib, boolean bl, Varargs varargs) {
            this(file, string, n, ioLib);
            this.toclose = bl;
            this.args = varargs.dealias();
        }

        public IoLibV(File file, String string, int n, IoLib ioLib) {
            this.f = file;
            this.name = string;
            this.opcode = n;
            this.iolib = ioLib;
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            try {
                switch (this.opcode) {
                    case 1: {
                        return this.iolib._io_flush();
                    }
                    case 8: {
                        return this.iolib._io_tmpfile();
                    }
                    case 0: {
                        return this.iolib._io_close(varargs.arg1());
                    }
                    case 2: {
                        return this.iolib._io_input(varargs.arg1());
                    }
                    case 5: {
                        return this.iolib._io_output(varargs.arg1());
                    }
                    case 9: {
                        return this.iolib._io_type(varargs.arg1());
                    }
                    case 6: {
                        return this.iolib._io_popen(varargs.checkjstring(1), varargs.optjstring(2, "r"));
                    }
                    case 4: {
                        return this.iolib._io_open(varargs.checkjstring(1), varargs.optjstring(2, "r"));
                    }
                    case 3: {
                        return this.iolib._io_lines(varargs);
                    }
                    case 7: {
                        return this.iolib._io_read(varargs);
                    }
                    case 10: {
                        return this.iolib._io_write(varargs);
                    }
                    case 11: {
                        return this.iolib._file_close(varargs.arg1());
                    }
                    case 12: {
                        return this.iolib._file_flush(varargs.arg1());
                    }
                    case 16: {
                        return this.iolib._file_setvbuf(varargs.arg1(), varargs.checkjstring(2), varargs.optint(3, 8192));
                    }
                    case 13: {
                        return this.iolib._file_lines(varargs);
                    }
                    case 14: {
                        return this.iolib._file_read(varargs.arg1(), varargs.subargs(2));
                    }
                    case 15: {
                        return this.iolib._file_seek(varargs.arg1(), varargs.optjstring(2, "cur"), varargs.optint(3, 0));
                    }
                    case 17: {
                        return this.iolib._file_write(varargs.arg1(), varargs.subargs(2));
                    }
                    case 18: {
                        return this.iolib._io_index(varargs.arg(2));
                    }
                    case 19: {
                        return this.iolib._lines_iter(this.f, this.toclose, this.args);
                    }
                }
            } catch (IOException iOException) {
                if (this.opcode == 19) {
                    String string = iOException.getMessage();
                    IoLibV.error(string != null ? string : iOException.toString());
                }
                return IoLib.errorresult(iOException);
            }
            return NONE;
        }
    }
}

