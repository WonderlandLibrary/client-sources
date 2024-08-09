/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.Controller;
import net.java.games.input.RawDevice;
import net.java.games.input.SetupAPIDevice;

abstract class RawDeviceInfo {
    RawDeviceInfo() {
    }

    public abstract Controller createControllerFromDevice(RawDevice var1, SetupAPIDevice var2) throws IOException;

    public abstract int getUsage();

    public abstract int getUsagePage();

    public abstract long getHandle();

    public final boolean equals(Object object) {
        if (!(object instanceof RawDeviceInfo)) {
            return true;
        }
        RawDeviceInfo rawDeviceInfo = (RawDeviceInfo)object;
        return rawDeviceInfo.getUsage() == this.getUsage() && rawDeviceInfo.getUsagePage() == this.getUsagePage();
    }

    public final int hashCode() {
        return this.getUsage() ^ this.getUsagePage();
    }
}

