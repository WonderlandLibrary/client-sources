package net.minecraft.world.storage;

import java.io.*;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.world.WorldSavedData;

public class MapStorage {
	private final ISaveHandler saveHandler;
	protected Map<String, WorldSavedData> loadedDataMap = Maps.<String, WorldSavedData> newHashMap();
	private final List<WorldSavedData> loadedDataList = Lists.<WorldSavedData> newArrayList();
	private final Map<String, Short> idCounts = Maps.<String, Short> newHashMap();

	public MapStorage(ISaveHandler saveHandlerIn) {
		this.saveHandler = saveHandlerIn;
		this.loadIdCounts();
	}

	@Nullable

	/**
	 * Loads an existing MapDataBase corresponding to the given id from disk, instantiating the given Class, or returns null if none such file exists.
	 */
	public WorldSavedData getOrLoadData(Class<? extends WorldSavedData> clazz, String dataIdentifier) {
		WorldSavedData worldsaveddata = this.loadedDataMap.get(dataIdentifier);

		if (worldsaveddata != null) {
			return worldsaveddata;
		} else {
			if (this.saveHandler != null) {
				try {
					File file1 = this.saveHandler.getMapFileFromName(dataIdentifier);

					if ((file1 != null) && file1.exists()) {
						try {
							worldsaveddata = clazz.getConstructor(new Class[] { String.class }).newInstance(new Object[] { dataIdentifier });
						} catch (Exception exception) {
							throw new RuntimeException("Failed to instantiate " + clazz, exception);
						}

						FileInputStream fileinputstream = new FileInputStream(file1);
						NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(fileinputstream);
						fileinputstream.close();
						worldsaveddata.readFromNBT(nbttagcompound.getCompoundTag("data"));
					}
				} catch (Exception exception1) {
					exception1.printStackTrace();
				}
			}

			if (worldsaveddata != null) {
				this.loadedDataMap.put(dataIdentifier, worldsaveddata);
				this.loadedDataList.add(worldsaveddata);
			}

			return worldsaveddata;
		}
	}

	/**
	 * Assigns the given String id to the given MapDataBase, removing any existing ones of the same id.
	 */
	public void setData(String dataIdentifier, WorldSavedData data) {
		if (this.loadedDataMap.containsKey(dataIdentifier)) {
			this.loadedDataList.remove(this.loadedDataMap.remove(dataIdentifier));
		}

		this.loadedDataMap.put(dataIdentifier, data);
		this.loadedDataList.add(data);
	}

	/**
	 * Saves all dirty loaded MapDataBases to disk.
	 */
	public void saveAllData() {
		for (int i = 0; i < this.loadedDataList.size(); ++i) {
			WorldSavedData worldsaveddata = this.loadedDataList.get(i);

			if (worldsaveddata.isDirty()) {
				this.saveData(worldsaveddata);
				worldsaveddata.setDirty(false);
			}
		}
	}

	/**
	 * Saves the given MapDataBase to disk.
	 */
	private void saveData(WorldSavedData data) {
		if (this.saveHandler != null) {
			try {
				File file1 = this.saveHandler.getMapFileFromName(data.mapName);

				if (file1 != null) {
					NBTTagCompound nbttagcompound = new NBTTagCompound();
					nbttagcompound.setTag("data", data.func_189551_b(new NBTTagCompound()));
					FileOutputStream fileoutputstream = new FileOutputStream(file1);
					CompressedStreamTools.writeCompressed(nbttagcompound, fileoutputstream);
					fileoutputstream.close();
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	/**
	 * Loads the idCounts Map from the 'idcounts' file.
	 */
	private void loadIdCounts() {
		try {
			this.idCounts.clear();

			if (this.saveHandler == null) { return; }

			File file1 = this.saveHandler.getMapFileFromName("idcounts");

			if ((file1 != null) && file1.exists()) {
				DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));
				NBTTagCompound nbttagcompound = CompressedStreamTools.read(datainputstream);
				datainputstream.close();

				for (String s : nbttagcompound.getKeySet()) {
					NBTBase nbtbase = nbttagcompound.getTag(s);

					if (nbtbase instanceof NBTTagShort) {
						NBTTagShort nbttagshort = (NBTTagShort) nbtbase;
						short short1 = nbttagshort.getShort();
						this.idCounts.put(s, Short.valueOf(short1));
					}
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Returns an unique new data id for the given prefix and saves the idCounts map to the 'idcounts' file.
	 */
	public int getUniqueDataId(String key) {
		Short oshort = this.idCounts.get(key);

		if (oshort == null) {
			oshort = Short.valueOf((short) 0);
		} else {
			oshort = Short.valueOf((short) (oshort.shortValue() + 1));
		}

		this.idCounts.put(key, oshort);

		if (this.saveHandler == null) {
			return oshort.shortValue();
		} else {
			try {
				File file1 = this.saveHandler.getMapFileFromName("idcounts");

				if (file1 != null) {
					NBTTagCompound nbttagcompound = new NBTTagCompound();

					for (String s : this.idCounts.keySet()) {
						nbttagcompound.setShort(s, this.idCounts.get(s).shortValue());
					}

					DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));
					CompressedStreamTools.write(nbttagcompound, dataoutputstream);
					dataoutputstream.close();
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}

			return oshort.shortValue();
		}
	}
}
