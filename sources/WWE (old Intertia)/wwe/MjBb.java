package wwe;

import java.util.*;
import wwe.utils.*;
import net.minecraft.client.gui.*;
import java.awt.*;

public class MjBb
{
    public static ArrayList<TYDL> gEac;
    
    public MjBb() {
        super();
    }
    
    public static void EPsz() {
        ISFC();
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        final int syPH = 100;
        final int n = 30;
        int n2 = 0;
        int i = 0;
        while (i < MjBb.gEac.size()) {
            final TYDL tydl = MjBb.gEac.get(i);
            if (System.nanoTime() / (0xB78EB0CD3642ED61L ^ 0xB78EB0CD364DAF21L) - tydl.UNSh > tydl.CeyA) {
                final TYDL tydl2 = tydl;
                tydl2.syPH -= 3;
                if (MjBb.\u2002\u2007\u200c\u2002) {
                    throw null;
                }
            }
            else {
                final TYDL tydl3 = tydl;
                tydl3.syPH += 3;
            }
            if (tydl.syPH > syPH) {
                tydl.syPH = syPH;
            }
            final int n3 = scaledResolution.getScaledWidth() - tydl.syPH;
            final int n4 = scaledResolution.getScaledHeight() - 20 - n;
            gnNa.dYaT((float)n3, (float)(n4 + n2), (float)scaledResolution.getScaledWidth(), (float)(scaledResolution.getScaledHeight() - 20 + n2), 1.0f, new Color(0, 0, 0, 176).getRGB(), new Color(189, 0, 0).getRGB());
            XybC.eTim(tydl.CmCq, (float)(n3 + 5), (float)(n4 + n2 + n / 2 - XybC.wKEb().FONT_HEIGHT / 2), -1);
            n2 -= 35;
            ++i;
            if (MjBb.\u2002\u2007\u200c\u2002) {
                throw null;
            }
        }
    }
    
    public static void ISFC() {
        int i = 0;
        while (i < MjBb.gEac.size()) {
            final TYDL tydl = MjBb.gEac.get(i);
            if (System.nanoTime() / (0x71256D34CCAAD8ADL ^ 0x71256D34CCA59AEDL) - tydl.UNSh > tydl.CeyA + 330.3333333333) {
                MjBb.gEac.remove(i);
                --i;
            }
            ++i;
            if (MjBb.\u2002\u2007\u200c\u2002) {
                throw null;
            }
        }
    }
    
    public static void cZyl(final TYDL tydl) {
        MjBb.gEac.add(tydl);
    }
    
    static {
        MjBb.gEac = new ArrayList<TYDL>();
    }
}
