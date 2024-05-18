package wwe;

import java.net.*;
import javax.imageio.*;

class pCUN extends Thread
{
    final String cbEx;
    final Lnec JUmK;
    
    pCUN(final Lnec jUmK, final String cbEx) {
        this.JUmK = jUmK;
        this.cbEx = cbEx;
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
        //     5: getfield        wwe/pCUN.JUmK:Lwwe/Lnec;
        //     8: new             Ljava/lang/StringBuilder;
        //    11: dup            
        //    12: invokespecial   java/lang/StringBuilder.<init>:()V
        //    15: aload_0        
        //    16: getfield        wwe/pCUN.cbEx:Ljava/lang/String;
        //    19: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    22: ldc             "?size=200"
        //    24: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    27: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    30: putfield        wwe/Lnec.GExK:Ljava/lang/String;
        //    33: aload_0        
        //    34: getfield        wwe/pCUN.JUmK:Lwwe/Lnec;
        //    37: new             Ljava/net/URL;
        //    40: dup            
        //    41: new             Ljava/lang/StringBuilder;
        //    44: dup            
        //    45: invokespecial   java/lang/StringBuilder.<init>:()V
        //    48: ldc             "https://crafatar.com/avatars/"
        //    50: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    53: aload_0        
        //    54: getfield        wwe/pCUN.cbEx:Ljava/lang/String;
        //    57: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    60: ldc             "?size=200"
        //    62: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    65: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    68: invokespecial   java/net/URL.<init>:(Ljava/lang/String;)V
        //    71: invokestatic    javax/imageio/ImageIO.read:(Ljava/net/URL;)Ljava/awt/image/BufferedImage;
        //    74: ldc             500
        //    76: invokestatic    wwe/Lnec.tCKN:(Ljava/awt/image/BufferedImage;I)Ljava/awt/image/BufferedImage;
        //    79: putfield        wwe/Lnec.JOKI:Ljava/awt/image/BufferedImage;
        //    82: aload_0        
        //    83: getfield        wwe/pCUN.JUmK:Lwwe/Lnec;
        //    86: ldc             0
        //    88: putfield        wwe/Lnec.DplW:I
        //    91: getstatic       wwe/pCUN.\u2007\u2006\u2004:Z
        //    94: ifeq            104
        //    97: aconst_null    
        //    98: athrow         
        //    99: astore_1       
        //   100: aload_1        
        //   101: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   104: return         
        //   105: nop            
        //   106: nop            
        //   107: nop            
        //   108: athrow         
        //    StackMapTable: 00 05 FD 00 03 00 01 00 F7 00 5E 07 00 1C 04 FF 00 00 00 00 00 01 07 00 10
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  4      91     99     104    Ljava/lang/Exception;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
