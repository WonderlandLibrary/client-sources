/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.Event3D;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class Trails
extends Module {
    public ArrayList<Trailses> tr = new ArrayList();
    public Map<Entity, List<AkrienTr>> tra = new HashMap<Entity, List<AkrienTr>>();

    public Trails() {
        super("Trails", 0, Module.Category.RENDER);
        this.settings.add(new Settings("Targets", "Self", (Module)this, new String[]{"Self", "All&Self", "All"}));
        this.settings.add(new Settings("Mode", "Points", (Module)this, new String[]{"Points", "Default", "Default2", "Russia", "Particles", "BackTracks"}));
        this.settings.add(new Settings("Particle", "Fire", this, new String[]{"Fire", "Ench", "Spark"}, () -> this.currentMode("Mode").equalsIgnoreCase("Particles")));
        this.settings.add(new Settings("MaxDistance", 5.0f, 10.0f, 0.5f, this, () -> !this.currentMode("Mode").equalsIgnoreCase("Particles") && !this.currentMode("Mode").equalsIgnoreCase("BackTracks")));
        this.settings.add(new Settings("CountCrate", 2.0f, 5.0f, 1.0f, this, () -> this.currentMode("Mode").equalsIgnoreCase("Particles")));
        this.settings.add(new Settings("Alpha", 200.0f, 255.0f, 0.0f, this, () -> !this.currentMode("Mode").equalsIgnoreCase("Particles")));
        this.settings.add(new Settings("BloomEffect", true, (Module)this, () -> !this.currentMode("Mode").equalsIgnoreCase("Particles") && !this.currentMode("Mode").equalsIgnoreCase("BackTracks")));
        this.settings.add(new Settings("Ticks", 3.0f, 15.0f, 1.0f, this, () -> this.currentMode("Mode").equalsIgnoreCase("BackTracks")));
        this.settings.add(new Settings("NoFirstPerson", true, (Module)this, () -> this.currentMode("Targets").contains("Self")));
    }

    int getTracksSize(EntityPlayer player) {
        return (int)this.currentFloatValue("Ticks") + 1;
    }

    Vec3d getVecByPlayer(EntityPlayer player) {
        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;
        return new Vec3d(x, y, z);
    }

    void updateTrack(EntityPlayer player) {
        boolean playerMove;
        boolean bl = playerMove = MathUtils.getDifferenceOf(player.posX, player.lastTickPosX) > 0.03 || MathUtils.getDifferenceOf(player.posY, player.lastTickPosY) > 0.01 || MathUtils.getDifferenceOf(player.posZ, player.lastTickPosZ) > 0.03;
        if (playerMove && Trails.doRenderTrail(player)) {
            player.tracks.add(new Track(this.getVecByPlayer(player), player.rotationYaw, player.rotationPitch, player.prevLimbSwingAmount, player.limbSwingAmount, player.limbSwing));
        } else if (player.tracks.size() > 0) {
            player.tracks.remove(player.tracks.get(0));
        }
        if (player.tracks.size() >= this.getTracksSize(player)) {
            player.tracks.remove(player.tracks.get(0));
        }
    }

    List<EntityPlayer> players() {
        CopyOnWriteArrayList<EntityPlayer> p = new CopyOnWriteArrayList<EntityPlayer>();
        for (EntityPlayer e : Trails.mc.world.playerEntities) {
            if (e == null || e.getHealth() == 0.0f || e.getPosition().equals(new BlockPos(0, 0, 0))) continue;
            p.add(e);
        }
        return p;
    }

    void startDraws() {
        GL11.glPushMatrix();
        GL11.glDisable(2896);
        Trails.mc.entityRenderer.disableLightmap();
        GL11.glEnable(3042);
        GlStateManager.enableAlpha();
        GL11.glDisable(3008);
        GL11.glTranslated(-RenderManager.renderPosX, -RenderManager.renderPosY, -RenderManager.renderPosZ);
        GL11.glBlendFunc(770, 1);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
    }

    void stopDraws() {
        Trails.mc.entityRenderer.enableLightmap();
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glBlendFunc(770, 771);
        GL11.glTranslated(RenderManager.renderPosX, RenderManager.renderPosY, RenderManager.renderPosZ);
        GL11.glPopMatrix();
    }

    Vec3d getPosByAABB(AxisAlignedBB aabb) {
        return new Vec3d(aabb.minX + (aabb.maxX - aabb.minX) / 2.0, aabb.minY, aabb.minZ + (aabb.maxZ - aabb.minZ) / 2.0);
    }

    void drawModel(EntityPlayer player, float pticks) {
        if (player == null) {
            return;
        }
        int i = 0;
        this.startDraws();
        for (Track track : player.tracks) {
            Vec3d vec;
            Vec3d prevVec = vec = track.pos;
            if (i - 1 > 0 && player.tracks.get(i - 1) != null && player.tracks.get((int)(i - 1)).pos != null) {
                prevVec = player.tracks.get((int)(i - 1)).pos;
            }
            double xDiff = vec.xCoord - prevVec.xCoord;
            double yDiff = vec.yCoord - prevVec.yCoord;
            double zDiff = vec.zCoord - prevVec.zCoord;
            Vec3d posVec = vec.addVector(xDiff * (double)pticks, yDiff * (double)pticks, zDiff * (double)pticks);
            int color = ClientColors.getColor1((int)((float)(++i) * 50.0f));
            float alphaPC = (float)i / (float)player.tracks.size();
            alphaPC = alphaPC > 0.5f ? 1.0f - alphaPC : alphaPC;
            RenderUtils.glColor(ClientColors.getColor1((int)((float)i * 50.0f), this.currentFloatValue("Alpha") / 255.0f * alphaPC * 0.2f));
            if (Minecraft.player.connection != null && Minecraft.player.connection.getPlayerInfo(player.getUniqueID()) != null) {
                GameType gm = Minecraft.player.connection.getPlayerInfo(player.getUniqueID()).getGameType();
                player.setGameType(GameType.SPECTATOR);
                player.noRenderArms = true;
                float prevPitch = player.rotationPitch;
                player.rotationPitch = track.pitch;
                float prevYaw = player.rotationYaw;
                player.rotationYaw = track.yaw;
                RenderLivingBase.silentMode = true;
                Trails.mc.renderManager.doRenderEntityNoShadow(player, posVec.xCoord, posVec.yCoord, posVec.zCoord, track.yaw, pticks, true);
                RenderLivingBase.silentMode = false;
                player.rotationPitch = prevPitch;
                player.rotationYaw = prevYaw;
                player.noRenderArms = false;
                player.setGameType(gm);
            }
            GlStateManager.resetColor();
        }
        this.stopDraws();
    }

    @Override
    public void alwaysRender3DV2(float partialTicks) {
        if (!this.actived || !this.currentMode("Mode").equalsIgnoreCase("BackTracks")) {
            return;
        }
        for (EntityPlayer player : this.players()) {
            if (player.tracks == null || player.tracks.size() == 0) continue;
            this.drawModel(player, partialTicks);
        }
    }

    @Override
    public void onUpdate() {
        if (this.currentMode("Mode").equalsIgnoreCase("BackTracks")) {
            for (EntityPlayer player : this.players()) {
                if (player == null || player.tracks == null) continue;
                this.updateTrack(player);
            }
        } else {
            for (EntityPlayer player : this.players()) {
                if (player.tracks.size() <= 0) continue;
                player.tracks.clear();
            }
        }
        if (this.currentMode("Mode").equalsIgnoreCase("Particles")) {
            int oldSetting = Trails.mc.gameSettings.particleSetting;
            boolean oldSetting2 = Trails.mc.gameSettings.ofFireworkParticles;
            boolean oldSetting3 = Trails.mc.gameSettings.ofPortalParticles;
            boolean oldSetting4 = Trails.mc.gameSettings.ofPotionParticles;
            boolean oldSetting5 = Trails.mc.gameSettings.ofVoidParticles;
            boolean oldSetting6 = Trails.mc.gameSettings.ofWaterParticles;
            Trails.mc.gameSettings.particleSetting = 1;
            Trails.mc.gameSettings.ofFireworkParticles = true;
            Trails.mc.gameSettings.ofPortalParticles = true;
            Trails.mc.gameSettings.ofPotionParticles = true;
            Trails.mc.gameSettings.ofVoidParticles = true;
            Trails.mc.gameSettings.ofWaterParticles = true;
            for (EntityPlayer e : Trails.mc.world.playerEntities) {
                if (!Trails.doRenderTrail(e)) continue;
                double yaw = (double)e.rotationYaw * 0.017453292;
                boolean ex = e == Minecraft.player;
                float x = (float)(e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)mc.getRenderPartialTicks());
                float y = (float)(e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)mc.getRenderPartialTicks());
                float z = (float)(e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)mc.getRenderPartialTicks());
                for (int i = 0; i < (int)this.currentFloatValue("CountCrate"); ++i) {
                    if (this.currentMode("Particle").equalsIgnoreCase("Fire")) {
                        Trails.mc.world.spawnParticle(EnumParticleTypes.LAVA, (double)x - Math.sin(yaw) * -0.45 * (double)ex, (double)y + 0.1, (double)z + Math.cos(yaw) * -0.45 * (double)ex, 0.0, 0.0, 0.0, 1);
                        continue;
                    }
                    if (this.currentMode("Particle").equalsIgnoreCase("Ench")) {
                        for (float h = 0.0f; h < e.height - 0.05f; h += 0.1f) {
                            Trails.mc.world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, (double)x - Math.sin(yaw) * -0.45 * (double)ex, (double)(y + h), (double)z + Math.cos(yaw) * -0.45 * (double)ex, 0.0, 0.0, 0.0, 1);
                        }
                        continue;
                    }
                    if (!this.currentMode("Particle").equalsIgnoreCase("Spark")) continue;
                    Trails.mc.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, (double)x - Math.sin(yaw) * -0.45 * (double)ex, (double)y + 0.4, (double)z + Math.cos(yaw) * -0.45 * (double)ex, 0.0, 0.0, 0.0, 1);
                }
            }
            Trails.mc.gameSettings.particleSetting = oldSetting;
            Trails.mc.gameSettings.ofFireworkParticles = oldSetting2;
            Trails.mc.gameSettings.ofPortalParticles = oldSetting3;
            Trails.mc.gameSettings.ofPotionParticles = oldSetting4;
            Trails.mc.gameSettings.ofVoidParticles = oldSetting5;
            Trails.mc.gameSettings.ofWaterParticles = oldSetting6;
        }
    }

    @Override
    public void onToggled(boolean actived) {
        if (!actived) {
            for (EntityPlayer player : this.players()) {
                if (player == null) continue;
                player.tracks.clear();
            }
            for (EntityPlayer e : Trails.mc.world.playerEntities) {
                e.trail.clear();
            }
            this.tr.clear();
        }
        super.onToggled(actived);
    }

    public static double getInterpolationSpeed(double speed) {
        return MathUtils.clamp(speed * 100.0 / (double)Minecraft.getDebugFPS(), 0.0 / (speed * 100.0) - (double)Minecraft.getDebugFPS(), speed * 100.0 / (double)Minecraft.getDebugFPS());
    }

    public static boolean doRenderTrail(Entity entityIn) {
        Module trails = Client.moduleManager.getModule("Trails");
        if (trails.currentMode("Targets").equalsIgnoreCase("Self") && entityIn instanceof EntityPlayerSP && (Trails.mc.gameSettings.thirdPersonView != 0 || !trails.currentBooleanValue("NoFirstPerson"))) {
            return true;
        }
        if (trails.currentMode("Targets").equalsIgnoreCase("All&Self")) {
            return !(entityIn instanceof EntityPlayerSP) || Trails.mc.gameSettings.thirdPersonView != 0 || !trails.currentBooleanValue("NoFirstPerson");
        }
        return trails.currentMode("Targets").equalsIgnoreCase("All") && entityIn != Minecraft.player;
    }

    @EventTarget
    public void onRender3D(Event3D event) {
        int GET_MAX_SIZE = (int)(this.currentFloatValue("MaxDistance") * 20.0f);
        int GET_MAX_SIZE2 = (int)(this.currentFloatValue("MaxDistance") * 100.0f);
        for (EntityPlayer e : Trails.mc.world.playerEntities) {
            double posX = e.posX;
            double prevX = posX - e.prevPosX;
            double posY = e.posY;
            double prevY = posY - e.prevPosY;
            double posZ = e.posZ;
            double prevZ = posZ - e.prevPosZ;
            double bps = Math.sqrt(prevX * prevX + prevY * prevY + prevZ * prevZ);
            float x = (float)(e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)mc.getRenderPartialTicks());
            float y = (float)(e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)mc.getRenderPartialTicks());
            float z = (float)(e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)mc.getRenderPartialTicks());
            if (bps != 0.0 && Trails.doRenderTrail(e) && !this.currentMode("Mode").equalsIgnoreCase("Particles") && !this.currentMode("Mode").equalsIgnoreCase("BackTracks")) {
                if (!(this.currentMode("Mode").equalsIgnoreCase("Default") || this.currentMode("Mode").equalsIgnoreCase("Default2") || this.currentMode("Mode").equalsIgnoreCase("Russia"))) {
                    this.tr.add(new Trailses(x, y + 0.2f, z));
                } else {
                    boolean DO;
                    boolean bl = DO = !(e instanceof EntityPlayerSP) || Trails.mc.gameSettings.thirdPersonView != 0 || !this.currentBooleanValue("NoFirstPerson");
                    if (DO) {
                        e.trail.add(new AkrienTr(x, y, z));
                    } else {
                        e.trail.clear();
                    }
                }
            }
            GlStateManager.pushMatrix();
            GlStateManager.translate(-RenderManager.renderPosX, -RenderManager.renderPosY, -RenderManager.renderPosZ);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, this.currentBooleanValue("BloomEffect") ? GlStateManager.DestFactor.ONE : GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.shadeModel(7425);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glDisable(3008);
            GlStateManager.disableCull();
            Trails.mc.entityRenderer.disableLightmap();
            GL11.glBegin(5);
            int color = -1;
            float alpha = 0.0f;
            int step = 0;
            for (AkrienTr t2 : e.trail) {
                long deltaTime = Minecraft.getSystemTime() - t2.spawnTime;
                if (deltaTime >= (long)GET_MAX_SIZE2 || !e.isEntityAlive()) {
                    e.trail.remove(t2);
                }
                t2.age = deltaTime / (long)GET_MAX_SIZE2;
            }
            if (this.currentMode("Mode").equalsIgnoreCase("Russia")) {
                int[] colors = new int[]{ColorUtils.getColor(255, 0, 0), ColorUtils.getColor(0, 0, 255), -1};
                for (int i = 0; i < 3; ++i) {
                    if (i != 0) {
                        GL11.glBegin(5);
                    }
                    float h = e.height / (e.isChild() ? 1.8f : 1.0f) / 3.0f;
                    for (AkrienTr t3 : e.trail) {
                        step = e.trail.indexOf(t3) * -3 + 1000;
                        alpha = (float)e.trail.indexOf(t3) / (float)e.trail.size() * ((float)e.trail.indexOf(t3) / (float)e.trail.size()) * this.currentFloatValue("Alpha") * ((float)ColorUtils.getAlphaFromColor(color) / 255.0f);
                        RenderUtils.setupColor(colors[i], alpha);
                        GL11.glVertex3d(t3.posX, t3.posY + (double)(h * (float)i) + (double)h, t3.posZ);
                        GL11.glVertex3d(t3.posX, t3.posY + (double)(h * (float)i), t3.posZ);
                    }
                    GL11.glEnd();
                }
            } else {
                for (AkrienTr t2 : e.trail) {
                    step = e.trail.indexOf(t2) * -3 + 1000;
                    color = ClientColors.getColor1((int)((float)step * 2.25f));
                    alpha = (float)e.trail.indexOf(t2) / (float)e.trail.size() * ((float)e.trail.indexOf(t2) / (float)e.trail.size()) * this.currentFloatValue("Alpha") * ((float)ColorUtils.getAlphaFromColor(color) / 255.0f);
                    RenderUtils.setupColor(color, this.currentMode("Mode").equalsIgnoreCase("Default2") ? alpha : 0.0f);
                    float h = e.height / (e.isChild() ? 1.8f : 1.0f);
                    GL11.glVertex3d(t2.posX, t2.posY + (double)h, t2.posZ);
                    RenderUtils.setupColor(color, alpha);
                    GL11.glVertex3d(t2.posX, t2.posY, t2.posZ);
                }
                GL11.glEnd();
                if (this.currentMode("Mode").equalsIgnoreCase("Default2")) {
                    GL11.glBegin(5);
                    for (AkrienTr t2 : e.trail) {
                        step = e.trail.indexOf(t2) * -3 + 1000;
                        color = ClientColors.getColor1((int)((float)step * 2.25f));
                        alpha = (float)e.trail.indexOf(t2) / (float)e.trail.size() * ((float)e.trail.indexOf(t2) / (float)e.trail.size()) * this.currentFloatValue("Alpha") * ((float)ColorUtils.getAlphaFromColor(color) / 255.0f);
                        RenderUtils.setupColor(color, alpha);
                        float h = e.height / (e.isChild() ? 1.8f : 1.0f);
                        float ss = (float)e.trail.indexOf(t2) / (float)e.trail.size();
                        GL11.glVertex3d(t2.posX, t2.posY + (double)h, t2.posZ);
                        GL11.glVertex3d(t2.posX, t2.posY + (double)h - 0.07 * (double)ss, t2.posZ);
                    }
                    GL11.glEnd();
                    GL11.glBegin(5);
                    for (AkrienTr t2 : e.trail) {
                        step = e.trail.indexOf(t2) * -3 + 1000;
                        color = ClientColors.getColor1((int)((float)step * 2.25f));
                        alpha = (float)e.trail.indexOf(t2) / (float)e.trail.size() * ((float)e.trail.indexOf(t2) / (float)e.trail.size()) * this.currentFloatValue("Alpha") * ((float)ColorUtils.getAlphaFromColor(color) / 255.0f);
                        RenderUtils.setupColor(color, alpha);
                        float ss = (float)e.trail.indexOf(t2) / (float)e.trail.size();
                        GL11.glVertex3d(t2.posX, t2.posY, t2.posZ);
                        GL11.glVertex3d(t2.posX, t2.posY + 0.07 * (double)ss, t2.posZ);
                    }
                    GL11.glEnd();
                }
            }
            Trails.mc.entityRenderer.enableLightmap();
            GlStateManager.shadeModel(7424);
            GL11.glEnable(3008);
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.resetColor();
            GlStateManager.enableCull();
            GlStateManager.enableTexture2D();
            GlStateManager.depthMask(true);
            GlStateManager.enableAlpha();
            GlStateManager.resetColor();
            GlStateManager.popMatrix();
        }
        if (this.currentMode("Mode").equalsIgnoreCase("Default") || this.currentMode("Mode").equalsIgnoreCase("Default2")) {
            for (Entity entity : this.tra.keySet()) {
                EntityPlayer e = (EntityPlayer)entity;
                double posX = e.posX;
                double prevX = posX - e.prevPosX;
                double posY = e.posY;
                double prevY = posY - e.prevPosY;
                double posZ = e.posZ;
                double prevZ = posZ - e.prevPosZ;
                double bps = Math.sqrt(prevX * prevX + prevY * prevY + prevZ * prevZ);
                this.tra.get(entity).removeIf(t -> entity == null || t.age >= (double)(GET_MAX_SIZE * 2));
            }
        } else {
            this.tr.removeIf(t -> t.size >= (float)GET_MAX_SIZE);
            if (this.tr.size() > 0) {
                mc.getTextureManager().bindTexture(new ResourceLocation("vegaline/modules/trails/points/bloom.png"));
            }
            for (Trailses p : this.tr) {
                int color = -1;
                int step = (int)p.size * -2 + 1000;
                color = ColorUtils.getOverallColorFrom(ClientColors.getColor2(step), ClientColors.getColor1(step), MathUtils.clamp(p.size / (float)this.tr.size(), 0.0f, 1.0f));
                color = ColorUtils.swapAlpha(color, (int)this.currentFloatValue("Alpha"));
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.shadeModel(7425);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask(false);
                GL11.glDisable(3008);
                GlStateManager.disableCull();
                Trails.mc.entityRenderer.disableLightmap();
                GL11.glTranslated((double)p.x - RenderManager.renderPosX, (double)p.y - RenderManager.renderPosY, (double)p.z - RenderManager.renderPosZ);
                GlStateManager.rotate(-Trails.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(Trails.mc.getRenderManager().playerViewX, Trails.mc.gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f, 0.0f, 0.0f);
                float enfo = ((1.0f - p.size / (float)GET_MAX_SIZE) / 1.5f + 0.5f) / 2.0f;
                GL11.glScalef(enfo, enfo, enfo);
                int c = ColorUtils.swapAlpha(color, (float)ColorUtils.getAlphaFromColor(color) * ((1.0f - p.size / (float)GET_MAX_SIZE) * (this.currentFloatValue("Alpha") / 255.0f)));
                GL11.glBlendFunc(770, 32772);
                GlStateManager.enableTexture2D();
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBuffer();
                buffer.begin(6, DefaultVertexFormats.POSITION_TEX_COLOR);
                buffer.pos(-0.5, -0.5).tex(0.0, 0.0).color(c).endVertex();
                buffer.pos(0.5, -0.5).tex(1.0, 0.0).color(c).endVertex();
                buffer.pos(0.5, 0.5).tex(1.0, 1.0).color(c).endVertex();
                buffer.pos(-0.5, 0.5).tex(0.0, 1.0).color(c).endVertex();
                tessellator.draw();
                GL11.glBlendFunc(770, 771);
                Trails.mc.entityRenderer.enableLightmap();
                GlStateManager.shadeModel(7424);
                GL11.glEnable(3008);
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.resetColor();
                GlStateManager.enableCull();
                GlStateManager.enableTexture2D();
                GlStateManager.depthMask(true);
                GlStateManager.enableAlpha();
                GlStateManager.resetColor();
                GlStateManager.popMatrix();
                p.size += 1.0f;
            }
        }
    }

    public class Track {
        Vec3d pos;
        float yaw;
        float pitch;
        float prevLimbSwingAmount;
        float limbSwingAmount;
        float limbSwing;

        Track(Vec3d pos, float yaw, float pitch, float prevLimbSwingAmount, float limbSwingAmount, float limbSwing) {
            this.pos = pos;
            this.yaw = yaw;
            this.pitch = pitch;
            this.prevLimbSwingAmount = prevLimbSwingAmount;
            this.limbSwingAmount = limbSwingAmount;
            this.limbSwing = limbSwing;
        }
    }

    public class Trailses {
        float x;
        float y;
        float z;
        float size;

        public Trailses(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public class AkrienTr {
        public double age;
        double posX;
        double posY;
        double posZ;
        public final long spawnTime;

        public AkrienTr(double posX, double posY, double posZ) {
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            this.spawnTime = Minecraft.getSystemTime();
        }

        public void updateAge() {
            this.age += 1.0;
        }
    }
}

