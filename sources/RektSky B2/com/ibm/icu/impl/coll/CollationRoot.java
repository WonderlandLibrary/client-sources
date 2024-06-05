package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.*;
import java.util.*;
import java.io.*;
import java.nio.*;

public final class CollationRoot
{
    private static final CollationTailoring rootSingleton;
    private static final RuntimeException exception;
    
    public static final CollationTailoring getRoot() {
        if (CollationRoot.exception != null) {
            throw CollationRoot.exception;
        }
        return CollationRoot.rootSingleton;
    }
    
    public static final CollationData getData() {
        final CollationTailoring root = getRoot();
        return root.data;
    }
    
    static final CollationSettings getSettings() {
        final CollationTailoring root = getRoot();
        return root.settings.readOnly();
    }
    
    static {
        CollationTailoring t = null;
        RuntimeException e2 = null;
        try {
            final ByteBuffer bytes = ICUBinary.getRequiredData("coll/ucadata.icu");
            final CollationTailoring t2 = new CollationTailoring(null);
            CollationDataReader.read(null, bytes, t2);
            t = t2;
        }
        catch (IOException e4) {
            e2 = new MissingResourceException("IOException while reading CLDR root data", "CollationRoot", "data/icudt62b/coll/ucadata.icu");
        }
        catch (RuntimeException e3) {
            e2 = e3;
        }
        rootSingleton = t;
        exception = e2;
    }
}
