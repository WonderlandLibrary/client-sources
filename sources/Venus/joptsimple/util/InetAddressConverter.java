/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import joptsimple.ValueConversionException;
import joptsimple.ValueConverter;
import joptsimple.internal.Messages;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class InetAddressConverter
implements ValueConverter<InetAddress> {
    @Override
    public InetAddress convert(String string) {
        try {
            return InetAddress.getByName(string);
        } catch (UnknownHostException unknownHostException) {
            throw new ValueConversionException(this.message(string));
        }
    }

    @Override
    public Class<InetAddress> valueType() {
        return InetAddress.class;
    }

    @Override
    public String valuePattern() {
        return null;
    }

    private String message(String string) {
        return Messages.message(Locale.getDefault(), "joptsimple.ExceptionMessages", InetAddressConverter.class, "message", string);
    }

    @Override
    public Object convert(String string) {
        return this.convert(string);
    }
}

