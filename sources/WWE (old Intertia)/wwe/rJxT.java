package wwe;

import net.minecraft.client.gui.*;
import java.util.*;
import wwe.utils.*;
import java.io.*;

public class rJxT extends GuiScreen
{
    public rJxT() {
        super();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        super.drawScreen(n, n2, n3);
    }
    
    public void func_73866_w_() {
        int n = 0;
        int n2 = 0;
        for (final RCMD rcmd : vlwv.fomn.JNkz) {
            if (rcmd.VhuH != DlwW.UyOo) {
                if (rJxT.\u2004\u2005) {
                    throw null;
                }
                continue;
            }
            else {
                final List buttonList = this.buttonList;
                final int size = this.buttonList.size();
                int n3;
                if (n2 != 0) {
                    n3 = this.width / 2 + 2;
                    if (rJxT.\u2004\u2005) {
                        throw null;
                    }
                }
                else {
                    n3 = this.width / 2 - 100;
                }
                buttonList.add(new GuiButton(size, n3, this.height / 4 + n, 98, 20, rcmd.cpTz));
                if (n2 != 0) {
                    n += 25;
                }
                final int n4 = 1;
                if (rJxT.\u2004\u2005) {
                    throw null;
                }
                n2 = n4;
                if (rJxT.\u2004\u2005) {
                    throw null;
                }
                continue;
            }
        }
        if (n2 != 0) {
            n += 25;
        }
        this.buttonList.add(new GuiButton(this.buttonList.size(), this.width / 2 - 100, this.height / 4 + n, "TPS Menu"));
        n += 25;
        this.buttonList.add(new GuiButton(99, this.width / 2 - 100, this.height / 4 + n, "Back"));
        this.updateScreen();
    }
    
    protected void func_146284_a(final GuiButton guiButton) throws IOException {
        int n = 0;
        for (final RCMD rcmd : vlwv.fomn.JNkz) {
            if (rcmd.VhuH != DlwW.UyOo) {
                if (rJxT.\u2004\u2005) {
                    throw null;
                }
                continue;
            }
            else {
                if (guiButton.id == n) {
                    this.mc.displayGuiScreen((GuiScreen)new oBqx(rcmd));
                    return;
                }
                ++n;
                if (rJxT.\u2004\u2005) {
                    throw null;
                }
                continue;
            }
        }
        if (guiButton.id == n) {
            this.mc.displayGuiScreen((GuiScreen)new fdhW());
        }
        if (guiButton.id == 99) {
            Wrapper.mc.displayGuiScreen((GuiScreen)new rHwk());
        }
    }
}
