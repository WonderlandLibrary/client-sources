package wwe;

import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import java.io.*;
import wwe.utils.*;

public class IhAb extends GuiScreen
{
    public GuiTextField qDyl;
    
    public IhAb() {
        super();
    }
    
    public void func_73866_w_() {
        Keyboard.enableRepeatEvents(true);
        (this.qDyl = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, this.height / 2 - 10 - this.height / 4, 200, 20)).setMaxStringLength(30);
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + this.height / 2 - 25, "Done"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + this.height / 2, "Cancel"));
    }
    
    public void func_146281_b() {
        Keyboard.enableRepeatEvents(false);
    }
    
    public void func_73876_c() {
        this.qDyl.updateCursorCounter();
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        this.qDyl.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    public void func_146284_a(final GuiButton guiButton) {
        if (guiButton.id == 1) {
            String text;
            if (this.qDyl.getText().isEmpty()) {
                text = "no name";
                if (IhAb.\u2006\u2001\u200f\u200f) {
                    throw null;
                }
            }
            else {
                text = this.qDyl.getText();
            }
            final hfst hfst = new hfst(text, vlwv.hNxW.ijZl);
            final hEGx prNz = vlwv.pRNz;
            hEGx.aTlJ.QWLO(hfst, true);
            Wrapper.mc.displayGuiScreen((GuiScreen)vlwv.pRNz);
        }
        if (guiButton.id == 2) {
            Wrapper.mc.displayGuiScreen((GuiScreen)vlwv.pRNz);
        }
    }
    
    protected void func_73869_a(final char c, final int n) {
        this.qDyl.textboxKeyTyped(c, n);
        if (n == 1) {
            this.mc.displayGuiScreen((GuiScreen)vlwv.pRNz);
        }
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawString(this.fontRenderer, "Name", this.width / 2 - 100, this.height / 2 - 10 - this.height / 4 - 10, 10526880);
        this.qDyl.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
}
