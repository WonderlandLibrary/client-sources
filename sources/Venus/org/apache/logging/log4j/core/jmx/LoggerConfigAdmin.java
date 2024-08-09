/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.jmx;

import java.util.List;
import java.util.Objects;
import javax.management.ObjectName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.jmx.LoggerConfigAdminMBean;
import org.apache.logging.log4j.core.jmx.Server;

public class LoggerConfigAdmin
implements LoggerConfigAdminMBean {
    private final LoggerContext loggerContext;
    private final LoggerConfig loggerConfig;
    private final ObjectName objectName;

    public LoggerConfigAdmin(LoggerContext loggerContext, LoggerConfig loggerConfig) {
        this.loggerContext = Objects.requireNonNull(loggerContext, "loggerContext");
        this.loggerConfig = Objects.requireNonNull(loggerConfig, "loggerConfig");
        try {
            String string = Server.escape(loggerContext.getName());
            String string2 = Server.escape(loggerConfig.getName());
            String string3 = String.format("org.apache.logging.log4j2:type=%s,component=Loggers,name=%s", string, string2);
            this.objectName = new ObjectName(string3);
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    public ObjectName getObjectName() {
        return this.objectName;
    }

    @Override
    public String getName() {
        return this.loggerConfig.getName();
    }

    @Override
    public String getLevel() {
        return this.loggerConfig.getLevel().name();
    }

    @Override
    public void setLevel(String string) {
        this.loggerConfig.setLevel(Level.getLevel(string));
        this.loggerContext.updateLoggers();
    }

    @Override
    public boolean isAdditive() {
        return this.loggerConfig.isAdditive();
    }

    @Override
    public void setAdditive(boolean bl) {
        this.loggerConfig.setAdditive(bl);
        this.loggerContext.updateLoggers();
    }

    @Override
    public boolean isIncludeLocation() {
        return this.loggerConfig.isIncludeLocation();
    }

    @Override
    public String getFilter() {
        return String.valueOf(this.loggerConfig.getFilter());
    }

    @Override
    public String[] getAppenderRefs() {
        List<AppenderRef> list = this.loggerConfig.getAppenderRefs();
        String[] stringArray = new String[list.size()];
        for (int i = 0; i < stringArray.length; ++i) {
            stringArray[i] = list.get(i).getRef();
        }
        return stringArray;
    }
}

