/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.google.common.collect.Maps;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SlotArgument
implements ArgumentType<Integer> {
    private static final Collection<String> EXAMPLES = Arrays.asList("container.5", "12", "weapon");
    private static final DynamicCommandExceptionType SLOT_UNKNOWN = new DynamicCommandExceptionType(SlotArgument::lambda$static$0);
    private static final Map<String, Integer> KNOWN_SLOTS = Util.make(Maps.newHashMap(), SlotArgument::lambda$static$1);

    public static SlotArgument slot() {
        return new SlotArgument();
    }

    public static int getSlot(CommandContext<CommandSource> commandContext, String string) {
        return commandContext.getArgument(string, Integer.class);
    }

    @Override
    public Integer parse(StringReader stringReader) throws CommandSyntaxException {
        String string = stringReader.readUnquotedString();
        if (!KNOWN_SLOTS.containsKey(string)) {
            throw SLOT_UNKNOWN.create(string);
        }
        return KNOWN_SLOTS.get(string);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        return ISuggestionProvider.suggest(KNOWN_SLOTS.keySet(), suggestionsBuilder);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    private static void lambda$static$1(HashMap hashMap) {
        int n;
        for (n = 0; n < 54; ++n) {
            hashMap.put("container." + n, n);
        }
        for (n = 0; n < 9; ++n) {
            hashMap.put("hotbar." + n, n);
        }
        for (n = 0; n < 27; ++n) {
            hashMap.put("inventory." + n, 9 + n);
        }
        for (n = 0; n < 27; ++n) {
            hashMap.put("enderchest." + n, 200 + n);
        }
        for (n = 0; n < 8; ++n) {
            hashMap.put("villager." + n, 300 + n);
        }
        for (n = 0; n < 15; ++n) {
            hashMap.put("horse." + n, 500 + n);
        }
        hashMap.put("weapon", 98);
        hashMap.put("weapon.mainhand", 98);
        hashMap.put("weapon.offhand", 99);
        hashMap.put("armor.head", 100 + EquipmentSlotType.HEAD.getIndex());
        hashMap.put("armor.chest", 100 + EquipmentSlotType.CHEST.getIndex());
        hashMap.put("armor.legs", 100 + EquipmentSlotType.LEGS.getIndex());
        hashMap.put("armor.feet", 100 + EquipmentSlotType.FEET.getIndex());
        hashMap.put("horse.saddle", 400);
        hashMap.put("horse.armor", 401);
        hashMap.put("horse.chest", 499);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("slot.unknown", object);
    }
}

