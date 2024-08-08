// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.gui.components.items.buttons;

import java.util.Iterator;
import me.perry.mcdonalds.features.gui.components.Component;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import me.perry.mcdonalds.features.gui.McDonaldsGui;
import me.perry.mcdonalds.util.RenderUtil;
import me.perry.mcdonalds.features.modules.client.ClickGui;
import me.perry.mcdonalds.McDonalds;
import me.perry.mcdonalds.features.gui.components.items.Item;

public class Button extends Item
{
    private boolean state;
    
    public Button(final String name) {
        super(name);
        this.height = 15;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        RenderUtil.drawRect(this.x, this.y, this.x + this.width, this.y + this.height - 0.5f, this.getState() ? (this.isHovering(mouseX, mouseY) ? McDonalds.colorManager.getColorWithAlpha(McDonalds.moduleManager.getModuleByClass(ClickGui.class).alpha.getValue()) : McDonalds.colorManager.getColorWithAlpha(McDonalds.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue())) : (this.isHovering(mouseX, mouseY) ? -2007673515 : 290805077));
        McDonalds.textManager.drawStringWithShadow(this.getName(), this.x + 2.3f, this.y - 2.0f - McDonaldsGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            this.onMouseClick();
        }
    }
    
    public void onMouseClick() {
        this.state = !this.state;
        this.toggle();
        Button.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
    }
    
    public void toggle() {
    }
    
    public boolean getState() {
        return this.state;
    }
    
    @Override
    public int getHeight() {
        return 14;
    }
    
    public boolean isHovering(final int mouseX, final int mouseY) {
        for (final Component component : McDonaldsGui.getClickGui().getComponents()) {
            if (!component.drag) {
                continue;
            }
            return false;
        }
        return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + this.height;
    }
}
