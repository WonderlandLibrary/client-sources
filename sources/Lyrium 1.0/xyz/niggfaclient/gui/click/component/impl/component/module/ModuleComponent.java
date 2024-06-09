// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.gui.click.component.impl.component.module;

import xyz.niggfaclient.gui.click.ClickGui;
import xyz.niggfaclient.module.impl.render.HUD;
import xyz.niggfaclient.font.Fonts;
import xyz.niggfaclient.utils.render.RenderUtils;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import xyz.niggfaclient.gui.click.component.impl.component.property.PropertyComponent;
import net.minecraft.client.gui.ScaledResolution;
import java.util.Iterator;
import java.util.List;
import xyz.niggfaclient.gui.click.component.impl.component.property.impl.EnumBoxProperty;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.gui.click.component.impl.component.property.impl.SliderPropertyComponent;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.gui.click.component.impl.component.property.impl.ColorPropertyComponent;
import xyz.niggfaclient.gui.click.component.impl.component.property.impl.BooleanPropertyComponent;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.utils.animation.impl.EaseInOutQuad;
import xyz.niggfaclient.utils.animation.Direction;
import xyz.niggfaclient.gui.click.component.Component;
import xyz.niggfaclient.module.Module;
import xyz.niggfaclient.utils.animation.Animation;
import xyz.niggfaclient.gui.click.component.impl.ExpandableComponent;

public final class ModuleComponent extends ExpandableComponent
{
    private final Animation arrowAnimation;
    private final Module module;
    private boolean binding;
    
    public ModuleComponent(final Component parent, final Module module, final int x, final int y, final int width, final int height) {
        super(parent, module.getName(), x, y, width, height);
        this.arrowAnimation = new EaseInOutQuad(250, 1.0, Direction.BACKWARDS);
        this.module = module;
        final List<Property<?>> properties = module.getElements();
        final int propertyX = 1;
        int propertyY = height;
        for (final Property<?> property : properties) {
            Component component = null;
            if (property.getType() == Boolean.class) {
                component = new BooleanPropertyComponent(this, (Property<Boolean>)property, propertyX, propertyY, width - 2, 15);
            }
            else if (property.getType() == Integer.class) {
                component = new ColorPropertyComponent(this, (Property<Integer>)property, propertyX, propertyY, width - 2, 15);
            }
            else if (property instanceof DoubleProperty) {
                component = new SliderPropertyComponent(this, (DoubleProperty)property, propertyX, propertyY, width - 2, 15);
            }
            else if (property instanceof EnumProperty) {
                component = new EnumBoxProperty(this, (EnumProperty)property, propertyX, propertyY, width - 2, 22);
            }
            if (component != null) {
                this.children.add(component);
                propertyY += component.getHeight();
            }
        }
    }
    
    @Override
    public void drawComponent(final ScaledResolution scaledResolution, final int mouseX, final int mouseY) {
        final int x = this.getX();
        final int y = this.getY();
        final int width = this.getWidth();
        final int height = this.getHeight();
        this.arrowAnimation.setDirection(this.isExpanded() ? Direction.FORWARDS : Direction.BACKWARDS);
        if (this.isExpanded()) {
            int childY = 15;
            for (final Component child : this.children) {
                int cHeight = child.getHeight();
                if (child instanceof PropertyComponent) {
                    final PropertyComponent propertyComponent = (PropertyComponent)child;
                    if (!propertyComponent.getProperty().isAvailable()) {
                        continue;
                    }
                }
                if (child instanceof ExpandableComponent) {
                    final ExpandableComponent expandableComponent = (ExpandableComponent)child;
                    if (expandableComponent.isExpanded()) {
                        cHeight = expandableComponent.getHeightWithExpand();
                    }
                }
                child.setY(childY);
                child.drawComponent(scaledResolution, mouseX, mouseY);
                childY += cHeight;
            }
            Gui.drawRect(x, y, x + width, y + height, this.isHovered(mouseX, mouseY) ? new Color(85, 85, 85).getRGB() : new Color(45, 45, 45).getRGB());
        }
        else {
            RenderUtils.drawCustomRounded((float)x, (float)y, (float)(x + width), y + height / 2.0f + 10.7f, 0.0f, 0.0f, 5.0f, 5.0f, this.isHovered(mouseX, mouseY) ? new Color(85, 85, 85).getRGB() : new Color(45, 45, 45).getRGB());
        }
        if (this.canExpand()) {
            RenderUtils.drawAndRotateArrow((float)(x + width - 10), y + height / 2.0f - 2.0f, 6.0f, this.arrowAnimation);
        }
        Fonts.sf19.drawStringWithShadow(this.binding ? "Press A Key..." : this.getName(), (float)(x + 2), y + height / 2.0f - 3.0f, this.module.isEnabled() ? ((int)HUD.hudColor.getValue()) : Color.LIGHT_GRAY.getRGB());
    }
    
    @Override
    public boolean canExpand() {
        return !this.children.isEmpty();
    }
    
    @Override
    public void onPress(final int mouseX, final int mouseY, final int button) {
        switch (button) {
            case 0: {
                this.module.toggle();
                break;
            }
            case 2: {
                this.binding = !this.binding;
                break;
            }
        }
    }
    
    @Override
    public void onKeyPress(final int keyCode) {
        if (this.binding) {
            ClickGui.escapeKeyInUse = true;
            this.module.setKey((keyCode == 1) ? 0 : keyCode);
            this.binding = false;
        }
    }
    
    @Override
    public int getHeightWithExpand() {
        int height = this.getHeight();
        if (this.isExpanded()) {
            for (final Component child : this.children) {
                int cHeight = child.getHeight();
                if (child instanceof PropertyComponent) {
                    final PropertyComponent propertyComponent = (PropertyComponent)child;
                    if (!propertyComponent.getProperty().isAvailable()) {
                        continue;
                    }
                }
                if (child instanceof ExpandableComponent) {
                    final ExpandableComponent expandableComponent = (ExpandableComponent)child;
                    if (expandableComponent.isExpanded()) {
                        cHeight = expandableComponent.getHeightWithExpand();
                    }
                }
                height += cHeight;
            }
        }
        return height;
    }
}
