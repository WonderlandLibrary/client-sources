package wtf.diablo.client.gui.clickgui.material.impl.setting;

import net.minecraft.util.EnumChatFormatting;
import wtf.diablo.client.font.FontHandler;
import wtf.diablo.client.font.FontRepository;
import wtf.diablo.client.font.TTFFontRenderer;
import wtf.diablo.client.gui.clickgui.material.animations.Animation;
import wtf.diablo.client.gui.clickgui.material.api.AbstractComponent;
import wtf.diablo.client.gui.clickgui.material.impl.module.ModulePanel;
import wtf.diablo.client.setting.api.AbstractSetting;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.util.render.ColorUtil;
import wtf.diablo.client.util.render.RenderUtil;
import wtf.diablo.client.util.system.mouse.MouseUtils;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class ModeSettingComponent extends AbstractComponent {

    private boolean extended;
    private int extendValueMode;
    private final ModeSetting<?> enumValue;
    private final Animation animation;
    private double width;
    private final int height;


    public ModeSettingComponent(ModulePanel moduleButton, AbstractSetting<?> abstractValue) {
        super(moduleButton, abstractValue);
        enumValue = (ModeSetting<?>) abstractValue;
        this.animation = new Animation();
        this.height = 15;
    }

    @Override
    public void initGui() {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        width = getModuleButton().getWidth() - 25;
        final String name = enumValue.getValue().getName();
        final String modeName = enumValue.getName() + ": ";
        FontRepository.SFREG18.drawString(modeName, x + 2.5f, y + 4, new Color(255, 255, 255, 255).getRGB());
        FontRepository.SFREG18.drawString(name, x + FontRepository.SFREG18.getWidth(modeName), y + 4, new Color(255, 255, 255, 185).getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        width = FontRepository.SFREG18.getWidth(enumValue.getName() + ": " + enumValue.getValue().getName());
        if (MouseUtils.isHoveringCoords(x + 2.5f, y + 4, FontRepository.SFREG18.getWidth(enumValue.getName() + ": " + enumValue.getValue().getName()) + 2, FontRepository.SFREG18.getHeight(enumValue.getName() + ": " + enumValue.getValue().getName()), mouseX, mouseY)) {
            if (mouseButton == 0) {
                enumValue.cycle(false);
            } else if (mouseButton == 1) {
                enumValue.cycle(true);
            }
            this.getModuleButton().getModuleContainer().getMainPanel().setCanDrag(false);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public void onGuiClosed() {

    }

    @Override
    public int getExtendValue() {
        return 20 + extendValueMode;
    }
}
