package club.strifeclient.ui.implementations.hud.clickgui.components;

import club.strifeclient.module.Module;
import club.strifeclient.ui.implementations.hud.clickgui.Component;
import club.strifeclient.ui.implementations.hud.clickgui.ExtendableComponent;
import club.strifeclient.ui.implementations.hud.clickgui.GuiFocusable;
import club.strifeclient.ui.implementations.hud.clickgui.Theme;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjglx.input.Keyboard;

import java.io.IOException;

public class BindComponent extends Component<Module> implements GuiFocusable {

    private boolean focused;
    private long start;

    public BindComponent(Module object, Theme theme, Component<?> parent, float x, float y, float width, float height) {
        super(object, theme, parent, x, y, width, height);
        if(parent instanceof ExtendableComponent<?>) {
            ExtendableComponent<?> extendableParent = (ExtendableComponent<?>) parent;
            extendableParent.addExtendCallback(extended -> {
                visible = extended;
                start = System.currentTimeMillis();
            });
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float fOfX = Math.min(1, (System.currentTimeMillis() - start) / 500f);
        float expandAnimation = (float) (Math.pow(fOfX - 1, 6));
        height = origHeight * (1 - expandAnimation);
        theme.drawBindSetting(object, x, y, width, height, origHeight, focused, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0)
            focused = true;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (focused && (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_BACK)) {
            object.setKeybind(0);
            focused = false;
        }
        if (focused && ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
            object.setKeybind(keyCode);
            focused = false;
        }
    }

    @Override
    public boolean isFocused() {
        return focused;
    }

    @Override
    public void setFocused(boolean focused) {
       this.focused = focused;
    }

    @Override
    public void onGuiClosed() {

    }

    @Override
    public void initGui() {
        start = System.currentTimeMillis();
    }
}
