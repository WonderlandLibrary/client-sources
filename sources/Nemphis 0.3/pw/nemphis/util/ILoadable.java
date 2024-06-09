/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.util;

import java.io.IOException;

public interface ILoadable {
    public void load() throws IOException, ClassNotFoundException;

    public void save() throws IOException;
}

