// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.render;

import net.minecraft.util.math.BlockPos;
import me.perry.mcdonalds.util.RenderUtil;
import java.awt.Color;
import net.minecraft.util.math.RayTraceResult;
import me.perry.mcdonalds.event.events.Render3DEvent;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class BlockHighlight extends Module
{
    private final Setting<Float> lineWidth;
    private final Setting<Integer> alpha;
    private final Setting<Integer> red;
    private final Setting<Integer> green;
    private final Setting<Integer> blue;
    
    public BlockHighlight() {
        super("BlockHighlight", "Highlights the block u look at.", Category.RENDER, false, false, false);
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.0f, (T)0.1f, (T)5.0f));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", (T)255, (T)0, (T)255));
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)255, (T)0, (T)255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)255, (T)0, (T)255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)255, (T)0, (T)255));
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        final RayTraceResult ray = BlockHighlight.mc.objectMouseOver;
        if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK) {
            final BlockPos blockpos = ray.getBlockPos();
            RenderUtil.drawBlockOutline(blockpos, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), this.lineWidth.getValue(), false);
        }
    }
}
