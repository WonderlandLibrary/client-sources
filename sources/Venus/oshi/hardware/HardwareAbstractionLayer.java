/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package oshi.hardware;

import oshi.hardware.Memory;
import oshi.hardware.Processor;

public interface HardwareAbstractionLayer {
    public Processor[] getProcessors();

    public Memory getMemory();
}

