package gq.vapu.czfclient.Module.Modules.Render;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Render.EventRender3D;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.Render.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Iterator;

public class ChestESP extends Module {
    public ChestESP() {
        super("ChestESP", new String[]{"chesthack"}, ModuleType.Render);
        this.setColor(new Color(90, 209, 165).getRGB());
    }

    public void drawChestOutlines(TileEntity ent, float partialTicks) {
        int entityDispList = GL11.glGenLists(1);
        GL11.glPushMatrix();
        mc.entityRenderer.setupCameraTransform(partialTicks, 0);
        GL11.glMatrixMode(5888);
        RenderHelper.enableStandardItemLighting();
        GL11.glEnable(2884);
        Minecraft.getMinecraft();
        Frustum frustrum = new Frustum();
        frustrum.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        GL11.glDisable(2929);
        GL11.glDepthMask(true);
        GL11.glNewList(entityDispList, 4864);
        for (Object obj : mc.theWorld.loadedTileEntityList) {
            TileEntity entity = (TileEntity) obj;
            if (!(entity instanceof TileEntityLockable))
                continue;
            GL11.glLineWidth(3.0f);
            GL11.glEnable(3042);
            GL11.glEnable(2848);
            GL11.glDisable(3553);
            GL11.glPushMatrix();
            GL11.glTranslated((double) entity.getPos().getX() - RenderManager.renderPosX,
                    (double) entity.getPos().getY() - RenderManager.renderPosY,
                    (double) entity.getPos().getZ() - RenderManager.renderPosZ);
            GL11.glColor4f(0.31f, 1.31f, 2.18f, 1.0f);
            TileEntityRendererDispatcher.instance.renderTileEntityAt(entity, 0.0, 0.0, 0.0, partialTicks);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
        }
        GL11.glEndList();
        GL11.glPolygonMode(1032, 6913);
        GL11.glCallList(entityDispList);
        GL11.glPolygonMode(1032, 6912);
        GL11.glCallList(entityDispList);
        GL11.glPolygonMode(1032, 6914);
        GL11.glCallList(entityDispList);
        GL11.glPolygonMode(1032, 6913);
        GL11.glCallList(entityDispList);
        GL11.glPolygonMode(1032, 6912);
        GL11.glCallList(entityDispList);
        GL11.glPolygonMode(1032, 6914);
        Minecraft.getMinecraft().entityRenderer.disableLightmap();
        RenderHelper.disableStandardItemLighting();
        Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
        GL11.glDisable(2960);
        GL11.glPopMatrix();
        Minecraft.getMinecraft().entityRenderer.disableLightmap();
        RenderHelper.disableStandardItemLighting();
        Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
        GL11.glDeleteLists(entityDispList, 1);
    }

    public void draw(Block block, double x, double y, double z, double xo, double yo, double zo) {
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glDepthMask(false);
        GL11.glLineWidth(0.75f);
        if (block == Blocks.ender_chest) {
            GL11.glColor4f(0.4f, 0.2f, 1.0f, 1.0f);
            x += 0.0650000000745058;
            y += 0.0;
            z += 0.06000000074505806;
            xo -= 0.13000000149011612;
            yo -= 0.1200000149011612;
            zo -= 0.12000000149011612;
        } else if (block == Blocks.chest) {
            GL11.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
            x += 0.0650000000745058;
            y += 0.0;
            z += 0.06000000074505806;
            xo -= 0.13000000149011612;
            yo -= 0.1200000149011612;
            zo -= 0.12000000149011612;
        } else if (block == Blocks.trapped_chest) {
            GL11.glColor4f(1.0f, 0.6f, 0.0f, 1.0f);
            x += 0.0650000000745058;
            y += 0.0;
            z += 0.06000000074505806;
            xo -= 0.13000000149011612;
            yo -= 0.1200000149011612;
            zo -= 0.12000000149011612;
        }
        RenderUtil.R3DUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + xo, y + yo, z + zo));
        if (block == Blocks.ender_chest) {
            GL11.glColor4f(0.4f, 0.2f, 1.0f, 0.21f);
        } else if (block == Blocks.chest) {
            GL11.glColor4f(1.0f, 1.0f, 0.0f, 0.11f);
        } else if (block == Blocks.trapped_chest) {
            GL11.glColor4f(1.0f, 0.6f, 0.0f, 0.11f);
        }
        RenderUtil.R3DUtils.drawFilledBox(new AxisAlignedBB(x, y, z, x + xo, y + yo, z + zo));
        if (block == Blocks.ender_chest) {
            GL11.glColor4f(0.4f, 0.2f, 1.0f, 1.0f);
        } else if (block == Blocks.chest) {
            GL11.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
        } else if (block == Blocks.trapped_chest) {
            GL11.glColor4f(1.0f, 0.6f, 0.0f, 1.0f);
        }
        RenderUtil.R3DUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + xo, y + yo, z + zo));
        GL11.glDepthMask(true);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
    }

    @EventHandler
    public void onRender(EventRender3D eventRender) {
        Iterator var3;
        var3 = mc.theWorld.loadedTileEntityList.iterator();
        while (true) {
            TileEntity ent;
            do {
                if (!var3.hasNext()) {
                    return;
                }

                ent = (TileEntity) var3.next();
            } while (!(ent instanceof TileEntityChest) && !(ent instanceof TileEntityEnderChest));

            mc.getRenderManager();
            double x = (double) ent.getPos().getX() - RenderManager.renderPosX;
            mc.getRenderManager();
            double y = (double) ent.getPos().getY() - RenderManager.renderPosY;
            mc.getRenderManager();
            double z = (double) ent.getPos().getZ() - RenderManager.renderPosZ;
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glColor4f(1, 1, 1, 0.25F);
            RenderUtil.drawBoundingBox(new AxisAlignedBB(x + ent.getBlockType().getBlockBoundsMinX(),
                    y + ent.getBlockType().getBlockBoundsMinY(), z + ent.getBlockType().getBlockBoundsMinZ(),
                    x + ent.getBlockType().getBlockBoundsMaxX(), y + ent.getBlockType().getBlockBoundsMaxY(),
                    z + ent.getBlockType().getBlockBoundsMaxZ()));
            GL11.glDisable(2848);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
    }
}
