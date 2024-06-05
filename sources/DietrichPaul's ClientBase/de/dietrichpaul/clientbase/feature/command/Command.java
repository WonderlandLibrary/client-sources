/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import de.dietrichpaul.clientbase.ClientBase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;

public abstract class Command implements Comparable<Command> {
    private final String name;

    protected MinecraftClient mc = MinecraftClient.getInstance();

    public Command(String name) {
        this.name = name;
    }

    public abstract void buildCommand(LiteralArgumentBuilder<CommandSource> root);

    protected static LiteralArgumentBuilder<CommandSource> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    protected static <T> RequiredArgumentBuilder<CommandSource, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> root = literal(name);
        buildCommand(root);
        dispatcher.register(root);
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Command o) {
        return name.compareTo(o.name);
    }
}
