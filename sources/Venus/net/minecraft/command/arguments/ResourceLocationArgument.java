/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.advancements.Advancement;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.loot.LootPredicateManager;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ResourceLocationArgument
implements ArgumentType<ResourceLocation> {
    private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo:bar", "012");
    private static final DynamicCommandExceptionType ADVANCEMENT_NOT_FOUND = new DynamicCommandExceptionType(ResourceLocationArgument::lambda$static$0);
    private static final DynamicCommandExceptionType RECIPE_NOT_FOUND = new DynamicCommandExceptionType(ResourceLocationArgument::lambda$static$1);
    private static final DynamicCommandExceptionType field_228258_d_ = new DynamicCommandExceptionType(ResourceLocationArgument::lambda$static$2);
    private static final DynamicCommandExceptionType field_239090_e_ = new DynamicCommandExceptionType(ResourceLocationArgument::lambda$static$3);

    public static ResourceLocationArgument resourceLocation() {
        return new ResourceLocationArgument();
    }

    public static Advancement getAdvancement(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        ResourceLocation resourceLocation = commandContext.getArgument(string, ResourceLocation.class);
        Advancement advancement = commandContext.getSource().getServer().getAdvancementManager().getAdvancement(resourceLocation);
        if (advancement == null) {
            throw ADVANCEMENT_NOT_FOUND.create(resourceLocation);
        }
        return advancement;
    }

    public static IRecipe<?> getRecipe(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        RecipeManager recipeManager = commandContext.getSource().getServer().getRecipeManager();
        ResourceLocation resourceLocation = commandContext.getArgument(string, ResourceLocation.class);
        return recipeManager.getRecipe(resourceLocation).orElseThrow(() -> ResourceLocationArgument.lambda$getRecipe$4(resourceLocation));
    }

    public static ILootCondition func_228259_c_(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        ResourceLocation resourceLocation = commandContext.getArgument(string, ResourceLocation.class);
        LootPredicateManager lootPredicateManager = commandContext.getSource().getServer().func_229736_aP_();
        ILootCondition iLootCondition = lootPredicateManager.func_227517_a_(resourceLocation);
        if (iLootCondition == null) {
            throw field_228258_d_.create(resourceLocation);
        }
        return iLootCondition;
    }

    public static Attribute func_239094_d_(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        ResourceLocation resourceLocation = commandContext.getArgument(string, ResourceLocation.class);
        return Registry.ATTRIBUTE.getOptional(resourceLocation).orElseThrow(() -> ResourceLocationArgument.lambda$func_239094_d_$5(resourceLocation));
    }

    public static ResourceLocation getResourceLocation(CommandContext<CommandSource> commandContext, String string) {
        return commandContext.getArgument(string, ResourceLocation.class);
    }

    @Override
    public ResourceLocation parse(StringReader stringReader) throws CommandSyntaxException {
        return ResourceLocation.read(stringReader);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    private static CommandSyntaxException lambda$func_239094_d_$5(ResourceLocation resourceLocation) {
        return field_239090_e_.create(resourceLocation);
    }

    private static CommandSyntaxException lambda$getRecipe$4(ResourceLocation resourceLocation) {
        return RECIPE_NOT_FOUND.create(resourceLocation);
    }

    private static Message lambda$static$3(Object object) {
        return new TranslationTextComponent("attribute.unknown", object);
    }

    private static Message lambda$static$2(Object object) {
        return new TranslationTextComponent("predicate.unknown", object);
    }

    private static Message lambda$static$1(Object object) {
        return new TranslationTextComponent("recipe.notFound", object);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("advancement.advancementNotFound", object);
    }
}

