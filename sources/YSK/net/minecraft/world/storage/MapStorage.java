package net.minecraft.world.storage;

import net.minecraft.world.*;
import java.lang.reflect.*;
import java.util.*;
import com.google.common.collect.*;
import java.io.*;
import net.minecraft.nbt.*;

public class MapStorage
{
    private Map<String, Short> idCounts;
    private List<WorldSavedData> loadedDataList;
    private static final String[] I;
    private ISaveHandler saveHandler;
    protected Map<String, WorldSavedData> loadedDataMap;
    
    private void saveData(final WorldSavedData worldSavedData) {
        if (this.saveHandler != null) {
            try {
                final File mapFileFromName = this.saveHandler.getMapFileFromName(worldSavedData.mapName);
                if (mapFileFromName != null) {
                    final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                    worldSavedData.writeToNBT(nbtTagCompound);
                    final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                    nbtTagCompound2.setTag(MapStorage.I["  ".length()], nbtTagCompound);
                    final FileOutputStream fileOutputStream = new FileOutputStream(mapFileFromName);
                    CompressedStreamTools.writeCompressed(nbtTagCompound2, fileOutputStream);
                    fileOutputStream.close();
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public WorldSavedData loadData(final Class<? extends WorldSavedData> clazz, final String s) {
        WorldSavedData worldSavedData = this.loadedDataMap.get(s);
        if (worldSavedData != null) {
            return worldSavedData;
        }
        if (this.saveHandler != null) {
            try {
                final File mapFileFromName = this.saveHandler.getMapFileFromName(s);
                if (mapFileFromName != null && mapFileFromName.exists()) {
                    try {
                        final Class[] array = new Class[" ".length()];
                        array["".length()] = String.class;
                        final Constructor<? extends WorldSavedData> constructor = clazz.getConstructor((Class<?>[])array);
                        final Object[] array2 = new Object[" ".length()];
                        array2["".length()] = s;
                        worldSavedData = (WorldSavedData)constructor.newInstance(array2);
                        "".length();
                        if (3 <= -1) {
                            throw null;
                        }
                    }
                    catch (Exception ex) {
                        throw new RuntimeException(MapStorage.I["".length()] + clazz.toString(), ex);
                    }
                    final FileInputStream fileInputStream = new FileInputStream(mapFileFromName);
                    final NBTTagCompound compressed = CompressedStreamTools.readCompressed(fileInputStream);
                    fileInputStream.close();
                    worldSavedData.readFromNBT(compressed.getCompoundTag(MapStorage.I[" ".length()]));
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                }
            }
            catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
        if (worldSavedData != null) {
            this.loadedDataMap.put(s, worldSavedData);
            this.loadedDataList.add(worldSavedData);
        }
        return worldSavedData;
    }
    
    public int getUniqueDataId(final String s) {
        final Short n = this.idCounts.get(s);
        Short n2;
        if (n == null) {
            n2 = (short)"".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            n2 = (short)(n + " ".length());
        }
        this.idCounts.put(s, n2);
        if (this.saveHandler == null) {
            return n2;
        }
        try {
            final File mapFileFromName = this.saveHandler.getMapFileFromName(MapStorage.I[0x86 ^ 0x82]);
            if (mapFileFromName != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                final Iterator<String> iterator = this.idCounts.keySet().iterator();
                "".length();
                if (2 < 2) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final String s2 = iterator.next();
                    nbtTagCompound.setShort(s2, this.idCounts.get(s2));
                }
                final DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(mapFileFromName));
                CompressedStreamTools.write(nbtTagCompound, dataOutputStream);
                dataOutputStream.close();
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return n2;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void saveAllData() {
        int i = "".length();
        "".length();
        if (2 <= 0) {
            throw null;
        }
        while (i < this.loadedDataList.size()) {
            final WorldSavedData worldSavedData = this.loadedDataList.get(i);
            if (worldSavedData.isDirty()) {
                this.saveData(worldSavedData);
                worldSavedData.setDirty("".length() != 0);
            }
            ++i;
        }
    }
    
    private static void I() {
        (I = new String[0x96 ^ 0x93])["".length()] = I("\u0002\u0000\u0001\u001a\u0002 A\u001c\u0019G-\u000f\u001b\u0002\u0006*\u0015\u0001\u0017\u0013!A", "Dahvg");
        MapStorage.I[" ".length()] = I("%6\u0001%", "AWuDw");
        MapStorage.I["  ".length()] = I("*\u000b\r2", "NjySM");
        MapStorage.I["   ".length()] = I("\u000b\u0005\u00055\u0000\f\u0015\u0015", "bafZu");
        MapStorage.I[0x73 ^ 0x77] = I("3\u0007\u0013\t\u00054\u0017\u0003", "Zcpfp");
    }
    
    static {
        I();
    }
    
    public MapStorage(final ISaveHandler saveHandler) {
        this.loadedDataMap = (Map<String, WorldSavedData>)Maps.newHashMap();
        this.loadedDataList = (List<WorldSavedData>)Lists.newArrayList();
        this.idCounts = (Map<String, Short>)Maps.newHashMap();
        this.saveHandler = saveHandler;
        this.loadIdCounts();
    }
    
    public void setData(final String s, final WorldSavedData worldSavedData) {
        if (this.loadedDataMap.containsKey(s)) {
            this.loadedDataList.remove(this.loadedDataMap.remove(s));
        }
        this.loadedDataMap.put(s, worldSavedData);
        this.loadedDataList.add(worldSavedData);
    }
    
    private void loadIdCounts() {
        try {
            this.idCounts.clear();
            if (this.saveHandler == null) {
                return;
            }
            final File mapFileFromName = this.saveHandler.getMapFileFromName(MapStorage.I["   ".length()]);
            if (mapFileFromName != null && mapFileFromName.exists()) {
                final DataInputStream dataInputStream = new DataInputStream(new FileInputStream(mapFileFromName));
                final NBTTagCompound read = CompressedStreamTools.read(dataInputStream);
                dataInputStream.close();
                final Iterator<String> iterator = read.getKeySet().iterator();
                "".length();
                if (2 <= 1) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final String s = iterator.next();
                    final NBTBase tag = read.getTag(s);
                    if (tag instanceof NBTTagShort) {
                        this.idCounts.put(s, ((NBTTagShort)tag).getShort());
                    }
                }
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
