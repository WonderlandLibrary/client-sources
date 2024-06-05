package client.metaware.api.clickgui.panel.implementations;

import client.metaware.api.clickgui.panel.Panel;
import client.metaware.api.module.api.Category;
import client.metaware.impl.utils.render.RenderUtil;
import org.lwjgl.opengl.GL11;

public class CategoryPanel extends Panel {

    private float dragX, dragY;
    private Category category;
    private boolean dragging;

    public CategoryPanel(Category category, float x, float y, float width, float height) {
        super(x, y, width, height);
        this.category = category;
    }

    @Override
    public void reset() {
        origHeight = height;
        super.reset();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        if (dragging) {
            x = mouseX + dragX;
            y = mouseY + dragY;
            origX = x;
            origY = y;
        }
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.prepareScissorBox(x, y, x + width, y + origHeight);
        origHeight = RenderUtil.animate(totalHeight(), origHeight, 0.05f) + 0.1f;
        if (origHeight > totalHeight()) origHeight = totalHeight();
        if (origHeight < 0) origHeight = 0;
        theme.drawCategory(this, x, y, width, origHeight);
        super.drawScreen(mouseX, mouseY);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (RenderUtil.isHovered(x, y, width, height, mouseX, mouseY)) {
            if (mouseButton == 0) {
                dragging = true;
                dragX = (x - mouseX);
                dragY = (y - mouseY);
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        dragging = false;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
    }

    public Category category() {
        return category;
    }

    public void category(Category category) {
        this.category = category;
    }
}
