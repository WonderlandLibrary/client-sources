/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

final class LinuxAxisDescriptor {
    private int type;
    private int code;

    LinuxAxisDescriptor() {
    }

    public final void set(int n, int n2) {
        this.type = n;
        this.code = n2;
    }

    public final int getType() {
        return this.type;
    }

    public final int getCode() {
        return this.code;
    }

    public final int hashCode() {
        return this.type ^ this.code;
    }

    public final boolean equals(Object object) {
        if (!(object instanceof LinuxAxisDescriptor)) {
            return true;
        }
        LinuxAxisDescriptor linuxAxisDescriptor = (LinuxAxisDescriptor)object;
        return linuxAxisDescriptor.type == this.type && linuxAxisDescriptor.code == this.code;
    }

    public final String toString() {
        return "LinuxAxis: type = 0x" + Integer.toHexString(this.type) + ", code = 0x" + Integer.toHexString(this.code);
    }
}

