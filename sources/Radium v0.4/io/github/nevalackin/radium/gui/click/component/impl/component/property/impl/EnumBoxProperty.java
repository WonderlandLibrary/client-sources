package io.github.nevalackin.radium.gui.click.component.impl.component.property.impl;

import io.github.nevalackin.radium.gui.click.ClickGui;
import io.github.nevalackin.radium.gui.click.component.Component;
import io.github.nevalackin.radium.gui.click.component.impl.ExpandableComponent;
import io.github.nevalackin.radium.gui.click.component.impl.component.property.PropertyComponent;
import io.github.nevalackin.radium.gui.click.component.impl.panel.impl.CategoryPanel;
import io.github.nevalackin.radium.property.impl.EnumProperty;
import io.github.nevalackin.radium.utils.StringUtils;
import io.github.nevalackin.radium.utils.Wrapper;
import io.github.nevalackin.radium.utils.render.OGLUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public final class EnumBoxProperty extends ExpandableComponent implements PropertyComponent {

    private final EnumProperty<?> property;

    public EnumBoxProperty(Component parent, EnumProperty<?> property, int x, int y, int width, int height) {
        super(parent, property.getLabel(), x, y, width, height);
        this.property = property;
    }

    @Override
    public EnumProperty<?> getProperty() {
        return property;
    }

    @Override
    public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        super.drawComponent(scaledResolution, mouseX, mouseY);

        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();

        String selectedText = StringUtils.upperSnakeCaseToPascal(property.getValue().name());

        int dropDownBoxY = y + 10;
        boolean needScissor = Wrapper.getFontRenderer().getWidth(selectedText) > width - 4;

        int textColor = 0xFFFFFF;
        int bgColor = getSecondaryBackgroundColor(isHovered(mouseX, mouseY));

        Gui.drawRect(x, y, x + width, y + height, bgColor);
        Wrapper.getFontRenderer().drawStringWithShadow(getName(), x + 2, y + 1, textColor);
        Gui.drawRect(x + 2, dropDownBoxY, x + getWidth() - 2, dropDownBoxY + 10.5, 0xFF6B6B6B);
        Gui.drawRect(x + 2.5, dropDownBoxY + 0.5, x + getWidth() - 2.5, dropDownBoxY + 10, 0xFF3C3F41);

        if (needScissor)
            OGLUtils.startScissorBox(scaledResolution, x + 2, dropDownBoxY + 2, width - 5, 10);
        Wrapper.getFontRenderer().drawStringWithShadow(selectedText, x + 3.5F, dropDownBoxY + 1.5F, textColor);
        if (needScissor)
            OGLUtils.endScissorBox();

        if (isExpanded()) {
            Gui.drawRect(x + CategoryPanel.X_ITEM_OFFSET,
                    y + height,
                    x + width - CategoryPanel.X_ITEM_OFFSET,
                    y + getHeightWithExpand(),
                    ClickGui.getInstance().getPalette().getSecondaryBackgroundColor().getRGB());

            handleRender(x, y + getHeight() + 2, width, textColor);
        }
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int button) {
        super.onMouseClick(mouseX, mouseY, button);

        if (isExpanded())
            handleClick(mouseX, mouseY, getX(), getY() + getHeight() + 2, getWidth());
    }

    private <T extends Enum<T>> void handleRender(int x, int y, int width, int textColor) {
        int enabledColor = ClickGui.getInstance().getPalette().getEnabledModuleColor().getRGB();
        EnumProperty<T> property = (EnumProperty<T>) this.property;

        for (T e : property.getValues()) {
            if (property.isSelected(e))
                Gui.drawRect(x + CategoryPanel.X_ITEM_OFFSET,
                        y - 2,
                        x + width - CategoryPanel.X_ITEM_OFFSET,
                        y + CategoryPanel.ITEM_HEIGHT - 3 - 2,
                        enabledColor);

            Wrapper.getFontRenderer().drawStringWithShadow(StringUtils.upperSnakeCaseToPascal(e.name()),
                    x + CategoryPanel.X_ITEM_OFFSET + 2,
                    y,
                    textColor);

            y += (CategoryPanel.ITEM_HEIGHT - 3);
        }
    }

    private <T extends Enum<T>> void handleClick(int mouseX, int mouseY, int x, int y, int width) {
        EnumProperty<T> property = (EnumProperty<T>) this.property;
        for (T e : property.getValues()) {
            if (mouseX >= x &&
                    mouseY >= y &&
                    mouseX <= x + width &&
                    mouseY <= y + CategoryPanel.ITEM_HEIGHT - 3) {
                property.setValue(e);
            }

            y += CategoryPanel.ITEM_HEIGHT - 3;
        }
    }

    @Override
    public int getHeightWithExpand() {
        return getHeight() + property.getValues().length * (CategoryPanel.ITEM_HEIGHT - 3);
    }

    @Override
    public void onPress(int mouseX, int mouseY, int button) {
    }

    @Override
    public boolean canExpand() {
        return property.getValues().length > 1;
    }
}
