package net.optifine.util;

import lombok.Getter;

import java.awt.*;

@Getter
public class DynamicDimension {
    private boolean relative = false;
    private final float width;
    private final float height;

    public DynamicDimension(boolean relative, float width, float height) {
        this.relative = relative;
        this.width = width;
        this.height = height;
    }

    public Dimension getDimension(int baseWidth, int baseHeight) {
        return this.relative ? new Dimension((int) (this.width * (float) baseWidth), (int) (this.height * (float) baseHeight)) : new Dimension((int) this.width, (int) this.height);
    }

    public int hashCode() {
        int i = this.relative ? 1 : 0;
        i = i * 37 + (int) this.width;
        return i * 37 + (int) this.height;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DynamicDimension dynamicdimension)) {
            return false;
        } else {

            if (this.relative != dynamicdimension.relative) {
                return false;
            } else if (this.width != dynamicdimension.width) {
                return false;
            } else {
                return this.height == dynamicdimension.height;
            }
        }
    }
}
