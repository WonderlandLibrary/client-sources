/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import com.google.common.primitives.Floats;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.renderer.model.ItemOverride;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.ItemOverrideProperty;
import net.optifine.reflect.Reflector;
import net.optifine.util.CompoundKey;

public class ItemOverrideCache {
    private ItemOverrideProperty[] itemOverrideProperties;
    private Map<CompoundKey, Integer> mapModelIndexes = new HashMap<CompoundKey, Integer>();
    public static final Integer INDEX_NONE = new Integer(-1);

    public ItemOverrideCache(ItemOverrideProperty[] itemOverridePropertyArray) {
        this.itemOverrideProperties = itemOverridePropertyArray;
    }

    public Integer getModelIndex(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
        CompoundKey compoundKey = this.getValueKey(itemStack, clientWorld, livingEntity);
        return compoundKey == null ? null : this.mapModelIndexes.get(compoundKey);
    }

    public void putModelIndex(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity, Integer n) {
        CompoundKey compoundKey = this.getValueKey(itemStack, clientWorld, livingEntity);
        if (compoundKey != null) {
            this.mapModelIndexes.put(compoundKey, n);
        }
    }

    private CompoundKey getValueKey(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
        Object[] objectArray = new Integer[this.itemOverrideProperties.length];
        for (int i = 0; i < objectArray.length; ++i) {
            Integer n = this.itemOverrideProperties[i].getValueIndex(itemStack, clientWorld, livingEntity);
            if (n == null) {
                return null;
            }
            objectArray[i] = n;
        }
        return new CompoundKey(objectArray);
    }

    public static ItemOverrideCache make(List<ItemOverride> list) {
        Object object;
        Object object22;
        if (list.isEmpty()) {
            return null;
        }
        if (!Reflector.ItemOverride_mapResourceValues.exists()) {
            return null;
        }
        LinkedHashMap<Object, HashSet<Object>> linkedHashMap = new LinkedHashMap<Object, HashSet<Object>>();
        for (ItemOverride itemOverridePropertyArray2 : list) {
            object22 = (Map)Reflector.getFieldValue(itemOverridePropertyArray2, Reflector.ItemOverride_mapResourceValues);
            for (Object object3 : object22.keySet()) {
                object = (Float)object22.get(object3);
                if (object == null) continue;
                HashSet<Object> hashSet = (HashSet<Object>)linkedHashMap.get(object3);
                if (hashSet == null) {
                    hashSet = new HashSet<Object>();
                    linkedHashMap.put(object3, hashSet);
                }
                hashSet.add(object);
            }
        }
        ArrayList arrayList = new ArrayList();
        for (Object object22 : linkedHashMap.keySet()) {
            Object object3;
            Set set = (Set)linkedHashMap.get(object22);
            object3 = Floats.toArray(set);
            object = new ItemOverrideProperty((ResourceLocation)object22, (float[])object3);
            arrayList.add(object);
        }
        ItemOverrideProperty[] itemOverridePropertyArray = arrayList.toArray(new ItemOverrideProperty[arrayList.size()]);
        object22 = new ItemOverrideCache(itemOverridePropertyArray);
        ItemOverrideCache.logCache(itemOverridePropertyArray, list);
        return object22;
    }

    private static void logCache(ItemOverrideProperty[] itemOverridePropertyArray, List<ItemOverride> list) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < itemOverridePropertyArray.length; ++i) {
            ItemOverrideProperty itemOverrideProperty = itemOverridePropertyArray[i];
            if (stringBuffer.length() > 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(itemOverrideProperty.getLocation() + "=" + itemOverrideProperty.getValues().length);
        }
        if (list.size() > 0) {
            stringBuffer.append(" -> " + list.get(0).getLocation() + " ...");
        }
        Config.dbg("ItemOverrideCache: " + stringBuffer.toString());
    }

    public String toString() {
        return "properties: " + this.itemOverrideProperties.length + ", modelIndexes: " + this.mapModelIndexes.size();
    }
}

