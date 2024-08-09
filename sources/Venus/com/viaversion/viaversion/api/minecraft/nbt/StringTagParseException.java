/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.nbt;

import java.io.IOException;

class StringTagParseException
extends IOException {
    private static final long serialVersionUID = -3001637554903912905L;
    private final CharSequence buffer;
    private final int position;

    public StringTagParseException(String string, CharSequence charSequence, int n) {
        super(string);
        this.buffer = charSequence;
        this.position = n;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "(at position " + this.position + ")";
    }
}

