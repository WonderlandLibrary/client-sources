package xyz.mathax.mathaxclient.gui.widgets;

import xyz.mathax.mathaxclient.gui.renderer.GuiRenderer;
import xyz.mathax.mathaxclient.gui.renderer.packer.GuiTexture;
import xyz.mathax.mathaxclient.utils.render.color.Color;

public class WGuiTexture extends WWidget {
    private final double width, height;
    private final double rotation;

    private final GuiTexture guiTexture;

    public WGuiTexture(double width, double height, double rotation, GuiTexture guiTexture) {
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.guiTexture = guiTexture;
    }

    @Override
    protected void onCalculateSize() {
        super.width = theme.scale(width);
        super.height = theme.scale(height);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.rotatedQuad(guiTexture, Color.WHITE, x, y, super.width, super.height, rotation);
    }
}
