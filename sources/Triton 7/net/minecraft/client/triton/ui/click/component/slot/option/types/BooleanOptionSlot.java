// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.ui.click.component.slot.option.types;

import net.minecraft.client.triton.impl.modules.render.Gui;
import net.minecraft.client.triton.management.option.types.BooleanOption;
import net.minecraft.client.triton.ui.click.component.slot.option.OptionSlotComponent;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.RenderUtils;

public class BooleanOptionSlot extends OptionSlotComponent<BooleanOption>
{
    public BooleanOptionSlot(final BooleanOption parent, final double x, final double y, final double width, final double height) {
        super(parent, x, y, width, height);
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        final boolean useDarkTheme = ((Gui)new Gui().getInstance()).isDarkTheme();
        RenderUtils.rectangle(this.getX() - 3, this.getY(), this.getX() + this.getWidth() + 3, this.getY() + this.getHeight(), this.hovering(mouseX, mouseY) ? 0x3effffff : 0x00000000);
        ClientUtils.clientFont().drawCenteredString(this.getParent().getDisplayName(), this.getX() + this.getWidth() / 2.0, this.getY() + 0.5 + this.getHeight() / 2.0 - ClientUtils.clientFont().FONT_HEIGHT / 2, ((boolean)this.getParent().getValue()) ? 0xceffffff : 0xFFb3b3b3);
    }
    
    @Override
    public void click(final int mouseX, final int mouseY, final int button) {
        if (button == 0) {
            this.getParent().setValue(Boolean.valueOf(!this.getParent().getValue()));
        }
    }
    
    @Override
    public void drag(final int mouseX, final int mouseY, final int button) {
    }
    
    @Override
    public void release(final int mouseX, final int mouseY, final int button) {
    }
    
    @Override
    public void keyPress(final int keyInt, final char keyChar) {
    }
}
