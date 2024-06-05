package io.github.nevalackin.radium.gui.click.component.impl.component.module;

import io.github.nevalackin.radium.gui.click.ClickGui;
import io.github.nevalackin.radium.gui.click.component.Component;
import io.github.nevalackin.radium.gui.click.component.impl.ExpandableComponent;
import io.github.nevalackin.radium.gui.click.component.impl.component.property.PropertyComponent;
import io.github.nevalackin.radium.gui.click.component.impl.component.property.impl.BooleanPropertyComponent;
import io.github.nevalackin.radium.gui.click.component.impl.component.property.impl.ColorPropertyComponent;
import io.github.nevalackin.radium.gui.click.component.impl.component.property.impl.EnumBoxProperty;
import io.github.nevalackin.radium.gui.click.component.impl.component.property.impl.SliderPropertyComponent;
import io.github.nevalackin.radium.gui.click.component.impl.panel.impl.CategoryPanel;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.property.impl.EnumProperty;
import io.github.nevalackin.radium.utils.Wrapper;
import io.github.nevalackin.radium.utils.render.RenderingUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.util.List;

public final class ModuleComponent extends ExpandableComponent {

    private final Module module;
    private boolean binding;

    @SuppressWarnings("unchecked")
    public ModuleComponent(Component parent,
                           Module module,
                           int x, int y,
                           int width,
                           int height) {
        super(parent, module.getLabel(), x, y, width, height);

        this.module = module;

        List<Property<?>> properties = module.getElements();

        int propertyX = CategoryPanel.X_ITEM_OFFSET;
        int propertyY = height;

        for (Property<?> property : properties) {
            Component component = null;
            if (property.getType() == Boolean.class) {
                component = new BooleanPropertyComponent(this, (Property<Boolean>) property, propertyX, propertyY, width - (CategoryPanel.X_ITEM_OFFSET * 2), CategoryPanel.ITEM_HEIGHT);
            } else if (property.getType() == Integer.class) {
                component = new ColorPropertyComponent(this, (Property<Integer>) property, propertyX, propertyY, width - (CategoryPanel.X_ITEM_OFFSET * 2), CategoryPanel.ITEM_HEIGHT);
            } else if (property instanceof DoubleProperty) {
                component = new SliderPropertyComponent(this, (DoubleProperty) property, propertyX, propertyY, width - (CategoryPanel.X_ITEM_OFFSET * 2), CategoryPanel.ITEM_HEIGHT);
            } else if (property instanceof EnumProperty) {
                component = new EnumBoxProperty(this, (EnumProperty<?>) property, propertyX, propertyY, width - (CategoryPanel.X_ITEM_OFFSET * 2), CategoryPanel.ITEM_HEIGHT + 7);
            }

            if (component != null) {
                this.children.add(component);
                propertyY += component.getHeight();
            }
        }
    }

    @Override
    public void drawComponent(ScaledResolution scaledResolution,
                              int mouseX,
                              int mouseY) {
        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();

        if (isExpanded()) {
            int childY = CategoryPanel.ITEM_HEIGHT;
            for (Component child : children) {
                int cHeight = child.getHeight();

                if (child instanceof PropertyComponent) {
                    PropertyComponent propertyComponent = (PropertyComponent) child;
                    if (!propertyComponent.getProperty().isAvailable())
                        continue;
                }
                if (child instanceof ExpandableComponent) {
                    ExpandableComponent expandableComponent = (ExpandableComponent) child;
                    if (expandableComponent.isExpanded())
                        cHeight = expandableComponent.getHeightWithExpand();
                }
                child.setY(childY);
                child.drawComponent(scaledResolution, mouseX, mouseY);
                childY += cHeight;
            }
        }

        int moduleColor = 0;

        switch (ClickGui.getInstance().getPalette()) {
            case DEFAULT:
                moduleColor = ClickGui.getInstance().getPalette().getPanelHeaderTextColor().getRGB();
                break;
            case PINK:
                moduleColor = module.isEnabled() ?
                        ClickGui.getInstance().getPalette().getEnabledModuleColor().getRGB() :
                        ClickGui.getInstance().getPalette().getDisabledModuleColor().getRGB();
                break;
        }

        Gui.drawRect(x, y, x + width, y + height, getBackgroundColor(isHovered(mouseX, mouseY)));
        Wrapper.getFontRenderer().drawStringWithShadow(binding ? "Press A Key..." : getName(), x + 2, y + height / 2.0F - 4,
                moduleColor);

        if (canExpand())
            RenderingUtils.drawAndRotateArrow(x + width - 10, y + height / 2.0F - 2, 6, isExpanded());
    }

    @Override
    public boolean canExpand() {
        return !children.isEmpty();
    }

    @Override
    public void onPress(int mouseX,
                        int mouseY,
                        int button) {
        switch (button) {
            case 0:
                module.toggle();
                break;
            case 2:
                binding = !binding;
                break;
        }
    }

    @Override
    public void onKeyPress(int keyCode) {
        if (binding) {
            ClickGui.escapeKeyInUse = true;
            module.setKey(keyCode == Keyboard.KEY_ESCAPE ? Keyboard.KEY_NONE : keyCode);
            binding = false;
        }
    }

    @Override
    public int getHeightWithExpand() {
        int height = getHeight();

        if (isExpanded()) {
            for (Component child : children) {
                int cHeight = child.getHeight();
                if (child instanceof PropertyComponent) {
                    PropertyComponent propertyComponent = (PropertyComponent) child;
                    if (!propertyComponent.getProperty().isAvailable())
                        continue;
                }
                if (child instanceof ExpandableComponent) {
                    ExpandableComponent expandableComponent = (ExpandableComponent) child;
                    if (expandableComponent.isExpanded())
                        cHeight = expandableComponent.getHeightWithExpand();
                }
                height += cHeight;
            }
        }

        return height;
    }
}
