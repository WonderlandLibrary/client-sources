package cn.Hanabi.modules.Render;

import net.minecraft.util.*;
import cn.Hanabi.modules.*;
import net.minecraft.block.*;
import com.darkmagician6.eventapi.*;
import cn.Hanabi.events.*;
import cn.Hanabi.injection.interfaces.*;
import net.minecraft.block.material.*;
import ClassSub.*;
import java.util.*;
import net.minecraft.block.state.*;

public class CaveFinder extends Mod
{
    ArrayList<BlockPos> list;
    
    
    public CaveFinder() {
        super("CaveFinder", Category.RENDER);
        this.list = new ArrayList<BlockPos>();
    }
    
    public void onEnable() {
        CaveFinder.mc.renderGlobal.loadRenderers();
        this.list.clear();
    }
    
    @EventTarget
    public void onRenderBlock(final EventRenderBlock eventRenderBlock) {
        final BlockPos blockPos = new BlockPos(eventRenderBlock.x, eventRenderBlock.y, eventRenderBlock.z);
        if (!this.list.contains(blockPos) && eventRenderBlock.block instanceof BlockLiquid && eventRenderBlock.y <= 40) {
            this.list.add(blockPos);
        }
    }
    
    @EventTarget
    public void onRender(final EventRender eventRender) {
        for (final BlockPos blockPos : this.list) {
            final IBlockState getBlockState = CaveFinder.mc.theWorld.getBlockState(blockPos);
            if (!(getBlockState.getBlock() instanceof BlockLiquid)) {
                this.list.remove(blockPos);
            }
            Class246.drawSolidBlockESP(blockPos.getX() - ((IRenderManager)CaveFinder.mc.getRenderManager()).getRenderPosX(), blockPos.getY() - ((IRenderManager)CaveFinder.mc.getRenderManager()).getRenderPosY(), blockPos.getZ() - ((IRenderManager)CaveFinder.mc.getRenderManager()).getRenderPosZ(), (getBlockState.getBlock().getMaterial() == Material.lava) ? 1.0f : 0.0f, 0.0f, (getBlockState.getBlock().getMaterial() == Material.water) ? 1.0f : 0.0f, 0.2f);
        }
    }
}
