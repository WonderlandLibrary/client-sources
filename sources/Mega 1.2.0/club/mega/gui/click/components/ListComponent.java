package club.mega.gui.click.components;

import club.mega.Mega;
import club.mega.gui.click.Component;
import club.mega.module.setting.impl.ListSetting;

public class ListComponent extends Component {

    private final ListSetting setting;

    public ListComponent(ListSetting setting, double width, double height) {
        super(setting, width, height);
        this.setting = setting;
    }

    @Override
    public void drawComponent(final int mouseX, final int mouseY) {
        super.drawComponent(mouseX, mouseY);
        Mega.INSTANCE.getFontManager().getFont("Arial 20").drawString(setting.getName() + ":", x + 5, y + 5, -1);
        Mega.INSTANCE.getFontManager().getFont("Arial 20").drawString(setting.getCurrent(), x + width - Mega.INSTANCE.getFontManager().getFont("Arial 20").getWidth(setting.getCurrent()) - 5, y + 5, -1);
    }

    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        if (isInside(mouseX, mouseY)) {
            if (mouseButton == 0)
                setting.loopNext();
            if (mouseButton == 1)
                setting.loopPrev();
        }
    }

}
