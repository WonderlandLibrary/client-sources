package wwe;

import net.minecraft.util.*;
import wwe.utils.*;
import java.util.*;
import net.minecraft.client.gui.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.*;

public class Lnec
{
    public String wcvo;
    public String LYEe;
    public ResourceLocation PkRG;
    public int XMpd;
    public int ZwCC;
    public int Bcna;
    public int uvzZ;
    public boolean mnmo;
    public int gYaC;
    public int DplW;
    public BufferedImage JOKI;
    public String GExK;
    
    public Lnec(final String wcvo, final String lyEe, final String s) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: aload_0        
        //     5: invokespecial   java/lang/Object.<init>:()V
        //     8: aload_0        
        //     9: ldc             -1
        //    11: putfield        wwe/Lnec.DplW:I
        //    14: aload_0        
        //    15: aload_1        
        //    16: putfield        wwe/Lnec.wcvo:Ljava/lang/String;
        //    19: aload_0        
        //    20: aload_2        
        //    21: putfield        wwe/Lnec.LYEe:Ljava/lang/String;
        //    24: aload_0        
        //    25: new             Lnet/minecraft/util/ResourceLocation;
        //    28: dup            
        //    29: ldc             "textures/wwe/unknown.png"
        //    31: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //    34: putfield        wwe/Lnec.PkRG:Lnet/minecraft/util/ResourceLocation;
        //    37: new             Lwwe/pCUN;
        //    40: dup            
        //    41: aload_0        
        //    42: aload_3        
        //    43: invokespecial   wwe/pCUN.<init>:(Lwwe/Lnec;Ljava/lang/String;)V
        //    46: invokevirtual   wwe/pCUN.start:()V
        //    49: return         
        //    StackMapTable: 00 02 FF 00 03 00 00 00 01 07 00 29 FF 00 00 00 05 06 07 00 2B 07 00 2B 07 00 2B 01 00 00
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public Lnec(final String wcvo, final String lyEe, final String s, final boolean b) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: aload_0        
        //     5: invokespecial   java/lang/Object.<init>:()V
        //     8: aload_0        
        //     9: ldc             -1
        //    11: putfield        wwe/Lnec.DplW:I
        //    14: aload_0        
        //    15: aload_1        
        //    16: putfield        wwe/Lnec.wcvo:Ljava/lang/String;
        //    19: aload_0        
        //    20: aload_2        
        //    21: putfield        wwe/Lnec.LYEe:Ljava/lang/String;
        //    24: aload_0        
        //    25: new             Lnet/minecraft/util/ResourceLocation;
        //    28: dup            
        //    29: ldc             "textures/wwe/unknown.png"
        //    31: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //    34: putfield        wwe/Lnec.PkRG:Lnet/minecraft/util/ResourceLocation;
        //    37: iload           4
        //    39: ifeq            58
        //    42: new             Lwwe/gaWS;
        //    45: dup            
        //    46: aload_0        
        //    47: aload_2        
        //    48: aload_3        
        //    49: invokespecial   wwe/gaWS.<init>:(Lwwe/Lnec;Ljava/lang/String;Ljava/lang/String;)V
        //    52: invokevirtual   wwe/gaWS.start:()V
        //    55: goto            71
        //    58: new             Lwwe/iARD;
        //    61: dup            
        //    62: aload_0        
        //    63: aload_2        
        //    64: aload_3        
        //    65: invokespecial   wwe/iARD.<init>:(Lwwe/Lnec;Ljava/lang/String;Ljava/lang/String;)V
        //    68: invokevirtual   wwe/iARD.start:()V
        //    71: return         
        //    StackMapTable: 00 04 FF 00 03 00 00 00 01 07 00 29 FF 00 00 00 06 06 07 00 2B 07 00 2B 07 00 2B 01 01 00 00 FF 00 35 00 06 07 00 02 07 00 2B 07 00 2B 07 00 2B 01 01 00 00 0C
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public Lnec(final String wcvo, final String lyEe, final ResourceLocation pkRG) {
        super();
        this.DplW = -1;
        this.wcvo = wcvo;
        this.LYEe = lyEe;
        this.PkRG = pkRG;
    }
    
    public void xvET(final int n, final int n2, final float n3) {
        if (this.DplW != -1) {
            this.PkRG = FIwW.dkaH(this.JOKI, this.GExK);
            this.DplW = -1;
        }
        if (this.PkRG == null) {
            return;
        }
        boolean mnmo;
        if (n >= this.XMpd && n <= this.XMpd + 100 && n2 >= this.ZwCC && n2 <= this.ZwCC + 100) {
            mnmo = true;
            if (Lnec.\u2000\u200d\u2006\u200d\u2005\u200f\u200e\u200b) {
                throw null;
            }
        }
        else {
            mnmo = false;
        }
        this.mnmo = mnmo;
        Wrapper.mc.getTextureManager().bindTexture(this.PkRG);
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        Gui.drawModalRectWithCustomSizedTexture(this.XMpd, this.ZwCC, 0.0f, 0.0f, 100, 100, 100.0f, 100.0f);
        if (this.mnmo) {
            ++this.gYaC;
            int stringWidth = 0;
            final ArrayList<String> dDuY = FIwW.DDuY(this.wcvo + " : " + this.LYEe, 24);
            for (final String s : dDuY) {
                if (Wrapper.fontRenderer.getStringWidth(s) > stringWidth) {
                    stringWidth = Wrapper.fontRenderer.getStringWidth(s);
                }
                if (Lnec.\u2000\u200d\u2006\u200d\u2005\u200f\u200e\u200b) {
                    throw null;
                }
            }
            gnNa.NOOc();
            final int xMpd = this.XMpd;
            final int n4 = this.ZwCC - 5;
            final int n5 = this.XMpd + stringWidth;
            final int n6 = this.ZwCC - 5 - 10;
            int n7;
            if (dDuY.size() == 1) {
                n7 = 0;
                if (Lnec.\u2000\u200d\u2006\u200d\u2005\u200f\u200e\u200b) {
                    throw null;
                }
            }
            else {
                n7 = (dDuY.size() - 1) * 10;
            }
            gnNa.AVqM(xMpd, n4, n5, n6 - n7, 3.0f, dDuY.size(), new Color(56, 54, 54).getRGB());
            gnNa.uCta();
            int i = 0;
            while (i < dDuY.size()) {
                final String s2 = dDuY.get(i);
                final FontRenderer fontRenderer = Wrapper.fontRenderer;
                final String s3 = s2;
                final float n8 = (float)this.XMpd;
                final int n9 = this.ZwCC - 5 - 10;
                int n10;
                if (dDuY.size() == 1) {
                    n10 = 0;
                    if (Lnec.\u2000\u200d\u2006\u200d\u2005\u200f\u200e\u200b) {
                        throw null;
                    }
                }
                else {
                    n10 = (dDuY.size() - 1) * 10;
                }
                fontRenderer.drawStringWithShadow(s3, n8, (float)(n9 - n10 + i * 10), -1);
                ++i;
                if (Lnec.\u2000\u200d\u2006\u200d\u2005\u200f\u200e\u200b) {
                    throw null;
                }
            }
            if (Lnec.\u2000\u200d\u2006\u200d\u2005\u200f\u200e\u200b) {
                throw null;
            }
        }
        else {
            this.gYaC = 0;
        }
    }
    
    public static BufferedImage tCKN(final BufferedImage bufferedImage, final int n) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final BufferedImage bufferedImage2 = new BufferedImage(width, height, 2);
        final Graphics2D graphics = bufferedImage2.createGraphics();
        graphics.setComposite(AlphaComposite.Src);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.WHITE);
        graphics.fill(new RoundRectangle2D.Float(0.0f, 0.0f, (float)width, (float)height, (float)n, (float)n));
        graphics.setComposite(AlphaComposite.SrcIn);
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.dispose();
        return bufferedImage2;
    }
}
