package cn.Hanabi.modules.World;

import ClassSub.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import net.minecraft.client.gui.*;
import com.darkmagician6.eventapi.*;

public class IRC extends Mod
{
    public static Class203 client;
    
    
    public IRC() {
        super("IRC", Category.WORLD);
        (IRC.client = new Class203()).start(IRC.fuck, IRC.me);
    }
    
    @EventTarget
    public void onKey(final EventKey eventKey) {
        if (eventKey.getKey() == 52) {
            IRC.mc.displayGuiScreen((GuiScreen)new GuiChat());
        }
    }
}
