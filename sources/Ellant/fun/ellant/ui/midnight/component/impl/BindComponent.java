package fun.ellant.ui.midnight.component.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.functions.settings.impl.BindSetting;
import fun.ellant.utils.client.KeyStorage;
import fun.ellant.utils.font.Fonts;
import fun.ellant.utils.render.DisplayUtils;

import java.awt.*;

public class BindComponent extends Component {

    public BindSetting option;
    boolean bind;


    public BindComponent(BindSetting option) {
        this.option = option;
        this.setting = option;
    }

    @Override
    public void drawComponent(MatrixStack matrixStack, int mouseX, int mouseY) {

        height -= 3;

        String bindString = option.get() == 0 ? "NONE" : KeyStorage.getKey(option.get());

        if (bindString == null) {
            bindString = "";
        }

        float width = Fonts.gilroy[14].getWidth(bindString) + 4;
        DisplayUtils.drawRoundedRect(x + 5, y + 2, width, 10, 2, bind ? new Color(17, 18, 21).brighter().brighter().getRGB() : new Color(17, 18, 21).brighter().getRGB());
        Fonts.gilroy[14].drawCenteredString(matrixStack, bindString, x + 5 + (width / 2), y + 6, -1);
        Fonts.gilroy[14].drawString(matrixStack, option.getName(), x + 5 + width + 3, y + 6, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (bind && mouseButton > 1) {
            option.set(-100 + mouseButton);
            bind = false;
        }
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            bind = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void keyTyped(int keyCode, int scanCode, int modifiers) {
        if (bind) {
            if (keyCode == 261) {
                option.set(0);
                bind = false;
                return;
            }
            option.set(keyCode);
            bind = false;
        }
    }

    @Override
    public void charTyped(char codePoint, int modifiers) {

    }
}
