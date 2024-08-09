/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.optifine.Config;
import net.optifine.config.BiomeId;
import net.optifine.config.ConnectedParser;
import net.optifine.config.Matches;
import net.optifine.config.RangeListInt;
import net.optifine.render.Blender;
import net.optifine.util.NumUtils;
import net.optifine.util.SmoothFloat;
import net.optifine.util.TextureUtils;

public class CustomSkyLayer {
    public String source = null;
    private int startFadeIn = -1;
    private int endFadeIn = -1;
    private int startFadeOut = -1;
    private int endFadeOut = -1;
    private int blend = 1;
    private boolean rotate = false;
    private float speed = 1.0f;
    private float[] axis = DEFAULT_AXIS;
    private RangeListInt days = null;
    private int daysLoop = 8;
    private boolean weatherClear = true;
    private boolean weatherRain = false;
    private boolean weatherThunder = false;
    public BiomeId[] biomes = null;
    public RangeListInt heights = null;
    private float transition = 1.0f;
    private SmoothFloat smoothPositionBrightness = null;
    public int textureId = -1;
    private World lastWorld = null;
    public static final float[] DEFAULT_AXIS = new float[]{1.0f, 0.0f, 0.0f};
    private static final String WEATHER_CLEAR = "clear";
    private static final String WEATHER_RAIN = "rain";
    private static final String WEATHER_THUNDER = "thunder";

    public CustomSkyLayer(Properties properties, String string) {
        ConnectedParser connectedParser = new ConnectedParser("CustomSky");
        this.source = properties.getProperty("source", string);
        this.startFadeIn = this.parseTime(properties.getProperty("startFadeIn"));
        this.endFadeIn = this.parseTime(properties.getProperty("endFadeIn"));
        this.startFadeOut = this.parseTime(properties.getProperty("startFadeOut"));
        this.endFadeOut = this.parseTime(properties.getProperty("endFadeOut"));
        this.blend = Blender.parseBlend(properties.getProperty("blend"));
        this.rotate = this.parseBoolean(properties.getProperty("rotate"), false);
        this.speed = this.parseFloat(properties.getProperty("speed"), 1.0f);
        this.axis = this.parseAxis(properties.getProperty("axis"), DEFAULT_AXIS);
        this.days = connectedParser.parseRangeListInt(properties.getProperty("days"));
        this.daysLoop = connectedParser.parseInt(properties.getProperty("daysLoop"), 8);
        List<String> list = this.parseWeatherList(properties.getProperty("weather", WEATHER_CLEAR));
        this.weatherClear = list.contains(WEATHER_CLEAR);
        this.weatherRain = list.contains(WEATHER_RAIN);
        this.weatherThunder = list.contains(WEATHER_THUNDER);
        this.biomes = connectedParser.parseBiomes(properties.getProperty("biomes"));
        this.heights = connectedParser.parseRangeListInt(properties.getProperty("heights"));
        this.transition = this.parseFloat(properties.getProperty("transition"), 1.0f);
    }

    private List<String> parseWeatherList(String string) {
        List<String> list = Arrays.asList(WEATHER_CLEAR, WEATHER_RAIN, WEATHER_THUNDER);
        ArrayList<String> arrayList = new ArrayList<String>();
        String[] stringArray = Config.tokenize(string, " ");
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            if (!list.contains(string2)) {
                Config.warn("Unknown weather: " + string2);
                continue;
            }
            arrayList.add(string2);
        }
        return arrayList;
    }

    private int parseTime(String string) {
        if (string == null) {
            return 1;
        }
        String[] stringArray = Config.tokenize(string, ":");
        if (stringArray.length != 2) {
            Config.warn("Invalid time: " + string);
            return 1;
        }
        String string2 = stringArray[0];
        String string3 = stringArray[5];
        int n = Config.parseInt(string2, -1);
        int n2 = Config.parseInt(string3, -1);
        if (n >= 0 && n <= 23 && n2 >= 0 && n2 <= 59) {
            if ((n -= 6) < 0) {
                n += 24;
            }
            return n * 1000 + (int)((double)n2 / 60.0 * 1000.0);
        }
        Config.warn("Invalid time: " + string);
        return 1;
    }

    private boolean parseBoolean(String string, boolean bl) {
        if (string == null) {
            return bl;
        }
        if (string.toLowerCase().equals("true")) {
            return false;
        }
        if (string.toLowerCase().equals("false")) {
            return true;
        }
        Config.warn("Unknown boolean: " + string);
        return bl;
    }

    private float parseFloat(String string, float f) {
        if (string == null) {
            return f;
        }
        float f2 = Config.parseFloat(string, Float.MIN_VALUE);
        if (f2 == Float.MIN_VALUE) {
            Config.warn("Invalid value: " + string);
            return f;
        }
        return f2;
    }

    private float[] parseAxis(String string, float[] fArray) {
        if (string == null) {
            return fArray;
        }
        String[] stringArray = Config.tokenize(string, " ");
        if (stringArray.length != 3) {
            Config.warn("Invalid axis: " + string);
            return fArray;
        }
        float[] fArray2 = new float[3];
        for (int i = 0; i < stringArray.length; ++i) {
            fArray2[i] = Config.parseFloat(stringArray[i], Float.MIN_VALUE);
            if (fArray2[i] != Float.MIN_VALUE) continue;
            Config.warn("Invalid axis: " + string);
            return fArray;
        }
        float f = fArray2[0];
        float f2 = fArray2[1];
        float f3 = fArray2[2];
        if (f * f + f2 * f2 + f3 * f3 < 1.0E-5f) {
            Config.warn("Invalid axis values: " + string);
            return fArray;
        }
        return new float[]{f3, f2, -f};
    }

    public boolean isValid(String string) {
        if (this.source == null) {
            Config.warn("No source texture: " + string);
            return true;
        }
        this.source = TextureUtils.fixResourcePath(this.source, TextureUtils.getBasePath(string));
        if (this.startFadeIn >= 0 && this.endFadeIn >= 0 && this.endFadeOut >= 0) {
            int n;
            int n2;
            int n3;
            int n4;
            int n5 = this.normalizeTime(this.endFadeIn - this.startFadeIn);
            if (this.startFadeOut < 0) {
                this.startFadeOut = this.normalizeTime(this.endFadeOut - n5);
                if (this.timeBetween(this.startFadeOut, this.startFadeIn, this.endFadeIn)) {
                    this.startFadeOut = this.endFadeIn;
                }
            }
            if ((n4 = n5 + (n3 = this.normalizeTime(this.startFadeOut - this.endFadeIn)) + (n2 = this.normalizeTime(this.endFadeOut - this.startFadeOut)) + (n = this.normalizeTime(this.startFadeIn - this.endFadeOut))) != 24000) {
                Config.warn("Invalid fadeIn/fadeOut times, sum is not 24h: " + n4);
                return true;
            }
            if (this.speed < 0.0f) {
                Config.warn("Invalid speed: " + this.speed);
                return true;
            }
            if (this.daysLoop <= 0) {
                Config.warn("Invalid daysLoop: " + this.daysLoop);
                return true;
            }
            return false;
        }
        Config.warn("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
        return true;
    }

    private int normalizeTime(int n) {
        while (n >= 24000) {
            n -= 24000;
        }
        while (n < 0) {
            n += 24000;
        }
        return n;
    }

    public void render(World world, MatrixStack matrixStack, int n, float f, float f2, float f3) {
        float f4 = this.getPositionBrightness(world);
        float f5 = this.getWeatherBrightness(f2, f3);
        float f6 = this.getFadeBrightness(n);
        float f7 = f4 * f5 * f6;
        if (!((f7 = Config.limit(f7, 0.0f, 1.0f)) < 1.0E-4f)) {
            GlStateManager.bindTexture(this.textureId);
            Blender.setupBlend(this.blend, f7);
            GlStateManager.pushMatrix();
            GlStateManager.multMatrix(matrixStack.getLast().getMatrix());
            if (this.rotate) {
                float f8 = 0.0f;
                if (this.speed != (float)Math.round(this.speed)) {
                    long l = (world.getDayTime() + 18000L) / 24000L;
                    double d = this.speed % 1.0f;
                    double d2 = (double)l * d;
                    f8 = (float)(d2 % 1.0);
                }
                GlStateManager.rotatef(360.0f * (f8 + f * this.speed), this.axis[0], this.axis[1], this.axis[2]);
            }
            Tessellator tessellator = Tessellator.getInstance();
            GlStateManager.rotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotatef(-90.0f, 0.0f, 0.0f, 1.0f);
            this.renderSide(tessellator, 4);
            GlStateManager.pushMatrix();
            GlStateManager.rotatef(90.0f, 1.0f, 0.0f, 0.0f);
            this.renderSide(tessellator, 1);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.rotatef(-90.0f, 1.0f, 0.0f, 0.0f);
            this.renderSide(tessellator, 0);
            GlStateManager.popMatrix();
            GlStateManager.rotatef(90.0f, 0.0f, 0.0f, 1.0f);
            this.renderSide(tessellator, 5);
            GlStateManager.rotatef(90.0f, 0.0f, 0.0f, 1.0f);
            this.renderSide(tessellator, 2);
            GlStateManager.rotatef(90.0f, 0.0f, 0.0f, 1.0f);
            this.renderSide(tessellator, 3);
            GlStateManager.popMatrix();
        }
    }

    private float getPositionBrightness(World world) {
        if (this.biomes == null && this.heights == null) {
            return 1.0f;
        }
        float f = this.getPositionBrightnessRaw(world);
        if (this.smoothPositionBrightness == null) {
            this.smoothPositionBrightness = new SmoothFloat(f, this.transition);
        }
        return this.smoothPositionBrightness.getSmoothValue(f);
    }

    private float getPositionBrightnessRaw(World world) {
        Entity entity2 = Minecraft.getInstance().getRenderViewEntity();
        if (entity2 == null) {
            return 0.0f;
        }
        BlockPos blockPos = entity2.getPosition();
        if (this.biomes != null) {
            Biome biome = world.getBiome(blockPos);
            if (biome == null) {
                return 0.0f;
            }
            if (!Matches.biome(biome, this.biomes)) {
                return 0.0f;
            }
        }
        return this.heights != null && !this.heights.isInRange(blockPos.getY()) ? 0.0f : 1.0f;
    }

    private float getWeatherBrightness(float f, float f2) {
        float f3 = 1.0f - f;
        float f4 = f - f2;
        float f5 = 0.0f;
        if (this.weatherClear) {
            f5 += f3;
        }
        if (this.weatherRain) {
            f5 += f4;
        }
        if (this.weatherThunder) {
            f5 += f2;
        }
        return NumUtils.limit(f5, 0.0f, 1.0f);
    }

    private float getFadeBrightness(int n) {
        if (this.timeBetween(n, this.startFadeIn, this.endFadeIn)) {
            int n2 = this.normalizeTime(this.endFadeIn - this.startFadeIn);
            int n3 = this.normalizeTime(n - this.startFadeIn);
            return (float)n3 / (float)n2;
        }
        if (this.timeBetween(n, this.endFadeIn, this.startFadeOut)) {
            return 1.0f;
        }
        if (this.timeBetween(n, this.startFadeOut, this.endFadeOut)) {
            int n4 = this.normalizeTime(this.endFadeOut - this.startFadeOut);
            int n5 = this.normalizeTime(n - this.startFadeOut);
            return 1.0f - (float)n5 / (float)n4;
        }
        return 0.0f;
    }

    private void renderSide(Tessellator tessellator, int n) {
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        float f = (float)(n % 3) / 3.0f;
        float f2 = (float)(n / 3) / 2.0f;
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(-100.0, -100.0, -100.0).tex(f, f2).endVertex();
        bufferBuilder.pos(-100.0, -100.0, 100.0).tex(f, f2 + 0.5f).endVertex();
        bufferBuilder.pos(100.0, -100.0, 100.0).tex(f + 0.33333334f, f2 + 0.5f).endVertex();
        bufferBuilder.pos(100.0, -100.0, -100.0).tex(f + 0.33333334f, f2).endVertex();
        tessellator.draw();
    }

    public boolean isActive(World world, int n) {
        if (world != this.lastWorld) {
            this.lastWorld = world;
            this.smoothPositionBrightness = null;
        }
        if (this.timeBetween(n, this.endFadeOut, this.startFadeIn)) {
            return true;
        }
        if (this.days != null) {
            long l;
            long l2 = world.getDayTime();
            for (l = l2 - (long)this.startFadeIn; l < 0L; l += (long)(24000 * this.daysLoop)) {
            }
            int n2 = (int)(l / 24000L);
            int n3 = n2 % this.daysLoop;
            if (!this.days.isInRange(n3)) {
                return true;
            }
        }
        return false;
    }

    private boolean timeBetween(int n, int n2, int n3) {
        if (n2 <= n3) {
            return n >= n2 && n <= n3;
        }
        return n >= n2 || n <= n3;
    }

    public String toString() {
        return this.source + ", " + this.startFadeIn + "-" + this.endFadeIn + " " + this.startFadeOut + "-" + this.endFadeOut;
    }
}

