package dev.excellent.impl.util.other;

import dev.excellent.api.interfaces.client.IMouse;
import dev.excellent.api.interfaces.game.IWindow;
import dev.excellent.impl.util.math.Interpolator;
import org.joml.Vector2f;

public class Drag implements IMouse, IWindow {
    public Vector2f position, targetPosition, size, offset = new Vector2f(0, 0);
    public boolean dragging = false;

    public Drag(Vector2f position, Vector2f size) {
        this.position = targetPosition = position;
        this.size = size;
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHover(mouseX, mouseY, position.x, position.y, size.x, size.y) && isLClick(button)) {
            dragging = true;
            offset.x = (int) (targetPosition.x - mouseX);
            offset.y = (int) (targetPosition.y - mouseY);
        }
    }

    public void render(int mouseX, int mouseY) {
        if (dragging) {
            if (targetPosition == null) targetPosition = new Vector2f(0, 0);
            targetPosition.x = Interpolator.lerp(targetPosition.x, mouseX + offset.x, 0.1F);
            targetPosition.y = Interpolator.lerp(targetPosition.y, mouseY + offset.y, 0.1F);
        }
        if (targetPosition == null) return;
        final int width = (int) scaled().x;
        final int height = (int) scaled().y;
        position.x = Math.max(0, position.x);
        position.x = Math.min(width - size.x, position.x);

        position.y = Math.max(0, position.y);
        position.y = Math.min(height - size.y, position.y);

        targetPosition.x = Math.max(0, targetPosition.x);
        targetPosition.x = Math.min(width - size.x, targetPosition.x);

        targetPosition.y = Math.max(0, targetPosition.y);
        targetPosition.y = Math.min(height - size.y, targetPosition.y);
    }

    public void mouseReleased() {
        targetPosition = position;
        dragging = false;
    }
}