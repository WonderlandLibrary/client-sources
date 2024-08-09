/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

public class BidiClassifier {
    protected Object context;

    public BidiClassifier(Object object) {
        this.context = object;
    }

    public void setContext(Object object) {
        this.context = object;
    }

    public Object getContext() {
        return this.context;
    }

    public int classify(int n) {
        return 0;
    }
}

