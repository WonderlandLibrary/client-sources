/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.command.arguments.NBTPathArgument;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.JSONUtils;

public class CopyNbt
extends LootFunction {
    private final Source field_215887_a;
    private final List<Operation> field_215888_c;
    private static final Function<Entity, INBT> field_215889_d = NBTPredicate::writeToNBTWithSelectedItem;
    private static final Function<TileEntity, INBT> field_215890_e = CopyNbt::lambda$static$0;

    private CopyNbt(ILootCondition[] iLootConditionArray, Source source, List<Operation> list) {
        super(iLootConditionArray);
        this.field_215887_a = source;
        this.field_215888_c = ImmutableList.copyOf(list);
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.COPY_NBT;
    }

    private static NBTPathArgument.NBTPath parsePath(String string) {
        try {
            return new NBTPathArgument().parse(new StringReader(string));
        } catch (CommandSyntaxException commandSyntaxException) {
            throw new IllegalArgumentException("Failed to parse path " + string, commandSyntaxException);
        }
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(this.field_215887_a.lootParam);
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        INBT iNBT = this.field_215887_a.field_216226_g.apply(lootContext);
        if (iNBT != null) {
            this.field_215888_c.forEach(arg_0 -> CopyNbt.lambda$doApply$1(itemStack, iNBT, arg_0));
        }
        return itemStack;
    }

    public static Builder builder(Source source) {
        return new Builder(source);
    }

    private static void lambda$doApply$1(ItemStack itemStack, INBT iNBT, Operation operation) {
        operation.func_216216_a(itemStack::getOrCreateTag, iNBT);
    }

    private static INBT lambda$static$0(TileEntity tileEntity) {
        return tileEntity.write(new CompoundNBT());
    }

    public static enum Source {
        THIS("this", LootParameters.THIS_ENTITY, field_215889_d),
        KILLER("killer", LootParameters.KILLER_ENTITY, field_215889_d),
        KILLER_PLAYER("killer_player", LootParameters.LAST_DAMAGE_PLAYER, field_215889_d),
        BLOCK_ENTITY("block_entity", LootParameters.BLOCK_ENTITY, field_215890_e);

        public final String sourceName;
        public final LootParameter<?> lootParam;
        public final Function<LootContext, INBT> field_216226_g;

        private <T> Source(String string2, LootParameter<T> lootParameter, Function<? super T, INBT> function) {
            this.sourceName = string2;
            this.lootParam = lootParameter;
            this.field_216226_g = arg_0 -> Source.lambda$new$0(lootParameter, function, arg_0);
        }

        public static Source getByName(String string) {
            for (Source source : Source.values()) {
                if (!source.sourceName.equals(string)) continue;
                return source;
            }
            throw new IllegalArgumentException("Invalid tag source " + string);
        }

        private static INBT lambda$new$0(LootParameter lootParameter, Function function, LootContext lootContext) {
            Object t = lootContext.get(lootParameter);
            return t != null ? (INBT)function.apply(t) : null;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    extends LootFunction.Builder<Builder> {
        private final Source source;
        private final List<Operation> operations = Lists.newArrayList();

        private Builder(Source source) {
            this.source = source;
        }

        public Builder addOperation(String string, String string2, Action action) {
            this.operations.add(new Operation(string, string2, action));
            return this;
        }

        public Builder replaceOperation(String string, String string2) {
            return this.addOperation(string, string2, Action.REPLACE);
        }

        @Override
        protected Builder doCast() {
            return this;
        }

        @Override
        public ILootFunction build() {
            return new CopyNbt(this.getConditions(), this.source, this.operations);
        }

        @Override
        protected LootFunction.Builder doCast() {
            return this.doCast();
        }
    }

    static class Operation {
        private final String source;
        private final NBTPathArgument.NBTPath field_216218_b;
        private final String target;
        private final NBTPathArgument.NBTPath field_216220_d;
        private final Action action;

        private Operation(String string, String string2, Action action) {
            this.source = string;
            this.field_216218_b = CopyNbt.parsePath(string);
            this.target = string2;
            this.field_216220_d = CopyNbt.parsePath(string2);
            this.action = action;
        }

        public void func_216216_a(Supplier<INBT> supplier, INBT iNBT) {
            try {
                List<INBT> list = this.field_216218_b.func_218071_a(iNBT);
                if (!list.isEmpty()) {
                    this.action.runAction(supplier.get(), this.field_216220_d, list);
                }
            } catch (CommandSyntaxException commandSyntaxException) {
                // empty catch block
            }
        }

        public JsonObject serialize() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("source", this.source);
            jsonObject.addProperty("target", this.target);
            jsonObject.addProperty("op", this.action.op);
            return jsonObject;
        }

        public static Operation deserialize(JsonObject jsonObject) {
            String string = JSONUtils.getString(jsonObject, "source");
            String string2 = JSONUtils.getString(jsonObject, "target");
            Action action = Action.getByName(JSONUtils.getString(jsonObject, "op"));
            return new Operation(string, string2, action);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends LootFunction.Serializer<CopyNbt> {
        @Override
        public void serialize(JsonObject jsonObject, CopyNbt copyNbt, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, copyNbt, jsonSerializationContext);
            jsonObject.addProperty("source", copyNbt.field_215887_a.sourceName);
            JsonArray jsonArray = new JsonArray();
            copyNbt.field_215888_c.stream().map(Operation::serialize).forEach(jsonArray::add);
            jsonObject.add("ops", jsonArray);
        }

        @Override
        public CopyNbt deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            Source source = Source.getByName(JSONUtils.getString(jsonObject, "source"));
            ArrayList<Operation> arrayList = Lists.newArrayList();
            for (JsonElement jsonElement : JSONUtils.getJsonArray(jsonObject, "ops")) {
                JsonObject jsonObject2 = JSONUtils.getJsonObject(jsonElement, "op");
                arrayList.add(Operation.deserialize(jsonObject2));
            }
            return new CopyNbt(iLootConditionArray, source, arrayList);
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void serialize(JsonObject jsonObject, LootFunction lootFunction, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (CopyNbt)lootFunction, jsonSerializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (CopyNbt)object, jsonSerializationContext);
        }
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static enum Action {
        REPLACE("replace"){

            @Override
            public void runAction(INBT iNBT, NBTPathArgument.NBTPath nBTPath, List<INBT> list) throws CommandSyntaxException {
                nBTPath.func_218076_b(iNBT, Iterables.getLast(list)::copy);
            }
        }
        ,
        APPEND("append"){

            @Override
            public void runAction(INBT iNBT, NBTPathArgument.NBTPath nBTPath, List<INBT> list) throws CommandSyntaxException {
                List<INBT> list2 = nBTPath.func_218073_a(iNBT, ListNBT::new);
                list2.forEach(arg_0 -> 2.lambda$runAction$1(list, arg_0));
            }

            private static void lambda$runAction$1(List list, INBT iNBT) {
                if (iNBT instanceof ListNBT) {
                    list.forEach(arg_0 -> 2.lambda$runAction$0(iNBT, arg_0));
                }
            }

            private static void lambda$runAction$0(INBT iNBT, INBT iNBT2) {
                ((ListNBT)iNBT).add(iNBT2.copy());
            }
        }
        ,
        MERGE("merge"){

            @Override
            public void runAction(INBT iNBT, NBTPathArgument.NBTPath nBTPath, List<INBT> list) throws CommandSyntaxException {
                List<INBT> list2 = nBTPath.func_218073_a(iNBT, CompoundNBT::new);
                list2.forEach(arg_0 -> 3.lambda$runAction$1(list, arg_0));
            }

            private static void lambda$runAction$1(List list, INBT iNBT) {
                if (iNBT instanceof CompoundNBT) {
                    list.forEach(arg_0 -> 3.lambda$runAction$0(iNBT, arg_0));
                }
            }

            private static void lambda$runAction$0(INBT iNBT, INBT iNBT2) {
                if (iNBT2 instanceof CompoundNBT) {
                    ((CompoundNBT)iNBT).merge((CompoundNBT)iNBT2);
                }
            }
        };

        private final String op;

        public abstract void runAction(INBT var1, NBTPathArgument.NBTPath var2, List<INBT> var3) throws CommandSyntaxException;

        private Action(String string2) {
            this.op = string2;
        }

        public static Action getByName(String string) {
            for (Action action : Action.values()) {
                if (!action.op.equals(string)) continue;
                return action;
            }
            throw new IllegalArgumentException("Invalid merge strategy" + string);
        }
    }
}

