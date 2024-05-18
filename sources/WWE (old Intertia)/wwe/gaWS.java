package wwe;

class gaWS extends Thread
{
    final String oMTi;
    final String ahYX;
    final Lnec mRTD;
    
    gaWS(final Lnec mrtd, final String omTi, final String ahYX) {
        this.mRTD = mrtd;
        this.oMTi = omTi;
        this.ahYX = ahYX;
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
        //     5: getfield        wwe/gaWS.mRTD:Lwwe/Lnec;
        //     8: new             Ljava/lang/StringBuilder;
        //    11: dup            
        //    12: invokespecial   java/lang/StringBuilder.<init>:()V
        //    15: aload_0        
        //    16: getfield        wwe/gaWS.oMTi:Ljava/lang/String;
        //    19: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    22: ldc             "?size=200"
        //    24: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    27: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    30: putfield        wwe/Lnec.GExK:Ljava/lang/String;
        //    33: aload_0        
        //    34: getfield        wwe/gaWS.mRTD:Lwwe/Lnec;
        //    37: new             Ljava/lang/StringBuilder;
        //    40: dup            
        //    41: invokespecial   java/lang/StringBuilder.<init>:()V
        //    44: ldc             "https://wweclient.com/"
        //    46: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    49: aload_0        
        //    50: getfield        wwe/gaWS.ahYX:Ljava/lang/String;
        //    53: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    56: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    59: invokestatic    wwe/FIwW.vdYN:(Ljava/lang/String;)Ljava/awt/image/BufferedImage;
        //    62: ldc             500
        //    64: invokestatic    wwe/Lnec.tCKN:(Ljava/awt/image/BufferedImage;I)Ljava/awt/image/BufferedImage;
        //    67: putfield        wwe/Lnec.JOKI:Ljava/awt/image/BufferedImage;
        //    70: aload_0        
        //    71: getfield        wwe/gaWS.mRTD:Lwwe/Lnec;
        //    74: ldc             1
        //    76: putfield        wwe/Lnec.DplW:I
        //    79: getstatic       wwe/gaWS.\u200c\u2001\u2005:Z
        //    82: ifeq            92
        //    85: aconst_null    
        //    86: athrow         
        //    87: astore_1       
        //    88: aload_1        
        //    89: invokevirtual   java/lang/Exception.printStackTrace:()V
        //    92: return         
        //    93: nop            
        //    94: nop            
        //    95: nop            
        //    96: athrow         
        //    StackMapTable: 00 05 FD 00 03 00 01 00 F7 00 52 07 00 1F 04 FF 00 00 00 00 00 01 07 00 11
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  4      79     87     92     Ljava/lang/Exception;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
