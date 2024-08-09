/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package oshi.software.os.linux;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Memory;
import oshi.hardware.Processor;
import oshi.software.os.linux.proc.CentralProcessor;
import oshi.software.os.linux.proc.GlobalMemory;

public class LinuxHardwareAbstractionLayer
implements HardwareAbstractionLayer {
    private static final String SEPARATOR = "\\s+:\\s";
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
            ArrayList<CentralProcessor> arrayList = new ArrayList<CentralProcessor>();
            Scanner scanner = null;
            try {
                scanner = new Scanner(new FileReader("/proc/cpuinfo"));
            } catch (FileNotFoundException fileNotFoundException) {
                System.err.println("Problem with: /proc/cpuinfo");
                System.err.println(fileNotFoundException.getMessage());
                return null;
            }
            scanner.useDelimiter("\n");
            CentralProcessor centralProcessor = null;
            while (scanner.hasNext()) {
                String string = scanner.next();
                if (string.equals("")) {
                    if (centralProcessor != null) {
                        arrayList.add(centralProcessor);
                    }
                    centralProcessor = null;
                    continue;
                }
                if (centralProcessor == null) {
                    centralProcessor = new CentralProcessor();
                }
                if (string.startsWith("model name\t")) {
                    centralProcessor.setName(string.split(SEPARATOR)[1]);
                    continue;
                }
                if (string.startsWith("flags\t")) {
                    String[] stringArray = string.split(SEPARATOR)[1].split(" ");
                    boolean bl = false;
                    for (String string2 : stringArray) {
                        if (!string2.equalsIgnoreCase("LM")) continue;
                        bl = true;
                        break;
                    }
                    centralProcessor.setCpu64(bl);
                    continue;
                }
                if (string.startsWith("cpu family\t")) {
                    centralProcessor.setFamily(string.split(SEPARATOR)[1]);
                    continue;
                }
                if (string.startsWith("model\t")) {
                    centralProcessor.setModel(string.split(SEPARATOR)[1]);
                    continue;
                }
                if (string.startsWith("stepping\t")) {
                    centralProcessor.setStepping(string.split(SEPARATOR)[1]);
                    continue;
                }
                if (!string.startsWith("vendor_id")) continue;
                centralProcessor.setVendor(string.split(SEPARATOR)[1]);
            }
            scanner.close();
            if (centralProcessor != null) {
                arrayList.add(centralProcessor);
            }
            this._processors = arrayList.toArray(new Processor[0]);
        }
        return this._processors;
    }
}

