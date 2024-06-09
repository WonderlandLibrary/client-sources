/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.gui.click.component.slot.option.types;

import me.thekirkayt.client.gui.click.component.slot.option.OptionSlotComponent;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.render.Gui;
import me.thekirkayt.client.option.types.NumberOption;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.RenderUtils;
import me.thekirkayt.utils.minecraft.FontRenderer;

public class NumberOptionSlot
extends OptionSlotComponent<NumberOption> {
    private static final double VALUE_WINDOW_PADDING = 6.0;
    private static final double PADDING = 4.0;
    private boolean drag;

    public NumberOptionSlot(NumberOption parent, double x, double y, double width, double height) {
        super(parent, x, y, width, height);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        double sliderPos = (this.getWidth() - 2.0) * (((double)((Number)((NumberOption)this.getParent()).getValue()).floatValue() - ((NumberOption)this.getParent()).getMin()) / (((NumberOption)this.getParent()).getMax() - ((NumberOption)this.getParent()).getMin()));
        boolean useDarkTheme = ((Gui)new Gui().getInstance()).isDarkTheme();
        RenderUtils.rectangle(this.getX(), this.getY(), this.getX() + sliderPos + 1.5, this.getY() + 2.0, -11184641);
        ClientUtils.clientFont().drawCenteredString(((NumberOption)this.getParent()).getDisplayName(), this.getX() + this.getWidth() / 2.0, this.getY() + 0.5 + this.getHeight() / 2.0 - (double)(ClientUtils.clientFont().FONT_HEIGHT / 2), -1);
        double width = (double)ClientUtils.clientFont().getStringWidth("" + ((Number)((NumberOption)this.getParent()).getValue()).floatValue()) + 16.0;
        double x = this.getX() + this.getWidth() + 6.0;
        double x2 = this.getX() + this.getWidth() + 6.0 + width;
        RenderUtils.rectangle(x, this.getY(), x2, this.getY() + this.getHeight(), -11184641);
        ClientUtils.clientFont().drawCenteredString("" + ((Number)((NumberOption)this.getParent()).getValue()).floatValue(), x + width / 2.0, this.getY() + this.getHeight() / 2.0 - 3.5, -1);
    }

    @Override
    public void click(int mouseX, int mouseY, int button) {
        if (this.hovering(mouseX, mouseY)) {
            double min = ((NumberOption)this.getParent()).getMin();
            double max = ((NumberOption)this.getParent()).getMax();
            double inc = ((NumberOption)this.getParent()).getIncrement();
            double valAbs = (double)mouseX - (this.getX() + 1.0);
            double perc = valAbs / (this.getWidth() - 2.0);
            perc = Math.min(Math.max(0.0, perc), 1.0);
            double valRel = (max - min) * perc;
            double val = min + valRel;
            val = (double)Math.round(val * (1.0 / inc)) / (1.0 / inc);
            ((NumberOption)this.getParent()).setValue(val);
            this.drag = true;
        }
    }

    @Override
    public void drag(int mouseX, int mouseY, int button) {
        if (this.drag && this.hovering(mouseX, mouseY)) {
            double min = ((NumberOption)this.getParent()).getMin();
            double max = ((NumberOption)this.getParent()).getMax();
            double inc = ((NumberOption)this.getParent()).getIncrement();
            double valAbs = (double)mouseX - (this.getX() + 1.0);
            double perc = valAbs / (this.getWidth() - 2.0);
            perc = Math.min(Math.max(0.0, perc), 1.0);
            double valRel = (max - min) * perc;
            double val = min + valRel;
            val = (double)Math.round(val * (1.0 / inc)) / (1.0 / inc);
            ((NumberOption)this.getParent()).setValue(val);
        }
    }

    @Override
    public void release(int mouseX, int mouseY, int button) {
        this.drag = false;
    }

    @Override
    public void keyPress(int keyInt, char keyChar) {
    }
}

