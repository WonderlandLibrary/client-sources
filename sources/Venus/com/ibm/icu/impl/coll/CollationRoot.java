/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.coll.CollationData;
import com.ibm.icu.impl.coll.CollationDataReader;
import com.ibm.icu.impl.coll.CollationSettings;
import com.ibm.icu.impl.coll.CollationTailoring;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.MissingResourceException;

public final class CollationRoot {
    private static final CollationTailoring rootSingleton;
    private static final RuntimeException exception;

    public static final CollationTailoring getRoot() {
        if (exception != null) {
            throw exception;
        }
        return rootSingleton;
    }

    public static final CollationData getData() {
        CollationTailoring collationTailoring = CollationRoot.getRoot();
        return collationTailoring.data;
    }

    static final CollationSettings getSettings() {
        CollationTailoring collationTailoring = CollationRoot.getRoot();
        return collationTailoring.settings.readOnly();
    }

    static {
        CollationTailoring collationTailoring = null;
        RuntimeException runtimeException = null;
        try {
            ByteBuffer byteBuffer = ICUBinary.getRequiredData("coll/ucadata.icu");
            CollationTailoring collationTailoring2 = new CollationTailoring(null);
            CollationDataReader.read(null, byteBuffer, collationTailoring2);
            collationTailoring = collationTailoring2;
        } catch (IOException iOException) {
            runtimeException = new MissingResourceException("IOException while reading CLDR root data", "CollationRoot", "data/icudt66b/coll/ucadata.icu");
        } catch (RuntimeException runtimeException2) {
            runtimeException = runtimeException2;
        }
        rootSingleton = collationTailoring;
        exception = runtimeException;
    }
}

