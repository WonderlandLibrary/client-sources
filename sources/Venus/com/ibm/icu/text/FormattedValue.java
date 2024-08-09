/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.ConstrainedFieldPosition;
import java.text.AttributedCharacterIterator;

public interface FormattedValue
extends CharSequence {
    @Override
    public String toString();

    public <A extends Appendable> A appendTo(A var1);

    public boolean nextPosition(ConstrainedFieldPosition var1);

    public AttributedCharacterIterator toCharacterIterator();
}

