package com.client.glowclient;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.client.resources.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import java.io.*;
import net.minecraft.client.gui.*;

public class MA extends TA
{
    private yc J;
    private GuiButton E;
    private yc h;
    private yc D;
    private final String j;
    private final String F;
    private int I;
    private final String e;
    private int a;
    private yc i;
    private yc g;
    private GuiButton l;
    private final String K;
    private GuiButton c;
    private Iterator<String> k;
    public static final boolean H;
    private GuiTextField f;
    private GuiButton M;
    private GuiButton G;
    private String d;
    private final String L;
    private yc A;
    private final String B;
    private String b;
    
    static {
        H = !MA.class.desiredAssertionStatus();
    }
    
    public void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == this.l.id) {
                eb.M(eb.H);
                eb.k();
                this.M(this.g, this.A, this.i, eb.H);
                return;
            }
            if (guiButton.id == this.g.id) {
                eb.H.b = this.g.getValue();
                eb.k();
                return;
            }
            if (guiButton.id == this.A.id) {
                eb.H.A = this.A.getValue();
                eb.k();
                return;
            }
            if (guiButton.id == this.i.id) {
                eb.H.B = this.i.getValue();
                eb.k();
                return;
            }
            if (guiButton.id == this.E.id) {
                eb.M(eb.k);
                eb.k();
                this.M(this.J, this.D, this.h, eb.k);
                return;
            }
            if (guiButton.id == this.J.id) {
                eb.k.b = this.J.getValue();
                eb.k();
                return;
            }
            if (guiButton.id == this.D.id) {
                eb.k.A = this.D.getValue();
                eb.k();
                return;
            }
            if (guiButton.id == this.h.id) {
                eb.k.B = this.h.getValue();
                eb.k();
                return;
            }
            if (guiButton.id == this.G.id) {
                eb.A = (!eb.A && kB.b.B);
                this.G.displayString = (eb.A ? this.K : this.j);
                this.M.enabled = (eb.A || eb.g != null);
                this.c.enabled = (eb.A || eb.g != null);
                return;
            }
            if (guiButton.id == this.c.id) {
                this.b = this.M();
                final GuiButton c = this.c;
                final String s = "schematica.gui.format";
                final Object[] array = { null };
                final String m = Sd.M(this.b);
                final int n = 0;
                array[n] = I18n.format(m, (Object[])new Object[n]);
                c.displayString = I18n.format(s, (Object[])array);
                return;
            }
            if (guiButton.id == this.M.id) {
                final String string = new StringBuilder().insert(0, this.f.getText()).append(Sd.D(this.b)).toString();
                if (eb.A) {
                    if (kB.b.M((EntityPlayer)this.mc.player, SC.Ga, string, (World)this.mc.world, this.b, eb.G, eb.c)) {
                        this.d = "";
                        this.f.setText(this.d);
                    }
                }
                else {
                    Sd.M(new File(SC.Ga, string), this.b, eb.g.getSchematic(), (EntityPlayer)this.mc.player);
                }
            }
        }
    }
    
    private void M(final yc yc) {
        final int maximum = 30000000;
        yc.setMinimum(-30000000);
        yc.setMaximum(maximum);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawString(this.fontRenderer, this.e, this.width - 205, this.height - 70, 16777215);
        this.drawString(this.fontRenderer, this.F, this.a - 145, this.I - 24, 16777215);
        this.drawString(this.fontRenderer, Integer.toString(eb.H.b), this.a - 25, this.I - 24, 16777215);
        this.drawString(this.fontRenderer, this.L, this.a - 145, this.I + 1, 16777215);
        this.drawString(this.fontRenderer, Integer.toString(eb.H.A), this.a - 25, this.I + 1, 16777215);
        this.drawString(this.fontRenderer, this.B, this.a - 145, this.I + 26, 16777215);
        this.drawString(this.fontRenderer, Integer.toString(eb.H.B), this.a - 25, this.I + 26, 16777215);
        this.drawString(this.fontRenderer, this.F, this.a + 15, this.I - 24, 16777215);
        this.drawString(this.fontRenderer, Integer.toString(eb.k.b), this.a + 135, this.I - 24, 16777215);
        this.drawString(this.fontRenderer, this.L, this.a + 15, this.I + 1, 16777215);
        this.drawString(this.fontRenderer, Integer.toString(eb.k.A), this.a + 135, this.I + 1, 16777215);
        this.drawString(this.fontRenderer, this.B, this.a + 15, this.I + 26, 16777215);
        this.drawString(this.fontRenderer, Integer.toString(eb.k.B), this.a + 135, this.I + 26, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public void initGui() {
        final int n = 0;
        this.a = this.width / 2;
        this.I = this.height / 2;
        this.buttonList.clear();
        final int n3;
        int n2 = n3 = n;
        final int a = this.a;
        final int n4 = 130;
        ++n2;
        this.l = new GuiButton(n3, a - n4, this.I - 55, 100, 20, I18n.format("schematica.gui.point.red", (Object[])new Object[0]));
        this.buttonList.add(this.l);
        final FontRenderer fontRenderer = this.fontRenderer;
        final int n5 = n2;
        final int a2 = this.a;
        final int n6 = 130;
        ++n2;
        this.g = new yc(fontRenderer, n5, a2 - n6, this.I - 30);
        this.buttonList.add(this.g);
        final FontRenderer fontRenderer2 = this.fontRenderer;
        final int n7 = n2;
        final int a3 = this.a;
        final int n8 = 130;
        ++n2;
        this.A = new yc(fontRenderer2, n7, a3 - n8, this.I - 5);
        this.buttonList.add(this.A);
        final FontRenderer fontRenderer3 = this.fontRenderer;
        final int n9 = n2;
        final int a4 = this.a;
        final int n10 = 130;
        ++n2;
        this.i = new yc(fontRenderer3, n9, a4 - n10, this.I + 20);
        this.buttonList.add(this.i);
        final int n11 = n2;
        final int a5 = this.a;
        final int n12 = 30;
        ++n2;
        this.E = new GuiButton(n11, a5 + n12, this.I - 55, 100, 20, I18n.format("schematica.gui.point.blue", (Object[])new Object[0]));
        this.buttonList.add(this.E);
        final FontRenderer fontRenderer4 = this.fontRenderer;
        final int n13 = n2;
        final int a6 = this.a;
        final int n14 = 30;
        ++n2;
        this.J = new yc(fontRenderer4, n13, a6 + n14, this.I - 30);
        this.buttonList.add(this.J);
        final FontRenderer fontRenderer5 = this.fontRenderer;
        final int n15 = n2;
        final int a7 = this.a;
        final int n16 = 30;
        ++n2;
        this.D = new yc(fontRenderer5, n15, a7 + n16, this.I - 5);
        this.buttonList.add(this.D);
        final FontRenderer fontRenderer6 = this.fontRenderer;
        final int n17 = n2;
        final int a8 = this.a;
        final int n18 = 30;
        ++n2;
        this.h = new yc(fontRenderer6, n17, a8 + n18, this.I + 20);
        this.buttonList.add(this.h);
        final int n19 = n2;
        final int width = this.width;
        final int n20 = 210;
        ++n2;
        this.G = new GuiButton(n19, width - n20, this.height - 55, 50, 20, (eb.A && kB.b.B) ? this.K : this.j);
        this.buttonList.add(this.G);
        final int n21 = n2;
        final FontRenderer fontRenderer7 = this.fontRenderer;
        ++n2;
        this.f = new GuiTextField(n21, fontRenderer7, this.width - 209, this.height - 29, 153, 18);
        this.B.add(this.f);
        final int n22 = n2;
        final int width2 = this.width;
        final int n23 = 50;
        ++n2;
        this.M = new GuiButton(n22, width2 - n23, this.height - 30, 40, 20, I18n.format("schematica.gui.save", (Object[])new Object[0]));
        this.M.enabled = ((eb.A && kB.b.B) || eb.g != null);
        this.buttonList.add(this.M);
        final int n24 = n2;
        final int width3 = this.width;
        final int n25 = 155;
        ++n2;
        final int n26 = width3 - n25;
        final int n27 = this.height - 55;
        final int n28 = 145;
        final int n29 = 20;
        final String s = "schematica.gui.format";
        final Object[] array = { null };
        final String m = Sd.M(this.b);
        final int n30 = 0;
        array[n30] = I18n.format(m, (Object[])new Object[n30]);
        this.c = new GuiButton(n24, n26, n27, n28, n29, I18n.format(s, (Object[])array));
        this.c.enabled = ((eb.A && kB.b.B) || eb.g != null);
        this.buttonList.add(this.c);
        this.f.setMaxStringLength(1024);
        this.f.setText(this.d);
        this.M(this.g);
        this.M(this.A);
        this.M(this.i);
        this.M(this.J);
        this.M(this.D);
        this.M(this.h);
        this.M(this.g, this.A, this.i, eb.H);
        this.M(this.J, this.D, this.h, eb.k);
    }
    
    @Override
    public void keyTyped(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
        this.d = this.f.getText();
    }
    
    private String M() {
        if (this.k != null) {
            if (!this.k.hasNext()) {
                this.k = Sd.B.keySet().iterator();
            }
            return this.k.next();
        }
        if (!MA.H && Sd.B.size() <= 0) {
            throw new AssertionError();
        }
        if (!MA.H && !Sd.B.containsKey(Sd.b)) {
            throw new AssertionError();
        }
        MA ma = this;
        this.k = Sd.B.keySet().iterator();
        while (!ma.k.next().equals(Sd.b)) {
            ma = this;
        }
        return Sd.b;
    }
    
    public MA(final GuiScreen guiScreen) {
        final String s = "schematica.gui.saveselection";
        final Iterator<String> k = null;
        final String d = "";
        final GuiTextField guiTextField = null;
        final GuiButton guiButton = null;
        final yc yc = null;
        final yc yc2 = null;
        final yc yc3 = null;
        final yc yc4 = null;
        final int n = 0;
        super(guiScreen);
        this.a = n;
        this.I = n;
        this.l = yc4;
        this.g = yc4;
        this.A = yc3;
        this.i = yc3;
        this.E = yc2;
        this.J = yc2;
        this.D = yc;
        this.h = yc;
        this.G = guiButton;
        this.c = guiButton;
        this.M = (GuiButton)guiTextField;
        this.f = guiTextField;
        this.d = d;
        this.b = this.M();
        this.k = k;
        this.e = I18n.format(s, (Object[])new Object[0]);
        this.F = I18n.format("schematica.gui.x", (Object[])new Object[0]);
        this.L = I18n.format("schematica.gui.y", (Object[])new Object[0]);
        this.B = I18n.format("schematica.gui.z", (Object[])new Object[0]);
        this.K = I18n.format("schematica.gui.on", (Object[])new Object[0]);
        this.j = I18n.format("schematica.gui.off", (Object[])new Object[0]);
    }
    
    private void M(final yc yc, final yc yc2, final yc yc3, final BlockPos blockPos) {
        yc.setValue(blockPos.getX());
        yc2.setValue(blockPos.getY());
        yc3.setValue(blockPos.getZ());
    }
}
