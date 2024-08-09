/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.datasync;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.optifine.util.BiomeUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityDataManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<Class<? extends Entity>, Integer> NEXT_ID_MAP = Maps.newHashMap();
    private final Entity entity;
    private final Map<Integer, DataEntry<?>> entries = Maps.newHashMap();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private boolean empty = true;
    private boolean dirty;
    public Biome spawnBiome = BiomeUtils.PLAINS;
    public BlockPos spawnPosition = BlockPos.ZERO;

    public EntityDataManager(Entity entity2) {
        this.entity = entity2;
    }

    public static <T> DataParameter<T> createKey(Class<? extends Entity> clazz, IDataSerializer<T> iDataSerializer) {
        int n;
        if (LOGGER.isDebugEnabled()) {
            try {
                Class<?> clazz2 = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
                if (!clazz2.equals(clazz)) {
                    LOGGER.debug("defineId called for: {} from {}", (Object)clazz, (Object)clazz2, (Object)new RuntimeException());
                }
            } catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
        }
        if (NEXT_ID_MAP.containsKey(clazz)) {
            n = NEXT_ID_MAP.get(clazz) + 1;
        } else {
            int n2 = 0;
            Class<? extends Entity> clazz3 = clazz;
            while (clazz3 != Entity.class) {
                if (!NEXT_ID_MAP.containsKey(clazz3 = clazz3.getSuperclass())) continue;
                n2 = NEXT_ID_MAP.get(clazz3) + 1;
                break;
            }
            n = n2;
        }
        if (n > 254) {
            throw new IllegalArgumentException("Data value id is too big with " + n + "! (Max is 254)");
        }
        NEXT_ID_MAP.put(clazz, n);
        return iDataSerializer.createKey(n);
    }

    public <T> void register(DataParameter<T> dataParameter, T t) {
        int n = dataParameter.getId();
        if (n > 254) {
            throw new IllegalArgumentException("Data value id is too big with " + n + "! (Max is 254)");
        }
        if (this.entries.containsKey(n)) {
            throw new IllegalArgumentException("Duplicate id value for " + n + "!");
        }
        if (DataSerializers.getSerializerId(dataParameter.getSerializer()) < 0) {
            throw new IllegalArgumentException("Unregistered serializer " + dataParameter.getSerializer() + " for " + n + "!");
        }
        this.setEntry(dataParameter, t);
    }

    private <T> void setEntry(DataParameter<T> dataParameter, T t) {
        DataEntry<T> dataEntry = new DataEntry<T>(dataParameter, t);
        this.lock.writeLock().lock();
        this.entries.put(dataParameter.getId(), dataEntry);
        this.empty = false;
        this.lock.writeLock().unlock();
    }

    private <T> DataEntry<T> getEntry(DataParameter<T> dataParameter) {
        DataEntry<?> dataEntry;
        this.lock.readLock().lock();
        try {
            dataEntry = this.entries.get(dataParameter.getId());
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Getting synched entity data");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Synched entity data");
            crashReportCategory.addDetail("Data ID", dataParameter);
            throw new ReportedException(crashReport);
        } finally {
            this.lock.readLock().unlock();
        }
        return dataEntry;
    }

    public <T> T get(DataParameter<T> dataParameter) {
        return this.getEntry(dataParameter).getValue();
    }

    public <T> void set(DataParameter<T> dataParameter, T t) {
        DataEntry<T> dataEntry = this.getEntry(dataParameter);
        if (ObjectUtils.notEqual(t, dataEntry.getValue())) {
            dataEntry.setValue(t);
            this.entity.notifyDataManagerChange(dataParameter);
            dataEntry.setDirty(false);
            this.dirty = true;
        }
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public static void writeEntries(List<DataEntry<?>> list, PacketBuffer packetBuffer) throws IOException {
        if (list != null) {
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                EntityDataManager.writeEntry(packetBuffer, list.get(i));
            }
        }
        packetBuffer.writeByte(255);
    }

    @Nullable
    public List<DataEntry<?>> getDirty() {
        ArrayList<DataEntry<?>> arrayList = null;
        if (this.dirty) {
            this.lock.readLock().lock();
            for (DataEntry<?> dataEntry : this.entries.values()) {
                if (!dataEntry.isDirty()) continue;
                dataEntry.setDirty(true);
                if (arrayList == null) {
                    arrayList = Lists.newArrayList();
                }
                arrayList.add(dataEntry.copy());
            }
            this.lock.readLock().unlock();
        }
        this.dirty = false;
        return arrayList;
    }

    @Nullable
    public List<DataEntry<?>> getAll() {
        ArrayList<DataEntry<?>> arrayList = null;
        this.lock.readLock().lock();
        for (DataEntry<?> dataEntry : this.entries.values()) {
            if (arrayList == null) {
                arrayList = Lists.newArrayList();
            }
            arrayList.add(dataEntry.copy());
        }
        this.lock.readLock().unlock();
        return arrayList;
    }

    private static <T> void writeEntry(PacketBuffer packetBuffer, DataEntry<T> dataEntry) throws IOException {
        DataParameter<T> dataParameter = dataEntry.getKey();
        int n = DataSerializers.getSerializerId(dataParameter.getSerializer());
        if (n < 0) {
            throw new EncoderException("Unknown serializer type " + dataParameter.getSerializer());
        }
        packetBuffer.writeByte(dataParameter.getId());
        packetBuffer.writeVarInt(n);
        dataParameter.getSerializer().write(packetBuffer, dataEntry.getValue());
    }

    @Nullable
    public static List<DataEntry<?>> readEntries(PacketBuffer packetBuffer) throws IOException {
        short s;
        ArrayList<DataEntry<?>> arrayList = null;
        while ((s = packetBuffer.readUnsignedByte()) != 255) {
            int n;
            IDataSerializer<?> iDataSerializer;
            if (arrayList == null) {
                arrayList = Lists.newArrayList();
            }
            if ((iDataSerializer = DataSerializers.getSerializer(n = packetBuffer.readVarInt())) == null) {
                throw new DecoderException("Unknown serializer type " + n);
            }
            arrayList.add(EntityDataManager.makeDataEntry(packetBuffer, s, iDataSerializer));
        }
        return arrayList;
    }

    private static <T> DataEntry<T> makeDataEntry(PacketBuffer packetBuffer, int n, IDataSerializer<T> iDataSerializer) {
        return new DataEntry<T>(iDataSerializer.createKey(n), iDataSerializer.read(packetBuffer));
    }

    public void setEntryValues(List<DataEntry<?>> list) {
        this.lock.writeLock().lock();
        for (DataEntry<?> dataEntry : list) {
            DataEntry<?> dataEntry2 = this.entries.get(dataEntry.getKey().getId());
            if (dataEntry2 == null) continue;
            this.setEntryValue(dataEntry2, dataEntry);
            this.entity.notifyDataManagerChange(dataEntry.getKey());
        }
        this.lock.writeLock().unlock();
        this.dirty = true;
    }

    private <T> void setEntryValue(DataEntry<T> dataEntry, DataEntry<?> dataEntry2) {
        if (!Objects.equals(dataEntry2.key.getSerializer(), dataEntry.key.getSerializer())) {
            throw new IllegalStateException(String.format("Invalid entity data item type for field %d on entity %s: old=%s(%s), new=%s(%s)", dataEntry.key.getId(), this.entity, dataEntry.value, dataEntry.value.getClass(), dataEntry2.value, dataEntry2.value.getClass()));
        }
        dataEntry.setValue(dataEntry2.getValue());
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public void setClean() {
        this.dirty = false;
        this.lock.readLock().lock();
        for (DataEntry<?> dataEntry : this.entries.values()) {
            dataEntry.setDirty(true);
        }
        this.lock.readLock().unlock();
    }

    public static class DataEntry<T> {
        private final DataParameter<T> key;
        private T value;
        private boolean dirty;

        public DataEntry(DataParameter<T> dataParameter, T t) {
            this.key = dataParameter;
            this.value = t;
            this.dirty = true;
        }

        public DataParameter<T> getKey() {
            return this.key;
        }

        public void setValue(T t) {
            this.value = t;
        }

        public T getValue() {
            return this.value;
        }

        public boolean isDirty() {
            return this.dirty;
        }

        public void setDirty(boolean bl) {
            this.dirty = bl;
        }

        public DataEntry<T> copy() {
            return new DataEntry<T>(this.key, this.key.getSerializer().copyValue(this.value));
        }
    }
}

