/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.jmx;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import javax.management.ObjectName;

public interface LoggerContextAdminMBean {
    public static final String PATTERN = "org.apache.logging.log4j2:type=%s";
    public static final String NOTIF_TYPE_RECONFIGURED = "com.apache.logging.log4j.core.jmx.config.reconfigured";

    public ObjectName getObjectName();

    public String getStatus();

    public String getName();

    public String getConfigLocationUri();

    public void setConfigLocationUri(String var1) throws URISyntaxException, IOException;

    public String getConfigText() throws IOException;

    public String getConfigText(String var1) throws IOException;

    public void setConfigText(String var1, String var2);

    public String getConfigName();

    public String getConfigClassName();

    public String getConfigFilter();

    public Map<String, String> getConfigProperties();
}

