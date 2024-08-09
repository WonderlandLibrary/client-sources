/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;

public class LootContext {
    private final Random random;
    private final float luck;
    private final ServerWorld world;
    private final Function<ResourceLocation, LootTable> lootTableManager;
    private final Set<LootTable> lootTables = Sets.newLinkedHashSet();
    private final Function<ResourceLocation, ILootCondition> field_227499_f_;
    private final Set<ILootCondition> conditions = Sets.newLinkedHashSet();
    private final Map<LootParameter<?>, Object> parameters;
    private final Map<ResourceLocation, IDynamicDropProvider> field_216037_g;

    private LootContext(Random random2, float f, ServerWorld serverWorld, Function<ResourceLocation, LootTable> function, Function<ResourceLocation, ILootCondition> function2, Map<LootParameter<?>, Object> map, Map<ResourceLocation, IDynamicDropProvider> map2) {
        this.random = random2;
        this.luck = f;
        this.world = serverWorld;
        this.lootTableManager = function;
        this.field_227499_f_ = function2;
        this.parameters = ImmutableMap.copyOf(map);
        this.field_216037_g = ImmutableMap.copyOf(map2);
    }

    public boolean has(LootParameter<?> lootParameter) {
        return this.parameters.containsKey(lootParameter);
    }

    public void generateDynamicDrop(ResourceLocation resourceLocation, Consumer<ItemStack> consumer) {
        IDynamicDropProvider iDynamicDropProvider = this.field_216037_g.get(resourceLocation);
        if (iDynamicDropProvider != null) {
            iDynamicDropProvider.add(this, consumer);
        }
    }

    @Nullable
    public <T> T get(LootParameter<T> lootParameter) {
        return (T)this.parameters.get(lootParameter);
    }

    public boolean addLootTable(LootTable lootTable) {
        return this.lootTables.add(lootTable);
    }

    public void removeLootTable(LootTable lootTable) {
        this.lootTables.remove(lootTable);
    }

    public boolean addCondition(ILootCondition iLootCondition) {
        return this.conditions.add(iLootCondition);
    }

    public void removeCondition(ILootCondition iLootCondition) {
        this.conditions.remove(iLootCondition);
    }

    public LootTable getLootTable(ResourceLocation resourceLocation) {
        return this.lootTableManager.apply(resourceLocation);
    }

    public ILootCondition getLootCondition(ResourceLocation resourceLocation) {
        return this.field_227499_f_.apply(resourceLocation);
    }

    public Random getRandom() {
        return this.random;
    }

    public float getLuck() {
        return this.luck;
    }

    public ServerWorld getWorld() {
        return this.world;
    }

    @FunctionalInterface
    public static interface IDynamicDropProvider {
        public void add(LootContext var1, Consumer<ItemStack> var2);
    }

    public static enum EntityTarget {
        THIS("this", LootParameters.THIS_ENTITY),
        KILLER("killer", LootParameters.KILLER_ENTITY),
        DIRECT_KILLER("direct_killer", LootParameters.DIRECT_KILLER_ENTITY),
        KILLER_PLAYER("killer_player", LootParameters.LAST_DAMAGE_PLAYER);

        private final String targetType;
        private final LootParameter<? extends Entity> parameter;

        private EntityTarget(String string2, LootParameter<? extends Entity> lootParameter) {
            this.targetType = string2;
            this.parameter = lootParameter;
        }

        public LootParameter<? extends Entity> getParameter() {
            return this.parameter;
        }

        public static EntityTarget fromString(String string) {
            for (EntityTarget entityTarget : EntityTarget.values()) {
                if (!entityTarget.targetType.equals(string)) continue;
                return entityTarget;
            }
            throw new IllegalArgumentException("Invalid entity target " + string);
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        public static class Serializer
        extends TypeAdapter<EntityTarget> {
            @Override
            public void write(JsonWriter jsonWriter, EntityTarget entityTarget) throws IOException {
                jsonWriter.value(entityTarget.targetType);
            }

            @Override
            public EntityTarget read(JsonReader jsonReader) throws IOException {
                return EntityTarget.fromString(jsonReader.nextString());
            }

            @Override
            public Object read(JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }

            @Override
            public void write(JsonWriter jsonWriter, Object object) throws IOException {
                this.write(jsonWriter, (EntityTarget)((Object)object));
            }
        }
    }

    public static class Builder {
        private final ServerWorld world;
        private final Map<LootParameter<?>, Object> lootParameters = Maps.newIdentityHashMap();
        private final Map<ResourceLocation, IDynamicDropProvider> field_216026_c = Maps.newHashMap();
        private Random rand;
        private float luck;

        public Builder(ServerWorld serverWorld) {
            this.world = serverWorld;
        }

        public Builder withRandom(Random random2) {
            this.rand = random2;
            return this;
        }

        public Builder withSeed(long l) {
            if (l != 0L) {
                this.rand = new Random(l);
            }
            return this;
        }

        public Builder withSeededRandom(long l, Random random2) {
            this.rand = l == 0L ? random2 : new Random(l);
            return this;
        }

        public Builder withLuck(float f) {
            this.luck = f;
            return this;
        }

        public <T> Builder withParameter(LootParameter<T> lootParameter, T t) {
            this.lootParameters.put(lootParameter, t);
            return this;
        }

        public <T> Builder withNullableParameter(LootParameter<T> lootParameter, @Nullable T t) {
            if (t == null) {
                this.lootParameters.remove(lootParameter);
            } else {
                this.lootParameters.put(lootParameter, t);
            }
            return this;
        }

        public Builder withDynamicDrop(ResourceLocation resourceLocation, IDynamicDropProvider iDynamicDropProvider) {
            IDynamicDropProvider iDynamicDropProvider2 = this.field_216026_c.put(resourceLocation, iDynamicDropProvider);
            if (iDynamicDropProvider2 != null) {
                throw new IllegalStateException("Duplicated dynamic drop '" + this.field_216026_c + "'");
            }
            return this;
        }

        public ServerWorld getWorld() {
            return this.world;
        }

        public <T> T assertPresent(LootParameter<T> lootParameter) {
            Object object = this.lootParameters.get(lootParameter);
            if (object == null) {
                throw new IllegalArgumentException("No parameter " + lootParameter);
            }
            return (T)object;
        }

        @Nullable
        public <T> T get(LootParameter<T> lootParameter) {
            return (T)this.lootParameters.get(lootParameter);
        }

        public LootContext build(LootParameterSet lootParameterSet) {
            Sets.SetView<LootParameter<?>> setView = Sets.difference(this.lootParameters.keySet(), lootParameterSet.getAllParameters());
            if (!setView.isEmpty()) {
                throw new IllegalArgumentException("Parameters not allowed in this parameter set: " + setView);
            }
            Sets.SetView<LootParameter<?>> setView2 = Sets.difference(lootParameterSet.getRequiredParameters(), this.lootParameters.keySet());
            if (!setView2.isEmpty()) {
                throw new IllegalArgumentException("Missing required parameters: " + setView2);
            }
            Random random2 = this.rand;
            if (random2 == null) {
                random2 = new Random();
            }
            MinecraftServer minecraftServer = this.world.getServer();
            return new LootContext(random2, this.luck, this.world, minecraftServer.getLootTableManager()::getLootTableFromLocation, minecraftServer.func_229736_aP_()::func_227517_a_, this.lootParameters, this.field_216026_c);
        }
    }
}

