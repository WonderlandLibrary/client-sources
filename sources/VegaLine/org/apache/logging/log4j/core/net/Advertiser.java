/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net;

import java.util.Map;

public interface Advertiser {
    public Object advertise(Map<String, String> var1);

    public void unadvertise(Object var1);
}

