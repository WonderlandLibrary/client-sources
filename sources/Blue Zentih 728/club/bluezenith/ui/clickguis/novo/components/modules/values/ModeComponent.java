package club.bluezenith.ui.clickguis.novo.components.modules.values;

import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.types.ActionValue;
import club.bluezenith.ui.clickguis.novo.components.Component;
import club.bluezenith.ui.clickgui.ClickGui;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.font.TFontRenderer;
import net.minecraft.client.gui.ScaledResolution;

import java.util.Locale;

import static java.lang.String.valueOf;

public class ModeComponent extends Component {
    private static final TFontRenderer font = FontUtil.createFont("helvetica", 32);

    private final Value<?> parent;

    //3 different values accepted so that i don't have to implement 3 components that do the same thing
    public ModeComponent(Value<?> parent, float x, float y) {
        super(x, y);
        this.parent = parent;

        this.identifier = parent instanceof ActionValue ? parent.name.toUpperCase(Locale.ROOT) : parent.name;
    }

    @Override
    public boolean shouldUpdateWidth() {
        return true;
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
        y += 1;
        if(parent instanceof ActionValue) {
            font.drawString(identifier, x + width/2F - font.getStringWidthF(identifier)/2F, y + 2, -1, true);
        } else {
            font.drawString("§7" + identifier, x + 3, y + 2, -1, true);

            final String value = valueOf(parent.get()).toUpperCase(Locale.ROOT);

            font.drawString(value, x + width - 4 - font.getStringWidthF(value), y + 2, -1, true);
        }
        y -= 1;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton == 0)
            parent.next();
        else if(mouseButton == 1)
            parent.previous();
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {

    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return ClickGui.i(mouseX, mouseY, getX(), getY(), getX() + getWidth(), getY() + getHeight() - 2);
    }
}
