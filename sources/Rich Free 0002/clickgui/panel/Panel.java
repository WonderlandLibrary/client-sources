/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package clickgui.panel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.opengl.GL11;

import clickgui.panel.component.Component;
import clickgui.panel.component.impl.ModuleComponent;
import me.rich.Main;
import me.rich.font.Fonts;
import me.rich.helpers.render.AnimationHelper;
import me.rich.helpers.render.RenderHelper;
import me.rich.module.Category;
import me.rich.module.Feature;

public final class Panel {
    public static final int HEADER_SIZE = 20;
    public static final int HEADER_OFFSET = 2;
    public final Category category;
    public final List components = new ArrayList();
    public final int width;
    public double scissorBoxHeight;
    public int x;
    public int lastX;
    public int y;
    public int lastY;
    public int height;
    public AnimationState state = AnimationState.STATIC;
    public boolean dragging;
    public int activeRectAnimate;
    public double scalling;
    public Panel(Category category, int x, int y) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = 100;
        int componentY = 20;
        List<Feature> modulesForCategory = Arrays.asList(Main.instance.moduleManager.getModulesInCategory(category));
        int modulesForCategorySize = modulesForCategory.size();
        for (int i = 0; i < modulesForCategorySize; ++i) {
            Feature module = modulesForCategory.get(i);
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
        List componentList = this.components;
        int componentListSize = componentList.size();
        for (int i = 0; i < componentListSize; ++i) {
            Component component = (Component)componentList.get(i);
            component.setY(componentY);
            componentY = (int)((double)componentY + (double)component.getHeight() + component.getOffset());
        }
        this.height = componentY - 20;
    }

    public final void onDraw(int mouseX, int mouseY) {
        int x = this.x;
        int y = this.y;
        int width = this.width;
        this.updateComponentHeight();
        this.handleScissorBox();
        double scissorBoxHeight = this.scissorBoxHeight;
        int backgroundColor = new Color(17, 17, 17).getRGB();
       this.activeRectAnimate = (int)AnimationHelper.animate(this.activeRectAnimate, this.dragging ? -1.879048192E9 : (double)backgroundColor, 0.1f);
       RenderHelper.drawNewRect(this.x - 5, this.y - 3, this.x + this.width + 5, (float)((double)(y + 22) + scissorBoxHeight), new Color(1, 1, 1).getRGB());
       RenderHelper.drawNewRect(this.x - 4.5, this.y - 2.5, this.x + this.width + 4.5, (float)((double)(y + 22) + scissorBoxHeight) - 0.5, new Color(35, 33, 35).getRGB());
       	RenderHelper.drawNewRect(this.x - 2.5, this.y - 0.5, this.x + this.width + 2.5, (float)((double)(y + 20) + scissorBoxHeight) - 0.5, new Color(140, 140, 140).getRGB());
        //RenderHelper.drawGradientSideways(x - 2, (float)((double)(y + 20) + scissorBoxHeight) - 1, x + width + 2 , (float)((double)(y + 20) + scissorBoxHeight), (Main.getClientColor().getRGB()), (Main.getClientColor().getRGB()));
        RenderHelper.drawNewRect(x - 2, y, x + width + 2, (float)((double)(y + 19) + scissorBoxHeight), new Color(21, 21 ,21).getRGB());
        RenderHelper.drawNewRect(x - 2, y, x + width + 2, y + 16, new Color(24, 24, 24, 255).getRGB());
        RenderHelper.drawNewRect(this.x + -2, this.y + 18, this.x + this.width + 2, this.y + 19, new Color(140, 140, 140).getRGB());
        Fonts.neverlose500_17.drawStringWithShadow(WordUtils.capitalizeFully(this.category.name()).toLowerCase(), x + 2, (float)y + 10f - 3.0f, -1);
        Fonts.stylesicons_20.drawStringWithShadow("A", 323, y + 7, -1);
        Fonts.stylesicons_20.drawStringWithShadow("B", 207, y + 7, -1);
        Fonts.stylesicons_20.drawStringWithShadow("C", 437, y + 7, -1);
        Fonts.stylesicons_20.drawStringWithShadow("D", 93, y + 7, -1);
        Fonts.stylesicons_20.drawStringWithShadow("F", 552, y + 7, -1);
        Fonts.elegant_20.drawStringWithShadow("j", 667, y + 7, -1);
        GL11.glPushMatrix();
        GL11.glEnable((int)3089);

        RenderHelper.prepareScissorBox(x - 2, y + 20 - 2, x + width + 2, (float)((double)(y + 20) + scissorBoxHeight));
        List components = this.components;
        int componentsSize = components.size();
        for (int i = 0; i < componentsSize; ++i) {
            ((Component)components.get(i)).onDraw(mouseX, mouseY);
            if (i == componentsSize - 1) continue;
            RenderHelper.prepareScissorBox(x - 2, y + 20, x + width + 2, (float)((double)(y + 20) + scissorBoxHeight));
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
        List components = this.components;
        int componentsSize = components.size();
        for (int i = 0; i < componentsSize; ++i) {
            Component component = (Component)components.get(i);
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
            List components = this.components;
            int componentsSize = components.size();
            for (int i = 0; i < componentsSize; ++i) {
                ((Component)components.get(i)).onMouseRelease(mouseX, mouseY, mouseButton);
            }
        }
    }

    public void setY(int y) {
        this.y = y;
    }

    public final void onKeyPress(char typedChar, int keyCode) {
        if (this.scissorBoxHeight > 0.0) {
            List components = this.components;
            int componentsSize = components.size();
            for (int i = 0; i < componentsSize; ++i) {
                ((Component)components.get(i)).onKeyPress(typedChar, keyCode);
            }
        }
    }

    private void handleScissorBox() {
        int height = this.height;
        switch (this.state) {
            case EXPANDING: {
                if (this.scissorBoxHeight < (double)(height + 2)) {
                    this.scissorBoxHeight = AnimationHelper.animate(height + 2, this.scissorBoxHeight, 0.09);
                    break;
                }
                if (!(this.scissorBoxHeight >= (double)(height + 2))) break;
                this.state = AnimationState.STATIC;
                break;
            }
            case RETRACTING: {
                if (this.scissorBoxHeight > 0.0) {
                    this.scissorBoxHeight = AnimationHelper.animate(0.0, this.scissorBoxHeight, 0.09);
                    break;
                }
                if (!(this.scissorBoxHeight <= 0.0)) break;
                this.state = AnimationState.STATIC;
                break;
            }
            case STATIC: {
                if (this.scissorBoxHeight > 0.0 && this.scissorBoxHeight != (double)(height + 2)) {
                    this.scissorBoxHeight = AnimationHelper.animate(height + 2, this.scissorBoxHeight, 0.09);
                }
                this.scissorBoxHeight = this.clamp(this.scissorBoxHeight, height + 2);
            }
        }
    }

    private double clamp(double a, double max) {
        if (a < 0.0) {
            return 0.0;
        }
        return Math.min(a, max);
    }
}

