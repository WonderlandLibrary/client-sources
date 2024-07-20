/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.util;

import org.lwjgl.util.WritableDimension;

public interface ReadableDimension {
    public int getWidth();

    public int getHeight();

    public void getSize(WritableDimension var1);
}

