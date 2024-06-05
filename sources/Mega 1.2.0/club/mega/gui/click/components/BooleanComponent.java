package club.mega.gui.click.components;

import club.mega.Mega;
import club.mega.gui.click.Component;
import club.mega.module.setting.impl.BooleanSetting;
import club.mega.util.ColorUtil;
import club.mega.util.RenderUtil;

public class BooleanComponent extends Component {

    private final BooleanSetting setting;

    public BooleanComponent(BooleanSetting setting, double width, double height) {
        super(setting, width, height);
        this.setting = setting;
    }

    @Override
    public void drawComponent(final int mouseX, final int mouseY) {
        super.drawComponent(mouseX, mouseY);
        if (setting.get())
            RenderUtil.drawRect(x, y, width, height, ColorUtil.getMainColor());
        Mega.INSTANCE.getFontManager().getFont("Arial 20").drawString(setting.getName(), x + 5, y + 5, -1);
    }

    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        if (isInside(mouseX, mouseY))
            setting.set(!setting.get());
    }

}
