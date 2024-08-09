/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.google.common.collect.ImmutableMap;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.spi.FileSystemProvider;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.profiler.IProfileResult;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugCommand {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleCommandExceptionType NOT_RUNNING_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.debug.notRunning"));
    private static final SimpleCommandExceptionType ALREADY_RUNNING_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.debug.alreadyRunning"));
    @Nullable
    private static final FileSystemProvider JAR_FILESYSTEM_PROVIDER = FileSystemProvider.installedProviders().stream().filter(DebugCommand::lambda$static$0).findFirst().orElse(null);

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("debug").requires(DebugCommand::lambda$register$1)).then(Commands.literal("start").executes(DebugCommand::lambda$register$2))).then(Commands.literal("stop").executes(DebugCommand::lambda$register$3))).then(Commands.literal("report").executes(DebugCommand::lambda$register$4)));
    }

    private static int startDebug(CommandSource commandSource) throws CommandSyntaxException {
        MinecraftServer minecraftServer = commandSource.getServer();
        if (minecraftServer.func_240789_aP_()) {
            throw ALREADY_RUNNING_EXCEPTION.create();
        }
        minecraftServer.func_240790_aQ_();
        commandSource.sendFeedback(new TranslationTextComponent("commands.debug.started", "Started the debug profiler. Type '/debug stop' to stop it."), false);
        return 1;
    }

    private static int stopDebug(CommandSource commandSource) throws CommandSyntaxException {
        MinecraftServer minecraftServer = commandSource.getServer();
        if (!minecraftServer.func_240789_aP_()) {
            throw NOT_RUNNING_EXCEPTION.create();
        }
        IProfileResult iProfileResult = minecraftServer.func_240791_aR_();
        File file = new File(minecraftServer.getFile("debug"), "profile-results-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt");
        iProfileResult.writeToFile(file);
        float f = (float)iProfileResult.nanoTime() / 1.0E9f;
        float f2 = (float)iProfileResult.ticksSpend() / f;
        commandSource.sendFeedback(new TranslationTextComponent("commands.debug.stopped", String.format(Locale.ROOT, "%.2f", Float.valueOf(f)), iProfileResult.ticksSpend(), String.format("%.2f", Float.valueOf(f2))), false);
        return MathHelper.floor(f2);
    }

    private static int writeDebugReport(CommandSource commandSource) {
        MinecraftServer minecraftServer = commandSource.getServer();
        String string = "debug-report-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date());
        try {
            Path path = minecraftServer.getFile("debug").toPath();
            Files.createDirectories(path, new FileAttribute[0]);
            if (!SharedConstants.developmentMode && JAR_FILESYSTEM_PROVIDER != null) {
                Path path2 = path.resolve(string + ".zip");
                try (FileSystem fileSystem = JAR_FILESYSTEM_PROVIDER.newFileSystem(path2, ImmutableMap.of("create", "true"));){
                    minecraftServer.dumpDebugInfo(fileSystem.getPath("/", new String[0]));
                }
            } else {
                Path path3 = path.resolve(string);
                minecraftServer.dumpDebugInfo(path3);
            }
            commandSource.sendFeedback(new TranslationTextComponent("commands.debug.reportSaved", string), true);
            return 1;
        } catch (IOException iOException) {
            LOGGER.error("Failed to save debug dump", (Throwable)iOException);
            commandSource.sendErrorMessage(new TranslationTextComponent("commands.debug.reportFailed"));
            return 1;
        }
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return DebugCommand.writeDebugReport((CommandSource)commandContext.getSource());
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return DebugCommand.stopDebug((CommandSource)commandContext.getSource());
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return DebugCommand.startDebug((CommandSource)commandContext.getSource());
    }

    private static boolean lambda$register$1(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(0);
    }

    private static boolean lambda$static$0(FileSystemProvider fileSystemProvider) {
        return fileSystemProvider.getScheme().equalsIgnoreCase("jar");
    }
}

