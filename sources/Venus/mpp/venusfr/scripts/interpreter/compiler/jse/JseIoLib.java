/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler.jse;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import mpp.venusfr.scripts.interpreter.Globals;
import mpp.venusfr.scripts.interpreter.LuaError;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.lib.IoLib;

public class JseIoLib
extends IoLib {
    @Override
    protected IoLib.File wrapStdin() throws IOException {
        return new StdinFile(this);
    }

    @Override
    protected IoLib.File wrapStdout() throws IOException {
        return new StdoutFile(this, 1);
    }

    @Override
    protected IoLib.File wrapStderr() throws IOException {
        return new StdoutFile(this, 2);
    }

    @Override
    protected IoLib.File openFile(String string, boolean bl, boolean bl2, boolean bl3, boolean bl4) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(string, bl ? "r" : "rw");
        if (bl2) {
            randomAccessFile.seek(randomAccessFile.length());
        } else if (!bl) {
            randomAccessFile.setLength(0L);
        }
        return new FileImpl(this, randomAccessFile);
    }

    @Override
    protected IoLib.File openProgram(String string, String string2) throws IOException {
        Process process = Runtime.getRuntime().exec(string);
        return "w".equals(string2) ? new FileImpl(this, process.getOutputStream()) : new FileImpl(this, process.getInputStream());
    }

    @Override
    protected IoLib.File tmpFile() throws IOException {
        File file = File.createTempFile(".luaj", "bin");
        file.deleteOnExit();
        return new FileImpl(this, new RandomAccessFile(file, "rw"));
    }

    private static void notimplemented() {
        throw new LuaError("not implemented");
    }

    static Globals access$000(JseIoLib jseIoLib) {
        return jseIoLib.globals;
    }

    static Globals access$100(JseIoLib jseIoLib) {
        return jseIoLib.globals;
    }

    static Globals access$200(JseIoLib jseIoLib) {
        return jseIoLib.globals;
    }

    static Globals access$300(JseIoLib jseIoLib) {
        return jseIoLib.globals;
    }

    static Globals access$400(JseIoLib jseIoLib) {
        return jseIoLib.globals;
    }

    static Globals access$500(JseIoLib jseIoLib) {
        return jseIoLib.globals;
    }

    static Globals access$600(JseIoLib jseIoLib) {
        return jseIoLib.globals;
    }

    private final class StdinFile
    extends IoLib.File {
        final JseIoLib this$0;

        private StdinFile(JseIoLib jseIoLib) {
            this.this$0 = jseIoLib;
            super(jseIoLib);
        }

        @Override
        public String tojstring() {
            return "file (" + this.hashCode() + ")";
        }

        @Override
        public void write(LuaString luaString) throws IOException {
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        public boolean isstdfile() {
            return false;
        }

        @Override
        public void close() throws IOException {
        }

        @Override
        public boolean isclosed() {
            return true;
        }

        @Override
        public int seek(String string, int n) throws IOException {
            return 1;
        }

        @Override
        public void setvbuf(String string, int n) {
        }

        @Override
        public int remaining() throws IOException {
            return 1;
        }

        @Override
        public int peek() throws IOException {
            JseIoLib.access$200((JseIoLib)this.this$0).STDIN.mark(1);
            int n = JseIoLib.access$300((JseIoLib)this.this$0).STDIN.read();
            JseIoLib.access$400((JseIoLib)this.this$0).STDIN.reset();
            return n;
        }

        @Override
        public int read() throws IOException {
            return JseIoLib.access$500((JseIoLib)this.this$0).STDIN.read();
        }

        @Override
        public int read(byte[] byArray, int n, int n2) throws IOException {
            return JseIoLib.access$600((JseIoLib)this.this$0).STDIN.read(byArray, n, n2);
        }
    }

    private final class StdoutFile
    extends IoLib.File {
        private final int file_type;
        final JseIoLib this$0;

        private StdoutFile(JseIoLib jseIoLib, int n) {
            this.this$0 = jseIoLib;
            super(jseIoLib);
            this.file_type = n;
        }

        @Override
        public String tojstring() {
            return "file (" + this.hashCode() + ")";
        }

        private PrintStream getPrintStream() {
            return this.file_type == 2 ? JseIoLib.access$000((JseIoLib)this.this$0).STDERR : JseIoLib.access$100((JseIoLib)this.this$0).STDOUT;
        }

        @Override
        public void write(LuaString luaString) throws IOException {
            this.getPrintStream().write(luaString.m_bytes, luaString.m_offset, luaString.m_length);
        }

        @Override
        public void flush() throws IOException {
            this.getPrintStream().flush();
        }

        @Override
        public boolean isstdfile() {
            return false;
        }

        @Override
        public void close() throws IOException {
        }

        @Override
        public boolean isclosed() {
            return true;
        }

        @Override
        public int seek(String string, int n) throws IOException {
            return 1;
        }

        @Override
        public void setvbuf(String string, int n) {
        }

        @Override
        public int remaining() throws IOException {
            return 1;
        }

        @Override
        public int peek() throws IOException {
            return 1;
        }

        @Override
        public int read() throws IOException {
            return 1;
        }

        @Override
        public int read(byte[] byArray, int n, int n2) throws IOException {
            return 1;
        }
    }

    private final class FileImpl
    extends IoLib.File {
        private final RandomAccessFile file;
        private final InputStream is;
        private final OutputStream os;
        private boolean closed;
        private boolean nobuffer;
        final JseIoLib this$0;

        private FileImpl(JseIoLib jseIoLib, RandomAccessFile randomAccessFile, InputStream inputStream, OutputStream outputStream) {
            this.this$0 = jseIoLib;
            super(jseIoLib);
            this.closed = false;
            this.nobuffer = false;
            this.file = randomAccessFile;
            this.is = inputStream != null ? (inputStream.markSupported() ? inputStream : new BufferedInputStream(inputStream)) : null;
            this.os = outputStream;
        }

        private FileImpl(JseIoLib jseIoLib, RandomAccessFile randomAccessFile) {
            this(jseIoLib, randomAccessFile, null, null);
        }

        private FileImpl(JseIoLib jseIoLib, InputStream inputStream) {
            this(jseIoLib, null, inputStream, null);
        }

        private FileImpl(JseIoLib jseIoLib, OutputStream outputStream) {
            this(jseIoLib, null, null, outputStream);
        }

        @Override
        public String tojstring() {
            return "file (" + (this.closed ? "closed" : String.valueOf(this.hashCode())) + ")";
        }

        @Override
        public boolean isstdfile() {
            return this.file == null;
        }

        @Override
        public void close() throws IOException {
            this.closed = true;
            if (this.file != null) {
                this.file.close();
            }
        }

        @Override
        public void flush() throws IOException {
            if (this.os != null) {
                this.os.flush();
            }
        }

        @Override
        public void write(LuaString luaString) throws IOException {
            if (this.os != null) {
                this.os.write(luaString.m_bytes, luaString.m_offset, luaString.m_length);
            } else if (this.file != null) {
                this.file.write(luaString.m_bytes, luaString.m_offset, luaString.m_length);
            } else {
                JseIoLib.notimplemented();
            }
            if (this.nobuffer) {
                this.flush();
            }
        }

        @Override
        public boolean isclosed() {
            return this.closed;
        }

        @Override
        public int seek(String string, int n) throws IOException {
            if (this.file != null) {
                if ("set".equals(string)) {
                    this.file.seek(n);
                } else if ("end".equals(string)) {
                    this.file.seek(this.file.length() + (long)n);
                } else {
                    this.file.seek(this.file.getFilePointer() + (long)n);
                }
                return (int)this.file.getFilePointer();
            }
            JseIoLib.notimplemented();
            return 1;
        }

        @Override
        public void setvbuf(String string, int n) {
            this.nobuffer = "no".equals(string);
        }

        @Override
        public int remaining() throws IOException {
            return this.file != null ? (int)(this.file.length() - this.file.getFilePointer()) : -1;
        }

        @Override
        public int peek() throws IOException {
            if (this.is != null) {
                this.is.mark(1);
                int n = this.is.read();
                this.is.reset();
                return n;
            }
            if (this.file != null) {
                long l = this.file.getFilePointer();
                int n = this.file.read();
                this.file.seek(l);
                return n;
            }
            JseIoLib.notimplemented();
            return 1;
        }

        @Override
        public int read() throws IOException {
            if (this.is != null) {
                return this.is.read();
            }
            if (this.file != null) {
                return this.file.read();
            }
            JseIoLib.notimplemented();
            return 1;
        }

        @Override
        public int read(byte[] byArray, int n, int n2) throws IOException {
            if (this.file != null) {
                return this.file.read(byArray, n, n2);
            }
            if (this.is != null) {
                return this.is.read(byArray, n, n2);
            }
            JseIoLib.notimplemented();
            return n2;
        }
    }
}

