package wwe;

import net.minecraft.client.gui.*;
import wwe.utils.*;
import java.io.*;

public class jBNh extends GuiScreen
{
    public GuiScreen pFJJ;
    public GuiTextField ySWH;
    
    public jBNh(final GuiScreen pfjj) {
        super();
        this.pFJJ = pfjj;
    }
    
    public void func_73866_w_() {
        (this.ySWH = new GuiTextField(1, this.fontRenderer, this.width / 2 - 100, this.height / 2, 200, 20)).setMaxStringLength(16);
        this.addButton(new GuiButton(1, this.width / 2 + 105, this.height / 2, 50, 20, "Send"));
        this.addButton(new GuiButton(3, this.width - 55, this.height - 25, 50, 20, "Back"));
        hSNE.lyvR(this);
        super.initGui();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Wrapper.fontRenderer.drawStringWithShadow("Welcome, " + Wrapper.mc.getSession().getUsername(), (float)(this.width / 12), (float)(this.height / 12), -1);
        Wrapper.fontRenderer.drawStringWithShadow("Send friend request to?", (float)(this.width / 2 - Wrapper.fontRenderer.getStringWidth("Send friend request to?") / 2), (float)(this.height / 4 + 12), -1);
        this.ySWH.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
    
    public void func_146281_b() {
        hSNE.kduK(this);
    }
    
    public void func_73876_c() {
        this.ySWH.updateCursorCounter();
        super.updateScreen();
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        this.ySWH.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    protected void func_73869_a(final char c, final int n) {
        this.ySWH.textboxKeyTyped(c, n);
        if (this.ySWH.isFocused() && !this.ySWH.getText().isEmpty() && n == 28) {
            vlwv.kXkt.DKWU(new yueR(this.ySWH.getText()));
        }
    }
    
    protected void func_146284_a(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 1 && !this.ySWH.getText().isEmpty()) {
            vlwv.kXkt.DKWU(new yueR(this.ySWH.getText()));
        }
        if (guiButton.id == 3) {
            Wrapper.mc.displayGuiScreen(this.pFJJ);
        }
    }
}
