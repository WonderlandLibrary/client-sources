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
import net.minecraft.client.gui.ScaledResolution;

import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.opengl.GL11;

import clickgui.panel.component.Component;
import clickgui.panel.component.impl.ModuleComponent;
import white.floor.Main;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.font.Fonts;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.render.AnimationHelper;

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
        List<Feature> modulesForCategory = Arrays.asList(Main.featureDirector.getModulesInCategory(category));
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

    public void onDraw(int mouseX, int mouseY) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        int yTotal = 0;
        for (int i = 0; i < Main.featureDirector.getFeatures().size(); ++i) {
            yTotal += Fonts.neverlose500_16.getHeight() + 3;
        }

        int x = this.x;
        int y = this.y;
        int width = this.width;
        this.updateComponentHeight();
        this.handleScissorBox();
        double scissorBoxHeight = this.scissorBoxHeight;
        int backgroundColor = new Color(17, 17, 17).getRGB();
       this.activeRectAnimate = (int) AnimationHelper.animate(this.activeRectAnimate, this.dragging ? -1.879048192E9 : backgroundColor, 0.1f);
        DrawHelper.drawNewRect(x - 3, y - 0.5, x + width + 2, y + 20, new Color(16, 16, 16, 255).getRGB());

        Fonts.neverlose500_17.drawStringWithShadow(WordUtils.capitalizeFully(this.category.name()), x + 2, (float)y + 8, -1);
        Fonts.stylesicons_20.drawStringWithShadow("A", 323, y + 8, -1);
        Fonts.stylesicons_20.drawStringWithShadow("B", 207, y + 8, -1);
        Fonts.stylesicons_20.drawStringWithShadow("C", 437, y + 8, -1);
        Fonts.stylesicons_20.drawStringWithShadow("D", 93, y + 8, -1);
        Fonts.stylesicons_20.drawStringWithShadow("F", 552, y + 8, -1);
        Fonts.elegant_20.drawStringWithShadow("j", 667, y + 8, -1);
        GL11.glPushMatrix();
        GL11.glEnable((int)3089);

        DrawHelper.prepareScissorBox(x - 2, y + 20 - 2, x + width + 150, (float)((double)(y + 20) + scissorBoxHeight));
        List components = this.components;
        int componentsSize = components.size();
        for (int i = 0; i < componentsSize; ++i) {
            ((Component)components.get(i)).onDraw(mouseX, mouseY);
            if (i == componentsSize - 1) continue;
            DrawHelper.prepareScissorBox(x - 2, y + 20, x + width + 150, (float)((double)(y + 20) + scissorBoxHeight));
        }
        GL11.glDisable((int)3089);
        GL11.glPopMatrix();

        DrawHelper.drawNewRect(x - 3, y + 20, x + width + 2, y + 20.5, Main.getClientColor(y + height / 1.5f - 4.5f, yTotal, 4).getRGB());

        Fonts.neverlose500_16.drawStringWithShadow("Build: \2477" + Main.version, 1, sr.getScaledHeight() - 7, -1);

        //DrawHelper.drawImage(new ResourceLocation("richclient/kuriyama.png"), (int) (sr.getScaledWidth() / 1.4f) - 12 + 20,  (int) (sr.getScaledHeight() / 2f) - 47, 250, 300, 70);
    }

    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
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

