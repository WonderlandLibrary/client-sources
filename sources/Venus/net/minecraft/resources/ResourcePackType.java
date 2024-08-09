/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

public enum ResourcePackType {
    CLIENT_RESOURCES("assets"),
    SERVER_DATA("data");

    private final String directoryName;

    private ResourcePackType(String string2) {
        this.directoryName = string2;
    }

    public String getDirectoryName() {
        return this.directoryName;
    }
}

