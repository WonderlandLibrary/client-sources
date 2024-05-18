package net.minecraft.src;

import java.util.*;
import java.io.*;

public class MapStorage
{
    private ISaveHandler saveHandler;
    private Map loadedDataMap;
    private List loadedDataList;
    private Map idCounts;
    
    public MapStorage(final ISaveHandler par1ISaveHandler) {
        this.loadedDataMap = new HashMap();
        this.loadedDataList = new ArrayList();
        this.idCounts = new HashMap();
        this.saveHandler = par1ISaveHandler;
        this.loadIdCounts();
    }
    
    public WorldSavedData loadData(final Class par1Class, final String par2Str) {
        WorldSavedData var3 = this.loadedDataMap.get(par2Str);
        if (var3 != null) {
            return var3;
        }
        if (this.saveHandler != null) {
            try {
                final File var4 = this.saveHandler.getMapFileFromName(par2Str);
                if (var4 != null && var4.exists()) {
                    try {
                        var3 = par1Class.getConstructor(String.class).newInstance(par2Str);
                    }
                    catch (Exception var5) {
                        throw new RuntimeException("Failed to instantiate " + par1Class.toString(), var5);
                    }
                    final FileInputStream var6 = new FileInputStream(var4);
                    final NBTTagCompound var7 = CompressedStreamTools.readCompressed(var6);
                    var6.close();
                    var3.readFromNBT(var7.getCompoundTag("data"));
                }
            }
            catch (Exception var8) {
                var8.printStackTrace();
            }
        }
        if (var3 != null) {
            this.loadedDataMap.put(par2Str, var3);
            this.loadedDataList.add(var3);
        }
        return var3;
    }
    
    public void setData(final String par1Str, final WorldSavedData par2WorldSavedData) {
        if (par2WorldSavedData == null) {
            throw new RuntimeException("Can't set null data");
        }
        if (this.loadedDataMap.containsKey(par1Str)) {
            this.loadedDataList.remove(this.loadedDataMap.remove(par1Str));
        }
        this.loadedDataMap.put(par1Str, par2WorldSavedData);
        this.loadedDataList.add(par2WorldSavedData);
    }
    
    public void saveAllData() {
        for (int var1 = 0; var1 < this.loadedDataList.size(); ++var1) {
            final WorldSavedData var2 = this.loadedDataList.get(var1);
            if (var2.isDirty()) {
                this.saveData(var2);
                var2.setDirty(false);
            }
        }
    }
    
    private void saveData(final WorldSavedData par1WorldSavedData) {
        if (this.saveHandler != null) {
            try {
                final File var2 = this.saveHandler.getMapFileFromName(par1WorldSavedData.mapName);
                if (var2 != null) {
                    final NBTTagCompound var3 = new NBTTagCompound();
                    par1WorldSavedData.writeToNBT(var3);
                    final NBTTagCompound var4 = new NBTTagCompound();
                    var4.setCompoundTag("data", var3);
                    final FileOutputStream var5 = new FileOutputStream(var2);
                    CompressedStreamTools.writeCompressed(var4, var5);
                    var5.close();
                }
            }
            catch (Exception var6) {
                var6.printStackTrace();
            }
        }
    }
    
    private void loadIdCounts() {
        try {
            this.idCounts.clear();
            if (this.saveHandler == null) {
                return;
            }
            final File var1 = this.saveHandler.getMapFileFromName("idcounts");
            if (var1 != null && var1.exists()) {
                final DataInputStream var2 = new DataInputStream(new FileInputStream(var1));
                final NBTTagCompound var3 = CompressedStreamTools.read(var2);
                var2.close();
                for (final NBTBase var5 : var3.getTags()) {
                    if (var5 instanceof NBTTagShort) {
                        final NBTTagShort var6 = (NBTTagShort)var5;
                        final String var7 = var6.getName();
                        final short var8 = var6.data;
                        this.idCounts.put(var7, var8);
                    }
                }
            }
        }
        catch (Exception var9) {
            var9.printStackTrace();
        }
    }
    
    public int getUniqueDataId(final String par1Str) {
        Short var2 = this.idCounts.get(par1Str);
        if (var2 == null) {
            var2 = 0;
        }
        else {
            var2 = (short)(var2 + 1);
        }
        this.idCounts.put(par1Str, var2);
        if (this.saveHandler == null) {
            return var2;
        }
        try {
            final File var3 = this.saveHandler.getMapFileFromName("idcounts");
            if (var3 != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                for (final String var6 : this.idCounts.keySet()) {
                    final short var7 = this.idCounts.get(var6);
                    var4.setShort(var6, var7);
                }
                final DataOutputStream var8 = new DataOutputStream(new FileOutputStream(var3));
                CompressedStreamTools.write(var4, var8);
                var8.close();
            }
        }
        catch (Exception var9) {
            var9.printStackTrace();
        }
        return var2;
    }
}
