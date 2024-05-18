package wwe;

import net.minecraft.client.*;
import java.io.*;
import wwe.utils.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;

public class fIdW extends GuiScreen
{
    public String EMav;
    public boolean CVhq;
    private nxlz JvDn;
    
    public fIdW() {
        super();
        this.EMav = "";
        this.CVhq = false;
    }
    
    public FontRenderer qLsH() {
        return this.fontRenderer;
    }
    
    public void func_146281_b() {
        super.onGuiClosed();
    }
    
    public void func_73866_w_() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(5, this.width / 2 - 105, this.height - 47, 100, 20, "Direct Login"));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 5, this.height - 47, 100, 20, "Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 105, this.height - 26, 66, 20, "Add"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 33, this.height - 26, 66, 20, "Remove"));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 39, this.height - 26, 66, 20, "Cancel"));
        (this.JvDn = new nxlz(Minecraft.getMinecraft(), this)).registerScrollButtons(7, 8);
    }
    
    public void func_146274_d() throws IOException {
        super.handleMouseInput();
        this.JvDn.handleMouseInput();
    }
    
    public void func_73878_a(final boolean b, final int n) {
        super.confirmClicked(b, n);
        if (this.CVhq) {
            this.CVhq = false;
            if (b) {
                wGez.VkYf.remove(n);
                Wrapper.ypJK().dheG();
            }
            this.mc.displayGuiScreen((GuiScreen)this);
        }
    }
    
    public void func_146284_a(final GuiButton guiButton) {
        try {
            super.actionPerformed(guiButton);
            if (fIdW.\u2007\u2007\u2007\u2004\u2006\u2009\u2005\u200b) {
                throw null;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        if (guiButton.id == 1) {
            this.mc.displayGuiScreen((GuiScreen)new weQP(this));
        }
        if (guiButton.id == 2) {
            try {
                final aRkr aRkr = wGez.VkYf.get(this.JvDn.zFMV());
                Label_0172: {
                    if (aRkr.RZeI()) {
                        try {
                            final Session uhdS = exyI.UhdS(aRkr.dcDV(), aRkr.WLJz());
                            if (uhdS != null) {
                                yYGD.VHzJ(uhdS);
                            }
                            wGez.PrrU.EMav = "";
                            if (fIdW.\u2007\u2007\u2007\u2004\u2006\u2009\u2005\u200b) {
                                throw null;
                            }
                            break Label_0172;
                        }
                        catch (Exception ex2) {
                            this.EMav = "".concat("§cBad Login §7(").concat(aRkr.dcDV()).concat(")");
                            if (fIdW.\u2007\u2007\u2007\u2004\u2006\u2009\u2005\u200b) {
                                throw null;
                            }
                            break Label_0172;
                        }
                    }
                    yYGD.VHzJ(exyI.cadX(aRkr.dcDV()));
                    wGez.PrrU.EMav = "";
                }
                if (fIdW.\u2007\u2007\u2007\u2004\u2006\u2009\u2005\u200b) {
                    throw null;
                }
            }
            catch (Exception ex3) {}
        }
        if (guiButton.id == 3) {
            try {
                final GuiYesNo guiYesNo = new GuiYesNo((GuiYesNoCallback)this, "Delete the alt: \"" + wGez.VkYf.get(this.JvDn.zFMV()).dcDV() + "\"?", "", "Delete", "Cancel", this.JvDn.zFMV());
                this.CVhq = true;
                this.mc.displayGuiScreen((GuiScreen)guiYesNo);
                if (fIdW.\u2007\u2007\u2007\u2004\u2006\u2009\u2005\u200b) {
                    throw null;
                }
            }
            catch (Exception ex4) {}
        }
        if (guiButton.id == 4) {
            this.mc.displayGuiScreen((GuiScreen)new GuiMainMenu());
        }
        if (guiButton.id == 5) {
            final NJJw njJw = new NJJw(this);
            this.mc.displayGuiScreen((GuiScreen)new NJJw(this));
        }
    }
    
    public void func_73876_c() {
        super.updateScreen();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.JvDn.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRenderer, "Alts", this.width / 2, 13, 16777215);
        this.fontRenderer.drawStringWithShadow("Playing as " + Minecraft.getMinecraft().getSession().getUsername(), 3.0f, 3.0f, 16777215);
        this.fontRenderer.drawStringWithShadow(this.EMav, 3.0f, 13.0f, 16777215);
        super.drawScreen(n, n2, n3);
    }
}
