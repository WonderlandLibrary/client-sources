/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.HpackUtil;
import io.netty.util.internal.ObjectUtil;

class HpackHeaderField {
    static final int HEADER_ENTRY_OVERHEAD = 32;
    final CharSequence name;
    final CharSequence value;

    static long sizeOf(CharSequence charSequence, CharSequence charSequence2) {
        return charSequence.length() + charSequence2.length() + 32;
    }

    HpackHeaderField(CharSequence charSequence, CharSequence charSequence2) {
        this.name = ObjectUtil.checkNotNull(charSequence, "name");
        this.value = ObjectUtil.checkNotNull(charSequence2, "value");
    }

    final int size() {
        return this.name.length() + this.value.length() + 32;
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public final boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof HpackHeaderField)) {
            return true;
        }
        HpackHeaderField hpackHeaderField = (HpackHeaderField)object;
        return (HpackUtil.equalsConstantTime(this.name, hpackHeaderField.name) & HpackUtil.equalsConstantTime(this.value, hpackHeaderField.value)) != 0;
    }

    public String toString() {
        return this.name + ": " + this.value;
    }
}

