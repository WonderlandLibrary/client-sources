package wwe;

import net.minecraft.client.gui.*;
import wwe.utils.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class JImF extends GuiScreen
{
    public GuiScreen izdo;
    public char[] hmkw;
    public ArrayList<EylK> eNIQ;
    public String alls;
    public boolean xooC;
    
    public JImF(final GuiScreen izdo) {
        super();
        this.hmkw = new char[9];
        this.eNIQ = new ArrayList<EylK>();
        this.xooC = true;
        this.izdo = izdo;
    }
    
    public void func_73866_w_() {
        this.eNIQ.clear();
        if (this.xooC) {
            int n = 0;
            char c = '0';
            while (c < '9') {
                this.hmkw[n] = c;
                ++n;
                ++c;
                if (JImF.\u200e\u2009\u200b\u2001\u2006) {
                    throw null;
                }
            }
            this.xooC = false;
        }
        final int n3;
        int n2 = n3 = this.width / 2 - 79;
        int n4 = this.height / 2 - 79;
        int i = 0;
        while (i < 9) {
            this.eNIQ.add(new EylK(n2, n4, 50, i, this));
            if (i % 3 == 2) {
                n4 += 54;
                n2 = n3 - 54;
            }
            n2 += 54;
            ++i;
            if (JImF.\u200e\u2009\u200b\u2001\u2006) {
                throw null;
            }
        }
        hSNE.kduK(this);
        hSNE.lyvR(this);
        this.addButton(new GuiButton(3, this.width / 2 - 100, this.height - 50, "Back"));
        super.initGui();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        if (this.alls != null) {
            Wrapper.fontRenderer.drawString(this.alls, this.width / 2 - Wrapper.fontRenderer.getStringWidth(this.alls) / 2, 30, -1);
        }
        drawRect(this.width / 2 - 25, this.height / 2 - 79, this.width / 2 - 29, this.height / 2 + 79, Color.black.getRGB());
        drawRect(this.width / 2 + 25, this.height / 2 - 79, this.width / 2 + 29, this.height / 2 + 79, Color.black.getRGB());
        drawRect(this.width / 2 - 79, this.height / 2 - 25, this.width / 2 + 79, this.height / 2 - 29, Color.black.getRGB());
        drawRect(this.width / 2 - 79, this.height / 2 + 25, this.width / 2 + 79, this.height / 2 + 29, Color.black.getRGB());
        int i = 0;
        while (i < this.eNIQ.size()) {
            this.eNIQ.get(i).cVOn(n, n2, n3);
            ++i;
            if (JImF.\u200e\u2009\u200b\u2001\u2006) {
                throw null;
            }
        }
        super.drawScreen(n, n2, n3);
    }
    
    public void func_146281_b() {
        hSNE.kduK(this);
        this.xooC = true;
        super.onGuiClosed();
    }
    
    public void func_73876_c() {
        super.updateScreen();
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        final Iterator<EylK> iterator = this.eNIQ.iterator();
        while (iterator.hasNext()) {
            iterator.next().acXl(n, n2, n3);
            if (JImF.\u200e\u2009\u200b\u2001\u2006) {
                throw null;
            }
        }
        super.mouseClicked(n, n2, n3);
    }
    
    protected void func_73869_a(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
    }
    
    protected void func_146284_a(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 3) {
            this.mc.displayGuiScreen(this.izdo);
        }
    }
    
    public boolean LTGY(final int n) {
        boolean b;
        if (this.hmkw[n] != 'x' && this.hmkw[n] != 'o') {
            b = true;
            if (JImF.\u200e\u2009\u200b\u2001\u2006) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    @oqrr
    public void iWAt(final mIcn.wwe/OxHg wwe/OxHg) {
        this.hmkw[wwe/OxHg.THBX] = wwe/OxHg.upTJ;
    }
    
    @oqrr
    public void zVty(final mIcn.wwe/fYTJ wwe/fYTJ) {
        if (wwe/fYTJ.vCOH != 2) {
            return;
        }
        this.alls = wwe/fYTJ.eaSc;
    }
}
