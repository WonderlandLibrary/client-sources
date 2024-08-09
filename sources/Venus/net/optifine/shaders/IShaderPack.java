/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import java.io.InputStream;

public interface IShaderPack {
    public String getName();

    public InputStream getResourceAsStream(String var1);

    public boolean hasDirectory(String var1);

    public void close();
}

