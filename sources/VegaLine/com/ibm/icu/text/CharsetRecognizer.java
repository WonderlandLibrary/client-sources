/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

abstract class CharsetRecognizer {
    CharsetRecognizer() {
    }

    abstract String getName();

    public String getLanguage() {
        return null;
    }

    abstract CharsetMatch match(CharsetDetector var1);
}

