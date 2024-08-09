/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package oshi.software.os.windows.nt;

import oshi.hardware.Processor;

public class CentralProcessor
implements Processor {
    private String _vendor;
    private String _name;
    private String _identifier;

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
        return this._identifier;
    }

    public void setIdentifier(String string) {
        this._identifier = string;
    }

    public boolean isCpu64bit() {
        throw new UnsupportedOperationException();
    }

    public void setCpu64(boolean bl) {
        throw new UnsupportedOperationException();
    }

    public String getStepping() {
        throw new UnsupportedOperationException();
    }

    public void setStepping(String string) {
        throw new UnsupportedOperationException();
    }

    public String getModel() {
        throw new UnsupportedOperationException();
    }

    public void setModel(String string) {
        throw new UnsupportedOperationException();
    }

    public String getFamily() {
        throw new UnsupportedOperationException();
    }

    public void setFamily(String string) {
        throw new UnsupportedOperationException();
    }

    public float getLoad() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return this._name;
    }
}

