/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.rewriter;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import java.util.HashMap;
import java.util.Map;

public class CommandRewriter<C extends ClientboundPacketType> {
    protected final Protocol<C, ?, ?, ?> protocol;
    protected final Map<String, CommandArgumentConsumer> parserHandlers = new HashMap<String, CommandArgumentConsumer>();

    public CommandRewriter(Protocol<C, ?, ?, ?> protocol) {
        this.protocol = protocol;
        this.parserHandlers.put("brigadier:double", CommandRewriter::lambda$new$0);
        this.parserHandlers.put("brigadier:float", CommandRewriter::lambda$new$1);
        this.parserHandlers.put("brigadier:integer", CommandRewriter::lambda$new$2);
        this.parserHandlers.put("brigadier:long", CommandRewriter::lambda$new$3);
        this.parserHandlers.put("brigadier:string", CommandRewriter::lambda$new$4);
        this.parserHandlers.put("minecraft:entity", CommandRewriter::lambda$new$5);
        this.parserHandlers.put("minecraft:score_holder", CommandRewriter::lambda$new$6);
        this.parserHandlers.put("minecraft:resource", CommandRewriter::lambda$new$7);
        this.parserHandlers.put("minecraft:resource_or_tag", CommandRewriter::lambda$new$8);
        this.parserHandlers.put("minecraft:resource_or_tag_key", CommandRewriter::lambda$new$9);
        this.parserHandlers.put("minecraft:resource_key", CommandRewriter::lambda$new$10);
    }

    public void registerDeclareCommands(C c) {
        this.protocol.registerClientbound(c, this::lambda$registerDeclareCommands$11);
    }

    public void registerDeclareCommands1_19(C c) {
        this.protocol.registerClientbound(c, this::lambda$registerDeclareCommands1_19$12);
    }

    public void handleArgument(PacketWrapper packetWrapper, String string) throws Exception {
        CommandArgumentConsumer commandArgumentConsumer = this.parserHandlers.get(string);
        if (commandArgumentConsumer != null) {
            commandArgumentConsumer.accept(packetWrapper);
        }
    }

    public String handleArgumentType(String string) {
        if (this.protocol.getMappingData() != null && this.protocol.getMappingData().getArgumentTypeMappings() != null) {
            return this.protocol.getMappingData().getArgumentTypeMappings().mappedIdentifier(string);
        }
        return string;
    }

    protected String argumentType(int n) {
        return this.protocol.getMappingData().getArgumentTypeMappings().identifier(n);
    }

    protected int mappedArgumentTypeId(String string) {
        return this.protocol.getMappingData().getArgumentTypeMappings().mappedId(string);
    }

    private void lambda$registerDeclareCommands1_19$12(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < n; ++i) {
            byte by;
            byte by2 = packetWrapper.passthrough(Type.BYTE);
            packetWrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
            if ((by2 & 8) != 0) {
                packetWrapper.passthrough(Type.VAR_INT);
            }
            if ((by = (byte)(by2 & 3)) == 1 || by == 2) {
                packetWrapper.passthrough(Type.STRING);
            }
            if (by == 2) {
                int n2 = packetWrapper.read(Type.VAR_INT);
                String string = this.argumentType(n2);
                String string2 = this.handleArgumentType(string);
                Preconditions.checkNotNull(string2, "No mapping for argument type %s", new Object[]{string});
                packetWrapper.write(Type.VAR_INT, this.mappedArgumentTypeId(string2));
                this.handleArgument(packetWrapper, string);
            }
            if ((by2 & 0x10) == 0) continue;
            packetWrapper.passthrough(Type.STRING);
        }
        packetWrapper.passthrough(Type.VAR_INT);
    }

    private void lambda$registerDeclareCommands$11(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < n; ++i) {
            byte by;
            byte by2 = packetWrapper.passthrough(Type.BYTE);
            packetWrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
            if ((by2 & 8) != 0) {
                packetWrapper.passthrough(Type.VAR_INT);
            }
            if ((by = (byte)(by2 & 3)) == 1 || by == 2) {
                packetWrapper.passthrough(Type.STRING);
            }
            if (by == 2) {
                String string = packetWrapper.read(Type.STRING);
                String string2 = this.handleArgumentType(string);
                if (string2 != null) {
                    packetWrapper.write(Type.STRING, string2);
                }
                this.handleArgument(packetWrapper, string);
            }
            if ((by2 & 0x10) == 0) continue;
            packetWrapper.passthrough(Type.STRING);
        }
        packetWrapper.passthrough(Type.VAR_INT);
    }

    private static void lambda$new$10(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.STRING);
    }

    private static void lambda$new$9(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.STRING);
    }

    private static void lambda$new$8(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.STRING);
    }

    private static void lambda$new$7(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.STRING);
    }

    private static void lambda$new$6(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.BYTE);
    }

    private static void lambda$new$5(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.BYTE);
    }

    private static void lambda$new$4(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.VAR_INT);
    }

    private static void lambda$new$3(PacketWrapper packetWrapper) throws Exception {
        byte by = packetWrapper.passthrough(Type.BYTE);
        if ((by & 1) != 0) {
            packetWrapper.passthrough(Type.LONG);
        }
        if ((by & 2) != 0) {
            packetWrapper.passthrough(Type.LONG);
        }
    }

    private static void lambda$new$2(PacketWrapper packetWrapper) throws Exception {
        byte by = packetWrapper.passthrough(Type.BYTE);
        if ((by & 1) != 0) {
            packetWrapper.passthrough(Type.INT);
        }
        if ((by & 2) != 0) {
            packetWrapper.passthrough(Type.INT);
        }
    }

    private static void lambda$new$1(PacketWrapper packetWrapper) throws Exception {
        byte by = packetWrapper.passthrough(Type.BYTE);
        if ((by & 1) != 0) {
            packetWrapper.passthrough(Type.FLOAT);
        }
        if ((by & 2) != 0) {
            packetWrapper.passthrough(Type.FLOAT);
        }
    }

    private static void lambda$new$0(PacketWrapper packetWrapper) throws Exception {
        byte by = packetWrapper.passthrough(Type.BYTE);
        if ((by & 1) != 0) {
            packetWrapper.passthrough(Type.DOUBLE);
        }
        if ((by & 2) != 0) {
            packetWrapper.passthrough(Type.DOUBLE);
        }
    }

    @FunctionalInterface
    public static interface CommandArgumentConsumer {
        public void accept(PacketWrapper var1) throws Exception;
    }
}

