/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.data;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.opennbt.NBTIO;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class VBMappingDataLoader {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static @Nullable CompoundTag loadNBT(String string) {
        InputStream inputStream = VBMappingDataLoader.getResource(string);
        if (inputStream == null) {
            return null;
        }
        try (InputStream inputStream2 = inputStream;){
            CompoundTag compoundTag = NBTIO.readTag(inputStream2);
            return compoundTag;
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    public static @Nullable CompoundTag loadNBTFromDir(String string) {
        CompoundTag compoundTag = VBMappingDataLoader.loadNBT(string);
        File file = new File(ViaBackwards.getPlatform().getDataFolder(), string);
        if (!file.exists()) {
            return compoundTag;
        }
        ViaBackwards.getPlatform().getLogger().info("Loading " + string + " from plugin folder");
        try {
            CompoundTag compoundTag2 = NBTIO.readFile(file, false, false);
            return VBMappingDataLoader.mergeTags(compoundTag, compoundTag2);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static CompoundTag mergeTags(CompoundTag compoundTag, CompoundTag compoundTag2) {
        for (Map.Entry<String, Tag> entry : compoundTag2.entrySet()) {
            CompoundTag compoundTag3;
            if (entry.getValue() instanceof CompoundTag && (compoundTag3 = (CompoundTag)compoundTag.get(entry.getKey())) != null) {
                VBMappingDataLoader.mergeTags(compoundTag3, (CompoundTag)entry.getValue());
                continue;
            }
            compoundTag.put(entry.getKey(), entry.getValue());
        }
        return compoundTag;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static JsonObject loadData(String string) {
        try (InputStream inputStream = VBMappingDataLoader.getResource(string);){
            if (inputStream == null) {
                JsonObject jsonObject = null;
                return jsonObject;
            }
            JsonObject jsonObject = GsonUtil.getGson().fromJson((Reader)new InputStreamReader(inputStream), JsonObject.class);
            return jsonObject;
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static JsonObject loadFromDataDir(String string) {
        File file = new File(ViaBackwards.getPlatform().getDataFolder(), string);
        if (!file.exists()) {
            return VBMappingDataLoader.loadData(string);
        }
        try (FileReader fileReader = new FileReader(file);){
            JsonObject jsonObject = GsonUtil.getGson().fromJson((Reader)fileReader, JsonObject.class);
            return jsonObject;
        } catch (JsonSyntaxException jsonSyntaxException) {
            ViaBackwards.getPlatform().getLogger().warning(string + " is badly formatted!");
            jsonSyntaxException.printStackTrace();
            ViaBackwards.getPlatform().getLogger().warning("Falling back to resource's file!");
            return VBMappingDataLoader.loadData(string);
        } catch (JsonIOException | IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static @Nullable InputStream getResource(String string) {
        return VBMappingDataLoader.class.getClassLoader().getResourceAsStream("assets/viabackwards/data/" + string);
    }
}

