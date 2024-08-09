/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler.jse;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.compiler.jse.JseProcess;
import mpp.venusfr.scripts.interpreter.lib.OsLib;

public class JseOsLib
extends OsLib {
    public static final int EXEC_IOEXCEPTION = 1;
    public static final int EXEC_INTERRUPTED = -2;
    public static final int EXEC_ERROR = -3;

    @Override
    protected String getenv(String string) {
        String string2 = System.getenv(string);
        return string2 != null ? string2 : System.getProperty(string);
    }

    @Override
    protected Varargs execute(String string) {
        int n;
        try {
            n = new JseProcess(string, null, (OutputStream)this.globals.STDOUT, (OutputStream)this.globals.STDERR).waitFor();
        } catch (IOException iOException) {
            n = 1;
        } catch (InterruptedException interruptedException) {
            n = -2;
        } catch (Throwable throwable) {
            n = -3;
        }
        if (n == 0) {
            return JseOsLib.varargsOf(TRUE, JseOsLib.valueOf("exit"), ZERO);
        }
        return JseOsLib.varargsOf(NIL, JseOsLib.valueOf("signal"), JseOsLib.valueOf(n));
    }

    @Override
    protected void remove(String string) throws IOException {
        File file = new File(string);
        if (!file.exists()) {
            throw new IOException("No such file or directory");
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete");
        }
    }

    @Override
    protected void rename(String string, String string2) throws IOException {
        File file = new File(string);
        if (!file.exists()) {
            throw new IOException("No such file or directory");
        }
        if (!file.renameTo(new File(string2))) {
            throw new IOException("Failed to rename");
        }
    }

    @Override
    protected String tmpname() {
        try {
            File file = File.createTempFile(".luaj", "tmp");
            return file.getAbsolutePath();
        } catch (IOException iOException) {
            return super.tmpname();
        }
    }
}

