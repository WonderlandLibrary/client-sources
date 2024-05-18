// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.click.panel;

import net.minecraft.client.Minecraft;
import java.util.TimerTask;
import java.util.Timer;
import org.lwjgl.input.Mouse;
import java.util.Iterator;
import java.util.ArrayList;
import com.klintos.twelve.gui.click.elements.ElementBase;
import java.util.List;

public abstract class PanelBase
{
    private boolean isAlreadyOneClick;
    private String PANEL_NAME;
    private List<ElementBase> PANEL_ELEMENTS;
    public int PANEL_POSX;
    public int PANEL_POSY;
    private int PANEL_HEIGHT;
    private int PANEL_OPENHEIGHT;
    private int PANEL_WIDTH;
    private boolean PANEL_DRAG;
    private int PANEL_DRAGX;
    private int PANEL_DRAGY;
    private boolean PANEL_OPEN;
    private boolean PANEL_PIN;
    private boolean PANEL_VISIBLE;
    
    protected PanelBase(final String NAME, final int POSX, final int POSY, final int WIDTH) {
        this.PANEL_ELEMENTS = new ArrayList<ElementBase>();
        this.PANEL_NAME = NAME;
        this.PANEL_POSX = POSX;
        this.PANEL_POSY = POSY;
        this.PANEL_HEIGHT = 15;
        this.PANEL_WIDTH = WIDTH;
    }
    
    protected PanelBase(final String NAME, final int POSX, final int POSY) {
        this.PANEL_ELEMENTS = new ArrayList<ElementBase>();
        this.PANEL_NAME = NAME;
        this.PANEL_POSX = POSX;
        this.PANEL_POSY = POSY;
        this.PANEL_HEIGHT = 15;
        this.PANEL_WIDTH = 110;
    }
    
    public void drawScreen(final int POSX, final int POSY) {
    }
    
    public void onGuiClosed() {
        this.setDrag(false);
    }
    
    public void mouseMovedOrUp(final int POSX, final int POSY, final int BUTTON) {
        for (final ElementBase i$2 : this.getElements()) {
            i$2.mouseMovedOrUp(POSX, POSY, BUTTON);
        }
    }
    
    protected void updatePanel(final int POSX, final int POSY) {
        if (this.getDrag()) {
            this.setPosX(POSX + this.getDragX());
            this.setPosY(POSY + this.getDragY());
            if (!Mouse.isButtonDown(0)) {
                this.setDrag(false);
            }
        }
        if (this.getOpen()) {
            this.setHeight(15 + this.getOpenHeight());
        }
        else {
            this.setHeight(15);
        }
    }
    
    public abstract void drawElements(final int p0, final int p1);
    
    public abstract void addElements();
    
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
                PanelBase.access$0(PanelBase.this, false);
            }
        }, 500L);
        return false;
    }
    
    public void mouseClicked(final int POSX, final int POSY, final int BUTTON) {
        if (POSX >= this.getPosX() && POSY >= this.getPosY() && POSX <= this.getPosX() + this.getWidth() && POSY <= this.getPosY() + 20 && BUTTON == 0) {
            if (POSX >= this.getPosX() && POSY >= this.getPosY() && POSX <= this.getPosX() + this.getWidth() && POSY <= this.getPosY() + 20 && BUTTON == 0) {
                this.setDrag(true);
                this.setDragX(this.getPosX() - POSX);
                this.setDragY(this.getPosY() - POSY);
            }
            if (POSX >= this.getPosX() && POSY >= this.getPosY() && POSX <= this.getPosX() + this.getWidth() && POSY <= this.getPosY() + 20 && BUTTON == 0 && this.isDoubleClick()) {
                this.setOpen(!this.getOpen());
                Minecraft.getMinecraft().thePlayer.playSound("random.click", 1.0f, 1.0f);
            }
        }
        else if (POSX >= this.getPosX() && POSY >= this.getPosY() && POSX <= this.getPosX() + this.getWidth() && POSY <= this.getPosY() + 20 && BUTTON == 1) {
            this.setPin(!this.getPin());
            Minecraft.getMinecraft().thePlayer.playSound("random.click", 1.0f, 1.0f);
        }
        for (final ElementBase i$2 : this.getElements()) {
            if (this.getOpen() && (POSX > 110 || POSY > 17)) {
                i$2.mouseClicked(POSX, POSY, BUTTON);
            }
        }
    }
    
    public String getName() {
        return this.PANEL_NAME;
    }
    
    public void setName(final String NAME) {
        this.PANEL_NAME = NAME;
    }
    
    public List<ElementBase> getElements() {
        return this.PANEL_ELEMENTS;
    }
    
    public void addElement(final ElementBase ELEMENT) {
        this.PANEL_ELEMENTS.add(ELEMENT);
    }
    
    public void removeElement(final ElementBase ELEMENT) {
        this.PANEL_ELEMENTS.remove(ELEMENT);
    }
    
    public int getPosX() {
        return this.PANEL_POSX;
    }
    
    public void setPosX(final int POSX) {
        this.PANEL_POSX = POSX;
    }
    
    public int getPosY() {
        return this.PANEL_POSY;
    }
    
    public void setPosY(final int POSY) {
        this.PANEL_POSY = POSY;
    }
    
    public int getHeight() {
        return this.PANEL_HEIGHT;
    }
    
    public void setHeight(final int HEIGHT) {
        this.PANEL_HEIGHT = HEIGHT;
    }
    
    public int getOpenHeight() {
        return this.PANEL_OPENHEIGHT;
    }
    
    public void setOpenHeight(final int OPENHEIGHT) {
        this.PANEL_OPENHEIGHT = OPENHEIGHT;
    }
    
    public int getWidth() {
        return this.PANEL_WIDTH;
    }
    
    public void setWidth(final int WIDTH) {
        this.PANEL_WIDTH = WIDTH;
    }
    
    public boolean getDrag() {
        return this.PANEL_DRAG;
    }
    
    public void setDrag(final boolean DRAG) {
        this.PANEL_DRAG = DRAG;
    }
    
    public int getDragX() {
        return this.PANEL_DRAGX;
    }
    
    public void setDragX(final int DRAGX) {
        this.PANEL_DRAGX = DRAGX;
    }
    
    public int getDragY() {
        return this.PANEL_DRAGY;
    }
    
    public void setDragY(final int DRAGY) {
        this.PANEL_DRAGY = DRAGY;
    }
    
    public boolean getOpen() {
        return this.PANEL_OPEN;
    }
    
    public void setOpen(final boolean OPEN) {
        this.PANEL_OPEN = OPEN;
    }
    
    public boolean getPin() {
        return this.PANEL_PIN;
    }
    
    public void setPin(final boolean PIN) {
        this.PANEL_PIN = PIN;
    }
    
    public boolean getVisible() {
        return this.PANEL_VISIBLE;
    }
    
    public void setVisible(final boolean VISIBLE) {
        this.PANEL_VISIBLE = VISIBLE;
    }
    
    static /* synthetic */ void access$0(final PanelBase panelBase, final boolean isAlreadyOneClick) {
        panelBase.isAlreadyOneClick = isAlreadyOneClick;
    }
}
