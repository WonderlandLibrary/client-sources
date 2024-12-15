//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.logging.log4j;


import org.apache.logging.log4j.message.Message;


public interface Logger {
    void catching(Level level, Throwable throwable);

    void catching(Throwable throwable);

    void debug(Marker marker, Message message);

    void debug(Marker marker, Message message, Throwable throwable);

    void debug(Marker marker);

    void debug(Marker marker, Throwable throwable);

    void debug(Marker marker, CharSequence message);

    void debug(Marker marker, CharSequence message, Throwable throwable);

    void debug(Marker marker, Object message);

    void debug(Marker marker, Object message, Throwable throwable);

    void debug(Marker marker, String message);

    void debug(Marker marker, String message, Object... params);


    void debug(Marker marker, String message, Throwable throwable);


    void debug(Message message);

    void debug(Message message, Throwable throwable);


    void debug(CharSequence message);

    void debug(CharSequence message, Throwable throwable);

    void debug(Object message);

    void debug(Object message, Throwable throwable);

    void debug(String message);

    void debug(String message, Object... params);


    void debug(String message, Throwable throwable);


    void debug(Marker marker, String message, Object p0);

    void debug(Marker marker, String message, Object p0, Object p1);

    void debug(Marker marker, String message, Object p0, Object p1, Object p2);

    void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3);

    void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4);

    void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5);

    void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

    void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

    void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

    void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9);

    void debug(String message, Object p0);

    void debug(String message, Object p0, Object p1);

    void debug(String message, Object p0, Object p1, Object p2);

    void debug(String message, Object p0, Object p1, Object p2, Object p3);

    void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4);

    void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5);

    void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

    void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

    void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

    void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9);

    /**
     * @deprecated
     */
    @Deprecated
    void entry();

    /**
     * @deprecated
     */
    @Deprecated
    void entry(Object... params);

    void error(Marker marker, Message message);

    void error(Marker marker, Message message, Throwable throwable);

    void error(Marker marker, CharSequence message);

    void error(Marker marker, CharSequence message, Throwable throwable);

    void error(Marker marker, Object message);

    void error(Marker marker, Object message, Throwable throwable);

    void error(Marker marker, String message);

    void error(Marker marker, String message, Object... params);


    void error(Marker marker, String message, Throwable throwable);


    void error(Message message);

    void error(Message message, Throwable throwable);


    void error(CharSequence message);

    void error(CharSequence message, Throwable throwable);

    void error(Object message);

    void error(Object message, Throwable throwable);

    void error(String message);

    void error(String message, Object... params);


    void error(String message, Throwable throwable);


    void error(Marker marker, String message, Object p0);

    void error(Marker marker, String message, Object p0, Object p1);

    void error(Marker marker, String message, Object p0, Object p1, Object p2);

    void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3);

    void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4);

    void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5);

    void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

    void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

    void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

    void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9);

    void error(String message, Object p0);

    void error(String message, Object p0, Object p1);

    void error(String message, Object p0, Object p1, Object p2);

    void error(String message, Object p0, Object p1, Object p2, Object p3);

    void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4);

    void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5);

    void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

    void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

    void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

    void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9);

    /**
     * @deprecated
     */
    @Deprecated
    void exit();

    /**
     * @deprecated
     */
    @Deprecated
    <R> R exit(R result);

    void fatal(Marker marker, Message message);

    void fatal(Marker marker, Message message, Throwable throwable);


    void fatal(Marker marker, CharSequence message);

    void fatal(Marker marker, CharSequence message, Throwable throwable);

    void fatal(Marker marker, Object message);

    void fatal(Marker marker, Object message, Throwable throwable);

    void fatal(Marker marker, String message);

    void fatal(Marker marker, String message, Object... params);


    void fatal(Marker marker, String message, Throwable throwable);


    void fatal(Message message);

    void fatal(Message message, Throwable throwable);


    void fatal(CharSequence message);

    void fatal(CharSequence message, Throwable throwable);

    void fatal(Object message);

    void fatal(Object message, Throwable throwable);

    void fatal(String message);

    void fatal(String message, Object... params);


    void fatal(String message, Throwable throwable);


    void fatal(Marker marker, String message, Object p0);

    void fatal(Marker marker, String message, Object p0, Object p1);

    void fatal(Marker marker, String message, Object p0, Object p1, Object p2);

    void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3);

    void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4);

    void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5);

    void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

    void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

    void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

    void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9);

    void fatal(String message, Object p0);

    void fatal(String message, Object p0, Object p1);

    void fatal(String message, Object p0, Object p1, Object p2);

    void fatal(String message, Object p0, Object p1, Object p2, Object p3);

    void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4);

    void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5);

    void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

    void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

    void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

    void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9);

    void info(Marker marker, Message message);

    void info(Marker marker, Message message, Throwable throwable);


    void info(Marker marker, CharSequence message);

    void info(Marker marker, CharSequence message, Throwable throwable);

    void info(Marker marker, Object message);

    void info(Marker marker, Object message, Throwable throwable);

    void info(Marker marker, String message);

    void info(Marker marker, String message, Object... params);


    void info(Marker marker, String message, Throwable throwable);


    void info(Message message);

    void info(Message message, Throwable throwable);


    void info(CharSequence message);

    void info(CharSequence message, Throwable throwable);

    void info(Object message);

    void info(Object message, Throwable throwable);

    void info(String message);

    void info(String message, Object... params);


    void info(String message, Throwable throwable);


    void info(Marker marker, String message, Object p0);

    void info(Marker marker, String message, Object p0, Object p1);

    void info(Marker marker, String message, Object p0, Object p1, Object p2);

    void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3);

    void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4);

    void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5);

    void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

    void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

    void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

    void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9);

    void info(String message, Object p0);

    void info(String message, Object p0, Object p1);

    void info(String message, Object p0, Object p1, Object p2);

    void info(String message, Object p0, Object p1, Object p2, Object p3);

    void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4);

    void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5);

    void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

    void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

    void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

    void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9);

    boolean isDebugEnabled();

    boolean isDebugEnabled(Marker marker);

    boolean isEnabled(Level level);

    boolean isEnabled(Level level, Marker marker);

    boolean isErrorEnabled();

    boolean isErrorEnabled(Marker marker);

    boolean isFatalEnabled();

    boolean isFatalEnabled(Marker marker);

    boolean isInfoEnabled();

    boolean isInfoEnabled(Marker marker);

    boolean isTraceEnabled();

    boolean isTraceEnabled(Marker marker);

    boolean isWarnEnabled();

    boolean isWarnEnabled(Marker marker);

    void log(Level level, Marker marker, Message message);

    void log(Level level, Marker marker, Message message, Throwable throwable);


    void log(Level level, Marker marker, CharSequence message);

    void log(Level level, Marker marker, CharSequence message, Throwable throwable);

    void log(Level level, Marker marker, Object message);

    void log(Level level, Marker marker, Object message, Throwable throwable);

    void log(Level level, Marker marker, String message);

    void log(Level level, Marker marker, String message, Object... params);


    void log(Level level, Marker marker, String message, Throwable throwable);


    void log(Level level, Message message);

    void log(Level level, Message message, Throwable throwable);


    void log(Level level, CharSequence message);

    void log(Level level, CharSequence message, Throwable throwable);

    void log(Level level, Object message);

    void log(Level level, Object message, Throwable throwable);

    void log(Level level, String message);

    void log(Level level, String message, Object... params);


    void log(Level level, String message, Throwable throwable);


    void log(Level level, Marker marker, String message, Object p0);

    void log(Level level, Marker marker, String message, Object p0, Object p1);

    void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2);

    void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3);

    void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4);

    void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5);

    void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

    void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

    void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

    void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9);

    void log(Level level, String message, Object p0);

    void log(Level level, String message, Object p0, Object p1);

    void log(Level level, String message, Object p0, Object p1, Object p2);

    void log(Level level, String message, Object p0, Object p1, Object p2, Object p3);

    void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4);

    void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5);

    void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

    void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

    void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

    void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9);

    void printf(Level level, Marker marker, String format, Object... params);

    void printf(Level level, String format, Object... params);

    <T extends Throwable> T throwing(Level level, T throwable);

    <T extends Throwable> T throwing(T throwable);

    void trace(Marker marker, Message message);

    void trace(Marker marker, Message message, Throwable throwable);


    void trace(Marker marker, CharSequence message);

    void trace(Marker marker, CharSequence message, Throwable throwable);

    void trace(Marker marker, Object message);

    void trace(Marker marker, Object message, Throwable throwable);

    void trace(Marker marker, String message);

    void trace(Marker marker, String message, Object... params);


    void trace(Marker marker, String message, Throwable throwable);

    void trace(Message message);

    void trace(Message message, Throwable throwable);


    void trace(CharSequence message);

    void trace(CharSequence message, Throwable throwable);

    void trace(Object message);

    void trace(Object message, Throwable throwable);

    void trace(String message);

    void trace(String message, Object... params);


    void trace(String message, Throwable throwable);


    void trace(Marker marker, String message, Object p0);

    void trace(Marker marker, String message, Object p0, Object p1);

    void trace(Marker marker, String message, Object p0, Object p1, Object p2);

    void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3);

    void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4);

    void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5);

    void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

    void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

    void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

    void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9);

    void trace(String message, Object p0);

    void trace(String message, Object p0, Object p1);

    void trace(String message, Object p0, Object p1, Object p2);

    void trace(String message, Object p0, Object p1, Object p2, Object p3);

    void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4);

    void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5);

    void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

    void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

    void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

    void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9);


    void warn(Marker marker, Message message);

    void warn(Marker marker, Message message, Throwable throwable);


    void warn(Marker marker, CharSequence message);

    void warn(Marker marker, CharSequence message, Throwable throwable);

    void warn(Marker marker, Object message);

    void warn(Marker marker, Object message, Throwable throwable);

    void warn(Marker marker, String message);

    void warn(Marker marker, String message, Object... params);


    void warn(Marker marker, String message, Throwable throwable);

    void warn(Message message);

    void warn(Message message, Throwable throwable);


    void warn(CharSequence message);

    void warn(CharSequence message, Throwable throwable);

    void warn(Object message);

    void warn(Object message, Throwable throwable);

    void warn(String message);

    void warn(String message, Object... params);


    void warn(String message, Throwable throwable);


    void warn(Marker marker, String message, Object p0);

    void warn(Marker marker, String message, Object p0, Object p1);

    void warn(Marker marker, String message, Object p0, Object p1, Object p2);

    void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3);

    void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4);

    void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5);

    void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

    void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

    void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

    void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9);

    void warn(String message, Object p0);

    void warn(String message, Object p0, Object p1);

    void warn(String message, Object p0, Object p1, Object p2);

    void warn(String message, Object p0, Object p1, Object p2, Object p3);

    void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4);

    void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5);

    void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

    void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

    void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

    void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9);

    default void logMessage(Level level, Marker marker, String fqcn, StackTraceElement location, Message message, Throwable throwable) {
    }
}
