package club.strifeclient.util.ui;

import club.strifeclient.util.misc.MinecraftUtil;
import club.strifeclient.util.rendering.RenderUtil;
import lombok.Getter;

@Getter
public class DraggingUtil extends MinecraftUtil {
    private float x, y, width, height, prevMouseX, prevMouseY;
    private boolean dragging;

    public DraggingUtil(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(dragging) {
            float deltaX = mouseX - prevMouseX;
            float deltaY = mouseY - prevMouseY;
            x += deltaX;
            y += deltaY;
            prevMouseX = mouseX;
            prevMouseY = mouseY;
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (RenderUtil.isHovered(x, y, width, height, mouseX, mouseY) && button == 0) {
            dragging = true;
            prevMouseX = mouseX;
            prevMouseY = mouseY;
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int button) {
        dragging = false;
    }

}
