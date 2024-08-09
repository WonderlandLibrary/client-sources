/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.drag;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.utils.client.ClientUtil;
import mpp.venusfr.utils.client.Vec2i;
import mpp.venusfr.utils.math.MathUtil;
import net.minecraft.client.MainWindow;

public class Dragging {
    @Expose
    @SerializedName(value="x")
    private float xPos;
    @Expose
    @SerializedName(value="y")
    private float yPos;
    public float initialXVal;
    public float initialYVal;
    private float startX;
    private float startY;
    private boolean dragging;
    private float width;
    private float height;
    @Expose
    @SerializedName(value="name")
    private final String name;
    private final Function module;

    public Dragging(Function function, String string, float f, float f2) {
        this.module = function;
        this.name = string;
        this.xPos = f;
        this.yPos = f2;
        this.initialXVal = f;
        this.initialYVal = f2;
    }

    public Function getModule() {
        return this.module;
    }

    public String getName() {
        return this.name;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float f) {
        this.width = f;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float f) {
        this.height = f;
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

    public final void onDraw(int n, int n2, MainWindow mainWindow) {
        Vec2i vec2i = ClientUtil.getMouse(n, n2);
        n = vec2i.getX();
        n2 = vec2i.getY();
        if (this.dragging) {
            this.xPos = (float)n - this.startX;
            this.yPos = (float)n2 - this.startY;
            if (this.xPos + this.width > (float)mainWindow.scaledWidth()) {
                this.xPos = (float)mainWindow.scaledWidth() - this.width;
            }
            if (this.yPos + this.height > (float)mainWindow.scaledHeight()) {
                this.yPos = (float)mainWindow.scaledHeight() - this.height;
            }
            if (this.xPos < 0.0f) {
                this.xPos = 0.0f;
            }
            if (this.yPos < 0.0f) {
                this.yPos = 0.0f;
            }
        }
    }

    public final boolean onClick(double d, double d2, int n) {
        Vec2i vec2i = ClientUtil.getMouse((int)d, (int)d2);
        d = vec2i.getX();
        d2 = vec2i.getY();
        if (n == 0 && MathUtil.isHovered((float)d, (float)d2, this.xPos, this.yPos, this.width, this.height)) {
            this.dragging = true;
            this.startX = (int)(d - (double)this.xPos);
            this.startY = (int)(d2 - (double)this.yPos);
            return false;
        }
        return true;
    }

    public final void onRelease(int n) {
        if (n == 0) {
            this.dragging = false;
        }
    }
}

