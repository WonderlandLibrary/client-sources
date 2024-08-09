/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpConnection;

@Deprecated
public class IdleConnectionHandler {
    private final Log log = LogFactory.getLog(this.getClass());
    private final Map<HttpConnection, TimeValues> connectionToTimes = new HashMap<HttpConnection, TimeValues>();

    public void add(HttpConnection httpConnection, long l, TimeUnit timeUnit) {
        long l2 = System.currentTimeMillis();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Adding connection at: " + l2);
        }
        this.connectionToTimes.put(httpConnection, new TimeValues(l2, l, timeUnit));
    }

    public boolean remove(HttpConnection httpConnection) {
        TimeValues timeValues = this.connectionToTimes.remove(httpConnection);
        if (timeValues == null) {
            this.log.warn("Removing a connection that never existed!");
            return false;
        }
        return System.currentTimeMillis() <= TimeValues.access$000(timeValues);
    }

    public void removeAll() {
        this.connectionToTimes.clear();
    }

    public void closeIdleConnections(long l) {
        long l2 = System.currentTimeMillis() - l;
        if (this.log.isDebugEnabled()) {
            this.log.debug("Checking for connections, idle timeout: " + l2);
        }
        for (Map.Entry<HttpConnection, TimeValues> entry : this.connectionToTimes.entrySet()) {
            HttpConnection httpConnection = entry.getKey();
            TimeValues timeValues = entry.getValue();
            long l3 = TimeValues.access$100(timeValues);
            if (l3 > l2) continue;
            if (this.log.isDebugEnabled()) {
                this.log.debug("Closing idle connection, connection time: " + l3);
            }
            try {
                httpConnection.close();
            } catch (IOException iOException) {
                this.log.debug("I/O error closing connection", iOException);
            }
        }
    }

    public void closeExpiredConnections() {
        long l = System.currentTimeMillis();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Checking for expired connections, now: " + l);
        }
        for (Map.Entry<HttpConnection, TimeValues> entry : this.connectionToTimes.entrySet()) {
            HttpConnection httpConnection = entry.getKey();
            TimeValues timeValues = entry.getValue();
            if (TimeValues.access$000(timeValues) > l) continue;
            if (this.log.isDebugEnabled()) {
                this.log.debug("Closing connection, expired @: " + TimeValues.access$000(timeValues));
            }
            try {
                httpConnection.close();
            } catch (IOException iOException) {
                this.log.debug("I/O error closing connection", iOException);
            }
        }
    }

    private static class TimeValues {
        private final long timeAdded;
        private final long timeExpires;

        TimeValues(long l, long l2, TimeUnit timeUnit) {
            this.timeAdded = l;
            this.timeExpires = l2 > 0L ? l + timeUnit.toMillis(l2) : Long.MAX_VALUE;
        }

        static long access$000(TimeValues timeValues) {
            return timeValues.timeExpires;
        }

        static long access$100(TimeValues timeValues) {
            return timeValues.timeAdded;
        }
    }
}

