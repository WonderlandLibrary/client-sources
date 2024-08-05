package studio.dreamys.clickgui.component.components.sub;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import studio.dreamys.clickgui.component.Component;
import studio.dreamys.clickgui.component.components.Button;
import studio.dreamys.setting.Setting;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Slider extends Component {
    private final Setting set;
    private final Button parent;
    private boolean hovered;
    private int offset;
    private int x;
    private int y;
    private boolean dragging;

    private double renderWidth;

    public Slider(Setting value, Button button, int offset) {
        set = value;
        parent = button;
        x = button.parent.getX() + button.parent.getWidth();
        y = button.parent.getY() + button.offset;
        this.offset = offset;
    }

    private static double roundToPlace(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void renderComponent() {
        Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 12, hovered ? 0xFF222222 : 0xFF111111);
        Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + (int) renderWidth, parent.parent.getY() + offset + 12, hovered ? 0xFF555555 : 0xFF444444);
        Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, 0xFF111111);
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(set.getName() + ": " + set.getValDouble(), (parent.parent.getX() * 2 + 15), (parent.parent.getY() + offset + 2) * 2 + 5, -1);
        GL11.glPopMatrix();
    }

    @Override
    public void setOff(int newOff) {
        offset = newOff;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        hovered = isMouseOnButtonD(mouseX, mouseY) || isMouseOnButtonI(mouseX, mouseY);
        y = parent.parent.getY() + offset;
        x = parent.parent.getX();

        double diff = Math.min(88, Math.max(0, mouseX - x));

        double min = set.getMin();
        double max = set.getMax();

        renderWidth = (88) * (set.getValDouble() - min) / (max - min);

        if (dragging) {
            if (diff == 0) {
                set.setValDouble(set.getMin());
            } else {
                double newValue = Slider.roundToPlace(((diff / 88) * (max - min) + min));
                set.setValDouble(newValue);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if ((isMouseOnButtonD(mouseX, mouseY) || isMouseOnButtonI(mouseX, mouseY)) && button == 0 && parent.open) {
            dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        dragging = false;
    }

    public boolean isMouseOnButtonD(int x, int y) {
        return x > this.x && x < this.x + (parent.parent.getWidth() / 2 + 1) && y > this.y && y < this.y + 12;
    }

    public boolean isMouseOnButtonI(int x, int y) {
        return x > this.x + parent.parent.getWidth() / 2 && x < this.x + parent.parent.getWidth() && y > this.y && y < this.y + 12;
    }
}
