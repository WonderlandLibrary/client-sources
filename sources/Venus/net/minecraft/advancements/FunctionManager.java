/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import net.minecraft.command.CommandSource;
import net.minecraft.command.FunctionObject;
import net.minecraft.resources.FunctionReloader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameRules;

public class FunctionManager {
    private static final ResourceLocation TICK_TAG_ID = new ResourceLocation("tick");
    private static final ResourceLocation LOAD_TAG_ID = new ResourceLocation("load");
    private final MinecraftServer server;
    private boolean isExecuting;
    private final ArrayDeque<QueuedCommand> commandQueue = new ArrayDeque();
    private final List<QueuedCommand> commandChain = Lists.newArrayList();
    private final List<FunctionObject> tickFunctions = Lists.newArrayList();
    private boolean loadFunctionsRun;
    private FunctionReloader reloader;

    public FunctionManager(MinecraftServer minecraftServer, FunctionReloader functionReloader) {
        this.server = minecraftServer;
        this.reloader = functionReloader;
        this.clearAndResetTickFunctions(functionReloader);
    }

    public int getMaxCommandChainLength() {
        return this.server.getGameRules().getInt(GameRules.MAX_COMMAND_CHAIN_LENGTH);
    }

    public CommandDispatcher<CommandSource> getCommandDispatcher() {
        return this.server.getCommandManager().getDispatcher();
    }

    public void tick() {
        this.executeAndProfile(this.tickFunctions, TICK_TAG_ID);
        if (this.loadFunctionsRun) {
            this.loadFunctionsRun = false;
            List<FunctionObject> list = this.reloader.func_240942_b_().getTagByID(LOAD_TAG_ID).getAllElements();
            this.executeAndProfile(list, LOAD_TAG_ID);
        }
    }

    private void executeAndProfile(Collection<FunctionObject> collection, ResourceLocation resourceLocation) {
        this.server.getProfiler().startSection(resourceLocation::toString);
        for (FunctionObject functionObject : collection) {
            this.execute(functionObject, this.getCommandSource());
        }
        this.server.getProfiler().endSection();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int execute(FunctionObject functionObject, CommandSource commandSource) {
        int n = this.getMaxCommandChainLength();
        if (this.isExecuting) {
            if (this.commandQueue.size() + this.commandChain.size() < n) {
                this.commandChain.add(new QueuedCommand(this, commandSource, new FunctionObject.FunctionEntry(functionObject)));
            }
            return 1;
        }
        try {
            int n2;
            this.isExecuting = true;
            int n3 = 0;
            FunctionObject.IEntry[] iEntryArray = functionObject.getEntries();
            for (n2 = iEntryArray.length - 1; n2 >= 0; --n2) {
                this.commandQueue.push(new QueuedCommand(this, commandSource, iEntryArray[n2]));
            }
            while (!this.commandQueue.isEmpty()) {
                try {
                    QueuedCommand queuedCommand = this.commandQueue.removeFirst();
                    this.server.getProfiler().startSection(queuedCommand::toString);
                    queuedCommand.execute(this.commandQueue, n);
                    if (!this.commandChain.isEmpty()) {
                        Lists.reverse(this.commandChain).forEach(this.commandQueue::addFirst);
                        this.commandChain.clear();
                    }
                } finally {
                    this.server.getProfiler().endSection();
                }
                if (++n3 < n) continue;
                int n4 = n3;
                return n4;
            }
            n2 = n3;
            return n2;
        } finally {
            this.commandQueue.clear();
            this.commandChain.clear();
            this.isExecuting = false;
        }
    }

    public void setFunctionReloader(FunctionReloader functionReloader) {
        this.reloader = functionReloader;
        this.clearAndResetTickFunctions(functionReloader);
    }

    private void clearAndResetTickFunctions(FunctionReloader functionReloader) {
        this.tickFunctions.clear();
        this.tickFunctions.addAll(functionReloader.func_240942_b_().getTagByID(TICK_TAG_ID).getAllElements());
        this.loadFunctionsRun = true;
    }

    public CommandSource getCommandSource() {
        return this.server.getCommandSource().withPermissionLevel(2).withFeedbackDisabled();
    }

    public Optional<FunctionObject> get(ResourceLocation resourceLocation) {
        return this.reloader.func_240940_a_(resourceLocation);
    }

    public ITag<FunctionObject> getFunctionTag(ResourceLocation resourceLocation) {
        return this.reloader.func_240943_b_(resourceLocation);
    }

    public Iterable<ResourceLocation> getFunctionIdentifiers() {
        return this.reloader.func_240931_a_().keySet();
    }

    public Iterable<ResourceLocation> getFunctionTagIdentifiers() {
        return this.reloader.func_240942_b_().getRegisteredTags();
    }

    public static class QueuedCommand {
        private final FunctionManager functionManager;
        private final CommandSource sender;
        private final FunctionObject.IEntry entry;

        public QueuedCommand(FunctionManager functionManager, CommandSource commandSource, FunctionObject.IEntry iEntry) {
            this.functionManager = functionManager;
            this.sender = commandSource;
            this.entry = iEntry;
        }

        public void execute(ArrayDeque<QueuedCommand> arrayDeque, int n) {
            try {
                this.entry.execute(this.functionManager, this.sender, arrayDeque, n);
            } catch (Throwable throwable) {
                // empty catch block
            }
        }

        public String toString() {
            return this.entry.toString();
        }
    }
}

