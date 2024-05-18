package wwe;

import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import wwe.modules.gui.*;
import java.awt.*;
import java.util.*;
import net.minecraft.util.math.*;

public class FinI
{
    public ArrayList<ohwW> UfJC;
    public int kVnf;
    public int CQIZ;
    public int dedb;
    public int vjbx;
    public int eMSV;
    public int Uuyo;
    public Category XLez;
    public boolean hYsX;
    public boolean oBaW;
    public boolean aBNN;
    public boolean iWnD;
    public Zuqu kTWT;
    public int EJcl;
    public int ypmq;
    public int noHS;
    
    public FinI(final Category xLez) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: aload_0        
        //     5: invokespecial   java/lang/Object.<init>:()V
        //     8: aload_0        
        //     9: new             Ljava/util/ArrayList;
        //    12: dup            
        //    13: invokespecial   java/util/ArrayList.<init>:()V
        //    16: putfield        wwe/FinI.UfJC:Ljava/util/ArrayList;
        //    19: aload_0        
        //    20: ldc             5
        //    22: putfield        wwe/FinI.kVnf:I
        //    25: aload_0        
        //    26: ldc             5
        //    28: putfield        wwe/FinI.CQIZ:I
        //    31: aload_0        
        //    32: ldc             88
        //    34: putfield        wwe/FinI.dedb:I
        //    37: aload_0        
        //    38: ldc             13
        //    40: putfield        wwe/FinI.vjbx:I
        //    43: aload_0        
        //    44: aload_1        
        //    45: putfield        wwe/FinI.XLez:Lwwe/modules/Category;
        //    48: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //    51: getfield        wwe/eOnJ.ijZl:Ljava/util/ArrayList;
        //    54: astore_2       
        //    55: aload_2        
        //    56: new             Lwwe/xkKY;
        //    59: dup            
        //    60: aload_0        
        //    61: invokespecial   wwe/xkKY.<init>:(Lwwe/FinI;)V
        //    64: invokevirtual   java/util/ArrayList.sort:(Ljava/util/Comparator;)V
        //    67: aload_0        
        //    68: getfield        wwe/FinI.vjbx:I
        //    71: istore_3       
        //    72: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //    75: invokevirtual   wwe/eOnJ.xRen:()Ljava/util/ArrayList;
        //    78: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //    81: astore          4
        //    83: aload           4
        //    85: invokevirtual   java/util/Iterator.hasNext:()Z
        //    88: ifeq            145
        //    91: aload           4
        //    93: invokevirtual   java/util/Iterator.next:()Ljava/lang/Object;
        //    96: checkcast       Lwwe/qMIe;
        //    99: astore          5
        //   101: aload           5
        //   103: invokevirtual   wwe/qMIe.ufol:()Lwwe/modules/Category;
        //   106: aload_1        
        //   107: invokevirtual   wwe/modules/Category.equals:(Ljava/lang/Object;)Z
        //   110: ifne            116
        //   113: goto            83
        //   116: new             Lwwe/ohwW;
        //   119: dup            
        //   120: aload           5
        //   122: aload_0        
        //   123: iload_3        
        //   124: invokespecial   wwe/ohwW.<init>:(Lwwe/qMIe;Lwwe/FinI;I)V
        //   127: astore          6
        //   129: aload_0        
        //   130: getfield        wwe/FinI.UfJC:Ljava/util/ArrayList;
        //   133: aload           6
        //   135: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   138: pop            
        //   139: iinc            3, 12
        //   142: goto            83
        //   145: return         
        //    StackMapTable: 00 05 FF 00 03 00 00 00 01 07 00 20 FF 00 00 00 08 06 07 00 22 00 00 00 00 00 01 00 00 FF 00 4E 00 08 07 00 02 07 00 22 07 00 27 01 07 00 53 00 00 01 00 00 FF 00 20 00 08 07 00 02 07 00 22 07 00 27 01 07 00 53 07 00 5D 00 01 00 00 FF 00 1C 00 08 07 00 02 07 00 22 07 00 27 01 07 00 53 00 00 01 00 00
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void Lkys(final int n, final int n2, final float n3) {
        this.yveP(0, this.EJcl);
        this.JrYt(0.0, false);
        this.hYsX = this.MgxK(n, n2);
        this.iWnD = this.WFgi(n, n2);
        if (this.oBaW) {
            this.kVnf = n - this.eMSV;
            this.CQIZ = n2 - this.Uuyo;
        }
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        final int scaleFactor = scaledResolution.getScaleFactor();
        GL11.glPushMatrix();
        final int n4 = (int)((scaledResolution.getScaledHeight_double() - this.CQIZ - this.vjbx - 240.0) * scaleFactor);
        final int n5 = 240 * scaleFactor;
        if (vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).scrolling.OnGi().equalsIgnoreCase("Normal")) {
            GL11.glScissor(0, n4, (int)(scaledResolution.getScaledWidth_double() * scaleFactor), n5);
            GL11.glEnable(3089);
        }
        if (this.aBNN) {
            final Iterator<ohwW> iterator = this.UfJC.iterator();
            while (iterator.hasNext()) {
                iterator.next().kUCc(n, n2, n3);
                if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                    throw null;
                }
            }
        }
        if (vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).scrolling.OnGi().equalsIgnoreCase("Normal")) {
            GL11.glDisable(3089);
        }
        GL11.glPopMatrix();
        gnNa.wQYt((float)this.kVnf, (float)this.CQIZ, (float)(this.kVnf + this.dedb), (float)(this.CQIZ + this.vjbx), new Color(vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).red.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).green.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).blue.OnGi().intValue(), 175).getRGB());
        if (this.oBaW) {
            gnNa.wQYt((float)this.kVnf, (float)this.CQIZ, (float)(this.kVnf + this.dedb), (float)(this.CQIZ + this.vjbx), new Color(vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).red.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).green.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).blue.OnGi().intValue()).getRGB());
        }
        XybC.eTim(this.XLez.name(), (float)(this.kVnf + 3), (float)(this.CQIZ + 2), -1);
        if (this.aBNN && vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).toolTips.OnGi()) {
            for (final ohwW ohwW : this.UfJC) {
                if (ohwW.xecA && this.iWnD) {
                    gnNa.dYaT(2.0f, (float)(scaledResolution.getScaledHeight() - 13), (float)(XybC.wKEb().getStringWidth("  " + ohwW.lJcE.GDCZ()) + 2), (float)(scaledResolution.getScaledHeight() + 19), 1.0f, new Color(15, 15, 15).getRGB(), -1);
                    XybC.eTim(ohwW.lJcE.GDCZ(), 6.0f, (float)(scaledResolution.getScaledHeight() - 10), -1);
                    if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                        throw null;
                    }
                }
                else if (ohwW.fzNA && this.iWnD && !ohwW.znxP.isEmpty()) {
                    final Iterator<kUUQ> iterator3 = ohwW.znxP.iterator();
                    while (iterator3.hasNext()) {
                        iterator3.next().Lrih(n, n2, n3, scaledResolution);
                        if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                            throw null;
                        }
                    }
                }
                if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                    throw null;
                }
            }
        }
    }
    
    public void xJWM(final double n, final double n2, final int n3) {
        this.oBaW = false;
    }
    
    public void LXFg(final int n) {
    }
    
    public void XzAP(final double n) {
        if (vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).scrolling.OnGi().equalsIgnoreCase("All")) {
            if (n < 0.0) {
                this.CQIZ -= 5;
                if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                    throw null;
                }
            }
            else if (n > 0.0) {
                this.CQIZ += 5;
            }
        }
        if (this.aBNN && this.iWnD) {
            this.JrYt(n, true);
        }
    }
    
    public void yveP(final int n, final int n2) {
        int yQtC = this.vjbx + n - n2;
        final ArrayList<Integer> list = new ArrayList<Integer>();
        int i = 0;
        while (i < this.UfJC.size()) {
            final ohwW ohwW = this.UfJC.get(i);
            if (ohwW.fzNA) {
                list.add(i + 1);
            }
            ohwW.yQtC = yQtC;
            yQtC += 12;
            final Iterator<Integer> iterator = list.iterator();
            while (iterator.hasNext()) {
                if (i + 1 == iterator.next()) {
                    int n3 = -12;
                    for (final kUUQ kuuq : ohwW.znxP) {
                        if (kuuq instanceof nzoK) {
                            n3 += 12;
                            ((nzoK)kuuq).jXta = yQtC + n3;
                        }
                        if (kuuq instanceof uQsp) {
                            n3 += 12;
                            ((uQsp)kuuq).jXta = yQtC + n3;
                        }
                        if (kuuq instanceof RwuA) {
                            n3 += 12;
                            ((RwuA)kuuq).jXta = yQtC + n3;
                        }
                        if (kuuq instanceof kmYZ) {
                            n3 += 12;
                            ((kmYZ)kuuq).jXta = yQtC + n3;
                            if (((kmYZ)kuuq).BAIk.OnGi()) {
                                int n4 = 0;
                                for (final kUUQ kuuq2 : ((kmYZ)kuuq).CYJH) {
                                    if (kuuq2 instanceof nzoK) {
                                        n3 += 12;
                                        ((nzoK)kuuq2).jXta = yQtC + n3;
                                        n4 += 12;
                                    }
                                    if (kuuq2 instanceof uQsp) {
                                        n3 += 12;
                                        ((uQsp)kuuq2).jXta = yQtC + n3;
                                        n4 += 12;
                                    }
                                    if (kuuq2 instanceof RwuA) {
                                        n3 += 12;
                                        ((RwuA)kuuq2).jXta = yQtC + n3;
                                        n4 += 12;
                                    }
                                    if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                                        throw null;
                                    }
                                }
                                yQtC += ((kmYZ)kuuq).BAIk.DxzD.size() * 12;
                                n3 -= n4;
                            }
                        }
                        if (kuuq instanceof XwHe) {
                            n3 += 12;
                            ((XwHe)kuuq).jXta = yQtC + n3;
                        }
                        if (kuuq instanceof IhNF) {
                            n3 += 12;
                            ((IhNF)kuuq).jXta = yQtC + n3;
                        }
                        if (kuuq instanceof BiVj) {
                            n3 += 12;
                            ((BiVj)kuuq).jXta = yQtC + n3;
                        }
                        if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                            throw null;
                        }
                    }
                    yQtC += ohwW.znxP.size() * 12;
                }
                if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                    throw null;
                }
            }
            ++i;
            if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                throw null;
            }
        }
    }
    
    public void UkgR(final float n, final float n2, final float n3, final float n4) {
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        final int scaleFactor = scaledResolution.getScaleFactor();
        GL11.glScissor((int)(n * scaleFactor), (int)((scaledResolution.getScaledHeight() - n4) * scaleFactor), (int)((n3 - n) * scaleFactor), (int)((n4 - n2) * scaleFactor));
    }
    
    public boolean MgxK(final int n, final int n2) {
        boolean b;
        if (n >= this.kVnf && n <= this.kVnf + this.dedb && n2 >= this.CQIZ && n2 < this.CQIZ + this.vjbx) {
            b = true;
            if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public boolean dEfG(final int n, final int n2) {
        int n3 = 0;
        int i = 0;
        while (i < this.UfJC.size()) {
            final ohwW ohwW = this.UfJC.get(i);
            if (ohwW.fzNA) {
                for (final kUUQ kuuq : ohwW.znxP) {
                    if (kuuq instanceof kmYZ) {
                        ++n3;
                        if (((kmYZ)kuuq).BAIk.OnGi()) {
                            n3 += ((kmYZ)kuuq).BAIk.DxzD.size();
                            if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                                throw null;
                            }
                        }
                    }
                    else {
                        ++n3;
                    }
                    if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                        throw null;
                    }
                }
            }
            ++n3;
            ++i;
            if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                throw null;
            }
        }
        if (n >= this.kVnf && n <= this.kVnf + this.dedb && n2 >= this.CQIZ) {
            final int n4 = this.CQIZ + this.vjbx;
            final int n5 = 12;
            int n6;
            if (!this.aBNN) {
                n6 = 0;
                if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                    throw null;
                }
            }
            else if (vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).scrolling.OnGi().equalsIgnoreCase("Normal")) {
                n6 = 20;
                if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                    throw null;
                }
            }
            else {
                n6 = n3;
            }
            if (n2 < n4 + n5 * n6) {
                final boolean b = true;
                if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                    throw null;
                }
                return b;
            }
        }
        return false;
    }
    
    public boolean WFgi(final int n, final int n2) {
        int n3 = 0;
        int i = 0;
        while (i < this.UfJC.size()) {
            final ohwW ohwW = this.UfJC.get(i);
            if (ohwW.fzNA) {
                for (final kUUQ kuuq : ohwW.znxP) {
                    if (kuuq instanceof kmYZ) {
                        ++n3;
                        if (((kmYZ)kuuq).BAIk.OnGi()) {
                            n3 += ((kmYZ)kuuq).BAIk.DxzD.size();
                            if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                                throw null;
                            }
                        }
                    }
                    else {
                        ++n3;
                    }
                    if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                        throw null;
                    }
                }
            }
            ++n3;
            ++i;
            if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                throw null;
            }
        }
        if (n >= this.kVnf && n <= this.kVnf + this.dedb && n2 >= this.CQIZ + this.vjbx) {
            final int n4 = this.CQIZ + this.vjbx;
            final int n5 = 12;
            int n6;
            if (vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).scrolling.OnGi().equalsIgnoreCase("Normal")) {
                n6 = 20;
                if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                    throw null;
                }
            }
            else {
                n6 = n3;
            }
            if (n2 < n4 + n5 * n6) {
                final boolean b = true;
                if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                    throw null;
                }
                return b;
            }
        }
        return false;
    }
    
    public void JrYt(double clamp, final boolean b) {
        int n = 12;
        if (this.UfJC.size() <= 20) {
            n = 0;
        }
        clamp = MathHelper.clamp(clamp, 1.0, -1.0);
        if (b && vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).scrolling.OnGi().equalsIgnoreCase("Normal")) {
            this.EJcl += (int)(clamp * 10.0);
        }
        if (this.EJcl < 0) {
            this.EJcl = 0;
        }
        int i = 0;
        while (i < this.UfJC.size()) {
            final ohwW ohwW = this.UfJC.get(i);
            if (i > Math.min(this.UfJC.size(), 20)) {
                n += 12;
            }
            if (ohwW.fzNA) {
                for (final kUUQ kuuq : ohwW.znxP) {
                    if (kuuq instanceof nzoK) {
                        n += 12;
                    }
                    if (kuuq instanceof uQsp) {
                        n += 12;
                    }
                    if (kuuq instanceof RwuA) {
                        n += 12;
                    }
                    if (kuuq instanceof kmYZ) {
                        n += 12;
                        if (((kmYZ)kuuq).BAIk.OnGi()) {
                            n += ((kmYZ)kuuq).BAIk.DxzD.size() * 12;
                        }
                    }
                    if (kuuq instanceof XwHe) {
                        n += 12;
                    }
                    if (kuuq instanceof IhNF) {
                        n += 12;
                    }
                    if (kuuq instanceof BiVj) {
                        n += 12;
                    }
                    if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                        throw null;
                    }
                }
            }
            ++i;
            if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                throw null;
            }
        }
        int n2 = (this.UfJC.size() - 20) * 12;
        for (final ohwW ohwW2 : this.UfJC) {
            if (ohwW2.fzNA) {
                for (final kUUQ kuuq2 : ohwW2.znxP) {
                    if (!(kuuq2 instanceof kmYZ)) {
                        n2 += 12;
                        n -= 12;
                        if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                            throw null;
                        }
                    }
                    else if (kuuq2 instanceof kmYZ) {
                        n2 += 12;
                        n -= 12;
                        if (((kmYZ)kuuq2).BAIk.OnGi()) {
                            n2 += ((kmYZ)kuuq2).BAIk.DxzD.size() * 12;
                            n -= ((kmYZ)kuuq2).BAIk.DxzD.size() * 12;
                        }
                    }
                    if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                        throw null;
                    }
                }
            }
            if (FinI.\u200e\u200c\u2006\u2007\u2006\u2009\u200f\u200d\u200d) {
                throw null;
            }
        }
        final int max = Math.max(n, n2);
        if (this.EJcl > Math.abs(max)) {
            this.EJcl = max;
        }
    }
}
