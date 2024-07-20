/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.handler.codec.http.Cookie;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

@Deprecated
public class DefaultCookie
extends io.netty.handler.codec.http.cookie.DefaultCookie
implements Cookie {
    private String comment;
    private String commentUrl;
    private boolean discard;
    private Set<Integer> ports = Collections.emptySet();
    private Set<Integer> unmodifiablePorts = this.ports;
    private int version;

    public DefaultCookie(String name, String value) {
        super(name, value);
    }

    @Override
    @Deprecated
    public String getName() {
        return this.name();
    }

    @Override
    @Deprecated
    public String getValue() {
        return this.value();
    }

    @Override
    @Deprecated
    public String getDomain() {
        return this.domain();
    }

    @Override
    @Deprecated
    public String getPath() {
        return this.path();
    }

    @Override
    @Deprecated
    public String getComment() {
        return this.comment();
    }

    @Override
    @Deprecated
    public String comment() {
        return this.comment;
    }

    @Override
    @Deprecated
    public void setComment(String comment) {
        this.comment = this.validateValue("comment", comment);
    }

    @Override
    @Deprecated
    public String getCommentUrl() {
        return this.commentUrl();
    }

    @Override
    @Deprecated
    public String commentUrl() {
        return this.commentUrl;
    }

    @Override
    @Deprecated
    public void setCommentUrl(String commentUrl) {
        this.commentUrl = this.validateValue("commentUrl", commentUrl);
    }

    @Override
    @Deprecated
    public boolean isDiscard() {
        return this.discard;
    }

    @Override
    @Deprecated
    public void setDiscard(boolean discard) {
        this.discard = discard;
    }

    @Override
    @Deprecated
    public Set<Integer> getPorts() {
        return this.ports();
    }

    @Override
    @Deprecated
    public Set<Integer> ports() {
        if (this.unmodifiablePorts == null) {
            this.unmodifiablePorts = Collections.unmodifiableSet(this.ports);
        }
        return this.unmodifiablePorts;
    }

    @Override
    @Deprecated
    public void setPorts(int ... ports) {
        if (ports == null) {
            throw new NullPointerException("ports");
        }
        int[] portsCopy = (int[])ports.clone();
        if (portsCopy.length == 0) {
            this.ports = Collections.emptySet();
            this.unmodifiablePorts = this.ports;
        } else {
            TreeSet<Integer> newPorts = new TreeSet<Integer>();
            for (int p : portsCopy) {
                if (p <= 0 || p > 65535) {
                    throw new IllegalArgumentException("port out of range: " + p);
                }
                newPorts.add(p);
            }
            this.ports = newPorts;
            this.unmodifiablePorts = null;
        }
    }

    @Override
    @Deprecated
    public void setPorts(Iterable<Integer> ports) {
        TreeSet<Integer> newPorts = new TreeSet<Integer>();
        for (int p : ports) {
            if (p <= 0 || p > 65535) {
                throw new IllegalArgumentException("port out of range: " + p);
            }
            newPorts.add(p);
        }
        if (newPorts.isEmpty()) {
            this.ports = Collections.emptySet();
            this.unmodifiablePorts = this.ports;
        } else {
            this.ports = newPorts;
            this.unmodifiablePorts = null;
        }
    }

    @Override
    @Deprecated
    public long getMaxAge() {
        return this.maxAge();
    }

    @Override
    @Deprecated
    public int getVersion() {
        return this.version();
    }

    @Override
    @Deprecated
    public int version() {
        return this.version;
    }

    @Override
    @Deprecated
    public void setVersion(int version) {
        this.version = version;
    }
}

