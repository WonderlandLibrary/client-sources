/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import org.apache.logging.log4j.core.LogEventListener;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.net.server.AbstractLogEventBridge;

public abstract class InputStreamLogEventBridge
extends AbstractLogEventBridge<InputStream> {
    private final int bufferSize;
    private final Charset charset;
    private final String eventEndMarker;
    private final ObjectReader objectReader;

    public InputStreamLogEventBridge(ObjectMapper objectMapper, int n, Charset charset, String string) {
        this.bufferSize = n;
        this.charset = charset;
        this.eventEndMarker = string;
        this.objectReader = objectMapper.readerFor(Log4jLogEvent.class);
    }

    protected abstract int[] getEventIndices(String var1, int var2);

    @Override
    public void logEvents(InputStream inputStream, LogEventListener logEventListener) throws IOException {
        String string = "";
        try {
            int n;
            byte[] byArray = new byte[this.bufferSize];
            string = "";
            String string2 = "";
            block2: while ((n = inputStream.read(byArray)) != -1) {
                String string3 = string = string2 + new String(byArray, 0, n, this.charset);
                int n2 = 0;
                while (true) {
                    int[] nArray;
                    int n3;
                    if ((n3 = (nArray = this.getEventIndices(string3, n2))[0]) < 0) {
                        string2 = string3.substring(n2);
                        continue block2;
                    }
                    int n4 = nArray[1];
                    if (n4 <= 0) break;
                    int n5 = n4 + this.eventEndMarker.length();
                    String string4 = string = string3.substring(n3, n5);
                    Log4jLogEvent log4jLogEvent = this.unmarshal(string4);
                    logEventListener.log(log4jLogEvent);
                    n2 = n5;
                }
                string2 = string3.substring(n2);
            }
        } catch (IOException iOException) {
            logger.error(string, (Throwable)iOException);
        }
    }

    protected Log4jLogEvent unmarshal(String string) throws IOException {
        return (Log4jLogEvent)this.objectReader.readValue(string);
    }
}

