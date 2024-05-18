package wwe;

import wwe.modules.render.*;
import java.io.*;
import net.minecraft.client.gui.*;
import wwe.utils.*;

public class gUfK extends GuiScreen
{
    public gUfK() {
        super();
    }
    
    public void func_73866_w_() {
        int i = 0;
        while (i < F3Spoofer.HHRM.size()) {
            this.buttonList.add(new bdJV(i, this.width / 2 - this.width / 4 + 5 + i % 3 * 100, this.height / 2 - this.height / 4 + 5 + i / 3 * 12, XybC.wKEb().getStringWidth(F3Spoofer.HHRM.get(i).NKEQ) + 2, 10, F3Spoofer.HHRM.get(i)));
            ++i;
            if (gUfK.\u200b\u200c\u2009\u200a\u2007\u2005\u2008\u2005\u200f) {
                throw null;
            }
        }
        super.initGui();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        gnNa.wQYt((float)(this.width / 2 - this.width / 4), (float)(this.height / 2 - this.height / 4), (float)(this.width / 2 + this.width / 4), (float)(this.height / 2 + this.height / 4), -16777216);
        super.drawScreen(n, n2, n3);
    }
    
    public void func_146281_b() {
        super.onGuiClosed();
    }
    
    public void func_73876_c() {
        super.updateScreen();
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
    }
    
    public void func_146286_b(final int n, final int n2, final int n3) {
        super.mouseReleased(n, n2, n3);
    }
    
    protected void func_73869_a(final char c, final int n) {
        if (n == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    public void func_146284_a(final GuiButton guiButton) throws IOException {
        Wrapper.mc.displayGuiScreen((GuiScreen)new HCFm((bdJV)guiButton));
    }
}
