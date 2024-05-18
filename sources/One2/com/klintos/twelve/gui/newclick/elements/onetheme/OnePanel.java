// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.newclick.elements.onetheme;

import java.util.Iterator;
import com.klintos.twelve.gui.newclick.elements.base.Element;
import net.minecraft.client.Minecraft;
import com.klintos.twelve.utils.GuiUtils;
import com.klintos.twelve.gui.newclick.ClickGui;
import com.klintos.twelve.gui.newclick.elements.base.Panel;

public class OnePanel extends Panel
{
    public OnePanel(final String title, final int posX, final int posY, final boolean expanded, final boolean pinned, final ClickGui parent) {
        super(title, posX, posY, 100, 20, expanded, pinned, parent);
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        this.updatePanel(mouseX, mouseY);
        GuiUtils.drawBorderedRect(this.getPosX() + this.dragX, this.getPosY() + this.dragY, this.getPosX() + this.getWidth() + this.dragX, this.getPosY() + (this.isExpanded() ? this.getOpenHeight() : this.getHeight()) + this.dragY, this.isPinned() ? -36752 : -11184811, -13421773);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.getTitle(), this.getPosX() + 5 + this.dragX, this.getPosY() + 7 + this.dragY, -36752);
        if (this.isExpanded()) {
            for (final Element element : this.getElements()) {
                element.draw(mouseX, mouseY);
            }
        }
    }
    
    @Override
    public void updatePanelsElements() {
        int y = 20;
        for (Element element : this.getElements()) {
            element.setPosY(this.getPosY() + y);
            if (element instanceof ElementButton) {
                ElementButton button = (ElementButton)element;
                button.updateMiniElements();
                y += button.isExpanded() ? button.getOpenHeight() + 2 : button.getHeight() + 2;
                continue;
            }
            y += element.getHeight() + 2;
        }
        this.setOpenHeight(y);
    }
}
