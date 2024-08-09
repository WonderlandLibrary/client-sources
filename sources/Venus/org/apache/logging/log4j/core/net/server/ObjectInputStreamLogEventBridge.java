/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LogEventListener;
import org.apache.logging.log4j.core.net.server.AbstractLogEventBridge;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ObjectInputStreamLogEventBridge
extends AbstractLogEventBridge<ObjectInputStream> {
    @Override
    public void logEvents(ObjectInputStream objectInputStream, LogEventListener logEventListener) throws IOException {
        try {
            logEventListener.log((LogEvent)objectInputStream.readObject());
        } catch (ClassNotFoundException classNotFoundException) {
            throw new IOException(classNotFoundException);
        }
    }

    @Override
    public ObjectInputStream wrapStream(InputStream inputStream) throws IOException {
        return new ObjectInputStream(inputStream);
    }

    @Override
    public InputStream wrapStream(InputStream inputStream) throws IOException {
        return this.wrapStream(inputStream);
    }

    @Override
    public void logEvents(InputStream inputStream, LogEventListener logEventListener) throws IOException {
        this.logEvents((ObjectInputStream)inputStream, logEventListener);
    }
}

