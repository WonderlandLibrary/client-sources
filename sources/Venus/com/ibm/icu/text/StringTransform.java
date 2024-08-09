/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.Transform;

public interface StringTransform
extends Transform<String, String> {
    @Override
    public String transform(String var1);
}

