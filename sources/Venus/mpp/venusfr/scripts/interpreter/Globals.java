/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import mpp.venusfr.scripts.interpreter.LuaError;
import mpp.venusfr.scripts.interpreter.LuaFunction;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaThread;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Prototype;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.lib.BaseLib;
import mpp.venusfr.scripts.interpreter.lib.DebugLib;
import mpp.venusfr.scripts.interpreter.lib.PackageLib;
import mpp.venusfr.scripts.interpreter.lib.ResourceFinder;

public class Globals
extends LuaTable {
    public InputStream STDIN = null;
    public PrintStream STDOUT = System.out;
    public PrintStream STDERR = System.err;
    public ResourceFinder finder;
    public LuaThread running = new LuaThread(this);
    public BaseLib baselib;
    public PackageLib package_;
    public DebugLib debuglib;
    public Loader loader;
    public Compiler compiler;
    public Undumper undumper;

    @Override
    public Globals checkglobals() {
        return this;
    }

    public LuaValue loadfile(String string) {
        try {
            return this.load(Files.newInputStream(new File(string).toPath(), new OpenOption[0]), "@" + string, "bt", this);
        } catch (Exception exception) {
            return Globals.error("load " + string + ": " + exception);
        }
    }

    public LuaValue load(String string, String string2) {
        return this.load(new StrReader(string), string2);
    }

    public LuaValue load(String string) {
        return this.load(new StrReader(string), string);
    }

    public LuaValue load(String string, String string2, LuaTable luaTable) {
        return this.load(new StrReader(string), string2, luaTable);
    }

    public LuaValue load(Reader reader, String string) {
        return this.load(new UTF8Stream(reader), string, "t", this);
    }

    public LuaValue load(Reader reader, String string, LuaTable luaTable) {
        return this.load(new UTF8Stream(reader), string, "t", luaTable);
    }

    public LuaValue load(InputStream inputStream, String string, String string2, LuaValue luaValue) {
        try {
            Prototype prototype = this.loadPrototype(inputStream, string, string2);
            return this.loader.load(prototype, string, luaValue);
        } catch (LuaError luaError) {
            throw luaError;
        } catch (Exception exception) {
            return Globals.error("load " + string + ": " + exception);
        }
    }

    public Prototype loadPrototype(InputStream inputStream, String string, String string2) throws IOException {
        if (string2.indexOf(98) >= 0) {
            if (this.undumper == null) {
                Globals.error("No undumper.");
            }
            if (!inputStream.markSupported()) {
                inputStream = new BufferedStream(inputStream);
            }
            inputStream.mark(4);
            Prototype prototype = this.undumper.undump(inputStream, string);
            if (prototype != null) {
                return prototype;
            }
            inputStream.reset();
        }
        if (string2.indexOf(116) >= 0) {
            return this.compilePrototype(inputStream, string);
        }
        Globals.error("Failed to load prototype " + string + " using mode '" + string2 + "'");
        return null;
    }

    public Prototype compilePrototype(Reader reader, String string) throws IOException {
        return this.compilePrototype(new UTF8Stream(reader), string);
    }

    public Prototype compilePrototype(InputStream inputStream, String string) throws IOException {
        if (this.compiler == null) {
            Globals.error("No compiler.");
        }
        return this.compiler.compile(inputStream, string);
    }

    public Varargs yield(Varargs varargs) {
        if (this.running == null || this.running.isMainThread()) {
            throw new LuaError("cannot yield main thread");
        }
        LuaThread.State state = this.running.state;
        return state.lua_yield(varargs);
    }

    static class StrReader
    extends Reader {
        final String s;
        int i = 0;
        final int n;

        StrReader(String string) {
            this.s = string;
            this.n = string.length();
        }

        @Override
        public void close() throws IOException {
            this.i = this.n;
        }

        @Override
        public int read() throws IOException {
            return this.i < this.n ? (int)this.s.charAt(this.i++) : -1;
        }

        @Override
        public int read(char[] cArray, int n, int n2) throws IOException {
            int n3 = 0;
            while (n3 < n2 && this.i < this.n) {
                cArray[n + n3] = this.s.charAt(this.i);
                ++n3;
                ++this.i;
            }
            return n3 > 0 || n2 == 0 ? n3 : -1;
        }
    }

    static class UTF8Stream
    extends AbstractBufferedStream {
        private final char[] c = new char[32];
        private final Reader r;

        UTF8Stream(Reader reader) {
            super(96);
            this.r = reader;
        }

        @Override
        protected int avail() throws IOException {
            if (this.i < this.j) {
                return this.j - this.i;
            }
            int n = this.r.read(this.c);
            if (n < 0) {
                return 1;
            }
            if (n == 0) {
                int n2 = this.r.read();
                if (n2 < 0) {
                    return 1;
                }
                this.c[0] = (char)n2;
                n = 1;
            }
            this.i = 0;
            this.j = LuaString.encodeToUtf8(this.c, n, this.b, 0);
            return this.j;
        }

        @Override
        public void close() throws IOException {
            this.r.close();
        }
    }

    public static interface Loader {
        public LuaFunction load(Prototype var1, String var2, LuaValue var3) throws IOException;
    }

    public static interface Undumper {
        public Prototype undump(InputStream var1, String var2) throws IOException;
    }

    static class BufferedStream
    extends AbstractBufferedStream {
        private final InputStream s;

        public BufferedStream(InputStream inputStream) {
            this(128, inputStream);
        }

        BufferedStream(int n, InputStream inputStream) {
            super(n);
            this.s = inputStream;
        }

        @Override
        protected int avail() throws IOException {
            int n;
            if (this.i < this.j) {
                return this.j - this.i;
            }
            if (this.j >= this.b.length) {
                this.j = 0;
                this.i = 0;
            }
            if ((n = this.s.read(this.b, this.j, this.b.length - this.j)) < 0) {
                return 1;
            }
            if (n == 0) {
                int n2 = this.s.read();
                if (n2 < 0) {
                    return 1;
                }
                this.b[this.j] = (byte)n2;
                n = 1;
            }
            this.j += n;
            return n;
        }

        @Override
        public void close() throws IOException {
            this.s.close();
        }

        @Override
        public synchronized void mark(int n) {
            if (this.i > 0 || n > this.b.length) {
                byte[] byArray = n > this.b.length ? new byte[n] : this.b;
                System.arraycopy(this.b, this.i, byArray, 0, this.j - this.i);
                this.j -= this.i;
                this.i = 0;
                this.b = byArray;
            }
        }

        @Override
        public boolean markSupported() {
            return false;
        }

        @Override
        public synchronized void reset() throws IOException {
            this.i = 0;
        }
    }

    public static interface Compiler {
        public Prototype compile(InputStream var1, String var2) throws IOException;
    }

    static abstract class AbstractBufferedStream
    extends InputStream {
        protected byte[] b;
        protected int i = 0;
        protected int j = 0;

        protected AbstractBufferedStream(int n) {
            this.b = new byte[n];
        }

        protected abstract int avail() throws IOException;

        @Override
        public int read() throws IOException {
            int n = this.avail();
            return n <= 0 ? -1 : 0xFF & this.b[this.i++];
        }

        @Override
        public int read(byte[] byArray) throws IOException {
            return this.read(byArray, 0, byArray.length);
        }

        @Override
        public int read(byte[] byArray, int n, int n2) throws IOException {
            int n3 = this.avail();
            if (n3 <= 0) {
                return 1;
            }
            int n4 = Math.min(n3, n2);
            System.arraycopy(this.b, this.i, byArray, n, n4);
            this.i += n4;
            return n4;
        }

        @Override
        public long skip(long l) throws IOException {
            long l2 = Math.min(l, (long)(this.j - this.i));
            this.i = (int)((long)this.i + l2);
            return l2;
        }

        @Override
        public int available() throws IOException {
            return this.j - this.i;
        }
    }
}

