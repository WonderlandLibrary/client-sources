/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.fastutil;

public interface Size64 {
    public long size64();

    @Deprecated
    default public int size() {
        return (int)Math.min(Integer.MAX_VALUE, this.size64());
    }
}

