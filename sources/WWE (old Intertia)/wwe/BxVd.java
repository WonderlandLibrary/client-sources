package wwe;

import java.util.*;
import java.awt.*;
import wwe.utils.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import java.io.*;
import org.lwjgl.input.*;
import net.minecraft.util.math.*;

public class BxVd extends GuiScreen
{
    public ArrayList<nRMt> XIGs;
    public int OOVQ;
    public GuiTextField VZxu;
    public int VLJq;
    
    public BxVd() {
        super();
        this.XIGs = new ArrayList<nRMt>();
    }
    
    public void func_73866_w_() {
        this.XIGs.clear();
        final GraphicsEnvironment localGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int n = 40;
        this.VLJq = localGraphicsEnvironment.getAvailableFontFamilyNames().length;
        int i = 0;
        while (i < localGraphicsEnvironment.getAvailableFontFamilyNames().length) {
            final String s = localGraphicsEnvironment.getAvailableFontFamilyNames()[i];
            this.XIGs.add(new nRMt(this, s, this.width / 2 - XybC.wKEb().getStringWidth(s) / 2 - 2, n, XybC.wKEb().getStringWidth(s) + 4, 10));
            n += 12;
            ++i;
            if (BxVd.\u2006) {
                throw null;
            }
        }
        (this.VZxu = new GuiTextField(0, Wrapper.fontRenderer, this.width / 2 - 100, 10, 200, 20)).setFocused(true);
        super.initGui();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        if (this.VLJq < (this.height - 70) / 12) {
            this.OOVQ = 0;
            if (BxVd.\u2006) {
                throw null;
            }
        }
        else if (this.OOVQ > 0) {
            this.OOVQ = 0;
            if (BxVd.\u2006) {
                throw null;
            }
        }
        else if (Math.abs(this.OOVQ) > this.VLJq * 12 - (this.height - 70)) {
            this.OOVQ = -(this.VLJq * 12 - (this.height - 70));
        }
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        GL11.glPushMatrix();
        scaledResolution.getScaleFactor();
        gnNa.zfJJ(0.0, 40.0, this.width, scaledResolution.getScaledHeight_double() - 30.0);
        GL11.glEnable(3089);
        int i = 0;
        while (i < this.XIGs.size()) {
            this.XIGs.get(i).pHsd(n, n2, n3);
            ++i;
            if (BxVd.\u2006) {
                throw null;
            }
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        this.VZxu.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
    
    public void func_73876_c() {
        this.VZxu.updateCursorCounter();
        super.updateScreen();
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        int i = 0;
        while (i < this.XIGs.size()) {
            this.XIGs.get(i).FTwg(n, n2, n3);
            ++i;
            if (BxVd.\u2006) {
                throw null;
            }
        }
        this.VZxu.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    public void func_146286_b(final int n, final int n2, final int n3) {
        super.mouseReleased(n, n2, n3);
    }
    
    public void func_73869_a(final char c, final int n) throws IOException {
        if (this.VZxu.textboxKeyTyped(c, n)) {
            this.XIGs.clear();
            this.OOVQ = 0;
            final GraphicsEnvironment localGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            int n2 = 40;
            int vlJq = 0;
            int i = 0;
            while (i < localGraphicsEnvironment.getAvailableFontFamilyNames().length) {
                final String s = localGraphicsEnvironment.getAvailableFontFamilyNames()[i];
                if (this.VZxu.getText().isEmpty() || s.toLowerCase().contains(this.VZxu.getText().toLowerCase())) {
                    this.XIGs.add(new nRMt(this, s, this.width / 2 - XybC.wKEb().getStringWidth(s) / 2 - 2, n2, XybC.wKEb().getStringWidth(s) + 4, 10));
                    n2 += 12;
                    ++vlJq;
                }
                ++i;
                if (BxVd.\u2006) {
                    throw null;
                }
            }
            this.VLJq = vlJq;
        }
        super.keyTyped(c, n);
    }
    
    public void func_146274_d() throws IOException {
        final int dWheel = Mouse.getDWheel();
        if (dWheel > 0 || dWheel < 0) {
            int i = 0;
            while (i < this.XIGs.size()) {
                this.XIGs.get(i).kybB(dWheel);
                ++i;
                if (BxVd.\u2006) {
                    throw null;
                }
            }
            this.OOVQ -= MathHelper.clamp(dWheel, 3, -3) * 10;
        }
        super.handleMouseInput();
    }
}
