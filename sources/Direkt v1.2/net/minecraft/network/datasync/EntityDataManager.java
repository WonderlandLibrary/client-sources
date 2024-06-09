package net.minecraft.network.datasync;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ReportedException;

public class EntityDataManager {
	private static final Logger NEXT_ID_MAP = LogManager.getLogger();
	private static final Map<Class<? extends Entity>, Integer> entity = Maps.<Class<? extends Entity>, Integer> newHashMap();
	private final Entity entries;
	private final Map<Integer, EntityDataManager.DataEntry<?>> lock = Maps.<Integer, EntityDataManager.DataEntry<?>> newHashMap();
	private final ReadWriteLock empty = new ReentrantReadWriteLock();
	private boolean dirty = true;
	private boolean g;

	public EntityDataManager(Entity entityIn) {
		this.entries = entityIn;
	}

	public static <T> DataParameter<T> createKey(Class<? extends Entity> clazz, DataSerializer<T> serializer) {
		if (NEXT_ID_MAP.isDebugEnabled()) {
			try {
				Class<?> oclass = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());

				if (!oclass.equals(clazz)) {
					NEXT_ID_MAP.debug("defineId called for: {} from {}", new Object[] { clazz, oclass, new RuntimeException() });
				}
			} catch (ClassNotFoundException var5) {
				;
			}
		}

		int j;

		if (entity.containsKey(clazz)) {
			j = entity.get(clazz).intValue() + 1;
		} else {
			int i = 0;
			Class<?> oclass1 = clazz;

			while (oclass1 != Entity.class) {
				oclass1 = oclass1.getSuperclass();

				if (entity.containsKey(oclass1)) {
					i = entity.get(oclass1).intValue() + 1;
					break;
				}
			}

			j = i;
		}

		if (j > 254) {
			throw new IllegalArgumentException("Data value id is too big with " + j + "! (Max is " + 254 + ")");
		} else {
			entity.put(clazz, Integer.valueOf(j));
			return serializer.createKey(j);
		}
	}

	public <T> void register(DataParameter<T> key, T value) {
		int i = key.getId();

		if (i > 254) {
			throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is " + 254 + ")");
		} else if (this.lock.containsKey(Integer.valueOf(i))) {
			throw new IllegalArgumentException("Duplicate id value for " + i + "!");
		} else if (DataSerializers.getSerializerId(key.getSerializer()) < 0) {
			throw new IllegalArgumentException("Unregistered serializer " + key.getSerializer() + " for " + i + "!");
		} else {
			this.setEntry(key, value);
		}
	}

	private <T> void setEntry(DataParameter<T> key, T value) {
		EntityDataManager.DataEntry<T> dataentry = new EntityDataManager.DataEntry(key, value);
		this.empty.writeLock().lock();
		this.lock.put(Integer.valueOf(key.getId()), dataentry);
		this.dirty = false;
		this.empty.writeLock().unlock();
	}

	private <T> EntityDataManager.DataEntry<T> getEntry(DataParameter<T> key) {
		this.empty.readLock().lock();
		EntityDataManager.DataEntry<T> dataentry;

		try {
			dataentry = (EntityDataManager.DataEntry) this.lock.get(Integer.valueOf(key.getId()));
		} catch (Throwable throwable) {
			CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Getting synched entity data");
			CrashReportCategory crashreportcategory = crashreport.makeCategory("Synched entity data");
			crashreportcategory.addCrashSection("Data ID", key);
			throw new ReportedException(crashreport);
		}

		this.empty.readLock().unlock();
		return dataentry;
	}

	public <T> T get(DataParameter<T> key) {
		return this.getEntry(key).getValue();
	}

	public <T> void set(DataParameter<T> key, T value) {
		EntityDataManager.DataEntry<T> dataentry = this.<T> getEntry(key);

		if (ObjectUtils.notEqual(value, dataentry.getValue())) {
			dataentry.setValue(value);
			this.entries.notifyDataManagerChange(key);
			dataentry.setDirty(true);
			this.g = true;
		}
	}

	public <T> void setDirty(DataParameter<T> key) {
		this.getEntry(key).dirty = true;
		this.g = true;
	}

	public boolean isDirty() {
		return this.g;
	}

	public static void writeEntries(List<EntityDataManager.DataEntry<?>> entriesIn, PacketBuffer buf) throws IOException {
		if (entriesIn != null) {
			int i = 0;

			for (int j = entriesIn.size(); i < j; ++i) {
				EntityDataManager.DataEntry<?> dataentry = entriesIn.get(i);
				writeEntry(buf, dataentry);
			}
		}

		buf.writeByte(255);
	}

	@Nullable
	public List<EntityDataManager.DataEntry<?>> getDirty() {
		List<EntityDataManager.DataEntry<?>> list = null;

		if (this.g) {
			this.empty.readLock().lock();

			for (EntityDataManager.DataEntry<?> dataentry : this.lock.values()) {
				if (dataentry.isDirty()) {
					dataentry.setDirty(false);

					if (list == null) {
						list = Lists.<EntityDataManager.DataEntry<?>> newArrayList();
					}

					list.add(dataentry);
				}
			}

			this.empty.readLock().unlock();
		}

		this.g = false;
		return list;
	}

	public void writeEntries(PacketBuffer buf) throws IOException {
		this.empty.readLock().lock();

		for (EntityDataManager.DataEntry<?> dataentry : this.lock.values()) {
			writeEntry(buf, dataentry);
		}

		this.empty.readLock().unlock();
		buf.writeByte(255);
	}

	@Nullable
	public List<EntityDataManager.DataEntry<?>> getAll() {
		List<EntityDataManager.DataEntry<?>> list = null;
		this.empty.readLock().lock();

		for (EntityDataManager.DataEntry<?> dataentry : this.lock.values()) {
			if (list == null) {
				list = Lists.<EntityDataManager.DataEntry<?>> newArrayList();
			}

			list.add(dataentry);
		}

		this.empty.readLock().unlock();
		return list;
	}

	private static <T> void writeEntry(PacketBuffer buf, EntityDataManager.DataEntry<T> entry) throws IOException {
		DataParameter<T> dataparameter = entry.getKey();
		int i = DataSerializers.getSerializerId(dataparameter.getSerializer());

		if (i < 0) {
			throw new EncoderException("Unknown serializer type " + dataparameter.getSerializer());
		} else {
			buf.writeByte(dataparameter.getId());
			buf.writeVarIntToBuffer(i);
			dataparameter.getSerializer().write(buf, entry.getValue());
		}
	}

	@Nullable
	public static List<EntityDataManager.DataEntry<?>> readEntries(PacketBuffer buf) throws IOException {
		List<EntityDataManager.DataEntry<?>> list = null;
		int i;

		while ((i = buf.readUnsignedByte()) != 255) {
			if (list == null) {
				list = Lists.<EntityDataManager.DataEntry<?>> newArrayList();
			}

			int j = buf.readVarIntFromBuffer();
			DataSerializer<?> dataserializer = DataSerializers.getSerializer(j);

			if (dataserializer == null) { throw new DecoderException("Unknown serializer type " + j); }

			list.add(new EntityDataManager.DataEntry(dataserializer.createKey(i), dataserializer.read(buf)));
		}

		return list;
	}

	public void setEntryValues(List<EntityDataManager.DataEntry<?>> entriesIn) {
		this.empty.writeLock().lock();

		for (EntityDataManager.DataEntry<?> dataentry : entriesIn) {
			EntityDataManager.DataEntry<?> dataentry1 = this.lock.get(Integer.valueOf(dataentry.getKey().getId()));

			if (dataentry1 != null) {
				this.setEntryValue(dataentry1, dataentry);
				this.entries.notifyDataManagerChange(dataentry.getKey());
			}
		}

		this.empty.writeLock().unlock();
		this.g = true;
	}

	protected <T> void setEntryValue(EntityDataManager.DataEntry<T> target, EntityDataManager.DataEntry<?> source) {
		target.setValue((T) source.getValue());
	}

	public boolean isEmpty() {
		return this.dirty;
	}

	public void setClean() {
		this.g = false;
	}

	public static class DataEntry<T> {
		private final DataParameter<T> key;
		private T value;
		private boolean dirty;

		public DataEntry(DataParameter<T> keyIn, T valueIn) {
			this.key = keyIn;
			this.value = valueIn;
			this.dirty = true;
		}

		public DataParameter<T> getKey() {
			return this.key;
		}

		public void setValue(T valueIn) {
			this.value = valueIn;
		}

		public T getValue() {
			return this.value;
		}

		public boolean isDirty() {
			return this.dirty;
		}

		public void setDirty(boolean dirtyIn) {
			this.dirty = dirtyIn;
		}
	}
}
