package dev.stephen.nexus.gui.clickgui.old;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.gui.clickgui.old.components.ModuleButton;
import dev.stephen.nexus.gui.clickgui.old.components.settings.RenderableSetting;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.utils.render.ThemeUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("all")
public final class Window {
    public List<ModuleButton> moduleButtons = new ArrayList<>();
    protected MinecraftClient mc = Client.mc;
    private int x, y;
    private final int width, height;
    private final ModuleCategory category;
    public boolean dragging, extended;
    int dragX, dragY;

    public Window(int x, int y, int width, int height, ModuleCategory category) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.dragging = false;
        this.extended = true;
        this.height = height;
        this.category = category;

        int offset = height;

        List<Module> a = Client.INSTANCE.getModuleManager().getModulesInCategory(category);
        a.sort(Comparator.comparingInt(module -> -mc.textRenderer.getWidth(module.getName())));
        for (Module module : a) {
            moduleButtons.add(new ModuleButton(this, module, offset));
            offset += height;
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(x, y, x + width, y + height, ThemeUtils.getMainColor().darker().getRGB());
        MatrixStack matrixStack = context.getMatrices();

        matrixStack.push();

        float scale = 2.0f;
        matrixStack.scale(scale, scale, 1.0f);
        int scaledX = (int) ((x + (width / 2)) / scale);
        int scaledY = (int) ((y + 6) / scale);
        int scaledXRight = (int) ((x + getWidth() - 20) / scale);

        context.drawCenteredTextWithShadow(mc.textRenderer, category.name, scaledX, scaledY, Color.WHITE.getRGB());

        context.drawText(mc.textRenderer, extended ? "+" : "-", scaledXRight, scaledY, Color.white.getRGB(), true);

        matrixStack.pop();
        if (!extended) return;

        for (ModuleButton moduleButton : moduleButtons) {
            moduleButton.render(context, mouseX, mouseY, delta);
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            switch (button) {
                case 0: {
                    dragging = true;
                    dragX = (int) (mouseX - x);
                    dragY = (int) (mouseY - y);
                    break;
                }
                case 1: {
                    extended = !extended;
                    break;
                }
            }
        }

        for (ModuleButton moduleButton : moduleButtons) {
            moduleButton.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        for (ModuleButton moduleButton : moduleButtons) {
            moduleButton.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    }


    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        for (ModuleButton moduleButton : moduleButtons) {
            moduleButton.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    public void keyReleased(int keyCode, int scanCode, int modifiers) {
        for (ModuleButton moduleButton : moduleButtons) {
            moduleButton.keyReleased(keyCode, scanCode, modifiers);
        }
    }

    public void updateButtons() {
        int offset = height;

        for (ModuleButton moduleButton : moduleButtons) {
            moduleButton.offset = offset;
            offset += height;

            if (moduleButton.extended) {
                for (RenderableSetting renderableSetting : moduleButton.settings) {
                    offset += height;
                }
            }
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && dragging) {
            dragging = false;
        }

        for (ModuleButton moduleButton : moduleButtons) {
            moduleButton.mouseReleased(mouseX, mouseY, button);
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return (mouseX > x && mouseX < x + width) && (mouseY > y && mouseY < y + height);
    }

    public void updatePosition(double mouseX, double mouseY) {
        if (dragging) {
            x = (int) (mouseX - dragX);
            y = (int) (mouseY - dragY);
        }
    }
}