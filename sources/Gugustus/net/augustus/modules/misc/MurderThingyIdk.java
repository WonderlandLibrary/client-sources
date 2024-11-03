package net.augustus.modules.misc;

import net.augustus.events.EventRender2D;
import net.augustus.events.EventWorld;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.utils.skid.idek.Esp2DUtil;
import net.augustus.utils.skid.lorious.ColorUtils;
import net.augustus.utils.skid.tenacity.MathUtils;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MurderThingyIdk extends Module {

    private final IntBuffer viewport;
    private final FloatBuffer modelview;
    private final FloatBuffer projection;
    private final FloatBuffer vector;
    private ArrayList<String> murderers = new ArrayList<>();
    public MurderThingyIdk() {
        super("MurderThingy", Color.white, Categorys.MISC);
        this.viewport = GLAllocation.createDirectIntBuffer(16);
        this.modelview = GLAllocation.createDirectFloatBuffer(16);
        this.projection = GLAllocation.createDirectFloatBuffer(16);
        this.vector = GLAllocation.createDirectFloatBuffer(4);
    }

    @Override
    public void onEnable() {
        murderers.clear();
    }

    @EventTarget
    public void onWorld(EventWorld eventWorld) {
        murderers.clear();
    }

    @EventTarget
    public void onUpdate(EventRender2D event) {
        for(Entity e : mc.theWorld.loadedEntityList) {
            if(!murderers.contains(e.getName())) {
                if (e instanceof EntityPlayer) {
                    if (((EntityPlayer) e).getHeldItem() != null) {
                        if (((EntityPlayer) e).getHeldItem().getItem() instanceof ItemSword) {
                            murderers.add(e.getName());
                        }
                    }
                }
            }
        }
        name2d();
        gold();
    }

    private void gold() {
    }

    private void name2d() {
        GL11.glPushMatrix();
        float partialTicks = mc.timer.renderPartialTicks;
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int scaleFactor = scaledResolution.getScaleFactor();
        double scaling = (double)scaleFactor / Math.pow(scaleFactor, 2.0D);
        GL11.glScaled(scaling, scaling, scaling);
        EntityRenderer entityRenderer = mc.entityRenderer;

        for(Entity entity : mc.theWorld.loadedEntityList) {
            if(entity instanceof EntityPlayer) {
                if (entity == mc.thePlayer) continue;
                if (Esp2DUtil.isInViewFrustrum(entity)) {
                    double x = Esp2DUtil.interpolate(entity.posX, entity.lastTickPosX, partialTicks);
                    double y = Esp2DUtil.interpolate(entity.posY, entity.lastTickPosY, partialTicks);
                    double z = Esp2DUtil.interpolate(entity.posZ, entity.lastTickPosZ, partialTicks);
                    double width = (double) entity.width / 1.5D;
                    double height = entity.height;
                    AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
                    List<Vector3d> vectors = Arrays.asList(new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ));
                    entityRenderer.setupCameraTransform(partialTicks, 0);
                    Vector4d position = null;

                    for (Vector3d o : vectors) {
                        Vector3d vector = o;
                        vector = this.project2D(scaleFactor, vector.x - RenderManager.viewerPosX, vector.y - RenderManager.viewerPosY + height, vector.z - RenderManager.viewerPosZ);
                        if (vector != null && vector.z >= 0.0D && vector.z < 1.0D) {
                            if (position == null) {
                                position = new Vector4d(vector.x, vector.y, vector.z, 0.0D);
                            }

                            position.x = Math.min(vector.x, position.x);
                            position.y = Math.min(vector.y, position.y);
                            position.z = Math.max(vector.x, position.z);
                            position.w = Math.max(vector.y, position.w);
                        }
                    }

                    if (position != null) {
                        entityRenderer.setupOverlayRendering();
                        double posX = position.x;
                        double posY = position.y;
                        double endPosX = position.z;
                        double endPosY = position.w;
                        if(murderers.contains(entity.getName())) {
                            double distance = MathUtils.round(Math.abs(Math.sqrt(entity.posZ * entity.posZ) - Math.sqrt(entity.posX * entity.posX)), 2);
                            mc.fontRendererObj.drawString("Murderer " + distance + "m", (float) ((posX + endPosX) / 2) - ((float) mc.fontRendererObj.getStringWidth("Murderer " + distance + "m") /2), (float) ((posY + endPosY) / 2), ColorUtils.getRainbow(0.5F, 1, 1).getRGB());
                        }
                    }
                }
            }
        }
        GL11.glPopMatrix();
        GlStateManager.enableBlend();
        GlStateManager.resetColor();
        entityRenderer.setupOverlayRendering();
    }
    private Vector3d project2D(int scaleFactor, double x, double y, double z) {
        GL11.glGetFloat(2982, this.modelview);
        GL11.glGetFloat(2983, this.projection);
        GL11.glGetInteger(2978, this.viewport);
        return GLU.gluProject((float)x, (float)y, (float)z, this.modelview, this.projection, this.viewport, this.vector) ? new Vector3d(this.vector.get(0) / (float)scaleFactor, ((float) Display.getHeight() - this.vector.get(1)) / (float)scaleFactor, this.vector.get(2)) : null;
    }
}
