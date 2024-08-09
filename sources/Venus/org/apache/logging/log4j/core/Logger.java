/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.ReliabilityStrategy;
import org.apache.logging.log4j.core.filter.CompositeFilter;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.apache.logging.log4j.util.Supplier;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Logger
extends AbstractLogger
implements Supplier<LoggerConfig> {
    private static final long serialVersionUID = 1L;
    protected volatile PrivateConfig privateConfig;
    private final LoggerContext context;

    protected Logger(LoggerContext loggerContext, String string, MessageFactory messageFactory) {
        super(string, messageFactory);
        this.context = loggerContext;
        this.privateConfig = new PrivateConfig(this, loggerContext.getConfiguration(), this);
    }

    protected Object writeReplace() throws ObjectStreamException {
        return new LoggerProxy(this.getName(), (MessageFactory)this.getMessageFactory());
    }

    public Logger getParent() {
        Object MF;
        LoggerConfig loggerConfig;
        LoggerConfig loggerConfig2 = loggerConfig = this.privateConfig.loggerConfig.getName().equals(this.getName()) ? this.privateConfig.loggerConfig.getParent() : this.privateConfig.loggerConfig;
        if (loggerConfig == null) {
            return null;
        }
        String string = loggerConfig.getName();
        if (this.context.hasLogger(string, (MessageFactory)(MF = this.getMessageFactory()))) {
            return this.context.getLogger(string, (MessageFactory)MF);
        }
        return new Logger(this.context, string, (MessageFactory)MF);
    }

    public LoggerContext getContext() {
        return this.context;
    }

    public synchronized void setLevel(Level level) {
        Logger logger;
        if (level == this.getLevel()) {
            return;
        }
        Level level2 = level != null ? level : ((logger = this.getParent()) != null ? logger.getLevel() : PrivateConfig.access$000(this.privateConfig));
        this.privateConfig = new PrivateConfig(this, this.privateConfig, level2);
    }

    @Override
    public LoggerConfig get() {
        return this.privateConfig.loggerConfig;
    }

    @Override
    public void logMessage(String string, Level level, Marker marker, Message message, Throwable throwable) {
        Message message2 = message == null ? new SimpleMessage("") : message;
        ReliabilityStrategy reliabilityStrategy = this.privateConfig.loggerConfig.getReliabilityStrategy();
        reliabilityStrategy.log(this, this.getName(), string, marker, level, message2, throwable);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Throwable throwable) {
        return this.privateConfig.filter(level, marker, string, throwable);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string) {
        return this.privateConfig.filter(level, marker, string);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object ... objectArray) {
        return this.privateConfig.filter(level, marker, string, objectArray);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object) {
        return this.privateConfig.filter(level, marker, string, object);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2) {
        return this.privateConfig.filter(level, marker, string, object, object2);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3) {
        return this.privateConfig.filter(level, marker, string, object, object2, object3);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4) {
        return this.privateConfig.filter(level, marker, string, object, object2, object3, object4);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        return this.privateConfig.filter(level, marker, string, object, object2, object3, object4, object5);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return this.privateConfig.filter(level, marker, string, object, object2, object3, object4, object5, object6);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        return this.privateConfig.filter(level, marker, string, object, object2, object3, object4, object5, object6, object7);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        return this.privateConfig.filter(level, marker, string, object, object2, object3, object4, object5, object6, object7, object8);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        return this.privateConfig.filter(level, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        return this.privateConfig.filter(level, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, CharSequence charSequence, Throwable throwable) {
        return this.privateConfig.filter(level, marker, charSequence, throwable);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, Object object, Throwable throwable) {
        return this.privateConfig.filter(level, marker, object, throwable);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, Message message, Throwable throwable) {
        return this.privateConfig.filter(level, marker, message, throwable);
    }

    public void addAppender(Appender appender) {
        this.privateConfig.config.addLoggerAppender(this, appender);
    }

    public void removeAppender(Appender appender) {
        this.privateConfig.loggerConfig.removeAppender(appender.getName());
    }

    public Map<String, Appender> getAppenders() {
        return this.privateConfig.loggerConfig.getAppenders();
    }

    public Iterator<Filter> getFilters() {
        Filter filter = this.privateConfig.loggerConfig.getFilter();
        if (filter == null) {
            return new ArrayList().iterator();
        }
        if (filter instanceof CompositeFilter) {
            return ((CompositeFilter)filter).iterator();
        }
        ArrayList<Filter> arrayList = new ArrayList<Filter>();
        arrayList.add(filter);
        return arrayList.iterator();
    }

    @Override
    public Level getLevel() {
        return PrivateConfig.access$000(this.privateConfig);
    }

    public int filterCount() {
        Filter filter = this.privateConfig.loggerConfig.getFilter();
        if (filter == null) {
            return 1;
        }
        if (filter instanceof CompositeFilter) {
            return ((CompositeFilter)filter).size();
        }
        return 0;
    }

    public void addFilter(Filter filter) {
        this.privateConfig.config.addLoggerFilter(this, filter);
    }

    public boolean isAdditive() {
        return this.privateConfig.loggerConfig.isAdditive();
    }

    public void setAdditive(boolean bl) {
        this.privateConfig.config.setLoggerAdditive(this, bl);
    }

    protected void updateConfiguration(Configuration configuration) {
        this.privateConfig = new PrivateConfig(this, configuration, this);
    }

    public String toString() {
        String string = "" + this.getName() + ':' + this.getLevel();
        if (this.context == null) {
            return string;
        }
        String string2 = this.context.getName();
        return string2 == null ? string : string + " in " + string2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        Logger logger = (Logger)object;
        return this.getName().equals(logger.getName());
    }

    public int hashCode() {
        return this.getName().hashCode();
    }

    @Override
    public Object get() {
        return this.get();
    }

    protected static class LoggerProxy
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String name;
        private final MessageFactory messageFactory;

        public LoggerProxy(String string, MessageFactory messageFactory) {
            this.name = string;
            this.messageFactory = messageFactory;
        }

        protected Object readResolve() throws ObjectStreamException {
            return new Logger(LoggerContext.getContext(), this.name, this.messageFactory);
        }
    }

    protected class PrivateConfig {
        public final LoggerConfig loggerConfig;
        public final Configuration config;
        private final Level loggerConfigLevel;
        private final int intLevel;
        private final Logger logger;
        final Logger this$0;

        public PrivateConfig(Logger logger, Configuration configuration, Logger logger2) {
            this.this$0 = logger;
            this.config = configuration;
            this.loggerConfig = configuration.getLoggerConfig(logger.getName());
            this.loggerConfigLevel = this.loggerConfig.getLevel();
            this.intLevel = this.loggerConfigLevel.intLevel();
            this.logger = logger2;
        }

        public PrivateConfig(Logger logger, PrivateConfig privateConfig, Level level) {
            this.this$0 = logger;
            this.config = privateConfig.config;
            this.loggerConfig = privateConfig.loggerConfig;
            this.loggerConfigLevel = level;
            this.intLevel = this.loggerConfigLevel.intLevel();
            this.logger = privateConfig.logger;
        }

        public PrivateConfig(Logger logger, PrivateConfig privateConfig, LoggerConfig loggerConfig) {
            this.this$0 = logger;
            this.config = privateConfig.config;
            this.loggerConfig = loggerConfig;
            this.loggerConfigLevel = loggerConfig.getLevel();
            this.intLevel = this.loggerConfigLevel.intLevel();
            this.logger = privateConfig.logger;
        }

        public void logEvent(LogEvent logEvent) {
            this.loggerConfig.log(logEvent);
        }

        boolean filter(Level level, Marker marker, String string) {
            Filter.Result result;
            Filter filter = this.config.getFilter();
            if (filter != null && (result = filter.filter(this.logger, level, marker, string, new Object[0])) != Filter.Result.NEUTRAL) {
                return result == Filter.Result.ACCEPT;
            }
            return level != null && this.intLevel >= level.intLevel();
        }

        boolean filter(Level level, Marker marker, String string, Throwable throwable) {
            Filter.Result result;
            Filter filter = this.config.getFilter();
            if (filter != null && (result = filter.filter(this.logger, level, marker, (Object)string, throwable)) != Filter.Result.NEUTRAL) {
                return result == Filter.Result.ACCEPT;
            }
            return level != null && this.intLevel >= level.intLevel();
        }

        boolean filter(Level level, Marker marker, String string, Object ... objectArray) {
            Filter.Result result;
            Filter filter = this.config.getFilter();
            if (filter != null && (result = filter.filter(this.logger, level, marker, string, objectArray)) != Filter.Result.NEUTRAL) {
                return result == Filter.Result.ACCEPT;
            }
            return level != null && this.intLevel >= level.intLevel();
        }

        boolean filter(Level level, Marker marker, String string, Object object) {
            Filter.Result result;
            Filter filter = this.config.getFilter();
            if (filter != null && (result = filter.filter(this.logger, level, marker, string, object)) != Filter.Result.NEUTRAL) {
                return result == Filter.Result.ACCEPT;
            }
            return level != null && this.intLevel >= level.intLevel();
        }

        boolean filter(Level level, Marker marker, String string, Object object, Object object2) {
            Filter.Result result;
            Filter filter = this.config.getFilter();
            if (filter != null && (result = filter.filter(this.logger, level, marker, string, object, object2)) != Filter.Result.NEUTRAL) {
                return result == Filter.Result.ACCEPT;
            }
            return level != null && this.intLevel >= level.intLevel();
        }

        boolean filter(Level level, Marker marker, String string, Object object, Object object2, Object object3) {
            Filter.Result result;
            Filter filter = this.config.getFilter();
            if (filter != null && (result = filter.filter(this.logger, level, marker, string, object, object2, object3)) != Filter.Result.NEUTRAL) {
                return result == Filter.Result.ACCEPT;
            }
            return level != null && this.intLevel >= level.intLevel();
        }

        boolean filter(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4) {
            Filter.Result result;
            Filter filter = this.config.getFilter();
            if (filter != null && (result = filter.filter(this.logger, level, marker, string, object, object2, object3, object4)) != Filter.Result.NEUTRAL) {
                return result == Filter.Result.ACCEPT;
            }
            return level != null && this.intLevel >= level.intLevel();
        }

        boolean filter(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
            Filter.Result result;
            Filter filter = this.config.getFilter();
            if (filter != null && (result = filter.filter(this.logger, level, marker, string, object, object2, object3, object4, object5)) != Filter.Result.NEUTRAL) {
                return result == Filter.Result.ACCEPT;
            }
            return level != null && this.intLevel >= level.intLevel();
        }

        boolean filter(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
            Filter.Result result;
            Filter filter = this.config.getFilter();
            if (filter != null && (result = filter.filter(this.logger, level, marker, string, object, object2, object3, object4, object5, object6)) != Filter.Result.NEUTRAL) {
                return result == Filter.Result.ACCEPT;
            }
            return level != null && this.intLevel >= level.intLevel();
        }

        boolean filter(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
            Filter.Result result;
            Filter filter = this.config.getFilter();
            if (filter != null && (result = filter.filter(this.logger, level, marker, string, object, object2, object3, object4, object5, object6, object7)) != Filter.Result.NEUTRAL) {
                return result == Filter.Result.ACCEPT;
            }
            return level != null && this.intLevel >= level.intLevel();
        }

        boolean filter(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
            Filter.Result result;
            Filter filter = this.config.getFilter();
            if (filter != null && (result = filter.filter(this.logger, level, marker, string, object, object2, object3, object4, object5, object6, object7, object8)) != Filter.Result.NEUTRAL) {
                return result == Filter.Result.ACCEPT;
            }
            return level != null && this.intLevel >= level.intLevel();
        }

        boolean filter(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
            Filter.Result result;
            Filter filter = this.config.getFilter();
            if (filter != null && (result = filter.filter(this.logger, level, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9)) != Filter.Result.NEUTRAL) {
                return result == Filter.Result.ACCEPT;
            }
            return level != null && this.intLevel >= level.intLevel();
        }

        boolean filter(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
            Filter.Result result;
            Filter filter = this.config.getFilter();
            if (filter != null && (result = filter.filter(this.logger, level, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10)) != Filter.Result.NEUTRAL) {
                return result == Filter.Result.ACCEPT;
            }
            return level != null && this.intLevel >= level.intLevel();
        }

        boolean filter(Level level, Marker marker, CharSequence charSequence, Throwable throwable) {
            Filter.Result result;
            Filter filter = this.config.getFilter();
            if (filter != null && (result = filter.filter(this.logger, level, marker, charSequence, throwable)) != Filter.Result.NEUTRAL) {
                return result == Filter.Result.ACCEPT;
            }
            return level != null && this.intLevel >= level.intLevel();
        }

        boolean filter(Level level, Marker marker, Object object, Throwable throwable) {
            Filter.Result result;
            Filter filter = this.config.getFilter();
            if (filter != null && (result = filter.filter(this.logger, level, marker, object, throwable)) != Filter.Result.NEUTRAL) {
                return result == Filter.Result.ACCEPT;
            }
            return level != null && this.intLevel >= level.intLevel();
        }

        boolean filter(Level level, Marker marker, Message message, Throwable throwable) {
            Filter.Result result;
            Filter filter = this.config.getFilter();
            if (filter != null && (result = filter.filter(this.logger, level, marker, message, throwable)) != Filter.Result.NEUTRAL) {
                return result == Filter.Result.ACCEPT;
            }
            return level != null && this.intLevel >= level.intLevel();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PrivateConfig [loggerConfig=");
            stringBuilder.append(this.loggerConfig);
            stringBuilder.append(", config=");
            stringBuilder.append(this.config);
            stringBuilder.append(", loggerConfigLevel=");
            stringBuilder.append(this.loggerConfigLevel);
            stringBuilder.append(", intLevel=");
            stringBuilder.append(this.intLevel);
            stringBuilder.append(", logger=");
            stringBuilder.append(this.logger);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        static Level access$000(PrivateConfig privateConfig) {
            return privateConfig.loggerConfigLevel;
        }
    }
}

