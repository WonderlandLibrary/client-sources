// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.clickgui;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.clickgui.category.CategoryComponent;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.impl.ClickGui;
import dev.lvstrng.argon.utils.ColorUtil;
import dev.lvstrng.argon.utils.RenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class ClickGUI extends Screen {
    public List<CategoryComponent> components;
    public Color color;

    public ClickGUI() {
        super(Text.empty());
        this.components = new ArrayList<>();
        int x = 50;
        final Category[] values = Category.values();
        final int length = values.length;
        int i = 0;
        while (i < length) {
            this.components.add(new CategoryComponent(x, 50, 230, 30, values[i]));
            x += 250;
            ++i;
        }
    }

    @Override
    public void render(final DrawContext context, int mouseX, int mouseY, final float delta) {
        if (Argon.mc.currentScreen == this) {
            if (Argon.INSTANCE.screen != null) {
                Argon.INSTANCE.screen.render(context, 0, 0, delta);
            }
            if (this.color == null) this.color = new Color(0, 0, 0, 0);
            else this.color = new Color(0, 0, 0, this.color.getAlpha());
            if (this.color.getAlpha() != (ClickGui.backgroundSetting.getValue() ? 200 : 0))
                this.color = ColorUtil.method523(0.05f, ClickGui.backgroundSetting.getValue() ? 200 : 0, this.color);
            if (Argon.mc.currentScreen instanceof ClickGUI)
                context.fill(0, 0, Argon.mc.getWindow().getWidth(), Argon.mc.getWindow().getHeight(), this.color.getRGB());
            RenderUtil.startDrawing();
            mouseX *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
            mouseY *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
            super.render(context, mouseX, mouseY, delta);
            for (final CategoryComponent class120 : this.components) {
                class120.render(context, mouseX, mouseY, delta);
                class120.postRender(mouseX, mouseY, delta);
            }
            RenderUtil.stopDrawing();
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for (CategoryComponent component : this.components) {
            component.method436(keyCode, scanCode, modifiers);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        mouseX *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
        mouseY *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
        for (CategoryComponent component : this.components) {
            component.method438(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        mouseX *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
        mouseY *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
        for (CategoryComponent component : this.components) {
            component.method439(mouseX, mouseY, button, deltaX, deltaY);
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        mouseY *= MinecraftClient.getInstance().getWindow().getScaleFactor();
        for (CategoryComponent component : this.components)
            component.method442(mouseX, mouseY, horizontalAmount, verticalAmount);
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    public void destroy() {
        Argon.INSTANCE.getModuleManager().getModuleByClass(ClickGui.class).setEnabled(false);
        this.removeComponents();
    }

    public void removeComponents() {
        Argon.mc.setScreenAndRender(Argon.INSTANCE.screen);
        this.color = null;
        for (CategoryComponent categoryComponent : this.components)
            categoryComponent.delete();
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        mouseX *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
        mouseY *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
        for (CategoryComponent component : this.components) {
            component.method441(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }
}