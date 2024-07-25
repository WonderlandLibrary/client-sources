package club.bluezenith.ui.clickguis.novo.components.panels.config;

import club.bluezenith.ui.clickguis.novo.AncientNovoGUI;
import club.bluezenith.ui.clickguis.novo.components.Component;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.font.TFontRenderer;
import club.bluezenith.util.render.ColorUtil;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

import static club.bluezenith.util.render.ColorUtil.transitionBetween;
import static club.bluezenith.util.render.RenderUtil.animate;
import static club.bluezenith.util.render.RenderUtil.rect;

public class ConfigActionComponent extends Component {
    private static final int BACKGROUND_COLOR = new Color(0, 0, 0, 200).getRGB();
    private static final TFontRenderer font = FontUtil.createFont("posaytightposaycleanposayfresh2", 36);

    private final Runnable clickAction;

    private float hoverAlpha;

    private final AncientNovoGUI ancientNovoGUI;

    public ConfigActionComponent(AncientNovoGUI ancientNovoGUI, float x, float y, Runnable clickAction) {
        super(x, y);
        this.clickAction = clickAction;
        this.ancientNovoGUI = ancientNovoGUI;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void draw(int mouseX, int mouseY, ScaledResolution scaledResolution) {
        Color color = Color.WHITE;

        boolean isMouseOver = isMouseOver(mouseX, mouseY);

        if(isMouseOver || hoverAlpha > 0.05F) {
            hoverAlpha = animate(isMouseOver ? 0.7F : 0F, hoverAlpha, 0.1F);

            final float[] activeColor = ColorUtil.get(ancientNovoGUI.getGuiAccentColor()),
                    normalColor = new float[] { 1F, 1F, 1F };

            color = transitionBetween(hoverAlpha, normalColor, activeColor);
        }

        rect(x, y, x + width, y + height, BACKGROUND_COLOR);
        font.drawString(identifier, x + 2, y + height - font.getHeight(identifier) - 2, color.getRGB(), true);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        clickAction.run();
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {

    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return isMouseInBounds(mouseX, mouseY);
    }
}
