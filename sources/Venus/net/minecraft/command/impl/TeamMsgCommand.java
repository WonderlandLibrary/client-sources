/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.List;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class TeamMsgCommand {
    private static final Style field_241076_a_ = Style.EMPTY.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("chat.type.team.hover"))).setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/teammsg "));
    private static final SimpleCommandExceptionType field_218919_a = new SimpleCommandExceptionType(new TranslationTextComponent("commands.teammsg.failed.noteam"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        LiteralCommandNode<CommandSource> literalCommandNode = commandDispatcher.register((LiteralArgumentBuilder)Commands.literal("teammsg").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("message", MessageArgument.message()).executes(TeamMsgCommand::lambda$register$0)));
        commandDispatcher.register((LiteralArgumentBuilder)Commands.literal("tm").redirect(literalCommandNode));
    }

    private static int func_218917_a(CommandSource commandSource, ITextComponent iTextComponent) throws CommandSyntaxException {
        Entity entity2 = commandSource.assertIsEntity();
        ScorePlayerTeam scorePlayerTeam = (ScorePlayerTeam)entity2.getTeam();
        if (scorePlayerTeam == null) {
            throw field_218919_a.create();
        }
        IFormattableTextComponent iFormattableTextComponent = scorePlayerTeam.func_237501_d_().mergeStyle(field_241076_a_);
        List<ServerPlayerEntity> list = commandSource.getServer().getPlayerList().getPlayers();
        for (ServerPlayerEntity serverPlayerEntity : list) {
            if (serverPlayerEntity == entity2) {
                serverPlayerEntity.sendMessage(new TranslationTextComponent("chat.type.team.sent", iFormattableTextComponent, commandSource.getDisplayName(), iTextComponent), entity2.getUniqueID());
                continue;
            }
            if (serverPlayerEntity.getTeam() != scorePlayerTeam) continue;
            serverPlayerEntity.sendMessage(new TranslationTextComponent("chat.type.team.text", iFormattableTextComponent, commandSource.getDisplayName(), iTextComponent), entity2.getUniqueID());
        }
        return list.size();
    }

    private static int lambda$register$0(CommandContext commandContext) throws CommandSyntaxException {
        return TeamMsgCommand.func_218917_a((CommandSource)commandContext.getSource(), MessageArgument.getMessage(commandContext, "message"));
    }
}

