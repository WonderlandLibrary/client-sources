/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package oshi.software.os.linux.proc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import oshi.software.os.OperatingSystemVersion;

public class OSVersionInfoEx
implements OperatingSystemVersion {
    private String _version = null;
    private String _codeName = null;
    private String version = null;

    public OSVersionInfoEx() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileReader("/etc/os-release"));
        } catch (FileNotFoundException fileNotFoundException) {
            return;
        }
        scanner.useDelimiter("\n");
        while (scanner.hasNext()) {
            String[] stringArray = scanner.next().split("=");
            if (stringArray[0].equals("VERSION_ID")) {
                this.setVersion(stringArray[5].replaceAll("^\"|\"$", ""));
            }
            if (!stringArray[0].equals("VERSION")) continue;
            stringArray[1] = stringArray[5].replaceAll("^\"|\"$", "");
            String[] stringArray2 = stringArray[5].split("[()]");
            if (stringArray2.length <= 1) {
                stringArray2 = stringArray[5].split(", ");
            }
            if (stringArray2.length > 1) {
                this.setCodeName(stringArray2[0]);
                continue;
            }
            this.setCodeName(stringArray[5]);
        }
        scanner.close();
    }

    public String getCodeName() {
        return this._codeName;
    }

    public String getVersion() {
        return this._version;
    }

    public void setCodeName(String string) {
        this._codeName = string;
    }

    public void setVersion(String string) {
        this._version = string;
    }

    public String toString() {
        if (this.version == null) {
            this.version = this.getVersion() + " (" + this.getCodeName() + ")";
        }
        return this.version;
    }
}

