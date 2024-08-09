/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

public class SkeletonSyntaxException
extends IllegalArgumentException {
    private static final long serialVersionUID = 7733971331648360554L;

    public SkeletonSyntaxException(String string, CharSequence charSequence) {
        super("Syntax error in skeleton string: " + string + ": " + charSequence);
    }

    public SkeletonSyntaxException(String string, CharSequence charSequence, Throwable throwable) {
        super("Syntax error in skeleton string: " + string + ": " + charSequence, throwable);
    }
}

