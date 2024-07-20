/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.RbnfLenientScanner;
import com.ibm.icu.util.ULocale;

public interface RbnfLenientScannerProvider {
    public RbnfLenientScanner get(ULocale var1, String var2);
}

