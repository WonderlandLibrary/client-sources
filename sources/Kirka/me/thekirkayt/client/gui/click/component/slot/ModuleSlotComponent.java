/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.gui.click.component.slot;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import me.thekirkayt.client.gui.click.ClickGui;
import me.thekirkayt.client.gui.click.component.slot.SlotComponent;
import me.thekirkayt.client.gui.click.component.window.ModuleWindow;
import me.thekirkayt.client.gui.click.component.window.OptionWindow;
import me.thekirkayt.client.gui.click.component.window.Window;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.RenderUtils;
import me.thekirkayt.utils.minecraft.FontRenderer;

public class ModuleSlotComponent
extends SlotComponent<Module> {
    private ModuleWindow parentWindow;

    public ModuleSlotComponent(Module parent, double x, double y, double width, double height, ModuleWindow parentWindow) {
        super(parent, x, y, width, height);
        this.parentWindow = parentWindow;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        RenderUtils.rectangle(this.getX() - 3.0, this.getY(), this.getX() + this.getWidth() + 3.0, this.getY() + this.getHeight(), this.hovering(mouseX, mouseY) ? -1 : 0);
        ClientUtils.clientFont().drawCenteredString(((Module)this.getParent()).getDisplayName(), this.getX() + this.getWidth() / 2.0, this.getY() + 0.5 + this.getHeight() / 2.0 - (double)(ClientUtils.clientFont().FONT_HEIGHT / 2), ((Module)this.getParent()).isEnabled() ? -11184641 : -1);
        if (this.getOptionWindow() != null) {
            this.getOptionWindow().draw(mouseX, mouseY);
        }
    }

    @Override
    public void click(int mouseX, int mouseY, int button) {
        if (this.getOptionWindow() != null) {
            this.getOptionWindow().click(mouseX, mouseY, button);
        }
        if (this.hovering(mouseX, mouseY)) {
            if (button == 0) {
                ((Module)this.getParent()).toggle();
            } else if (button == 1) {
                if (this.getOptionWindow() == null) {
                    for (Window window : ClickGui.getInstance().getWindows()) {
                        Iterator<SlotComponent> iterator = window.getSlotList().iterator();
                        while (iterator.hasNext()) {
                            SlotComponent slot1;
                            SlotComponent slot = slot1 = iterator.next();
                            slot.setOptionWindow(null);
                        }
                    }
                    this.setOptionWindow(new OptionWindow((Module)this.getParent(), this.getX() + this.getWidth() + 4.0, this.parentWindow.getY(), this.getWidth(), this.parentWindow));
                } else {
                    this.setOptionWindow(null);
                }
            }
        }
    }

    public void drag(double xDifference, double yDifference, double[] startOffset) {
        this.setX(this.getX() - xDifference);
        this.setY(this.getY() - yDifference);
        if (this.getOptionWindow() != null) {
            this.getOptionWindow().drag(xDifference, yDifference, startOffset);
        }
    }

    @Override
    public void drag(int mouseX, int mouseY, int button) {
        if (this.getOptionWindow() != null) {
            this.getOptionWindow().drag(mouseX, mouseY, button);
        }
    }

    @Override
    public void release(int mouseX, int mouseY, int button) {
    }

    @Override
    public void keyPress(int keyInt, char keyChar) {
        if (this.getOptionWindow() != null) {
            this.getOptionWindow().keyPress(keyInt, keyChar);
        }
    }
}

