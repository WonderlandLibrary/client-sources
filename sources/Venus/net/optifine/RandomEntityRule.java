/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.util.Properties;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.optifine.Config;
import net.optifine.IRandomEntity;
import net.optifine.RandomEntities;
import net.optifine.RandomEntity;
import net.optifine.config.BiomeId;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchProfession;
import net.optifine.config.Matches;
import net.optifine.config.NbtTagValue;
import net.optifine.config.RangeInt;
import net.optifine.config.RangeListInt;
import net.optifine.config.Weather;
import net.optifine.util.ArrayUtils;
import net.optifine.util.MathUtils;

public class RandomEntityRule {
    private String pathProps = null;
    private ResourceLocation baseResLoc = null;
    private int index;
    private int[] textures = null;
    private ResourceLocation[] resourceLocations = null;
    private int[] weights = null;
    private BiomeId[] biomes = null;
    private RangeListInt heights = null;
    private RangeListInt healthRange = null;
    private boolean healthPercent = false;
    private NbtTagValue nbtName = null;
    public int[] sumWeights = null;
    public int sumAllWeights = 1;
    private MatchProfession[] professions = null;
    private DyeColor[] collarColors = null;
    private Boolean baby = null;
    private RangeListInt moonPhases = null;
    private RangeListInt dayTimes = null;
    private Weather[] weatherList = null;

    public RandomEntityRule(Properties properties, String string, ResourceLocation resourceLocation, int n, String string2, ConnectedParser connectedParser) {
        String string3;
        this.pathProps = string;
        this.baseResLoc = resourceLocation;
        this.index = n;
        this.textures = connectedParser.parseIntList(string2);
        this.weights = connectedParser.parseIntList(properties.getProperty("weights." + n));
        this.biomes = connectedParser.parseBiomes(properties.getProperty("biomes." + n));
        this.heights = connectedParser.parseRangeListInt(properties.getProperty("heights." + n));
        if (this.heights == null) {
            this.heights = this.parseMinMaxHeight(properties, n);
        }
        if ((string3 = properties.getProperty("health." + n)) != null) {
            this.healthPercent = string3.contains("%");
            string3 = string3.replace("%", "");
            this.healthRange = connectedParser.parseRangeListInt(string3);
        }
        this.nbtName = connectedParser.parseNbtTagValue("name", properties.getProperty("name." + n));
        this.professions = connectedParser.parseProfessions(properties.getProperty("professions." + n));
        this.collarColors = connectedParser.parseDyeColors(properties.getProperty("collarColors." + n), "collar color", ConnectedParser.DYE_COLORS_INVALID);
        this.baby = connectedParser.parseBooleanObject(properties.getProperty("baby." + n));
        this.moonPhases = connectedParser.parseRangeListInt(properties.getProperty("moonPhase." + n));
        this.dayTimes = connectedParser.parseRangeListInt(properties.getProperty("dayTime." + n));
        this.weatherList = connectedParser.parseWeather(properties.getProperty("weather." + n), "weather." + n, null);
    }

    private RangeListInt parseMinMaxHeight(Properties properties, int n) {
        String string = properties.getProperty("minHeight." + n);
        String string2 = properties.getProperty("maxHeight." + n);
        if (string == null && string2 == null) {
            return null;
        }
        int n2 = 0;
        if (string != null && (n2 = Config.parseInt(string, -1)) < 0) {
            Config.warn("Invalid minHeight: " + string);
            return null;
        }
        int n3 = 256;
        if (string2 != null && (n3 = Config.parseInt(string2, -1)) < 0) {
            Config.warn("Invalid maxHeight: " + string2);
            return null;
        }
        if (n3 < 0) {
            Config.warn("Invalid minHeight, maxHeight: " + string + ", " + string2);
            return null;
        }
        RangeListInt rangeListInt = new RangeListInt();
        rangeListInt.addRange(new RangeInt(n2, n3));
        return rangeListInt;
    }

    public boolean isValid(String string) {
        if (this.textures != null && this.textures.length != 0) {
            int n;
            int n2;
            if (this.resourceLocations != null) {
                return false;
            }
            this.resourceLocations = new ResourceLocation[this.textures.length];
            boolean bl = this.pathProps.startsWith("optifine/mob/");
            ResourceLocation resourceLocation = RandomEntities.getLocationRandom(this.baseResLoc, bl);
            if (resourceLocation == null) {
                Config.warn("Invalid path: " + this.baseResLoc.getPath());
                return true;
            }
            for (n2 = 0; n2 < this.resourceLocations.length; ++n2) {
                n = this.textures[n2];
                if (n <= 1) {
                    this.resourceLocations[n2] = this.baseResLoc;
                    continue;
                }
                ResourceLocation resourceLocation2 = RandomEntities.getLocationIndexed(resourceLocation, n);
                if (resourceLocation2 == null) {
                    Config.warn("Invalid path: " + this.baseResLoc.getPath());
                    return true;
                }
                if (!Config.hasResource(resourceLocation2)) {
                    Config.warn("Texture not found: " + resourceLocation2.getPath());
                    return true;
                }
                this.resourceLocations[n2] = resourceLocation2;
            }
            if (this.weights != null) {
                if (this.weights.length > this.resourceLocations.length) {
                    Config.warn("More weights defined than skins, trimming weights: " + string);
                    int[] nArray = new int[this.resourceLocations.length];
                    System.arraycopy(this.weights, 0, nArray, 0, nArray.length);
                    this.weights = nArray;
                }
                if (this.weights.length < this.resourceLocations.length) {
                    Config.warn("Less weights defined than skins, expanding weights: " + string);
                    int[] nArray = new int[this.resourceLocations.length];
                    System.arraycopy(this.weights, 0, nArray, 0, this.weights.length);
                    n = MathUtils.getAverage(this.weights);
                    for (int i = this.weights.length; i < nArray.length; ++i) {
                        nArray[i] = n;
                    }
                    this.weights = nArray;
                }
                this.sumWeights = new int[this.weights.length];
                n2 = 0;
                for (n = 0; n < this.weights.length; ++n) {
                    if (this.weights[n] < 0) {
                        Config.warn("Invalid weight: " + this.weights[n]);
                        return true;
                    }
                    this.sumWeights[n] = n2 += this.weights[n];
                }
                this.sumAllWeights = n2;
                if (this.sumAllWeights <= 0) {
                    Config.warn("Invalid sum of all weights: " + n2);
                    this.sumAllWeights = 1;
                }
            }
            if (this.professions == ConnectedParser.PROFESSIONS_INVALID) {
                Config.warn("Invalid professions or careers: " + string);
                return true;
            }
            if (this.collarColors == ConnectedParser.DYE_COLORS_INVALID) {
                Config.warn("Invalid collar colors: " + string);
                return true;
            }
            return false;
        }
        Config.warn("Invalid skins for rule: " + this.index);
        return true;
    }

    public boolean matches(IRandomEntity iRandomEntity) {
        Weather weather;
        Entity entity2;
        int n;
        LivingEntity livingEntity;
        Object object;
        VillagerProfession villagerProfession;
        Entity entity3;
        int n2;
        Object object2;
        if (this.biomes != null && !Matches.biome(iRandomEntity.getSpawnBiome(), this.biomes)) {
            return true;
        }
        if (this.heights != null && (object2 = iRandomEntity.getSpawnPosition()) != null && !this.heights.isInRange(((Vector3i)object2).getY())) {
            return true;
        }
        if (this.healthRange != null) {
            int n3 = iRandomEntity.getHealth();
            if (this.healthPercent && (n2 = iRandomEntity.getMaxHealth()) > 0) {
                n3 = (int)((double)(n3 * 100) / (double)n2);
            }
            if (!this.healthRange.isInRange(n3)) {
                return true;
            }
        }
        if (this.nbtName != null && !this.nbtName.matchesValue((String)(object2 = iRandomEntity.getName()))) {
            return true;
        }
        if (this.professions != null && iRandomEntity instanceof RandomEntity && (entity3 = ((RandomEntity)(object2 = (RandomEntity)iRandomEntity)).getEntity()) instanceof VillagerEntity && !MatchProfession.matchesOne(villagerProfession = ((VillagerData)(object = ((VillagerEntity)(livingEntity = (VillagerEntity)entity3)).getVillagerData())).getProfession(), n = ((VillagerData)object).getLevel(), this.professions)) {
            return true;
        }
        if (this.collarColors != null && iRandomEntity instanceof RandomEntity) {
            object2 = (RandomEntity)iRandomEntity;
            Entity entity4 = ((RandomEntity)object2).getEntity();
            if (entity4 instanceof WolfEntity) {
                livingEntity = (WolfEntity)entity4;
                if (!((TameableEntity)livingEntity).isTamed()) {
                    return true;
                }
                object = ((WolfEntity)livingEntity).getCollarColor();
                if (!Config.equalsOne(object, this.collarColors)) {
                    return true;
                }
            }
            if (entity4 instanceof CatEntity) {
                livingEntity = (CatEntity)entity4;
                if (!((TameableEntity)livingEntity).isTamed()) {
                    return true;
                }
                object = ((CatEntity)livingEntity).getCollarColor();
                if (!Config.equalsOne(object, this.collarColors)) {
                    return true;
                }
            }
        }
        if (this.baby != null && iRandomEntity instanceof RandomEntity && (entity2 = ((RandomEntity)(object2 = (RandomEntity)iRandomEntity)).getEntity()) instanceof LivingEntity && (livingEntity = (LivingEntity)entity2).isChild() != this.baby.booleanValue()) {
            return true;
        }
        if (this.moonPhases != null && (object2 = Config.getMinecraft().world) != null && !this.moonPhases.isInRange(n2 = object2.getMoonPhase())) {
            return true;
        }
        if (this.dayTimes != null && (object2 = Config.getMinecraft().world) != null && !this.dayTimes.isInRange(n2 = (int)((World)object2).getDayTime())) {
            return true;
        }
        return this.weatherList != null && (object2 = Config.getMinecraft().world) != null && !ArrayUtils.contains((Object[])this.weatherList, (Object)(weather = Weather.getWeather((World)object2, 0.0f)));
    }

    public ResourceLocation getTextureLocation(ResourceLocation resourceLocation, int n) {
        if (this.resourceLocations != null && this.resourceLocations.length != 0) {
            int n2 = 0;
            if (this.weights == null) {
                n2 = n % this.resourceLocations.length;
            } else {
                int n3 = n % this.sumAllWeights;
                for (int i = 0; i < this.sumWeights.length; ++i) {
                    if (this.sumWeights[i] <= n3) continue;
                    n2 = i;
                    break;
                }
            }
            return this.resourceLocations[n2];
        }
        return resourceLocation;
    }
}

