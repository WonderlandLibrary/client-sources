package wwe;

import wwe.modules.exploits.*;

class nvFL extends Thread
{
    final AutoFish nMuB;
    
    nvFL(final AutoFish nMuB) {
        this.nMuB = nMuB;
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
        //     4: invokestatic    wwe/yYGD.AXbo:()V
        //     7: ldc2_w          -3396013685424598080
        //    10: ldc2_w          4028629955984350118
        //    13: dup2_x2        
        //    14: pop2           
        //    15: pop2           
        //    16: ldc2_w          4028629955984349834
        //    19: lxor           
        //    20: invokestatic    java/lang/Thread.sleep:(J)V
        //    23: invokestatic    wwe/yYGD.AXbo:()V
        //    26: getstatic       wwe/nvFL.\u2009\u2002\u2001\u2003\u2000\u2000\u2004:Z
        //    29: ifeq            35
        //    32: aconst_null    
        //    33: athrow         
        //    34: astore_1       
        //    35: return         
        //    36: nop            
        //    37: nop            
        //    38: nop            
        //    39: athrow         
        //    StackMapTable: 00 05 FD 00 03 00 01 00 5D 07 00 19 00 FF 00 00 00 00 00 01 07 00 0F
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  4      26     34     35     Ljava/lang/Exception;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
