// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.newclick.elements.base;

import net.minecraft.client.Minecraft;
import java.util.TimerTask;
import java.util.Timer;
import com.klintos.twelve.gui.newclick.elements.onetheme.ElementButton;
import java.util.Iterator;
import org.lwjgl.input.Mouse;
import java.util.ArrayList;
import java.util.List;
import com.klintos.twelve.gui.newclick.ClickGui;

public class Panel
{
    private String title;
    private boolean dragging;
    private boolean expanded;
    private boolean stopped;
    private boolean pinned;
    private int width;
    private int height;
    private int openHeight;
    private int posX;
    private int posY;
    private int lastX;
    private int lastY;
    protected int dragX;
    protected int dragY;
    private ClickGui parent;
    private List<Element> elements;
    private boolean isAlreadyOneClick;
    
    public Panel(final String title, final int posX, final int posY, final int width, final int height, final boolean expanded, final boolean pinned, final ClickGui parent) {
        this.elements = new ArrayList<Element>();
        this.title = title;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.expanded = expanded;
        this.pinned = pinned;
        this.parent = parent;
    }
    
    public Panel(final String title, final int posX, final int posY, final boolean expanded, final boolean pinned, final ClickGui parent) {
        this.elements = new ArrayList<Element>();
        this.title = title;
        this.posX = posX;
        this.posY = posY;
        this.width = 100;
        this.height = 20;
        this.expanded = expanded;
        this.pinned = pinned;
        this.parent = parent;
    }
    
    public void draw(final int mouseX, final int mouseY) {
        this.updatePanel(mouseX, mouseY);
    }
    
    public void updatePanel(final int mouseX, final int mouseY) {
        if (!Mouse.isButtonDown(0)) {
            this.setDrag(false);
        }
        if (this.isDragging()) {
            this.stopped = false;
            this.shiftX(mouseX - this.lastX);
            this.shiftY(mouseY - this.lastY);
        }
        else if (!this.stopped) {
            this.stopDragging();
        }
    }
    
    public void stopDragging() {
        this.setPosX(this.getPosX() + this.dragX);
        this.setPosY(this.getPosY() + this.dragY);
        this.dragX = 0;
        this.dragY = 0;
        for (final Element element : this.elements) {
            element.stopDragging();
        }
        this.parent.saveSettings();
        this.stopped = true;
    }
    
    public void updatePanelsElements() {
        int y = 20;
        for (final Element element : this.elements) {
            element.setPosY(this.getPosY() + y);
            if (element instanceof ElementButton) {
                final ElementButton button = (ElementButton)element;
                y += (button.isExpanded() ? (button.getOpenHeight() + 2) : (button.getHeight() + 2));
            }
            else {
                y += element.getHeight() + 2;
            }
        }
        this.setOpenHeight(y);
    }
    
    private boolean isDoubleClick() {
        if (this.isAlreadyOneClick) {
            this.isAlreadyOneClick = false;
            return true;
        }
        this.isAlreadyOneClick = true;
        final Timer t = new Timer("doubleclickTimer", false);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
            	isAlreadyOneClick = false;
            }
        }, 500L);
        return false;
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        final boolean isHover = mouseX >= this.getPosX() + this.dragX && mouseY >= this.getPosY() + this.dragY && mouseX <= this.getPosX() + this.getWidth() + this.dragX && mouseY <= this.getPosY() + this.getHeight() + this.dragY;
        if (isHover) {
            if (button == 0) {
                if (this.isDoubleClick()) {
                    this.setPinned(!this.isPinned());
                    Minecraft.getMinecraft().thePlayer.playSound("random.click", 1.0f, 1.0f);
                }
                this.lastX = mouseX;
                this.lastY = mouseY;
                this.setDrag(true);
            }
            else if (button == 1) {
                this.setExpanded(!this.isExpanded());
            }
            this.parent.setPanelOnTop(this);
        }
        for (final Element element : this.elements) {
            element.mouseClicked(mouseX, mouseY, button);
        }
    }
    
    public void addElement(final Element element) {
        this.elements.add(element);
    }
    
    public List<Element> getElements() {
        return this.elements;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public int getPosX() {
        return this.posX;
    }
    
    public void setPosX(final int posX) {
        this.posX = posX;
    }
    
    public int getPosY() {
        return this.posY;
    }
    
    public void setPosY(final int posY) {
        this.posY = posY;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    public int getOpenHeight() {
        return this.openHeight;
    }
    
    public void setOpenHeight(final int openHeight) {
        this.openHeight = openHeight;
    }
    
    public boolean isDragging() {
        return this.dragging;
    }
    
    public void setDrag(final boolean dragging) {
        this.dragging = dragging;
    }
    
    public void shiftX(final int ammount) {
        this.dragX = ammount;
        for (final Element element : this.elements) {
            element.shiftX(ammount);
        }
    }
    
    public void shiftY(final int ammount) {
        this.dragY = ammount;
        for (final Element element : this.elements) {
            element.shiftY(ammount);
        }
    }
    
    public boolean isExpanded() {
        return this.expanded;
    }
    
    public void setExpanded(final boolean expanded) {
        this.expanded = expanded;
    }
    
    public boolean isPinned() {
        return this.pinned;
    }
    
    public void setPinned(final boolean pinned) {
        this.pinned = pinned;
    }
    
    public ClickGui getParent() {
        return this.parent;
    }
    
    static /* synthetic */ void access$0(final Panel panel, final boolean isAlreadyOneClick) {
        panel.isAlreadyOneClick = isAlreadyOneClick;
    }
}
