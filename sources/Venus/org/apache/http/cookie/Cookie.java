/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.cookie;

import java.util.Date;
import org.apache.http.annotation.Obsolete;

public interface Cookie {
    public String getName();

    public String getValue();

    @Obsolete
    public String getComment();

    @Obsolete
    public String getCommentURL();

    public Date getExpiryDate();

    public boolean isPersistent();

    public String getDomain();

    public String getPath();

    @Obsolete
    public int[] getPorts();

    public boolean isSecure();

    @Obsolete
    public int getVersion();

    public boolean isExpired(Date var1);
}

