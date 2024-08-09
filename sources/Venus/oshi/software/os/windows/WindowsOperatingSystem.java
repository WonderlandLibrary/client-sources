/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package oshi.software.os.windows;

import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystemVersion;
import oshi.software.os.windows.nt.OSVersionInfoEx;

public class WindowsOperatingSystem
implements OperatingSystem {
    private OperatingSystemVersion _version = null;

    public OperatingSystemVersion getVersion() {
        if (this._version == null) {
            this._version = new OSVersionInfoEx();
        }
        return this._version;
    }

    public String getFamily() {
        return "Windows";
    }

    public String getManufacturer() {
        return "Microsoft";
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getManufacturer());
        stringBuilder.append(" ");
        stringBuilder.append(this.getFamily());
        stringBuilder.append(" ");
        stringBuilder.append(this.getVersion().toString());
        return stringBuilder.toString();
    }
}

