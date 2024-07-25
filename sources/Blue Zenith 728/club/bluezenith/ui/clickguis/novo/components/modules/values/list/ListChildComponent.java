package club.bluezenith.ui.clickguis.novo.components.modules.values.list;

import club.bluezenith.module.value.types.ListValue;
import club.bluezenith.ui.clickguis.novo.components.Component;
import club.bluezenith.ui.clickgui.ClickGui;
import club.bluezenith.util.font.TFontRenderer;
import net.minecraft.client.gui.ScaledResolution;

import static net.minecraft.util.EnumChatFormatting.GRAY;

public class ListChildComponent extends Component {
    private final ListValue parent;

    private final TFontRenderer font;

    private final String value;

    public ListChildComponent(ListValue parent, TFontRenderer font, String value, float x, float y) {
        super(x, y);

        this.parent = parent;
        this.font = font;
        this.value = value;
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
    @SuppressWarnings("all")
    public void draw(int mouseX, int mouseY, ScaledResolution scaledResolution) {
        if(!parent.getOptionState(value))
            font.drawString(GRAY + value, x + 6, y, -1, true);
        else font.drawString(value, x + 6, y, -1, true);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        parent.setOptionState(value, !parent.getOptionState(value));
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {

    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return ClickGui.i(mouseX, mouseY, getX(), getY() - 2, getX() + getWidth(), getY() + font.getHeight(value) + 2);//isMouseInBounds(mouseX, mouseY);
    }
}
