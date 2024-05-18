// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.newclick.elements.onetheme.mini;

import com.klintos.twelve.utils.GuiUtils;
import com.klintos.twelve.gui.newclick.elements.base.Element;
import com.klintos.twelve.mod.Search;

public class MiniBlockSelector extends MiniElement
{
    private Search mod;
    
    public MiniBlockSelector(final int posX, final int posY, final Search mod, final Element parent) {
        super(posX, posY, 92, 92, parent);
        this.mod = mod;
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        GuiUtils.drawFineBorderedRect(this.getPosX() + this.dragX, this.getPosY() + this.dragY, this.getPosX() + this.getWidth() + this.dragX, this.getPosY() + this.getHeight() + this.dragY, -36752, -13882324);
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
    }
}
