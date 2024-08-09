/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.type;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ImInt
extends Number
implements Cloneable,
Comparable<ImInt> {
    private final int[] data = new int[]{0};

    public ImInt() {
    }

    public ImInt(ImInt imInt) {
        this.data[0] = imInt.data[0];
    }

    public ImInt(int n) {
        this.set(n);
    }

    public int get() {
        return this.data[0];
    }

    public int[] getData() {
        return this.data;
    }

    public void set(int n) {
        this.data[0] = n;
    }

    public void set(ImInt imInt) {
        this.set(imInt.get());
    }

    public String toString() {
        return String.valueOf(this.get());
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ImInt imInt = (ImInt)object;
        return this.data[0] == imInt.data[0];
    }

    public int hashCode() {
        return Integer.hashCode(this.data[0]);
    }

    public ImInt clone() {
        return new ImInt(this);
    }

    @Override
    public int compareTo(ImInt imInt) {
        return Integer.compare(this.get(), imInt.get());
    }

    @Override
    public int intValue() {
        return this.get();
    }

    @Override
    public long longValue() {
        return this.get();
    }

    @Override
    public float floatValue() {
        return this.get();
    }

    @Override
    public double doubleValue() {
        return this.get();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((ImInt)object);
    }
}

