// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.draggable;

import xyz.niggfaclient.utils.render.RenderUtils;
import xyz.niggfaclient.module.Module;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import xyz.niggfaclient.utils.Utils;

public class Dragging extends Utils
{
    @Expose
    @SerializedName("x")
    private float xPos;
    @Expose
    @SerializedName("y")
    private float yPos;
    public float initialXVal;
    public float initialYVal;
    private float startX;
    private float startY;
    private boolean dragging;
    private float width;
    private float height;
    @Expose
    @SerializedName("name")
    private final String name;
    private final Module module;
    
    public Dragging(final Module module, final String name, final float initialXVal, final float initialYVal) {
        this.module = module;
        this.name = name;
        this.xPos = initialXVal;
        this.yPos = initialYVal;
        this.initialXVal = initialXVal;
        this.initialYVal = initialYVal;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public String getName() {
        return this.name;
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public void setWidth(final float width) {
        this.width = width;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public void setHeight(final float height) {
        this.height = height;
    }
    
    public float getX() {
        return this.xPos;
    }
    
    public void setX(final float x) {
        this.xPos = x;
    }
    
    public float getY() {
        return this.yPos;
    }
    
    public void setY(final float y) {
        this.yPos = y;
    }
    
    public final void onDraw(final int mouseX, final int mouseY) {
        final boolean hovering = RenderUtils.isHovering(this.xPos, this.yPos - 0.5f, this.width, this.height + 1.0f, mouseX, mouseY);
        if (this.dragging) {
            this.xPos = mouseX - this.startX;
            this.yPos = mouseY - this.startY;
        }
        if (hovering) {
            RenderUtils.drawOutlinedRoundedRect(this.xPos, this.yPos - 0.5, this.width, this.height + 1.0f, 0.0, 2.0f, -1);
        }
    }
    
    public final void onClick(final int mouseX, final int mouseY, final int button) {
        final boolean hovering = RenderUtils.isHovering(this.xPos, this.yPos - 0.5f, this.width, this.height + 1.0f, mouseX, mouseY);
        if (button == 0 && hovering) {
            this.dragging = true;
            this.startX = (float)(int)(mouseX - this.xPos);
            this.startY = (float)(int)(mouseY - this.yPos);
        }
    }
    
    public final void onRelease(final int button) {
        if (button == 0) {
            this.dragging = false;
        }
    }
}
