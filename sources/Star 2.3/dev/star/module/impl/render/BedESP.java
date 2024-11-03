package dev.star.module.impl.render;

import dev.star.event.impl.player.UpdateEvent;
import dev.star.event.impl.render.Render3DEvent;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.settings.impl.BooleanSetting;
import dev.star.module.settings.impl.ModeSetting;
import dev.star.module.settings.impl.NumberSetting;
import dev.star.utils.render.ColorUtil;
import dev.star.utils.render.RenderUtil;
import dev.star.utils.render.Theme;
import net.minecraft.block.BlockBed;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BedESP extends Module {
    private BlockPos[] bed = null;
    private List<BlockPos[]> beds = new ArrayList<>();
    private long lastCheck = 0;
    private final ModeSetting mode = new ModeSetting("Esp Mode", "3D","3D", "Shaded");
    private final NumberSetting range =  new NumberSetting("Range", 10.0, 30.0, 2, 2.0);
    private final NumberSetting rate =  new NumberSetting("Rate", 0.4, 3.0, 0.1, 0.1);
    private final BooleanSetting firstBed = new BooleanSetting("First Bed", false);
    private final BooleanSetting renderFullBlock  = new BooleanSetting("Render Full Block", false);
   
    public BedESP() {
        super("BedESP", Category.RENDER, "Renders Beds");


       addSettings(mode,range, rate, firstBed, renderFullBlock);
    }

    @Override
    public void onUpdateEvent(UpdateEvent event) {
       if (this.isEnabled()) {

           if (System.currentTimeMillis() - lastCheck < rate.getValue() * 1000) {
               return;
           }
           lastCheck = System.currentTimeMillis();
           int i;
           priorityLoop:
           for (int n = i = range.getValue().intValue(); i >= -n; --i) {
               for (int j = -n; j <= n; ++j) {
                   for (int k = -n; k <= n; ++k) {
                       final BlockPos blockPos = new BlockPos(mc.thePlayer.posX + j, mc.thePlayer.posY + i, mc.thePlayer.posZ + k);
                       final IBlockState getBlockState = mc.theWorld.getBlockState(blockPos);
                       if (getBlockState.getBlock() == Blocks.bed && getBlockState.getValue((IProperty) BlockBed.PART) == BlockBed.EnumPartType.FOOT) {
                           if (firstBed.isEnabled()) {
                               if (this.bed != null && BlockPos.isSamePos(blockPos, this.bed[0])) {
                                   return;
                               }
                               this.bed = new BlockPos[]{blockPos, blockPos.offset((EnumFacing) getBlockState.getValue((IProperty) BlockBed.FACING))};
                               return;
                           } else {
                               for (int l = 0; l < this.beds.size(); ++l) {
                                   if (BlockPos.isSamePos(blockPos, ((BlockPos[]) this.beds.get(l))[0])) {
                                       continue priorityLoop;
                                   }
                               }
                               this.beds.add(new BlockPos[]{blockPos, blockPos.offset((EnumFacing) getBlockState.getValue((IProperty) BlockBed.FACING))});
                           }
                       }
                   }
               }
           }
       }
    }

    @Override
    public void onRender3DEvent(Render3DEvent e) {
        if (this.isEnabled()) {

            if (mc.theWorld != null && mc.thePlayer != null) {
                if (firstBed.isEnabled() && this.bed != null) {
                    if (!(mc.theWorld.getBlockState(bed[0]).getBlock() instanceof BlockBed)) {
                        this.bed = null;
                        return;
                    }
                    renderBed(this.bed);
                    return;
                }
                if (this.beds.isEmpty()) {
                    return;
                }
                Iterator<BlockPos[]> iterator = this.beds.iterator();
                while (iterator.hasNext()) {
                    BlockPos[] blockPos = iterator.next();
                    if (!(mc.theWorld.getBlockState(blockPos[0]).getBlock() instanceof BlockBed)) {
                        iterator.remove();
                        continue;
                    }
                    renderBed(blockPos);
                }
            }
        }
    }

    public void onDisable() {
        this.bed = null;
        this.beds.clear();
    }

    private void renderBed(final BlockPos[] array) {
        final double n = array[0].getX() - mc.getRenderManager().viewerPosX;
        final double n2 = array[0].getY() - mc.getRenderManager().viewerPosY;
        final double n3 = array[0].getZ() - mc.getRenderManager().viewerPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
          Theme theme = Theme.getCurrentTheme();
        final int e = ColorUtil.applyOpacity(theme.getColors().getSecond(), 0.7f).getRGB();
        final float n4 = (e >> 24 & 0xFF) / 255.0f;
        final float n5 = (e >> 16 & 0xFF) / 255.0f;
        final float n6 = (e >> 8 & 0xFF) / 255.0f;
        final float n7 = (e & 0xFF) / 255.0f;
        GL11.glColor4d(n5, n6, n7, n4);
        AxisAlignedBB axisAlignedBB;
        if (array[0].getX() != array[1].getX()) {
            if (array[0].getX() > array[1].getX()) {
                axisAlignedBB = new AxisAlignedBB(n - 1.0, n2, n3, n + 1.0, n2 + getBlockHeight(), n3 + 1.0);
            } else {
                axisAlignedBB = new AxisAlignedBB(n, n2, n3, n + 2.0, n2 + getBlockHeight(), n3 + 1.0);
            }
        } else if (array[0].getZ() > array[1].getZ()) {
            axisAlignedBB = new AxisAlignedBB(n, n2, n3 - 1.0, n + 1.0, n2 + getBlockHeight(), n3 + 1.0);
        } else {
            axisAlignedBB = new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + getBlockHeight(), n3 + 2.0);
        }
        if (mode.is("Shaded")) {
            RenderUtil.drawBoundingBox(axisAlignedBB, n5, n6, n7);
        }
        else if (mode.is("3D"))
        {
            Color color = ColorUtil.applyOpacity(theme.getColors().getSecond(), 1);
            RenderGlobal.drawOutlinedBoundingBox(axisAlignedBB, color.getRed(),color.getGreen(), color.getBlue(), color.getAlpha());
        }
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    private float getBlockHeight() {
        return (renderFullBlock.isEnabled() ? 1 : 0.5625F);
    }
}