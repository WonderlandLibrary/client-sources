// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.gui.click.component.slot.option.types;

import me.chrest.utils.ClientUtils;
import me.chrest.utils.RenderUtils;
import me.chrest.client.module.modules.render.Gui;
import me.chrest.client.option.types.BooleanOption;
import me.chrest.client.module.Module;

public class VisibilityOptionSlot extends BooleanOptionSlot
{
    private Module module;
    
    public VisibilityOptionSlot(final Module module, final double x, final double y, final double width, final double height) {
        super(null, x, y, width, height);
        this.module = module;
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        final boolean useDarkTheme = ((Gui)new Gui().getInstance()).isDarkTheme();
        RenderUtils.rectangleBordered(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), 0.5, RenderUtils.blend(useDarkTheme ? -14540254 : -40865, -16777216, this.module.isShown() ? (this.hovering(mouseX, mouseY) ? 0.8f : 1.0f) : (this.hovering(mouseX, mouseY) ? 0.6f : 0.7f)), -15658735);
        RenderUtils.rectangle(this.getX() + 1.0, this.getY() + 0.5, this.getX() + this.getWidth() - 1.0, this.getY() + 1.0, this.module.isShown() ? -1 : -1);
        ClientUtils.clientFont().drawCenteredString("Hidden", this.getX() + this.getWidth() / 2.0, this.getY() + 0.5 + this.getHeight() / 2.0 - ClientUtils.clientFont().FONT_HEIGHT / 2, -1710619);
    }
    
    @Override
    public void click(final int mouseX, final int mouseY, final int button) {
        if (button == 0) {
            this.module.setShown(!this.module.isShown());
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
