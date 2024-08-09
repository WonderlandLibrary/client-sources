/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package oshi.software.os.mac.local;

import java.util.ArrayList;
import oshi.hardware.Processor;
import oshi.util.ExecutingCommand;

public class CentralProcessor
implements Processor {
    private String _vendor;
    private String _name;
    private String _identifier = null;
    private String _stepping;
    private String _model;
    private String _family;
    private Boolean _cpu64;

    public String getVendor() {
        if (this._vendor == null) {
            this._vendor = ExecutingCommand.getFirstAnswer("sysctl -n machdep.cpu.vendor");
        }
        return this._vendor;
    }

    public void setVendor(String string) {
        this._vendor = string;
    }

    public String getName() {
        if (this._name == null) {
            this._name = ExecutingCommand.getFirstAnswer("sysctl -n machdep.cpu.brand_string");
        }
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
        if (this._cpu64 == null) {
            this._cpu64 = ExecutingCommand.getFirstAnswer("sysctl -n hw.cpu64bit_capable").equals("1");
        }
        return this._cpu64;
    }

    public void setCpu64(boolean bl) {
        this._cpu64 = bl;
    }

    public String getStepping() {
        if (this._stepping == null) {
            this._stepping = ExecutingCommand.getFirstAnswer("sysctl -n machdep.cpu.stepping");
        }
        return this._stepping;
    }

    public void setStepping(String string) {
        this._stepping = string;
    }

    public String getModel() {
        if (this._model == null) {
            this._model = ExecutingCommand.getFirstAnswer("sysctl -n machdep.cpu.model");
        }
        return this._model;
    }

    public void setModel(String string) {
        this._model = string;
    }

    public String getFamily() {
        if (this._family == null) {
            this._family = ExecutingCommand.getFirstAnswer("sysctl -n machdep.cpu.family");
        }
        return this._family;
    }

    public void setFamily(String string) {
        this._family = string;
    }

    public float getLoad() {
        ArrayList<String> arrayList = ExecutingCommand.runNative("top -l 1 -R -F -n1");
        String[] stringArray = arrayList.get(3).split(" ");
        return 100.0f - Float.valueOf(stringArray[0].replace("%", "")).floatValue();
    }

    public String toString() {
        return this.getName();
    }
}

