/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.data.BlockModelProvider;
import net.minecraft.data.BlockModelWriter;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.IFinishedBlockState;
import net.minecraft.data.ItemModelProvider;
import net.minecraft.data.ModelsResourceUtil;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockStateProvider
implements IDataProvider {
    private static final Logger field_240078_b_ = LogManager.getLogger();
    private static final Gson field_240079_c_ = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator field_240080_d_;

    public BlockStateProvider(DataGenerator dataGenerator) {
        this.field_240080_d_ = dataGenerator;
    }

    @Override
    public void act(DirectoryCache directoryCache) {
        Path path = this.field_240080_d_.getOutputFolder();
        HashMap hashMap = Maps.newHashMap();
        Consumer<IFinishedBlockState> consumer = arg_0 -> BlockStateProvider.lambda$act$0(hashMap, arg_0);
        HashMap hashMap2 = Maps.newHashMap();
        HashSet hashSet = Sets.newHashSet();
        BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer = (arg_0, arg_1) -> BlockStateProvider.lambda$act$1(hashMap2, arg_0, arg_1);
        Consumer<Item> consumer2 = hashSet::add;
        new BlockModelProvider(consumer, biConsumer, consumer2).func_239863_a_();
        new ItemModelProvider(biConsumer).func_240074_a_();
        List list = Registry.BLOCK.stream().filter(arg_0 -> BlockStateProvider.lambda$act$2(hashMap, arg_0)).collect(Collectors.toList());
        if (!list.isEmpty()) {
            throw new IllegalStateException("Missing blockstate definitions for: " + list);
        }
        Registry.BLOCK.forEach(arg_0 -> BlockStateProvider.lambda$act$3(hashSet, hashMap2, arg_0));
        this.func_240081_a_(directoryCache, path, hashMap, BlockStateProvider::func_240082_a_);
        this.func_240081_a_(directoryCache, path, hashMap2, BlockStateProvider::func_240083_a_);
    }

    private <T> void func_240081_a_(DirectoryCache directoryCache, Path path, Map<T, ? extends Supplier<JsonElement>> map, BiFunction<Path, T, Path> biFunction) {
        map.forEach((arg_0, arg_1) -> BlockStateProvider.lambda$func_240081_a_$4(biFunction, path, directoryCache, arg_0, arg_1));
    }

    private static Path func_240082_a_(Path path, Block block) {
        ResourceLocation resourceLocation = Registry.BLOCK.getKey(block);
        return path.resolve("assets/" + resourceLocation.getNamespace() + "/blockstates/" + resourceLocation.getPath() + ".json");
    }

    private static Path func_240083_a_(Path path, ResourceLocation resourceLocation) {
        return path.resolve("assets/" + resourceLocation.getNamespace() + "/models/" + resourceLocation.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Block State Definitions";
    }

    private static void lambda$func_240081_a_$4(BiFunction biFunction, Path path, DirectoryCache directoryCache, Object object, Supplier supplier) {
        Path path2 = (Path)biFunction.apply(path, object);
        try {
            IDataProvider.save(field_240079_c_, directoryCache, (JsonElement)supplier.get(), path2);
        } catch (Exception exception) {
            field_240078_b_.error("Couldn't save {}", (Object)path2, (Object)exception);
        }
    }

    private static void lambda$act$3(Set set, Map map, Block block) {
        Item item = Item.BLOCK_TO_ITEM.get(block);
        if (item != null) {
            if (set.contains(item)) {
                return;
            }
            ResourceLocation resourceLocation = ModelsResourceUtil.func_240219_a_(item);
            if (!map.containsKey(resourceLocation)) {
                map.put(resourceLocation, new BlockModelWriter(ModelsResourceUtil.func_240221_a_(block)));
            }
        }
    }

    private static boolean lambda$act$2(Map map, Block block) {
        return !map.containsKey(block);
    }

    private static void lambda$act$1(Map map, ResourceLocation resourceLocation, Supplier supplier) {
        Supplier supplier2 = map.put(resourceLocation, supplier);
        if (supplier2 != null) {
            throw new IllegalStateException("Duplicate model definition for " + resourceLocation);
        }
    }

    private static void lambda$act$0(Map map, IFinishedBlockState iFinishedBlockState) {
        Block block = iFinishedBlockState.func_230524_a_();
        IFinishedBlockState iFinishedBlockState2 = map.put(block, iFinishedBlockState);
        if (iFinishedBlockState2 != null) {
            throw new IllegalStateException("Duplicate blockstate definition for " + block);
        }
    }
}

