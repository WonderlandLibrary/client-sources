/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.StringLayout;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.appender.ManagerFactory;

public class WriterManager
extends AbstractManager {
    protected final StringLayout layout;
    private volatile Writer writer;

    public static <T> WriterManager getManager(String string, T t, ManagerFactory<? extends WriterManager, T> managerFactory) {
        return AbstractManager.getManager(string, managerFactory, t);
    }

    public WriterManager(Writer writer, String string, StringLayout stringLayout, boolean bl) {
        super(null, string);
        byte[] byArray;
        this.writer = writer;
        this.layout = stringLayout;
        if (bl && stringLayout != null && (byArray = stringLayout.getHeader()) != null) {
            try {
                this.writer.write(new String(byArray, stringLayout.getCharset()));
            } catch (IOException iOException) {
                this.logError("Unable to write header", iOException);
            }
        }
    }

    protected synchronized void closeWriter() {
        Writer writer = this.writer;
        try {
            writer.close();
        } catch (IOException iOException) {
            this.logError("Unable to close stream", iOException);
        }
    }

    public synchronized void flush() {
        try {
            this.writer.flush();
        } catch (IOException iOException) {
            String string = "Error flushing stream " + this.getName();
            throw new AppenderLoggingException(string, iOException);
        }
    }

    protected Writer getWriter() {
        return this.writer;
    }

    public boolean isOpen() {
        return this.getCount() > 0;
    }

    @Override
    public boolean releaseSub(long l, TimeUnit timeUnit) {
        this.writeFooter();
        this.closeWriter();
        return false;
    }

    protected void setWriter(Writer writer) {
        byte[] byArray = this.layout.getHeader();
        if (byArray != null) {
            try {
                writer.write(new String(byArray, this.layout.getCharset()));
                this.writer = writer;
            } catch (IOException iOException) {
                this.logError("Unable to write header", iOException);
            }
        } else {
            this.writer = writer;
        }
    }

    protected synchronized void write(String string) {
        try {
            this.writer.write(string);
        } catch (IOException iOException) {
            String string2 = "Error writing to stream " + this.getName();
            throw new AppenderLoggingException(string2, iOException);
        }
    }

    protected void writeFooter() {
        if (this.layout == null) {
            return;
        }
        byte[] byArray = this.layout.getFooter();
        if (byArray != null && byArray.length > 0) {
            this.write(new String(byArray, this.layout.getCharset()));
        }
    }
}

