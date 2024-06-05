package cn.Hanabi.modules.Render;

import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import net.minecraft.util.*;
import cn.Hanabi.injection.interfaces.*;
import ClassSub.*;
import java.util.*;
import com.darkmagician6.eventapi.*;

public class BedESP extends Mod
{
    
    
    public BedESP() {
        super("BedESP", Category.RENDER);
    }
    
    @EventTarget
    public void onRender(final EventRender eventRender) {
        for (final BlockPos blockPos : Class190.list) {
            Class246.drawSolidBlockESP(blockPos.getX() - ((IRenderManager)BedESP.mc.getRenderManager()).getRenderPosX(), blockPos.getY() - ((IRenderManager)BedESP.mc.getRenderManager()).getRenderPosY(), blockPos.getZ() - ((IRenderManager)BedESP.mc.getRenderManager()).getRenderPosZ(), 1.0f, 1.0f, 1.0f, 0.2f);
        }
    }
}
