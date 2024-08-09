/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.uniform;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.optifine.Config;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.uniform.ShaderUniform1f;
import net.optifine.shaders.uniform.ShaderUniform1i;
import net.optifine.shaders.uniform.ShaderUniform2f;
import net.optifine.shaders.uniform.ShaderUniform2i;
import net.optifine.shaders.uniform.ShaderUniform3f;
import net.optifine.shaders.uniform.ShaderUniform4f;
import net.optifine.shaders.uniform.ShaderUniformBase;
import net.optifine.shaders.uniform.ShaderUniformM4;
import net.optifine.util.BiomeUtils;

public enum ShaderParameterFloat {
    BIOME("biome"),
    BIOME_CATEGORY("biome_category"),
    BIOME_PRECIPITATION("biome_precipitation"),
    TEMPERATURE("temperature"),
    RAINFALL("rainfall"),
    HELD_ITEM_ID(Shaders.uniform_heldItemId),
    HELD_BLOCK_LIGHT_VALUE(Shaders.uniform_heldBlockLightValue),
    HELD_ITEM_ID2(Shaders.uniform_heldItemId2),
    HELD_BLOCK_LIGHT_VALUE2(Shaders.uniform_heldBlockLightValue2),
    WORLD_TIME(Shaders.uniform_worldTime),
    WORLD_DAY(Shaders.uniform_worldDay),
    MOON_PHASE(Shaders.uniform_moonPhase),
    FRAME_COUNTER(Shaders.uniform_frameCounter),
    FRAME_TIME(Shaders.uniform_frameTime),
    FRAME_TIME_COUNTER(Shaders.uniform_frameTimeCounter),
    SUN_ANGLE(Shaders.uniform_sunAngle),
    SHADOW_ANGLE(Shaders.uniform_shadowAngle),
    RAIN_STRENGTH(Shaders.uniform_rainStrength),
    ASPECT_RATIO(Shaders.uniform_aspectRatio),
    VIEW_WIDTH(Shaders.uniform_viewWidth),
    VIEW_HEIGHT(Shaders.uniform_viewHeight),
    NEAR(Shaders.uniform_near),
    FAR(Shaders.uniform_far),
    WETNESS(Shaders.uniform_wetness),
    EYE_ALTITUDE(Shaders.uniform_eyeAltitude),
    EYE_BRIGHTNESS(Shaders.uniform_eyeBrightness, new String[]{"x", "y"}),
    TERRAIN_TEXTURE_SIZE(Shaders.uniform_terrainTextureSize, new String[]{"x", "y"}),
    TERRRAIN_ICON_SIZE(Shaders.uniform_terrainIconSize),
    IS_EYE_IN_WATER(Shaders.uniform_isEyeInWater),
    NIGHT_VISION(Shaders.uniform_nightVision),
    BLINDNESS(Shaders.uniform_blindness),
    SCREEN_BRIGHTNESS(Shaders.uniform_screenBrightness),
    HIDE_GUI(Shaders.uniform_hideGUI),
    CENTER_DEPT_SMOOTH(Shaders.uniform_centerDepthSmooth),
    ATLAS_SIZE(Shaders.uniform_atlasSize, new String[]{"x", "y"}),
    PLAYER_MOOD(Shaders.uniform_playerMood),
    CAMERA_POSITION(Shaders.uniform_cameraPosition, new String[]{"x", "y", "z"}),
    PREVIOUS_CAMERA_POSITION(Shaders.uniform_previousCameraPosition, new String[]{"x", "y", "z"}),
    SUN_POSITION(Shaders.uniform_sunPosition, new String[]{"x", "y", "z"}),
    MOON_POSITION(Shaders.uniform_moonPosition, new String[]{"x", "y", "z"}),
    SHADOW_LIGHT_POSITION(Shaders.uniform_shadowLightPosition, new String[]{"x", "y", "z"}),
    UP_POSITION(Shaders.uniform_upPosition, new String[]{"x", "y", "z"}),
    SKY_COLOR(Shaders.uniform_skyColor, new String[]{"r", "g", "b"}),
    GBUFFER_PROJECTION(Shaders.uniform_gbufferProjection, new String[]{"0", "1", "2", "3"}, new String[]{"0", "1", "2", "3"}),
    GBUFFER_PROJECTION_INVERSE(Shaders.uniform_gbufferProjectionInverse, new String[]{"0", "1", "2", "3"}, new String[]{"0", "1", "2", "3"}),
    GBUFFER_PREVIOUS_PROJECTION(Shaders.uniform_gbufferPreviousProjection, new String[]{"0", "1", "2", "3"}, new String[]{"0", "1", "2", "3"}),
    GBUFFER_MODEL_VIEW(Shaders.uniform_gbufferModelView, new String[]{"0", "1", "2", "3"}, new String[]{"0", "1", "2", "3"}),
    GBUFFER_MODEL_VIEW_INVERSE(Shaders.uniform_gbufferModelViewInverse, new String[]{"0", "1", "2", "3"}, new String[]{"0", "1", "2", "3"}),
    GBUFFER_PREVIOUS_MODEL_VIEW(Shaders.uniform_gbufferPreviousModelView, new String[]{"0", "1", "2", "3"}, new String[]{"0", "1", "2", "3"}),
    SHADOW_PROJECTION(Shaders.uniform_shadowProjection, new String[]{"0", "1", "2", "3"}, new String[]{"0", "1", "2", "3"}),
    SHADOW_PROJECTION_INVERSE(Shaders.uniform_shadowProjectionInverse, new String[]{"0", "1", "2", "3"}, new String[]{"0", "1", "2", "3"}),
    SHADOW_MODEL_VIEW(Shaders.uniform_shadowModelView, new String[]{"0", "1", "2", "3"}, new String[]{"0", "1", "2", "3"}),
    SHADOW_MODEL_VIEW_INVERSE(Shaders.uniform_shadowModelViewInverse, new String[]{"0", "1", "2", "3"}, new String[]{"0", "1", "2", "3"});

    private String name;
    private ShaderUniformBase uniform;
    private String[] indexNames1;
    private String[] indexNames2;

    private ShaderParameterFloat(String string2) {
        this.name = string2;
    }

    private ShaderParameterFloat(ShaderUniformBase shaderUniformBase) {
        this.name = shaderUniformBase.getName();
        this.uniform = shaderUniformBase;
        if (!ShaderParameterFloat.instanceOf(shaderUniformBase, ShaderUniform1f.class, ShaderUniform1i.class)) {
            throw new IllegalArgumentException("Invalid uniform type for enum: " + this + ", uniform: " + shaderUniformBase.getClass().getName());
        }
    }

    private ShaderParameterFloat(ShaderUniformBase shaderUniformBase, String[] stringArray) {
        this.name = shaderUniformBase.getName();
        this.uniform = shaderUniformBase;
        this.indexNames1 = stringArray;
        if (!ShaderParameterFloat.instanceOf(shaderUniformBase, ShaderUniform2i.class, ShaderUniform2f.class, ShaderUniform3f.class, ShaderUniform4f.class)) {
            throw new IllegalArgumentException("Invalid uniform type for enum: " + this + ", uniform: " + shaderUniformBase.getClass().getName());
        }
    }

    private ShaderParameterFloat(ShaderUniformBase shaderUniformBase, String[] stringArray, String[] stringArray2) {
        this.name = shaderUniformBase.getName();
        this.uniform = shaderUniformBase;
        this.indexNames1 = stringArray;
        this.indexNames2 = stringArray2;
        if (!ShaderParameterFloat.instanceOf(shaderUniformBase, ShaderUniformM4.class)) {
            throw new IllegalArgumentException("Invalid uniform type for enum: " + this + ", uniform: " + shaderUniformBase.getClass().getName());
        }
    }

    public String getName() {
        return this.name;
    }

    public ShaderUniformBase getUniform() {
        return this.uniform;
    }

    public String[] getIndexNames1() {
        return this.indexNames1;
    }

    public String[] getIndexNames2() {
        return this.indexNames2;
    }

    public float eval(int n, int n2) {
        if (this.indexNames1 == null || n >= 0 && n <= this.indexNames1.length) {
            if (this.indexNames2 == null || n2 >= 0 && n2 <= this.indexNames2.length) {
                switch (1.$SwitchMap$net$optifine$shaders$uniform$ShaderParameterFloat[this.ordinal()]) {
                    case 1: {
                        BlockPos blockPos = Shaders.getWorldCameraPosition();
                        Biome biome = Shaders.getCurrentWorld().getBiome(blockPos);
                        return BiomeUtils.getId(biome);
                    }
                    case 2: {
                        BlockPos blockPos = Shaders.getWorldCameraPosition();
                        Biome biome = Shaders.getCurrentWorld().getBiome(blockPos);
                        return biome != null ? (float)biome.getCategory().ordinal() : 0.0f;
                    }
                    case 3: {
                        BlockPos blockPos = Shaders.getWorldCameraPosition();
                        Biome biome = Shaders.getCurrentWorld().getBiome(blockPos);
                        return biome != null ? (float)biome.getPrecipitation().ordinal() : 0.0f;
                    }
                    case 4: {
                        BlockPos blockPos = Shaders.getWorldCameraPosition();
                        Biome biome = Shaders.getCurrentWorld().getBiome(blockPos);
                        return biome != null ? biome.getTemperature(blockPos) : 0.0f;
                    }
                    case 5: {
                        BlockPos blockPos = Shaders.getWorldCameraPosition();
                        Biome biome = Shaders.getCurrentWorld().getBiome(blockPos);
                        return biome != null ? biome.getDownfall() : 0.0f;
                    }
                }
                if (this.uniform instanceof ShaderUniform1f) {
                    return ((ShaderUniform1f)this.uniform).getValue();
                }
                if (this.uniform instanceof ShaderUniform1i) {
                    return ((ShaderUniform1i)this.uniform).getValue();
                }
                if (this.uniform instanceof ShaderUniform2i) {
                    return ((ShaderUniform2i)this.uniform).getValue()[n];
                }
                if (this.uniform instanceof ShaderUniform2f) {
                    return ((ShaderUniform2f)this.uniform).getValue()[n];
                }
                if (this.uniform instanceof ShaderUniform3f) {
                    return ((ShaderUniform3f)this.uniform).getValue()[n];
                }
                if (this.uniform instanceof ShaderUniform4f) {
                    return ((ShaderUniform4f)this.uniform).getValue()[n];
                }
                if (this.uniform instanceof ShaderUniformM4) {
                    return ((ShaderUniformM4)this.uniform).getValue(n, n2);
                }
                throw new IllegalArgumentException("Unknown uniform type: " + this);
            }
            Config.warn("Invalid index2, parameter: " + this + ", index: " + n2);
            return 0.0f;
        }
        Config.warn("Invalid index1, parameter: " + this + ", index: " + n);
        return 0.0f;
    }

    private static boolean instanceOf(Object object, Class ... classArray) {
        if (object == null) {
            return true;
        }
        Class<?> clazz = object.getClass();
        for (int i = 0; i < classArray.length; ++i) {
            Class clazz2 = classArray[i];
            if (!clazz2.isAssignableFrom(clazz)) continue;
            return false;
        }
        return true;
    }
}

