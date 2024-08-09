/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.client;

public final class Vec2i {
    private final int x;
    private final int y;

    public Vec2i(int n, int n2) {
        this.x = n;
        this.y = n2;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof Vec2i)) {
            return true;
        }
        Vec2i vec2i = (Vec2i)object;
        if (this.getX() != vec2i.getX()) {
            return true;
        }
        return this.getY() != vec2i.getY();
    }

    public int hashCode() {
        int n = 59;
        int n2 = 1;
        n2 = n2 * 59 + this.getX();
        n2 = n2 * 59 + this.getY();
        return n2;
    }

    public String toString() {
        return "Vec2i(x=" + this.getX() + ", y=" + this.getY() + ")";
    }
}

