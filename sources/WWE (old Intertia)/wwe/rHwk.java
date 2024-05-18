package wwe;

import wwe.utils.*;
import net.minecraft.client.gui.*;
import java.io.*;

public class rHwk extends GuiScreen
{
    public rHwk() {
        super();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        super.drawScreen(n, n2, n3);
    }
    
    public void func_73866_w_() {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4, "Font Colors"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 25, "TabGui Colors"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 50, "Module Element Colors"));
        this.buttonList.add(new GuiButton(10, this.width / 2 - 100, this.height / 4 + 75, "Back"));
        this.updateScreen();
    }
    
    protected void func_146284_a(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 1) {
            Wrapper.mc.displayGuiScreen((GuiScreen)new rJxT());
        }
        if (guiButton.id == 2) {
            Wrapper.mc.displayGuiScreen((GuiScreen)new uIsz());
        }
        if (guiButton.id == 3) {
            Wrapper.mc.displayGuiScreen((GuiScreen)new Knov());
        }
        if (guiButton.id == 10) {
            if (Wrapper.mc.world != null) {
                Wrapper.mc.displayGuiScreen((GuiScreen)new GuiIngameMenu());
                if (rHwk.\u2005) {
                    throw null;
                }
            }
            else {
                Wrapper.mc.displayGuiScreen((GuiScreen)new ssGO());
            }
        }
    }
}
