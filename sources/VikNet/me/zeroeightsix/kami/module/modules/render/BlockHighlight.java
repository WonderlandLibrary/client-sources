package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.*;
import net.minecraftforge.common.*;
import me.zeroeightsix.kami.event.events.*;
import net.minecraft.client.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import me.zeroeightsix.kami.util.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraftforge.client.event.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.lwjgl.opengl.GL11;

import java.awt.Color;

@Module.Info(name = "BlockHighlight", description = "Happy Halloween <3", category = Module.Category.RENDER)
public class BlockHighlight extends Module {
    private Setting<Boolean> boundingbox = register(Settings.b("Bouding Box", true));
    private Setting<Boolean> box = register(Settings.b("Full Block Highlight", false));
    private Setting<Double> width = register(Settings.d("Width", 1.5));
    private Setting<Integer> alpha = register(
            Settings.integerBuilder("Alpha").withMinimum(1).withMaximum(255).withValue(30));
    private Setting<Integer> Red = register(
            Settings.integerBuilder("Red").withMinimum(1).withMaximum(255).withValue(255));
    private Setting<Integer> Green = register(
            Settings.integerBuilder("Green").withMinimum(1).withMaximum(255).withValue(255));
    private Setting<Integer> Blue = register(
            Settings.integerBuilder("Blue").withMinimum(1).withMaximum(255).withValue(255));
    private Setting<Integer> alpha2 = register(
            Settings.integerBuilder("Bounding Box Alpha").withMinimum(1).withMaximum(255).withValue(200));
    private Setting<Boolean> rainbow = register(Settings.b("Rainbow", false));

    public void onWorldRender(RenderEvent event) {
        final float[] hue = {(System.currentTimeMillis() % (360 * 32)) / (360f * 32)};
        int rgb = Color.HSBtoRGB(hue[0], 1, 1);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        final Minecraft mc = Minecraft.getMinecraft();
        final RayTraceResult ray = mc.objectMouseOver;
        if (ray.typeOfHit == RayTraceResult.Type.BLOCK) {

            final BlockPos blockpos = ray.getBlockPos();
            final IBlockState iblockstate = mc.world.getBlockState(blockpos);

            if (iblockstate.getMaterial() != Material.AIR && mc.world.getWorldBorder().contains(blockpos)) {
                if (box.getValue()) {
                    KamiTessellator.prepare(GL11.GL_QUADS);
                    if (rainbow.getValue()) {
                        KamiTessellator.drawBox(blockpos, r, g, b, alpha.getValue(), GeometryMasks.Quad.ALL);
                    } else {
                        KamiTessellator.drawBox(blockpos, this.Red.getValue(), this.Green.getValue(), this.Blue.getValue(), alpha.getValue(), GeometryMasks.Quad.ALL);
                    }
                    KamiTessellator.release();
                }
                if (boundingbox.getValue()) {
                    KamiTessellator.prepare(GL11.GL_QUADS);
                    if (rainbow.getValue()) {
                        KamiTessellator.drawBoundingBoxBlockPos(blockpos, width.getValue().floatValue(), r, g, b, alpha2.getValue());
                    } else {
                        KamiTessellator.drawBoundingBoxBlockPos(blockpos, width.getValue().floatValue(), this.Red.getValue(), this.Green.getValue(), this.Blue.getValue(), alpha2.getValue());
                    }
                    KamiTessellator.release();
                }
            }
        }
    }
}