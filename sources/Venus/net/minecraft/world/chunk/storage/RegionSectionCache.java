/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk.storage;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.OptionalDynamic;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DefaultTypeReferences;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.IOWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegionSectionCache<R>
implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final IOWorker field_227173_b_;
    private final Long2ObjectMap<Optional<R>> data = new Long2ObjectOpenHashMap<Optional<R>>();
    private final LongLinkedOpenHashSet dirtySections = new LongLinkedOpenHashSet();
    private final Function<Runnable, Codec<R>> field_235988_e_;
    private final Function<Runnable, R> field_219124_f;
    private final DataFixer field_219125_g;
    private final DefaultTypeReferences field_219126_h;

    public RegionSectionCache(File file, Function<Runnable, Codec<R>> function, Function<Runnable, R> function2, DataFixer dataFixer, DefaultTypeReferences defaultTypeReferences, boolean bl) {
        this.field_235988_e_ = function;
        this.field_219124_f = function2;
        this.field_219125_g = dataFixer;
        this.field_219126_h = defaultTypeReferences;
        this.field_227173_b_ = new IOWorker(file, bl, file.getName());
    }

    protected void tick(BooleanSupplier booleanSupplier) {
        while (!this.dirtySections.isEmpty() && booleanSupplier.getAsBoolean()) {
            ChunkPos chunkPos = SectionPos.from(this.dirtySections.firstLong()).asChunkPos();
            this.save(chunkPos);
        }
    }

    @Nullable
    protected Optional<R> func_219106_c(long l) {
        return (Optional)this.data.get(l);
    }

    protected Optional<R> func_219113_d(long l) {
        SectionPos sectionPos = SectionPos.from(l);
        if (this.func_219114_b(sectionPos)) {
            return Optional.empty();
        }
        Optional<R> optional = this.func_219106_c(l);
        if (optional != null) {
            return optional;
        }
        this.func_219107_b(sectionPos.asChunkPos());
        optional = this.func_219106_c(l);
        if (optional == null) {
            throw Util.pauseDevMode(new IllegalStateException());
        }
        return optional;
    }

    protected boolean func_219114_b(SectionPos sectionPos) {
        return World.isYOutOfBounds(SectionPos.toWorld(sectionPos.getSectionY()));
    }

    protected R func_235995_e_(long l) {
        Optional<R> optional = this.func_219113_d(l);
        if (optional.isPresent()) {
            return optional.get();
        }
        R r = this.field_219124_f.apply(() -> this.lambda$func_235995_e_$0(l));
        this.data.put(l, Optional.of(r));
        return r;
    }

    private void func_219107_b(ChunkPos chunkPos) {
        this.func_235992_a_(chunkPos, NBTDynamicOps.INSTANCE, this.func_223138_c(chunkPos));
    }

    @Nullable
    private CompoundNBT func_223138_c(ChunkPos chunkPos) {
        try {
            return this.field_227173_b_.func_227090_a_(chunkPos);
        } catch (IOException iOException) {
            LOGGER.error("Error reading chunk {} data from disk", (Object)chunkPos, (Object)iOException);
            return null;
        }
    }

    private <T> void func_235992_a_(ChunkPos chunkPos, DynamicOps<T> dynamicOps, @Nullable T t) {
        if (t == null) {
            for (int i = 0; i < 16; ++i) {
                this.data.put(SectionPos.from(chunkPos, i).asLong(), (Optional<R>)Optional.empty());
            }
        } else {
            int n;
            Dynamic<T> dynamic = new Dynamic<T>(dynamicOps, t);
            int n2 = RegionSectionCache.func_235993_a_(dynamic);
            boolean bl = n2 != (n = SharedConstants.getVersion().getWorldVersion());
            Dynamic<T> dynamic2 = this.field_219125_g.update(this.field_219126_h.getTypeReference(), dynamic, n2, n);
            OptionalDynamic<T> optionalDynamic = dynamic2.get("Sections");
            for (int i = 0; i < 16; ++i) {
                long l = SectionPos.from(chunkPos, i).asLong();
                Optional optional = optionalDynamic.get(Integer.toString(i)).result().flatMap(arg_0 -> this.lambda$func_235992_a_$2(l, arg_0));
                this.data.put(l, (Optional<R>)optional);
                optional.ifPresent(arg_0 -> this.lambda$func_235992_a_$3(l, bl, arg_0));
            }
        }
    }

    private void save(ChunkPos chunkPos) {
        Dynamic<INBT> dynamic = this.func_235991_a_(chunkPos, NBTDynamicOps.INSTANCE);
        INBT iNBT = dynamic.getValue();
        if (iNBT instanceof CompoundNBT) {
            this.field_227173_b_.func_227093_a_(chunkPos, (CompoundNBT)iNBT);
        } else {
            LOGGER.error("Expected compound tag, got {}", (Object)iNBT);
        }
    }

    private <T> Dynamic<T> func_235991_a_(ChunkPos chunkPos, DynamicOps<T> dynamicOps) {
        HashMap hashMap = Maps.newHashMap();
        for (int i = 0; i < 16; ++i) {
            long l = SectionPos.from(chunkPos, i).asLong();
            this.dirtySections.remove(l);
            Optional optional = (Optional)this.data.get(l);
            if (optional == null || !optional.isPresent()) continue;
            DataResult<T> dataResult = this.field_235988_e_.apply(() -> this.lambda$func_235991_a_$4(l)).encodeStart(dynamicOps, optional.get());
            String string = Integer.toString(i);
            dataResult.resultOrPartial(LOGGER::error).ifPresent(arg_0 -> RegionSectionCache.lambda$func_235991_a_$5(hashMap, dynamicOps, string, arg_0));
        }
        return new Dynamic<T>(dynamicOps, dynamicOps.createMap(ImmutableMap.of(dynamicOps.createString("Sections"), dynamicOps.createMap(hashMap), dynamicOps.createString("DataVersion"), dynamicOps.createInt(SharedConstants.getVersion().getWorldVersion()))));
    }

    protected void onSectionLoad(long l) {
    }

    protected void markDirty(long l) {
        Optional optional = (Optional)this.data.get(l);
        if (optional != null && optional.isPresent()) {
            this.dirtySections.add(l);
        } else {
            LOGGER.warn("No data for position: {}", (Object)SectionPos.from(l));
        }
    }

    private static int func_235993_a_(Dynamic<?> dynamic) {
        return dynamic.get("DataVersion").asInt(1945);
    }

    public void saveIfDirty(ChunkPos chunkPos) {
        if (!this.dirtySections.isEmpty()) {
            for (int i = 0; i < 16; ++i) {
                long l = SectionPos.from(chunkPos, i).asLong();
                if (!this.dirtySections.contains(l)) continue;
                this.save(chunkPos);
                return;
            }
        }
    }

    @Override
    public void close() throws IOException {
        this.field_227173_b_.close();
    }

    private static void lambda$func_235991_a_$5(Map map, DynamicOps dynamicOps, String string, Object object) {
        map.put(dynamicOps.createString(string), object);
    }

    private void lambda$func_235991_a_$4(long l) {
        this.markDirty(l);
    }

    private void lambda$func_235992_a_$3(long l, boolean bl, Object object) {
        this.onSectionLoad(l);
        if (bl) {
            this.markDirty(l);
        }
    }

    private Optional lambda$func_235992_a_$2(long l, Dynamic dynamic) {
        return this.field_235988_e_.apply(() -> this.lambda$func_235992_a_$1(l)).parse(dynamic).resultOrPartial(LOGGER::error);
    }

    private void lambda$func_235992_a_$1(long l) {
        this.markDirty(l);
    }

    private void lambda$func_235995_e_$0(long l) {
        this.markDirty(l);
    }
}

