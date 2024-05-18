package net.minecraft.client.gui;

import org.lwjgl.input.*;
import net.minecraft.client.*;
import net.minecraft.realms.*;

public class GuiClickableScrolledSelectionListProxy extends GuiSlot
{
    private final RealmsClickableScrolledSelectionList field_178046_u;
    
    @Override
    protected int getContentHeight() {
        return this.field_178046_u.getMaxPosition();
    }
    
    public int func_178042_f() {
        return super.mouseY;
    }
    
    @Override
    protected int getScrollBarX() {
        return this.field_178046_u.getScrollbarPosition();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (!true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void drawBackground() {
        this.field_178046_u.renderBackground();
    }
    
    @Override
    protected boolean isSelected(final int n) {
        return this.field_178046_u.isSelectedItem(n);
    }
    
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        if (this.scrollMultiplier > 0.0f && Mouse.getEventButtonState()) {
            this.field_178046_u.customMouseEvent(this.top, this.bottom, this.headerPadding, this.amountScrolled, this.slotHeight);
        }
    }
    
    public int func_178045_g() {
        return super.mouseX;
    }
    
    @Override
    protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
        this.field_178046_u.selectItem(n, b, n2, n3);
    }
    
    public GuiClickableScrolledSelectionListProxy(final RealmsClickableScrolledSelectionList field_178046_u, final int n, final int n2, final int n3, final int n4, final int n5) {
        super(Minecraft.getMinecraft(), n, n2, n3, n4, n5);
        this.field_178046_u = field_178046_u;
    }
    
    @Override
    protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.field_178046_u.renderItem(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    protected void drawSelectionBox(final int n, final int n2, final int n3, final int n4) {
        final int size = this.getSize();
        int i = "".length();
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (i < size) {
            final int n5 = n2 + i * this.slotHeight + this.headerPadding;
            final int n6 = this.slotHeight - (0xA7 ^ 0xA3);
            if (n5 > this.bottom || n5 + n6 < this.top) {
                this.func_178040_a(i, n, n5);
            }
            if (this.showSelectionBox && this.isSelected(i)) {
                this.func_178043_a(this.width, n5, n6, Tezzelator.instance);
            }
            this.drawSlot(i, n, n5, n6, n3, n4);
            ++i;
        }
    }
    
    @Override
    protected int getSize() {
        return this.field_178046_u.getItemCount();
    }
    
    public int func_178044_e() {
        return super.width;
    }
    
    public void func_178043_a(final int n, final int n2, final int n3, final Tezzelator tezzelator) {
        this.field_178046_u.renderSelected(n, n2, n3, tezzelator);
    }
}
