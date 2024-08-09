/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.type;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ImFloat
extends Number
implements Cloneable,
Comparable<ImFloat> {
    private final float[] data = new float[]{0.0f};

    public ImFloat() {
    }

    public ImFloat(ImFloat imFloat) {
        this.data[0] = imFloat.data[0];
    }

    public ImFloat(float f) {
        this.set(f);
    }

    public float get() {
        return this.data[0];
    }

    public float[] getData() {
        return this.data;
    }

    public void set(float f) {
        this.data[0] = f;
    }

    public void set(ImFloat imFloat) {
        this.set(imFloat.get());
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
        ImFloat imFloat = (ImFloat)object;
        return this.data[0] == imFloat.data[0];
    }

    public int hashCode() {
        return Float.hashCode(this.data[0]);
    }

    public ImFloat clone() {
        return new ImFloat(this);
    }

    @Override
    public int compareTo(ImFloat imFloat) {
        return Float.compare(this.get(), imFloat.get());
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
        return this.compareTo((ImFloat)object);
    }
}

