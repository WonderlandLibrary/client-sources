/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.util.management;

import pw.vertexcode.util.Nameable;

public class Manager
implements Nameable {
    private final String name;

    public Manager(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

