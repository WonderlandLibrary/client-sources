package wwe;

import wwe.utils.*;
import java.util.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import java.io.*;
import net.minecraft.util.*;

public class BmDl extends GuiScreen
{
    public GuiScreen DVqx;
    public GuiTextField djtH;
    public String LXGm;
    
    public BmDl(final GuiScreen dVqx) {
        super();
        this.LXGm = "";
        this.DVqx = dVqx;
    }
    
    public void func_73866_w_() {
        this.LXGm = "";
        final char[] array = Wrapper.mc.getSession().getUsername().toCharArray();
        final int length = array.length;
        int i = 0;
        while (i < length) {
            final char c = array[i];
            if (Character.isAlphabetic(c)) {
                final StringBuilder append = new StringBuilder().append(this.LXGm);
                char c2;
                if (new Random().nextBoolean()) {
                    c2 = Character.toLowerCase(c);
                    if (BmDl.\u200f\u200b\u2009) {
                        throw null;
                    }
                }
                else {
                    c2 = Character.toUpperCase(c);
                }
                this.LXGm = append.append(c2).toString();
            }
            ++i;
            if (BmDl.\u200f\u200b\u2009) {
                throw null;
            }
        }
        Keyboard.enableRepeatEvents(true);
        this.djtH = new GuiTextField(0, Wrapper.fontRenderer, this.width / 2 - 100, 91, 200, 20);
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96 + 12, "Change Name"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 96 + 36, "Back"));
        this.djtH.setMaxStringLength(16);
        this.updateScreen();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawBackground(1);
        Wrapper.fontRenderer.drawString("Current Name : " + Wrapper.mc.getSession().getUsername(), this.width / 2 - Wrapper.fontRenderer.getStringWidth("Current Name : " + Wrapper.mc.getSession().getUsername()) / 2, 41, -1);
        Wrapper.fontRenderer.drawString("Name", this.width / 2 - 100, 79, 10526880);
        if (!Wrapper.mc.getSession().getUsername().isEmpty()) {
            Wrapper.fontRenderer.drawString("This Allows You To Change Your " + Wrapper.mc.getSession().getUsername() + " Case's, For Example " + this.LXGm, this.width / 2 - Wrapper.fontRenderer.getStringWidth("This Allows You To Change Your getUsername() Case's, For Example " + this.LXGm) / 2, this.height / 4 + 53, -1);
        }
        this.djtH.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        this.djtH.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    public void func_146284_a(final GuiButton guiButton) {
        if (guiButton.id == 1) {
            final Session session = this.mc.getSession();
            if (!this.djtH.getText().isEmpty() || this.djtH.getText() != null) {
                yYGD.VHzJ(new Session(this.djtH.getText(), session.getPlayerID(), session.getToken(), "LEGACY"));
            }
            if (BmDl.\u200f\u200b\u2009) {
                throw null;
            }
        }
        else if (guiButton.id == 2) {
            Wrapper.mc.displayGuiScreen(this.DVqx);
        }
    }
    
    public void func_73869_a(final char c, final int n) {
        this.djtH.textboxKeyTyped(c, n);
        if (n == 1) {
            this.mc.displayGuiScreen(this.DVqx);
        }
    }
    
    public void func_146281_b() {
        Keyboard.enableRepeatEvents(false);
    }
    
    public void func_73876_c() {
        this.djtH.updateCursorCounter();
    }
}
