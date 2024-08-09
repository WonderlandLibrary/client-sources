/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.drag;

public class Drag {
    private float xPos;
    private float yPos;
    private float startX;
    private float startY;
    private boolean dragging;

    public Drag(float f, float f2) {
        this.xPos = f;
        this.yPos = f2;
    }

    public float getX() {
        return this.xPos;
    }

    public void setX(float f) {
        this.xPos = f;
    }

    public float getY() {
        return this.yPos;
    }

    public void setY(float f) {
        this.yPos = f;
    }

    public final void onDraw(int n, int n2) {
        if (this.dragging) {
            this.xPos = (float)n - this.startX;
            this.yPos = (float)n2 - this.startY;
        }
    }

    public final void onDrawNegX(int n, int n2) {
        if (this.dragging) {
            this.xPos = -((float)n - this.startX);
            this.yPos = (float)n2 - this.startY;
        }
    }

    public final void onClick(int n, int n2, int n3, boolean bl) {
        if (n3 == 0 && bl) {
            this.dragging = true;
            this.startX = (int)((float)n - this.xPos);
            this.startY = (int)((float)n2 - this.yPos);
        }
    }

    public final void onClickAddX(int n, int n2, int n3, boolean bl) {
        if (n3 == 0 && bl) {
            this.dragging = true;
            this.startX = (int)((float)n + this.xPos);
            this.startY = (int)((float)n2 - this.yPos);
        }
    }

    public final void onRelease(int n) {
        if (n == 0) {
            this.dragging = false;
        }
    }
}

