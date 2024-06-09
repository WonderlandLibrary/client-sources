package com.client.glowclient;

import net.minecraftforge.fml.client.config.*;
import java.util.*;
import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.item.*;

public class rA extends TA
{
    private GuiButton f;
    private GuiButton M;
    private final String G;
    private qB d;
    private Ld L;
    private final String A;
    private GuiUnicodeGlyphButton B;
    public final List<Bb> b;
    
    public void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == this.B.id) {
                (this.d = this.d.M()).M(this.b);
                this.B.displayString = new StringBuilder().insert(0, " ").append(I18n.format(new StringBuilder().insert(0, "schematica.gui.material").append(this.d.B).toString(), (Object[])new Object[0])).toString();
                this.B.glyph = this.d.L;
                SC.p.set(String.valueOf(this.d));
                SC.A();
                return;
            }
            if (guiButton.id == this.f.id) {
                this.M(this.b);
                return;
            }
            if (guiButton.id == this.M.id) {
                this.mc.displayGuiScreen(this.b);
                return;
            }
            this.L.actionPerformed(guiButton);
        }
    }
    
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.L.handleMouseInput();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.L.drawScreen(n, n2, n3);
        this.drawString(this.fontRenderer, this.G, this.width / 2 - 108, 4, 16777215);
        this.drawString(this.fontRenderer, this.A, this.width / 2 + 108 - this.fontRenderer.getStringWidth(this.A), 4, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    public rA(final GuiScreen guiScreen) {
        final String s = "schematica.gui.materialname";
        final GuiButton guiButton = null;
        final GuiUnicodeGlyphButton b = null;
        super(guiScreen);
        this.d = qB.M(SC.m);
        this.B = b;
        this.f = guiButton;
        this.M = guiButton;
        this.G = I18n.format(s, (Object[])new Object[0]);
        this.A = I18n.format("schematica.gui.materialamount", (Object[])new Object[0]);
        final Minecraft minecraft = Minecraft.getMinecraft();
        this.b = new td().M((EntityPlayer)minecraft.player, eb.g, (World)minecraft.world);
        this.d.M(this.b);
    }
    
    @Override
    public void initGui() {
        int n = 0;
        this.B = new GuiUnicodeGlyphButton(++n, this.width / 2 - 154, this.height - 30, 100, 20, new StringBuilder().insert(0, " ").append(I18n.format(new StringBuilder().insert(0, "schematica.gui.material").append(this.d.B).toString(), (Object[])new Object[0])).toString(), this.d.L, 2.0f);
        this.buttonList.add(this.B);
        ++n;
        this.f = new GuiButton(n, this.width / 2 - 50, this.height - 30, 100, 20, I18n.format("schematica.gui.materialdump", (Object[])new Object[0]));
        this.buttonList.add(this.f);
        ++n;
        this.M = new GuiButton(n, this.width / 2 + 54, this.height - 30, 100, 20, I18n.format("schematica.gui.done", (Object[])new Object[0]));
        this.buttonList.add(this.M);
        this.L = new Ld(this);
    }
    
    public void renderToolTip(final ItemStack itemStack, final int n, final int n2) {
        super.renderToolTip(itemStack, n, n2);
    }
    
    private void M(final List<Bb> p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokeinterface java/util/List.size:()I
        //     6: ifgt            11
        //     9: return         
        //    10: athrow         
        //    11: iconst_0       
        //    12: istore_2       
        //    13: iconst_0       
        //    14: istore_3       
        //    15: aload_1        
        //    16: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    21: dup            
        //    22: astore          4
        //    24: invokeinterface java/util/Iterator.hasNext:()Z
        //    29: ifeq            73
        //    32: aload           4
        //    34: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    39: checkcast       Lcom/client/glowclient/Bb;
        //    42: astore          5
        //    44: iload_2        
        //    45: aload           5
        //    47: invokevirtual   com/client/glowclient/Bb.D:()Ljava/lang/String;
        //    50: invokevirtual   java/lang/String.length:()I
        //    53: invokestatic    java/lang/Math.max:(II)I
        //    56: istore_2       
        //    57: iload_3        
        //    58: aload           5
        //    60: getfield        com/client/glowclient/Bb.A:I
        //    63: invokestatic    java/lang/Math.max:(II)I
        //    66: istore_3       
        //    67: aload           4
        //    69: goto            24
        //    72: athrow         
        //    73: iload_3        
        //    74: invokestatic    java/lang/String.valueOf:(I)Ljava/lang/String;
        //    77: invokevirtual   java/lang/String.length:()I
        //    80: istore          4
        //    82: new             Ljava/lang/StringBuilder;
        //    85: dup            
        //    86: invokespecial   java/lang/StringBuilder.<init>:()V
        //    89: iconst_0       
        //    90: ldc_w           "%-"
        //    93: invokevirtual   java/lang/StringBuilder.insert:(ILjava/lang/String;)Ljava/lang/StringBuilder;
        //    96: iload_2        
        //    97: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   100: ldc_w           "s"
        //   103: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   106: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   109: astore          5
        //   111: new             Ljava/lang/StringBuilder;
        //   114: dup            
        //   115: invokespecial   java/lang/StringBuilder.<init>:()V
        //   118: iconst_0       
        //   119: ldc_w           "%"
        //   122: invokevirtual   java/lang/StringBuilder.insert:(ILjava/lang/String;)Ljava/lang/StringBuilder;
        //   125: iload           4
        //   127: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   130: ldc_w           "d"
        //   133: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   136: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   139: astore_3       
        //   140: new             Ljava/lang/StringBuilder;
        //   143: dup            
        //   144: iload_2        
        //   145: iconst_1       
        //   146: iadd           
        //   147: iload           4
        //   149: iadd           
        //   150: aload_1        
        //   151: invokeinterface java/util/List.size:()I
        //   156: imul           
        //   157: invokespecial   java/lang/StringBuilder.<init>:(I)V
        //   160: astore_2       
        //   161: new             Ljava/util/Formatter;
        //   164: dup            
        //   165: aload_2        
        //   166: invokespecial   java/util/Formatter.<init>:(Ljava/lang/Appendable;)V
        //   169: astore          4
        //   171: aload_1        
        //   172: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   177: dup            
        //   178: astore_1       
        //   179: invokeinterface java/util/Iterator.hasNext:()Z
        //   184: ifeq            263
        //   187: aload_1        
        //   188: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   193: checkcast       Lcom/client/glowclient/Bb;
        //   196: astore          6
        //   198: aload           4
        //   200: aload           5
        //   202: iconst_1       
        //   203: anewarray       Ljava/lang/Object;
        //   206: iconst_1       
        //   207: dup            
        //   208: pop2           
        //   209: dup            
        //   210: iconst_0       
        //   211: aload           6
        //   213: invokevirtual   com/client/glowclient/Bb.D:()Ljava/lang/String;
        //   216: aastore        
        //   217: invokevirtual   java/util/Formatter.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/util/Formatter;
        //   220: aload_2        
        //   221: ldc             " "
        //   223: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   226: pop2           
        //   227: aload           4
        //   229: aload_3        
        //   230: iconst_1       
        //   231: anewarray       Ljava/lang/Object;
        //   234: iconst_1       
        //   235: dup            
        //   236: pop2           
        //   237: dup            
        //   238: iconst_0       
        //   239: aload           6
        //   241: getfield        com/client/glowclient/Bb.A:I
        //   244: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   247: aastore        
        //   248: invokevirtual   java/util/Formatter.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/util/Formatter;
        //   251: aload_2        
        //   252: invokestatic    java/lang/System.lineSeparator:()Ljava/lang/String;
        //   255: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   258: pop2           
        //   259: aload_1        
        //   260: goto            179
        //   263: getstatic       com/client/glowclient/kB.b:Lcom/client/glowclient/bc;
        //   266: ldc_w           "dumps"
        //   269: invokevirtual   com/client/glowclient/bc.M:(Ljava/lang/String;)Ljava/io/File;
        //   272: astore_1       
        //   273: new             Ljava/io/FileOutputStream;
        //   276: dup            
        //   277: new             Ljava/io/File;
        //   280: dup            
        //   281: aload_1        
        //   282: ldc_w           "schematica-materials.txt"
        //   285: invokespecial   java/io/File.<init>:(Ljava/io/File;Ljava/lang/String;)V
        //   288: invokespecial   java/io/FileOutputStream.<init>:(Ljava/io/File;)V
        //   291: astore          6
        //   293: aconst_null    
        //   294: astore_1       
        //   295: aload_2        
        //   296: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   299: aload           6
        //   301: ldc_w           "utf-8"
        //   304: invokestatic    java/nio/charset/Charset.forName:(Ljava/lang/String;)Ljava/nio/charset/Charset;
        //   307: invokestatic    org/apache/commons/io/IOUtils.write:(Ljava/lang/String;Ljava/io/OutputStream;Ljava/nio/charset/Charset;)V
        //   310: aload           6
        //   312: ifnull          399
        //   315: aload_1        
        //   316: ifnull          332
        //   319: aload           6
        //   321: invokevirtual   java/io/FileOutputStream.close:()V
        //   324: return         
        //   325: astore_2       
        //   326: aload_1        
        //   327: aload_2        
        //   328: invokevirtual   java/lang/Throwable.addSuppressed:(Ljava/lang/Throwable;)V
        //   331: return         
        //   332: aload           6
        //   334: invokevirtual   java/io/FileOutputStream.close:()V
        //   337: return         
        //   338: astore_2       
        //   339: aload_2        
        //   340: dup            
        //   341: astore_1       
        //   342: athrow         
        //   343: astore          7
        //   345: aload           6
        //   347: ifnull          380
        //   350: aload_1        
        //   351: ifnull          375
        //   354: aload           6
        //   356: invokevirtual   java/io/FileOutputStream.close:()V
        //   359: aload           7
        //   361: goto            382
        //   364: astore_2       
        //   365: aload           7
        //   367: aload_1        
        //   368: aload_2        
        //   369: invokevirtual   java/lang/Throwable.addSuppressed:(Ljava/lang/Throwable;)V
        //   372: goto            382
        //   375: aload           6
        //   377: invokevirtual   java/io/FileOutputStream.close:()V
        //   380: aload           7
        //   382: athrow         
        //   383: athrow         
        //   384: astore          6
        //   386: getstatic       com/client/glowclient/ld.H:Lorg/apache/logging/log4j/Logger;
        //   389: ldc_w           "Could not dump the material list!"
        //   392: aload           6
        //   394: invokeinterface org/apache/logging/log4j/Logger.error:(Ljava/lang/String;Ljava/lang/Throwable;)V
        //   399: return         
        //    Signature:
        //  (Ljava/util/List<Lcom/client/glowclient/Bb;>;)V
        //    StackMapTable: 00 12 FF 00 0A 00 00 00 01 07 00 71 FD 00 00 07 00 02 07 00 DA FF 00 0C 00 05 07 00 02 07 00 DA 01 01 07 00 F9 00 01 07 00 F9 FF 00 2F 00 00 00 01 07 00 71 FF 00 00 00 05 07 00 02 07 00 DA 01 01 07 00 F9 00 00 FF 00 69 00 06 07 00 02 07 00 F9 07 00 33 07 00 63 07 01 23 07 00 63 00 01 07 00 F9 FB 00 53 FF 00 3D 00 07 07 00 02 05 07 00 33 07 00 63 07 01 23 07 00 63 07 01 41 00 01 07 00 71 06 45 07 00 71 FF 00 04 00 07 07 00 02 07 00 71 07 00 4B 07 00 63 07 01 23 07 00 63 07 01 41 00 01 07 00 71 FF 00 14 00 08 07 00 02 07 00 71 07 00 4B 07 00 63 07 01 23 07 00 63 07 01 41 07 00 71 00 01 07 00 71 0A 04 41 07 00 71 FF 00 00 00 00 00 01 07 00 71 FF 00 00 00 06 07 00 02 07 00 4B 07 00 4B 07 00 63 07 01 23 07 00 63 00 01 07 00 EF FC 00 0E 07 00 4B
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  319    324    325    332    Ljava/lang/Throwable;
        //  295    310    338    343    Ljava/lang/Throwable;
        //  295    310    343    383    Any
        //  354    359    364    375    Ljava/lang/Throwable;
        //  338    345    343    383    Any
        //  273    383    384    399    Ljava/lang/Exception;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
