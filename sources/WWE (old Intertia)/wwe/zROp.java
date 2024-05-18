package wwe;

import net.minecraft.client.gui.*;
import wwe.utils.*;
import java.io.*;

public class zROp extends GuiScreen
{
    public GuiScreen AlRz;
    public Kdwt iBKN;
    
    public zROp(final GuiScreen alRz) {
        super();
        this.AlRz = alRz;
    }
    
    public void func_73866_w_() {
        this.addButton(new GuiButton(0, this.width / 2 - 40 - 85, this.height / 2, 80, 20, "Offline"));
        this.addButton(new GuiButton(1, this.width / 2 - 40, this.height / 2, 80, 20, "Do not disturb"));
        this.addButton(new GuiButton(2, this.width / 2 - 40 + 85, this.height / 2, 80, 20, "Online"));
        this.addButton(new GuiButton(3, this.width - 55, this.height - 25, 50, 20, "Back"));
        cJGk cjGk = null;
        int i = 0;
        while (i < vlwv.kXkt.SoAu.size()) {
            final cJGk cjGk2 = vlwv.kXkt.SoAu.get(i);
            if (cjGk2.URwX.equals(Wrapper.mc.getSession().getUsername())) {
                cjGk = cjGk2;
                if (zROp.\u200c\u2006\u200b\u200d\u200c\u2003\u200d\u2003\u2005) {
                    throw null;
                }
                break;
            }
            else {
                ++i;
                if (zROp.\u200c\u2006\u200b\u200d\u200c\u2003\u200d\u2003\u2005) {
                    throw null;
                }
                continue;
            }
        }
        if (cjGk != null) {
            this.iBKN = new Kdwt(cjGk, this, this.width / 2 - (35 + Wrapper.fontRenderer.getStringWidth("O") * 16 + 3 - 1) / 2, this.height / 2 - 50);
        }
        super.initGui();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Wrapper.fontRenderer.drawStringWithShadow("Welcome, " + Wrapper.mc.getSession().getUsername(), (float)(this.width / 12), (float)(this.height / 12), -1);
        Wrapper.fontRenderer.drawStringWithShadow("Status", (float)(this.width / 2 - Wrapper.fontRenderer.getStringWidth("Status") / 2), (float)(this.height / 2 - 70), -1);
        if (this.iBKN != null) {
            this.iBKN.FDgX(n, n2, n3);
        }
        super.drawScreen(n, n2, n3);
    }
    
    public void func_146281_b() {
        hSNE.kduK(this);
    }
    
    public void func_73876_c() {
        super.updateScreen();
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
    }
    
    protected void func_73869_a(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
    }
    
    protected void func_146284_a(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            vlwv.kXkt.DKWU(new Coyk(0));
        }
        if (guiButton.id == 1) {
            vlwv.kXkt.DKWU(new Coyk(1));
        }
        if (guiButton.id == 2) {
            vlwv.kXkt.DKWU(new Coyk(2));
        }
        if (guiButton.id == 3) {
            Wrapper.mc.displayGuiScreen(this.AlRz);
        }
    }
}
