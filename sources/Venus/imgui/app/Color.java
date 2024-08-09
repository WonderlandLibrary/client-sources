/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.app;

import java.util.Arrays;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Color
implements Cloneable {
    public final float[] data = new float[4];

    public Color() {
    }

    public Color(float[] fArray) {
        this.set(fArray);
    }

    public Color(float f, float f2, float f3, float f4) {
        this.data[0] = f;
        this.data[1] = f2;
        this.data[2] = f3;
        this.data[3] = f4;
    }

    public final void set(float f, float f2, float f3, float f4) {
        this.data[0] = f;
        this.data[1] = f2;
        this.data[2] = f3;
        this.data[3] = f4;
    }

    public final void set(float[] fArray) {
        System.arraycopy(fArray, 0, this.data, 0, 4);
    }

    public final float getRed() {
        return this.data[0];
    }

    public final float getGreen() {
        return this.data[1];
    }

    public final float getBlue() {
        return this.data[2];
    }

    public final float getAlpha() {
        return this.data[3];
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        Color color = (Color)object;
        return Arrays.equals(this.data, color.data);
    }

    public int hashCode() {
        return Arrays.hashCode(this.data);
    }

    public String toString() {
        return "Color{data=" + Arrays.toString(this.data) + '}';
    }

    public Color clone() {
        return new Color(this.data);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

