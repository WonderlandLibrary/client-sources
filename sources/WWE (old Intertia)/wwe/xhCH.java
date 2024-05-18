package wwe;

import net.minecraft.tileentity.*;
import java.util.*;
import wwe.utils.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import java.io.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import org.lwjgl.input.*;
import net.minecraft.util.text.*;

public class xhCH
{
    public TileEntitySign cLyU;
    public boolean Mnrb;
    public boolean JrKm;
    public ArrayList<String> lPrK;
    public static int xmQB;
    
    public xhCH(final TileEntitySign cLyU) {
        super();
        this.lPrK = new ArrayList<String>();
        this.cLyU = cLyU;
        this.lPrK = Wrapper.ypJK().kLDM();
    }
    
    public void vhfY() {
        this.JrKm = false;
    }
    
    public void KqvF(final int n, final int n2, final float n3) {
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        final int n4 = scaledResolution.getScaledWidth() - scaledResolution.getScaledWidth() / 4;
        final int n5 = scaledResolution.getScaledHeight() / 4 + 120;
        final int n6 = 20;
        final int n7 = 20;
        final int n8 = 100;
        boolean mnrb;
        if (n > n4 && n < n4 + n6 && n2 > n5 && n2 < n5 + n7) {
            mnrb = true;
            if (xhCH.\u2003\u2002\u2005\u2001\u200e) {
                throw null;
            }
        }
        else {
            mnrb = false;
        }
        this.Mnrb = mnrb;
        final float n9 = (float)n4;
        final float n10 = (float)n5;
        final float n11 = (float)(n4 + n6);
        final float n12 = (float)(n5 + n7);
        final int n13 = 0;
        final int n14 = 0;
        final int n15 = 0;
        int n16;
        if (this.Mnrb) {
            n16 = 255;
            if (xhCH.\u2003\u2002\u2005\u2001\u200e) {
                throw null;
            }
        }
        else {
            n16 = 75;
        }
        gnNa.wQYt(n9, n10, n11, n12, new Color(n13, n14, n15, n16).getRGB());
        Wrapper.fontRenderer.drawStringWithShadow("µ", (float)(n4 + n6 / 2 - Wrapper.fontRenderer.getStringWidth("µ") / 2), (float)n5, -1);
        if (this.JrKm) {
            GL11.glPushMatrix();
            GL11.glScissor((n4 + n6 / 2 - n8 / 2 - 3) * scaledResolution.getScaleFactor(), (scaledResolution.getScaledHeight() - n5 + 3) * scaledResolution.getScaleFactor(), 103 * scaledResolution.getScaleFactor(), 100 * scaledResolution.getScaleFactor());
            GL11.glEnable(3089);
            gnNa.wQYt((float)(n4 + n6 / 2 - n8 / 2), (float)(n5 - 103), (float)(n4 + n6 / 2 + n8 / 2), (float)(n5 - 3), new Color(0, 0, 0, 150).getRGB());
            int n17 = n4 + n6 / 2 - n8 / 2 + 3;
            final int n18 = n4 + n6 / 2 - n8 / 2 - 7;
            int n19 = n5 - 103;
            int i = 0;
            while (i < this.lPrK.size()) {
                Wrapper.fontRenderer.drawString((String)this.lPrK.get(i), n17, n19 + xhCH.xmQB, -1);
                if (i % 10 == 9) {
                    n19 += 10;
                    n17 = n18;
                }
                n17 += 10;
                ++i;
                if (xhCH.\u2003\u2002\u2005\u2001\u200e) {
                    throw null;
                }
            }
            GL11.glDisable(3089);
            GL11.glPopMatrix();
        }
    }
    
    public void Txia() {
    }
    
    public void Rwph(final GuiButton guiButton) throws IOException {
    }
    
    public void dufy(final char c, final int n) throws IOException {
    }
    
    public void boNl(final int n, final int n2, final int n3, final int n4) throws IOException {
        if (this.Mnrb) {
            boolean jrKm;
            if (!this.JrKm) {
                jrKm = true;
                if (xhCH.\u2003\u2002\u2005\u2001\u200e) {
                    throw null;
                }
            }
            else {
                jrKm = false;
            }
            this.JrKm = jrKm;
            Wrapper.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        }
        if (this.JrKm) {
            final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
            final int n5 = scaledResolution.getScaledWidth() - scaledResolution.getScaledWidth() / 4;
            final int n6 = scaledResolution.getScaledHeight() / 4 + 120;
            final int n7 = 20;
            final int n8 = 100;
            int n9 = n5 + n7 / 2 - n8 / 2 + 3;
            final int n10 = n5 + n7 / 2 - n8 / 2 - 7;
            int n11 = n6 - 103;
            String s = null;
            int i = 0;
            while (i < this.lPrK.size()) {
                final String s2 = this.lPrK.get(i);
                if (n > n9 && n < n9 + 10 && n2 > n11 + xhCH.xmQB && n2 < n11 + 10 + xhCH.xmQB && n2 < n6 - 3) {
                    s = s2;
                    if (xhCH.\u2003\u2002\u2005\u2001\u200e) {
                        throw null;
                    }
                    break;
                }
                else {
                    if (i % 10 == 9) {
                        n11 += 10;
                        n9 = n10;
                    }
                    n9 += 10;
                    ++i;
                    if (xhCH.\u2003\u2002\u2005\u2001\u200e) {
                        throw null;
                    }
                    continue;
                }
            }
            if (s != null) {
                this.cLyU.signText[n4] = (ITextComponent)new TextComponentString(this.cLyU.signText[n4].getUnformattedComponentText() + s);
            }
        }
    }
    
    public void uPlD() throws IOException {
        final int eventDWheel = Mouse.getEventDWheel();
        if (eventDWheel > 0) {
            xhCH.xmQB += 5;
        }
        if (eventDWheel < 0) {
            xhCH.xmQB -= 5;
        }
    }
}
