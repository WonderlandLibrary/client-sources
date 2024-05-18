package wwe;

import net.minecraft.client.gui.*;
import wwe.modules.*;
import wwe.utils.*;
import wwe.modules.gui.*;
import java.io.*;
import org.lwjgl.input.*;
import java.util.*;

public class hEGx extends GuiScreen
{
    public ArrayList<FinI> nYFA;
    public static XdhZ aTlJ;
    public GuiTextField mwSh;
    
    public hEGx() {
        super();
        this.nYFA = new ArrayList<FinI>();
        int n = 25;
        final AzzV[] array = (AzzV[])Category.fgCR();
        for (int length = array.length, i = 0; i < length; ++i) {
            final FinI finI = new FinI((Category)array[i]);
            finI.CQIZ = n;
            this.nYFA.add(finI);
            finI.ypmq = finI.kVnf;
            finI.noHS = finI.CQIZ;
            n += 28;
        }
        hEGx.aTlJ.zzJD = n;
        hEGx.aTlJ.KyWB = hEGx.aTlJ.OsiI;
        hEGx.aTlJ.PVCD = hEGx.aTlJ.zzJD;
    }
    
    public void func_73866_w_() {
        this.mwSh = new GuiTextField(0, Wrapper.fontRenderer, this.width / 2 - 100, this.height - 21, 200, 20);
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        if (vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).search.OnGi()) {
            this.mwSh.drawTextBox();
        }
        int i = 0;
        while (i < this.nYFA.size()) {
            this.nYFA.get(i).Lkys(n, n2, n3);
            ++i;
            if (hEGx.\u2008\u2007\u2006\u2002\u200d) {
                throw null;
            }
        }
        hEGx.aTlJ.OQea(n, n2, n3);
        final Iterator<NoaN> iterator = hEGx.aTlJ.HSDz.iterator();
        while (iterator.hasNext()) {
            iterator.next().QdjS(n, n2);
            if (hEGx.\u2008\u2007\u2006\u2002\u200d) {
                throw null;
            }
        }
    }
    
    public void func_73876_c() {
        if (vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).search.OnGi()) {
            this.mwSh.updateCursorCounter();
        }
        super.updateScreen();
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        FinI finI = null;
        int i = 0;
        while (i < this.nYFA.size()) {
            final FinI finI2 = this.nYFA.get(i);
            if (finI2.MgxK(n, n2)) {
                finI = finI2;
            }
            ++i;
            if (hEGx.\u2008\u2007\u2006\u2002\u200d) {
                throw null;
            }
        }
        if (finI != null) {
            final ArrayList<FinI> nyfa = new ArrayList<FinI>();
            for (final FinI finI3 : this.nYFA) {
                if (finI3 != finI) {
                    nyfa.add(finI3);
                }
                if (hEGx.\u2008\u2007\u2006\u2002\u200d) {
                    throw null;
                }
            }
            nyfa.add(finI);
            this.nYFA = nyfa;
        }
        int n4 = -1;
        int j = this.nYFA.size() - 1;
        while (j >= 0) {
            if (this.nYFA.get(j).dEfG(n, n2)) {
                n4 = j;
                if (hEGx.\u2008\u2007\u2006\u2002\u200d) {
                    throw null;
                }
                break;
            }
            else {
                --j;
                if (hEGx.\u2008\u2007\u2006\u2002\u200d) {
                    throw null;
                }
                continue;
            }
        }
        if (n4 != -1) {
            final FinI finI4 = this.nYFA.get(n4);
            if (finI4.MgxK(n, n2)) {
                finI4.oBaW = true;
                finI4.eMSV = n - finI4.kVnf;
                finI4.Uuyo = n2 - finI4.CQIZ;
                if (hEGx.\u2008\u2007\u2006\u2002\u200d) {
                    throw null;
                }
            }
            else if (finI4.MgxK(n, n2) && n3 == 1) {
                final FinI finI5 = finI4;
                boolean abnn;
                if (!finI4.aBNN) {
                    abnn = true;
                    if (hEGx.\u2008\u2007\u2006\u2002\u200d) {
                        throw null;
                    }
                }
                else {
                    abnn = false;
                }
                finI5.aBNN = abnn;
                if (hEGx.\u2008\u2007\u2006\u2002\u200d) {
                    throw null;
                }
            }
            else if (finI4.aBNN && !finI4.UfJC.isEmpty()) {
                final Iterator<ohwW> iterator2 = finI4.UfJC.iterator();
                while (iterator2.hasNext()) {
                    iterator2.next().PFLg(n, n2, n3);
                    if (hEGx.\u2008\u2007\u2006\u2002\u200d) {
                        throw null;
                    }
                }
            }
        }
        hEGx.aTlJ.KolI(n, n2, n3);
        if (vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).search.OnGi()) {
            this.mwSh.mouseClicked(n, n2, n3);
        }
    }
    
    public void func_146286_b(final int n, final int n2, final int n3) {
        int i = 0;
        while (i < this.nYFA.size()) {
            final FinI finI = this.nYFA.get(i);
            finI.xJWM(n, n2, n3);
            if (finI.aBNN) {
                final Iterator<ohwW> iterator = finI.UfJC.iterator();
                while (iterator.hasNext()) {
                    iterator.next().EDua(n, n2, n3);
                    if (hEGx.\u2008\u2007\u2006\u2002\u200d) {
                        throw null;
                    }
                }
            }
            ++i;
            if (hEGx.\u2008\u2007\u2006\u2002\u200d) {
                throw null;
            }
        }
        hEGx.aTlJ.ioOs = false;
        if (hEGx.aTlJ.wMBz && !hEGx.aTlJ.HSDz.isEmpty()) {
            final Iterator<NoaN> iterator2 = hEGx.aTlJ.HSDz.iterator();
            while (iterator2.hasNext()) {
                iterator2.next().XrGT(n, n2, n3);
                if (hEGx.\u2008\u2007\u2006\u2002\u200d) {
                    throw null;
                }
            }
        }
    }
    
    protected void func_73869_a(final char c, final int n) {
        if (n == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        int i = 0;
        while (i < this.nYFA.size()) {
            final FinI finI = this.nYFA.get(i);
            finI.LXFg(n);
            if (finI.aBNN) {
                final Iterator<ohwW> iterator = finI.UfJC.iterator();
                while (iterator.hasNext()) {
                    iterator.next().WRUj(n);
                    if (hEGx.\u2008\u2007\u2006\u2002\u200d) {
                        throw null;
                    }
                }
            }
            ++i;
            if (hEGx.\u2008\u2007\u2006\u2002\u200d) {
                throw null;
            }
        }
        if (hEGx.aTlJ.wMBz && n != 1 && !hEGx.aTlJ.HSDz.isEmpty()) {
            final Iterator<NoaN> iterator2 = hEGx.aTlJ.HSDz.iterator();
            while (iterator2.hasNext()) {
                iterator2.next().WwPa(n);
                if (hEGx.\u2008\u2007\u2006\u2002\u200d) {
                    throw null;
                }
            }
        }
        if (vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).search.OnGi() && this.mwSh.textboxKeyTyped(c, n)) {
            this.neJc(false);
        }
    }
    
    public void func_146281_b() {
        if (vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).search.OnGi()) {
            this.neJc(true);
        }
        Wrapper.ypJK().VvWw();
    }
    
    public void func_146274_d() throws IOException {
        final int dWheel = Mouse.getDWheel();
        if (dWheel > 0 || dWheel < 0) {
            int i = 0;
            while (i < this.nYFA.size()) {
                final FinI finI = this.nYFA.get(i);
                finI.XzAP(dWheel);
                if (finI.aBNN) {
                    final Iterator<ohwW> iterator = finI.UfJC.iterator();
                    while (iterator.hasNext()) {
                        iterator.next().XBgs(dWheel);
                        if (hEGx.\u2008\u2007\u2006\u2002\u200d) {
                            throw null;
                        }
                    }
                }
                ++i;
                if (hEGx.\u2008\u2007\u2006\u2002\u200d) {
                    throw null;
                }
            }
            hEGx.aTlJ.odQY(dWheel);
        }
        super.handleMouseInput();
    }
    
    public void neJc(final boolean b) {
        class cbMC implements Comparator<qMIe>
        {
            final hEGx qLlg;
            
            cbMC(final hEGx qLlg) {
                this.qLlg = qLlg;
                super();
            }
            
            public int XTrG(final qMIe qmIe, final qMIe qmIe2) {
                return qmIe.czUH().compareTo(qmIe2.czUH());
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.XTrG((qMIe)o, (qMIe)o2);
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: return         
        //     4: ldc             0
        //     6: istore_2       
        //     7: iload_2        
        //     8: aload_0        
        //     9: getfield        wwe/hEGx.nYFA:Ljava/util/ArrayList;
        //    12: invokevirtual   java/util/ArrayList.size:()I
        //    15: if_icmpge       261
        //    18: aload_0        
        //    19: getfield        wwe/hEGx.nYFA:Ljava/util/ArrayList;
        //    22: iload_2        
        //    23: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //    26: checkcast       Lwwe/FinI;
        //    29: astore_3       
        //    30: aload_3        
        //    31: getfield        wwe/FinI.UfJC:Ljava/util/ArrayList;
        //    34: invokevirtual   java/util/ArrayList.clear:()V
        //    37: aload_3        
        //    38: ldc             0
        //    40: putfield        wwe/FinI.EJcl:I
        //    43: aload_3        
        //    44: getfield        wwe/FinI.vjbx:I
        //    47: istore          4
        //    49: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //    52: getfield        wwe/eOnJ.ijZl:Ljava/util/ArrayList;
        //    55: astore          5
        //    57: aload           5
        //    59: new             Lwwe/cbMC;
        //    62: dup            
        //    63: aload_0        
        //    64: invokespecial   wwe/cbMC.<init>:(Lwwe/hEGx;)V
        //    67: invokevirtual   java/util/ArrayList.sort:(Ljava/util/Comparator;)V
        //    70: ldc             0
        //    72: istore          6
        //    74: iload           6
        //    76: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //    79: invokevirtual   wwe/eOnJ.xRen:()Ljava/util/ArrayList;
        //    82: invokevirtual   java/util/ArrayList.size:()I
        //    85: if_icmpge       245
        //    88: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //    91: invokevirtual   wwe/eOnJ.xRen:()Ljava/util/ArrayList;
        //    94: iload           6
        //    96: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //    99: checkcast       Lwwe/qMIe;
        //   102: astore          7
        //   104: aload_0        
        //   105: getfield        wwe/hEGx.mwSh:Lnet/minecraft/client/gui/GuiTextField;
        //   108: invokevirtual   net/minecraft/client/gui/GuiTextField.getText:()Ljava/lang/String;
        //   111: invokevirtual   java/lang/String.isEmpty:()Z
        //   114: ifne            174
        //   117: aload           7
        //   119: invokevirtual   wwe/qMIe.czUH:()Ljava/lang/String;
        //   122: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //   125: aload_0        
        //   126: getfield        wwe/hEGx.mwSh:Lnet/minecraft/client/gui/GuiTextField;
        //   129: invokevirtual   net/minecraft/client/gui/GuiTextField.getText:()Ljava/lang/String;
        //   132: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //   135: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   138: ifne            174
        //   141: aload           7
        //   143: invokevirtual   wwe/qMIe.czUH:()Ljava/lang/String;
        //   146: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //   149: ldc_w           " "
        //   152: ldc_w           ""
        //   155: invokevirtual   java/lang/String.replaceAll:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   158: aload_0        
        //   159: getfield        wwe/hEGx.mwSh:Lnet/minecraft/client/gui/GuiTextField;
        //   162: invokevirtual   net/minecraft/client/gui/GuiTextField.getText:()Ljava/lang/String;
        //   165: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //   168: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   171: ifeq            229
        //   174: aload           7
        //   176: invokevirtual   wwe/qMIe.ufol:()Lwwe/modules/Category;
        //   179: aload_3        
        //   180: getfield        wwe/FinI.XLez:Lwwe/modules/Category;
        //   183: invokevirtual   wwe/modules/Category.equals:(Ljava/lang/Object;)Z
        //   186: ifne            202
        //   189: getstatic       wwe/hEGx.\u2008\u2007\u2006\u2002\u200d:Z
        //   192: ifeq            229
        //   195: aconst_null    
        //   196: athrow         
        //   197: nop            
        //   198: nop            
        //   199: nop            
        //   200: nop            
        //   201: athrow         
        //   202: new             Lwwe/ohwW;
        //   205: dup            
        //   206: aload           7
        //   208: aload_3        
        //   209: iload           4
        //   211: invokespecial   wwe/ohwW.<init>:(Lwwe/qMIe;Lwwe/FinI;I)V
        //   214: astore          8
        //   216: aload_3        
        //   217: getfield        wwe/FinI.UfJC:Ljava/util/ArrayList;
        //   220: aload           8
        //   222: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   225: pop            
        //   226: iinc            4, 12
        //   229: iinc            6, 1
        //   232: getstatic       wwe/hEGx.\u2008\u2007\u2006\u2002\u200d:Z
        //   235: ifeq            74
        //   238: aconst_null    
        //   239: athrow         
        //   240: nop            
        //   241: nop            
        //   242: nop            
        //   243: nop            
        //   244: athrow         
        //   245: iinc            2, 1
        //   248: getstatic       wwe/hEGx.\u2008\u2007\u2006\u2002\u200d:Z
        //   251: ifeq            7
        //   254: aconst_null    
        //   255: athrow         
        //   256: nop            
        //   257: nop            
        //   258: nop            
        //   259: nop            
        //   260: athrow         
        //   261: return         
        //   262: nop            
        //   263: nop            
        //   264: nop            
        //   265: nop            
        //   266: athrow         
        //    StackMapTable: 00 0D FF 00 03 00 0A 07 00 02 01 00 00 00 00 00 00 00 01 00 00 00 FF 00 02 00 0A 07 00 02 01 01 00 00 00 00 00 00 01 00 00 FF 00 42 00 0A 07 00 02 01 01 07 00 24 01 07 00 15 01 00 00 01 00 00 FF 00 63 00 0A 07 00 02 01 01 07 00 24 01 07 00 15 01 07 01 44 00 01 00 00 FF 00 16 00 00 00 01 07 00 11 FF 00 04 00 0A 07 00 02 01 01 07 00 24 01 07 00 15 01 07 01 44 00 01 00 00 1A FF 00 0A 00 00 00 01 07 00 11 FF 00 04 00 0A 07 00 02 01 01 07 00 24 01 07 00 15 01 00 00 01 00 00 FF 00 0A 00 00 00 01 07 00 11 FF 00 04 00 0A 07 00 02 01 01 00 00 00 00 00 00 01 00 00 FF 00 00 00 00 00 01 07 00 11
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static {
        hEGx.aTlJ = new XdhZ("Presets");
    }
}
