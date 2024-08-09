/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractLayout;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Plugin(name="SerializedLayout", category="Core", elementType="layout", printObject=true)
public final class SerializedLayout
extends AbstractLayout<LogEvent> {
    private static byte[] serializedHeader;

    private SerializedLayout() {
        super(null, null, null);
    }

    @Override
    public byte[] toByteArray(LogEvent logEvent) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (PrivateObjectOutputStream privateObjectOutputStream = new PrivateObjectOutputStream(this, byteArrayOutputStream);){
            privateObjectOutputStream.writeObject(logEvent);
            privateObjectOutputStream.reset();
        } catch (IOException iOException) {
            LOGGER.error("Serialization of LogEvent failed.", (Throwable)iOException);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public LogEvent toSerializable(LogEvent logEvent) {
        return logEvent;
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

    @Override
    public Serializable toSerializable(LogEvent logEvent) {
        return this.toSerializable(logEvent);
    }

    static {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream(byteArrayOutputStream).close();
            serializedHeader = byteArrayOutputStream.toByteArray();
        } catch (Exception exception) {
            LOGGER.error("Unable to generate Object stream header", (Throwable)exception);
        }
    }

    private class PrivateObjectOutputStream
    extends ObjectOutputStream {
        final SerializedLayout this$0;

        public PrivateObjectOutputStream(SerializedLayout serializedLayout, OutputStream outputStream) throws IOException {
            this.this$0 = serializedLayout;
            super(outputStream);
        }

        @Override
        protected void writeStreamHeader() {
        }
    }
}

