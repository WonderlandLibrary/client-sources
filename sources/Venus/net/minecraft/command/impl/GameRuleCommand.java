/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;

public class GameRuleCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        LiteralArgumentBuilder literalArgumentBuilder = (LiteralArgumentBuilder)Commands.literal("gamerule").requires(GameRuleCommand::lambda$register$0);
        GameRules.visitAll(new GameRules.IRuleEntryVisitor(literalArgumentBuilder){
            final LiteralArgumentBuilder val$literalargumentbuilder;
            {
                this.val$literalargumentbuilder = literalArgumentBuilder;
            }

            @Override
            public <T extends GameRules.RuleValue<T>> void visit(GameRules.RuleKey<T> ruleKey, GameRules.RuleType<T> ruleType) {
                this.val$literalargumentbuilder.then(((LiteralArgumentBuilder)Commands.literal(ruleKey.getName()).executes(arg_0 -> 1.lambda$visit$0(ruleKey, arg_0))).then(ruleType.createArgument("value").executes(arg_0 -> 1.lambda$visit$1(ruleKey, arg_0))));
            }

            private static int lambda$visit$1(GameRules.RuleKey ruleKey, CommandContext commandContext) throws CommandSyntaxException {
                return GameRuleCommand.func_223485_b(commandContext, ruleKey);
            }

            private static int lambda$visit$0(GameRules.RuleKey ruleKey, CommandContext commandContext) throws CommandSyntaxException {
                return GameRuleCommand.func_223486_b((CommandSource)commandContext.getSource(), ruleKey);
            }
        });
        commandDispatcher.register(literalArgumentBuilder);
    }

    private static <T extends GameRules.RuleValue<T>> int func_223485_b(CommandContext<CommandSource> commandContext, GameRules.RuleKey<T> ruleKey) {
        CommandSource commandSource = commandContext.getSource();
        T t = commandSource.getServer().getGameRules().get(ruleKey);
        ((GameRules.RuleValue)t).updateValue(commandContext, "value");
        commandSource.sendFeedback(new TranslationTextComponent("commands.gamerule.set", ruleKey.getName(), ((GameRules.RuleValue)t).toString()), false);
        return ((GameRules.RuleValue)t).intValue();
    }

    private static <T extends GameRules.RuleValue<T>> int func_223486_b(CommandSource commandSource, GameRules.RuleKey<T> ruleKey) {
        T t = commandSource.getServer().getGameRules().get(ruleKey);
        commandSource.sendFeedback(new TranslationTextComponent("commands.gamerule.query", ruleKey.getName(), ((GameRules.RuleValue)t).toString()), true);
        return ((GameRules.RuleValue)t).intValue();
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

