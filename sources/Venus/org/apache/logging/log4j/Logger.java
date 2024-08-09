/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.EntryMessage;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.util.MessageSupplier;
import org.apache.logging.log4j.util.Supplier;

public interface Logger {
    public void catching(Level var1, Throwable var2);

    public void catching(Throwable var1);

    public void debug(Marker var1, Message var2);

    public void debug(Marker var1, Message var2, Throwable var3);

    public void debug(Marker var1, MessageSupplier var2);

    public void debug(Marker var1, MessageSupplier var2, Throwable var3);

    public void debug(Marker var1, CharSequence var2);

    public void debug(Marker var1, CharSequence var2, Throwable var3);

    public void debug(Marker var1, Object var2);

    public void debug(Marker var1, Object var2, Throwable var3);

    public void debug(Marker var1, String var2);

    public void debug(Marker var1, String var2, Object ... var3);

    public void debug(Marker var1, String var2, Supplier<?> ... var3);

    public void debug(Marker var1, String var2, Throwable var3);

    public void debug(Marker var1, Supplier<?> var2);

    public void debug(Marker var1, Supplier<?> var2, Throwable var3);

    public void debug(Message var1);

    public void debug(Message var1, Throwable var2);

    public void debug(MessageSupplier var1);

    public void debug(MessageSupplier var1, Throwable var2);

    public void debug(CharSequence var1);

    public void debug(CharSequence var1, Throwable var2);

    public void debug(Object var1);

    public void debug(Object var1, Throwable var2);

    public void debug(String var1);

    public void debug(String var1, Object ... var2);

    public void debug(String var1, Supplier<?> ... var2);

    public void debug(String var1, Throwable var2);

    public void debug(Supplier<?> var1);

    public void debug(Supplier<?> var1, Throwable var2);

    public void debug(Marker var1, String var2, Object var3);

    public void debug(Marker var1, String var2, Object var3, Object var4);

    public void debug(Marker var1, String var2, Object var3, Object var4, Object var5);

    public void debug(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6);

    public void debug(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7);

    public void debug(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8);

    public void debug(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9);

    public void debug(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10);

    public void debug(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11);

    public void debug(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12);

    public void debug(String var1, Object var2);

    public void debug(String var1, Object var2, Object var3);

    public void debug(String var1, Object var2, Object var3, Object var4);

    public void debug(String var1, Object var2, Object var3, Object var4, Object var5);

    public void debug(String var1, Object var2, Object var3, Object var4, Object var5, Object var6);

    public void debug(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7);

    public void debug(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8);

    public void debug(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9);

    public void debug(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10);

    public void debug(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11);

    @Deprecated
    public void entry();

    public void entry(Object ... var1);

    public void error(Marker var1, Message var2);

    public void error(Marker var1, Message var2, Throwable var3);

    public void error(Marker var1, MessageSupplier var2);

    public void error(Marker var1, MessageSupplier var2, Throwable var3);

    public void error(Marker var1, CharSequence var2);

    public void error(Marker var1, CharSequence var2, Throwable var3);

    public void error(Marker var1, Object var2);

    public void error(Marker var1, Object var2, Throwable var3);

    public void error(Marker var1, String var2);

    public void error(Marker var1, String var2, Object ... var3);

    public void error(Marker var1, String var2, Supplier<?> ... var3);

    public void error(Marker var1, String var2, Throwable var3);

    public void error(Marker var1, Supplier<?> var2);

    public void error(Marker var1, Supplier<?> var2, Throwable var3);

    public void error(Message var1);

    public void error(Message var1, Throwable var2);

    public void error(MessageSupplier var1);

    public void error(MessageSupplier var1, Throwable var2);

    public void error(CharSequence var1);

    public void error(CharSequence var1, Throwable var2);

    public void error(Object var1);

    public void error(Object var1, Throwable var2);

    public void error(String var1);

    public void error(String var1, Object ... var2);

    public void error(String var1, Supplier<?> ... var2);

    public void error(String var1, Throwable var2);

    public void error(Supplier<?> var1);

    public void error(Supplier<?> var1, Throwable var2);

    public void error(Marker var1, String var2, Object var3);

    public void error(Marker var1, String var2, Object var3, Object var4);

    public void error(Marker var1, String var2, Object var3, Object var4, Object var5);

    public void error(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6);

    public void error(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7);

    public void error(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8);

    public void error(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9);

    public void error(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10);

    public void error(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11);

    public void error(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12);

    public void error(String var1, Object var2);

    public void error(String var1, Object var2, Object var3);

    public void error(String var1, Object var2, Object var3, Object var4);

    public void error(String var1, Object var2, Object var3, Object var4, Object var5);

    public void error(String var1, Object var2, Object var3, Object var4, Object var5, Object var6);

    public void error(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7);

    public void error(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8);

    public void error(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9);

    public void error(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10);

    public void error(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11);

    @Deprecated
    public void exit();

    @Deprecated
    public <R> R exit(R var1);

    public void fatal(Marker var1, Message var2);

    public void fatal(Marker var1, Message var2, Throwable var3);

    public void fatal(Marker var1, MessageSupplier var2);

    public void fatal(Marker var1, MessageSupplier var2, Throwable var3);

    public void fatal(Marker var1, CharSequence var2);

    public void fatal(Marker var1, CharSequence var2, Throwable var3);

    public void fatal(Marker var1, Object var2);

    public void fatal(Marker var1, Object var2, Throwable var3);

    public void fatal(Marker var1, String var2);

    public void fatal(Marker var1, String var2, Object ... var3);

    public void fatal(Marker var1, String var2, Supplier<?> ... var3);

    public void fatal(Marker var1, String var2, Throwable var3);

    public void fatal(Marker var1, Supplier<?> var2);

    public void fatal(Marker var1, Supplier<?> var2, Throwable var3);

    public void fatal(Message var1);

    public void fatal(Message var1, Throwable var2);

    public void fatal(MessageSupplier var1);

    public void fatal(MessageSupplier var1, Throwable var2);

    public void fatal(CharSequence var1);

    public void fatal(CharSequence var1, Throwable var2);

    public void fatal(Object var1);

    public void fatal(Object var1, Throwable var2);

    public void fatal(String var1);

    public void fatal(String var1, Object ... var2);

    public void fatal(String var1, Supplier<?> ... var2);

    public void fatal(String var1, Throwable var2);

    public void fatal(Supplier<?> var1);

    public void fatal(Supplier<?> var1, Throwable var2);

    public void fatal(Marker var1, String var2, Object var3);

    public void fatal(Marker var1, String var2, Object var3, Object var4);

    public void fatal(Marker var1, String var2, Object var3, Object var4, Object var5);

    public void fatal(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6);

    public void fatal(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7);

    public void fatal(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8);

    public void fatal(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9);

    public void fatal(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10);

    public void fatal(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11);

    public void fatal(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12);

    public void fatal(String var1, Object var2);

    public void fatal(String var1, Object var2, Object var3);

    public void fatal(String var1, Object var2, Object var3, Object var4);

    public void fatal(String var1, Object var2, Object var3, Object var4, Object var5);

    public void fatal(String var1, Object var2, Object var3, Object var4, Object var5, Object var6);

    public void fatal(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7);

    public void fatal(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8);

    public void fatal(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9);

    public void fatal(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10);

    public void fatal(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11);

    public Level getLevel();

    public <MF extends MessageFactory> MF getMessageFactory();

    public String getName();

    public void info(Marker var1, Message var2);

    public void info(Marker var1, Message var2, Throwable var3);

    public void info(Marker var1, MessageSupplier var2);

    public void info(Marker var1, MessageSupplier var2, Throwable var3);

    public void info(Marker var1, CharSequence var2);

    public void info(Marker var1, CharSequence var2, Throwable var3);

    public void info(Marker var1, Object var2);

    public void info(Marker var1, Object var2, Throwable var3);

    public void info(Marker var1, String var2);

    public void info(Marker var1, String var2, Object ... var3);

    public void info(Marker var1, String var2, Supplier<?> ... var3);

    public void info(Marker var1, String var2, Throwable var3);

    public void info(Marker var1, Supplier<?> var2);

    public void info(Marker var1, Supplier<?> var2, Throwable var3);

    public void info(Message var1);

    public void info(Message var1, Throwable var2);

    public void info(MessageSupplier var1);

    public void info(MessageSupplier var1, Throwable var2);

    public void info(CharSequence var1);

    public void info(CharSequence var1, Throwable var2);

    public void info(Object var1);

    public void info(Object var1, Throwable var2);

    public void info(String var1);

    public void info(String var1, Object ... var2);

    public void info(String var1, Supplier<?> ... var2);

    public void info(String var1, Throwable var2);

    public void info(Supplier<?> var1);

    public void info(Supplier<?> var1, Throwable var2);

    public void info(Marker var1, String var2, Object var3);

    public void info(Marker var1, String var2, Object var3, Object var4);

    public void info(Marker var1, String var2, Object var3, Object var4, Object var5);

    public void info(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6);

    public void info(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7);

    public void info(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8);

    public void info(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9);

    public void info(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10);

    public void info(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11);

    public void info(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12);

    public void info(String var1, Object var2);

    public void info(String var1, Object var2, Object var3);

    public void info(String var1, Object var2, Object var3, Object var4);

    public void info(String var1, Object var2, Object var3, Object var4, Object var5);

    public void info(String var1, Object var2, Object var3, Object var4, Object var5, Object var6);

    public void info(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7);

    public void info(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8);

    public void info(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9);

    public void info(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10);

    public void info(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11);

    public boolean isDebugEnabled();

    public boolean isDebugEnabled(Marker var1);

    public boolean isEnabled(Level var1);

    public boolean isEnabled(Level var1, Marker var2);

    public boolean isErrorEnabled();

    public boolean isErrorEnabled(Marker var1);

    public boolean isFatalEnabled();

    public boolean isFatalEnabled(Marker var1);

    public boolean isInfoEnabled();

    public boolean isInfoEnabled(Marker var1);

    public boolean isTraceEnabled();

    public boolean isTraceEnabled(Marker var1);

    public boolean isWarnEnabled();

    public boolean isWarnEnabled(Marker var1);

    public void log(Level var1, Marker var2, Message var3);

    public void log(Level var1, Marker var2, Message var3, Throwable var4);

    public void log(Level var1, Marker var2, MessageSupplier var3);

    public void log(Level var1, Marker var2, MessageSupplier var3, Throwable var4);

    public void log(Level var1, Marker var2, CharSequence var3);

    public void log(Level var1, Marker var2, CharSequence var3, Throwable var4);

    public void log(Level var1, Marker var2, Object var3);

    public void log(Level var1, Marker var2, Object var3, Throwable var4);

    public void log(Level var1, Marker var2, String var3);

    public void log(Level var1, Marker var2, String var3, Object ... var4);

    public void log(Level var1, Marker var2, String var3, Supplier<?> ... var4);

    public void log(Level var1, Marker var2, String var3, Throwable var4);

    public void log(Level var1, Marker var2, Supplier<?> var3);

    public void log(Level var1, Marker var2, Supplier<?> var3, Throwable var4);

    public void log(Level var1, Message var2);

    public void log(Level var1, Message var2, Throwable var3);

    public void log(Level var1, MessageSupplier var2);

    public void log(Level var1, MessageSupplier var2, Throwable var3);

    public void log(Level var1, CharSequence var2);

    public void log(Level var1, CharSequence var2, Throwable var3);

    public void log(Level var1, Object var2);

    public void log(Level var1, Object var2, Throwable var3);

    public void log(Level var1, String var2);

    public void log(Level var1, String var2, Object ... var3);

    public void log(Level var1, String var2, Supplier<?> ... var3);

    public void log(Level var1, String var2, Throwable var3);

    public void log(Level var1, Supplier<?> var2);

    public void log(Level var1, Supplier<?> var2, Throwable var3);

    public void log(Level var1, Marker var2, String var3, Object var4);

    public void log(Level var1, Marker var2, String var3, Object var4, Object var5);

    public void log(Level var1, Marker var2, String var3, Object var4, Object var5, Object var6);

    public void log(Level var1, Marker var2, String var3, Object var4, Object var5, Object var6, Object var7);

    public void log(Level var1, Marker var2, String var3, Object var4, Object var5, Object var6, Object var7, Object var8);

    public void log(Level var1, Marker var2, String var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9);

    public void log(Level var1, Marker var2, String var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10);

    public void log(Level var1, Marker var2, String var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11);

    public void log(Level var1, Marker var2, String var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12);

    public void log(Level var1, Marker var2, String var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12, Object var13);

    public void log(Level var1, String var2, Object var3);

    public void log(Level var1, String var2, Object var3, Object var4);

    public void log(Level var1, String var2, Object var3, Object var4, Object var5);

    public void log(Level var1, String var2, Object var3, Object var4, Object var5, Object var6);

    public void log(Level var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7);

    public void log(Level var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8);

    public void log(Level var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9);

    public void log(Level var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10);

    public void log(Level var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11);

    public void log(Level var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12);

    public void printf(Level var1, Marker var2, String var3, Object ... var4);

    public void printf(Level var1, String var2, Object ... var3);

    public <T extends Throwable> T throwing(Level var1, T var2);

    public <T extends Throwable> T throwing(T var1);

    public void trace(Marker var1, Message var2);

    public void trace(Marker var1, Message var2, Throwable var3);

    public void trace(Marker var1, MessageSupplier var2);

    public void trace(Marker var1, MessageSupplier var2, Throwable var3);

    public void trace(Marker var1, CharSequence var2);

    public void trace(Marker var1, CharSequence var2, Throwable var3);

    public void trace(Marker var1, Object var2);

    public void trace(Marker var1, Object var2, Throwable var3);

    public void trace(Marker var1, String var2);

    public void trace(Marker var1, String var2, Object ... var3);

    public void trace(Marker var1, String var2, Supplier<?> ... var3);

    public void trace(Marker var1, String var2, Throwable var3);

    public void trace(Marker var1, Supplier<?> var2);

    public void trace(Marker var1, Supplier<?> var2, Throwable var3);

    public void trace(Message var1);

    public void trace(Message var1, Throwable var2);

    public void trace(MessageSupplier var1);

    public void trace(MessageSupplier var1, Throwable var2);

    public void trace(CharSequence var1);

    public void trace(CharSequence var1, Throwable var2);

    public void trace(Object var1);

    public void trace(Object var1, Throwable var2);

    public void trace(String var1);

    public void trace(String var1, Object ... var2);

    public void trace(String var1, Supplier<?> ... var2);

    public void trace(String var1, Throwable var2);

    public void trace(Supplier<?> var1);

    public void trace(Supplier<?> var1, Throwable var2);

    public void trace(Marker var1, String var2, Object var3);

    public void trace(Marker var1, String var2, Object var3, Object var4);

    public void trace(Marker var1, String var2, Object var3, Object var4, Object var5);

    public void trace(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6);

    public void trace(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7);

    public void trace(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8);

    public void trace(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9);

    public void trace(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10);

    public void trace(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11);

    public void trace(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12);

    public void trace(String var1, Object var2);

    public void trace(String var1, Object var2, Object var3);

    public void trace(String var1, Object var2, Object var3, Object var4);

    public void trace(String var1, Object var2, Object var3, Object var4, Object var5);

    public void trace(String var1, Object var2, Object var3, Object var4, Object var5, Object var6);

    public void trace(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7);

    public void trace(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8);

    public void trace(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9);

    public void trace(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10);

    public void trace(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11);

    public EntryMessage traceEntry();

    public EntryMessage traceEntry(String var1, Object ... var2);

    public EntryMessage traceEntry(Supplier<?> ... var1);

    public EntryMessage traceEntry(String var1, Supplier<?> ... var2);

    public EntryMessage traceEntry(Message var1);

    public void traceExit();

    public <R> R traceExit(R var1);

    public <R> R traceExit(String var1, R var2);

    public void traceExit(EntryMessage var1);

    public <R> R traceExit(EntryMessage var1, R var2);

    public <R> R traceExit(Message var1, R var2);

    public void warn(Marker var1, Message var2);

    public void warn(Marker var1, Message var2, Throwable var3);

    public void warn(Marker var1, MessageSupplier var2);

    public void warn(Marker var1, MessageSupplier var2, Throwable var3);

    public void warn(Marker var1, CharSequence var2);

    public void warn(Marker var1, CharSequence var2, Throwable var3);

    public void warn(Marker var1, Object var2);

    public void warn(Marker var1, Object var2, Throwable var3);

    public void warn(Marker var1, String var2);

    public void warn(Marker var1, String var2, Object ... var3);

    public void warn(Marker var1, String var2, Supplier<?> ... var3);

    public void warn(Marker var1, String var2, Throwable var3);

    public void warn(Marker var1, Supplier<?> var2);

    public void warn(Marker var1, Supplier<?> var2, Throwable var3);

    public void warn(Message var1);

    public void warn(Message var1, Throwable var2);

    public void warn(MessageSupplier var1);

    public void warn(MessageSupplier var1, Throwable var2);

    public void warn(CharSequence var1);

    public void warn(CharSequence var1, Throwable var2);

    public void warn(Object var1);

    public void warn(Object var1, Throwable var2);

    public void warn(String var1);

    public void warn(String var1, Object ... var2);

    public void warn(String var1, Supplier<?> ... var2);

    public void warn(String var1, Throwable var2);

    public void warn(Supplier<?> var1);

    public void warn(Supplier<?> var1, Throwable var2);

    public void warn(Marker var1, String var2, Object var3);

    public void warn(Marker var1, String var2, Object var3, Object var4);

    public void warn(Marker var1, String var2, Object var3, Object var4, Object var5);

    public void warn(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6);

    public void warn(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7);

    public void warn(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8);

    public void warn(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9);

    public void warn(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10);

    public void warn(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11);

    public void warn(Marker var1, String var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12);

    public void warn(String var1, Object var2);

    public void warn(String var1, Object var2, Object var3);

    public void warn(String var1, Object var2, Object var3, Object var4);

    public void warn(String var1, Object var2, Object var3, Object var4, Object var5);

    public void warn(String var1, Object var2, Object var3, Object var4, Object var5, Object var6);

    public void warn(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7);

    public void warn(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8);

    public void warn(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9);

    public void warn(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10);

    public void warn(String var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11);
}

