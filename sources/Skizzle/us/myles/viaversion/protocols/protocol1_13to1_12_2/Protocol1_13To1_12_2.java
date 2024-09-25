/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  com.google.common.primitives.Ints
 */
package us.myles.ViaVersion.protocols.protocol1_13to1_12_2;

import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.entities.Entity1_13Types;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.platform.providers.ViaProviders;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.remapper.ValueCreator;
import us.myles.ViaVersion.api.remapper.ValueTransformer;
import us.myles.ViaVersion.api.rewriters.SoundRewriter;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import us.myles.ViaVersion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.ChatRewriter;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections.providers.PacketBlockConnectionProvider;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data.BlockIdData;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data.MappingData;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data.RecipeData;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data.StatisticData;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data.StatisticMappings;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.metadata.MetadataRewriter1_13To1_12_2;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.packets.EntityPackets;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.packets.InventoryPackets;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.packets.WorldPackets;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.providers.PaintingProvider;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.storage.BlockConnectionStorage;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.storage.BlockStorage;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.storage.EntityTracker1_13;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.storage.TabCompleteTracker;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import us.myles.ViaVersion.util.GsonUtil;
import us.myles.viaversion.libs.bungeecordchat.api.ChatColor;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.viaversion.libs.gson.JsonParseException;

public class Protocol1_13To1_12_2
extends Protocol<ClientboundPackets1_12_1, ClientboundPackets1_13, ServerboundPackets1_12_1, ServerboundPackets1_13> {
    public static final MappingData MAPPINGS = new MappingData();
    public static final PacketHandler POS_TO_3_INT = wrapper -> {
        Position position = wrapper.read(Type.POSITION);
        wrapper.write(Type.INT, position.getX());
        wrapper.write(Type.INT, Integer.valueOf(position.getY()));
        wrapper.write(Type.INT, position.getZ());
    };
    private static final PacketHandler SEND_DECLARE_COMMANDS_AND_TAGS = w -> {
        w.create(17, new ValueCreator(){

            @Override
            public void write(PacketWrapper wrapper) {
                wrapper.write(Type.VAR_INT, 2);
                wrapper.write(Type.VAR_INT, 0);
                wrapper.write(Type.VAR_INT, 1);
                wrapper.write(Type.VAR_INT, 1);
                wrapper.write(Type.VAR_INT, 22);
                wrapper.write(Type.VAR_INT, 0);
                wrapper.write(Type.STRING, "args");
                wrapper.write(Type.STRING, "brigadier:string");
                wrapper.write(Type.VAR_INT, 2);
                wrapper.write(Type.STRING, "minecraft:ask_server");
                wrapper.write(Type.VAR_INT, 0);
            }
        }).send(Protocol1_13To1_12_2.class);
        w.create(85, new ValueCreator(){

            @Override
            public void write(PacketWrapper wrapper) throws Exception {
                wrapper.write(Type.VAR_INT, MAPPINGS.getBlockTags().size());
                for (Map.Entry<String, Integer[]> tag : MAPPINGS.getBlockTags().entrySet()) {
                    wrapper.write(Type.STRING, tag.getKey());
                    wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, Protocol1_13To1_12_2.toPrimitive(tag.getValue()));
                }
                wrapper.write(Type.VAR_INT, MAPPINGS.getItemTags().size());
                for (Map.Entry<String, Integer[]> tag : MAPPINGS.getItemTags().entrySet()) {
                    wrapper.write(Type.STRING, tag.getKey());
                    wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, Protocol1_13To1_12_2.toPrimitive(tag.getValue()));
                }
                wrapper.write(Type.VAR_INT, MAPPINGS.getFluidTags().size());
                for (Map.Entry<String, Integer[]> tag : MAPPINGS.getFluidTags().entrySet()) {
                    wrapper.write(Type.STRING, tag.getKey());
                    wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, Protocol1_13To1_12_2.toPrimitive(tag.getValue()));
                }
            }
        }).send(Protocol1_13To1_12_2.class);
    };
    protected static final Map<ChatColor, Character> SCOREBOARD_TEAM_NAME_REWRITE = new HashMap<ChatColor, Character>();
    private static final Set<ChatColor> FORMATTING_CODES = Sets.newHashSet((Object[])new ChatColor[]{ChatColor.MAGIC, ChatColor.BOLD, ChatColor.STRIKETHROUGH, ChatColor.UNDERLINE, ChatColor.ITALIC, ChatColor.RESET});

    public Protocol1_13To1_12_2() {
        super(ClientboundPackets1_12_1.class, ClientboundPackets1_13.class, ServerboundPackets1_12_1.class, ServerboundPackets1_13.class);
    }

    @Override
    protected void registerPackets() {
        MetadataRewriter1_13To1_12_2 metadataRewriter = new MetadataRewriter1_13To1_12_2(this);
        EntityPackets.register(this);
        WorldPackets.register(this);
        InventoryPackets.register(this);
        this.registerOutgoing(State.LOGIN, 0, 0, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> ChatRewriter.processTranslate(wrapper.passthrough(Type.COMPONENT)));
            }
        });
        this.registerOutgoing(State.STATUS, 0, 0, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        String response = wrapper.get(Type.STRING, 0);
                        try {
                            JsonObject json = GsonUtil.getGson().fromJson(response, JsonObject.class);
                            if (json.has("favicon")) {
                                json.addProperty("favicon", json.get("favicon").getAsString().replace("\n", ""));
                            }
                            wrapper.set(Type.STRING, 0, GsonUtil.getGson().toJson(json));
                        }
                        catch (JsonParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        this.registerOutgoing(ClientboundPackets1_12_1.STATISTICS, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int size = wrapper.read(Type.VAR_INT);
                        ArrayList<StatisticData> remappedStats = new ArrayList<StatisticData>();
                        for (int i = 0; i < size; ++i) {
                            String name = wrapper.read(Type.STRING);
                            String[] split = name.split("\\.");
                            int categoryId = 0;
                            int newId = -1;
                            int value = wrapper.read(Type.VAR_INT);
                            if (split.length == 2) {
                                categoryId = 8;
                                Integer newIdRaw = StatisticMappings.CUSTOM_STATS.get(name);
                                if (newIdRaw != null) {
                                    newId = newIdRaw;
                                } else {
                                    Via.getPlatform().getLogger().warning("Could not find 1.13 -> 1.12.2 statistic mapping for " + name);
                                }
                            } else {
                                String category;
                                switch (category = split[1]) {
                                    case "mineBlock": {
                                        categoryId = 0;
                                        break;
                                    }
                                    case "craftItem": {
                                        categoryId = 1;
                                        break;
                                    }
                                    case "useItem": {
                                        categoryId = 2;
                                        break;
                                    }
                                    case "breakItem": {
                                        categoryId = 3;
                                        break;
                                    }
                                    case "pickup": {
                                        categoryId = 4;
                                        break;
                                    }
                                    case "drop": {
                                        categoryId = 5;
                                        break;
                                    }
                                    case "killEntity": {
                                        categoryId = 6;
                                        break;
                                    }
                                    case "entityKilledBy": {
                                        categoryId = 7;
                                    }
                                }
                            }
                            if (newId == -1) continue;
                            remappedStats.add(new StatisticData(categoryId, newId, value));
                        }
                        wrapper.write(Type.VAR_INT, remappedStats.size());
                        for (StatisticData stat : remappedStats) {
                            wrapper.write(Type.VAR_INT, stat.getCategoryId());
                            wrapper.write(Type.VAR_INT, stat.getNewId());
                            wrapper.write(Type.VAR_INT, stat.getValue());
                        }
                    }
                });
            }
        });
        this.registerOutgoing(ClientboundPackets1_12_1.BOSSBAR, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int action = wrapper.get(Type.VAR_INT, 0);
                        if (action == 0 || action == 3) {
                            ChatRewriter.processTranslate(wrapper.passthrough(Type.COMPONENT));
                        }
                    }
                });
            }
        });
        this.registerOutgoing(ClientboundPackets1_12_1.CHAT_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> ChatRewriter.processTranslate(wrapper.passthrough(Type.COMPONENT)));
            }
        });
        this.registerOutgoing(ClientboundPackets1_12_1.TAB_COMPLETE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper wrapper) throws Exception {
                        int length;
                        int index;
                        wrapper.write(Type.VAR_INT, wrapper.user().get(TabCompleteTracker.class).getTransactionId());
                        String input = wrapper.user().get(TabCompleteTracker.class).getInput();
                        if (input.endsWith(" ") || input.isEmpty()) {
                            index = input.length();
                            length = 0;
                        } else {
                            int lastSpace;
                            index = lastSpace = input.lastIndexOf(32) + 1;
                            length = input.length() - lastSpace;
                        }
                        wrapper.write(Type.VAR_INT, index);
                        wrapper.write(Type.VAR_INT, length);
                        int count = wrapper.passthrough(Type.VAR_INT);
                        for (int i = 0; i < count; ++i) {
                            String suggestion = wrapper.read(Type.STRING);
                            if (suggestion.startsWith("/") && index == 0) {
                                suggestion = suggestion.substring(1);
                            }
                            wrapper.write(Type.STRING, suggestion);
                            wrapper.write(Type.BOOLEAN, false);
                        }
                    }
                });
            }
        });
        this.registerOutgoing(ClientboundPackets1_12_1.OPEN_WINDOW, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.handler(wrapper -> ChatRewriter.processTranslate(wrapper.passthrough(Type.COMPONENT)));
            }
        });
        this.registerOutgoing(ClientboundPackets1_12_1.COOLDOWN, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int item = wrapper.read(Type.VAR_INT);
                        int ticks = wrapper.read(Type.VAR_INT);
                        wrapper.cancel();
                        if (item == 383) {
                            Integer newItem;
                            for (int i = 0; i < 44 && (newItem = Integer.valueOf(Protocol1_13To1_12_2.this.getMappingData().getItemMappings().get(item << 16 | i))) != null; ++i) {
                                PacketWrapper packet = wrapper.create(24);
                                packet.write(Type.VAR_INT, newItem);
                                packet.write(Type.VAR_INT, ticks);
                                packet.send(Protocol1_13To1_12_2.class);
                            }
                        } else {
                            int newItem;
                            for (int i = 0; i < 16 && (newItem = Protocol1_13To1_12_2.this.getMappingData().getItemMappings().get(item << 4 | i)) != -1; ++i) {
                                PacketWrapper packet = wrapper.create(24);
                                packet.write(Type.VAR_INT, newItem);
                                packet.write(Type.VAR_INT, ticks);
                                packet.send(Protocol1_13To1_12_2.class);
                            }
                        }
                    }
                });
            }
        });
        this.registerOutgoing(ClientboundPackets1_12_1.DISCONNECT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> ChatRewriter.processTranslate(wrapper.passthrough(Type.COMPONENT)));
            }
        });
        this.registerOutgoing(ClientboundPackets1_12_1.EFFECT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int id = wrapper.get(Type.INT, 0);
                        int data = wrapper.get(Type.INT, 1);
                        if (id == 1010) {
                            wrapper.set(Type.INT, 1, Protocol1_13To1_12_2.this.getMappingData().getItemMappings().get(data << 4));
                        } else if (id == 2001) {
                            int blockId = data & 0xFFF;
                            int blockData = data >> 12;
                            wrapper.set(Type.INT, 1, WorldPackets.toNewId(blockId << 4 | blockData));
                        }
                    }
                });
            }
        });
        this.registerOutgoing(ClientboundPackets1_12_1.JOIN_GAME, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityId = wrapper.get(Type.INT, 0);
                        wrapper.user().get(EntityTracker1_13.class).addEntity(entityId, Entity1_13Types.EntityType.PLAYER);
                        ClientWorld clientChunks = wrapper.user().get(ClientWorld.class);
                        int dimensionId = wrapper.get(Type.INT, 1);
                        clientChunks.setEnvironment(dimensionId);
                    }
                });
                this.handler(SEND_DECLARE_COMMANDS_AND_TAGS);
            }
        });
        this.registerOutgoing(ClientboundPackets1_12_1.CRAFT_RECIPE_RESPONSE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.BYTE);
                this.handler(wrapper -> wrapper.write(Type.STRING, "viaversion:legacy/" + wrapper.read(Type.VAR_INT)));
            }
        });
        this.registerOutgoing(ClientboundPackets1_12_1.COMBAT_EVENT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (wrapper.get(Type.VAR_INT, 0) == 2) {
                            wrapper.passthrough(Type.VAR_INT);
                            wrapper.passthrough(Type.INT);
                            ChatRewriter.processTranslate(wrapper.passthrough(Type.COMPONENT));
                        }
                    }
                });
            }
        });
        this.registerOutgoing(ClientboundPackets1_12_1.MAP_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int iconCount = wrapper.passthrough(Type.VAR_INT);
                        for (int i = 0; i < iconCount; ++i) {
                            byte directionAndType = wrapper.read(Type.BYTE);
                            int type = (directionAndType & 0xF0) >> 4;
                            wrapper.write(Type.VAR_INT, type);
                            wrapper.passthrough(Type.BYTE);
                            wrapper.passthrough(Type.BYTE);
                            byte direction = (byte)(directionAndType & 0xF);
                            wrapper.write(Type.BYTE, direction);
                            wrapper.write(Type.OPTIONAL_COMPONENT, null);
                        }
                    }
                });
            }
        });
        this.registerOutgoing(ClientboundPackets1_12_1.UNLOCK_RECIPES, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper wrapper) throws Exception {
                        wrapper.write(Type.BOOLEAN, false);
                        wrapper.write(Type.BOOLEAN, false);
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int action = wrapper.get(Type.VAR_INT, 0);
                        for (int i = 0; i < (action == 0 ? 2 : 1); ++i) {
                            int[] ids = wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                            String[] stringIds = new String[ids.length];
                            for (int j = 0; j < ids.length; ++j) {
                                stringIds[j] = "viaversion:legacy/" + ids[j];
                            }
                            wrapper.write(Type.STRING_ARRAY, stringIds);
                        }
                        if (action == 0) {
                            wrapper.create(84, new ValueCreator(){

                                @Override
                                public void write(PacketWrapper wrapper) throws Exception {
                                    wrapper.write(Type.VAR_INT, RecipeData.recipes.size());
                                    for (Map.Entry<String, RecipeData.Recipe> entry : RecipeData.recipes.entrySet()) {
                                        wrapper.write(Type.STRING, entry.getKey());
                                        wrapper.write(Type.STRING, entry.getValue().getType());
                                        switch (entry.getValue().getType()) {
                                            case "crafting_shapeless": {
                                                int i;
                                                Item[] clone;
                                                wrapper.write(Type.STRING, entry.getValue().getGroup());
                                                wrapper.write(Type.VAR_INT, entry.getValue().getIngredients().length);
                                                for (Item[] ingredient : entry.getValue().getIngredients()) {
                                                    clone = (Item[])ingredient.clone();
                                                    for (i = 0; i < clone.length; ++i) {
                                                        if (clone[i] == null) continue;
                                                        clone[i] = new Item(clone[i]);
                                                    }
                                                    wrapper.write(Type.FLAT_ITEM_ARRAY_VAR_INT, clone);
                                                }
                                                wrapper.write(Type.FLAT_ITEM, new Item(entry.getValue().getResult()));
                                                break;
                                            }
                                            case "crafting_shaped": {
                                                int i;
                                                Item[] clone;
                                                wrapper.write(Type.VAR_INT, entry.getValue().getWidth());
                                                wrapper.write(Type.VAR_INT, entry.getValue().getHeight());
                                                wrapper.write(Type.STRING, entry.getValue().getGroup());
                                                for (Item[] ingredient : entry.getValue().getIngredients()) {
                                                    clone = (Item[])ingredient.clone();
                                                    for (i = 0; i < clone.length; ++i) {
                                                        if (clone[i] == null) continue;
                                                        clone[i] = new Item(clone[i]);
                                                    }
                                                    wrapper.write(Type.FLAT_ITEM_ARRAY_VAR_INT, clone);
                                                }
                                                wrapper.write(Type.FLAT_ITEM, new Item(entry.getValue().getResult()));
                                                break;
                                            }
                                            case "smelting": {
                                                wrapper.write(Type.STRING, entry.getValue().getGroup());
                                                Item[] clone = (Item[])entry.getValue().getIngredient().clone();
                                                for (int i = 0; i < clone.length; ++i) {
                                                    if (clone[i] == null) continue;
                                                    clone[i] = new Item(clone[i]);
                                                }
                                                wrapper.write(Type.FLAT_ITEM_ARRAY_VAR_INT, clone);
                                                wrapper.write(Type.FLAT_ITEM, new Item(entry.getValue().getResult()));
                                                wrapper.write(Type.FLOAT, Float.valueOf(entry.getValue().getExperience()));
                                                wrapper.write(Type.VAR_INT, entry.getValue().getCookingTime());
                                            }
                                        }
                                    }
                                }
                            }).send(Protocol1_13To1_12_2.class, true, true);
                        }
                    }
                });
            }
        });
        this.registerOutgoing(ClientboundPackets1_12_1.RESPAWN, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
                        int dimensionId = wrapper.get(Type.INT, 0);
                        clientWorld.setEnvironment(dimensionId);
                        if (Via.getConfig().isServersideBlockConnections()) {
                            ConnectionData.clearBlockStorage(wrapper.user());
                        }
                    }
                });
                this.handler(SEND_DECLARE_COMMANDS_AND_TAGS);
            }
        });
        this.registerOutgoing(ClientboundPackets1_12_1.SCOREBOARD_OBJECTIVE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        byte mode = wrapper.get(Type.BYTE, 0);
                        if (mode == 0 || mode == 2) {
                            String value = wrapper.read(Type.STRING);
                            wrapper.write(Type.COMPONENT, ChatRewriter.legacyTextToJson(value));
                            String type = wrapper.read(Type.STRING);
                            wrapper.write(Type.VAR_INT, type.equals("integer") ? 0 : 1);
                        }
                    }
                });
            }
        });
        this.registerOutgoing(ClientboundPackets1_12_1.TEAMS, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        byte action = wrapper.get(Type.BYTE, 0);
                        if (action == 0 || action == 2) {
                            String displayName = wrapper.read(Type.STRING);
                            wrapper.write(Type.COMPONENT, ChatRewriter.legacyTextToJson(displayName));
                            String prefix = wrapper.read(Type.STRING);
                            String suffix = wrapper.read(Type.STRING);
                            wrapper.passthrough(Type.BYTE);
                            wrapper.passthrough(Type.STRING);
                            wrapper.passthrough(Type.STRING);
                            int colour = wrapper.read(Type.BYTE).intValue();
                            if (colour == -1) {
                                colour = 21;
                            }
                            if (Via.getConfig().is1_13TeamColourFix()) {
                                ChatColor lastColor = Protocol1_13To1_12_2.this.getLastColor(prefix);
                                colour = lastColor.ordinal();
                                suffix = lastColor.toString() + suffix;
                            }
                            wrapper.write(Type.VAR_INT, colour);
                            wrapper.write(Type.COMPONENT, ChatRewriter.legacyTextToJson(prefix));
                            wrapper.write(Type.COMPONENT, ChatRewriter.legacyTextToJson(suffix));
                        }
                        if (action == 0 || action == 3 || action == 4) {
                            String[] names = wrapper.read(Type.STRING_ARRAY);
                            for (int i = 0; i < names.length; ++i) {
                                names[i] = Protocol1_13To1_12_2.this.rewriteTeamMemberName(names[i]);
                            }
                            wrapper.write(Type.STRING_ARRAY, names);
                        }
                    }
                });
            }
        });
        this.registerOutgoing(ClientboundPackets1_12_1.UPDATE_SCORE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        String displayName = wrapper.read(Type.STRING);
                        displayName = Protocol1_13To1_12_2.this.rewriteTeamMemberName(displayName);
                        wrapper.write(Type.STRING, displayName);
                        byte action = wrapper.read(Type.BYTE);
                        wrapper.write(Type.BYTE, action);
                        wrapper.passthrough(Type.STRING);
                        if (action != 1) {
                            wrapper.passthrough(Type.VAR_INT);
                        }
                    }
                });
            }
        });
        this.registerOutgoing(ClientboundPackets1_12_1.TITLE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int action = wrapper.get(Type.VAR_INT, 0);
                        if (action >= 0 && action <= 2) {
                            ChatRewriter.processTranslate(wrapper.passthrough(Type.COMPONENT));
                        }
                    }
                });
            }
        });
        new SoundRewriter(this).registerSound(ClientboundPackets1_12_1.SOUND);
        this.registerOutgoing(ClientboundPackets1_12_1.TAB_LIST, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ChatRewriter.processTranslate(wrapper.passthrough(Type.COMPONENT));
                        ChatRewriter.processTranslate(wrapper.passthrough(Type.COMPONENT));
                    }
                });
            }
        });
        this.registerOutgoing(ClientboundPackets1_12_1.ADVANCEMENTS, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.passthrough(Type.BOOLEAN);
                        int size = wrapper.passthrough(Type.VAR_INT);
                        for (int i = 0; i < size; ++i) {
                            wrapper.passthrough(Type.STRING);
                            if (wrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                                wrapper.passthrough(Type.STRING);
                            }
                            if (wrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                                ChatRewriter.processTranslate(wrapper.passthrough(Type.COMPONENT));
                                ChatRewriter.processTranslate(wrapper.passthrough(Type.COMPONENT));
                                Item icon = wrapper.read(Type.ITEM);
                                InventoryPackets.toClient(icon);
                                wrapper.write(Type.FLAT_ITEM, icon);
                                wrapper.passthrough(Type.VAR_INT);
                                int flags = wrapper.passthrough(Type.INT);
                                if ((flags & 1) != 0) {
                                    wrapper.passthrough(Type.STRING);
                                }
                                wrapper.passthrough(Type.FLOAT);
                                wrapper.passthrough(Type.FLOAT);
                            }
                            wrapper.passthrough(Type.STRING_ARRAY);
                            int arrayLength = wrapper.passthrough(Type.VAR_INT);
                            for (int array = 0; array < arrayLength; ++array) {
                                wrapper.passthrough(Type.STRING_ARRAY);
                            }
                        }
                    }
                });
            }
        });
        this.cancelIncoming(State.LOGIN, 2);
        this.cancelIncoming(ServerboundPackets1_13.QUERY_BLOCK_NBT);
        this.registerIncoming(ServerboundPackets1_13.TAB_COMPLETE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (Via.getConfig().isDisable1_13AutoComplete()) {
                            wrapper.cancel();
                        }
                        int tid = wrapper.read(Type.VAR_INT);
                        wrapper.user().get(TabCompleteTracker.class).setTransactionId(tid);
                    }
                });
                this.map(Type.STRING, new ValueTransformer<String, String>(Type.STRING){

                    @Override
                    public String transform(PacketWrapper wrapper, String inputValue) {
                        wrapper.user().get(TabCompleteTracker.class).setInput(inputValue);
                        return "/" + inputValue;
                    }
                });
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper wrapper) throws Exception {
                        wrapper.write(Type.BOOLEAN, false);
                        wrapper.write(Type.OPTIONAL_POSITION, null);
                        if (!wrapper.isCancelled() && Via.getConfig().get1_13TabCompleteDelay() > 0) {
                            TabCompleteTracker tracker = wrapper.user().get(TabCompleteTracker.class);
                            wrapper.cancel();
                            tracker.setTimeToSend(System.currentTimeMillis() + (long)Via.getConfig().get1_13TabCompleteDelay() * 50L);
                            tracker.setLastTabComplete(wrapper.get(Type.STRING, 0));
                        }
                    }
                });
            }
        });
        this.registerIncoming(ServerboundPackets1_13.EDIT_BOOK, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Item item = wrapper.read(Type.FLAT_ITEM);
                        boolean isSigning = wrapper.read(Type.BOOLEAN);
                        InventoryPackets.toServer(item);
                        wrapper.write(Type.STRING, isSigning ? "MC|BSign" : "MC|BEdit");
                        wrapper.write(Type.ITEM, item);
                    }
                });
            }
        });
        this.cancelIncoming(ServerboundPackets1_13.ENTITY_NBT_REQUEST);
        this.registerIncoming(ServerboundPackets1_13.PICK_ITEM, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper wrapper) throws Exception {
                        wrapper.write(Type.STRING, "MC|PickItem");
                    }
                });
            }
        });
        this.registerIncoming(ServerboundPackets1_13.CRAFT_RECIPE_REQUEST, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.BYTE);
                this.handler(wrapper -> {
                    Integer id;
                    String s = wrapper.read(Type.STRING);
                    if (s.length() < 19 || (id = Ints.tryParse((String)s.substring(18))) == null) {
                        wrapper.cancel();
                        return;
                    }
                    wrapper.write(Type.VAR_INT, id);
                });
            }
        });
        this.registerIncoming(ServerboundPackets1_13.RECIPE_BOOK_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int type = wrapper.get(Type.VAR_INT, 0);
                        if (type == 0) {
                            Integer id;
                            String s = wrapper.read(Type.STRING);
                            if (s.length() < 19 || (id = Ints.tryParse((String)s.substring(18))) == null) {
                                wrapper.cancel();
                                return;
                            }
                            wrapper.write(Type.INT, id);
                        }
                        if (type == 1) {
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.read(Type.BOOLEAN);
                            wrapper.read(Type.BOOLEAN);
                        }
                    }
                });
            }
        });
        this.registerIncoming(ServerboundPackets1_13.RENAME_ITEM, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.create(wrapper -> wrapper.write(Type.STRING, "MC|ItemName"));
            }
        });
        this.registerIncoming(ServerboundPackets1_13.SELECT_TRADE, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.create(wrapper -> wrapper.write(Type.STRING, "MC|TrSel"));
                this.map((Type)Type.VAR_INT, Type.INT);
            }
        });
        this.registerIncoming(ServerboundPackets1_13.SET_BEACON_EFFECT, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.create(wrapper -> wrapper.write(Type.STRING, "MC|Beacon"));
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map((Type)Type.VAR_INT, Type.INT);
            }
        });
        this.registerIncoming(ServerboundPackets1_13.UPDATE_COMMAND_BLOCK, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.create(wrapper -> wrapper.write(Type.STRING, "MC|AutoCmd"));
                this.handler(POS_TO_3_INT);
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int mode = wrapper.read(Type.VAR_INT);
                        byte flags = wrapper.read(Type.BYTE);
                        String stringMode = mode == 0 ? "SEQUENCE" : (mode == 1 ? "AUTO" : "REDSTONE");
                        wrapper.write(Type.BOOLEAN, (flags & 1) != 0);
                        wrapper.write(Type.STRING, stringMode);
                        wrapper.write(Type.BOOLEAN, (flags & 2) != 0);
                        wrapper.write(Type.BOOLEAN, (flags & 4) != 0);
                    }
                });
            }
        });
        this.registerIncoming(ServerboundPackets1_13.UPDATE_COMMAND_BLOCK_MINECART, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper wrapper) throws Exception {
                        wrapper.write(Type.STRING, "MC|AdvCmd");
                        wrapper.write(Type.BYTE, (byte)1);
                    }
                });
                this.map((Type)Type.VAR_INT, Type.INT);
            }
        });
        this.registerIncoming(ServerboundPackets1_13.UPDATE_STRUCTURE_BLOCK, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.create(wrapper -> wrapper.write(Type.STRING, "MC|Struct"));
                this.handler(POS_TO_3_INT);
                this.map(Type.VAR_INT, new ValueTransformer<Integer, Byte>(Type.BYTE){

                    @Override
                    public Byte transform(PacketWrapper wrapper, Integer action) throws Exception {
                        return (byte)(action + 1);
                    }
                });
                this.map(Type.VAR_INT, new ValueTransformer<Integer, String>(Type.STRING){

                    @Override
                    public String transform(PacketWrapper wrapper, Integer mode) throws Exception {
                        return mode == 0 ? "SAVE" : (mode == 1 ? "LOAD" : (mode == 2 ? "CORNER" : "DATA"));
                    }
                });
                this.map(Type.STRING);
                this.map(Type.BYTE, Type.INT);
                this.map(Type.BYTE, Type.INT);
                this.map(Type.BYTE, Type.INT);
                this.map(Type.BYTE, Type.INT);
                this.map(Type.BYTE, Type.INT);
                this.map(Type.BYTE, Type.INT);
                this.map(Type.VAR_INT, new ValueTransformer<Integer, String>(Type.STRING){

                    @Override
                    public String transform(PacketWrapper wrapper, Integer mirror) throws Exception {
                        return mirror == 0 ? "NONE" : (mirror == 1 ? "LEFT_RIGHT" : "FRONT_BACK");
                    }
                });
                this.map(Type.VAR_INT, new ValueTransformer<Integer, String>(Type.STRING){

                    @Override
                    public String transform(PacketWrapper wrapper, Integer rotation) throws Exception {
                        return rotation == 0 ? "NONE" : (rotation == 1 ? "CLOCKWISE_90" : (rotation == 2 ? "CLOCKWISE_180" : "COUNTERCLOCKWISE_90"));
                    }
                });
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        float integrity = wrapper.read(Type.FLOAT).floatValue();
                        long seed = wrapper.read(Type.VAR_LONG);
                        byte flags = wrapper.read(Type.BYTE);
                        wrapper.write(Type.BOOLEAN, (flags & 1) != 0);
                        wrapper.write(Type.BOOLEAN, (flags & 2) != 0);
                        wrapper.write(Type.BOOLEAN, (flags & 4) != 0);
                        wrapper.write(Type.FLOAT, Float.valueOf(integrity));
                        wrapper.write(Type.VAR_LONG, seed);
                    }
                });
            }
        });
    }

    @Override
    protected void onMappingDataLoaded() {
        ConnectionData.init();
        RecipeData.init();
        BlockIdData.init();
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new EntityTracker1_13(userConnection));
        userConnection.put(new TabCompleteTracker(userConnection));
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
        userConnection.put(new BlockStorage(userConnection));
        if (Via.getConfig().isServersideBlockConnections() && Via.getManager().getProviders().get(BlockConnectionProvider.class) instanceof PacketBlockConnectionProvider) {
            userConnection.put(new BlockConnectionStorage(userConnection));
        }
    }

    @Override
    protected void register(ViaProviders providers) {
        providers.register(BlockEntityProvider.class, new BlockEntityProvider());
        providers.register(PaintingProvider.class, new PaintingProvider());
    }

    public ChatColor getLastColor(String input) {
        int length = input.length();
        for (int index = length - 1; index > -1; --index) {
            char c;
            ChatColor color;
            char section = input.charAt(index);
            if (section != '\u00a7' || index >= length - 1 || (color = ChatColor.getByChar(c = input.charAt(index + 1))) == null || FORMATTING_CODES.contains(color)) continue;
            return color;
        }
        return ChatColor.RESET;
    }

    protected String rewriteTeamMemberName(String name) {
        if (ChatColor.stripColor(name).isEmpty()) {
            StringBuilder newName = new StringBuilder();
            for (int i = 1; i < name.length(); i += 2) {
                char colorChar = name.charAt(i);
                Character rewrite = SCOREBOARD_TEAM_NAME_REWRITE.get(ChatColor.getByChar(colorChar));
                if (rewrite == null) {
                    rewrite = Character.valueOf(colorChar);
                }
                newName.append('\u00a7').append(rewrite);
            }
            name = newName.toString();
        }
        return name;
    }

    public static int[] toPrimitive(Integer[] array) {
        int[] prim = new int[array.length];
        for (int i = 0; i < array.length; ++i) {
            prim[i] = array[i];
        }
        return prim;
    }

    @Override
    public MappingData getMappingData() {
        return MAPPINGS;
    }

    static {
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.BLACK, Character.valueOf('g'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.DARK_BLUE, Character.valueOf('h'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.DARK_GREEN, Character.valueOf('i'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.DARK_AQUA, Character.valueOf('j'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.DARK_RED, Character.valueOf('p'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.DARK_PURPLE, Character.valueOf('q'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.GOLD, Character.valueOf('s'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.GRAY, Character.valueOf('t'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.DARK_GRAY, Character.valueOf('u'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.BLUE, Character.valueOf('v'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.GREEN, Character.valueOf('w'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.AQUA, Character.valueOf('x'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.RED, Character.valueOf('y'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.LIGHT_PURPLE, Character.valueOf('z'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.YELLOW, Character.valueOf('!'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.WHITE, Character.valueOf('?'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.MAGIC, Character.valueOf('#'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.BOLD, Character.valueOf('('));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.STRIKETHROUGH, Character.valueOf(')'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.UNDERLINE, Character.valueOf(':'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.ITALIC, Character.valueOf(';'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(ChatColor.RESET, Character.valueOf('/'));
    }
}

