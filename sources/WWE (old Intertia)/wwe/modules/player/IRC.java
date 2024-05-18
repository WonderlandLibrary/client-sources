package wwe.modules.player;

import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.util.text.*;
import java.util.*;
import net.minecraft.client.*;
import wwe.*;

public class IRC extends qMIe
{
    public static boolean FclU;
    
    public IRC() {
        super("IRC", "Allows you to talk to other wwe users", 0, Category.PLAYER);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (IRC.FclU && vlwv.IJGi.dzdQ() && vlwv.IJGi.cfKj()) {
            for (final Jhbg jhbg : vlwv.IJGi.QgjU()) {
                Wrapper.mc.player.sendMessage((ITextComponent)new TextComponentString(TextFormatting.DARK_AQUA + "[IRC] " + TextFormatting.RESET + jhbg.gUTp() + ": " + jhbg.EbtK().replaceAll(">", TextFormatting.GREEN + ">")));
                jhbg.ihLK(true);
                if (IRC.\u2000\u200b\u200a\u200e) {
                    throw null;
                }
            }
        }
    }
    
    @Override
    public void LPxx() {
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
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: return         
        //     4: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //     7: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //    10: ifnull          38
        //    13: new             Lwwe/VaFK;
        //    16: dup            
        //    17: aload_0        
        //    18: ldc             "enable IRC"
        //    20: invokespecial   wwe/VaFK.<init>:(Lwwe/modules/player/IRC;Ljava/lang/String;)V
        //    23: invokevirtual   wwe/VaFK.start:()V
        //    26: getstatic       wwe/modules/player/IRC.\u2000\u200b\u200a\u200e:Z
        //    29: ifeq            68
        //    32: aconst_null    
        //    33: athrow         
        //    34: nop            
        //    35: nop            
        //    36: nop            
        //    37: athrow         
        //    38: new             Lwwe/Quvl;
        //    41: dup            
        //    42: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //    45: invokevirtual   net/minecraft/client/Minecraft.getSession:()Lnet/minecraft/util/Session;
        //    48: invokevirtual   net/minecraft/util/Session.getUsername:()Ljava/lang/String;
        //    51: invokespecial   wwe/Quvl.<init>:(Ljava/lang/String;)V
        //    54: putstatic       wwe/vlwv.IJGi:Lwwe/Quvl;
        //    57: getstatic       wwe/vlwv.IJGi:Lwwe/Quvl;
        //    60: invokevirtual   wwe/Quvl.XJrX:()V
        //    63: ldc             1
        //    65: putstatic       wwe/modules/player/IRC.FclU:Z
        //    68: aload_0        
        //    69: invokespecial   wwe/qMIe.LPxx:()V
        //    72: return         
        //    73: nop            
        //    74: nop            
        //    75: nop            
        //    76: athrow         
        //    StackMapTable: 00 06 FF 00 03 00 02 07 00 1D 01 00 00 00 FF 00 1D 00 00 00 01 07 00 0D FD 00 03 07 00 1D 01 1D FF 00 04 00 00 00 01 07 00 0D
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void qBmy() {
        if (vlwv.IJGi.dzdQ()) {
            vlwv.IJGi.JyVG();
            HAxG.TuzD("Disconnected from IRC");
            IRC.FclU = false;
        }
        super.qBmy();
    }
    
    static {
        IRC.FclU = false;
    }
}
