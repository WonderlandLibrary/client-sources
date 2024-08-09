/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.api.data.MappedLegacyBlockItem;
import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter;
import com.viaversion.viabackwards.api.rewriters.LegacyEnchantmentRewriter;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.Protocol1_10To1_11;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.ChestedHorseStorage;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.WindowTracker;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_11Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.EntityIdRewriter;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
import java.util.Arrays;
import java.util.Optional;

public class BlockItemPackets1_11
extends LegacyBlockItemRewriter<ClientboundPackets1_9_3, ServerboundPackets1_9_3, Protocol1_10To1_11> {
    private LegacyEnchantmentRewriter enchantmentRewriter;

    public BlockItemPackets1_11(Protocol1_10To1_11 protocol1_10To1_11) {
        super(protocol1_10To1_11);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.SET_SLOT, new PacketHandlers(this){
            final BlockItemPackets1_11 this$0;
            {
                this.this$0 = blockItemPackets1_11;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.ITEM);
                this.handler(this.this$0.itemToClientHandler(Type.ITEM));
                this.handler(new PacketHandler(this){
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                    }

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        if (BlockItemPackets1_11.access$000(this.this$1.this$0, packetWrapper.user())) {
                            Optional optional = BlockItemPackets1_11.access$100(this.this$1.this$0, packetWrapper.user());
                            if (!optional.isPresent()) {
                                return;
                            }
                            ChestedHorseStorage chestedHorseStorage = (ChestedHorseStorage)optional.get();
                            int n = packetWrapper.get(Type.SHORT, 0).shortValue();
                            n = BlockItemPackets1_11.access$200(this.this$1.this$0, chestedHorseStorage, n);
                            packetWrapper.set(Type.SHORT, 0, Integer.valueOf(n).shortValue());
                            packetWrapper.set(Type.ITEM, 0, BlockItemPackets1_11.access$300(this.this$1.this$0, chestedHorseStorage, n, packetWrapper.get(Type.ITEM, 0)));
                        }
                    }
                });
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.WINDOW_ITEMS, new PacketHandlers(this){
            final BlockItemPackets1_11 this$0;
            {
                this.this$0 = blockItemPackets1_11;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.ITEM_ARRAY);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Item[] itemArray = packetWrapper.get(Type.ITEM_ARRAY, 0);
                for (int i = 0; i < itemArray.length; ++i) {
                    itemArray[i] = this.this$0.handleItemToClient(itemArray[i]);
                }
                if (BlockItemPackets1_11.access$000(this.this$0, packetWrapper.user())) {
                    Optional optional = BlockItemPackets1_11.access$100(this.this$0, packetWrapper.user());
                    if (!optional.isPresent()) {
                        return;
                    }
                    ChestedHorseStorage chestedHorseStorage = (ChestedHorseStorage)optional.get();
                    itemArray = Arrays.copyOf(itemArray, !chestedHorseStorage.isChested() ? 38 : 53);
                    for (int i = itemArray.length - 1; i >= 0; --i) {
                        itemArray[BlockItemPackets1_11.access$200((BlockItemPackets1_11)this.this$0, (ChestedHorseStorage)chestedHorseStorage, (int)i)] = itemArray[i];
                        itemArray[i] = BlockItemPackets1_11.access$300(this.this$0, chestedHorseStorage, i, itemArray[i]);
                    }
                    packetWrapper.set(Type.ITEM_ARRAY, 0, itemArray);
                }
            }
        });
        this.registerEntityEquipment(ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.PLUGIN_MESSAGE, new PacketHandlers(this){
            final BlockItemPackets1_11 this$0;
            {
                this.this$0 = blockItemPackets1_11;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.get(Type.STRING, 0).equalsIgnoreCase("MC|TrList")) {
                    packetWrapper.passthrough(Type.INT);
                    int n = packetWrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                    for (int i = 0; i < n; ++i) {
                        packetWrapper.write(Type.ITEM, this.this$0.handleItemToClient(packetWrapper.read(Type.ITEM)));
                        packetWrapper.write(Type.ITEM, this.this$0.handleItemToClient(packetWrapper.read(Type.ITEM)));
                        boolean bl = packetWrapper.passthrough(Type.BOOLEAN);
                        if (bl) {
                            packetWrapper.write(Type.ITEM, this.this$0.handleItemToClient(packetWrapper.read(Type.ITEM)));
                        }
                        packetWrapper.passthrough(Type.BOOLEAN);
                        packetWrapper.passthrough(Type.INT);
                        packetWrapper.passthrough(Type.INT);
                    }
                }
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerServerbound(ServerboundPackets1_9_3.CLICK_WINDOW, new PacketHandlers(this){
            final BlockItemPackets1_11 this$0;
            {
                this.this$0 = blockItemPackets1_11;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT);
                this.map(Type.ITEM);
                this.handler(this.this$0.itemToServerHandler(Type.ITEM));
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (BlockItemPackets1_11.access$000(this.this$0, packetWrapper.user())) {
                    Optional optional = BlockItemPackets1_11.access$100(this.this$0, packetWrapper.user());
                    if (!optional.isPresent()) {
                        return;
                    }
                    ChestedHorseStorage chestedHorseStorage = (ChestedHorseStorage)optional.get();
                    short s = packetWrapper.get(Type.SHORT, 0);
                    int n = BlockItemPackets1_11.access$400(this.this$0, chestedHorseStorage, s);
                    packetWrapper.set(Type.SHORT, 0, Integer.valueOf(n).shortValue());
                }
            }
        });
        this.registerCreativeInvAction(ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.CHUNK_DATA, this::lambda$registerPackets$0);
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.BLOCK_CHANGE, new PacketHandlers(this){
            final BlockItemPackets1_11 this$0;
            {
                this.this$0 = blockItemPackets1_11;
            }

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                packetWrapper.set(Type.VAR_INT, 0, this.this$0.handleBlockID(n));
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.MULTI_BLOCK_CHANGE, new PacketHandlers(this){
            final BlockItemPackets1_11 this$0;
            {
                this.this$0 = blockItemPackets1_11;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                for (BlockChangeRecord blockChangeRecord : packetWrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                    blockChangeRecord.setBlockId(this.this$0.handleBlockID(blockChangeRecord.getBlockId()));
                }
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, new PacketHandlers(this){
            final BlockItemPackets1_11 this$0;
            {
                this.this$0 = blockItemPackets1_11;
            }

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(7::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.get(Type.UNSIGNED_BYTE, 0) == 10) {
                    packetWrapper.cancel();
                }
                if (packetWrapper.get(Type.UNSIGNED_BYTE, 0) == 1) {
                    CompoundTag compoundTag = packetWrapper.get(Type.NBT, 0);
                    EntityIdRewriter.toClientSpawner(compoundTag, true);
                }
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.OPEN_WINDOW, new PacketHandlers(this){
            final BlockItemPackets1_11 this$0;
            {
                this.this$0 = blockItemPackets1_11;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.COMPONENT);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = -1;
                if (packetWrapper.get(Type.STRING, 0).equals("EntityHorse")) {
                    n = packetWrapper.passthrough(Type.INT);
                }
                String string = packetWrapper.get(Type.STRING, 0);
                WindowTracker windowTracker = packetWrapper.user().get(WindowTracker.class);
                windowTracker.setInventory(string);
                windowTracker.setEntityId(n);
                if (BlockItemPackets1_11.access$000(this.this$0, packetWrapper.user())) {
                    packetWrapper.set(Type.UNSIGNED_BYTE, 1, (short)17);
                }
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.CLOSE_WINDOW, new PacketHandlers(this){
            final BlockItemPackets1_11 this$0;
            {
                this.this$0 = blockItemPackets1_11;
            }

            @Override
            public void register() {
                this.handler(9::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                WindowTracker windowTracker = packetWrapper.user().get(WindowTracker.class);
                windowTracker.setInventory(null);
                windowTracker.setEntityId(-1);
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerServerbound(ServerboundPackets1_9_3.CLOSE_WINDOW, new PacketHandlers(this){
            final BlockItemPackets1_11 this$0;
            {
                this.this$0 = blockItemPackets1_11;
            }

            @Override
            public void register() {
                this.handler(10::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                WindowTracker windowTracker = packetWrapper.user().get(WindowTracker.class);
                windowTracker.setInventory(null);
                windowTracker.setEntityId(-1);
            }
        });
        ((Protocol1_10To1_11)this.protocol).getEntityRewriter().filter().handler(this::lambda$registerPackets$1);
    }

    @Override
    protected void registerRewrites() {
        MappedLegacyBlockItem mappedLegacyBlockItem = this.replacementData.computeIfAbsent(52, BlockItemPackets1_11::lambda$registerRewrites$2);
        mappedLegacyBlockItem.setBlockEntityHandler(BlockItemPackets1_11::lambda$registerRewrites$3);
        this.enchantmentRewriter = new LegacyEnchantmentRewriter(this.nbtTagName);
        this.enchantmentRewriter.registerEnchantment(71, "\u00a7cCurse of Vanishing");
        this.enchantmentRewriter.registerEnchantment(10, "\u00a7cCurse of Binding");
        this.enchantmentRewriter.setHideLevelForEnchants(71, 10);
    }

    @Override
    public Item handleItemToClient(Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToClient(item);
        CompoundTag compoundTag = item.tag();
        if (compoundTag == null) {
            return item;
        }
        EntityIdRewriter.toClientItem(item, true);
        if (compoundTag.get("ench") instanceof ListTag) {
            this.enchantmentRewriter.rewriteEnchantmentsToClient(compoundTag, true);
        }
        if (compoundTag.get("StoredEnchantments") instanceof ListTag) {
            this.enchantmentRewriter.rewriteEnchantmentsToClient(compoundTag, false);
        }
        return item;
    }

    @Override
    public Item handleItemToServer(Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToServer(item);
        CompoundTag compoundTag = item.tag();
        if (compoundTag == null) {
            return item;
        }
        EntityIdRewriter.toServerItem(item, true);
        if (compoundTag.contains(this.nbtTagName + "|ench")) {
            this.enchantmentRewriter.rewriteEnchantmentsToServer(compoundTag, true);
        }
        if (compoundTag.contains(this.nbtTagName + "|StoredEnchantments")) {
            this.enchantmentRewriter.rewriteEnchantmentsToServer(compoundTag, false);
        }
        return item;
    }

    private boolean isLlama(UserConnection userConnection) {
        WindowTracker windowTracker = userConnection.get(WindowTracker.class);
        if (windowTracker.getInventory() != null && windowTracker.getInventory().equals("EntityHorse")) {
            Object t = userConnection.getEntityTracker(Protocol1_10To1_11.class);
            StoredEntityData storedEntityData = t.entityData(windowTracker.getEntityId());
            return storedEntityData != null && storedEntityData.type().is((EntityType)Entity1_11Types.EntityType.LIAMA);
        }
        return true;
    }

    private Optional<ChestedHorseStorage> getChestedHorse(UserConnection userConnection) {
        Object t;
        StoredEntityData storedEntityData;
        WindowTracker windowTracker = userConnection.get(WindowTracker.class);
        if (windowTracker.getInventory() != null && windowTracker.getInventory().equals("EntityHorse") && (storedEntityData = (t = userConnection.getEntityTracker(Protocol1_10To1_11.class)).entityData(windowTracker.getEntityId())) != null) {
            return Optional.of(storedEntityData.get(ChestedHorseStorage.class));
        }
        return Optional.empty();
    }

    private int getNewSlotId(ChestedHorseStorage chestedHorseStorage, int n) {
        int n2 = !chestedHorseStorage.isChested() ? 38 : 53;
        int n3 = chestedHorseStorage.isChested() ? chestedHorseStorage.getLiamaStrength() : 0;
        int n4 = 2 + 3 * n3;
        int n5 = 15 - 3 * n3;
        if (n >= n4 && n2 > n + n5) {
            return n5 + n;
        }
        if (n == 1) {
            return 1;
        }
        return n;
    }

    private int getOldSlotId(ChestedHorseStorage chestedHorseStorage, int n) {
        int n2 = chestedHorseStorage.isChested() ? chestedHorseStorage.getLiamaStrength() : 0;
        int n3 = 2 + 3 * n2;
        int n4 = 2 + 3 * (chestedHorseStorage.isChested() ? 5 : 0);
        int n5 = n4 - n3;
        if (n == 1 || n >= n3 && n < n4) {
            return 1;
        }
        if (n >= n4) {
            return n - n5;
        }
        if (n == 0) {
            return 0;
        }
        return n;
    }

    private Item getNewItem(ChestedHorseStorage chestedHorseStorage, int n, Item item) {
        int n2 = chestedHorseStorage.isChested() ? chestedHorseStorage.getLiamaStrength() : 0;
        int n3 = 2 + 3 * n2;
        int n4 = 2 + 3 * (chestedHorseStorage.isChested() ? 5 : 0);
        if (n >= n3 && n < n4) {
            return new DataItem(166, 1, 0, this.getNamedTag("\u00a74SLOT DISABLED"));
        }
        if (n == 1) {
            return null;
        }
        return item;
    }

    private static CompoundTag lambda$registerRewrites$3(int n, CompoundTag compoundTag) {
        EntityIdRewriter.toClientSpawner(compoundTag, true);
        return compoundTag;
    }

    private static MappedLegacyBlockItem lambda$registerRewrites$2(int n) {
        return new MappedLegacyBlockItem(52, -1, null, false);
    }

    private void lambda$registerPackets$1(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        if (metadata.metaType().type().equals(Type.ITEM)) {
            metadata.setValue(this.handleItemToClient((Item)metadata.getValue()));
        }
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
        Chunk1_9_3_4Type chunk1_9_3_4Type = new Chunk1_9_3_4Type(clientWorld);
        Chunk chunk = packetWrapper.passthrough(chunk1_9_3_4Type);
        this.handleChunk(chunk);
        for (CompoundTag compoundTag : chunk.getBlockEntities()) {
            String string;
            Object t = compoundTag.get("id");
            if (!(t instanceof StringTag) || !(string = (String)((Tag)t).getValue()).equals("minecraft:sign")) continue;
            ((StringTag)t).setValue("Sign");
        }
    }

    static boolean access$000(BlockItemPackets1_11 blockItemPackets1_11, UserConnection userConnection) {
        return blockItemPackets1_11.isLlama(userConnection);
    }

    static Optional access$100(BlockItemPackets1_11 blockItemPackets1_11, UserConnection userConnection) {
        return blockItemPackets1_11.getChestedHorse(userConnection);
    }

    static int access$200(BlockItemPackets1_11 blockItemPackets1_11, ChestedHorseStorage chestedHorseStorage, int n) {
        return blockItemPackets1_11.getNewSlotId(chestedHorseStorage, n);
    }

    static Item access$300(BlockItemPackets1_11 blockItemPackets1_11, ChestedHorseStorage chestedHorseStorage, int n, Item item) {
        return blockItemPackets1_11.getNewItem(chestedHorseStorage, n, item);
    }

    static int access$400(BlockItemPackets1_11 blockItemPackets1_11, ChestedHorseStorage chestedHorseStorage, int n) {
        return blockItemPackets1_11.getOldSlotId(chestedHorseStorage, n);
    }
}

