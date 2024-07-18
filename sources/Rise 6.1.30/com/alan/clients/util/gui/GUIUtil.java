package com.alan.clients.util.gui;

import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.util.vector.Vector2f;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GUIUtil {

    public boolean mouseOver(final double posX, final double posY, final double width, final double height, final double mouseX, final double mouseY) {
        return mouseX > posX && mouseX < posX + width && mouseY > posY && mouseY < posY + height;
    }

    public boolean mouseOver(final Vector2d position, final Vector2d scale, final double mouseX, final double mouseY) {
        return mouseX > position.x && mouseX < position.x + scale.x && mouseY > position.y && mouseY < position.y + scale.y;
    }

    public boolean mouseOver(final Vector2f position, final Vector2f scale, final double mouseX, final double mouseY) {
        return mouseX > position.x && mouseX < position.x + scale.x && mouseY > position.y && mouseY < position.y + scale.y;
    }

    public boolean mouseOver(final Vector2f position, final Vector2f scale, final Vector2d mouse) {
        return mouse.x > position.x && mouse.x < position.x + scale.x && mouse.y > position.y && mouse.y < position.y + scale.y;
    }
}
