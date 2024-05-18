package wwe;

import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import java.io.*;
import wwe.utils.*;

public class weQP extends GuiScreen
{
    private GuiScreen BWUL;
    public GuiTextField QpcW;
    public PQVr fMxz;
    
    public weQP(final GuiScreen bwul) {
        super();
        this.BWUL = bwul;
    }
    
    public void func_73866_w_() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96 + 12, "Add"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 96 + 36, "Back"));
        this.QpcW = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, 76, 200, 20);
        this.fMxz = new PQVr(2, this.fontRenderer, this.width / 2 - 100, 116, 200, 20);
        this.QpcW.setMaxStringLength(200);
        this.fMxz.cRhr(128);
    }
    
    public void func_146281_b() {
        Keyboard.enableRepeatEvents(false);
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawString(this.fontRenderer, "Username", this.width / 2 - 100, 63, 10526880);
        this.drawString(this.fontRenderer, "Password", this.width / 2 - 100, 104, 10526880);
        try {
            this.QpcW.drawTextBox();
            this.fMxz.zJhX();
            if (weQP.\u2001\u200f) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        super.drawScreen(n, n2, n3);
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        this.QpcW.mouseClicked(n, n2, n3);
        this.fMxz.iqZX(n, n2, n3);
        try {
            super.mouseClicked(n, n2, n3);
            if (weQP.\u2001\u200f) {
                throw null;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    protected void func_73869_a(final char c, final int n) throws IOException {
        this.QpcW.textboxKeyTyped(c, n);
        this.fMxz.SifW(c, n);
        if (c == '\t') {
            if (this.QpcW.isFocused()) {
                this.QpcW.setFocused(false);
                this.fMxz.iCKJ(true);
                if (weQP.\u2001\u200f) {
                    throw null;
                }
            }
            else {
                this.QpcW.setFocused(true);
                this.fMxz.iCKJ(false);
            }
        }
        if (c == '\r') {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }
    
    protected void func_146284_a(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                if (!this.QpcW.getText().trim().isEmpty()) {
                    if (this.fMxz.pBDN().trim().isEmpty()) {
                        final aRkr aRkr = new aRkr(this.QpcW.getText().trim());
                        if (!wGez.VkYf.contains(aRkr)) {
                            wGez.VkYf.add(aRkr);
                        }
                        if (weQP.\u2001\u200f) {
                            throw null;
                        }
                    }
                    else {
                        final aRkr aRkr2 = new aRkr(this.QpcW.getText().trim(), this.fMxz.pBDN().trim());
                        if (!wGez.VkYf.contains(aRkr2)) {
                            wGez.VkYf.add(aRkr2);
                        }
                    }
                }
                Wrapper.ypJK().dheG();
                Wrapper.mc.displayGuiScreen(this.BWUL);
                if (weQP.\u2001\u200f) {
                    throw null;
                }
            }
            else if (guiButton.id == 2) {
                Wrapper.mc.displayGuiScreen(this.BWUL);
            }
        }
    }
    
    public void func_73876_c() {
        this.QpcW.updateCursorCounter();
        this.fMxz.DHVQ();
    }
}
