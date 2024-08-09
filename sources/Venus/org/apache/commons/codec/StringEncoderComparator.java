/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec;

import java.util.Comparator;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class StringEncoderComparator
implements Comparator {
    private final StringEncoder stringEncoder;

    @Deprecated
    public StringEncoderComparator() {
        this.stringEncoder = null;
    }

    public StringEncoderComparator(StringEncoder stringEncoder) {
        this.stringEncoder = stringEncoder;
    }

    public int compare(Object object, Object object2) {
        int n = 0;
        try {
            Comparable comparable = (Comparable)this.stringEncoder.encode(object);
            Comparable comparable2 = (Comparable)this.stringEncoder.encode(object2);
            n = comparable.compareTo(comparable2);
        } catch (EncoderException encoderException) {
            n = 0;
        }
        return n;
    }
}

