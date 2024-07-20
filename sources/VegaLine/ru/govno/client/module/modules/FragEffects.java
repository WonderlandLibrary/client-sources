/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import optifine.Config;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventSendPacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.BowAimbot;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.module.modules.Timer;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Combat.RotationUtil;
import ru.govno.client.utils.CrystalField;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.MusicHelper;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class FragEffects
extends Module {
    private final Settings NoMobsDetect;
    private final Settings ZoomFov;
    private final Settings SoundFX;
    private final Settings Desaturation;
    private final Settings TimeDilation;
    private final Settings ScreenOverlay;
    private final Settings FlickToDeceased;
    private final Settings DeathStink;
    private final AnimationUtils strikeAnimation = new AnimationUtils(0.0f, 0.0f, 0.03f);
    public static FragEffects get;
    private boolean timerForce2;
    private final List<EntityDeathMemory> DEATH_MEMORIES_LIST = new ArrayList<EntityDeathMemory>();
    private boolean hasLoadedDesaturate;
    private final ResourceLocation LIGHT_VIGNETTE_TEX = new ResourceLocation("vegaline/modules/frageffects/vignette.png");
    private boolean texHasLoaded;
    private final List<FragParticle> fragParticles = new ArrayList<FragParticle>();

    public FragEffects() {
        super("FragEffects", 0, Module.Category.RENDER);
        this.NoMobsDetect = new Settings("NoMobsDetect", true, (Module)this);
        this.settings.add(this.NoMobsDetect);
        this.ZoomFov = new Settings("ZoomFov", true, (Module)this);
        this.settings.add(this.ZoomFov);
        this.SoundFX = new Settings("SoundFX", true, (Module)this);
        this.settings.add(this.SoundFX);
        this.Desaturation = new Settings("Desaturation", true, (Module)this);
        this.settings.add(this.Desaturation);
        this.TimeDilation = new Settings("TimeDilation", true, (Module)this);
        this.settings.add(this.TimeDilation);
        this.ScreenOverlay = new Settings("ScreenOverlay", true, (Module)this);
        this.settings.add(this.ScreenOverlay);
        this.FlickToDeceased = new Settings("FlickToDeceased", true, (Module)this);
        this.settings.add(this.FlickToDeceased);
        this.DeathStink = new Settings("DeathStink", true, (Module)this);
        this.settings.add(this.DeathStink);
        get = this;
    }

    private float updateStrikeAnimation() {
        float value = 0.0f;
        if (this.strikeAnimation.to == 1.0f) {
            float f;
            value = this.strikeAnimation.getAnim();
            if (f > 0.995f) {
                this.strikeAnimation.setAnim(1.0f);
                this.strikeAnimation.to = 0.0f;
                value = 1.0f;
            }
        } else if (this.strikeAnimation.to == 0.0f) {
            float f;
            value = this.strikeAnimation.getAnim();
            if (f < 0.01f && value != 0.0f) {
                this.strikeAnimation.setAnim(0.0f);
            }
        }
        return value;
    }

    private void triggerStrikeAniation() {
        this.strikeAnimation.to = 1.0f;
        this.strikeAnimation.setAnim(0.5f);
    }

    private void playStrikeSound() {
        MusicHelper.playSound("strikesf-2.wav");
    }

    private void doFlickToDeceased(Entity deceased) {
        float[] rot = RotationUtil.getRots(deceased, false, false);
        if (RotationUtil.getAngleDifference(Minecraft.player.rotationYaw, rot[0]) > 5.0f || RotationUtil.getAngleDifference(Minecraft.player.rotationPitch, rot[1]) > 10.0f) {
            Minecraft.player.rotationYaw = rot[0];
            Minecraft.player.rotationPitch = rot[1];
        }
    }

    private long getMaxTimeMemory() {
        return 1000L;
    }

    private Runnable getMemoryTrigger(Entity deceased) {
        return () -> {
            if (this.ZoomFov.bValue || this.Desaturation.bValue || this.ScreenOverlay.bValue) {
                this.triggerStrikeAniation();
            }
            if (this.SoundFX.bValue) {
                this.playStrikeSound();
            }
            if (deceased != null && this.FlickToDeceased.bValue) {
                this.doFlickToDeceased(deceased);
            }
            if (this.TimeDilation.bValue) {
                Timer.forceTimer(0.075f);
                this.timerForce2 = true;
            }
            if (this.DeathStink.bValue && deceased instanceof EntityLivingBase) {
                EntityLivingBase deceasedBase = (EntityLivingBase)deceased;
                this.spawnAllParticles(deceasedBase, 3600, 1500.0f + 1500.0f * (float)Math.random());
            }
        };
    }

    private void controllingAddingMemoryToEntity(EntityLivingBase baseTo) {
        if (baseTo == null || baseTo instanceof EntityPlayerSP || baseTo.ticksExisted < 2 || baseTo.getHealth() == 0.0f) {
            return;
        }
        EntityDeathMemory searchedMemory = null;
        for (EntityDeathMemory memory : this.DEATH_MEMORIES_LIST) {
            if (!memory.isValidMemory() || memory.base.getEntityId() != baseTo.getEntityId()) continue;
            searchedMemory = memory;
        }
        long maxTimeMemory = this.getMaxTimeMemory();
        if (searchedMemory != null) {
            searchedMemory.resetMemory(baseTo, maxTimeMemory);
            return;
        }
        if (this.NoMobsDetect.bValue && !(baseTo instanceof EntityOtherPlayerMP)) {
            return;
        }
        this.DEATH_MEMORIES_LIST.add(new EntityDeathMemory(baseTo, maxTimeMemory, this.getMemoryTrigger(baseTo)));
    }

    private void removeAutoMemories() {
        this.DEATH_MEMORIES_LIST.removeIf(EntityDeathMemory::isNotValidMemory);
    }

    @EventTarget
    public void onSendPackets(EventSendPacket event) {
        Entity checkedEntity;
        CPacketUseEntity useEntityPacket;
        Packet packet = event.getPacket();
        if (packet instanceof CPacketUseEntity && (useEntityPacket = (CPacketUseEntity)packet).getAction() == CPacketUseEntity.Action.ATTACK && (checkedEntity = useEntityPacket.getEntityFromWorld(FragEffects.mc.world)) != null && checkedEntity instanceof EntityLivingBase) {
            EntityLivingBase base = (EntityLivingBase)checkedEntity;
            this.controllingAddingMemoryToEntity(base);
        }
    }

    private ResourceLocation getShaderEffectLoc() {
        return new ResourceLocation("shaders/post/desaturate.json");
    }

    private void updateDesaturate(boolean setTrue) {
        if (Config.isShaders() || FragEffects.mc.gameSettings.ofFastRender) {
            setTrue = false;
        }
        if (setTrue) {
            if (!this.hasLoadedDesaturate) {
                FragEffects.mc.entityRenderer.loadShader(this.getShaderEffectLoc());
                this.hasLoadedDesaturate = true;
            }
        } else if (this.hasLoadedDesaturate) {
            if (FragEffects.mc.entityRenderer.isShaderActive()) {
                FragEffects.mc.entityRenderer.theShaderGroup = null;
            }
            this.hasLoadedDesaturate = false;
        }
    }

    private void drawLightVignette(ScaledResolution sr, float alphaPC, boolean loadMode) {
        int color = ColorUtils.getColor(80, 255, 80, 100.0f * alphaPC);
        float sideSetX = (float)sr.getScaledWidth() / 2.0f * (1.0f - alphaPC);
        float sideSetY = (float)sr.getScaledHeight() / 2.0f * (1.0f - alphaPC);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        GL11.glDisable(3008);
        mc.getTextureManager().bindTexture(this.LIGHT_VIGNETTE_TEX);
        RenderUtils.glColor(loadMode ? 0 : color);
        GL11.glBlendFunc(770, 32772);
        Gui.drawModalRectWithCustomSizedTexture(-sideSetX, -sideSetY, 0.0f, 0.0f, (float)sr.getScaledWidth() + sideSetX * 2.0f, (float)sr.getScaledHeight() + sideSetY * 2.0f, (float)sr.getScaledWidth() + sideSetX * 2.0f, (float)sr.getScaledHeight() + sideSetY * 2.0f);
        GL11.glBlendFunc(770, 771);
        GlStateManager.resetColor();
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
    }

    @Override
    public void onUpdate() {
        EntityLivingBase pointedBase;
        Entity entity;
        if (!this.settings.stream().anyMatch(set -> set.bValue)) {
            this.toggle(false);
            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7l" + this.getName() + "\u00a7r\u00a77]: \u041d\u0438 \u043e\u0434\u0438\u043d \u044d\u0444\u0444\u0435\u043a\u0442 \u043d\u0435 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0435\u0442\u0441\u044f.", false);
            return;
        }
        if (this.timerForce2) {
            Timer.forceTimer(0.2f);
            this.timerForce2 = false;
        }
        ArrayList<EntityLivingBase> toUpdateMemoryEntities = new ArrayList<EntityLivingBase>();
        if (HitAura.TARGET_ROTS != null) {
            toUpdateMemoryEntities.add(HitAura.TARGET_ROTS);
        }
        if (BowAimbot.target != null) {
            toUpdateMemoryEntities.add(BowAimbot.target);
        }
        if (FragEffects.mc.pointedEntity != null && (entity = FragEffects.mc.pointedEntity) instanceof EntityLivingBase && (pointedBase = (EntityLivingBase)entity) != null) {
            toUpdateMemoryEntities.add(pointedBase);
        }
        if (!CrystalField.listIsEmptyOrNull(CrystalField.getTargets())) {
            for (EntityLivingBase crystalFieldTarget : CrystalField.getTargets()) {
                if (crystalFieldTarget == null) continue;
                toUpdateMemoryEntities.add(crystalFieldTarget);
            }
        }
        toUpdateMemoryEntities.stream().filter(base -> base.getHealth() != 0.0f).forEach(base -> {
            if (base != null) {
                this.controllingAddingMemoryToEntity((EntityLivingBase)base);
            }
        });
        this.DEATH_MEMORIES_LIST.forEach(EntityDeathMemory::updateMemoryTrigger);
        this.removeAutoMemories();
    }

    private float getGlobalEffectAnimation(boolean setup) {
        float value;
        if (setup) {
            this.stateAnim.to = this.actived ? 1.0f : 0.0f;
            this.strikeAnimation.speed = 0.04f;
        }
        float f = value = setup ? this.stateAnim.getAnim() * this.updateStrikeAnimation() : this.stateAnim.anim * this.strikeAnimation.anim;
        if (value < 0.005f) {
            value = 0.0f;
        }
        if (value > 0.995f) {
            value = 1.0f;
        }
        return value;
    }

    @Override
    public void onToggled(boolean actived) {
        if (actived) {
            this.timerForce2 = false;
        } else {
            this.DEATH_MEMORIES_LIST.clear();
            this.timerForce2 = false;
        }
        this.updateDesaturate(false);
        this.strikeAnimation.setAnim(0.0f);
        this.strikeAnimation.to = 0.0f;
        super.onToggled(actived);
    }

    @Override
    public void alwaysRender2D(ScaledResolution sr) {
        float alphaPC;
        if (!this.texHasLoaded) {
            this.drawLightVignette(sr, 1.0f, true);
            this.texHasLoaded = true;
        }
        if ((alphaPC = this.getGlobalEffectAnimation(true)) == 0.0f) {
            return;
        }
        this.updateDesaturate(alphaPC >= 0.3f && this.Desaturation.bValue);
        if (this.ScreenOverlay.bValue) {
            this.drawLightVignette(sr, alphaPC, false);
        }
    }

    @Override
    public void alwaysRender3D() {
        float alphaPC = this.stateAnim.getAnim();
        if ((double)alphaPC < 0.003) {
            return;
        }
        this.particlesRemoveAuto();
        this.drawAllParticles(alphaPC);
    }

    public float getStrikeEffectFovModifyPC() {
        return this.ZoomFov.bValue ? this.getGlobalEffectAnimation(false) : 0.0f;
    }

    private void spawnAllParticles(EntityLivingBase base, int count, float maxTime) {
        float startDst;
        if (base == null) {
            return;
        }
        float yExt = startDst = 1.4f;
        float yMaxRand = yExt / 10.0f;
        Vec3d spawnPos = base.getPositionVector().addVector(0.0, base.getEyeHeight() / 2.0f, 0.0);
        for (int index = 0; index < count; ++index) {
            float pc01 = (float)index / (float)count;
            int yExtMul = 1 - index % 2 * 2;
            float yRandom = (float)((double)(-yMaxRand / 2.0f) + Math.random() * (double)yMaxRand);
            float dstRandom = startDst / 2.0f + (float)((double)(-startDst / 4.0f) + Math.random() * (double)startDst / 2.0);
            float yExtRandom = (float)((double)((float)yExtMul * yExt) * Math.random());
            this.fragParticles.add(new FragParticle(spawnPos.addVector(0.0, yRandom, 0.0), pc01 * 360.0f, dstRandom, startDst, 180.0f * (float)Math.random(), yExtRandom, maxTime));
        }
    }

    private void particlesRemoveAuto() {
        if (!this.fragParticles.isEmpty()) {
            this.fragParticles.removeIf(FragParticle::toRemove);
        }
    }

    private void drawAllParticles(float alphaPC) {
        if (alphaPC == 0.0f || this.fragParticles.isEmpty()) {
            return;
        }
        RenderUtils.setup3dForBlockPos(() -> {
            GL11.glEnable(2929);
            GL11.glDisable(3008);
            GL11.glDepthMask(false);
            GL11.glPointSize(2.5f);
            GL11.glBegin(0);
            for (FragParticle fragParticle : this.fragParticles) {
                float aPC = MathUtils.clamp(fragParticle.get010PC() * 2.0f * fragParticle.getAlphaPC(), 0.0f, 1.0f) * alphaPC;
                if (aPC == 0.0f) {
                    return;
                }
                int color = ColorUtils.getOverallColorFrom(ColorUtils.swapAlpha(-1, 255.0f * aPC), ColorUtils.getColor(0, 111, 255, 255.0f * aPC), 0.5f + fragParticle.randomFloat / 2.0f);
                Vec3d pos = fragParticle.getPos();
                RenderUtils.glColor(color);
                GL11.glVertex3d(pos.xCoord, pos.yCoord, pos.zCoord);
            }
            GL11.glEnd();
            GL11.glPointSize(1.0f);
            GL11.glEnable(3008);
            GL11.glDepthMask(true);
        }, true);
    }

    private class EntityDeathMemory {
        private final TimerHelper startTime = new TimerHelper();
        private long maxTimeMemory;
        private EntityLivingBase base;
        private boolean hasReseted;
        private final Runnable onTrigger;

        public EntityDeathMemory(EntityLivingBase base, long maxTimeMemory, Runnable onTrigger) {
            this.base = base;
            this.startTime.reset();
            this.maxTimeMemory = maxTimeMemory;
            this.onTrigger = onTrigger;
        }

        public void resetMemory(EntityLivingBase base, long maxTimeMemory) {
            if (this.base != null && this.base.getHealth() == 0.0f) {
                return;
            }
            this.base = base;
            this.maxTimeMemory = maxTimeMemory;
            this.startTime.reset();
        }

        public boolean isValidMemory() {
            return this.base != null && this.maxTimeMemory != 0L && !this.startTime.hasReached(this.maxTimeMemory);
        }

        public boolean isNotValidMemory() {
            return !this.isValidMemory();
        }

        public void updateMemoryTrigger() {
            if (this.isValidMemory() && this.base.getHealth() == 0.0f && !this.hasReseted) {
                this.onTrigger.run();
                this.hasReseted = true;
                this.maxTimeMemory = 300L;
            }
        }
    }

    private class FragParticle {
        public Vec3d spawnPos;
        public long startTime = System.currentTimeMillis();
        public float startYaw;
        public float toYaw;
        public float startDst;
        public float toDst;
        public float toY;
        public float maxTime;
        public float randomFloat = (float)Math.random();

        public float getTimePC() {
            return MathUtils.clamp((float)(System.currentTimeMillis() - this.startTime) / this.maxTime, 0.0f, 1.0f);
        }

        public float get010PC() {
            float timePC = this.getTimePC();
            return ((double)timePC > 0.5 ? 1.0f - timePC : timePC) * 2.0f;
        }

        public float getAlphaPC() {
            return 1.0f - this.getTimePC();
        }

        public boolean toRemove() {
            return this.getTimePC() == 1.0f;
        }

        public FragParticle(Vec3d spawnPos, float yaw, float startDst, float maxDst, float additionYaw, float additionY, float maxTime) {
            this.startDst = startDst;
            this.toDst = maxDst;
            this.startYaw = yaw;
            this.toYaw = this.startYaw + additionYaw;
            this.toY = additionY;
            this.maxTime = maxTime;
            this.spawnPos = spawnPos;
        }

        private Vec3d posAdds(Vec3d vec3d, float yaw, float dst) {
            double yawRadian = Math.toRadians(yaw);
            return vec3d.addVector(Math.sin(yawRadian) * (double)dst, 0.0, Math.cos(yawRadian) * (double)dst);
        }

        public Vec3d getPos() {
            float timePC = this.getTimePC();
            float pc010 = this.get010PC();
            float yaw = MathUtils.lerp(this.startYaw, this.toYaw, timePC);
            float dst = MathUtils.lerp(this.startDst, this.toDst, timePC);
            float yExt = this.toY * MathUtils.lerp(pc010, timePC, 1.0f - timePC);
            return this.posAdds(this.spawnPos, yaw, dst).addVector(0.0, yExt, 0.0);
        }
    }
}

