package wwe;

import wwe.modules.player.*;
import net.minecraft.client.*;

class VaFK extends Thread
{
    final IRC vACk;
    
    VaFK(final IRC vaCk, final String s) {
        this.vACk = vaCk;
        super(s);
    }
    
    @Override
    public void run() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: return         
        //     4: ldc             "Connecting to IRC"
        //     6: invokestatic    wwe/HAxG.TuzD:(Ljava/lang/Object;)V
        //     9: new             Lwwe/Quvl;
        //    12: dup            
        //    13: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //    16: invokevirtual   net/minecraft/client/Minecraft.getSession:()Lnet/minecraft/util/Session;
        //    19: invokevirtual   net/minecraft/util/Session.getUsername:()Ljava/lang/String;
        //    22: invokespecial   wwe/Quvl.<init>:(Ljava/lang/String;)V
        //    25: putstatic       wwe/vlwv.IJGi:Lwwe/Quvl;
        //    28: getstatic       wwe/vlwv.IJGi:Lwwe/Quvl;
        //    31: invokevirtual   wwe/Quvl.XJrX:()V
        //    34: ldc             "Connected to IRC use @ + message to chat"
        //    36: invokestatic    wwe/HAxG.TuzD:(Ljava/lang/Object;)V
        //    39: ldc             1
        //    41: putstatic       wwe/modules/player/IRC.FclU:Z
        //    44: getstatic       wwe/VaFK.\u2006\u200c\u200a\u2005:Z
        //    47: ifeq            53
        //    50: aconst_null    
        //    51: athrow         
        //    52: astore_1       
        //    53: return         
        //    54: nop            
        //    55: nop            
        //    56: nop            
        //    57: athrow         
        //    StackMapTable: 00 05 FD 00 03 00 01 00 6F 07 00 1B 00 FF 00 00 00 00 00 01 07 00 0F
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  4      44     52     53     Ljava/lang/Exception;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
