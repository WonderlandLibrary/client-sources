package me.finz0.osiris.module.modules.render;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.util.AuroraTessellator;
import me.finz0.osiris.util.GeometryMasks;
import me.finz0.osiris.util.OsirisTessellator;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class BlockHighlight extends Module {
    public BlockHighlight() {
        super("BlockHighlight", Category.RENDER, "Highlights the block you're looking at");
    }

    Setting r;
    Setting g;
    Setting b;
    Setting a;
    Setting w;

    Setting a2;



    Setting boundingbox;
    Setting box;
    Setting rainbow;

    public void setup(){
        box = new Setting("Box", this, true, "Box");
        AuroraMod.getInstance().settingsManager.rSetting(box);
        boundingbox = new Setting("boundingbox", this, true, "boundingbox");
        AuroraMod.getInstance().settingsManager.rSetting(boundingbox);
        r = new Setting("Red", this, 255, 0, 255, true, "BlockHighlightRed");
        AuroraMod.getInstance().settingsManager.rSetting(r);
        g = new Setting("Green", this, 255, 0, 255, true, "BlockHighlightGreen");
        AuroraMod.getInstance().settingsManager.rSetting(g);
        b = new Setting("Blue", this, 255, 0, 255, true, "BlockHighlightBlue");
        AuroraMod.getInstance().settingsManager.rSetting(b);
        a = new Setting("BoundingBoxAlpha", this, 255, 0, 255, true, "BlockHighlightAlpha");
        AuroraMod.getInstance().settingsManager.rSetting(a);
        a2 = new Setting("Alpha", this, 30, 0, 255, true, "BlockHighlightAlphaBounding");
        AuroraMod.getInstance().settingsManager.rSetting(a2);
        w = new Setting("Width", this, 1, 1, 10, true, "BlockHighlightWidth");
        AuroraMod.getInstance().settingsManager.rSetting(w);
        AuroraMod.getInstance().settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "BlockHighlightRainbow"));
    }

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
                if (box.getValBoolean()) {
                    OsirisTessellator.prepare(GL11.GL_QUADS);
                    if (rainbow.getValBoolean()) {
                        OsirisTessellator.drawBox(blockpos, r, g, b, a2.getValInt(), GeometryMasks.Quad.ALL);
                    } else {
                        OsirisTessellator.drawBox(blockpos, this.r.getValInt(), this.g.getValInt(), this.b.getValInt(), a2.getValInt(), GeometryMasks.Quad.ALL);
                    }
                    OsirisTessellator.release();
                }
                if (boundingbox.getValBoolean()) {
                    AuroraTessellator.prepare(GL11.GL_QUADS);
                    if (rainbow.getValBoolean()) {
                        AuroraTessellator.drawBoundingBoxBlockPos(blockpos, w.getValInt(), r, g, b, a.getValInt());
                    } else {
                        AuroraTessellator.drawBoundingBoxBlockPos(blockpos, w.getValInt(), this.r.getValInt(), this.g.getValInt(), this.b.getValInt(), a.getValInt());
                    }
                    AuroraTessellator.release();
                }
            }
        }
    }
}