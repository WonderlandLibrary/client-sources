package ClassSub;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.block.*;
import com.darkmagician6.eventapi.*;
import cn.Hanabi.events.*;
import java.util.*;
import java.text.*;

public class Class190
{
    public static ArrayList<BlockPos> list;
    public static int bedCounts;
    
    
    public static void update() {
        Minecraft.getMinecraft().renderGlobal.loadRenderers();
        Class190.list.clear();
    }
    
    @EventTarget
    public void onRenderBlock(final EventRenderBlock eventRenderBlock) {
        final BlockPos blockPos = new BlockPos(eventRenderBlock.x, eventRenderBlock.y, eventRenderBlock.z);
        if (!Class190.list.contains(blockPos) && eventRenderBlock.block instanceof BlockBed) {
            Class190.list.add(blockPos);
        }
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        for (final BlockPos blockPos : Class190.list) {
            if (!(Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock() instanceof BlockBed)) {
                Class190.list.remove(blockPos);
            }
        }
    }
    
    static {
        Class190.list = new ArrayList<BlockPos>();
        try {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Class190.bedCounts = simpleDateFormat.parse(Class211.fuckman).compareTo(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
}
