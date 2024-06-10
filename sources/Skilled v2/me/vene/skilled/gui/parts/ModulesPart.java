package me.vene.skilled.gui.parts;
 
 import java.awt.Color;
 import java.util.ArrayList;
 import me.vene.skilled.gui.component.Component;
 import me.vene.skilled.gui.component.Frame;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.modules.mods.main.CombatGUI;
 import me.vene.skilled.modules.mods.main.OtherGUI;
 import me.vene.skilled.modules.mods.main.PlayerGUI;
 import me.vene.skilled.modules.mods.main.RenderGUI;
 import me.vene.skilled.modules.mods.main.UtilityGUI;
 import me.vene.skilled.modules.mods.other.ClickGUI;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.values.BooleanValue;
 import me.vene.skilled.values.NumberValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.gui.Gui;
 
  public class ModulesPart extends Component
{
    public Module mod;
    public Frame parent;
    public int offset;
    private boolean isHovered;
    private ArrayList<Component> subcomponents;
    public boolean open;
    
    public ModulesPart(final Module mod, final Frame parent, final int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList<Component>();
        this.open = false;
        int opY = offset + 16;
        if (mod != Module.getModule(CombatGUI.class) && mod != Module.getModule(OtherGUI.class) && mod != Module.getModule(PlayerGUI.class) && mod != Module.getModule(RenderGUI.class) && mod != Module.getModule(UtilityGUI.class)) {
            final KeybindPart keybindPart = new KeybindPart(this, opY);
            this.subcomponents.add(keybindPart);
        }
        opY += 4;
        if (!mod.getOptions().isEmpty()) {
            for (final BooleanValue bool : mod.getOptions()) {
                final CheckboxPart check = new CheckboxPart(bool, this, opY);
                this.subcomponents.add(check);
                opY += 12;
            }
        }
        opY += 2;
        if (!mod.getValues().isEmpty()) {
            for (final NumberValue num : mod.getValues()) {
                final SliderPart sliderPart = new SliderPart(num, this, opY);
                this.subcomponents.add(sliderPart);
                opY += 14;
            }
        }
    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
        int opY = this.offset + 14;
        for (final Component comp : this.subcomponents) {
            comp.setOff(opY);
            opY += 14;
        }
    }
    
    @Override
    public void render() {
        Gui.drawRect(this.parent.getX(), this.parent.getY() - 2 + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 12 + this.offset, this.mod.getState() ? ClickGUI.getColor() : ClickGUI.getModuleColor());
        Minecraft.getMinecraft().fontRendererObj.drawString(StringRegistry.register(this.mod.getName()), this.parent.getX() + this.parent.getWidth() / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.mod.getName()) / 2, this.parent.getY() + 1 + this.offset, -1);
        if (!this.mod.getOptions().isEmpty() || !this.mod.getValues().isEmpty()) {
            Minecraft.getMinecraft().fontRendererObj.drawString(this.open ? new String(new char[] { '-' }) : new String(new char[] { '+' }), this.parent.getX() + this.parent.getWidth() - 10, this.parent.getY() + this.offset + 1, this.isHovered ? -1 : new Color(150, 150, 150, 255).getRGB());
        }
        if (this.open && !this.subcomponents.isEmpty()) {
            for (final Component comp : this.subcomponents) {
                comp.render();
            }
        }
    }
    
    @Override
    public int getHeight() {
        if (this.open) {
            return 14 * (this.subcomponents.size() + 1);
        }
        return 14;
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.parent.refresh();
        this.isHovered = this.isMouseOnButton(mouseX, mouseY);
        if (!this.subcomponents.isEmpty()) {
            for (final Component comp : this.subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.mod.setState(!this.mod.getState());
        }
        if (this.isMouseOnButton(mouseX, mouseY) && button == 1 && this.parent.open) {
            this.open = !this.open;
            this.parent.refresh();
        }
        for (final Component comp : this.subcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int key) {
        for (final Component comp : this.subcomponents) {
            comp.keyTyped(typedChar, key);
        }
    }
    
    private boolean isMouseOnButton(final int x, final int y) {
        return x > this.parent.getX() && x < this.parent.getX() + this.parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 14 + this.offset;
    }
}
