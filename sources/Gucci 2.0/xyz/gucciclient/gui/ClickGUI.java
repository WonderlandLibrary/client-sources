package xyz.gucciclient.gui;

import net.minecraft.client.gui.*;
import xyz.gucciclient.modules.*;
import xyz.gucciclient.gui.component.*;
import java.util.*;

public class ClickGUI extends GuiScreen
{
    public ArrayList<Frame> frames;
    
    public ClickGUI() {
        this.frames = new ArrayList<Frame>();
        int frameX = 5;
        Module.Category[] values;
        for (int length = (values = Module.Category.values()).length, i = 0; i < length; ++i) {
            final Module.Category category = values[i];
            final Frame frame = new Frame(category);
            frame.setX(frameX);
            this.frames.add(frame);
            frameX += frame.getWidth() + 1;
        }
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        for (final Frame frame : this.frames) {
            frame.renderFrame(this.fontRendererObj);
            frame.updatePosition(mouseX, mouseY);
            for (final Component comp : frame.getComponents()) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }
    
    protected void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) {
        for (final Frame frame : this.frames) {
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                frame.setDrag(true);
                frame.dragX = mouseX - frame.getX();
                frame.dragY = mouseY - frame.getY();
            }
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                frame.setOpen(!frame.isOpen());
            }
            if (frame.isOpen() && !frame.getComponents().isEmpty()) {
                for (final Component component : frame.getComponents()) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }
    
    protected void func_73869_a(final char typedChar, final int keyCode) {
        for (final Frame frame : this.frames) {
            if (frame.isOpen() && keyCode != 1 && !frame.getComponents().isEmpty()) {
                for (final Component component : frame.getComponents()) {
                    component.keyTyped(typedChar, keyCode);
                }
            }
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    protected void func_146286_b(final int mouseX, final int mouseY, final int state) {
        for (final Frame frame : this.frames) {
            frame.setDrag(false);
        }
    }
    
    public boolean func_73868_f() {
        return false;
    }
}
