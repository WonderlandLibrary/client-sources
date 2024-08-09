/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Memory;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Brain<E extends LivingEntity> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Supplier<Codec<Brain<E>>> brainCodec;
    private final Map<MemoryModuleType<?>, Optional<? extends Memory<?>>> memories = Maps.newHashMap();
    private final Map<SensorType<? extends Sensor<? super E>>, Sensor<? super E>> sensors = Maps.newLinkedHashMap();
    private final Map<Integer, Map<Activity, Set<Task<? super E>>>> taskPriorityMap = Maps.newTreeMap();
    private Schedule schedule = Schedule.EMPTY;
    private final Map<Activity, Set<Pair<MemoryModuleType<?>, MemoryModuleStatus>>> requiredMemoryStates = Maps.newHashMap();
    private final Map<Activity, Set<MemoryModuleType<?>>> memoryMap = Maps.newHashMap();
    private Set<Activity> defaultActivities = Sets.newHashSet();
    private final Set<Activity> activities = Sets.newHashSet();
    private Activity fallbackActivity = Activity.IDLE;
    private long lastGameTime = -9999L;

    public static <E extends LivingEntity> BrainCodec<E> createCodec(Collection<? extends MemoryModuleType<?>> collection, Collection<? extends SensorType<? extends Sensor<? super E>>> collection2) {
        return new BrainCodec(collection, collection2);
    }

    public static <E extends LivingEntity> Codec<Brain<E>> getBrainCodec(Collection<? extends MemoryModuleType<?>> collection, Collection<? extends SensorType<? extends Sensor<? super E>>> collection2) {
        MutableObject mutableObject = new MutableObject();
        mutableObject.setValue(new MapCodec<Brain<E>>(collection, collection2, mutableObject){
            final Collection val$memoryTypes;
            final Collection val$sensorTypes;
            final MutableObject val$mutableobject;
            {
                this.val$memoryTypes = collection;
                this.val$sensorTypes = collection2;
                this.val$mutableobject = mutableObject;
            }

            @Override
            public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                return this.val$memoryTypes.stream().flatMap(1::lambda$keys$1).map(arg_0 -> 1.lambda$keys$2(dynamicOps, arg_0));
            }

            @Override
            public <T> DataResult<Brain<E>> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
                MutableObject mutableObject = new MutableObject(DataResult.success(ImmutableList.builder()));
                mapLike.entries().forEach(arg_0 -> this.lambda$decode$4(dynamicOps, mutableObject, arg_0));
                ImmutableList immutableList = mutableObject.getValue().resultOrPartial(LOGGER::error).map(ImmutableList.Builder::build).orElseGet(ImmutableList::of);
                return DataResult.success(new Brain(this.val$memoryTypes, this.val$sensorTypes, immutableList, this.val$mutableobject::getValue));
            }

            private <T, U> DataResult<MemoryCodec<U>> decodeMemory(MemoryModuleType<U> memoryModuleType, DynamicOps<T> dynamicOps, T t) {
                return memoryModuleType.getMemoryCodec().map(DataResult::success).orElseGet(() -> 1.lambda$decodeMemory$5(memoryModuleType)).flatMap(arg_0 -> 1.lambda$decodeMemory$6(dynamicOps, t, arg_0)).map(arg_0 -> 1.lambda$decodeMemory$7(memoryModuleType, arg_0));
            }

            @Override
            public <T> RecordBuilder<T> encode(Brain<E> brain, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
                brain.createMemoryCodecs().forEach(arg_0 -> 1.lambda$encode$8(dynamicOps, recordBuilder, arg_0));
                return recordBuilder;
            }

            @Override
            public RecordBuilder encode(Object object, DynamicOps dynamicOps, RecordBuilder recordBuilder) {
                return this.encode((Brain)object, dynamicOps, recordBuilder);
            }

            private static void lambda$encode$8(DynamicOps dynamicOps, RecordBuilder recordBuilder, MemoryCodec memoryCodec) {
                memoryCodec.encode(dynamicOps, recordBuilder);
            }

            private static MemoryCodec lambda$decodeMemory$7(MemoryModuleType memoryModuleType, Memory memory) {
                return new MemoryCodec(memoryModuleType, Optional.of(memory));
            }

            private static DataResult lambda$decodeMemory$6(DynamicOps dynamicOps, Object object, Codec codec) {
                return codec.parse(dynamicOps, object);
            }

            private static DataResult lambda$decodeMemory$5(MemoryModuleType memoryModuleType) {
                return DataResult.error("No codec for memory: " + memoryModuleType);
            }

            private void lambda$decode$4(DynamicOps dynamicOps, MutableObject mutableObject, Pair pair) {
                DataResult dataResult = Registry.MEMORY_MODULE_TYPE.parse(dynamicOps, (MemoryModuleType<?>)pair.getFirst());
                DataResult dataResult2 = dataResult.flatMap(arg_0 -> this.lambda$decode$3(dynamicOps, pair, arg_0));
                mutableObject.setValue(((DataResult)mutableObject.getValue()).apply2(ImmutableList.Builder::add, dataResult2));
            }

            private DataResult lambda$decode$3(DynamicOps dynamicOps, Pair pair, MemoryModuleType memoryModuleType) {
                return this.decodeMemory(memoryModuleType, dynamicOps, pair.getSecond());
            }

            private static Object lambda$keys$2(DynamicOps dynamicOps, ResourceLocation resourceLocation) {
                return dynamicOps.createString(resourceLocation.toString());
            }

            private static Stream lambda$keys$1(MemoryModuleType memoryModuleType) {
                return Util.streamOptional(memoryModuleType.getMemoryCodec().map(arg_0 -> 1.lambda$keys$0(memoryModuleType, arg_0)));
            }

            private static ResourceLocation lambda$keys$0(MemoryModuleType memoryModuleType, Codec codec) {
                return Registry.MEMORY_MODULE_TYPE.getKey(memoryModuleType);
            }
        }.fieldOf("memories").codec());
        return (Codec)mutableObject.getValue();
    }

    public Brain(Collection<? extends MemoryModuleType<?>> collection, Collection<? extends SensorType<? extends Sensor<? super E>>> collection2, ImmutableList<MemoryCodec<?>> immutableList, Supplier<Codec<Brain<E>>> supplier) {
        this.brainCodec = supplier;
        for (MemoryModuleType<?> object : collection) {
            this.memories.put(object, Optional.empty());
        }
        for (SensorType sensorType : collection2) {
            this.sensors.put(sensorType, (Sensor<E>)sensorType.getSensor());
        }
        for (Sensor sensor : this.sensors.values()) {
            for (MemoryModuleType<?> memoryModuleType : sensor.getUsedMemories()) {
                this.memories.put(memoryModuleType, Optional.empty());
            }
        }
        for (MemoryCodec memoryCodec : immutableList) {
            memoryCodec.refreshMemory(this);
        }
    }

    public <T> DataResult<T> encode(DynamicOps<T> dynamicOps) {
        return this.brainCodec.get().encodeStart(dynamicOps, this);
    }

    private Stream<MemoryCodec<?>> createMemoryCodecs() {
        return this.memories.entrySet().stream().map(Brain::lambda$createMemoryCodecs$0);
    }

    public boolean hasMemory(MemoryModuleType<?> memoryModuleType) {
        return this.hasMemory(memoryModuleType, MemoryModuleStatus.VALUE_PRESENT);
    }

    public <U> void removeMemory(MemoryModuleType<U> memoryModuleType) {
        this.setMemory(memoryModuleType, Optional.empty());
    }

    public <U> void setMemory(MemoryModuleType<U> memoryModuleType, @Nullable U u) {
        this.setMemory(memoryModuleType, Optional.ofNullable(u));
    }

    public <U> void replaceMemory(MemoryModuleType<U> memoryModuleType, U u, long l) {
        this.replaceMemory(memoryModuleType, Optional.of(Memory.create(u, l)));
    }

    public <U> void setMemory(MemoryModuleType<U> memoryModuleType, Optional<? extends U> optional) {
        this.replaceMemory(memoryModuleType, optional.map(Memory::create));
    }

    private <U> void replaceMemory(MemoryModuleType<U> memoryModuleType, Optional<? extends Memory<?>> optional) {
        if (this.memories.containsKey(memoryModuleType)) {
            if (optional.isPresent() && this.isEmptyCollection(optional.get().getValue())) {
                this.removeMemory(memoryModuleType);
            } else {
                this.memories.put(memoryModuleType, optional);
            }
        }
    }

    public <U> Optional<U> getMemory(MemoryModuleType<U> memoryModuleType) {
        return this.memories.get(memoryModuleType).map(Memory::getValue);
    }

    public <U> boolean hasMemory(MemoryModuleType<U> memoryModuleType, U u) {
        return !this.hasMemory(memoryModuleType) ? false : this.getMemory(memoryModuleType).filter(arg_0 -> Brain.lambda$hasMemory$1(u, arg_0)).isPresent();
    }

    public boolean hasMemory(MemoryModuleType<?> memoryModuleType, MemoryModuleStatus memoryModuleStatus) {
        Optional<Memory<?>> optional = this.memories.get(memoryModuleType);
        if (optional == null) {
            return true;
        }
        return memoryModuleStatus == MemoryModuleStatus.REGISTERED || memoryModuleStatus == MemoryModuleStatus.VALUE_PRESENT && optional.isPresent() || memoryModuleStatus == MemoryModuleStatus.VALUE_ABSENT && !optional.isPresent();
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setDefaultActivities(Set<Activity> set) {
        this.defaultActivities = set;
    }

    @Deprecated
    public List<Task<? super E>> getRunningTasks() {
        ObjectArrayList<Task<Task<E>>> objectArrayList = new ObjectArrayList<Task<Task<E>>>();
        for (Map<Activity, Set<Task<E>>> map : this.taskPriorityMap.values()) {
            for (Set<Task<E>> set : map.values()) {
                for (Task<E> task : set) {
                    if (task.getStatus() != Task.Status.RUNNING) continue;
                    objectArrayList.add(task);
                }
            }
        }
        return objectArrayList;
    }

    public void switchToFallbackActivity() {
        this.switchActivity(this.fallbackActivity);
    }

    public Optional<Activity> getTemporaryActivity() {
        for (Activity activity : this.activities) {
            if (this.defaultActivities.contains(activity)) continue;
            return Optional.of(activity);
        }
        return Optional.empty();
    }

    public void switchTo(Activity activity) {
        if (this.hasRequiredMemories(activity)) {
            this.switchActivity(activity);
        } else {
            this.switchToFallbackActivity();
        }
    }

    private void switchActivity(Activity activity) {
        if (!this.hasActivity(activity)) {
            this.removeUnassociatedMemories(activity);
            this.activities.clear();
            this.activities.addAll(this.defaultActivities);
            this.activities.add(activity);
        }
    }

    private void removeUnassociatedMemories(Activity activity) {
        for (Activity activity2 : this.activities) {
            Set<MemoryModuleType<?>> set;
            if (activity2 == activity || (set = this.memoryMap.get(activity2)) == null) continue;
            for (MemoryModuleType<?> memoryModuleType : set) {
                this.removeMemory(memoryModuleType);
            }
        }
    }

    public void updateActivity(long l, long l2) {
        if (l2 - this.lastGameTime > 20L) {
            this.lastGameTime = l2;
            Activity activity = this.getSchedule().getScheduledActivity((int)(l % 24000L));
            if (!this.activities.contains(activity)) {
                this.switchTo(activity);
            }
        }
    }

    public void switchActivities(List<Activity> list) {
        for (Activity activity : list) {
            if (!this.hasRequiredMemories(activity)) continue;
            this.switchActivity(activity);
            break;
        }
    }

    public void setFallbackActivity(Activity activity) {
        this.fallbackActivity = activity;
    }

    public void registerActivity(Activity activity, int n, ImmutableList<? extends Task<? super E>> immutableList) {
        this.registerActivity(activity, this.getTaskPriorityList(n, immutableList));
    }

    public void registerActivity(Activity activity, int n, ImmutableList<? extends Task<? super E>> immutableList, MemoryModuleType<?> memoryModuleType) {
        ImmutableSet<Pair<MemoryModuleType<?>, MemoryModuleStatus>> immutableSet = ImmutableSet.of(Pair.of(memoryModuleType, MemoryModuleStatus.VALUE_PRESENT));
        ImmutableSet<MemoryModuleType<?>> immutableSet2 = ImmutableSet.of(memoryModuleType);
        this.registerActivity(activity, this.getTaskPriorityList(n, immutableList), immutableSet, immutableSet2);
    }

    public void registerActivity(Activity activity, ImmutableList<? extends Pair<Integer, ? extends Task<? super E>>> immutableList) {
        this.registerActivity(activity, immutableList, ImmutableSet.of(), Sets.newHashSet());
    }

    public void registerActivity(Activity activity, ImmutableList<? extends Pair<Integer, ? extends Task<? super E>>> immutableList, Set<Pair<MemoryModuleType<?>, MemoryModuleStatus>> set) {
        this.registerActivity(activity, immutableList, set, Sets.newHashSet());
    }

    private void registerActivity(Activity activity, ImmutableList<? extends Pair<Integer, ? extends Task<? super E>>> immutableList, Set<Pair<MemoryModuleType<?>, MemoryModuleStatus>> set, Set<MemoryModuleType<?>> set2) {
        this.requiredMemoryStates.put(activity, set);
        if (!set2.isEmpty()) {
            this.memoryMap.put(activity, set2);
        }
        for (Pair pair : immutableList) {
            this.taskPriorityMap.computeIfAbsent((Integer)pair.getFirst(), Brain::lambda$registerActivity$2).computeIfAbsent(activity, Brain::lambda$registerActivity$3).add((Task)pair.getSecond());
        }
    }

    public boolean hasActivity(Activity activity) {
        return this.activities.contains(activity);
    }

    public Brain<E> copy() {
        Brain<E> brain = new Brain<E>(this.memories.keySet(), this.sensors.keySet(), ImmutableList.of(), this.brainCodec);
        for (Map.Entry<MemoryModuleType<?>, Optional<Memory<?>>> entry : this.memories.entrySet()) {
            MemoryModuleType<?> memoryModuleType = entry.getKey();
            if (!entry.getValue().isPresent()) continue;
            brain.memories.put(memoryModuleType, entry.getValue());
        }
        return brain;
    }

    public void tick(ServerWorld serverWorld, E e) {
        this.tickMemories();
        this.tickSensors(serverWorld, e);
        this.startTasks(serverWorld, e);
        this.tickTasks(serverWorld, e);
    }

    private void tickSensors(ServerWorld serverWorld, E e) {
        for (Sensor<E> sensor : this.sensors.values()) {
            sensor.tick(serverWorld, e);
        }
    }

    private void tickMemories() {
        for (Map.Entry<MemoryModuleType<?>, Optional<Memory<?>>> entry : this.memories.entrySet()) {
            if (!entry.getValue().isPresent()) continue;
            Memory<?> memory = entry.getValue().get();
            memory.tick();
            if (!memory.isForgotten()) continue;
            this.removeMemory(entry.getKey());
        }
    }

    public void stopAllTasks(ServerWorld serverWorld, E e) {
        long l = ((LivingEntity)e).world.getGameTime();
        for (Task<E> task : this.getRunningTasks()) {
            task.stop(serverWorld, e, l);
        }
    }

    private void startTasks(ServerWorld serverWorld, E e) {
        long l = serverWorld.getGameTime();
        for (Map<Activity, Set<Task<E>>> map : this.taskPriorityMap.values()) {
            for (Map.Entry<Activity, Set<Task<E>>> entry : map.entrySet()) {
                Activity activity = entry.getKey();
                if (!this.activities.contains(activity)) continue;
                for (Task<E> task : entry.getValue()) {
                    if (task.getStatus() != Task.Status.STOPPED) continue;
                    task.start(serverWorld, e, l);
                }
            }
        }
    }

    private void tickTasks(ServerWorld serverWorld, E e) {
        long l = serverWorld.getGameTime();
        for (Task<E> task : this.getRunningTasks()) {
            task.tick(serverWorld, e, l);
        }
    }

    private boolean hasRequiredMemories(Activity activity) {
        if (!this.requiredMemoryStates.containsKey(activity)) {
            return true;
        }
        for (Pair<MemoryModuleType<?>, MemoryModuleStatus> pair : this.requiredMemoryStates.get(activity)) {
            MemoryModuleStatus memoryModuleStatus;
            MemoryModuleType<?> memoryModuleType = pair.getFirst();
            if (this.hasMemory(memoryModuleType, memoryModuleStatus = pair.getSecond())) continue;
            return true;
        }
        return false;
    }

    private boolean isEmptyCollection(Object object) {
        return object instanceof Collection && ((Collection)object).isEmpty();
    }

    ImmutableList<? extends Pair<Integer, ? extends Task<? super E>>> getTaskPriorityList(int n, ImmutableList<? extends Task<? super E>> immutableList) {
        int n2 = n;
        ImmutableList.Builder builder = ImmutableList.builder();
        for (Task task : immutableList) {
            builder.add(Pair.of(n2++, task));
        }
        return builder.build();
    }

    private static Set lambda$registerActivity$3(Activity activity) {
        return Sets.newLinkedHashSet();
    }

    private static Map lambda$registerActivity$2(Integer n) {
        return Maps.newHashMap();
    }

    private static boolean lambda$hasMemory$1(Object object, Object object2) {
        return object2.equals(object);
    }

    private static MemoryCodec lambda$createMemoryCodecs$0(Map.Entry entry) {
        return MemoryCodec.createCodec((MemoryModuleType)entry.getKey(), (Optional)entry.getValue());
    }

    public static final class BrainCodec<E extends LivingEntity> {
        private final Collection<? extends MemoryModuleType<?>> memoryTypes;
        private final Collection<? extends SensorType<? extends Sensor<? super E>>> sensorTypes;
        private final Codec<Brain<E>> brainCodec;

        private BrainCodec(Collection<? extends MemoryModuleType<?>> collection, Collection<? extends SensorType<? extends Sensor<? super E>>> collection2) {
            this.memoryTypes = collection;
            this.sensorTypes = collection2;
            this.brainCodec = Brain.getBrainCodec(collection, collection2);
        }

        public Brain<E> deserialize(Dynamic<?> dynamic) {
            return this.brainCodec.parse(dynamic).resultOrPartial(LOGGER::error).orElseGet(this::lambda$deserialize$1);
        }

        private Brain lambda$deserialize$1() {
            return new Brain(this.memoryTypes, this.sensorTypes, ImmutableList.of(), this::lambda$deserialize$0);
        }

        private Codec lambda$deserialize$0() {
            return this.brainCodec;
        }
    }

    static final class MemoryCodec<U> {
        private final MemoryModuleType<U> memoryType;
        private final Optional<? extends Memory<U>> memory;

        private static <U> MemoryCodec<U> createCodec(MemoryModuleType<U> memoryModuleType, Optional<? extends Memory<?>> optional) {
            return new MemoryCodec<U>(memoryModuleType, optional);
        }

        private MemoryCodec(MemoryModuleType<U> memoryModuleType, Optional<? extends Memory<U>> optional) {
            this.memoryType = memoryModuleType;
            this.memory = optional;
        }

        private void refreshMemory(Brain<?> brain) {
            brain.replaceMemory(this.memoryType, this.memory);
        }

        public <T> void encode(DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
            this.memoryType.getMemoryCodec().ifPresent(arg_0 -> this.lambda$encode$1(recordBuilder, dynamicOps, arg_0));
        }

        private void lambda$encode$1(RecordBuilder recordBuilder, DynamicOps dynamicOps, Codec codec) {
            this.memory.ifPresent(arg_0 -> this.lambda$encode$0(recordBuilder, dynamicOps, codec, arg_0));
        }

        private void lambda$encode$0(RecordBuilder recordBuilder, DynamicOps dynamicOps, Codec codec, Memory memory) {
            recordBuilder.add(Registry.MEMORY_MODULE_TYPE.encodeStart(dynamicOps, this.memoryType), codec.encodeStart(dynamicOps, memory));
        }
    }
}

