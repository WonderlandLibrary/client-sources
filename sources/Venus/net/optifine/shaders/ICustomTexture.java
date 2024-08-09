/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

public interface ICustomTexture {
    public int getTextureId();

    public int getTextureUnit();

    public void deleteTexture();

    default public int getTarget() {
        return 0;
    }
}

