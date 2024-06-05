// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.gui.element;

import org.lwjgl.opengl.Display;
import java.util.Iterator;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import java.util.List;
import gg.childtrafficking.smokex.gui.animation.Animation;

public class Element
{
    private float renderX;
    private float renderY;
    public float x;
    public float y;
    private String identifier;
    private Animation animation;
    private final List<Element> elements;
    private Element parent;
    protected final Minecraft mc;
    public boolean visible;
    private boolean absolute;
    private HAlignment horizontalAlignment;
    private VAlignment verticalAlignment;
    private float width;
    private float height;
    public boolean clickable;
    
    public Element(final String identifier, final float x, final float y) {
        this(identifier, x, y, 0.0f, 0.0f);
    }
    
    public Element(final String identifier, final float x, final float y, final float width, final float height) {
        this.elements = new ArrayList<Element>();
        this.mc = Minecraft.getMinecraft();
        this.visible = true;
        this.absolute = false;
        this.horizontalAlignment = HAlignment.LEFT;
        this.verticalAlignment = VAlignment.TOP;
        this.identifier = identifier;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void render(final double partialTicks) {
        if (this.animation != null && this.animation.process(partialTicks, this)) {
            this.animation = null;
        }
        try {
            for (final Element child : this.elements) {
                child.render(partialTicks);
            }
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    public void hovered(final double partialTicks) {
    }
    
    public Element setAnimation(final Animation animation) {
        if (this.animation != null && animation.x == this.animation.x && animation.y == this.animation.y) {
            return this;
        }
        animation.startX = this.x;
        animation.startY = this.y;
        animation.startWidth = this.width;
        animation.startHeight = this.height;
        this.animation = animation;
        return this;
    }
    
    public Animation getAnimation() {
        return this.animation;
    }
    
    public Element addElement(final Element element) {
        element.parent = this;
        element.updatePosition();
        this.elements.add(element);
        return element;
    }
    
    public Element addElements(final Element... e) {
        for (final Element element : this.elements) {
            this.addElement(element);
        }
        return this;
    }
    
    public Element getElement(final String identifier) {
        for (final Element child : this.elements) {
            if (child.getIdentifier().equalsIgnoreCase(identifier)) {
                return child;
            }
        }
        return null;
    }
    
    public Element removeElement(final String identifier) {
        for (int i = 0; i < this.elements.size(); ++i) {
            if (this.elements.get(i).getIdentifier().equalsIgnoreCase(identifier)) {
                this.elements.remove(this.elements.get(i));
                --i;
            }
        }
        return this;
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
    
    public List<Element> getElements() {
        return this.elements;
    }
    
    public Element setParent(final Element element) {
        this.parent.elements.remove(this);
        element.addElement(this);
        this.parent = element;
        return this;
    }
    
    public Element getParent() {
        return this.parent;
    }
    
    public boolean isAtPosition(final double x, final double y) {
        return this.x == x && this.y == y;
    }
    
    public Element setPosition(final float x, final float y) {
        if (this.x != x || this.y != y) {
            this.x = x;
            this.y = y;
            this.updatePosition();
        }
        return this;
    }
    
    public Element setX(final float x) {
        return this.setPosition(x, this.y);
    }
    
    public Element setY(final float y) {
        return this.setPosition(this.x, y);
    }
    
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public Element setAbsolute(final boolean absolute) {
        if (absolute) {
            this.absolute = true;
            this.x = this.renderX;
            this.y = this.renderY;
        }
        else {
            this.x = this.renderX - this.parent.renderX;
            this.y = this.renderY - this.parent.renderY;
            this.absolute = false;
        }
        this.updatePosition();
        return this;
    }
    
    public Element setHAlignment(final HAlignment alignment) {
        if (alignment != this.horizontalAlignment) {
            this.horizontalAlignment = alignment;
            this.updatePosition();
        }
        return this;
    }
    
    public Element setVAlignment(final VAlignment alignment) {
        if (alignment != this.verticalAlignment) {
            this.verticalAlignment = alignment;
            this.updatePosition();
        }
        return this;
    }
    
    public Element setWidth(final float width) {
        return this.setSize(width, this.height);
    }
    
    public Element setHeight(final float height) {
        return this.setSize(this.width, height);
    }
    
    public Element setSize(final float width, final float height) {
        if (this.width != width || this.height != height) {
            this.width = width;
            this.height = height;
            this.updatePosition();
        }
        return this;
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public float getRenderX() {
        return this.renderX;
    }
    
    public float getRenderY() {
        return this.renderY;
    }
    
    public void clearElements() {
        this.elements.clear();
    }
    
    public void updatePosition() {
        float parentWidth;
        float parentHeight;
        if (this.parent != null) {
            this.renderX = this.parent.renderX;
            this.renderY = this.parent.renderY;
            parentWidth = this.parent.width;
            parentHeight = this.parent.height;
        }
        else {
            this.renderX = 0.0f;
            this.renderY = 0.0f;
            parentWidth = Display.getWidth() / 2.0f;
            parentHeight = Display.getHeight() / 2.0f;
        }
        switch (this.horizontalAlignment) {
            case RIGHT: {
                this.renderX += parentWidth - this.x - this.width;
                break;
            }
            case CENTER: {
                this.renderX += parentWidth / 2.0f + this.x - this.width / 2.0f;
                break;
            }
            case LEFT: {
                this.renderX += this.x;
                break;
            }
        }
        switch (this.verticalAlignment) {
            case TOP: {
                this.renderY += this.y;
                break;
            }
            case CENTER: {
                this.renderY = this.renderY + parentHeight / 2.0f + this.y - this.height / 2.0f;
                break;
            }
            case BOTTOM: {
                this.renderY = this.renderY + parentHeight - this.y - this.height;
                break;
            }
        }
        for (final Element child : this.elements) {
            child.updatePosition();
        }
    }
}
