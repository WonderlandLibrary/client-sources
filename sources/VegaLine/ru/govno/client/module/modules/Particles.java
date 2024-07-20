/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.Event3D;
import ru.govno.client.event.events.EventSendPacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class Particles
extends Module {
    List<Part> parts = new CopyOnWriteArrayList<Part>();
    Settings Mode;
    Settings Particle;
    Settings RandomizePos;
    Settings RandomStrength;
    private static final ResourceLocation STAR_TEXTURE = new ResourceLocation("vegaline/modules/particles/particle1.png");
    private final Tessellator tessellator = Tessellator.getInstance();
    private final BufferBuilder buffer = this.tessellator.getBuffer();

    public Particles() {
        super("Particles", 0, Module.Category.RENDER);
        this.Mode = new Settings("Mode", "Client", (Module)this, new String[]{"Client", "Minecraft"});
        this.settings.add(this.Mode);
        this.Particle = new Settings("Particle", "Crit", this, new String[]{"Explode", "Largeexplode", "HugeExplosion", "FireworksSpark", "Bubble", "Splash", "Wake", "Suspended", "DepthSuspend", "Crit", "MagicCrit", "Smoke", "LargeSmoke", "Spell", "InstantSpell", "MobSpell", "MobSpellAmbient", "WitchMagic", "DripWater", "DripLava", "AngryVillager", "HappyVillager", "TownAura", "Note", "Portal", "EnchantmentTable", "Flame", "Lava", "Footstep", "Cloud", "Reddust", "SnowBallPoof", "SnowShovel", "Slime", "Heart", "Barrier", "IconCrack", "BlockCrack", "BlockDust", "Droplet", "Take", "MobAppearance", "DragonBreath", "EndRod", "DamageIndicator", "SweepAttack", "FallingDust", "Totem", "Spit"}, () -> this.Mode.currentMode.equalsIgnoreCase("Minecraft"));
        this.settings.add(this.Particle);
        this.RandomizePos = new Settings("RandomizePos", true, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("Minecraft"));
        this.settings.add(this.RandomizePos);
        this.RandomStrength = new Settings("RandomStrength", 0.9f, 1.0f, 0.05f, this, () -> this.Mode.currentMode.equalsIgnoreCase("Minecraft"));
        this.settings.add(this.RandomStrength);
    }

    @EventTarget
    public void onPacketSend(EventSendPacket event) {
        Packet packet = event.getPacket();
        if (packet instanceof CPacketUseEntity) {
            CPacketUseEntity packet2 = (CPacketUseEntity)packet;
            Entity entityIn = null;
            if (packet2.getAction() == CPacketUseEntity.Action.ATTACK && packet2 instanceof CPacketUseEntity) {
                if (Particles.mc.world == null) {
                    return;
                }
                entityIn = packet2.getEntityFromWorld(Particles.mc.world);
            }
            if (!Minecraft.player.isEntityAlive()) {
                return;
            }
            if (this.Mode.currentMode.equalsIgnoreCase("Client")) {
                if (entityIn instanceof EntityLivingBase) {
                    int maxTime = 3000;
                    if (entityIn == null && !entityIn.isEntityAlive() || entityIn instanceof EntityPlayerSP) {
                        return;
                    }
                    float w = entityIn.width / 2.0f;
                    float h = entityIn.height;
                    Vec3d vec = entityIn.getPositionVector().addVector((double)(-w) + (double)(w * 2.0f) * Math.random(), (double)h * Math.random(), (double)(-w) + (double)(w * 2.0f) * Math.random());
                    this.parts.add(new Part(vec, maxTime));
                    this.parts.add(new Part(vec, maxTime));
                    this.parts.add(new Part(vec, maxTime));
                    this.parts.add(new Part(vec, maxTime));
                    this.parts.add(new Part(vec, maxTime));
                    this.parts.add(new Part(vec, maxTime));
                    this.parts.add(new Part(vec, maxTime));
                }
            } else if (event.getPacket() instanceof CPacketUseEntity && packet2.getAction() == CPacketUseEntity.Action.ATTACK && packet2 instanceof CPacketUseEntity) {
                for (int count = 0; count < 60; ++count) {
                    this.addParticlesFor(entityIn, 1.0f);
                }
            }
        }
    }

    int getById(String mode) {
        if (mode.equalsIgnoreCase("Explode")) {
            return 0;
        }
        if (mode.equalsIgnoreCase("Largeexplode")) {
            return 1;
        }
        if (mode.equalsIgnoreCase("HugeExplosion")) {
            return 2;
        }
        if (mode.equalsIgnoreCase("FireworksSpark")) {
            return 3;
        }
        if (mode.equalsIgnoreCase("Bubble")) {
            return 4;
        }
        if (mode.equalsIgnoreCase("Splash")) {
            return 5;
        }
        if (mode.equalsIgnoreCase("Wake")) {
            return 6;
        }
        if (mode.equalsIgnoreCase("Suspended")) {
            return 7;
        }
        if (mode.equalsIgnoreCase("DepthSuspend")) {
            return 8;
        }
        if (mode.equalsIgnoreCase("Crit")) {
            return 9;
        }
        if (mode.equalsIgnoreCase("MagicCrit")) {
            return 10;
        }
        if (mode.equalsIgnoreCase("Smoke")) {
            return 11;
        }
        if (mode.equalsIgnoreCase("LargeSmoke")) {
            return 12;
        }
        if (mode.equalsIgnoreCase("Spell")) {
            return 13;
        }
        if (mode.equalsIgnoreCase("InstantSpell")) {
            return 14;
        }
        if (mode.equalsIgnoreCase("MobSpell")) {
            return 15;
        }
        if (mode.equalsIgnoreCase("MobSpellAmbient")) {
            return 16;
        }
        if (mode.equalsIgnoreCase("WitchMagic")) {
            return 17;
        }
        if (mode.equalsIgnoreCase("DripWater")) {
            return 18;
        }
        if (mode.equalsIgnoreCase("DripLava")) {
            return 19;
        }
        if (mode.equalsIgnoreCase("AngryVillager")) {
            return 20;
        }
        if (mode.equalsIgnoreCase("HappyVillager")) {
            return 21;
        }
        if (mode.equalsIgnoreCase("TownAura")) {
            return 22;
        }
        if (mode.equalsIgnoreCase("Note")) {
            return 23;
        }
        if (mode.equalsIgnoreCase("Portal")) {
            return 24;
        }
        if (mode.equalsIgnoreCase("EnchantmentTable")) {
            return 25;
        }
        if (mode.equalsIgnoreCase("Flame")) {
            return 26;
        }
        if (mode.equalsIgnoreCase("Lava")) {
            return 27;
        }
        if (mode.equalsIgnoreCase("Footstep")) {
            return 28;
        }
        if (mode.equalsIgnoreCase("Cloud")) {
            return 29;
        }
        if (mode.equalsIgnoreCase("Reddust")) {
            return 30;
        }
        if (mode.equalsIgnoreCase("SnowBallPoof")) {
            return 31;
        }
        if (mode.equalsIgnoreCase("SnowShovel")) {
            return 32;
        }
        if (mode.equalsIgnoreCase("Slime")) {
            return 33;
        }
        if (mode.equalsIgnoreCase("Heart")) {
            return 34;
        }
        if (mode.equalsIgnoreCase("Barrier")) {
            return 35;
        }
        if (mode.equalsIgnoreCase("IconCrack")) {
            return 36;
        }
        if (mode.equalsIgnoreCase("BlockCrack")) {
            return 37;
        }
        if (mode.equalsIgnoreCase("BlockDust")) {
            return 38;
        }
        if (mode.equalsIgnoreCase("Droplet")) {
            return 39;
        }
        if (mode.equalsIgnoreCase("Take")) {
            return 40;
        }
        if (mode.equalsIgnoreCase("MobAppearance")) {
            return 41;
        }
        if (mode.equalsIgnoreCase("DragonBreath")) {
            return 42;
        }
        if (mode.equalsIgnoreCase("EndRod")) {
            return 43;
        }
        if (mode.equalsIgnoreCase("DamageIndicator")) {
            return 44;
        }
        if (mode.equalsIgnoreCase("SweepAttack")) {
            return 45;
        }
        if (mode.equalsIgnoreCase("FallingDust")) {
            return 46;
        }
        if (mode.equalsIgnoreCase("Totem")) {
            return 47;
        }
        if (mode.equalsIgnoreCase("Spit")) {
            return 48;
        }
        return -1;
    }

    void addParticlesFor(Entity entityIn, float amount) {
        if (entityIn == null) {
            return;
        }
        String mode = this.Particle.currentMode;
        double posX = entityIn.posX;
        double posY = entityIn.posY;
        double posZ = entityIn.posZ;
        double next = 0.0;
        double next2 = (float)MathUtils.getRandomInRange(-1, 1) * this.RandomStrength.fValue;
        double next3 = (float)(-MathUtils.getRandomInRange(-1, 1)) * this.RandomStrength.fValue;
        double next4 = (float)MathUtils.getRandomInRange(-1, 1) * -this.RandomStrength.fValue;
        if (this.RandomizePos.bValue) {
            next = 2.0f * this.RandomStrength.fValue;
            double w = entityIn.width * this.RandomStrength.fValue;
            double h = entityIn.getEyeHeight() * this.RandomStrength.fValue;
            posX += MathUtils.getRandomInRange(-w / 2.0, w / 2.0) * (next * 4.0);
            posY += MathUtils.getRandomInRange(-h / 2.0, h / 2.0) * (next * 2.0);
            posZ += MathUtils.getRandomInRange(-w / 2.0, w / 2.0) * (next * 4.0);
        }
        EnumParticleTypes particle2 = null;
        if (this.getById(mode) != -1) {
            particle2 = EnumParticleTypes.getParticleFromId(this.getById(mode));
        }
        if (particle2 != null) {
            int oldSetting = Particles.mc.gameSettings.particleSetting;
            boolean oldSetting2 = Particles.mc.gameSettings.ofFireworkParticles;
            boolean oldSetting3 = Particles.mc.gameSettings.ofPortalParticles;
            boolean oldSetting4 = Particles.mc.gameSettings.ofPotionParticles;
            boolean oldSetting5 = Particles.mc.gameSettings.ofVoidParticles;
            boolean oldSetting6 = Particles.mc.gameSettings.ofWaterParticles;
            Particles.mc.gameSettings.particleSetting = 1;
            Particles.mc.gameSettings.ofFireworkParticles = true;
            Particles.mc.gameSettings.ofPortalParticles = true;
            Particles.mc.gameSettings.ofPotionParticles = true;
            Particles.mc.gameSettings.ofVoidParticles = true;
            Particles.mc.gameSettings.ofWaterParticles = true;
            Particles.mc.world.spawnParticle(particle2, posX, posY, posZ, next2, next3, next4, 1);
            Particles.mc.gameSettings.particleSetting = oldSetting;
            Particles.mc.gameSettings.ofFireworkParticles = oldSetting2;
            Particles.mc.gameSettings.ofPortalParticles = oldSetting3;
            Particles.mc.gameSettings.ofPotionParticles = oldSetting4;
            Particles.mc.gameSettings.ofVoidParticles = oldSetting5;
            Particles.mc.gameSettings.ofWaterParticles = oldSetting6;
        }
    }

    @EventTarget
    public void onRender3D(Event3D event) {
        if (this.Mode.currentMode.equalsIgnoreCase("Client") && !this.parts.isEmpty()) {
            this.setupRenderParts(() -> {
                float i = 0.0f;
                if (this.parts.isEmpty()) {
                    return;
                }
                mc.getTextureManager().bindTexture(STAR_TEXTURE);
                for (Part part : this.parts) {
                    if (part == null || part.toRemove) continue;
                    float gradPC = i / (float)this.parts.size();
                    int col = ColorUtils.getOverallColorFrom(ClientColors.getColor1((int)(i * 5.0f)), ClientColors.getColor2((int)(i * 5.0f)), MathUtils.clamp(gradPC, 0.0f, 1.0f));
                    float scale = MathHelper.clamp((float)(Particles.mc.gameSettings.thirdPersonView == 0 ? 3 : 2) - (float)(Minecraft.player.getDistance(part.posX.getAnim(), part.posY.getAnim(), part.posZ.getAnim()) / 10.0), 1.0f, 5.0f);
                    if (Particles.mc.gameSettings.ofKeyBindZoom.isKeyDown()) {
                        scale *= 2.0f + (float)(Minecraft.player.getDistance(part.posX.getAnim(), part.posY.getAnim(), part.posZ.getAnim()) / 5.0);
                    }
                    part.vertexColored(col);
                    i += 1.0f;
                }
            }, true);
        }
    }

    boolean setupRenderParts(Runnable render, boolean bloom) {
        RenderManager mngr = mc.getRenderManager();
        Vec3d renderPos = new Vec3d(RenderManager.viewerPosX, RenderManager.viewerPosY, RenderManager.viewerPosZ);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, bloom ? GlStateManager.DestFactor.ONE : GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.translate(-renderPos.xCoord, -renderPos.yCoord, -renderPos.zCoord);
        GlStateManager.shadeModel(7425);
        Particles.mc.entityRenderer.disableLightmap();
        render.run();
        Particles.mc.entityRenderer.enableLightmap();
        GlStateManager.translate(renderPos.xCoord, renderPos.yCoord, renderPos.zCoord);
        GlStateManager.shadeModel(7424);
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GlStateManager.depthMask(true);
        GlStateManager.resetColor();
        GlStateManager.popMatrix();
        return true;
    }

    @Override
    public void onUpdate() {
        if (this.Mode.currentMode.equalsIgnoreCase("Client") && !this.parts.isEmpty()) {
            for (int i = 0; i < this.parts.size(); ++i) {
                Part part = this.parts.get(i);
                if (part == null) continue;
                if (part.toRemove) {
                    this.parts.remove(i);
                    continue;
                }
                part.updatePart();
            }
        } else if (!this.parts.isEmpty()) {
            this.parts.clear();
        }
    }

    class Part {
        AnimationUtils alphaPC = new AnimationUtils(0.1f, 1.0f, 0.035f);
        boolean toRemove = false;
        float[] randomXYZM = new float[]{(float)MathUtils.getRandomInRange(0.3, -0.3), (float)MathUtils.getRandomInRange(0.12, -0.09), (float)MathUtils.getRandomInRange(0.3, -0.3)};
        AnimationUtils posX;
        AnimationUtils posY;
        AnimationUtils posZ;
        float motionX = this.randomXYZM[0];
        float motionY = this.randomXYZM[1];
        float motionZ = this.randomXYZM[2];
        TimerHelper time = new TimerHelper();
        long maxTime = 10000L;

        Part(Vec3d vec, long maxTime) {
            this.posX = new AnimationUtils((float)vec.xCoord, (float)vec.xCoord, 0.08f);
            this.posY = new AnimationUtils((float)vec.yCoord, (float)vec.yCoord, 0.08f);
            this.posZ = new AnimationUtils((float)vec.zCoord, (float)vec.zCoord, 0.08f);
            this.maxTime = maxTime;
            this.time.reset();
        }

        long getTime() {
            return this.time.getTime();
        }

        float getSpeed() {
            return (float)Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        }

        void gravityAndMove() {
            boolean gravityY;
            this.posX.to = this.posX.getAnim() + this.motionX;
            this.posY.to = this.posY.getAnim() + this.motionY;
            this.posZ.to = this.posZ.getAnim() + this.motionZ;
            float x = this.posX.getAnim();
            float y = this.posY.getAnim();
            float z = this.posZ.getAnim();
            float motionX = this.motionX;
            float motionY = this.motionY;
            float motionZ = this.motionZ;
            BlockPos xPrePos = new BlockPos(x + motionX * 2.0f, (double)(y - motionY) + 0.1, z);
            BlockPos yPrePos = new BlockPos(x, y + motionY * 2.0f, z);
            BlockPos zPrePos = new BlockPos(x, (double)(y - motionY) + 0.1, z + motionZ * 2.0f);
            IBlockState xState = Module.mc.world.getBlockState(xPrePos);
            IBlockState yState = Module.mc.world.getBlockState(yPrePos);
            IBlockState zState = Module.mc.world.getBlockState(zPrePos);
            boolean collideX = xState.getCollisionBoundingBox(Module.mc.world, xPrePos) != null;
            boolean collideY = yState.getCollisionBoundingBox(Module.mc.world, yPrePos) != null;
            boolean collideZ = zState.getCollisionBoundingBox(Module.mc.world, zPrePos) != null;
            boolean isInLiquid = xState.getBlock() instanceof BlockLiquid || zState.getBlock() instanceof BlockLiquid || yState.getBlock() instanceof BlockLiquid;
            boolean bl = gravityY = motionY != 0.0f;
            if (gravityY) {
                this.motionY -= 0.01f;
            }
            if (isInLiquid) {
                this.motionX *= 0.975f;
                this.motionY *= 0.75f;
                this.motionY -= 0.025f;
                this.motionZ *= 0.975f;
            }
            if (collideY) {
                this.motionY = -this.motionY * 0.9f;
            }
            if (collideX) {
                this.motionX *= -1.0f;
            }
            if (collideZ) {
                this.motionZ *= -1.0f;
            }
        }

        void updatePart() {
            if (this.getTime() > this.maxTime) {
                this.alphaPC.to = 0.0f;
            }
            if ((double)this.alphaPC.getAnim() < 0.005) {
                this.toRemove = true;
            }
            if (this.toRemove) {
                return;
            }
            this.gravityAndMove();
        }

        void vertexColored(int color) {
            float alphaPC = ColorUtils.getGLAlphaFromColor(color) * this.alphaPC.getAnim();
            color = ColorUtils.swapAlpha(color, alphaPC * 255.0f);
            GlStateManager.pushMatrix();
            double xf = Minecraft.player.lastTickPosX + (Minecraft.player.posX - Minecraft.player.lastTickPosX) * (double)Module.mc.getRenderPartialTicks() - (double)this.posX.getAnim();
            double yf = Minecraft.player.lastTickPosY + (Minecraft.player.posY - Minecraft.player.lastTickPosY) * (double)Module.mc.getRenderPartialTicks() - (double)this.posY.getAnim();
            double zf = Minecraft.player.lastTickPosZ + (Minecraft.player.posZ - Minecraft.player.lastTickPosZ) * (double)Module.mc.getRenderPartialTicks() - (double)this.posZ.getAnim();
            double distance = Math.min(Math.max(1.25 * (Math.sqrt(xf * xf + yf * yf + zf * zf) * 0.15), 0.5), 10.0) * 0.1;
            GL11.glTranslated(this.posX.getAnim(), (double)this.posY.getAnim() + 0.25, this.posZ.getAnim());
            float fixed = Module.mc.gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f;
            GL11.glRotatef(-Module.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(Module.mc.getRenderManager().playerViewX, fixed, 0.0f, 0.0f);
            GL11.glScaled(-0.2, -0.2, -0.2);
            Particles.this.buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            Particles.this.buffer.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).color(color).endVertex();
            Particles.this.buffer.pos(0.0, 1.0, 0.0).tex(0.0, 1.0).color(color).endVertex();
            Particles.this.buffer.pos(1.0, 1.0, 0.0).tex(1.0, 1.0).color(color).endVertex();
            Particles.this.buffer.pos(1.0, 0.0, 0.0).tex(1.0, 0.0).color(color).endVertex();
            RenderUtils.customRotatedObject2D(0.0f, 0.0f, 1.0f, 1.0f, (float)this.getTime() / (float)this.maxTime * 3600.0f * (float)(this.randomXYZM[0] > 0.0f ? 1 : -1));
            Particles.this.tessellator.draw();
            GlStateManager.popMatrix();
        }
    }
}

