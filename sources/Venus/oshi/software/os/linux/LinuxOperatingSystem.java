/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package oshi.software.os.linux;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystemVersion;
import oshi.software.os.linux.proc.OSVersionInfoEx;

public class LinuxOperatingSystem
implements OperatingSystem {
    private OperatingSystemVersion _version = null;
    private String _family = null;

    public String getFamily() {
        if (this._family == null) {
            Scanner scanner;
            try {
                scanner = new Scanner(new FileReader("/etc/os-release"));
            } catch (FileNotFoundException fileNotFoundException) {
                return "";
            }
            scanner.useDelimiter("\n");
            while (scanner.hasNext()) {
                String[] stringArray = scanner.next().split("=");
                if (!stringArray[0].equals("NAME")) continue;
                this._family = stringArray[5].replaceAll("^\"|\"$", "");
                break;
            }
            scanner.close();
        }
        return this._family;
    }

    public String getManufacturer() {
        return "GNU/Linux";
    }

    public OperatingSystemVersion getVersion() {
        if (this._version == null) {
            this._version = new OSVersionInfoEx();
        }
        return this._version;
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

