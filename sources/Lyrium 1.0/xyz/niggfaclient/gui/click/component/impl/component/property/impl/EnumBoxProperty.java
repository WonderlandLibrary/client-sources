// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.gui.click.component.impl.component.property.impl;

import xyz.niggfaclient.property.Property;
import net.minecraft.client.gui.Gui;
import xyz.niggfaclient.module.impl.render.HUD;
import xyz.niggfaclient.utils.render.RenderUtils;
import java.awt.Color;
import xyz.niggfaclient.font.Fonts;
import net.minecraft.client.gui.ScaledResolution;
import xyz.niggfaclient.gui.click.component.Component;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.gui.click.component.impl.component.property.PropertyComponent;
import xyz.niggfaclient.gui.click.component.impl.ExpandableComponent;

public final class EnumBoxProperty extends ExpandableComponent implements PropertyComponent
{
    private final EnumProperty<?> property;
    
    public EnumBoxProperty(final Component parent, final EnumProperty<?> property, final int x, final int y, final int width, final int height) {
        super(parent, property.getName(), x, y, width, height);
        this.property = property;
    }
    
    @Override
    public EnumProperty<?> getProperty() {
        return this.property;
    }
    
    @Override
    public void drawComponent(final ScaledResolution scaledResolution, final int mouseX, final int mouseY) {
        super.drawComponent(scaledResolution, mouseX, mouseY);
        final int x = (int)(this.getX() - 0.7);
        final int y = this.getY();
        final int width = this.getWidth() + 2;
        final int height = this.getHeight() + 3;
        final String selectedText = this.property.getValue().name();
        final int dropDownBoxY = y + 10;
        final boolean needScissor = Fonts.sf19.getStringWidth(selectedText) > width - 4;
        final int textColor = 16777215;
        RenderUtils.drawCustomRounded((float)x, (float)y, (float)(x + width), (float)(y + height), 0.0f, 0.0f, 4.0f, 4.0f, this.isHovered(mouseX, mouseY) ? new Color(85, 85, 85).getRGB() : new Color(45, 45, 45).getRGB());
        Fonts.sf19.drawStringWithShadow(this.getName(), (float)(x + 2), (float)(y + 1), textColor);
        RenderUtils.drawOutlinedRoundedRect2(x + 2, dropDownBoxY, x + this.getWidth() - 2, dropDownBoxY + 10.5, 4.0, 2.0f, HUD.hudColor.getValue());
        if (needScissor) {
            RenderUtils.startScissorBox(x + 2, dropDownBoxY + 2, width - 5, 10);
        }
        Fonts.sf19.drawStringWithShadow(selectedText, x + 3.5f, dropDownBoxY + 1.5f, textColor);
        if (needScissor) {
            RenderUtils.endScissorBox();
        }
        if (this.isExpanded()) {
            Gui.drawRect(x + 1, y + height, x + width - 1, y + this.getHeightWithExpand() + 2, new Color(45, 45, 45).getRGB());
            this.handleRender(x, y + this.getHeight() + 2, width, textColor);
        }
    }
    
    @Override
    public void onMouseClick(final int mouseX, final int mouseY, final int button) {
        super.onMouseClick(mouseX, mouseY, button);
        if (this.isExpanded()) {
            this.handleClick(mouseX, mouseY, this.getX(), this.getY() + this.getHeight() + 2, this.getWidth());
        }
    }
    
    private <T extends Enum<T>> void handleRender(final int x, int y, final int width, final int textColor) {
        final EnumProperty<T> property = (EnumProperty<T>)this.property;
        for (final T e : property.getValues()) {
            if (property.isSelected(e)) {
                Gui.drawRect(x + 1, y - 2, x + width - 1, y + 15 - 5, HUD.hudColor.getValue());
            }
            Fonts.sf19.drawStringWithShadow(e.name(), (float)(x + 1 + 2), (float)y, textColor);
            y += 12;
        }
    }
    
    private <T extends Enum<T>> void handleClick(final int mouseX, final int mouseY, final int x, int y, final int width) {
        final EnumProperty<T> property = (EnumProperty<T>)this.property;
        for (final T e : property.getValues()) {
            if (mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + 15 - 3) {
                property.setValue(e);
            }
            y += 12;
        }
    }
    
    @Override
    public int getHeightWithExpand() {
        return this.getHeight() + this.property.getValues().length * 12;
    }
    
    @Override
    public void onPress(final int mouseX, final int mouseY, final int button) {
    }
    
    @Override
    public boolean canExpand() {
        return this.property.getValues().length > 1;
    }
}
