package wwe;

import net.minecraft.util.*;

public class KULU
{
    public exyI SqLL;
    public String ZPfK;
    public String KVqZ;
    private Session NGxX;
    
    public KULU(final String zPfK, final String kVqZ) {
        super();
        this.SqLL = new exyI();
        this.ZPfK = zPfK;
        this.KVqZ = kVqZ;
    }
    
    public boolean smZj() {
        class dorv extends Thread
        {
            final KULU oMGm;
            
            dorv(final KULU omGm) {
                this.oMGm = omGm;
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
                //    13: getstatic       wwe/dorv.\u2007\u2005\u2008\u2000\u200c\u2009:Z
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
                //    StackMapTable: 00 05 FD 00 03 00 01 00 50 07 00 17 04 FF 00 00 00 00 00 01 07 00 0F
                //    Exceptions:
                //  Try           Handler
                //  Start  End    Start  End    Type                 
                //  -----  -----  -----  -----  ---------------------
                //  4      13     21     26     Ljava/lang/Exception;
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
        }
        class HmeR extends Thread
        {
            final KULU Ewmc;
            
            HmeR(final KULU ewmc) {
                this.Ewmc = ewmc;
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
                //    13: getstatic       wwe/HmeR.\u2008\u2008\u2004\u200f\u2009:Z
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
                //    StackMapTable: 00 05 FD 00 03 00 01 00 50 07 00 17 04 FF 00 00 00 00 00 01 07 00 0F
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
        //     3: iconst_1       
        //     4: ireturn        
        //     5: aload_0        
        //     6: getfield        wwe/KULU.KVqZ:Ljava/lang/String;
        //     9: ldc             ""
        //    11: if_acmpeq       87
        //    14: aload_0        
        //    15: getfield        wwe/KULU.KVqZ:Ljava/lang/String;
        //    18: ifnull          87
        //    21: aload_0        
        //    22: getfield        wwe/KULU.SqLL:Lwwe/exyI;
        //    25: pop            
        //    26: aload_0        
        //    27: getfield        wwe/KULU.ZPfK:Ljava/lang/String;
        //    30: aload_0        
        //    31: getfield        wwe/KULU.KVqZ:Ljava/lang/String;
        //    34: invokestatic    wwe/exyI.UhdS:(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/Session;
        //    37: astore_1       
        //    38: aload_1        
        //    39: ifnull          75
        //    42: aload_0        
        //    43: aload_1        
        //    44: putfield        wwe/KULU.NGxX:Lnet/minecraft/util/Session;
        //    47: aload_0        
        //    48: getfield        wwe/KULU.NGxX:Lnet/minecraft/util/Session;
        //    51: invokevirtual   net/minecraft/util/Session.getUsername:()Ljava/lang/String;
        //    54: invokestatic    wwe/IDwi.lZga:(Ljava/lang/String;)V
        //    57: new             Lwwe/dorv;
        //    60: dup            
        //    61: aload_0        
        //    62: invokespecial   wwe/dorv.<init>:(Lwwe/KULU;)V
        //    65: invokevirtual   wwe/dorv.start:()V
        //    68: ldc             1
        //    70: ireturn        
        //    71: nop            
        //    72: nop            
        //    73: nop            
        //    74: athrow         
        //    75: getstatic       wwe/KULU.\u2005\u2005\u2006\u2005\u2009:Z
        //    78: ifeq            130
        //    81: aconst_null    
        //    82: athrow         
        //    83: nop            
        //    84: nop            
        //    85: nop            
        //    86: athrow         
        //    87: aload_0        
        //    88: getfield        wwe/KULU.SqLL:Lwwe/exyI;
        //    91: pop            
        //    92: aload_0        
        //    93: getfield        wwe/KULU.ZPfK:Ljava/lang/String;
        //    96: invokestatic    wwe/exyI.cadX:(Ljava/lang/String;)Lnet/minecraft/util/Session;
        //    99: astore_1       
        //   100: aload_0        
        //   101: aload_1        
        //   102: putfield        wwe/KULU.NGxX:Lnet/minecraft/util/Session;
        //   105: aload_0        
        //   106: getfield        wwe/KULU.ZPfK:Ljava/lang/String;
        //   109: invokestatic    wwe/IDwi.lZga:(Ljava/lang/String;)V
        //   112: new             Lwwe/HmeR;
        //   115: dup            
        //   116: aload_0        
        //   117: invokespecial   wwe/HmeR.<init>:(Lwwe/KULU;)V
        //   120: invokevirtual   wwe/HmeR.start:()V
        //   123: ldc             1
        //   125: ireturn        
        //   126: nop            
        //   127: nop            
        //   128: nop            
        //   129: athrow         
        //   130: ldc             0
        //   132: ireturn        
        //   133: nop            
        //   134: nop            
        //   135: nop            
        //   136: athrow         
        //    StackMapTable: 00 09 FD 00 03 00 01 01 FF 00 41 00 00 00 01 07 00 13 FE 00 03 07 00 02 07 00 2D 01 FF 00 07 00 00 00 01 07 00 13 FE 00 03 07 00 02 00 01 FF 00 26 00 00 00 01 07 00 13 FE 00 03 07 00 02 07 00 2D 01 FF 00 02 00 00 00 01 07 00 13
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public Session sEFp() {
        return this.NGxX;
    }
}
