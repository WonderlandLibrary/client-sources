/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package oshi.software.os.mac.local;

import oshi.hardware.Memory;
import oshi.util.ExecutingCommand;

public class GlobalMemory
implements Memory {
    private long totalMemory = 0L;

    public long getAvailable() {
        long l = 0L;
        for (String string : ExecutingCommand.runNative("vm_stat")) {
            String[] stringArray;
            if (string.startsWith("Pages free:")) {
                stringArray = string.split(":\\s+");
                l += new Long(stringArray[0].replace(".", "")).longValue();
                continue;
            }
            if (!string.startsWith("Pages speculative:")) continue;
            stringArray = string.split(":\\s+");
            l += new Long(stringArray[0].replace(".", "")).longValue();
        }
        return l *= 4096L;
    }

    public long getTotal() {
        if (this.totalMemory == 0L) {
            this.totalMemory = new Long(ExecutingCommand.getFirstAnswer("sysctl -n hw.memsize"));
        }
        return this.totalMemory;
    }
}

