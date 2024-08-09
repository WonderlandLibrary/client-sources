/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.OptionalInt;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.BlockPredicateArgument;
import net.minecraft.command.arguments.DimensionArgument;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.IRangeArgument;
import net.minecraft.command.arguments.NBTPathArgument;
import net.minecraft.command.arguments.ObjectiveArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.command.arguments.RotationArgument;
import net.minecraft.command.arguments.ScoreHolderArgument;
import net.minecraft.command.arguments.SwizzleArgument;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.command.impl.BossBarCommand;
import net.minecraft.command.impl.data.DataCommand;
import net.minecraft.command.impl.data.IDataAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootPredicateManager;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.nbt.ShortNBT;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.CustomServerBossInfo;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class ExecuteCommand {
    private static final Dynamic2CommandExceptionType TOO_MANY_BLOCKS = new Dynamic2CommandExceptionType(ExecuteCommand::lambda$static$0);
    private static final SimpleCommandExceptionType TEST_FAILED = new SimpleCommandExceptionType(new TranslationTextComponent("commands.execute.conditional.fail"));
    private static final DynamicCommandExceptionType TEST_FAILED_COUNT = new DynamicCommandExceptionType(ExecuteCommand::lambda$static$1);
    private static final BinaryOperator<ResultConsumer<CommandSource>> COMBINE_ON_RESULT_COMPLETE = ExecuteCommand::lambda$static$3;
    private static final SuggestionProvider<CommandSource> field_229760_e_ = ExecuteCommand::lambda$static$4;

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        LiteralCommandNode<CommandSource> literalCommandNode = commandDispatcher.register((LiteralArgumentBuilder)Commands.literal("execute").requires(ExecuteCommand::lambda$register$5));
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("execute").requires(ExecuteCommand::lambda$register$6)).then(Commands.literal("run").redirect(commandDispatcher.getRoot()))).then(ExecuteCommand.makeIfCommand(literalCommandNode, Commands.literal("if"), true))).then(ExecuteCommand.makeIfCommand(literalCommandNode, Commands.literal("unless"), false))).then(Commands.literal("as").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", EntityArgument.entities()).fork(literalCommandNode, ExecuteCommand::lambda$register$7)))).then(Commands.literal("at").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", EntityArgument.entities()).fork(literalCommandNode, ExecuteCommand::lambda$register$8)))).then(((LiteralArgumentBuilder)Commands.literal("store").then(ExecuteCommand.makeStoreSubcommand(literalCommandNode, Commands.literal("result"), true))).then(ExecuteCommand.makeStoreSubcommand(literalCommandNode, Commands.literal("success"), false)))).then(((LiteralArgumentBuilder)Commands.literal("positioned").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("pos", Vec3Argument.vec3()).redirect(literalCommandNode, ExecuteCommand::lambda$register$9))).then(Commands.literal("as").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", EntityArgument.entities()).fork(literalCommandNode, ExecuteCommand::lambda$register$10))))).then(((LiteralArgumentBuilder)Commands.literal("rotated").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("rot", RotationArgument.rotation()).redirect(literalCommandNode, ExecuteCommand::lambda$register$11))).then(Commands.literal("as").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", EntityArgument.entities()).fork(literalCommandNode, ExecuteCommand::lambda$register$12))))).then(((LiteralArgumentBuilder)Commands.literal("facing").then((ArgumentBuilder<CommandSource, ?>)Commands.literal("entity").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", EntityArgument.entities()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("anchor", EntityAnchorArgument.entityAnchor()).fork(literalCommandNode, ExecuteCommand::lambda$register$13))))).then(Commands.argument("pos", Vec3Argument.vec3()).redirect(literalCommandNode, ExecuteCommand::lambda$register$14)))).then(Commands.literal("align").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("axes", SwizzleArgument.swizzle()).redirect(literalCommandNode, ExecuteCommand::lambda$register$15)))).then(Commands.literal("anchored").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("anchor", EntityAnchorArgument.entityAnchor()).redirect(literalCommandNode, ExecuteCommand::lambda$register$16)))).then(Commands.literal("in").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("dimension", DimensionArgument.getDimension()).redirect(literalCommandNode, ExecuteCommand::lambda$register$17))));
    }

    private static ArgumentBuilder<CommandSource, ?> makeStoreSubcommand(LiteralCommandNode<CommandSource> literalCommandNode, LiteralArgumentBuilder<CommandSource> literalArgumentBuilder, boolean bl) {
        literalArgumentBuilder.then((ArgumentBuilder<CommandSource, ?>)Commands.literal("score").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", ScoreHolderArgument.scoreHolders()).suggests(ScoreHolderArgument.SUGGEST_ENTITY_SELECTOR).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("objective", ObjectiveArgument.objective()).redirect(literalCommandNode, arg_0 -> ExecuteCommand.lambda$makeStoreSubcommand$18(bl, arg_0)))));
        literalArgumentBuilder.then((ArgumentBuilder<CommandSource, ?>)Commands.literal("bossbar").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("id", ResourceLocationArgument.resourceLocation()).suggests(BossBarCommand.SUGGESTIONS_PROVIDER).then((ArgumentBuilder<CommandSource, ?>)Commands.literal("value").redirect(literalCommandNode, arg_0 -> ExecuteCommand.lambda$makeStoreSubcommand$19(bl, arg_0)))).then(Commands.literal("max").redirect(literalCommandNode, arg_0 -> ExecuteCommand.lambda$makeStoreSubcommand$20(bl, arg_0)))));
        for (DataCommand.IDataProvider iDataProvider : DataCommand.field_218955_b) {
            iDataProvider.createArgument(literalArgumentBuilder, arg_0 -> ExecuteCommand.lambda$makeStoreSubcommand$33(literalCommandNode, iDataProvider, bl, arg_0));
        }
        return literalArgumentBuilder;
    }

    private static CommandSource storeIntoScore(CommandSource commandSource, Collection<String> collection, ScoreObjective scoreObjective, boolean bl) {
        ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
        return commandSource.withResultConsumer((arg_0, arg_1, arg_2) -> ExecuteCommand.lambda$storeIntoScore$34(collection, serverScoreboard, scoreObjective, bl, arg_0, arg_1, arg_2), COMBINE_ON_RESULT_COMPLETE);
    }

    private static CommandSource storeIntoBossbar(CommandSource commandSource, CustomServerBossInfo customServerBossInfo, boolean bl, boolean bl2) {
        return commandSource.withResultConsumer((arg_0, arg_1, arg_2) -> ExecuteCommand.lambda$storeIntoBossbar$35(bl2, bl, customServerBossInfo, arg_0, arg_1, arg_2), COMBINE_ON_RESULT_COMPLETE);
    }

    private static CommandSource storeIntoNBT(CommandSource commandSource, IDataAccessor iDataAccessor, NBTPathArgument.NBTPath nBTPath, IntFunction<INBT> intFunction, boolean bl) {
        return commandSource.withResultConsumer((arg_0, arg_1, arg_2) -> ExecuteCommand.lambda$storeIntoNBT$37(iDataAccessor, bl, nBTPath, intFunction, arg_0, arg_1, arg_2), COMBINE_ON_RESULT_COMPLETE);
    }

    private static ArgumentBuilder<CommandSource, ?> makeIfCommand(CommandNode<CommandSource> commandNode, LiteralArgumentBuilder<CommandSource> literalArgumentBuilder, boolean bl) {
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.then((ArgumentBuilder<CommandSource, ?>)Commands.literal("block").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("pos", BlockPosArgument.blockPos()).then(ExecuteCommand.buildIfResult(commandNode, Commands.argument("block", BlockPredicateArgument.blockPredicate()), bl, ExecuteCommand::lambda$makeIfCommand$38))))).then(Commands.literal("score").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("target", ScoreHolderArgument.scoreHolder()).suggests(ScoreHolderArgument.SUGGEST_ENTITY_SELECTOR).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("targetObjective", ObjectiveArgument.objective()).then((ArgumentBuilder<CommandSource, ?>)Commands.literal("=").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("source", ScoreHolderArgument.scoreHolder()).suggests(ScoreHolderArgument.SUGGEST_ENTITY_SELECTOR).then(ExecuteCommand.buildIfResult(commandNode, Commands.argument("sourceObjective", ObjectiveArgument.objective()), bl, ExecuteCommand::lambda$makeIfCommand$39))))).then(Commands.literal("<").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("source", ScoreHolderArgument.scoreHolder()).suggests(ScoreHolderArgument.SUGGEST_ENTITY_SELECTOR).then(ExecuteCommand.buildIfResult(commandNode, Commands.argument("sourceObjective", ObjectiveArgument.objective()), bl, ExecuteCommand::lambda$makeIfCommand$41))))).then(Commands.literal("<=").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("source", ScoreHolderArgument.scoreHolder()).suggests(ScoreHolderArgument.SUGGEST_ENTITY_SELECTOR).then(ExecuteCommand.buildIfResult(commandNode, Commands.argument("sourceObjective", ObjectiveArgument.objective()), bl, ExecuteCommand::lambda$makeIfCommand$43))))).then(Commands.literal(">").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("source", ScoreHolderArgument.scoreHolder()).suggests(ScoreHolderArgument.SUGGEST_ENTITY_SELECTOR).then(ExecuteCommand.buildIfResult(commandNode, Commands.argument("sourceObjective", ObjectiveArgument.objective()), bl, ExecuteCommand::lambda$makeIfCommand$45))))).then(Commands.literal(">=").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("source", ScoreHolderArgument.scoreHolder()).suggests(ScoreHolderArgument.SUGGEST_ENTITY_SELECTOR).then(ExecuteCommand.buildIfResult(commandNode, Commands.argument("sourceObjective", ObjectiveArgument.objective()), bl, ExecuteCommand::lambda$makeIfCommand$47))))).then(Commands.literal("matches").then(ExecuteCommand.buildIfResult(commandNode, Commands.argument("range", IRangeArgument.intRange()), bl, ExecuteCommand::lambda$makeIfCommand$48))))))).then(Commands.literal("blocks").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("start", BlockPosArgument.blockPos()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("end", BlockPosArgument.blockPos()).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("destination", BlockPosArgument.blockPos()).then(ExecuteCommand.buildIfBlocks(commandNode, Commands.literal("all"), bl, false))).then(ExecuteCommand.buildIfBlocks(commandNode, Commands.literal("masked"), bl, true))))))).then(Commands.literal("entity").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("entities", EntityArgument.entities()).fork(commandNode, arg_0 -> ExecuteCommand.lambda$makeIfCommand$49(bl, arg_0))).executes(ExecuteCommand.func_218834_a(bl, ExecuteCommand::lambda$makeIfCommand$50))))).then(Commands.literal("predicate").then(ExecuteCommand.buildIfResult(commandNode, Commands.argument("predicate", ResourceLocationArgument.resourceLocation()).suggests(field_229760_e_), bl, ExecuteCommand::lambda$makeIfCommand$51)));
        for (DataCommand.IDataProvider iDataProvider : DataCommand.field_218956_c) {
            literalArgumentBuilder.then(iDataProvider.createArgument(Commands.literal("data"), arg_0 -> ExecuteCommand.lambda$makeIfCommand$54(commandNode, bl, iDataProvider, arg_0)));
        }
        return literalArgumentBuilder;
    }

    private static Command<CommandSource> func_218834_a(boolean bl, INumericTest iNumericTest) {
        return bl ? arg_0 -> ExecuteCommand.lambda$func_218834_a$55(iNumericTest, arg_0) : arg_0 -> ExecuteCommand.lambda$func_218834_a$56(iNumericTest, arg_0);
    }

    private static int func_218831_a(IDataAccessor iDataAccessor, NBTPathArgument.NBTPath nBTPath) throws CommandSyntaxException {
        return nBTPath.func_218069_b(iDataAccessor.getData());
    }

    private static boolean compareScores(CommandContext<CommandSource> commandContext, BiPredicate<Integer, Integer> biPredicate) throws CommandSyntaxException {
        String string = ScoreHolderArgument.getSingleScoreHolderNoObjectives(commandContext, "target");
        ScoreObjective scoreObjective = ObjectiveArgument.getObjective(commandContext, "targetObjective");
        String string2 = ScoreHolderArgument.getSingleScoreHolderNoObjectives(commandContext, "source");
        ScoreObjective scoreObjective2 = ObjectiveArgument.getObjective(commandContext, "sourceObjective");
        ServerScoreboard serverScoreboard = commandContext.getSource().getServer().getScoreboard();
        if (serverScoreboard.entityHasObjective(string, scoreObjective) && serverScoreboard.entityHasObjective(string2, scoreObjective2)) {
            Score score = serverScoreboard.getOrCreateScore(string, scoreObjective);
            Score score2 = serverScoreboard.getOrCreateScore(string2, scoreObjective2);
            return biPredicate.test(score.getScorePoints(), score2.getScorePoints());
        }
        return true;
    }

    private static boolean checkScore(CommandContext<CommandSource> commandContext, MinMaxBounds.IntBound intBound) throws CommandSyntaxException {
        String string = ScoreHolderArgument.getSingleScoreHolderNoObjectives(commandContext, "target");
        ScoreObjective scoreObjective = ObjectiveArgument.getObjective(commandContext, "targetObjective");
        ServerScoreboard serverScoreboard = commandContext.getSource().getServer().getScoreboard();
        return !serverScoreboard.entityHasObjective(string, scoreObjective) ? false : intBound.test(serverScoreboard.getOrCreateScore(string, scoreObjective).getScorePoints());
    }

    private static boolean func_229767_a_(CommandSource commandSource, ILootCondition iLootCondition) {
        ServerWorld serverWorld = commandSource.getWorld();
        LootContext.Builder builder = new LootContext.Builder(serverWorld).withParameter(LootParameters.field_237457_g_, commandSource.getPos()).withNullableParameter(LootParameters.THIS_ENTITY, commandSource.getEntity());
        return iLootCondition.test(builder.build(LootParameterSets.COMMAND));
    }

    private static Collection<CommandSource> checkIfMatches(CommandContext<CommandSource> commandContext, boolean bl, boolean bl2) {
        return bl2 == bl ? Collections.singleton(commandContext.getSource()) : Collections.emptyList();
    }

    private static ArgumentBuilder<CommandSource, ?> buildIfResult(CommandNode<CommandSource> commandNode, ArgumentBuilder<CommandSource, ?> argumentBuilder, boolean bl, IBooleanTest iBooleanTest) {
        return ((ArgumentBuilder)argumentBuilder.fork(commandNode, arg_0 -> ExecuteCommand.lambda$buildIfResult$57(bl, iBooleanTest, arg_0))).executes(arg_0 -> ExecuteCommand.lambda$buildIfResult$58(bl, iBooleanTest, arg_0));
    }

    private static ArgumentBuilder<CommandSource, ?> buildIfBlocks(CommandNode<CommandSource> commandNode, ArgumentBuilder<CommandSource, ?> argumentBuilder, boolean bl, boolean bl2) {
        return ((ArgumentBuilder)argumentBuilder.fork(commandNode, arg_0 -> ExecuteCommand.lambda$buildIfBlocks$59(bl, bl2, arg_0))).executes(bl ? arg_0 -> ExecuteCommand.lambda$buildIfBlocks$60(bl2, arg_0) : arg_0 -> ExecuteCommand.lambda$buildIfBlocks$61(bl2, arg_0));
    }

    private static int checkBlockCountIf(CommandContext<CommandSource> commandContext, boolean bl) throws CommandSyntaxException {
        OptionalInt optionalInt = ExecuteCommand.countMatchingBlocks(commandContext, bl);
        if (optionalInt.isPresent()) {
            commandContext.getSource().sendFeedback(new TranslationTextComponent("commands.execute.conditional.pass_count", optionalInt.getAsInt()), true);
            return optionalInt.getAsInt();
        }
        throw TEST_FAILED.create();
    }

    private static int checkBlockCountUnless(CommandContext<CommandSource> commandContext, boolean bl) throws CommandSyntaxException {
        OptionalInt optionalInt = ExecuteCommand.countMatchingBlocks(commandContext, bl);
        if (optionalInt.isPresent()) {
            throw TEST_FAILED_COUNT.create(optionalInt.getAsInt());
        }
        commandContext.getSource().sendFeedback(new TranslationTextComponent("commands.execute.conditional.pass"), true);
        return 0;
    }

    private static OptionalInt countMatchingBlocks(CommandContext<CommandSource> commandContext, boolean bl) throws CommandSyntaxException {
        return ExecuteCommand.countMatchingBlocks(commandContext.getSource().getWorld(), BlockPosArgument.getLoadedBlockPos(commandContext, "start"), BlockPosArgument.getLoadedBlockPos(commandContext, "end"), BlockPosArgument.getLoadedBlockPos(commandContext, "destination"), bl);
    }

    private static OptionalInt countMatchingBlocks(ServerWorld serverWorld, BlockPos blockPos, BlockPos blockPos2, BlockPos blockPos3, boolean bl) throws CommandSyntaxException {
        MutableBoundingBox mutableBoundingBox = new MutableBoundingBox(blockPos, blockPos2);
        MutableBoundingBox mutableBoundingBox2 = new MutableBoundingBox(blockPos3, blockPos3.add(mutableBoundingBox.getLength()));
        BlockPos blockPos4 = new BlockPos(mutableBoundingBox2.minX - mutableBoundingBox.minX, mutableBoundingBox2.minY - mutableBoundingBox.minY, mutableBoundingBox2.minZ - mutableBoundingBox.minZ);
        int n = mutableBoundingBox.getXSize() * mutableBoundingBox.getYSize() * mutableBoundingBox.getZSize();
        if (n > 32768) {
            throw TOO_MANY_BLOCKS.create(32768, n);
        }
        int n2 = 0;
        for (int i = mutableBoundingBox.minZ; i <= mutableBoundingBox.maxZ; ++i) {
            for (int j = mutableBoundingBox.minY; j <= mutableBoundingBox.maxY; ++j) {
                for (int k = mutableBoundingBox.minX; k <= mutableBoundingBox.maxX; ++k) {
                    BlockPos blockPos5 = new BlockPos(k, j, i);
                    BlockPos blockPos6 = blockPos5.add(blockPos4);
                    BlockState blockState = serverWorld.getBlockState(blockPos5);
                    if (bl && blockState.isIn(Blocks.AIR)) continue;
                    if (blockState != serverWorld.getBlockState(blockPos6)) {
                        return OptionalInt.empty();
                    }
                    TileEntity tileEntity = serverWorld.getTileEntity(blockPos5);
                    TileEntity tileEntity2 = serverWorld.getTileEntity(blockPos6);
                    if (tileEntity != null) {
                        if (tileEntity2 == null) {
                            return OptionalInt.empty();
                        }
                        CompoundNBT compoundNBT = tileEntity.write(new CompoundNBT());
                        compoundNBT.remove("x");
                        compoundNBT.remove("y");
                        compoundNBT.remove("z");
                        CompoundNBT compoundNBT2 = tileEntity2.write(new CompoundNBT());
                        compoundNBT2.remove("x");
                        compoundNBT2.remove("y");
                        compoundNBT2.remove("z");
                        if (!compoundNBT.equals(compoundNBT2)) {
                            return OptionalInt.empty();
                        }
                    }
                    ++n2;
                }
            }
        }
        return OptionalInt.of(n2);
    }

    private static int lambda$buildIfBlocks$61(boolean bl, CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.checkBlockCountUnless(commandContext, bl);
    }

    private static int lambda$buildIfBlocks$60(boolean bl, CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.checkBlockCountIf(commandContext, bl);
    }

    private static Collection lambda$buildIfBlocks$59(boolean bl, boolean bl2, CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.checkIfMatches(commandContext, bl, ExecuteCommand.countMatchingBlocks(commandContext, bl2).isPresent());
    }

    private static int lambda$buildIfResult$58(boolean bl, IBooleanTest iBooleanTest, CommandContext commandContext) throws CommandSyntaxException {
        if (bl == iBooleanTest.test(commandContext)) {
            ((CommandSource)commandContext.getSource()).sendFeedback(new TranslationTextComponent("commands.execute.conditional.pass"), true);
            return 0;
        }
        throw TEST_FAILED.create();
    }

    private static Collection lambda$buildIfResult$57(boolean bl, IBooleanTest iBooleanTest, CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.checkIfMatches(commandContext, bl, iBooleanTest.test(commandContext));
    }

    private static int lambda$func_218834_a$56(INumericTest iNumericTest, CommandContext commandContext) throws CommandSyntaxException {
        int n = iNumericTest.test(commandContext);
        if (n == 0) {
            ((CommandSource)commandContext.getSource()).sendFeedback(new TranslationTextComponent("commands.execute.conditional.pass"), true);
            return 0;
        }
        throw TEST_FAILED_COUNT.create(n);
    }

    private static int lambda$func_218834_a$55(INumericTest iNumericTest, CommandContext commandContext) throws CommandSyntaxException {
        int n = iNumericTest.test(commandContext);
        if (n > 0) {
            ((CommandSource)commandContext.getSource()).sendFeedback(new TranslationTextComponent("commands.execute.conditional.pass_count", n), true);
            return n;
        }
        throw TEST_FAILED.create();
    }

    private static ArgumentBuilder lambda$makeIfCommand$54(CommandNode commandNode, boolean bl, DataCommand.IDataProvider iDataProvider, ArgumentBuilder argumentBuilder) {
        return argumentBuilder.then(((RequiredArgumentBuilder)Commands.argument("path", NBTPathArgument.nbtPath()).fork(commandNode, arg_0 -> ExecuteCommand.lambda$makeIfCommand$52(bl, iDataProvider, arg_0))).executes(ExecuteCommand.func_218834_a(bl, arg_0 -> ExecuteCommand.lambda$makeIfCommand$53(iDataProvider, arg_0))));
    }

    private static int lambda$makeIfCommand$53(DataCommand.IDataProvider iDataProvider, CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.func_218831_a(iDataProvider.createAccessor(commandContext), NBTPathArgument.getNBTPath(commandContext, "path"));
    }

    private static Collection lambda$makeIfCommand$52(boolean bl, DataCommand.IDataProvider iDataProvider, CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.checkIfMatches(commandContext, bl, ExecuteCommand.func_218831_a(iDataProvider.createAccessor(commandContext), NBTPathArgument.getNBTPath(commandContext, "path")) > 0);
    }

    private static boolean lambda$makeIfCommand$51(CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.func_229767_a_((CommandSource)commandContext.getSource(), ResourceLocationArgument.func_228259_c_(commandContext, "predicate"));
    }

    private static int lambda$makeIfCommand$50(CommandContext commandContext) throws CommandSyntaxException {
        return EntityArgument.getEntitiesAllowingNone(commandContext, "entities").size();
    }

    private static Collection lambda$makeIfCommand$49(boolean bl, CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.checkIfMatches(commandContext, bl, !EntityArgument.getEntitiesAllowingNone(commandContext, "entities").isEmpty());
    }

    private static boolean lambda$makeIfCommand$48(CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.checkScore(commandContext, IRangeArgument.IntRange.getIntRange(commandContext, "range"));
    }

    private static boolean lambda$makeIfCommand$47(CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.compareScores(commandContext, ExecuteCommand::lambda$makeIfCommand$46);
    }

    private static boolean lambda$makeIfCommand$46(Integer n, Integer n2) {
        return n >= n2;
    }

    private static boolean lambda$makeIfCommand$45(CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.compareScores(commandContext, ExecuteCommand::lambda$makeIfCommand$44);
    }

    private static boolean lambda$makeIfCommand$44(Integer n, Integer n2) {
        return n > n2;
    }

    private static boolean lambda$makeIfCommand$43(CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.compareScores(commandContext, ExecuteCommand::lambda$makeIfCommand$42);
    }

    private static boolean lambda$makeIfCommand$42(Integer n, Integer n2) {
        return n <= n2;
    }

    private static boolean lambda$makeIfCommand$41(CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.compareScores(commandContext, ExecuteCommand::lambda$makeIfCommand$40);
    }

    private static boolean lambda$makeIfCommand$40(Integer n, Integer n2) {
        return n < n2;
    }

    private static boolean lambda$makeIfCommand$39(CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.compareScores(commandContext, Integer::equals);
    }

    private static boolean lambda$makeIfCommand$38(CommandContext commandContext) throws CommandSyntaxException {
        return BlockPredicateArgument.getBlockPredicate(commandContext, "block").test(new CachedBlockInfo(((CommandSource)commandContext.getSource()).getWorld(), BlockPosArgument.getLoadedBlockPos(commandContext, "pos"), true));
    }

    private static void lambda$storeIntoNBT$37(IDataAccessor iDataAccessor, boolean bl, NBTPathArgument.NBTPath nBTPath, IntFunction intFunction, CommandContext commandContext, boolean bl2, int n) {
        try {
            CompoundNBT compoundNBT = iDataAccessor.getData();
            int n2 = bl ? n : (bl2 ? 1 : 0);
            nBTPath.func_218076_b(compoundNBT, () -> ExecuteCommand.lambda$storeIntoNBT$36(intFunction, n2));
            iDataAccessor.mergeData(compoundNBT);
        } catch (CommandSyntaxException commandSyntaxException) {
            // empty catch block
        }
    }

    private static INBT lambda$storeIntoNBT$36(IntFunction intFunction, int n) {
        return (INBT)intFunction.apply(n);
    }

    private static void lambda$storeIntoBossbar$35(boolean bl, boolean bl2, CustomServerBossInfo customServerBossInfo, CommandContext commandContext, boolean bl3, int n) {
        int n2;
        int n3 = bl ? n : (n2 = bl3 ? 1 : 0);
        if (bl2) {
            customServerBossInfo.setValue(n2);
        } else {
            customServerBossInfo.setMax(n2);
        }
    }

    private static void lambda$storeIntoScore$34(Collection collection, Scoreboard scoreboard, ScoreObjective scoreObjective, boolean bl, CommandContext commandContext, boolean bl2, int n) {
        for (String string : collection) {
            Score score = scoreboard.getOrCreateScore(string, scoreObjective);
            int n2 = bl ? n : (bl2 ? 1 : 0);
            score.setScorePoints(n2);
        }
    }

    private static ArgumentBuilder lambda$makeStoreSubcommand$33(LiteralCommandNode literalCommandNode, DataCommand.IDataProvider iDataProvider, boolean bl, ArgumentBuilder argumentBuilder) {
        return argumentBuilder.then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("path", NBTPathArgument.nbtPath()).then((ArgumentBuilder<CommandSource, ?>)Commands.literal("int").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("scale", DoubleArgumentType.doubleArg()).redirect(literalCommandNode, arg_0 -> ExecuteCommand.lambda$makeStoreSubcommand$22(iDataProvider, bl, arg_0))))).then(Commands.literal("float").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("scale", DoubleArgumentType.doubleArg()).redirect(literalCommandNode, arg_0 -> ExecuteCommand.lambda$makeStoreSubcommand$24(iDataProvider, bl, arg_0))))).then(Commands.literal("short").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("scale", DoubleArgumentType.doubleArg()).redirect(literalCommandNode, arg_0 -> ExecuteCommand.lambda$makeStoreSubcommand$26(iDataProvider, bl, arg_0))))).then(Commands.literal("long").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("scale", DoubleArgumentType.doubleArg()).redirect(literalCommandNode, arg_0 -> ExecuteCommand.lambda$makeStoreSubcommand$28(iDataProvider, bl, arg_0))))).then(Commands.literal("double").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("scale", DoubleArgumentType.doubleArg()).redirect(literalCommandNode, arg_0 -> ExecuteCommand.lambda$makeStoreSubcommand$30(iDataProvider, bl, arg_0))))).then(Commands.literal("byte").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("scale", DoubleArgumentType.doubleArg()).redirect(literalCommandNode, arg_0 -> ExecuteCommand.lambda$makeStoreSubcommand$32(iDataProvider, bl, arg_0)))));
    }

    private static CommandSource lambda$makeStoreSubcommand$32(DataCommand.IDataProvider iDataProvider, boolean bl, CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.storeIntoNBT((CommandSource)commandContext.getSource(), iDataProvider.createAccessor(commandContext), NBTPathArgument.getNBTPath(commandContext, "path"), arg_0 -> ExecuteCommand.lambda$makeStoreSubcommand$31(commandContext, arg_0), bl);
    }

    private static INBT lambda$makeStoreSubcommand$31(CommandContext commandContext, int n) {
        return ByteNBT.valueOf((byte)((double)n * DoubleArgumentType.getDouble(commandContext, "scale")));
    }

    private static CommandSource lambda$makeStoreSubcommand$30(DataCommand.IDataProvider iDataProvider, boolean bl, CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.storeIntoNBT((CommandSource)commandContext.getSource(), iDataProvider.createAccessor(commandContext), NBTPathArgument.getNBTPath(commandContext, "path"), arg_0 -> ExecuteCommand.lambda$makeStoreSubcommand$29(commandContext, arg_0), bl);
    }

    private static INBT lambda$makeStoreSubcommand$29(CommandContext commandContext, int n) {
        return DoubleNBT.valueOf((double)n * DoubleArgumentType.getDouble(commandContext, "scale"));
    }

    private static CommandSource lambda$makeStoreSubcommand$28(DataCommand.IDataProvider iDataProvider, boolean bl, CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.storeIntoNBT((CommandSource)commandContext.getSource(), iDataProvider.createAccessor(commandContext), NBTPathArgument.getNBTPath(commandContext, "path"), arg_0 -> ExecuteCommand.lambda$makeStoreSubcommand$27(commandContext, arg_0), bl);
    }

    private static INBT lambda$makeStoreSubcommand$27(CommandContext commandContext, int n) {
        return LongNBT.valueOf((long)((double)n * DoubleArgumentType.getDouble(commandContext, "scale")));
    }

    private static CommandSource lambda$makeStoreSubcommand$26(DataCommand.IDataProvider iDataProvider, boolean bl, CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.storeIntoNBT((CommandSource)commandContext.getSource(), iDataProvider.createAccessor(commandContext), NBTPathArgument.getNBTPath(commandContext, "path"), arg_0 -> ExecuteCommand.lambda$makeStoreSubcommand$25(commandContext, arg_0), bl);
    }

    private static INBT lambda$makeStoreSubcommand$25(CommandContext commandContext, int n) {
        return ShortNBT.valueOf((short)((double)n * DoubleArgumentType.getDouble(commandContext, "scale")));
    }

    private static CommandSource lambda$makeStoreSubcommand$24(DataCommand.IDataProvider iDataProvider, boolean bl, CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.storeIntoNBT((CommandSource)commandContext.getSource(), iDataProvider.createAccessor(commandContext), NBTPathArgument.getNBTPath(commandContext, "path"), arg_0 -> ExecuteCommand.lambda$makeStoreSubcommand$23(commandContext, arg_0), bl);
    }

    private static INBT lambda$makeStoreSubcommand$23(CommandContext commandContext, int n) {
        return FloatNBT.valueOf((float)((double)n * DoubleArgumentType.getDouble(commandContext, "scale")));
    }

    private static CommandSource lambda$makeStoreSubcommand$22(DataCommand.IDataProvider iDataProvider, boolean bl, CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.storeIntoNBT((CommandSource)commandContext.getSource(), iDataProvider.createAccessor(commandContext), NBTPathArgument.getNBTPath(commandContext, "path"), arg_0 -> ExecuteCommand.lambda$makeStoreSubcommand$21(commandContext, arg_0), bl);
    }

    private static INBT lambda$makeStoreSubcommand$21(CommandContext commandContext, int n) {
        return IntNBT.valueOf((int)((double)n * DoubleArgumentType.getDouble(commandContext, "scale")));
    }

    private static CommandSource lambda$makeStoreSubcommand$20(boolean bl, CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.storeIntoBossbar((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), false, bl);
    }

    private static CommandSource lambda$makeStoreSubcommand$19(boolean bl, CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.storeIntoBossbar((CommandSource)commandContext.getSource(), BossBarCommand.getBossbar(commandContext), true, bl);
    }

    private static CommandSource lambda$makeStoreSubcommand$18(boolean bl, CommandContext commandContext) throws CommandSyntaxException {
        return ExecuteCommand.storeIntoScore((CommandSource)commandContext.getSource(), ScoreHolderArgument.getScoreHolder(commandContext, "targets"), ObjectiveArgument.getObjective(commandContext, "objective"), bl);
    }

    private static CommandSource lambda$register$17(CommandContext commandContext) throws CommandSyntaxException {
        return ((CommandSource)commandContext.getSource()).withWorld(DimensionArgument.getDimensionArgument(commandContext, "dimension"));
    }

    private static CommandSource lambda$register$16(CommandContext commandContext) throws CommandSyntaxException {
        return ((CommandSource)commandContext.getSource()).withEntityAnchorType(EntityAnchorArgument.getEntityAnchor(commandContext, "anchor"));
    }

    private static CommandSource lambda$register$15(CommandContext commandContext) throws CommandSyntaxException {
        return ((CommandSource)commandContext.getSource()).withPos(((CommandSource)commandContext.getSource()).getPos().align(SwizzleArgument.getSwizzle(commandContext, "axes")));
    }

    private static CommandSource lambda$register$14(CommandContext commandContext) throws CommandSyntaxException {
        return ((CommandSource)commandContext.getSource()).withRotation(Vec3Argument.getVec3(commandContext, "pos"));
    }

    private static Collection lambda$register$13(CommandContext commandContext) throws CommandSyntaxException {
        ArrayList<CommandSource> arrayList = Lists.newArrayList();
        EntityAnchorArgument.Type type = EntityAnchorArgument.getEntityAnchor(commandContext, "anchor");
        for (Entity entity2 : EntityArgument.getEntitiesAllowingNone(commandContext, "targets")) {
            arrayList.add(((CommandSource)commandContext.getSource()).withRotation(entity2, type));
        }
        return arrayList;
    }

    private static Collection lambda$register$12(CommandContext commandContext) throws CommandSyntaxException {
        ArrayList<CommandSource> arrayList = Lists.newArrayList();
        for (Entity entity2 : EntityArgument.getEntitiesAllowingNone(commandContext, "targets")) {
            arrayList.add(((CommandSource)commandContext.getSource()).withRotation(entity2.getPitchYaw()));
        }
        return arrayList;
    }

    private static CommandSource lambda$register$11(CommandContext commandContext) throws CommandSyntaxException {
        return ((CommandSource)commandContext.getSource()).withRotation(RotationArgument.getRotation(commandContext, "rot").getRotation((CommandSource)commandContext.getSource()));
    }

    private static Collection lambda$register$10(CommandContext commandContext) throws CommandSyntaxException {
        ArrayList<CommandSource> arrayList = Lists.newArrayList();
        for (Entity entity2 : EntityArgument.getEntitiesAllowingNone(commandContext, "targets")) {
            arrayList.add(((CommandSource)commandContext.getSource()).withPos(entity2.getPositionVec()));
        }
        return arrayList;
    }

    private static CommandSource lambda$register$9(CommandContext commandContext) throws CommandSyntaxException {
        return ((CommandSource)commandContext.getSource()).withPos(Vec3Argument.getVec3(commandContext, "pos")).withEntityAnchorType(EntityAnchorArgument.Type.FEET);
    }

    private static Collection lambda$register$8(CommandContext commandContext) throws CommandSyntaxException {
        ArrayList<CommandSource> arrayList = Lists.newArrayList();
        for (Entity entity2 : EntityArgument.getEntitiesAllowingNone(commandContext, "targets")) {
            arrayList.add(((CommandSource)commandContext.getSource()).withWorld((ServerWorld)entity2.world).withPos(entity2.getPositionVec()).withRotation(entity2.getPitchYaw()));
        }
        return arrayList;
    }

    private static Collection lambda$register$7(CommandContext commandContext) throws CommandSyntaxException {
        ArrayList<CommandSource> arrayList = Lists.newArrayList();
        for (Entity entity2 : EntityArgument.getEntitiesAllowingNone(commandContext, "targets")) {
            arrayList.add(((CommandSource)commandContext.getSource()).withEntity(entity2));
        }
        return arrayList;
    }

    private static boolean lambda$register$6(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static boolean lambda$register$5(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static CompletableFuture lambda$static$4(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        LootPredicateManager lootPredicateManager = ((CommandSource)commandContext.getSource()).getServer().func_229736_aP_();
        return ISuggestionProvider.suggestIterable(lootPredicateManager.func_227513_a_(), suggestionsBuilder);
    }

    private static ResultConsumer lambda$static$3(ResultConsumer resultConsumer, ResultConsumer resultConsumer2) {
        return (arg_0, arg_1, arg_2) -> ExecuteCommand.lambda$static$2(resultConsumer, resultConsumer2, arg_0, arg_1, arg_2);
    }

    private static void lambda$static$2(ResultConsumer resultConsumer, ResultConsumer resultConsumer2, CommandContext commandContext, boolean bl, int n) {
        resultConsumer.onCommandComplete(commandContext, bl, n);
        resultConsumer2.onCommandComplete(commandContext, bl, n);
    }

    private static Message lambda$static$1(Object object) {
        return new TranslationTextComponent("commands.execute.conditional.fail_count", object);
    }

    private static Message lambda$static$0(Object object, Object object2) {
        return new TranslationTextComponent("commands.execute.blocks.toobig", object, object2);
    }

    @FunctionalInterface
    static interface IBooleanTest {
        public boolean test(CommandContext<CommandSource> var1) throws CommandSyntaxException;
    }

    @FunctionalInterface
    static interface INumericTest {
        public int test(CommandContext<CommandSource> var1) throws CommandSyntaxException;
    }
}

