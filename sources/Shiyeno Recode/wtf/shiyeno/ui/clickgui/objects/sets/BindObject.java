package wtf.shiyeno.ui.clickgui.objects.sets;

import com.mojang.blaze3d.matrix.MatrixStack;
import wtf.shiyeno.modules.settings.imp.BindSetting;
import wtf.shiyeno.ui.clickgui.binds.BindWindow;
import wtf.shiyeno.ui.clickgui.objects.ModuleObject;
import wtf.shiyeno.ui.clickgui.objects.Object;
import wtf.shiyeno.util.ClientUtil;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.util.render.ColorUtil;
import wtf.shiyeno.util.render.RenderUtil;

import java.util.concurrent.ThreadLocalRandom;

public class BindObject extends Object {

    public BindSetting set;
    public ModuleObject object;

    public BindWindow bindWindow;
    public boolean isBinding;

    public BindObject(ModuleObject object, BindSetting set) {
        this.object = object;
        this.set = set;
        setting = set;

        bindWindow = new BindWindow(this);
        bindWindow.x = 10 + ThreadLocalRandom.current().nextFloat(0, 200);
        bindWindow.y = 10 + ThreadLocalRandom.current().nextFloat(0, 200);
        bindWindow.width = 178 / 2f;
        bindWindow.height = 73 / 2f;
    }

    @Override
    public void draw(MatrixStack stack, int mouseX, int mouseY) {
        super.draw(stack, mouseX, mouseY);
        Fonts.msBold[13].drawString(stack, set.getName(), x + 10, y + height / 2f - Fonts.msBold[13].getFontHeight() / 2f + 3, ColorUtil.rgba(100, 100, 100, 255));

        float wwidth = Math.max(10, Fonts.msBold[13].getWidth(ClientUtil.getKey(set.getKey()) == null ? "" : ClientUtil.getKey(set.getKey()).toUpperCase()) + 4);
        RenderUtil.Render2D.drawRoundedRect(x + width - wwidth - 10, y, wwidth, 10, 3, ColorUtil.rgba(30, 30, 30, 255));

        Fonts.msBold[13].drawCenteredString(stack, ClientUtil.getKey(set.getKey()) == null ? "" : ClientUtil.getKey(set.getKey()).toUpperCase(), x + width - wwidth - 10 + wwidth / 2f, y + height / 2f - Fonts.msBold[13].getFontHeight() / 2f, ColorUtil.rgba(100, 100, 100, 255));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isBinding && mouseButton > 1) {
            set.setKey(-100 + mouseButton);
            isBinding = false;
        }
        if (isHovered(mouseX, mouseY) && (mouseButton == 2)) {
            isBinding = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
    }

    @Override
    public void keyTyped(int keyCode, int scanCode, int modifiers) {
        if (isBinding) {
            if (keyCode == 261) {
                set.setKey(0);
                isBinding = false;
                return;
            }
            set.setKey(keyCode);
            isBinding = false;
        }
    }

    @Override
    public void charTyped(char codePoint, int modifiers) {
    }
}