package wwe;

import java.util.*;
import wwe.utils.*;
import java.awt.*;
import net.minecraft.util.text.*;
import net.minecraft.client.gui.*;
import java.io.*;

public class Kdwt
{
    public int ByAc;
    public int AzOt;
    public cJGk uKMJ;
    public GuiScreen TRqF;
    public ArrayList<iZvY> TXHL;
    
    public Kdwt(final cJGk ukmj, final GuiScreen tRqF, final int byAc, final int azOt) {
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
        //    16: putfield        wwe/Kdwt.TXHL:Ljava/util/ArrayList;
        //    19: aload_0        
        //    20: aload_1        
        //    21: putfield        wwe/Kdwt.uKMJ:Lwwe/cJGk;
        //    24: aload_0        
        //    25: aload_2        
        //    26: putfield        wwe/Kdwt.TRqF:Lnet/minecraft/client/gui/GuiScreen;
        //    29: aload_0        
        //    30: iload_3        
        //    31: putfield        wwe/Kdwt.ByAc:I
        //    34: aload_0        
        //    35: iload           4
        //    37: putfield        wwe/Kdwt.AzOt:I
        //    40: aload_0        
        //    41: getfield        wwe/Kdwt.uKMJ:Lwwe/cJGk;
        //    44: getfield        wwe/cJGk.kBoK:Lnet/minecraft/util/ResourceLocation;
        //    47: ifnonnull       62
        //    50: new             Lwwe/ndZI;
        //    53: dup            
        //    54: aload_0        
        //    55: aload_1        
        //    56: invokespecial   wwe/ndZI.<init>:(Lwwe/Kdwt;Lwwe/cJGk;)V
        //    59: invokevirtual   wwe/ndZI.start:()V
        //    62: return         
        //    StackMapTable: 00 03 FF 00 03 00 00 00 01 07 00 14 FF 00 00 00 06 06 07 00 16 07 00 18 01 01 01 00 00 FF 00 39 00 06 07 00 02 07 00 16 07 00 18 01 01 01 00 00
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void FDgX(final int n, final int n2, final float n3) {
        if (this.uKMJ.kBoK == null && this.uKMJ.ocQD != null) {
            this.uKMJ.kBoK = FIwW.dkaH(this.uKMJ.ocQD, this.uKMJ.umix.toString() + "?size=50");
        }
        if (this.TRqF instanceof Wwox) {
            final float n4 = (float)(this.ByAc - 1);
            final float n5 = (float)(this.AzOt - 1);
            final float n6 = (float)(this.ByAc + 35 + Wrapper.fontRenderer.getStringWidth("O") * 16 + 3);
            final float n7 = (float)(this.AzOt + 31);
            int n8;
            if (((Wwox)this.TRqF).inUT == this) {
                n8 = new Color(0, 0, 0).getRGB();
                if (Kdwt.\u2002\u2009\u2004\u200d\u2003\u2000\u200d\u2009\u2005) {
                    throw null;
                }
            }
            else {
                n8 = new Color(0, 0, 0, 100).getRGB();
            }
            gnNa.wQYt(n4, n5, n6, n7, n8);
            if (Kdwt.\u2002\u2009\u2004\u200d\u2003\u2000\u200d\u2009\u2005) {
                throw null;
            }
        }
        else {
            gnNa.wQYt((float)(this.ByAc - 1), (float)(this.AzOt - 1), (float)(this.ByAc + 35 + Wrapper.fontRenderer.getStringWidth("O") * 16 + 3), (float)(this.AzOt + 31), new Color(0, 0, 0, 100).getRGB());
        }
        if (this.uKMJ.kBoK != null) {
            Wrapper.mc.getTextureManager().bindTexture(this.uKMJ.kBoK);
            Gui.drawModalRectWithCustomSizedTexture(this.ByAc, this.AzOt, 0.0f, 0.0f, 30, 30, 30.0f, 30.0f);
        }
        gnNa.NOOc();
        final int n9 = this.ByAc + 25;
        final int n10 = this.AzOt + 25;
        final float n11 = 3.0f;
        int n12;
        if (this.uKMJ.PqmF == 0) {
            n12 = -7895161;
            if (Kdwt.\u2002\u2009\u2004\u200d\u2003\u2000\u200d\u2009\u2005) {
                throw null;
            }
        }
        else if (this.uKMJ.PqmF == 1) {
            n12 = -3727585;
            if (Kdwt.\u2002\u2009\u2004\u200d\u2003\u2000\u200d\u2009\u2005) {
                throw null;
            }
        }
        else if (this.uKMJ.PqmF == 2) {
            n12 = -14301409;
            if (Kdwt.\u2002\u2009\u2004\u200d\u2003\u2000\u200d\u2009\u2005) {
                throw null;
            }
        }
        else {
            n12 = -7895161;
        }
        gnNa.QFoa(n9, n10, n11, n12);
        gnNa.uCta();
        if (this.uKMJ.URwX != null) {
            Wrapper.fontRenderer.drawStringWithShadow(this.uKMJ.URwX, (float)(this.ByAc + 35), (float)(this.AzOt + 2), -1);
        }
        if (this.uKMJ.KxEN != null && this.uKMJ.eFSq != null) {
            Wrapper.fontRenderer.drawStringWithShadow(this.uKMJ.KxEN + " " + this.uKMJ.eFSq, (float)(this.ByAc + 35), (float)(this.AzOt + 12), -1);
        }
        if (this.uKMJ.smby != 0) {
            final FontRenderer fontRenderer = Wrapper.fontRenderer;
            String s;
            if (this.uKMJ.smby == 1) {
                s = TextFormatting.YELLOW + "Donator";
                if (Kdwt.\u2002\u2009\u2004\u200d\u2003\u2000\u200d\u2009\u2005) {
                    throw null;
                }
            }
            else if (this.uKMJ.smby == 2) {
                s = TextFormatting.RED + "Developer";
                if (Kdwt.\u2002\u2009\u2004\u200d\u2003\u2000\u200d\u2009\u2005) {
                    throw null;
                }
            }
            else {
                s = "";
            }
            fontRenderer.drawString(s, this.ByAc + 35, this.AzOt + 22, -1);
        }
    }
    
    public void zXDD(final int n, final int n2, final int n3) throws IOException {
    }
}
