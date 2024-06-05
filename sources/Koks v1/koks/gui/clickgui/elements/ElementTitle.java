package koks.gui.clickgui.elements;

import koks.utilities.value.Value;
import koks.utilities.value.values.TitleValue;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 10:01
 */
public class ElementTitle extends Element {

    public final TitleValue titleValue;
    private final ArrayList<Value> savedVisibilty = new ArrayList<>();

    public ElementTitle(TitleValue value) {
        super(value);
        this.titleValue = value;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        getFontRenderer().drawStringWithShadow("§n" + getValue().getName() + (titleValue.isExpanded() ? " -" : " +"), getX(), getY() + getHeight() / 2 - getFontRenderer().FONT_HEIGHT / 2, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && isHovering(mouseX, mouseY)) {
            titleValue.setExpanded(!titleValue.isExpanded());
            if (titleValue.isExpanded()) {
                this.savedVisibilty.forEach(visible -> {
                    Arrays.stream(this.titleValue.getObjects()).forEach(visible1 -> {
                        visible1.setVisible(visible.isVisible());
                    });
                });
            } else {
                savedVisibilty.clear();
                Arrays.stream(this.titleValue.getObjects()).forEach(visible -> {
                    this.savedVisibilty.add(titleValue);
                    visible.setVisible(false);
                });
            }
        }
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > getX() && mouseX < getX() + getWidth() && mouseY > getY() && mouseY < getY() + getHeight();
    }

    @Override
    public void mouseReleased() {

    }

    @Override
    public void keyTyped(int keyCode) {

    }


}
