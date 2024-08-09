/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.BackoffManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.Clock;
import org.apache.http.impl.client.SystemClock;
import org.apache.http.pool.ConnPoolControl;
import org.apache.http.util.Args;

public class AIMDBackoffManager
implements BackoffManager {
    private final ConnPoolControl<HttpRoute> connPerRoute;
    private final Clock clock;
    private final Map<HttpRoute, Long> lastRouteProbes;
    private final Map<HttpRoute, Long> lastRouteBackoffs;
    private long coolDown = 5000L;
    private double backoffFactor = 0.5;
    private int cap = 2;

    public AIMDBackoffManager(ConnPoolControl<HttpRoute> connPoolControl) {
        this(connPoolControl, new SystemClock());
    }

    AIMDBackoffManager(ConnPoolControl<HttpRoute> connPoolControl, Clock clock) {
        this.clock = clock;
        this.connPerRoute = connPoolControl;
        this.lastRouteProbes = new HashMap<HttpRoute, Long>();
        this.lastRouteBackoffs = new HashMap<HttpRoute, Long>();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void backOff(HttpRoute httpRoute) {
        ConnPoolControl<HttpRoute> connPoolControl = this.connPerRoute;
        synchronized (connPoolControl) {
            int n = this.connPerRoute.getMaxPerRoute(httpRoute);
            Long l = this.getLastUpdate(this.lastRouteBackoffs, httpRoute);
            long l2 = this.clock.getCurrentTime();
            if (l2 - l < this.coolDown) {
                return;
            }
            this.connPerRoute.setMaxPerRoute(httpRoute, this.getBackedOffPoolSize(n));
            this.lastRouteBackoffs.put(httpRoute, l2);
        }
    }

    private int getBackedOffPoolSize(int n) {
        if (n <= 1) {
            return 0;
        }
        return (int)Math.floor(this.backoffFactor * (double)n);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void probe(HttpRoute httpRoute) {
        ConnPoolControl<HttpRoute> connPoolControl = this.connPerRoute;
        synchronized (connPoolControl) {
            int n = this.connPerRoute.getMaxPerRoute(httpRoute);
            int n2 = n >= this.cap ? this.cap : n + 1;
            Long l = this.getLastUpdate(this.lastRouteProbes, httpRoute);
            Long l2 = this.getLastUpdate(this.lastRouteBackoffs, httpRoute);
            long l3 = this.clock.getCurrentTime();
            if (l3 - l < this.coolDown || l3 - l2 < this.coolDown) {
                return;
            }
            this.connPerRoute.setMaxPerRoute(httpRoute, n2);
            this.lastRouteProbes.put(httpRoute, l3);
        }
    }

    private Long getLastUpdate(Map<HttpRoute, Long> map, HttpRoute httpRoute) {
        Long l = map.get(httpRoute);
        if (l == null) {
            l = 0L;
        }
        return l;
    }

    public void setBackoffFactor(double d) {
        Args.check(d > 0.0 && d < 1.0, "Backoff factor must be 0.0 < f < 1.0");
        this.backoffFactor = d;
    }

    public void setCooldownMillis(long l) {
        Args.positive(this.coolDown, "Cool down");
        this.coolDown = l;
    }

    public void setPerHostConnectionCap(int n) {
        Args.positive(n, "Per host connection cap");
        this.cap = n;
    }
}

