package wwe;

import net.minecraft.client.gui.*;
import java.util.*;
import wwe.utils.*;
import java.io.*;

public class fdhW extends GuiScreen
{
    public fdhW() {
        super();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        super.drawScreen(n, n2, n3);
    }
    
    public void func_73866_w_() {
        int n = 0;
        for (final RCMD rcmd : vlwv.fomn.JNkz) {
            if (rcmd.VhuH != DlwW.CfSS) {
                if (fdhW.\u2007) {
                    throw null;
                }
                continue;
            }
            else {
                this.buttonList.add(new GuiButton(this.buttonList.size(), this.width / 2 - 100, this.height / 4 + n, rcmd.cpTz));
                n += 25;
                if (fdhW.\u2007) {
                    throw null;
                }
                continue;
            }
        }
        this.buttonList.add(new GuiButton(99, this.width / 2 - 100, this.height / 4 + n, "Back"));
        this.updateScreen();
    }
    
    protected void func_146284_a(final GuiButton guiButton) throws IOException {
        int n = 0;
        for (final RCMD rcmd : vlwv.fomn.JNkz) {
            if (rcmd.VhuH != DlwW.CfSS) {
                if (fdhW.\u2007) {
                    throw null;
                }
                continue;
            }
            else if (guiButton.id == n) {
                this.mc.displayGuiScreen((GuiScreen)new oBqx(rcmd));
                if (fdhW.\u2007) {
                    throw null;
                }
                break;
            }
            else {
                ++n;
                if (fdhW.\u2007) {
                    throw null;
                }
                continue;
            }
        }
        if (guiButton.id == 99) {
            Wrapper.mc.displayGuiScreen((GuiScreen)new rJxT());
        }
    }
}
