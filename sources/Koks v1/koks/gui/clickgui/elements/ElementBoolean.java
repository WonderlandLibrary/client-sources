package koks.gui.clickgui.elements;

import koks.Koks;
import koks.utilities.value.Value;
import koks.utilities.value.values.BooleanValue;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 09:32
 */
public class ElementBoolean extends Element {

    public BooleanValue value;

    public ElementBoolean(BooleanValue value) {
        super(value);
        this.value = value;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        getRenderUtils().drawRect(7, getX(), getY() + getHeight() / 2 - 3.5F, getX() + 7, getY() + getHeight() / 2 + 3.5F, new Color(40, 39, 42, 255));
        if(value.isToggled())
            getRenderUtils().drawRect(7, getX(), getY() + getHeight() / 2 - 3.5F, getX() + 7, getY() + getHeight() / 2 + 3.5F, Koks.getKoks().client_color);
        getRenderUtils().drawOutlineRect( getX(), getY() + getHeight() / 2 - 3.5F, getX() + 7, getY() + getHeight() / 2 + 3.5F,1, Color.BLACK);

        GL11.glPushMatrix();
        GL11.glTranslated(getX() + 9, getY() + getHeight() / 2 - getFontRenderer().FONT_HEIGHT / 2 + 1.5F, 0);
        GL11.glScaled(0.75, 0.75, 0.75);
        getFontRenderer().drawStringWithShadow(this.value.getName(), 0, 0, -1);
        GL11.glPopMatrix();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            this.value.setToggled(!this.value.isToggled());
        }
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > getX() && mouseX < getX() + 7 && mouseY > getY() + getHeight() / 2 - 3.5F && mouseY < getY() + getHeight() / 2 + 3.5F;
    }

    @Override
    public void mouseReleased() {

    }

    @Override
    public void keyTyped(int keyCode) {

    }
}
