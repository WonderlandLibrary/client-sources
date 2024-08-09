/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class SetAttributes
extends LootFunction {
    private final List<Modifier> modifiers;

    private SetAttributes(ILootCondition[] iLootConditionArray, List<Modifier> list) {
        super(iLootConditionArray);
        this.modifiers = ImmutableList.copyOf(list);
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.SET_ATTRIBUTES;
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        Random random2 = lootContext.getRandom();
        for (Modifier modifier : this.modifiers) {
            UUID uUID = modifier.uuid;
            if (uUID == null) {
                uUID = UUID.randomUUID();
            }
            EquipmentSlotType equipmentSlotType = Util.getRandomObject(modifier.slots, random2);
            itemStack.addAttributeModifier(modifier.attributeName, new AttributeModifier(uUID, modifier.modifierName, (double)modifier.amount.generateFloat(random2), modifier.operation), equipmentSlotType);
        }
        return itemStack;
    }

    static class Modifier {
        private final String modifierName;
        private final Attribute attributeName;
        private final AttributeModifier.Operation operation;
        private final RandomValueRange amount;
        @Nullable
        private final UUID uuid;
        private final EquipmentSlotType[] slots;

        private Modifier(String string, Attribute attribute, AttributeModifier.Operation operation, RandomValueRange randomValueRange, EquipmentSlotType[] equipmentSlotTypeArray, @Nullable UUID uUID) {
            this.modifierName = string;
            this.attributeName = attribute;
            this.operation = operation;
            this.amount = randomValueRange;
            this.uuid = uUID;
            this.slots = equipmentSlotTypeArray;
        }

        public JsonObject serialize(JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", this.modifierName);
            jsonObject.addProperty("attribute", Registry.ATTRIBUTE.getKey(this.attributeName).toString());
            jsonObject.addProperty("operation", Modifier.func_216244_a(this.operation));
            jsonObject.add("amount", jsonSerializationContext.serialize(this.amount));
            if (this.uuid != null) {
                jsonObject.addProperty("id", this.uuid.toString());
            }
            if (this.slots.length == 1) {
                jsonObject.addProperty("slot", this.slots[0].getName());
            } else {
                JsonArray jsonArray = new JsonArray();
                for (EquipmentSlotType equipmentSlotType : this.slots) {
                    jsonArray.add(new JsonPrimitive(equipmentSlotType.getName()));
                }
                jsonObject.add("slot", jsonArray);
            }
            return jsonObject;
        }

        public static Modifier deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            Object object;
            EquipmentSlotType[] equipmentSlotTypeArray;
            String string = JSONUtils.getString(jsonObject, "name");
            ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "attribute"));
            Attribute attribute = Registry.ATTRIBUTE.getOrDefault(resourceLocation);
            if (attribute == null) {
                throw new JsonSyntaxException("Unknown attribute: " + resourceLocation);
            }
            AttributeModifier.Operation operation = Modifier.func_216246_a(JSONUtils.getString(jsonObject, "operation"));
            RandomValueRange randomValueRange = JSONUtils.deserializeClass(jsonObject, "amount", jsonDeserializationContext, RandomValueRange.class);
            UUID uUID = null;
            if (JSONUtils.isString(jsonObject, "slot")) {
                equipmentSlotTypeArray = new EquipmentSlotType[]{EquipmentSlotType.fromString(JSONUtils.getString(jsonObject, "slot"))};
            } else {
                if (!JSONUtils.isJsonArray(jsonObject, "slot")) {
                    throw new JsonSyntaxException("Invalid or missing attribute modifier slot; must be either string or array of strings.");
                }
                object = JSONUtils.getJsonArray(jsonObject, "slot");
                equipmentSlotTypeArray = new EquipmentSlotType[((JsonArray)object).size()];
                int n = 0;
                Iterator<JsonElement> iterator2 = ((JsonArray)object).iterator();
                while (iterator2.hasNext()) {
                    JsonElement jsonElement = iterator2.next();
                    equipmentSlotTypeArray[n++] = EquipmentSlotType.fromString(JSONUtils.getString(jsonElement, "slot"));
                }
                if (equipmentSlotTypeArray.length == 0) {
                    throw new JsonSyntaxException("Invalid attribute modifier slot; must contain at least one entry.");
                }
            }
            if (jsonObject.has("id")) {
                object = JSONUtils.getString(jsonObject, "id");
                try {
                    uUID = UUID.fromString((String)object);
                } catch (IllegalArgumentException illegalArgumentException) {
                    throw new JsonSyntaxException("Invalid attribute modifier id '" + (String)object + "' (must be UUID format, with dashes)");
                }
            }
            return new Modifier(string, attribute, operation, randomValueRange, equipmentSlotTypeArray, uUID);
        }

        private static String func_216244_a(AttributeModifier.Operation operation) {
            switch (operation) {
                case ADDITION: {
                    return "addition";
                }
                case MULTIPLY_BASE: {
                    return "multiply_base";
                }
                case MULTIPLY_TOTAL: {
                    return "multiply_total";
                }
            }
            throw new IllegalArgumentException("Unknown operation " + operation);
        }

        private static AttributeModifier.Operation func_216246_a(String string) {
            int n = -1;
            switch (string.hashCode()) {
                case -1226589444: {
                    if (!string.equals("addition")) break;
                    n = 0;
                    break;
                }
                case -78229492: {
                    if (!string.equals("multiply_base")) break;
                    n = 1;
                    break;
                }
                case 1886894441: {
                    if (!string.equals("multiply_total")) break;
                    n = 2;
                }
            }
            switch (n) {
                case 0: {
                    return AttributeModifier.Operation.ADDITION;
                }
                case 1: {
                    return AttributeModifier.Operation.MULTIPLY_BASE;
                }
                case 2: {
                    return AttributeModifier.Operation.MULTIPLY_TOTAL;
                }
            }
            throw new JsonSyntaxException("Unknown attribute modifier operation " + string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends LootFunction.Serializer<SetAttributes> {
        @Override
        public void serialize(JsonObject jsonObject, SetAttributes setAttributes, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, setAttributes, jsonSerializationContext);
            JsonArray jsonArray = new JsonArray();
            for (Modifier modifier : setAttributes.modifiers) {
                jsonArray.add(modifier.serialize(jsonSerializationContext));
            }
            jsonObject.add("modifiers", jsonArray);
        }

        @Override
        public SetAttributes deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            JsonArray jsonArray = JSONUtils.getJsonArray(jsonObject, "modifiers");
            ArrayList<Modifier> arrayList = Lists.newArrayListWithExpectedSize(jsonArray.size());
            for (JsonElement jsonElement : jsonArray) {
                arrayList.add(Modifier.deserialize(JSONUtils.getJsonObject(jsonElement, "modifier"), jsonDeserializationContext));
            }
            if (arrayList.isEmpty()) {
                throw new JsonSyntaxException("Invalid attribute modifiers array; cannot be empty");
            }
            return new SetAttributes(iLootConditionArray, arrayList);
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void serialize(JsonObject jsonObject, LootFunction lootFunction, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (SetAttributes)lootFunction, jsonSerializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (SetAttributes)object, jsonSerializationContext);
        }
    }
}

