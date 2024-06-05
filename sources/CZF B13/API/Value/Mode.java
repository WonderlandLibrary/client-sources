package gq.vapu.czfclient.API.Value;

public class Mode<V extends Enum> extends Value<V> {
    private final V[] modes;

    public Mode(String displayName, String name, Enum[] modes, V value) {
        super(displayName, name);
        this.modes = (V[]) modes;
        setValue(value);
    }

    public V[] getModes() {
        return this.modes;
    }

    public String getModeAsString() {
        return getValue().name();
    }

    public void setMode(String mode) {
        V[] arrayOfV = this.modes;
        int n = arrayOfV.length;
        int n2 = 0;
        while (n2 < n) {
            V e = arrayOfV[n2];
            if (e.name().equalsIgnoreCase(mode))
                setValue(e);
            n2++;
        }
    }

    public boolean isValid(String name) {
        V[] arrayOfV = this.modes;
        int n = arrayOfV.length;
        int n2 = 0;
        while (n2 < n) {
            V e = arrayOfV[n2];
            if (e.name().equalsIgnoreCase(name))
                return true;
            n2++;
        }
        return false;
    }
}
