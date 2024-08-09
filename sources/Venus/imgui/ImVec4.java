/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import java.util.Objects;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ImVec4
implements Cloneable {
    public float x;
    public float y;
    public float z;
    public float w;

    public ImVec4() {
    }

    public ImVec4(float f, float f2, float f3, float f4) {
        this.set(f, f2, f3, f4);
    }

    public ImVec4(ImVec4 imVec4) {
        this(imVec4.x, imVec4.y, imVec4.z, imVec4.w);
    }

    public ImVec4 set(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.w = f4;
        return this;
    }

    public ImVec4 set(ImVec4 imVec4) {
        return this.set(imVec4.x, imVec4.y, imVec4.z, imVec4.w);
    }

    public ImVec4 plus(float f, float f2, float f3, float f4) {
        this.x += f;
        this.y += f2;
        this.z += f3;
        this.w += f4;
        return this;
    }

    public ImVec4 plus(ImVec4 imVec4) {
        return this.plus(imVec4.x, imVec4.y, imVec4.z, imVec4.w);
    }

    public ImVec4 minus(float f, float f2, float f3, float f4) {
        this.x -= f;
        this.y -= f2;
        this.z -= f3;
        this.w -= f4;
        return this;
    }

    public ImVec4 minus(ImVec4 imVec4) {
        return this.minus(imVec4.x, imVec4.y, imVec4.z, imVec4.w);
    }

    public ImVec4 times(float f, float f2, float f3, float f4) {
        this.x *= f;
        this.y *= f2;
        this.z *= f3;
        this.w *= f4;
        return this;
    }

    public ImVec4 times(ImVec4 imVec4) {
        return this.times(imVec4.x, imVec4.y, imVec4.z, imVec4.w);
    }

    public String toString() {
        return "ImVec4{x=" + this.x + ", y=" + this.y + ", z=" + this.z + ", w=" + this.w + '}';
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ImVec4 imVec4 = (ImVec4)object;
        return Float.compare(imVec4.x, this.x) == 0 && Float.compare(imVec4.y, this.y) == 0 && Float.compare(imVec4.z, this.z) == 0 && Float.compare(imVec4.w, this.w) == 0;
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.x), Float.valueOf(this.y), Float.valueOf(this.z), Float.valueOf(this.w));
    }

    public ImVec4 clone() {
        return new ImVec4(this);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

