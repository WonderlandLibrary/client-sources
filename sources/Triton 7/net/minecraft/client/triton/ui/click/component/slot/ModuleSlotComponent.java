// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.ui.click.component.slot;

import java.util.Iterator;

import net.minecraft.client.triton.impl.modules.render.Gui;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.ui.click.ClickGui;
import net.minecraft.client.triton.ui.click.component.window.ModuleWindow;
import net.minecraft.client.triton.ui.click.component.window.OptionWindow;
import net.minecraft.client.triton.ui.click.component.window.Window;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.RenderUtils;

public class ModuleSlotComponent extends SlotComponent<Module>
{
    private ModuleWindow parentWindow;
    
    public ModuleSlotComponent(final Module parent, final double x, final double y, final double width, final double height, final ModuleWindow parentWindow) {
        super(parent, x, y, width, height);
        this.parentWindow = parentWindow;
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        RenderUtils.rectangle(this.getX() - 3, this.getY(), this.getX() + this.getWidth() + 3, this.getY() + this.getHeight(), this.hovering(mouseX, mouseY) ? 0x3effffff : 0x00000000);
        ClientUtils.clientFont().drawCenteredString(this.getParent().getDisplayName(), this.getX() + this.getWidth() / 2.0, this.getY() + 0.5 + this.getHeight() / 2.0 - ClientUtils.clientFont().FONT_HEIGHT / 2, this.getParent().isEnabled() ? 0xceffffff : 0xFFb3b3b3);
        if (this.getOptionWindow() != null) {
            this.getOptionWindow().draw(mouseX, mouseY);
        }
    }
    
    @Override
    public void click(final int mouseX, final int mouseY, final int button) {
        if (this.getOptionWindow() != null) {
            this.getOptionWindow().click(mouseX, mouseY, button);
        }
        if (this.hovering(mouseX, mouseY)) {
            if (button == 0) {
                this.getParent().toggle();
            }
            else if (button == 1) {
                if (this.getOptionWindow() == null) {
                    for (final Window window : ClickGui.getInstance().getWindows()) {
                        for (Object slot1 : window.getSlotList()) {
                        	SlotComponent slot = (SlotComponent) slot1;
                            slot.setOptionWindow(null);
                        }
                    }
                    this.setOptionWindow(new OptionWindow(this.getParent(), this.getX() + this.getWidth() + 4.0, this.parentWindow.getY(), this.getWidth(), this.parentWindow));
                }
                else {
                    this.setOptionWindow(null);
                }
            }
        }
    }
    
    public void drag(final double xDifference, final double yDifference, final double[] startOffset) {
        this.setX(this.getX() - xDifference);
        this.setY(this.getY() - yDifference);
        if (this.getOptionWindow() != null) {
            this.getOptionWindow().drag(xDifference, yDifference, startOffset);
        }
    }
    
    @Override
    public void drag(final int mouseX, final int mouseY, final int button) {
        if (this.getOptionWindow() != null) {
            this.getOptionWindow().drag(mouseX, mouseY, button);
        }
    }
    
    @Override
    public void release(final int mouseX, final int mouseY, final int button) {
    }
    
    @Override
    public void keyPress(final int keyInt, final char keyChar) {
        if (this.getOptionWindow() != null) {
            this.getOptionWindow().keyPress(keyInt, keyChar);
        }
    }
}
