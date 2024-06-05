/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package digital.rbq.clickgui.panel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import digital.rbq.clickgui.ClickGuiScreen;
import digital.rbq.clickgui.panel.AnimationState;
import digital.rbq.clickgui.panel.component.Component;
import digital.rbq.clickgui.panel.component.impl.ModuleComponent;
import digital.rbq.core.Autumn;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.utils.render.AnimationUtils;
import digital.rbq.utils.render.RenderUtils;

public final class Panel {
    public static final int HEADER_SIZE = 20;
    public static final int HEADER_OFFSET = 2;
    private final FontRenderer fr;
    private final ModuleCategory category;
    private final List<Component> components;
    private final int width;
    public double scissorBoxHeight;
    private int x;
    private int lastX;
    private int y;
    private int lastY;
    private int height;
    public AnimationState state;
    private boolean dragging;

    public Panel(ModuleCategory category, int x, int y) {
        this.fr = Minecraft.getMinecraft().fontRenderer;
        this.components = new ArrayList<Component>();
        this.state = AnimationState.STATIC;
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = 100;
        int componentY = 20;
        int componentHeight = 15;
        List<Module> modulesForCategory = Autumn.MANAGER_REGISTRY.moduleManager.getModulesForCategory(category);
        int modulesForCategorySize = modulesForCategory.size();
        for (int i = 0; i < modulesForCategorySize; ++i) {
            Module module = modulesForCategory.get(i);
            ModuleComponent component = new ModuleComponent(module, this, 0, componentY, this.width, 15);
            this.components.add(component);
            componentY += 15;
        }
        this.height = componentY - 20;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private void updateComponentHeight() {
        int componentY = 20;
        List<Component> componentList = this.components;
        int componentListSize = componentList.size();
        for (int i = 0; i < componentListSize; ++i) {
            Component component = componentList.get(i);
            component.setY(componentY);
            componentY = (int)((double)componentY + ((double)component.getHeight() + component.getOffset()));
        }
        this.height = componentY - 20;
    }

    public final void onDraw(int mouseX, int mouseY) {
        int x = this.x;
        int y = this.y;
        int width = this.width;
        this.updateComponentHeight();
        this.handleScissorBox();
        this.handleDragging(mouseX, mouseY);
        double scissorBoxHeight = this.scissorBoxHeight;
        int backgroundColor = new Color(17, 17, 17).getRGB();
        Gui.drawRect(x - 2, y, x + width + 2, y + 20, backgroundColor);
        this.fr.drawStringWithShadow(this.category.toString(), x + 3, (float)y + 10.0f - 3.0f, ClickGuiScreen.getColor().getRGB());
        GL11.glPushMatrix();
        GL11.glEnable((int)3089);
        RenderUtils.prepareScissorBox(x - 2, y + 20 - 2, x + width + 2, (float)((double)(y + 20) + scissorBoxHeight));
        Gui.drawRect(x - 2, y, x + width + 2, (double)(y + 20) + scissorBoxHeight, backgroundColor);
        List<Component> components = this.components;
        int componentsSize = components.size();
        for (int i = 0; i < componentsSize; ++i) {
            components.get(i).onDraw(mouseX, mouseY);
            if (i == componentsSize - 1) continue;
            RenderUtils.prepareScissorBox(x - 2, y + 20, x + width + 2, (float)((double)(y + 20) + scissorBoxHeight));
        }
        GL11.glDisable((int)3089);
        GL11.glPopMatrix();
    }

    public final void onMouseClick(int mouseX, int mouseY, int mouseButton) {
        int x = this.x;
        int y = this.y;
        int width = this.width;
        double scissorBoxHeight = this.scissorBoxHeight;
        if (mouseX > x - 2 && mouseX < x + width + 2 && mouseY > y && mouseY < y + 20) {
            if (mouseButton == 1) {
                if (scissorBoxHeight > 0.0 && (this.state == AnimationState.EXPANDING || this.state == AnimationState.STATIC)) {
                    this.state = AnimationState.RETRACTING;
                } else if (scissorBoxHeight < (double)(this.height + 2) && (this.state == AnimationState.EXPANDING || this.state == AnimationState.STATIC)) {
                    this.state = AnimationState.EXPANDING;
                }
            } else if (mouseButton == 0 && !this.dragging) {
                this.lastX = x - mouseX;
                this.lastY = y - mouseY;
                this.dragging = true;
            }
        }
        List<Component> components = this.components;
        int componentsSize = components.size();
        for (int i = 0; i < componentsSize; ++i) {
            Component component = components.get(i);
            int componentY = component.getY();
            if (!((double)componentY < scissorBoxHeight + 20.0)) continue;
            component.onMouseClick(mouseX, mouseY, mouseButton);
        }
    }

    public final void onMouseRelease(int mouseX, int mouseY, int mouseButton) {
        if (this.dragging) {
            this.dragging = false;
        }
        if (this.scissorBoxHeight > 0.0) {
            List<Component> components = this.components;
            int componentsSize = components.size();
            for (int i = 0; i < componentsSize; ++i) {
                components.get(i).onMouseRelease(mouseX, mouseY, mouseButton);
            }
        }
    }

    public final void onKeyPress(char typedChar, int keyCode) {
        if (this.scissorBoxHeight > 0.0) {
            List<Component> components = this.components;
            int componentsSize = components.size();
            for (int i = 0; i < componentsSize; ++i) {
                components.get(i).onKeyPress(typedChar, keyCode);
            }
        }
    }

    private void handleDragging(int mouseX, int mouseY) {
        if (this.dragging) {
            this.x = mouseX + this.lastX;
            this.y = mouseY + this.lastY;
        }
    }

    private void handleScissorBox() {
        int height = this.height;
        switch (this.state) {
            case EXPANDING: {
                if (this.scissorBoxHeight < (double)(height + 2)) {
                    this.scissorBoxHeight = AnimationUtils.animate(height + 2, this.scissorBoxHeight, 0.1);
                    break;
                }
                if (!(this.scissorBoxHeight >= (double)(height + 2))) break;
                this.state = AnimationState.STATIC;
                break;
            }
            case RETRACTING: {
                if (this.scissorBoxHeight > 0.0) {
                    this.scissorBoxHeight = AnimationUtils.animate(0.0, this.scissorBoxHeight, 0.1);
                    break;
                }
                if (!(this.scissorBoxHeight <= 0.0)) break;
                this.state = AnimationState.STATIC;
                break;
            }
            case STATIC: {
                if (this.scissorBoxHeight > 0.0 && this.scissorBoxHeight != (double)(height + 2)) {
                    this.scissorBoxHeight = AnimationUtils.animate(height + 2, this.scissorBoxHeight, 0.1);
                }
                this.scissorBoxHeight = this.clamp(this.scissorBoxHeight, height + 2);
            }
        }
    }

    private double clamp(double a, double max) {
        if (a < 0.0) {
            return 0.0;
        }
        if (a > max) {
            return max;
        }
        return a;
    }
}

