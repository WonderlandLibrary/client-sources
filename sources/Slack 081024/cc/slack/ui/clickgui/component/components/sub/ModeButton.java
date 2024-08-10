package cc.slack.ui.clickgui.component.components.sub;

import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.utils.other.TimeUtil;
import cc.slack.utils.render.ColorUtil;
import cc.slack.utils.render.RenderUtil;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.Gui;
import cc.slack.ui.clickgui.component.Component;
import cc.slack.ui.clickgui.component.components.Button;
import cc.slack.features.modules.api.Module;

import java.awt.*;

public class ModeButton extends Component {

    private boolean hovered;
    private final Button parent;
    private final ModeValue set;
    private int offset;
    private int x;
    private int y;
    private final Module mod;
    private boolean open;
    private int mousePosX;
    private int mousePosY;
    private TimeUtil animTimer = new TimeUtil();

    private String hoveredMode;
    
    private int modeIndex;
    

    public ModeButton(ModeValue set, Button button, Module mod, int offset) {
        this.set = set;
        this.parent = button;
        this.mod = mod;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
        this.modeIndex = set.getIndex();
        this.open = false;
    }

    @Override
    public void setOff(int newOff) {
        offset = newOff;
    }

    @Override
    public void renderComponent() {
        offset = parent.ryo;
        parent.ryo += getHeight();
        Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth()), parent.parent.getY() + offset + getHeight(), 0xFF111111);
        Gui.drawRect(parent.parent.getX() + 3, parent.parent.getY() + offset, parent.parent.getX() + 4, parent.parent.getY() + offset + getHeight(), ColorUtil.getColor().darker().getRGB());

        Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth()), parent.parent.getY() + offset + 12, this.hovered ? 0xFF2a2a2a : 0xFF1a1a1a);
        Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, 0xFF111111);
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        String prefix = set.getName() == "Mode: " ? "Mode: " : set.getName() + " Mode: ";
        
        if (this.open) {
            Minecraft.getFontRenderer().drawStringWithShadow(prefix, (parent.parent.getX() + 7) * 2, (parent.parent.getY() + offset + 2) * 2 + 5, -1);
            Minecraft.getFontRenderer().drawStringWithShadow("-", (parent.parent.getX() + parent.parent.getWidth() - 10) * 2, (parent.parent.getY() + offset + 2) * 2 + 5, -1);
        } else {
            Minecraft.getFontRenderer().drawStringWithShadow("+", (parent.parent.getX() + parent.parent.getWidth() - 10) * 2, (parent.parent.getY() + offset + 2) * 2 + 5, -1);
            Minecraft.getFontRenderer().drawStringWithShadow(prefix + set.getValue().toString(), (parent.parent.getX() + 7) * 2, (parent.parent.getY() + offset + 2) * 2 + 5, -1);
        }
        int i = 10;
        this.hoveredMode = null;

        for (Object mode : set.getModes()) {
            if (i > getHeight() - 12) break;
            String mset = mode.toString();
            if (isMouseOnButton(mousePosX, mousePosY - i)) {
                this.hoveredMode = mset;
                Gui.drawRect((parent.parent.getX() + 2) * 2, (parent.parent.getY() + offset + 12 + i - 10) * 2, (parent.parent.getX() + (parent.parent.getWidth())) * 2, (parent.parent.getY() + offset + 12 + i) * 2, 0x2222222);
            }
            if (mset.equalsIgnoreCase(set.getValue().toString())) {
                Gui.drawRect((parent.parent.getX() + 2) * 2, (parent.parent.getY() + offset + 12 + i - 10) * 2, (parent.parent.getX() + (parent.parent.getWidth())) * 2, (parent.parent.getY() + offset + 12 + i) * 2, 0x2727227);
                Minecraft.getFontRenderer().drawStringWithShadow(mset, (parent.parent.getX() + 7 + 2 + 6) * 2, (parent.parent.getY() + offset + 2 + i) * 2 + 5, -1);
            } else {
                Minecraft.getFontRenderer().drawStringWithShadow(mset, (parent.parent.getX() + 7 + 2) * 2, (parent.parent.getY() + offset + 2 + i) * 2 + 5, -1);
            }
            i += 10;
        }
        GL11.glPopMatrix();
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        mousePosX = mouseX;
        mousePosY = mouseY;
        this.hovered = isMouseOnButton(mouseX, mouseY);
        this.y = parent.parent.getY() + offset;
        this.x = parent.parent.getX();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (!this.parent.open) return;

        if (this.hovered) {
            if (button == 0 || button == 1) {
                this.open = !this.open;
                animTimer.reset();
            }
        }

        if (this.open) {
            if (this.hoveredMode != null) {
                if (button == 0 || button == 1) {
                    set.setValueFromString(this.hoveredMode);
                }
            }
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
        return 12 + (int) (this.open ?
                (1 - Math.pow(1 - (Math.min(400, animTimer.elapsed()) / 400.0), 3)) * this.set.getModes().length * 10 :
                Math.pow(1 - (Math.min(400, animTimer.elapsed()) / 400.0), 3) * this.set.getModes().length * 10);
    }
}
