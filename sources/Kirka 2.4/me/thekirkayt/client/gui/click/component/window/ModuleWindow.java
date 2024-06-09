/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.gui.click.component.window;

import java.util.List;
import me.thekirkayt.client.gui.click.ClickGui;
import me.thekirkayt.client.gui.click.component.slot.ModuleSlotComponent;
import me.thekirkayt.client.gui.click.component.slot.SlotComponent;
import me.thekirkayt.client.gui.click.component.window.Handle;
import me.thekirkayt.client.gui.click.component.window.Window;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.ModuleManager;
import me.thekirkayt.utils.RenderUtils;

public class ModuleWindow
extends Window<Module.Category> {
    private static final float BORDER_WIDTH = 1.5f;
    private static final double PADDING = 4.0;
    private static final double SLOT_COMPONENT_HEIGHT = 16.0;

    public ModuleWindow(Module.Category category, double x, double y, double width) {
        super(category, x, y, 0.0, 0.0, new Handle(category.name(), x + 1.5 - 1.5, y - 18.0 + 1.5 + 0.5, width + 16.0 - 3.0 + 3.0, 18.0));
        double height = 1.5;
        for (Module mod : ModuleManager.getModules()) {
            if (!mod.getCategory().equals((Object)category)) continue;
            ModuleSlotComponent component = new ModuleSlotComponent(mod, x + 1.5 + 1.5, y + height - 0.5, width + 16.0 - 3.0 - 3.0, 17.0, this);
            this.getSlotList().add(component);
            height += 18.0;
        }
        this.setWidth(width += 16.0);
        this.setHeight(height += 1.5);
        this.getHandle().setParent(this);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        this.getHandle().draw(mouseX, mouseY, this.isExtended());
        if (this.isExtended()) {
            int[] fillGradient = new int[]{-11184641, -11141121, RenderUtils.blend(-11184641, -11141121, 0.95f), RenderUtils.blend(-11141121, -16777216, 0.95f)};
            int[] outlineGradient = new int[]{RenderUtils.blend(-16777216, -16777216, 0.95f), RenderUtils.blend(-16777216, -16777216, 0.95f), -16777216, -16777216};
            RenderUtils.rectangleBorderedGradient(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), fillGradient, outlineGradient, 0.5);
            for (SlotComponent slotComponent : this.getSlotList()) {
                slotComponent.draw(mouseX, mouseY);
            }
        }
    }

    @Override
    public void click(int mouseX, int mouseY, int button) {
        Window topWindow = ClickGui.getInstance().getTopWindow(mouseX, mouseY);
        if (topWindow != null && topWindow.equals(this) && this.getHandle().hovering(mouseX, mouseY)) {
            this.getHandle().click(mouseX, mouseY, button);
        }
        for (SlotComponent slot : this.getSlotList()) {
            slot.click(mouseX, mouseY, button);
        }
    }

    @Override
    public void drag(int mouseX, int mouseY, int button) {
        if (this.isDragging()) {
            double xDifference = this.getX() - ((double)mouseX - this.getStartOffset()[0]);
            double yDifference = this.getY() - ((double)mouseY - this.getStartOffset()[1]);
            xDifference = Math.round(xDifference / 10.0) * 10L;
            yDifference = Math.round(yDifference / 10.0) * 10L;
            for (SlotComponent slot : this.getSlotList()) {
                ((ModuleSlotComponent)slot).drag(xDifference, yDifference, this.getStartOffset());
            }
            this.setX(this.getX() - xDifference);
            this.setY(this.getY() - yDifference);
            this.getHandle().setX(this.getHandle().getX() - xDifference);
            this.getHandle().setY(this.getHandle().getY() - yDifference);
        }
        for (SlotComponent slot : this.getSlotList()) {
            ((ModuleSlotComponent)slot).drag(mouseX, mouseY, button);
        }
    }

    @Override
    public void release(int mouseX, int mouseY, int button) {
        this.getHandle().release(mouseX, mouseY, button);
    }

    @Override
    public void keyPress(int keyInt, char keyChar) {
        for (SlotComponent slot : this.getSlotList()) {
            slot.keyPress(keyInt, keyChar);
        }
    }
}

