/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package drunkclient.beta.IMPL.Module.impl.render;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.render.EventRender3D;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.set.Option;
import drunkclient.beta.UTILS.simpleTimer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class Xray
extends Module {
    public List<Integer> blocks = new ArrayList<Integer>();
    private final ArrayList<BlockPos> matchingBlocks = new ArrayList();
    public final Option<Boolean> diamonds = new Option<Boolean>("diamonds", "diamonds", true);
    public final Option<Boolean> gold = new Option<Boolean>("gold", "gold", true);
    public final Option<Boolean> iron = new Option<Boolean>("iron", "iron", false);
    public final Option<Boolean> coal = new Option<Boolean>("coal", "coal", false);
    public final Option<Boolean> tracers = new Option<Boolean>("tracers", "tracers", true);

    public Xray() {
        super("Xray", new String[]{"xrai", "oreesp"}, Type.RENDER, "xray lol");
        this.addValues(this.diamonds, this.gold, this.iron, this.coal, this.tracers);
    }

    @Override
    public void onEnable() {
        if (((Boolean)this.diamonds.getValue()).booleanValue()) {
            this.blocks.add(56);
        }
        if (((Boolean)this.gold.getValue()).booleanValue()) {
            this.blocks.add(14);
        }
        if (((Boolean)this.iron.getValue()).booleanValue()) {
            this.blocks.add(15);
        }
        if (((Boolean)this.coal.getValue()).booleanValue()) {
            this.blocks.add(16);
        }
        Xray.mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        this.blocks.clear();
        Xray.mc.renderGlobal.loadRenderers();
    }

    public List<Integer> getBlocks() {
        return this.blocks;
    }

    @EventHandler
    public void onRender(EventRender3D event) {
        try {
            Iterator<BlockPos> iterator = this.matchingBlocks.iterator();
            while (iterator.hasNext()) {
                BlockPos pos = iterator.next();
                if (!Xray.mc.theWorld.getChunkFromBlockCoords(pos).isLoaded()) continue;
                if (!Xray.mc.theWorld.getChunkFromBlockCoords(Minecraft.thePlayer.getPosition()).isLoaded()) continue;
                Xray.drawPortalESP(pos, 255, 255, 255, 0, 255, 255, 255, 255, 1);
                double d = (double)pos.getX() + 0.5;
                mc.getRenderManager();
                double d2 = d - RenderManager.renderPosX;
                double d3 = (double)pos.getY() + 0.5;
                mc.getRenderManager();
                double d4 = d3 - RenderManager.renderPosY;
                double d5 = (double)pos.getZ() + 0.5;
                mc.getRenderManager();
                Xray.drawLine(d2, d4, d5 - RenderManager.renderPosZ, 0.0, 255.0f, 255.0f, 255.0f, 255.0f);
            }
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @EventHandler
    public void onUpdate(EventPreUpdate event) {
        try {
            if (!simpleTimer.isStarted()) {
                simpleTimer.start();
            }
            if (simpleTimer.hasTimeElapsed(20000.0)) {
                simpleTimer.start();
                this.matchingBlocks.clear();
            }
            int range = 200;
            int modulo = Minecraft.thePlayer.ticksExisted % 64;
            int startY = 255 - modulo * 4;
            int endY = startY - 4;
            BlockPos playerPos = new BlockPos(Minecraft.thePlayer.posX, 0.0, Minecraft.thePlayer.posZ);
            int y = startY;
            while (y > endY) {
                for (int x = range; x > -range; --x) {
                    for (int z = range; z > -range; --z) {
                        if (this.matchingBlocks.size() >= 10000) return;
                        BlockPos pos = playerPos.add(x, y, z);
                        if (Xray.moreThanOnce(this.matchingBlocks, pos)) {
                            this.matchingBlocks.remove(pos);
                        }
                        if (!Xray.mc.theWorld.getChunkFromBlockCoords(pos).isLoaded()) {
                            this.matchingBlocks.remove(pos);
                        }
                        this.matchingBlocks.add(pos);
                    }
                }
                --y;
            }
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private static boolean moreThanOnce(ArrayList<BlockPos> list, BlockPos searched) {
        int numCount = 0;
        for (BlockPos thisNum : list) {
            if (thisNum != searched) continue;
            ++numCount;
        }
        if (numCount <= true) return false;
        return true;
    }

    private static void drawPortalESP(BlockPos blockPos, int red, int green, int blue, int alpha, int outlineR, int outlineG, int outlineB, int outlineA, int outlineWidth) {
        try {
            mc.getRenderManager();
            double renderPosX = RenderManager.renderPosX;
            mc.getRenderManager();
            double renderPosY = RenderManager.renderPosY;
            mc.getRenderManager();
            double renderPosZ = RenderManager.renderPosZ;
            double x = (double)blockPos.getX() - renderPosX;
            double y = (double)blockPos.getY() - renderPosY;
            double z = (double)blockPos.getZ() - renderPosZ;
            GL11.glPushMatrix();
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)outlineWidth);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glLineWidth((float)2.0f);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            GL11.glPopMatrix();
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private static void drawLine(double posx, double posy, double posz, double up, float red, float green, float blue, float opacity) {
        Xray.drawLineFromPosToPos(Minecraft.thePlayer.getEyeHeight(), (double)Minecraft.thePlayer.getEyeHeight() + (double)Minecraft.thePlayer.getEyeHeight(), Minecraft.thePlayer.getEyeHeight(), posx, posy, posz, up, red, green, blue, opacity);
    }

    private static void drawLineFromPosToPos(double posx, double posy, double posz, double posx2, double posy2, double posz2, double up, float red, float green, float blue, float opacity) {
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)0.5f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)opacity);
        GlStateManager.disableLighting();
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)posx, (double)posy, (double)posz);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        GL11.glVertex3d((double)posx2, (double)(posy2 + up), (double)posz2);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glColor3d((double)1.0, (double)1.0, (double)1.0);
    }
}

