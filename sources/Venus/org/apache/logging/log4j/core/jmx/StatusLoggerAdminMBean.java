/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.jmx;

import java.util.List;
import javax.management.ObjectName;
import org.apache.logging.log4j.status.StatusData;

public interface StatusLoggerAdminMBean {
    public static final String PATTERN = "org.apache.logging.log4j2:type=%s,component=StatusLogger";
    public static final String NOTIF_TYPE_DATA = "com.apache.logging.log4j.core.jmx.statuslogger.data";
    public static final String NOTIF_TYPE_MESSAGE = "com.apache.logging.log4j.core.jmx.statuslogger.message";

    public ObjectName getObjectName();

    public List<StatusData> getStatusData();

    public String[] getStatusDataHistory();

    public String getLevel();

    public void setLevel(String var1);

    public String getContextName();
}

