/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.stats;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.server.SStatisticsPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DefaultTypeReferences;
import net.minecraft.util.registry.Registry;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerStatisticsManager
extends StatisticsManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private final MinecraftServer server;
    private final File statsFile;
    private final Set<Stat<?>> dirty = Sets.newHashSet();
    private int lastStatRequest = -300;

    public ServerStatisticsManager(MinecraftServer minecraftServer, File file) {
        this.server = minecraftServer;
        this.statsFile = file;
        if (file.isFile()) {
            try {
                this.parseLocal(minecraftServer.getDataFixer(), FileUtils.readFileToString(file));
            } catch (IOException iOException) {
                LOGGER.error("Couldn't read statistics file {}", (Object)file, (Object)iOException);
            } catch (JsonParseException jsonParseException) {
                LOGGER.error("Couldn't parse statistics file {}", (Object)file, (Object)jsonParseException);
            }
        }
    }

    public void saveStatFile() {
        try {
            FileUtils.writeStringToFile(this.statsFile, this.func_199061_b());
        } catch (IOException iOException) {
            LOGGER.error("Couldn't save stats", (Throwable)iOException);
        }
    }

    @Override
    public void setValue(PlayerEntity playerEntity, Stat<?> stat, int n) {
        super.setValue(playerEntity, stat, n);
        this.dirty.add(stat);
    }

    private Set<Stat<?>> getDirty() {
        HashSet<Stat<?>> hashSet = Sets.newHashSet(this.dirty);
        this.dirty.clear();
        return hashSet;
    }

    public void parseLocal(DataFixer dataFixer, String string) {
        try (JsonReader jsonReader = new JsonReader(new StringReader(string));){
            jsonReader.setLenient(true);
            JsonElement jsonElement = Streams.parse(jsonReader);
            if (jsonElement.isJsonNull()) {
                LOGGER.error("Unable to parse Stat data from {}", (Object)this.statsFile);
                return;
            }
            CompoundNBT compoundNBT = ServerStatisticsManager.func_199065_a(jsonElement.getAsJsonObject());
            if (!compoundNBT.contains("DataVersion", 0)) {
                compoundNBT.putInt("DataVersion", 1343);
            }
            if ((compoundNBT = NBTUtil.update(dataFixer, DefaultTypeReferences.STATS, compoundNBT, compoundNBT.getInt("DataVersion"))).contains("stats", 1)) {
                CompoundNBT compoundNBT2 = compoundNBT.getCompound("stats");
                for (String string2 : compoundNBT2.keySet()) {
                    if (!compoundNBT2.contains(string2, 1)) continue;
                    Util.acceptOrElse(Registry.STATS.getOptional(new ResourceLocation(string2)), arg_0 -> this.lambda$parseLocal$2(compoundNBT2, string2, arg_0), () -> this.lambda$parseLocal$3(string2));
                }
            }
        } catch (JsonParseException | IOException exception) {
            LOGGER.error("Unable to parse Stat data from {}", (Object)this.statsFile, (Object)exception);
        }
    }

    private <T> Optional<Stat<T>> func_219728_a(StatType<T> statType, String string) {
        return Optional.ofNullable(ResourceLocation.tryCreate(string)).flatMap(statType.getRegistry()::getOptional).map(statType::get);
    }

    private static CompoundNBT func_199065_a(JsonObject jsonObject) {
        CompoundNBT compoundNBT = new CompoundNBT();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            JsonPrimitive jsonPrimitive;
            JsonElement jsonElement = entry.getValue();
            if (jsonElement.isJsonObject()) {
                compoundNBT.put(entry.getKey(), ServerStatisticsManager.func_199065_a(jsonElement.getAsJsonObject()));
                continue;
            }
            if (!jsonElement.isJsonPrimitive() || !(jsonPrimitive = jsonElement.getAsJsonPrimitive()).isNumber()) continue;
            compoundNBT.putInt(entry.getKey(), jsonPrimitive.getAsInt());
        }
        return compoundNBT;
    }

    protected String func_199061_b() {
        HashMap<StatType, JsonObject> hashMap = Maps.newHashMap();
        for (Object2IntMap.Entry object2 : this.statsData.object2IntEntrySet()) {
            Stat stat = (Stat)object2.getKey();
            hashMap.computeIfAbsent(stat.getType(), ServerStatisticsManager::lambda$func_199061_b$4).addProperty(ServerStatisticsManager.func_199066_b(stat).toString(), object2.getIntValue());
        }
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry entry : hashMap.entrySet()) {
            jsonObject.add(Registry.STATS.getKey((StatType)entry.getKey()).toString(), (JsonElement)entry.getValue());
        }
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.add("stats", jsonObject);
        jsonObject2.addProperty("DataVersion", SharedConstants.getVersion().getWorldVersion());
        return jsonObject2.toString();
    }

    private static <T> ResourceLocation func_199066_b(Stat<T> stat) {
        return stat.getType().getRegistry().getKey(stat.getValue());
    }

    public void markAllDirty() {
        this.dirty.addAll(this.statsData.keySet());
    }

    public void sendStats(ServerPlayerEntity serverPlayerEntity) {
        int n = this.server.getTickCounter();
        Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
        if (n - this.lastStatRequest > 300) {
            this.lastStatRequest = n;
            for (Stat<?> stat : this.getDirty()) {
                object2IntOpenHashMap.put(stat, this.getValue(stat));
            }
        }
        serverPlayerEntity.connection.sendPacket(new SStatisticsPacket(object2IntOpenHashMap));
    }

    private static JsonObject lambda$func_199061_b$4(StatType statType) {
        return new JsonObject();
    }

    private void lambda$parseLocal$3(String string) {
        LOGGER.warn("Invalid statistic type in {}: Don't know what {} is", (Object)this.statsFile, (Object)string);
    }

    private void lambda$parseLocal$2(CompoundNBT compoundNBT, String string, StatType statType) {
        CompoundNBT compoundNBT2 = compoundNBT.getCompound(string);
        for (String string2 : compoundNBT2.keySet()) {
            if (compoundNBT2.contains(string2, 0)) {
                Util.acceptOrElse(this.func_219728_a(statType, string2), arg_0 -> this.lambda$parseLocal$0(compoundNBT2, string2, arg_0), () -> this.lambda$parseLocal$1(string2));
                continue;
            }
            LOGGER.warn("Invalid statistic value in {}: Don't know what {} is for key {}", (Object)this.statsFile, (Object)compoundNBT2.get(string2), (Object)string2);
        }
    }

    private void lambda$parseLocal$1(String string) {
        LOGGER.warn("Invalid statistic in {}: Don't know what {} is", (Object)this.statsFile, (Object)string);
    }

    private void lambda$parseLocal$0(CompoundNBT compoundNBT, String string, Stat stat) {
        this.statsData.put(stat, compoundNBT.getInt(string));
    }
}

