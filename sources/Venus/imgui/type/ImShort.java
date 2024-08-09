/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.type;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ImShort
extends Number
implements Cloneable,
Comparable<ImShort> {
    private final short[] data = new short[]{0};

    public ImShort() {
    }

    public ImShort(ImShort imShort) {
        this.data[0] = imShort.data[0];
    }

    public ImShort(short s) {
        this.set(s);
    }

    public short get() {
        return this.data[0];
    }

    public short[] getData() {
        return this.data;
    }

    public void set(short s) {
        this.data[0] = s;
    }

    public void set(ImShort imShort) {
        this.set(imShort.get());
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
        ImShort imShort = (ImShort)object;
        return this.data[0] == imShort.data[0];
    }

    public int hashCode() {
        return Short.hashCode(this.data[0]);
    }

    public ImShort clone() {
        return new ImShort(this);
    }

    @Override
    public int compareTo(ImShort imShort) {
        return Short.compare(this.get(), imShort.get());
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
        return this.compareTo((ImShort)object);
    }
}

