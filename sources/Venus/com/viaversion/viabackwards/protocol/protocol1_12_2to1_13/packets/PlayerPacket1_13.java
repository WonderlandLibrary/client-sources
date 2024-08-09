/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.google.common.base.Joiner;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.TabCompleteStorage;
import com.viaversion.viabackwards.utils.ChatUtil;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerPacket1_13
extends RewriterBase<Protocol1_12_2To1_13> {
    private final CommandRewriter<ClientboundPackets1_13> commandRewriter;

    public PlayerPacket1_13(Protocol1_12_2To1_13 protocol1_12_2To1_13) {
        super(protocol1_12_2To1_13);
        this.commandRewriter = new CommandRewriter(this.protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(State.LOGIN, 4, -1, new PacketHandlers(this){
            final PlayerPacket1_13 this$0;
            {
                this.this$0 = playerPacket1_13;
            }

            @Override
            public void register() {
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.cancel();
                packetWrapper.create(2, new PacketHandler(this, packetWrapper){
                    final PacketWrapper val$packetWrapper;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.val$packetWrapper = packetWrapper;
                    }

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.VAR_INT, this.val$packetWrapper.read(Type.VAR_INT));
                        packetWrapper.write(Type.BOOLEAN, false);
                    }
                }).sendToServer(Protocol1_12_2To1_13.class);
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.PLUGIN_MESSAGE, this::lambda$registerPackets$0);
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.SPAWN_PARTICLE, new PacketHandlers(this){
            final PlayerPacket1_13 this$0;
            {
                this.this$0 = playerPacket1_13;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ParticleMapping.ParticleData particleData = ParticleMapping.getMapping(packetWrapper.get(Type.INT, 0));
                packetWrapper.set(Type.INT, 0, particleData.getHistoryId());
                int[] nArray = particleData.rewriteData((Protocol1_12_2To1_13)PlayerPacket1_13.access$000(this.this$0), packetWrapper);
                if (nArray != null) {
                    if (particleData.getHandler().isBlockHandler() && nArray[0] == 0) {
                        packetWrapper.cancel();
                        return;
                    }
                    for (int n : nArray) {
                        packetWrapper.write(Type.VAR_INT, n);
                    }
                }
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.PLAYER_INFO, new PacketHandlers(this){
            final PlayerPacket1_13 this$0;
            {
                this.this$0 = playerPacket1_13;
            }

            @Override
            public void register() {
                this.handler(3::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                TabCompleteStorage tabCompleteStorage = packetWrapper.user().get(TabCompleteStorage.class);
                int n = packetWrapper.passthrough(Type.VAR_INT);
                int n2 = packetWrapper.passthrough(Type.VAR_INT);
                for (int i = 0; i < n2; ++i) {
                    UUID uUID = packetWrapper.passthrough(Type.UUID);
                    if (n == 0) {
                        String string = packetWrapper.passthrough(Type.STRING);
                        tabCompleteStorage.usernames().put(uUID, string);
                        int n3 = packetWrapper.passthrough(Type.VAR_INT);
                        for (int j = 0; j < n3; ++j) {
                            packetWrapper.passthrough(Type.STRING);
                            packetWrapper.passthrough(Type.STRING);
                            packetWrapper.passthrough(Type.OPTIONAL_STRING);
                        }
                        packetWrapper.passthrough(Type.VAR_INT);
                        packetWrapper.passthrough(Type.VAR_INT);
                        packetWrapper.passthrough(Type.OPTIONAL_COMPONENT);
                        continue;
                    }
                    if (n == 1) {
                        packetWrapper.passthrough(Type.VAR_INT);
                        continue;
                    }
                    if (n == 2) {
                        packetWrapper.passthrough(Type.VAR_INT);
                        continue;
                    }
                    if (n == 3) {
                        packetWrapper.passthrough(Type.OPTIONAL_COMPONENT);
                        continue;
                    }
                    if (n != 4) continue;
                    tabCompleteStorage.usernames().remove(uUID);
                }
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.SCOREBOARD_OBJECTIVE, new PacketHandlers(this){
            final PlayerPacket1_13 this$0;
            {
                this.this$0 = playerPacket1_13;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                byte by = packetWrapper.get(Type.BYTE, 0);
                if (by == 0 || by == 2) {
                    JsonElement jsonElement = packetWrapper.read(Type.COMPONENT);
                    String string = ((Protocol1_12_2To1_13)PlayerPacket1_13.access$100(this.this$0)).jsonToLegacy(jsonElement);
                    if (string.length() > 32) {
                        string = string.substring(0, 32);
                    }
                    packetWrapper.write(Type.STRING, string);
                    int n = packetWrapper.read(Type.VAR_INT);
                    packetWrapper.write(Type.STRING, n == 1 ? "hearts" : "integer");
                }
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.TEAMS, new PacketHandlers(this){
            final PlayerPacket1_13 this$0;
            {
                this.this$0 = playerPacket1_13;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                byte by = packetWrapper.get(Type.BYTE, 0);
                if (by == 0 || by == 2) {
                    JsonElement jsonElement = packetWrapper.read(Type.COMPONENT);
                    String string = ((Protocol1_12_2To1_13)PlayerPacket1_13.access$200(this.this$0)).jsonToLegacy(jsonElement);
                    if ((string = ChatUtil.removeUnusedColor(string, 'f')).length() > 32) {
                        string = string.substring(0, 32);
                    }
                    packetWrapper.write(Type.STRING, string);
                    byte by2 = packetWrapper.read(Type.BYTE);
                    String string2 = packetWrapper.read(Type.STRING);
                    String string3 = packetWrapper.read(Type.STRING);
                    int n = packetWrapper.read(Type.VAR_INT);
                    if (n == 21) {
                        n = -1;
                    }
                    JsonElement jsonElement2 = packetWrapper.read(Type.COMPONENT);
                    JsonElement jsonElement3 = packetWrapper.read(Type.COMPONENT);
                    String string4 = ((Protocol1_12_2To1_13)PlayerPacket1_13.access$300(this.this$0)).jsonToLegacy(jsonElement2);
                    if (ViaBackwards.getConfig().addTeamColorTo1_13Prefix()) {
                        string4 = string4 + "\u00a7" + (n > -1 && n <= 15 ? Integer.toHexString(n) : "r");
                    }
                    if ((string4 = ChatUtil.removeUnusedColor(string4, 'f', true)).length() > 16) {
                        string4 = string4.substring(0, 16);
                    }
                    if (string4.endsWith("\u00a7")) {
                        string4 = string4.substring(0, string4.length() - 1);
                    }
                    String string5 = ((Protocol1_12_2To1_13)PlayerPacket1_13.access$400(this.this$0)).jsonToLegacy(jsonElement3);
                    if ((string5 = ChatUtil.removeUnusedColor(string5, '\u0000')).length() > 16) {
                        string5 = string5.substring(0, 16);
                    }
                    if (string5.endsWith("\u00a7")) {
                        string5 = string5.substring(0, string5.length() - 1);
                    }
                    packetWrapper.write(Type.STRING, string4);
                    packetWrapper.write(Type.STRING, string5);
                    packetWrapper.write(Type.BYTE, by2);
                    packetWrapper.write(Type.STRING, string2);
                    packetWrapper.write(Type.STRING, string3);
                    packetWrapper.write(Type.BYTE, (byte)n);
                }
                if (by == 0 || by == 3 || by == 4) {
                    packetWrapper.passthrough(Type.STRING_ARRAY);
                }
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.DECLARE_COMMANDS, null, this::lambda$registerPackets$1);
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.TAB_COMPLETE, PlayerPacket1_13::lambda$registerPackets$2);
        ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_12_1.TAB_COMPLETE, PlayerPacket1_13::lambda$registerPackets$3);
        ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_12_1.PLUGIN_MESSAGE, this::lambda$registerPackets$4);
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.STATISTICS, new PacketHandlers(this){
            final PlayerPacket1_13 this$0;
            {
                this.this$0 = playerPacket1_13;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n;
                int n2 = n = packetWrapper.get(Type.VAR_INT, 0).intValue();
                block4: for (int i = 0; i < n; ++i) {
                    int n3 = packetWrapper.read(Type.VAR_INT);
                    int n4 = packetWrapper.read(Type.VAR_INT);
                    String string = "";
                    switch (n3) {
                        case 0: 
                        case 1: 
                        case 2: 
                        case 3: 
                        case 4: 
                        case 5: 
                        case 6: 
                        case 7: {
                            packetWrapper.read(Type.VAR_INT);
                            --n2;
                            continue block4;
                        }
                        case 8: {
                            string = (String)((Protocol1_12_2To1_13)PlayerPacket1_13.access$500(this.this$0)).getMappingData().getStatisticMappings().get(n4);
                            if (string == null) {
                                packetWrapper.read(Type.VAR_INT);
                                --n2;
                                continue block4;
                            }
                        }
                        default: {
                            packetWrapper.write(Type.STRING, string);
                            packetWrapper.passthrough(Type.VAR_INT);
                        }
                    }
                }
                if (n2 != n) {
                    packetWrapper.set(Type.VAR_INT, 0, n2);
                }
            }
        });
    }

    private static boolean startsWithIgnoreCase(String string, String string2) {
        if (string.length() < string2.length()) {
            return true;
        }
        return string.regionMatches(true, 0, string2, 0, string2.length());
    }

    private void lambda$registerPackets$4(PacketWrapper packetWrapper) throws Exception {
        String string;
        switch (string = packetWrapper.read(Type.STRING)) {
            case "MC|BSign": 
            case "MC|BEdit": {
                packetWrapper.setPacketType(ServerboundPackets1_13.EDIT_BOOK);
                Item item = packetWrapper.read(Type.ITEM);
                packetWrapper.write(Type.FLAT_ITEM, ((Protocol1_12_2To1_13)this.protocol).getItemRewriter().handleItemToServer(item));
                boolean bl = string.equals("MC|BSign");
                packetWrapper.write(Type.BOOLEAN, bl);
                break;
            }
            case "MC|ItemName": {
                packetWrapper.setPacketType(ServerboundPackets1_13.RENAME_ITEM);
                break;
            }
            case "MC|AdvCmd": {
                byte by = packetWrapper.read(Type.BYTE);
                if (by == 0) {
                    packetWrapper.setPacketType(ServerboundPackets1_13.UPDATE_COMMAND_BLOCK);
                    packetWrapper.cancel();
                    ViaBackwards.getPlatform().getLogger().warning("Client send MC|AdvCmd custom payload to update command block, weird!");
                    break;
                }
                if (by == 1) {
                    packetWrapper.setPacketType(ServerboundPackets1_13.UPDATE_COMMAND_BLOCK_MINECART);
                    packetWrapper.write(Type.VAR_INT, packetWrapper.read(Type.INT));
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.BOOLEAN);
                    break;
                }
                packetWrapper.cancel();
                break;
            }
            case "MC|AutoCmd": {
                String string2;
                packetWrapper.setPacketType(ServerboundPackets1_13.UPDATE_COMMAND_BLOCK);
                int n = packetWrapper.read(Type.INT);
                int n2 = packetWrapper.read(Type.INT);
                int n3 = packetWrapper.read(Type.INT);
                packetWrapper.write(Type.POSITION, new Position(n, (short)n2, n3));
                packetWrapper.passthrough(Type.STRING);
                byte by = 0;
                if (packetWrapper.read(Type.BOOLEAN).booleanValue()) {
                    by = (byte)(by | 1);
                }
                int n4 = (string2 = packetWrapper.read(Type.STRING)).equals("SEQUENCE") ? 0 : (string2.equals("AUTO") ? 1 : 2);
                packetWrapper.write(Type.VAR_INT, n4);
                if (packetWrapper.read(Type.BOOLEAN).booleanValue()) {
                    by = (byte)(by | 2);
                }
                if (packetWrapper.read(Type.BOOLEAN).booleanValue()) {
                    by = (byte)(by | 4);
                }
                packetWrapper.write(Type.BYTE, by);
                break;
            }
            case "MC|Struct": {
                packetWrapper.setPacketType(ServerboundPackets1_13.UPDATE_STRUCTURE_BLOCK);
                int n = packetWrapper.read(Type.INT);
                int n5 = packetWrapper.read(Type.INT);
                int n6 = packetWrapper.read(Type.INT);
                packetWrapper.write(Type.POSITION, new Position(n, (short)n5, n6));
                packetWrapper.write(Type.VAR_INT, packetWrapper.read(Type.BYTE) - 1);
                String string3 = packetWrapper.read(Type.STRING);
                int n7 = string3.equals("SAVE") ? 0 : (string3.equals("LOAD") ? 1 : (string3.equals("CORNER") ? 2 : 3));
                packetWrapper.write(Type.VAR_INT, n7);
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.write(Type.BYTE, packetWrapper.read(Type.INT).byteValue());
                packetWrapper.write(Type.BYTE, packetWrapper.read(Type.INT).byteValue());
                packetWrapper.write(Type.BYTE, packetWrapper.read(Type.INT).byteValue());
                packetWrapper.write(Type.BYTE, packetWrapper.read(Type.INT).byteValue());
                packetWrapper.write(Type.BYTE, packetWrapper.read(Type.INT).byteValue());
                packetWrapper.write(Type.BYTE, packetWrapper.read(Type.INT).byteValue());
                String string4 = packetWrapper.read(Type.STRING);
                int n8 = string3.equals("NONE") ? 0 : (string3.equals("LEFT_RIGHT") ? 1 : 2);
                String string5 = packetWrapper.read(Type.STRING);
                int n9 = string3.equals("NONE") ? 0 : (string3.equals("CLOCKWISE_90") ? 1 : (string3.equals("CLOCKWISE_180") ? 2 : 3));
                packetWrapper.passthrough(Type.STRING);
                byte by = 0;
                if (packetWrapper.read(Type.BOOLEAN).booleanValue()) {
                    by = (byte)(by | 1);
                }
                if (packetWrapper.read(Type.BOOLEAN).booleanValue()) {
                    by = (byte)(by | 2);
                }
                if (packetWrapper.read(Type.BOOLEAN).booleanValue()) {
                    by = (byte)(by | 4);
                }
                packetWrapper.passthrough(Type.FLOAT);
                packetWrapper.passthrough(Type.VAR_LONG);
                packetWrapper.write(Type.BYTE, by);
                break;
            }
            case "MC|Beacon": {
                packetWrapper.setPacketType(ServerboundPackets1_13.SET_BEACON_EFFECT);
                packetWrapper.write(Type.VAR_INT, packetWrapper.read(Type.INT));
                packetWrapper.write(Type.VAR_INT, packetWrapper.read(Type.INT));
                break;
            }
            case "MC|TrSel": {
                packetWrapper.setPacketType(ServerboundPackets1_13.SELECT_TRADE);
                packetWrapper.write(Type.VAR_INT, packetWrapper.read(Type.INT));
                break;
            }
            case "MC|PickItem": {
                packetWrapper.setPacketType(ServerboundPackets1_13.PICK_ITEM);
                break;
            }
            default: {
                String string6 = InventoryPackets.getNewPluginChannelId(string);
                if (string6 == null) {
                    if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                        ViaBackwards.getPlatform().getLogger().warning("Ignoring incoming plugin message with channel: " + string);
                    }
                    packetWrapper.cancel();
                    return;
                }
                packetWrapper.write(Type.STRING, string6);
                if (!string6.equals("minecraft:register") && !string6.equals("minecraft:unregister")) break;
                String[] stringArray = new String(packetWrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                ArrayList<String> arrayList = new ArrayList<String>();
                for (String string7 : stringArray) {
                    String string8 = InventoryPackets.getNewPluginChannelId(string7);
                    if (string8 != null) {
                        arrayList.add(string8);
                        continue;
                    }
                    if (Via.getConfig().isSuppressConversionWarnings() && !Via.getManager().isDebug()) continue;
                    ViaBackwards.getPlatform().getLogger().warning("Ignoring plugin channel in incoming REGISTER: " + string7);
                }
                if (!arrayList.isEmpty()) {
                    packetWrapper.write(Type.REMAINING_BYTES, Joiner.on('\u0000').join(arrayList).getBytes(StandardCharsets.UTF_8));
                    break;
                }
                packetWrapper.cancel();
                return;
            }
        }
    }

    private static void lambda$registerPackets$3(PacketWrapper packetWrapper) throws Exception {
        Object object;
        TabCompleteStorage tabCompleteStorage = packetWrapper.user().get(TabCompleteStorage.class);
        ArrayList<String> arrayList = new ArrayList<String>();
        String string = packetWrapper.read(Type.STRING);
        boolean bl = packetWrapper.read(Type.BOOLEAN);
        packetWrapper.read(Type.OPTIONAL_POSITION);
        if (!bl && !string.startsWith("/")) {
            object = string.substring(string.lastIndexOf(32) + 1);
            for (String string2 : tabCompleteStorage.usernames().values()) {
                if (!PlayerPacket1_13.startsWithIgnoreCase(string2, (String)object)) continue;
                arrayList.add(string2);
            }
        } else if (!tabCompleteStorage.commands().isEmpty() && !string.contains(" ")) {
            for (String string3 : tabCompleteStorage.commands()) {
                if (!PlayerPacket1_13.startsWithIgnoreCase(string3, string)) continue;
                arrayList.add(string3);
            }
        }
        if (!arrayList.isEmpty()) {
            packetWrapper.cancel();
            object = packetWrapper.create(ClientboundPackets1_12_1.TAB_COMPLETE);
            object.write(Type.VAR_INT, arrayList.size());
            for (String string4 : arrayList) {
                object.write(Type.STRING, string4);
            }
            object.scheduleSend(Protocol1_12_2To1_13.class);
            tabCompleteStorage.setLastRequest(null);
            return;
        }
        if (!bl && string.startsWith("/")) {
            string = string.substring(1);
        }
        int n = ThreadLocalRandom.current().nextInt();
        packetWrapper.write(Type.VAR_INT, n);
        packetWrapper.write(Type.STRING, string);
        tabCompleteStorage.setLastId(n);
        tabCompleteStorage.setLastAssumeCommand(bl);
        tabCompleteStorage.setLastRequest(string);
    }

    private static void lambda$registerPackets$2(PacketWrapper packetWrapper) throws Exception {
        TabCompleteStorage tabCompleteStorage = packetWrapper.user().get(TabCompleteStorage.class);
        if (tabCompleteStorage.lastRequest() == null) {
            packetWrapper.cancel();
            return;
        }
        if (tabCompleteStorage.lastId() != packetWrapper.read(Type.VAR_INT).intValue()) {
            packetWrapper.cancel();
        }
        int n = packetWrapper.read(Type.VAR_INT);
        int n2 = packetWrapper.read(Type.VAR_INT);
        int n3 = tabCompleteStorage.lastRequest().lastIndexOf(32) + 1;
        if (n3 != n) {
            packetWrapper.cancel();
        }
        if (n2 != tabCompleteStorage.lastRequest().length() - n3) {
            packetWrapper.cancel();
        }
        int n4 = packetWrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < n4; ++i) {
            String string = packetWrapper.read(Type.STRING);
            packetWrapper.write(Type.STRING, (n == 0 && !tabCompleteStorage.isLastAssumeCommand() ? "/" : "") + string);
            if (!packetWrapper.read(Type.BOOLEAN).booleanValue()) continue;
            packetWrapper.read(Type.STRING);
        }
    }

    private void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.cancel();
        TabCompleteStorage tabCompleteStorage = packetWrapper.user().get(TabCompleteStorage.class);
        if (!tabCompleteStorage.commands().isEmpty()) {
            tabCompleteStorage.commands().clear();
        }
        int n = packetWrapper.read(Type.VAR_INT);
        boolean bl = true;
        for (int i = 0; i < n; ++i) {
            byte by = packetWrapper.read(Type.BYTE);
            packetWrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
            if ((by & 8) != 0) {
                packetWrapper.read(Type.VAR_INT);
            }
            byte by2 = (byte)(by & 3);
            if (bl && by2 == 2) {
                bl = false;
            }
            if (by2 == 1 || by2 == 2) {
                String string = packetWrapper.read(Type.STRING);
                if (by2 == 1 && bl) {
                    tabCompleteStorage.commands().add('/' + string);
                }
            }
            if (by2 == 2) {
                this.commandRewriter.handleArgument(packetWrapper, packetWrapper.read(Type.STRING));
            }
            if ((by & 0x10) == 0) continue;
            packetWrapper.read(Type.STRING);
        }
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        String string = packetWrapper.read(Type.STRING);
        if (string.equals("minecraft:trader_list")) {
            packetWrapper.write(Type.STRING, "MC|TrList");
            packetWrapper.passthrough(Type.INT);
            int n = packetWrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
            for (int i = 0; i < n; ++i) {
                Item item = packetWrapper.read(Type.FLAT_ITEM);
                packetWrapper.write(Type.ITEM, ((Protocol1_12_2To1_13)this.protocol).getItemRewriter().handleItemToClient(item));
                Item item2 = packetWrapper.read(Type.FLAT_ITEM);
                packetWrapper.write(Type.ITEM, ((Protocol1_12_2To1_13)this.protocol).getItemRewriter().handleItemToClient(item2));
                boolean bl = packetWrapper.passthrough(Type.BOOLEAN);
                if (bl) {
                    Item item3 = packetWrapper.read(Type.FLAT_ITEM);
                    packetWrapper.write(Type.ITEM, ((Protocol1_12_2To1_13)this.protocol).getItemRewriter().handleItemToClient(item3));
                }
                packetWrapper.passthrough(Type.BOOLEAN);
                packetWrapper.passthrough(Type.INT);
                packetWrapper.passthrough(Type.INT);
            }
        } else {
            String string2 = InventoryPackets.getOldPluginChannelId(string);
            if (string2 == null) {
                if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                    ViaBackwards.getPlatform().getLogger().warning("Ignoring outgoing plugin message with channel: " + string);
                }
                packetWrapper.cancel();
                return;
            }
            packetWrapper.write(Type.STRING, string2);
            if (string2.equals("REGISTER") || string2.equals("UNREGISTER")) {
                String[] stringArray = new String(packetWrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                ArrayList<String> arrayList = new ArrayList<String>();
                for (String string3 : stringArray) {
                    String string4 = InventoryPackets.getOldPluginChannelId(string3);
                    if (string4 != null) {
                        arrayList.add(string4);
                        continue;
                    }
                    if (Via.getConfig().isSuppressConversionWarnings() && !Via.getManager().isDebug()) continue;
                    ViaBackwards.getPlatform().getLogger().warning("Ignoring plugin channel in outgoing REGISTER: " + string3);
                }
                packetWrapper.write(Type.REMAINING_BYTES, Joiner.on('\u0000').join(arrayList).getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    static Protocol access$000(PlayerPacket1_13 playerPacket1_13) {
        return playerPacket1_13.protocol;
    }

    static Protocol access$100(PlayerPacket1_13 playerPacket1_13) {
        return playerPacket1_13.protocol;
    }

    static Protocol access$200(PlayerPacket1_13 playerPacket1_13) {
        return playerPacket1_13.protocol;
    }

    static Protocol access$300(PlayerPacket1_13 playerPacket1_13) {
        return playerPacket1_13.protocol;
    }

    static Protocol access$400(PlayerPacket1_13 playerPacket1_13) {
        return playerPacket1_13.protocol;
    }

    static Protocol access$500(PlayerPacket1_13 playerPacket1_13) {
        return playerPacket1_13.protocol;
    }
}

