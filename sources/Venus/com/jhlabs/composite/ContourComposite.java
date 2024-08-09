/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.composite;

import com.jhlabs.composite.ContourCompositeContext;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;

public final class ContourComposite
implements Composite {
    private int offset;

    public ContourComposite(int n) {
        this.offset = n;
    }

    @Override
    public CompositeContext createContext(ColorModel colorModel, ColorModel colorModel2, RenderingHints renderingHints) {
        return new ContourCompositeContext(this.offset, colorModel, colorModel2);
    }

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object object) {
        return !(object instanceof ContourComposite);
    }
}

