/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package clickgui.panel.component.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import clickgui.panel.AnimationState;
import clickgui.panel.Panel;
import clickgui.panel.component.Component;
import clickgui.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import white.floor.Main;
import white.floor.features.Feature;
import white.floor.font.Fonts;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.render.AnimationHelper;

public final class ModuleComponent
extends Component {
    public final List components = new ArrayList();
    private final ArrayList<Component> children = new ArrayList();
    private static Color BACKGROUND_COLOR = new Color(23, 23, 23);
    private final Feature module;
    private int opacity = 120;
    private int childrenHeight;
    private double scissorBoxHeight;
    private AnimationState state = AnimationState.STATIC;
    private boolean binding;
    private float activeRectAnimate = 0.0f;
    public float animation = 0.0f;
    int onlySettingsY = 0;

    public ModuleComponent(Feature module, Panel parent, int x, int y, int width, int height) {
        super(parent, x, y, width, height);
        this.module = module;
        int y2 = height;
        boolean i = false;
        if (Main.settingsManager.getSettingsByMod(module) != null) {
            for (Setting s : Main.settingsManager.getSettingsByMod(module)) {
                if (s.isCombo()) {
                    this.children.add(new ModeComponent(s, this.getPanel(), x, y + y2, width, height));
                    y2 += height + 20;
                }
                if (s.isSlider()) {
                    this.children.add(new SliderComponent(s, this.getPanel(), x, y, width, 16));
                    y2 += height + 20;
                    y2 += height + 20;
                }
                if (!s.isCheck()) continue;
                this.children.add(new BooleanComponent(s, this.getPanel(), x, y + y2, width, height));
                y2 += height + 20;
            }
        }
    }

    @Override
    public double getOffset() {
        return this.scissorBoxHeight;
    }

    private void drawChildren(int mouseX, int mouseY) {
        int childY = 15;
        ArrayList<Component> children = this.children;
        int componentListSize = children.size();
        for (int i = 0; i < componentListSize; ++i) {
            Component child = (Component)children.get(i);
            if (child.isHidden()) continue;
            child.setY(this.getY() + childY);
            child.onDraw(mouseX, mouseY);
            childY += 15;
        }
    }

    private int calculateChildrenHeight() {
        int height = 0;
        ArrayList<Component> children = this.children;
        int childrenSize = children.size();
        for (int i = 0; i < childrenSize; ++i) {
            Component component = (Component)children.get(i);
            if (component.isHidden()) continue;
            height = (int)((double)(height + component.getHeight()) + component.getOffset());
        }
        return height;
    }

    @Override
    public void onDraw(int mouseX, int mouseY) {
        boolean hover;
        Panel parent = this.getPanel();
        int x = parent.getX() + this.getX();
        int y = parent.getY() + this.getY();
        int height = this.getHeight();
        int width = this.getWidth();
        boolean hovered = this.isMouseOver(mouseX, mouseY);
        this.handleScissorBox();
        this.childrenHeight = this.calculateChildrenHeight();
        if (hovered) {
            if (this.opacity < 200) {
                this.opacity += 3;
            }
        } else if (this.opacity > 50) {
            this.opacity -= 3;
        }

        this.activeRectAnimate = AnimationHelper.animation(this.activeRectAnimate, (hover = hovered) ? 4.0f : 2.0f, 0.001f);
        int opacity = this.opacity;
        int color = this.module.isToggled() ? new Color(255, 255, 255).getRGB() : new Color(120,120,120).getRGB();
        DrawHelper.drawNewRect(x, y, x + width, y + height, hovered ? DrawHelper.setAlpha(new Color(21, 21, 21), opacity).getRGB() : new Color(23, 23, 23, 255).getRGB());

        //if(hovered)
       // DrawHelper.drawRectWithGlow(x - 2, y, x + width + 2, y + height, 6, 6, Main.getClientColor());

        Fonts.neverlose500_16.drawCenteredStringWithShadow(this.binding ? "Binding... Key:" + Keyboard.getKeyName(this.module.getKey()) : this.module.getName(), x + 51, y + height / 1.5f - 4.5f, color);
        if (this.scissorBoxHeight > 0.0) {
            if (parent.state != AnimationState.RETRACTING) {
                DrawHelper.prepareScissorBox(x, y, x + width, (float)((double)y + Math.min(this.scissorBoxHeight, parent.scissorBoxHeight) + (double)height));
            }
            this.drawChildren(mouseX, mouseY);
        }

        if(hovered) {
            Gui.drawRect(mouseX + 15, mouseY - 2, mouseX + 18 + Fonts.neverlose500_14.getStringWidth(this.module.getDesc()) + 2, mouseY + 6, new Color(32, 32, 32).getRGB());
            Gui.drawRect(mouseX + 15.5, mouseY - 2, mouseX + 16, mouseY + 6, -1);
            Gui.drawRect(mouseX + 18 + Fonts.neverlose500_14.getStringWidth(this.module.getDesc()) + 2, mouseY - 2, mouseX + 18.5 + Fonts.neverlose500_14.getStringWidth(this.module.getDesc()) + 2, mouseY + 6, -1);
            Fonts.neverlose500_14.drawStringWithShadow(this.module.getDesc(), mouseX + 18, mouseY, -1);
        }
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (this.scissorBoxHeight > 0.0) {
            ArrayList<Component> componentList = this.children;
            int componentListSize = componentList.size();
            for (int i = 0; i < componentListSize; ++i) {
                ((Component)componentList.get(i)).onMouseClick(mouseX, mouseY, mouseButton);
            }
        }
        if (this.isMouseOver(mouseX, mouseY) && mouseButton == 2) {
            boolean bl = this.binding = !this.binding;
        }
        if (this.isMouseOver(mouseX, mouseY)) {
            if (mouseButton == 0) {
                this.module.toggle();
            } else if (mouseButton == 1 && !this.children.isEmpty()) {
                if (this.scissorBoxHeight > 0.0 && (this.state == AnimationState.EXPANDING || this.state == AnimationState.STATIC)) {
                    this.state = AnimationState.RETRACTING;
                } else if (this.scissorBoxHeight < (double)this.childrenHeight && (this.state == AnimationState.EXPANDING ||  this.state == AnimationState.STATIC)) {
                    this.state = AnimationState.EXPANDING;
                }
            }
        }
    }

    @Override
    public void onMouseRelease(int mouseX, int mouseY, int mouseButton) {
        if (this.scissorBoxHeight > 0.0) {
            ArrayList<Component> componentList = this.children;
            int componentListSize = componentList.size();
            for (int i = 0; i < componentListSize; ++i) {
                ((Component)componentList.get(i)).onMouseRelease(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void onKeyPress(int typedChar, int keyCode) {
        if (this.binding) {
            this.module.setKey(keyCode);
            this.binding = false;
            if (keyCode == 211) {
                this.module.setKey(0);
            } else if (keyCode == 1) {
                this.setBinding(false);
            }
        }
        if (this.scissorBoxHeight > 0.0) {
            ArrayList<Component> componentList = this.children;
            int componentListSize = componentList.size();
            for (int i = 0; i < componentListSize; ++i) {
                ((Component)componentList.get(i)).onKeyPress(typedChar, keyCode);
            }
        }
    }

    public void setBinding(boolean binding) {
        this.binding = binding;
    }

    private void handleScissorBox() {
        int childrenHeight = this.childrenHeight;
        switch (this.state) {
            case EXPANDING: {
                if (this.scissorBoxHeight < (double)childrenHeight) {
                    this.scissorBoxHeight = AnimationHelper.animate(childrenHeight, this.scissorBoxHeight, 0.08);
                } else if (this.scissorBoxHeight >= (double)childrenHeight) {
                    this.state = AnimationState.STATIC;
                }
                this.scissorBoxHeight = this.clamp(this.scissorBoxHeight, childrenHeight);
                break;
            }
            case RETRACTING: {
                if (this.scissorBoxHeight > 0.0) {
                    this.scissorBoxHeight = AnimationHelper.animate(0.0, this.scissorBoxHeight, 0.08);
                } else if (this.scissorBoxHeight <= 0.0) {
                    this.state = AnimationState.STATIC;
                }
                this.scissorBoxHeight = this.clamp(this.scissorBoxHeight, childrenHeight);
                break;
            }
            case STATIC: {
                if (this.scissorBoxHeight > 0.0 && this.scissorBoxHeight != (double)childrenHeight) {
                    this.scissorBoxHeight = AnimationHelper.animate(childrenHeight, this.scissorBoxHeight, 0.08);
                }
                this.scissorBoxHeight = this.clamp(this.scissorBoxHeight, childrenHeight);
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

