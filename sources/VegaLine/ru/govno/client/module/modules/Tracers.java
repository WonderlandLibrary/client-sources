/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.module.Module;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;

public class Tracers
extends Module {
    private final AnimationUtils alphaPC = new AnimationUtils(0.0f, 0.0f, 0.025f);
    private final Tessellator tessellator = Tessellator.getInstance();
    private final BufferBuilder buffer = this.tessellator.getBuffer();
    private final List<Entity> entities = new CopyOnWriteArrayList<Entity>();

    public Tracers() {
        super("Tracers", 0, Module.Category.RENDER);
        this.settings.add(new Settings("Players", true, (Module)this));
        this.settings.add(new Settings("Player color", new Color(255, 40, 95, 195).getRGB(), (Module)this, () -> this.currentBooleanValue("Players")));
        this.settings.add(new Settings("Friends", true, (Module)this));
        this.settings.add(new Settings("Friend color", new Color(0, 255, 0).getRGB(), (Module)this, () -> this.currentBooleanValue("Friends")));
        this.settings.add(new Settings("Mobs", false, (Module)this));
        this.settings.add(new Settings("Mob color", new Color(0, 170, 120, 110).getRGB(), (Module)this, () -> this.currentBooleanValue("Mobs")));
        this.settings.add(new Settings("Line width", 0.05f, 3.5f, 0.05f, this, () -> this.currentBooleanValue("Players") || this.currentBooleanValue("Friends") || this.currentBooleanValue("Mobs")));
    }

    private float alphaPC(boolean modIsEnabled, List<Entity> entities) {
        this.alphaPC.to = modIsEnabled && entities != null && Tracers.mc.gameSettings.thirdPersonView == 0 ? 1.0f : 0.0f;
        return this.alphaPC.getAnim();
    }

    private final int[] getColor(Entity entityIn, float alphaPC) {
        String pickerName;
        EntityOtherPlayerMP player;
        int color = 0;
        String string = entityIn instanceof EntityOtherPlayerMP ? (Client.friendManager.isFriend((player = (EntityOtherPlayerMP)entityIn).getName()) ? "Friend" : "Player") : (pickerName = entityIn instanceof EntityMob || entityIn instanceof EntityAnimal ? "Mob" : null);
        if (pickerName != null) {
            color = this.currentColorValue(pickerName + " color");
        }
        color = ColorUtils.swapAlpha(color, alphaPC * (float)ColorUtils.getAlphaFromColor(color));
        int color2 = ColorUtils.swapAlpha(color, alphaPC * (float)ColorUtils.getAlphaFromColor(color) / 2.0f);
        return new int[]{color, color2};
    }

    private final double interpolate(double val, double val2, float pt) {
        return val + (val2 - val) * (double)pt;
    }

    private final Vec3d getRenderEntityPos(Entity fromEntity, float pTicks) {
        double x = this.interpolate(fromEntity.lastTickPosX, fromEntity.posX, pTicks);
        double y = this.interpolate(fromEntity.lastTickPosY, fromEntity.posY, pTicks);
        double z = this.interpolate(fromEntity.lastTickPosZ, fromEntity.posZ, pTicks);
        return new Vec3d(x, y, z);
    }

    private final DVec3d DVecToEntity(Entity fromEntity, RenderManager manager) {
        float radiansF = (float)(-Math.PI) / 180;
        Vec3d returnPos = new Vec3d(RenderManager.viewerPosX, RenderManager.viewerPosY, RenderManager.viewerPosZ);
        Vec3d second = this.getRenderEntityPos(fromEntity, mc.getRenderPartialTicks()).addVector(0.0, (double)fromEntity.height * 0.5, 0.0).addVector(-returnPos.xCoord, -returnPos.yCoord, -returnPos.zCoord);
        Vec3d first = new Vec3d(0.0, 0.0, 0.285).rotatePitch(Minecraft.player.rotationPitch * radiansF).rotateYaw(Minecraft.player.rotationYaw * radiansF).addVector(0.0, Minecraft.player.getEyeHeight(), 0.0);
        return new DVec3d(first, second);
    }

    private final void vertexFromVec3d(Vec3d vec, int color) {
        this.buffer.pos(vec).color(color).endVertex();
    }

    private final void setup3dLinesRender(Runnable displays, float lineW) {
        boolean viewBobbing = Tracers.mc.gameSettings.viewBobbing;
        Tracers.mc.gameSettings.viewBobbing = false;
        Tracers.mc.entityRenderer.setupCameraTransform(mc.getRenderPartialTicks(), 0);
        Tracers.mc.gameSettings.viewBobbing = viewBobbing;
        Tracers.mc.entityRenderer.disableLightmap();
        GL11.glDisable(2896);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(1.0E-4f + lineW);
        this.buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        displays.run();
        this.tessellator.draw();
        GL11.glLineWidth(1.0f);
        GL11.glHint(3154, 4352);
        GL11.glDisable(2848);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
        GL11.glShadeModel(7424);
    }

    private static final boolean[] enabledTypesENTITY(Module mod) {
        boolean players = mod.currentBooleanValue("Players");
        boolean friends = mod.currentBooleanValue("Friends");
        boolean mobs = mod.currentBooleanValue("Mobs");
        return new boolean[]{players, friends, mobs};
    }

    private final boolean updatedList(boolean[] currents) {
        if (Tracers.mc.world == null) {
            return false;
        }
        this.entities.clear();
        Tracers.mc.world.getLoadedEntityList().forEach(entity -> {
            block2: {
                EntityLivingBase base;
                block4: {
                    block3: {
                        if (entity == null || !(entity instanceof EntityLivingBase) || !(base = (EntityLivingBase)entity).isEntityAlive()) break block2;
                        if (!(base instanceof EntityOtherPlayerMP)) break block3;
                        EntityOtherPlayerMP player = (EntityOtherPlayerMP)base;
                        if (currents[0] && !Client.friendManager.isFriend(player.getName()) || currents[1] && Client.friendManager.isFriend(player.getName())) break block4;
                    }
                    if (!currents[2] || !(base instanceof EntityMob) && !(base instanceof EntityAnimal)) break block2;
                }
                this.entities.add(base);
            }
        });
        return Minecraft.player != null && this.entities != null || !this.entities.isEmpty();
    }

    private final void drawBeginTracer(Entity fromEntity, RenderManager manager, float alphaPC) {
        int[] color = this.getColor(fromEntity, alphaPC);
        if (ColorUtils.getAlphaFromColor(color[0]) < 1 || ColorUtils.getAlphaFromColor(color[0]) > 255) {
            return;
        }
        DVec3d dvec = this.DVecToEntity(fromEntity, manager);
        assert (mc.getRenderViewEntity() != null);
        this.vertexFromVec3d(dvec.second, color[0]);
        this.vertexFromVec3d(dvec.first, color[1]);
    }

    private final void drawTracers(List entities, RenderManager manager, float alphaPC, float lineW) {
        this.setup3dLinesRender(() -> entities.forEach(e -> this.drawBeginTracer((Entity)e, manager, alphaPC)), lineW);
    }

    @Override
    public void alwaysRender3D() {
        float alphaPC = this.alphaPC(this.actived, this.entities);
        if (Panic.stop || alphaPC < 0.05f) {
            return;
        }
        RenderManager manager = mc.getRenderManager();
        boolean[] currentTypes = Tracers.enabledTypesENTITY(this);
        float lineWidth = this.currentFloatValue("Line width");
        if (this.updatedList(currentTypes)) {
            this.drawTracers(this.entities, manager, alphaPC, lineWidth);
        }
    }

    protected final class DVec3d {
        Vec3d first;
        Vec3d second;

        private DVec3d(Vec3d first, Vec3d second) {
            this.first = first;
            this.second = second;
        }
    }
}

