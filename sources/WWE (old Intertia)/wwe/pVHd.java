package wwe;

import net.minecraft.client.gui.*;
import wwe.utils.*;
import java.io.*;

public class pVHd extends GuiScreen
{
    public GuiScreen udsY;
    public String myIP;
    public GuiTextField WGQA;
    
    public pVHd(final GuiScreen udsY) {
        super();
        this.udsY = udsY;
    }
    
    public void func_73866_w_() {
        (this.WGQA = new GuiTextField(1, this.fontRenderer, this.width / 2 - 100, this.height / 2, 200, 20)).setMaxStringLength(16);
        hSNE.kduK(this);
        hSNE.lyvR(this);
        this.addButton(new GuiButton(1, this.width / 2 + 105, this.height / 2, 50, 20, "Send"));
        this.addButton(new GuiButton(3, this.width / 2 - 100, this.height - 50, "Back"));
        super.initGui();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Wrapper.fontRenderer.drawStringWithShadow("Welcome, " + Wrapper.mc.getSession().getUsername(), (float)(this.width / 12), (float)(this.height / 12), -1);
        Wrapper.fontRenderer.drawStringWithShadow("Send tic tac toe request to?", (float)(this.width / 2 - Wrapper.fontRenderer.getStringWidth("Send tic tac toe request to?") / 2), (float)(this.height / 4 + 12), -1);
        if (this.myIP != null) {
            Wrapper.fontRenderer.drawString(this.myIP, this.width / 2 - Wrapper.fontRenderer.getStringWidth(this.myIP) / 2, 50, -1);
        }
        this.WGQA.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
    
    public void func_146281_b() {
        hSNE.kduK(this);
        super.onGuiClosed();
    }
    
    public void func_73876_c() {
        this.WGQA.updateCursorCounter();
        super.updateScreen();
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        this.WGQA.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    protected void func_73869_a(final char c, final int n) throws IOException {
        this.WGQA.textboxKeyTyped(c, n);
        if (this.WGQA.isFocused() && !this.WGQA.getText().isEmpty() && n == 28) {
            this.myIP = null;
            vlwv.kXkt.DKWU(new yjZR(this.WGQA.getText()));
        }
        super.keyTyped(c, n);
    }
    
    protected void func_146284_a(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 1 && !this.WGQA.getText().isEmpty()) {
            this.myIP = null;
            vlwv.kXkt.DKWU(new yjZR(this.WGQA.getText()));
        }
        if (guiButton.id == 3) {
            Wrapper.mc.displayGuiScreen(this.udsY);
        }
    }
    
    @oqrr
    public void TyOe(final mIcn.wwe/fYTJ wwe/fYTJ) {
        if (wwe/fYTJ.vCOH != 2) {
            return;
        }
        this.myIP = wwe/fYTJ.eaSc;
    }
}
