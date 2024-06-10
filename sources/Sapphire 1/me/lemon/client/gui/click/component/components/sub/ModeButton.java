package me.lemon.client.gui.click.component.components.sub;

import ac.sapphire.client.module.AbstractModule;
import ac.sapphire.client.module.AbstractModuleMode;
import me.lemon.client.gui.click.component.Component;
import me.lemon.client.gui.click.component.components.Button;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class ModeButton extends Component {

    private final Button parent;
    private final AbstractModule mod;
    private boolean hovered;
    private int offset;
    private int x;
    private int y;

    public ModeButton(Button button, AbstractModule mod, int offset) {
        this.parent = button;
        this.mod = mod;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }

    @Override
    public void setOff(int newOff) {
        offset = newOff;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth() * 1), parent.parent.getY() + offset + 12, this.hovered ? 0xFF222222 : 0xFF111111);
        Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, 0xFF111111);
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Mode: " + mod.getCurrentMode().getDisplayName(), (parent.parent.getX() + 7) * 2, (parent.parent.getY() + offset + 2) * 2 + 5, -1);
        GL11.glPopMatrix();
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
            if (mod.getModes().size() != 1) {
                AbstractModuleMode<?> currMode = mod.getCurrentMode();
                assert currMode != null;

                int indexOfCurrMode = 0;
                for (int i = 0; i < mod.getModes().size(); i++) {
                    AbstractModuleMode<?> mode = mod.getModes().get(i);
                    if (mode.getName().equals(currMode.getName())) {
                        indexOfCurrMode = i;
                        break;
                    }
                }

                int indexOfNextMode = indexOfCurrMode;
                if(indexOfNextMode + 1 >= mod.getModes().size()) {
                    indexOfNextMode = 0;
                } else {
                    indexOfNextMode++;
                }

                mod.setCurrentMode(mod.getModes().get(indexOfNextMode).getName());
                parent.parent.onModeChange();
            }
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12;
    }
}
