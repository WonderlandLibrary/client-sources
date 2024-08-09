/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SCommandListPacket
implements IPacket<IClientPlayNetHandler> {
    private RootCommandNode<ISuggestionProvider> root;

    public SCommandListPacket() {
    }

    public SCommandListPacket(RootCommandNode<ISuggestionProvider> rootCommandNode) {
        this.root = rootCommandNode;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        Entry[] entryArray = new Entry[packetBuffer.readVarInt()];
        for (int i = 0; i < entryArray.length; ++i) {
            entryArray[i] = SCommandListPacket.readEntry(packetBuffer);
        }
        SCommandListPacket.func_244294_a(entryArray);
        this.root = (RootCommandNode)entryArray[packetBuffer.readVarInt()].node;
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        Object2IntMap<CommandNode<ISuggestionProvider>> object2IntMap = SCommandListPacket.func_244292_a(this.root);
        CommandNode<ISuggestionProvider>[] commandNodeArray = SCommandListPacket.func_244293_a(object2IntMap);
        packetBuffer.writeVarInt(commandNodeArray.length);
        for (CommandNode<ISuggestionProvider> commandNode : commandNodeArray) {
            SCommandListPacket.writeCommandNode(packetBuffer, commandNode, object2IntMap);
        }
        packetBuffer.writeVarInt(object2IntMap.get(this.root));
    }

    private static void func_244294_a(Entry[] entryArray) {
        ArrayList<Entry> arrayList = Lists.newArrayList(entryArray);
        while (!arrayList.isEmpty()) {
            boolean bl = arrayList.removeIf(arg_0 -> SCommandListPacket.lambda$func_244294_a$0(entryArray, arg_0));
            if (bl) continue;
            throw new IllegalStateException("Server sent an impossible command tree");
        }
    }

    private static Object2IntMap<CommandNode<ISuggestionProvider>> func_244292_a(RootCommandNode<ISuggestionProvider> rootCommandNode) {
        CommandNode commandNode;
        Object2IntOpenHashMap<CommandNode<ISuggestionProvider>> object2IntOpenHashMap = new Object2IntOpenHashMap<CommandNode<ISuggestionProvider>>();
        ArrayDeque arrayDeque = Queues.newArrayDeque();
        arrayDeque.add(rootCommandNode);
        while ((commandNode = (CommandNode)arrayDeque.poll()) != null) {
            if (object2IntOpenHashMap.containsKey(commandNode)) continue;
            int n = object2IntOpenHashMap.size();
            object2IntOpenHashMap.put((CommandNode<ISuggestionProvider>)commandNode, n);
            arrayDeque.addAll(commandNode.getChildren());
            if (commandNode.getRedirect() == null) continue;
            arrayDeque.add(commandNode.getRedirect());
        }
        return object2IntOpenHashMap;
    }

    private static CommandNode<ISuggestionProvider>[] func_244293_a(Object2IntMap<CommandNode<ISuggestionProvider>> object2IntMap) {
        CommandNode[] commandNodeArray = new CommandNode[object2IntMap.size()];
        for (Object2IntMap.Entry entry : Object2IntMaps.fastIterable(object2IntMap)) {
            commandNodeArray[entry.getIntValue()] = (CommandNode)entry.getKey();
        }
        return commandNodeArray;
    }

    private static Entry readEntry(PacketBuffer packetBuffer) {
        byte by = packetBuffer.readByte();
        int[] nArray = packetBuffer.readVarIntArray();
        int n = (by & 8) != 0 ? packetBuffer.readVarInt() : 0;
        ArgumentBuilder<ISuggestionProvider, ?> argumentBuilder = SCommandListPacket.readArgumentBuilder(packetBuffer, by);
        return new Entry(argumentBuilder, by, n, nArray);
    }

    @Nullable
    private static ArgumentBuilder<ISuggestionProvider, ?> readArgumentBuilder(PacketBuffer packetBuffer, byte by) {
        int n = by & 3;
        if (n == 2) {
            String string = packetBuffer.readString(Short.MAX_VALUE);
            ArgumentType<?> argumentType = ArgumentTypes.deserialize(packetBuffer);
            if (argumentType == null) {
                return null;
            }
            RequiredArgumentBuilder<ISuggestionProvider, ?> requiredArgumentBuilder = RequiredArgumentBuilder.argument(string, argumentType);
            if ((by & 0x10) != 0) {
                requiredArgumentBuilder.suggests(SuggestionProviders.get(packetBuffer.readResourceLocation()));
            }
            return requiredArgumentBuilder;
        }
        return n == 1 ? LiteralArgumentBuilder.literal(packetBuffer.readString(Short.MAX_VALUE)) : null;
    }

    private static void writeCommandNode(PacketBuffer packetBuffer, CommandNode<ISuggestionProvider> commandNode, Map<CommandNode<ISuggestionProvider>, Integer> map) {
        int n = 0;
        if (commandNode.getRedirect() != null) {
            n = (byte)(n | 8);
        }
        if (commandNode.getCommand() != null) {
            n = (byte)(n | 4);
        }
        if (commandNode instanceof RootCommandNode) {
            n = (byte)(n | 0);
        } else if (commandNode instanceof ArgumentCommandNode) {
            n = (byte)(n | 2);
            if (((ArgumentCommandNode)commandNode).getCustomSuggestions() != null) {
                n = (byte)(n | 0x10);
            }
        } else {
            if (!(commandNode instanceof LiteralCommandNode)) {
                throw new UnsupportedOperationException("Unknown node type " + commandNode);
            }
            n = (byte)(n | 1);
        }
        packetBuffer.writeByte(n);
        packetBuffer.writeVarInt(commandNode.getChildren().size());
        for (CommandNode<ISuggestionProvider> commandNode2 : commandNode.getChildren()) {
            packetBuffer.writeVarInt(map.get(commandNode2));
        }
        if (commandNode.getRedirect() != null) {
            packetBuffer.writeVarInt(map.get(commandNode.getRedirect()));
        }
        if (commandNode instanceof ArgumentCommandNode) {
            ArgumentCommandNode argumentCommandNode = (ArgumentCommandNode)commandNode;
            packetBuffer.writeString(argumentCommandNode.getName());
            ArgumentTypes.serialize(packetBuffer, argumentCommandNode.getType());
            if (argumentCommandNode.getCustomSuggestions() != null) {
                packetBuffer.writeResourceLocation(SuggestionProviders.getId(argumentCommandNode.getCustomSuggestions()));
            }
        } else if (commandNode instanceof LiteralCommandNode) {
            packetBuffer.writeString(((LiteralCommandNode)commandNode).getLiteral());
        }
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleCommandList(this);
    }

    public RootCommandNode<ISuggestionProvider> getRoot() {
        return this.root;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }

    private static boolean lambda$func_244294_a$0(Entry[] entryArray, Entry entry) {
        return entry.createCommandNode(entryArray);
    }

    static class Entry {
        @Nullable
        private final ArgumentBuilder<ISuggestionProvider, ?> argBuilder;
        private final byte flags;
        private final int redirectTarget;
        private final int[] children;
        @Nullable
        private CommandNode<ISuggestionProvider> node;

        private Entry(@Nullable ArgumentBuilder<ISuggestionProvider, ?> argumentBuilder, byte by, int n, int[] nArray) {
            this.argBuilder = argumentBuilder;
            this.flags = by;
            this.redirectTarget = n;
            this.children = nArray;
        }

        public boolean createCommandNode(Entry[] entryArray) {
            if (this.node == null) {
                if (this.argBuilder == null) {
                    this.node = new RootCommandNode<ISuggestionProvider>();
                } else {
                    if ((this.flags & 8) != 0) {
                        if (entryArray[this.redirectTarget].node == null) {
                            return true;
                        }
                        this.argBuilder.redirect(entryArray[this.redirectTarget].node);
                    }
                    if ((this.flags & 4) != 0) {
                        this.argBuilder.executes(Entry::lambda$createCommandNode$0);
                    }
                    this.node = this.argBuilder.build();
                }
            }
            for (int n : this.children) {
                if (entryArray[n].node != null) continue;
                return true;
            }
            for (int n : this.children) {
                CommandNode<ISuggestionProvider> commandNode = entryArray[n].node;
                if (commandNode instanceof RootCommandNode) continue;
                this.node.addChild(commandNode);
            }
            return false;
        }

        private static int lambda$createCommandNode$0(CommandContext commandContext) throws CommandSyntaxException {
            return 1;
        }
    }
}

