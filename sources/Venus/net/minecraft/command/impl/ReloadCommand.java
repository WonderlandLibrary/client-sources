/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.storage.IServerConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReloadCommand {
    private static final Logger field_241057_a_ = LogManager.getLogger();

    public static void func_241062_a_(Collection<String> collection, CommandSource commandSource) {
        commandSource.getServer().func_240780_a_(collection).exceptionally(arg_0 -> ReloadCommand.lambda$func_241062_a_$0(commandSource, arg_0));
    }

    private static Collection<String> func_241058_a_(ResourcePackList resourcePackList, IServerConfiguration iServerConfiguration, Collection<String> collection) {
        resourcePackList.reloadPacksFromFinders();
        ArrayList<String> arrayList = Lists.newArrayList(collection);
        List<String> list = iServerConfiguration.getDatapackCodec().getDisabled();
        for (String string : resourcePackList.func_232616_b_()) {
            if (list.contains(string) || arrayList.contains(string)) continue;
            arrayList.add(string);
        }
        return arrayList;
    }

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("reload").requires(ReloadCommand::lambda$register$1)).executes(ReloadCommand::lambda$register$2));
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        CommandSource commandSource = (CommandSource)commandContext.getSource();
        MinecraftServer minecraftServer = commandSource.getServer();
        ResourcePackList resourcePackList = minecraftServer.getResourcePacks();
        IServerConfiguration iServerConfiguration = minecraftServer.func_240793_aU_();
        Collection<String> collection = resourcePackList.func_232621_d_();
        Collection<String> collection2 = ReloadCommand.func_241058_a_(resourcePackList, iServerConfiguration, collection);
        commandSource.sendFeedback(new TranslationTextComponent("commands.reload.success"), false);
        ReloadCommand.func_241062_a_(collection2, commandSource);
        return 1;
    }

    private static boolean lambda$register$1(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static Void lambda$func_241062_a_$0(CommandSource commandSource, Throwable throwable) {
        field_241057_a_.warn("Failed to execute reload", throwable);
        commandSource.sendErrorMessage(new TranslationTextComponent("commands.reload.failure"));
        return null;
    }
}

