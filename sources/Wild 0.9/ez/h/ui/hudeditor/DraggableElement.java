package ez.h.ui.hudeditor;

import ez.h.utils.*;
import ez.h.ui.fonts.*;

public class DraggableElement
{
    public float y;
    public float width;
    public float y1;
    protected bib mc;
    public float x1;
    public float height;
    public String name;
    public float x;
    public boolean isDragging;
    
    public void mouseClicked(float scale, float scale2, final int n) {
        scale = ScaleUtils.getScale((int)scale);
        scale2 = ScaleUtils.getScale((int)scale2);
        if (this.isHover(scale, scale2, this.x, this.y, this.width, this.height) && n == 0) {
            this.x1 = ScaleUtils.getScale((int)(scale - this.x));
            this.y1 = ScaleUtils.getScale((int)(scale2 - this.y));
            this.isDragging = true;
        }
    }
    
    public boolean isHover(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        return n >= n3 && n <= n3 + n5 && n2 >= n4 && n2 <= n4 + n6;
    }
    
    public DraggableElement(final String name, final float width, final float height) {
        this.mc = bib.z();
        this.isDragging = false;
        this.width = width;
        this.height = height;
        this.name = name;
        HUDEditor.draggableRegistry.add(this);
    }
    
    public void render(float scale, float scale2, final float n, final boolean b) {
        scale = ScaleUtils.getScale((int)scale);
        scale2 = ScaleUtils.getScale((int)scale2);
        if (this.isDragging) {
            this.x = scale - this.x1;
            this.y = scale2 - this.y1;
        }
        ScaleUtils.scale_pre();
        RenderUtils.drawRectWH(this.x, this.y, this.width, this.height, Integer.MIN_VALUE);
        RenderUtils.drawOutlinedRect(this.x, this.y, this.width, this.height, 517977373 + 1177030699 - 284042277 + 736517852, 2.0f);
        CFontManager.montserrat.drawCenteredString(this.name, this.x + this.width / 2.0f, this.y + this.height / 2.0f - CFontManager.montserrat.FONT_HEIGHT / 2.0f, -1);
        ScaleUtils.scale_post();
    }
    
    public void mouseReleased(final float n, final float n2, final int n3) {
        this.isDragging = false;
    }
}
