/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.type;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ImLong
extends Number
implements Cloneable,
Comparable<ImLong> {
    private final long[] data = new long[]{0L};

    public ImLong() {
    }

    public ImLong(ImLong imLong) {
        this.data[0] = imLong.data[0];
    }

    public ImLong(long l) {
        this.set(l);
    }

    public long get() {
        return this.data[0];
    }

    public long[] getData() {
        return this.data;
    }

    public void set(long l) {
        this.data[0] = l;
    }

    public void set(ImLong imLong) {
        this.set(imLong.get());
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
        ImLong imLong = (ImLong)object;
        return this.data[0] == imLong.data[0];
    }

    public int hashCode() {
        return Long.hashCode(this.data[0]);
    }

    public ImLong clone() {
        return new ImLong(this);
    }

    @Override
    public int compareTo(ImLong imLong) {
        return Long.compare(this.get(), imLong.get());
    }

    @Override
    public int intValue() {
        return (int)this.get();
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
        return this.compareTo((ImLong)object);
    }
}

