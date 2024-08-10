package cc.slack.ui.clickgui.component.components.sub;

import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.ui.clickgui.component.Component;
import cc.slack.ui.clickgui.component.components.Button;
import cc.slack.utils.render.ColorUtil;
import cc.slack.utils.render.RenderUtil;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;

import java.awt.*;

public class Checkbox extends Component {

    private boolean hovered;
    private final BooleanValue op;
    private Button parent;
    private int offset;
    private int x;
    private int y;
    private double alpha = 0.0;

    public Checkbox(BooleanValue option, Button button, int offset) {
        this.op = option;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
        this.alpha = 0.0;
    }

    @Override
    public void renderComponent() {
        offset = parent.ryo;
        parent.ryo += getHeight();
        Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 12, this.hovered ? 0xFF222222 : 0xFF111111);
        Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, 0xFF111111);
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.getFontRenderer().drawStringWithShadow(this.op.getName(), (parent.parent.getX() + 10 + 4) * 2 + 5, (parent.parent.getY() + offset + 2) * 2 + 4, -1);
        GL11.glPopMatrix();
        RenderUtil.drawCircle(parent.parent.getX() + 6 + 4, parent.parent.getY() + offset + 6, 3, 0xFF999999);
        Color c = ColorUtil.getColor();
        c = new Color(c.getRed(), c.getGreen(), c.getBlue(), (int) (alpha * 255));
        if (this.op.getValue()) {
            alpha += (1 - alpha) / 13;
        } else {
            alpha += (0 - alpha) / 13;
        }

        RenderUtil.drawCircle(parent.parent.getX() + 6 + 4, parent.parent.getY() + offset + 6, 2, c.getRGB());

    }

    @Override
    public void setOff(int newOff) {
        offset = newOff;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = isMouseOnButton(mouseX, mouseY);
        this.y = parent.parent.getY() + offset;
        this.x = parent.parent.getX();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.op.setValue(!op.getValue());
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        if (x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12) {
            return true;
        }
        return false;
    }

    @Override
    public int getHeight() {
        return 12;
    }
}
