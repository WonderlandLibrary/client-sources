package wwe;

import net.minecraft.util.text.*;
import net.minecraft.client.gui.*;
import java.util.*;
import wwe.utils.*;
import java.io.*;

public class HCFm extends GuiScreen
{
    public bdJV QpRj;
    public GuiTextField CVbk;
    
    public HCFm(final bdJV qpRj) {
        super();
        this.QpRj = qpRj;
    }
    
    public void func_73866_w_() {
        (this.CVbk = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, this.height / 2 - 10 - this.height / 4, 200, 20)).setMaxStringLength(Integer.MAX_VALUE);
        this.CVbk.setText(this.QpRj.STEn.Plwx);
        final List buttonList = this.buttonList;
        final int n = 1;
        final int n2 = this.width / 2 - 100;
        final int n3 = this.height / 4 + this.height / 2 - 50;
        final StringBuilder append = new StringBuilder().append("Use default value ");
        String s;
        if (this.QpRj.STEn.wgqj) {
            s = TextFormatting.RED + "true";
            if (HCFm.\u200a\u2008\u200a\u2006\u2007\u2000\u200b\u2000\u2007) {
                throw null;
            }
        }
        else {
            s = TextFormatting.GREEN + "false";
        }
        buttonList.add(new GuiButton(n, n2, n3, append.append(s).toString()));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + this.height / 2 - 25, "Done"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + this.height / 2, "Cancel"));
        super.initGui();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawString(this.fontRenderer, "Name: " + this.QpRj.STEn.NKEQ, this.width / 2 - 100, this.height / 2 - 10 - this.height / 4 - 10, 10526880);
        this.CVbk.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
    
    public void func_146284_a(final GuiButton guiButton) {
        if (guiButton.id == 1) {
            final FZwI.wwe/bdJV stEn = this.QpRj.STEn;
            boolean wgqj;
            if (!this.QpRj.STEn.wgqj) {
                wgqj = true;
                if (HCFm.\u200a\u2008\u200a\u2006\u2007\u2000\u200b\u2000\u2007) {
                    throw null;
                }
            }
            else {
                wgqj = false;
            }
            stEn.wgqj = wgqj;
            this.buttonList.clear();
            this.initGui();
        }
        if (guiButton.id == 2) {
            this.QpRj.STEn.Plwx = this.CVbk.getText();
            Wrapper.mc.displayGuiScreen((GuiScreen)new gUfK());
            Wrapper.ypJK().iDTw();
        }
        if (guiButton.id == 3) {
            Wrapper.mc.displayGuiScreen((GuiScreen)new gUfK());
        }
    }
    
    public void func_73876_c() {
        if (this.QpRj.STEn.wgqj) {
            this.CVbk.setEnabled(false);
            if (HCFm.\u200a\u2008\u200a\u2006\u2007\u2000\u200b\u2000\u2007) {
                throw null;
            }
        }
        else {
            this.CVbk.setEnabled(true);
        }
        this.CVbk.updateCursorCounter();
        super.updateScreen();
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        this.CVbk.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    protected void func_73869_a(final char c, final int n) {
        this.CVbk.textboxKeyTyped(c, n);
        if (n == 1) {
            Wrapper.mc.displayGuiScreen((GuiScreen)new gUfK());
        }
    }
}
