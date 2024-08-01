package wtf.diablo.client.gui.clickgui.material.impl.setting;

import wtf.diablo.client.font.FontRepository;
import wtf.diablo.client.gui.clickgui.material.animations.Animation;
import wtf.diablo.client.gui.clickgui.material.api.AbstractComponent;
import wtf.diablo.client.gui.clickgui.material.impl.module.ModulePanel;
import wtf.diablo.client.setting.api.AbstractSetting;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.util.render.ColorUtil;
import wtf.diablo.client.util.render.RenderUtil;
import wtf.diablo.client.util.system.mouse.MouseUtils;

import java.awt.*;

public final class BooleanSettingComponent extends AbstractComponent {

    private final BooleanSetting value;
    private final Animation animation;

    public BooleanSettingComponent(final ModulePanel moduleButton, final AbstractSetting<?> abstractValue) {
        super(moduleButton, abstractValue);
        this.value = (BooleanSetting) abstractValue;
        this.animation = new Animation();
    }

    @Override
    public void initGui() {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        this.animation.slide(!this.value.getValue() ? 0 : 1, 2);
        final double animationVal = this.animation.getValue() > 0 && this.animation.getValue() < 1 ? this.animation.getValue() : (!this.value.getValue() ? 0 : 1);
        FontRepository.SFREG18.drawString(this.value.getName(), x + 3, y + (this.getExtendValue() - FontRepository.SFREG20.getHeight(this.value.getName())) / 2f - 4, new Color(255, 255, 255, 185).getRGB());
        RenderUtil.drawRoundedRectangle(this.x + this.getModuleButton().getWidth() - 20, y + (this.getExtendValue() - 6) / 2f - 4, 15, 6, 3, ColorUtil.interpolate2(new Color(43, 53, 63).getRGB(), ColorUtil.SECONDARY_MAIN_COLOR.getRGB(), (float) animationVal));
        RenderUtil.drawRoundedRectangle(this.x + this.getModuleButton().getWidth() - 20 + animationVal * 6, y + (this.getExtendValue() - 9) / 2f - 4, 9, 9, 4.5f, ColorUtil.interpolate2(new Color(100, 100, 100).getRGB(), ColorUtil.PRIMARY_MAIN_COLOR.getRGB(), (float) animationVal));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (MouseUtils.isHoveringCoords((float) (x + this.getModuleButton().getWidth() - 21), (float) (y + (this.getExtendValue() - 11) / 2f) - 4, 17, 10, mouseX, mouseY)) {
            this.value.setValue(!this.value.getValue());
            this.getModuleButton().getModuleContainer().getMainPanel().setCanDrag(false);
            this.getModuleButton().getModuleContainer().getMainPanel().setCanDrag(false);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.getModuleButton().getModuleContainer().getMainPanel().setCanDrag(true);
    }

    @Override
    public void onGuiClosed() {

    }

    @Override
    public int getExtendValue() {
        return 19;
    }
}
