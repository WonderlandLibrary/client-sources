package club.strifeclient.ui.implementations.hud.clickgui.components;

import club.strifeclient.module.Category;
import club.strifeclient.ui.implementations.hud.clickgui.ExtendableComponent;
import club.strifeclient.ui.implementations.hud.clickgui.Theme;
import club.strifeclient.util.ui.DraggingUtil;
import net.minecraft.client.gui.ScaledResolution;

public class CategoryComponent extends ExtendableComponent<Category> {

    private final DraggingUtil draggingUtil;
    private long start;

    public CategoryComponent(Category object, Theme theme, float x, float y, float width, float height) {
        super(object, theme, null, x, y, width, height);
        draggingUtil = new DraggingUtil(x, y, width, height);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        final ScaledResolution scaledResolution = new ScaledResolution(mc);
        float fOfX = Math.min(1, (System.currentTimeMillis() - start) / 500f);
        float expandAnimation = (float) (Math.pow(fOfX - 1, 6));
        height = origHeight * (1 - expandAnimation);
        draggingUtil.drawScreen(mouseX, mouseY, partialTicks);
        if (this.x != draggingUtil.getX())
            this.x = draggingUtil.getX();
        if (this.y != draggingUtil.getY())
            this.y = draggingUtil.getY();
        theme.drawCategory(object, x, y, width, height, origHeight, partialTicks, scaledResolution);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        draggingUtil.mouseClicked(mouseX, mouseY, button);
        if (isHovered(mouseX, mouseY) && button == 1) {
            setExtended(!isExtended());
            start = System.currentTimeMillis();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {
        draggingUtil.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }

    @Override
    public void onGuiClosed() {
        draggingUtil.mouseReleased(0, 0, 0);
    }

    @Override
    public void initGui() {
        height = 0;
        start = System.currentTimeMillis();
    }

}
