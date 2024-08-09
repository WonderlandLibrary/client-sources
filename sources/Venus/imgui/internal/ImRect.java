/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.internal;

import imgui.ImVec2;
import java.util.Objects;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ImRect
implements Cloneable {
    public final ImVec2 min = new ImVec2();
    public final ImVec2 max = new ImVec2();

    public ImRect() {
    }

    public ImRect(ImVec2 imVec2, ImVec2 imVec22) {
        this.min.x = imVec2.x;
        this.min.y = imVec2.y;
        this.max.x = imVec22.x;
        this.max.y = imVec22.y;
    }

    public ImRect(float f, float f2, float f3, float f4) {
        this.min.x = f;
        this.min.y = f2;
        this.max.x = f3;
        this.max.y = f4;
    }

    public ImRect(ImRect imRect) {
        this(imRect.min, imRect.max);
    }

    public void set(ImRect imRect) {
        this.min.x = imRect.min.x;
        this.min.y = imRect.min.y;
        this.max.x = imRect.max.x;
        this.max.y = imRect.max.y;
    }

    public String toString() {
        return "ImRect{min=" + this.min + ", max=" + this.max + '}';
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ImRect imRect = (ImRect)object;
        return Objects.equals(this.min, imRect.min) && Objects.equals(this.max, imRect.max);
    }

    public int hashCode() {
        return Objects.hash(this.min, this.max);
    }

    public ImRect clone() {
        return new ImRect(this);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

