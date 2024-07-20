/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.datafix.IFixType;
import net.minecraft.util.datafix.IFixableData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataFixer
implements IDataFixer {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<IFixType, List<IDataWalker>> walkerMap = Maps.newHashMap();
    private final Map<IFixType, List<IFixableData>> fixMap = Maps.newHashMap();
    private final int version;

    public DataFixer(int versionIn) {
        this.version = versionIn;
    }

    public NBTTagCompound process(IFixType type2, NBTTagCompound compound) {
        int i = compound.hasKey("DataVersion", 99) ? compound.getInteger("DataVersion") : -1;
        return i >= 1343 ? compound : this.process(type2, compound, i);
    }

    @Override
    public NBTTagCompound process(IFixType type2, NBTTagCompound compound, int versionIn) {
        if (versionIn < this.version) {
            compound = this.processFixes(type2, compound, versionIn);
            compound = this.processWalkers(type2, compound, versionIn);
        }
        return compound;
    }

    private NBTTagCompound processFixes(IFixType type2, NBTTagCompound compound, int versionIn) {
        List<IFixableData> list = this.fixMap.get(type2);
        if (list != null) {
            for (int i = 0; i < list.size(); ++i) {
                IFixableData ifixabledata = list.get(i);
                if (ifixabledata.getFixVersion() <= versionIn) continue;
                compound = ifixabledata.fixTagCompound(compound);
            }
        }
        return compound;
    }

    private NBTTagCompound processWalkers(IFixType type2, NBTTagCompound compound, int versionIn) {
        List<IDataWalker> list = this.walkerMap.get(type2);
        if (list != null) {
            for (int i = 0; i < list.size(); ++i) {
                compound = list.get(i).process(this, compound, versionIn);
            }
        }
        return compound;
    }

    public void registerWalker(FixTypes type2, IDataWalker walker) {
        this.registerWalkerAdd(type2, walker);
    }

    public void registerWalkerAdd(IFixType type2, IDataWalker walker) {
        this.getTypeList(this.walkerMap, type2).add(walker);
    }

    public void registerFix(IFixType type2, IFixableData fixable) {
        List list = this.getTypeList(this.fixMap, type2);
        int i = fixable.getFixVersion();
        if (i > this.version) {
            LOGGER.warn("Ignored fix registered for version: {} as the DataVersion of the game is: {}", (Object)i, (Object)this.version);
        } else if (!list.isEmpty() && ((IFixableData)Util.getLastElement(list)).getFixVersion() > i) {
            for (int j = 0; j < list.size(); ++j) {
                if (((IFixableData)list.get(j)).getFixVersion() <= i) continue;
                list.add(j, fixable);
                break;
            }
        } else {
            list.add(fixable);
        }
    }

    private <V> List<V> getTypeList(Map<IFixType, List<V>> map, IFixType type2) {
        List<V> list = map.get(type2);
        if (list == null) {
            list = Lists.newArrayList();
            map.put(type2, list);
        }
        return list;
    }
}

