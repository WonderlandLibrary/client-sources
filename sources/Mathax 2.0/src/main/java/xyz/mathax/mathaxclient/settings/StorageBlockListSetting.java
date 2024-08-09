package xyz.mathax.mathaxclient.settings;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.misc.MatHaxIdentifier;
import xyz.mathax.mathaxclient.utils.settings.IVisible;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class StorageBlockListSetting extends Setting<List<BlockEntityType<?>>> {
    public static final BlockEntityType<?>[] STORAGE_BLOCKS = {
            BlockEntityType.FURNACE,
            BlockEntityType.CHEST,
            BlockEntityType.TRAPPED_CHEST,
            BlockEntityType.ENDER_CHEST,
            BlockEntityType.DISPENSER,
            BlockEntityType.DROPPER,
            BlockEntityType.HOPPER,
            BlockEntityType.SHULKER_BOX,
            BlockEntityType.BARREL,
            BlockEntityType.SMOKER,
            BlockEntityType.BLAST_FURNACE,
            BlockEntityType.CAMPFIRE
    };

    public static final Registry<BlockEntityType<?>> REGISTRY = new SRegistry();

    public StorageBlockListSetting(String name, String description, List<BlockEntityType<?>> defaultValue, Consumer<List<BlockEntityType<?>>> onChanged, Consumer<Setting<List<BlockEntityType<?>>>> onModuleEnabled, IVisible visible) {
        super(name, description, defaultValue, onChanged, onModuleEnabled, visible);
    }

    @Override
    public void resetImpl() {
        value = new ArrayList<>(defaultValue);
    }

    @Override
    protected List<BlockEntityType<?>> parseImpl(String string) {
        String[] values = string.split(",");
        List<BlockEntityType<?>> blocks = new ArrayList<>(values.length);

        try {
            for (String value : values) {
                BlockEntityType<?> block = parseId(Registries.BLOCK_ENTITY_TYPE, value);
                if (block != null) {
                    blocks.add(block);
                }
            }
        } catch (Exception ignored) {}

        return blocks;
    }

    @Override
    protected boolean isValueValid(List<BlockEntityType<?>> value) {
        return true;
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registries.BLOCK_ENTITY_TYPE.getIds();
    }

    @Override
    public JSONObject save(JSONObject json) {
        if (json.has("value")) {
            json.put("value", new JSONArray());
            for (BlockEntityType<?> type : get()) {
                Identifier id = Registries.BLOCK_ENTITY_TYPE.getId(type);
                if (id != null) {
                    json.append("value", id.toString());
                }
            }
        }

        return json;
    }

    @Override
    public List<BlockEntityType<?>> load(JSONObject json) {
        get().clear();

        if (json.has("value") && JSONUtils.isValidJSONArray(json, "value")) {
            for (Object object : json.getJSONArray("value")) {
                if (object instanceof String id) {
                    BlockEntityType<?> type = Registries.BLOCK_ENTITY_TYPE.get(new Identifier(id));
                    if (type != null) {
                        get().add(type);
                    }
                }
            }
        }

        return get();
    }

    public static class Builder extends SettingBuilder<Builder, List<BlockEntityType<?>>, StorageBlockListSetting> {
        public Builder() {
            super(new ArrayList<>(0));
        }

        public Builder defaultValue(BlockEntityType<?>... defaults) {
            return defaultValue(defaults != null ? Arrays.asList(defaults) : new ArrayList<>());
        }

        @Override
        public StorageBlockListSetting build() {
            return new StorageBlockListSetting(name, description, defaultValue, onChanged, onModuleEnabled, visible);
        }
    }

    private static class SRegistry extends SimpleRegistry<BlockEntityType<?>> {
        public SRegistry() {
            super(RegistryKey.ofRegistry(new MatHaxIdentifier("storage-blocks")), Lifecycle.stable());
        }

        @Override
        public int size() {
            return STORAGE_BLOCKS.length;
        }

        @Nullable
        @Override
        public Identifier getId(BlockEntityType<?> entry) {
            return null;
        }

        @Override
        public Optional<RegistryKey<BlockEntityType<?>>> getKey(BlockEntityType<?> entry) {
            return Optional.empty();
        }

        @Override
        public int getRawId(@Nullable BlockEntityType<?> entry) {
            return 0;
        }

        @Nullable
        @Override
        public BlockEntityType<?> get(@Nullable RegistryKey<BlockEntityType<?>> key) {
            return null;
        }

        @Nullable
        @Override
        public BlockEntityType<?> get(@Nullable Identifier id) {
            return null;
        }

        @Override
        public Lifecycle getEntryLifecycle(BlockEntityType<?> object) {
            return null;
        }

        @Override
        public Lifecycle getLifecycle() {
            return null;
        }

        @Override
        public Set<Identifier> getIds() {
            return null;
        }

        @Override
        public BlockEntityType<?> getOrThrow(int index) {
            return super.getOrThrow(index);
        }

        @Override
        public boolean containsId(Identifier id) {
            return false;
        }

        @Nullable
        @Override
        public BlockEntityType<?> get(int index) {
            return null;
        }

        @NotNull
        @Override
        public Iterator<BlockEntityType<?>> iterator() {
            return ObjectIterators.wrap(STORAGE_BLOCKS);
        }

        @Override
        public boolean contains(RegistryKey<BlockEntityType<?>> key) {
            return false;
        }

        @Override
        public Set<Map.Entry<RegistryKey<BlockEntityType<?>>, BlockEntityType<?>>> getEntrySet() {
            return null;
        }

        @Override
        public Optional<RegistryEntry.Reference<BlockEntityType<?>>> getRandom(net.minecraft.util.math.random.Random random) {
            return Optional.empty();
        }

        @Override
        public Registry<BlockEntityType<?>> freeze() {
            return null;
        }

        @Override
        public RegistryEntry.Reference<BlockEntityType<?>> createEntry(BlockEntityType<?> value) {
            return null;
        }

        @Override
        public Optional<RegistryEntry.Reference<BlockEntityType<?>>> getEntry(int rawId) {
            return Optional.empty();
        }

        @Override
        public Optional<RegistryEntry.Reference<BlockEntityType<?>>> getEntry(RegistryKey<BlockEntityType<?>> key) {
            return Optional.empty();
        }

        @Override
        public Stream<RegistryEntry.Reference<BlockEntityType<?>>> streamEntries() {
            return null;
        }

        @Override
        public Optional<RegistryEntryList.Named<BlockEntityType<?>>> getEntryList(TagKey<BlockEntityType<?>> tag) {
            return Optional.empty();
        }

        @Override
        public RegistryEntryList.Named<BlockEntityType<?>> getOrCreateEntryList(TagKey<BlockEntityType<?>> tag) {
            return null;
        }

        @Override
        public Stream<Pair<TagKey<BlockEntityType<?>>, RegistryEntryList.Named<BlockEntityType<?>>>> streamTagsAndEntries() {
            return null;
        }

        @Override
        public Stream<TagKey<BlockEntityType<?>>> streamTags() {
            return null;
        }

        @Override
        public void clearTags() {

        }

        @Override
        public void populateTags(Map<TagKey<BlockEntityType<?>>, List<RegistryEntry<BlockEntityType<?>>>> tagEntries) {

        }

        @Override
        public Set<RegistryKey<BlockEntityType<?>>> getKeys() {
            return null;
        }
    }
}
