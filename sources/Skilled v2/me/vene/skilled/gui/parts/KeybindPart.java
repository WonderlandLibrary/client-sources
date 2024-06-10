 package me.vene.skilled.gui.parts;

 import java.awt.Color;
 import me.vene.skilled.gui.component.Component;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.modules.mods.main.CombatGUI;
 import me.vene.skilled.modules.mods.main.OtherGUI;
 import me.vene.skilled.modules.mods.main.PlayerGUI;
 import me.vene.skilled.modules.mods.main.RenderGUI;
 import me.vene.skilled.modules.mods.main.UtilityGUI;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.gui.Gui;
 import org.lwjgl.input.Keyboard;
 import org.lwjgl.opengl.GL11;

public class KeybindPart extends Component
{
    private boolean binding;
    private ModulesPart parent;
    private int offset;
    private int x;
    private int y;
    
    public KeybindPart(final ModulesPart button, final int offset) {
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }
    
    @Override
    public void render() {
        Gui.drawRect(this.parent.parent.getX() + 1, this.parent.parent.getY() - 2 + this.offset, this.parent.parent.getX() - 2 + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 12, new Color(35, 35, 35, 230).getRGB());
        GL11.glPushMatrix();
        Minecraft.getMinecraft().fontRendererObj.drawString(this.binding ? "Press a key..." : ("Bind: " + Keyboard.getKeyName(this.parent.mod.getKey())), this.parent.parent.getX() + this.parent.parent.getWidth() / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.binding ? "Press a key..." : ("Bind: " + Keyboard.getKeyName(this.parent.mod.getKey()))) / 2, this.parent.parent.getY() + this.offset + 1, -1);
        GL11.glPopMatrix();
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.binding = !this.binding;
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int key) {
        if (this.binding && this.parent.mod != Module.getModule(CombatGUI.class) && this.parent.mod != Module.getModule(OtherGUI.class) && this.parent.mod != Module.getModule(PlayerGUI.class) && this.parent.mod != Module.getModule(RenderGUI.class) && this.parent.mod != Module.getModule(UtilityGUI.class)) {
            if (key == 14) {
                this.parent.mod.setKey(0);
                this.binding = false;
                return;
            }
            this.parent.mod.setKey(key);
            this.binding = false;
        }
        if (key == 42) {
            this.parent.mod.setKey(0);
            this.binding = false;
        }
    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }
    
    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.x && x < this.x + this.parent.parent.getWidth() && y > this.y && y < this.y + 12;
    }
}
