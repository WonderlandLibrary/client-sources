/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.type;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ImDouble
extends Number
implements Cloneable,
Comparable<ImDouble> {
    private final double[] data = new double[]{0.0};

    public ImDouble() {
    }

    public ImDouble(ImDouble imDouble) {
        this.data[0] = imDouble.data[0];
    }

    public ImDouble(double d) {
        this.set(d);
    }

    public double get() {
        return this.data[0];
    }

    public double[] getData() {
        return this.data;
    }

    public void set(double d) {
        this.data[0] = d;
    }

    public void set(ImDouble imDouble) {
        this.set(imDouble.get());
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
        ImDouble imDouble = (ImDouble)object;
        return this.data[0] == imDouble.data[0];
    }

    public int hashCode() {
        return Double.hashCode(this.data[0]);
    }

    public ImDouble clone() {
        return new ImDouble(this);
    }

    @Override
    public int compareTo(ImDouble imDouble) {
        return Double.compare(this.get(), imDouble.get());
    }

    @Override
    public int intValue() {
        return (int)this.get();
    }

    @Override
    public long longValue() {
        return (long)this.get();
    }

    @Override
    public float floatValue() {
        return (float)this.get();
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
        return this.compareTo((ImDouble)object);
    }
}

