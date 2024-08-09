/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.simple;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.Strings;

public class SimpleLogger
extends AbstractLogger {
    private static final long serialVersionUID = 1L;
    private static final char SPACE = ' ';
    private final DateFormat dateFormatter;
    private Level level;
    private final boolean showDateTime;
    private final boolean showContextMap;
    private PrintStream stream;
    private final String logName;

    public SimpleLogger(String string, Level level, boolean bl, boolean bl2, boolean bl3, boolean bl4, String string2, MessageFactory messageFactory, PropertiesUtil propertiesUtil, PrintStream printStream) {
        super(string, messageFactory);
        int n;
        String string3 = propertiesUtil.getStringProperty("org.apache.logging.log4j.simplelog." + string + ".level");
        this.level = Level.toLevel(string3, level);
        this.logName = bl2 ? ((n = string.lastIndexOf(".")) > 0 && n < string.length() ? string.substring(n + 1) : string) : (bl ? string : null);
        this.showDateTime = bl3;
        this.showContextMap = bl4;
        this.stream = printStream;
        if (bl3) {
            SimpleDateFormat simpleDateFormat;
            try {
                simpleDateFormat = new SimpleDateFormat(string2);
            } catch (IllegalArgumentException illegalArgumentException) {
                simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS zzz");
            }
            this.dateFormatter = simpleDateFormat;
        } else {
            this.dateFormatter = null;
        }
    }

    @Override
    public Level getLevel() {
        return this.level;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, Message message, Throwable throwable) {
        return this.level.intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, CharSequence charSequence, Throwable throwable) {
        return this.level.intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, Object object, Throwable throwable) {
        return this.level.intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string) {
        return this.level.intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object ... objectArray) {
        return this.level.intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object) {
        return this.level.intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2) {
        return this.level.intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3) {
        return this.level.intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4) {
        return this.level.intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        return this.level.intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return this.level.intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        return this.level.intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        return this.level.intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        return this.level.intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        return this.level.intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Throwable throwable) {
        return this.level.intLevel() >= level.intLevel();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void logMessage(String string, Level level, Marker marker, Message message, Throwable throwable) {
        Object object;
        Object[] objectArray;
        StringBuilder stringBuilder = new StringBuilder();
        if (this.showDateTime) {
            objectArray = new Date();
            DateFormat dateFormat = this.dateFormatter;
            synchronized (dateFormat) {
                object = this.dateFormatter.format((Date)objectArray);
            }
            stringBuilder.append((String)object);
            stringBuilder.append(' ');
        }
        stringBuilder.append(level.toString());
        stringBuilder.append(' ');
        if (Strings.isNotEmpty(this.logName)) {
            stringBuilder.append(this.logName);
            stringBuilder.append(' ');
        }
        stringBuilder.append(message.getFormattedMessage());
        if (this.showContextMap && (objectArray = ThreadContext.getImmutableContext()).size() > 0) {
            stringBuilder.append(' ');
            stringBuilder.append(objectArray.toString());
            stringBuilder.append(' ');
        }
        objectArray = message.getParameters();
        object = throwable == null && objectArray != null && objectArray.length > 0 && objectArray[objectArray.length - 1] instanceof Throwable ? (Throwable)objectArray[objectArray.length - 1] : throwable;
        this.stream.println(stringBuilder.toString());
        if (object != null) {
            this.stream.print(' ');
            ((Throwable)object).printStackTrace(this.stream);
        }
    }

    public void setLevel(Level level) {
        if (level != null) {
            this.level = level;
        }
    }

    public void setStream(PrintStream printStream) {
        this.stream = printStream;
    }
}

