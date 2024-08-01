package wtf.diablo.client.gui.clickgui.material.impl.module;

import wtf.diablo.client.font.FontRepository;
import wtf.diablo.client.gui.clickgui.material.animations.Animation;
import wtf.diablo.client.gui.clickgui.material.api.AbstractComponent;
import wtf.diablo.client.gui.clickgui.material.impl.setting.BooleanSettingComponent;
import wtf.diablo.client.gui.clickgui.material.impl.setting.ModeSettingComponent;
import wtf.diablo.client.gui.clickgui.material.impl.setting.NumberSettingComponent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.setting.api.AbstractSetting;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.render.ColorUtil;
import wtf.diablo.client.util.render.RenderUtil;
import wtf.diablo.client.util.system.mouse.MouseUtils;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public final class ModulePanel {

    private final ModuleContainer moduleContainer;
    private final AbstractModule abstractModule;
    private final Animation animation;
    private final Collection<AbstractComponent> componentArrayList;
    private int x, y;
    private float width, height;

    public ModulePanel(final ModuleContainer moduleContainer, final AbstractModule abstractModule) {
        this.moduleContainer = moduleContainer;
        this.abstractModule = abstractModule;
        this.animation = new Animation();
        this.componentArrayList = new ArrayList<>();

        for (final AbstractSetting<?> setting : abstractModule.getSettingList()) {
            if (setting instanceof BooleanSetting) {
                this.componentArrayList.add(new BooleanSettingComponent(this, setting));
            }

            if (setting instanceof NumberSetting<?>) {
                this.componentArrayList.add(new NumberSettingComponent(this, (NumberSetting<?>) setting));
            }

            if (setting instanceof ModeSetting<?>) {
                this.componentArrayList.add(new ModeSettingComponent(this, setting));
            }

        }

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.animation.slide(!this.abstractModule.isEnabled() ? 0 : 1, 2);
        double animationVal = this.animation.getValue() > 0 && this.animation.getValue() < 1 ? this.animation.getValue() : (!this.abstractModule.isEnabled() ? 0 : 1);

        RenderUtil.drawRect(this.x, this.y, (int) this.width, (int) this.height, new Color(20, 20, 20).getRGB());
        FontRepository.SFREG22.drawString(this.abstractModule.getName(), this.x + 4, this.y + 6, -1);

        RenderUtil.drawRoundedRectangle(this.x + this.width - 20, this.y + (25 - 6) / 2f, 15, 6, 3, ColorUtil.interpolate2(new Color(43, 53, 63).getRGB(), ColorUtil.SECONDARY_MAIN_COLOR.getRGB(), (float) animationVal));
        RenderUtil.drawRoundedRectangle(this.x + this.width - 20 + animationVal * 6, y + (25 - 9) / 2f, 9, 9, 4.5f, ColorUtil.interpolate2(new Color(100, 100, 100).getRGB(), ColorUtil.PRIMARY_MAIN_COLOR.getRGB(), (float) animationVal));

        final AtomicInteger count = new AtomicInteger();
        if (!this.componentArrayList.isEmpty()) {
            this.componentArrayList.forEach(component -> {
                component.setX(this.x);
                component.setY(this.y + 22 + count.get());
                component.drawScreen(mouseX, mouseY);
                count.addAndGet(component.getExtendValue());
            });
        }
        this.height = 18 + count.get();
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (MouseUtils.isHoveringCoords(this.x + this.width - 21, this.y + (25 - 11) / 2f, 17, 10, mouseX, mouseY)) {
            this.abstractModule.toggle(!this.abstractModule.isEnabled());
            this.moduleContainer.getMainPanel().setCanDrag(false);
        }

        if (mouseButton == 1) {
            this.componentArrayList.forEach(componentArrayList -> componentArrayList.setExtended(!componentArrayList.isExtended()));
        }

        if (!this.componentArrayList.isEmpty()) {
            this.componentArrayList.forEach(component -> component.mouseClicked(mouseX, mouseY, mouseButton));
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (!this.componentArrayList.isEmpty()) {
            this.componentArrayList.forEach(component -> component.mouseReleased(mouseX, mouseY, state));
        }
    }

    public void onGuiClosed() {
        if (!this.componentArrayList.isEmpty()) {
            this.componentArrayList.forEach(AbstractComponent::onGuiClosed);
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }


    public ModuleContainer getModuleContainer() {
        return moduleContainer;
    }
}
