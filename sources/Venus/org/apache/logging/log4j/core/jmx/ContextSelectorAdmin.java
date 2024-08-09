/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.jmx;

import java.util.Objects;
import javax.management.ObjectName;
import org.apache.logging.log4j.core.jmx.ContextSelectorAdminMBean;
import org.apache.logging.log4j.core.jmx.Server;
import org.apache.logging.log4j.core.selector.ContextSelector;

public class ContextSelectorAdmin
implements ContextSelectorAdminMBean {
    private final ObjectName objectName;
    private final ContextSelector selector;

    public ContextSelectorAdmin(String string, ContextSelector contextSelector) {
        this.selector = Objects.requireNonNull(contextSelector, "ContextSelector");
        try {
            String string2 = String.format("org.apache.logging.log4j2:type=%s,component=ContextSelector", Server.escape(string));
            this.objectName = new ObjectName(string2);
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    public ObjectName getObjectName() {
        return this.objectName;
    }

    @Override
    public String getImplementationClassName() {
        return this.selector.getClass().getName();
    }
}

