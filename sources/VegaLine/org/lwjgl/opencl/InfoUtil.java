/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opencl;

import org.lwjgl.opencl.CLObject;

interface InfoUtil<T extends CLObject> {
    public int getInfoInt(T var1, int var2);

    public long getInfoSize(T var1, int var2);

    public long[] getInfoSizeArray(T var1, int var2);

    public long getInfoLong(T var1, int var2);

    public String getInfoString(T var1, int var2);
}

