/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumSkyBlock;
import org.lwjgl.input.Keyboard;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventLightingCheck;
import ru.govno.client.event.events.EventRenderChunk;
import ru.govno.client.event.events.EventRenderChunkContainer;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.GaussianBlur;
import ru.govno.client.utils.TPSDetect;

public class WorldRender
extends Module {
    public static WorldRender get;
    public Settings ClientPlayersSkins;
    public Settings SelfSkin;
    public Settings FastWorldLoad;
    public Settings RenderBarrier;
    public Settings ItemPhisics;
    public Settings ChunksDebuger;
    public Settings ChunkAnim;
    public Settings FullBright;
    public Settings BrightMode;
    public Settings BlockLightFix;
    public Settings WorldBloom;
    public Settings BloomPower;
    public Settings CustomParticles;
    public Settings ParticleSpeed;
    public Settings ParticleCount;
    public Settings WorldReTime;
    public Settings Time;
    public Settings TimeCustom;
    public Settings TimeSpinSpeed;
    public Settings SkyRecolor;
    public Settings SkyColorMode;
    public Settings SkyColorPick;
    public Settings SkyColorPick2;
    public Settings SkyFadeSpeed;
    public Settings SkyClientColBright;
    public Settings SkyBright;
    public Settings FogRedistance;
    public Settings FogDistanceCustom;
    public Settings ClearWeather;
    public Settings CustomCamDist;
    public Settings CameraRedistance;
    public Settings ClientCamera;
    public Settings AntiAliasing;
    public Settings AAMode;
    public Settings AltReverseCamera;
    public AnimationUtils gammaAnimation = new AnimationUtils(0.0f, 0.0f, 0.03f);
    private boolean has_use_alias;
    private boolean has_enabled;
    private String get_last_alias;
    private int prevThirdPersonView = 0;
    AnimationUtils orientAnim = new AnimationUtils(0.0f, 0.0f, 0.0333f);
    public AnimationUtils fovMultiplier = new AnimationUtils(1.0f, 1.0f, 0.08f);
    public boolean isItemPhisics = false;
    protected AnimationUtils spinnedTime = new AnimationUtils(0.0f, 0.0f, 0.04f);
    protected float current = 0.0f;
    boolean rend = true;
    public float oldGamma;
    private final HashMap<RenderChunk, AtomicLong> renderChunkMap = new HashMap();

    public WorldRender() {
        super("WorldRender", 0, Module.Category.RENDER);
        this.ClientPlayersSkins = new Settings("ClientPlayersSkins", false, (Module)this);
        this.settings.add(this.ClientPlayersSkins);
        this.SelfSkin = new Settings("SelfSkin", "Skin1", this, new String[]{"Skin1", "Skin2", "Skin3", "Skin4", "Skin5", "Skin6", "Skin7", "Skin8", "Skin9", "Skin10", "Skin11", "Skin12", "Skin13", "Skin14", "Skin15", "Skin16"}, () -> this.ClientPlayersSkins.bValue);
        this.settings.add(this.SelfSkin);
        this.FastWorldLoad = new Settings("FastWorldLoad", true, (Module)this);
        this.settings.add(this.FastWorldLoad);
        this.RenderBarrier = new Settings("RenderBarrier", true, (Module)this);
        this.settings.add(this.RenderBarrier);
        this.ItemPhisics = new Settings("ItemPhisics", true, (Module)this);
        this.settings.add(this.ItemPhisics);
        this.ChunksDebuger = new Settings("ChunksDebuger", true, (Module)this);
        this.settings.add(this.ChunksDebuger);
        this.ChunkAnim = new Settings("ChunkAnim", true, (Module)this);
        this.settings.add(this.ChunkAnim);
        this.FullBright = new Settings("FullBright", true, (Module)this);
        this.settings.add(this.FullBright);
        this.BrightMode = new Settings("BrightMode", "Vision", this, new String[]{"Vision", "Gamma"}, () -> this.FullBright.bValue);
        this.settings.add(this.BrightMode);
        this.BlockLightFix = new Settings("BlockLightFix", true, (Module)this);
        this.settings.add(this.BlockLightFix);
        this.WorldBloom = new Settings("WorldBloom", false, (Module)this);
        this.settings.add(this.WorldBloom);
        this.BloomPower = new Settings("BloomPower", 0.5f, 1.0f, 0.05f, this, () -> this.WorldBloom.bValue);
        this.settings.add(this.BloomPower);
        this.CustomParticles = new Settings("CustomParticles", false, (Module)this);
        this.settings.add(this.CustomParticles);
        this.ParticleSpeed = new Settings("ParticleSpeed", 0.2f, 1.5f, 0.05f, this, () -> this.CustomParticles.bValue);
        this.settings.add(this.ParticleSpeed);
        this.ParticleCount = new Settings("ParticleCount", 1.5f, 10.0f, 0.25f, this, () -> this.CustomParticles.bValue);
        this.settings.add(this.ParticleCount);
        this.WorldReTime = new Settings("WorldReTime", false, (Module)this);
        this.settings.add(this.WorldReTime);
        this.Time = new Settings("Time", "Night", this, new String[]{"Evening", "Night", "Morning", "Day", "SpinTime", "Custom", "RealWorldTime"}, () -> this.WorldReTime.bValue);
        this.settings.add(this.Time);
        this.TimeCustom = new Settings("TimeCustom", 14000.0f, 23500.0f, 0.0f, this, () -> this.WorldReTime.bValue && this.Time.currentMode.equalsIgnoreCase("Custom"));
        this.settings.add(this.TimeCustom);
        this.TimeSpinSpeed = new Settings("TimeSpinSpeed", 1.0f, 3.0f, 0.1f, this, () -> this.WorldReTime.bValue && this.Time.currentMode.equalsIgnoreCase("SpinTime"));
        this.settings.add(this.TimeSpinSpeed);
        this.SkyRecolor = new Settings("SkyRecolor", false, (Module)this);
        this.settings.add(this.SkyRecolor);
        this.SkyColorMode = new Settings("SkyColorMode", "Colored", this, new String[]{"Colored", "Fade", "Client", "ReBright"}, () -> this.SkyRecolor.bValue);
        this.settings.add(this.SkyColorMode);
        this.SkyColorPick = new Settings("SkyColorPick", ColorUtils.getColor(40, 40, 255, 140), (Module)this, () -> this.SkyRecolor.bValue && (this.SkyColorMode.currentMode.equalsIgnoreCase("Colored") || this.SkyColorMode.currentMode.equalsIgnoreCase("Fade")));
        this.settings.add(this.SkyColorPick);
        this.SkyColorPick2 = new Settings("SkyColorPick2", ColorUtils.getColor(40, 40, 255, 60), (Module)this, () -> this.SkyRecolor.bValue && this.SkyColorMode.currentMode.equalsIgnoreCase("Fade"));
        this.settings.add(this.SkyColorPick2);
        this.SkyFadeSpeed = new Settings("SkyFadeSpeed", 0.35f, 1.5f, 0.1f, this, () -> this.SkyRecolor.bValue && this.SkyColorMode.currentMode.equalsIgnoreCase("Fade"));
        this.settings.add(this.SkyFadeSpeed);
        this.SkyClientColBright = new Settings("SkyClientColBright", 0.6f, 1.0f, 0.05f, this, () -> this.SkyRecolor.bValue && this.SkyColorMode.currentMode.equalsIgnoreCase("Client"));
        this.settings.add(this.SkyClientColBright);
        this.SkyBright = new Settings("SkyBright", 0.4f, 1.0f, 0.0f, this, () -> this.SkyRecolor.bValue && this.SkyColorMode.currentMode.equalsIgnoreCase("ReBright"));
        this.settings.add(this.SkyBright);
        this.FogRedistance = new Settings("FogRedistance", false, (Module)this);
        this.settings.add(this.FogRedistance);
        this.FogDistanceCustom = new Settings("FogDistanceCustom", 40.0f, 120.0f, 15.0f, this, () -> this.FogRedistance.bValue);
        this.settings.add(this.FogDistanceCustom);
        this.ClearWeather = new Settings("ClearWeather", true, (Module)this);
        this.settings.add(this.ClearWeather);
        this.CustomCamDist = new Settings("CustomCamDist", false, (Module)this);
        this.settings.add(this.CustomCamDist);
        this.CameraRedistance = new Settings("CameraRedistance", 7.0f, 15.0f, 1.0f, this, () -> this.CustomCamDist.bValue);
        this.settings.add(this.CameraRedistance);
        this.ClientCamera = new Settings("ClientCamera", false, (Module)this);
        this.settings.add(this.ClientCamera);
        this.AntiAliasing = new Settings("AntiAliasing", false, (Module)this);
        this.settings.add(this.AntiAliasing);
        this.AAMode = new Settings("AAMode", "FXAA", this, new String[]{"FXAA", "TAA"}, () -> this.AntiAliasing.bValue);
        this.settings.add(this.AAMode);
        this.AltReverseCamera = new Settings("AltReverseCamera", false, (Module)this);
        this.settings.add(this.AltReverseCamera);
        get = this;
    }

    private int getClientSkinsCount() {
        return 16;
    }

    public ResourceLocation updatedResourceSkin(ResourceLocation prevResource, Entity entity) {
        if (get != null && this.actived && entity != null && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            if (WorldRender.get.ClientPlayersSkins.bValue) {
                int index = (player instanceof EntityPlayerSP || player == FreeCam.fakePlayer ? Integer.parseInt(this.SelfSkin.currentMode.replace("Skin", "")) : player.getEntityId() * 3) % this.getClientSkinsCount() + 1;
                prevResource = new ResourceLocation("vegaline/modules/worldrender/skins/default/skin" + index + ".png");
            }
        }
        return prevResource;
    }

    public float setupedGammaNightVision() {
        float gamma;
        this.gammaAnimation.to = !Panic.stop && get != null && this.FullBright.bValue && this.BrightMode.currentMode.equalsIgnoreCase("Vision") ? 1.0f : 0.0f;
        float f = gamma = !Panic.stop ? this.gammaAnimation.getAnim() : 0.0f;
        return (double)gamma < 0.02 ? 0.0f : ((double)gamma > 0.98 ? 1.0f : gamma);
    }

    public boolean isReverseCamera() {
        return !Panic.stop && get != null && this.actived && this.AltReverseCamera.bValue && Keyboard.isKeyDown(56) && WorldRender.mc.currentScreen == null;
    }

    private String getAliasName() {
        return "shaders/post/" + this.AAMode.currentMode.toLowerCase().replace("fxaa", "fxaa_of_4x") + ".json";
    }

    private ResourceLocation getAlias(boolean usement) {
        return usement ? new ResourceLocation(this.getAliasName()) : null;
    }

    private boolean aliasIsCurrentLoaded() {
        return this.aliasIsLoaded() && WorldRender.mc.entityRenderer.theShaderGroup.getShaderGroupName().endsWith(this.getAliasName());
    }

    private boolean aliasIsLoaded() {
        return WorldRender.mc.entityRenderer.theShaderGroup != null && (WorldRender.mc.entityRenderer.theShaderGroup.getShaderGroupName().contains("fxaa") || WorldRender.mc.entityRenderer.theShaderGroup.getShaderGroupName().contains("taa"));
    }

    private void controllFXAAUsement(boolean usement) {
        if (WorldRender.mc.gameSettings.ofFastRender || FreeCam.get.actived && !FreeCam.get.currentBooleanValue("NoDessaturate")) {
            usement = false;
        }
        ResourceLocation alias = this.getAlias(usement);
        String aliasName = this.getAliasName();
        if (this.has_use_alias != usement || this.get_last_alias != null && !this.get_last_alias.equalsIgnoreCase(aliasName) || this.prevThirdPersonView != WorldRender.mc.gameSettings.thirdPersonView || this.has_enabled != this.actived || Minecraft.player.ticksExisted == 1) {
            this.has_use_alias = usement;
            this.get_last_alias = aliasName;
            this.has_enabled = this.actived;
            this.prevThirdPersonView = WorldRender.mc.gameSettings.thirdPersonView;
            if (this.has_use_alias) {
                if (WorldRender.mc.entityRenderer.theShaderGroup != null && !this.aliasIsCurrentLoaded()) {
                    WorldRender.mc.entityRenderer.theShaderGroup = null;
                }
                if (!WorldRender.mc.entityRenderer.isShaderActive()) {
                    WorldRender.mc.entityRenderer.loadShader(alias);
                }
            } else if (this.aliasIsLoaded()) {
                WorldRender.mc.entityRenderer.theShaderGroup = null;
            }
        }
    }

    public float orientCustom(float partialTicks) {
        if (!Panic.stop && get != null && WorldRender.get.actived && this.ClientCamera.bValue) {
            this.orientAnim.to = MathUtils.clamp(Minecraft.player.rotationYaw - Minecraft.player.PreYaw, -90.0f, 90.0f) / 8.0f;
            return this.orientAnim.getAnim();
        }
        return 0.0f;
    }

    public float getClientFovMul(float prevVal, float partialTicks) {
        if (!Panic.stop && get.isActived() && WorldRender.get.ClientCamera.bValue) {
            float fovTo = 0.0f;
            if (Minecraft.player != null) {
                if (WorldRender.mc.gameSettings.thirdPersonView == 1 && Minecraft.player.isSneaking()) {
                    fovTo = 1.01f;
                } else {
                    if (WorldRender.mc.pointedEntity != null) {
                        fovTo += 0.01f;
                    }
                    if (Minecraft.player.isBowing() || Minecraft.player.isDrinking()) {
                        fovTo += (0.05f + MathUtils.clamp(((float)Minecraft.player.getItemInUseMaxCount() + partialTicks) / (Minecraft.player.isDrinking() ? 32.0f : 21.0f), 0.0f, 1.0f)) * (Minecraft.player.isDrinking() ? -0.015f : 0.5f);
                    }
                }
            }
            this.fovMultiplier.to = fovTo;
            if (MathUtils.getDifferenceOf(this.fovMultiplier.getAnim(), this.fovMultiplier.to) < (double)0.001f) {
                this.fovMultiplier.setAnim(this.fovMultiplier.to);
            }
            return prevVal + this.fovMultiplier.anim;
        }
        return prevVal;
    }

    public double cameraRedistance(double prevDistance) {
        return !Panic.stop && get != null && WorldRender.get.actived && this.CustomCamDist.bValue ? (double)this.CameraRedistance.fValue : prevDistance;
    }

    public float weatherReStrengh(float prevStrengh) {
        return !Panic.stop && get != null && WorldRender.get.actived && this.ClearWeather.bValue ? 0.0f : prevStrengh;
    }

    public float[] getSkyColorRGB(float prevRed, float prevGreen, float prevBlue) {
        WorldRender MOD = get;
        if (!Panic.stop && MOD != null && MOD.actived && this.SkyRecolor.bValue) {
            String mode;
            switch (mode = this.SkyColorMode.currentMode) {
                case "Colored": {
                    int pick1 = this.SkyColorPick.color;
                    float aLP = ColorUtils.getGLAlphaFromColor(pick1);
                    float[] rgbFloat = new float[]{ColorUtils.getGLRedFromColor(pick1) * aLP, ColorUtils.getGLGreenFromColor(pick1) * aLP, ColorUtils.getGLBlueFromColor(pick1) * aLP};
                    prevRed = rgbFloat[0];
                    prevGreen = rgbFloat[1];
                    prevBlue = rgbFloat[2];
                    break;
                }
                case "Fade": {
                    int pick1 = this.SkyColorPick.color;
                    int pick2 = this.SkyColorPick2.color;
                    float fadeSpeed = this.SkyFadeSpeed.fValue * 0.5f;
                    int pickFadedColor = ColorUtils.fadeColorIndexed(pick1, pick2, fadeSpeed, 0);
                    float aLP = ColorUtils.getGLAlphaFromColor(pickFadedColor);
                    float[] rgbFloat = new float[]{ColorUtils.getGLRedFromColor(pickFadedColor) * aLP, ColorUtils.getGLGreenFromColor(pickFadedColor) * aLP, ColorUtils.getGLBlueFromColor(pickFadedColor) * aLP};
                    prevRed = rgbFloat[0];
                    prevGreen = rgbFloat[1];
                    prevBlue = rgbFloat[2];
                    break;
                }
                case "Client": {
                    int col = ClientColors.getColor1(0, this.SkyClientColBright.fValue);
                    float aLP = ColorUtils.getGLAlphaFromColor(col);
                    float[] rgbFloat = new float[]{ColorUtils.getGLRedFromColor(col) * aLP, ColorUtils.getGLGreenFromColor(col) * aLP, ColorUtils.getGLBlueFromColor(col) * aLP};
                    prevRed = rgbFloat[0];
                    prevGreen = rgbFloat[1];
                    prevBlue = rgbFloat[2];
                    break;
                }
                case "ReBright": {
                    float bright = MathUtils.clamp(this.SkyBright.fValue, 0.0f, 1.0f);
                    prevRed *= bright;
                    prevGreen *= bright;
                    prevBlue *= bright;
                }
            }
        }
        return new float[]{prevRed, prevGreen, prevBlue};
    }

    public float getRedistanceFogValue(float prevMaxDstSq) {
        return !Panic.stop && WorldRender.get.actived && this.FogRedistance.bValue ? this.FogDistanceCustom.fValue : prevMaxDstSq;
    }

    private static float[] getSmoothRealTime() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        float smoothSec = (float)(date.getSeconds() - 1) + (float)(System.currentTimeMillis() % 1000L) / 1000.0f;
        float smoothMins = (float)(date.getMinutes() - 1) + smoothSec / 60.0f;
        float smoothHours = (float)(date.getHours() - 1) + smoothMins / 60.0f;
        return new float[]{smoothSec, smoothMins, smoothHours};
    }

    private float getWorldTimeByRealTime(float[] realTime) {
        float rlTime = realTime[2] - 13.0f;
        return (rlTime - 1.0f) * 1000.0f;
    }

    public long getWorldReTime(long oldTime) {
        boolean sataFlag;
        WorldRender mod = get;
        boolean bl = sataFlag = MathUtils.getDifferenceOf(this.spinnedTime.anim, oldTime) > 1.0;
        if (mod != null && mod.actived && this.WorldReTime.bValue) {
            String mode = this.Time.currentMode;
            if (mode != null) {
                switch (mode) {
                    case "Evening": {
                        this.current = 13000.0f;
                        break;
                    }
                    case "Night": {
                        this.current = 15000.0f;
                        break;
                    }
                    case "Morning": {
                        this.current = 23500.0f;
                        break;
                    }
                    case "Day": {
                        this.current = 6000.0f;
                        break;
                    }
                    case "SpinTime": {
                        this.current = (this.current + this.TimeSpinSpeed.fValue * 4.0f) % 24000.0f;
                        break;
                    }
                    case "Custom": {
                        this.current = this.TimeCustom.fValue;
                        break;
                    }
                    case "RealWorldTime": {
                        this.current = this.getWorldTimeByRealTime(WorldRender.getSmoothRealTime());
                    }
                }
                if (this.spinnedTime.getAnim() == 0.0f || MathUtils.getDifferenceOf(this.spinnedTime.getAnim(), this.current) > 36000.0) {
                    this.spinnedTime.setAnim(this.current * 0.85f);
                }
                if (MathUtils.getDifferenceOf(this.spinnedTime.getAnim(), this.current) < (double)(16.0f * TPSDetect.getConpensationTPS(true))) {
                    this.spinnedTime.setAnim(this.current);
                }
                this.spinnedTime.to = this.current;
                sataFlag = true;
            } else {
                this.spinnedTime.to = oldTime % 24000L;
            }
        } else {
            this.spinnedTime.to = oldTime % 24000L;
        }
        return (sataFlag ? (long)this.spinnedTime.getAnim() : oldTime) % 24000L;
    }

    public int particleReCount(int prevCount) {
        float count = 1.0f;
        if (get != null) {
            WorldRender mod = get;
            if (mod.actived && this.CustomParticles.bValue) {
                count *= this.ParticleCount.fValue;
            }
        }
        return (int)((float)prevCount * count);
    }

    public float particleReSpeed(float prevParticleSpeed) {
        float speed = 1.0f;
        if (get != null) {
            WorldRender mod = get;
            if (mod.actived && this.CustomParticles.bValue) {
                speed *= this.ParticleSpeed.fValue;
            }
        }
        return prevParticleSpeed * speed;
    }

    public boolean isRenderBloom() {
        if (get != null && WorldRender.get.actived && this.WorldBloom.bValue) {
            return this.BloomPower.fValue > 0.0f && this.BloomPower.fValue <= 1.0f;
        }
        return false;
    }

    public void drawWorldBloom() {
        GaussianBlur.renderBlur(0.8f - this.BloomPower.fValue / 2.0f);
    }

    @EventTarget
    public void onLightingCheck(EventLightingCheck event) {
        if (this.BlockLightFix.bValue && (event.getEnumSkyBlock() == EnumSkyBlock.SKY || event.getEnumSkyBlock() == EnumSkyBlock.BLOCK && Minecraft.player != null && Minecraft.player.getDistanceToBlockPos(event.getPos()) > 64.0 || event.getEnumSkyBlock() == EnumSkyBlock.SKY && event.getPos().getY() >= 253)) {
            event.cancel();
        }
    }

    @Override
    public void onUpdate() {
        this.controllFXAAUsement(this.AntiAliasing.bValue);
        this.isItemPhisics = this.ItemPhisics.bValue;
        if (this.ChunksDebuger.bValue && Minecraft.player.ticksExisted < 7 && Minecraft.player.ticksExisted > 5) {
            WorldRender.mc.renderGlobal.loadRenderers();
        }
        if (Minecraft.player.getActivePotionEffect(Potion.getPotionById(16)) != null && Minecraft.player.getActivePotionEffect(Potion.getPotionById(16)).getDuration() >= 16345) {
            Minecraft.player.removeActivePotionEffect(Potion.getPotionById(16));
        }
        if (this.rend != this.RenderBarrier.bValue) {
            WorldRender.mc.renderGlobal.loadRenderers();
            this.rend = this.RenderBarrier.bValue;
        }
        if (this.FullBright.bValue && this.BrightMode.currentMode.equalsIgnoreCase("Gamma")) {
            if (WorldRender.mc.gameSettings.gammaSetting != 1000.0f) {
                this.oldGamma = WorldRender.mc.gameSettings.gammaSetting;
            }
            WorldRender.mc.gameSettings.gammaSetting = 1000.0f;
        } else if (this.oldGamma != -1.0f) {
            WorldRender.mc.gameSettings.gammaSetting = this.oldGamma;
            this.oldGamma = -1.0f;
        }
        this.updateSmoothingCamera();
    }

    @Override
    public void onToggled(boolean actived) {
        if (this.RenderBarrier.bValue) {
            WorldRender.mc.renderGlobal.loadRenderers();
        }
        if (this.FullBright.bValue) {
            if (actived) {
                if (this.BrightMode.currentMode.equalsIgnoreCase("Gamma")) {
                    this.oldGamma = WorldRender.mc.gameSettings.gammaSetting;
                }
            } else {
                this.controllFXAAUsement(false);
                this.isItemPhisics = false;
                if (this.BrightMode.currentMode.equalsIgnoreCase("Gamma") && this.oldGamma != -1.0f) {
                    WorldRender.mc.gameSettings.gammaSetting = this.oldGamma;
                    this.oldGamma = -1.0f;
                }
            }
        }
        this.updateSmoothingCamera();
        super.onToggled(actived);
    }

    private double easeOutCubic(double t) {
        return (t -= 1.0) * t * t + 1.0;
    }

    @EventTarget
    private void onRenderChunk(EventRenderChunk event) {
        if (this.ChunkAnim.bValue && Minecraft.player != null && !this.renderChunkMap.containsKey(event.getRenderChunk())) {
            this.renderChunkMap.put(event.getRenderChunk(), new AtomicLong(-1L));
        }
    }

    @EventTarget
    private void onChunkRender(EventRenderChunkContainer event) {
        if (this.ChunkAnim.bValue && this.renderChunkMap.containsKey(event.getRenderChunk())) {
            long timeDifference;
            AtomicLong timeAlive = this.renderChunkMap.get(event.getRenderChunk());
            long timeClone = timeAlive.get();
            if (timeClone == -1L) {
                timeClone = System.currentTimeMillis();
                timeAlive.set(timeClone);
            }
            if ((timeDifference = System.currentTimeMillis() - timeClone) <= 350L) {
                double chunkY = event.getRenderChunk().getPosition().getY();
                double offsetY = chunkY * this.easeOutCubic((float)timeDifference / 350.0f);
                GlStateManager.translate(0.0, -chunkY + offsetY, 0.0);
            }
        }
    }

    private void updateSmoothingCamera() {
        if (!this.ClientCamera.bValue || !this.isActived()) {
            return;
        }
        Minecraft.player.rotationYaw -= (Minecraft.player.rotationYaw - Minecraft.player.PreYaw) / 16.0f;
        Minecraft.player.rotationPitch -= (Minecraft.player.rotationPitch - Minecraft.player.PrePitch) / 16.0f;
    }
}

