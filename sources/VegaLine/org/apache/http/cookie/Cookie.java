/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.cookie;

import java.util.Date;

public interface Cookie {
    public String getName();

    public String getValue();

    public String getComment();

    public String getCommentURL();

    public Date getExpiryDate();

    public boolean isPersistent();

    public String getDomain();

    public String getPath();

    public int[] getPorts();

    public boolean isSecure();

    public int getVersion();

    public boolean isExpired(Date var1);
}

