package com.viaversion.viaversion.protocols.protocol1_13to1_12_2;

import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
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
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.util.ChatColorUtil;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.GsonUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Protocol1_13To1_12_2 extends AbstractProtocol<ClientboundPackets1_12_1, ClientboundPackets1_13, ServerboundPackets1_12_1, ServerboundPackets1_13> {
   public static final MappingData MAPPINGS = new MappingData();
   private static final Map<Character, Character> SCOREBOARD_TEAM_NAME_REWRITE = new HashMap<>();
   private static final Set<Character> FORMATTING_CODES = Sets.newHashSet(new Character[]{'k', 'l', 'm', 'n', 'o', 'r'});
   private final MetadataRewriter1_13To1_12_2 entityRewriter = new MetadataRewriter1_13To1_12_2(this);
   private final InventoryPackets itemRewriter = new InventoryPackets(this);
   private final ComponentRewriter1_13<ClientboundPackets1_12_1> componentRewriter = new ComponentRewriter1_13<>(this);
   public static final PacketHandler POS_TO_3_INT = wrapper -> {
      Position position = wrapper.read(Type.POSITION1_8);
      wrapper.write(Type.INT, position.x());
      wrapper.write(Type.INT, position.y());
      wrapper.write(Type.INT, position.z());
   };
   public static final PacketHandler SEND_DECLARE_COMMANDS_AND_TAGS = w -> {
      w.create(ClientboundPackets1_13.DECLARE_COMMANDS, wrapper -> {
         wrapper.write(Type.VAR_INT, 2);
         wrapper.write(Type.BYTE, (byte)0);
         wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{1});
         wrapper.write(Type.BYTE, (byte)22);
         wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[0]);
         wrapper.write(Type.STRING, "args");
         wrapper.write(Type.STRING, "brigadier:string");
         wrapper.write(Type.VAR_INT, 2);
         wrapper.write(Type.STRING, "minecraft:ask_server");
         wrapper.write(Type.VAR_INT, 0);
      }).scheduleSend(Protocol1_13To1_12_2.class);
      w.create(ClientboundPackets1_13.TAGS, wrapper -> {
         wrapper.write(Type.VAR_INT, MAPPINGS.getBlockTags().size());

         for (Entry<String, int[]> tag : MAPPINGS.getBlockTags().entrySet()) {
            wrapper.write(Type.STRING, tag.getKey());
            wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, tag.getValue());
         }

         wrapper.write(Type.VAR_INT, MAPPINGS.getItemTags().size());

         for (Entry<String, int[]> tag : MAPPINGS.getItemTags().entrySet()) {
            wrapper.write(Type.STRING, tag.getKey());
            wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, tag.getValue());
         }

         wrapper.write(Type.VAR_INT, MAPPINGS.getFluidTags().size());

         for (Entry<String, int[]> tag : MAPPINGS.getFluidTags().entrySet()) {
            wrapper.write(Type.STRING, tag.getKey());
            wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, tag.getValue());
         }
      }).scheduleSend(Protocol1_13To1_12_2.class);
   };

   public Protocol1_13To1_12_2() {
      super(ClientboundPackets1_12_1.class, ClientboundPackets1_13.class, ServerboundPackets1_12_1.class, ServerboundPackets1_13.class);
   }

   @Override
   protected void registerPackets() {
      this.entityRewriter.register();
      this.itemRewriter.register();
      EntityPackets.register(this);
      WorldPackets.register(this);
      this.registerClientbound(State.LOGIN, 0, 0, wrapper -> this.componentRewriter.processText(wrapper.passthrough(Type.COMPONENT)));
      this.registerClientbound(State.STATUS, 0, 0, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.handler(wrapper -> {
               String response = wrapper.get(Type.STRING, 0);

               try {
                  JsonObject json = GsonUtil.getGson().fromJson(response, JsonObject.class);
                  if (json.has("favicon")) {
                     json.addProperty("favicon", json.get("favicon").getAsString().replace("\n", ""));
                  }

                  wrapper.set(Type.STRING, 0, GsonUtil.getGson().toJson((JsonElement)json));
               } catch (JsonParseException var3) {
                  var3.printStackTrace();
               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_12_1.STATISTICS, wrapper -> {
         int size = wrapper.read(Type.VAR_INT);
         List<StatisticData> remappedStats = new ArrayList<>();

         for (int i = 0; i < size; i++) {
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
            } else if (split.length > 2) {
               String category = split[1];
               switch (category) {
                  case "mineBlock":
                     categoryId = 0;
                     break;
                  case "craftItem":
                     categoryId = 1;
                     break;
                  case "useItem":
                     categoryId = 2;
                     break;
                  case "breakItem":
                     categoryId = 3;
                     break;
                  case "pickup":
                     categoryId = 4;
                     break;
                  case "drop":
                     categoryId = 5;
                     break;
                  case "killEntity":
                     categoryId = 6;
                     break;
                  case "entityKilledBy":
                     categoryId = 7;
               }
            }

            if (newId != -1) {
               remappedStats.add(new StatisticData(categoryId, newId, value));
            }
         }

         wrapper.write(Type.VAR_INT, remappedStats.size());

         for (StatisticData stat : remappedStats) {
            wrapper.write(Type.VAR_INT, stat.getCategoryId());
            wrapper.write(Type.VAR_INT, stat.getNewId());
            wrapper.write(Type.VAR_INT, stat.getValue());
         }
      });
      this.componentRewriter.registerBossBar(ClientboundPackets1_12_1.BOSSBAR);
      this.componentRewriter.registerComponentPacket(ClientboundPackets1_12_1.CHAT_MESSAGE);
      this.registerClientbound(ClientboundPackets1_12_1.TAB_COMPLETE, wrapper -> {
         wrapper.write(Type.VAR_INT, wrapper.user().get(TabCompleteTracker.class).getTransactionId());
         String input = wrapper.user().get(TabCompleteTracker.class).getInput();
         int index;
         int length;
         if (!input.endsWith(" ") && !input.isEmpty()) {
            int lastSpace = input.lastIndexOf(32) + 1;
            index = lastSpace;
            length = input.length() - lastSpace;
         } else {
            index = input.length();
            length = 0;
         }

         wrapper.write(Type.VAR_INT, index);
         wrapper.write(Type.VAR_INT, length);
         int count = wrapper.passthrough(Type.VAR_INT);

         for (int i = 0; i < count; i++) {
            String suggestion = wrapper.read(Type.STRING);
            if (suggestion.startsWith("/") && index == 0) {
               suggestion = suggestion.substring(1);
            }

            wrapper.write(Type.STRING, suggestion);
            wrapper.write(Type.OPTIONAL_COMPONENT, null);
         }
      });
      this.registerClientbound(ClientboundPackets1_12_1.OPEN_WINDOW, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.STRING);
            this.handler(wrapper -> Protocol1_13To1_12_2.this.componentRewriter.processText(wrapper.passthrough(Type.COMPONENT)));
         }
      });
      this.registerClientbound(ClientboundPackets1_12_1.COOLDOWN, wrapper -> {
         int item = wrapper.read(Type.VAR_INT);
         int ticks = wrapper.read(Type.VAR_INT);
         wrapper.cancel();
         if (item == 383) {
            for (int i = 0; i < 44; i++) {
               int newItem = MAPPINGS.getItemMappings().getNewId(item << 16 | i);
               if (newItem == -1) {
                  break;
               }

               PacketWrapper packet = wrapper.create(ClientboundPackets1_13.COOLDOWN);
               packet.write(Type.VAR_INT, newItem);
               packet.write(Type.VAR_INT, ticks);
               packet.send(Protocol1_13To1_12_2.class);
            }
         } else {
            for (int i = 0; i < 16; i++) {
               int newItem = MAPPINGS.getItemMappings().getNewId(item << 4 | i);
               if (newItem == -1) {
                  break;
               }

               PacketWrapper packet = wrapper.create(ClientboundPackets1_13.COOLDOWN);
               packet.write(Type.VAR_INT, newItem);
               packet.write(Type.VAR_INT, ticks);
               packet.send(Protocol1_13To1_12_2.class);
            }
         }
      });
      this.componentRewriter.registerComponentPacket(ClientboundPackets1_12_1.DISCONNECT);
      this.registerClientbound(ClientboundPackets1_12_1.EFFECT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.POSITION1_8);
            this.map(Type.INT);
            this.handler(wrapper -> {
               int id = wrapper.get(Type.INT, 0);
               int data = wrapper.get(Type.INT, 1);
               if (id == 1010) {
                  wrapper.set(Type.INT, 1, Protocol1_13To1_12_2.this.getMappingData().getItemMappings().getNewId(data << 4));
               } else if (id == 2001) {
                  int blockId = data & 4095;
                  int blockData = data >> 12;
                  wrapper.set(Type.INT, 1, WorldPackets.toNewId(blockId << 4 | blockData));
               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_12_1.CRAFT_RECIPE_RESPONSE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.BYTE);
            this.handler(wrapper -> wrapper.write(Type.STRING, "viaversion:legacy/" + wrapper.read(Type.VAR_INT)));
         }
      });
      this.componentRewriter.registerCombatEvent(ClientboundPackets1_12_1.COMBAT_EVENT);
      this.registerClientbound(ClientboundPackets1_12_1.MAP_DATA, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.map(Type.BOOLEAN);
            this.handler(wrapper -> {
               int iconCount = wrapper.passthrough(Type.VAR_INT);

               for (int i = 0; i < iconCount; i++) {
                  byte directionAndType = wrapper.read(Type.BYTE);
                  int type = (directionAndType & 240) >> 4;
                  wrapper.write(Type.VAR_INT, type);
                  wrapper.passthrough(Type.BYTE);
                  wrapper.passthrough(Type.BYTE);
                  byte direction = (byte)(directionAndType & 15);
                  wrapper.write(Type.BYTE, direction);
                  wrapper.write(Type.OPTIONAL_COMPONENT, null);
               }
            });
         }
      });
      this.registerClientbound(
         ClientboundPackets1_12_1.UNLOCK_RECIPES,
         new PacketHandlers() {
            @Override
            public void register() {
               this.map(Type.VAR_INT);
               this.map(Type.BOOLEAN);
               this.map(Type.BOOLEAN);
               this.handler(wrapper -> {
                  wrapper.write(Type.BOOLEAN, false);
                  wrapper.write(Type.BOOLEAN, false);
               });
               this.handler(
                  wrapper -> {
                     int action = wrapper.get(Type.VAR_INT, 0);

                     for (int i = 0; i < (action == 0 ? 2 : 1); i++) {
                        int[] ids = wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                        String[] stringIds = new String[ids.length];

                        for (int j = 0; j < ids.length; j++) {
                           stringIds[j] = "viaversion:legacy/" + ids[j];
                        }

                        wrapper.write(Type.STRING_ARRAY, stringIds);
                     }

                     if (action == 0) {
                        wrapper.create(ClientboundPackets1_13.DECLARE_RECIPES, w -> Protocol1_13To1_12_2.this.writeDeclareRecipes(w))
                           .send(Protocol1_13To1_12_2.class);
                     }
                  }
               );
            }
         }
      );
      this.registerClientbound(ClientboundPackets1_12_1.RESPAWN, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.handler(wrapper -> {
               ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
               int dimensionId = wrapper.get(Type.INT, 0);
               clientWorld.setEnvironment(dimensionId);
               if (Via.getConfig().isServersideBlockConnections()) {
                  ConnectionData.clearBlockStorage(wrapper.user());
               }
            });
            this.handler(Protocol1_13To1_12_2.SEND_DECLARE_COMMANDS_AND_TAGS);
         }
      });
      this.registerClientbound(ClientboundPackets1_12_1.SCOREBOARD_OBJECTIVE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.BYTE);
            this.handler(wrapper -> {
               byte mode = wrapper.get(Type.BYTE, 0);
               if (mode == 0 || mode == 2) {
                  String value = wrapper.read(Type.STRING);
                  wrapper.write(Type.COMPONENT, ComponentUtil.legacyToJson(value));
                  String type = wrapper.read(Type.STRING);
                  wrapper.write(Type.VAR_INT, type.equals("integer") ? 0 : 1);
               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_12_1.TEAMS, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.BYTE);
            this.handler(wrapper -> {
               byte action = wrapper.get(Type.BYTE, 0);
               if (action == 0 || action == 2) {
                  String displayName = wrapper.read(Type.STRING);
                  wrapper.write(Type.COMPONENT, ComponentUtil.legacyToJson(displayName));
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
                     char lastColorChar = Protocol1_13To1_12_2.this.getLastColorChar(prefix);
                     colour = ChatColorUtil.getColorOrdinal(lastColorChar);
                     suffix = '§' + Character.toString(lastColorChar) + suffix;
                  }

                  wrapper.write(Type.VAR_INT, colour);
                  wrapper.write(Type.COMPONENT, ComponentUtil.legacyToJson(prefix));
                  wrapper.write(Type.COMPONENT, ComponentUtil.legacyToJson(suffix));
               }

               if (action == 0 || action == 3 || action == 4) {
                  String[] names = wrapper.read(Type.STRING_ARRAY);

                  for (int i = 0; i < names.length; i++) {
                     names[i] = Protocol1_13To1_12_2.this.rewriteTeamMemberName(names[i]);
                  }

                  wrapper.write(Type.STRING_ARRAY, names);
               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_12_1.UPDATE_SCORE, wrapper -> {
         String displayName = wrapper.read(Type.STRING);
         displayName = this.rewriteTeamMemberName(displayName);
         wrapper.write(Type.STRING, displayName);
      });
      this.componentRewriter.registerTitle(ClientboundPackets1_12_1.TITLE);
      new SoundRewriter<>(this).registerSound(ClientboundPackets1_12_1.SOUND);
      this.registerClientbound(ClientboundPackets1_12_1.TAB_LIST, wrapper -> {
         this.componentRewriter.processText(wrapper.passthrough(Type.COMPONENT));
         this.componentRewriter.processText(wrapper.passthrough(Type.COMPONENT));
      });
      this.registerClientbound(ClientboundPackets1_12_1.ADVANCEMENTS, wrapper -> {
         wrapper.passthrough(Type.BOOLEAN);
         int size = wrapper.passthrough(Type.VAR_INT);

         for (int i = 0; i < size; i++) {
            wrapper.passthrough(Type.STRING);
            if (wrapper.passthrough(Type.BOOLEAN)) {
               wrapper.passthrough(Type.STRING);
            }

            if (wrapper.passthrough(Type.BOOLEAN)) {
               this.componentRewriter.processText(wrapper.passthrough(Type.COMPONENT));
               this.componentRewriter.processText(wrapper.passthrough(Type.COMPONENT));
               Item icon = wrapper.read(Type.ITEM1_8);
               this.itemRewriter.handleItemToClient(icon);
               wrapper.write(Type.ITEM1_13, icon);
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

            for (int array = 0; array < arrayLength; array++) {
               wrapper.passthrough(Type.STRING_ARRAY);
            }
         }
      });
      this.cancelServerbound(State.LOGIN, 2);
      this.cancelServerbound(ServerboundPackets1_13.QUERY_BLOCK_NBT);
      this.registerServerbound(ServerboundPackets1_13.TAB_COMPLETE, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(wrapper -> {
               if (Via.getConfig().isDisable1_13AutoComplete()) {
                  wrapper.cancel();
               }

               int tid = wrapper.read(Type.VAR_INT);
               wrapper.user().get(TabCompleteTracker.class).setTransactionId(tid);
            });
            this.map(Type.STRING, new ValueTransformer<String, String>(Type.STRING) {
               public String transform(PacketWrapper wrapper, String inputValue) {
                  wrapper.user().get(TabCompleteTracker.class).setInput(inputValue);
                  return "/" + inputValue;
               }
            });
            this.handler(wrapper -> {
               wrapper.write(Type.BOOLEAN, false);
               Position playerLookTarget = Via.getManager().getProviders().get(PlayerLookTargetProvider.class).getPlayerLookTarget(wrapper.user());
               wrapper.write(Type.OPTIONAL_POSITION1_8, playerLookTarget);
               if (!wrapper.isCancelled() && Via.getConfig().get1_13TabCompleteDelay() > 0) {
                  TabCompleteTracker tracker = wrapper.user().get(TabCompleteTracker.class);
                  wrapper.cancel();
                  tracker.setTimeToSend(System.currentTimeMillis() + (long)Via.getConfig().get1_13TabCompleteDelay() * 50L);
                  tracker.setLastTabComplete(wrapper.get(Type.STRING, 0));
               }
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_13.EDIT_BOOK, ServerboundPackets1_12_1.PLUGIN_MESSAGE, wrapper -> {
         Item item = wrapper.read(Type.ITEM1_13);
         boolean isSigning = wrapper.read(Type.BOOLEAN);
         this.itemRewriter.handleItemToServer(item);
         wrapper.write(Type.STRING, isSigning ? "MC|BSign" : "MC|BEdit");
         wrapper.write(Type.ITEM1_8, item);
      });
      this.cancelServerbound(ServerboundPackets1_13.ENTITY_NBT_REQUEST);
      this.registerServerbound(ServerboundPackets1_13.PICK_ITEM, ServerboundPackets1_12_1.PLUGIN_MESSAGE, wrapper -> wrapper.write(Type.STRING, "MC|PickItem"));
      this.registerServerbound(ServerboundPackets1_13.CRAFT_RECIPE_REQUEST, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.BYTE);
            this.handler(wrapper -> {
               String s = wrapper.read(Type.STRING);
               Integer id;
               if (s.length() >= 19 && (id = Ints.tryParse(s.substring(18))) != null) {
                  wrapper.write(Type.VAR_INT, id);
               } else {
                  wrapper.cancel();
               }
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_13.RECIPE_BOOK_DATA, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int type = wrapper.get(Type.VAR_INT, 0);
               if (type == 0) {
                  String s = wrapper.read(Type.STRING);
                  Integer id;
                  if (s.length() < 19 || (id = Ints.tryParse(s.substring(18))) == null) {
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
            });
         }
      });
      this.registerServerbound(
         ServerboundPackets1_13.RENAME_ITEM, ServerboundPackets1_12_1.PLUGIN_MESSAGE, wrapper -> wrapper.write(Type.STRING, "MC|ItemName")
      );
      this.registerServerbound(ServerboundPackets1_13.SELECT_TRADE, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(wrapper -> wrapper.write(Type.STRING, "MC|TrSel"));
            this.map(Type.VAR_INT, Type.INT);
         }
      });
      this.registerServerbound(ServerboundPackets1_13.SET_BEACON_EFFECT, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(wrapper -> wrapper.write(Type.STRING, "MC|Beacon"));
            this.map(Type.VAR_INT, Type.INT);
            this.map(Type.VAR_INT, Type.INT);
         }
      });
      this.registerServerbound(ServerboundPackets1_13.UPDATE_COMMAND_BLOCK, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(wrapper -> wrapper.write(Type.STRING, "MC|AutoCmd"));
            this.handler(Protocol1_13To1_12_2.POS_TO_3_INT);
            this.map(Type.STRING);
            this.handler(wrapper -> {
               int mode = wrapper.read(Type.VAR_INT);
               byte flags = wrapper.read(Type.BYTE);
               String stringMode = mode == 0 ? "SEQUENCE" : (mode == 1 ? "AUTO" : "REDSTONE");
               wrapper.write(Type.BOOLEAN, (flags & 1) != 0);
               wrapper.write(Type.STRING, stringMode);
               wrapper.write(Type.BOOLEAN, (flags & 2) != 0);
               wrapper.write(Type.BOOLEAN, (flags & 4) != 0);
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_13.UPDATE_COMMAND_BLOCK_MINECART, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(wrapper -> {
               wrapper.write(Type.STRING, "MC|AdvCmd");
               wrapper.write(Type.BYTE, (byte)1);
            });
            this.map(Type.VAR_INT, Type.INT);
         }
      });
      this.registerServerbound(ServerboundPackets1_13.UPDATE_STRUCTURE_BLOCK, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(wrapper -> wrapper.write(Type.STRING, "MC|Struct"));
            this.handler(Protocol1_13To1_12_2.POS_TO_3_INT);
            this.map(Type.VAR_INT, new ValueTransformer<Integer, Byte>(Type.BYTE) {
               public Byte transform(PacketWrapper wrapper, Integer action) throws Exception {
                  return (byte)(action + 1);
               }
            });
            this.map(Type.VAR_INT, new ValueTransformer<Integer, String>(Type.STRING) {
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
            this.map(Type.VAR_INT, new ValueTransformer<Integer, String>(Type.STRING) {
               public String transform(PacketWrapper wrapper, Integer mirror) throws Exception {
                  return mirror == 0 ? "NONE" : (mirror == 1 ? "LEFT_RIGHT" : "FRONT_BACK");
               }
            });
            this.map(Type.VAR_INT, new ValueTransformer<Integer, String>(Type.STRING) {
               public String transform(PacketWrapper wrapper, Integer rotation) throws Exception {
                  return rotation == 0 ? "NONE" : (rotation == 1 ? "CLOCKWISE_90" : (rotation == 2 ? "CLOCKWISE_180" : "COUNTERCLOCKWISE_90"));
               }
            });
            this.map(Type.STRING);
            this.handler(wrapper -> {
               float integrity = wrapper.read(Type.FLOAT);
               long seed = wrapper.read(Type.VAR_LONG);
               byte flags = wrapper.read(Type.BYTE);
               wrapper.write(Type.BOOLEAN, (flags & 1) != 0);
               wrapper.write(Type.BOOLEAN, (flags & 2) != 0);
               wrapper.write(Type.BOOLEAN, (flags & 4) != 0);
               wrapper.write(Type.FLOAT, integrity);
               wrapper.write(Type.VAR_LONG, seed);
            });
         }
      });
   }

   private void writeDeclareRecipes(PacketWrapper recipesPacket) {
      recipesPacket.write(Type.VAR_INT, RecipeData.recipes.size());

      for (Entry<String, RecipeData.Recipe> entry : RecipeData.recipes.entrySet()) {
         recipesPacket.write(Type.STRING, entry.getKey());
         recipesPacket.write(Type.STRING, entry.getValue().getType());
         String var4 = entry.getValue().getType();
         switch (var4) {
            case "crafting_shapeless":
               recipesPacket.write(Type.STRING, entry.getValue().getGroup());
               recipesPacket.write(Type.VAR_INT, entry.getValue().getIngredients().length);

               for (Item[] ingredient : entry.getValue().getIngredients()) {
                  Item[] clone = (Item[])ingredient.clone();

                  for (int i = 0; i < clone.length; i++) {
                     if (clone[i] != null) {
                        clone[i] = new DataItem(clone[i]);
                     }
                  }

                  recipesPacket.write(Type.ITEM1_13_ARRAY, clone);
               }

               recipesPacket.write(Type.ITEM1_13, new DataItem(entry.getValue().getResult()));
               break;
            case "crafting_shaped":
               recipesPacket.write(Type.VAR_INT, entry.getValue().getWidth());
               recipesPacket.write(Type.VAR_INT, entry.getValue().getHeight());
               recipesPacket.write(Type.STRING, entry.getValue().getGroup());

               for (Item[] ingredient : entry.getValue().getIngredients()) {
                  Item[] clone = (Item[])ingredient.clone();

                  for (int ix = 0; ix < clone.length; ix++) {
                     if (clone[ix] != null) {
                        clone[ix] = new DataItem(clone[ix]);
                     }
                  }

                  recipesPacket.write(Type.ITEM1_13_ARRAY, clone);
               }

               recipesPacket.write(Type.ITEM1_13, new DataItem(entry.getValue().getResult()));
               break;
            case "smelting":
               recipesPacket.write(Type.STRING, entry.getValue().getGroup());
               Item[] clone = (Item[])entry.getValue().getIngredient().clone();

               for (int ixx = 0; ixx < clone.length; ixx++) {
                  if (clone[ixx] != null) {
                     clone[ixx] = new DataItem(clone[ixx]);
                  }
               }

               recipesPacket.write(Type.ITEM1_13_ARRAY, clone);
               recipesPacket.write(Type.ITEM1_13, new DataItem(entry.getValue().getResult()));
               recipesPacket.write(Type.FLOAT, entry.getValue().getExperience());
               recipesPacket.write(Type.VAR_INT, entry.getValue().getCookingTime());
         }
      }
   }

   @Override
   protected void onMappingDataLoaded() {
      ConnectionData.init();
      RecipeData.init();
      BlockIdData.init();
      Types1_13.PARTICLE
         .filler(this)
         .reader(3, ParticleType.Readers.BLOCK)
         .reader(20, ParticleType.Readers.DUST)
         .reader(11, ParticleType.Readers.DUST)
         .reader(27, ParticleType.Readers.ITEM1_13);
      if (Via.getConfig().isServersideBlockConnections()
         && Via.getManager().getProviders().get(BlockConnectionProvider.class) instanceof PacketBlockConnectionProvider) {
         BlockConnectionStorage.init();
      }
   }

   @Override
   public void init(UserConnection userConnection) {
      userConnection.addEntityTracker((Class<? extends Protocol>)this.getClass(), new EntityTrackerBase(userConnection, EntityTypes1_13.EntityType.PLAYER));
      userConnection.put(new TabCompleteTracker());
      if (!userConnection.has(ClientWorld.class)) {
         userConnection.put(new ClientWorld());
      }

      userConnection.put(new BlockStorage());
      if (Via.getConfig().isServersideBlockConnections()
         && Via.getManager().getProviders().get(BlockConnectionProvider.class) instanceof PacketBlockConnectionProvider) {
         userConnection.put(new BlockConnectionStorage());
      }
   }

   @Override
   public void register(ViaProviders providers) {
      providers.register(BlockEntityProvider.class, new BlockEntityProvider());
      providers.register(PaintingProvider.class, new PaintingProvider());
      providers.register(PlayerLookTargetProvider.class, new PlayerLookTargetProvider());
   }

   public char getLastColorChar(String input) {
      int length = input.length();

      for (int index = length - 1; index > -1; index--) {
         char section = input.charAt(index);
         if (section == 167 && index < length - 1) {
            char c = input.charAt(index + 1);
            if (ChatColorUtil.isColorCode(c) && !FORMATTING_CODES.contains(c)) {
               return c;
            }
         }
      }

      return 'r';
   }

   protected String rewriteTeamMemberName(String name) {
      if (ChatColorUtil.stripColor(name).isEmpty()) {
         StringBuilder newName = new StringBuilder();

         for (int i = 1; i < name.length(); i += 2) {
            char colorChar = name.charAt(i);
            Character rewrite = SCOREBOARD_TEAM_NAME_REWRITE.get(colorChar);
            if (rewrite == null) {
               rewrite = colorChar;
            }

            newName.append('§').append(rewrite);
         }

         name = newName.toString();
      }

      return name;
   }

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

   static {
      SCOREBOARD_TEAM_NAME_REWRITE.put('0', 'g');
      SCOREBOARD_TEAM_NAME_REWRITE.put('1', 'h');
      SCOREBOARD_TEAM_NAME_REWRITE.put('2', 'i');
      SCOREBOARD_TEAM_NAME_REWRITE.put('3', 'j');
      SCOREBOARD_TEAM_NAME_REWRITE.put('4', 'p');
      SCOREBOARD_TEAM_NAME_REWRITE.put('5', 'q');
      SCOREBOARD_TEAM_NAME_REWRITE.put('6', 's');
      SCOREBOARD_TEAM_NAME_REWRITE.put('7', 't');
      SCOREBOARD_TEAM_NAME_REWRITE.put('8', 'u');
      SCOREBOARD_TEAM_NAME_REWRITE.put('9', 'v');
      SCOREBOARD_TEAM_NAME_REWRITE.put('a', 'w');
      SCOREBOARD_TEAM_NAME_REWRITE.put('b', 'x');
      SCOREBOARD_TEAM_NAME_REWRITE.put('c', 'y');
      SCOREBOARD_TEAM_NAME_REWRITE.put('d', 'z');
      SCOREBOARD_TEAM_NAME_REWRITE.put('e', '!');
      SCOREBOARD_TEAM_NAME_REWRITE.put('f', '?');
      SCOREBOARD_TEAM_NAME_REWRITE.put('k', '#');
      SCOREBOARD_TEAM_NAME_REWRITE.put('l', '(');
      SCOREBOARD_TEAM_NAME_REWRITE.put('m', ')');
      SCOREBOARD_TEAM_NAME_REWRITE.put('n', ':');
      SCOREBOARD_TEAM_NAME_REWRITE.put('o', ';');
      SCOREBOARD_TEAM_NAME_REWRITE.put('r', '/');
   }
}
