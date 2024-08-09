/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeProvider
implements IDataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator generator;

    public BiomeProvider(DataGenerator dataGenerator) {
        this.generator = dataGenerator;
    }

    @Override
    public void act(DirectoryCache directoryCache) {
        Path path = this.generator.getOutputFolder();
        for (Map.Entry<RegistryKey<Biome>, Biome> entry : WorldGenRegistries.BIOME.getEntries()) {
            Path path2 = BiomeProvider.getPath(path, entry.getKey().getLocation());
            Biome biome = entry.getValue();
            Function<Supplier<Biome>, DataResult<Supplier<Biome>>> function = JsonOps.INSTANCE.withEncoder(Biome.BIOME_CODEC);
            try {
                Optional optional = function.apply(() -> BiomeProvider.lambda$act$0(biome)).result();
                if (optional.isPresent()) {
                    IDataProvider.save(GSON, directoryCache, (JsonElement)optional.get(), path2);
                    continue;
                }
                LOGGER.error("Couldn't serialize biome {}", (Object)path2);
            } catch (IOException iOException) {
                LOGGER.error("Couldn't save biome {}", (Object)path2, (Object)iOException);
            }
        }
    }

    private static Path getPath(Path path, ResourceLocation resourceLocation) {
        return path.resolve("reports/biomes/" + resourceLocation.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Biomes";
    }

    private static Biome lambda$act$0(Biome biome) {
        return biome;
    }
}

