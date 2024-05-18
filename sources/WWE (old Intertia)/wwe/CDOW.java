package wwe;

import wwe.modules.render.*;
import net.minecraft.client.gui.*;
import wwe.utils.*;

class CDOW implements pSdr
{
    final XybC hILD;
    
    CDOW(final XybC hild) {
        this.hILD = hild;
        super();
    }
    
    @Override
    public int Gsgl() {
        return vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).wkYe + 2;
    }
    
    @Override
    public void Hrqs(final int n) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: return         
        //     4: aload_0        
        //     5: getfield        wwe/CDOW.hILD:Lwwe/XybC;
        //     8: iload_1        
        //     9: ldc             1
        //    11: iadd           
        //    12: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //    15: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //    18: instanceof      Lnet/minecraft/client/gui/GuiChat;
        //    21: ifeq            34
        //    24: ldc             1
        //    26: getstatic       wwe/CDOW.\u2007\u2002\u2008\u2001\u2003\u200d\u2009\u2008:Z
        //    29: ifeq            36
        //    32: aconst_null    
        //    33: athrow         
        //    34: ldc             0
        //    36: isub           
        //    37: invokevirtual   wwe/XybC.qGPs:(I)V
        //    40: return         
        //    StackMapTable: 00 04 FC 00 03 01 00 FF 00 1D 00 03 07 00 02 01 01 00 02 07 00 08 01 FF 00 01 00 03 07 00 02 01 01 00 03 07 00 08 01 01
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
