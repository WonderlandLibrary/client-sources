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
    public static @Nullable CompoundTag loadNBT(String name) {
        CompoundTag compoundTag;
        block9: {
            InputStream resource = VBMappingDataLoader.getResource(name);
            if (resource == null) {
                return null;
            }
            InputStream stream = resource;
            try {
                compoundTag = NBTIO.readTag(stream);
                if (stream == null) break block9;
            } catch (Throwable throwable) {
                try {
                    if (stream != null) {
                        try {
                            stream.close();
                        } catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            stream.close();
        }
        return compoundTag;
    }

    public static @Nullable CompoundTag loadNBTFromDir(String name) {
        CompoundTag packedData = VBMappingDataLoader.loadNBT(name);
        File file = new File(ViaBackwards.getPlatform().getDataFolder(), name);
        if (!file.exists()) {
            return packedData;
        }
        ViaBackwards.getPlatform().getLogger().info("Loading " + name + " from plugin folder");
        try {
            CompoundTag fileData = NBTIO.readFile(file, false, false);
            return VBMappingDataLoader.mergeTags(packedData, fileData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static CompoundTag mergeTags(CompoundTag original, CompoundTag extra) {
        for (Map.Entry<String, Tag> entry : extra.entrySet()) {
            CompoundTag originalEntry;
            if (entry.getValue() instanceof CompoundTag && (originalEntry = (CompoundTag)original.get(entry.getKey())) != null) {
                VBMappingDataLoader.mergeTags(originalEntry, (CompoundTag)entry.getValue());
                continue;
            }
            original.put(entry.getKey(), entry.getValue());
        }
        return original;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static JsonObject loadData(String name) {
        try (InputStream stream = VBMappingDataLoader.getResource(name);){
            if (stream == null) {
                JsonObject jsonObject2 = null;
                return jsonObject2;
            }
            JsonObject jsonObject = GsonUtil.getGson().fromJson((Reader)new InputStreamReader(stream), JsonObject.class);
            return jsonObject;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonObject loadFromDataDir(String name) {
        JsonObject jsonObject;
        File file = new File(ViaBackwards.getPlatform().getDataFolder(), name);
        if (!file.exists()) {
            return VBMappingDataLoader.loadData(name);
        }
        FileReader reader = new FileReader(file);
        try {
            jsonObject = GsonUtil.getGson().fromJson((Reader)reader, JsonObject.class);
        } catch (Throwable throwable) {
            try {
                try {
                    reader.close();
                } catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            } catch (JsonSyntaxException e) {
                ViaBackwards.getPlatform().getLogger().warning(name + " is badly formatted!");
                e.printStackTrace();
                ViaBackwards.getPlatform().getLogger().warning("Falling back to resource's file!");
                return VBMappingDataLoader.loadData(name);
            } catch (JsonIOException | IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        reader.close();
        return jsonObject;
    }

    public static @Nullable InputStream getResource(String name) {
        return VBMappingDataLoader.class.getClassLoader().getResourceAsStream("assets/viabackwards/data/" + name);
    }
}

