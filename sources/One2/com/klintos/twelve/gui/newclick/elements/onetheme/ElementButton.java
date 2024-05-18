// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.newclick.elements.onetheme;

import net.minecraft.client.Minecraft;
import com.klintos.twelve.utils.GuiUtils;
import java.util.Iterator;
import com.klintos.twelve.gui.newclick.elements.onetheme.mini.MiniToggle;
import com.klintos.twelve.mod.value.ValueBoolean;
import com.klintos.twelve.gui.newclick.elements.onetheme.mini.MiniSlider;
import com.klintos.twelve.mod.value.ValueDouble;
import com.klintos.twelve.mod.value.Value;
import java.util.ArrayList;
import com.klintos.twelve.gui.newclick.elements.base.Panel;
import com.klintos.twelve.gui.newclick.elements.onetheme.mini.MiniElement;
import java.util.List;
import com.klintos.twelve.mod.Mod;
import com.klintos.twelve.gui.newclick.elements.base.Element;

public class ElementButton extends Element
{
    private Mod mod;
    private boolean expanded;
    private int openHeight;
    private List<MiniElement> minis;
    
    public ElementButton(final Mod mod, final int posX, final int posY, final Panel parent) {
        super(posX, posY, 96, 20, parent);
        this.minis = new ArrayList<MiniElement>();
        this.mod = mod;
        mod.getModName().equals("Search");
        this.initMiniElements();
    }
    
    private void initMiniElements() {
        if (this.mod.getValues().size() > 0) {
            final int x = 2;
            int y = 22;
            for (final Value value : this.sortMiniElements(this.mod.getValues())) {
                if (value instanceof ValueDouble) {
                    final MiniSlider slider = new MiniSlider(this.getPosX() + x, this.getPosY() + y, (ValueDouble)value, this);
                    this.minis.add(slider);
                    y += slider.getHeight() + 2;
                }
                else {
                    if (!(value instanceof ValueBoolean)) {
                        continue;
                    }
                    final MiniToggle toggle = new MiniToggle(this.getPosX() + x, this.getPosY() + y, (ValueBoolean)value, this);
                    this.minis.add(toggle);
                    y += toggle.getHeight() + 2;
                }
            }
            this.setOpenHeight(y);
        }
    }
    
    public void updateMiniElements() {
        if (this.minis.size() > 0) {
            int y = 22;
            for (final MiniElement mini : this.minis) {
                mini.setPosY(this.getPosY() + y);
                y += mini.getHeight() + 2;
            }
            this.setOpenHeight(y);
        }
    }
    
    public List<Value> sortMiniElements(final List<Value> values) {
        final List<Value> sorted = new ArrayList<Value>();
        for (final Value v : values) {
            if (v instanceof ValueBoolean) {
                sorted.add(v);
            }
        }
        for (final Value v : values) {
            if (v instanceof ValueDouble) {
                sorted.add(v);
            }
        }
        return sorted;
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        final boolean isHover = mouseX >= this.getPosX() + this.dragX && mouseY >= this.getPosY() + this.dragY && mouseX <= this.getPosX() + this.getWidth() + this.dragX && mouseY <= this.getPosY() + this.getHeight() + this.dragY;
        GuiUtils.drawFineBorderedRect(this.getPosX() + this.dragX, this.getPosY() + this.dragY, this.getPosX() + this.getWidth() + this.dragX, this.getPosY() + (this.isExpanded() ? this.getOpenHeight() : this.getHeight()) + this.dragY, -12303292, -13882324);
        GuiUtils.drawFineBorderedRect(this.getPosX() + this.dragX, this.getPosY() + this.dragY, this.getPosX() + this.getWidth() + this.dragX, this.getPosY() + this.getHeight() + this.dragY, isHover ? -36752 : -12303292, this.mod.getEnabled() ? -44976 : -13882324);
        GuiUtils.drawCentredStringWithShadow(this.mod.getModName(), this.getPosX() + this.getWidth() / 2 + this.dragX, this.getPosY() + 7 + this.dragY, this.mod.getEnabled() ? -1 : -7829368);
        if (this.minis.size() > 0) {
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.isExpanded() ? "v" : "...", this.getPosX() + this.getWidth() - 8 + this.dragX, this.getPosY() + (this.isExpanded() ? 8 : 5) + this.dragY, this.mod.getEnabled() ? -1 : -7829368);
            if (this.isExpanded()) {
                for (final MiniElement mini : this.minis) {
                    mini.draw(mouseX, mouseY);
                }
            }
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.getParent().isExpanded()) {
            final boolean isHover = mouseX >= this.getPosX() + this.dragX && mouseY >= this.getPosY() + this.dragY && mouseX <= this.getPosX() + this.getWidth() + this.dragX && mouseY <= this.getPosY() + this.getHeight() + this.dragY;
            if (isHover) {
                if (button == 0) {
                    this.mod.onToggle();
                    Minecraft.getMinecraft().thePlayer.playSound("random.click", 1.0f, 1.0f);
                }
                else if (button == 1 && this.minis.size() > 0) {
                    this.setExpanded(!this.isExpanded());
                    this.getParent().updatePanelsElements();
                    this.updateMiniElements();
                }
            }
        }
        for (final MiniElement mini : this.minis) {
            mini.mouseClicked(mouseX, mouseY, button);
        }
    }
    
    public int getOpenHeight() {
        return this.openHeight;
    }
    
    public void setOpenHeight(final int openHeight) {
        this.openHeight = openHeight;
    }
    
    public boolean isExpanded() {
        return this.expanded;
    }
    
    public void setExpanded(final boolean expanded) {
        this.expanded = expanded;
    }
    
    @Override
    public void shiftX(final int ammount) {
        this.dragX = ammount;
        for (final MiniElement mini : this.minis) {
            mini.shiftX(ammount);
        }
    }
    
    @Override
    public void shiftY(final int ammount) {
        this.dragY = ammount;
        for (final MiniElement mini : this.minis) {
            mini.shiftY(ammount);
        }
    }
    
    @Override
    public void stopDragging() {
        this.setPosX(this.getPosX() + this.dragX);
        this.setPosY(this.getPosY() + this.dragY);
        this.dragX = 0;
        this.dragY = 0;
        for (final MiniElement mini : this.minis) {
            mini.stopDragging();
        }
    }
}
