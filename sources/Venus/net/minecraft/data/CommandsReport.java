/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import java.io.IOException;
import java.nio.file.Path;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;

public class CommandsReport
implements IDataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator generator;

    public CommandsReport(DataGenerator dataGenerator) {
        this.generator = dataGenerator;
    }

    @Override
    public void act(DirectoryCache directoryCache) throws IOException {
        Path path = this.generator.getOutputFolder().resolve("reports/commands.json");
        CommandDispatcher<CommandSource> commandDispatcher = new Commands(Commands.EnvironmentType.ALL).getDispatcher();
        IDataProvider.save(GSON, directoryCache, ArgumentTypes.serialize(commandDispatcher, commandDispatcher.getRoot()), path);
    }

    @Override
    public String getName() {
        return "Command Syntax";
    }
}

