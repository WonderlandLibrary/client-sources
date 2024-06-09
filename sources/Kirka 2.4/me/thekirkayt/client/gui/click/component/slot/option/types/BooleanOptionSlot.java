/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.gui.click.component.slot.option.types;

import me.thekirkayt.client.gui.click.component.slot.option.OptionSlotComponent;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.render.Gui;
import me.thekirkayt.client.option.types.BooleanOption;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.RenderUtils;
import me.thekirkayt.utils.minecraft.FontRenderer;

public class BooleanOptionSlot
extends OptionSlotComponent<BooleanOption> {
    public BooleanOptionSlot(BooleanOption parent, double x, double y, double width, double height) {
        super(parent, x, y, width, height);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        boolean useDarkTheme = ((Gui)new Gui().getInstance()).isDarkTheme();
        RenderUtils.rectangle(this.getX() - 3.0, this.getY(), this.getX() + this.getWidth() + 3.0, this.getY() + this.getHeight(), this.hovering(mouseX, mouseY) ? -1 : 0);
        ClientUtils.clientFont().drawCenteredString(((BooleanOption)this.getParent()).getDisplayName(), this.getX() + this.getWidth() / 2.0, this.getY() + 0.5 + this.getHeight() / 2.0 - (double)(ClientUtils.clientFont().FONT_HEIGHT / 2), (Boolean)((BooleanOption)this.getParent()).getValue() != false ? -11184641 : -1);
    }

    @Override
    public void click(int mouseX, int mouseY, int button) {
        if (button == 0) {
            ((BooleanOption)this.getParent()).setValue((Boolean)((BooleanOption)this.getParent()).getValue() == false);
        }
    }

    @Override
    public void drag(int mouseX, int mouseY, int button) {
    }

    @Override
    public void release(int mouseX, int mouseY, int button) {
    }

    @Override
    public void keyPress(int keyInt, char keyChar) {
    }
}

