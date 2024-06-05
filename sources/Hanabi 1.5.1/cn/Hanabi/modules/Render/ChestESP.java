package cn.Hanabi.modules.Render;

import net.minecraft.util.*;
import cn.Hanabi.modules.*;
import net.minecraft.block.*;
import com.darkmagician6.eventapi.*;
import cn.Hanabi.events.*;
import cn.Hanabi.injection.interfaces.*;
import ClassSub.*;
import java.util.*;

public class ChestESP extends Mod
{
    ArrayList<BlockPos> list;
    
    
    public ChestESP() {
        super("ChestESP", Category.RENDER);
        this.list = new ArrayList<BlockPos>();
    }
    
    public void onEnable() {
        ChestESP.mc.renderGlobal.loadRenderers();
        this.list.clear();
    }
    
    @EventTarget
    public void onRenderBlock(final EventRenderBlock eventRenderBlock) {
        final BlockPos blockPos = new BlockPos(eventRenderBlock.x, eventRenderBlock.y, eventRenderBlock.z);
        if (!this.list.contains(blockPos) && (eventRenderBlock.block instanceof BlockChest || eventRenderBlock.block instanceof BlockEnderChest)) {
            this.list.add(blockPos);
        }
    }
    
    @EventTarget
    public void onRender(final EventRender eventRender) {
        for (final BlockPos blockPos : this.list) {
            if (!(ChestESP.mc.theWorld.getBlockState(blockPos).getBlock() instanceof BlockChest) && !(ChestESP.mc.theWorld.getBlockState(blockPos).getBlock() instanceof BlockEnderChest)) {
                this.list.remove(blockPos);
            }
            Class246.drawSolidBlockESP(blockPos.getX() - ((IRenderManager)ChestESP.mc.getRenderManager()).getRenderPosX(), blockPos.getY() - ((IRenderManager)ChestESP.mc.getRenderManager()).getRenderPosY(), blockPos.getZ() - ((IRenderManager)ChestESP.mc.getRenderManager()).getRenderPosZ(), 1.0f, 1.0f, 1.0f, 0.2f);
        }
    }
}
