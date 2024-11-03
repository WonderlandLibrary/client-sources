package dev.stephen.nexus.gui.clickgui.old;

import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.utils.render.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

import static dev.stephen.nexus.Client.mc;

public final class ClickGui extends Screen {
    List<Window> windows = new ArrayList<>();

    public ClickGui() {
        super(Text.empty());

        int offsetX = 50;

        for(ModuleCategory category : ModuleCategory.values()) {
            windows.add(new Window(offsetX, 40, 240, 30, category));
            offsetX += 260;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderUtils.unscaledProjection();
        mouseX *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
        mouseY *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
        super.render(context, mouseX, mouseY, delta);

        for(Window window : windows) {
            window.render(context, mouseX, mouseY, delta);
            window.updatePosition(mouseX, mouseY);

            if(window.dragging) {
                if (window.getX() < 0) {
                    window.setX(0);
                }

                if (window.getY() < 0) {
                    window.setY(0);
                }

                if (window.getX() + window.getWidth() > mc.getWindow().getWidth()) {
                    window.setX(window.getX() - window.getWidth());
                }

                if (window.getY() + window.getHeight() > mc.getWindow().getHeight()) {
                    window.setY(window.getY() - window.getHeight());
                }
            }
        }

        RenderUtils.scaledProjection();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        mouseX *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
        mouseY *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();

        for(Window window : windows) {
            window.mouseClicked(mouseX, mouseY, button);
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        mouseX *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
        mouseY *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
        for(Window window : windows) {
            window.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        mouseX *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
        mouseY *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();

        for(Window window : windows) {
            window.mouseReleased(mouseX, mouseY, button);
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for(Window window : windows) {
            window.keyPressed(keyCode, scanCode, modifiers);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        for(Window window : windows) {
            window.keyReleased(keyCode, scanCode, modifiers);
        }
        return super.keyReleased(keyCode, scanCode, modifiers);
    }
}