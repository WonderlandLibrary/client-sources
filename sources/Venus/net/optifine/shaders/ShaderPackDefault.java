/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import java.io.InputStream;
import net.optifine.Config;
import net.optifine.shaders.IShaderPack;

public class ShaderPackDefault
implements IShaderPack {
    @Override
    public void close() {
    }

    @Override
    public InputStream getResourceAsStream(String string) {
        return Config.getOptiFineResourceStream(string);
    }

    @Override
    public String getName() {
        return "(internal)";
    }

    @Override
    public boolean hasDirectory(String string) {
        return true;
    }
}

