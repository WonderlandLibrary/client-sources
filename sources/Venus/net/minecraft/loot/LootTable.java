/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ILootFunctionConsumer;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTable {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final LootTable EMPTY_LOOT_TABLE = new LootTable(LootParameterSets.EMPTY, new LootPool[0], new ILootFunction[0]);
    public static final LootParameterSet DEFAULT_PARAMETER_SET = LootParameterSets.GENERIC;
    private final LootParameterSet parameterSet;
    private final LootPool[] pools;
    private final ILootFunction[] functions;
    private final BiFunction<ItemStack, LootContext, ItemStack> combinedFunctions;

    private LootTable(LootParameterSet lootParameterSet, LootPool[] lootPoolArray, ILootFunction[] iLootFunctionArray) {
        this.parameterSet = lootParameterSet;
        this.pools = lootPoolArray;
        this.functions = iLootFunctionArray;
        this.combinedFunctions = LootFunctionManager.combine(iLootFunctionArray);
    }

    public static Consumer<ItemStack> capStackSizes(Consumer<ItemStack> consumer) {
        return arg_0 -> LootTable.lambda$capStackSizes$0(consumer, arg_0);
    }

    public void recursiveGenerate(LootContext lootContext, Consumer<ItemStack> consumer) {
        if (lootContext.addLootTable(this)) {
            Consumer<ItemStack> consumer2 = ILootFunction.func_215858_a(this.combinedFunctions, consumer, lootContext);
            for (LootPool lootPool : this.pools) {
                lootPool.generate(consumer2, lootContext);
            }
            lootContext.removeLootTable(this);
        } else {
            LOGGER.warn("Detected infinite loop in loot tables");
        }
    }

    public void generate(LootContext lootContext, Consumer<ItemStack> consumer) {
        this.recursiveGenerate(lootContext, LootTable.capStackSizes(consumer));
    }

    public List<ItemStack> generate(LootContext lootContext) {
        ArrayList<ItemStack> arrayList = Lists.newArrayList();
        this.generate(lootContext, arrayList::add);
        return arrayList;
    }

    public LootParameterSet getParameterSet() {
        return this.parameterSet;
    }

    public void validate(ValidationTracker validationTracker) {
        int n;
        for (n = 0; n < this.pools.length; ++n) {
            this.pools[n].func_227505_a_(validationTracker.func_227534_b_(".pools[" + n + "]"));
        }
        for (n = 0; n < this.functions.length; ++n) {
            this.functions[n].func_225580_a_(validationTracker.func_227534_b_(".functions[" + n + "]"));
        }
    }

    public void fillInventory(IInventory iInventory, LootContext lootContext) {
        List<ItemStack> list = this.generate(lootContext);
        Random random2 = lootContext.getRandom();
        List<Integer> list2 = this.getEmptySlotsRandomized(iInventory, random2);
        this.shuffleItems(list, list2.size(), random2);
        for (ItemStack itemStack : list) {
            if (list2.isEmpty()) {
                LOGGER.warn("Tried to over-fill a container");
                return;
            }
            if (itemStack.isEmpty()) {
                iInventory.setInventorySlotContents(list2.remove(list2.size() - 1), ItemStack.EMPTY);
                continue;
            }
            iInventory.setInventorySlotContents(list2.remove(list2.size() - 1), itemStack);
        }
    }

    private void shuffleItems(List<ItemStack> list, int n, Random random2) {
        ItemStack itemStack;
        ArrayList<ItemStack> arrayList = Lists.newArrayList();
        Iterator<ItemStack> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            itemStack = iterator2.next();
            if (itemStack.isEmpty()) {
                iterator2.remove();
                continue;
            }
            if (itemStack.getCount() <= 1) continue;
            arrayList.add(itemStack);
            iterator2.remove();
        }
        while (n - list.size() - arrayList.size() > 0 && !arrayList.isEmpty()) {
            itemStack = (ItemStack)arrayList.remove(MathHelper.nextInt(random2, 0, arrayList.size() - 1));
            int n2 = MathHelper.nextInt(random2, 1, itemStack.getCount() / 2);
            ItemStack itemStack2 = itemStack.split(n2);
            if (itemStack.getCount() > 1 && random2.nextBoolean()) {
                arrayList.add(itemStack);
            } else {
                list.add(itemStack);
            }
            if (itemStack2.getCount() > 1 && random2.nextBoolean()) {
                arrayList.add(itemStack2);
                continue;
            }
            list.add(itemStack2);
        }
        list.addAll(arrayList);
        Collections.shuffle(list, random2);
    }

    private List<Integer> getEmptySlotsRandomized(IInventory iInventory, Random random2) {
        ArrayList<Integer> arrayList = Lists.newArrayList();
        for (int i = 0; i < iInventory.getSizeInventory(); ++i) {
            if (!iInventory.getStackInSlot(i).isEmpty()) continue;
            arrayList.add(i);
        }
        Collections.shuffle(arrayList, random2);
        return arrayList;
    }

    public static Builder builder() {
        return new Builder();
    }

    private static void lambda$capStackSizes$0(Consumer consumer, ItemStack itemStack) {
        if (itemStack.getCount() < itemStack.getMaxStackSize()) {
            consumer.accept(itemStack);
        } else {
            ItemStack itemStack2;
            for (int i = itemStack.getCount(); i > 0; i -= itemStack2.getCount()) {
                itemStack2 = itemStack.copy();
                itemStack2.setCount(Math.min(itemStack.getMaxStackSize(), i));
                consumer.accept(itemStack2);
            }
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    implements ILootFunctionConsumer<Builder> {
        private final List<LootPool> lootPools = Lists.newArrayList();
        private final List<ILootFunction> lootFunctions = Lists.newArrayList();
        private LootParameterSet parameterSet = DEFAULT_PARAMETER_SET;

        public Builder addLootPool(LootPool.Builder builder) {
            this.lootPools.add(builder.build());
            return this;
        }

        public Builder setParameterSet(LootParameterSet lootParameterSet) {
            this.parameterSet = lootParameterSet;
            return this;
        }

        @Override
        public Builder acceptFunction(ILootFunction.IBuilder iBuilder) {
            this.lootFunctions.add(iBuilder.build());
            return this;
        }

        @Override
        public Builder cast() {
            return this;
        }

        public LootTable build() {
            return new LootTable(this.parameterSet, this.lootPools.toArray(new LootPool[0]), this.lootFunctions.toArray(new ILootFunction[0]));
        }

        @Override
        public Object cast() {
            return this.cast();
        }

        @Override
        public Object acceptFunction(ILootFunction.IBuilder iBuilder) {
            return this.acceptFunction(iBuilder);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements JsonDeserializer<LootTable>,
    JsonSerializer<LootTable> {
        @Override
        public LootTable deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            ILootFunction[] iLootFunctionArray;
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "loot table");
            LootPool[] lootPoolArray = JSONUtils.deserializeClass(jsonObject, "pools", new LootPool[0], jsonDeserializationContext, LootPool[].class);
            LootParameterSet lootParameterSet = null;
            if (jsonObject.has("type")) {
                iLootFunctionArray = JSONUtils.getString(jsonObject, "type");
                lootParameterSet = LootParameterSets.getValue(new ResourceLocation((String)iLootFunctionArray));
            }
            iLootFunctionArray = JSONUtils.deserializeClass(jsonObject, "functions", new ILootFunction[0], jsonDeserializationContext, ILootFunction[].class);
            return new LootTable(lootParameterSet != null ? lootParameterSet : LootParameterSets.GENERIC, lootPoolArray, iLootFunctionArray);
        }

        @Override
        public JsonElement serialize(LootTable lootTable, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject = new JsonObject();
            if (lootTable.parameterSet != DEFAULT_PARAMETER_SET) {
                ResourceLocation resourceLocation = LootParameterSets.getKey(lootTable.parameterSet);
                if (resourceLocation != null) {
                    jsonObject.addProperty("type", resourceLocation.toString());
                } else {
                    LOGGER.warn("Failed to find id for param set " + lootTable.parameterSet);
                }
            }
            if (lootTable.pools.length > 0) {
                jsonObject.add("pools", jsonSerializationContext.serialize(lootTable.pools));
            }
            if (!ArrayUtils.isEmpty(lootTable.functions)) {
                jsonObject.add("functions", jsonSerializationContext.serialize(lootTable.functions));
            }
            return jsonObject;
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }

        @Override
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((LootTable)object, type, jsonSerializationContext);
        }
    }
}

