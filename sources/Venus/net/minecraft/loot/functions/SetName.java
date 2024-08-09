/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Set;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetName
extends LootFunction {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ITextComponent name;
    @Nullable
    private final LootContext.EntityTarget field_215940_d;

    private SetName(ILootCondition[] iLootConditionArray, @Nullable ITextComponent iTextComponent, @Nullable LootContext.EntityTarget entityTarget) {
        super(iLootConditionArray);
        this.name = iTextComponent;
        this.field_215940_d = entityTarget;
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.SET_NAME;
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return this.field_215940_d != null ? ImmutableSet.of(this.field_215940_d.getParameter()) : ImmutableSet.of();
    }

    public static UnaryOperator<ITextComponent> func_215936_a(LootContext lootContext, @Nullable LootContext.EntityTarget entityTarget) {
        Entity entity2;
        if (entityTarget != null && (entity2 = lootContext.get(entityTarget.getParameter())) != null) {
            CommandSource commandSource = entity2.getCommandSource().withPermissionLevel(2);
            return arg_0 -> SetName.lambda$func_215936_a$0(commandSource, entity2, arg_0);
        }
        return SetName::lambda$func_215936_a$1;
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        if (this.name != null) {
            itemStack.setDisplayName((ITextComponent)SetName.func_215936_a(lootContext, this.field_215940_d).apply(this.name));
        }
        return itemStack;
    }

    private static ITextComponent lambda$func_215936_a$1(ITextComponent iTextComponent) {
        return iTextComponent;
    }

    private static ITextComponent lambda$func_215936_a$0(CommandSource commandSource, Entity entity2, ITextComponent iTextComponent) {
        try {
            return TextComponentUtils.func_240645_a_(commandSource, iTextComponent, entity2, 0);
        } catch (CommandSyntaxException commandSyntaxException) {
            LOGGER.warn("Failed to resolve text component", (Throwable)commandSyntaxException);
            return iTextComponent;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends LootFunction.Serializer<SetName> {
        @Override
        public void serialize(JsonObject jsonObject, SetName setName, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, setName, jsonSerializationContext);
            if (setName.name != null) {
                jsonObject.add("name", ITextComponent.Serializer.toJsonTree(setName.name));
            }
            if (setName.field_215940_d != null) {
                jsonObject.add("entity", jsonSerializationContext.serialize((Object)setName.field_215940_d));
            }
        }

        @Override
        public SetName deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            IFormattableTextComponent iFormattableTextComponent = ITextComponent.Serializer.getComponentFromJson(jsonObject.get("name"));
            LootContext.EntityTarget entityTarget = JSONUtils.deserializeClass(jsonObject, "entity", null, jsonDeserializationContext, LootContext.EntityTarget.class);
            return new SetName(iLootConditionArray, iFormattableTextComponent, entityTarget);
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void serialize(JsonObject jsonObject, LootFunction lootFunction, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (SetName)lootFunction, jsonSerializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (SetName)object, jsonSerializationContext);
        }
    }
}

