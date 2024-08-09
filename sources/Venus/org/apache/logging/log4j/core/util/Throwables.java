/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.core.util.Closer;

public final class Throwables {
    private Throwables() {
    }

    public static Throwable getRootCause(Throwable throwable) {
        Throwable throwable2;
        Throwable throwable3 = throwable;
        while ((throwable2 = throwable3.getCause()) != null) {
            throwable3 = throwable2;
        }
        return throwable3;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static List<String> toStringList(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        try {
            throwable.printStackTrace(printWriter);
        } catch (RuntimeException runtimeException) {
            // empty catch block
        }
        printWriter.flush();
        ArrayList<String> arrayList = new ArrayList<String>();
        LineNumberReader lineNumberReader = new LineNumberReader(new StringReader(stringWriter.toString()));
        try {
            String string = lineNumberReader.readLine();
            while (string != null) {
                arrayList.add(string);
                string = lineNumberReader.readLine();
            }
        } catch (IOException iOException) {
            if (iOException instanceof InterruptedIOException) {
                Thread.currentThread().interrupt();
            }
            arrayList.add(iOException.toString());
        } finally {
            Closer.closeSilently(lineNumberReader);
        }
        return arrayList;
    }

    public static void rethrow(Throwable throwable) {
        Throwables.rethrow0(throwable);
    }

    private static <T extends Throwable> void rethrow0(Throwable throwable) throws T {
        throw throwable;
    }
}

