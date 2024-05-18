package pw.latematt.xiv.ui.managers.alt;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;

public class AltSlot extends GuiSlot {
    private GuiAltManager screen;
    private int selected;

    public AltSlot(GuiAltManager screen, Minecraft mcIn, int width, int height, int p_i1052_4_, int p_i1052_5_, int p_i1052_6_) {
        super(mcIn, width, height, p_i1052_4_, p_i1052_5_, p_i1052_6_);

        this.screen = screen;
    }

    @Override
    protected int getSize() {
        return screen.getAccounts().size();
    }

    @Override
    public int getSlotHeight() {
        return super.getSlotHeight();
    }

    @Override
    protected void elementClicked(int slot, boolean var2, int var3, int var4) {
        this.selected = slot;
        this.screen.keyword.setText(getAlt().getKeyword());

        if (var2) {
            screen.login(getAlt());
        }
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

    public AltAccount getAlt() {
        return getAlt(getSelected());
    }

    @Override
    protected void drawBackground() {

    }

    @Override
    protected void drawSlot(int slot, int x, int y, int var4, int var5, int var6) {
        AltAccount alt = getAlt(slot);

        if (alt != null) {
            mc.fontRendererObj.drawStringWithShadow(alt.getUsername(), x + 1, y + 2, 0xFFFFFFFF);
            mc.fontRendererObj.drawStringWithShadow(alt.getPassword().replaceAll("(?s).", "*"), x + 1, y + 12, 0xFF888888);
            mc.fontRendererObj.drawStringWithShadow((alt.getKeyword().isEmpty() ? "\2477No keyword set" : alt.getKeyword()), x + 1, y + 22, (alt.getKeyword().isEmpty() ? 0xFFFFFFFF : 0xFFFFFF00));
        }
    }

    public AltAccount getAlt(int slot) {
        int count = 0;

        for (AltAccount alt : screen.getAccounts()) {
            if (count == slot) {
                return alt;
            }
            count++;
        }

        return null;
    }

    public void setBounds(int width, int height, int top, int bottom, int right) {
        this.width = width;
        this.height = height;
        this.top = top;
        this.bottom = bottom;
        this.right = right;
    }
}
