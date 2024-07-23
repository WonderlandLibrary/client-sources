package io.github.liticane.monoxide.ui.screens.clickgui.astolfo.component.impl;

import io.github.liticane.monoxide.util.render.font.FontStorage;
import net.minecraft.client.gui.FontRenderer;
import io.github.liticane.monoxide.value.Value;
import io.github.liticane.monoxide.value.impl.MultiBooleanValue;
import io.github.liticane.monoxide.util.render.RenderUtil;

import java.awt.*;

public class MultiStringBoxComponent extends ValueComponent {

    private boolean expanded = false;
    private MultiBooleanValue multiBooleanValue;

    public MultiStringBoxComponent(Value value, float posX, float posY, float baseWidth, float baseHeight) {
        super(value, posX, posY, baseWidth, baseHeight);
        this.multiBooleanValue = (MultiBooleanValue) value;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        FontRenderer normal = FontStorage.getInstance().findFont("SFUI Medium", 16);
        normal.drawString(value.getName(), getPosX() + 5 + getAddX(), getPosY() + getBaseHeight() / 2 - normal.FONT_HEIGHT / 2, -1);
        normal.drawString((multiBooleanValue.getValue().size() - 2) + " Enabled", getPosX() + this.getBaseWidth() - 5 - normal.getStringWidthInt(multiBooleanValue.getValue().size() + " Enabled") + getAddX(), getPosY() + getBaseHeight() / 2 - normal.FONT_HEIGHT / 2,-1);
        if(expanded) {
            float y = this.getPosY() + this.getBaseHeight();
            for(String string : multiBooleanValue.getValues()) {
                normal.drawString(" - " + string, getPosX() + 5 + getAddX(), y + getBaseHeight() / 2 - normal.FONT_HEIGHT / 2, -1);
                normal.drawString("X", getPosX() + this.getBaseWidth() - 5 - normal.getStringWidthInt("X") + getAddX(), y + getBaseHeight() / 2 - normal.FONT_HEIGHT / 2,  !multiBooleanValue.get(string) ? new Color(200, 200, 200).getRGB() : -1);
                y += this.getBaseHeight();
            }
        }
    }

    @Override
    public float getFinalHeight() {
        return this.expanded ? this.getBaseHeight() + this.getBaseHeight() * multiBooleanValue.getValues().length : this.getBaseHeight();
    }

    @Override
    public void mouseClick(int mouseX, int mouseY, int mouseButton) {
        if(RenderUtil.isHovered(mouseX, mouseY, this.getPosX() + getAddX(), this.getPosY(), this.getBaseWidth(), this.getBaseHeight())) {
            expanded = !expanded;
        }
        if(expanded) {
            float y = this.getPosY() + this.getBaseHeight();
            String toToggle = null;
            for(String string : multiBooleanValue.getValues()) {
                if(RenderUtil.isHovered(mouseX, mouseY, this.getPosX() + getAddX(), y, this.getBaseWidth(), this.getBaseHeight())) {
                    toToggle = string;
                }
                y += this.getBaseHeight();
            }
            if(toToggle != null)
                multiBooleanValue.toggle(toToggle);
        }
    }
}
