package studio.dreamys.clickgui.component.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import studio.dreamys.clickgui.ClickGUI;
import studio.dreamys.clickgui.component.Component;
import studio.dreamys.clickgui.component.Frame;
import studio.dreamys.clickgui.component.components.sub.Checkbox;
import studio.dreamys.clickgui.component.components.sub.Keybind;
import studio.dreamys.clickgui.component.components.sub.ModeButton;
import studio.dreamys.clickgui.component.components.sub.Slider;
import studio.dreamys.module.Module;
import studio.dreamys.near;
import studio.dreamys.setting.Setting;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Button extends Component {
    private final ArrayList<Component> subcomponents;
    public final Module mod;
    public final Frame parent;
    public int offset;
    public boolean open;
    private boolean isHovered;

    public Button(Module mod, Frame parent, int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        subcomponents = new ArrayList<>();
        open = false;
        int opY = offset + 12;
        if (near.settingsManager.getSettingsByMod(mod) != null) {
            for (Setting s : near.settingsManager.getSettingsByMod(mod)) {
                if (s.isCombo()) {
                    subcomponents.add(new ModeButton(s, this, opY));
                    opY += 12;
                }
                if (s.isSlider()) {
                    subcomponents.add(new Slider(s, this, opY));
                    opY += 12;
                }
                if (s.isCheck()) {
                    subcomponents.add(new Checkbox(s, this, opY));
                    opY += 12;
                }
            }
        }
        subcomponents.add(new Keybind(this, opY));
    }

    @Override
    public void setOff(int newOff) {
        offset = newOff;
        int opY = offset + 12;
        for (Component comp : subcomponents) {
            comp.setOff(opY);
            opY += 12;
        }
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(parent.getX(), parent.getY() + offset, parent.getX() + parent.getWidth(), parent.getY() + 12 + offset, isHovered ? (mod.isToggled() ? new Color(0xFF222222).getRGB() : new Color(0x2D2D2D).darker().getRGB()) : (mod.isToggled() ? new Color(0xFF111111).getRGB() : new Color(14, 14, 14).getRGB()));
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(mod.getName(), (parent.getX() + 2) * 2, (parent.getY() + offset + 2) * 2 + 4, !mod.isToggled() ? 0x999999 : -1);
        if (subcomponents.size() > 1) Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(open ? "-" : "+", (parent.getX() + parent.getWidth() - 10) * 2, (parent.getY() + offset + 2) * 2 + 4, -1);
        GL11.glPopMatrix();
        if (open) {
            if (!subcomponents.isEmpty()) {
                for (Component comp : subcomponents) {
                    comp.renderComponent();
                }
                Gui.drawRect(parent.getX() + 2, parent.getY() + offset + 12, parent.getX() + 3, parent.getY() + offset + ((subcomponents.size() + 1) * 12), ClickGUI.color);
            }
        }
    }

    @Override
    public int getHeight() {
        if (open) {
            return (12 * (subcomponents.size() + 1));
        }
        return 12;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) throws IOException {
        isHovered = isMouseOnButton(mouseX, mouseY);
        if (!subcomponents.isEmpty()) {
            for (Component comp : subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if (isMouseOnButton(mouseX, mouseY) && button == 0) {
            mod.toggle();
        }
        if (isMouseOnButton(mouseX, mouseY) && button == 1) {
            open = !open;
            parent.refresh();
        }
        for (Component comp : subcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Component comp : subcomponents) {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char typedChar, int key) throws IOException {
        for (Component comp : subcomponents) {
            comp.keyTyped(typedChar, key);
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > parent.getX() && x < parent.getX() + parent.getWidth() && y > parent.getY() + offset && y < parent.getY() + 12 + offset;
    }
}
