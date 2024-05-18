package wwe;

import net.minecraft.client.gui.*;
import wwe.utils.*;
import java.io.*;

public class MkSj extends GuiScreen
{
    public GuiScreen GzOH;
    
    public MkSj(final GuiScreen gzOH) {
        super();
        this.GzOH = gzOH;
    }
    
    public void func_73866_w_() {
        this.addButton(new GuiButton(2, this.width / 2 - 100, this.height - 100, "Connect"));
        this.addButton(new GuiButton(3, this.width / 2 - 100, this.height - 75, "Back"));
        super.initGui();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        final String string = "Reason: " + vlwv.kXkt.svnA;
        Wrapper.fontRenderer.drawString("Not connected to the WWE server", (float)(this.width / 2 - Wrapper.fontRenderer.getStringWidth("Not connected to the WWE server") / 2), 50.0f, -1, true);
        if (vlwv.kXkt.svnA != null && !vlwv.kXkt.svnA.equalsIgnoreCase("None")) {
            Wrapper.fontRenderer.drawString(string, (float)(this.width / 2 - Wrapper.fontRenderer.getStringWidth(string) / 2), 60.0f, -1, true);
        }
        super.drawScreen(n, n2, n3);
    }
    
    public void func_146281_b() {
        super.onGuiClosed();
    }
    
    public void func_73876_c() {
        if (vlwv.kXkt.svnA != null && vlwv.kXkt.svnA.equalsIgnoreCase("None")) {
            this.mc.displayGuiScreen((GuiScreen)new Wwox(this.GzOH));
            return;
        }
        super.updateScreen();
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
    }
    
    protected void func_73869_a(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
    }
    
    protected void func_146284_a(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 2) {
            vlwv.kXkt.AByw();
        }
        if (guiButton.id == 3) {
            this.mc.displayGuiScreen(this.GzOH);
        }
    }
}
