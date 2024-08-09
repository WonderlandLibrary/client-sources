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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class InetAddressConverter
implements IStringConverter<InetAddress> {
    public InetAddress convert(String string) {
        try {
            return InetAddress.getByName(string);
        } catch (UnknownHostException unknownHostException) {
            throw new IllegalArgumentException(string, unknownHostException);
        }
    }

    public Object convert(String string) {
        return this.convert(string);
    }
}

