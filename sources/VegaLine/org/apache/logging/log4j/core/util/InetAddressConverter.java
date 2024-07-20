/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.beust.jcommander.IStringConverter
 */
package org.apache.logging.log4j.core.util;

import com.beust.jcommander.IStringConverter;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressConverter
implements IStringConverter<InetAddress> {
    public InetAddress convert(String host) {
        try {
            return InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException(host, e);
        }
    }
}

