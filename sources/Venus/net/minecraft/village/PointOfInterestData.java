/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.village;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.village.PointOfInterest;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;

public class PointOfInterestData {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Short2ObjectMap<PointOfInterest> records = new Short2ObjectOpenHashMap<PointOfInterest>();
    private final Map<PointOfInterestType, Set<PointOfInterest>> byType = Maps.newHashMap();
    private final Runnable onChange;
    private boolean valid;

    public static Codec<PointOfInterestData> func_234158_a_(Runnable runnable) {
        return RecordCodecBuilder.create(arg_0 -> PointOfInterestData.lambda$func_234158_a_$2(runnable, arg_0)).orElseGet(Util.func_240982_a_("Failed to read POI section: ", LOGGER::error), () -> PointOfInterestData.lambda$func_234158_a_$3(runnable));
    }

    public PointOfInterestData(Runnable runnable) {
        this(runnable, true, ImmutableList.of());
    }

    private PointOfInterestData(Runnable runnable, boolean bl, List<PointOfInterest> list) {
        this.onChange = runnable;
        this.valid = bl;
        list.forEach(this::add);
    }

    public Stream<PointOfInterest> getRecords(Predicate<PointOfInterestType> predicate, PointOfInterestManager.Status status2) {
        return this.byType.entrySet().stream().filter(arg_0 -> PointOfInterestData.lambda$getRecords$4(predicate, arg_0)).flatMap(PointOfInterestData::lambda$getRecords$5).filter(status2.getTest());
    }

    public void add(BlockPos blockPos, PointOfInterestType pointOfInterestType) {
        if (this.add(new PointOfInterest(blockPos, pointOfInterestType, this.onChange))) {
            LOGGER.debug("Added POI of type {} @ {}", () -> PointOfInterestData.lambda$add$6(pointOfInterestType), () -> PointOfInterestData.lambda$add$7(blockPos));
            this.onChange.run();
        }
    }

    private boolean add(PointOfInterest pointOfInterest) {
        BlockPos blockPos = pointOfInterest.getPos();
        PointOfInterestType pointOfInterestType = pointOfInterest.getType();
        short s = SectionPos.toRelativeOffset(blockPos);
        PointOfInterest pointOfInterest2 = (PointOfInterest)this.records.get(s);
        if (pointOfInterest2 != null) {
            if (pointOfInterestType.equals(pointOfInterest2.getType())) {
                return true;
            }
            String string = "POI data mismatch: already registered at " + blockPos;
            if (SharedConstants.developmentMode) {
                throw Util.pauseDevMode(new IllegalStateException(string));
            }
            LOGGER.error(string);
        }
        this.records.put(s, pointOfInterest);
        this.byType.computeIfAbsent(pointOfInterestType, PointOfInterestData::lambda$add$8).add(pointOfInterest);
        return false;
    }

    public void remove(BlockPos blockPos) {
        PointOfInterest pointOfInterest = (PointOfInterest)this.records.remove(SectionPos.toRelativeOffset(blockPos));
        if (pointOfInterest == null) {
            LOGGER.error("POI data mismatch: never registered at " + blockPos);
        } else {
            this.byType.get(pointOfInterest.getType()).remove(pointOfInterest);
            Supplier[] supplierArray = new Supplier[2];
            supplierArray[0] = pointOfInterest::getType;
            supplierArray[1] = pointOfInterest::getPos;
            LOGGER.debug("Removed POI of type {} @ {}", supplierArray);
            this.onChange.run();
        }
    }

    public boolean release(BlockPos blockPos) {
        PointOfInterest pointOfInterest = (PointOfInterest)this.records.get(SectionPos.toRelativeOffset(blockPos));
        if (pointOfInterest == null) {
            throw Util.pauseDevMode(new IllegalStateException("POI never registered at " + blockPos));
        }
        boolean bl = pointOfInterest.release();
        this.onChange.run();
        return bl;
    }

    public boolean exists(BlockPos blockPos, Predicate<PointOfInterestType> predicate) {
        short s = SectionPos.toRelativeOffset(blockPos);
        PointOfInterest pointOfInterest = (PointOfInterest)this.records.get(s);
        return pointOfInterest != null && predicate.test(pointOfInterest.getType());
    }

    public Optional<PointOfInterestType> getType(BlockPos blockPos) {
        short s = SectionPos.toRelativeOffset(blockPos);
        PointOfInterest pointOfInterest = (PointOfInterest)this.records.get(s);
        return pointOfInterest != null ? Optional.of(pointOfInterest.getType()) : Optional.empty();
    }

    public void refresh(Consumer<BiConsumer<BlockPos, PointOfInterestType>> consumer) {
        if (!this.valid) {
            Short2ObjectOpenHashMap<PointOfInterest> short2ObjectOpenHashMap = new Short2ObjectOpenHashMap<PointOfInterest>(this.records);
            this.clear();
            consumer.accept((arg_0, arg_1) -> this.lambda$refresh$10(short2ObjectOpenHashMap, arg_0, arg_1));
            this.valid = true;
            this.onChange.run();
        }
    }

    private void clear() {
        this.records.clear();
        this.byType.clear();
    }

    boolean isValid() {
        return this.valid;
    }

    private void lambda$refresh$10(Short2ObjectMap short2ObjectMap, BlockPos blockPos, PointOfInterestType pointOfInterestType) {
        short s = SectionPos.toRelativeOffset(blockPos);
        PointOfInterest pointOfInterest = short2ObjectMap.computeIfAbsent(s, arg_0 -> this.lambda$refresh$9(blockPos, pointOfInterestType, arg_0));
        this.add(pointOfInterest);
    }

    private PointOfInterest lambda$refresh$9(BlockPos blockPos, PointOfInterestType pointOfInterestType, int n) {
        return new PointOfInterest(blockPos, pointOfInterestType, this.onChange);
    }

    private static Set lambda$add$8(PointOfInterestType pointOfInterestType) {
        return Sets.newHashSet();
    }

    private static Object lambda$add$7(BlockPos blockPos) {
        return blockPos;
    }

    private static Object lambda$add$6(PointOfInterestType pointOfInterestType) {
        return pointOfInterestType;
    }

    private static Stream lambda$getRecords$5(Map.Entry entry) {
        return ((Set)entry.getValue()).stream();
    }

    private static boolean lambda$getRecords$4(Predicate predicate, Map.Entry entry) {
        return predicate.test((PointOfInterestType)entry.getKey());
    }

    private static PointOfInterestData lambda$func_234158_a_$3(Runnable runnable) {
        return new PointOfInterestData(runnable, false, ImmutableList.of());
    }

    private static App lambda$func_234158_a_$2(Runnable runnable, RecordCodecBuilder.Instance instance) {
        return instance.group(RecordCodecBuilder.point(runnable), Codec.BOOL.optionalFieldOf("Valid", false).forGetter(PointOfInterestData::lambda$func_234158_a_$0), ((MapCodec)PointOfInterest.func_234150_a_(runnable).listOf().fieldOf("Records")).forGetter(PointOfInterestData::lambda$func_234158_a_$1)).apply(instance, PointOfInterestData::new);
    }

    private static List lambda$func_234158_a_$1(PointOfInterestData pointOfInterestData) {
        return ImmutableList.copyOf(pointOfInterestData.records.values());
    }

    private static Boolean lambda$func_234158_a_$0(PointOfInterestData pointOfInterestData) {
        return pointOfInterestData.valid;
    }
}

