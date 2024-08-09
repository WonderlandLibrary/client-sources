/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package oshi.software.os.linux.proc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import oshi.hardware.Memory;

public class GlobalMemory
implements Memory {
    private long totalMemory = 0L;

    public long getAvailable() {
        long l = 0L;
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileReader("/proc/meminfo"));
        } catch (FileNotFoundException fileNotFoundException) {
            return l;
        }
        scanner.useDelimiter("\n");
        while (scanner.hasNext()) {
            String string = scanner.next();
            if (!string.startsWith("MemFree:") && !string.startsWith("MemAvailable:")) continue;
            String[] stringArray = string.split("\\s+");
            l = new Long(stringArray[0]);
            if (stringArray[5].equals("kB")) {
                l *= 1024L;
            }
            if (!stringArray[5].equals("MemAvailable:")) continue;
            break;
        }
        scanner.close();
        return l;
    }

    public long getTotal() {
        if (this.totalMemory == 0L) {
            Scanner scanner = null;
            try {
                scanner = new Scanner(new FileReader("/proc/meminfo"));
            } catch (FileNotFoundException fileNotFoundException) {
                this.totalMemory = 0L;
                return this.totalMemory;
            }
            scanner.useDelimiter("\n");
            while (scanner.hasNext()) {
                String string = scanner.next();
                if (!string.startsWith("MemTotal:")) continue;
                String[] stringArray = string.split("\\s+");
                this.totalMemory = new Long(stringArray[0]);
                if (!stringArray[5].equals("kB")) break;
                this.totalMemory *= 1024L;
                break;
            }
            scanner.close();
        }
        return this.totalMemory;
    }
}

