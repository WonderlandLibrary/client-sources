/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package oshi.software.os.linux.proc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import oshi.hardware.Processor;
import oshi.util.FormatUtil;

public class CentralProcessor
implements Processor {
    private String _vendor;
    private String _name;
    private String _identifier = null;
    private String _stepping;
    private String _model;
    private String _family;
    private boolean _cpu64;

    public String getVendor() {
        return this._vendor;
    }

    public void setVendor(String string) {
        this._vendor = string;
    }

    public String getName() {
        return this._name;
    }

    public void setName(String string) {
        this._name = string;
    }

    public String getIdentifier() {
        if (this._identifier == null) {
            StringBuilder stringBuilder = new StringBuilder();
            if (this.getVendor().contentEquals("GenuineIntel")) {
                stringBuilder.append(this.isCpu64bit() ? "Intel64" : "x86");
            } else {
                stringBuilder.append(this.getVendor());
            }
            stringBuilder.append(" Family ");
            stringBuilder.append(this.getFamily());
            stringBuilder.append(" Model ");
            stringBuilder.append(this.getModel());
            stringBuilder.append(" Stepping ");
            stringBuilder.append(this.getStepping());
            this._identifier = stringBuilder.toString();
        }
        return this._identifier;
    }

    public void setIdentifier(String string) {
        this._identifier = string;
    }

    public boolean isCpu64bit() {
        return this._cpu64;
    }

    public void setCpu64(boolean bl) {
        this._cpu64 = bl;
    }

    public String getStepping() {
        return this._stepping;
    }

    public void setStepping(String string) {
        this._stepping = string;
    }

    public String getModel() {
        return this._model;
    }

    public void setModel(String string) {
        this._model = string;
    }

    public String getFamily() {
        return this._family;
    }

    public void setFamily(String string) {
        this._family = string;
    }

    public float getLoad() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileReader("/proc/stat"));
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Problem with: /proc/stat");
            System.err.println(fileNotFoundException.getMessage());
            return -1.0f;
        }
        scanner.useDelimiter("\n");
        String[] stringArray = scanner.next().split(" ");
        ArrayList<Float> arrayList = new ArrayList<Float>();
        for (String string : stringArray) {
            if (!string.matches("-?\\d+(\\.\\d+)?")) continue;
            arrayList.add(Float.valueOf(string));
        }
        float f = (((Float)arrayList.get(0)).floatValue() + ((Float)arrayList.get(2)).floatValue()) * 100.0f / (((Float)arrayList.get(0)).floatValue() + ((Float)arrayList.get(2)).floatValue() + ((Float)arrayList.get(3)).floatValue());
        return FormatUtil.round(f, 2);
    }

    public String toString() {
        return this.getName();
    }
}

