/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2;

import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.PacketBlockConnectionProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.BlockIdData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ComponentRewriter1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.RecipeData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.StatisticData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.StatisticMappings;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.metadata.MetadataRewriter1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.PaintingProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.PlayerLookTargetProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockConnectionStorage;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockStorage;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.TabCompleteTracker;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.util.ChatColorUtil;
import com.viaversion.viaversion.util.GsonUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Protocol1_13To1_12_2
extends AbstractProtocol<ClientboundPackets1_12_1, ClientboundPackets1_13, ServerboundPackets1_12_1, ServerboundPackets1_13> {
    public static final MappingData MAPPINGS = new MappingData();
    private static final Map<Character, Character> SCOREBOARD_TEAM_NAME_REWRITE = new HashMap<Character, Character>();
    private static final Set<Character> FORMATTING_CODES = Sets.newHashSet(Character.valueOf('k'), Character.valueOf('l'), Character.valueOf('m'), Character.valueOf('n'), Character.valueOf('o'), Character.valueOf('r'));
    private final MetadataRewriter1_13To1_12_2 entityRewriter = new MetadataRewriter1_13To1_12_2(this);
    private final InventoryPackets itemRewriter = new InventoryPackets(this);
    private final ComponentRewriter1_13<ClientboundPackets1_12_1> componentRewriter = new ComponentRewriter1_13<ClientboundPackets1_12_1>(this);
    public static final PacketHandler POS_TO_3_INT;
    public static final PacketHandler SEND_DECLARE_COMMANDS_AND_TAGS;

    public Protocol1_13To1_12_2() {
        super(ClientboundPackets1_12_1.class, ClientboundPackets1_13.class, ServerboundPackets1_12_1.class, ServerboundPackets1_13.class);
    }

    @Override
    protected void registerPackets() {
        this.entityRewriter.register();
        this.itemRewriter.register();
        EntityPackets.register(this);
        WorldPackets.register(this);
        this.registerClientbound(State.LOGIN, 0, 0, this::lambda$registerPackets$4);
        this.registerClientbound(State.STATUS, 0, 0, new PacketHandlers(this){
            final Protocol1_13To1_12_2 this$0;
            {
                this.this$0 = protocol1_13To1_12_2;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                String string = packetWrapper.get(Type.STRING, 0);
                try {
                    JsonObject jsonObject = GsonUtil.getGson().fromJson(string, JsonObject.class);
                    if (jsonObject.has("favicon")) {
                        jsonObject.addProperty("favicon", jsonObject.get("favicon").getAsString().replace("\n", ""));
                    }
                    packetWrapper.set(Type.STRING, 0, GsonUtil.getGson().toJson(jsonObject));
                } catch (JsonParseException jsonParseException) {
                    jsonParseException.printStackTrace();
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_12_1.STATISTICS, Protocol1_13To1_12_2::lambda$registerPackets$5);
        this.componentRewriter.registerBossBar(ClientboundPackets1_12_1.BOSSBAR);
        this.componentRewriter.registerComponentPacket(ClientboundPackets1_12_1.CHAT_MESSAGE);
        this.registerClientbound(ClientboundPackets1_12_1.TAB_COMPLETE, Protocol1_13To1_12_2::lambda$registerPackets$6);
        this.registerClientbound(ClientboundPackets1_12_1.OPEN_WINDOW, new PacketHandlers(this){
            final Protocol1_13To1_12_2 this$0;
            {
                this.this$0 = protocol1_13To1_12_2;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Protocol1_13To1_12_2.access$000(this.this$0).processText(packetWrapper.passthrough(Type.COMPONENT));
            }
        });
        this.registerClientbound(ClientboundPackets1_12_1.COOLDOWN, Protocol1_13To1_12_2::lambda$registerPackets$7);
        this.componentRewriter.registerComponentPacket(ClientboundPackets1_12_1.DISCONNECT);
        this.registerClientbound(ClientboundPackets1_12_1.EFFECT, new PacketHandlers(this){
            final Protocol1_13To1_12_2 this$0;
            {
                this.this$0 = protocol1_13To1_12_2;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.INT, 0);
                int n2 = packetWrapper.get(Type.INT, 1);
                if (n == 1010) {
                    packetWrapper.set(Type.INT, 1, this.this$0.getMappingData().getItemMappings().getNewId(n2 << 4));
                } else if (n == 2001) {
                    int n3 = n2 & 0xFFF;
                    int n4 = n2 >> 12;
                    packetWrapper.set(Type.INT, 1, WorldPackets.toNewId(n3 << 4 | n4));
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_12_1.CRAFT_RECIPE_RESPONSE, new PacketHandlers(this){
            final Protocol1_13To1_12_2 this$0;
            {
                this.this$0 = protocol1_13To1_12_2;
            }

            @Override
            public void register() {
                this.map(Type.BYTE);
                this.handler(4::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.STRING, "viaversion:legacy/" + packetWrapper.read(Type.VAR_INT));
            }
        });
        this.componentRewriter.registerCombatEvent(ClientboundPackets1_12_1.COMBAT_EVENT);
        this.registerClientbound(ClientboundPackets1_12_1.MAP_DATA, new PacketHandlers(this){
            final Protocol1_13To1_12_2 this$0;
            {
                this.this$0 = protocol1_13To1_12_2;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(5::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.passthrough(Type.VAR_INT);
                for (int i = 0; i < n; ++i) {
                    byte by = packetWrapper.read(Type.BYTE);
                    int n2 = (by & 0xF0) >> 4;
                    packetWrapper.write(Type.VAR_INT, n2);
                    packetWrapper.passthrough(Type.BYTE);
                    packetWrapper.passthrough(Type.BYTE);
                    byte by2 = (byte)(by & 0xF);
                    packetWrapper.write(Type.BYTE, by2);
                    packetWrapper.write(Type.OPTIONAL_COMPONENT, null);
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_12_1.UNLOCK_RECIPES, new PacketHandlers(this){
            final Protocol1_13To1_12_2 this$0;
            {
                this.this$0 = protocol1_13To1_12_2;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.handler(6::lambda$register$0);
                this.handler(6::lambda$register$2);
            }

            private static void lambda$register$2(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                for (int i = 0; i < (n == 0 ? 2 : 1); ++i) {
                    int[] nArray = packetWrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                    String[] stringArray = new String[nArray.length];
                    for (int j = 0; j < nArray.length; ++j) {
                        stringArray[j] = "viaversion:legacy/" + nArray[j];
                    }
                    packetWrapper.write(Type.STRING_ARRAY, stringArray);
                }
                if (n == 0) {
                    packetWrapper.create(ClientboundPackets1_13.DECLARE_RECIPES, 6::lambda$null$1).send(Protocol1_13To1_12_2.class);
                }
            }

            private static void lambda$null$1(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.VAR_INT, RecipeData.recipes.size());
                for (Map.Entry<String, RecipeData.Recipe> entry : RecipeData.recipes.entrySet()) {
                    packetWrapper.write(Type.STRING, entry.getKey());
                    packetWrapper.write(Type.STRING, entry.getValue().getType());
                    switch (entry.getValue().getType()) {
                        case "crafting_shapeless": {
                            int n;
                            Item[] itemArray;
                            packetWrapper.write(Type.STRING, entry.getValue().getGroup());
                            packetWrapper.write(Type.VAR_INT, entry.getValue().getIngredients().length);
                            for (Item item : entry.getValue().getIngredients()) {
                                itemArray = (Item[])item.clone();
                                for (n = 0; n < itemArray.length; ++n) {
                                    if (itemArray[n] == null) continue;
                                    itemArray[n] = new DataItem(itemArray[n]);
                                }
                                packetWrapper.write(Type.FLAT_ITEM_ARRAY_VAR_INT, itemArray);
                            }
                            packetWrapper.write(Type.FLAT_ITEM, new DataItem(entry.getValue().getResult()));
                            break;
                        }
                        case "crafting_shaped": {
                            int n;
                            Item[] itemArray;
                            packetWrapper.write(Type.VAR_INT, entry.getValue().getWidth());
                            packetWrapper.write(Type.VAR_INT, entry.getValue().getHeight());
                            packetWrapper.write(Type.STRING, entry.getValue().getGroup());
                            for (Item item : entry.getValue().getIngredients()) {
                                itemArray = (Item[])item.clone();
                                for (n = 0; n < itemArray.length; ++n) {
                                    if (itemArray[n] == null) continue;
                                    itemArray[n] = new DataItem(itemArray[n]);
                                }
                                packetWrapper.write(Type.FLAT_ITEM_ARRAY_VAR_INT, itemArray);
                            }
                            packetWrapper.write(Type.FLAT_ITEM, new DataItem(entry.getValue().getResult()));
                            break;
                        }
                        case "smelting": {
                            packetWrapper.write(Type.STRING, entry.getValue().getGroup());
                            Item[] itemArray = (Item[])entry.getValue().getIngredient().clone();
                            for (int i = 0; i < itemArray.length; ++i) {
                                if (itemArray[i] == null) continue;
                                itemArray[i] = new DataItem(itemArray[i]);
                            }
                            packetWrapper.write(Type.FLAT_ITEM_ARRAY_VAR_INT, itemArray);
                            packetWrapper.write(Type.FLAT_ITEM, new DataItem(entry.getValue().getResult()));
                            packetWrapper.write(Type.FLOAT, Float.valueOf(entry.getValue().getExperience()));
                            packetWrapper.write(Type.VAR_INT, entry.getValue().getCookingTime());
                            break;
                        }
                    }
                }
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BOOLEAN, false);
                packetWrapper.write(Type.BOOLEAN, false);
            }
        });
        this.registerClientbound(ClientboundPackets1_12_1.RESPAWN, new PacketHandlers(this){
            final Protocol1_13To1_12_2 this$0;
            {
                this.this$0 = protocol1_13To1_12_2;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.handler(7::lambda$register$0);
                this.handler(SEND_DECLARE_COMMANDS_AND_TAGS);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                int n = packetWrapper.get(Type.INT, 0);
                clientWorld.setEnvironment(n);
                if (Via.getConfig().isServersideBlockConnections()) {
                    ConnectionData.clearBlockStorage(packetWrapper.user());
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_12_1.SCOREBOARD_OBJECTIVE, new PacketHandlers(this){
            final Protocol1_13To1_12_2 this$0;
            {
                this.this$0 = protocol1_13To1_12_2;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.handler(8::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                byte by = packetWrapper.get(Type.BYTE, 0);
                if (by == 0 || by == 2) {
                    String string = packetWrapper.read(Type.STRING);
                    packetWrapper.write(Type.COMPONENT, ChatRewriter.legacyTextToJson(string));
                    String string2 = packetWrapper.read(Type.STRING);
                    packetWrapper.write(Type.VAR_INT, string2.equals("integer") ? 0 : 1);
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_12_1.TEAMS, new PacketHandlers(this){
            final Protocol1_13To1_12_2 this$0;
            {
                this.this$0 = protocol1_13To1_12_2;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                String[] stringArray;
                byte by = packetWrapper.get(Type.BYTE, 0);
                if (by == 0 || by == 2) {
                    stringArray = packetWrapper.read(Type.STRING);
                    packetWrapper.write(Type.COMPONENT, ChatRewriter.legacyTextToJson((String)stringArray));
                    String string = packetWrapper.read(Type.STRING);
                    String string2 = packetWrapper.read(Type.STRING);
                    packetWrapper.passthrough(Type.BYTE);
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.STRING);
                    int n = packetWrapper.read(Type.BYTE).intValue();
                    if (n == -1) {
                        n = 21;
                    }
                    if (Via.getConfig().is1_13TeamColourFix()) {
                        char c = this.this$0.getLastColorChar(string);
                        n = ChatColorUtil.getColorOrdinal(c);
                        string2 = '\u00a7' + Character.toString(c) + string2;
                    }
                    packetWrapper.write(Type.VAR_INT, n);
                    packetWrapper.write(Type.COMPONENT, ChatRewriter.legacyTextToJson(string));
                    packetWrapper.write(Type.COMPONENT, ChatRewriter.legacyTextToJson(string2));
                }
                if (by == 0 || by == 3 || by == 4) {
                    stringArray = packetWrapper.read(Type.STRING_ARRAY);
                    for (int i = 0; i < stringArray.length; ++i) {
                        stringArray[i] = this.this$0.rewriteTeamMemberName(stringArray[i]);
                    }
                    packetWrapper.write(Type.STRING_ARRAY, stringArray);
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_12_1.UPDATE_SCORE, this::lambda$registerPackets$8);
        this.componentRewriter.registerTitle(ClientboundPackets1_12_1.TITLE);
        new SoundRewriter<ClientboundPackets1_12_1>(this).registerSound(ClientboundPackets1_12_1.SOUND);
        this.registerClientbound(ClientboundPackets1_12_1.TAB_LIST, this::lambda$registerPackets$9);
        this.registerClientbound(ClientboundPackets1_12_1.ADVANCEMENTS, this::lambda$registerPackets$10);
        this.cancelServerbound(State.LOGIN, 2);
        this.cancelServerbound(ServerboundPackets1_13.QUERY_BLOCK_NBT);
        this.registerServerbound(ServerboundPackets1_13.TAB_COMPLETE, new PacketHandlers(this){
            final Protocol1_13To1_12_2 this$0;
            {
                this.this$0 = protocol1_13To1_12_2;
            }

            @Override
            public void register() {
                this.handler(10::lambda$register$0);
                this.map(Type.STRING, new ValueTransformer<String, String>(this, Type.STRING){
                    final 10 this$1;
                    {
                        this.this$1 = var1_1;
                        super(type);
                    }

                    @Override
                    public String transform(PacketWrapper packetWrapper, String string) {
                        packetWrapper.user().get(TabCompleteTracker.class).setInput(string);
                        return "/" + string;
                    }

                    @Override
                    public Object transform(PacketWrapper packetWrapper, Object object) throws Exception {
                        return this.transform(packetWrapper, (String)object);
                    }
                });
                this.handler(10::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BOOLEAN, false);
                Position position = Via.getManager().getProviders().get(PlayerLookTargetProvider.class).getPlayerLookTarget(packetWrapper.user());
                packetWrapper.write(Type.OPTIONAL_POSITION, position);
                if (!packetWrapper.isCancelled() && Via.getConfig().get1_13TabCompleteDelay() > 0) {
                    TabCompleteTracker tabCompleteTracker = packetWrapper.user().get(TabCompleteTracker.class);
                    packetWrapper.cancel();
                    tabCompleteTracker.setTimeToSend(System.currentTimeMillis() + (long)Via.getConfig().get1_13TabCompleteDelay() * 50L);
                    tabCompleteTracker.setLastTabComplete(packetWrapper.get(Type.STRING, 0));
                }
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (Via.getConfig().isDisable1_13AutoComplete()) {
                    packetWrapper.cancel();
                }
                int n = packetWrapper.read(Type.VAR_INT);
                packetWrapper.user().get(TabCompleteTracker.class).setTransactionId(n);
            }
        });
        this.registerServerbound(ServerboundPackets1_13.EDIT_BOOK, ServerboundPackets1_12_1.PLUGIN_MESSAGE, this::lambda$registerPackets$11);
        this.cancelServerbound(ServerboundPackets1_13.ENTITY_NBT_REQUEST);
        this.registerServerbound(ServerboundPackets1_13.PICK_ITEM, ServerboundPackets1_12_1.PLUGIN_MESSAGE, Protocol1_13To1_12_2::lambda$registerPackets$12);
        this.registerServerbound(ServerboundPackets1_13.CRAFT_RECIPE_REQUEST, new PacketHandlers(this){
            final Protocol1_13To1_12_2 this$0;
            {
                this.this$0 = protocol1_13To1_12_2;
            }

            @Override
            public void register() {
                this.map(Type.BYTE);
                this.handler(11::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Integer n;
                String string = packetWrapper.read(Type.STRING);
                if (string.length() < 19 || (n = Ints.tryParse(string.substring(18))) == null) {
                    packetWrapper.cancel();
                    return;
                }
                packetWrapper.write(Type.VAR_INT, n);
            }
        });
        this.registerServerbound(ServerboundPackets1_13.RECIPE_BOOK_DATA, new PacketHandlers(this){
            final Protocol1_13To1_12_2 this$0;
            {
                this.this$0 = protocol1_13To1_12_2;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(12::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                if (n == 0) {
                    Integer n2;
                    String string = packetWrapper.read(Type.STRING);
                    if (string.length() < 19 || (n2 = Ints.tryParse(string.substring(18))) == null) {
                        packetWrapper.cancel();
                        return;
                    }
                    packetWrapper.write(Type.INT, n2);
                }
                if (n == 1) {
                    packetWrapper.passthrough(Type.BOOLEAN);
                    packetWrapper.passthrough(Type.BOOLEAN);
                    packetWrapper.read(Type.BOOLEAN);
                    packetWrapper.read(Type.BOOLEAN);
                }
            }
        });
        this.registerServerbound(ServerboundPackets1_13.RENAME_ITEM, ServerboundPackets1_12_1.PLUGIN_MESSAGE, Protocol1_13To1_12_2::lambda$registerPackets$13);
        this.registerServerbound(ServerboundPackets1_13.SELECT_TRADE, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketHandlers(this){
            final Protocol1_13To1_12_2 this$0;
            {
                this.this$0 = protocol1_13To1_12_2;
            }

            @Override
            public void register() {
                this.handler(13::lambda$register$0);
                this.map((Type)Type.VAR_INT, Type.INT);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.STRING, "MC|TrSel");
            }
        });
        this.registerServerbound(ServerboundPackets1_13.SET_BEACON_EFFECT, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketHandlers(this){
            final Protocol1_13To1_12_2 this$0;
            {
                this.this$0 = protocol1_13To1_12_2;
            }

            @Override
            public void register() {
                this.handler(14::lambda$register$0);
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map((Type)Type.VAR_INT, Type.INT);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.STRING, "MC|Beacon");
            }
        });
        this.registerServerbound(ServerboundPackets1_13.UPDATE_COMMAND_BLOCK, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketHandlers(this){
            final Protocol1_13To1_12_2 this$0;
            {
                this.this$0 = protocol1_13To1_12_2;
            }

            @Override
            public void register() {
                this.handler(15::lambda$register$0);
                this.handler(POS_TO_3_INT);
                this.map(Type.STRING);
                this.handler(15::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.read(Type.VAR_INT);
                byte by = packetWrapper.read(Type.BYTE);
                String string = n == 0 ? "SEQUENCE" : (n == 1 ? "AUTO" : "REDSTONE");
                packetWrapper.write(Type.BOOLEAN, (by & 1) != 0);
                packetWrapper.write(Type.STRING, string);
                packetWrapper.write(Type.BOOLEAN, (by & 2) != 0);
                packetWrapper.write(Type.BOOLEAN, (by & 4) != 0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.STRING, "MC|AutoCmd");
            }
        });
        this.registerServerbound(ServerboundPackets1_13.UPDATE_COMMAND_BLOCK_MINECART, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketHandlers(this){
            final Protocol1_13To1_12_2 this$0;
            {
                this.this$0 = protocol1_13To1_12_2;
            }

            @Override
            public void register() {
                this.handler(16::lambda$register$0);
                this.map((Type)Type.VAR_INT, Type.INT);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.STRING, "MC|AdvCmd");
                packetWrapper.write(Type.BYTE, (byte)1);
            }
        });
        this.registerServerbound(ServerboundPackets1_13.UPDATE_STRUCTURE_BLOCK, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketHandlers(this){
            final Protocol1_13To1_12_2 this$0;
            {
                this.this$0 = protocol1_13To1_12_2;
            }

            @Override
            public void register() {
                this.handler(17::lambda$register$0);
                this.handler(POS_TO_3_INT);
                this.map(Type.VAR_INT, new ValueTransformer<Integer, Byte>(this, (Type)Type.BYTE){
                    final 17 this$1;
                    {
                        this.this$1 = var1_1;
                        super(type);
                    }

                    @Override
                    public Byte transform(PacketWrapper packetWrapper, Integer n) throws Exception {
                        return (byte)(n + 1);
                    }

                    @Override
                    public Object transform(PacketWrapper packetWrapper, Object object) throws Exception {
                        return this.transform(packetWrapper, (Integer)object);
                    }
                });
                this.map(Type.VAR_INT, new ValueTransformer<Integer, String>(this, Type.STRING){
                    final 17 this$1;
                    {
                        this.this$1 = var1_1;
                        super(type);
                    }

                    @Override
                    public String transform(PacketWrapper packetWrapper, Integer n) throws Exception {
                        return n == 0 ? "SAVE" : (n == 1 ? "LOAD" : (n == 2 ? "CORNER" : "DATA"));
                    }

                    @Override
                    public Object transform(PacketWrapper packetWrapper, Object object) throws Exception {
                        return this.transform(packetWrapper, (Integer)object);
                    }
                });
                this.map(Type.STRING);
                this.map((Type)Type.BYTE, Type.INT);
                this.map((Type)Type.BYTE, Type.INT);
                this.map((Type)Type.BYTE, Type.INT);
                this.map((Type)Type.BYTE, Type.INT);
                this.map((Type)Type.BYTE, Type.INT);
                this.map((Type)Type.BYTE, Type.INT);
                this.map(Type.VAR_INT, new ValueTransformer<Integer, String>(this, Type.STRING){
                    final 17 this$1;
                    {
                        this.this$1 = var1_1;
                        super(type);
                    }

                    @Override
                    public String transform(PacketWrapper packetWrapper, Integer n) throws Exception {
                        return n == 0 ? "NONE" : (n == 1 ? "LEFT_RIGHT" : "FRONT_BACK");
                    }

                    @Override
                    public Object transform(PacketWrapper packetWrapper, Object object) throws Exception {
                        return this.transform(packetWrapper, (Integer)object);
                    }
                });
                this.map(Type.VAR_INT, new ValueTransformer<Integer, String>(this, Type.STRING){
                    final 17 this$1;
                    {
                        this.this$1 = var1_1;
                        super(type);
                    }

                    @Override
                    public String transform(PacketWrapper packetWrapper, Integer n) throws Exception {
                        return n == 0 ? "NONE" : (n == 1 ? "CLOCKWISE_90" : (n == 2 ? "CLOCKWISE_180" : "COUNTERCLOCKWISE_90"));
                    }

                    @Override
                    public Object transform(PacketWrapper packetWrapper, Object object) throws Exception {
                        return this.transform(packetWrapper, (Integer)object);
                    }
                });
                this.map(Type.STRING);
                this.handler(17::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                float f = packetWrapper.read(Type.FLOAT).floatValue();
                long l = packetWrapper.read(Type.VAR_LONG);
                byte by = packetWrapper.read(Type.BYTE);
                packetWrapper.write(Type.BOOLEAN, (by & 1) != 0);
                packetWrapper.write(Type.BOOLEAN, (by & 2) != 0);
                packetWrapper.write(Type.BOOLEAN, (by & 4) != 0);
                packetWrapper.write(Type.FLOAT, Float.valueOf(f));
                packetWrapper.write(Type.VAR_LONG, l);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.STRING, "MC|Struct");
            }
        });
    }

    @Override
    protected void onMappingDataLoaded() {
        ConnectionData.init();
        RecipeData.init();
        BlockIdData.init();
        Types1_13.PARTICLE.filler(this).reader(3, ParticleType.Readers.BLOCK).reader(20, ParticleType.Readers.DUST).reader(11, ParticleType.Readers.DUST).reader(27, ParticleType.Readers.ITEM);
        if (Via.getConfig().isServersideBlockConnections() && Via.getManager().getProviders().get(BlockConnectionProvider.class) instanceof PacketBlockConnectionProvider) {
            BlockConnectionStorage.init();
        }
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_13Types.EntityType.PLAYER));
        userConnection.put(new TabCompleteTracker());
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
        userConnection.put(new BlockStorage());
        if (Via.getConfig().isServersideBlockConnections() && Via.getManager().getProviders().get(BlockConnectionProvider.class) instanceof PacketBlockConnectionProvider) {
            userConnection.put(new BlockConnectionStorage());
        }
    }

    @Override
    public void register(ViaProviders viaProviders) {
        viaProviders.register(BlockEntityProvider.class, new BlockEntityProvider());
        viaProviders.register(PaintingProvider.class, new PaintingProvider());
        viaProviders.register(PlayerLookTargetProvider.class, new PlayerLookTargetProvider());
    }

    public char getLastColorChar(String string) {
        int n = string.length();
        for (int i = n - 1; i > -1; --i) {
            char c;
            char c2 = string.charAt(i);
            if (c2 != '\u00a7' || i >= n - 1 || !ChatColorUtil.isColorCode(c = string.charAt(i + 1)) || FORMATTING_CODES.contains(Character.valueOf(c))) continue;
            return c;
        }
        return '\u0001';
    }

    protected String rewriteTeamMemberName(String string) {
        if (ChatColorUtil.stripColor(string).isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i < string.length(); i += 2) {
                char c = string.charAt(i);
                Character c2 = SCOREBOARD_TEAM_NAME_REWRITE.get(Character.valueOf(c));
                if (c2 == null) {
                    c2 = Character.valueOf(c);
                }
                stringBuilder.append('\u00a7').append(c2);
            }
            string = stringBuilder.toString();
        }
        return string;
    }

    @Override
    public MappingData getMappingData() {
        return MAPPINGS;
    }

    public MetadataRewriter1_13To1_12_2 getEntityRewriter() {
        return this.entityRewriter;
    }

    public InventoryPackets getItemRewriter() {
        return this.itemRewriter;
    }

    public ComponentRewriter1_13 getComponentRewriter() {
        return this.componentRewriter;
    }

    @Override
    public ItemRewriter getItemRewriter() {
        return this.getItemRewriter();
    }

    @Override
    public EntityRewriter getEntityRewriter() {
        return this.getEntityRewriter();
    }

    @Override
    public com.viaversion.viaversion.api.data.MappingData getMappingData() {
        return this.getMappingData();
    }

    private static void lambda$registerPackets$13(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(Type.STRING, "MC|ItemName");
    }

    private static void lambda$registerPackets$12(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(Type.STRING, "MC|PickItem");
    }

    private void lambda$registerPackets$11(PacketWrapper packetWrapper) throws Exception {
        Item item = packetWrapper.read(Type.FLAT_ITEM);
        boolean bl = packetWrapper.read(Type.BOOLEAN);
        this.itemRewriter.handleItemToServer(item);
        packetWrapper.write(Type.STRING, bl ? "MC|BSign" : "MC|BEdit");
        packetWrapper.write(Type.ITEM, item);
    }

    private void lambda$registerPackets$10(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.BOOLEAN);
        int n = packetWrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < n; ++i) {
            int n2;
            packetWrapper.passthrough(Type.STRING);
            if (packetWrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                packetWrapper.passthrough(Type.STRING);
            }
            if (packetWrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                this.componentRewriter.processText(packetWrapper.passthrough(Type.COMPONENT));
                this.componentRewriter.processText(packetWrapper.passthrough(Type.COMPONENT));
                Item item = packetWrapper.read(Type.ITEM);
                this.itemRewriter.handleItemToClient(item);
                packetWrapper.write(Type.FLAT_ITEM, item);
                packetWrapper.passthrough(Type.VAR_INT);
                n2 = packetWrapper.passthrough(Type.INT);
                if ((n2 & 1) != 0) {
                    packetWrapper.passthrough(Type.STRING);
                }
                packetWrapper.passthrough(Type.FLOAT);
                packetWrapper.passthrough(Type.FLOAT);
            }
            packetWrapper.passthrough(Type.STRING_ARRAY);
            int n3 = packetWrapper.passthrough(Type.VAR_INT);
            for (n2 = 0; n2 < n3; ++n2) {
                packetWrapper.passthrough(Type.STRING_ARRAY);
            }
        }
    }

    private void lambda$registerPackets$9(PacketWrapper packetWrapper) throws Exception {
        this.componentRewriter.processText(packetWrapper.passthrough(Type.COMPONENT));
        this.componentRewriter.processText(packetWrapper.passthrough(Type.COMPONENT));
    }

    private void lambda$registerPackets$8(PacketWrapper packetWrapper) throws Exception {
        String string = packetWrapper.read(Type.STRING);
        string = this.rewriteTeamMemberName(string);
        packetWrapper.write(Type.STRING, string);
        byte by = packetWrapper.read(Type.BYTE);
        packetWrapper.write(Type.BYTE, by);
        packetWrapper.passthrough(Type.STRING);
        if (by != 1) {
            packetWrapper.passthrough(Type.VAR_INT);
        }
    }

    private static void lambda$registerPackets$7(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.read(Type.VAR_INT);
        int n2 = packetWrapper.read(Type.VAR_INT);
        packetWrapper.cancel();
        if (n == 383) {
            int n3;
            for (int i = 0; i < 44 && (n3 = MAPPINGS.getItemMappings().getNewId(n << 16 | i)) != -1; ++i) {
                PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_13.COOLDOWN);
                packetWrapper2.write(Type.VAR_INT, n3);
                packetWrapper2.write(Type.VAR_INT, n2);
                packetWrapper2.send(Protocol1_13To1_12_2.class);
            }
        } else {
            int n4;
            for (int i = 0; i < 16 && (n4 = MAPPINGS.getItemMappings().getNewId(n << 4 | i)) != -1; ++i) {
                PacketWrapper packetWrapper3 = packetWrapper.create(ClientboundPackets1_13.COOLDOWN);
                packetWrapper3.write(Type.VAR_INT, n4);
                packetWrapper3.write(Type.VAR_INT, n2);
                packetWrapper3.send(Protocol1_13To1_12_2.class);
            }
        }
    }

    private static void lambda$registerPackets$6(PacketWrapper packetWrapper) throws Exception {
        int n;
        int n2;
        int n3;
        packetWrapper.write(Type.VAR_INT, packetWrapper.user().get(TabCompleteTracker.class).getTransactionId());
        String string = packetWrapper.user().get(TabCompleteTracker.class).getInput();
        if (string.endsWith(" ") || string.isEmpty()) {
            n3 = string.length();
            n2 = 0;
        } else {
            n3 = n = string.lastIndexOf(32) + 1;
            n2 = string.length() - n;
        }
        packetWrapper.write(Type.VAR_INT, n3);
        packetWrapper.write(Type.VAR_INT, n2);
        n = packetWrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < n; ++i) {
            String string2 = packetWrapper.read(Type.STRING);
            if (string2.startsWith("/") && n3 == 0) {
                string2 = string2.substring(1);
            }
            packetWrapper.write(Type.STRING, string2);
            packetWrapper.write(Type.BOOLEAN, false);
        }
    }

    private static void lambda$registerPackets$5(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.read(Type.VAR_INT);
        ArrayList<StatisticData> arrayList = new ArrayList<StatisticData>();
        for (int i = 0; i < n; ++i) {
            Object object;
            String object2 = packetWrapper.read(Type.STRING);
            String[] stringArray = object2.split("\\.");
            int n2 = 0;
            int n3 = -1;
            int n4 = packetWrapper.read(Type.VAR_INT);
            if (stringArray.length == 2) {
                n2 = 8;
                object = StatisticMappings.CUSTOM_STATS.get(object2);
                if (object != null) {
                    n3 = (Integer)object;
                } else {
                    Via.getPlatform().getLogger().warning("Could not find 1.13 -> 1.12.2 statistic mapping for " + object2);
                }
            } else if (stringArray.length > 2) {
                switch (object = stringArray[0]) {
                    case "mineBlock": {
                        n2 = 0;
                        break;
                    }
                    case "craftItem": {
                        n2 = 1;
                        break;
                    }
                    case "useItem": {
                        n2 = 2;
                        break;
                    }
                    case "breakItem": {
                        n2 = 3;
                        break;
                    }
                    case "pickup": {
                        n2 = 4;
                        break;
                    }
                    case "drop": {
                        n2 = 5;
                        break;
                    }
                    case "killEntity": {
                        n2 = 6;
                        break;
                    }
                    case "entityKilledBy": {
                        n2 = 7;
                    }
                }
            }
            if (n3 == -1) continue;
            arrayList.add(new StatisticData(n2, n3, n4));
        }
        packetWrapper.write(Type.VAR_INT, arrayList.size());
        for (StatisticData statisticData : arrayList) {
            packetWrapper.write(Type.VAR_INT, statisticData.getCategoryId());
            packetWrapper.write(Type.VAR_INT, statisticData.getNewId());
            packetWrapper.write(Type.VAR_INT, statisticData.getValue());
        }
    }

    private void lambda$registerPackets$4(PacketWrapper packetWrapper) throws Exception {
        this.componentRewriter.processText(packetWrapper.passthrough(Type.COMPONENT));
    }

    private static void lambda$static$3(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.create(ClientboundPackets1_13.DECLARE_COMMANDS, Protocol1_13To1_12_2::lambda$null$1).scheduleSend(Protocol1_13To1_12_2.class);
        packetWrapper.create(ClientboundPackets1_13.TAGS, Protocol1_13To1_12_2::lambda$null$2).scheduleSend(Protocol1_13To1_12_2.class);
    }

    private static void lambda$null$2(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(Type.VAR_INT, MAPPINGS.getBlockTags().size());
        for (Map.Entry<String, int[]> entry : MAPPINGS.getBlockTags().entrySet()) {
            packetWrapper.write(Type.STRING, entry.getKey());
            packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, entry.getValue());
        }
        packetWrapper.write(Type.VAR_INT, MAPPINGS.getItemTags().size());
        for (Map.Entry<String, int[]> entry : MAPPINGS.getItemTags().entrySet()) {
            packetWrapper.write(Type.STRING, entry.getKey());
            packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, entry.getValue());
        }
        packetWrapper.write(Type.VAR_INT, MAPPINGS.getFluidTags().size());
        for (Map.Entry<String, int[]> entry : MAPPINGS.getFluidTags().entrySet()) {
            packetWrapper.write(Type.STRING, entry.getKey());
            packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, entry.getValue());
        }
    }

    private static void lambda$null$1(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(Type.VAR_INT, 2);
        packetWrapper.write(Type.BYTE, (byte)0);
        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{1});
        packetWrapper.write(Type.BYTE, (byte)22);
        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[0]);
        packetWrapper.write(Type.STRING, "args");
        packetWrapper.write(Type.STRING, "brigadier:string");
        packetWrapper.write(Type.VAR_INT, 2);
        packetWrapper.write(Type.STRING, "minecraft:ask_server");
        packetWrapper.write(Type.VAR_INT, 0);
    }

    private static void lambda$static$0(PacketWrapper packetWrapper) throws Exception {
        Position position = packetWrapper.read(Type.POSITION);
        packetWrapper.write(Type.INT, position.x());
        packetWrapper.write(Type.INT, position.y());
        packetWrapper.write(Type.INT, position.z());
    }

    static ComponentRewriter1_13 access$000(Protocol1_13To1_12_2 protocol1_13To1_12_2) {
        return protocol1_13To1_12_2.componentRewriter;
    }

    static {
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('0'), Character.valueOf('g'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('1'), Character.valueOf('h'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('2'), Character.valueOf('i'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('3'), Character.valueOf('j'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('4'), Character.valueOf('p'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('5'), Character.valueOf('q'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('6'), Character.valueOf('s'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('7'), Character.valueOf('t'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('8'), Character.valueOf('u'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('9'), Character.valueOf('v'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('a'), Character.valueOf('w'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('b'), Character.valueOf('x'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('c'), Character.valueOf('y'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('d'), Character.valueOf('z'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('e'), Character.valueOf('!'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('f'), Character.valueOf('?'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('k'), Character.valueOf('#'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('l'), Character.valueOf('('));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('m'), Character.valueOf(')'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('n'), Character.valueOf(':'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('o'), Character.valueOf(';'));
        SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('r'), Character.valueOf('/'));
        POS_TO_3_INT = Protocol1_13To1_12_2::lambda$static$0;
        SEND_DECLARE_COMMANDS_AND_TAGS = Protocol1_13To1_12_2::lambda$static$3;
    }
}

