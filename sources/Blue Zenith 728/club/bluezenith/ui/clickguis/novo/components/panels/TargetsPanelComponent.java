package club.bluezenith.ui.clickguis.novo.components.panels;

import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.ui.clickguis.novo.AncientNovoGUI;
import club.bluezenith.ui.clickguis.novo.components.Component;
import club.bluezenith.ui.clickguis.novo.components.modules.values.BooleanComponent;
import club.bluezenith.ui.clickgui.ClickGui;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.font.TFontRenderer;
import club.bluezenith.util.player.TargetHelper;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static club.bluezenith.ui.clickguis.novo.AncientNovoGUI.TITLE_RECT_HEIGHT;
import static club.bluezenith.util.render.RenderUtil.rect;

public class TargetsPanelComponent extends Component {
    private static final TFontRenderer title = FontUtil.createFont("posaytightposaycleanposayfresh2", 38);

    private boolean isDragging, setPrevXY;
    private float prevX, prevY;

    private final List<Component> targetOptions = new ArrayList<>();

    private final AncientNovoGUI ancientNovoGUI;

    public TargetsPanelComponent(AncientNovoGUI ancientNovoGUI, float x, float y) {
        super(x, y);

        for (TargetHelper.Targets value : TargetHelper.Targets.values()) {
            final BooleanValue booleanValue = new BooleanValue(value.displayName, value.on);
            booleanValue.setValueChangeListener((before, after) -> value.on = after);

            final BooleanComponent newComponent = new BooleanComponent(null, booleanValue, x, y);
            newComponent.setHeight(10);
            newComponent.setWidth(10);
            targetOptions.add(newComponent);
        }
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
        rect(x, y, x + (width = 100), y + TITLE_RECT_HEIGHT, ancientNovoGUI.getGuiAccentColor());


        title.drawString(identifier, x + 2, y + 2, -1, true);

        if(this.shown) {

            updateSize();
            rect(x, y + TITLE_RECT_HEIGHT, x + width, height, new Color(0, 0, 0, 200));

            float targetHeight = y + TITLE_RECT_HEIGHT + 5;

            for (Component component : targetOptions) {
                component.moveTo(getX(), targetHeight);

                component.draw(mouseX, mouseY, scaledResolution);
                targetHeight += component.getHeight();
            }
        }

        if(isDragging)
            mouseDragging(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(ClickGui.i(mouseX, mouseY, x, y, x + width, y + TITLE_RECT_HEIGHT)) {
            if (mouseButton == 0)
                isDragging = !isDragging;
            else if (mouseButton == 1)
                shown = !shown;
            return;
        }
        if(!shown) return;
        for (Component targetOption : targetOptions) {
            if(targetOption.isMouseOver(mouseX, mouseY)) {
                targetOption.mouseClicked(mouseX, mouseY, mouseButton);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        isDragging = false;
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {

    }

    public void updateSize() {
        float backgroundHeight = y + TITLE_RECT_HEIGHT;

        for (Component targetOption : targetOptions) {
            backgroundHeight += targetOption.getHeight();
        }

        backgroundHeight += 3;

        height = backgroundHeight;
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return isMouseInBounds(mouseX, mouseY);
    }

    private void mouseDragging(int mouseX, int mouseY) {
        if (!setPrevXY) {
            this.prevX = mouseX - x;
            this.prevY = mouseY - y;
            this.setPrevXY = true;
        } else {
            this.x = mouseX - this.prevX;
            this.y = mouseY - this.prevY;
        }
    }
}
