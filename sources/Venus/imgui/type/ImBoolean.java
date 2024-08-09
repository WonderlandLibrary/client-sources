/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.type;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ImBoolean
implements Cloneable,
Comparable<ImBoolean> {
    private final boolean[] data = new boolean[]{false};

    public ImBoolean() {
    }

    public ImBoolean(ImBoolean imBoolean) {
        this.data[0] = imBoolean.data[0];
    }

    public ImBoolean(boolean bl) {
        this.data[0] = bl;
    }

    public boolean get() {
        return this.data[0];
    }

    public boolean[] getData() {
        return this.data;
    }

    public void set(boolean bl) {
        this.data[0] = bl;
    }

    public void set(ImBoolean imBoolean) {
        this.set(imBoolean.get());
    }

    public String toString() {
        return String.valueOf(this.data[0]);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ImBoolean imBoolean = (ImBoolean)object;
        return this.data[0] == imBoolean.data[0];
    }

    public int hashCode() {
        return Boolean.hashCode(this.data[0]);
    }

    public ImBoolean clone() {
        return new ImBoolean(this);
    }

    @Override
    public int compareTo(ImBoolean imBoolean) {
        return Boolean.compare(this.get(), imBoolean.get());
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((ImBoolean)object);
    }
}

