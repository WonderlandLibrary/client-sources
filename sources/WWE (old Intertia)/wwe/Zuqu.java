package wwe;

import wwe.modules.gui.*;
import java.awt.*;
import java.util.*;
import net.minecraft.util.math.*;

public class Zuqu
{
    public FinI NKGo;
    public int fPbL;
    public boolean BLjJ;
    public int YsjZ;
    public int ORoU;
    public int lzIt;
    public boolean SGQw;
    public boolean CMNu;
    public boolean JHBb;
    
    public Zuqu(final FinI nkGo) {
        super();
        this.NKGo = nkGo;
    }
    
    public void cAJG(final int n, final int n2, final float n3) {
        boolean bLjJ;
        if (n >= this.NKGo.kVnf + this.NKGo.dedb - 5 && n <= this.NKGo.kVnf + this.NKGo.dedb && n2 >= this.NKGo.CQIZ && n2 <= this.NKGo.CQIZ + this.NKGo.vjbx + Math.min(240, this.NKGo.UfJC.size() * 12)) {
            bLjJ = true;
            if (Zuqu.\u2001\u200a) {
                throw null;
            }
        }
        else {
            bLjJ = false;
        }
        this.BLjJ = bLjJ;
        this.NKGo.yveP(0, this.ORoU);
        this.TPrS(0.0, true);
    }
    
    public void RzsG(final int n, final int n2, final float n3) {
        double n4 = 0.0;
        int i = 0;
        while (i < this.NKGo.UfJC.size()) {
            final ohwW ohwW = this.NKGo.UfJC.get(i);
            if (ohwW.fzNA) {
                for (final kUUQ kuuq : ohwW.znxP) {
                    if (kuuq instanceof nzoK || kuuq instanceof uQsp || kuuq instanceof RwuA || kuuq instanceof IhNF || kuuq instanceof BiVj) {
                        ++n4;
                        if (Zuqu.\u2001\u200a) {
                            throw null;
                        }
                    }
                    else if (kuuq instanceof kmYZ) {
                        ++n4;
                        if (((kmYZ)kuuq).BAIk.OnGi()) {}
                    }
                    if (Zuqu.\u2001\u200a) {
                        throw null;
                    }
                }
            }
            ++n4;
            ++i;
            if (Zuqu.\u2001\u200a) {
                throw null;
            }
        }
        final int n5 = (int)n4;
        this.YsjZ = this.ORoU;
        this.fPbL = this.NKGo.CQIZ + this.NKGo.vjbx + this.ORoU;
        int min = Math.min(240, this.NKGo.UfJC.size() * 12);
        boolean b;
        if (min == 240) {
            b = true;
            if (Zuqu.\u2001\u200a) {
                throw null;
            }
        }
        else {
            b = false;
        }
        final boolean b2 = b;
        int n6 = 0;
        int j = 0;
        while (j < this.NKGo.UfJC.size()) {
            final ohwW ohwW2 = this.NKGo.UfJC.get(j);
            if (b2) {
                if (j > 20) {
                    min -= 12;
                }
                if (ohwW2.fzNA) {
                    for (final kUUQ kuuq2 : ohwW2.znxP) {
                        if (kuuq2 instanceof nzoK || kuuq2 instanceof uQsp || kuuq2 instanceof RwuA || kuuq2 instanceof IhNF || kuuq2 instanceof BiVj) {
                            min -= 12;
                            if (Zuqu.\u2001\u200a) {
                                throw null;
                            }
                        }
                        else if (kuuq2 instanceof kmYZ) {
                            min -= 12;
                            if (((kmYZ)kuuq2).BAIk.OnGi()) {
                                min -= ((kmYZ)kuuq2).BAIk.DxzD.size() * 12;
                            }
                        }
                        if (Zuqu.\u2001\u200a) {
                            throw null;
                        }
                    }
                    if (Zuqu.\u2001\u200a) {
                        throw null;
                    }
                }
            }
            else if (ohwW2.fzNA) {
                for (final kUUQ kuuq3 : ohwW2.znxP) {
                    if (kuuq3 instanceof nzoK || kuuq3 instanceof uQsp || kuuq3 instanceof RwuA || kuuq3 instanceof IhNF || kuuq3 instanceof BiVj) {
                        final int n7 = min;
                        int n8;
                        if (n6 != 0) {
                            n8 = -12;
                            if (Zuqu.\u2001\u200a) {
                                throw null;
                            }
                        }
                        else {
                            n8 = 12;
                        }
                        min = n7 + n8;
                        if (Zuqu.\u2001\u200a) {
                            throw null;
                        }
                    }
                    else if (kuuq3 instanceof kmYZ) {
                        final int n9 = min;
                        int n10;
                        if (n6 != 0) {
                            n10 = -12;
                            if (Zuqu.\u2001\u200a) {
                                throw null;
                            }
                        }
                        else {
                            n10 = 12;
                        }
                        min = n9 + n10;
                        if (((kmYZ)kuuq3).BAIk.OnGi()) {
                            min += ((kmYZ)kuuq3).BAIk.DxzD.size() * 12;
                        }
                    }
                    if (min > 240) {
                        min -= 12;
                        n6 = 1;
                    }
                    if (Zuqu.\u2001\u200a) {
                        throw null;
                    }
                }
            }
            ++j;
            if (Zuqu.\u2001\u200a) {
                throw null;
            }
        }
        if (n6 != 0 && this.NKGo.UfJC.size() != 20) {
            min -= 12;
            if (Zuqu.\u2001\u200a) {
                throw null;
            }
        }
        else if (b2 && this.NKGo.UfJC.size() != 20) {
            min -= 12;
        }
        boolean cmNu;
        if (min == 20) {
            cmNu = true;
            if (Zuqu.\u2001\u200a) {
                throw null;
            }
        }
        else {
            cmNu = false;
        }
        this.CMNu = cmNu;
        final int n11 = this.fPbL + min;
        final float n12 = (float)(this.NKGo.kVnf + this.NKGo.dedb - 2);
        final float n13 = (float)this.fPbL;
        final float n14 = (float)(this.NKGo.kVnf + this.NKGo.dedb);
        final float n15 = (float)n11;
        int rgb;
        if (this.BLjJ || this.SGQw) {
            rgb = new Color(vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).red.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).green.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).blue.OnGi().intValue(), 175).getRGB();
            if (Zuqu.\u2001\u200a) {
                throw null;
            }
        }
        else {
            rgb = -1;
        }
        gnNa.wQYt(n12, n13, n14, n15, rgb);
    }
    
    public void jqFh(final double n, final double n2, final int n3) {
    }
    
    public void uNfw(final double n, final double n2, final int n3) {
        this.SGQw = false;
    }
    
    public void TPrS(double n, final boolean b) {
        if (this.NKGo.aBNN && this.NKGo.iWnD) {
            int n2 = 12;
            if (this.NKGo.UfJC.size() <= 20) {
                n2 = 0;
            }
            double clamp;
            if (b) {
                clamp = 0.0;
                if (Zuqu.\u2001\u200a) {
                    throw null;
                }
            }
            else {
                clamp = MathHelper.clamp(n, 1.0, -1.0);
            }
            n = clamp;
            this.ORoU += (int)(n * 10.0);
            if (this.ORoU < 0) {
                this.ORoU = 0;
            }
            int i = 0;
            while (i < this.NKGo.UfJC.size()) {
                final ohwW ohwW = this.NKGo.UfJC.get(i);
                if (i > Math.min(this.NKGo.UfJC.size(), 20)) {
                    n2 += 12;
                }
                if (ohwW.fzNA) {
                    for (final kUUQ kuuq : ohwW.znxP) {
                        if (kuuq instanceof nzoK) {
                            n2 += 12;
                        }
                        if (kuuq instanceof uQsp) {
                            n2 += 12;
                        }
                        if (kuuq instanceof RwuA) {
                            n2 += 12;
                        }
                        if (kuuq instanceof kmYZ) {
                            n2 += 12;
                            if (((kmYZ)kuuq).BAIk.OnGi()) {
                                n2 += ((kmYZ)kuuq).BAIk.DxzD.size() * 12;
                            }
                        }
                        if (kuuq instanceof IhNF) {
                            n2 += 12;
                        }
                        if (kuuq instanceof BiVj) {
                            n2 += 12;
                        }
                        if (Zuqu.\u2001\u200a) {
                            throw null;
                        }
                    }
                }
                ++i;
                if (Zuqu.\u2001\u200a) {
                    throw null;
                }
            }
            int n3 = (this.NKGo.UfJC.size() - 20) * 12;
            for (final ohwW ohwW2 : this.NKGo.UfJC) {
                if (ohwW2.fzNA) {
                    for (final kUUQ kuuq2 : ohwW2.znxP) {
                        if (!(kuuq2 instanceof kmYZ)) {
                            n3 += 12;
                            n2 -= 12;
                            if (Zuqu.\u2001\u200a) {
                                throw null;
                            }
                        }
                        else if (kuuq2 instanceof kmYZ) {
                            n3 += 12;
                            n2 -= 12;
                            if (((kmYZ)kuuq2).BAIk.OnGi()) {
                                n3 += ((kmYZ)kuuq2).BAIk.DxzD.size() * 12;
                                n2 -= ((kmYZ)kuuq2).BAIk.DxzD.size() * 12;
                            }
                        }
                        if (Zuqu.\u2001\u200a) {
                            throw null;
                        }
                    }
                }
                if (Zuqu.\u2001\u200a) {
                    throw null;
                }
            }
            Math.max(n2, n3);
        }
    }
    
    public void JDhq(final int n) {
    }
}
