// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.ui.click.component.slot.option.types;

import net.minecraft.client.triton.impl.modules.render.Gui;
import net.minecraft.client.triton.management.option.types.NumberOption;
import net.minecraft.client.triton.ui.click.component.slot.option.OptionSlotComponent;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.RenderUtils;

public class NumberOptionSlot extends OptionSlotComponent<NumberOption>
{
    private static final double VALUE_WINDOW_PADDING = 6.0;
    private static final double PADDING = 4.0;
    private boolean drag;
    
    public NumberOptionSlot(final NumberOption parent, final double x, final double y, final double width, final double height) {
        super(parent, x, y, width, height);
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        final double sliderPos = (this.getWidth() - 2.0) * ((this.getParent().getValue().floatValue() - this.getParent().getMin()) / (this.getParent().getMax() - this.getParent().getMin()));
        final boolean useDarkTheme = ((Gui)new Gui().getInstance()).isDarkTheme();
        RenderUtils.rectangle(this.getX(), this.getY(), this.getX() + sliderPos + 1.5, this.getY() + 2, 0xceffffff);
        ClientUtils.clientFont().drawCenteredString(this.getParent().getDisplayName(), this.getX() + this.getWidth() / 2.0, this.getY() + 0.5 + this.getHeight() / 2.0 - ClientUtils.clientFont().FONT_HEIGHT / 2, -1);
        final double width = ClientUtils.clientFont().getStringWidth(new StringBuilder().append(this.getParent().getValue().floatValue()).toString()) + 16.0;
        final double x = this.getX() + this.getWidth() + 6.0;
        final double x2 = this.getX() + this.getWidth() + 6.0 + width;
        RenderUtils.rectangle(x, this.getY(), x2, this.getY() + this.getHeight(), 0xa6000000);
        ClientUtils.clientFont().drawCenteredString(new StringBuilder().append(this.getParent().getValue().floatValue()).toString(), x + width / 2.0, this.getY() + this.getHeight() / 2.0 - 3.5, -1);
    }
    
    @Override
    public void click(final int mouseX, final int mouseY, final int button) {
        if (this.hovering(mouseX, mouseY)) {
            final double min = this.getParent().getMin();
            final double max = this.getParent().getMax();
            final double inc = this.getParent().getIncrement();
            final double valAbs = mouseX - (this.getX() + 1.0);
            double perc = valAbs / (this.getWidth() - 2.0);
            perc = Math.min(Math.max(0.0, perc), 1.0);
            final double valRel = (max - min) * perc;
            double val = min + valRel;
            val = Math.round(val * (1.0 / inc)) / (1.0 / inc);
            this.getParent().setValue(val);
            this.drag = true;
        }
    }
    
    @Override
    public void drag(final int mouseX, final int mouseY, final int button) {
        if (this.drag && this.hovering(mouseX, mouseY)) {
            final double min = this.getParent().getMin();
            final double max = this.getParent().getMax();
            final double inc = this.getParent().getIncrement();
            final double valAbs = mouseX - (this.getX() + 1.0);
            double perc = valAbs / (this.getWidth() - 2.0);
            perc = Math.min(Math.max(0.0, perc), 1.0);
            final double valRel = (max - min) * perc;
            double val = min + valRel;
            val = Math.round(val * (1.0 / inc)) / (1.0 / inc);
            this.getParent().setValue(val);
        }
    }
    
    @Override
    public void release(final int mouseX, final int mouseY, final int button) {
        this.drag = false;
    }
    
    @Override
    public void keyPress(final int keyInt, final char keyChar) {
    }
}
