package wtf.diablo.client.gui.clickgui.material.api;

import net.minecraft.client.Minecraft;
import wtf.diablo.client.gui.clickgui.material.impl.module.ModulePanel;
import wtf.diablo.client.setting.api.AbstractSetting;

public abstract class AbstractComponent {
    protected static final Minecraft mc = Minecraft.getMinecraft();

    private final ModulePanel moduleButton;
    private final AbstractSetting<?> abstractValue;
    private boolean extended;
    protected double x, y, extendValue;

    public AbstractComponent(final ModulePanel moduleButton, final AbstractSetting<?> abstractValue) {
        this.moduleButton = moduleButton;
        this.abstractValue = abstractValue;
    }

    public abstract void initGui();

    public abstract void drawScreen(int mouseX, int mouseY);

    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);

    public abstract void mouseReleased(int mouseX, int mouseY, int state);

    public abstract void onGuiClosed();

    public ModulePanel getModuleButton() {
        return this.moduleButton;
    }

    public int getExtendValue() {
        return 0;
    }

    public AbstractSetting getValue() {
        return this.abstractValue;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }
}
