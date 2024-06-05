package me.satisfactory.base.hero.clickgui;

import net.minecraft.client.gui.*;
import me.satisfactory.base.module.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import me.satisfactory.base.hero.clickgui.component.*;
import java.util.*;

public class ClickGui extends GuiScreen
{
    private static final ArrayList<Frame> frames;
    
    public ClickGui() {
        int frameX = 5;
        for (final Category category : Category.values()) {
            if (category != Category.HIDDEN) {
                final Frame frame = new Frame(category);
                frame.setX(frameX);
                ClickGui.frames.add(frame);
                frameX += frame.getWidth() + 1;
            }
        }
    }
    
    @Override
    public void onGuiClosed() {
        if (ClickGui.mc.entityRenderer.theShaderGroup != null) {
            ClickGui.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            ClickGui.mc.entityRenderer.theShaderGroup = null;
        }
    }
    
    @Override
    public void initGui() {
        if (OpenGlHelper.shadersSupported && ClickGui.mc.getRenderViewEntity() instanceof EntityPlayer) {
            if (ClickGui.mc.entityRenderer.theShaderGroup != null) {
                ClickGui.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            }
            ClickGui.mc.entityRenderer.func_175069_a(new ResourceLocation("shaders/post/blur.json"));
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        for (final Frame frame : ClickGui.frames) {
            frame.renderFrame(this.fontRendererObj);
            frame.updatePosition(mouseX, mouseY);
            for (final Component comp : frame.getComponents()) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        for (final Frame frame : ClickGui.frames) {
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
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) {
        for (final Frame frame : ClickGui.frames) {
            if (frame.isOpen() && keyCode != 1 && !frame.getComponents().isEmpty()) {
                for (final Component component : frame.getComponents()) {
                    component.keyTyped(typedChar, keyCode);
                }
            }
        }
        if (keyCode == 1) {
            ClickGui.mc.displayGuiScreen(null);
        }
    }
    
    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        for (final Frame frame : ClickGui.frames) {
            frame.setDrag(false);
        }
        for (final Frame frame : ClickGui.frames) {
            if (frame.isOpen() && !frame.getComponents().isEmpty()) {
                for (final Component component : frame.getComponents()) {
                    component.mouseReleased(mouseX, mouseY, state);
                }
            }
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
    
    static {
        frames = new ArrayList<Frame>();
    }
}
