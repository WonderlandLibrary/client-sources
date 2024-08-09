/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package oshi.software.os.windows;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import java.util.ArrayList;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Memory;
import oshi.hardware.Processor;
import oshi.software.os.windows.nt.CentralProcessor;
import oshi.software.os.windows.nt.GlobalMemory;

public class WindowsHardwareAbstractionLayer
implements HardwareAbstractionLayer {
    private Processor[] _processors = null;
    private Memory _memory = null;

    public Memory getMemory() {
        if (this._memory == null) {
            this._memory = new GlobalMemory();
        }
        return this._memory;
    }

    public Processor[] getProcessors() {
        if (this._processors == null) {
            String[] stringArray;
            String string = "HARDWARE\\DESCRIPTION\\System\\CentralProcessor";
            ArrayList<CentralProcessor> arrayList = new ArrayList<CentralProcessor>();
            for (String string2 : stringArray = Advapi32Util.registryGetKeys(WinReg.HKEY_LOCAL_MACHINE, "HARDWARE\\DESCRIPTION\\System\\CentralProcessor")) {
                String string3 = "HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\" + string2;
                CentralProcessor centralProcessor = new CentralProcessor();
                centralProcessor.setIdentifier(Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, string3, "Identifier"));
                centralProcessor.setName(Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, string3, "ProcessorNameString"));
                centralProcessor.setVendor(Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, string3, "VendorIdentifier"));
                arrayList.add(centralProcessor);
            }
            this._processors = arrayList.toArray(new Processor[0]);
        }
        return this._processors;
    }
}

