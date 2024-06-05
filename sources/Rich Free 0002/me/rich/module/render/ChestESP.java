package me.rich.module.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event3D;
import me.rich.helpers.render.RenderHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.math.AxisAlignedBB;

public class ChestESP extends Feature {
    public ChestESP() {
        super("ChestESP", 0, Category.RENDER);
    }

    @EventTarget
    public void render3d(Event3D event) {
        for (TileEntity tile : mc.world.loadedTileEntityList) {
            double posX = tile.getPos().getX() - mc.getRenderManager().renderPosX;
            double posY = tile.getPos().getY() - mc.getRenderManager().renderPosY;
            double posZ = tile.getPos().getZ() - mc.getRenderManager().renderPosZ;
            if (tile instanceof TileEntityChest) {
                AxisAlignedBB bb = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(posX, posY, posZ);
                TileEntityChest adjacent = null;
                if (((TileEntityChest) tile).adjacentChestXNeg != null)
                    adjacent = ((TileEntityChest) tile).adjacentChestXNeg;
                if (((TileEntityChest) tile).adjacentChestXPos != null)
                    adjacent = ((TileEntityChest) tile).adjacentChestXPos;
                if (((TileEntityChest) tile).adjacentChestZNeg != null)
                    adjacent = ((TileEntityChest) tile).adjacentChestZNeg;
                if (((TileEntityChest) tile).adjacentChestZPos != null)
                    adjacent = ((TileEntityChest) tile).adjacentChestZPos;
                if (adjacent != null)
                    bb = bb.union(new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(
                            adjacent.getPos().getX() - mc.getRenderManager().renderPosX,
                            adjacent.getPos().getY() - mc.getRenderManager().renderPosY,
                            adjacent.getPos().getZ() - mc.getRenderManager().renderPosZ));

                drawBlockESP(bb, 44, 44, 44, 120, 1f);
            }
        }
    }

    private void drawBlockESP(AxisAlignedBB bb, float red, float green, float blue, float alpha, float width) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        RenderHelper.color(Main.getClientColor().getRed() / 255.0f, Main.getClientColor().getGreen() / 255.0f,
				Main.getClientColor().getBlue() / 255.0f, 90.0f / 255.0f);
        RenderHelper.drawBoundingBox(bb);
        GL11.glLineWidth(width);
        GL11.glColor4f(red / 255f, green / 255f, blue / 255f, alpha / 5f);
        RenderHelper.drawOutlinedBoundingBox(bb);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }
    @Override
    public void onEnable() {
        super.onEnable();
        NotificationPublisher.queue(getName(), "was enabled.", NotificationType.INFO);
    }

    public void onDisable() {
        NotificationPublisher.queue(getName(), "was disabled.", NotificationType.INFO);
        super.onDisable();
    }
}