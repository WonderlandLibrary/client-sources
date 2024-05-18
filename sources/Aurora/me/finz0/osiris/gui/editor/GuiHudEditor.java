package me.finz0.osiris.gui.editor;

import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.ShutDownHookerino;
import me.finz0.osiris.gui.editor.anchor.AnchorPoint;
import me.finz0.osiris.gui.editor.component.DraggableHudComponent;
import me.finz0.osiris.gui.editor.component.HudComponent;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.gui.HudEditorModule;
import me.finz0.osiris.util.OsirisTessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public final class GuiHudEditor extends GuiScreen {

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        final HudEditorModule mod = (HudEditorModule) ModuleManager.getModuleByName("HudEditor");

        if (mod != null) {
            if (keyCode == mod.getBind()) {
                if (mod.isOpen()) {
                    mod.setOpen(false);
                } else {
                    Minecraft.getMinecraft().displayGuiScreen(null);
                }
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawDefaultBackground();

        final ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());

        final float halfWidth = res.getScaledWidth() / 2.0f;
        final float halfHeight = res.getScaledHeight() / 2.0f;
        OsirisTessellator.drawLine(halfWidth, 0, halfWidth, res.getScaledHeight(), 1, 0x75909090);
        OsirisTessellator.drawLine(0, halfHeight, res.getScaledWidth(), halfHeight, 1, 0x75909090);

        for (AnchorPoint point : AuroraMod.getInstance().hudManager.getAnchorPoints()) {
            OsirisTessellator.drawRect(point.getX() - 1, point.getY() - 1, point.getX() + 1, point.getY() + 1, 0x75909090);
        }

        for (HudComponent component : AuroraMod.getInstance().hudManager.getComponentList()) {
            if (component.isVisible()) {
                component.render(mouseX, mouseY, partialTicks);

                if (component instanceof DraggableHudComponent) {
                    DraggableHudComponent draggable = (DraggableHudComponent) component;
                    if (draggable.isDragging()) {
                        OsirisTessellator.drawRect(draggable.getX(), draggable.getY(), draggable.getX() + draggable.getW(), draggable.getY() + draggable.getH(), 0x35FFFFFF);
                        for (HudComponent other : AuroraMod.getInstance().hudManager.getComponentList()) {
                            if (other != draggable && draggable.collidesWith(other) && other.isVisible() && draggable.isSnappable()) {
                                OsirisTessellator.drawRect(draggable.getX(), draggable.getY(), draggable.getX() + draggable.getW(), draggable.getY() + draggable.getH(), 0x3510FF10);
                                OsirisTessellator.drawRect(other.getX(), other.getY(), other.getX() + other.getW(), other.getY() + other.getH(), 0x35FF1010);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);

        for (HudComponent component : AuroraMod.getInstance().hudManager.getComponentList()) {
            if (component.isVisible()) {
                component.mouseClickMove(mouseX, mouseY, clickedMouseButton);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        try {
            super.mouseClicked(mouseX, mouseY, mouseButton);

            for (HudComponent component : AuroraMod.getInstance().hudManager.getComponentList()) {
                if (component.isVisible()) {
                    component.mouseClick(mouseX, mouseY, mouseButton);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        for (HudComponent component : AuroraMod.getInstance().hudManager.getComponentList()) {
            if (component.isVisible()) {
                component.mouseRelease(mouseX, mouseY, state);
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void onGuiClosed() {
        ShutDownHookerino.saveConfig();

        final HudEditorModule mod = (HudEditorModule) ModuleManager.getModuleByName("HudEditor");

        if (mod != null) {
            if (mod.blur.getValBoolean()) {
                if (OpenGlHelper.shadersSupported) {
                    mc.entityRenderer.stopUseShader();
                }
            }
        }

        for (HudComponent component : AuroraMod.getInstance().hudManager.getComponentList()) {
            if (component instanceof DraggableHudComponent) {
                if (component.isVisible()) {
                    final DraggableHudComponent draggable = (DraggableHudComponent) component;
                    if (draggable.isDragging())
                        draggable.setDragging(false);
                }
            }
        }
    }
}
