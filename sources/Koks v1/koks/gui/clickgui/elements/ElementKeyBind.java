package koks.gui.clickgui.elements;

import koks.Koks;
import koks.modules.Module;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 19:22
 */
public class ElementKeyBind extends Element {

    public boolean settingKey;

    public ElementKeyBind(Module module) {
        super(module);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        String text = settingKey ? "Please enter a vaild Key" : "Key:" + Keyboard.getKeyName(getModule().getKeyBind());
        GL11.glPushMatrix();
        GL11.glTranslated(getX() + 2, getY() + getHeight() / 2 - getFontRenderer().FONT_HEIGHT / 2 + 1.5F, 0);
        GL11.glScaled(0.75, 0.75, 0.75);
        getFontRenderer().drawStringWithShadow(text, 0, 0, -1);
        GL11.glPopMatrix();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            this.settingKey = true;
        }
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > getX() && mouseX < getX() + getWidth() && mouseY > getY() + getHeight() / 2 - 3.5F && mouseY < getY() + getHeight() / 2 + 3.5F;
    }


    @Override
    public void mouseReleased() {

    }

    @Override
    public void keyTyped(int keyCode) {
        if (settingKey) {
            keyCode = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() : Keyboard.getEventKey();
            if (keyCode == Keyboard.KEY_ESCAPE)
                keyCode = Keyboard.KEY_NONE;
            this.getModule().setKeyBind(keyCode);
            Koks.getKoks().shutdownClient();
            this.settingKey = false;
        }
    }
}
