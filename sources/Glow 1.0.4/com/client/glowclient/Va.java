package com.client.glowclient;

import java.util.function.*;
import java.io.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.*;

public class va extends Za
{
    private final String f;
    private Supplier<String> M;
    private String G;
    private int d;
    private boolean L;
    private int A;
    private int B;
    private final int b;
    
    private static String D(final String s) {
        return s;
    }
    
    public void setMinorTaskProgress(final int b) {
        this.B = b;
    }
    
    public void setDoneWorking() {
        this.L = true;
    }
    
    public void keyTyped(final char c, final int n) throws IOException {
    }
    
    private void M(final int n, final int n2, final int n3, int n4, int n5) {
        if (n5 == 0) {
            return;
        }
        this.mc.getTextureManager().bindTexture(Gui.ICONS);
        n4 = n4 * 182 / n5;
        n5 = 5;
        n5 = this.width / 2 - 91;
        this.drawTexturedModalRect(n5, n, 0, n2, 182, 5);
        this.drawTexturedModalRect(n5, n, 0, n3, n4, 5);
    }
    
    private static String M(final String s) {
        return s;
    }
    
    public void setMinorTaskProgress(final String s, final int b) {
        this.M = va::D;
        this.B = b;
    }
    
    public void startMajorTask(final String g, final int a) {
        this.G = g;
        ++this.d;
        this.M = va::M;
        this.B = 0;
        this.A = a;
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        if (this.L) {
            this.mc.displayGuiScreen((GuiScreen)null);
            return;
        }
        final int n4 = 32;
        final int n5 = 0;
        Qa.M(n4, n4, n5, n5, this.height, this.width);
        String s = this.G;
        if (this.b > 1) {
            s = I18n.format("com.client.glowclient.utils.mod.imports.wdl.gui.saveProgress.progressInfo", new Object[] { this.G, this.d, this.b });
        }
        final String s2 = this.M.get();
        if (this.A > 1) {
            I18n.format("com.client.glowclient.utils.mod.imports.wdl.gui.saveProgress.progressInfo", new Object[] { s2, this.B, this.A });
        }
        this.drawCenteredString(this.fontRenderer, this.f, this.width / 2, 8, 16777215);
        this.drawCenteredString(this.fontRenderer, s, this.width / 2, 100, 16777215);
        va va;
        if (this.A > 0) {
            va = this;
            this.M(110, 84, 89, this.d * this.A + this.B, (this.b + 1) * this.A);
        }
        else {
            va = this;
            this.M(110, 84, 89, this.d, this.b);
        }
        va.drawScreen(n, n2, n3);
    }
    
    public void setMinorTaskProgress(final Supplier<String> m, final int b) {
        this.M = m;
        this.B = b;
    }
    
    public va(final String f, final int b) {
        final String g = "";
        super();
        this.G = g;
        this.M = va::M;
        final int d = 0;
        this.L = false;
        this.f = f;
        this.b = b;
        this.d = d;
    }
    
    private static String M() {
        return "";
    }
    
    public void setMinorTaskCount(final int a) {
        this.A = a;
    }
}
