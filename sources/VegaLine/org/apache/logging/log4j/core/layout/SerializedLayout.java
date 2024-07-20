/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractLayout;

@Plugin(name="SerializedLayout", category="Core", elementType="layout", printObject=true)
public final class SerializedLayout
extends AbstractLayout<LogEvent> {
    private static byte[] serializedHeader;

    private SerializedLayout() {
        super(null, null, null);
    }

    @Override
    public byte[] toByteArray(LogEvent event) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (PrivateObjectOutputStream oos = new PrivateObjectOutputStream(baos);){
            oos.writeObject(event);
            oos.reset();
        } catch (IOException ioe) {
            LOGGER.error("Serialization of LogEvent failed.", (Throwable)ioe);
        }
        return baos.toByteArray();
    }

    @Override
    public LogEvent toSerializable(LogEvent event) {
        return event;
    }

    @PluginFactory
    public static SerializedLayout createLayout() {
        return new SerializedLayout();
    }

    @Override
    public byte[] getHeader() {
        return serializedHeader;
    }

    @Override
    public String getContentType() {
        return "application/octet-stream";
    }

    static {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream(baos).close();
            serializedHeader = baos.toByteArray();
        } catch (Exception ex) {
            LOGGER.error("Unable to generate Object stream header", (Throwable)ex);
        }
    }

    private class PrivateObjectOutputStream
    extends ObjectOutputStream {
        public PrivateObjectOutputStream(OutputStream os) throws IOException {
            super(os);
        }

        @Override
        protected void writeStreamHeader() {
        }
    }
}

