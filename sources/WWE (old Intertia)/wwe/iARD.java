package wwe;

class iARD extends Thread
{
    final String VMVa;
    final String ETyl;
    final Lnec fSJI;
    
    iARD(final Lnec fsji, final String vmVa, final String eTyl) {
        this.fSJI = fsji;
        this.VMVa = vmVa;
        this.ETyl = eTyl;
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
        //     4: aload_0        
        //     5: getfield        wwe/iARD.fSJI:Lwwe/Lnec;
        //     8: new             Ljava/lang/StringBuilder;
        //    11: dup            
        //    12: invokespecial   java/lang/StringBuilder.<init>:()V
        //    15: aload_0        
        //    16: getfield        wwe/iARD.VMVa:Ljava/lang/String;
        //    19: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    22: ldc             "?size=200"
        //    24: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    27: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    30: putfield        wwe/Lnec.GExK:Ljava/lang/String;
        //    33: aload_0        
        //    34: getfield        wwe/iARD.fSJI:Lwwe/Lnec;
        //    37: new             Ljava/lang/StringBuilder;
        //    40: dup            
        //    41: invokespecial   java/lang/StringBuilder.<init>:()V
        //    44: ldc             "https://wweclient.com/"
        //    46: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    49: aload_0        
        //    50: getfield        wwe/iARD.ETyl:Ljava/lang/String;
        //    53: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    56: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    59: invokestatic    wwe/FIwW.vdYN:(Ljava/lang/String;)Ljava/awt/image/BufferedImage;
        //    62: putfield        wwe/Lnec.JOKI:Ljava/awt/image/BufferedImage;
        //    65: aload_0        
        //    66: getfield        wwe/iARD.fSJI:Lwwe/Lnec;
        //    69: ldc             2
        //    71: putfield        wwe/Lnec.DplW:I
        //    74: getstatic       wwe/iARD.\u200f\u200c\u2004\u200e\u200a\u2007\u2004\u200c:Z
        //    77: ifeq            87
        //    80: aconst_null    
        //    81: athrow         
        //    82: astore_1       
        //    83: aload_1        
        //    84: invokevirtual   java/lang/Exception.printStackTrace:()V
        //    87: return         
        //    88: nop            
        //    89: nop            
        //    90: nop            
        //    91: athrow         
        //    StackMapTable: 00 05 FD 00 03 00 01 00 F7 00 4D 07 00 1F 04 FF 00 00 00 00 00 01 07 00 11
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  4      74     82     87     Ljava/lang/Exception;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
