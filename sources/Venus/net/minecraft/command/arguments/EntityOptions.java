/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.google.common.collect.Maps;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.MinMaxBoundsWrapped;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntitySelectorParser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.server.ServerWorld;

public class EntityOptions {
    private static final Map<String, OptionHandler> REGISTRY = Maps.newHashMap();
    public static final DynamicCommandExceptionType UNKNOWN_ENTITY_OPTION = new DynamicCommandExceptionType(EntityOptions::lambda$static$0);
    public static final DynamicCommandExceptionType INAPPLICABLE_ENTITY_OPTION = new DynamicCommandExceptionType(EntityOptions::lambda$static$1);
    public static final SimpleCommandExceptionType NEGATIVE_DISTANCE = new SimpleCommandExceptionType(new TranslationTextComponent("argument.entity.options.distance.negative"));
    public static final SimpleCommandExceptionType NEGATIVE_LEVEL = new SimpleCommandExceptionType(new TranslationTextComponent("argument.entity.options.level.negative"));
    public static final SimpleCommandExceptionType NONPOSITIVE_LIMIT = new SimpleCommandExceptionType(new TranslationTextComponent("argument.entity.options.limit.toosmall"));
    public static final DynamicCommandExceptionType INVALID_SORT = new DynamicCommandExceptionType(EntityOptions::lambda$static$2);
    public static final DynamicCommandExceptionType INVALID_GAME_MODE = new DynamicCommandExceptionType(EntityOptions::lambda$static$3);
    public static final DynamicCommandExceptionType INVALID_ENTITY_TYPE = new DynamicCommandExceptionType(EntityOptions::lambda$static$4);

    private static void register(String string, IFilter iFilter, Predicate<EntitySelectorParser> predicate, ITextComponent iTextComponent) {
        REGISTRY.put(string, new OptionHandler(iFilter, predicate, iTextComponent));
    }

    public static void registerOptions() {
        if (REGISTRY.isEmpty()) {
            EntityOptions.register("name", EntityOptions::lambda$registerOptions$6, EntityOptions::lambda$registerOptions$7, new TranslationTextComponent("argument.entity.options.name.description"));
            EntityOptions.register("distance", EntityOptions::lambda$registerOptions$8, EntityOptions::lambda$registerOptions$9, new TranslationTextComponent("argument.entity.options.distance.description"));
            EntityOptions.register("level", EntityOptions::lambda$registerOptions$10, EntityOptions::lambda$registerOptions$11, new TranslationTextComponent("argument.entity.options.level.description"));
            EntityOptions.register("x", EntityOptions::lambda$registerOptions$12, EntityOptions::lambda$registerOptions$13, new TranslationTextComponent("argument.entity.options.x.description"));
            EntityOptions.register("y", EntityOptions::lambda$registerOptions$14, EntityOptions::lambda$registerOptions$15, new TranslationTextComponent("argument.entity.options.y.description"));
            EntityOptions.register("z", EntityOptions::lambda$registerOptions$16, EntityOptions::lambda$registerOptions$17, new TranslationTextComponent("argument.entity.options.z.description"));
            EntityOptions.register("dx", EntityOptions::lambda$registerOptions$18, EntityOptions::lambda$registerOptions$19, new TranslationTextComponent("argument.entity.options.dx.description"));
            EntityOptions.register("dy", EntityOptions::lambda$registerOptions$20, EntityOptions::lambda$registerOptions$21, new TranslationTextComponent("argument.entity.options.dy.description"));
            EntityOptions.register("dz", EntityOptions::lambda$registerOptions$22, EntityOptions::lambda$registerOptions$23, new TranslationTextComponent("argument.entity.options.dz.description"));
            EntityOptions.register("x_rotation", EntityOptions::lambda$registerOptions$24, EntityOptions::lambda$registerOptions$25, new TranslationTextComponent("argument.entity.options.x_rotation.description"));
            EntityOptions.register("y_rotation", EntityOptions::lambda$registerOptions$26, EntityOptions::lambda$registerOptions$27, new TranslationTextComponent("argument.entity.options.y_rotation.description"));
            EntityOptions.register("limit", EntityOptions::lambda$registerOptions$28, EntityOptions::lambda$registerOptions$29, new TranslationTextComponent("argument.entity.options.limit.description"));
            EntityOptions.register("sort", EntityOptions::lambda$registerOptions$31, EntityOptions::lambda$registerOptions$32, new TranslationTextComponent("argument.entity.options.sort.description"));
            EntityOptions.register("gamemode", EntityOptions::lambda$registerOptions$35, EntityOptions::lambda$registerOptions$36, new TranslationTextComponent("argument.entity.options.gamemode.description"));
            EntityOptions.register("team", EntityOptions::lambda$registerOptions$38, EntityOptions::lambda$registerOptions$39, new TranslationTextComponent("argument.entity.options.team.description"));
            EntityOptions.register("type", EntityOptions::lambda$registerOptions$44, EntityOptions::lambda$registerOptions$45, new TranslationTextComponent("argument.entity.options.type.description"));
            EntityOptions.register("tag", EntityOptions::lambda$registerOptions$47, EntityOptions::lambda$registerOptions$48, new TranslationTextComponent("argument.entity.options.tag.description"));
            EntityOptions.register("nbt", EntityOptions::lambda$registerOptions$50, EntityOptions::lambda$registerOptions$51, new TranslationTextComponent("argument.entity.options.nbt.description"));
            EntityOptions.register("scores", EntityOptions::lambda$registerOptions$53, EntityOptions::lambda$registerOptions$54, new TranslationTextComponent("argument.entity.options.scores.description"));
            EntityOptions.register("advancements", EntityOptions::lambda$registerOptions$59, EntityOptions::lambda$registerOptions$60, new TranslationTextComponent("argument.entity.options.advancements.description"));
            EntityOptions.register("predicate", EntityOptions::lambda$registerOptions$62, EntityOptions::lambda$registerOptions$63, new TranslationTextComponent("argument.entity.options.predicate.description"));
        }
    }

    public static IFilter get(EntitySelectorParser entitySelectorParser, String string, int n) throws CommandSyntaxException {
        OptionHandler optionHandler = REGISTRY.get(string);
        if (optionHandler != null) {
            if (optionHandler.canHandle.test(entitySelectorParser)) {
                return optionHandler.handler;
            }
            throw INAPPLICABLE_ENTITY_OPTION.createWithContext(entitySelectorParser.getReader(), string);
        }
        entitySelectorParser.getReader().setCursor(n);
        throw UNKNOWN_ENTITY_OPTION.createWithContext(entitySelectorParser.getReader(), string);
    }

    public static void suggestOptions(EntitySelectorParser entitySelectorParser, SuggestionsBuilder suggestionsBuilder) {
        String string = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
        for (Map.Entry<String, OptionHandler> entry : REGISTRY.entrySet()) {
            if (!entry.getValue().canHandle.test(entitySelectorParser) || !entry.getKey().toLowerCase(Locale.ROOT).startsWith(string)) continue;
            suggestionsBuilder.suggest(entry.getKey() + "=", (Message)entry.getValue().tooltip);
        }
    }

    private static boolean lambda$registerOptions$63(EntitySelectorParser entitySelectorParser) {
        return false;
    }

    private static void lambda$registerOptions$62(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        boolean bl = entitySelectorParser.shouldInvertValue();
        ResourceLocation resourceLocation = ResourceLocation.read(entitySelectorParser.getReader());
        entitySelectorParser.addFilter(arg_0 -> EntityOptions.lambda$registerOptions$61(resourceLocation, bl, arg_0));
    }

    private static boolean lambda$registerOptions$61(ResourceLocation resourceLocation, boolean bl, Entity entity2) {
        if (!(entity2.world instanceof ServerWorld)) {
            return true;
        }
        ServerWorld serverWorld = (ServerWorld)entity2.world;
        ILootCondition iLootCondition = serverWorld.getServer().func_229736_aP_().func_227517_a_(resourceLocation);
        if (iLootCondition == null) {
            return true;
        }
        LootContext lootContext = new LootContext.Builder(serverWorld).withParameter(LootParameters.THIS_ENTITY, entity2).withParameter(LootParameters.field_237457_g_, entity2.getPositionVec()).build(LootParameterSets.SELECTOR);
        return bl ^ iLootCondition.test(lootContext);
    }

    private static boolean lambda$registerOptions$60(EntitySelectorParser entitySelectorParser) {
        return !entitySelectorParser.hasAdvancements();
    }

    private static void lambda$registerOptions$59(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        StringReader stringReader = entitySelectorParser.getReader();
        HashMap<ResourceLocation, Predicate<AdvancementProgress>> hashMap = Maps.newHashMap();
        stringReader.expect('{');
        stringReader.skipWhitespace();
        while (stringReader.canRead() && stringReader.peek() != '}') {
            stringReader.skipWhitespace();
            ResourceLocation resourceLocation = ResourceLocation.read(stringReader);
            stringReader.skipWhitespace();
            stringReader.expect('=');
            stringReader.skipWhitespace();
            if (stringReader.canRead() && stringReader.peek() == '{') {
                HashMap<String, Predicate<CriterionProgress>> hashMap2 = Maps.newHashMap();
                stringReader.skipWhitespace();
                stringReader.expect('{');
                stringReader.skipWhitespace();
                while (stringReader.canRead() && stringReader.peek() != '}') {
                    stringReader.skipWhitespace();
                    String string = stringReader.readUnquotedString();
                    stringReader.skipWhitespace();
                    stringReader.expect('=');
                    stringReader.skipWhitespace();
                    boolean bl = stringReader.readBoolean();
                    hashMap2.put(string, arg_0 -> EntityOptions.lambda$registerOptions$55(bl, arg_0));
                    stringReader.skipWhitespace();
                    if (!stringReader.canRead() || stringReader.peek() != ',') continue;
                    stringReader.skip();
                }
                stringReader.skipWhitespace();
                stringReader.expect('}');
                stringReader.skipWhitespace();
                hashMap.put(resourceLocation, arg_0 -> EntityOptions.lambda$registerOptions$56(hashMap2, arg_0));
            } else {
                boolean bl = stringReader.readBoolean();
                hashMap.put(resourceLocation, arg_0 -> EntityOptions.lambda$registerOptions$57(bl, arg_0));
            }
            stringReader.skipWhitespace();
            if (!stringReader.canRead() || stringReader.peek() != ',') continue;
            stringReader.skip();
        }
        stringReader.expect('}');
        if (!hashMap.isEmpty()) {
            entitySelectorParser.addFilter(arg_0 -> EntityOptions.lambda$registerOptions$58(hashMap, arg_0));
            entitySelectorParser.setIncludeNonPlayers(true);
        }
        entitySelectorParser.setHasAdvancements(false);
    }

    private static boolean lambda$registerOptions$58(Map map, Entity entity2) {
        if (!(entity2 instanceof ServerPlayerEntity)) {
            return true;
        }
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity2;
        PlayerAdvancements playerAdvancements = serverPlayerEntity.getAdvancements();
        AdvancementManager advancementManager = serverPlayerEntity.getServer().getAdvancementManager();
        for (Map.Entry entry : map.entrySet()) {
            Advancement advancement = advancementManager.getAdvancement((ResourceLocation)entry.getKey());
            if (advancement != null && ((Predicate)entry.getValue()).test(playerAdvancements.getProgress(advancement))) continue;
            return true;
        }
        return false;
    }

    private static boolean lambda$registerOptions$57(boolean bl, AdvancementProgress advancementProgress) {
        return advancementProgress.isDone() == bl;
    }

    private static boolean lambda$registerOptions$56(Map map, AdvancementProgress advancementProgress) {
        for (Map.Entry entry : map.entrySet()) {
            CriterionProgress criterionProgress = advancementProgress.getCriterionProgress((String)entry.getKey());
            if (criterionProgress != null && ((Predicate)entry.getValue()).test(criterionProgress)) continue;
            return true;
        }
        return false;
    }

    private static boolean lambda$registerOptions$55(boolean bl, CriterionProgress criterionProgress) {
        return criterionProgress.isObtained() == bl;
    }

    private static boolean lambda$registerOptions$54(EntitySelectorParser entitySelectorParser) {
        return !entitySelectorParser.hasScores();
    }

    private static void lambda$registerOptions$53(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        StringReader stringReader = entitySelectorParser.getReader();
        HashMap<String, MinMaxBounds.IntBound> hashMap = Maps.newHashMap();
        stringReader.expect('{');
        stringReader.skipWhitespace();
        while (stringReader.canRead() && stringReader.peek() != '}') {
            stringReader.skipWhitespace();
            String string = stringReader.readUnquotedString();
            stringReader.skipWhitespace();
            stringReader.expect('=');
            stringReader.skipWhitespace();
            MinMaxBounds.IntBound intBound = MinMaxBounds.IntBound.fromReader(stringReader);
            hashMap.put(string, intBound);
            stringReader.skipWhitespace();
            if (!stringReader.canRead() || stringReader.peek() != ',') continue;
            stringReader.skip();
        }
        stringReader.expect('}');
        if (!hashMap.isEmpty()) {
            entitySelectorParser.addFilter(arg_0 -> EntityOptions.lambda$registerOptions$52(hashMap, arg_0));
        }
        entitySelectorParser.setHasScores(false);
    }

    private static boolean lambda$registerOptions$52(Map map, Entity entity2) {
        ServerScoreboard serverScoreboard = entity2.getServer().getScoreboard();
        String string = entity2.getScoreboardName();
        for (Map.Entry entry : map.entrySet()) {
            ScoreObjective scoreObjective = serverScoreboard.getObjective((String)entry.getKey());
            if (scoreObjective == null) {
                return true;
            }
            if (!serverScoreboard.entityHasObjective(string, scoreObjective)) {
                return true;
            }
            Score score = serverScoreboard.getOrCreateScore(string, scoreObjective);
            int n = score.getScorePoints();
            if (((MinMaxBounds.IntBound)entry.getValue()).test(n)) continue;
            return true;
        }
        return false;
    }

    private static boolean lambda$registerOptions$51(EntitySelectorParser entitySelectorParser) {
        return false;
    }

    private static void lambda$registerOptions$50(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        boolean bl = entitySelectorParser.shouldInvertValue();
        CompoundNBT compoundNBT = new JsonToNBT(entitySelectorParser.getReader()).readStruct();
        entitySelectorParser.addFilter(arg_0 -> EntityOptions.lambda$registerOptions$49(compoundNBT, bl, arg_0));
    }

    private static boolean lambda$registerOptions$49(CompoundNBT compoundNBT, boolean bl, Entity entity2) {
        ItemStack itemStack;
        CompoundNBT compoundNBT2 = entity2.writeWithoutTypeId(new CompoundNBT());
        if (entity2 instanceof ServerPlayerEntity && !(itemStack = ((ServerPlayerEntity)entity2).inventory.getCurrentItem()).isEmpty()) {
            compoundNBT2.put("SelectedItem", itemStack.write(new CompoundNBT()));
        }
        return NBTUtil.areNBTEquals(compoundNBT, compoundNBT2, true) != bl;
    }

    private static boolean lambda$registerOptions$48(EntitySelectorParser entitySelectorParser) {
        return false;
    }

    private static void lambda$registerOptions$47(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        boolean bl = entitySelectorParser.shouldInvertValue();
        String string = entitySelectorParser.getReader().readUnquotedString();
        entitySelectorParser.addFilter(arg_0 -> EntityOptions.lambda$registerOptions$46(string, bl, arg_0));
    }

    private static boolean lambda$registerOptions$46(String string, boolean bl, Entity entity2) {
        if ("".equals(string)) {
            return entity2.getTags().isEmpty() != bl;
        }
        return entity2.getTags().contains(string) != bl;
    }

    private static boolean lambda$registerOptions$45(EntitySelectorParser entitySelectorParser) {
        return !entitySelectorParser.isTypeLimited();
    }

    private static void lambda$registerOptions$44(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        entitySelectorParser.setSuggestionHandler((arg_0, arg_1) -> EntityOptions.lambda$registerOptions$40(entitySelectorParser, arg_0, arg_1));
        int n = entitySelectorParser.getReader().getCursor();
        boolean bl = entitySelectorParser.shouldInvertValue();
        if (entitySelectorParser.isTypeLimitedInversely() && !bl) {
            entitySelectorParser.getReader().setCursor(n);
            throw INAPPLICABLE_ENTITY_OPTION.createWithContext(entitySelectorParser.getReader(), "type");
        }
        if (bl) {
            entitySelectorParser.setTypeLimitedInversely();
        }
        if (entitySelectorParser.func_218115_f()) {
            ResourceLocation resourceLocation = ResourceLocation.read(entitySelectorParser.getReader());
            entitySelectorParser.addFilter(arg_0 -> EntityOptions.lambda$registerOptions$41(resourceLocation, bl, arg_0));
        } else {
            ResourceLocation resourceLocation = ResourceLocation.read(entitySelectorParser.getReader());
            EntityType<?> entityType = Registry.ENTITY_TYPE.getOptional(resourceLocation).orElseThrow(() -> EntityOptions.lambda$registerOptions$42(entitySelectorParser, n, resourceLocation));
            if (Objects.equals(EntityType.PLAYER, entityType) && !bl) {
                entitySelectorParser.setIncludeNonPlayers(true);
            }
            entitySelectorParser.addFilter(arg_0 -> EntityOptions.lambda$registerOptions$43(entityType, bl, arg_0));
            if (!bl) {
                entitySelectorParser.func_218114_a(entityType);
            }
        }
    }

    private static boolean lambda$registerOptions$43(EntityType entityType, boolean bl, Entity entity2) {
        return Objects.equals(entityType, entity2.getType()) != bl;
    }

    private static CommandSyntaxException lambda$registerOptions$42(EntitySelectorParser entitySelectorParser, int n, ResourceLocation resourceLocation) {
        entitySelectorParser.getReader().setCursor(n);
        return INVALID_ENTITY_TYPE.createWithContext(entitySelectorParser.getReader(), resourceLocation.toString());
    }

    private static boolean lambda$registerOptions$41(ResourceLocation resourceLocation, boolean bl, Entity entity2) {
        return entity2.getServer().func_244266_aF().getEntityTypeTags().getTagByID(resourceLocation).contains(entity2.getType()) != bl;
    }

    private static CompletableFuture lambda$registerOptions$40(EntitySelectorParser entitySelectorParser, SuggestionsBuilder suggestionsBuilder, Consumer consumer) {
        ISuggestionProvider.suggestIterable(Registry.ENTITY_TYPE.keySet(), suggestionsBuilder, String.valueOf('!'));
        ISuggestionProvider.suggestIterable(EntityTypeTags.getCollection().getRegisteredTags(), suggestionsBuilder, "!#");
        if (!entitySelectorParser.isTypeLimitedInversely()) {
            ISuggestionProvider.suggestIterable(Registry.ENTITY_TYPE.keySet(), suggestionsBuilder);
            ISuggestionProvider.suggestIterable(EntityTypeTags.getCollection().getRegisteredTags(), suggestionsBuilder, String.valueOf('#'));
        }
        return suggestionsBuilder.buildFuture();
    }

    private static boolean lambda$registerOptions$39(EntitySelectorParser entitySelectorParser) {
        return !entitySelectorParser.hasTeamEquals();
    }

    private static void lambda$registerOptions$38(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        boolean bl = entitySelectorParser.shouldInvertValue();
        String string = entitySelectorParser.getReader().readUnquotedString();
        entitySelectorParser.addFilter(arg_0 -> EntityOptions.lambda$registerOptions$37(string, bl, arg_0));
        if (bl) {
            entitySelectorParser.setHasTeamNotEquals(false);
        } else {
            entitySelectorParser.setHasTeamEquals(false);
        }
    }

    private static boolean lambda$registerOptions$37(String string, boolean bl, Entity entity2) {
        if (!(entity2 instanceof LivingEntity)) {
            return true;
        }
        Team team = entity2.getTeam();
        String string2 = team == null ? "" : team.getName();
        return string2.equals(string) != bl;
    }

    private static boolean lambda$registerOptions$36(EntitySelectorParser entitySelectorParser) {
        return !entitySelectorParser.hasGamemodeEquals();
    }

    private static void lambda$registerOptions$35(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        entitySelectorParser.setSuggestionHandler((arg_0, arg_1) -> EntityOptions.lambda$registerOptions$33(entitySelectorParser, arg_0, arg_1));
        int n = entitySelectorParser.getReader().getCursor();
        boolean bl = entitySelectorParser.shouldInvertValue();
        if (entitySelectorParser.hasGamemodeNotEquals() && !bl) {
            entitySelectorParser.getReader().setCursor(n);
            throw INAPPLICABLE_ENTITY_OPTION.createWithContext(entitySelectorParser.getReader(), "gamemode");
        }
        String string = entitySelectorParser.getReader().readUnquotedString();
        GameType gameType = GameType.parseGameTypeWithDefault(string, GameType.NOT_SET);
        if (gameType == GameType.NOT_SET) {
            entitySelectorParser.getReader().setCursor(n);
            throw INVALID_GAME_MODE.createWithContext(entitySelectorParser.getReader(), string);
        }
        entitySelectorParser.setIncludeNonPlayers(true);
        entitySelectorParser.addFilter(arg_0 -> EntityOptions.lambda$registerOptions$34(bl, gameType, arg_0));
        if (bl) {
            entitySelectorParser.setHasGamemodeNotEquals(false);
        } else {
            entitySelectorParser.setHasGamemodeEquals(false);
        }
    }

    private static boolean lambda$registerOptions$34(boolean bl, GameType gameType, Entity entity2) {
        if (!(entity2 instanceof ServerPlayerEntity)) {
            return true;
        }
        GameType gameType2 = ((ServerPlayerEntity)entity2).interactionManager.getGameType();
        return bl ? gameType2 != gameType : gameType2 == gameType;
    }

    private static CompletableFuture lambda$registerOptions$33(EntitySelectorParser entitySelectorParser, SuggestionsBuilder suggestionsBuilder, Consumer consumer) {
        String string = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
        boolean bl = !entitySelectorParser.hasGamemodeNotEquals();
        boolean bl2 = true;
        if (!string.isEmpty()) {
            if (string.charAt(0) == '!') {
                bl = false;
                string = string.substring(1);
            } else {
                bl2 = false;
            }
        }
        for (GameType gameType : GameType.values()) {
            if (gameType == GameType.NOT_SET || !gameType.getName().toLowerCase(Locale.ROOT).startsWith(string)) continue;
            if (bl2) {
                suggestionsBuilder.suggest("!" + gameType.getName());
            }
            if (!bl) continue;
            suggestionsBuilder.suggest(gameType.getName());
        }
        return suggestionsBuilder.buildFuture();
    }

    private static boolean lambda$registerOptions$32(EntitySelectorParser entitySelectorParser) {
        return !entitySelectorParser.isCurrentEntity() && !entitySelectorParser.isSorted();
    }

    private static void lambda$registerOptions$31(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        int n = entitySelectorParser.getReader().getCursor();
        String string = entitySelectorParser.getReader().readUnquotedString();
        entitySelectorParser.setSuggestionHandler(EntityOptions::lambda$registerOptions$30);
        int n2 = -1;
        switch (string.hashCode()) {
            case -938285885: {
                if (!string.equals("random")) break;
                n2 = 2;
                break;
            }
            case 1510793967: {
                if (!string.equals("furthest")) break;
                n2 = 1;
                break;
            }
            case 1780188658: {
                if (!string.equals("arbitrary")) break;
                n2 = 3;
                break;
            }
            case 1825779806: {
                if (!string.equals("nearest")) break;
                n2 = 0;
            }
        }
        entitySelectorParser.setSorter(switch (n2) {
            case 0 -> EntitySelectorParser.NEAREST;
            case 1 -> EntitySelectorParser.FURTHEST;
            case 2 -> EntitySelectorParser.RANDOM;
            case 3 -> EntitySelectorParser.ARBITRARY;
            default -> {
                entitySelectorParser.getReader().setCursor(n);
                throw INVALID_SORT.createWithContext(entitySelectorParser.getReader(), string);
            }
        });
        entitySelectorParser.setSorted(false);
    }

    private static CompletableFuture lambda$registerOptions$30(SuggestionsBuilder suggestionsBuilder, Consumer consumer) {
        return ISuggestionProvider.suggest(Arrays.asList("nearest", "furthest", "random", "arbitrary"), suggestionsBuilder);
    }

    private static boolean lambda$registerOptions$29(EntitySelectorParser entitySelectorParser) {
        return !entitySelectorParser.isCurrentEntity() && !entitySelectorParser.isLimited();
    }

    private static void lambda$registerOptions$28(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        int n = entitySelectorParser.getReader().getCursor();
        int n2 = entitySelectorParser.getReader().readInt();
        if (n2 < 1) {
            entitySelectorParser.getReader().setCursor(n);
            throw NONPOSITIVE_LIMIT.createWithContext(entitySelectorParser.getReader());
        }
        entitySelectorParser.setLimit(n2);
        entitySelectorParser.setLimited(false);
    }

    private static boolean lambda$registerOptions$27(EntitySelectorParser entitySelectorParser) {
        return entitySelectorParser.getYRotation() == MinMaxBoundsWrapped.UNBOUNDED;
    }

    private static void lambda$registerOptions$26(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        entitySelectorParser.setYRotation(MinMaxBoundsWrapped.fromReader(entitySelectorParser.getReader(), true, MathHelper::wrapDegrees));
    }

    private static boolean lambda$registerOptions$25(EntitySelectorParser entitySelectorParser) {
        return entitySelectorParser.getXRotation() == MinMaxBoundsWrapped.UNBOUNDED;
    }

    private static void lambda$registerOptions$24(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        entitySelectorParser.setXRotation(MinMaxBoundsWrapped.fromReader(entitySelectorParser.getReader(), true, MathHelper::wrapDegrees));
    }

    private static boolean lambda$registerOptions$23(EntitySelectorParser entitySelectorParser) {
        return entitySelectorParser.getDz() == null;
    }

    private static void lambda$registerOptions$22(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        entitySelectorParser.setCurrentWorldOnly();
        entitySelectorParser.setDz(entitySelectorParser.getReader().readDouble());
    }

    private static boolean lambda$registerOptions$21(EntitySelectorParser entitySelectorParser) {
        return entitySelectorParser.getDy() == null;
    }

    private static void lambda$registerOptions$20(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        entitySelectorParser.setCurrentWorldOnly();
        entitySelectorParser.setDy(entitySelectorParser.getReader().readDouble());
    }

    private static boolean lambda$registerOptions$19(EntitySelectorParser entitySelectorParser) {
        return entitySelectorParser.getDx() == null;
    }

    private static void lambda$registerOptions$18(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        entitySelectorParser.setCurrentWorldOnly();
        entitySelectorParser.setDx(entitySelectorParser.getReader().readDouble());
    }

    private static boolean lambda$registerOptions$17(EntitySelectorParser entitySelectorParser) {
        return entitySelectorParser.getZ() == null;
    }

    private static void lambda$registerOptions$16(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        entitySelectorParser.setCurrentWorldOnly();
        entitySelectorParser.setZ(entitySelectorParser.getReader().readDouble());
    }

    private static boolean lambda$registerOptions$15(EntitySelectorParser entitySelectorParser) {
        return entitySelectorParser.getY() == null;
    }

    private static void lambda$registerOptions$14(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        entitySelectorParser.setCurrentWorldOnly();
        entitySelectorParser.setY(entitySelectorParser.getReader().readDouble());
    }

    private static boolean lambda$registerOptions$13(EntitySelectorParser entitySelectorParser) {
        return entitySelectorParser.getX() == null;
    }

    private static void lambda$registerOptions$12(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        entitySelectorParser.setCurrentWorldOnly();
        entitySelectorParser.setX(entitySelectorParser.getReader().readDouble());
    }

    private static boolean lambda$registerOptions$11(EntitySelectorParser entitySelectorParser) {
        return entitySelectorParser.getLevel().isUnbounded();
    }

    private static void lambda$registerOptions$10(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        int n = entitySelectorParser.getReader().getCursor();
        MinMaxBounds.IntBound intBound = MinMaxBounds.IntBound.fromReader(entitySelectorParser.getReader());
        if (intBound.getMin() != null && (Integer)intBound.getMin() < 0 || intBound.getMax() != null && (Integer)intBound.getMax() < 0) {
            entitySelectorParser.getReader().setCursor(n);
            throw NEGATIVE_LEVEL.createWithContext(entitySelectorParser.getReader());
        }
        entitySelectorParser.setLevel(intBound);
        entitySelectorParser.setIncludeNonPlayers(true);
    }

    private static boolean lambda$registerOptions$9(EntitySelectorParser entitySelectorParser) {
        return entitySelectorParser.getDistance().isUnbounded();
    }

    private static void lambda$registerOptions$8(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        int n = entitySelectorParser.getReader().getCursor();
        MinMaxBounds.FloatBound floatBound = MinMaxBounds.FloatBound.fromReader(entitySelectorParser.getReader());
        if (floatBound.getMin() != null && ((Float)floatBound.getMin()).floatValue() < 0.0f || floatBound.getMax() != null && ((Float)floatBound.getMax()).floatValue() < 0.0f) {
            entitySelectorParser.getReader().setCursor(n);
            throw NEGATIVE_DISTANCE.createWithContext(entitySelectorParser.getReader());
        }
        entitySelectorParser.setDistance(floatBound);
        entitySelectorParser.setCurrentWorldOnly();
    }

    private static boolean lambda$registerOptions$7(EntitySelectorParser entitySelectorParser) {
        return !entitySelectorParser.hasNameEquals();
    }

    private static void lambda$registerOptions$6(EntitySelectorParser entitySelectorParser) throws CommandSyntaxException {
        int n = entitySelectorParser.getReader().getCursor();
        boolean bl = entitySelectorParser.shouldInvertValue();
        String string = entitySelectorParser.getReader().readString();
        if (entitySelectorParser.hasNameNotEquals() && !bl) {
            entitySelectorParser.getReader().setCursor(n);
            throw INAPPLICABLE_ENTITY_OPTION.createWithContext(entitySelectorParser.getReader(), "name");
        }
        if (bl) {
            entitySelectorParser.setHasNameNotEquals(false);
        } else {
            entitySelectorParser.setHasNameEquals(false);
        }
        entitySelectorParser.addFilter(arg_0 -> EntityOptions.lambda$registerOptions$5(string, bl, arg_0));
    }

    private static boolean lambda$registerOptions$5(String string, boolean bl, Entity entity2) {
        return entity2.getName().getString().equals(string) != bl;
    }

    private static Message lambda$static$4(Object object) {
        return new TranslationTextComponent("argument.entity.options.type.invalid", object);
    }

    private static Message lambda$static$3(Object object) {
        return new TranslationTextComponent("argument.entity.options.mode.invalid", object);
    }

    private static Message lambda$static$2(Object object) {
        return new TranslationTextComponent("argument.entity.options.sort.irreversible", object);
    }

    private static Message lambda$static$1(Object object) {
        return new TranslationTextComponent("argument.entity.options.inapplicable", object);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("argument.entity.options.unknown", object);
    }

    static class OptionHandler {
        public final IFilter handler;
        public final Predicate<EntitySelectorParser> canHandle;
        public final ITextComponent tooltip;

        private OptionHandler(IFilter iFilter, Predicate<EntitySelectorParser> predicate, ITextComponent iTextComponent) {
            this.handler = iFilter;
            this.canHandle = predicate;
            this.tooltip = iTextComponent;
        }
    }

    public static interface IFilter {
        public void handle(EntitySelectorParser var1) throws CommandSyntaxException;
    }
}

