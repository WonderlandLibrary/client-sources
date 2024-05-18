package pw.latematt.xiv.ui.managers.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import pw.latematt.xiv.mod.Mod;

public class ModSlot extends GuiSlot {
    private GuiModManager screen;
    private int selected;

    public ModSlot(GuiModManager screen, Minecraft mcIn, int width, int height, int p_i1052_4_, int p_i1052_5_, int p_i1052_6_) {
        super(mcIn, width, height, p_i1052_4_, p_i1052_5_, p_i1052_6_);

        this.screen = screen;
    }

    @Override
    protected int getSize() {
        return screen.getMods().size();
    }

    @Override
    public int getSlotHeight() {
        return super.getSlotHeight();
    }

    @Override
    protected void elementClicked(int slot, boolean var2, int var3, int var4) {
        this.selected = slot;
    }

    @Override
    protected boolean isSelected(int slot) {
        return selected == slot;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int slot) {
        this.selected = slot;
    }

    public Mod getMod() {
        return getMod(getSelected());
    }

    @Override
    protected void drawBackground() {

    }

    @Override
    protected void drawSlot(int slot, int x, int y, int var4, int var5, int var6) {
        Mod mod = getMod(slot);

        if (mod != null) {
            mc.fontRendererObj.drawStringWithShadow(mod.getName(), x + 1, y + 1, mod.getColor());
            GL11.glPushMatrix();
            GL11.glScaled(0.5, 0.5, 0.5);
            mc.fontRendererObj.drawStringWithShadow("State: " + mod.isEnabled(), (x + 1) * 2, (y + 12) * 2, 0xFFFFFFFF);
            mc.fontRendererObj.drawStringWithShadow("Key: " + Keyboard.getKeyName(mod.getKeybind()), (x + 1) * 2, (y + 18) * 2, 0xFFFFFFFF);
            mc.fontRendererObj.drawStringWithShadow("Visible: " + mod.isVisible(), (x + 1) * 2, (y + 24) * 2, 0xFFFFFFFF);
            GL11.glScaled(2, 2, 2);
            GL11.glPopMatrix();
        }
    }

    public Mod getMod(int slot) {
        int count = 0;

        for (Mod mod : screen.getMods()) {
            if (count == slot) {
                return mod;
            }
            count++;
        }

        return null;
    }
}
