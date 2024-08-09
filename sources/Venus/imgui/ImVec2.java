/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import java.util.Objects;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ImVec2
implements Cloneable {
    public float x;
    public float y;

    public ImVec2() {
    }

    public ImVec2(float f, float f2) {
        this.set(f, f2);
    }

    public ImVec2(ImVec2 imVec2) {
        this(imVec2.x, imVec2.y);
    }

    public ImVec2 set(float f, float f2) {
        this.x = f;
        this.y = f2;
        return this;
    }

    public ImVec2 set(ImVec2 imVec2) {
        return this.set(imVec2.x, imVec2.y);
    }

    public ImVec2 plus(float f, float f2) {
        this.x += f;
        this.y += f2;
        return this;
    }

    public ImVec2 plus(ImVec2 imVec2) {
        return this.plus(imVec2.x, imVec2.y);
    }

    public ImVec2 minus(float f, float f2) {
        this.x -= f;
        this.y -= f2;
        return this;
    }

    public ImVec2 minus(ImVec2 imVec2) {
        return this.minus(imVec2.x, imVec2.y);
    }

    public ImVec2 times(float f, float f2) {
        this.x *= f;
        this.y *= f2;
        return this;
    }

    public ImVec2 times(ImVec2 imVec2) {
        return this.times(imVec2.x, imVec2.y);
    }

    public String toString() {
        return "ImVec2{x=" + this.x + ", y=" + this.y + '}';
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ImVec2 imVec2 = (ImVec2)object;
        return Float.compare(imVec2.x, this.x) == 0 && Float.compare(imVec2.y, this.y) == 0;
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.x), Float.valueOf(this.y));
    }

    public ImVec2 clone() {
        return new ImVec2(this);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

