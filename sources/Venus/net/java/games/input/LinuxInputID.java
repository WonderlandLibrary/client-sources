/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import net.java.games.input.Controller;
import net.java.games.input.LinuxNativeTypesMap;

final class LinuxInputID {
    private final int bustype;
    private final int vendor;
    private final int product;
    private final int version;

    public LinuxInputID(int n, int n2, int n3, int n4) {
        this.bustype = n;
        this.vendor = n2;
        this.product = n3;
        this.version = n4;
    }

    public final Controller.PortType getPortType() {
        return LinuxNativeTypesMap.getPortType(this.bustype);
    }

    public final String toString() {
        return "LinuxInputID: bustype = 0x" + Integer.toHexString(this.bustype) + " | vendor = 0x" + Integer.toHexString(this.vendor) + " | product = 0x" + Integer.toHexString(this.product) + " | version = 0x" + Integer.toHexString(this.version);
    }
}

