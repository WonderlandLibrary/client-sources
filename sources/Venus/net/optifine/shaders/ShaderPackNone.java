/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import java.io.InputStream;
import net.optifine.shaders.IShaderPack;

public class ShaderPackNone
implements IShaderPack {
    @Override
    public void close() {
    }

    @Override
    public InputStream getResourceAsStream(String string) {
        return null;
    }

    @Override
    public boolean hasDirectory(String string) {
        return true;
    }

    @Override
    public String getName() {
        return "OFF";
    }
}

