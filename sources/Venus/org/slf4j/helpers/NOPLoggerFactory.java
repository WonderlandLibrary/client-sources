/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.helpers;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.helpers.NOPLogger;

public class NOPLoggerFactory
implements ILoggerFactory {
    @Override
    public Logger getLogger(String string) {
        return NOPLogger.NOP_LOGGER;
    }
}

