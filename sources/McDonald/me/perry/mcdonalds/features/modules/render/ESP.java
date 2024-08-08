// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.render;

import net.minecraft.util.math.Vec3d;
import java.util.Iterator;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityXPOrb;
import me.perry.mcdonalds.util.RenderUtil;
import java.awt.Color;
import net.minecraft.client.renderer.RenderGlobal;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import me.perry.mcdonalds.util.EntityUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;
import me.perry.mcdonalds.event.events.Render3DEvent;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class ESP extends Module
{
    private static ESP INSTANCE;
    private final Setting<Boolean> items;
    private final Setting<Boolean> xporbs;
    private final Setting<Boolean> xpbottles;
    private final Setting<Boolean> pearl;
    private final Setting<Integer> red;
    private final Setting<Integer> green;
    private final Setting<Integer> blue;
    private final Setting<Integer> boxAlpha;
    private final Setting<Integer> alpha;
    
    public ESP() {
        super("ESP", "Renders a nice ESP.", Category.RENDER, false, false, false);
        this.items = (Setting<Boolean>)this.register(new Setting("Items", (T)false));
        this.xporbs = (Setting<Boolean>)this.register(new Setting("XpOrbs", (T)false));
        this.xpbottles = (Setting<Boolean>)this.register(new Setting("XpBottles", (T)true));
        this.pearl = (Setting<Boolean>)this.register(new Setting("Pearls", (T)true));
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)0, (T)0, (T)255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)0, (T)0, (T)255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)255, (T)0, (T)255));
        this.boxAlpha = (Setting<Integer>)this.register(new Setting("BoxAlpha", (T)120, (T)0, (T)255));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", (T)255, (T)0, (T)255));
        this.setInstance();
    }
    
    public static ESP getInstance() {
        if (ESP.INSTANCE == null) {
            ESP.INSTANCE = new ESP();
        }
        return ESP.INSTANCE;
    }
    
    private void setInstance() {
        ESP.INSTANCE = this;
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (this.items.getValue()) {
            int i = 0;
            for (final Entity entity : ESP.mc.world.loadedEntityList) {
                if (entity instanceof EntityItem) {
                    if (ESP.mc.player.getDistanceSq(entity) >= 2500.0) {
                        continue;
                    }
                    final Vec3d interp = EntityUtil.getInterpolatedRenderPos(entity, ESP.mc.getRenderPartialTicks());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.renderFilledBox(bb, this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.boxAlpha.getValue() / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    RenderUtil.drawBlockOutline(bb, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), 1.0f);
                    if (++i < 50) {
                        continue;
                    }
                    break;
                }
            }
        }
        if (this.xporbs.getValue()) {
            int i = 0;
            for (final Entity entity : ESP.mc.world.loadedEntityList) {
                if (entity instanceof EntityXPOrb) {
                    if (ESP.mc.player.getDistanceSq(entity) >= 2500.0) {
                        continue;
                    }
                    final Vec3d interp = EntityUtil.getInterpolatedRenderPos(entity, ESP.mc.getRenderPartialTicks());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.renderFilledBox(bb, this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.boxAlpha.getValue() / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    RenderUtil.drawBlockOutline(bb, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), 1.0f);
                    if (++i < 50) {
                        continue;
                    }
                    break;
                }
            }
        }
        if (this.pearl.getValue()) {
            int i = 0;
            for (final Entity entity : ESP.mc.world.loadedEntityList) {
                if (entity instanceof EntityEnderPearl) {
                    if (ESP.mc.player.getDistanceSq(entity) >= 2500.0) {
                        continue;
                    }
                    final Vec3d interp = EntityUtil.getInterpolatedRenderPos(entity, ESP.mc.getRenderPartialTicks());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.renderFilledBox(bb, this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.boxAlpha.getValue() / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    RenderUtil.drawBlockOutline(bb, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), 1.0f);
                    if (++i < 50) {
                        continue;
                    }
                    break;
                }
            }
        }
        if (this.xpbottles.getValue()) {
            int i = 0;
            for (final Entity entity : ESP.mc.world.loadedEntityList) {
                if (entity instanceof EntityExpBottle) {
                    if (ESP.mc.player.getDistanceSq(entity) >= 2500.0) {
                        continue;
                    }
                    final Vec3d interp = EntityUtil.getInterpolatedRenderPos(entity, ESP.mc.getRenderPartialTicks());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.renderFilledBox(bb, this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.boxAlpha.getValue() / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    RenderUtil.drawBlockOutline(bb, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), 1.0f);
                    if (++i < 50) {
                        continue;
                    }
                    break;
                }
            }
        }
    }
    
    static {
        ESP.INSTANCE = new ESP();
    }
}
