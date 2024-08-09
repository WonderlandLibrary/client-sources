/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import java.text.FieldPosition;

public final class DontCareFieldPosition
extends FieldPosition {
    public static final DontCareFieldPosition INSTANCE = new DontCareFieldPosition();

    private DontCareFieldPosition() {
        super(-913028704);
    }

    @Override
    public void setBeginIndex(int n) {
    }

    @Override
    public void setEndIndex(int n) {
    }
}

