/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.jmx;

import java.util.Objects;
import javax.management.ObjectName;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.filter.AbstractFilterable;
import org.apache.logging.log4j.core.jmx.AppenderAdminMBean;
import org.apache.logging.log4j.core.jmx.Server;

public class AppenderAdmin
implements AppenderAdminMBean {
    private final String contextName;
    private final Appender appender;
    private final ObjectName objectName;

    public AppenderAdmin(String string, Appender appender) {
        this.contextName = Objects.requireNonNull(string, "contextName");
        this.appender = Objects.requireNonNull(appender, "appender");
        try {
            String string2 = Server.escape(this.contextName);
            String string3 = Server.escape(appender.getName());
            String string4 = String.format("org.apache.logging.log4j2:type=%s,component=Appenders,name=%s", string2, string3);
            this.objectName = new ObjectName(string4);
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    public ObjectName getObjectName() {
        return this.objectName;
    }

    @Override
    public String getName() {
        return this.appender.getName();
    }

    @Override
    public String getLayout() {
        return String.valueOf(this.appender.getLayout());
    }

    @Override
    public boolean isIgnoreExceptions() {
        return this.appender.ignoreExceptions();
    }

    @Override
    public String getErrorHandler() {
        return String.valueOf(this.appender.getHandler());
    }

    @Override
    public String getFilter() {
        if (this.appender instanceof AbstractFilterable) {
            return String.valueOf(((AbstractFilterable)((Object)this.appender)).getFilter());
        }
        return null;
    }
}

