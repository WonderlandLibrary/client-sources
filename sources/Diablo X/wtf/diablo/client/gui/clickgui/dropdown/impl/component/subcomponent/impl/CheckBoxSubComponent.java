package wtf.diablo.client.gui.clickgui.dropdown.impl.component.subcomponent.impl;


import wtf.diablo.client.font.FontHandler;
import wtf.diablo.client.font.TTFFontRenderer;
import wtf.diablo.client.gui.clickgui.dropdown.impl.component.subcomponent.api.AbstractSubComponent;
import wtf.diablo.client.gui.clickgui.dropdown.impl.component.setting.impl.BooleanSettingComponent;
import wtf.diablo.client.util.render.ColorUtil;
import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;

public final class CheckBoxSubComponent extends AbstractSubComponent<Boolean> {
    private final static Color BACKGROUND_COLOR = new Color(19, 19, 19, 255);

    private final BooleanSettingComponent setting;
    private final int toggleGap;

    CheckBoxSubComponent(final Builder builder) {
        super(builder.x, builder.y, builder.width, builder.height);
        this.setting = builder.setting;
        this.value = setting.getSetting().getValue();
        this.toggleGap = builder.toggleGap;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        final int x = getX() + setting.getModulePanelComponent().getParent().getX() + 90;
        final int y = getY() + setting.getModulePanelComponent().getParent().getPanelY() + setting.getModulePanelComponent().getParent().getY();

        Color color = Color.WHITE;

        if (value) {
            color = ColorUtil.AMBIENT_COLOR.getValue();
        }

        RenderUtil.drawOutlineRoundedRectangle(x, y - 2, width, height, 2, .1f, color.getRGB(), BACKGROUND_COLOR.getRGB());

        final TTFFontRenderer font = FontHandler.fetch("check 14");

        if (value) {
            font.drawString("A", x + toggleGap - 1, y + toggleGap - 2, color.getRGB());
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        final int x = getX() + setting.getModulePanelComponent().getParent().getX() + 90;
        final int y = getY() + setting.getModulePanelComponent().getPanelY() + setting.getModulePanelComponent().getParent().getY();

        if(RenderUtil.isHovered(mouseX,mouseY,x,y,x + width, y + height)) {
            setting.getSetting().setValue(!value);
            value = !value;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private BooleanSettingComponent setting;
        private int x;
        private int y;
        private int width;
        private int height;
        private int toggleGap;

        public Builder setting(final BooleanSettingComponent setting) {
            this.setting = setting;
            return this;
        }

        public Builder x(final int x) {
            this.x = x;
            return this;
        }

        public Builder y(final int y) {
            this.y = y;
            return this;
        }

        public Builder width(final int width) {
            this.width = width;
            return this;
        }

        public Builder height(final int height) {
            this.height = height;
            return this;
        }

        public Builder toggleGap(final int toggleGap) {
            this.toggleGap = toggleGap;
            return this;
        }

        public CheckBoxSubComponent build() {
            return new CheckBoxSubComponent(this);
        }
    }
}
