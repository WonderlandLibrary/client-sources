/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.advancements.AdventureAdvancements;
import net.minecraft.data.advancements.EndAdvancements;
import net.minecraft.data.advancements.HusbandryAdvancements;
import net.minecraft.data.advancements.NetherAdvancements;
import net.minecraft.data.advancements.StoryAdvancements;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancementProvider
implements IDataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator generator;
    private final List<Consumer<Consumer<Advancement>>> advancements = ImmutableList.of(new EndAdvancements(), new HusbandryAdvancements(), new AdventureAdvancements(), new NetherAdvancements(), new StoryAdvancements());

    public AdvancementProvider(DataGenerator dataGenerator) {
        this.generator = dataGenerator;
    }

    @Override
    public void act(DirectoryCache directoryCache) throws IOException {
        Path path = this.generator.getOutputFolder();
        HashSet hashSet = Sets.newHashSet();
        Consumer<Advancement> consumer = arg_0 -> AdvancementProvider.lambda$act$0(hashSet, path, directoryCache, arg_0);
        for (Consumer<Consumer<Advancement>> consumer2 : this.advancements) {
            consumer2.accept(consumer);
        }
    }

    private static Path getPath(Path path, Advancement advancement) {
        return path.resolve("data/" + advancement.getId().getNamespace() + "/advancements/" + advancement.getId().getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Advancements";
    }

    private static void lambda$act$0(Set set, Path path, DirectoryCache directoryCache, Advancement advancement) {
        if (!set.add(advancement.getId())) {
            throw new IllegalStateException("Duplicate advancement " + advancement.getId());
        }
        Path path2 = AdvancementProvider.getPath(path, advancement);
        try {
            IDataProvider.save(GSON, directoryCache, advancement.copy().serialize(), path2);
        } catch (IOException iOException) {
            LOGGER.error("Couldn't save advancement {}", (Object)path2, (Object)iOException);
        }
    }
}

