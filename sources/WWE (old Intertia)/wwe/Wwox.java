package wwe;

import wwe.utils.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import java.util.*;
import net.minecraft.client.gui.*;
import java.io.*;
import org.lwjgl.input.*;

public class Wwox extends GuiScreen
{
    public GuiScreen xMqg;
    public GuiTextField xSlq;
    public Kdwt inUT;
    public boolean qNrw;
    public boolean kwXH;
    public int eYUY;
    
    public Wwox(final GuiScreen xMqg) {
        super();
        this.xMqg = xMqg;
    }
    
    public void func_73866_w_() {
        Keyboard.enableRepeatEvents(true);
        final Iterator<Kdwt> iterator = vlwv.kXkt.CkvS.iterator();
        while (iterator.hasNext()) {
            iterator.next().TRqF = this;
            if (Wwox.\u200c\u2005\u200a\u2003\u2001\u200a\u200d) {
                throw null;
            }
        }
        (this.xSlq = new GuiTextField(1, this.fontRenderer, this.width / 2 / 2, this.height - 25, this.width / 2, 20)).setMaxStringLength(500);
        this.addButton(new GuiButton(1, this.width - 55, 5, 50, 20, "Friends"));
        this.addButton(new GuiButton(2, this.width - 55, 30, 50, 20, "Tic Tac Toe"));
        this.addButton(new GuiButton(3, this.width - 55, 55, 50, 20, "Options"));
        this.addButton(new GuiButton(4, this.width - 55, 80, 50, 20, "Log Out"));
        this.addButton(new GuiButton(5, this.width / 2 + this.width / 4 + 20, this.height - 25, 50, 20, "Send"));
        this.addButton(new GuiButton(6, this.width - 55, this.height - 25, 50, 20, "Back"));
        hSNE.lyvR(this);
        super.initGui();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        if (this.inUT == null) {
            this.xSlq.setEnabled(false);
            this.buttonList.get(4).enabled = false;
            if (Wwox.\u200c\u2005\u200a\u2003\u2001\u200a\u200d) {
                throw null;
            }
        }
        else {
            this.xSlq.setEnabled(true);
            this.buttonList.get(4).enabled = true;
        }
        Wrapper.fontRenderer.drawStringWithShadow("Welcome, " + Wrapper.mc.getSession().getUsername(), (float)(this.width / 12), (float)(this.height / 12), -1);
        Wrapper.fontRenderer.drawStringWithShadow("WWE Chat", (float)(this.width / 2 - Wrapper.fontRenderer.getStringWidth("WWE Chat") / 2), 10.0f, -1);
        GL11.glPushMatrix();
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        GL11.glScissor(4 * scaledResolution.getScaleFactor(), 30 * scaledResolution.getScaleFactor(), (39 + Wrapper.fontRenderer.getStringWidth("O") * 16 + 3) * scaledResolution.getScaleFactor(), (this.height - 80) * scaledResolution.getScaleFactor());
        GL11.glEnable(3089);
        int i = 0;
        while (i < vlwv.kXkt.CkvS.size()) {
            vlwv.kXkt.CkvS.get(i).FDgX(n, n2, n3);
            ++i;
            if (Wwox.\u200c\u2005\u200a\u2003\u2001\u200a\u200d) {
                throw null;
            }
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        this.xSlq.drawTextBox();
        if (this.inUT != null) {
            gnNa.wQYt((float)(39 + Wrapper.fontRenderer.getStringWidth("O") * 16 + 8), 50.0f, (float)(this.width - 55), (float)(this.height - 30), new Color(0, 0, 0, 100).getRGB());
            GL11.glPushMatrix();
            GL11.glScissor((39 + Wrapper.fontRenderer.getStringWidth("O") * 16 + 8) * scaledResolution.getScaleFactor(), 30 * scaledResolution.getScaleFactor(), (this.width - 55 - (39 + Wrapper.fontRenderer.getStringWidth("O") * 16 + 8)) * scaledResolution.getScaleFactor(), (this.height - 80) * scaledResolution.getScaleFactor());
            GL11.glEnable(3089);
            int n4 = 52;
            int j = 0;
            while (j < this.inUT.TXHL.size()) {
                final iZvY iZvY = this.inUT.TXHL.get(j);
                final ArrayList<String> dDuY = FIwW.DDuY(iZvY.JfDk, 40);
                int stringWidth = 0;
                for (final String s : dDuY) {
                    if (Wrapper.fontRenderer.getStringWidth(s) > stringWidth) {
                        stringWidth = Wrapper.fontRenderer.getStringWidth(s);
                    }
                    if (Wwox.\u200c\u2005\u200a\u2003\u2001\u200a\u200d) {
                        throw null;
                    }
                }
                int n5;
                if (iZvY.JYtN != null) {
                    n5 = 39 + Wrapper.fontRenderer.getStringWidth("O") * 16 + 14;
                    if (Wwox.\u200c\u2005\u200a\u2003\u2001\u200a\u200d) {
                        throw null;
                    }
                }
                else {
                    n5 = this.width - 57 - stringWidth;
                }
                final int n6 = n5;
                int n7 = n4 - 1 + this.eYUY;
                final int n8 = n6 + stringWidth - 4;
                final int n9 = n7 + 10 + (dDuY.size() - 1) * 10;
                n4 += dDuY.size() * 10 + 3;
                gnNa.NOOc();
                final int n10 = n6;
                final int n11 = n7;
                final int n12 = n8;
                final int n13 = n9;
                final float n14 = 5.0f;
                final int size = dDuY.size();
                int n15;
                if (iZvY.JYtN != null) {
                    n15 = -1;
                    if (Wwox.\u200c\u2005\u200a\u2003\u2001\u200a\u200d) {
                        throw null;
                    }
                }
                else {
                    n15 = -16744193;
                }
                gnNa.nWXs(n10, n11, n12, n13, n14, size, n15);
                gnNa.uCta();
                int n16;
                if (iZvY.JYtN != null) {
                    n16 = -16777216;
                    if (Wwox.\u200c\u2005\u200a\u2003\u2001\u200a\u200d) {
                        throw null;
                    }
                }
                else {
                    n16 = -1;
                }
                gnNa.zwAE(n16);
                int k = 0;
                while (k < dDuY.size()) {
                    final String s2 = dDuY.get(k);
                    final int n17 = 39 + Wrapper.fontRenderer.getStringWidth("O") * 16 + 12;
                    final FontRenderer fontRenderer = Wrapper.fontRenderer;
                    final String s3 = s2;
                    int n18;
                    if (iZvY.JYtN != null) {
                        n18 = n17;
                        if (Wwox.\u200c\u2005\u200a\u2003\u2001\u200a\u200d) {
                            throw null;
                        }
                    }
                    else {
                        n18 = this.width - 55 - stringWidth - 4;
                    }
                    final int n19 = n7 + 1;
                    int n20;
                    if (iZvY.JYtN != null) {
                        n20 = -16777216;
                        if (Wwox.\u200c\u2005\u200a\u2003\u2001\u200a\u200d) {
                            throw null;
                        }
                    }
                    else {
                        n20 = -1;
                    }
                    fontRenderer.drawString(s3, n18, n19, n20);
                    n7 += 10;
                    ++k;
                    if (Wwox.\u200c\u2005\u200a\u2003\u2001\u200a\u200d) {
                        throw null;
                    }
                }
                ++j;
                if (Wwox.\u200c\u2005\u200a\u2003\u2001\u200a\u200d) {
                    throw null;
                }
            }
            GL11.glDisable(3089);
            GL11.glPopMatrix();
        }
        boolean qNrw;
        if (n >= 4 && n <= 39 + Wrapper.fontRenderer.getStringWidth("O") * 16 + 3 && n2 >= 50 && n2 <= this.height - 30) {
            qNrw = true;
            if (Wwox.\u200c\u2005\u200a\u2003\u2001\u200a\u200d) {
                throw null;
            }
        }
        else {
            qNrw = false;
        }
        this.qNrw = qNrw;
        boolean kwXH;
        if (n >= 39 + Wrapper.fontRenderer.getStringWidth("O") * 16 + 8 && n <= this.height - 30 + Wrapper.fontRenderer.getStringWidth("O") * 16 + 3 && n2 >= 50 && n2 <= this.height - 30) {
            kwXH = true;
            if (Wwox.\u200c\u2005\u200a\u2003\u2001\u200a\u200d) {
                throw null;
            }
        }
        else {
            kwXH = false;
        }
        this.kwXH = kwXH;
        super.drawScreen(n, n2, n3);
    }
    
    public void func_146281_b() {
        Keyboard.enableRepeatEvents(false);
        hSNE.kduK(this);
    }
    
    public void func_73876_c() {
        if (!vlwv.kXkt.xFrI()) {
            this.mc.displayGuiScreen((GuiScreen)new MkSj(this.xMqg));
        }
        this.xSlq.updateCursorCounter();
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        for (final Kdwt inUT : vlwv.kXkt.CkvS) {
            if (n >= inUT.ByAc - 1 && n < inUT.ByAc + 35 + Wrapper.fontRenderer.getStringWidth("O") * 16 + 3 && n2 >= inUT.AzOt - 1 && n2 < inUT.AzOt + 31) {
                this.inUT = inUT;
                this.xSlq.setText("");
                if (Wwox.\u200c\u2005\u200a\u2003\u2001\u200a\u200d) {
                    throw null;
                }
                break;
            }
            else {
                if (Wwox.\u200c\u2005\u200a\u2003\u2001\u200a\u200d) {
                    throw null;
                }
                continue;
            }
        }
        this.xSlq.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    public void func_146274_d() throws IOException {
        final int eventDWheel = Mouse.getEventDWheel();
        if (this.qNrw) {
            if (eventDWheel > 0 && vlwv.kXkt.CkvS.get(0).AzOt <= new ScaledResolution(Wrapper.mc).getScaledHeight() - 75) {
                for (final Kdwt kdwt : vlwv.kXkt.CkvS) {
                    kdwt.AzOt += 10;
                    if (Wwox.\u200c\u2005\u200a\u2003\u2001\u200a\u200d) {
                        throw null;
                    }
                }
            }
            if (eventDWheel < 0 && vlwv.kXkt.CkvS.get(vlwv.kXkt.CkvS.size() - 1).AzOt >= 50) {
                for (final Kdwt kdwt2 : vlwv.kXkt.CkvS) {
                    kdwt2.AzOt -= 10;
                    if (Wwox.\u200c\u2005\u200a\u2003\u2001\u200a\u200d) {
                        throw null;
                    }
                }
                if (Wwox.\u200c\u2005\u200a\u2003\u2001\u200a\u200d) {
                    throw null;
                }
            }
        }
        else if (this.kwXH) {
            if (eventDWheel > 0) {
                this.eYUY += 10;
            }
            if (eventDWheel < 0) {
                this.eYUY -= 10;
            }
        }
        super.handleMouseInput();
    }
    
    protected void func_73869_a(final char c, final int n) {
        this.xSlq.textboxKeyTyped(c, n);
        if (this.inUT != null && this.xSlq.isFocused() && !this.xSlq.getText().isEmpty() && n == 28) {
            vlwv.kXkt.DKWU(new UAPS(this.inUT.uKMJ.umix.toString(), this.xSlq.getText()));
            this.xSlq.setText("");
        }
    }
    
    protected void func_146284_a(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 1) {
            Wrapper.mc.displayGuiScreen((GuiScreen)new jBNh(this));
        }
        if (guiButton.id == 2) {
            this.mc.displayGuiScreen((GuiScreen)new pVHd(this));
        }
        if (guiButton.id == 3) {
            this.mc.displayGuiScreen((GuiScreen)new zROp(this));
        }
        if (guiButton.id == 4) {
            vlwv.kXkt.DKWU(new zymI());
        }
        if (guiButton.id == 5 && this.inUT != null && !this.xSlq.getText().isEmpty()) {
            vlwv.kXkt.DKWU(new UAPS(this.inUT.uKMJ.umix.toString(), this.xSlq.getText()));
            this.xSlq.setText("");
        }
        if (guiButton.id == 6) {
            Wrapper.mc.displayGuiScreen(this.xMqg);
        }
    }
}
