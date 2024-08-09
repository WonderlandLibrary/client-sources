/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import mpp.venusfr.functions.impl.render.World;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.RenderUtil;
import mpp.venusfr.venusfr;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;

public class FogRenderer {
    public static float red;
    public static float green;
    public static float blue;
    private static int lastWaterFogColor;
    private static int waterFogColor;
    private static long waterFogUpdateTime;
    public static boolean fogStandard;

    public static void updateFogColor(ActiveRenderInfo activeRenderInfo, float f, ClientWorld clientWorld, int n, float f2) {
        Entity entity2;
        Vector3d vector3d;
        int n2;
        FluidState fluidState = activeRenderInfo.getFluidState();
        World world = venusfr.getInstance().getFunctionRegistry().getWorld();
        if (fluidState.isTagged(FluidTags.WATER)) {
            long l = Util.milliTime();
            n2 = clientWorld.getBiome(new BlockPos(activeRenderInfo.getProjectedView())).getWaterFogColor();
            if (waterFogUpdateTime < 0L) {
                lastWaterFogColor = n2;
                waterFogColor = n2;
                waterFogUpdateTime = l;
            }
            int n3 = lastWaterFogColor >> 16 & 0xFF;
            int n4 = lastWaterFogColor >> 8 & 0xFF;
            int n5 = lastWaterFogColor & 0xFF;
            int n6 = waterFogColor >> 16 & 0xFF;
            int n7 = waterFogColor >> 8 & 0xFF;
            int n8 = waterFogColor & 0xFF;
            float f3 = MathHelper.clamp((float)(l - waterFogUpdateTime) / 5000.0f, 0.0f, 1.0f);
            float f4 = MathHelper.lerp(f3, n6, n3);
            float f5 = MathHelper.lerp(f3, n7, n4);
            float f6 = MathHelper.lerp(f3, n8, n5);
            red = f4 / 255.0f;
            green = f5 / 255.0f;
            blue = f6 / 255.0f;
            if (lastWaterFogColor != n2) {
                lastWaterFogColor = n2;
                waterFogColor = MathHelper.floor(f4) << 16 | MathHelper.floor(f5) << 8 | MathHelper.floor(f6);
                waterFogUpdateTime = l;
            }
        } else if (fluidState.isTagged(FluidTags.LAVA)) {
            red = 0.6f;
            green = 0.1f;
            blue = 0.0f;
            waterFogUpdateTime = -1L;
        } else {
            float f7;
            float f8;
            float f9 = 0.25f + 0.75f * (float)n / 32.0f;
            f9 = 1.0f - (float)Math.pow(f9, 0.25);
            Vector3d vector3d2 = clientWorld.getSkyColor(activeRenderInfo.getBlockPos(), f);
            vector3d2 = CustomColors.getWorldSkyColor(vector3d2, clientWorld, activeRenderInfo.getRenderViewEntity(), f);
            float f10 = (float)vector3d2.x;
            float f11 = (float)vector3d2.y;
            float f12 = (float)vector3d2.z;
            float f13 = MathHelper.clamp(MathHelper.cos(clientWorld.func_242415_f(f) * ((float)Math.PI * 2)) * 2.0f + 0.5f, 0.0f, 1.0f);
            BiomeManager biomeManager = clientWorld.getBiomeManager();
            Vector3d vector3d3 = activeRenderInfo.getProjectedView().subtract(2.0, 2.0, 2.0).scale(0.25);
            Vector3d vector3d4 = CubicSampler.func_240807_a_(vector3d3, (arg_0, arg_1, arg_2) -> FogRenderer.lambda$updateFogColor$0(clientWorld, biomeManager, f13, arg_0, arg_1, arg_2));
            vector3d4 = CustomColors.getWorldFogColor(vector3d4, clientWorld, activeRenderInfo.getRenderViewEntity(), f);
            if (venusfr.getInstance().getFunctionRegistry().getWorld().isState() && ((Boolean)world.getModes().getValueByName("\u0422\u0443\u043c\u0430\u043d").get()).booleanValue()) {
                float[] fArray = RenderUtil.IntColor.rgb(ColorUtils.getColor(1));
                red = fArray[0];
                green = fArray[1];
                blue = fArray[2];
            } else {
                red = (float)vector3d4.x;
                green = (float)vector3d4.y;
                blue = (float)vector3d4.z;
            }
            if (n >= 4) {
                float[] fArray;
                float f14 = MathHelper.sin(clientWorld.getCelestialAngleRadians(f)) > 0.0f ? -1.0f : 1.0f;
                Vector3f vector3f = new Vector3f(f14, 0.0f, 0.0f);
                f8 = activeRenderInfo.getViewVector().dot(vector3f);
                if (f8 < 0.0f) {
                    f8 = 0.0f;
                }
                if (f8 > 0.0f && (fArray = clientWorld.func_239132_a_().func_230492_a_(clientWorld.func_242415_f(f), f)) != null) {
                    red = red * (1.0f - (f8 *= fArray[3])) + fArray[0] * f8;
                    green = green * (1.0f - f8) + fArray[1] * f8;
                    blue = blue * (1.0f - f8) + fArray[2] * f8;
                }
            }
            red += (f10 - red) * f9;
            green += (f11 - green) * f9;
            blue += (f12 - blue) * f9;
            float f15 = clientWorld.getRainStrength(f);
            if (f15 > 0.0f) {
                float f16 = 1.0f - f15 * 0.5f;
                f8 = 1.0f - f15 * 0.4f;
                red *= f16;
                green *= f16;
                blue *= f8;
            }
            if ((f7 = clientWorld.getThunderStrength(f)) > 0.0f) {
                f8 = 1.0f - f7 * 0.5f;
                red *= f8;
                green *= f8;
                blue *= f8;
            }
            waterFogUpdateTime = -1L;
        }
        double d = activeRenderInfo.getProjectedView().y * clientWorld.getWorldInfo().getFogDistance();
        if (activeRenderInfo.getRenderViewEntity() instanceof LivingEntity && ((LivingEntity)activeRenderInfo.getRenderViewEntity()).isPotionActive(Effects.BLINDNESS)) {
            n2 = ((LivingEntity)activeRenderInfo.getRenderViewEntity()).getActivePotionEffect(Effects.BLINDNESS).getDuration();
            d = n2 < 20 ? (d *= (double)(1.0f - (float)n2 / 20.0f)) : 0.0;
        }
        if (d < 1.0 && !fluidState.isTagged(FluidTags.LAVA)) {
            if (d < 0.0) {
                d = 0.0;
            }
            d *= d;
            red = (float)((double)red * d);
            green = (float)((double)green * d);
            blue = (float)((double)blue * d);
        }
        if (f2 > 0.0f) {
            red = red * (1.0f - f2) + red * 0.7f * f2;
            green = green * (1.0f - f2) + green * 0.6f * f2;
            blue = blue * (1.0f - f2) + blue * 0.6f * f2;
        }
        if (fluidState.isTagged(FluidTags.WATER)) {
            float f17;
            float f18 = 0.0f;
            if (activeRenderInfo.getRenderViewEntity() instanceof ClientPlayerEntity) {
                ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity)activeRenderInfo.getRenderViewEntity();
                f18 = clientPlayerEntity.getWaterBrightness();
            }
            if (Float.isInfinite(f17 = Math.min(1.0f / red, Math.min(1.0f / green, 1.0f / blue)))) {
                f17 = Math.nextAfter(f17, 0.0);
            }
            red = red * (1.0f - f18) + red * f17 * f18;
            green = green * (1.0f - f18) + green * f17 * f18;
            blue = blue * (1.0f - f18) + blue * f17 * f18;
        } else if (activeRenderInfo.getRenderViewEntity() instanceof LivingEntity && ((LivingEntity)activeRenderInfo.getRenderViewEntity()).isPotionActive(Effects.NIGHT_VISION)) {
            float f19 = GameRenderer.getNightVisionBrightness((LivingEntity)activeRenderInfo.getRenderViewEntity(), f);
            float f20 = Math.min(1.0f / red, Math.min(1.0f / green, 1.0f / blue));
            if (Float.isInfinite(f20)) {
                f20 = Math.nextAfter(f20, 0.0);
            }
            red = red * (1.0f - f19) + red * f20 * f19;
            green = green * (1.0f - f19) + green * f20 * f19;
            blue = blue * (1.0f - f19) + blue * f20 * f19;
        }
        if (fluidState.isTagged(FluidTags.WATER)) {
            Entity entity3 = activeRenderInfo.getRenderViewEntity();
            Vector3d vector3d5 = CustomColors.getUnderwaterColor(clientWorld, entity3.getPosX(), entity3.getPosY() + 1.0, entity3.getPosZ());
            if (vector3d5 != null) {
                red = (float)vector3d5.x;
                green = (float)vector3d5.y;
                blue = (float)vector3d5.z;
            }
        } else if (fluidState.isTagged(FluidTags.LAVA) && (vector3d = CustomColors.getUnderlavaColor(clientWorld, (entity2 = activeRenderInfo.getRenderViewEntity()).getPosX(), entity2.getPosY() + 1.0, entity2.getPosZ())) != null) {
            red = (float)vector3d.x;
            green = (float)vector3d.y;
            blue = (float)vector3d.z;
        }
        if (Reflector.EntityViewRenderEvent_FogColors_Constructor.exists()) {
            Object object = Reflector.newInstance(Reflector.EntityViewRenderEvent_FogColors_Constructor, activeRenderInfo, Float.valueOf(f), Float.valueOf(red), Float.valueOf(green), Float.valueOf(blue));
            Reflector.postForgeBusEvent(object);
            red = Reflector.callFloat(object, Reflector.EntityViewRenderEvent_FogColors_getRed, new Object[0]);
            green = Reflector.callFloat(object, Reflector.EntityViewRenderEvent_FogColors_getGreen, new Object[0]);
            blue = Reflector.callFloat(object, Reflector.EntityViewRenderEvent_FogColors_getBlue, new Object[0]);
        }
        Shaders.setClearColor(red, green, blue, 0.0f);
        RenderSystem.clearColor(red, green, blue, 0.0f);
    }

    public static void resetFog() {
        RenderSystem.fogDensity(0.0f);
        RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
    }

    public static void setupFog(ActiveRenderInfo activeRenderInfo, FogType fogType, float f, boolean bl) {
        FogRenderer.setupFog(activeRenderInfo, fogType, f, bl, 0.0f);
    }

    public static void setupFog(ActiveRenderInfo activeRenderInfo, FogType fogType, float f, boolean bl, float f2) {
        fogStandard = false;
        World world = venusfr.getInstance().getFunctionRegistry().getWorld();
        FluidState fluidState = activeRenderInfo.getFluidState();
        Entity entity2 = activeRenderInfo.getRenderViewEntity();
        float f3 = -1.0f;
        if (Reflector.ForgeHooksClient_getFogDensity.exists()) {
            f3 = Reflector.callFloat(Reflector.ForgeHooksClient_getFogDensity, new Object[]{fogType, activeRenderInfo, Float.valueOf(f2), Float.valueOf(0.1f)});
        }
        if (f3 >= 0.0f) {
            GlStateManager.fogDensity(f3);
        } else if (fluidState.isTagged(FluidTags.WATER)) {
            float f4 = 1.0f;
            f4 = 0.05f;
            if (entity2 instanceof ClientPlayerEntity) {
                ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity)entity2;
                f4 -= clientPlayerEntity.getWaterBrightness() * clientPlayerEntity.getWaterBrightness() * 0.03f;
                Biome biome = clientPlayerEntity.world.getBiome(clientPlayerEntity.getPosition());
                if (biome.getCategory() == Biome.Category.SWAMP) {
                    f4 += 0.005f;
                }
            }
            RenderSystem.fogDensity(f4);
            RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
        } else {
            float f5;
            float f6;
            if (fluidState.isTagged(FluidTags.LAVA)) {
                if (entity2 instanceof LivingEntity && ((LivingEntity)entity2).isPotionActive(Effects.FIRE_RESISTANCE)) {
                    f6 = 0.0f;
                    f5 = 3.0f;
                } else {
                    f6 = 0.25f;
                    f5 = 1.0f;
                }
            } else if (entity2 instanceof LivingEntity && ((LivingEntity)entity2).isPotionActive(Effects.BLINDNESS)) {
                int n = ((LivingEntity)entity2).getActivePotionEffect(Effects.BLINDNESS).getDuration();
                float f7 = MathHelper.lerp(Math.min(1.0f, (float)n / 20.0f), f, 5.0f);
                if (fogType == FogType.FOG_SKY) {
                    f6 = 0.0f;
                    f5 = f7 * 0.8f;
                } else {
                    f6 = f7 * 0.25f;
                    f5 = f7;
                }
            } else if (bl) {
                fogStandard = true;
                f6 = f * 0.05f;
                f5 = Math.min(f, 192.0f) * 0.5f;
            } else if (fogType == FogType.FOG_SKY) {
                fogStandard = true;
                f6 = 0.0f;
                f5 = f;
            } else {
                fogStandard = true;
                f6 = f;
                f6 = venusfr.getInstance().getFunctionRegistry().getWorld().isState() && ((Boolean)world.getModes().getValueByName("\u0422\u0443\u043c\u0430\u043d").get()).booleanValue() ? (f6 /= ((Float)world.getDistanceFog().get()).floatValue()) : (f6 *= Config.getFogStart());
                f5 = f;
            }
            RenderSystem.fogStart(f6);
            RenderSystem.fogEnd(f5);
            RenderSystem.fogMode(GlStateManager.FogMode.LINEAR);
            RenderSystem.setupNvFogDistance();
            if (Reflector.ForgeHooksClient_onFogRender.exists()) {
                Reflector.callVoid(Reflector.ForgeHooksClient_onFogRender, new Object[]{fogType, activeRenderInfo, Float.valueOf(f2), Float.valueOf(f5)});
            }
        }
    }

    public static void applyFog() {
        RenderSystem.fog(2918, red, green, blue, 1.0f);
        if (Config.isShaders()) {
            Shaders.setFogColor(red, green, blue);
        }
    }

    private static Vector3d lambda$updateFogColor$0(ClientWorld clientWorld, BiomeManager biomeManager, float f, int n, int n2, int n3) {
        return clientWorld.func_239132_a_().func_230494_a_(Vector3d.unpack(biomeManager.getBiomeAtPosition(n, n2, n3).getFogColor()), f);
    }

    static {
        lastWaterFogColor = -1;
        waterFogColor = -1;
        waterFogUpdateTime = -1L;
        fogStandard = false;
    }

    public static enum FogType {
        FOG_SKY,
        FOG_TERRAIN;

    }
}

