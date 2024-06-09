/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.gui.click.component.window;

import java.util.List;
import me.thekirkayt.client.gui.click.component.slot.SlotComponent;
import me.thekirkayt.client.gui.click.component.slot.option.OptionSlotComponent;
import me.thekirkayt.client.gui.click.component.slot.option.types.BooleanOptionSlot;
import me.thekirkayt.client.gui.click.component.slot.option.types.KeybindOptionSlot;
import me.thekirkayt.client.gui.click.component.slot.option.types.NumberOptionSlot;
import me.thekirkayt.client.gui.click.component.slot.option.types.VisibilityOptionSlot;
import me.thekirkayt.client.gui.click.component.window.Handle;
import me.thekirkayt.client.gui.click.component.window.ModuleWindow;
import me.thekirkayt.client.gui.click.component.window.Window;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.client.option.types.BooleanOption;
import me.thekirkayt.client.option.types.NumberOption;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.RenderUtils;

public class OptionWindow
extends Window<Module> {
    private static final float BORDER_WIDTH = 1.5f;
    private static final double PADDING = 4.0;
    private static final double SLOT_COMPONENT_HEIGHT = 16.0;
    private ModuleWindow parentWindow;

    public OptionWindow(Module module, double x, double y, double width, ModuleWindow parentWindow) {
        super(module, x, y, 0.0, 0.0, new Handle(module.getDisplayName(), x + 1.5 - 1.5, y - 18.0 + 1.5 + 0.5, width + 16.0 - 3.0 + 3.0, 18.0));
        this.parentWindow = parentWindow;
        double height = 1.5;
        for (Option option : module.getOptions()) {
            width = Math.max(width, (double)ClientUtils.clientFont().getStringWidth(option.getDisplayName()));
        }
        for (Option option : module.getOptions()) {
            OptionSlotComponent optionSlot = null;
            if (option instanceof BooleanOption) {
                optionSlot = new BooleanOptionSlot((BooleanOption)option, x + 1.5 + 1.5, y + height - 0.5, width + 16.0 - 3.0 - 3.0, 17.0);
            } else if (option instanceof NumberOption) {
                optionSlot = new NumberOptionSlot((NumberOption)option, x + 1.5 + 1.5, y + height - 0.5, width + 16.0 - 3.0 - 3.0, 17.0);
            }
            if (optionSlot == null) continue;
            this.getSlotList().add(optionSlot);
            height += 18.0;
        }
        this.getSlotList().add(new KeybindOptionSlot(module, x + 1.5 + 1.5, y + height - 0.5, width + 16.0 - 3.0 - 3.0, 17.0));
        this.getSlotList().add(new VisibilityOptionSlot(module, x + 1.5 + 1.5, y + (height += 18.0) - 0.5, width + 16.0 - 3.0 - 3.0, 17.0));
        height += 18.0;
        this.setWidth(width += 16.0);
        this.setHeight(height += 1.5);
        this.getHandle().setWidth(width);
        this.getHandle().setParent(this);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        this.getHandle().draw(mouseX, mouseY, this.isExtended());
        int[] fillGradient = new int[]{-11184641, -11141121, RenderUtils.blend(-11184641, -11141121, 0.95f), RenderUtils.blend(-11141121, -16777216, 0.95f)};
        int[] outlineGradient = new int[]{RenderUtils.blend(-16777216, -16777216, 0.95f), RenderUtils.blend(-16777216, -16777216, 0.95f), -16777216, -16777216};
        RenderUtils.rectangleBorderedGradient(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), fillGradient, outlineGradient, 0.5);
        for (SlotComponent slotComponent : this.getSlotList()) {
            slotComponent.draw(mouseX, mouseY);
        }
    }

    @Override
    public void click(int mouseX, int mouseY, int button) {
        for (SlotComponent slot : this.getSlotList()) {
            if (!slot.hovering(mouseX, mouseY)) continue;
            slot.click(mouseX, mouseY, button);
        }
    }

    public void drag(double xDifference, double yDifference, double[] startOffset) {
        for (SlotComponent slot : this.getSlotList()) {
            slot.setX(slot.getX() - xDifference);
            slot.setY(slot.getY() - yDifference);
            if (slot.getOptionWindow() == null) continue;
            slot.getOptionWindow().setX(slot.getOptionWindow().getX() - xDifference);
            slot.getOptionWindow().setY(slot.getOptionWindow().getY() - yDifference);
        }
        this.setX(this.getX() - xDifference);
        this.setY(this.getY() - yDifference);
        this.getHandle().setX(this.getHandle().getX() - xDifference);
        this.getHandle().setY(this.getHandle().getY() - yDifference);
    }

    @Override
    public void drag(int mouseX, int mouseY, int button) {
        for (SlotComponent slot : this.getSlotList()) {
            slot.drag(mouseX, mouseY, button);
        }
    }

    @Override
    public void release(int mouseX, int mouseY, int button) {
    }

    @Override
    public void keyPress(int keyInt, char keyChar) {
        for (SlotComponent slot : this.getSlotList()) {
            slot.keyPress(keyInt, keyChar);
        }
    }
}

