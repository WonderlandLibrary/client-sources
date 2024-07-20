/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling;

import org.apache.logging.log4j.core.appender.rolling.RollingFileManager;

public interface DirectFileRolloverStrategy {
    public String getCurrentFileName(RollingFileManager var1);
}

