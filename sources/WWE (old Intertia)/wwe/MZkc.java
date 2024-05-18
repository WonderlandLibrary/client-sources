package wwe;

import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import wwe.utils.*;
import net.minecraft.client.gui.*;

public class MZkc
{
    public MZkc() {
        super();
        hSNE.lyvR(this);
        if (vlwv.wMQO == null) {
            return;
        }
        vlwv.wMQO.BBwj();
    }
    
    @SubscribeEvent
    public void HCpg(final TickEvent.ClientTickEvent clientTickEvent) {
        class ekQq extends Thread
        {
            final MZkc XQml;
            
            ekQq(final MZkc xQml) {
                this.XQml = xQml;
                super();
            }
            
            @Override
            public void run() {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     3: return         
                //     4: invokestatic    wwe/vlwv.wNWV:()V
                //     7: getstatic       wwe/vlwv.wMQO:Lwwe/sbfc;
                //    10: invokevirtual   wwe/sbfc.BBwj:()V
                //    13: getstatic       wwe/ekQq.\u2005\u2004:Z
                //    16: ifeq            26
                //    19: aconst_null    
                //    20: athrow         
                //    21: astore_1       
                //    22: aload_1        
                //    23: invokevirtual   java/lang/Exception.printStackTrace:()V
                //    26: return         
                //    27: nop            
                //    28: nop            
                //    29: nop            
                //    30: athrow         
                //    StackMapTable: 00 05 FD 00 03 00 01 00 50 07 00 1C 04 FF 00 00 00 00 00 01 07 00 14
                //    Exceptions:
                //  Try           Handler
                //  Start  End    Start  End    Type                 
                //  -----  -----  -----  -----  ---------------------
                //  4      13     21     26     Ljava/lang/Exception;
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
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
        //     8: getstatic       wwe/MjBb.gEac:Ljava/util/ArrayList;
        //    11: invokevirtual   java/util/ArrayList.size:()I
        //    14: if_icmpge       48
        //    17: getstatic       wwe/MjBb.gEac:Ljava/util/ArrayList;
        //    20: iload_2        
        //    21: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //    24: checkcast       Lwwe/TYDL;
        //    27: astore_3       
        //    28: aload_3        
        //    29: invokevirtual   wwe/TYDL.gpgp:()V
        //    32: iinc            2, 1
        //    35: getstatic       wwe/MZkc.\u2008\u200a\u2006\u200e\u2009:Z
        //    38: ifeq            7
        //    41: aconst_null    
        //    42: athrow         
        //    43: nop            
        //    44: nop            
        //    45: nop            
        //    46: nop            
        //    47: athrow         
        //    48: getstatic       wwe/vlwv.wMQO:Lwwe/sbfc;
        //    51: ifnonnull       60
        //    54: return         
        //    55: nop            
        //    56: nop            
        //    57: nop            
        //    58: nop            
        //    59: athrow         
        //    60: getstatic       wwe/vlwv.wMQO:Lwwe/sbfc;
        //    63: invokevirtual   wwe/sbfc.gCgh:()V
        //    66: getstatic       wwe/vlwv.wMQO:Lwwe/sbfc;
        //    69: ldc2_w          600000.0
        //    72: invokevirtual   wwe/sbfc.QOXG:(D)Z
        //    75: ifeq            95
        //    78: new             Lwwe/ekQq;
        //    81: dup            
        //    82: aload_0        
        //    83: invokespecial   wwe/ekQq.<init>:(Lwwe/MZkc;)V
        //    86: invokevirtual   wwe/ekQq.start:()V
        //    89: getstatic       wwe/vlwv.wMQO:Lwwe/sbfc;
        //    92: invokevirtual   wwe/sbfc.BBwj:()V
        //    95: return         
        //    96: nop            
        //    97: nop            
        //    98: nop            
        //    99: nop            
        //   100: athrow         
        //    StackMapTable: 00 09 FE 00 03 00 00 01 00 FF 00 02 00 05 07 00 02 07 00 08 01 00 01 00 00 FF 00 23 00 00 00 01 07 00 17 FF 00 04 00 05 07 00 02 07 00 08 01 00 01 00 00 FF 00 06 00 00 00 01 07 00 17 FF 00 04 00 05 07 00 02 07 00 08 01 00 01 00 00 22 FF 00 00 00 00 00 01 07 00 17
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @oqrr
    public void ZIDX(final mIcn.wwe/goVx wwe/goVx) {
        Wrapper.mc.displayGuiScreen((GuiScreen)new JImF(Wrapper.mc.currentScreen));
    }
    
    @oqrr
    public void KdXa(final mIcn.wwe/eoOk wwe/eoOk) {
    }
    
    @oqrr
    public void pbZk(final mIcn.wwe/XvGl wwe/XvGl) {
    }
}
