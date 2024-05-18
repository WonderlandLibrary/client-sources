package net.minecraft.client.renderer;

import java.nio.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import net.minecraft.block.material.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.shader.*;
import net.minecraft.entity.boss.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.passive.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.client.particle.*;
import java.lang.reflect.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.world.biome.*;
import org.apache.logging.log4j.*;
import optfine.*;
import org.lwjgl.util.glu.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.settings.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.client.gui.*;
import net.minecraft.server.integrated.*;
import java.io.*;
import com.google.gson.*;
import com.google.common.base.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.client.entity.*;
import org.lwjgl.input.*;
import java.util.concurrent.*;
import net.minecraft.crash.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;

public class EntityRenderer implements IResourceManagerReloadListener
{
    private float thirdPersonDistanceTemp;
    public static int anaglyphField;
    private double cameraPitch;
    private boolean initialized;
    public boolean fogStandard;
    private final int[] lightmapColors;
    private FloatBuffer fogColorBuffer;
    private int debugViewDirection;
    private float farPlaneDistance;
    private static final ResourceLocation locationSnowPng;
    private int lastServerTicks;
    private float fogColor1;
    private float[] rainYCoords;
    private Entity pointedEntity;
    private float avgServerTimeDiff;
    private static final String[] I;
    private static final Logger logger;
    private float fogColor2;
    private float[] rainXCoords;
    private static final String __OBFID;
    private boolean renderHand;
    private MouseFilter mouseFilterYAxis;
    private float smoothCamYaw;
    public static final int shaderCount;
    private boolean cloudFog;
    private Minecraft mc;
    private long renderEndNanoTime;
    private float torchFlickerDX;
    private final MapItemRenderer theMapItemRenderer;
    public ItemRenderer itemRenderer;
    private int frameCount;
    private boolean showDebugInfo;
    public float fogColorGreen;
    public float fogColorRed;
    private float avgServerTickDiff;
    private double cameraZoom;
    private static final ResourceLocation locationRainPng;
    private boolean drawBlockOutline;
    private World updatedWorld;
    private final ResourceLocation locationLightMap;
    private int shaderIndex;
    private float smoothCamPitch;
    private int rainSoundCounter;
    private MouseFilter mouseFilterXAxis;
    private static final ResourceLocation[] shaderResourceLocations;
    private float thirdPersonDistance;
    private int serverWaitTime;
    private float bossColorModifierPrev;
    private boolean useShader;
    private float fovModifierHand;
    private float torchFlickerX;
    private boolean debugView;
    private float fovModifierHandPrev;
    private long prevFrameTime;
    public float fogColorBlue;
    private int rendererUpdateCount;
    private float smoothCamFilterY;
    private boolean lightmapUpdateNeeded;
    private float smoothCamPartialTicks;
    private long lastServerTime;
    private double cameraYaw;
    private final DynamicTexture lightmapTexture;
    private float smoothCamFilterX;
    private Random random;
    private float clipDistance;
    private final IResourceManager resourceManager;
    private float bossColorModifier;
    private ShaderGroup theShaderGroup;
    public static boolean anaglyphEnable;
    private long lastErrorCheckTimeMs;
    private int serverWaitTimeCurrent;
    
    private void updateFogColor(final float n) {
        final WorldClient theWorld = this.mc.theWorld;
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        final float n2 = 1.0f - (float)Math.pow(0.25f + 0.75f * this.mc.gameSettings.renderDistanceChunks / 32.0f, 0.25);
        final Vec3 worldSkyColor = CustomColorizer.getWorldSkyColor(theWorld.getSkyColor(this.mc.getRenderViewEntity(), n), theWorld, this.mc.getRenderViewEntity(), n);
        final float n3 = (float)worldSkyColor.xCoord;
        final float n4 = (float)worldSkyColor.yCoord;
        final float n5 = (float)worldSkyColor.zCoord;
        final Vec3 worldFogColor = CustomColorizer.getWorldFogColor(theWorld.getFogColor(n), theWorld, this.mc.getRenderViewEntity(), n);
        this.fogColorRed = (float)worldFogColor.xCoord;
        this.fogColorGreen = (float)worldFogColor.yCoord;
        this.fogColorBlue = (float)worldFogColor.zCoord;
        if (this.mc.gameSettings.renderDistanceChunks >= (0x5B ^ 0x5F)) {
            final double n6 = -1.0;
            Vec3 vec3;
            if (MathHelper.sin(theWorld.getCelestialAngleRadians(n)) > 0.0f) {
                vec3 = new Vec3(n6, 0.0, 0.0);
                "".length();
                if (2 == -1) {
                    throw null;
                }
            }
            else {
                vec3 = new Vec3(1.0, 0.0, 0.0);
            }
            float n7 = (float)renderViewEntity.getLook(n).dotProduct(vec3);
            if (n7 < 0.0f) {
                n7 = 0.0f;
            }
            if (n7 > 0.0f) {
                final float[] calcSunriseSunsetColors = theWorld.provider.calcSunriseSunsetColors(theWorld.getCelestialAngle(n), n);
                if (calcSunriseSunsetColors != null) {
                    final float n8 = n7 * calcSunriseSunsetColors["   ".length()];
                    this.fogColorRed = this.fogColorRed * (1.0f - n8) + calcSunriseSunsetColors["".length()] * n8;
                    this.fogColorGreen = this.fogColorGreen * (1.0f - n8) + calcSunriseSunsetColors[" ".length()] * n8;
                    this.fogColorBlue = this.fogColorBlue * (1.0f - n8) + calcSunriseSunsetColors["  ".length()] * n8;
                }
            }
        }
        this.fogColorRed += (n3 - this.fogColorRed) * n2;
        this.fogColorGreen += (n4 - this.fogColorGreen) * n2;
        this.fogColorBlue += (n5 - this.fogColorBlue) * n2;
        final float rainStrength = theWorld.getRainStrength(n);
        if (rainStrength > 0.0f) {
            final float n9 = 1.0f - rainStrength * 0.5f;
            final float n10 = 1.0f - rainStrength * 0.4f;
            this.fogColorRed *= n9;
            this.fogColorGreen *= n9;
            this.fogColorBlue *= n10;
        }
        final float thunderStrength = theWorld.getThunderStrength(n);
        if (thunderStrength > 0.0f) {
            final float n11 = 1.0f - thunderStrength * 0.5f;
            this.fogColorRed *= n11;
            this.fogColorGreen *= n11;
            this.fogColorBlue *= n11;
        }
        final Block blockAtEntityViewpoint = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, renderViewEntity, n);
        if (this.cloudFog) {
            final Vec3 cloudColour = theWorld.getCloudColour(n);
            this.fogColorRed = (float)cloudColour.xCoord;
            this.fogColorGreen = (float)cloudColour.yCoord;
            this.fogColorBlue = (float)cloudColour.zCoord;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (blockAtEntityViewpoint.getMaterial() == Material.water) {
            float n12 = EnchantmentHelper.getRespiration(renderViewEntity) * 0.2f;
            if (renderViewEntity instanceof EntityLivingBase && ((EntityLivingBase)renderViewEntity).isPotionActive(Potion.waterBreathing)) {
                n12 = n12 * 0.3f + 0.6f;
            }
            this.fogColorRed = 0.02f + n12;
            this.fogColorGreen = 0.02f + n12;
            this.fogColorBlue = 0.2f + n12;
            final Vec3 underwaterColor = CustomColorizer.getUnderwaterColor(this.mc.theWorld, this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY + 1.0, this.mc.getRenderViewEntity().posZ);
            if (underwaterColor != null) {
                this.fogColorRed = (float)underwaterColor.xCoord;
                this.fogColorGreen = (float)underwaterColor.yCoord;
                this.fogColorBlue = (float)underwaterColor.zCoord;
                "".length();
                if (2 == -1) {
                    throw null;
                }
            }
        }
        else if (blockAtEntityViewpoint.getMaterial() == Material.lava) {
            this.fogColorRed = 0.6f;
            this.fogColorGreen = 0.1f;
            this.fogColorBlue = 0.0f;
        }
        final float n13 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * n;
        this.fogColorRed *= n13;
        this.fogColorGreen *= n13;
        this.fogColorBlue *= n13;
        double n14 = (renderViewEntity.lastTickPosY + (renderViewEntity.posY - renderViewEntity.lastTickPosY) * n) * theWorld.provider.getVoidFogYFactor();
        if (renderViewEntity instanceof EntityLivingBase && ((EntityLivingBase)renderViewEntity).isPotionActive(Potion.blindness)) {
            final int duration = ((EntityLivingBase)renderViewEntity).getActivePotionEffect(Potion.blindness).getDuration();
            if (duration < (0x40 ^ 0x54)) {
                n14 *= 1.0f - duration / 20.0f;
                "".length();
                if (4 < 4) {
                    throw null;
                }
            }
            else {
                n14 = 0.0;
            }
        }
        if (n14 < 1.0) {
            if (n14 < 0.0) {
                n14 = 0.0;
            }
            final double n15 = n14 * n14;
            this.fogColorRed *= (float)n15;
            this.fogColorGreen *= (float)n15;
            this.fogColorBlue *= (float)n15;
        }
        if (this.bossColorModifier > 0.0f) {
            final float n16 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * n;
            this.fogColorRed = this.fogColorRed * (1.0f - n16) + this.fogColorRed * 0.7f * n16;
            this.fogColorGreen = this.fogColorGreen * (1.0f - n16) + this.fogColorGreen * 0.6f * n16;
            this.fogColorBlue = this.fogColorBlue * (1.0f - n16) + this.fogColorBlue * 0.6f * n16;
        }
        if (renderViewEntity instanceof EntityLivingBase && ((EntityLivingBase)renderViewEntity).isPotionActive(Potion.nightVision)) {
            final float nightVisionBrightness = this.getNightVisionBrightness((EntityLivingBase)renderViewEntity, n);
            float n17 = 1.0f / this.fogColorRed;
            if (n17 > 1.0f / this.fogColorGreen) {
                n17 = 1.0f / this.fogColorGreen;
            }
            if (n17 > 1.0f / this.fogColorBlue) {
                n17 = 1.0f / this.fogColorBlue;
            }
            this.fogColorRed = this.fogColorRed * (1.0f - nightVisionBrightness) + this.fogColorRed * n17 * nightVisionBrightness;
            this.fogColorGreen = this.fogColorGreen * (1.0f - nightVisionBrightness) + this.fogColorGreen * n17 * nightVisionBrightness;
            this.fogColorBlue = this.fogColorBlue * (1.0f - nightVisionBrightness) + this.fogColorBlue * n17 * nightVisionBrightness;
        }
        if (this.mc.gameSettings.anaglyph) {
            final float fogColorRed = (this.fogColorRed * 30.0f + this.fogColorGreen * 59.0f + this.fogColorBlue * 11.0f) / 100.0f;
            final float fogColorGreen = (this.fogColorRed * 30.0f + this.fogColorGreen * 70.0f) / 100.0f;
            final float fogColorBlue = (this.fogColorRed * 30.0f + this.fogColorBlue * 70.0f) / 100.0f;
            this.fogColorRed = fogColorRed;
            this.fogColorGreen = fogColorGreen;
            this.fogColorBlue = fogColorBlue;
        }
        if (Reflector.EntityViewRenderEvent_FogColors_Constructor.exists()) {
            final ReflectorConstructor entityViewRenderEvent_FogColors_Constructor = Reflector.EntityViewRenderEvent_FogColors_Constructor;
            final Object[] array = new Object[0xB9 ^ 0xBE];
            array["".length()] = this;
            array[" ".length()] = renderViewEntity;
            array["  ".length()] = blockAtEntityViewpoint;
            array["   ".length()] = n;
            array[0x79 ^ 0x7D] = this.fogColorRed;
            array[0xB9 ^ 0xBC] = this.fogColorGreen;
            array[0x21 ^ 0x27] = this.fogColorBlue;
            final Object instance = Reflector.newInstance(entityViewRenderEvent_FogColors_Constructor, array);
            Reflector.postForgeBusEvent(instance);
            this.fogColorRed = Reflector.getFieldValueFloat(instance, Reflector.EntityViewRenderEvent_FogColors_red, this.fogColorRed);
            this.fogColorGreen = Reflector.getFieldValueFloat(instance, Reflector.EntityViewRenderEvent_FogColors_green, this.fogColorGreen);
            this.fogColorBlue = Reflector.getFieldValueFloat(instance, Reflector.EntityViewRenderEvent_FogColors_blue, this.fogColorBlue);
        }
        GlStateManager.clearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0f);
    }
    
    private void updateLightmap(final float n) {
        if (this.lightmapUpdateNeeded) {
            this.mc.mcProfiler.startSection(EntityRenderer.I[0xBD ^ 0x9F]);
            final WorldClient theWorld = this.mc.theWorld;
            if (theWorld != null) {
                if (CustomColorizer.updateLightmap(theWorld, this.torchFlickerX, this.lightmapColors, this.mc.thePlayer.isPotionActive(Potion.nightVision))) {
                    this.lightmapTexture.updateDynamicTexture();
                    this.lightmapUpdateNeeded = ("".length() != 0);
                    this.mc.mcProfiler.endSection();
                    return;
                }
                final float sunBrightness = theWorld.getSunBrightness(1.0f);
                final float n2 = sunBrightness * 0.95f + 0.05f;
                int i = "".length();
                "".length();
                if (3 < 3) {
                    throw null;
                }
                while (i < 204 + 251 - 318 + 119) {
                    float n3 = theWorld.provider.getLightBrightnessTable()[i / (0x7F ^ 0x6F)] * n2;
                    final float n4 = theWorld.provider.getLightBrightnessTable()[i % (0x47 ^ 0x57)] * (this.torchFlickerX * 0.1f + 1.5f);
                    if (theWorld.getLastLightningBolt() > 0) {
                        n3 = theWorld.provider.getLightBrightnessTable()[i / (0xAA ^ 0xBA)];
                    }
                    final float n5 = n3 * (sunBrightness * 0.65f + 0.35f);
                    final float n6 = n3 * (sunBrightness * 0.65f + 0.35f);
                    final float n7 = n4 * ((n4 * 0.6f + 0.4f) * 0.6f + 0.4f);
                    final float n8 = n4 * (n4 * n4 * 0.6f + 0.4f);
                    final float n9 = n5 + n4;
                    final float n10 = n6 + n7;
                    final float n11 = n3 + n8;
                    float n12 = n9 * 0.96f + 0.03f;
                    float n13 = n10 * 0.96f + 0.03f;
                    float n14 = n11 * 0.96f + 0.03f;
                    if (this.bossColorModifier > 0.0f) {
                        final float n15 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * n;
                        n12 = n12 * (1.0f - n15) + n12 * 0.7f * n15;
                        n13 = n13 * (1.0f - n15) + n13 * 0.6f * n15;
                        n14 = n14 * (1.0f - n15) + n14 * 0.6f * n15;
                    }
                    if (theWorld.provider.getDimensionId() == " ".length()) {
                        n12 = 0.22f + n4 * 0.75f;
                        n13 = 0.28f + n7 * 0.75f;
                        n14 = 0.25f + n8 * 0.75f;
                    }
                    if (this.mc.thePlayer.isPotionActive(Potion.nightVision)) {
                        final float nightVisionBrightness = this.getNightVisionBrightness(this.mc.thePlayer, n);
                        float n16 = 1.0f / n12;
                        if (n16 > 1.0f / n13) {
                            n16 = 1.0f / n13;
                        }
                        if (n16 > 1.0f / n14) {
                            n16 = 1.0f / n14;
                        }
                        n12 = n12 * (1.0f - nightVisionBrightness) + n12 * n16 * nightVisionBrightness;
                        n13 = n13 * (1.0f - nightVisionBrightness) + n13 * n16 * nightVisionBrightness;
                        n14 = n14 * (1.0f - nightVisionBrightness) + n14 * n16 * nightVisionBrightness;
                    }
                    if (n12 > 1.0f) {
                        n12 = 1.0f;
                    }
                    if (n13 > 1.0f) {
                        n13 = 1.0f;
                    }
                    if (n14 > 1.0f) {
                        n14 = 1.0f;
                    }
                    final float gammaSetting = this.mc.gameSettings.gammaSetting;
                    final float n17 = 1.0f - n12;
                    final float n18 = 1.0f - n13;
                    final float n19 = 1.0f - n14;
                    final float n20 = 1.0f - n17 * n17 * n17 * n17;
                    final float n21 = 1.0f - n18 * n18 * n18 * n18;
                    final float n22 = 1.0f - n19 * n19 * n19 * n19;
                    final float n23 = n12 * (1.0f - gammaSetting) + n20 * gammaSetting;
                    final float n24 = n13 * (1.0f - gammaSetting) + n21 * gammaSetting;
                    final float n25 = n14 * (1.0f - gammaSetting) + n22 * gammaSetting;
                    float n26 = n23 * 0.96f + 0.03f;
                    float n27 = n24 * 0.96f + 0.03f;
                    float n28 = n25 * 0.96f + 0.03f;
                    if (n26 > 1.0f) {
                        n26 = 1.0f;
                    }
                    if (n27 > 1.0f) {
                        n27 = 1.0f;
                    }
                    if (n28 > 1.0f) {
                        n28 = 1.0f;
                    }
                    if (n26 < 0.0f) {
                        n26 = 0.0f;
                    }
                    if (n27 < 0.0f) {
                        n27 = 0.0f;
                    }
                    if (n28 < 0.0f) {
                        n28 = 0.0f;
                    }
                    this.lightmapColors[i] = (103 + 50 + 84 + 18 << (0x40 ^ 0x58) | (int)(n26 * 255.0f) << (0xB ^ 0x1B) | (int)(n27 * 255.0f) << (0x92 ^ 0x9A) | (int)(n28 * 255.0f));
                    ++i;
                }
                this.lightmapTexture.updateDynamicTexture();
                this.lightmapUpdateNeeded = ("".length() != 0);
                this.mc.mcProfiler.endSection();
            }
        }
    }
    
    public void updateRenderer() {
        if (OpenGlHelper.shadersSupported && ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
            ShaderLinkHelper.setNewStaticShaderLinkHelper();
        }
        this.updateFovModifierHand();
        this.updateTorchFlicker();
        this.fogColor2 = this.fogColor1;
        this.thirdPersonDistanceTemp = this.thirdPersonDistance;
        if (this.mc.gameSettings.smoothCamera) {
            final float n = this.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            final float n2 = n * n * n * 8.0f;
            this.smoothCamFilterX = this.mouseFilterXAxis.smooth(this.smoothCamYaw, 0.05f * n2);
            this.smoothCamFilterY = this.mouseFilterYAxis.smooth(this.smoothCamPitch, 0.05f * n2);
            this.smoothCamPartialTicks = 0.0f;
            this.smoothCamYaw = 0.0f;
            this.smoothCamPitch = 0.0f;
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else {
            this.smoothCamFilterX = 0.0f;
            this.smoothCamFilterY = 0.0f;
            this.mouseFilterXAxis.reset();
            this.mouseFilterYAxis.reset();
        }
        if (this.mc.getRenderViewEntity() == null) {
            this.mc.setRenderViewEntity(this.mc.thePlayer);
        }
        final float lightBrightness = this.mc.theWorld.getLightBrightness(new BlockPos(this.mc.getRenderViewEntity()));
        final float n3 = this.mc.gameSettings.renderDistanceChunks / 32.0f;
        this.fogColor1 += (lightBrightness * (1.0f - n3) + n3 - this.fogColor1) * 0.1f;
        this.rendererUpdateCount += " ".length();
        this.itemRenderer.updateEquippedItem();
        this.addRainParticles();
        this.bossColorModifierPrev = this.bossColorModifier;
        if (BossStatus.hasColorModifier) {
            this.bossColorModifier += 0.05f;
            if (this.bossColorModifier > 1.0f) {
                this.bossColorModifier = 1.0f;
            }
            BossStatus.hasColorModifier = ("".length() != 0);
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else if (this.bossColorModifier > 0.0f) {
            this.bossColorModifier -= 0.0125f;
        }
    }
    
    private void orientCamera(final float n) {
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        float eyeHeight = renderViewEntity.getEyeHeight();
        final double n2 = renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * n;
        final double n3 = renderViewEntity.prevPosY + (renderViewEntity.posY - renderViewEntity.prevPosY) * n + eyeHeight;
        final double n4 = renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * n;
        if (renderViewEntity instanceof EntityLivingBase && ((EntityLivingBase)renderViewEntity).isPlayerSleeping()) {
            ++eyeHeight;
            GlStateManager.translate(0.0f, 0.3f, 0.0f);
            if (!this.mc.gameSettings.debugCamEnable) {
                final BlockPos blockPos = new BlockPos(renderViewEntity);
                final IBlockState blockState = this.mc.theWorld.getBlockState(blockPos);
                final Block block = blockState.getBlock();
                if (Reflector.ForgeHooksClient_orientBedCamera.exists()) {
                    final ReflectorMethod forgeHooksClient_orientBedCamera = Reflector.ForgeHooksClient_orientBedCamera;
                    final Object[] array = new Object[0x21 ^ 0x25];
                    array["".length()] = this.mc.theWorld;
                    array[" ".length()] = blockPos;
                    array["  ".length()] = blockState;
                    array["   ".length()] = renderViewEntity;
                    Reflector.callVoid(forgeHooksClient_orientBedCamera, array);
                    "".length();
                    if (2 < 1) {
                        throw null;
                    }
                }
                else if (block == Blocks.bed) {
                    GlStateManager.rotate(blockState.getValue((IProperty<EnumFacing>)BlockBed.FACING).getHorizontalIndex() * (0xC6 ^ 0x9C), 0.0f, 1.0f, 0.0f);
                }
                GlStateManager.rotate(renderViewEntity.prevRotationYaw + (renderViewEntity.rotationYaw - renderViewEntity.prevRotationYaw) * n + 180.0f, 0.0f, -1.0f, 0.0f);
                GlStateManager.rotate(renderViewEntity.prevRotationPitch + (renderViewEntity.rotationPitch - renderViewEntity.prevRotationPitch) * n, -1.0f, 0.0f, 0.0f);
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
        }
        else if (this.mc.gameSettings.thirdPersonView > 0) {
            double n5 = this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * n;
            if (this.mc.gameSettings.debugCamEnable) {
                GlStateManager.translate(0.0f, 0.0f, (float)(-n5));
                "".length();
                if (3 < 3) {
                    throw null;
                }
            }
            else {
                final float rotationYaw = renderViewEntity.rotationYaw;
                float rotationPitch = renderViewEntity.rotationPitch;
                if (this.mc.gameSettings.thirdPersonView == "  ".length()) {
                    rotationPitch += 180.0f;
                }
                final double n6 = -MathHelper.sin(rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f) * n5;
                final double n7 = MathHelper.cos(rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f) * n5;
                final double n8 = -MathHelper.sin(rotationPitch / 180.0f * 3.1415927f) * n5;
                int i = "".length();
                "".length();
                if (4 == 3) {
                    throw null;
                }
                while (i < (0x80 ^ 0x88)) {
                    final float n9 = (i & " ".length()) * "  ".length() - " ".length();
                    final float n10 = (i >> " ".length() & " ".length()) * "  ".length() - " ".length();
                    final float n11 = (i >> "  ".length() & " ".length()) * "  ".length() - " ".length();
                    final float n12 = n9 * 0.1f;
                    final float n13 = n10 * 0.1f;
                    final float n14 = n11 * 0.1f;
                    final MovingObjectPosition rayTraceBlocks = this.mc.theWorld.rayTraceBlocks(new Vec3(n2 + n12, n3 + n13, n4 + n14), new Vec3(n2 - n6 + n12 + n14, n3 - n8 + n13, n4 - n7 + n14));
                    if (rayTraceBlocks != null) {
                        final double distanceTo = rayTraceBlocks.hitVec.distanceTo(new Vec3(n2, n3, n4));
                        if (distanceTo < n5) {
                            n5 = distanceTo;
                        }
                    }
                    ++i;
                }
                if (this.mc.gameSettings.thirdPersonView == "  ".length()) {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }
                GlStateManager.rotate(renderViewEntity.rotationPitch - rotationPitch, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(renderViewEntity.rotationYaw - rotationYaw, 0.0f, 1.0f, 0.0f);
                GlStateManager.translate(0.0f, 0.0f, (float)(-n5));
                GlStateManager.rotate(rotationYaw - renderViewEntity.rotationYaw, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(rotationPitch - renderViewEntity.rotationPitch, 1.0f, 0.0f, 0.0f);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
        }
        else {
            GlStateManager.translate(0.0f, 0.0f, -0.1f);
        }
        if (!this.mc.gameSettings.debugCamEnable) {
            GlStateManager.rotate(renderViewEntity.prevRotationPitch + (renderViewEntity.rotationPitch - renderViewEntity.prevRotationPitch) * n, 1.0f, 0.0f, 0.0f);
            if (renderViewEntity instanceof EntityAnimal) {
                final EntityAnimal entityAnimal = (EntityAnimal)renderViewEntity;
                GlStateManager.rotate(entityAnimal.prevRotationYawHead + (entityAnimal.rotationYawHead - entityAnimal.prevRotationYawHead) * n + 180.0f, 0.0f, 1.0f, 0.0f);
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            else {
                GlStateManager.rotate(renderViewEntity.prevRotationYaw + (renderViewEntity.rotationYaw - renderViewEntity.prevRotationYaw) * n + 180.0f, 0.0f, 1.0f, 0.0f);
            }
        }
        GlStateManager.translate(0.0f, -eyeHeight, 0.0f);
        this.cloudFog = this.mc.renderGlobal.hasCloudFog(renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * n, renderViewEntity.prevPosY + (renderViewEntity.posY - renderViewEntity.prevPosY) * n + eyeHeight, renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * n, n);
    }
    
    private void renderWorldPass(final int n, final float n2, final long n3) {
        final RenderGlobal renderGlobal = this.mc.renderGlobal;
        final EffectRenderer effectRenderer = this.mc.effectRenderer;
        final boolean drawBlockOutline = this.isDrawBlockOutline();
        GlStateManager.enableCull();
        this.mc.mcProfiler.endStartSection(EntityRenderer.I[0xA0 ^ 0x8C]);
        GlStateManager.viewport("".length(), "".length(), this.mc.displayWidth, this.mc.displayHeight);
        this.updateFogColor(n2);
        GlStateManager.clear(7760 + 10409 - 1699 + 170);
        this.mc.mcProfiler.endStartSection(EntityRenderer.I[0x4E ^ 0x63]);
        this.setupCameraTransform(n2, n);
        final EntityPlayerSP thePlayer = this.mc.thePlayer;
        int n4;
        if (this.mc.gameSettings.thirdPersonView == "  ".length()) {
            n4 = " ".length();
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        else {
            n4 = "".length();
        }
        ActiveRenderInfo.updateRenderInfo(thePlayer, n4 != 0);
        this.mc.mcProfiler.endStartSection(EntityRenderer.I[0x38 ^ 0x16]);
        ClippingHelperImpl.getInstance();
        this.mc.mcProfiler.endStartSection(EntityRenderer.I[0x51 ^ 0x7E]);
        final Frustum frustum = new Frustum();
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        frustum.setPosition(renderViewEntity.lastTickPosX + (renderViewEntity.posX - renderViewEntity.lastTickPosX) * n2, renderViewEntity.lastTickPosY + (renderViewEntity.posY - renderViewEntity.lastTickPosY) * n2, renderViewEntity.lastTickPosZ + (renderViewEntity.posZ - renderViewEntity.lastTickPosZ) * n2);
        if (!Config.isSkyEnabled() && !Config.isSunMoonEnabled() && !Config.isStarsEnabled()) {
            GlStateManager.disableBlend();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            this.setupFog(-" ".length(), n2);
            this.mc.mcProfiler.endStartSection(EntityRenderer.I[0xBB ^ 0x8B]);
            GlStateManager.matrixMode(914 + 599 + 91 + 4285);
            GlStateManager.loadIdentity();
            Project.gluPerspective(this.getFOVModifier(n2, (boolean)(" ".length() != 0)), this.mc.displayWidth / this.mc.displayHeight, 0.05f, this.clipDistance);
            GlStateManager.matrixMode(3166 + 5092 - 7657 + 5287);
            renderGlobal.renderSky(n2, n);
            GlStateManager.matrixMode(149 + 4099 - 2260 + 3901);
            GlStateManager.loadIdentity();
            Project.gluPerspective(this.getFOVModifier(n2, (boolean)(" ".length() != 0)), this.mc.displayWidth / this.mc.displayHeight, 0.05f, this.clipDistance);
            GlStateManager.matrixMode(4858 + 5071 - 4932 + 891);
        }
        this.setupFog("".length(), n2);
        GlStateManager.shadeModel(7093 + 4167 - 7637 + 3802);
        if (renderViewEntity.posY + renderViewEntity.getEyeHeight() < 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0f) {
            this.renderCloudsCheck(renderGlobal, n2, n);
        }
        this.mc.mcProfiler.endStartSection(EntityRenderer.I[0xBD ^ 0x8C]);
        this.setupFog("".length(), n2);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        RenderHelper.disableStandardItemLighting();
        this.mc.mcProfiler.endStartSection(EntityRenderer.I[0x7 ^ 0x35]);
        final RenderGlobal renderGlobal2 = renderGlobal;
        final Entity entity = renderViewEntity;
        final double n5 = n2;
        final Frustum frustum2 = frustum;
        final int frameCount = this.frameCount;
        this.frameCount = frameCount + " ".length();
        renderGlobal2.setupTerrain(entity, n5, frustum2, frameCount, this.mc.thePlayer.isSpectator());
        if (n == 0 || n == "  ".length()) {
            this.mc.mcProfiler.endStartSection(EntityRenderer.I[0x40 ^ 0x73]);
            Lagometer.timerChunkUpload.start();
            this.mc.renderGlobal.updateChunks(n3);
            Lagometer.timerChunkUpload.end();
        }
        this.mc.mcProfiler.endStartSection(EntityRenderer.I[0x65 ^ 0x51]);
        Lagometer.timerTerrain.start();
        if (this.mc.gameSettings.ofSmoothFps && n > 0) {
            GL11.glFinish();
        }
        GlStateManager.matrixMode(4399 + 2758 - 6587 + 5318);
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        renderGlobal.renderBlockLayer(EnumWorldBlockLayer.SOLID, n2, n, renderViewEntity);
        GlStateManager.enableAlpha();
        renderGlobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT_MIPPED, n2, n, renderViewEntity);
        this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap("".length() != 0, "".length() != 0);
        renderGlobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT, n2, n, renderViewEntity);
        this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
        Lagometer.timerTerrain.end();
        GlStateManager.shadeModel(5132 + 6789 - 9559 + 5062);
        GlStateManager.alphaFunc(49 + 324 - 95 + 238, 0.1f);
        if (!this.debugView) {
            GlStateManager.matrixMode(2749 + 601 - 2423 + 4961);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            RenderHelper.enableStandardItemLighting();
            this.mc.mcProfiler.endStartSection(EntityRenderer.I[0x68 ^ 0x5D]);
            if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
                final ReflectorMethod forgeHooksClient_setRenderPass = Reflector.ForgeHooksClient_setRenderPass;
                final Object[] array = new Object[" ".length()];
                array["".length()] = "".length();
                Reflector.callVoid(forgeHooksClient_setRenderPass, array);
            }
            renderGlobal.renderEntities(renderViewEntity, frustum, n2);
            if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
                final ReflectorMethod forgeHooksClient_setRenderPass2 = Reflector.ForgeHooksClient_setRenderPass;
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = -" ".length();
                Reflector.callVoid(forgeHooksClient_setRenderPass2, array2);
            }
            RenderHelper.disableStandardItemLighting();
            this.disableLightmap();
            GlStateManager.matrixMode(1219 + 3389 - 3933 + 5213);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            if (this.mc.objectMouseOver != null && renderViewEntity.isInsideOfMaterial(Material.water) && drawBlockOutline) {
                final EntityPlayer entityPlayer = (EntityPlayer)renderViewEntity;
                GlStateManager.disableAlpha();
                this.mc.mcProfiler.endStartSection(EntityRenderer.I[0x63 ^ 0x55]);
                Label_1325: {
                    if (Reflector.ForgeHooksClient_onDrawBlockHighlight.exists()) {
                        final ReflectorMethod forgeHooksClient_onDrawBlockHighlight = Reflector.ForgeHooksClient_onDrawBlockHighlight;
                        final Object[] array3 = new Object[0x12 ^ 0x14];
                        array3["".length()] = renderGlobal;
                        array3[" ".length()] = entityPlayer;
                        array3["  ".length()] = this.mc.objectMouseOver;
                        array3["   ".length()] = "".length();
                        array3[0x48 ^ 0x4C] = entityPlayer.getHeldItem();
                        array3[0xC ^ 0x9] = n2;
                        if (Reflector.callBoolean(forgeHooksClient_onDrawBlockHighlight, array3)) {
                            break Label_1325;
                        }
                    }
                    if (!this.mc.gameSettings.hideGUI) {
                        renderGlobal.drawSelectionBox(entityPlayer, this.mc.objectMouseOver, "".length(), n2);
                    }
                }
                GlStateManager.enableAlpha();
            }
        }
        GlStateManager.matrixMode(2442 + 620 - 1647 + 4473);
        GlStateManager.popMatrix();
        if (drawBlockOutline && this.mc.objectMouseOver != null && !renderViewEntity.isInsideOfMaterial(Material.water)) {
            final EntityPlayer entityPlayer2 = (EntityPlayer)renderViewEntity;
            GlStateManager.disableAlpha();
            this.mc.mcProfiler.endStartSection(EntityRenderer.I[0x40 ^ 0x77]);
            Label_1543: {
                if (Reflector.ForgeHooksClient_onDrawBlockHighlight.exists()) {
                    final ReflectorMethod forgeHooksClient_onDrawBlockHighlight2 = Reflector.ForgeHooksClient_onDrawBlockHighlight;
                    final Object[] array4 = new Object[0x82 ^ 0x84];
                    array4["".length()] = renderGlobal;
                    array4[" ".length()] = entityPlayer2;
                    array4["  ".length()] = this.mc.objectMouseOver;
                    array4["   ".length()] = "".length();
                    array4[0x8D ^ 0x89] = entityPlayer2.getHeldItem();
                    array4[0xAD ^ 0xA8] = n2;
                    if (Reflector.callBoolean(forgeHooksClient_onDrawBlockHighlight2, array4)) {
                        break Label_1543;
                    }
                }
                if (!this.mc.gameSettings.hideGUI) {
                    renderGlobal.drawSelectionBox(entityPlayer2, this.mc.objectMouseOver, "".length(), n2);
                }
            }
            GlStateManager.enableAlpha();
        }
        this.mc.mcProfiler.endStartSection(EntityRenderer.I[0x57 ^ 0x6F]);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(486 + 738 - 624 + 170, " ".length(), " ".length(), "".length());
        this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap("".length() != 0, "".length() != 0);
        renderGlobal.drawBlockDamageTexture(Tessellator.getInstance(), Tessellator.getInstance().getWorldRenderer(), renderViewEntity, n2);
        this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
        GlStateManager.disableBlend();
        if (!this.debugView) {
            this.enableLightmap();
            this.mc.mcProfiler.endStartSection(EntityRenderer.I[0x6B ^ 0x52]);
            effectRenderer.renderLitParticles(renderViewEntity, n2);
            RenderHelper.disableStandardItemLighting();
            this.setupFog("".length(), n2);
            this.mc.mcProfiler.endStartSection(EntityRenderer.I[0x7C ^ 0x46]);
            effectRenderer.renderParticles(renderViewEntity, n2);
            this.disableLightmap();
        }
        GlStateManager.depthMask("".length() != 0);
        GlStateManager.enableCull();
        this.mc.mcProfiler.endStartSection(EntityRenderer.I[0xAB ^ 0x90]);
        this.renderRainSnow(n2);
        GlStateManager.depthMask(" ".length() != 0);
        renderGlobal.renderWorldBorder(renderViewEntity, n2);
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.tryBlendFuncSeparate(478 + 147 - 326 + 471, 655 + 73 - 578 + 621, " ".length(), "".length());
        GlStateManager.alphaFunc(513 + 123 - 524 + 404, 0.1f);
        this.setupFog("".length(), n2);
        GlStateManager.enableBlend();
        GlStateManager.depthMask("".length() != 0);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        GlStateManager.shadeModel(5929 + 1232 - 3128 + 3392);
        this.mc.mcProfiler.endStartSection(EntityRenderer.I[0x7F ^ 0x43]);
        renderGlobal.renderBlockLayer(EnumWorldBlockLayer.TRANSLUCENT, n2, n, renderViewEntity);
        if (Reflector.ForgeHooksClient_setRenderPass.exists() && !this.debugView) {
            RenderHelper.enableStandardItemLighting();
            this.mc.mcProfiler.endStartSection(EntityRenderer.I[0x3D ^ 0x0]);
            final ReflectorMethod forgeHooksClient_setRenderPass3 = Reflector.ForgeHooksClient_setRenderPass;
            final Object[] array5 = new Object[" ".length()];
            array5["".length()] = " ".length();
            Reflector.callVoid(forgeHooksClient_setRenderPass3, array5);
            this.mc.renderGlobal.renderEntities(renderViewEntity, frustum, n2);
            final ReflectorMethod forgeHooksClient_setRenderPass4 = Reflector.ForgeHooksClient_setRenderPass;
            final Object[] array6 = new Object[" ".length()];
            array6["".length()] = -" ".length();
            Reflector.callVoid(forgeHooksClient_setRenderPass4, array6);
            RenderHelper.disableStandardItemLighting();
        }
        GlStateManager.shadeModel(2168 + 5152 - 6534 + 6638);
        GlStateManager.depthMask(" ".length() != 0);
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.disableFog();
        if (renderViewEntity.posY + renderViewEntity.getEyeHeight() >= 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0f) {
            this.mc.mcProfiler.endStartSection(EntityRenderer.I[0x5E ^ 0x60]);
            this.renderCloudsCheck(renderGlobal, n2, n);
        }
        if (Reflector.ForgeHooksClient_dispatchRenderLast.exists()) {
            this.mc.mcProfiler.endStartSection(EntityRenderer.I[0xFA ^ 0xC5]);
            final ReflectorMethod forgeHooksClient_dispatchRenderLast = Reflector.ForgeHooksClient_dispatchRenderLast;
            final Object[] array7 = new Object["  ".length()];
            array7["".length()] = renderGlobal;
            array7[" ".length()] = n2;
            Reflector.callVoid(forgeHooksClient_dispatchRenderLast, array7);
        }
        this.mc.mcProfiler.endStartSection(EntityRenderer.I[0x80 ^ 0xC0]);
        final ReflectorMethod forgeHooksClient_renderFirstPersonHand = Reflector.ForgeHooksClient_renderFirstPersonHand;
        final Object[] array8 = new Object["   ".length()];
        array8["".length()] = this.mc.renderGlobal;
        array8[" ".length()] = n2;
        array8["  ".length()] = n;
        if (!Reflector.callBoolean(forgeHooksClient_renderFirstPersonHand, array8) && this.renderHand) {
            GlStateManager.clear(44 + 80 - 54 + 186);
            this.renderHand(n2, n);
            this.renderWorldDirections(n2);
        }
    }
    
    public void updateShaderGroupSize(final int n, final int n2) {
        if (OpenGlHelper.shadersSupported) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.createBindFramebuffers(n, n2);
            }
            this.mc.renderGlobal.createBindEntityOutlineFbs(n, n2);
        }
    }
    
    public void activateNextShader() {
        if (OpenGlHelper.shadersSupported && this.mc.getRenderViewEntity() instanceof EntityPlayer) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.deleteShaderGroup();
            }
            this.shaderIndex = (this.shaderIndex + " ".length()) % (EntityRenderer.shaderResourceLocations.length + " ".length());
            if (this.shaderIndex != EntityRenderer.shaderCount) {
                this.loadShader(EntityRenderer.shaderResourceLocations[this.shaderIndex]);
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            else {
                this.theShaderGroup = null;
            }
        }
    }
    
    private void updateMainMenu(final GuiMainMenu guiMainMenu) {
        try {
            Object o = null;
            final Calendar instance = Calendar.getInstance();
            instance.setTime(new Date());
            final int value = instance.get(0x3E ^ 0x3B);
            final int n = instance.get("  ".length()) + " ".length();
            if (value == (0xAB ^ 0xA3) && n == (0x79 ^ 0x7D)) {
                o = EntityRenderer.I[0x64 ^ 0x34];
            }
            if (value == (0x33 ^ 0x3D) && n == (0x8D ^ 0x85)) {
                o = EntityRenderer.I[0x27 ^ 0x76];
            }
            if (o == null) {
                return;
            }
            final Field[] declaredFields = GuiMainMenu.class.getDeclaredFields();
            int i = "".length();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
            while (i < declaredFields.length) {
                if (declaredFields[i].getType() == String.class) {
                    declaredFields[i].setAccessible(" ".length() != 0);
                    declaredFields[i].set(guiMainMenu, o);
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                    return;
                }
                else {
                    ++i;
                }
            }
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        catch (Throwable t) {}
    }
    
    public EntityRenderer(final Minecraft mc, final IResourceManager resourceManager) {
        this.random = new Random();
        this.mouseFilterXAxis = new MouseFilter();
        this.mouseFilterYAxis = new MouseFilter();
        this.thirdPersonDistance = 4.0f;
        this.thirdPersonDistanceTemp = 4.0f;
        this.renderHand = (" ".length() != 0);
        this.drawBlockOutline = (" ".length() != 0);
        this.prevFrameTime = Minecraft.getSystemTime();
        this.rainXCoords = new float[914 + 738 - 1357 + 729];
        this.rainYCoords = new float[433 + 332 - 698 + 957];
        this.fogColorBuffer = GLAllocation.createDirectFloatBuffer(0xC ^ 0x1C);
        this.debugViewDirection = "".length();
        this.debugView = ("".length() != 0);
        this.cameraZoom = 1.0;
        this.initialized = ("".length() != 0);
        this.updatedWorld = null;
        this.showDebugInfo = ("".length() != 0);
        this.fogStandard = ("".length() != 0);
        this.clipDistance = 128.0f;
        this.lastServerTime = 0L;
        this.lastServerTicks = "".length();
        this.serverWaitTime = "".length();
        this.serverWaitTimeCurrent = "".length();
        this.avgServerTimeDiff = 0.0f;
        this.avgServerTickDiff = 0.0f;
        this.lastErrorCheckTimeMs = 0L;
        this.shaderIndex = EntityRenderer.shaderCount;
        this.useShader = ("".length() != 0);
        this.frameCount = "".length();
        this.mc = mc;
        this.resourceManager = resourceManager;
        this.itemRenderer = mc.getItemRenderer();
        this.theMapItemRenderer = new MapItemRenderer(mc.getTextureManager());
        this.lightmapTexture = new DynamicTexture(0x6A ^ 0x7A, 0x35 ^ 0x25);
        this.locationLightMap = mc.getTextureManager().getDynamicTextureLocation(EntityRenderer.I[0x56 ^ 0x4D], this.lightmapTexture);
        this.lightmapColors = this.lightmapTexture.getTextureData();
        this.theShaderGroup = null;
        int i = "".length();
        "".length();
        if (4 < 4) {
            throw null;
        }
        while (i < (0x54 ^ 0x74)) {
            int j = "".length();
            "".length();
            if (false) {
                throw null;
            }
            while (j < (0x69 ^ 0x49)) {
                final float n = j - (0x34 ^ 0x24);
                final float n2 = i - (0xAC ^ 0xBC);
                final float sqrt_float = MathHelper.sqrt_float(n * n + n2 * n2);
                this.rainXCoords[i << (0x88 ^ 0x8D) | j] = -n2 / sqrt_float;
                this.rainYCoords[i << (0x2F ^ 0x2A) | j] = n / sqrt_float;
                ++j;
            }
            ++i;
        }
    }
    
    public void func_181022_b() {
        if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
        }
        this.theShaderGroup = null;
        this.shaderIndex = EntityRenderer.shaderCount;
    }
    
    public boolean isShaderActive() {
        if (OpenGlHelper.shadersSupported && this.theShaderGroup != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private float getNightVisionBrightness(final EntityLivingBase entityLivingBase, final float n) {
        final int duration = entityLivingBase.getActivePotionEffect(Potion.nightVision).getDuration();
        float n2;
        if (duration > 191 + 4 - 170 + 175) {
            n2 = 1.0f;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            n2 = 0.7f + MathHelper.sin((duration - n) * 3.1415927f * 0.2f) * 0.3f;
        }
        return n2;
    }
    
    private void setupCameraTransform(final float n, final int n2) {
        this.farPlaneDistance = this.mc.gameSettings.renderDistanceChunks * (0x6F ^ 0x7F);
        if (Config.isFogFancy()) {
            this.farPlaneDistance *= 0.95f;
        }
        if (Config.isFogFast()) {
            this.farPlaneDistance *= 0.83f;
        }
        GlStateManager.matrixMode(2456 + 4986 - 7440 + 5887);
        GlStateManager.loadIdentity();
        final float n3 = 0.07f;
        if (this.mc.gameSettings.anaglyph) {
            GlStateManager.translate(-(n2 * "  ".length() - " ".length()) * n3, 0.0f, 0.0f);
        }
        this.clipDistance = this.farPlaneDistance * 2.0f;
        if (this.clipDistance < 173.0f) {
            this.clipDistance = 173.0f;
        }
        if (this.mc.theWorld.provider.getDimensionId() == " ".length()) {
            this.clipDistance = 256.0f;
        }
        if (this.cameraZoom != 1.0) {
            GlStateManager.translate((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0f);
            GlStateManager.scale(this.cameraZoom, this.cameraZoom, 1.0);
        }
        Project.gluPerspective(this.getFOVModifier(n, (boolean)(" ".length() != 0)), this.mc.displayWidth / this.mc.displayHeight, 0.05f, this.clipDistance);
        GlStateManager.matrixMode(3169 + 1801 - 3821 + 4739);
        GlStateManager.loadIdentity();
        if (this.mc.gameSettings.anaglyph) {
            GlStateManager.translate((n2 * "  ".length() - " ".length()) * 0.1f, 0.0f, 0.0f);
        }
        this.hurtCameraEffect(n);
        if (this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(n);
        }
        final float n4 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * n;
        if (n4 > 0.0f) {
            int n5 = 0xB5 ^ 0xA1;
            if (this.mc.thePlayer.isPotionActive(Potion.confusion)) {
                n5 = (0x46 ^ 0x41);
            }
            final float n6 = 5.0f / (n4 * n4 + 5.0f) - n4 * 0.04f;
            final float n7 = n6 * n6;
            GlStateManager.rotate((this.rendererUpdateCount + n) * n5, 0.0f, 1.0f, 1.0f);
            GlStateManager.scale(1.0f / n7, 1.0f, 1.0f);
            GlStateManager.rotate(-(this.rendererUpdateCount + n) * n5, 0.0f, 1.0f, 1.0f);
        }
        this.orientCamera(n);
        if (this.debugView) {
            switch (this.debugViewDirection) {
                case 0: {
                    GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
                    "".length();
                    if (3 < 1) {
                        throw null;
                    }
                    break;
                }
                case 1: {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                    "".length();
                    if (3 == -1) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                    break;
                }
                case 3: {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                    break;
                }
                case 4: {
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                    break;
                }
            }
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected void renderRainSnow(final float n) {
        if (Reflector.ForgeWorldProvider_getWeatherRenderer.exists()) {
            final Object call = Reflector.call(this.mc.theWorld.provider, Reflector.ForgeWorldProvider_getWeatherRenderer, new Object["".length()]);
            if (call != null) {
                final Object o = call;
                final ReflectorMethod iRenderHandler_render = Reflector.IRenderHandler_render;
                final Object[] array = new Object["   ".length()];
                array["".length()] = n;
                array[" ".length()] = this.mc.theWorld;
                array["  ".length()] = this.mc;
                Reflector.callVoid(o, iRenderHandler_render, array);
                return;
            }
        }
        final float rainStrength = this.mc.theWorld.getRainStrength(n);
        if (rainStrength > 0.0f) {
            if (Config.isRainOff()) {
                return;
            }
            this.enableLightmap();
            final Entity renderViewEntity = this.mc.getRenderViewEntity();
            final WorldClient theWorld = this.mc.theWorld;
            final int floor_double = MathHelper.floor_double(renderViewEntity.posX);
            final int floor_double2 = MathHelper.floor_double(renderViewEntity.posY);
            final int floor_double3 = MathHelper.floor_double(renderViewEntity.posZ);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            GlStateManager.disableCull();
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(83 + 256 - 2 + 433, 494 + 382 - 107 + 2, " ".length(), "".length());
            GlStateManager.alphaFunc(7 + 318 + 155 + 36, 0.1f);
            final double n2 = renderViewEntity.lastTickPosX + (renderViewEntity.posX - renderViewEntity.lastTickPosX) * n;
            final double n3 = renderViewEntity.lastTickPosY + (renderViewEntity.posY - renderViewEntity.lastTickPosY) * n;
            final double n4 = renderViewEntity.lastTickPosZ + (renderViewEntity.posZ - renderViewEntity.lastTickPosZ) * n;
            final int floor_double4 = MathHelper.floor_double(n3);
            int n5 = 0x6 ^ 0x3;
            if (Config.isRainFancy()) {
                n5 = (0xE ^ 0x4);
            }
            int n6 = -" ".length();
            final float n7 = this.rendererUpdateCount + n;
            worldRenderer.setTranslation(-n2, -n3, -n4);
            if (Config.isRainFancy()) {
                n5 = (0x3C ^ 0x36);
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            int i = floor_double3 - n5;
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (i <= floor_double3 + n5) {
                int j = floor_double - n5;
                "".length();
                if (4 < 3) {
                    throw null;
                }
                while (j <= floor_double + n5) {
                    final int n8 = (i - floor_double3 + (0xBD ^ 0xAD)) * (0xA1 ^ 0x81) + j - floor_double + (0xE ^ 0x1E);
                    final double n9 = this.rainXCoords[n8] * 0.5;
                    final double n10 = this.rainYCoords[n8] * 0.5;
                    mutableBlockPos.func_181079_c(j, "".length(), i);
                    final BiomeGenBase biomeGenForCoords = theWorld.getBiomeGenForCoords(mutableBlockPos);
                    if (biomeGenForCoords.canSpawnLightningBolt() || biomeGenForCoords.getEnableSnow()) {
                        final int y = theWorld.getPrecipitationHeight(mutableBlockPos).getY();
                        int n11 = floor_double2 - n5;
                        int n12 = floor_double2 + n5;
                        if (n11 < y) {
                            n11 = y;
                        }
                        if (n12 < y) {
                            n12 = y;
                        }
                        int n13;
                        if ((n13 = y) < floor_double4) {
                            n13 = floor_double4;
                        }
                        if (n11 != n12) {
                            this.random.setSeed(j * j * (1042 + 1433 - 6 + 652) + j * (4959545 + 12812095 + 27418783 + 48548) ^ i * i * (83992 + 147014 - 95518 + 283223) + i * (5277 + 8803 - 10128 + 9809));
                            mutableBlockPos.func_181079_c(j, n11, i);
                            if (theWorld.getWorldChunkManager().getTemperatureAtHeight(biomeGenForCoords.getFloatTemperature(mutableBlockPos), y) >= 0.15f) {
                                if (n6 != 0) {
                                    if (n6 >= 0) {
                                        instance.draw();
                                    }
                                    n6 = "".length();
                                    this.mc.getTextureManager().bindTexture(EntityRenderer.locationRainPng);
                                    worldRenderer.begin(0x7F ^ 0x78, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                                }
                                final double n14 = ((this.rendererUpdateCount + j * j * (1406 + 99 + 615 + 1001) + j * (25018592 + 31536368 - 20819893 + 9503904) + i * i * (311217 + 29797 - 295633 + 373330) + i * (226 + 10979 - 11016 + 13572) & (0x9 ^ 0x16)) + n) / 32.0 * (3.0 + this.random.nextDouble());
                                final double n15 = j + 0.5f - renderViewEntity.posX;
                                final double n16 = i + 0.5f - renderViewEntity.posZ;
                                final float n17 = MathHelper.sqrt_double(n15 * n15 + n16 * n16) / n5;
                                final float n18 = ((1.0f - n17 * n17) * 0.5f + 0.5f) * rainStrength;
                                mutableBlockPos.func_181079_c(j, n13, i);
                                final int combinedLight = theWorld.getCombinedLight(mutableBlockPos, "".length());
                                final int n19 = combinedLight >> (0x75 ^ 0x65) & 9073 + 23793 + 17356 + 15313;
                                final int n20 = combinedLight & 35111 + 1268 + 22316 + 6840;
                                worldRenderer.pos(j - n9 + 0.5, n11, i - n10 + 0.5).tex(0.0, n11 * 0.25 + n14).color(1.0f, 1.0f, 1.0f, n18).lightmap(n19, n20).endVertex();
                                worldRenderer.pos(j + n9 + 0.5, n11, i + n10 + 0.5).tex(1.0, n11 * 0.25 + n14).color(1.0f, 1.0f, 1.0f, n18).lightmap(n19, n20).endVertex();
                                worldRenderer.pos(j + n9 + 0.5, n12, i + n10 + 0.5).tex(1.0, n12 * 0.25 + n14).color(1.0f, 1.0f, 1.0f, n18).lightmap(n19, n20).endVertex();
                                worldRenderer.pos(j - n9 + 0.5, n12, i - n10 + 0.5).tex(0.0, n12 * 0.25 + n14).color(1.0f, 1.0f, 1.0f, n18).lightmap(n19, n20).endVertex();
                                "".length();
                                if (-1 != -1) {
                                    throw null;
                                }
                            }
                            else {
                                if (n6 != " ".length()) {
                                    if (n6 >= 0) {
                                        instance.draw();
                                    }
                                    n6 = " ".length();
                                    this.mc.getTextureManager().bindTexture(EntityRenderer.locationSnowPng);
                                    worldRenderer.begin(0x6A ^ 0x6D, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                                }
                                final double n21 = ((this.rendererUpdateCount & 136 + 23 + 289 + 63) + n) / 512.0f;
                                final double n22 = this.random.nextDouble() + n7 * 0.01 * (float)this.random.nextGaussian();
                                final double n23 = this.random.nextDouble() + n7 * (float)this.random.nextGaussian() * 0.001;
                                final double n24 = j + 0.5f - renderViewEntity.posX;
                                final double n25 = i + 0.5f - renderViewEntity.posZ;
                                final float n26 = MathHelper.sqrt_double(n24 * n24 + n25 * n25) / n5;
                                final float n27 = ((1.0f - n26 * n26) * 0.3f + 0.5f) * rainStrength;
                                mutableBlockPos.func_181079_c(j, n13, i);
                                final int n28 = (theWorld.getCombinedLight(mutableBlockPos, "".length()) * "   ".length() + (11553396 + 12058934 - 9909032 + 2025582)) / (0x9C ^ 0x98);
                                final int n29 = n28 >> (0x6F ^ 0x7F) & 2013 + 25240 - 18559 + 56841;
                                final int n30 = n28 & 32087 + 53638 - 35617 + 15427;
                                worldRenderer.pos(j - n9 + 0.5, n11, i - n10 + 0.5).tex(0.0 + n22, n11 * 0.25 + n21 + n23).color(1.0f, 1.0f, 1.0f, n27).lightmap(n29, n30).endVertex();
                                worldRenderer.pos(j + n9 + 0.5, n11, i + n10 + 0.5).tex(1.0 + n22, n11 * 0.25 + n21 + n23).color(1.0f, 1.0f, 1.0f, n27).lightmap(n29, n30).endVertex();
                                worldRenderer.pos(j + n9 + 0.5, n12, i + n10 + 0.5).tex(1.0 + n22, n12 * 0.25 + n21 + n23).color(1.0f, 1.0f, 1.0f, n27).lightmap(n29, n30).endVertex();
                                worldRenderer.pos(j - n9 + 0.5, n12, i - n10 + 0.5).tex(0.0 + n22, n12 * 0.25 + n21 + n23).color(1.0f, 1.0f, 1.0f, n27).lightmap(n29, n30).endVertex();
                            }
                        }
                    }
                    ++j;
                }
                ++i;
            }
            if (n6 >= 0) {
                instance.draw();
            }
            worldRenderer.setTranslation(0.0, 0.0, 0.0);
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(442 + 395 - 666 + 345, 0.1f);
            this.disableLightmap();
        }
    }
    
    private void renderWorldDirections(final float n) {
        if (this.mc.gameSettings.showDebugInfo && !this.mc.gameSettings.hideGUI && !this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo) {
            final Entity renderViewEntity = this.mc.getRenderViewEntity();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(87 + 113 + 147 + 423, 272 + 100 + 338 + 61, " ".length(), "".length());
            GL11.glLineWidth(1.0f);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask("".length() != 0);
            GlStateManager.pushMatrix();
            GlStateManager.matrixMode(2671 + 1482 - 232 + 1967);
            GlStateManager.loadIdentity();
            this.orientCamera(n);
            GlStateManager.translate(0.0f, renderViewEntity.getEyeHeight(), 0.0f);
            RenderGlobal.func_181563_a(new AxisAlignedBB(0.0, 0.0, 0.0, 0.005, 1.0E-4, 1.0E-4), 132 + 93 - 130 + 160, "".length(), "".length(), 122 + 3 - 45 + 175);
            RenderGlobal.func_181563_a(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0E-4, 1.0E-4, 0.005), "".length(), "".length(), 105 + 250 - 306 + 206, 23 + 5 + 224 + 3);
            RenderGlobal.func_181563_a(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0E-4, 0.0033, 1.0E-4), "".length(), 141 + 6 - 145 + 253, "".length(), 102 + 94 - 113 + 172);
            GlStateManager.popMatrix();
            GlStateManager.depthMask(" ".length() != 0);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }
    }
    
    static {
        I();
        __OBFID = EntityRenderer.I["".length()];
        logger = LogManager.getLogger();
        locationRainPng = new ResourceLocation(EntityRenderer.I[" ".length()]);
        locationSnowPng = new ResourceLocation(EntityRenderer.I["  ".length()]);
        final ResourceLocation[] shaderResourceLocations2 = new ResourceLocation[0xAC ^ 0xB4];
        shaderResourceLocations2["".length()] = new ResourceLocation(EntityRenderer.I["   ".length()]);
        shaderResourceLocations2[" ".length()] = new ResourceLocation(EntityRenderer.I[0x2F ^ 0x2B]);
        shaderResourceLocations2["  ".length()] = new ResourceLocation(EntityRenderer.I[0x95 ^ 0x90]);
        shaderResourceLocations2["   ".length()] = new ResourceLocation(EntityRenderer.I[0x98 ^ 0x9E]);
        shaderResourceLocations2[0x1 ^ 0x5] = new ResourceLocation(EntityRenderer.I[0xB9 ^ 0xBE]);
        shaderResourceLocations2[0x4B ^ 0x4E] = new ResourceLocation(EntityRenderer.I[0x9C ^ 0x94]);
        shaderResourceLocations2[0x71 ^ 0x77] = new ResourceLocation(EntityRenderer.I[0x13 ^ 0x1A]);
        shaderResourceLocations2[0xBB ^ 0xBC] = new ResourceLocation(EntityRenderer.I[0x88 ^ 0x82]);
        shaderResourceLocations2[0x9 ^ 0x1] = new ResourceLocation(EntityRenderer.I[0x3 ^ 0x8]);
        shaderResourceLocations2[0x62 ^ 0x6B] = new ResourceLocation(EntityRenderer.I[0x13 ^ 0x1F]);
        shaderResourceLocations2[0x4D ^ 0x47] = new ResourceLocation(EntityRenderer.I[0x79 ^ 0x74]);
        shaderResourceLocations2[0xB1 ^ 0xBA] = new ResourceLocation(EntityRenderer.I[0x84 ^ 0x8A]);
        shaderResourceLocations2[0xA5 ^ 0xA9] = new ResourceLocation(EntityRenderer.I[0x14 ^ 0x1B]);
        shaderResourceLocations2[0x62 ^ 0x6F] = new ResourceLocation(EntityRenderer.I[0x96 ^ 0x86]);
        shaderResourceLocations2[0xB3 ^ 0xBD] = new ResourceLocation(EntityRenderer.I[0x48 ^ 0x59]);
        shaderResourceLocations2[0x58 ^ 0x57] = new ResourceLocation(EntityRenderer.I[0x80 ^ 0x92]);
        shaderResourceLocations2[0x54 ^ 0x44] = new ResourceLocation(EntityRenderer.I[0x86 ^ 0x95]);
        shaderResourceLocations2[0x13 ^ 0x2] = new ResourceLocation(EntityRenderer.I[0x34 ^ 0x20]);
        shaderResourceLocations2[0x2B ^ 0x39] = new ResourceLocation(EntityRenderer.I[0xAC ^ 0xB9]);
        shaderResourceLocations2[0x16 ^ 0x5] = new ResourceLocation(EntityRenderer.I[0xB1 ^ 0xA7]);
        shaderResourceLocations2[0xBA ^ 0xAE] = new ResourceLocation(EntityRenderer.I[0x26 ^ 0x31]);
        shaderResourceLocations2[0x6A ^ 0x7F] = new ResourceLocation(EntityRenderer.I[0x10 ^ 0x8]);
        shaderResourceLocations2[0xF ^ 0x19] = new ResourceLocation(EntityRenderer.I[0x9E ^ 0x87]);
        shaderResourceLocations2[0x12 ^ 0x5] = new ResourceLocation(EntityRenderer.I[0x70 ^ 0x6A]);
        shaderResourceLocations = shaderResourceLocations2;
        shaderCount = EntityRenderer.shaderResourceLocations.length;
    }
    
    private void frameInit() {
        if (!this.initialized) {
            TextureUtils.registerResourceListener();
            RenderPlayerOF.register();
            if (Config.getBitsOs() == (0xF3 ^ 0xB3) && Config.getBitsJre() == (0x70 ^ 0x50)) {
                Config.setNotify64BitJava(" ".length() != 0);
            }
            this.initialized = (" ".length() != 0);
        }
        Config.isActing();
        Config.checkDisplayMode();
        final WorldClient theWorld = this.mc.theWorld;
        if (theWorld != null) {
            if (Config.getNewRelease() != null) {
                this.mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(EntityRenderer.I[0xE9 ^ 0xA3] + (String.valueOf(EntityRenderer.I[0xE4 ^ 0xA0].replace(EntityRenderer.I[0x4B ^ 0xE], EntityRenderer.I[0xC1 ^ 0x87]).replace(EntityRenderer.I[0xE8 ^ 0xAF], EntityRenderer.I[0xC8 ^ 0x80])) + EntityRenderer.I[0xEB ^ 0xA2] + Config.getNewRelease()) + EntityRenderer.I[0x62 ^ 0x29]));
                Config.setNewRelease(null);
            }
            if (Config.isNotify64BitJava()) {
                Config.setNotify64BitJava("".length() != 0);
                this.mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(EntityRenderer.I[0x49 ^ 0x5]));
            }
        }
        if (this.mc.currentScreen instanceof GuiMainMenu) {
            this.updateMainMenu((GuiMainMenu)this.mc.currentScreen);
        }
        if (this.updatedWorld != theWorld) {
            RandomMobs.worldChanged(this.updatedWorld, theWorld);
            Config.updateThreadPriorities();
            this.lastServerTime = 0L;
            this.lastServerTicks = "".length();
            this.updatedWorld = theWorld;
        }
    }
    
    private FloatBuffer setFogColorBuffer(final float n, final float n2, final float n3, final float n4) {
        this.fogColorBuffer.clear();
        this.fogColorBuffer.put(n).put(n2).put(n3).put(n4);
        this.fogColorBuffer.flip();
        return this.fogColorBuffer;
    }
    
    static Minecraft access$0(final EntityRenderer entityRenderer) {
        return entityRenderer.mc;
    }
    
    public void renderStreamIndicator(final float n) {
        this.setupOverlayRendering();
        this.mc.ingameGUI.renderStreamIndicator(new ScaledResolution(this.mc));
    }
    
    private void frameFinish() {
        if (this.mc.theWorld != null) {
            final long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis > this.lastErrorCheckTimeMs + 10000L) {
                this.lastErrorCheckTimeMs = currentTimeMillis;
                final int glGetError = GL11.glGetError();
                if (glGetError != 0) {
                    this.mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(EntityRenderer.I[0x2A ^ 0x67] + glGetError + EntityRenderer.I[0x76 ^ 0x38] + GLU.gluErrorString(glGetError) + EntityRenderer.I[0x7C ^ 0x33]));
                }
            }
        }
    }
    
    public void enableLightmap() {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.matrixMode(1162 + 2102 + 1951 + 675);
        GlStateManager.loadIdentity();
        final float n = 0.00390625f;
        GlStateManager.scale(n, n, n);
        GlStateManager.translate(8.0f, 8.0f, 8.0f);
        GlStateManager.matrixMode(5151 + 2334 - 3583 + 1986);
        this.mc.getTextureManager().bindTexture(this.locationLightMap);
        GL11.glTexParameteri(3189 + 2522 - 3202 + 1044, 1299 + 6129 - 5880 + 8693, 3133 + 2295 + 166 + 4135);
        GL11.glTexParameteri(3516 + 2613 - 3905 + 1329, 10213 + 4339 - 6739 + 2427, 3298 + 5311 - 8449 + 9569);
        GL11.glTexParameteri(1653 + 1681 - 2345 + 2564, 10103 + 680 - 8117 + 7576, 1826 + 5081 + 1271 + 2318);
        GL11.glTexParameteri(136 + 1060 + 542 + 1815, 6577 + 7088 - 10221 + 6799, 6453 + 4028 - 3792 + 3807);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    public void switchUseShader() {
        int useShader;
        if (this.useShader) {
            useShader = "".length();
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else {
            useShader = " ".length();
        }
        this.useShader = (useShader != 0);
    }
    
    public void loadEntityShader(final Entity entity) {
        if (OpenGlHelper.shadersSupported) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.deleteShaderGroup();
            }
            this.theShaderGroup = null;
            if (entity instanceof EntityCreeper) {
                this.loadShader(new ResourceLocation(EntityRenderer.I[0x3F ^ 0x23]));
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else if (entity instanceof EntitySpider) {
                this.loadShader(new ResourceLocation(EntityRenderer.I[0xB6 ^ 0xAB]));
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            else if (entity instanceof EntityEnderman) {
                this.loadShader(new ResourceLocation(EntityRenderer.I[0x54 ^ 0x4A]));
            }
        }
    }
    
    private void renderHand(final float n, final int n2) {
        if (!this.debugView) {
            GlStateManager.matrixMode(5089 + 646 - 235 + 389);
            GlStateManager.loadIdentity();
            final float n3 = 0.07f;
            if (this.mc.gameSettings.anaglyph) {
                GlStateManager.translate(-(n2 * "  ".length() - " ".length()) * n3, 0.0f, 0.0f);
            }
            Project.gluPerspective(this.getFOVModifier(n, (boolean)("".length() != 0)), this.mc.displayWidth / this.mc.displayHeight, 0.05f, this.farPlaneDistance * 2.0f);
            GlStateManager.matrixMode(2677 + 1715 - 840 + 2336);
            GlStateManager.loadIdentity();
            if (this.mc.gameSettings.anaglyph) {
                GlStateManager.translate((n2 * "  ".length() - " ".length()) * 0.1f, 0.0f, 0.0f);
            }
            GlStateManager.pushMatrix();
            this.hurtCameraEffect(n);
            if (this.mc.gameSettings.viewBobbing) {
                this.setupViewBobbing(n);
            }
            int n4;
            if (this.mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)this.mc.getRenderViewEntity()).isPlayerSleeping()) {
                n4 = " ".length();
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            else {
                n4 = "".length();
            }
            final int n5 = n4;
            if (this.mc.gameSettings.thirdPersonView == 0 && n5 == 0 && !this.mc.gameSettings.hideGUI && !this.mc.playerController.isSpectator()) {
                this.enableLightmap();
                this.itemRenderer.renderItemInFirstPerson(n);
                this.disableLightmap();
            }
            GlStateManager.popMatrix();
            if (this.mc.gameSettings.thirdPersonView == 0 && n5 == 0) {
                this.itemRenderer.renderOverlays(n);
                this.hurtCameraEffect(n);
            }
            if (this.mc.gameSettings.viewBobbing) {
                this.setupViewBobbing(n);
            }
        }
    }
    
    private void updateTorchFlicker() {
        this.torchFlickerDX += (float)((Math.random() - Math.random()) * Math.random() * Math.random());
        this.torchFlickerDX *= 0.9;
        this.torchFlickerX += (this.torchFlickerDX - this.torchFlickerX) * 1.0f;
        this.lightmapUpdateNeeded = (" ".length() != 0);
    }
    
    private float getFOVModifier(final float n, final boolean b) {
        if (this.debugView) {
            return 90.0f;
        }
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        float n2 = 70.0f;
        if (b) {
            n2 = this.mc.gameSettings.fovSetting * (this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * n);
        }
        int n3 = "".length();
        if (this.mc.currentScreen == null) {
            final GameSettings gameSettings = this.mc.gameSettings;
            n3 = (GameSettings.isKeyDown(this.mc.gameSettings.ofKeyBindZoom) ? 1 : 0);
        }
        if (n3 != 0) {
            if (!Config.zoomMode) {
                Config.zoomMode = (" ".length() != 0);
                this.mc.gameSettings.smoothCamera = (" ".length() != 0);
            }
            if (Config.zoomMode) {
                n2 /= 4.0f;
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
        }
        else if (Config.zoomMode) {
            Config.zoomMode = ("".length() != 0);
            this.mc.gameSettings.smoothCamera = ("".length() != 0);
            this.mouseFilterXAxis = new MouseFilter();
            this.mouseFilterYAxis = new MouseFilter();
            this.mc.renderGlobal.displayListEntitiesDirty = (" ".length() != 0);
        }
        if (renderViewEntity instanceof EntityLivingBase && ((EntityLivingBase)renderViewEntity).getHealth() <= 0.0f) {
            n2 /= (1.0f - 500.0f / (((EntityLivingBase)renderViewEntity).deathTime + n + 500.0f)) * 2.0f + 1.0f;
        }
        if (ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, renderViewEntity, n).getMaterial() == Material.water) {
            n2 = n2 * 60.0f / 70.0f;
        }
        return n2;
    }
    
    private void renderCloudsCheck(final RenderGlobal renderGlobal, final float n, final int n2) {
        if (this.mc.gameSettings.renderDistanceChunks >= (0x3B ^ 0x3F) && !Config.isCloudsOff()) {
            this.mc.mcProfiler.endStartSection(EntityRenderer.I[0xE7 ^ 0xA6]);
            GlStateManager.matrixMode(3190 + 1876 - 402 + 1225);
            GlStateManager.loadIdentity();
            Project.gluPerspective(this.getFOVModifier(n, (boolean)(" ".length() != 0)), this.mc.displayWidth / this.mc.displayHeight, 0.05f, this.clipDistance * 4.0f);
            GlStateManager.matrixMode(1860 + 600 + 705 + 2723);
            GlStateManager.pushMatrix();
            this.setupFog("".length(), n);
            renderGlobal.renderClouds(n, n2);
            GlStateManager.disableFog();
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(45 + 434 - 388 + 5798);
            GlStateManager.loadIdentity();
            Project.gluPerspective(this.getFOVModifier(n, (boolean)(" ".length() != 0)), this.mc.displayWidth / this.mc.displayHeight, 0.05f, this.clipDistance);
            GlStateManager.matrixMode(3898 + 3992 - 7581 + 5579);
        }
    }
    
    public void disableLightmap() {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    private boolean isDrawBlockOutline() {
        if (!this.drawBlockOutline) {
            return "".length() != 0;
        }
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        int n;
        if (renderViewEntity instanceof EntityPlayer && !this.mc.gameSettings.hideGUI) {
            n = " ".length();
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        int n2 = n;
        if (n2 != 0 && !((EntityPlayer)renderViewEntity).capabilities.allowEdit) {
            final ItemStack currentEquippedItem = ((EntityPlayer)renderViewEntity).getCurrentEquippedItem();
            if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final BlockPos blockPos = this.mc.objectMouseOver.getBlockPos();
                final Block block = this.mc.theWorld.getBlockState(blockPos).getBlock();
                if (this.mc.playerController.getCurrentGameType() == WorldSettings.GameType.SPECTATOR) {
                    boolean b;
                    if (Reflector.ForgeBlock_hasTileEntity.exists()) {
                        final IBlockState blockState = this.mc.theWorld.getBlockState(blockPos);
                        final Block block2 = block;
                        final ReflectorMethod forgeBlock_hasTileEntity = Reflector.ForgeBlock_hasTileEntity;
                        final Object[] array = new Object[" ".length()];
                        array["".length()] = blockState;
                        b = Reflector.callBoolean(block2, forgeBlock_hasTileEntity, array);
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                    }
                    else {
                        b = block.hasTileEntity();
                    }
                    int n3;
                    if (b && this.mc.theWorld.getTileEntity(blockPos) instanceof IInventory) {
                        n3 = " ".length();
                        "".length();
                        if (4 <= -1) {
                            throw null;
                        }
                    }
                    else {
                        n3 = "".length();
                    }
                    n2 = n3;
                    "".length();
                    if (3 <= 1) {
                        throw null;
                    }
                }
                else {
                    int n4;
                    if (currentEquippedItem != null && (currentEquippedItem.canDestroy(block) || currentEquippedItem.canPlaceOn(block))) {
                        n4 = " ".length();
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                    }
                    else {
                        n4 = "".length();
                    }
                    n2 = n4;
                }
            }
        }
        return n2 != 0;
    }
    
    public ShaderGroup getShaderGroup() {
        return this.theShaderGroup;
    }
    
    private void waitForServerThread() {
        this.serverWaitTimeCurrent = "".length();
        if (Config.isSmoothWorld() && Config.isSingleProcessor()) {
            if (this.mc.isIntegratedServerRunning()) {
                final IntegratedServer integratedServer = this.mc.getIntegratedServer();
                if (integratedServer != null) {
                    if (!this.mc.isGamePaused() && !(this.mc.currentScreen instanceof GuiDownloadTerrain)) {
                        if (this.serverWaitTime > 0) {
                            Lagometer.timerServer.start();
                            Config.sleep(this.serverWaitTime);
                            Lagometer.timerServer.end();
                            this.serverWaitTimeCurrent = this.serverWaitTime;
                        }
                        final long lastServerTime = System.nanoTime() / 1000000L;
                        if (this.lastServerTime != 0L && this.lastServerTicks != 0) {
                            long n = lastServerTime - this.lastServerTime;
                            if (n < 0L) {
                                this.lastServerTime = lastServerTime;
                                n = 0L;
                            }
                            if (n >= 50L) {
                                this.lastServerTime = lastServerTime;
                                final int tickCounter = integratedServer.getTickCounter();
                                int length = tickCounter - this.lastServerTicks;
                                if (length < 0) {
                                    this.lastServerTicks = tickCounter;
                                    length = "".length();
                                }
                                if (length < " ".length() && this.serverWaitTime < (0xFC ^ 0x98)) {
                                    this.serverWaitTime += "  ".length();
                                }
                                if (length > " ".length() && this.serverWaitTime > 0) {
                                    this.serverWaitTime -= " ".length();
                                }
                                this.lastServerTicks = tickCounter;
                                "".length();
                                if (1 <= 0) {
                                    throw null;
                                }
                            }
                        }
                        else {
                            this.lastServerTime = lastServerTime;
                            this.lastServerTicks = integratedServer.getTickCounter();
                            this.avgServerTickDiff = 1.0f;
                            this.avgServerTimeDiff = 50.0f;
                            "".length();
                            if (2 >= 4) {
                                throw null;
                            }
                        }
                    }
                    else {
                        if (this.mc.currentScreen instanceof GuiDownloadTerrain) {
                            Config.sleep(20L);
                        }
                        this.lastServerTime = 0L;
                        this.lastServerTicks = "".length();
                        "".length();
                        if (3 >= 4) {
                            throw null;
                        }
                    }
                }
            }
        }
        else {
            this.lastServerTime = 0L;
            this.lastServerTicks = "".length();
        }
    }
    
    private void loadShader(final ResourceLocation resourceLocation) {
        try {
            (this.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), resourceLocation)).createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
            this.useShader = (" ".length() != 0);
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        catch (IOException ex) {
            EntityRenderer.logger.warn(EntityRenderer.I[0xBF ^ 0xA0] + resourceLocation, (Throwable)ex);
            this.shaderIndex = EntityRenderer.shaderCount;
            this.useShader = ("".length() != 0);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        catch (JsonSyntaxException ex2) {
            EntityRenderer.logger.warn(EntityRenderer.I[0xE3 ^ 0xC3] + resourceLocation, (Throwable)ex2);
            this.shaderIndex = EntityRenderer.shaderCount;
            this.useShader = ("".length() != 0);
        }
    }
    
    private static void I() {
        (I = new String[0xDA ^ 0x88])["".length()] = I("\u0014\u0005\u0014RWgy{[S`", "WIKbg");
        EntityRenderer.I[" ".length()] = I("&\u0011/;> \u0011$`.<\u0002>=$<\u00192!?}\u00066&%|\u00049(", "RtWOK");
        EntityRenderer.I["  ".length()] = I("\u0007,\t\u000e\u0010\u0001,\u0002U\u0000\u001d?\u0018\b\n\u001d$\u0014\u0014\u0011\\:\u001f\u0015\u0012]9\u001f\u001d", "sIqze");
        EntityRenderer.I["   ".length()] = I("\n 2<\u0014\u000b;|(\u001e\n<|6\u001e\r+;v\u001b\n'=", "yHSXq");
        EntityRenderer.I[0x6F ^ 0x6B] = I("\u0010\t\n,\u000f\u0011\u0012D8\u0005\u0010\u0015D.\u0012\u0002\u0000E\"\u0019\f\u000f", "cakHj");
        EntityRenderer.I[0x8 ^ 0xD] = I("\n\u0001\r\u00033\u000b\u001aC\u00179\n\u001dC\u0006$\rG\u0006\u00149\u0017", "yilgV");
        EntityRenderer.I[0x70 ^ 0x76] = I("\u00021;+\u0013\u0003*u?\u0019\u0002-u-\u0003\u001c)#a\u001c\u000264", "qYZOv");
        EntityRenderer.I[0x3F ^ 0x38] = I("2+\u0019\u0016\u001330W\u0002\u001927W\u0010\u001a.!\u000b@X+0\u0017\u001c", "ACxrv");
        EntityRenderer.I[0x27 ^ 0x2F] = I("\n:\u00154\u0002\u000b![ \b\n&[ \u0002\u00171\u001d<I\u0013!\u001b>", "yRtPg");
        EntityRenderer.I[0x3A ^ 0x33] = I("\u0014&&><\u0015=h*6\u0014:h96\u000b!5\u0005:\b 155\u0011+i0*\b ", "gNGZY");
        EntityRenderer.I[0x68 ^ 0x62] = I("\u0005!6\u0017\u0015\u0004:x\u0003\u001f\u0005=x\u0017\u0015\u0015&9\u0005\u0015\u0004.2]\u001a\u0005&9", "vIWsp");
        EntityRenderer.I[0x99 ^ 0x92] = I("*\r ,\u0016+\u0016n8\u001c*\u0011n.\u001f0\u0015o\"\u00006\u000b", "YeAHs");
        EntityRenderer.I[0x86 ^ 0x8A] = I("?/4\u001e\u0014>4z\n\u001e?3z\u0013\u001f:\"'\u000e_&4:\u0014", "LGUzq");
        EntityRenderer.I[0x7A ^ 0x77] = I("?\n\u0002\u0005\u0015>\u0011L\u0011\u001f?\u0016L\u000f\u0004?\u0001M\u000b\u0003#\f", "Lbcap");
        EntityRenderer.I[0x85 ^ 0x8B] = I("5.\u0011\u001e\u001045_\n\u001a52_\u0015\u00002*\u0019\u0014\u0010h,\u0003\u0015\u001b", "FFpzu");
        EntityRenderer.I[0xAB ^ 0xA4] = I("\u0006221(\u0007)|%\"\u0006.|%%\u001a)#=\"\u0007t9&\"\u001b", "uZSUM");
        EntityRenderer.I[0x28 ^ 0x38] = I("\u0006\u0012\u000e\u0006#\u0007\t@\u0012)\u0006\u000e@\u0011%\u0014\u00140\u0012/\u001b\u0019\u001a\u0011.\u001c\u0015\u0001L,\u0006\u0015\u0001", "uzobF");
        EntityRenderer.I[0xB9 ^ 0xA8] = I(">\u001f 7!?\u0004n#+>\u0003n +/\u0012-}.>\u0018/", "MwASD");
        EntityRenderer.I[0x95 ^ 0x87] = I("\u001b$ 0\u0010\u001a?n$\u001a\u001b8n6\u001c\u001c?o>\u0006\u0007\"", "hLATu");
        EntityRenderer.I[0x6F ^ 0x7C] = I("\u0012)\u0011\u001d2\u00132_\t8\u00125_\u001d2\u0012 \u0004\f%\u00005\u0015W=\u0012.\u001e", "aApyW");
        EntityRenderer.I[0x61 ^ 0x75] = I("9\u0005)5\u00008\u001eg!\n9\u0019g6\u0017/\b&\u007f\u000f9\u0002&", "JmHQe");
        EntityRenderer.I[0x5D ^ 0x48] = I("\u0000\u000f\u0002/*\u0001\u0014L; \u0000\u0013L)#\u0006\u0015M!<\u001c\t", "sgcKO");
        EntityRenderer.I[0x53 ^ 0x45] = I("2\f&#*3\u0017h7 2\u0010h0 #\u0006+\"a+\u0017()", "AdGGO");
        EntityRenderer.I[0xC ^ 0x1B] = I("\n=\u0017&\u001f\u000b&Y2\u0015\n!Y \u0016\u00167\u0005l\u0010\n:\u0018", "yUvBz");
        EntityRenderer.I[0x72 ^ 0x6A] = I("\u001e92\u0000'\u001f\"|\u0014-\u001e%|\u0005,\u001982\b+\f\"}\u000e1\u0002?", "mQSdB");
        EntityRenderer.I[0x15 ^ 0xC] = I("?\u001c$>\u0014>\u0007j*\u001e?\u0000j9\u0003)\u00115?\u0003b\u001e65\u001f", "LtEZq");
        EntityRenderer.I[0x7E ^ 0x64] = I("><3%4?'}1>> }2!$073\u007f''=/", "MTRAQ");
        EntityRenderer.I[0x12 ^ 0x9] = I("5>>2\u0000\u00146)", "YWYZt");
        EntityRenderer.I[0x54 ^ 0x48] = I("4\u001f\t \u00135\u0004G4\u00194\u0003G'\u0004\"\u0012\u0018!\u0004i\u001d\u001b+\u0018", "GwhDv");
        EntityRenderer.I[0x97 ^ 0x8A] = I("4\f7\u001305\u0017y\u0007:4\u0010y\u0004%.\u00003\u0005{-\u00179\u0019", "GdVwU");
        EntityRenderer.I[0x29 ^ 0x37] = I(";:\u000b\u00007:!E\u0014=;&E\r<>7\u0018\u0010|\"!\u0005\n", "HRjdR");
        EntityRenderer.I[0xDD ^ 0xC2] = I("\u001e$\u0006\u001e\u000f<e\u001b\u001dJ4*\u000e\u0016J+-\u000e\u0016\u000f*\u007fO", "XEorj");
        EntityRenderer.I[0x48 ^ 0x68] = I("47/\u0015(\u0016v2\u0016m\u001e9'\u001dm\u0001>'\u001d(\u0000lf", "rVFyM");
        EntityRenderer.I[0x42 ^ 0x63] = I("3$\u0004 ", "CMgKs");
        EntityRenderer.I[0x3C ^ 0x1E] = I("\u001a\"\u00171\u0005\".\b", "vKpYq");
        EntityRenderer.I[0xE ^ 0x2D] = I("!\u001d?1\u001d", "LrJBx");
        EntityRenderer.I[0x0 ^ 0x24] = I("(4&3\u0000", "DQPVl");
        EntityRenderer.I[0xA ^ 0x2F] = I("\f\u001c\u0007", "kinhb");
        EntityRenderer.I[0x5A ^ 0x7C] = I("\u001b\u001d\u001f\u0015\u001d;\u0011\u001f\u0016X:\u001b\u0003\u0014\u001d'", "Ixqqx");
        EntityRenderer.I[0xBA ^ 0x9D] = I("9\u00168\u00062\u0004U8\u00069\u000e\u00108C3\u000f\u0001+\n;\u0019", "juJcW");
        EntityRenderer.I[0x36 ^ 0x1E] = I("\u001f\u0001#6\u0006\"B?2\u000e)", "LbQSc");
        EntityRenderer.I[0x49 ^ 0x60] = I("\u001b\r\u0019\u00103v\u000e\u0003\u00007\"\u000b\u0003\r", "VblcV");
        EntityRenderer.I[0x7B ^ 0x51] = I("\u00009\u0014\u000b\u0006=z\u0015\u0007\u00196", "SZfnc");
        EntityRenderer.I[0xAB ^ 0x80] = I("\u000f\u0016*\u00104\u001e", "lsDdQ");
        EntityRenderer.I[0xA7 ^ 0x8B] = I(",\u001e-\u0012\u0001", "OrHss");
        EntityRenderer.I[0xA ^ 0x27] = I("!0\u001b\r\u0001#", "BQvhs");
        EntityRenderer.I[0xBD ^ 0x93] = I("\r\u0006\u0002\u0018\u001d\u001e\u0019", "ktwki");
        EntityRenderer.I[0x5B ^ 0x74] = I("\u0017;=\u0000#\u001a)", "tNQlJ");
        EntityRenderer.I[0xBB ^ 0x8B] = I("*: ", "YQYZI");
        EntityRenderer.I[0xAA ^ 0x9B] = I("\u0003\u0004)\u0007\u0006\u0001\u00138\u0012\u0015\u0001\u0017%\u0019", "svLwg");
        EntityRenderer.I[0x7C ^ 0x4E] = I("\r!\u0006\u0017;\u0010*+\u0016?\r1\u0004", "yDteZ");
        EntityRenderer.I[0x59 ^ 0x6A] = I(">$\r2\u0004.7\u0001&\u001e '", "KTiSp");
        EntityRenderer.I[0x3B ^ 0xF] = I("8'\u0014?\u0003%,", "LBfMb");
        EntityRenderer.I[0xD ^ 0x38] = I("\u0015*\u0003!!\u0019!\u0004", "pDwHU");
        EntityRenderer.I[0x7B ^ 0x4D] = I("&\u0018,)\u001b'\b", "ImXEr");
        EntityRenderer.I[0x74 ^ 0x43] = I("(,-8\u0019)<", "GYYTp");
        EntityRenderer.I[0x4F ^ 0x77] = I("!.\u001b,\u001f*28*\u0002\"9\r+\u001e", "EKhXm");
        EntityRenderer.I[0x8A ^ 0xB3] = I("\u0000\u0018\u001b\u0019\t\u001e\u0005\u0006*\u0004\t\u0002", "lqoIh");
        EntityRenderer.I[0xB1 ^ 0x8B] = I("$\u0010\u001c:;7\u001d\u000b=", "TqnNR");
        EntityRenderer.I[0xBB ^ 0x80] = I("&\"2:$45", "QGSNL");
        EntityRenderer.I[0xFC ^ 0xC0] = I("\u0002&5\u001b\u001d\u001a!7\u0010\u0000\u0002", "vTTun");
        EntityRenderer.I[0x79 ^ 0x44] = I("\u0012\f\u001e\u00050\u001e\u0007\u0019", "wbjlD");
        EntityRenderer.I[0x75 ^ 0x4B] = I("\u0005\n\u000b4\u001c'\u0004\u000b7\u001d\u0017", "dhdBy");
        EntityRenderer.I[0x91 ^ 0xAE] = I("4\u0001>6\u0014\r\u001c)?\u00157\u001c\u0013=\u0010!\u001a", "RnLQq");
        EntityRenderer.I[0xEF ^ 0xAF] = I("\u000e1\"\f", "fPLhe");
        EntityRenderer.I[0x4D ^ 0xC] = I("/<\u001a\u0017\f?", "LPubh");
        EntityRenderer.I[0x12 ^ 0x50] = I("& -\u0018\u000f)9a\u0006\u000f&9'\u0014\u0018i?.\u0018\u0004", "GMOqj");
        EntityRenderer.I[0x5E ^ 0x1D] = I("$>\u00058#+'I&#$'\u000f44k!\u00068(", "ESgQF");
        EntityRenderer.I[0xE1 ^ 0xA5] = I("\u000f\u00053\u0018", "GAlMv");
        EntityRenderer.I[0xDB ^ 0x9E] = I("=40,", "upoyi");
        EntityRenderer.I[0x74 ^ 0x32] = I("\u00073e'\u0016;\u0005$", "OwErz");
        EntityRenderer.I[0x6E ^ 0x29] = I("-", "aWRtg");
        EntityRenderer.I[0xCE ^ 0x86] = I("=\u00136?\f", "qzQWx");
        EntityRenderer.I[0x1B ^ 0x52] = I("K", "krndF");
        EntityRenderer.I[0xDC ^ 0x96] = I("\u0005x\u0018\u00125d\u00ff\u001382010\u001e,!\u00ff\u0010W4!*\u0005\u001e-*x\u001f\u0004b%.\u0017\u001e.%:\u001a\u0012xd\u00ff\u0013", "DXvwB");
        EntityRenderer.I[0x22 ^ 0x69] = I("\u00e4\u0007", "CaPHs");
        EntityRenderer.I[0xC3 ^ 0x8F] = I("\u001e7\u001cg4&6I.94,\b+;g\u00ff\fqcj:\u00003w\r9\u001f&\u00f0!x\u001d(w.6\n52&+\fg'\"*\u000f(%*9\u0007$2", "GXiGW");
        EntityRenderer.I[0xD1 ^ 0x9C] = I("\u00cf\u0015*64\u00067)f\u0014\u001a\u0002\n4\u00f6\u000eJE", "hpeFQ");
        EntityRenderer.I[0xCE ^ 0x80] = I("c^", "CvhFi");
        EntityRenderer.I[0x5A ^ 0x15] = I("n", "Gcrbp");
        EntityRenderer.I[0xDC ^ 0x8C] = I("\t+\u0019\u001d\u0001a(\u0000\u001f\f).\b\u0014Ta\u0005\u0019\u0019\u0011\u0007#\u0007\bY", "AJimx");
        EntityRenderer.I[0x32 ^ 0x63] = I("\u0018\u0006\u0006\u00142p\u0005\u001f\u0016?8\u0003\u0017\u001dgp\u0014\u0006Rzd\u001fW", "PgvdK");
    }
    
    public void renderWorld(final float n, final long n2) {
        this.updateLightmap(n);
        if (this.mc.getRenderViewEntity() == null) {
            this.mc.setRenderViewEntity(this.mc.thePlayer);
        }
        this.getMouseOver(n);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(423 + 423 - 434 + 104, 0.1f);
        this.mc.mcProfiler.startSection(EntityRenderer.I[0x9B ^ 0xB0]);
        if (this.mc.gameSettings.anaglyph) {
            EntityRenderer.anaglyphField = "".length();
            GlStateManager.colorMask("".length() != 0, " ".length() != 0, " ".length() != 0, "".length() != 0);
            this.renderWorldPass("".length(), n, n2);
            EntityRenderer.anaglyphField = " ".length();
            GlStateManager.colorMask(" ".length() != 0, "".length() != 0, "".length() != 0, "".length() != 0);
            this.renderWorldPass(" ".length(), n, n2);
            GlStateManager.colorMask(" ".length() != 0, " ".length() != 0, " ".length() != 0, "".length() != 0);
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        else {
            this.renderWorldPass("  ".length(), n, n2);
        }
        this.mc.mcProfiler.endSection();
    }
    
    private void setupViewBobbing(final float n) {
        if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
            final EntityPlayer entityPlayer = (EntityPlayer)this.mc.getRenderViewEntity();
            final float n2 = -(entityPlayer.distanceWalkedModified + (entityPlayer.distanceWalkedModified - entityPlayer.prevDistanceWalkedModified) * n);
            final float n3 = entityPlayer.prevCameraYaw + (entityPlayer.cameraYaw - entityPlayer.prevCameraYaw) * n;
            final float n4 = entityPlayer.prevCameraPitch + (entityPlayer.cameraPitch - entityPlayer.prevCameraPitch) * n;
            GlStateManager.translate(MathHelper.sin(n2 * 3.1415927f) * n3 * 0.5f, -Math.abs(MathHelper.cos(n2 * 3.1415927f) * n3), 0.0f);
            GlStateManager.rotate(MathHelper.sin(n2 * 3.1415927f) * n3 * 3.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(Math.abs(MathHelper.cos(n2 * 3.1415927f - 0.2f) * n3) * 5.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(n4, 1.0f, 0.0f, 0.0f);
        }
    }
    
    private void hurtCameraEffect(final float n) {
        if (this.mc.getRenderViewEntity() instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)this.mc.getRenderViewEntity();
            final float n2 = entityLivingBase.hurtTime - n;
            if (entityLivingBase.getHealth() <= 0.0f) {
                GlStateManager.rotate(40.0f - 8000.0f / (entityLivingBase.deathTime + n + 200.0f), 0.0f, 0.0f, 1.0f);
            }
            if (n2 < 0.0f) {
                return;
            }
            final float n3 = n2 / entityLivingBase.maxHurtTime;
            final float sin = MathHelper.sin(n3 * n3 * n3 * n3 * 3.1415927f);
            final float attackedAtYaw = entityLivingBase.attackedAtYaw;
            GlStateManager.rotate(-attackedAtYaw, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-sin * 14.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(attackedAtYaw, 0.0f, 1.0f, 0.0f);
        }
    }
    
    public void getMouseOver(final float n) {
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        if (renderViewEntity != null && this.mc.theWorld != null) {
            this.mc.mcProfiler.startSection(EntityRenderer.I[0xAE ^ 0x8F]);
            this.mc.pointedEntity = null;
            final double n2 = this.mc.playerController.getBlockReachDistance();
            this.mc.objectMouseOver = renderViewEntity.rayTrace(n2, n);
            double distanceTo = n2;
            final Vec3 positionEyes = renderViewEntity.getPositionEyes(n);
            int n3 = "".length();
            " ".length();
            double n4;
            if (this.mc.playerController.extendedReach()) {
                n4 = 6.0;
                distanceTo = 6.0;
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            else {
                if (n2 > 3.0) {
                    n3 = " ".length();
                }
                n4 = n2;
            }
            if (this.mc.objectMouseOver != null) {
                distanceTo = this.mc.objectMouseOver.hitVec.distanceTo(positionEyes);
            }
            final Vec3 look = renderViewEntity.getLook(n);
            final Vec3 addVector = positionEyes.addVector(look.xCoord * n4, look.yCoord * n4, look.zCoord * n4);
            this.pointedEntity = null;
            Vec3 vec3 = null;
            final float n5 = 1.0f;
            final List<Entity> entitiesInAABBexcluding = this.mc.theWorld.getEntitiesInAABBexcluding(renderViewEntity, renderViewEntity.getEntityBoundingBox().addCoord(look.xCoord * n4, look.yCoord * n4, look.zCoord * n4).expand(n5, n5, n5), (Predicate<? super Entity>)Predicates.and((Predicate)EntitySelectors.NOT_SPECTATING, (Predicate)new EntityRenderer1(this)));
            double n6 = distanceTo;
            int i = "".length();
            "".length();
            if (0 >= 1) {
                throw null;
            }
            while (i < entitiesInAABBexcluding.size()) {
                final Entity pointedEntity = entitiesInAABBexcluding.get(i);
                final float collisionBorderSize = pointedEntity.getCollisionBorderSize();
                final AxisAlignedBB expand = pointedEntity.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                final MovingObjectPosition calculateIntercept = expand.calculateIntercept(positionEyes, addVector);
                if (expand.isVecInside(positionEyes)) {
                    if (n6 >= 0.0) {
                        this.pointedEntity = pointedEntity;
                        Vec3 hitVec;
                        if (calculateIntercept == null) {
                            hitVec = positionEyes;
                            "".length();
                            if (1 == 3) {
                                throw null;
                            }
                        }
                        else {
                            hitVec = calculateIntercept.hitVec;
                        }
                        vec3 = hitVec;
                        n6 = 0.0;
                        "".length();
                        if (2 == -1) {
                            throw null;
                        }
                    }
                }
                else if (calculateIntercept != null) {
                    final double distanceTo2 = positionEyes.distanceTo(calculateIntercept.hitVec);
                    if (distanceTo2 < n6 || n6 == 0.0) {
                        int n7 = "".length();
                        if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                            n7 = (Reflector.callBoolean(pointedEntity, Reflector.ForgeEntity_canRiderInteract, new Object["".length()]) ? 1 : 0);
                        }
                        if (pointedEntity == renderViewEntity.ridingEntity && n7 == 0) {
                            if (n6 == 0.0) {
                                this.pointedEntity = pointedEntity;
                                vec3 = calculateIntercept.hitVec;
                                "".length();
                                if (4 < 1) {
                                    throw null;
                                }
                            }
                        }
                        else {
                            this.pointedEntity = pointedEntity;
                            vec3 = calculateIntercept.hitVec;
                            n6 = distanceTo2;
                        }
                    }
                }
                ++i;
            }
            if (this.pointedEntity != null && n3 != 0 && positionEyes.distanceTo(vec3) > 3.0) {
                this.pointedEntity = null;
                this.mc.objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec3, null, new BlockPos(vec3));
            }
            if (this.pointedEntity != null && (n6 < distanceTo || this.mc.objectMouseOver == null)) {
                this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, vec3);
                if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
                    this.mc.pointedEntity = this.pointedEntity;
                }
            }
            this.mc.mcProfiler.endSection();
        }
    }
    
    public MapItemRenderer getMapItemRenderer() {
        return this.theMapItemRenderer;
    }
    
    private void updateFovModifierHand() {
        float fovModifier = 1.0f;
        if (this.mc.getRenderViewEntity() instanceof AbstractClientPlayer) {
            fovModifier = ((AbstractClientPlayer)this.mc.getRenderViewEntity()).getFovModifier();
        }
        this.fovModifierHandPrev = this.fovModifierHand;
        this.fovModifierHand += (fovModifier - this.fovModifierHand) * 0.5f;
        if (this.fovModifierHand > 1.5f) {
            this.fovModifierHand = 1.5f;
        }
        if (this.fovModifierHand < 0.1f) {
            this.fovModifierHand = 0.1f;
        }
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
        }
        this.theShaderGroup = null;
        if (this.shaderIndex != EntityRenderer.shaderCount) {
            this.loadShader(EntityRenderer.shaderResourceLocations[this.shaderIndex]);
            "".length();
            if (-1 == 3) {
                throw null;
            }
        }
        else {
            this.loadEntityShader(this.mc.getRenderViewEntity());
        }
    }
    
    public void setupOverlayRendering() {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        GlStateManager.clear(107 + 249 - 146 + 46);
        GlStateManager.matrixMode(2540 + 1125 + 686 + 1538);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0, scaledResolution.getScaledWidth_double(), scaledResolution.getScaledHeight_double(), 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(1597 + 3317 - 4699 + 5673);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0f, 0.0f, -2000.0f);
    }
    
    public void func_181560_a(final float smoothCamPartialTicks, final long n) {
        this.frameInit();
        final boolean active = Display.isActive();
        if (!active && this.mc.gameSettings.pauseOnLostFocus && (!this.mc.gameSettings.touchscreen || !Mouse.isButtonDown(" ".length()))) {
            if (Minecraft.getSystemTime() - this.prevFrameTime > 500L) {
                this.mc.displayInGameMenu();
                "".length();
                if (false) {
                    throw null;
                }
            }
        }
        else {
            this.prevFrameTime = Minecraft.getSystemTime();
        }
        this.mc.mcProfiler.startSection(EntityRenderer.I[0x80 ^ 0xA3]);
        if (active && Minecraft.isRunningOnMac && this.mc.inGameHasFocus && !Mouse.isInsideWindow()) {
            Mouse.setGrabbed((boolean)("".length() != 0));
            Mouse.setCursorPosition(Display.getWidth() / "  ".length(), Display.getHeight() / "  ".length());
            Mouse.setGrabbed((boolean)(" ".length() != 0));
        }
        if (this.mc.inGameHasFocus && active) {
            this.mc.mouseHelper.mouseXYChange();
            final float n2 = this.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            final float n3 = n2 * n2 * n2 * 8.0f;
            final float n4 = this.mc.mouseHelper.deltaX * n3;
            final float n5 = this.mc.mouseHelper.deltaY * n3;
            int length = " ".length();
            if (this.mc.gameSettings.invertMouse) {
                length = -" ".length();
            }
            if (this.mc.gameSettings.smoothCamera) {
                this.smoothCamYaw += n4;
                this.smoothCamPitch += n5;
                final float n6 = smoothCamPartialTicks - this.smoothCamPartialTicks;
                this.smoothCamPartialTicks = smoothCamPartialTicks;
                this.mc.thePlayer.setAngles(this.smoothCamFilterX * n6, this.smoothCamFilterY * n6 * length);
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
            else {
                this.smoothCamYaw = 0.0f;
                this.smoothCamPitch = 0.0f;
                this.mc.thePlayer.setAngles(n4, n5 * length);
            }
        }
        this.mc.mcProfiler.endSection();
        if (!this.mc.skipRenderWorld) {
            EntityRenderer.anaglyphEnable = this.mc.gameSettings.anaglyph;
            final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
            final int scaledWidth = scaledResolution.getScaledWidth();
            final int scaledHeight = scaledResolution.getScaledHeight();
            final int n7 = Mouse.getX() * scaledWidth / this.mc.displayWidth;
            final int n8 = scaledHeight - Mouse.getY() * scaledHeight / this.mc.displayHeight - " ".length();
            final int limitFramerate = this.mc.gameSettings.limitFramerate;
            if (this.mc.theWorld != null) {
                this.mc.mcProfiler.startSection(EntityRenderer.I[0x72 ^ 0x56]);
                this.renderWorld(smoothCamPartialTicks, System.nanoTime() + Math.max((940097157 + 160526045 - 935334329 + 834711127) / Math.max(Math.min(Minecraft.getDebugFPS(), limitFramerate), 0x63 ^ 0x5F) / (0xC6 ^ 0xC2) - (System.nanoTime() - n), 0L));
                if (OpenGlHelper.shadersSupported) {
                    this.mc.renderGlobal.renderEntityOutlineFramebuffer();
                    if (this.theShaderGroup != null && this.useShader) {
                        GlStateManager.matrixMode(1342 + 3679 - 4270 + 5139);
                        GlStateManager.pushMatrix();
                        GlStateManager.loadIdentity();
                        this.theShaderGroup.loadShaderGroup(smoothCamPartialTicks);
                        GlStateManager.popMatrix();
                    }
                    this.mc.getFramebuffer().bindFramebuffer(" ".length() != 0);
                }
                this.renderEndNanoTime = System.nanoTime();
                this.mc.mcProfiler.endStartSection(EntityRenderer.I[0x8D ^ 0xA8]);
                if (!this.mc.gameSettings.hideGUI || this.mc.currentScreen != null) {
                    GlStateManager.alphaFunc(74 + 68 + 123 + 251, 0.1f);
                    this.mc.ingameGUI.renderGameOverlay(smoothCamPartialTicks);
                    if (this.mc.gameSettings.ofShowFps && !this.mc.gameSettings.showDebugInfo) {
                        Config.drawFps();
                    }
                    if (this.mc.gameSettings.showDebugInfo) {
                        Lagometer.showLagometer(scaledResolution);
                    }
                }
                this.mc.mcProfiler.endSection();
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            else {
                GlStateManager.viewport("".length(), "".length(), this.mc.displayWidth, this.mc.displayHeight);
                GlStateManager.matrixMode(3670 + 1299 + 205 + 715);
                GlStateManager.loadIdentity();
                GlStateManager.matrixMode(5000 + 1561 - 3856 + 3183);
                GlStateManager.loadIdentity();
                this.setupOverlayRendering();
                this.renderEndNanoTime = System.nanoTime();
            }
            if (this.mc.currentScreen != null) {
                GlStateManager.clear(150 + 114 - 141 + 133);
                try {
                    if (Reflector.ForgeHooksClient_drawScreen.exists()) {
                        final ReflectorMethod forgeHooksClient_drawScreen = Reflector.ForgeHooksClient_drawScreen;
                        final Object[] array = new Object[0x1 ^ 0x5];
                        array["".length()] = this.mc.currentScreen;
                        array[" ".length()] = n7;
                        array["  ".length()] = n8;
                        array["   ".length()] = smoothCamPartialTicks;
                        Reflector.callVoid(forgeHooksClient_drawScreen, array);
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                    }
                    else {
                        this.mc.currentScreen.drawScreen(n7, n8, smoothCamPartialTicks);
                        "".length();
                        if (1 < 0) {
                            throw null;
                        }
                    }
                }
                catch (Throwable t) {
                    final CrashReport crashReport = CrashReport.makeCrashReport(t, EntityRenderer.I[0x36 ^ 0x10]);
                    final CrashReportCategory category = crashReport.makeCategory(EntityRenderer.I[0x38 ^ 0x1F]);
                    category.addCrashSectionCallable(EntityRenderer.I[0x97 ^ 0xBF], new EntityRenderer2(this));
                    category.addCrashSectionCallable(EntityRenderer.I[0x72 ^ 0x5B], new Callable(this, n7, n8) {
                        final EntityRenderer this$0;
                        private final int val$j1;
                        private static final String[] I;
                        private static final String __OBFID;
                        private final int val$k1;
                        
                        private static String I(final String s, final String s2) {
                            final StringBuilder sb = new StringBuilder();
                            final char[] charArray = s2.toCharArray();
                            int length = "".length();
                            final char[] charArray2 = s.toCharArray();
                            final int length2 = charArray2.length;
                            int i = "".length();
                            while (i < length2) {
                                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                                ++length;
                                ++i;
                                "".length();
                                if (-1 < -1) {
                                    throw null;
                                }
                            }
                            return sb.toString();
                        }
                        
                        @Override
                        public String call() throws Exception {
                            final String s = EntityRenderer$1.I["".length()];
                            final Object[] array = new Object[0x97 ^ 0x93];
                            array["".length()] = this.val$j1;
                            array[" ".length()] = this.val$k1;
                            array["  ".length()] = Mouse.getX();
                            array["   ".length()] = Mouse.getY();
                            return String.format(s, array);
                        }
                        
                        static {
                            I();
                            __OBFID = EntityRenderer$1.I[" ".length()];
                        }
                        
                        private static void I() {
                            (I = new String["  ".length()])["".length()] = I("\u001b/,\u000e&,vmJf,`mG'abm#!;#!\u00177-vmJf,`mG'a", "HLMbC");
                            EntityRenderer$1.I[" ".length()] = I("\u0013;(U^`GG\\[`", "Pwwen");
                        }
                        
                        @Override
                        public Object call() throws Exception {
                            return this.call();
                        }
                    });
                    category.addCrashSectionCallable(EntityRenderer.I[0x78 ^ 0x52], new Callable(this, scaledResolution) {
                        private static final String __OBFID;
                        private final ScaledResolution val$scaledresolution;
                        private static final String[] I;
                        final EntityRenderer this$0;
                        
                        @Override
                        public Object call() throws Exception {
                            return this.call();
                        }
                        
                        private static void I() {
                            (I = new String["  ".length()])["".length()] = I("))\u0015\r\u0013\u001epTIS\u001efTD\u0012SdT \u0014\t%\u0018\u0014\u0002\u001fpTIS\u001efTD\u0012SdT2\u0015\u001b&\u0011A\u0010\u001b)\u0000\u000e\u0004Z%\u0012AS\u001e", "zJtav");
                            EntityRenderer$2.I[" ".length()] = I("/\u00039QT\\\u007fVXQ]", "lOfad");
                        }
                        
                        private static String I(final String s, final String s2) {
                            final StringBuilder sb = new StringBuilder();
                            final char[] charArray = s2.toCharArray();
                            int length = "".length();
                            final char[] charArray2 = s.toCharArray();
                            final int length2 = charArray2.length;
                            int i = "".length();
                            while (i < length2) {
                                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                                ++length;
                                ++i;
                                "".length();
                                if (2 == 0) {
                                    throw null;
                                }
                            }
                            return sb.toString();
                        }
                        
                        static {
                            I();
                            __OBFID = EntityRenderer$2.I[" ".length()];
                        }
                        
                        @Override
                        public String call() throws Exception {
                            final String s = EntityRenderer$2.I["".length()];
                            final Object[] array = new Object[0x30 ^ 0x35];
                            array["".length()] = this.val$scaledresolution.getScaledWidth();
                            array[" ".length()] = this.val$scaledresolution.getScaledHeight();
                            array["  ".length()] = EntityRenderer.access$0(this.this$0).displayWidth;
                            array["   ".length()] = EntityRenderer.access$0(this.this$0).displayHeight;
                            array[0x29 ^ 0x2D] = this.val$scaledresolution.getScaleFactor();
                            return String.format(s, array);
                        }
                    });
                    throw new ReportedException(crashReport);
                }
            }
        }
        this.frameFinish();
        this.waitForServerThread();
        Lagometer.updateLagometer();
        if (this.mc.gameSettings.ofProfiler) {
            this.mc.gameSettings.showDebugProfilerChart = (" ".length() != 0);
        }
    }
    
    private void addRainParticles() {
        float rainStrength = this.mc.theWorld.getRainStrength(1.0f);
        if (!Config.isRainFancy()) {
            rainStrength /= 2.0f;
        }
        if (rainStrength != 0.0f && Config.isRainSplash()) {
            this.random.setSeed(this.rendererUpdateCount * 312987231L);
            final Entity renderViewEntity = this.mc.getRenderViewEntity();
            final WorldClient theWorld = this.mc.theWorld;
            final BlockPos blockPos = new BlockPos(renderViewEntity);
            final int n = 0xA1 ^ 0xAB;
            double n2 = 0.0;
            double n3 = 0.0;
            double n4 = 0.0;
            int length = "".length();
            int length2 = (int)(100.0f * rainStrength * rainStrength);
            if (this.mc.gameSettings.particleSetting == " ".length()) {
                length2 >>= " ".length();
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else if (this.mc.gameSettings.particleSetting == "  ".length()) {
                length2 = "".length();
            }
            int i = "".length();
            "".length();
            if (3 <= 1) {
                throw null;
            }
            while (i < length2) {
                final BlockPos precipitationHeight = theWorld.getPrecipitationHeight(blockPos.add(this.random.nextInt(n) - this.random.nextInt(n), "".length(), this.random.nextInt(n) - this.random.nextInt(n)));
                final BiomeGenBase biomeGenForCoords = theWorld.getBiomeGenForCoords(precipitationHeight);
                final BlockPos down = precipitationHeight.down();
                final Block block = theWorld.getBlockState(down).getBlock();
                if (precipitationHeight.getY() <= blockPos.getY() + n && precipitationHeight.getY() >= blockPos.getY() - n && biomeGenForCoords.canSpawnLightningBolt() && biomeGenForCoords.getFloatTemperature(precipitationHeight) >= 0.15f) {
                    final double nextDouble = this.random.nextDouble();
                    final double nextDouble2 = this.random.nextDouble();
                    if (block.getMaterial() == Material.lava) {
                        this.mc.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, precipitationHeight.getX() + nextDouble, precipitationHeight.getY() + 0.1f - block.getBlockBoundsMinY(), precipitationHeight.getZ() + nextDouble2, 0.0, 0.0, 0.0, new int["".length()]);
                        "".length();
                        if (2 < 2) {
                            throw null;
                        }
                    }
                    else if (block.getMaterial() != Material.air) {
                        block.setBlockBoundsBasedOnState(theWorld, down);
                        ++length;
                        if (this.random.nextInt(length) == 0) {
                            n2 = down.getX() + nextDouble;
                            n3 = down.getY() + 0.1f + block.getBlockBoundsMaxY() - 1.0;
                            n4 = down.getZ() + nextDouble2;
                        }
                        this.mc.theWorld.spawnParticle(EnumParticleTypes.WATER_DROP, down.getX() + nextDouble, down.getY() + 0.1f + block.getBlockBoundsMaxY(), down.getZ() + nextDouble2, 0.0, 0.0, 0.0, new int["".length()]);
                    }
                }
                ++i;
            }
            if (length > 0) {
                final int nextInt = this.random.nextInt("   ".length());
                final int rainSoundCounter = this.rainSoundCounter;
                this.rainSoundCounter = rainSoundCounter + " ".length();
                if (nextInt < rainSoundCounter) {
                    this.rainSoundCounter = "".length();
                    if (n3 > blockPos.getY() + " ".length() && theWorld.getPrecipitationHeight(blockPos).getY() > MathHelper.floor_float(blockPos.getY())) {
                        this.mc.theWorld.playSound(n2, n3, n4, EntityRenderer.I[0x76 ^ 0x34], 0.1f, 0.5f, "".length() != 0);
                        "".length();
                        if (-1 >= 3) {
                            throw null;
                        }
                    }
                    else {
                        this.mc.theWorld.playSound(n2, n3, n4, EntityRenderer.I[0xF2 ^ 0xB1], 0.2f, 1.0f, "".length() != 0);
                    }
                }
            }
        }
    }
    
    private void setupFog(final int n, final float n2) {
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        "".length();
        this.fogStandard = ("".length() != 0);
        if (renderViewEntity instanceof EntityPlayer) {
            final boolean isCreativeMode = ((EntityPlayer)renderViewEntity).capabilities.isCreativeMode;
        }
        GL11.glFog(497 + 1029 + 1138 + 254, this.setFogColorBuffer(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0f));
        GL11.glNormal3f(0.0f, -1.0f, 0.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final Block blockAtEntityViewpoint = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, renderViewEntity, n2);
        final ReflectorConstructor entityViewRenderEvent_FogDensity_Constructor = Reflector.EntityViewRenderEvent_FogDensity_Constructor;
        final Object[] array = new Object[0x91 ^ 0x94];
        array["".length()] = this;
        array[" ".length()] = renderViewEntity;
        array["  ".length()] = blockAtEntityViewpoint;
        array["   ".length()] = n2;
        array[0x3A ^ 0x3E] = 0.1f;
        final Object instance = Reflector.newInstance(entityViewRenderEvent_FogDensity_Constructor, array);
        if (Reflector.postForgeBusEvent(instance)) {
            GL11.glFogf(2236 + 1604 - 1695 + 769, Reflector.getFieldValueFloat(instance, Reflector.EntityViewRenderEvent_FogDensity_density, 0.0f));
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else if (renderViewEntity instanceof EntityLivingBase && ((EntityPlayer)renderViewEntity).isPotionActive(Potion.blindness)) {
            float fogEnd = 5.0f;
            final int duration = ((EntityPlayer)renderViewEntity).getActivePotionEffect(Potion.blindness).getDuration();
            if (duration < (0x67 ^ 0x73)) {
                fogEnd = 5.0f + (this.farPlaneDistance - 5.0f) * (1.0f - duration / 20.0f);
            }
            GlStateManager.setFog(821 + 8643 - 4855 + 5120);
            if (n == -" ".length()) {
                GlStateManager.setFogStart(0.0f);
                GlStateManager.setFogEnd(fogEnd * 0.8f);
                "".length();
                if (0 == -1) {
                    throw null;
                }
            }
            else {
                GlStateManager.setFogStart(fogEnd * 0.25f);
                GlStateManager.setFogEnd(fogEnd);
            }
            if (GLContext.getCapabilities().GL_NV_fog_distance && Config.isFogFancy()) {
                GL11.glFogi(15121 + 20136 - 6305 + 5186, 19437 + 20508 - 20603 + 14797);
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
        }
        else if (this.cloudFog) {
            GlStateManager.setFog(2 + 1091 + 505 + 450);
            GlStateManager.setFogDensity(0.1f);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (blockAtEntityViewpoint.getMaterial() == Material.water) {
            GlStateManager.setFog(1139 + 1522 - 2337 + 1724);
            if (renderViewEntity instanceof EntityLivingBase && ((EntityPlayer)renderViewEntity).isPotionActive(Potion.waterBreathing)) {
                GlStateManager.setFogDensity(0.01f);
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            else {
                GlStateManager.setFogDensity(0.1f - EnchantmentHelper.getRespiration(renderViewEntity) * 0.03f);
            }
            if (Config.isClearWater()) {
                GL11.glFogf(1298 + 1505 - 811 + 922, 0.02f);
                "".length();
                if (2 == 1) {
                    throw null;
                }
            }
        }
        else if (blockAtEntityViewpoint.getMaterial() == Material.lava) {
            GlStateManager.setFog(1184 + 571 - 761 + 1054);
            GlStateManager.setFogDensity(2.0f);
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else {
            final float farPlaneDistance = this.farPlaneDistance;
            this.fogStandard = (" ".length() != 0);
            GlStateManager.setFog(2384 + 9554 - 3253 + 1044);
            if (n == -" ".length()) {
                GlStateManager.setFogStart(0.0f);
                GlStateManager.setFogEnd(farPlaneDistance);
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else {
                GlStateManager.setFogStart(farPlaneDistance * Config.getFogStart());
                GlStateManager.setFogEnd(farPlaneDistance);
            }
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                if (Config.isFogFancy()) {
                    GL11.glFogi(33565 + 6937 - 13116 + 6752, 15920 + 25928 - 10929 + 3220);
                }
                if (Config.isFogFast()) {
                    GL11.glFogi(9518 + 15016 - 13729 + 23333, 12210 + 14946 + 5035 + 1949);
                }
            }
            if (this.mc.theWorld.provider.doesXZShowFog((int)renderViewEntity.posX, (int)renderViewEntity.posZ)) {
                GlStateManager.setFogStart(farPlaneDistance * 0.05f);
                GlStateManager.setFogEnd(farPlaneDistance);
            }
            if (Reflector.ForgeHooksClient_onFogRender.exists()) {
                final ReflectorMethod forgeHooksClient_onFogRender = Reflector.ForgeHooksClient_onFogRender;
                final Object[] array2 = new Object[0x1B ^ 0x1D];
                array2["".length()] = this;
                array2[" ".length()] = renderViewEntity;
                array2["  ".length()] = blockAtEntityViewpoint;
                array2["   ".length()] = n2;
                array2[0x1F ^ 0x1B] = n;
                array2[0x5E ^ 0x5B] = farPlaneDistance;
                Reflector.callVoid(forgeHooksClient_onFogRender, array2);
            }
        }
        GlStateManager.enableColorMaterial();
        GlStateManager.enableFog();
        GlStateManager.colorMaterial(410 + 453 + 7 + 158, 1396 + 2964 - 1330 + 1578);
    }
}
